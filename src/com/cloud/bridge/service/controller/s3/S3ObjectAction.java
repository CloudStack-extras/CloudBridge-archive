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
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;

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
import com.cloud.bridge.service.ServiceProvider;
import com.cloud.bridge.service.ServletAction;
import com.cloud.bridge.service.core.s3.S3ConditionalHeaders;
import com.cloud.bridge.service.core.s3.S3DeleteObjectRequest;
import com.cloud.bridge.service.core.s3.S3Engine;
import com.cloud.bridge.service.core.s3.S3GetObjectRequest;
import com.cloud.bridge.service.core.s3.S3GetObjectResponse;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineRequest;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineResponse;
import com.cloud.bridge.service.core.s3.S3Response;
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

	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String method = request.getMethod();
		
		     if (method.equalsIgnoreCase("GET"   )) executeGetObject(request, response);
		else if (method.equalsIgnoreCase("PUT"   )) executePutObject(request, response);
		else if (method.equalsIgnoreCase("DELETE")) executeDeleteObject(request, response);
		else if (method.equalsIgnoreCase("HEAD"  )) executeHeadObject(request, response);
		else if (method.equalsIgnoreCase("POST"  )) executePostObject(request, response);
		else throw new IllegalArgumentException( "Unsupported method in REST request");
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

	private void executeHeadObject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String   bucket    = (String) request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String   key       = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		String[] paramList = null;
			
		S3GetObjectRequest engineRequest = new S3GetObjectRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setInlineData(false);
		engineRequest.setReturnData(false);

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
	}

	public void executePostObject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO
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
