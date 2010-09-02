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
package com.cloud.bridge.service.controller.s3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.bind.DatatypeConverter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLSerializer;
import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import com.amazon.s3.GetBucketAccessControlPolicyResponse;
import com.amazon.s3.ListAllMyBucketsResponse;
import com.amazon.s3.ListBucketResponse;
import com.cloud.bridge.model.SBucket;
import com.cloud.bridge.persist.dao.SBucketDao;
import com.cloud.bridge.service.S3Constants;
import com.cloud.bridge.service.S3RestServlet;
import com.cloud.bridge.service.S3SoapServiceImpl;
import com.cloud.bridge.service.ServiceProvider;
import com.cloud.bridge.service.ServletAction;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.service.core.s3.S3AccessControlPolicy;
import com.cloud.bridge.service.core.s3.S3CanonicalUser;
import com.cloud.bridge.service.core.s3.S3CreateBucketConfiguration;
import com.cloud.bridge.service.core.s3.S3CreateBucketRequest;
import com.cloud.bridge.service.core.s3.S3CreateBucketResponse;
import com.cloud.bridge.service.core.s3.S3DeleteBucketRequest;
import com.cloud.bridge.service.core.s3.S3GetBucketAccessControlPolicyRequest;
import com.cloud.bridge.service.core.s3.S3ListAllMyBucketsRequest;
import com.cloud.bridge.service.core.s3.S3ListAllMyBucketsResponse;
import com.cloud.bridge.service.core.s3.S3ListBucketObjectEntry;
import com.cloud.bridge.service.core.s3.S3ListBucketRequest;
import com.cloud.bridge.service.core.s3.S3ListBucketResponse;
import com.cloud.bridge.service.core.s3.S3PutObjectRequest;
import com.cloud.bridge.service.core.s3.S3Response;
import com.cloud.bridge.service.core.s3.S3SetBucketAccessControlPolicyRequest;
import com.cloud.bridge.service.exception.InvalidRequestContentException;
import com.cloud.bridge.service.exception.NetworkIOException;
import com.cloud.bridge.service.exception.PermissionDeniedException;
import com.cloud.bridge.service.exception.UnsupportedException;
import com.cloud.bridge.util.Converter;
import com.cloud.bridge.util.StringHelper;
import com.cloud.bridge.util.XSerializer;
import com.cloud.bridge.util.XSerializerXmlAdapter;

/**
 * @author Kelven Yang
 */
public class S3BucketAction implements ServletAction {
    protected final static Logger logger = Logger.getLogger(S3BucketAction.class);
    
    private DocumentBuilderFactory dbf = null;
	private OMFactory factory = OMAbstractFactory.getOMFactory();
	private XMLOutputFactory xmlOutFactory = XMLOutputFactory.newInstance();
    
