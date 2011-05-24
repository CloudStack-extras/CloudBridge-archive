/*
 * Copyright 2010 Cloud.com, Inc.
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.simple.JSONValue;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cloud.bridge.persist.dao.OfferingDao;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.service.exception.EC2ServiceException;
import com.cloud.bridge.service.exception.EC2ServiceException.ClientError;
import com.cloud.bridge.service.exception.EC2ServiceException.ServerError;
import com.cloud.bridge.service.exception.InternalErrorException;
import com.cloud.bridge.util.ConfigurationHelper;
import com.cloud.bridge.util.Tuple;


public class EC2Engine {
    protected final static Logger logger = Logger.getLogger(EC2Engine.class);
    private DocumentBuilderFactory dbf = null;
    private String managementServer    = null;
    private String cloudAPIPort        = null;
    
    // -> in milliseconds, time interval to wait before asynch check on a jobId's status
    private int pollInterval1 = 100;   // for: createTemplate
    private int pollInterval2 = 100;   // for: deployVirtualMachine
    private int pollInterval3 = 100;   // for: createVolume
    private int pollInterval4 = 1000;  // for: createSnapshot
    private int pollInterval5 = 100;   // for: deleteSnapshot, deleteTemplate, deleteVolume, attachVolume, detachVolume, disassociateIpAddress
    private int pollInterval6 = 100;   // for: startVirtualMachine, destroyVirtualMachine, stopVirtualMachine
    private int CLOUD_STACK_VERSION_2_0 = 200;
    private int CLOUD_STACK_VERSION_2_1 = 210;
    private int CLOUD_STACK_VERSION_2_2 = 220;
    private int cloudStackVersion;
   
    
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
  	        
   	        try {
   	            pollInterval1  = Integer.parseInt( EC2Prop.getProperty( "pollInterval1", "100"  ));
   	            pollInterval2  = Integer.parseInt( EC2Prop.getProperty( "pollInterval2", "100"  ));
   	            pollInterval3  = Integer.parseInt( EC2Prop.getProperty( "pollInterval3", "100"  ));
   	            pollInterval4  = Integer.parseInt( EC2Prop.getProperty( "pollInterval4", "1000" ));
   	            pollInterval5  = Integer.parseInt( EC2Prop.getProperty( "pollInterval5", "100"  ));
   	            pollInterval6  = Integer.parseInt( EC2Prop.getProperty( "pollInterval6", "100"  ));
   	        } catch( Exception e ) {
    			logger.warn("Invalid polling interval: " + e.toString() + " using default values");
   	        }
   	        
            cloudStackVersion = getCloudStackVersion(EC2Prop);
    	} 
       	else logger.error( "ec2-service.properties not found" );
	}
    
    private int getCloudStackVersion(Properties prop) 
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
    
    private int getCloudStackVersionBasedOnCapabilitiesCmd() {
    	StringBuilder query = new StringBuilder();
    	query.append(getServerURL());
    	query.append("command=listCapabilities");
    	Document response = null;
    	   	    	
    	try {
    	    response = resolveURL(query.toString(), "listCapabilities", false);
    	} catch (Exception e) {
    		// listCapabilities is introduced in 2.2 - if the command fails the CloudStack 
    		// is older. Default to 2.1 in that case.
    		return CLOUD_STACK_VERSION_2_1;
    	}
    	
    	String version = "unknown";
    	NodeList responseList = response.getElementsByTagName("cloudStackVersion");
    	if (responseList != null && responseList.getLength() > 0 && responseList.item(0).hasChildNodes())
			version = responseList.item(0).getFirstChild().getNodeValue();
    	
    	if (!version.equals("unknown")) {
    		if(version.equals("2.2.0")){
    			return CLOUD_STACK_VERSION_2_2;
    		}    	
    	}
    	
    	// Default to 2.2
    	return CLOUD_STACK_VERSION_2_2;
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
    public boolean validateAccount( String accessKey, String secretKey ) 
    {
    	try {
        	StringBuffer sigOver = new StringBuffer();
        	sigOver.append( "apikey=" ).append( safeURLencode( accessKey ));
        	sigOver.append( "&" ).append( "command=listAccounts" );
        	String signature = calculateRFC2104HMAC( sigOver.toString().toLowerCase(), secretKey );

          	StringBuffer apiCommand = new StringBuffer();
            apiCommand.append( getServerURL());
            apiCommand.append( "command=listAccounts" );
            apiCommand.append( "&apikey=" ).append( safeURLencode( accessKey ));
            apiCommand.append( "&signature=" ).append( safeURLencode( signature ));

            resolveURL( apiCommand.toString(), "listAccounts", true );
            return true;
            
       	} catch( EC2ServiceException error ) {
     		logger.error( "validateAccount - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "validateAccount - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }

    /**
     * Remember to pre-sort the parameters in alphabetical order.
     * 
     * @param request
     * @return was the security group created?
     */
    public boolean createSecurityGroup(EC2SecurityGroup request) 
    {
		if (null == request.getDescription() || null == request.getName()) 
			 throw new EC2ServiceException(ServerError.InternalError, "Both name & description are required");

    	try {
	        String query = new String( "command=createSecurityGroup" +
	        		                   "&description=" + safeURLencode( request.getDescription()) + 
	        		                   "&name="        + safeURLencode( request.getName()));
	        
            resolveURL(genAPIURL(query, genQuerySignature(query)), "createSecurityGroup", true );
      		return true;
     		
       	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 CreateSecurityGroup - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 CreateSecurityGroup - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    public boolean deleteSecurityGroup(EC2SecurityGroup request) 
    {
	if (null == request.getName()) throw new EC2ServiceException(ServerError.InternalError, "Name is a required parameter");

   	try {
	        String query = new String( "command=deleteSecurityGroup&name=" + safeURLencode( request.getName()));        
            resolveURL( genAPIURL( query, genQuerySignature(query)), "deleteSecurityGroup", true );
     		return true;
    		
      	} catch( EC2ServiceException error ) {
   		    logger.error( "EC2 DeleteSecurityGroup - " + error.toString());
   		    throw error;
   		
   	    } catch( Exception e ) {
   		    logger.error( "EC2 DeleteSecurityGroup - " + e.toString());
   		    throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
   	    }
    }
    
    public EC2DescribeSecurityGroupsResponse handleRequest( EC2DescribeSecurityGroups request ) 
    {
    	try 
    	{   EC2DescribeSecurityGroupsResponse response = listSecurityGroups( request.getGroupSet());
        	EC2GroupFilterSet gfs = request.getFilterSet();
    		
	        if ( null == gfs )
	   	         return response;
	        else return gfs.evaluate( response );     
       	} 
    	catch( EC2ServiceException error ) {
    		logger.error( "EC2 DescribeSecurityGroups - " + error.toString());
    		throw error;   		
    	} 
    	catch( Exception e ) {
    		logger.error( "EC2 DescribeSecurityGroups - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
   
    
    /**
     * The Cload Stack API only handles one item out of the EC2 request at a time.
     * Place authorizw and revoke into one function since it appears that they are practically identical.
     * Both authorizeSecurityGroupIngress and revokeSecurityGroupIngress are asynchonrous
     * 
     * @param request - ip permission parameters
     * @param command - { authorizeSecurityGroupIngress | revokeSecurityGroupIngress }
     */
    public boolean securityGroupRequest(EC2AuthorizeRevokeSecurityGroup request, String command ) 
    {
		if (null == request.getName()) throw new EC2ServiceException(ServerError.InternalError, "Name is a required parameter");
    	
		StringBuffer url    = new StringBuffer();   // -> used to derive URL for Cloud Stack
		StringBuffer sorted = new StringBuffer();   // -> used for signature generation
		StringBuffer params = new StringBuffer();   // -> used to construct common set of parameters
		
		EC2IpPermission[] items = request.getIpPermissionSet();
		
   	    try {
   	    	for( int i=0; i < items.length; i++  ) {
   	    		
   	    	   // -> cidrList is before command for the sorted version used for signature generation
  	    	   String cidrList = constructCIDRList( items[i].getIpRangeSet());
    	       url.append( "command=" + command );	
    	       if ( null != cidrList) {
     	    	    url.append( "&" + cidrList );   // &cidrList
    	    	    sorted.append( cidrList );      // cidrList before command
    	    	    sorted.append( "&command=" + command );	
    	       }
    	       else sorted.append( "command="  + command );	

    	       
   	    	   String protocol = items[i].getProtocol();
   	    	   if ( protocol.equalsIgnoreCase( "icmp" )) {
  	   	            params.append( "&icmpCode="         + items[i].getToPort());
   	   	            params.append( "&icmpType="         + items[i].getFromPort());
   	   	            params.append( "&protocol="         + safeURLencode( protocol ));
   	   	            params.append( "&securityGroupName=" + safeURLencode( request.getName()));
   	    	   }
   	    	   else {
  	   	            params.append( "&endPort="          + items[i].getToPort());
   	   	            params.append( "&protocol="         + safeURLencode( protocol ));
   	   	            params.append( "&securityGroupName=" + safeURLencode( request.getName()));
   	   	            params.append( "&startPort="        + items[i].getFromPort());
   	    	   }
    	
   	    	   // -> sorted is: cidrList=&command=... while url is: command=&cidrList=...
   	    	   String netParams = params.toString();
   	    	   url.append( netParams );
   	    	   sorted.append( netParams );

   	    	   String userList = constructNetworkUserList( items[i].getUserSet());
   	    	   if (null != userList) {
   	    		   url.append( userList );
   	   	    	   sorted.append( userList );
   	    	   }

	           Document cloudResp = resolveURL(genAPIURL(url.toString(), genQuerySignature(sorted.toString())), command, true );
               NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
		       if ( 0 < match.getLength()) {
		    	    Node item = match.item(0);
		    	    String jobId = new String( item.getFirstChild().getNodeValue());
		    	    if (!waitForAsynch( jobId )) throw new EC2ServiceException(ServerError.InternalError, command + " failed" );
	 	        } 
	 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");

	            url.setLength( 0 );
   	    	    sorted.setLength( 0 );
   	    	    params.setLength( 0 );
   	    	}
     		return true;
    		
      	} catch( EC2ServiceException error ) {
   		    logger.error( "EC2 " + command + "- " + error.toString());
   		    throw error;
   		
   	    } catch( Exception e ) {
   		    logger.error( "EC2 " + command + " - " + e.toString());
   		    throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
   	    } 	
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
    	
    	String temp = safeURLencode( cidrList.toString());	
    	return new String( "cidrList=" + temp );
    }
    
    /**
     * The Cloud Stack API uses array notation to pass along a set of these values.
     * 
     * @throws UnsupportedEncodingException 
     */
    private String constructNetworkUserList( EC2SecurityGroup[] groups ) throws UnsupportedEncodingException 
    {
    	if (null == groups || 0 == groups.length) return null;    	
    	StringBuffer userList = new StringBuffer();

    	for( int i=0; i < groups.length; i++ ) {
        	userList.append( "&userSecurityGroupList["+i+"].account=" + safeURLencode( groups[i].getAccount()));
        	userList.append( "&userSecurityGroupList["+i+"].group="   + safeURLencode( groups[i].getName()));		
    	}
    	return userList.toString();
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
    		logger.error( "EC2 DescribeSnapshots - " + error.toString());
    		throw error;
    		
    	} 
    	catch( Exception e ) {
    		logger.error( "EC2 DescribeSnapshots - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    
    public EC2Snapshot createSnapshot( String volumeId ) 
    {
		EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();

    	try {
	        String query = new String( "command=createSnapshot&volumeId=" + volumeId );
            Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "createSnapshot", true );
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     EC2Snapshot shot = waitForSnapshot( jobId );
	    	     
	    	     shot.setVolumeId( volumeId );
	    	     volumes = listVolumes( volumeId, null, volumes );
	             EC2Volume[] volSet = volumes.getVolumeSet();
	             if (0 < volSet.length) shot.setVolumeSize( volSet[0].getSize());
	    	     return shot;
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
   	        
       	} catch( EC2ServiceException error ) {
     		logger.error( "EC2 CreateSnapshot - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 CreateSnapshot - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    public boolean deleteSnapshot( String snapshotId ) 
    {
    	try {
	        String query = new String( "command=deleteSnapshot&id=" + snapshotId );
            Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "deleteSnapshot", true );
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     if (waitForAsynch( jobId )) return true;
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");

    	    return false;
   	        
       	} catch( EC2ServiceException error ) {
     		logger.error( "EC2 DeleteSnapshot - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DeleteSnapshot - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    public boolean modifyImageAttribute( EC2Image request ) 
    {
    	EC2DescribeImagesResponse images = new EC2DescribeImagesResponse();

    	try {
            images = listTemplates( request.getId(), images );
            EC2Image[] imageSet = images.getImageSet();

 	        String query = new String( "command=updateTemplate" +
	                                   "&displayText=" + safeURLencode( request.getDescription()) +
	                                   "&id="          + request.getId() +
	                                   "&name="        + safeURLencode( imageSet[0].getName()));

            resolveURL(genAPIURL(query, genQuerySignature(query)), "updateTemplate", true );
            return true;
	        
   	    } catch( EC2ServiceException error ) {
 		    logger.error( "EC2 ModifyImage - " + error.toString());
		    throw error;
		
	    } catch( Exception e ) {
		    logger.error( "EC2 ModifyImage - " + e.toString());
		    throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
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
    		logger.error( "EC2 DescribeImages - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DescribeImages - " + e.toString());
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
            for( int i=0; i < volSet.length; i++ ) 
            {
            	 String volType = volSet[i].getType();
            	 if (volType.equalsIgnoreCase( "ROOT" )) 
            	 {
            		 String vmState = volSet[i].getVMState();
            		 if (vmState.equalsIgnoreCase( "running" ) || vmState.equalsIgnoreCase( "starting" )) 
            		 {
            			 needsRestart = true;
            			 if (!stopVirtualMachine( request.getInstanceId()))
            		         throw new EC2ServiceException(ClientError.IncorrectState, "CreateImage - instance must be in a stopped state");
            		 }           		 
            		 volumeId = volSet[i].getId();
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
            
            String description = request.getDescription();
  	        String query = new String( "command=createTemplate" +
  	        		                   "&displayText=" + (null == description ? "" : safeURLencode( description )) +
  	        		                   "&name="        + safeURLencode( request.getName()) +
  	        		                   "&osTypeId="    + osTypeId +
  	        		                   "&volumeId="    + volumeId );
 		    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "createTemplate", true );
 		    
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     response = waitForTemplate( jobId );  
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
 	        
 	        
 	        // [C] If we stopped the virtual machine now we need to restart it
 	        if (needsRestart) 
 	        {
   			    if (!startVirtualMachine( request.getInstanceId()))
		            throw new EC2ServiceException(ServerError.InternalError
		            		,"CreateImage - restarting instance " + request.getInstanceId() + " failed");
 	        }
 	        return response;
    	    
    	} catch( EC2ServiceException error ) {
 		    logger.error( "EC2 CreateImage - " + error.toString());
		    throw error;
		
	    } catch( Exception e ) {
		    logger.error( "EC2 CreateImage - " + e.toString());
		    throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
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
    			
            // -> the parameters must be in sorted order for proper signature generation
	        String query = new String( "command=registerTemplate" +
	       	 	                       "&displayText=" + (null == request.getDescription() ? safeURLencode( request.getName()) : safeURLencode( request.getDescription())) +
	       		                       "&format="      + safeURLencode( request.getFormat()) +
	       		                       "&name="        + safeURLencode( request.getName()) +
	       		                       "&osTypeId="    + safeURLencode( toOSTypeId( request.getOsTypeName())) +
	       		                       "&url="         + safeURLencode( request.getLocation()) +
	       		                       "&zoneId="      + toZoneId( request.getZoneName()));

		    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "registerTemplate", true );
		    
		    EC2CreateImageResponse image = new EC2CreateImageResponse(); 
            NodeList match = cloudResp.getElementsByTagName( "id" ); 
	        if ( 0 < match.getLength()) {
    	        Node item = match.item(0);
    	        image.setId( item.getFirstChild().getNodeValue());
            }
	        return image;
	    
	    } catch( EC2ServiceException error ) {
		    logger.error( "EC2 RegisterImage - " + error.toString());
	        throw error;
	
        } catch( Exception e ) {
	        logger.error( "EC2 RegisterImage - " + e.toString());
	        throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
        }
    }
    
    /**
     * Our implementation is different from Amazon in that we do delete the template
     * when we deregister it.   The cloud API has not deregister call.
     */
    public boolean deregisterImage( EC2Image image ) 
    {
    	try {
	        String query = new String( "command=deleteTemplate&id=" + image.getId());
            Document cloudResp = resolveURL(genAPIURL( query, genQuerySignature(query)), "deleteTemplate", true );
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     if (waitForAsynch( jobId )) return true;
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");

    	    return false;
   	        
       	} catch( EC2ServiceException error ) {
     		logger.error( "EC2 DeregisterImage - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DeregisterImage - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    
    public EC2DescribeInstancesResponse handleRequest( EC2DescribeInstances request ) 
    {
    	try {
   	        return listVirtualMachines( request.getInstancesSet(), request.getFilterSet()); 
   	        
       	} 
    	catch( EC2ServiceException error ) 
    	{
     		logger.error( "EC2 DescribeInstances - " + error.toString());
    		throw error;
    		
    	} 
    	catch( Exception e ) 
    	{
    		logger.error( "EC2 DescribeInstances - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }

    
    public Map[] describeAddresses(String[] publicIps)
    {
        try {
            Map[] addressList = execList("command=listPublicIpAddresses");
            Map[] vmList      = execList("command=listVirtualMachines");

            Map<String, String> id2name = new HashMap<String, String>();
            for (Map vm: vmList) {
                id2name.put(vm.get("id").toString(), vm.get("name").toString());
            }
            for (Map a: addressList) {
                if (a.containsKey("virtualmachineid")) {
                    String vmId = a.get("virtualmachineid").toString();
                    a.put("virtualmachinename", id2name.get(vmId));
                }
            }

            if (null == publicIps || 0 == publicIps.length)
                return addressList;

            // Otherwise, we filter addressList using publicIps.

            Map<String, Map> ip2address = new HashMap<String, Map>();
            for (Map a: addressList) {
                ip2address.put(a.get("ipaddress").toString(), a);
            }

            List<Map> filtered = new ArrayList<Map>();
            for (String ip: publicIps) {
                Map a = ip2address.get(ip);
                if (null != a) {
                    filtered.add(a);
                }
            }
            return filtered.toArray(new Map[0]);

        } catch( EC2ServiceException error ) {
            logger.error( "EC2 DescribeAddresses - " + error.toString());
            throw error;

        } catch( Exception e ) {
            logger.error( "EC2 DescribeAddresses - " + e.toString());
            throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
        }
    }

    public String allocateAddress()
    {
        try {
            Map r = execute("command=associateIpAddress&zoneId=%s", toZoneId(null));
            String ipId = r.get("id").toString();
            Map[] l = execList("command=listPublicIpAddresses&id=%s", ipId);
            return l[0].get("ipaddress").toString();

        } catch( EC2ServiceException error ) {
            logger.error( "EC2 AllocateAddress - " + error.toString());
            throw error;

        } catch( Exception e ) {
            logger.error( "EC2 AllocateAddress - " + e.toString());
            throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
        }
    }

	public boolean releaseAddress(String publicIp) {
		if (null == publicIp)
			throw new EC2ServiceException(ServerError.InternalError,
					"IP address is a required parameter");
		try {
			Map[] l = execList("command=listPublicIpAddresses&ipAddress=%s",
					publicIp);
			
			if (l == null || l.length == 0) {
				logger.error("Unable to find ip address " + publicIp);
				return false;
			}
			String ipId = l[0].get("id").toString();
			
			String query = new String("command=disassociateIpAddress&id=" + ipId);
			Document cloudResp = resolveURL(
					genAPIURL(query, genQuerySignature(query)),
					"disassociateIpAddress", true);
			
			NodeList match = cloudResp.getElementsByTagName("jobid");
			if (0 < match.getLength()) {
				Node item = match.item(0);
				String jobId = new String(item.getFirstChild().getNodeValue());
				if (waitForAsynch(jobId))
					return true;
			} else
				throw new EC2ServiceException(ServerError.InternalError,
						"An unexpected error occurred during ip address release.");

			return false;
			
		} catch (EC2ServiceException error) {
			logger.error("EC2 ReleaseAddress: ", error);
			throw error;

		} catch (Exception e) {
			logger.error("EC2 ReleaseAddress: ", e);
			throw new EC2ServiceException(ServerError.InternalError,
					"An unexpected error occurred.");
		}
	}

    public boolean associateAddress(String publicIp, String vmName)
    {
        if (null == publicIp || null == vmName)
            throw new EC2ServiceException(EC2ServiceException.ServerError.InternalError, "Both IP address and instance id are required");

        try {
            Map[] addressList = execList("command=listPublicIpAddresses&ipAddress=%s", publicIp);
            Map[] vmList      = execList("command=listVirtualMachines&name=%s", vmName);

            if (0 == addressList.length)
                throw new EC2ServiceException(ClientError.InvalidParameterValue, "Address not allocated to account.");
            if (0 == vmList.length)
                throw new EC2ServiceException(ClientError.InvalidParameterValue, "Instance not found.");

            String ipId = addressList[0].get("id").toString();
            String vmId = vmList[0].get("id").toString();
            Map r = execute("command=enableStaticNat&ipAddressId=%s&virtualMachineId=%s", ipId, vmId);
            return r.get("success").toString().equalsIgnoreCase("true");

        } catch( EC2ServiceException error ) {
            logger.error( "EC2 AssociateAddress - " + error.toString());
            throw error;

        } catch( Exception e ) {
            logger.error( "EC2 AssociateAddress - " + e.toString());
            throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
        }
    }

    public boolean disassociateAddress(String publicIp)
    {
        if (null == publicIp)
            throw new EC2ServiceException(EC2ServiceException.ServerError.InternalError, "IP address is a required parameter");

        try {
            Map[] l     = execList("command=listPublicIpAddresses&ipAddress=%s", publicIp);
            String ipId = l[0].get("id").toString();
            Map async   = execute("command=disableStaticNat&ipAddressId=%s", ipId);

            String jobId = async.get("jobid").toString();
            return waitForAsynch(jobId);

        } catch( EC2ServiceException error ) {
            logger.error( "EC2 DisassociateAddress - " + error.toString());
            throw error;

        } catch( Exception e ) {
            logger.error( "EC2 DisassociateAddress - " + e.toString());
            throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
        }
    }

    public EC2DescribeAvailabilityZonesResponse handleRequest(EC2DescribeAvailabilityZones request) 
    {	
    	try {
    		return listZones( request.getZoneSet());
    		
       	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 DescribeAvailabilityZones - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DescribeAvailabilityZones - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
    
    
    public EC2DescribeVolumesResponse handleRequest( EC2DescribeVolumes request ) 
    {
    	EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();
    	EC2VolumeFilterSet vfs = request.getFilterSet();

    	try 
    	{   String[] volumeIds = request.getVolumeSet();
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
       	} 
    	catch( EC2ServiceException error ) 
    	{
    		logger.error( "EC2 DescribeVolumes - " + error.toString());
    		throw error;		
    	} 
    	catch( Exception e ) 
    	{
    		logger.error( "EC2 DescribeVolumes - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
   
    
    public EC2Volume attachVolume( EC2Volume request ) 
    {
    	try 
    	{   request.setDeviceId( mapDeviceToCloudDeviceId( request.getDevice()));

	        StringBuffer params = new StringBuffer();
   	        params.append( "command=attachVolume" );
   	        params.append( "&deviceId=" + request.getDeviceId());
   	        params.append( "&id=" + request.getId());
   	        params.append( "&virtualMachineId=" + request.getInstanceId());
   	        String query = params.toString();

  		    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "attachVolume", true );
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) 
 	        {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     if (waitForAsynch( jobId )) request.setState( "attached" );
            }
 	        else throw new EC2ServiceException( ServerError.InternalError, "An unexpected error occurred." );
 	        
    	    return request;  	        
       	} 
    	catch( EC2ServiceException error ) 
    	{
    		logger.error( "EC2 AttachVolume 1 - " + error.toString());
    		throw error;    		
    	} 
    	catch( Exception e ) 
    	{
    		logger.error( "EC2 AttachVolume 2 - " + e.toString());
    		throw new EC2ServiceException( ServerError.InternalError, e.toString());
    	}   	    
    }

    
    public EC2Volume detachVolume(EC2Volume request) 
    {
    	try {
	        StringBuffer params = new StringBuffer();
   	        params.append( "command=detachVolume" );
   	        params.append( "&id=" + request.getId());
   	        String query = params.toString();

   	        // -> do first cause afterwards the volume has not volume associated with it
 	        // -> if instanceId is not given on the request then we need to look it up for the response
 	        if (null == request.getInstanceId()) {
 	   		    EC2DescribeVolumesResponse volumes = new EC2DescribeVolumesResponse();
  			    volumes = listVolumes( request.getId(), null, volumes );
 	            EC2Volume[] volSet = volumes.getVolumeSet();
 	            if (0 < volSet.length) request.setInstanceId( volSet[0].getInstanceId());
 	        }

  		    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "detachVolume", true );
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     if (waitForAsynch( jobId )) request.setState( "detached" );
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
 	        
            return request;
   	        
       	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 DetachVolume - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 DetachVolume - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}   	    
    }
   
    
    public EC2Volume handleRequest( EC2CreateVolume request ) 
    { 
        StringBuffer params = new StringBuffer();
    	boolean foundDisk = false;
    	
    	try 
    	{   params.append( "command=createVolume" );
   	        
    		// -> put either snapshotid or diskofferingid on the request
    		String snapshotId = request.getSnapshotId();
    		Integer size      = request.getSize();

   	        if (null == snapshotId)
   	        {
   	   	        DiskOfferings findDisk = listDiskOfferings(); 
   	   		    DiskOffer[] offerSet   = findDisk.getOfferSet();
   	   		    for( int i=0; i < offerSet.length; i++ ) 
   	   		    {
   	   		         if (offerSet[i].getIsCustomized()) {
   	   		    	     params.append( "&diskofferingid=" + offerSet[i].getId());
   	   		    	     foundDisk = true;
   	   		    	     break;
   	   		         } 	   		    		  
   	   		    }
   	 	        if (!foundDisk) throw new EC2ServiceException(ServerError.InternalError, "No Customize Disk Offering Found");
   	        }
   	      
   	        // -> no volume name is given in the Amazon request but is required in the cloud API
    		String volName = safeURLencode( UUID.randomUUID().toString());
   	        params.append( "&name=" + volName );
 
   	        if (null != size      ) params.append( "&size=" + size.toString());        
   	        if (null != snapshotId) params.append( "&snapshotId=" + snapshotId );  	        
   	        params.append( "&zoneId=" + toZoneId( request.getZoneName()));     
   	        String query = params.toString();   	        
   	        

            // ->
  		    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "createVolume", true );
	        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
 	        if ( 0 < match.getLength()) {
	    	     Node item = match.item(0);
	    	     String jobId = new String( item.getFirstChild().getNodeValue());
	    	     return waitForVolume( jobId );  
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
   	        
       	} 
    	catch( EC2ServiceException error ) 
    	{
    		logger.error( "EC2 CreateVolume - " + error.toString());
    		throw error;	
    	} 
    	catch( Exception e ) 
    	{
    		logger.error( "EC2 CreateVolume - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}   	    
    }

    
    /**
     * The cloudstack documentation shows this to be an "(A)" (i.e., asynch) operation but it is not.
     * @param request
     * @return
     */
    public EC2Volume deleteVolume( EC2Volume request ) 
    {
    	try 
    	{   StringBuffer params = new StringBuffer();
   	        params.append( "command=deleteVolume" );
   	        params.append( "&id=" + request.getId());
   	        String query = params.toString();

  		    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "deleteVolume", true );
	        NodeList match = cloudResp.getElementsByTagName( "success" );
 	        if ( 0 < match.getLength()) 
 	        {
	    	     Node item = match.item(0);
	    	     String value = new String( item.getFirstChild().getNodeValue());
	    	     if (value.equalsIgnoreCase( "true" )) request.setState( "deleted" );
            }
 	        else throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");

    	    return request;   	        
       	} 
    	catch( EC2ServiceException error ) 
    	{
    		logger.error( "EC2 DeleteVolume 1 - " + error.toString());
    		throw error;  		
    	} 
    	catch( Exception e ) 
    	{
    		logger.error( "EC2 DeleteVolume 2 - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}   	    
    }

    
    /**
     * Note that more than one VM can be requested rebooted at once. 
     * The Amazon API does not wait around for the result of the operation.
     */
    public boolean handleRequest(EC2RebootInstances request) 
    {
    	EC2Instance[] vms = null;

    	// -> reboot is not allowed on destroyed (i.e., terminated) instances
    	try {
    	    EC2DescribeInstancesResponse previousState = listVirtualMachines( request.getInstancesSet(), null );
     	    vms = previousState.getInstanceSet();
    	    
     	    // -> send reboot requests for each item 
     		for( int i=0; i < vms.length; i++ ) 
     		{
     		   if (vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;

     	       String query = new String( "command=rebootVirtualMachine&id=" + vms[i].getId());
     		   resolveURL(genAPIURL(query, genQuerySignature(query)), "rebootVirtualMachine", true );
     		}
     		return true;
     		
       	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 RebootInstances - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 RebootInstances - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
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
    public EC2RunInstancesResponse handleRequest(EC2RunInstances request) 
    {
    	EC2RunInstancesResponse instances = new EC2RunInstancesResponse();
    	NodeList match  = null;
    	Node     item   = null;
    	int createInstances    = 0;
    	int canCreateInstances = -1;
    	int waitFor            = 0;
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
	        String[]      jobIds = new String[ createInstances ];

	        
     	    // [B] Create n separate VM instances 
	        OfferingBundle offer = instanceTypeToOfferBundle( request.getInstanceType());
    	    StringBuffer params = new StringBuffer();
       	    params.append( "command=deployVirtualMachine" );

       	    if (null != request.getGroupId()) params.append("&group=" + request.getGroupId());
       	    
       	    String zoneId = toZoneId(request.getZoneName());
       	    
       	    if (cloudStackVersion >= CLOUD_STACK_VERSION_2_2) {
           	    if (null != request.getKeyName()) params.append("&keypair=" + safeURLencode(request.getKeyName()));
           	    
           	    String dcNetworkType = getDCNetworkType(zoneId);
           	    if (dcNetworkType.equals("Advanced")) { 
           	    	params.append("&networkIds=" + getNetworkId(zoneId));
           	    }
       	    }

       	    params.append( "&serviceOfferingId=" + offer.getServiceOfferingId());
            params.append("&size=" + String.valueOf(request.getSize()));
       	    params.append( "&templateId=" + request.getTemplateId());
       	    if (null != request.getUserData()) params.append( "&userData=" + safeURLencode( request.getUserData()));
       	    
       	    // temp hack
       	    //String vlanId = hackGetVlanId();
       	    //if (null != vlanId) params.append( "&vlanId=" + vlanId );
       	    // temp hack   
       	    
       	    params.append( "&zoneId=" + zoneId);
       	    String query      = params.toString();
            String apiCommand = genAPIURL(query, genQuerySignature(query));
 		
     		for( int i=0; i < createInstances; i++ ) 
     		{
     		   Document cloudResp = resolveURL( apiCommand, "deployVirtualMachine", true );
     		   vms[i] = new EC2Instance();
      		    
    	       match = cloudResp.getElementsByTagName( "jobid" ); 
     	       if (0 < match.getLength()) {
   	    	       item = match.item(0);
   	    	       jobIds[i] = new String( item.getFirstChild().getNodeValue());
   	    	       waitFor++;
	           }
     	    }    		
	   		
     	   	// [C] Wait until all the requested starts have completed or failed
     		//  -> did they all succeeded?
           	vms = waitForDeploy( vms, jobIds, waitFor );
        	for( int k=0; k < vms.length; k++ )	
        	{
        		if (null != vms[k].getId()) {
        			// request.getInstanceType() can return null
        			vms[k].setServiceOffering( serviceOfferingIdToInstanceType( offer.getServiceOfferingId()));
        			instances.addInstance( vms[k] );
        			countCreated++;
        		}
        	}

         	if (0 == countCreated) 
         		throw new EC2ServiceException(ServerError.InsufficientInstanceCapacity, "Insufficient Instance Capacity" );
            
         	return instances;
            
    	} catch( EC2ServiceException error ) {
    		throw error;
     	} catch( Exception e ) {
    		logger.error( "EC2 RunInstances - deploy 2 " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
     	}
     	
    }
    
    /**
     * Note that more than one VM can be requested started at once. 
     * The Amazon API returns the previous state and the modified state as the
     * result of this API.
     */
    public EC2StartInstancesResponse handleRequest(EC2StartInstances request) 
    {
    	EC2StartInstancesResponse instances = new EC2StartInstancesResponse();
    	EC2Instance[] vms = null;
    	Node item    = null;
    	int  waitFor = 0;
    	
    	// -> first determine the current state of each VM (becomes it previous state)
    	try {
    	    EC2DescribeInstancesResponse previousState = listVirtualMachines( request.getInstancesSet(), null );
     	    vms = previousState.getInstanceSet();
        	String[] jobIds = new String[ vms.length ];

    	    // -> send start requests for each item 
     		for( int i=0; i < vms.length; i++ ) 
     		{
     		   // -> if its already running then we are done
     		   vms[i].setPreviousState( vms[i].getState());
     		   if (vms[i].getState().equalsIgnoreCase( "Running" ) || vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;

     	       String query = new String( "command=startVirtualMachine&id=" + vms[i].getId());
     		   Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "startVirtualMachine", true );
     		   
    	       NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
     	       if (0 < match.getLength()) {
   	    	       item = match.item(0);
   	    	       jobIds[i] = new String( item.getFirstChild().getNodeValue());
   	    	       waitFor++;
	           }
     		}
     		
        	// -> wait until all the requested starts have completed or failed
           	vms = waitForStartStop( vms, jobIds, waitFor, "running" );
        	for( int k=0; k < vms.length; k++ )	instances.addInstance( vms[k] );
        	return instances;

    	} catch( EC2ServiceException error ) {
    		logger.error( "EC2 StartInstances - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 StartInstances - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }
   
    /**
     * Note that more than one VM can be requested stopped at once. 
     * The Amazon API returns the previous state and the modified state as the
     * result of this API.
     */
    public EC2StopInstancesResponse handleRequest(EC2StopInstances request) 
    {
    	EC2StopInstancesResponse instances = new EC2StopInstancesResponse();
    	EC2Instance[] vms = null;
    	String query   = null;
    	Node   item    = null;
    	int    waitFor = 0;
    
    	// -> first determine the current state of each VM (becomes it previous state)
    	try {
    	    EC2DescribeInstancesResponse previousState = listVirtualMachines( request.getInstancesSet(), null );
     	    vms = previousState.getInstanceSet();
        	String[] jobIds = new String[ vms.length ];
    
    	    // -> send stop requests for each item 
     		for( int i=0; i < vms.length; i++ ) 
     		{
     		   vms[i].setPreviousState( vms[i].getState());
     		   
     		   if ( request.getDestroyInstances()) {
     			    if (vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;
     		   }
     		   else {
     			    if (vms[i].getState().equalsIgnoreCase( "Stopped" ) || vms[i].getState().equalsIgnoreCase( "Destroyed" )) continue;
     		   }

       	       if ( request.getDestroyInstances())
     	            query = new String( "command=destroyVirtualMachine&id=" + vms[i].getId());
   	           else query = new String( "command=stopVirtualMachine&id="    + vms[i].getId());
     		   Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), query, true );
     		   
    	       NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
    	       if (0 < match.getLength()) {
   	    	       item = match.item(0);
   	    	       jobIds[i] = new String( item.getFirstChild().getNodeValue());
   	    	       waitFor++;
	           }
     		}
     		
        	// -> wait until all the requested stops have completed or failed
        	vms = waitForStartStop( vms, jobIds, waitFor, (request.getDestroyInstances() ? "destroyed" : "stopped"));
        	for( int k=0; k < vms.length; k++ )	instances.addInstance( vms[k] );
        	return instances;

    	} catch( EC2ServiceException error ) {
     		logger.error( "EC2 StopInstances - " + error.toString());
    		throw error;
    		
    	} catch( Exception e ) {
    		logger.error( "EC2 StopInstances - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred.");
    	}
    }

    
    public List<EC2SSHKeyPair> describeKeyPairs() {
    	ensureVersion(CLOUD_STACK_VERSION_2_2);
    	
    	String query = "command=listSSHKeyPairs";
    	Document response = null;
    	
    	try {
			response = resolveURL(genAPIURL(query, genQuerySignature(query)), "listSSHKeyPairs", true);
		} catch (EC2ServiceException e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
		} catch (Exception e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
		}
		
		List<EC2SSHKeyPair> returnList = new ArrayList<EC2SSHKeyPair>();
		NodeList match = response.getElementsByTagName("keypair"); 
	   
		for (int i=0; i < match.getLength(); i++) {
			EC2SSHKeyPair keyPair = new EC2SSHKeyPair();
			Node parent = null;
			
			if ((parent = match.item(i)) != null) { 
				NodeList children = parent.getChildNodes();
	    		
				for( int j=0; j < children.getLength(); j++ ) {
					Node child = children.item(j);
	    			String name = child.getNodeName();
	    			
	    			if (child.getFirstChild() != null) {
	    				if (name.equalsIgnoreCase("name"))
	    					keyPair.setKeyName(child.getFirstChild().getNodeValue());
	    				else if (name.equalsIgnoreCase("fingerprint"))
	    					keyPair.setFingerprint(child.getFirstChild().getNodeValue());
	    			} 
				}
			}
			
			if (keyPair.getKeyName() != null && keyPair.getFingerprint() != null)
				returnList.add(keyPair);
		}
    	
		return returnList;
    }
    
    public EC2SSHKeyPair importKeyPair(String keyName, String publicKey) {     	
    	ensureVersion(CLOUD_STACK_VERSION_2_2);

    	String query = "command=registerSSHKeyPair";
    	Document response = null;
    	
    	try {
        	query += "&name=".concat(safeURLencode(keyName));
        	query += "&publickey=".concat(safeURLencode(publicKey));
        	response = resolveURL(genAPIURL(query, genQuerySignature(query)), "registerSSHKeyPair", true);
		} catch (EC2ServiceException e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());

			if (e.getMessage().startsWith("431")) // Needs improvement
				throw new EC2ServiceException(ClientError.InvalidKeyPair_Duplicate, e.getMessage().replaceFirst("431 ", ""));
			else
				throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
			
		} catch (Exception e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
		}

		EC2SSHKeyPair keyPair = new EC2SSHKeyPair();		
		NodeList pair = response.getElementsByTagName("keypair");
		if (pair.getLength() > 0 && pair.item(0) != null && pair.item(0).hasChildNodes()) {
			NodeList values = pair.item(0).getChildNodes();
			for (int i = 0; i < values.getLength(); i++) {
				Node value = values.item(i);
    			if (value.getFirstChild() != null) {
    				if (value.getNodeName().equals("name")) 
    					keyPair.setKeyName(value.getFirstChild().getNodeValue());
    				if (value.getNodeName().equals("fingerprint")) 
    					keyPair.setFingerprint(value.getFirstChild().getNodeValue());
    			}
			}
		}

    	return keyPair;
    }
    
    public EC2SSHKeyPair createKeyPair(String keyName) {
    	ensureVersion(CLOUD_STACK_VERSION_2_2);

    	String query = "command=createSSHKeyPair";
    	Document response = null;
    	
    	try {
        	query += "&name=".concat(URLEncoder.encode(keyName, "utf8"));
			response = resolveURL(genAPIURL(query, genQuerySignature(query)), "createSSHKeyPair", true);
		} catch (EC2ServiceException e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());

			if (e.getMessage().startsWith("431")) // Needs improvement
				throw new EC2ServiceException(ClientError.InvalidKeyPair_Duplicate, e.getMessage().replaceFirst("431 ", ""));
			else
				throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
			
		} catch (Exception e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
		}
		
		EC2SSHKeyPair keyPair = new EC2SSHKeyPair();		
		NodeList pair = response.getElementsByTagName("keypair");
		if (pair.getLength() > 0 && pair.item(0) != null && pair.item(0).hasChildNodes()) {
			NodeList values = pair.item(0).getChildNodes();
			for (int i = 0; i < values.getLength(); i++) {
				Node value = values.item(i);
    			if (value.getFirstChild() != null) {
    				if (value.getNodeName().equals("name")) 
    					keyPair.setKeyName(value.getFirstChild().getNodeValue());
    				if (value.getNodeName().equals("fingerprint")) 
    					keyPair.setFingerprint(value.getFirstChild().getNodeValue());
    				if (value.getNodeName().equals("privatekey"))
    					keyPair.setPrivateKey(value.getFirstChild().getNodeValue());
    			}
			}
		}

    	return keyPair;	
    }
    
    public boolean deleteKeyPair(String keyName) {
    	ensureVersion(CLOUD_STACK_VERSION_2_2);

    	String query = "command=deleteSSHKeyPair";
    	Document response = null;
    	
    	try {
        	query += "&name=".concat(URLEncoder.encode(keyName, "utf8"));
			response = resolveURL(genAPIURL(query, genQuerySignature(query)), "deleteSSHKeyPair", true);
		} catch (EC2ServiceException e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());	
		} catch (Exception e) {
			logger.error( "EC2 Describe KeyPairs - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
		}
		
		NodeList success = response.getElementsByTagName("success");
		if (success != null && success.getLength() > 0 && success.item(0).hasChildNodes())
			return success.item(0).getFirstChild().getNodeValue().equals("true");
		
		return false;
    }
    
    public EC2PasswordData getPasswordData(String instanceId) {
    	ensureVersion(CLOUD_STACK_VERSION_2_2);

    	String query = "command=getVMPassword";
    	Document response = null;
    	EC2PasswordData passwdData = new EC2PasswordData();
    	passwdData.setInstanceId(instanceId);
    	
    	try {
    		query += "&id=".concat(URLEncoder.encode(instanceId, "utf8"));
    		response = resolveURL(genAPIURL(query, genQuerySignature(query)), "getVMPassword", true);
    		NodeList passwd = response.getElementsByTagName("encryptedpassword");
    		if (passwd != null && passwd.getLength() > 0 && passwd.item(0).hasChildNodes())
    			passwdData.setEncryptedPassword(passwd.item(0).getFirstChild().getNodeValue());

    	} catch (EC2ServiceException e) {
    		if (!e.getMessage().startsWith("431 No password for VM with id")) { 
    			logger.error( "EC2 Describe KeyPairs - " + e.toString());
    			throw new EC2ServiceException(ServerError.InternalError, e.getMessage());
    		} else {
    			passwdData.setEncryptedPassword("");
    		}
    	} catch (Exception e) {
    		logger.error( "EC2 Describe KeyPairs - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    	}

    	return passwdData;
    }
    
    
    /**
     * Wait until one specific VM has stopped
     */
    private boolean stopVirtualMachine( String instanceId ) 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException 
    {
    	EC2Instance[] vms    = new EC2Instance[1];
    	String[]      jobIds = new String[1];
    	
    	vms[0] = new EC2Instance();
    	vms[0].setState( "running" );
    	vms[0].setPreviousState( "running" );

        String query = new String( "command=stopVirtualMachine&id=" + instanceId );
	    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), query, true );
		   
        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
        if ( 0 < match.getLength()) {
   	         Node item = match.item(0);
   	         jobIds[0] = new String( item.getFirstChild().getNodeValue());
        }
        else throw new EC2ServiceException(ServerError.InternalError ,"Internal Server Error");
		
	    vms = waitForStartStop( vms, jobIds, 1, "stopped" );
	    return vms[0].getState().equalsIgnoreCase( "stopped" );    	
    }
  
    private boolean startVirtualMachine( String instanceId ) 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException 
    {
	    EC2Instance[] vms    = new EC2Instance[1];
	    String[]      jobIds = new String[1];
	
	    vms[0] = new EC2Instance();
	    vms[0].setState( "stopped" );
	    vms[0].setPreviousState( "stopped" );

        String query = new String( "command=startVirtualMachine&id=" + instanceId );
        Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), query, true );
	   
        NodeList match = cloudResp.getElementsByTagName( "jobid" ); 
        if ( 0 < match.getLength()) {
	         Node item = match.item(0);
	         jobIds[0] = new String( item.getFirstChild().getNodeValue());
        }
        else throw new EC2ServiceException(ServerError.InternalError ,"Internal Server Error");
	
        vms = waitForStartStop( vms, jobIds, 1, "running" );
        return vms[0].getState().equalsIgnoreCase( "running" );    	
    }

    /**
     * RunInstances includes a min and max count of requested instances to create.  
     * We have to be able to create the min number for the user or none at all.  So
     * here we determine what the user has left to create. 
     * 
     * @return -1 means no limit exists, other positive numbers give max number left that
     *         the user can create.
     */
    private int calculateAllowedInstances() 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, 
               SAXException, ParserConfigurationException, ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
    {    
    	if ( cloudStackVersion >= CLOUD_STACK_VERSION_2_1 )
    	{
	    	 NodeList match      = null;
		     Node     item       = null;
		     int      maxAllowed = -1;
		
	 		 // -> get the user limits on instances
	     	 String   query     = new String( "command=listResourceLimits&resourceType=0" );
	 		 Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "listResourceLimits", true );
	 		    
	  		 match = cloudResp.getElementsByTagName( "max" ); 
		     if ( 0 < match.getLength()) 
		     {
		    	  item = match.item(0);
	    	      maxAllowed = Integer.parseInt( item.getFirstChild().getNodeValue());
	    	      if (-1 == maxAllowed) return -1;   // no limit
		        
		          // -> how many instances has the user already created?
	    	      EC2DescribeInstancesResponse existingVMS = listVirtualMachines( null, null );
	    	      EC2Instance[] vmsList = existingVMS.getInstanceSet();
	    	      return (maxAllowed - vmsList.length);
		     }
		     else return 0;    	
    	}
    	else return -1;
 	}
   
    /**
     * createTemplate is an asynchronus call so here we poll (sleeping so we do not hammer the server)
     * until the operation completes.
     * 
     * @param jobId - parameter returned from the createTemplate request
     */
    private EC2CreateImageResponse waitForTemplate( String jobId ) 
        throws EC2ServiceException, IOException, SAXException, ParserConfigurationException, SignatureException 
    {
    	EC2CreateImageResponse image = new EC2CreateImageResponse();
    	NodeList  match  = null;
	    Node      item   = null;
	    int       status = 0;
	    
        while( true ) 
        {
	      String query = "command=queryAsyncJobResult&jobId=" + jobId;
	      Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "queryAsyncJobResult", true );
	      
		  match = cloudResp.getElementsByTagName( "jobstatus" ); 
	      if ( 0 < match.getLength()) {
	    	   item = match.item(0);
	    	   status = Integer.parseInt( new String( item.getFirstChild().getNodeValue()));
          }
	      else status = 2;

	      switch( status ) {
	      case 0:  // in progress, slow down the hits from this polling a little
      	       try { Thread.sleep( pollInterval1 ); }
    	       catch( Exception e ) {}
	    	   break;
	    	   
	      case 2:  
	      default:
  	           match = cloudResp.getElementsByTagName( "jobresult" ); 
               if ( 0 < match.getLength()) {
    	            item = match.item(0);
    	            if (null != item && null != item.getFirstChild())
    	        	    throw new EC2ServiceException(ServerError.InternalError, "Template action failed: " + item.getFirstChild().getNodeValue());
               }
               throw new EC2ServiceException(ServerError.InternalError, "Template action failed");
	    	   
		  case 1:  // Successfully completed
      	       match = cloudResp.getElementsByTagName( "id" ); 
    	       if (0 < match.getLength()) {
   	               item = match.item(0);
   	               image.setId( item.getFirstChild().getNodeValue());
    	       }
    	       return image;
	      }
       }
    }
    
    /**
     * Waiting for VMs to deploy, all the information to return to the user is
     * returned once all the asynch commands finish.  Note that multiple deployVirtualMachine commands 
     * can be outstanding and here we wait for all of them to complete either successfully
     * or by failure.   On a failure we continue to wait for the other pending requests.
     * 
     * @param vms       - used to change the state of a new vm
     * @param jobIds    - entries are null after completed, is an array of deployVirtualMachine requests to complete
     * @param waitCount - number of asynch jobs to wait for, the number of entries in jobIds array
     * 
     * @return the same array given as 'vms' parameter but with modified vm state
     */
    private EC2Instance[] waitForDeploy( EC2Instance[] vms, String[] jobIds, int waitCount ) 
        throws EC2ServiceException, IOException, SAXException, ParserConfigurationException, DOMException, ParseException, SignatureException 
    {
   	    NodeList match  = null;
   	    Node     item   = null;
   	    int      status = 0;
   	    int      j      = 0;
  	    
	    while( waitCount > 0 ) 
	    {
 	      if (null != jobIds[j]) 
 	      {
 		      String query = "command=queryAsyncJobResult&jobId=" + jobIds[j];
 		      Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "queryAsyncJobResult", true );

			  match = cloudResp.getElementsByTagName( "jobstatus" ); 
		      if ( 0 < match.getLength()) {
		    	   item = match.item(0);
		    	   status = Integer.parseInt( new String( item.getFirstChild().getNodeValue()));
	          }
		      else status = 2;

		      switch( status ) {
		      case 0:  // in progress, slow down the hits from this polling a little
	        	   try { Thread.sleep( pollInterval2 ); }
	        	   catch( Exception e ) {}
		    	   break;
		    	   
		      case 2:  // operation failed -- keep waiting for others in progress
		      default:
	  	           match = cloudResp.getElementsByTagName( "jobresult" ); 
	               if ( 0 < match.getLength()) {
	    	            item = match.item(0);
	    	            if (null != item && null != item.getFirstChild())
	    	        	    logger.info( "InternalError - deploy action failed: " + item.getFirstChild().getNodeValue());
	               }
	               waitCount--;
			       jobIds[j] = null;
		    	   break;
		      
		      case 1:  // Successfully completed
	      	       match = cloudResp.getElementsByTagName( "id" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setId( item.getFirstChild().getNodeValue());
	    	       }
	      	       match = cloudResp.getElementsByTagName( "name" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setName( item.getFirstChild().getNodeValue());
	    	       }
	     	       match = cloudResp.getElementsByTagName( "zonename" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setZoneName( item.getFirstChild().getNodeValue());
	    	       }
	     	       match = cloudResp.getElementsByTagName( "templateid" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setTemplateId( item.getFirstChild().getNodeValue());
	    	       }
	     	       match = cloudResp.getElementsByTagName( "group" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               if (null != item && null != item.getFirstChild()) 
	   	            	   vms[j].setGroup( item.getFirstChild().getNodeValue());
	    	       }
	     	       match = cloudResp.getElementsByTagName( "state" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setState( item.getFirstChild().getNodeValue());
	    	       }
	     	       match = cloudResp.getElementsByTagName( "created" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setCreated( item.getFirstChild().getNodeValue());
	    	       }
	     	       match = cloudResp.getElementsByTagName( "ipaddress" ); 
	    	       if (0 < match.getLength()) {
	   	               item = match.item(0);
	   	               vms[j].setIpAddress( item.getFirstChild().getNodeValue());
	    	       }
	    	       match = cloudResp.getElementsByTagName("account");
	    	       if (0 < match.getLength()) {
                       item = match.item(0);
                       vms[j].setAccountName( item.getFirstChild().getNodeValue());
                   }
	    	       match = cloudResp.getElementsByTagName("domainid");
                   if (0 < match.getLength()) {
                       item = match.item(0);
                       vms[j].setDomainId( item.getFirstChild().getNodeValue());
                   }
	    	       
	               waitCount--;
			       jobIds[j] = null;
			       break;
		      }
	      }
 	      // -> go over the array repeatedly until all are done
          j++;
          if (j >= jobIds.length) j=0;
	   }
	   return vms;
    }
   
    /**
     * Each Asynch response had different XML tags to that forces a different waitFor function
     * to create the required return object.
     * 
     * @param jobId 
     */
    private EC2Volume waitForVolume( String jobId ) 
        throws EC2ServiceException, IOException, SAXException, ParserConfigurationException, DOMException, ParseException, SignatureException 
    {
	    EC2Volume vol    = new EC2Volume();
    	NodeList  match  = null;
	    Node      item   = null;
	    int       status = 0;
	    
        while( true ) 
        {
		  String query = "command=queryAsyncJobResult&jobId=" + jobId;
 		  Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "queryAsyncJobResult", true );

		  match = cloudResp.getElementsByTagName( "jobstatus" ); 
	      if ( 0 < match.getLength()) {
	    	   item = match.item(0);
	    	   status = Integer.parseInt( new String( item.getFirstChild().getNodeValue()));
          }
	      else status = 2;

	      switch( status ) {
	      case 0:  // in progress, slow down the hits from this polling a little
      	       try { Thread.sleep( pollInterval3 ); }
    	       catch( Exception e ) {}
	    	   break;
	    	   
	      case 2:  
	      default:
   	           match = cloudResp.getElementsByTagName( "jobresult" ); 
               if ( 0 < match.getLength()) {
    	            item = match.item(0);
    	            if (null != item && null != item.getFirstChild())
    	        	    throw new EC2ServiceException(ServerError.InternalError, "Volume action failed: " + item.getFirstChild().getNodeValue());
               }
               throw new EC2ServiceException(ServerError.InternalError, "Volume action failed");
	    	   
		  case 1:  // Successfully completed
      	       match = cloudResp.getElementsByTagName( "id" ); 
    	       if (0 < match.getLength()) {
   	               item = match.item(0);
   	               vol.setId( item.getFirstChild().getNodeValue());
    	       }
     	       match = cloudResp.getElementsByTagName( "zonename" ); 
    	       if (0 < match.getLength()) {
   	               item = match.item(0);
   	               vol.setZoneName( item.getFirstChild().getNodeValue());
    	       }
     	       match = cloudResp.getElementsByTagName( "size" ); 
    	       if (0 < match.getLength()) {
   	               item = match.item(0);
   	               vol.setSize( item.getFirstChild().getNodeValue());
    	       }
     	       match = cloudResp.getElementsByTagName( "created" ); 
    	       if (0 < match.getLength()) {
   	               item = match.item(0);
   	               vol.setCreated( item.getFirstChild().getNodeValue());
    	       }
    	       vol.setState( "available" );
    	       return vol;
	      }
       }
    }

    private EC2Snapshot waitForSnapshot( String jobId ) 
        throws EC2ServiceException, IOException, SAXException, ParserConfigurationException, DOMException, ParseException, SignatureException 
    {
        EC2Snapshot shot   = new EC2Snapshot();
	    NodeList    match  = null;
        Node        item   = null;
        int         status = 0;
    
        while( true ) 
        {
  		   String query = "command=queryAsyncJobResult&jobId=" + jobId;
 		   Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "queryAsyncJobResult", true );

	       match = cloudResp.getElementsByTagName( "jobstatus" ); 
           if ( 0 < match.getLength()) {
    	        item = match.item(0);
    	        status = Integer.parseInt( new String( item.getFirstChild().getNodeValue()));
           }
           else status = 2;

           switch( status ) {
           case 0:  // in progress, seems to take a long time
        	    try { Thread.sleep( pollInterval4 ); }
        	    catch( Exception e ) {}
    	        break;
    	   
           case 2:  
           default:
    	        match = cloudResp.getElementsByTagName( "jobresult" ); 
                if ( 0 < match.getLength()) {
        	         item = match.item(0);
        	         if (null != item && null != item.getFirstChild())
        	         	 throw new EC2ServiceException(ServerError.InternalError, "Snapshot action failed: " + item.getFirstChild().getNodeValue());
                }
                throw new EC2ServiceException(ServerError.InternalError, "Snapshot action failed");
    	   
	       case 1:  // Successfully completed
  	            match = cloudResp.getElementsByTagName( "id" ); 
	            if (0 < match.getLength()) {
	                item = match.item(0);
	                shot.setId( item.getFirstChild().getNodeValue());
	            }
 	            match = cloudResp.getElementsByTagName( "name" ); 
	            if (0 < match.getLength()) {
	                item = match.item(0);
	                shot.setName( item.getFirstChild().getNodeValue());
	            }
 	            match = cloudResp.getElementsByTagName( "snapshottype" ); 
	            if (0 < match.getLength()) {
	                item = match.item(0);
	                shot.setType( item.getFirstChild().getNodeValue());
	            }
 	            match = cloudResp.getElementsByTagName( "account" ); 
	            if (0 < match.getLength()) {
	                item = match.item(0);
	                shot.setAccountName( item.getFirstChild().getNodeValue());
	            }
	            match = cloudResp.getElementsByTagName( "domainid" ); 
	            if (0 < match.getLength()) {
                    item = match.item(0);
                    shot.setDomainId(item.getFirstChild().getNodeValue());
                }
 	            match = cloudResp.getElementsByTagName( "created" ); 
	            if (0 < match.getLength()) {
	                item = match.item(0);
	                shot.setCreated( item.getFirstChild().getNodeValue());
	            }
	            return shot;
           }
        }
    }

    /**
     * Wait for the asynch request to finish and we just need to know if it succeeded or not
     * 
     * @param jobId
     * @return true - the requested action was successful, false - it failed
     */
    private boolean waitForAsynch( String jobId ) throws SignatureException 
    {    
	    while( true ) 
	    {
		   String result = checkAsyncResult( jobId );
		   //System.out.println( "waitForAsynch: " + result );
		  
		   if ( -1 != result.indexOf( "s:1" )) 
		   {
		       // -> requested action has finished
		       return true;
		   }
		   else if (-1 != result.indexOf( "s:2" ) || -1 != result.indexOf( "s:-1" )) 
		   {
		        // -> requested action has failed
		  	    return false;
		   }
		  
		   // -> slow down the hits from this polling a little
  	       try { Thread.sleep( pollInterval5 ); }
	       catch( Exception e ) {}
	    }
    }

    /**
     * Waiting for VMs to start or stop is identical.
     * 
     * @param vms       - used to change the state
     * @param jobIds    - entries are null after completed
     * @param waitCount - number of asynch jobs to wait for
     * 
     * @return the same array given as 'vms' parameter but with modified vm state
     */
    private EC2Instance[] waitForStartStop( EC2Instance[] vms, String[] jobIds, int waitCount, String newState ) throws SignatureException 
    {
   	    int j=0;
   	    
	    while( waitCount > 0 ) 
	    {
 	      if (null != jobIds[j]) 
 	      {  
		      String result = checkAsyncResult( jobIds[j] );
		      if ( -1 != result.indexOf( "s:1" )) 
		      {
		           // -> requested action has finished
			       vms[j].setState( newState );
			       waitCount--;
			       jobIds[j] = null;
		      }
		      else if (-1 != result.indexOf( "s:2" )) 
		      {
		           // -> state of the vm has not changed
			       vms[j].setState( vms[j].getPreviousState());
			       waitCount--;
			       jobIds[j] = null;
		      }
		      else if (-1 != result.indexOf( "s:-1" ))
		      {
		    	   // -> somehow the request has failed on the CloudStack
		    	   waitCount--;
		    	   jobIds[j] = null;
		      }
		      
			  // -> slow down the hits from this polling a little
	  	      try { Thread.sleep( pollInterval6 ); }
		      catch( Exception e ) {}
	      }
 	      // -> go over the array repeatedly until all are done
          j++;
          if (j >= jobIds.length) j=0;
	   }
	   return vms;
    }
    
    
    /**
     * Performs the cloud API listVirtualMachines one or more times.
     * 
     * @param virtualMachineIds - an array of instances we are interested in getting information on
     * @param ifs - filter out unwanted instances
     */
    private EC2DescribeInstancesResponse listVirtualMachines( String[] virtualMachineIds, EC2InstanceFilterSet ifs ) 
        throws IOException, ParserConfigurationException, SAXException, ParseException, EC2ServiceException, 
               SignatureException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
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
    private EC2DescribeVolumesResponse listVolumes( String volumeId, String instanceId, EC2DescribeVolumesResponse volumes ) 
        throws IOException, SAXException, ParserConfigurationException, EC2ServiceException, SignatureException, ParseException 
    {    
       	Node parent = null;    	
	    StringBuffer params = new StringBuffer();
   	    params.append( "command=listVolumes" );
   	    if (null != volumeId  ) params.append( "&id=" + volumeId );
   	    if (null != instanceId) params.append( "&virtualMachineId=" + instanceId );
   	    String query = params.toString();

       	Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "listVolumes", true );
		NodeList match     = cloudResp.getElementsByTagName( "volume" ); 
		int length = match.getLength();
		    
	    for( int i=0; i < length; i++ ) 
	    {
	    	if (null != (parent = match.item(i))) 
	    	{ 
	    		NodeList children = parent.getChildNodes();
	    		int      numChild = children.getLength();
    			EC2Volume vol = new EC2Volume();
	    		
	    		for( int j=0; j < numChild; j++ ) 
	    		{
	    			Node   child = children.item(j);
	    			String name  = child.getNodeName();
	    			
	    			if (null != child.getFirstChild()) 
	    			{
	    			    String value = child.getFirstChild().getNodeValue();
	    			
		                     if (name.equalsIgnoreCase( "id"              )) vol.setId( value );
   			            else if (name.equalsIgnoreCase( "attached"        )) vol.setAttached( value );
	   			        else if (name.equalsIgnoreCase( "created"         )) vol.setCreated( value );
	   			        else if (name.equalsIgnoreCase( "deviceid"        )) vol.setDeviceId( Integer.parseInt( value ));
	   			        else if (name.equalsIgnoreCase( "hypervisor"      )) vol.setHypervisor( value );
	   			        else if (name.equalsIgnoreCase( "snapshotid"      )) vol.setSnapShotId( value );		                     
	   			        else if (name.equalsIgnoreCase( "state"           )) vol.setState( mapToAmazonVolState(value));
	   			        else if (name.equalsIgnoreCase( "size"            )) vol.setSize( value );
	   			        else if (name.equalsIgnoreCase( "type"            )) vol.setType( value );
	   			        else if (name.equalsIgnoreCase( "virtualmachineid")) vol.setInstanceId( value );
	   			        else if (name.equalsIgnoreCase( "vmstate"         )) vol.setVMState( value );
	   			        else if (name.equalsIgnoreCase( "zonename"        )) vol.setZoneName( value );
	    			}
	    	    }
	 		    volumes.addVolume( vol );
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
 	    throws EC2ServiceException, SignatureException, IOException, SAXException, ParserConfigurationException 
 	{
 		EC2DescribeAvailabilityZonesResponse zones = null;
 		String[] interestedZones = null;
 		
 		if ( null != zoneName) {
 		     interestedZones = new String[1];
             interestedZones[0] = zoneName;
 		}
 		zones = listZones( interestedZones );
	    
 		if (null == zones.getZoneIdAt( 0 )) throw new EC2ServiceException(ClientError.InvalidParameterValue, "Unknown zoneName value - " + zoneName);
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

 		if ( null == amazonOffering ) 
 		{
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
    private String toOSTypeId( String osTypeName ) 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException 
    {
   	    Node   parent = null;
   	    String id     = null;
   	
		Document cloudResp = resolveURL(genAPIURL("command=listOsTypes", genQuerySignature("command=listOsTypes")), "listOsTypes", true );
	    NodeList match     = cloudResp.getElementsByTagName( "ostype" ); 
	    
	    int length = match.getLength();
        for( int i=0; i < length; i++ ) 
        {
    	    if (null != (parent = match.item(i))) 
    	    { 
    		    NodeList children = parent.getChildNodes();
    		    int      numChild = children.getLength();
    		
    		    for( int j=0; j < numChild; j++ ) 
    		    {
    			    Node   child    = children.item(j);
    			    String nodeName = child.getNodeName();
    			
			        if (nodeName.equalsIgnoreCase( "id" )) 
			        	id = child.getFirstChild().getNodeValue();
			        
			        if (nodeName.equalsIgnoreCase( "description" )) 
			        {
			        	String description = child.getFirstChild().getNodeValue();
			        	if (-1 != description.indexOf( osTypeName )) return id;
			        }
    	        }
	        }
        }
		throw new EC2ServiceException(ClientError.InvalidParameterValue, "Unknown osTypeName value - " + osTypeName);
    }

    /**
     * More than one place we need to access the defined list of zones.  If given a specific
     * list of zones of interest, then only values from those zones are returned.
     * 
     * @param interestedZones - can be null, should be a subset of all zones
     * 
     * @return EC2DescribeAvailabilityZonesResponse
     */
    private EC2DescribeAvailabilityZonesResponse listZones( String[] interestedZones ) 
        throws IOException, SAXException, ParserConfigurationException, EC2ServiceException, SignatureException 
    {    
    	EC2DescribeAvailabilityZonesResponse zones = new EC2DescribeAvailabilityZonesResponse();
       	Node   parent = null;
       	String id     = null;
       	String name   = null;
       	
       	String   sortedQuery = new String( "available=true&command=listZones" );   // -> used to generate the signature
 		Document cloudResp   = resolveURL(genAPIURL( "command=listZones&available=true", genQuerySignature(sortedQuery)), "listZones", true );
		NodeList match       = cloudResp.getElementsByTagName( "zone" ); 
		int length = match.getLength();
		    
	    for( int i=0; i < length; i++ ) 
	    {
	    	if (null != (parent = match.item(i))) 
	    	{ 
	    		NodeList children = parent.getChildNodes();
	    		int      numChild = children.getLength();
	    		
	    		for( int j=0; j < numChild; j++ ) 
	    		{
	    			Node   child    = children.item(j);
	    			String nodeName = child.getNodeName();
	    			
   			             if (nodeName.equalsIgnoreCase( "id"   )) id   = child.getFirstChild().getNodeValue();
   			        else if (nodeName.equalsIgnoreCase( "name" )) name = child.getFirstChild().getNodeValue();
	    	    }
	    		
	 		    // -> are we asking about specific zones?
	 		    if ( null != interestedZones && 0 < interestedZones.length ) 
	 		    {
	 		     	 for( int j=0; j < interestedZones.length; j++ ) 
	 		     	 {
	 		    	     if (interestedZones[j].equalsIgnoreCase( name )) {
	 		    			 zones.addZone( id, name );
	 		    			 break;
	 		    		 }
	 		    	 }
	 		    }
	 		    else zones.addZone( id, name );
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
        throws IOException, ParserConfigurationException, SAXException, ParseException, EC2ServiceException, SignatureException, 
               InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
    {
       	Node parent = null;
	    StringBuffer params = new StringBuffer();
   	    params.append( "command=listVirtualMachines" );
   	    if (null != instanceId) params.append( "&id=" + instanceId );
   	    String query = params.toString();

       	Document cloudResp = resolveURL(genAPIURL( query, genQuerySignature(query)), "listVirtualMachines", true );
	    NodeList match     = cloudResp.getElementsByTagName( "virtualmachine" ); 
	    int      length    = match.getLength();

	    for( int i=0; i < length; i++ ) 
	    {
	    	if (null != (parent = match.item(i))) 
	    	{ 
	    		NodeList children = parent.getChildNodes();
	    		int      numChild = children.getLength();
    			EC2Instance vm = new EC2Instance();
	    		
	    		for( int j=0; j < numChild; j++ ) 
	    		{
	    		    // -> each template has many child tags
	    			Node   child = children.item(j);
	    			String name  = child.getNodeName();
    			
	    			if (null != child.getFirstChild()) 
	    			{
	    			    String value = child.getFirstChild().getNodeValue();
	    			    //System.out.println( "\n*** vm: " + name + " = " + value );
	    			
   			                 if (name.equalsIgnoreCase( "id"                )) vm.setId( value );
   			            else if (name.equalsIgnoreCase( "name"              )) vm.setName( value );
   			            else if (name.equalsIgnoreCase( "zonename"          )) vm.setZoneName( value );
   			            else if (name.equalsIgnoreCase( "templateid"        )) vm.setTemplateId( value );
   			            else if (name.equalsIgnoreCase( "group"             )) vm.setGroup( value );
   			            else if (name.equalsIgnoreCase( "state"             )) vm.setState( value );
   			            else if (name.equalsIgnoreCase( "created"           )) vm.setCreated( value );
   			            else if (name.equalsIgnoreCase( "ipaddress"         )) vm.setIpAddress( value );
   			            else if (name.equalsIgnoreCase( "account" 	        )) vm.setAccountName(value);
   			            else if (name.equalsIgnoreCase( "domainid" 	        )) vm.setDomainId(value);
   			            else if (name.equalsIgnoreCase( "hypervisor"        )) vm.setHypervisor(value);
   			            else if (name.equalsIgnoreCase( "rootdevicetype"    )) vm.setRootDeviceType(value);
   			            else if (name.equalsIgnoreCase( "rootdeviceid"      )) vm.setRootDeviceId( Integer.parseInt( value ));  			                 
   			            else if (name.equalsIgnoreCase( "serviceofferingid" )) vm.setServiceOffering( serviceOfferingIdToInstanceType( value ));
   			            else if (name.equalsIgnoreCase( "nic"               )) vm = getPrivateAddress( vm, child.getChildNodes());
	    			}
	    	    }
    			instances.addInstance( vm );
	    	}
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
    private EC2DescribeImagesResponse listTemplates( String templateId, EC2DescribeImagesResponse images ) 
        throws IOException, ParserConfigurationException, SAXException, EC2ServiceException, SignatureException 
    {
    	Node parent = null;
	    StringBuffer params = new StringBuffer();
   	    params.append( "command=listTemplates" );
   	    if (null != templateId) params.append( "&id=" + templateId );
   	    params.append( "&templateFilter=executable" );   // -> a required parameter
   	    String query = params.toString();

       	Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "listTemplates", true );
	    NodeList match     = cloudResp.getElementsByTagName( "template" ); 
	    int      length    = match.getLength();

	    for( int i=0; i < length; i++ ) 
	    {	   
	    	if (null != (parent = match.item(i))) 
	    	{ 
	    		NodeList children = parent.getChildNodes();
	    		int      numChild = children.getLength();
    			EC2Image template = new EC2Image();
	    		
	    		for( int j=0; j < numChild; j++ ) 
	    		{
	    		    // -> each template has many child tags
	    			Node   child = children.item(j);
	    			String name  =  child.getNodeName();
	    			
	    			if (null != child.getFirstChild()) 
	    			{
	    			    String value = child.getFirstChild().getNodeValue();
	    			    
	    			         if (name.equalsIgnoreCase( "id"         )) template.setId( value );
	    			    else if (name.equalsIgnoreCase( "name"       )) template.setName( value );
	    			    else if (name.equalsIgnoreCase( "displaytext")) template.setDescription( value );
	    			    else if (name.equalsIgnoreCase( "ostypeid"   )) template.setOsTypeId( value );
	    			    else if (name.equalsIgnoreCase( "ispublic"   )) template.setIsPublic( value.equalsIgnoreCase( "true" ));
	    			    else if (name.equalsIgnoreCase( "isready"    )) template.setIsReady( value.equalsIgnoreCase( "true" ));
	    			    else if (name.equalsIgnoreCase( "account"    )) template.setAccountName( value );
	    			    else if (name.equalsIgnoreCase( "domainid"   )) template.setDomainId(value);
	    			}
	    	    }
    			images.addImage( template );
	    	}
	    }
	    return images;
    }

    /**
     * Return information on all defined disk offerings.
     */
    private DiskOfferings listDiskOfferings() 
        throws IOException, ParserConfigurationException, SAXException, ParseException, EC2ServiceException, SignatureException 
   {
   	    DiskOfferings offerings = new DiskOfferings();
    	Node parent  = null;
	    String query = new String( "command=listDiskOfferings" );
   	    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "listDiskOfferings", true );
        NodeList match     = cloudResp.getElementsByTagName( "diskoffering" ); 
        int      length    = match.getLength();

        for( int i=0; i < length; i++ ) 
        {
    	    if (null != (parent = match.item(i))) 
    	    { 
    		    NodeList children = parent.getChildNodes();
    		    int      numChild = children.getLength();
			    DiskOffer df = new DiskOffer();
    		
    		    for( int j=0; j < numChild; j++ ) 
    		    {
    			    Node   child = children.item(j);
    			    String name  = child.getNodeName();
			
    			    if (null != child.getFirstChild()) 
    			    {
    			        String value = child.getFirstChild().getNodeValue();
      			
			                 if (name.equalsIgnoreCase( "id"           )) df.setId( value );
			            else if (name.equalsIgnoreCase( "name"         )) df.setName( value );
			            else if (name.equalsIgnoreCase( "disksize"     )) df.setSize( value );
			            else if (name.equalsIgnoreCase( "created"      )) df.setCreated( value );
			            else if (name.equalsIgnoreCase( "iscustomized" )) df.setIsCustomized( value.equalsIgnoreCase( "true" ));
    			    }
    	        }
			    offerings.addOffer( df );
    	    }
    	}
        return offerings;
    }

    /**
     * Return information on all defined service offerings.
     */
    private ServiceOfferings listServiceOfferings() 
        throws IOException, ParserConfigurationException, SAXException, ParseException, EC2ServiceException, SignatureException 
    {
	    ServiceOfferings offerings = new ServiceOfferings();
	    Node parent = null;
        String query = new String( "command=listServiceOfferings" );
	    Document cloudResp = resolveURL(genAPIURL(query, genQuerySignature(query)), "listServiceOfferings", true );
        NodeList match     = cloudResp.getElementsByTagName( "serviceoffering" ); 
        int      length    = match.getLength();

        for( int i=0; i < length; i++ ) 
        {
	        if (null != (parent = match.item(i))) 
	        { 
		        NodeList children = parent.getChildNodes();
		        int      numChild = children.getLength();
		        ServiceOffer sf = new ServiceOffer();
		
		        for( int j=0; j < numChild; j++ ) 
		        {
			         Node   child = children.item(j);
	 		         String name  = child.getNodeName();
		
			         if (null != child.getFirstChild()) 
			         {
			             String value = child.getFirstChild().getNodeValue();
		
		                      if (name.equalsIgnoreCase( "id"        )) sf.setId( value );
		                 else if (name.equalsIgnoreCase( "name"      )) sf.setName( value );
		                 else if (name.equalsIgnoreCase( "memory"    )) sf.setMemory( value );
		                 else if (name.equalsIgnoreCase( "cpunumber" )) sf.setCPUNumber( value );
		                 else if (name.equalsIgnoreCase( "cpuspeed"  )) sf.setCPUSpeed( value );
		                 else if (name.equalsIgnoreCase( "created"   )) sf.setCreated( value );
			         }
	            }
		        offerings.addOffer( sf );
	        }
	    }
        return offerings;
    }

    public EC2DescribeSecurityGroupsResponse listSecurityGroups( String[] interestedGroups ) 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException, ParseException 
    {
    	EC2DescribeSecurityGroupsResponse groupSet = new EC2DescribeSecurityGroupsResponse();
    	Node parent = null; 	
	    Document cloudResp = resolveURL(genAPIURL( "command=listSecurityGroups", genQuerySignature("command=listSecurityGroups")), "listSecurityGroups", true );
        NodeList match = cloudResp.getElementsByTagName( "securitygroup" ); 
	    int     length = match.getLength();
	       
	    for( int i=0; i < length; i++ ) 
	    {	   
	   	    if (null != (parent = match.item(i))) 
	   	    { 
	    		NodeList children = parent.getChildNodes();
	    		int      numChild = children.getLength();
    			EC2SecurityGroup group  = new EC2SecurityGroup();
	    		
	    		for( int j=0; j < numChild; j++ ) 
	    		{
	    		     Node   child = children.item(j);
	    			 String name  =  child.getNodeName();
	    			
	    			 if (null != child.getFirstChild()) 
	    			 {
	    			     String value = child.getFirstChild().getNodeValue();
	    			     //System.out.println( "listSecurityGroups " + name + "=" + value );
	    			     
   			                  if (name.equalsIgnoreCase( "id"          )) group.setId( value );
   			             else if (name.equalsIgnoreCase( "name"        )) group.setName( value );
	    			     else if (name.equalsIgnoreCase( "description" )) group.setDescription( value );
	    			     else if (name.equalsIgnoreCase( "account"     )) group.setAccount( value );
	    			     else if (name.equalsIgnoreCase( "domainid"    )) group.setDomainId( value );  			                  
	    			     else if (name.equalsIgnoreCase( "ingressrule" )) group.addIpPermission( toPermission( child.getChildNodes()));
	    			 } 
	    	    }
	 		    // -> are we asking about specific security groups?
	 		    if ( null != interestedGroups && 0 < interestedGroups.length ) 
	 		    {
	 		     	 for( int j=0; j < interestedGroups.length; j++ ) 
	 		     	 {
	 		    	     if (interestedGroups[j].equalsIgnoreCase( group.getName())) {
	 		    	    	 groupSet.addGroup( group );
	 		    			 break;
	 		    		 }
	 		    	 }
	 		    }
	 		    else groupSet.addGroup( group );
	    	}
	    }
	    return groupSet;
    }
    
    
    /**
     * Temporary hack to just get a valid VLANID
     */
    private String hackGetVlanId() 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException, ParseException 
    {
	    Node parent = null; 	
        Document cloudResp = resolveURL(genAPIURL("command=listVlanIpRanges&vlantype=DirectAttached", genQuerySignature( "command=listVlanIpRanges&vlantype=DirectAttached")), "listVlanIpRanges", true );
        NodeList match = cloudResp.getElementsByTagName( "vlaniprange" ); 
        int     length = match.getLength();
       
        for( int i=0; i < length; i++ ) 
        {	   
   	       if (null != (parent = match.item(i))) 
   	       { 
    		   NodeList children = parent.getChildNodes();
    		   int      numChild = children.getLength();
    		
    		   for( int j=0; j < numChild; j++ ) 
    		   {
    		       Node   child = children.item(j);
    			   String name  =  child.getNodeName();
    			
    			   if (null != child.getFirstChild()) 
    			   {
    			       String value = child.getFirstChild().getNodeValue();
    			       System.out.println( "listVlanIpRanges " + name + "=" + value );   			     
    			       if (name.equalsIgnoreCase( "id" )) return value;
    			   } 
    	       }
   	       }
    	}
        return null;
    }

    
    /**
     * 'nic' is a subobject of the listVirtualMachines
     * 
     * @param nic
     */
    private EC2Instance getPrivateAddress( EC2Instance vm, NodeList nic )
    {
    	int numChild = nic.getLength();

    	for( int i=0; i < numChild; i++ ) 
    	{
    		 Node   child = nic.item(i);
    		 String name  = child.getNodeName();
    			
    		 if (null != child.getFirstChild()) 
    		 {
    		     String value = child.getFirstChild().getNodeValue();
    		     //System.out.println( "nic: " + name + "=" + value );
    		     
		         if (name.equalsIgnoreCase( "ipaddress" )) vm.setPrivateIpAddress( value ); 
    	     }
    	}
		return vm;  	
    }
    
    
    /**
     * Ingress Rules are one more level down the XML tree.
     */
    private EC2IpPermission toPermission( NodeList rule ) 
    {
    	int numChild = rule.getLength();
    	EC2IpPermission perm = new EC2IpPermission();
    	String account   = null;
    	String groupName = null;

    	for( int i=0; i < numChild; i++ ) 
    	{
    		 Node   child = rule.item(i);
    		 String name  = child.getNodeName();
    			
    		 if (null != child.getFirstChild()) 
    		 {
    		     String value = child.getFirstChild().getNodeValue();
    		     //System.out.println( "ingress rule: " + name + "=" + value );
    		     
		              if (name.equalsIgnoreCase( "protocol"          )) perm.setProtocol( value ); 
		         else if (name.equalsIgnoreCase( "startport"         )) perm.setFromPort( Integer.parseInt( value ));
		         else if (name.equalsIgnoreCase( "endport"           )) perm.setToPort( Integer.parseInt( value ));
		         else if (name.equalsIgnoreCase( "account"           )) account = value;
		         else if (name.equalsIgnoreCase( "securitygroupname" )) groupName = value ;
		         else if (name.equalsIgnoreCase( "cidr"              )) {
		        	 perm.addIpRange( value );
		        	 perm.setCIDR( value );
		         }
		              
		         if (null != account && null != groupName) 
		         {
		        	 EC2SecurityGroup group = new EC2SecurityGroup();
		        	 group.setAccount( account );
		        	 group.setName( groupName );
		        	 account   = null;
		        	 groupName = null;
		        	 perm.addUser( group );
		         }
    	     }
    	}
		return perm;
    }
    
    /**
     * If given a specific list of snapshots of interest, then only values from those snapshots are returned.
     * 
     * @param interestedShots - can be null, should be a subset of all snapshots
     */
    private EC2DescribeSnapshotsResponse listSnapshots( String[] interestedShots ) 
        throws EC2ServiceException, UnsupportedEncodingException, SignatureException, IOException, SAXException, ParserConfigurationException, ParseException 
    {
    	EC2DescribeSnapshotsResponse snapshots = new EC2DescribeSnapshotsResponse();
    	Node parent = null; 	
	    Document cloudResp = resolveURL(genAPIURL("command=listSnapshots", genQuerySignature("command=listSnapshots")), "listSnapshots", true );
        NodeList match = cloudResp.getElementsByTagName( "snapshot" ); 
	    int     length = match.getLength();
	       
	    for( int i=0; i < length; i++ ) 
	    {
	   	    if (null != (parent = match.item(i))) 
	   	    { 
	    		NodeList children = parent.getChildNodes();
	    		int      numChild = children.getLength();
    			EC2Snapshot shot  = new EC2Snapshot();
	    		
	    		for( int j=0; j < numChild; j++ ) 
	    		{
	    		     Node   child = children.item(j);
	    			 String name  =  child.getNodeName();
	    			
	    			 if (null != child.getFirstChild()) 
	    			 {
	    			     String value = child.getFirstChild().getNodeValue();
	    			
	    			          if (name.equalsIgnoreCase( "id"           )) shot.setId( value );
	    			     else if (name.equalsIgnoreCase( "name"         )) shot.setName( value );
	    			     else if (name.equalsIgnoreCase( "volumeid"     )) shot.setVolumeId( value );
	    			     else if (name.equalsIgnoreCase( "snapshottype" )) shot.setType( value );
	    			     else if (name.equalsIgnoreCase( "created"      )) shot.setCreated( value );
	    			     else if (name.equalsIgnoreCase( "account"      )) shot.setAccountName(value);
	    			     else if (name.equalsIgnoreCase( "domainId"     )) shot.setDomainId(value);
	    			 } 
	    	    }
	 		    // -> are we asking about specific snapshots?
	 		    if ( null != interestedShots && 0 < interestedShots.length ) 
	 		    {
	 		     	 for( int j=0; j < interestedShots.length; j++ ) 
	 		     	 {
	 		    	     if (interestedShots[j].equalsIgnoreCase( shot.getId())) {
	 		    	    	 snapshots.addSnapshot( shot );
	 		    			 break;
	 		    		 }
	 		    	 }
	 		    }
	 		    else snapshots.addSnapshot( shot );
	    	}
	    }
	    return snapshots;
    }

    public Account getAccount() {
    	String query = "command=listAccounts";
    	Document response = null;
    	Account account = new Account();

    	try {
    		response = resolveURL(genAPIURL(query, genQuerySignature(query)), "listAccounts", true);
		} catch (Exception e) {
			logger.error( "List Accounts - " + e.toString());
			throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
		}

		NodeList match = response.getElementsByTagName("id");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) account.setId(match.item(0).getFirstChild().getNodeValue());
		
		match = response.getElementsByTagName("name");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) account.setAccountName(match.item(0).getFirstChild().getNodeValue());
		
		match = response.getElementsByTagName("domainid");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) account.setDomainId(match.item(0).getFirstChild().getNodeValue());
		
		match = response.getElementsByTagName("domain");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) account.setDomainName(match.item(0).getFirstChild().getNodeValue());

		return account;
    }
    
    /**
     * Check for three network types in succession until one is found.
     * Creates a new one if no network is found.
     * 
     * @return A network id suitable for use.
     */
    private String getNetworkId(String zoneId) {
    	String networkId = null;
    	
    	if (networkId==null) networkId = getNetworkId("direct", false);
    	if (networkId==null) networkId = getNetworkId("direct", true);
    	if (networkId==null) networkId = getNetworkId("virtual", false);
    	if (networkId==null) networkId = createNetwork(zoneId);
    	
    	return networkId;
    }
    
    private String getNetworkId(String networkType, boolean shared) {
    	String query = "command=listNetworks";
    	Document response = null;
    	
    	// Only need to specify account and domainid when admin
    	//if (!shared) {
        //	Account account = getAccount();
    	//	query = query.concat("&account=").concat(account.getAccountName());
    	//	query = query.concat("&domainid=").concat(account.getDomainId());
    	//} 
    		
    	query = query.concat("&isdefault=").concat("true");
    	if (shared) query = query.concat("&isshared=").concat("true");
    	query = query.concat("&type=").concat(networkType);
    	
    	try {
    		response = resolveURL(genAPIURL(query, genQuerySignature(query)), "listNetworks", true);
    	} catch (Exception e) {
    		logger.error( "List Networks - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    	}
    	
    	// Return the first network id found.
    	NodeList parent = response.getElementsByTagName("network");
    	if (parent.getLength() > 0 && parent.item(0).hasChildNodes()) {
    		NodeList children = parent.item(0).getChildNodes();
    		for (int i = 0; i < children.getLength(); i++) {
    			if (children.item(0).getNodeName().equals("id")) {
    				return children.item(0).getTextContent();
    			}
    		}
    	}
    	
    	return null;
    }
    
    private String createNetwork(String zoneId) {
    	String query = "command=createNetwork";
    	Document response = null;
    	//Account account = getAccount();
    	Tuple<String, String> networkOffering = getNetworkOffering();
    	
    	try {
    		//query = query.concat("&account=").concat(account.getAccountName());
    		query = query.concat("&displaytext=").concat(safeURLencode(networkOffering.getSecond()));
    		//query = query.concat("&domainid=").concat(account.getDomainId());
    		query = query.concat("&name=").concat(safeURLencode("EC2 created network"));
    		query = query.concat("&networkofferingid=").concat(networkOffering.getFirst());
    		query = query.concat("&zoneid=").concat(zoneId);
		
    		response = resolveURL(genAPIURL(query, genQuerySignature(query)), "createNetwork", true);
    	} catch (Exception e) {
    		logger.error("Create Network - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    	}
		
		NodeList match = response.getElementsByTagName("id");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) 
			return match.item(0).getFirstChild().getNodeValue();
		
		logger.error("Create Network - Unable to create a new network");
		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    }
    
    private Tuple<String, String> getNetworkOffering() {
    	String query = "command=listNetworkOfferings";
    	Document response = null;
    	
    	query = query.concat("&isdefault=true");
    	query = query.concat("&traffictype=guest");
    	
    	try {
    		response = resolveURL(genAPIURL(query, genQuerySignature(query)), "listNetworkOfferings", true);
    	} catch (Exception e) {
    		logger.error("List Network Offerings - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    	}
    	
    	Tuple<String, String> offering = new Tuple<String, String>("", ""); 
		NodeList match = response.getElementsByTagName("id");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) { 
			offering.setFirst(match.item(0).getFirstChild().getNodeValue());
		} else {
    		logger.error("List Network Offerings - No suitable network offering found");
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
		}
		match = response.getElementsByTagName("displaytext");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) {
			offering.setSecond(match.item(0).getFirstChild().getNodeValue());
		}
    	
    	return offering;
    }
    
    private String getDCNetworkType(String zoneId) {
    	String query = "command=listZones";
    	Document response = null;

    	query = query.concat("&id=").concat(zoneId);
    	
      	try {
    		response = resolveURL(genAPIURL(query, genQuerySignature(query)), "listZones", true);
    	} catch (Exception e) {
    		logger.error("List Zones - " + e.toString());
    		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    	}
    	
		NodeList match = response.getElementsByTagName("networktype");
		if (match.getLength() > 0 && match.item(0).hasChildNodes()) {
			return match.item(0).getFirstChild().getNodeValue();
		}

		logger.error("List Zones - Unable to determine zone network type");
		throw new EC2ServiceException(ServerError.InternalError, "An unexpected error occurred");
    }
       
    /**
     * Ensures that the CloudStack back-end is at least of version @param csVersion. 
     * Throws a Server.InternalError {@link EC2ServiceException} if it's not.  
     */
    private void ensureVersion(int csVersion) {
    	if (cloudStackVersion < csVersion) 
    		throw new EC2ServiceException(ServerError.InternalError, "CloudStack version " 
    				+ String.valueOf((double)csVersion/100) + " or higher is needed for this command.");
    }
    
    /**
     * Has the job finished?
     * 
     * @param jobId
     * @return A string with one or two values, "s:status r:result" if both present.
     *         "s:-1" means no result returned from CloudStack so request has failed.
     */
	private String checkAsyncResult(String jobId) throws EC2ServiceException,
		InternalErrorException, SignatureException {
		StringBuffer checkResult = new StringBuffer();
		NodeList match = null;
		Node item = null;
		String status = null;
		String result = null;
		
		try {
			String query = "command=queryAsyncJobResult&jobId=" + jobId;
			Document cloudResp = resolveURL(
					genAPIURL(query, genQuerySignature(query)),
					"queryAsyncJobResult", true);
			if (null == cloudResp)
				return new String("s:-1");
		
			match = cloudResp.getElementsByTagName("jobstatus");
			if (0 < match.getLength()) {
				item = match.item(0);
				status = new String(item.getFirstChild().getNodeValue());
			}
			match = cloudResp.getElementsByTagName("jobresult");
			if (0 < match.getLength()) {
				item = match.item(0).getLastChild();
				result = new String(item.getFirstChild().getNodeValue());
			}
		
			checkResult.append(" ");
			if (null != status)
				checkResult.append("s:").append(status);
			if (null != result)
				checkResult.append(" r:").append(result);
			return checkResult.toString();
		} catch (EC2ServiceException error) {
			logger.error("EC2 checkAsyncResult 1 - ", error);
			throw error;
		} catch (Exception e) {
			logger.error("EC2 checkAsyncResult 2 - ", e);
			throw new EC2ServiceException(ServerError.InternalError,
					"An unexpected error occurred.");
		}
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

    
    Map[] execList(String query, String... args) throws IOException, SignatureException {
        Map r = execute(query, args);
        if (r.isEmpty())
            return new Map[0];

        List l = (List) unwrap(r);
        return (Map[]) l.toArray(new Map[0]);
    }

    Map execute(String query, String... args) throws IOException, SignatureException {
        for (int i = 0; i < args.length; i++) {
            args[i] = safeURLencode(args[i]);
        }
        return execute(String.format(query, args));
    }

    Map execute(String query) throws IOException, SignatureException {
        query += "&response=json&apiKey=" + safeURLencode(UserContext.current().getAccessKey());
        String sig = calculateSignature(query);
        String url = getServerURL() + query + "&signature=" + safeURLencode(sig);
        InputStream in = openURL(url, "Unknown", true);
        Map jo = (Map) JSONValue.parse(new InputStreamReader(in));
        return (Map) unwrap(jo);
    }

    String calculateSignature(String query) throws SignatureException {
        String[] params = query.split("&");
        Arrays.sort(params);
        StringBuilder b = new StringBuilder(params[0]);
        for (int i = 1; i < params.length; i++) {
            b.append("&" + params[i]);
        }
        return calculateRFC2104HMAC(b.toString().toLowerCase(), UserContext.current().getSecretKey());
    }

    Object unwrap(Map m) {
        return m.values().iterator().next();
    }

    private String genAPIURL( String query, String signature) throws UnsupportedEncodingException 
    {
		UserContext ctx = UserContext.current();
		if ( null != ctx ) {
       	     StringBuffer apiCommand = new StringBuffer();
             apiCommand.append( getServerURL());
             apiCommand.append( query );
             apiCommand.append( "&apikey=" ).append( safeURLencode( ctx.getAccessKey()));
             apiCommand.append( "&signature=" ).append( safeURLencode( signature ));
             return apiCommand.toString();
		}
		else return null;
    }
    
    /**
     * Calculated the signature that is required for the Developer API.
     * 
     * @param sortedCommand - must be in sorted order, and URL encoded.
     * 
     * @return a hash of the lower-cased query string using the HMAC SHA-1 algorithm with the secret key
     */
    private String genQuerySignature( String sortedCommand ) throws UnsupportedEncodingException, SignatureException 
    {
    	StringBuffer sigOver = new StringBuffer();
    	sigOver.append( "apikey=" ).append( safeURLencode( UserContext.current().getAccessKey() ));
    	sigOver.append( "&" ).append( sortedCommand );
    	return calculateRFC2104HMAC( sigOver.toString().toLowerCase(), UserContext.current().getSecretKey());
    }
    
    /**
     * Create a signature by the following method:
     *     new String( Base64( SHA1( key, byte array )))
     * 
     * @param signIt    - the data to generate a keyed HMAC over
     * @param secretKey - the user's unique key for the HMAC operation
     * 
     * @return String   - the recalculated string
     */
    private String calculateRFC2104HMAC( String signIt, String secretKey ) throws SignatureException 
    {
   	    String result = null;
   	    try { 	 
   	    	SecretKeySpec key = new SecretKeySpec( secretKey.getBytes(), "HmacSHA1" );
   	        Mac hmacSha1 = Mac.getInstance( "HmacSHA1" );
   	        hmacSha1.init( key ); 
            byte [] rawHmac = hmacSha1.doFinal( signIt.getBytes());
            result = new String( Base64.encodeBase64( rawHmac ));
   	    }
   	    catch( Exception e ) {
   		    throw new SignatureException( "Failed to generate keyed HMAC on soap request: " + e.getMessage());
   	    }
   	    return result.trim();
    }

    /**
     * Every function in the engine is going to need to resolve a cloud API URL into an XML document.
     * 
     * @param uri - a complete cloud client API uri
     * @returns a parsed XML document from the cloud API server. 
     */
	private Document resolveURL( String uri, String command, boolean logRequest ) 
	    throws IOException, SAXException, ParserConfigurationException, EC2ServiceException  
	{	
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(openURL(uri, command, logRequest));
    }

    private InputStream openURL(String uri, String command, boolean logRequest)
        throws IOException, EC2ServiceException  
    {
		if (logRequest) logger.debug( "Cloud API call + [" + uri + "]" );
		String errorMsg = null;
        URL cloudapi = new URL( uri );
        HttpURLConnection connect = (HttpURLConnection) cloudapi.openConnection();
        Integer code = new Integer( connect.getResponseCode());
        if (400 <= code.intValue()) 
        {
        	if ( null != (errorMsg = connect.getResponseMessage()))
        		 throw new EC2ServiceException(ServerError.InternalError, code.toString() + " " + errorMsg);
        	else if ( null != (errorMsg = connect.getHeaderField("X-Description")))
        		 throw new EC2ServiceException(ServerError.InternalError, code.toString() + " " + errorMsg);
        	else throw new EC2ServiceException(ServerError.InternalError, command + " cloud API HTTP Error: " + code.toString());
        }
        return connect.getInputStream();
    }
	
	/**
	 * Normalized URL encoding uses "%20" for spaces instead of "+" signs.
	 */
	private String safeURLencode( String param ) throws UnsupportedEncodingException 
	{
	    return URLEncoder.encode( param, "UTF-8" ).replaceAll( "\\+", "%20" );
	}
	
	private String getServerURL() 
	{	
		if ( null == cloudAPIPort ) 
			 return new String( "http://" + managementServer + "/client/api?" );
		else return new String( "http://" + managementServer + ":" + cloudAPIPort + "/client/api?" );
		
	}
}
