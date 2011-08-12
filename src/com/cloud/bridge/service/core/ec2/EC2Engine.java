/*
 * Copyright (C) 2011 Citrix Systems, Inc.  All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloud.bridge.service.core.ec2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.SignatureException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.cloud.bridge.persist.dao.OfferingDao;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.service.exception.EC2ServiceException;
import com.cloud.bridge.service.exception.EC2ServiceException.ClientError;
import com.cloud.bridge.service.exception.EC2ServiceException.ServerError;
import com.cloud.bridge.util.ConfigurationHelper;
import com.cloud.bridge.util.JsonAccessor;
import com.cloud.bridge.util.Tuple;
import com.cloud.stack.ApiConstants;
import com.cloud.stack.CloudStackAccount;
import com.cloud.stack.CloudStackCapabilities;
import com.cloud.stack.CloudStackClient;
import com.cloud.stack.CloudStackCommand;
import com.cloud.stack.CloudStackDiskOfferings;
import com.cloud.stack.CloudStackInfoResponse;
import com.cloud.stack.CloudStackIngressRule;
import com.cloud.stack.CloudStackIpAddress;
import com.cloud.stack.CloudStackKeyPair;
import com.cloud.stack.CloudStackNetwork;
import com.cloud.stack.CloudStackNetworkOffering;
import com.cloud.stack.CloudStackNic;
import com.cloud.stack.CloudStackOsType;
import com.cloud.stack.CloudStackPasswordData;
import com.cloud.stack.CloudStackResourceLimit;
import com.cloud.stack.CloudStackSecurityGroup;
import com.cloud.stack.CloudStackSecurityGroupIngress;
import com.cloud.stack.CloudStackSnapshot;
import com.cloud.stack.CloudStackTemplate;
import com.cloud.stack.CloudStackUserVm;
import com.cloud.stack.CloudStackVolume;
import com.cloud.stack.CloudStackZone;
import com.google.gson.reflect.TypeToken;


public class EC2Engine {
    protected final static Logger logger = Logger.getLogger(EC2Engine.class);
    private DocumentBuilderFactory dbf = null;
    private String managementServer    = null;
    private String cloudAPIPort        = null;
    
    // -> in milliseconds, time interval to wait before asynch check on a jobId's status
    private int CLOUD_STACK_VERSION_2_0 = 200;
    private int CLOUD_STACK_VERSION_2_1 = 210;
    private int CLOUD_STACK_VERSION_2_2 = 220;
    private int cloudStackVersion;
    
    private CloudStackClient _client;
   
    public EC2Engine() throws IOException {
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware( true );
		
		loadConfigValues();
    }
    
    /**
     * Which management server to we talk to?  
     * Load a mapping form Amazon values for 'instanceType' to cloud defined
     * diskOfferingId and serviceOfferingId.
     */
    private void loadConfigValues() throws IOException 
    {
        File propertiesFile = ConfigurationHelper.findConfigurationFile("ec2-service.properties");
        if (null != propertiesFile) 
        {
       		logger.info("Use EC2 properties file: " + propertiesFile.getAbsolutePath());
       	    Properties EC2Prop = new Properties();
        	try {
    			EC2Prop.load( new FileInputStream( propertiesFile ));
    		} catch (FileNotFoundException e) {
    			logger.warn("Unable to open properties file: " + propertiesFile.getAbsolutePath(), e);
    		} catch (IOException e) {
    			logger.warn("Unable to read properties file: " + propertiesFile.getAbsolutePath(), e);
    		}
   	        managementServer = EC2Prop.getProperty( "managementServer" );
		    cloudAPIPort     = EC2Prop.getProperty( "cloudAPIPort", null );
		    
		    _client = new CloudStackClient(managementServer, 
		    	cloudAPIPort != null ? Integer.parseInt(cloudAPIPort) : 80, false);
  	        
   	        try {
   	        	// These are all going away, since cloudStackClient has it's own intervals configured
   	        	Integer.parseInt( EC2Prop.getProperty( "pollInterval1", "100"  ));
   	            Integer.parseInt( EC2Prop.getProperty( "pollInterval2", "100"  ));
   	            Integer.parseInt( EC2Prop.getProperty( "pollInterval3", "100"  ));
   	            Integer.parseInt( EC2Prop.getProperty( "pollInterval4", "1000" ));
   	            Integer.parseInt( EC2Prop.getProperty( "pollInterval5", "100"  ));
   	            Integer.parseInt( EC2Prop.getProperty( "pollInterval6", "100"  ));
   	        } catch( Exception e ) {
    			logger.warn("Invalid polling interval, using default values", e);
   	        }
   	        
   	        try {
   	        	cloudStackVersion = getCloudStackVersion(EC2Prop);
   	        } catch(Exception e) {
   	    		logger.error( "EC2 Loading Configuration file - ", e);
   	        }
            
     	    OfferingDao ofDao = new OfferingDao();
     	    try {
    	 	    if(ofDao.getOfferingCount() == 0) 
     	    	{
    	 	    	String strValue = EC2Prop.getProperty("m1.small.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("m1.small", strValue);
    	 	    	}
    	 	    	
    	 	    	strValue = EC2Prop.getProperty("m1.large.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("m1.large", strValue);
    	 	    	}
    	 	    	
    	 	    	strValue = EC2Prop.getProperty("m1.xlarge.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("m1.xlarge", strValue);
    	 	    	}

    	 	    	strValue = EC2Prop.getProperty("c1.medium.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("c1.medium", strValue);
    	 	    	}

    	 	    	strValue = EC2Prop.getProperty("c1.xlarge.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("c1.xlarge", strValue);
    	 	    	}

    	 	    	strValue = EC2Prop.getProperty("m2.xlarge.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("m2.xlarge", strValue);
    	 	    	}

    	 	    	strValue = EC2Prop.getProperty("m2.2xlarge.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("m2.2xlarge", strValue);
    	 	    	}

    	 	    	strValue = EC2Prop.getProperty("m2.4xlarge.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("m2.4xlarge", strValue);
    	 	    	}
    	 	    	
    	 	    	strValue = EC2Prop.getProperty("cc1.4xlarge.serviceId");
    	 	    	if(strValue != null) {
    	 	    		ofDao.setOfferMapping("cc1.4xlarge", strValue);
    	 	    	}
    	 	    }
     	    } catch(Exception e) {
     	    	logger.error("Unexpected exception ", e);
     	    }
    	} else logger.error( "ec2-service.properties not found" );
	}
    
    private int getCloudStackVersion(Properties prop) throws Exception 
    {	
    	String versionProp = prop.getProperty( "cloudstackVersion", null );
    	if(versionProp!=null){
    		if(versionProp.equals("2.0")){
    			return CLOUD_STACK_VERSION_2_0;
    		}
    		else if(versionProp.equals("2.0.0")){
    			return CLOUD_STACK_VERSION_2_0;
    		}
    		else if(versionProp.equals("2.1.0")){
    			return CLOUD_STACK_VERSION_2_1;
    		}
    		else if(versionProp.equals("2.2.0")){
    			return CLOUD_STACK_VERSION_2_2;
    		}
    	}
    	
    	// Let the version specified in the properties file have a higher precedence 
    	// than the CloudStack's listCapabilities command. Only check with the CloudStack
    	// if no version is specified in the properties file.
    	return getCloudStackVersionBasedOnCapabilitiesCmd();
	}
    
    private int getCloudStackVersionBasedOnCapabilitiesCmd() throws Exception {
    	CloudStackCommand command = new CloudStackCommand("listCapabilities");
    	CloudStackCapabilities resp = cloudStackCall(command, true, "listcapabilitiesresponse", "capability", CloudStackCapabilities.class);
    	if (resp != null) {
    		if (resp.getCloudStackVersion().startsWith("2.2.")) {
    			return CLOUD_STACK_VERSION_2_2;
    		}
    	} else {
    		// 2.1 and before don't have a listCapabilities command
    		return CLOUD_STACK_VERSION_2_1;
    	}
    	// This should only fall through after we move out of 2.2.x timeframe
    	return CLOUD_STACK_VERSION_2_2; // default
    }

	/**
     * We have to generate the URL explicity here because we are using the given inputs as the 
     * Cloud API keys.  Make sure the account represented by the two parameters exists by performing
     * a harmless operation.   If the parameters are bogus then the signature generated will be
     * wrong and the Cloud.com's Management server will send us a "401 unauthorized" failure
     * (which results in an exception being thrown).  
     * 
     * @param accessKey - Cloud.com's API access key
     * @param secretKey - Cloud.com's API secret key
     * @return true if the account exists, false otherwise
     */
    public boolean validateAccount( String accessKey, String secretKey ) {
    	Account[] accts = getAccounts(accessKey, secretKey);
    	if (accts == null || accts.length == 0) {
    		return false;
    	}
    	return true;
    }

    /**
     * Remember to pre-sort the parameters in alphabetical order.
     * 
     * @param request
     * @return was the security group created?
     */
    public boolean createSecurityGroup(EC2SecurityGroup request) {
    	if (null == request.getDescription() || null == request.getName()) 
			 throw new EC2ServiceException(ServerError.InternalError, "Both name & description are required");

    	try {
    		CloudStackCommand cmd = new CloudStackCommand("createSecurityGroup");
    		if (cmd != null) {
    			cmd.setParam("description", request.getDescription());
    			cmd.setParam("name", request.getName());
    		}
    		CloudStackInfoResponse resp = cloudStackCall(cmd, true, "createsecuritygroupresponse", null, CloudStackInfoResponse.class);
    		// TODO: this should be reworked... this is not 'good enough'
    		if (resp != null && resp.getId() != null) {
    			return resp.getSuccess();
    		}
    		return false;
    	} catch( Exception e ) {
    		logger.error( "EC2 CreateSecurityGroup - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public boolean deleteSecurityGroup(EC2SecurityGroup request) 
    {
    	if (null == request.getName()) throw new EC2ServiceException(ServerError.InternalError, "Name is a required parameter");

    	try {
    		CloudStackCommand cmd = new CloudStackCommand("deleteSecurityGroup");
    		if (cmd != null) {
    			cmd.setParam("name", request.getName());
    		}
    		CloudStackInfoResponse resp = cloudStackCall(cmd, true, "deletesecuritygroupresponse", null, CloudStackInfoResponse.class);
    		if (resp != null && resp.getId() != null) {
    			return resp.getSuccess();
    		}
    		return false;
    	} catch( Exception e ) {
    		logger.error( "EC2 DeleteSecurityGroup - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public EC2DescribeSecurityGroupsResponse handleRequest( EC2DescribeSecurityGroups request ) 
    {
    	try {
    		EC2DescribeSecurityGroupsResponse response = listSecurityGroups( request.getGroupSet());
    		EC2GroupFilterSet gfs = request.getFilterSet();

    		if ( null == gfs )
    			return response;
    		else return gfs.evaluate( response );     
    	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 DescribeSecurityGroups - ", error);
    		throw error;   		
    	} catch( Exception e ) {
    		logger.error( "EC2 DescribeSecurityGroups - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }

    
    /**
     * CloudStack supports revoke only by using the ruleid of the ingress rule.   
     * We list all security groups and find the matching group and use the first ruleId we find.
     * 
     * @param request
     * @return
     */
 
    public boolean revokeSecurityGroup( EC2AuthorizeRevokeSecurityGroup request ) 
    {
		if (null == request.getName()) throw new EC2ServiceException(ServerError.InternalError, "Name is a required parameter");
        String[] groupSet = new String[1];
        groupSet[0] = request.getName();
        String ruleId = null;
        
		EC2IpPermission[] items = request.getIpPermissionSet();
        		
   	    try 
  	    {   EC2DescribeSecurityGroupsResponse response = listSecurityGroups( groupSet );
  	        EC2SecurityGroup[] groupInfo = response.getGroupSet();
   	    	
  	        for( int i=0; i < groupInfo.length && null == ruleId; i++ )
  	        {
  	        	EC2IpPermission[] perms = groupInfo[i].getIpPermissionSet();
  	        	for( int j=0; j < perms.length && null == ruleId; j++ )
  	        	{
  	        		ruleId = doesRuleMatch( items[0], perms[j] );
  	        	}
  	        }
  	        
  	        if (null == ruleId)
  	    		throw new EC2ServiceException(ClientError.InvalidGroup_NotFound, "Cannot find matching ruleid.");

   	    	CloudStackCommand cmd = new CloudStackCommand("revokeSecurityGroupIngress");
   	    	if (cmd != null) {
   	    		cmd.setParam("id", ruleId);
   	    	}
   	    	
   	    	CloudStackInfoResponse resp = cloudStackCall(cmd, true, "revokesecuritygroupingressresponse", null, CloudStackInfoResponse.class);
   	    	if (resp != null && resp.getId() != null) {
   	    		return resp.getSuccess();
   	    	}
   	    	return false;
      	} catch( Exception e ) {
   		    logger.error( "EC2 revokeSecurityGroupIngress" + " - " + e.getMessage());
   		    throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
   	    } 	
    }

    
    /**
     * authorizeSecurityGroup
     * 
     * @param request - ip permission parameters
     */
    public boolean authorizeSecurityGroup(EC2AuthorizeRevokeSecurityGroup request ) 
    {
		if (null == request.getName()) throw new EC2ServiceException(ServerError.InternalError, "Name is a required parameter");
    	
		EC2IpPermission[] items = request.getIpPermissionSet();
		
   	    try {
   	    	for (EC2IpPermission ipPerm : items) {
   	    		CloudStackCommand cmd = new CloudStackCommand("authorizeSecurityGroupIngress");
   	    		cmd.setParam("cidrlist", constructCIDRList( ipPerm.getIpRangeSet()));
   	    		
   	    		if (ipPerm.getProtocol().equalsIgnoreCase("icmp")) {
   	    			cmd.setParam("icmpCode", ipPerm.getToPort().toString());
   	    			cmd.setParam("icmpType", ipPerm.getFromPort().toString());
   	    		} else {
   	    			cmd.setParam("endPort", ipPerm.getToPort().toString());
   	    			cmd.setParam("startPort", ipPerm.getFromPort().toString());
   	    		}
    			cmd.setParam("protocol", ipPerm.getProtocol());
    			cmd.setParam("securityGroupName", request.getName());
    			EC2SecurityGroup[] groups = ipPerm.getUserSet();
    			int i = 0;
    			for (EC2SecurityGroup group : groups) {
    				cmd.setParam("userSecurityGroupList["+i+"].account", group.getAccount());
    				cmd.setParam("userSecurityGroupList["+i+"].group", group.getName());
    				i++;
    			}
    			CloudStackSecurityGroupIngress resp = cloudStackCall(cmd, true, "authorizesecuritygroupingressresponse", "securitygroup", CloudStackSecurityGroupIngress.class);
    			if (resp == null || resp.getRuleId() == null) {
    				throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    			}
   	    	}
   	    } catch(Exception e) {
   		    logger.error( "EC2 AuthorizeSecurityGroupIngress - ", e);
   		    throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
   	    }
		return true;
    }

    /**
    
     * Does the permission from the request (left) match the permission from the cloudStack query (right).
     * If the cloudStack rule matches then we return its ruleId.
     * 
     * @param permLeft
     * @param permRight
     * @return ruleId of the cloudstack rule
     */
	private String doesRuleMatch( EC2IpPermission permLeft, EC2IpPermission permRight )
	{
		int matches = 0;
		
		if (null != permLeft.getIcmpType() && null != permLeft.getIcmpCode())
		{
			if (null == permRight.getIcmpType() || null == permRight.getIcmpCode()) return null;
			    
			if (!permLeft.getIcmpType().equalsIgnoreCase( permRight.getIcmpType())) return null;
			if (!permLeft.getIcmpCode().equalsIgnoreCase( permRight.getIcmpCode())) return null;
			matches++;
		}
		
		// -> "Valid Values for EC2 security groups: tcp | udp | icmp or the corresponding protocol number (6 | 17 | 1)."
		if (null != permLeft.getProtocol())
		{
			if (null == permRight.getProtocol()) return null;
			
			String protocol = permLeft.getProtocol();
			     if (protocol.equals( "6"  )) protocol = "tcp";
			else if (protocol.equals( "17" )) protocol = "udp";
			else if (protocol.equals( "1"  )) protocol = "icmp";
			
			if (!protocol.equalsIgnoreCase( permRight.getProtocol())) return null;
			matches++;
		}
		
		
		if (null != permLeft.getCIDR())
		{
			if (null == permRight.getCIDR()) return null;
			
			if (!permLeft.getCIDR().equalsIgnoreCase( permRight.getCIDR())) return null;
			matches++;
		}

		// -> is the port(s) from the request (left) a match of the rule's port(s) 
		if (0 != permLeft.getFromPort())
		{
			// -> -1 means all ports match
			if (-1 != permLeft.getFromPort()) {
			   if (!(permLeft.getFromPort() == permRight.getFromPort() && permLeft.getToPort() == permRight.getToPort())) return null;
			}
			matches++;
		}
		
		// -> was permLeft set up properly with at least one property to match?
		if ( 0 == matches ) 
			 return null;
		else return permRight.getRuleId();
	}

	
    /**
     * Cloud Stack API takes a comma separated list of IP ranges as one parameter.
     * 
     * @throws UnsupportedEncodingException 
     */
    private String constructCIDRList( String[] ipRanges ) throws UnsupportedEncodingException 
    {
    	if (null == ipRanges || 0 == ipRanges.length) return null;  	
    	StringBuffer cidrList = new StringBuffer();
   	
    	for( int i=0; i < ipRanges.length; i++ ) {
    		if (0 < i) cidrList.append( "," );
    		cidrList.append( ipRanges[i] );
    	}
    	return cidrList.toString();
    }
    
    public EC2DescribeSnapshotsResponse handleRequest( EC2DescribeSnapshots request ) 
    {
		EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();
    	EC2SnapshotFilterSet sfs = request.getFilterSet();
		
    	try 
    	{   // -> query to get the volume size for each snapshot
    		EC2DescribeSnapshotsResponse response = listSnapshots( request.getSnapshotSet());
    		EC2Snapshot[] snapshots = response.getSnapshotSet();
    		for( int i=0; i < snapshots.length; i++ ) 
    		{
    			volumes = listVolumes( snapshots[i].getVolumeId(), null, volumes );
                EC2Volume[] volSet = volumes.getVolumeSet();
                if (0 < volSet.length) snapshots[i].setVolumeSize( volSet[0].getSize());
                volumes.reset();
    		}
    		
    	    if ( null == sfs )
     	   	     return response;
     	    else return sfs.evaluate( response );     
       	} 
    	catch( EC2ServiceException error ) {
    		logger.error( "EC2 DescribeSnapshots - ", error);
    		throw error;
    		
    	} 
    	catch( Exception e ) {
    		logger.error( "EC2 DescribeSnapshots - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    public EC2Snapshot createSnapshot( String volumeId ) {
		try {
			CloudStackCommand command = new CloudStackCommand("createSnapshot");
			command.setParam("volumeId", volumeId);
			
			CloudStackInfoResponse response = cloudStackCall(command, false, "createsnapshotresponse", null, CloudStackInfoResponse.class);
			if(response.getJobId() == null || response.getId() == null) {
				throw new Exception("Invalid CloudStack API response");
			}
			
			CloudStackCommand listSnapshotCmd = new CloudStackCommand("listSnapshots");
			listSnapshotCmd.setParam("id", String.valueOf(response.getId()));
			
			List<CloudStackSnapshot> tmpList = cloudStackListCall(listSnapshotCmd, "listsnapshotsresponse", "snapshot", 
				new TypeToken<List<CloudStackSnapshot>> () {}.getType());
			if(tmpList == null || tmpList.size() != 1)
				throw new Exception("Invalid CloudStack API response to list a just created snapshot");
			
			CloudStackSnapshot cloudSnapshot = tmpList.get(0);
			EC2Snapshot ec2Snapshot = new EC2Snapshot();
			
			ec2Snapshot.setId(String.valueOf(cloudSnapshot.getId()));
			ec2Snapshot.setName(cloudSnapshot.getName());
			ec2Snapshot.setType(cloudSnapshot.getSnapshotType());
			ec2Snapshot.setAccountName(cloudSnapshot.getAccountName());
			ec2Snapshot.setDomainId(String.valueOf(cloudSnapshot.getDomainId()));
			ec2Snapshot.setCreated(cloudSnapshot.getCreated());
			ec2Snapshot.setVolumeId(volumeId);
			
			CloudStackCommand listVolsCmd = new CloudStackCommand("listVolumes");
			listVolsCmd.setParam("id", volumeId);
			List<CloudStackVolume> vols = cloudStackListCall(listVolsCmd, "listvolumesresponse", "volume", 
				new TypeToken<List<CloudStackVolume>>() {}.getType());
			
			if(vols.size() > 0) {
				assert(vols.get(0).getSize() != null);
				int sizeInGB =(int)(vols.get(0).getSize().longValue()/1073741824);
				ec2Snapshot.setVolumeSize(sizeInGB);
			}
			
			return ec2Snapshot;
		} catch( Exception e ) {
    		logger.error( "EC2 CreateSnapshot - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public boolean deleteSnapshot(String snapshotId) {
       	try {
        	CloudStackCommand command = new CloudStackCommand("deleteSnapshot");
        	command.setParam("id", snapshotId);
	    	CloudStackInfoResponse response = cloudStackCall(command, false, "deletesnapshotresponse", null, CloudStackInfoResponse.class);
	    	if(response.getJobId() != null)
	    		return true;
	    	
	    	return false;
    	} catch(Exception e) {
    		logger.error( "EC2 DeleteSnapshot - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }
    
    public boolean modifyImageAttribute( EC2Image request ) 
    {
    	EC2DescribeImagesResponse images = new EC2DescribeImagesResponse();

    	try {
            images = listTemplates( request.getId(), images );
            EC2Image[] imageSet = images.getImageSet();

    		CloudStackCommand command = new CloudStackCommand("updateTemplate");
    		if (command != null) {
    			command.setParam("displayText", request.getDescription());
    			command.setParam("id", request.getId());
    			command.setParam("name", imageSet[0].getName());
    		}
    		
    		CloudStackInfoResponse response = cloudStackCall(command, true, "updatetemplateresponse", null, CloudStackInfoResponse.class);
    		return response.getSuccess();
	    } catch( Exception e ) {
		    logger.error( "EC2 ModifyImage - ", e);
		    throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
	    }
    }
    
    /*
     * internal Helper functions to wrap CloudStackCommands used commonly...
     * 
     */
    private List<CloudStackIpAddress> getCloudStackIpAddressesFromKeyValue(String key, String value) throws Exception {
		CloudStackCommand listIPAddrCmd = new CloudStackCommand("listPublicIpAddresses");
		if (listIPAddrCmd != null && key != null && value != null) {
			listIPAddrCmd.setParam(key, value);
		}
		return cloudStackListCall(listIPAddrCmd, "listpublicipaddressesresponse", "publicipaddress", new TypeToken<List<CloudStackIpAddress>> () {}.getType());
    }
    
    private CloudStackIpAddress getCloudStackIpAddressFromKeyValue(String key, String value) throws Exception {
    	List<CloudStackIpAddress> addrList = getCloudStackIpAddressesFromKeyValue(key, value);
		if (addrList == null || addrList.size() != 1) {
			throw new EC2ServiceException(ClientError.InvalidParameterValue, "Address not allocated to account.");
		}
		return addrList.get(0);
    }
    
    private List<CloudStackUserVm> getCloudStackUserVmsFromKeyValue(String key, String value) throws Exception {
    	CloudStackCommand listVmCmd = new CloudStackCommand("listVirtualMachines");
    	if (listVmCmd != null && key != null && value != null) {
    		listVmCmd.setParam(key, value);
    	}
    	
    	return cloudStackListCall(listVmCmd, "listvirtualmachinesresponse", "virtualmachine", new TypeToken<List<CloudStackUserVm>>() {}.getType());
    }
    
    private CloudStackUserVm getCloudStackUserVmFromKeyValue(String key, String value) throws Exception {
    	List<CloudStackUserVm> vmList = getCloudStackUserVmsFromKeyValue(key, value);
    	if (vmList == null || vmList.size() != 1) {
    		throw new EC2ServiceException(ClientError.InvalidParameterValue, "Instance not found");
    	}
    	
    	return vmList.get(0);
    }
    
    private List<CloudStackKeyPair> getCloudStackKeyPairsFromKeyValue(String key, String value) throws Exception {
    	CloudStackCommand listKeyPairCmd = new CloudStackCommand("listSSHKeyPairs");
    	if (listKeyPairCmd != null && key != null && value != null) {
    		listKeyPairCmd.setParam(key, value);
    	}
    	
    	return cloudStackListCall(listKeyPairCmd, "listsshkeypairsresponse", "keypair", new TypeToken<List<CloudStackKeyPair>>() {}.getType());
    }
    
    /**
     * If given a specific list of snapshots of interest, then only values from those snapshots are returned.
     * 
     * @param interestedShots - can be null, should be a subset of all snapshots
     */
    
    private EC2DescribeSnapshotsResponse listSnapshots( String[] interestedShots ) throws Exception {
		EC2DescribeSnapshotsResponse snapshots = new EC2DescribeSnapshotsResponse();
		
		List<CloudStackSnapshot> cloudSnapshots;
		if(interestedShots == null || interestedShots.length == 0) {
			CloudStackCommand cmd = new CloudStackCommand("listSnapshots");
			cloudSnapshots = this.cloudStackListCall(cmd, "listsnapshotsresponse", "snapshot", 
				new TypeToken<List<CloudStackSnapshot>> () {}.getType());
		} else {
			cloudSnapshots = new ArrayList<CloudStackSnapshot>();
			
			for(String id : interestedShots) {
				CloudStackCommand cmd = new CloudStackCommand("listSnapshots");
				cmd.setParam("id", id);

				List<CloudStackSnapshot> tmpList = this.cloudStackListCall(cmd, "listsnapshotsresponse", "snapshot", 
					new TypeToken<List<CloudStackSnapshot>> () {}.getType());
				cloudSnapshots.addAll(tmpList);
			}
		}
		
		if (cloudSnapshots == null) { 
			return null;
		}
		
		for(CloudStackSnapshot cloudSnapshot : cloudSnapshots) {
			EC2Snapshot shot  = new EC2Snapshot();
	        shot.setId(String.valueOf(cloudSnapshot.getId()));
			shot.setName(cloudSnapshot.getName());
			shot.setVolumeId(String.valueOf(cloudSnapshot.getVolumeId()));
			shot.setType(cloudSnapshot.getSnapshotType());
			shot.setState(cloudSnapshot.getState());
			shot.setCreated(cloudSnapshot.getCreated());
			shot.setAccountName(cloudSnapshot.getAccountName());
			shot.setDomainId(String.valueOf(cloudSnapshot.getDomainId()));
			
			snapshots.addSnapshot(shot);
		}
	    return snapshots;
	}


    // handlers
    public EC2PasswordData getPasswordData(String instanceId) {
    	try {
	    	CloudStackCommand command = new CloudStackCommand("getVMPassword");
			if (command != null) {
				command.setParam("id", instanceId);
			}
			CloudStackPasswordData callResponse = cloudStackCall(command, true, "getvmpasswordresponse", "encryptedPassword", CloudStackPasswordData.class);
			EC2PasswordData passwdData = new EC2PasswordData();
			if (callResponse != null) {
				passwdData.setInstanceId(instanceId);
				passwdData.setEncryptedPassword(callResponse.getEncryptedpassword());
			}
			return passwdData;
    	} catch(Exception e) {
    		logger.error("EC2 GetPasswordData - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }

    public EC2DescribeKeyPairsResponse handleRequest( EC2DescribeKeyPairs request ) {
    	try {
    		EC2KeyPairFilterSet filterSet = request.getKeyFilterSet();
    		String[] keyNames = request.getKeyNames();

    		List<CloudStackKeyPair> keyPairs = getCloudStackKeyPairsFromKeyValue(null, null);
    		
    		// Let's trim the list of keypairs to only the ones listed in keyNames
    		if (keyPairs != null && keyNames != null && keyNames.length > 0) {
	    		for (CloudStackKeyPair keyPair : keyPairs) {
	    			boolean matched = false;
	    			for (String keyName : keyNames) {
	    				if (keyPair.getName().contains(keyName)) {
	    					matched = true;
	    					break;
	    				}
	    			}
	    			if (matched == false) {
	    				keyPairs.remove(keyPair);
	    			}
	    		}
    		}
    		
    		if (keyPairs.isEmpty() == true) {
    			throw new EC2ServiceException(ServerError.InternalError, "No keypairs left!");
    		}
    		
    		// this should be reworked... converting from CloudStackKeyPairResponse to EC2SSHKeyPair is dumb
    		List<EC2SSHKeyPair> keyPairsList = new ArrayList<EC2SSHKeyPair>();
    		
    		for (CloudStackKeyPair respKeyPair: keyPairs) {
    			EC2SSHKeyPair ec2KeyPair = new EC2SSHKeyPair();
    			ec2KeyPair.setFingerprint(respKeyPair.getFingerprint());
    			ec2KeyPair.setKeyName(respKeyPair.getName());
    			ec2KeyPair.setPrivateKey(respKeyPair.getPrivatekey());
    			keyPairsList.add(ec2KeyPair);
    		}
    		
    		return filterSet.evaluate(keyPairsList);
    	} catch(Exception e) {
    		logger.error("EC2 DescribeKeyPairs - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public boolean handleRequest( EC2DeleteKeyPair request ) {
    	try {
    		CloudStackCommand command = new CloudStackCommand("deleteSSHKeyPair");
    		if (command != null) {
    			command.setParam("name", request.getKeyName());
    		}
			CloudStackInfoResponse callResponse = cloudStackCall(command, true, "deletekeypairresponse", null, CloudStackInfoResponse.class);
	    	if (callResponse == null) { 
				throw new Exception("Ivalid CloudStack API response");
	    	}
	    	
	    	return callResponse.getSuccess();
    	} catch(Exception e) {
    		logger.error("EC2 DeleteKeyPair - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public EC2SSHKeyPair handleRequest( EC2CreateKeyPair request ) {
    	try {
    		CloudStackCommand command = new CloudStackCommand("createSSHKeyPair");
    		if (command != null) {
    			command.setParam("name", request.getKeyName());
    		}
    		CloudStackKeyPair callResponse = cloudStackCall(command, true, "createkeypairresponse", "keypair", CloudStackKeyPair.class);
			if (callResponse == null) {
				throw new Exception("Ivalid CloudStack API response");
    		}

    		EC2SSHKeyPair response = new EC2SSHKeyPair();
    		response.setFingerprint(callResponse.getFingerprint());
			response.setKeyName(callResponse.getName());
			response.setPrivateKey(callResponse.getPrivatekey());

			return response;
    		
    	} catch (Exception e) {
    		logger.error("EC2 CreateKeyPair - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public EC2SSHKeyPair handleRequest( EC2ImportKeyPair request ) {
    	try {
    		CloudStackCommand command = new CloudStackCommand("registerSSHKeyPair");
    		if (command != null) {
    			command.setParam("name", request.getKeyName());
    			command.setParam("publickey", request.getPublicKeyMaterial());
    		}
    		CloudStackKeyPair callResponse = cloudStackCall(command, true, "registerkeypairresponse", "keypair", CloudStackKeyPair.class);
			if (callResponse == null) {
				throw new Exception("Ivalid CloudStack API response");
    		}

    		EC2SSHKeyPair response = new EC2SSHKeyPair();
    		response.setFingerprint(callResponse.getFingerprint());
			response.setKeyName(callResponse.getName());
			response.setPrivateKey(callResponse.getPrivatekey());
    		
    		return response;
    	} catch (Exception e) {
    		logger.error("EC2 ImportKeyPair - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }

    public EC2DescribeAddressesResponse handleRequest( EC2DescribeAddresses request ) {
    	try {
            EC2DescribeAddressesResponse response = new EC2DescribeAddressesResponse();

            List<CloudStackIpAddress> addrList = getCloudStackIpAddressesFromKeyValue(null, null);
            
        	for (CloudStackIpAddress addr: addrList) {
        		// remember, if no filters are set, request.inPublicIpSet always returns true
        		if (request.inPublicIpSet(addr.getIpAddress())) {
            		EC2Address ec2Address = new EC2Address();
        			ec2Address.setIpAddress(addr.getIpAddress());
        			if (addr.getVirtualMachineId() != null) 
        				ec2Address.setAssociatedInstanceId(addr.getVirtualMachineId().toString());
        			response.addAddress(ec2Address);
        		}
        	}
        	return response;
    	} catch(Exception e) {
    		logger.error("EC2 DescribeAddresses - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
	public boolean handleRequest(EC2ReleaseAddress request) {
		try {
			CloudStackIpAddress cloudIp = getCloudStackIpAddressFromKeyValue("ipaddress", request.getPublicIp());

			CloudStackCommand command = new CloudStackCommand("disassociateIpAddress");
	    	if (command != null) {
	    		command.setParam("id", cloudIp.getId().toString());
	    	}
	    	CloudStackInfoResponse response = cloudStackCall(command, true, "disassociateipaddressresponse", null, CloudStackInfoResponse.class);
	    	if (response != null) {
	    		return response.getSuccess();
	    	}
		} catch(Exception e) {
			logger.error("EC2 ReleaseAddress - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
		}
		return false;
	}

	public boolean handleRequest( EC2AssociateAddress request ) {
		try {
			CloudStackIpAddress cloudIp = getCloudStackIpAddressFromKeyValue("ipaddress", request.getPublicIp());
	    	CloudStackUserVm cloudVm = getCloudStackUserVmFromKeyValue("id", request.getInstanceId());
	    	
	    	CloudStackCommand command = new CloudStackCommand("enableStaticNat");
	    	if (command != null) {
	    		command.setParam("ipAddressId", cloudIp.getId().toString());
	    		command.setParam("virtualMachineId", cloudVm.getId().toString());
	    	}
	    	CloudStackInfoResponse response = cloudStackCall(command, true, "enablestaticnatresponse", null, CloudStackInfoResponse.class);
	    	if (response != null) {
	    		return response.getSuccess();
	    	}
			
		} catch(Exception e) {
			logger.error( "EC2 AssociateAddress - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
		}
		return false;
	}

	public boolean handleRequest( EC2DisassociateAddress request ) {
		try {
			CloudStackIpAddress cloudIp = getCloudStackIpAddressFromKeyValue("ipaddress", request.getPublicIp());

			CloudStackCommand command = new CloudStackCommand("disableStaticNat");
	    	if (command != null) {
	    		command.setParam("ipAddressId", cloudIp.getId().toString());
	    	}
	    	
	    	CloudStackInfoResponse response = cloudStackCall(command, true, "disablestaticnatresponse", null, CloudStackInfoResponse.class);
	    	if (response != null) {
	    		return response.getSuccess();
	    	}
		} catch(Exception e) {
			logger.error( "EC2 DisassociateAddress - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
		}
		return false;
	}

	public EC2Address handleRequest( EC2AllocateAddress request )
    {
    	try {
	    	CloudStackCommand command = new CloudStackCommand("associateIpAddress");
	    	if (command != null) {
	    			command.setParam("zoneId", toZoneId(null));
	    	}
	    	CloudStackInfoResponse response = cloudStackCall(command, false, "associateipaddressresponse", null, CloudStackInfoResponse.class);
	    	if (response.getJobId() == null || response.getId() == null) {
	    		throw new Exception("Invalid CloudStack API response");			
	    	}
	    	
	    	CloudStackIpAddress cloudIp = getCloudStackIpAddressFromKeyValue("id", String.valueOf(response.getId()));

	    	EC2Address ec2Address = new EC2Address();
	    	ec2Address.setAssociatedInstanceId(String.valueOf(cloudIp.getId()));
	    	ec2Address.setIpAddress(cloudIp.getIpAddress());
	    	
	    	return ec2Address;
    	} catch(Exception e) { 
       		logger.error( "EC2 AllocateAddress - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    /**
     * We only support the imageSet version of this call or when no search parameters are passed
     * which results in asking for all templates.
     */
    public EC2DescribeImagesResponse handleRequest(EC2DescribeImages request) 
    {
    	EC2DescribeImagesResponse images = new EC2DescribeImagesResponse();
   
    	try {
    		String[] templateIds = request.getImageSet();
    	
   	        if ( 0 == templateIds.length ) {
    	         return listTemplates( null, images );
   	        }
   	        
   	        for( int i=0; i < templateIds.length; i++ ) {
               images = listTemplates( templateIds[i], images );
   	        }
   	        return images;
   	        
       	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 DescribeImages - ", error);
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DescribeImages - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    /**
     * Amazon API just gives us the instanceId to create the template from.
     * But our createTemplate function requires the volumeId and osTypeId.  
     * So to get that we must make the following sequence of cloud API calls:
     * 1) listVolumes&virtualMachineId=   -- gets the volumeId
     * 2) listVirtualMachinees&id=        -- gets the templateId
     * 3) listTemplates&id=               -- gets the osTypeId
     * 
     * If we have to start and stop the VM in question then this function is
     * going to take a long time to complete.
     */
    public EC2CreateImageResponse handleRequest(EC2CreateImage request) 
    {
    	EC2CreateImageResponse response = null;
    	boolean needsRestart = false;
    	String volumeId      = null;
    	
    	try {
    		// [A] Creating a template from a VM volume should be from the ROOT volume
    		//     Also for this to work the VM must be in a Stopped state so we 'reboot' it if its not
    	    EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();
            volumes = listVolumes( null, request.getInstanceId(), volumes );
            EC2Volume[] volSet = volumes.getVolumeSet();
            for (EC2Volume vol : volSet) {
            	 if (vol.getType().equalsIgnoreCase( "ROOT" )) {
            		 String vmState = vol.getVMState();
            		 if (vmState.equalsIgnoreCase( "running" ) || vmState.equalsIgnoreCase( "starting" )) {
            			 needsRestart = true;
            			 if (!stopVirtualMachine( request.getInstanceId() ))
            		         throw new EC2ServiceException(ClientError.IncorrectState, "CreateImage - instance must be in a stopped state");
            		 }           		 
            		 volumeId = vol.getId();
            		 break;
            	 }
            }
   	
            // [B] The parameters must be in sorted order for proper signature generation
    	    EC2DescribeInstancesResponse instances = new EC2DescribeInstancesResponse();
    	    instances = lookupInstances( request.getInstanceId(), instances );
    	    EC2Instance[] instanceSet = instances.getInstanceSet();
    	    String templateId = instanceSet[0].getTemplateId();
    	    
        	EC2DescribeImagesResponse images = new EC2DescribeImagesResponse();
            images = listTemplates( templateId, images );
            EC2Image[] imageSet = images.getImageSet();
            String osTypeId = imageSet[0].getOsTypeId();
            
            CloudStackCommand cmd = new CloudStackCommand("createTemplate");
            if (cmd != null) {
            	cmd.setParam("displayText", (request.getDescription() == null ? "" : request.getDescription()));
            	cmd.setParam("name", request.getName());
            	cmd.setParam("osTypeId", osTypeId);
            	cmd.setParam("volumeId", volumeId);
            }
            CloudStackTemplate resp = cloudStackCall(cmd, true, "createtemplateresponse", "template", CloudStackTemplate.class);
            if (resp == null || resp.getId() == null) {
            	throw new EC2ServiceException(ServerError.InternalError, "An upexpected error occurred.");
            }
 	        
 	        // [C] If we stopped the virtual machine now we need to restart it
 	        if (needsRestart) {
   			    if (!startVirtualMachine( request.getInstanceId() )) 
		            throw new EC2ServiceException(ServerError.InternalError, 
		            		"CreateImage - restarting instance " + request.getInstanceId() + " failed");
 	        }
 	        return response;
    	    
	    } catch( Exception e ) {
		    logger.error( "EC2 CreateImage - ", e);
		    throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
	    }
    }
    
    /**
     * EC2CreateImageResponse has the same content that the class EC2RegisterImageResponse
     * would have.
     */
    public EC2CreateImageResponse handleRequest(EC2RegisterImage request) 
    {
    	try {
    		if (null == request.getFormat()   || null == request.getName() || null == request.getOsTypeName() ||
    		    null == request.getLocation() || null == request.getZoneName())
    			throw new EC2ServiceException(ServerError.InternalError, "Missing parameter - location/architecture/name");
    		
    		CloudStackCommand cmd = new CloudStackCommand("registerTemplate");
    		if (cmd != null) {
    			cmd.setParam("displayText", (request.getDescription() == null ? request.getName() : request.getDescription()));
    			cmd.setParam("format", request.getFormat());
    			cmd.setParam("name", request.getName());
    			cmd.setParam("osTypeId", toOSTypeId( request.getOsTypeName()));
    			cmd.setParam("url", request.getLocation());
    			cmd.setParam("zoneId", request.getZoneName());
    		}
    		
    		CloudStackTemplate resp = cloudStackCall(cmd, true, "registertemplateresponse", "template", CloudStackTemplate.class);
    		if (resp != null && resp.getId() != null) {
    			EC2CreateImageResponse image = new EC2CreateImageResponse();
    			image.setId(resp.getId().toString());
    			return image;
    		}
    		return null;
        } catch( Exception e ) {
	        logger.error( "EC2 RegisterImage - ", e);
	        throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
        }
    }
    
    /**
     * Our implementation is different from Amazon in that we do delete the template
     * when we deregister it.   The cloud API has not deregister call.
     */
    public boolean deregisterImage( EC2Image image ) 
    {
    	try {
    		CloudStackCommand cmd = new CloudStackCommand("deleteTemplate");
    		if (cmd != null) {
    			cmd.setParam("id", image.getId());
    		}
    		CloudStackInfoResponse resp = cloudStackCall(cmd, true, "deletetemplateresponse", null, CloudStackInfoResponse.class);
    		return resp.getSuccess();
    	} catch( Exception e ) {
    		logger.error( "EC2 DeregisterImage - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    public EC2DescribeInstancesResponse handleRequest( EC2DescribeInstances request ) 
    {
    	try {
   	        return listVirtualMachines( request.getInstancesSet(), request.getFilterSet()); 
       	} 
    	catch( EC2ServiceException error ) 
    	{
     		logger.error( "EC2 DescribeInstances - ", error);
    		throw error;
    		
    	} 
    	catch( Exception e ) 
    	{
    		logger.error( "EC2 DescribeInstances - " ,e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }


    public EC2DescribeAvailabilityZonesResponse handleRequest(EC2DescribeAvailabilityZones request) {	
    	try {
    		return listZones( request.getZoneSet());
    		
       	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 DescribeAvailabilityZones - ", error);
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DescribeAvailabilityZones - " ,e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }
    
    public EC2DescribeVolumesResponse handleRequest( EC2DescribeVolumes request ) {
    	EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();
    	EC2VolumeFilterSet vfs = request.getFilterSet();

    	try {   
    		String[] volumeIds = request.getVolumeSet();
   	        if ( 0 == volumeIds.length ) 
   	        {
    	         volumes = listVolumes( null, null, volumes );
   	        }
   	        else
   	        {    for( int i=0; i < volumeIds.length; i++ ) 
                    volumes = listVolumes( volumeIds[i], null, volumes );
   	        }
   	        
   	        if ( null == vfs )
   	   	         return volumes;
   	        else return vfs.evaluate( volumes );     
       	}  catch( Exception e ) {
    		logger.error( "EC2 DescribeVolumes - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }
    
    public EC2Volume attachVolume( EC2Volume request ) {
    	try {   
    		request.setDeviceId( mapDeviceToCloudDeviceId( request.getDevice()));

    		CloudStackCommand command = new CloudStackCommand("attachVolume");
    		command.setParam("deviceId", String.valueOf(request.getDeviceId()));
    		command.setParam("id", request.getId());
    		command.setParam("virtualMachineId", request.getInstanceId());
    		
    		CloudStackVolume vol = cloudStackCall(command, true, "attachvolumeresponse", "volume", CloudStackVolume.class);
    		if(vol != null) {
    			request.setState("attached");
    			return request;
    		}
    		throw new EC2ServiceException( ServerError.InternalError, "An unexpected error occurred." );
       	} catch( Exception e ) {
    		logger.error( "EC2 AttachVolume 2 - ", e);
    		throw new EC2ServiceException( ServerError.InternalError, e.getMessage() != null ? e.getMessage() : e.toString());
    	}   	    
    }
   
    public EC2Volume detachVolume(EC2Volume request) {
    	try {
    		CloudStackCommand command = new CloudStackCommand("detachVolume");
    		command.setParam("id", request.getId());
    		
   	        // -> do first cause afterwards the volume has not volume associated with it
 	        // -> if instanceId is not given on the request then we need to look it up for the response
 	        if (null == request.getInstanceId()) {
 	   		    EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();
  			    volumes = listVolumes( request.getId(), null, volumes );
 	            EC2Volume[] volSet = volumes.getVolumeSet();
 	            if (0 < volSet.length) 
 	            	request.setInstanceId( volSet[0].getInstanceId());
 	        }
 	        
    		CloudStackVolume vol = cloudStackCall(command, true, "detachvolumeresponse", "volume", CloudStackVolume.class);
    		if(vol != null) {
    			request.setState("detached");
    			return request;
    		}
    		
    		throw new EC2ServiceException( ServerError.InternalError, "An unexpected error occurred." );
       	} catch( Exception e ) {
    		logger.error( "EC2 DetachVolume - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}   	    
    }
    
    public EC2Volume handleRequest( EC2CreateVolume request ) {
    	CloudStackCommand command = new CloudStackCommand("createVolume");
    	
    	boolean foundDisk = false;
    	try {
    		// -> put either snapshotid or diskofferingid on the request
    		String snapshotId = request.getSnapshotId();
    		Integer size = request.getSize();

   	        if (null == snapshotId)
   	        {
   	   	        DiskOfferings findDisk = listDiskOfferings(); 
   	   		    DiskOffer[] offerSet   = findDisk.getOfferSet();
   	   		    for( int i=0; i < offerSet.length; i++ ) 
   	   		    {
   	   		         if (offerSet[i].getIsCustomized()) {
   	   		        	 command.setParam("diskofferingid", offerSet[i].getId());
   	   		    	     foundDisk = true;
   	   		    	     break;
   	   		         } 	   		    		  
   	   		    }
   	 	        if (!foundDisk) throw new EC2ServiceException(ServerError.InternalError, "No Customize Disk Offering Found");
   	        }
   	      
   	        // -> no volume name is given in the Amazon request but is required in the cloud API
   	        command.setParam("name", UUID.randomUUID().toString());
   	        
   	        if (null != size)
   	        	command.setParam("size", size.toString());
   	        	
   	        if (null != snapshotId) 
   	        	command.setParam("snapshotId", snapshotId);
   	        
   	        command.setParam("zoneId", toZoneId( request.getZoneName()));
   	        
   	        CloudStackVolume vol = cloudStackCall(command, true, "createvolumeresponse", "volume", CloudStackVolume.class);
   	        EC2Volume ec2Vol = new EC2Volume();
   	        ec2Vol.setId(String.valueOf(vol.getId()));
   	        ec2Vol.setZoneName(vol.getZoneName());
   	        ec2Vol.setSize(String.valueOf(vol.getSize()));
   	        ec2Vol.setCreated(vol.getCreated());
   	        
   	        ec2Vol.setState( "available" );
   	        return ec2Vol;
       	} catch( Exception e ) {
    		logger.error( "EC2 CreateVolume - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}   	    
    }
    
    /**
     * The cloudstack documentation shows this to be an "(A)" (i.e., asynch) operation but it is not.
     * @param request
     * @return
     */
    public EC2Volume deleteVolume( EC2Volume request ) {
    	try {   
    		CloudStackCommand command = new CloudStackCommand("deleteVolume");
    		command.setParam("id", request.getId());
    		
    		CloudStackInfoResponse response = cloudStackCall(command, true, "deletevolumeresponse", null, CloudStackInfoResponse.class);
    		if(response.getSuccess() != null && response.getSuccess().booleanValue()) {
    			request.setState("deleted");
    			return request;
    		}
    		
 	        throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
       	} catch( Exception e ) {
    		logger.error( "EC2 DeleteVolume 2 - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}   	    
    }
    
    /**
     * Note that more than one VM can be requested rebooted at once. 
     * The Amazon API does not wait around for the result of the operation.
     */
    public boolean handleRequest( EC2RebootInstances request ) 
    {
    	EC2Instance[] vms = null;

    	// -> reboot is not allowed on destroyed (i.e., terminated) instances
    	try {   
    		String[] instanceSet = request.getInstancesSet();
    	    EC2DescribeInstancesResponse previousState = listVirtualMachines( instanceSet, null );
     	    vms = previousState.getInstanceSet();
    	    
     	    // -> send reboot requests for each found VM 
     		for( int i=0; i < vms.length; i++ ) {
     		   if (vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;

     		   CloudStackCommand command = new CloudStackCommand("rebootVirtualMachine");
     		   command.setParam("id", vms[i].getId());
     		   
     		   CloudStackInfoResponse commandResponse = cloudStackCall(command, false, "rebootvirtualmachineresponse", null, CloudStackInfoResponse.class);
     		   if(logger.isDebugEnabled())
     			   logger.debug("Rebooting VM " + vms[i].getId() + " job " + commandResponse.getJobId());
     		}
     		
     		// -> if some specified VMs where not found we have to tell the caller
     		if (instanceSet.length != vms.length) 
        		throw new EC2ServiceException(ClientError.InvalidAMIID_NotFound, "One or more instanceIds do not exist, other instances rebooted.");
    			
     		return true;
       	} catch( Exception e ) {
    		logger.error( "EC2 RebootInstances - ", e );
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }

    
    /**
     * The Amazon API allows one or multiple VMs to be started with a single request.
     * To do this efficiently we first make all the asynch deployVirtualMachine requests
     * required before we start waiting for any to complete.   We save all the jobId values
     * returned from each request in an array.   Once all the deployVirtualMachine requests 
     * have been made then we enter a polling state waiting for all the asynch requests to
     * complete.
     */
    public EC2RunInstancesResponse handleRequest(EC2RunInstances request) {
    	EC2RunInstancesResponse instances = new EC2RunInstancesResponse();
    	int createInstances    = 0;
    	int canCreateInstances = -1;
    	int countCreated       = 0;
    	
    	// [A] Can the user create the minimum required number of instances?
    	try {
    	    canCreateInstances = calculateAllowedInstances();
 	        if (-1 == canCreateInstances) canCreateInstances = request.getMaxCount();
 	        
     	    if (canCreateInstances < request.getMinCount()) {
    		    logger.info( "EC2 RunInstances - min count too big (" + request.getMinCount() + "), " + canCreateInstances + " left to allocate");
    		    throw new EC2ServiceException(ClientError.InstanceLimitExceeded ,"Only " + canCreateInstances + " instance(s) left to allocate");	
     	    }

     	    if ( canCreateInstances < request.getMaxCount()) 
     		     createInstances = canCreateInstances;
     	    else createInstances = request.getMaxCount();

	        EC2Instance[] vms    = new EC2Instance[ createInstances ];

     	    // [B] Create n separate VM instances 
	        OfferingBundle offer = instanceTypeToOfferBundle( request.getInstanceType());
	        
	        CloudStackCommand command = new CloudStackCommand("deployVirtualMachine");
       	    if (null != request.getGroupId()) 
       	    	command.setParam(ApiConstants.SECURITY_GROUP_NAMES, request.getGroupId());
	        
       	    String zoneId = toZoneId(request.getZoneName());
       	    
       	    if (cloudStackVersion >= CLOUD_STACK_VERSION_2_2) {
           	    if (null != request.getKeyName())
           	    	command.setParam("keypair", request.getKeyName());
           	    
           	    String dcNetworkType = getDCNetworkType(zoneId);
           	    if (dcNetworkType.equals("Advanced")) {
           	    	command.setParam("networkIds", getNetworkId(zoneId));
           	    }
       	    }

       	    command.setParam("serviceOfferingId", offer.getServiceOfferingId());
       	    command.setParam("size", String.valueOf(request.getSize()));
       	    command.setParam("templateId", request.getTemplateId());
       	    
       	    if (null != request.getUserData()) 
       	    	command.setParam( "userData", request.getUserData());
       	    
       	    command.setParam("zoneId", zoneId);
       	    
     		for( int i=0; i < createInstances; i++ ) {
     			CloudStackUserVm vm = cloudStackCall(command, true, "deployvirtualmachineresponse", "virtualmachine", CloudStackUserVm.class);
     			vms[i] = new EC2Instance();
     			vms[i].setId(String.valueOf(vm.getId()));
     			vms[i].setName(vm.getName());
     			vms[i].setZoneName(vm.getZoneName());
     			vms[i].setTemplateId(String.valueOf(vm.getTemplateId()));
     			if(vm.getSecurityGroupList() != null && vm.getSecurityGroupList().size() > 0) {
     				// TODO, we have a list of security groups, just return the first one?
     				CloudStackSecurityGroup securityGroup = vm.getSecurityGroupList().get(0);
     				vms[i].setGroup(securityGroup.getName());
     			}
     			vms[i].setState(vm.getState());
     			vms[i].setCreated(vm.getCreated());
     			vms[i].setIpAddress(vm.getIpAddress());
     			vms[i].setAccountName(vm.getAccountName());
     			vms[i].setDomainId(String.valueOf(vm.getDomainId()));
     			vms[i].setHypervisor(vm.getHypervisor());
    			vms[i].setServiceOffering( serviceOfferingIdToInstanceType( offer.getServiceOfferingId()));
    			instances.addInstance( vms[i] );
    			countCreated++;
     	    }    		
	   		
         	if (0 == countCreated) {
         		// TODO, we actually need to destroy left-over VMs when the exception is thrown
         		throw new EC2ServiceException(ServerError.InsufficientInstanceCapacity, "Insufficient Instance Capacity" );
         	}
            
         	return instances;
     	} catch( Exception e ) {
    		logger.error( "EC2 RunInstances - deploy 2 ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
     	}
    }
    
    /**
     * Note that more than one VM can be requested started at once. 
     * The Amazon API returns the previous state and the modified state as the
     * result of this API.
     */
    public EC2StartInstancesResponse handleRequest(EC2StartInstances request) {
    	EC2StartInstancesResponse instances = new EC2StartInstancesResponse();
    	EC2Instance[] vms = null;
    	
    	// -> first determine the current state of each VM (becomes it previous state)
    	try {
    	    EC2DescribeInstancesResponse previousState = listVirtualMachines( request.getInstancesSet(), null );
     	    vms = previousState.getInstanceSet();

    	    // -> send start requests for each item 
     		for( int i=0; i < vms.length; i++ ) {
     		   vms[i].setPreviousState( vms[i].getState());
     		   
     		   // -> if its already running then we are done
     		   if (vms[i].getState().equalsIgnoreCase( "Running" ) || vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;

     		   CloudStackCommand command = new CloudStackCommand("startVirtualMachine");
     		   command.setParam("id", vms[i].getId());
     		   CloudStackInfoResponse commandResponse = cloudStackCall(command, false, "startvirtualmachineresponse", null, CloudStackInfoResponse.class);
     		   if(logger.isDebugEnabled())
     			   logger.debug("Starting VM " + vms[i].getId() + " job " + commandResponse.getJobId());
     		   
     		  CloudStackUserVm vmForNewState = getCloudStackUserVmFromKeyValue("id", vms[i].getId());
     		  if(vmForNewState != null) {
     			  if(!vms[i].getState().equals(vmForNewState.getState())) { 
     				  vms[i].setState(vmForNewState.getState());  
     			  } else {
     				  if(commandResponse.getJobId() != null) {
     					  // TODO : we are querying too soon, we already have a job pending, assume it is taking action
     					 vms[i].setState("Starting");
     				  }
     			  }
     		  }
     		}
     		
        	for( int k=0; k < vms.length; k++ )	instances.addInstance( vms[k] );
        	return instances;
    	} catch( Exception e ) {
    		logger.error( "EC2 StartInstances - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }

    /**
     * Note that more than one VM can be requested stopped at once. 
     * The Amazon API returns the previous state and the modified state as the
     * result of this API.
     */
    public EC2StopInstancesResponse handleRequest(EC2StopInstances request) {
    	EC2StopInstancesResponse instances = new EC2StopInstancesResponse();
    	EC2Instance[] vms = null;
    	
    	// -> first determine the current state of each VM (becomes it previous state)
    	try 
    	{   
    		String[] instanceSet = request.getInstancesSet();
    		
    	    EC2DescribeInstancesResponse previousState = listVirtualMachines( instanceSet, null );
     	    vms = previousState.getInstanceSet();
    
    	    // -> send stop requests for each item 
     		for( int i=0; i < vms.length; i++ ) {
     		   vms[i].setPreviousState( vms[i].getState());
     		   
     		   CloudStackCommand command; 
     		   CloudStackInfoResponse commandResponse;
     		   if ( request.getDestroyInstances()) {
     			    if (vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;
     			    
     			    command = new CloudStackCommand("destroyVirtualMachine");
     			    command.setParam("id", vms[i].getId());
          		   	commandResponse = cloudStackCall(command, false, "destroyvirtualmachineresponse", null, CloudStackInfoResponse.class);
          		   	if(logger.isDebugEnabled())
          		   		logger.debug("Destroying VM " + vms[i].getId() + " job " + commandResponse.getJobId());
     		   } else {
     			    if (vms[i].getState().equalsIgnoreCase( "Stopped" ) || vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;
     			    
     			    command = new CloudStackCommand("stopVirtualMachine");
          		   	command.setParam("id", vms[i].getId());
     			    
          		   	commandResponse = cloudStackCall(command, false, "stopvirtualmachineresponse", null, CloudStackInfoResponse.class);
          		   	if(logger.isDebugEnabled())
          		   		logger.debug("Stopping VM " + vms[i].getId() + " job " + commandResponse.getJobId());
     		   }
     		   
      		   CloudStackUserVm vmForNewState = getCloudStackUserVmFromKeyValue("id", vms[i].getId());
      		   if(vmForNewState != null) {
     			  if(!vms[i].getState().equals(vmForNewState.getState())) { 
     				  vms[i].setState(vmForNewState.getState());  
     			  } else {
     				  if(commandResponse.getJobId() != null) {
     					  // TODO : we are querying too soon, we already have a job pending, assume it is taking action
     					 vms[i].setState("Stoping");
     				  }
     			  }
      		   } else {
      			   logger.error("VM " + vms[i].getId() + " no longer exists");
      			   vms[i].setState("Error");
      		   }
     		}
     		
        	for( int k=0; k < vms.length; k++ )	instances.addInstance( vms[k] );
        	return instances;
    	} catch( Exception e ) {
    		logger.error( "EC2 StopInstances - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }
    
    

    /**
     * RunInstances includes a min and max count of requested instances to create.  
     * We have to be able to create the min number for the user or none at all.  So
     * here we determine what the user has left to create. 
     * 
     * @return -1 means no limit exists, other positive numbers give max number left that
     *         the user can create.
     */
    private int calculateAllowedInstances() throws Exception 
    {    
    	if ( cloudStackVersion >= CLOUD_STACK_VERSION_2_1 ) {
		     int maxAllowed = -1;
		
	 		 // -> get the user limits on instances
		     CloudStackCommand command = new CloudStackCommand("listResourceLimits");
		     command.setParam("resourceType", "0");
		     
		     List<CloudStackResourceLimit> limits = cloudStackListCall(command, "listresourcelimitsresponse", "resourcelimit",
		     	new TypeToken<List<CloudStackResourceLimit>>() {}.getType());  
		     if(limits != null && limits.size() > 0) {
		    	 maxAllowed = (int)limits.get(0).getMax().longValue();
	    	      if (maxAllowed == -1) 
	    	    	  return -1;   // no limit
	    	      
	    	      EC2DescribeInstancesResponse existingVMS = listVirtualMachines( null, null );
	    	      EC2Instance[] vmsList = existingVMS.getInstanceSet();
	    	      return (maxAllowed - vmsList.length);
		     } else {
		    	 return 0;
		     }
    	}
    	else return -1;
 	}
   
    /**
     * Performs the cloud API listVirtualMachines one or more times.
     * 
     * @param virtualMachineIds - an array of instances we are interested in getting information on
     * @param ifs - filter out unwanted instances
     */
    private EC2DescribeInstancesResponse listVirtualMachines( String[] virtualMachineIds, EC2InstanceFilterSet ifs ) throws Exception 
    {
	    EC2DescribeInstancesResponse instances = new EC2DescribeInstancesResponse();
 	 
        if (null == virtualMachineIds || 0 == virtualMachineIds.length) 
        {
	        instances = lookupInstances( null, instances );
	    } else {
	    	for( int i=0; i <  virtualMachineIds.length; i++ ) 
		    {
		       instances = lookupInstances( virtualMachineIds[i], instances );
		    }
	    }
	    
	    if ( null == ifs )
  	   	     return instances;
  	    else return ifs.evaluate( instances );     
	}
    
    /**  
     * Get one or more templates depending on the volumeId parameter.
     * 
     * @param volumeId   - if interested in one specific volume, null if want to list all volumes
     * @param instanceId - if interested in volumes for a specific instance, null if instance is not important
     */
    private EC2DescribeVolumesResponse listVolumes(String volumeId, String instanceId, 
    		EC2DescribeVolumesResponse volumes)throws Exception {

    	CloudStackCommand command = new CloudStackCommand("listVolumes");
    	if(null != volumeId)
    		command.setParam("id", volumeId);
    	if(null != instanceId)
    		command.setParam("virtualMachineId", instanceId);
    	
    	List<CloudStackVolume> vols = cloudStackListCall(command, "listvolumesresponse", "volume",
    		new TypeToken<List<CloudStackVolume>>() {}.getType());

    	if(vols != null && vols.size() > 0) {
    		for(CloudStackVolume vol : vols) {
				EC2Volume ec2Vol = new EC2Volume();
				ec2Vol.setId(String.valueOf(vol.getId()));
				if(vol.getAttached() != null)
					ec2Vol.setAttached(vol.getAttached());
				ec2Vol.setCreated(vol.getCreated());
				
				if(vol.getDeviceId() != null)
					ec2Vol.setDeviceId((int)vol.getDeviceId().longValue());
				ec2Vol.setHypervisor(vol.getHypervisor());
				
				if(vol.getSnapshotId() != null)
					ec2Vol.setSnapShotId(String.valueOf(vol.getSnapshotId()));
				ec2Vol.setState(mapToAmazonVolState(vol.getState()));
				ec2Vol.setSize(String.valueOf(vol.getSize()));
				ec2Vol.setType(vol.getVolumeType());
				
				if(vol.getVirtualMachineId() != null)
					ec2Vol.setInstanceId(String.valueOf(vol.getVirtualMachineId()));
				
				if(vol.getVirtualMachineState() != null)
					ec2Vol.setVMState(vol.getVirtualMachineState());
				ec2Vol.setZoneName(vol.getZoneName());
				
	 		    volumes.addVolume(ec2Vol);
    		}
    	}
    	
		return volumes;
	}

    /**
     * Translate the given zone name into the required zoneId.  Query for 
     * a list of all zones and match the zone name given.   Amazon uses zone
     * names while the Cloud API often requires the zoneId.
     * 
     * @param zoneName - (e.g., 'AH'), if null return the first zone in the available list
     * 
     * @return the zoneId that matches the given zone name
     */
 	private String toZoneId( String zoneName ) 
 	    throws Exception 
 	{
 		EC2DescribeAvailabilityZonesResponse zones = null;
 		String[] interestedZones = null;
 		
 		if ( null != zoneName) {
 		     interestedZones = new String[1];
             interestedZones[0] = zoneName;
 		}
 		zones = listZones( interestedZones );
	    
 		if (null == zones.getZoneIdAt( 0 )) 
 			throw new EC2ServiceException(ClientError.InvalidParameterValue, "Unknown zoneName value - " + zoneName);
 		return zones.getZoneIdAt( 0 );
 	}
 	
 	/**
     * Convert from the Amazon instanceType strings to the Cloud APIs diskOfferingId and
     * serviceOfferingId based on the loaded map.
     * 
 	 * @param instanceType - if null we return the M1Small instance type
 	 * 
 	 * @return an OfferingBundle
 	 * @throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException 
 	 */
 	private OfferingBundle instanceTypeToOfferBundle( String instanceType ) 
 	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
 	{
 		OfferingBundle found = null;
 	    OfferingDao ofDao = new OfferingDao();
 
        if (null == instanceType) instanceType = "m1.small";                      
        String cloudOffering = ofDao.getCloudOffering( instanceType );

        if ( null != cloudOffering )
        {
 		     found = new OfferingBundle();
 		     found.setServiceOfferingId( cloudOffering );
        }
 		else throw new EC2ServiceException( ClientError.Unsupported, "Unknown: " + instanceType );   
 			
 		return found;
 	}
 	
 	/**
 	 * Convert from the Cloud serviceOfferingId to the Amazon instanceType strings based
 	 * on the loaded map.
 	 * 
 	 * @param serviceOfferingId
 	 * @return A valid value for the Amazon defined instanceType
 	 * @throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException 
 	 */
 	private String serviceOfferingIdToInstanceType( String serviceOfferingId ) 
 	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
 	{	
 	    OfferingDao ofDao = new OfferingDao();
        String amazonOffering = ofDao.getAmazonOffering( serviceOfferingId.trim());

 		if ( null == amazonOffering ) {
 			 logger.warn( "No instanceType match for serverOfferingId: [" + serviceOfferingId + "]" );
 			 return "m1.small";
 		}
 		else return amazonOffering;
 	}
 	 	
 	/**
 	 * Match the value in the 'description' field of the listOsTypes response to get 
 	 * the osTypeId.
 	 * 
 	 * @param osTypeName
 	 * @return the Cloud.com API osTypeId 
 	 */
    private String toOSTypeId( String osTypeName ) throws Exception { 
    	try {
    		CloudStackCommand command = new CloudStackCommand("listOsTypes");
    		List<CloudStackOsType> osTypes = cloudStackListCall(command, "listostypesresponse", "ostype", 
    				new TypeToken<List<CloudStackOsType>>() {}.getType());
    		for (CloudStackOsType osType : osTypes) {
    			if (osType.getDescription().indexOf(osTypeName) != -1)
    				return osType.getId().toString();
    		}
    		return null;
    	} catch(Exception e) {
    		logger.error( "List OS Types - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    	
    }

    /**
     * More than one place we need to access the defined list of zones.  If given a specific
     * list of zones of interest, then only values from those zones are returned.
     * 
     * @param interestedZones - can be null, should be a subset of all zones
     * 
     * @return EC2DescribeAvailabilityZonesResponse
     */
    private EC2DescribeAvailabilityZonesResponse listZones( String[] interestedZones ) throws Exception 
    {    
    	EC2DescribeAvailabilityZonesResponse zones = new EC2DescribeAvailabilityZonesResponse();
    	
    	CloudStackCommand command = new CloudStackCommand("listZones");
    	command.setParam("available", "true");
    	List<CloudStackZone> cloudZones = cloudStackListCall(command, "listzonesresponse", "zone",
    		new TypeToken<List<CloudStackZone>>() {}.getType());
    	
    	if(cloudZones != null) {
    		for(CloudStackZone cloudZone : cloudZones) {
	 		    if ( null != interestedZones && 0 < interestedZones.length ) {
	 		     	 for( int j=0; j < interestedZones.length; j++ ) {
	 		    	     if (interestedZones[j].equalsIgnoreCase( cloudZone.getName())) {
	 		    			 zones.addZone(String.valueOf(cloudZone.getId()), cloudZone.getName());
	 		    			 break;
	 		    		 }
	 		    	 }
	 		    } else { 
	 		    	zones.addZone(String.valueOf(cloudZone.getId()), cloudZone.getName());
	 		    }
    		}
    	}
		return zones;
    }
    
  
    /**
     * Get information on one or more virtual machines depending on the instanceId parameter.
     * 
     * @param instanceId - if null then return information on all existing instances, otherwise
     *                     just return information on the matching instance.
     * @param instances  - a container object to fill with one or more EC2Instance objects
     * 
     * @return the same object passed in as the "instances" parameter modified with one or more
     *         EC2Instance objects loaded.
     */
    private EC2DescribeInstancesResponse lookupInstances( String instanceId, EC2DescribeInstancesResponse instances ) 
	    throws Exception {

    	List<CloudStackUserVm> vms = getCloudStackUserVmsFromKeyValue("id", instanceId);
    	
    	for(CloudStackUserVm cloudVm : vms) {
			EC2Instance ec2Vm = new EC2Instance();
			
			ec2Vm.setId(String.valueOf(cloudVm.getId()));
			ec2Vm.setName(cloudVm.getName());
			ec2Vm.setZoneName(cloudVm.getZoneName());
			ec2Vm.setTemplateId(String.valueOf(cloudVm.getTemplateId()));
			ec2Vm.setGroup(cloudVm.getGroup());
			ec2Vm.setState(cloudVm.getState());
			ec2Vm.setCreated(cloudVm.getCreated());
			ec2Vm.setIpAddress(cloudVm.getIpAddress());
			ec2Vm.setAccountName(cloudVm.getAccountName());
			ec2Vm.setDomainId(String.valueOf(cloudVm.getDomainId()));
			ec2Vm.setHypervisor(cloudVm.getHypervisor());
			ec2Vm.setRootDeviceType(cloudVm.getRootDeviceType());
			ec2Vm.setRootDeviceId((int)cloudVm.getRootDeviceId().longValue());
			ec2Vm.setServiceOffering(serviceOfferingIdToInstanceType(String.valueOf(cloudVm.getServiceOfferingId())));
			
			List<CloudStackNic> nics = cloudVm.getNics();
			for(CloudStackNic nic : nics) {
				if(nic.getIsDefault()) {
					ec2Vm.setPrivateIpAddress(nic.getIpaddress());
					break;
				}
			}
    		instances.addInstance(ec2Vm);
    	}
    	return instances;
    }
    

    /**  
     * Get one or more templates depending on the templateId parameter.
     * 
     * @param templateId - if null then return information on all existing templates, otherwise
     *                     just return information on the matching template.
     * @param images     - a container object to fill with one or more EC2Image objects
     * 
     * @return the same object passed in as the "images" parameter modified with one or more
     *         EC2Image objects loaded.
     */
    private EC2DescribeImagesResponse listTemplates( String templateId, EC2DescribeImagesResponse images ) throws EC2ServiceException {
    	try {
	    	CloudStackCommand command = new CloudStackCommand("listTemplates");
	    	if (command != null) {
	    		if (templateId != null) command.setParam("id", templateId);
	    		command.setParam("templateFilter", "executable");
	    	}
	    	List<CloudStackTemplate> resp = cloudStackListCall(command, "listtemplatesresponse", "template", 
	    			new TypeToken<List<CloudStackTemplate>>() {}.getType());
	    	for (CloudStackTemplate temp : resp) {
	    		EC2Image ec2Image = new EC2Image();
	    		ec2Image.setId(temp.getId().toString());
	    		ec2Image.setAccountName(temp.getAccount());
	    		ec2Image.setName(temp.getName());
	    		ec2Image.setDescription(temp.getDisplayText());
	    		ec2Image.setOsTypeId(temp.getOsTypeId().toString());
	    		ec2Image.setIsPublic(temp.isPublic());
	    		ec2Image.setIsReady(temp.isReady());
	    		ec2Image.setDomainId(temp.getDomainId().toString());
	    		images.addImage(ec2Image);
	    	}
	    	return images;
    	} catch(Exception e) {
			logger.error( "List Templates - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }

    /**
     * Return information on all defined disk offerings.
     */
    private DiskOfferings listDiskOfferings() throws EC2ServiceException {
    	try {
    		CloudStackCommand command = new CloudStackCommand("listDiskOfferings");
    		List<CloudStackDiskOfferings> disks = cloudStackListCall(command, "listdiskofferingsresponse", "diskoffering", 
    				new TypeToken<List<CloudStackDiskOfferings>>() {}.getType());
    		
    		DiskOfferings offerings = new DiskOfferings();
    		for (CloudStackDiskOfferings disk : disks) {
    			DiskOffer df = new DiskOffer();
    			df.setId(disk.getId().toString());
    			df.setCreated(disk.getCreated());
    			df.setIsCustomized(disk.isCustomized());
    			df.setName(disk.getName());
    			df.setSize(disk.getDiskSize().toString());
    			offerings.addOffer(df);
    		}
    		return offerings;
    	} catch(Exception e) {
			logger.error( "Disk Offerings - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }


    public EC2DescribeSecurityGroupsResponse listSecurityGroups( String[] interestedGroups ) 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException, ParseException 
    {
    	try {
    		EC2DescribeSecurityGroupsResponse groupSet = new EC2DescribeSecurityGroupsResponse();
    		CloudStackCommand command = new CloudStackCommand("listSecurityGroups");
    		List<CloudStackSecurityGroup> groups = cloudStackListCall(command, "listsecuritygroupsresponse", "securitygroup", 
    				new TypeToken<List<CloudStackSecurityGroup>>() {}.getType());
    		
    		for (CloudStackSecurityGroup group : groups) {
    			boolean matched = false;
    			if (interestedGroups.length > 0) {
	    			for (String groupName :interestedGroups) {
	    				if (groupName.equalsIgnoreCase(group.getName())) {
	    					matched = true;
	    					break;
	    				}
	    			}
    			} else {
    				matched = true;
    			}
    			if (!matched) continue;
    			EC2SecurityGroup ec2Group = new EC2SecurityGroup();
    			// not sure if we should set both account and account name to accountname
    			ec2Group.setAccount(group.getAccountName());
    			ec2Group.setAccountName(group.getAccountName());
    			ec2Group.setName(group.getName());
    			ec2Group.setDescription(group.getDescription());
    			ec2Group.setDomainId(group.getDomainId().toString());
    			ec2Group.setId(group.getId().toString());
    			ec2Group.addIpPermission(toPermission(group));

    			groupSet.addGroup(ec2Group);
    		}
    		return groupSet;
    	} catch(Exception e) {
			logger.error( "List Security Groups - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    private EC2IpPermission toPermission( CloudStackSecurityGroup group ) {
    	List<CloudStackIngressRule> rules = group.getIngressRules();
    	
    	if (rules == null || rules.isEmpty()) return null;
    	
    	EC2IpPermission perm = new EC2IpPermission();
    	for (CloudStackIngressRule rule : rules) {
    		perm.setProtocol(rule.getProtocol());
    		perm.setFromPort(rule.getStartPort());
    		perm.setToPort(rule.getEndPort());
    		perm.setRuleId(rule.getRuleId().toString());
    		perm.setIcmpCode(rule.getIcmpCode().toString());
    		perm.setIcmpType(rule.getIcmpType().toString());
    		perm.setCIDR(rule.getCidr());
    		perm.addIpRange(rule.getCidr());
    		
    		if (rule.getAccountName() != null && rule.getSecurityGroupName() != null) {
	    		EC2SecurityGroup newGroup = new EC2SecurityGroup();
	    		newGroup.setAccount(rule.getAccountName());
	    		newGroup.setName(rule.getSecurityGroupName());
	    		perm.addUser(newGroup);
    		}
    	}
    	return perm;
    }
    
    /**
     * 
     * @return List of accounts
     * 
     *	This isn't used anywhere, but it used to be???
     */
    public Account[] getAccounts() {
    	return getAccounts(UserContext.current().getAccessKey(), UserContext.current().getSecretKey());
    }
    public Account[] getAccounts(String accessKey, String secretKey) {
    	try {
	    	CloudStackCommand command = new CloudStackCommand("listAccounts");
	    	List<Account> acctList = new ArrayList<Account>();
    		List<CloudStackAccount> accts = _client.listCall(command, accessKey, secretKey, "listaccountsresponse", "account", 
    				new TypeToken<List<CloudStackAccount>>() {}.getType());
    		for (CloudStackAccount acct : accts) {
    			Account newAcct = new Account();
    			newAcct.setAccountName(acct.getName());
    			newAcct.setDomainId(acct.getDomainId().toString());
    			newAcct.setDomainName(acct.getDomain());
    			newAcct.setId(acct.getId().toString());
    			acctList.add(newAcct);
    		}
    		return acctList.toArray(new Account[0]);
    			
    	} catch (Exception e) {
			logger.error( "List Accounts - ", e);
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    	}
    }
    
    /**
     * Check for three network types in succession until one is found.
     * Creates a new one if no network is found.
     * 
     * @return A network id suitable for use.
     * @throws Exception 
     */
    private String getNetworkId(String zoneId) throws Exception {
    	String networkId = null;
    	
    	if (networkId==null) networkId = getNetworkId("direct", false);
    	if (networkId==null) networkId = getNetworkId("direct", true);
    	if (networkId==null) networkId = getNetworkId("virtual", false);
    	if (networkId==null) networkId = createNetwork(zoneId);
    	
    	return networkId;
    }
    
    private String getNetworkId(String networkType, boolean shared) throws Exception {
    	CloudStackCommand command = new CloudStackCommand("listNetworks");
    	if (command != null) {
    		command.setParam("isdefault", "true");
    		if (shared) command.setParam("isshared", "true");
    		command.setParam("type", networkType);
    	}
    	List<CloudStackNetwork> networks = cloudStackListCall(command, "listnetworksresponse", "network", 
    			new TypeToken<List<CloudStackNetwork>>() {}.getType());
    	if (!networks.isEmpty()) {
	    	// Return the first network id found. TODO: Is this right?  
	    	return networks.get(0).getId().toString();
    	}
    	return null;
    }
    
    private String createNetwork(String zoneId) throws Exception {
    	CloudStackCommand command = new CloudStackCommand("createNetwork");
    	Tuple<String, String> networkOffering = getNetworkOffering();
    	if (command != null) {
//			Leaving these comments here because we have a bug about this stuff...
//    		command.setParam("account", account.getAccountName());
    		command.setParam("displaytext", networkOffering.getSecond());
//			command.setParam("domainid", account.getDomainId());
    		command.setParam("name", "EC2 created network");
    		command.setParam("networkofferingid", networkOffering.getFirst());
    		command.setParam("zoneid", zoneId);
    	}
    	CloudStackInfoResponse resp = cloudStackCall(command, true, "createnetworkresponse", null, CloudStackInfoResponse.class);
    	if (resp.getJobId() == null || resp.getId() == null) {
    		throw new Exception("Invalid CloudStack API response");
    	}
    	return resp.getId().toString();
    }
    
    /*
     * returns tuple with id and displaytext of first network offering provided
     */
    private Tuple<String, String> getNetworkOffering() throws Exception {
    	CloudStackCommand command = new CloudStackCommand("listNetworkOfferings");
    	if (command != null) {
    		command.setParam("isdefault", "true");
    		command.setParam("traffictype", "guest");
    	}
    	List<CloudStackNetworkOffering> offerings = cloudStackListCall(command, "listnetworkofferingsresponse", "networkoffering",
    			new TypeToken<List<CloudStackNetworkOffering>>() {}.getType());
    	if (!offerings.isEmpty()) {
    		Tuple<String, String> offering = new Tuple<String, String>("", "");
    		offering.setFirst(offerings.get(0).getId().toString());
    		offering.setFirst(offerings.get(0).getDisplayText());
    		return offering;
    	}
    	return null;
    }
    
    private String getDCNetworkType(String zoneId) throws Exception {
    	CloudStackCommand command = new CloudStackCommand("listZones");
    	if (command != null) {
    		command.setParam("id", zoneId);
    	}
    	List<CloudStackZone> zones = cloudStackListCall(command, "listzonesresponse", "zone",
    			new TypeToken<List<CloudStackZone>>() {}.getType());
    	
    	if (!zones.isEmpty()) {
    		return zones.get(0).getNetworkType(); 
    	}
    	return null;
    }
       
    /**
     * Windows has its own device strings.
     * 
     * @param hypervisor
     * @param deviceId
     * @return
     */
    public String cloudDeviceIdToDevicePath( String hypervisor, int deviceId )
    {
        if ( null != hypervisor && hypervisor.toLowerCase().contains( "windows" ))	
        {
        	 switch( deviceId ) {
        	 case 1:  return "xvdb";
        	 case 2:  return "xvdc";
        	 case 3:  return "xvdd";
        	 case 4:  return "xvde";
        	 case 5:  return "xvdf";
        	 case 6:  return "xvdg";
        	 case 7:  return "xvdh";
        	 case 8:  return "xvdi";
        	 case 9:  return "xvdj";
        	 default: return new String( "" + deviceId );
        	 }
        }
        else
        {    // -> assume its unix
       	     switch( deviceId ) {
    	     case 1:  return "/dev/sdb";
    	     case 2:  return "/dev/sdc";
    	     case 3:  return "/dev/sdd";
    	     case 4:  return "/dev/sde";
    	     case 5:  return "/dev/sdf";
    	     case 6:  return "/dev/sdg";
    	     case 7:  return "/dev/sdh";
    	     case 8:  return "/dev/sdi";
    	     case 9:  return "/dev/sdj";
    	     default: return new String( "" + deviceId );
    	     }
        }
    }
    
    
    /**
     * Translate the device name string into a Cloud Stack deviceId.   
     * deviceId 3 is reserved for CDROM and 0 for the ROOT disk
     * 
     * @param device string
     * @return deviceId value
     */
    private int mapDeviceToCloudDeviceId( String device ) 
    {	
	         if (device.equalsIgnoreCase( "/dev/sdb"  )) return 1;
    	else if (device.equalsIgnoreCase( "/dev/sdc"  )) return 2; 
    	else if (device.equalsIgnoreCase( "/dev/sde"  )) return 4; 
    	else if (device.equalsIgnoreCase( "/dev/sdf"  )) return 5; 
    	else if (device.equalsIgnoreCase( "/dev/sdg"  )) return 6; 
    	else if (device.equalsIgnoreCase( "/dev/sdh"  )) return 7; 
    	else if (device.equalsIgnoreCase( "/dev/sdi"  )) return 8; 
    	else if (device.equalsIgnoreCase( "/dev/sdj"  )) return 9; 
	         
    	else if (device.equalsIgnoreCase( "/dev/xvdb" )) return 1;  
    	else if (device.equalsIgnoreCase( "/dev/xvdc" )) return 2;  
    	else if (device.equalsIgnoreCase( "/dev/xvde" )) return 4;  
    	else if (device.equalsIgnoreCase( "/dev/xvdf" )) return 5;  
    	else if (device.equalsIgnoreCase( "/dev/xvdg" )) return 6;  
    	else if (device.equalsIgnoreCase( "/dev/xvdh" )) return 7;  
    	else if (device.equalsIgnoreCase( "/dev/xvdi" )) return 8;  
    	else if (device.equalsIgnoreCase( "/dev/xvdj" )) return 9;  
	         
    	else if (device.equalsIgnoreCase( "xvdb"      )) return 1;  
    	else if (device.equalsIgnoreCase( "xvdc"      )) return 2;  
    	else if (device.equalsIgnoreCase( "xvde"      )) return 4;  
    	else if (device.equalsIgnoreCase( "xvdf"      )) return 5;  
    	else if (device.equalsIgnoreCase( "xvdg"      )) return 6;  
    	else if (device.equalsIgnoreCase( "xvdh"      )) return 7;  
    	else if (device.equalsIgnoreCase( "xvdi"      )) return 8;  
    	else if (device.equalsIgnoreCase( "xvdj"      )) return 9;  

    	else throw new EC2ServiceException( ClientError.Unsupported, device + " is not supported" );
    }
    
    
    private String mapToAmazonVolState( String state ) 
    {
    	if (state.equalsIgnoreCase( "Allocated" ) ||
    		state.equalsIgnoreCase( "Creating"  ) ||
    		state.equalsIgnoreCase( "Ready"     )) return "available";
    	
    	if (state.equalsIgnoreCase( "Destroy"   )) return "deleting";
    	
    	return "error"; 
    }

    //
    // CloudStack interface methods
    //
	public <T> T cloudStackCall(CloudStackCommand cmd, boolean followToAsyncResult, String responseName, String responseObjName, Class<T> responseClz) throws Exception {
		return _client.call(cmd, UserContext.current().getAccessKey(), UserContext.current().getSecretKey(),
			followToAsyncResult, responseName, responseObjName, responseClz);
	}
	
	public <T> List<T> cloudStackListCall(CloudStackCommand cmd, String responseName, String responseObjName, Type collectionType) throws Exception {
		return _client.listCall(cmd, UserContext.current().getAccessKey(), UserContext.current().getSecretKey(),
			responseName, responseObjName, collectionType);
	}
	
	public JsonAccessor executeCommand(CloudStackCommand command) throws Exception {
		return _client.execute(command, UserContext.current().getAccessKey(), UserContext.current().getSecretKey());
	}
	
	
	/**
     * Wait until one specific VM has stopped
     */
    private boolean stopVirtualMachine( String instanceId) throws Exception {
    	try {
	    	CloudStackCommand cmd = new CloudStackCommand("stopVirtualMachine");
	    	if (cmd != null) {
	    		cmd.setParam("id", instanceId);
	    	}
	    	CloudStackInfoResponse resp =  cloudStackCall(cmd, true, "stopvirtualmachineresponse", null, CloudStackInfoResponse.class);
	    	if (logger.isDebugEnabled())
	    		logger.debug("Stopping VM " + instanceId );
	    	return resp.getSuccess();
    	} catch(Exception e) {
    		logger.error( "StopVirtualMachine - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }
  
    private boolean startVirtualMachine( String instanceId ) throws Exception {
    	try {
    		CloudStackCommand cmd = new CloudStackCommand("startVirtualMachine");
    		if (cmd != null) {
    			cmd.setParam("id", instanceId);
    		}
    		CloudStackInfoResponse resp = cloudStackCall(cmd, true, "startvirtualmachineresponse", null, CloudStackInfoResponse.class);
    		if (logger.isDebugEnabled())
    			logger.debug("Starting VM " + instanceId );
    		return resp.getSuccess();
    	} catch(Exception e) {
    		logger.error("StartVirtualMachine - ", e);
    		throw new EC2ServiceException(ServerError.InternalError, e.getMessage() != null ? e.getMessage() : "An unexpected error occurred.");
    	}
    }
}
