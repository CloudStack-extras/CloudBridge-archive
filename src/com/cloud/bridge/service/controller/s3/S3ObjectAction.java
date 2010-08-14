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
import com.cloud.bridge.service.core.s3.S3Engine;
import com.cloud.bridge.service.core.s3.S3GetObjectRequest;
import com.cloud.bridge.service.core.s3.S3GetObjectResponse;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineRequest;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineResponse;
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
		String bucket = (String) request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String key = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);

		S3GetObjectRequest engineRequest = new S3GetObjectRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setInlineData(true);
		engineRequest.setReturnData(true);

		S3GetObjectResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		
		response.setStatus( engineResponse.getResultCode());
		
		if (engineResponse.isDeleteMarker()) response.addHeader( "x-amz-delete-marker", "true" );	
	
		String version = engineResponse.getVersion();
		if (null != version) response.addHeader( "x-amz-version-id", engineResponse.getVersion());
		
		
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
	}

	/**
	 * Once versioining is turned on then to delete an object requires specifying a version 
	 * parameter.   A deletion marker is set once versioning is turned on in a bucket.
	 */
    @SuppressWarnings("deprecation")
	private void executeDeleteObject(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
        // -> make sure that the bucket and the object both exist
		String bucketName = (String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		SBucketDao bucketDao = new SBucketDao();
		SBucket sbucket = bucketDao.getByName( bucketName );
		if (null == sbucket) {
			logger.error( "executeDeleteObject - " + bucketName + " bucket name does not exist" );
			response.sendError( 404, "Bucket " + bucketName + " does not exist" );
			return;
		}

		String storedPath = null;
		String fileName   = request.getPathInfo();
		if (fileName.startsWith( "/" )) fileName = fileName.substring( 1 );

	    SObjectDao sobjectDao = new SObjectDao();
	    SObject sobject = sobjectDao.getByNameKey( sbucket, fileName );
		if ( null == sobject ) {
			 logger.error( "executeDeleteObject - " + fileName + " object name does not exist" );
			 response.sendError( 404, "Object " + fileName + " does not exist" );
			 return;
		}
		else {
		     Iterator<SObjectItem> it = sobject.getItems().iterator();
		     if (it.hasNext()) {
			     SObjectItem item = (SObjectItem)it.next();
			     storedPath = item.getStoredPath();
		     }
		}
		
		if (null == storedPath) throw new InternalErrorException( "DeleteObject cannot find: " + fileName );
		logger.debug( "executeDeleteObject - delete " + bucketName + ":" + fileName + " at: " + storedPath );
        

		// -> depending on if versioning is on controls our action
		int versionStatus = sbucket.getVersioningStatus();
		if ( SBucket.VERSIONING_NULL == versionStatus) {
			 // -> no versioning so we delete the actual object
			 S3Engine engine = ServiceProvider.getInstance().getS3Engine();
			 Tuple<SHost, String> tupleBucketHost = engine.getBucketStorageHost( sbucket );
			 S3BucketAdapter bucketAdapter =  engine.getStorageHostBucketAdapter( tupleBucketHost.getFirst());		 
			 bucketAdapter.deleteObject( tupleBucketHost.getSecond(), bucketName, storedPath );
			
			 // TODO
			 // Cascade-deleting can delete related SObject/SObjectItem objects, but not SAcl and SMeta objects. We
			 // need to perform deletion of these objects related to bucket manually.
             sobjectDao.delete( sobject );
     		 logger.debug( "executeDeleteObject - delete versioning off: " + bucketName + ":" + fileName );
		 }
		 else {
			  // -> versioning was on so we just set the delete marker
			  sobject.setDeletionMark(1);
			  sobjectDao.update( sobject );
     		  logger.debug( "executeDeleteObject - set delete marker versioning on" );
		 }
		 response.setStatus( 204 );
	}

	private void executeHeadObject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO
	}

	public void executePostObject(HttpServletRequest request,HttpServletResponse response) throws IOException {
		// TODO
	}
}
