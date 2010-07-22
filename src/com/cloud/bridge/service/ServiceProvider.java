/**
 *  Copyright (C) 2010 Cloud.com, Inc.  All rights reserved.
 * 
 * This software is licensed under the GNU General Public License v3 or later.
 * 
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.cloud.bridge.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.amazon.s3.AmazonS3SkeletonInterface;
import com.amazon.ec2.AmazonEC2SkeletonInterface;
import com.cloud.bridge.model.MHost;
import com.cloud.bridge.model.SHost;
import com.cloud.bridge.persist.PersistContext;
import com.cloud.bridge.persist.dao.MHostDao;
import com.cloud.bridge.persist.dao.SHostDao;
import com.cloud.bridge.service.core.ec2.EC2Engine;
import com.cloud.bridge.service.core.s3.S3Engine;
import com.cloud.bridge.service.exception.ConfigurationException;
import com.cloud.bridge.util.ConfigurationHelper;
import com.cloud.bridge.util.DateHelper;
import com.cloud.bridge.util.NetHelper;

/**
 * @author Kelven Yang
 */
public class ServiceProvider {
    protected final static Logger logger = Logger.getLogger(ServiceProvider.class);
    
    public final static long HEARTBEAT_INTERVAL = 10000;
    
    private static ServiceProvider instance;
    
    private Map<Class<?>, Object> serviceMap = new HashMap<Class<?>, Object>();
    private Timer timer = new Timer();;
    private MHost mhost;
    private Properties properties;
    private boolean useSubDomain = false;			// use DNS sub domain for bucket name
    
    private S3Engine engine;
    private EC2Engine EC2_engine;
    
    protected ServiceProvider() {
    	// register service implementation object
    	engine = new S3Engine();
    	EC2_engine = new EC2Engine();
    	serviceMap.put(AmazonS3SkeletonInterface.class, new S3SoapServiceImpl(engine));
    	serviceMap.put(AmazonEC2SkeletonInterface.class, new EC2SoapServiceImpl(EC2_engine));
    }
    
    public synchronized static ServiceProvider getInstance() {
    	if(instance == null) {
    		instance = new ServiceProvider();
    		try {
    			instance.initialize();
    			PersistContext.commitTransaction();
    		} catch(Throwable e) {
    			logger.error("Unexpected exception " + e.getMessage(), e);
    		} finally {
    			PersistContext.closeSession();
    		}
    	}
    	return instance;
    }
    
    public long getManagementHostId() {
    	// we want to limit mhost within its own session, id of the value will be returned 
    	long mhostId = 0;
    	if(mhost != null)
    		mhostId = mhost.getId() != null ? mhost.getId().longValue() : 0L;
    	return mhostId;
    }
    
    public S3Engine getS3Engine() {
    	return engine;
    }
    
    public EC2Engine getEC2Engine() {
    	return EC2_engine;
    }

    public boolean getUseSubDomain() {
    	return useSubDomain;
    }
    
    public Properties getStartupProperties() {
    	return properties;
    }
    
    public UserInfo getUserInfo(String accessKey) {
    	// TODO integrate with Cloud.com authentcation service
    	UserInfo info = new UserInfo();
    	info.setAccessKey(accessKey);
    	info.setSecretKey(accessKey);
    	info.setCanonicalUserId(accessKey);
    	info.setDescription("TODO");
    	return info;
    }
    
    protected void initialize() {
    	if(logger.isInfoEnabled())
    		logger.info("Initializing ServiceProvider...");
    	
    	File file = ConfigurationHelper.findConfigurationFile("log4j-cloud.xml");
    	if(file != null) {
			System.out.println("Log4j configuration from : " + file.getAbsolutePath());
			DOMConfigurator.configureAndWatch(file.getAbsolutePath(), 10000);
    	} else {
			System.out.println("Configure log4j with default properties");
    	}
    	
    	loadStartupProperties();
    	String hostKey = properties.getProperty("host.key");
    	if(hostKey == null) {
    		InetAddress inetAddr = NetHelper.getFirstNonLoopbackLocalInetAddress();
    		if(inetAddr != null)
    			hostKey = NetHelper.getMacAddress(inetAddr);
    	}
    	if(hostKey == null) 
    		throw new ConfigurationException("Please configure host.key property in cloud-bridge.properites");
    	String host = properties.getProperty("host");
    	if(host == null)
    		host = NetHelper.getHostName();
    	
    	if(properties.get("bucket.dns") != null && 
    		((String)properties.get("bucket.dns")).equalsIgnoreCase("true")) {
    		useSubDomain = true;
    	}
    	
    	setupHost(hostKey, host);
    	
    	// we will commit and start a new transaction to allow host info be flushed to DB
    	PersistContext.flush();

    	String localStorageRoot = properties.getProperty("storage.root");
    	if(localStorageRoot != null)
    		setupLocalStorage(localStorageRoot);
    	
    	timer.schedule(getHeartbeatTask(), HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL);
    	
    	if(logger.isInfoEnabled())
    		logger.info("ServiceProvider initialized");
    }
    