	public S3BucketAction() {
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware( true );

	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response) 
	    throws IOException, XMLStreamException {
		String method = request.getMethod(); 
		
		if (method.equalsIgnoreCase("PUT")) 
		{
			String queryString = request.getQueryString();
			if (queryString != null && queryString.length() > 0) 
			{
				if ( queryString.equalsIgnoreCase("acl")) {
					 executePutBucketAcl(request, response);
					 return;
				} 
				else if(queryString.equalsIgnoreCase("versioning")) {
					 executePutBucketVersioning(request, response);
					 return;
				} 
				else if(queryString.equalsIgnoreCase("logging")) {
					 executePutBucketLogging(request, response);
					 return;
				}
			}
			executePutBucket(request, response);
			return;
		} 
		else if(method.equalsIgnoreCase("GET")) 
		{
			String queryString = request.getQueryString();
			if (queryString != null && queryString.length() > 0) 
			{
				if ( queryString.equalsIgnoreCase("acl")) {
					 executeGetBucketAcl(request, response);
					 return;
				} 
				else if(queryString.equalsIgnoreCase("versioning")) {
					 executeGetBucketVersioning(request, response);
					 return;
				} 
				else if(queryString.equalsIgnoreCase("versions")) {
					 executeGetBucketObjectVersions(request, response);
					 return;
				} 
				else if(queryString.equalsIgnoreCase("logging")) {
					 executeGetBucketLogging(request, response);
					 return;
				} 
				else if(queryString.equalsIgnoreCase("location")) {
					 executeGetBucketLocation(request, response);
					 return;
				}
			}
			
			String bucketAtr = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
            if ( bucketAtr.equals( "/" ))
            	 executeGetAllBuckets(request, response);
            else executeGetBucket(request, response);
		} 
		else if (method.equalsIgnoreCase("DELETE")) 
		{
			executeDeleteBucket(request, response);
		} 
		else throw new IllegalArgumentException("Unsupported method in REST request");
	}
	
	
	public void executeGetAllBuckets(HttpServletRequest request, HttpServletResponse response) 
	    throws IOException, XMLStreamException 
	{
		Calendar cal = Calendar.getInstance();
		cal.set( 1970, 1, 1 ); 
		S3ListAllMyBucketsRequest engineRequest = new S3ListAllMyBucketsRequest();
		engineRequest.setAccessKey(UserContext.current().getAccessKey());
		engineRequest.setRequestTimestamp( cal );
		engineRequest.setSignature( "" );

		S3ListAllMyBucketsResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		
		// -> serialize using the apache's Axiom classes
		ListAllMyBucketsResponse allBuckets = S3SoapServiceImpl.toListAllMyBucketsResponse( engineResponse );

		OutputStream os = response.getOutputStream();
		response.setStatus(200);	
	    response.setContentType("text/xml; charset=UTF-8");
		XMLStreamWriter xmlWriter = xmlOutFactory.createXMLStreamWriter( os );
		String documentStart = new String( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
		os.write( documentStart.getBytes());
		MTOMAwareXMLSerializer MTOMWriter = new MTOMAwareXMLSerializer( xmlWriter );
        allBuckets.serialize( new QName( "http://s3.amazonaws.com/doc/2006-03-01/", "ListAllMyBucketsResponse", "ns1" ), factory, MTOMWriter );
        xmlWriter.flush();
        xmlWriter.close();
        os.close();
	}

	public void executeGetBucket(HttpServletRequest request, HttpServletResponse response) 
	    throws IOException, XMLStreamException 
	{
		S3ListBucketRequest engineRequest = new S3ListBucketRequest();
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));
		engineRequest.setDelimiter(request.getParameter("delimiter"));
		engineRequest.setMarker(request.getParameter("marker"));
		engineRequest.setPrefix(request.getParameter("prefix"));
		
		int maxKeys = Converter.toInt(request.getParameter("max-keys"), 1000);
		engineRequest.setMaxKeys(maxKeys);
		S3ListBucketResponse engineResponse = ServiceProvider.getInstance().getS3Engine().listBucketContents( engineRequest, false );
		
		// -> serialize using the apache's Axiom classes
		ListBucketResponse oneBucket = S3SoapServiceImpl.toListBucketResponse( engineResponse );
	
		OutputStream os = response.getOutputStream();
		response.setStatus(200);	
	    response.setContentType("text/xml; charset=UTF-8");
		XMLStreamWriter xmlWriter = xmlOutFactory.createXMLStreamWriter( os );
		String documentStart = new String( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
		os.write( documentStart.getBytes());
		MTOMAwareXMLSerializer MTOMWriter = new MTOMAwareXMLSerializer( xmlWriter );
        oneBucket.serialize( new QName( "http://s3.amazonaws.com/doc/2006-03-01/", "ListBucketResponse", "ns1" ), factory, MTOMWriter );
        xmlWriter.flush();
        xmlWriter.close();
        os.close();
	}
	
