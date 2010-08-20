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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMFactory;
import org.apache.axis2.databinding.utils.writer.MTOMAwareXMLSerializer;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;

import com.amazon.s3.GetBucketAccessControlPolicyResponse;
import com.amazon.s3.GetObjectAccessControlPolicyResponse;
import com.cloud.bridge.model.SBucket;
import com.cloud.bridge.model.SHost;
import com.cloud.bridge.model.SObject;
import com.cloud.bridge.model.SObjectItem;
import com.cloud.bridge.persist.PersistContext;
import com.cloud.bridge.persist.dao.SBucketDao;
import com.cloud.bridge.persist.dao.SObjectDao;
import com.cloud.bridge.service.S3BucketAdapter;
import com.cloud.bridge.service.S3Constants;
import com.cloud.bridge.service.S3RestServlet;
import com.cloud.bridge.service.S3SoapServiceImpl;
import com.cloud.bridge.service.ServiceProvider;
import com.cloud.bridge.service.ServletAction;
import com.cloud.bridge.service.UserContext;
import com.cloud.bridge.service.core.ec2.EC2Volume;
import com.cloud.bridge.service.core.s3.S3AccessControlPolicy;
import com.cloud.bridge.service.core.s3.S3ConditionalHeaders;
import com.cloud.bridge.service.core.s3.S3DeleteObjectRequest;
import com.cloud.bridge.service.core.s3.S3Engine;
import com.cloud.bridge.service.core.s3.S3GetBucketAccessControlPolicyRequest;
import com.cloud.bridge.service.core.s3.S3GetObjectAccessControlPolicyRequest;
import com.cloud.bridge.service.core.s3.S3GetObjectRequest;
import com.cloud.bridge.service.core.s3.S3GetObjectResponse;
import com.cloud.bridge.service.core.s3.S3MetaDataEntry;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineRequest;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineResponse;
import com.cloud.bridge.service.core.s3.S3PutObjectRequest;
import com.cloud.bridge.service.core.s3.S3Response;
import com.cloud.bridge.service.core.s3.S3SetBucketAccessControlPolicyRequest;
import com.cloud.bridge.service.core.s3.S3SetObjectAccessControlPolicyRequest;
import com.cloud.bridge.service.exception.InternalErrorException;
import com.cloud.bridge.util.Converter;
import com.cloud.bridge.util.DateHelper;
import com.cloud.bridge.util.ServletRequestDataSource;
import com.cloud.bridge.util.Tuple;

/**
 * @author Kelven Yang
 */
public class S3ObjectAction implements ServletAction {
    protected final static Logger logger = Logger.getLogger(S3ObjectAction.class);

    private DocumentBuilderFactory dbf = null;
	private OMFactory factory = OMAbstractFactory.getOMFactory();
	private XMLOutputFactory xmlOutFactory = XMLOutputFactory.newInstance();
    
	public S3ObjectAction() {
		dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware( true );

	}

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String method      = request.getMethod();
		String queryString = request.getQueryString();
		
	    response.addHeader( "x-amz-request-id", UUID.randomUUID().toString());	