    private void loadStartupProperties() {
    	File propertiesFile = ConfigurationHelper.findConfigurationFile("cloud-bridge.properties");
    	properties = new Properties(); 
    	if(propertiesFile != null) {
    		try {
				properties.load(new FileInputStream(propertiesFile));
			} catch (FileNotFoundException e) {
				logger.warn("Unable to open properties file: " + propertiesFile.getAbsolutePath(), e);
			} catch (IOException e) {
				logger.warn("Unable to read properties file: " + propertiesFile.getAbsolutePath(), e);
			}
			
			logger.info("Use startup properties file: " + propertiesFile.getAbsolutePath());
    	} else {
    		if(logger.isInfoEnabled())
    			logger.info("Startup properties is not found.");
    	}
    }
    
    private TimerTask getHeartbeatTask() {
    	return new TimerTask() {

			@Override
			public void run() {
				try {
					MHostDao mhostDao = new MHostDao();
					mhost.setLastHeartbeatTime(DateHelper.currentGMTTime());
					mhostDao.update(mhost);
					PersistContext.commitTransaction();
				} catch(Throwable e){
					logger.error("Unexpected exception " + e.getMessage(), e);
				} finally {
					PersistContext.closeSession();
				}
			}
    	};
    }
    
	private void setupHost(String hostKey, String host) {
		MHostDao mhostDao = new MHostDao();
		mhost = mhostDao.getByHostKey(hostKey);
		if(mhost == null) {
			mhost = new MHost();
			mhost.setHostKey(hostKey);
			mhost.setHost(host);
			mhost.setLastHeartbeatTime(DateHelper.currentGMTTime());
			mhostDao.save(mhost);
		} else {
			mhost.setHost(host);
			mhostDao.update(mhost);
		}
	}
	
	private void setupLocalStorage(String storageRoot) {
		SHostDao shostDao = new SHostDao();
		SHost shost = shostDao.getLocalStorageHost(mhost.getId(), storageRoot);
		if(shost == null) {
			shost = new SHost();
			shost.setMhost(mhost);
			mhost.getLocalSHosts().add(shost);
			shost.setHostType(SHost.STORAGE_HOST_TYPE_LOCAL);
			shost.setHost(NetHelper.getHostName());
			shost.setExportRoot(storageRoot);
			PersistContext.getSession().save(shost);
		}
	}
    
    public void shutdown() {
    	timer.cancel();
    	
    	if(logger.isInfoEnabled())
    		logger.info("ServiceProvider stopped");
    }
    
    private static <T> T getProxy(Class<?> serviceInterface, final T serviceObject) {
    	return (T) Proxy.newProxyInstance(serviceObject.getClass().getClassLoader(),
              new Class[] { serviceInterface },
              new InvocationHandler() {
                  public Object invoke(Object proxy, Method method, 
                    Object[] args) throws Throwable {
                	  try {
                		  Object result = method.invoke(serviceObject, args);
                		  PersistContext.commitTransaction();
                		  return result;
                	  } catch(Throwable e) {
                		  // log the exception to help debugging
                		  logger.warn("Unhandled exception " + e.getMessage(), e);
                		  
                		  // rethrow the exception to Axis
                		  throw e;
                	  } finally {
                		  PersistContext.closeSession();
                	  }
                  }
              });
    }
    
    public <T> T getServiceImpl(Class<?> serviceInterface) {
    	return getProxy(serviceInterface, (T)serviceMap.get(serviceInterface));
    }
}