	public void executeGetBucketAcl(HttpServletRequest request, HttpServletResponse response) 
	    throws IOException, XMLStreamException 
	{
		S3GetBucketAccessControlPolicyRequest engineRequest = new S3GetBucketAccessControlPolicyRequest();
		Calendar cal = Calendar.getInstance();
		cal.set( 1970, 1, 1 ); 
		engineRequest.setAccessKey(UserContext.current().getAccessKey());
		engineRequest.setRequestTimestamp( cal );
		engineRequest.setSignature( "" );
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));

		S3AccessControlPolicy engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		
		// -> serialize using the apache's Axiom classes
		GetBucketAccessControlPolicyResponse onePolicy = S3SoapServiceImpl.toGetBucketAccessControlPolicyResponse( engineResponse );
	
		OutputStream os = response.getOutputStream();
		response.setStatus(200);	
	    response.setContentType("text/xml; charset=UTF-8");
		XMLStreamWriter xmlWriter = xmlOutFactory.createXMLStreamWriter( os );
		String documentStart = new String( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
		os.write( documentStart.getBytes());
		MTOMAwareXMLSerializer MTOMWriter = new MTOMAwareXMLSerializer( xmlWriter );
        onePolicy.serialize( new QName( "http://s3.amazonaws.com/doc/2006-03-01/", "GetBucketAccessControlPolicyResponse", "ns1" ), factory, MTOMWriter );
        xmlWriter.flush();
        xmlWriter.close();
        os.close();
	}
	
	public void executeGetBucketVersioning(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String bucketName = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String versioningStatus = null;
		
		if (null == bucketName) {
			logger.error( "executeGetBucketVersioning - no bucket name given" );
			response.setStatus( 400 ); 
			return; 
		}
		
		SBucketDao bucketDao = new SBucketDao();
		SBucket sbucket = bucketDao.getByName( bucketName );
		if (sbucket == null) {
			response.setStatus( 404 );
			return;
		}
		
		String client = UserContext.current().getCanonicalUserId();
		if (!client.equals( sbucket.getOwnerCanonicalId()))
		    throw new PermissionDeniedException( "Access Denied - only the owner can read bucket versioning" );


		switch( sbucket.getVersioningStatus()) {
		default:
		case 0: versioningStatus = "";
		case 1: versioningStatus = "Enabled";   
		case 2: versioningStatus = "Suspended"; 
		}

		StringBuffer xml = new StringBuffer();
        xml.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
        xml.append( "<VersioningConfiguration xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">" );
        if (0 < versioningStatus.length()) xml.append( "<Status>" ).append( versioningStatus ).append( "</Status>" );
        xml.append( "</VersioningConfiguration>" );
      
		response.setStatus(200);
	    response.setContentType("text/xml; charset=UTF-8");
    	S3RestServlet.endResponse(response, xml.toString());
	}
	
	public void executeGetBucketObjectVersions(HttpServletRequest request, HttpServletResponse response) throws IOException
	{   
		S3ListBucketRequest engineRequest = new S3ListBucketRequest();
		String keyMarker       = request.getParameter("key-marker");
		String versionIdMarker = request.getParameter("version-id-marker");
		
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));
		engineRequest.setDelimiter(request.getParameter("delimiter"));
		engineRequest.setMarker( keyMarker );  
		engineRequest.setPrefix(request.getParameter("prefix"));
	    // ToDo - version-id-marker, ignored for now
		
		int maxKeys = Converter.toInt(request.getParameter("max-keys"), 1000);
		engineRequest.setMaxKeys(maxKeys);
		S3ListBucketResponse engineResponse = ServiceProvider.getInstance().getS3Engine().listBucketContents( engineRequest, true );
		
		// -> the SOAP version produces different XML
		StringBuffer xml = new StringBuffer();
        xml.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?>" );
        xml.append( "<ListVersionsResult xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">" );
        xml.append( "<Name>" ).append( engineResponse.getBucketName()).append( "</Name>" );
        
        if ( null == keyMarker )
        	 xml.append( "<KeyMarker/>" );
        else xml.append( "<KeyMarker>" ).append( keyMarker ).append( "</KeyMarker" ); 
  
        if ( null == versionIdMarker )
       	     xml.append( "<VersionIdMarker/>" );
        else xml.append( "<VersionIdMarker>" ).append( keyMarker ).append( "</VersionIdMarker" ); 

        xml.append( "<MaxKeys>" ).append( engineResponse.getMaxKeys()).append( "</MaxKeys>" );
        xml.append( "<IsTruncated>" ).append( engineResponse.isTruncated()).append( "</IsTruncated>" );
        
        S3ListBucketObjectEntry[] versions = engineResponse.getContents();
        for( int i=0; null != versions && i < versions.length; i++ )
        {
        	 S3CanonicalUser owner    = versions[i].getOwner();
        	 boolean isDeletionMarker = versions[i].getIsDeletionMarker();
        	 String displayName       = owner.getDisplayName();
        	 String id                = owner.getID();
        	 
        	 if ( isDeletionMarker ) 
        	 { 	  
        		  xml.append( "<DeleteMarker>" );  	 
                  xml.append( "<Key>" ).append( versions[i].getKey()).append( "</Key>" );
                  xml.append( "<VersionId>" ).append( versions[i].getVersion()).append( "</VersionId>" );
                  xml.append( "<IsLatest>" ).append( versions[i].getIsLatest()).append( "</IsLatest>" );
                  xml.append( "<LastModified>" ).append( DatatypeConverter.printDateTime( versions[i].getLastModified())).append( "</LastModified>" );
        	 }
        	 else
        	 { 	  xml.append( "<Version>" );  	 
                  xml.append( "<Key>" ).append( versions[i].getKey()).append( "</Key>" );
                  xml.append( "<VersionId>" ).append( versions[i].getVersion()).append( "</VersionId>" );
                  xml.append( "<IsLatest>" ).append( versions[i].getIsLatest()).append( "</IsLatest>" );
                  xml.append( "<LastModified>" ).append( DatatypeConverter.printDateTime( versions[i].getLastModified())).append( "</LastModified>" );
                  xml.append( "<ETag>" ).append( versions[i].getETag()).append( "</ETag>" );
                  xml.append( "<Size>" ).append( versions[i].getSize()).append( "</Size>" );
                  xml.append( "<StorageClass>" ).append( versions[i].getStorageClass()).append( "</StorageClass>" );
        	 }
        	 
             xml.append( "<Owner>" );
             xml.append( "<ID>" ).append( id ).append( "</ID>" );
             if ( null == displayName )
              	  xml.append( "<DisplayName/>" );
             else xml.append( "<DisplayName>" ).append( owner.getDisplayName()).append( "</DisplayName>" );
             xml.append( "</Owner>" );
        	 
             if ( isDeletionMarker )
            	  xml.append( "</DeleteMarker>" ); 
             else xml.append( "</Version>" );
        }
        xml.append( "</ListVersionsResult>" );
      
		response.setStatus(200);
	    response.setContentType("text/xml; charset=UTF-8");
    	S3RestServlet.endResponse(response, xml.toString());
	}
	
	public void executeGetBucketLogging(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO -- this is a beta feature of S3
	}
	
	public void executeGetBucketLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		throw new UnsupportedException( "No concept of Region is support in this EC2 implementation." );
	}
	
	public void executePutBucket(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		int contentLength = request.getContentLength();
		Object objectInContent = null;
		
		if(contentLength > 0) 
		{
			InputStream is = null;
			try {
				is = request.getInputStream();
				String xml = StringHelper.stringFromStream(is);
				XSerializer serializer = new XSerializer(new XSerializerXmlAdapter()); 
				objectInContent = serializer.serializeFrom(xml);
				if(objectInContent != null && !(objectInContent instanceof S3CreateBucketConfiguration)) {
					throw new InvalidRequestContentException("Invalid rquest content in create-bucket: " + xml);
				}
				is.close();
				
			} catch (IOException e) {
				logger.error("Unable to read request data due to " + e.getMessage(), e);
				throw new NetworkIOException(e);
				
			} finally {
				if(is != null) is.close();
			}
		}
		
		S3CreateBucketRequest engineRequest = new S3CreateBucketRequest();
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));
		engineRequest.setConfig((S3CreateBucketConfiguration)objectInContent);
		
		S3CreateBucketResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		response.addHeader("Location", "/" + engineResponse.getBucketName());
		response.setContentLength(0);
		response.setStatus(200);
		response.flushBuffer();
	}
	
	public void executePutBucketAcl(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		S3PutObjectRequest putRequest = null;
		
		// -> reuse the Access Control List parsing code that was added to support DIME
		String bucketName = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		try {
		    putRequest = S3RestServlet.toEnginePutObjectRequest( request.getInputStream());
		}
		catch( Exception e ) {
			throw new IOException( e.toString());
		}
		
		// -> reuse the SOAP code to save the passed in ACLs
		S3SetBucketAccessControlPolicyRequest engineRequest = new S3SetBucketAccessControlPolicyRequest();
		engineRequest.setBucketName( bucketName );
		engineRequest.setAcl( putRequest.getAcl());
		
	    S3Response engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);	
	    response.setStatus( engineResponse.getResultCode());
	}
	
	public void executePutBucketVersioning(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String bucketName       = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String versioningStatus = null;
		Node   item             = null;

		if (null == bucketName) {
			logger.error( "executePutBucketVersioning - no bucket name given" );
			response.setStatus( 400 ); 
			return; 
		}
		
		try {
		    DocumentBuilder db = dbf.newDocumentBuilder();
		    Document restXML = db.parse( request.getInputStream());
		    NodeList match   = restXML.getElementsByTagName( "Status" ); 
	        if ( 0 < match.getLength()) 
	        {
	    	     item = match.item(0);
	    	     versioningStatus = new String( item.getFirstChild().getNodeValue());
	        }
	        else
	        {    logger.error( "executePutBucketVersioning - cannot find Status tag in XML body" );
				 response.setStatus( 400 ); 
				 return; 
	        }
	      
			// -> does not matter what the ACLs say only the owner can turn on versioning on a bucket
			SBucketDao bucketDao = new SBucketDao();
			SBucket sbucket = bucketDao.getByName( bucketName );
		
			String client = UserContext.current().getCanonicalUserId();
			if (!client.equals( sbucket.getOwnerCanonicalId()))
			    throw new PermissionDeniedException( "Access Denied - only the owner can turn on versioing on a bucket" );
		
			     if (versioningStatus.equalsIgnoreCase( "Enabled"  )) sbucket.setVersioningStatus( 1 );
			else if (versioningStatus.equalsIgnoreCase( "Suspended")) sbucket.setVersioningStatus( 2 );
			else { 
				 logger.error( "executePutBucketVersioning - unknown state: [" + versioningStatus + "]" );
				 response.setStatus( 400 ); 
				 return; 
		    }
			bucketDao.update( sbucket );
			
		} catch( PermissionDeniedException e ) {
			logger.error( "executePutBucketVersioning - failed due to " + e.getMessage(), e);
			throw e;
			
		} catch( Exception e ) {
			logger.error( "executePutBucketVersioning - failed due to " + e.getMessage(), e);
			response.setStatus(500);
			return;
		}		
		response.setStatus(200);
	}
	
	public void executePutBucketLogging(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO -- this is a S3 beta feature
	}
	
	public void executeDeleteBucket(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		S3DeleteBucketRequest engineRequest = new S3DeleteBucketRequest();
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));
		S3Response engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);  
		response.setStatus(engineResponse.getResultCode());
		response.flushBuffer();
	}
}