		     if (method.equalsIgnoreCase( "GET" )) {
		    	 
			     if ( queryString != null && queryString.length() > 0 ) 
			     {
				      if ( queryString.equalsIgnoreCase("acl")) executeGetObjectAcl(request, response);
				 } 
			     else executeGetObject(request, response);
		     }
		else if (method.equalsIgnoreCase( "PUT" )) {
			
			     if ( queryString != null && queryString.length() > 0 ) 
			     {
				     if ( queryString.equalsIgnoreCase("acl")) executePutObjectAcl(request, response);
				 } 
			     else executePutObject(request, response);
		}
		else if (method.equalsIgnoreCase( "DELETE" )) {
			     executeDeleteObject(request, response);
		}
		else if (method.equalsIgnoreCase( "HEAD" )) {
			     executeHeadObject(request, response);
		}
		else if (method.equalsIgnoreCase( "POST" )) {
			     executePostObject(request, response);
		}
		else throw new IllegalArgumentException( "Unsupported method in REST request");
	}

	private void executeGetObjectAcl(HttpServletRequest request, HttpServletResponse response) 
	    throws IOException {
		String bucketName = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String key        = (String)request.getAttribute(S3Constants.OBJECT_ATTR_KEY);

		S3GetObjectAccessControlPolicyRequest engineRequest = new S3GetObjectAccessControlPolicyRequest();
		engineRequest.setBucketName( bucketName );
		engineRequest.setKey( key );

		S3AccessControlPolicy engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		
		// -> serialize using the apache's Axiom classes
		GetObjectAccessControlPolicyResponse onePolicy = S3SoapServiceImpl.toGetObjectAccessControlPolicyResponse( engineResponse );
	
		try {
		    OutputStream os = response.getOutputStream();
		    response.setStatus(200);	
	        response.setContentType("text/xml; charset=UTF-8");
		    XMLStreamWriter xmlWriter = xmlOutFactory.createXMLStreamWriter( os );
		    String documentStart = new String( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
		    os.write( documentStart.getBytes());
		    MTOMAwareXMLSerializer MTOMWriter = new MTOMAwareXMLSerializer( xmlWriter );
            onePolicy.serialize( new QName( "http://s3.amazonaws.com/doc/2006-03-01/", "GetObjectAccessControlPolicyResponse", "ns1" ), factory, MTOMWriter );
            xmlWriter.flush();
            xmlWriter.close();
            os.close();
		}
		catch( XMLStreamException e ) {
			throw new IOException( e.toString());
		}
	}
	
	private void executePutObjectAcl(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		S3PutObjectRequest putRequest = null;
		
		// -> reuse the Access Control List parsing code that was added to support DIME
		String bucketName = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String key        = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		try {
		    putRequest = S3RestServlet.toEnginePutObjectRequest( request.getInputStream());
		}
		catch( Exception e ) {
			throw new IOException( e.toString());
		}
		
		// -> reuse the SOAP code to save the passed in ACLs
		S3SetObjectAccessControlPolicyRequest engineRequest = new S3SetObjectAccessControlPolicyRequest();
		engineRequest.setBucketName( bucketName );
		engineRequest.setKey( key );
		engineRequest.setAcl( putRequest.getAcl());
		
	    S3Response engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);	
	    response.setStatus( engineResponse.getResultCode());
	}

	private void executeGetObject(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String   bucket    = (String) request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String   key       = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		String[] paramList = null;
		
		S3GetObjectRequest engineRequest = new S3GetObjectRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setInlineData(true);
		engineRequest.setReturnData(true);	
		//engineRequest.setReturnMetadata(true);
		engineRequest = setRequestByteRange( request, engineRequest );

		// -> is this a request for a specific version of the object?  look for "versionId=" in the query string
		String queryString = request.getQueryString();
		if (null != queryString) {
			paramList = queryString.split( "[&=]" );
		    if (null != paramList) engineRequest.setVersion( returnParameter( paramList, "versionId" ));
		}

		S3GetObjectResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest( engineRequest );			
		response.setStatus( engineResponse.getResultCode());
		
		String deleteMarker = engineResponse.getDeleteMarker();
		if ( null != deleteMarker ) {
			 response.addHeader( "x-amz-delete-marker", "true" );	
			 response.addHeader( "x-amz-version-id", deleteMarker );
		}
		else {
		     String version = engineResponse.getVersion();
		     if (null != version) response.addHeader( "x-amz-version-id", version );
		}
		
		
		// -> was the get conditional?
		if (!conditionPassed( request, response, engineResponse.getLastModified().getTime(), engineResponse.getETag())) 
			return;
	

		// -> is there data to return
		// -> from the Amazon REST documentation it appears that Meta data is only returned as part of a HEAD request
		//returnMetaData( engineResponse, response );
			
		DataHandler dataHandler = engineResponse.getData();
		if (dataHandler != null) {
			response.addHeader("ETag", engineResponse.getETag());
			response.addHeader("Last-Modified", DateHelper.getDateDisplayString(
				DateHelper.GMT_TIMEZONE, engineResponse.getLastModified().getTime(), "E, d MMM yyyy HH:mm:ss z"));

			response.setContentLength((int)engineResponse.getContentLength());			
			S3RestServlet.writeResponse(response, dataHandler.getInputStream());
		}
	}

	private void executePutObject(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String continueHeader = request.getHeader("Expect");
		if (continueHeader != null && continueHeader.equalsIgnoreCase("100-continue")) {
			S3RestServlet.writeResponse(response, "HTTP/1.1 100 Continue\r\n");
		}

		String contentType = request.getHeader("Content-Type");
		long contentLength = Converter.toLong(request.getHeader("Content-Length"), 0);

		String bucket = (String) request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String key = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		S3PutObjectInlineRequest engineRequest = new S3PutObjectInlineRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setContentLength(contentLength);
		engineRequest.setMetaEntries( extractMetaData( request ));

		DataHandler dataHandler = new DataHandler(new ServletRequestDataSource(request));
		engineRequest.setData(dataHandler);

		S3PutObjectInlineResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		response.setHeader("ETag", engineResponse.getETag());
		String version = engineResponse.getVersion();
		if (null != version) response.addHeader( "x-amz-version-id", version );		
	}

	/**
	 * Once versioining is turned on then to delete an object requires specifying a version 
	 * parameter.   A deletion marker is set once versioning is turned on in a bucket.
	 */
	private void executeDeleteObject(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String   bucket    = (String) request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String   key       = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		String[] paramList = null;
			
		S3DeleteObjectRequest engineRequest = new S3DeleteObjectRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);

		// -> is this a request for a specific version of the object?  look for "versionId=" in the query string
		String queryString = request.getQueryString();
		if (null != queryString) {
			paramList = queryString.split( "[&=]" );
		    if (null != paramList) engineRequest.setVersion( returnParameter( paramList, "versionId" ));
		}
		
		S3Response engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest( engineRequest );
		
		response.setStatus( engineResponse.getResultCode());	
		String version = engineRequest.getVersion();
		if (null != version) response.addHeader( "x-amz-version-id", version );		
	}

	private void executeHeadObject(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String   bucket    = (String) request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String   key       = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		String[] paramList = null;
			
		S3GetObjectRequest engineRequest = new S3GetObjectRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setInlineData(true);    // -> need to set so we get ETag etc returned
		engineRequest.setReturnData(true);
		engineRequest.setReturnMetadata(true);
		engineRequest = setRequestByteRange( request, engineRequest );
		
		// -> is this a request for a specific version of the object?  look for "versionId=" in the query string
		String queryString = request.getQueryString();
		if (null != queryString) {
			paramList = queryString.split( "[&=]" );
		    if (null != paramList) engineRequest.setVersion( returnParameter( paramList, "versionId" ));
		}

		S3GetObjectResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest( engineRequest );		
		response.setStatus( engineResponse.getResultCode());
		
		String deleteMarker = engineResponse.getDeleteMarker();
		if ( null != deleteMarker ) {
			 response.addHeader( "x-amz-delete-marker", "true" );	
			 response.addHeader( "x-amz-version-id", deleteMarker );
		}
		else {
		     String version = engineResponse.getVersion();
		     if (null != version) response.addHeader( "x-amz-version-id", version );
		}
		
		// -> was the head request conditional?
		if (!conditionPassed( request, response, engineResponse.getLastModified().getTime(), engineResponse.getETag())) 
			return;	
		
		
		// -> for a head request we return everything except the data
		returnMetaData( engineResponse, response );
		
		DataHandler dataHandler = engineResponse.getData();
		if (dataHandler != null) {
			response.addHeader("ETag", engineResponse.getETag());
			response.addHeader("Last-Modified", DateHelper.getDateDisplayString(
				DateHelper.GMT_TIMEZONE, engineResponse.getLastModified().getTime(), "E, d MMM yyyy HH:mm:ss z"));

			response.setContentLength((int)engineResponse.getContentLength());			
		}	
	}

	public void executePostObject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO
	}

	/**
	 * Support the "Range: bytes=0-399" header with just one byte range.
	 * @param request
	 * @param engineRequest
	 * @return
	 */
	private S3GetObjectRequest setRequestByteRange(HttpServletRequest request, S3GetObjectRequest engineRequest ) {
		String temp = request.getHeader( "Range" );
		if (null == temp) return engineRequest;
		
		int offset = temp.indexOf( "=" );
		if (-1 != offset) {
			String range = temp.substring( offset+1 );
		
		    String[] parts = range.split( "-" );
		    if (2 >= parts.length) {
		    	// -> the end byte is inclusive
			    engineRequest.setByteRangeStart( Long.parseLong(parts[0]));
			    engineRequest.setByteRangeEnd(   Long.parseLong(parts[1])+1);
		    }
		}	
		return engineRequest;
	}
	
	private S3ConditionalHeaders conditionalRequest( HttpServletRequest request ) {
		S3ConditionalHeaders headers = new S3ConditionalHeaders();
		
		headers.setModifiedSince( request.getHeader( "If-Modified-Since" ));
		headers.setUnModifiedSince( request.getHeader( "If-Unmodified-Since" ));
		headers.setMatch( request.getHeader( "If-Match" ));
		headers.setNoneMatch( request.getHeader( "If-None-Match" ));
        return headers;
	}
	
	private boolean conditionPassed( HttpServletRequest request, HttpServletResponse response, Date lastModified, String ETag ) {	
		S3ConditionalHeaders ifCond = conditionalRequest( request );
		
		if (0 > ifCond.ifModifiedSince( lastModified )) {
			response.setStatus( 304 );
			return false;
		}
		if (0 > ifCond.ifUnmodifiedSince( lastModified )) {
			response.setStatus( 412 );
			return false;
		}
		if (0 > ifCond.ifMatchEtag( ETag )) {
			response.setStatus( 412 );
			return false;
		}
		if (0 > ifCond.ifNoneMatchEtag( ETag )) {
			response.setStatus( 412 );
			return false;
		}	
		return true;
	}
	
	/**
	 * Return the saved object's meta data back to the client as HTTP "x-amz-meta-" headers.
	 * This function is constructing an HTTP header and these headers have a defined syntax
	 * as defined in rfc2616.   Any characters that could cause an invalid HTTP header will
	 * prevent that meta data from being returned via the REST call (as is defined in the Amazon
	 * spec).   These characters can be defined if using the SOAP API as well as the REST API.
	 * 
	 * @param engineResponse
	 * @param response
	 */
	private void returnMetaData( S3GetObjectResponse engineResponse, HttpServletResponse response ) 
	{ 
		boolean ignoreMeta   = false;
		int     ignoredCount = 0;
		
	    S3MetaDataEntry[] metaSet = engineResponse.getMetaEntries();
	    for( int i=0; null != metaSet && i < metaSet.length; i++ ) 
	    {
		   String name      = metaSet[i].getName();
		   String value     = metaSet[i].getValue();
		   byte[] nameBytes = name.getBytes();
		   ignoreMeta = false;
		   
		   // -> cannot have control characters (octets 0 - 31) and DEL (127), in an HTTP header
		   for( int j=0; j < name.length(); j++ ) {
			   if ((0 <= nameBytes[j] && 31 >= nameBytes[j]) || 127 == nameBytes[j]) {
				   ignoreMeta = true;
				   break;
			   }
		   }
		   
		   // -> cannot have HTTP separators in an HTTP header 
		   if (-1 != name.indexOf('(')  || -1 != name.indexOf(')') || -1 != name.indexOf('@')  || 
			   -1 != name.indexOf('<')  || -1 != name.indexOf('>') || -1 != name.indexOf('\"') ||
			   -1 != name.indexOf('[')  || -1 != name.indexOf(']') || -1 != name.indexOf('=')  ||	   
			   -1 != name.indexOf(',')  || -1 != name.indexOf(';') || -1 != name.indexOf(':')  ||
			   -1 != name.indexOf('\\') || -1 != name.indexOf('/') || -1 != name.indexOf(' ')  ||
			   -1 != name.indexOf('{')  || -1 != name.indexOf('}') || -1 != name.indexOf('?')  ||
			   -1 != name.indexOf('\t')
			  ) ignoreMeta = true;
		   
		   
		   if ( ignoreMeta )
			    ignoredCount++;
		   else response.addHeader( "x-amz-meta-" + name, value );
	    }
	    
	    if (0 < ignoredCount) response.addHeader( "x-amz-missing-meta", new String( "" + ignoredCount ));
	}		

	/**
	 * Extract the name and value of all meta data so it can be written with the
	 * object that is being 'PUT'.
	 * 
	 * @param request
	 * @return
	 */
	private S3MetaDataEntry[] extractMetaData( HttpServletRequest request ) {
		List<S3MetaDataEntry> metaSet = new ArrayList<S3MetaDataEntry>();  
		int count = 0;
		
		Enumeration headers = request.getHeaderNames();
        while( headers.hasMoreElements()) 
        {
	        String key = (String)headers.nextElement();
	        if (key.startsWith( "x-amz-meta-" )) 
	        {
	        	String name  = key.substring( 11 );
	            String value = request.getHeader( key );
	            if (null != value) {
	            	S3MetaDataEntry oneMeta = new S3MetaDataEntry();
	            	oneMeta.setName( name );
	            	oneMeta.setValue( value );
	            	metaSet.add( oneMeta );
	            	count++;
	            }
	        }
        }		

        if ( 0 < count )
        	 return metaSet.toArray(new S3MetaDataEntry[0]);
        else return null;
	}
	
	/**
	 * @param paramList - name - value pairs with name at odd indexes
	 * @param find      - name string to return first found
	 * @return the value matching the found name 
	 */
	private String returnParameter( String[] paramList, String find ) {
		int i=0;
		
		if (paramList == null) return null;
		
		while( i+2 <= paramList.length ) {
           if (paramList[i].equalsIgnoreCase( find )) return paramList[ i+1 ];
           i += 2;
        }
		return null;
	}
}
