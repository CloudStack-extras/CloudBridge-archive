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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cloud.bridge.service.S3Constants;
import com.cloud.bridge.service.S3RestServlet;
import com.cloud.bridge.service.ServiceProvider;
import com.cloud.bridge.service.ServletAction;
import com.cloud.bridge.service.core.s3.S3CreateBucketConfiguration;
import com.cloud.bridge.service.core.s3.S3CreateBucketRequest;
import com.cloud.bridge.service.core.s3.S3CreateBucketResponse;
import com.cloud.bridge.service.core.s3.S3DeleteBucketRequest;
import com.cloud.bridge.service.core.s3.S3ListBucketRequest;
import com.cloud.bridge.service.core.s3.S3ListBucketResponse;
import com.cloud.bridge.service.core.s3.S3Response;
import com.cloud.bridge.service.exception.InvalidRequestContentException;
import com.cloud.bridge.service.exception.NetworkIOException;
import com.cloud.bridge.util.Converter;
import com.cloud.bridge.util.StringHelper;
import com.cloud.bridge.util.XSerializer;
import com.cloud.bridge.util.XSerializerXmlAdapter;

/**
 * @author Kelven Yang
 */
public class S3BucketAction implements ServletAction {
    protected final static Logger logger = Logger.getLogger(S3BucketAction.class);
    
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getMethod(); 
		if(method.equalsIgnoreCase("PUT")) {
			String queryString = request.getQueryString();
			if(queryString != null && queryString.length() > 0) {
				if(queryString.equalsIgnoreCase("acl")) {
					executePutBucketAcl(request, response);
					return;
				} else if(queryString.equalsIgnoreCase("versioning")) {
					executePutBucketVersioning(request, response);
					return;
				} else if(queryString.equalsIgnoreCase("logging")) {
					executePutBucketLogging(request, response);
					return;
				}
			}
			executePutBucket(request, response);
			return;
		} else if(method.equalsIgnoreCase("GET")) {
			String queryString = request.getQueryString();
			if(queryString != null && queryString.length() > 0) {
				if(queryString.equalsIgnoreCase("acl")) {
					executeGetBucketAcl(request, response);
					return;
				} else if(queryString.equalsIgnoreCase("versioning")) {
					executeGetBucketVersioning(request, response);
					return;
				} else if(queryString.equalsIgnoreCase("logging")) {
					executeGetBucketLogging(request, response);
					return;
				} else if(queryString.equalsIgnoreCase("location")) {
					executeGetBucketLocation(request, response);
					return;
				}
			}
			
			executeGetBucket(request, response);
			return;
		} else if(method.equalsIgnoreCase("DELETE")) {
			executeDeleteBucket(request, response);
			return;
		} else {
			throw new IllegalArgumentException("Unsupported method in REST request");
		}
	}
	
	public void executeGetBucket(HttpServletRequest request, HttpServletResponse response) throws IOException {
		S3ListBucketRequest engineRequest = new S3ListBucketRequest();
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));
		engineRequest.setDelimiter(request.getParameter("delimiter"));
		engineRequest.setMarker(request.getParameter("marker"));
		engineRequest.setPrefix(request.getParameter("prefix"));
		
		int maxKeys = Converter.toInt(request.getParameter("max-keys"), 1000);
		engineRequest.setMaxKeys(maxKeys);
		S3ListBucketResponse engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);
		
		XSerializer serializer = new XSerializer(new XSerializerXmlAdapter(), true, true);
		String content = serializer.serializeTo(engineResponse, "ListBucketResult", "http://s3.amazonaws.com/doc/2006-03-01", 0);
		response.setStatus(200);
		S3RestServlet.endResponse(response, content);
	}
	
	public void executeGetBucketAcl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO
	}
	
	public void executeGetBucketVersioning(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// TODO
	}
	
	public void executeGetBucketLogging(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO
	}
	
	public void executeGetBucketLocation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO
	}
	
	public void executePutBucket(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int contentLength = request.getContentLength();
		Object objectInContent = null;
		if(contentLength > 0) {
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
				if(is != null) 
					is.close();
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
	
	public void executePutBucketAcl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO
	}
	
	public void executePutBucketVersioning(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO
	}
	
	public void executePutBucketLogging(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO
	}
	
	public void executeDeleteBucket(HttpServletRequest request, HttpServletResponse response) throws IOException {
		S3DeleteBucketRequest engineRequest = new S3DeleteBucketRequest();
		engineRequest.setBucketName((String)request.getAttribute(S3Constants.BUCKET_ATTR_KEY));
		S3Response engineResponse = ServiceProvider.getInstance().getS3Engine().handleRequest(engineRequest);  
		response.setStatus(engineResponse.getResultCode());
		response.flushBuffer();
	}
}
