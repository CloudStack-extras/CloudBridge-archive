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
package com.cloud.bridge.auth.ec2;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.log4j.Logger;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.AxisFault;
import org.apache.axis2.description.HandlerDescription; 
import org.apache.axis2.description.Parameter;
import org.apache.commons.codec.binary.Base64;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.cloud.bridge.model.UserCredentials;
import com.cloud.bridge.persist.dao.UserCredentialsDao;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.util.AuthenticationUtils;


public class AuthenticationHandler implements Handler {
     protected final static Logger logger = Logger.getLogger(AuthenticationHandler.class);
     
     private DocumentBuilderFactory dbf = null;

	 protected HandlerDescription handlerDesc = new HandlerDescription( "EC2AuthenticationHandler" );
     private String name = "EC2AuthenticationHandler";
    
     public void init( HandlerDescription handlerdesc ) 
     {
 		 dbf = DocumentBuilderFactory.newInstance();
		 dbf.setNamespaceAware( true );

   	  	 this.handlerDesc = handlerdesc;
     }
    	  	
     public String getName() 
     {
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
      * For EC2 SOAP calls this function's goal is to extract the X509 certificate that is
      * part of the WS-Security wrapped SOAP request.   We need the cert in order to 
      * map it to the user's Cloud API key and Cloud Secret Key.
      */
     public InvocationResponse invoke(MessageContext msgContext) throws AxisFault 
     {
    	 // -> the certificate we want is embedded into the soap header
    	 try
    	 {  SOAPEnvelope soapEnvelope = msgContext.getEnvelope();
            String       xmlHeader    = soapEnvelope.toString();
            //System.out.println( "entire request: " + xmlHeader );
    
    		InputStream is = new ByteArrayInputStream( xmlHeader.getBytes("UTF-8"));
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		Document request = db.parse( is );   		
    		NodeList certs   = request.getElementsByTagNameNS( "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken" ); 
	        if (0 < certs.getLength()) {
	    	    Node item = certs.item(0);
	    	    String result = new String( item.getFirstChild().getNodeValue());
	    	    byte[] certBytes = Base64.decodeBase64( result.getBytes()); 

	    	    Certificate userCert  = null;
	    	    CertificateFactory cf = CertificateFactory.getInstance( "X.509" );
	    	    ByteArrayInputStream bs = new ByteArrayInputStream( certBytes );
	    	    while (bs.available() > 0) userCert = cf.generateCertificate(bs);
                //System.out.println( "cert: " + userCert.toString());              
                String uniqueId = AuthenticationUtils.X509CertUniqueId( userCert );
                logger.debug( "X509 cert's uniqueId: " + uniqueId );
                
                // -> find the Cloud API key and the secret key from the cert's uniqueId 
	     	     UserCredentialsDao credentialDao = new UserCredentialsDao();
	     	     UserCredentials cloudKeys = credentialDao.getByCertUniqueId( uniqueId );
	     	     if ( null == cloudKeys ) {
	        	      logger.error( "Cert does not map to Cloud API keys: " + uniqueId );
	        		  throw new AxisFault( "User not properly registered: certificate does not map to Cloud API Keys", "Client.Blocked" );
	     	     }
	     	     else UserContext.current().initContext( cloudKeys.getAccessKey(), cloudKeys.getSecretKey(), cloudKeys.getAccessKey(), "SOAP Request" );
System.out.println( "end of cert match: " + UserContext.current().getSecretKey());
	        }
    	}
    	catch( Exception e )
    	{
    	    logger.error( "EC2 Authentication Handler: " + e.toString());
    		throw new AxisFault( e.toString(), "Server.InternalError" );
    	}    	
        return InvocationResponse.CONTINUE;
     }

     
     public void revoke(MessageContext msgContext) 
     {
         logger.info(msgContext.getEnvelope().toString());
     }

     public void setName(String name) 
     {
         this.name = name;
     }
     
 	 @Override
	 public void cleanup() 
	 {
	 }

	 @Override
	 public void flowComplete( MessageContext arg0 ) 
	 {
	 }
}