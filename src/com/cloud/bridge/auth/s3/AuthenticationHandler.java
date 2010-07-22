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
package com.cloud.bridge.auth.s3;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPBody;
import org.apache.log4j.Logger;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.AxisFault;
import org.apache.axis2.description.HandlerDescription; 
import org.apache.axis2.description.Parameter;
import org.apache.commons.codec.binary.Base64;

import com.cloud.bridge.model.UserCredentials;
import com.cloud.bridge.persist.dao.UserCredentialsDao;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;
import java.sql.SQLException;


public class AuthenticationHandler implements Handler {
     protected final static Logger logger = Logger.getLogger(AuthenticationHandler.class);
     
	 protected HandlerDescription handlerDesc = new HandlerDescription( "default handler" );
     private String name = "S3AuthenticationHandler";
    
     public void init( HandlerDescription handlerdesc ) 
     {
   	  	 this.handlerDesc = handlerdesc;
     }
    	  	
     public String getName() 
     {
    	 //logger.debug( "getName entry S3AuthenticationHandler" + name );
         return name;
     }

     public String toString() 
     {
    	 return (name != null) ? name.toString() : null;
     }
     
     public HandlerDescription getHandlerDesc() 
     {
       	 return handlerDesc;
     }
    	
     public Parameter getParameter( String name ) 
     {
    	 return handlerDesc.getParameter( name );
     } 
     
     
     /**
      * Verify the request's authentication signature by extracting all the 
      * necessary parts of the request, obtaining the requestor's secret key, and
      * recalculating the signature.
      * 
      * On Signature mismatch raise an AxisFault (i.e., a SoapFault) with what Amazon S3 
      * defines as a "Client.SignatureMismatch" error.
      * 
      * Special case: need to deal with anonymous requests where no AWSAccessKeyId is
      * given.   In this case just pass the request on.
      */
     public InvocationResponse invoke(MessageContext msgContext) throws AxisFault 
     {
    	 String accessKey  = null;
    	 String operation  = null;
    	 String msgSig     = null;
    	 String calSig     = null;
    	 String signString = null;
    	 String timestamp  = null;
    	 String secretKey  = null;
    	 String temp       = null;
    	 
    	 // [A] Try to recalculate the signature for non-anonymous requests
    	 try
    	 {  SOAPEnvelope soapEnvelope = msgContext.getEnvelope();
            SOAPBody     soapBody     = soapEnvelope.getBody();
            String       xmlBody      = soapBody.toString();
            logger.debug( "xmlrequest: " + xmlBody );
         
            // -> if it is anonymous request, then no access key should exist?
            int start = xmlBody.indexOf( "AWSAccessKeyId>" );
            if (-1 == start) return InvocationResponse.CONTINUE; 
            temp = xmlBody.substring( start+15 );
            int end   = temp.indexOf( "</" );
            accessKey = temp.substring( 0, end );
            logger.debug( "accesskey " + accessKey );
            
            // -> what if we cannot find the user's key?
            if (null != (secretKey = lookupSecretKey( accessKey )))
            {
                // -> if any other field is missing, then the signature will not match
                if ( null != (operation = soapBody.getFirstElementLocalName()))
                     operation = operation.trim();
                else operation = "";
                logger.debug( "operation " + operation );
 
                start = xmlBody.indexOf( "Timestamp>" );
                if ( -1 < start )
                {
                    temp = xmlBody.substring( start+10 );
                    end  = temp.indexOf( "</" );
                    timestamp = temp.substring( 0, end );
                    logger.debug( "timestamp " + timestamp );
                }
                else timestamp = "";
        
                start  = xmlBody.indexOf( "Signature>" );
                if ( -1 < start )
                {
                    temp = xmlBody.substring( start+10 );
                    end  = temp.indexOf( "</" );
                    msgSig = temp.substring( 0, end );
                    logger.debug( "signature " + msgSig );
                }
                else msgSig = "";

                // -> calculate RFC 2104 HMAC-SHA1 digest over the constructed string
                signString = "AmazonS3" + operation + timestamp;
                calSig     = calculateRFC2104HMAC( signString, secretKey );
            }
    	}
    	catch( Exception e )
    	{
    	    logger.error( "Signature calculation failed due to: " + e.toString());
    		throw new AxisFault( e.toString(), "Server.InternalError" );
    	}
    	
    	
    	// [B] Verify that the given signature matches what we calculated here
    	if (null == secretKey)
    	{
    	     logger.error( "Unknown AWSAccessKeyId: [" + accessKey + "]" );
    		 throw new AxisFault( "Unknown AWSAccessKeyId: [" + accessKey + "]", "Client.InvalidAccessKeyId" );
    	}
    	
    	// -> signature calculated properly but failed to match?
     	logger.debug( "Signature test, [" + msgSig + "] [" + calSig + "] over [" + signString + "]" );
        if ( !msgSig.equals( calSig ))
        {
     	     logger.error( "Signature mismatch, [" + msgSig + "] [" + calSig + "] over [" + signString + "]" );
    	 	 throw new AxisFault( "Authentication signature mismatch on AccessKey: [" + accessKey + "] [" + operation + "]",
                                  "Client.SignatureDoesNotMatch" );
        } 
        else return InvocationResponse.CONTINUE;
     }

     
     public void revoke(MessageContext msgContext) 
     {
         logger.info(msgContext.getEnvelope().toString());
     }

     public void setName(String name) 
     {
    	 //logger.debug( "setName entry S3AuthenticationHandler " + name );
         this.name = name;
     }
     
     /**
      * Create a signature by the following method:
      *     new String( Base64( SHA1( key, byte array )))
      * 
      * @param signIt    - the data to generate a keyed HMAC over
      * @param secretKey - the user's unique key for the HMAC operation
      * @return String   - the recalculated string
      * @throws SignatureException
      */
     private String calculateRFC2104HMAC( String signIt, String secretKey )
         throws SignatureException
     {
    	 String result = null;
    	 try
    	 { 	 SecretKeySpec key = new SecretKeySpec( secretKey.getBytes(), "HmacSHA1" );
    	     Mac hmacSha1 = Mac.getInstance( "HmacSHA1" );
    	     hmacSha1.init( key ); 
             byte [] rawHmac = hmacSha1.doFinal( signIt.getBytes());
             result = new String( Base64.encodeBase64( rawHmac ));
    	 }
    	 catch( Exception e )
    	 {
    		 throw new SignatureException( "Failed to generate keyed HMAC on soap request: " + e.getMessage());
    	 }
    	 return result.trim();
     }
     
     /**
      * TODO: Given the user's access key, then obtain his secret key in the user database.
      * For now this is just a stub to be implemented later.
      * 
      * @param accessKey - a unique string allocated for each registered user
      * @return the secret key or null of no matching user found
      */
     private String lookupSecretKey( String accessKey )
	    throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
     {
 	    UserCredentialsDao credentialDao = new UserCredentialsDao();
	    UserCredentials cloudKeys = credentialDao.getByAccessKey( accessKey ); 
	    if ( null == cloudKeys ) {
	    	 logger.debug( accessKey + " is not defined in the S3 service - call SetUserKeys" );
	         return null; 
	    }
		else return cloudKeys.getSecretKey(); 
     }

	 @Override
	 public void cleanup() 
	 {
      	//logger.debug( "cleanup entry S3AuthenticationHandler " );
	 }

	 @Override
	 public void flowComplete( MessageContext arg0 ) 
	 {
     	//logger.debug( "flowComplete entry S3AuthenticationHandler " );
	 }
}