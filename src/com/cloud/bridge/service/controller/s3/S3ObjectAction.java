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

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cloud.bridge.service.S3Constants;
import com.cloud.bridge.service.S3RestServlet;
import com.cloud.bridge.service.ServiceProvider;
import com.cloud.bridge.service.ServletAction;
import com.cloud.bridge.service.core.s3.S3GetObjectRequest;
import com.cloud.bridge.service.core.s3.S3GetObjectResponse;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineRequest;
import com.cloud.bridge.service.core.s3.S3PutObjectInlineResponse;
import com.cloud.bridge.util.Converter;
import com.cloud.bridge.util.DateHelper;
import com.cloud.bridge.util.ServletRequestDataSource;

/**
 * @author Kelven Yang
 */
public class S3ObjectAction implements ServletAction {
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String method = request.getMethod();
		if (method.equalsIgnoreCase("GET")) {
			executeGetObject(request, response);
			return;
		} else if (method.equalsIgnoreCase("PUT")) {
			executePutObject(request, response);
			return;
		} else if (method.equalsIgnoreCase("DELETE")) {
			executeDeleteObject(request, response);
			return;
		} else if (method.equalsIgnoreCase("HEAD")) {
			executeHeadObject(request, response);
			return;
		} else if (method.equalsIgnoreCase("POST")) {
			executePostObject(request, response);
			return;
		} else {
			throw new IllegalArgumentException(
					"Unsupported method in REST request");
		}
	}

	private void executeGetObject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String bucket = (String) request
				.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String key = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);

		S3GetObjectRequest engineRequest = new S3GetObjectRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setInlineData(true);
		engineRequest.setReturnData(true);

		S3GetObjectResponse engineResponse = ServiceProvider.getInstance()
				.getS3Engine().handleRequest(engineRequest);
		response.addHeader("ETag", engineResponse.getETag());
		response.addHeader("Last-Modified", DateHelper.getDateDisplayString(
			DateHelper.GMT_TIMEZONE, engineResponse.getLastModified().getTime(), "E, d MMM yyyy HH:mm:ss z"));
		
		DataHandler dataHandler = engineResponse.getData();
		if(dataHandler != null) {
			response.setContentLength((int)engineResponse.getContentLength());
			S3RestServlet.writeResponse(response, dataHandler.getInputStream());
		}
	}

	private void executePutObject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String continueHeader = request.getHeader("Expect");
		if (continueHeader != null
				&& continueHeader.equalsIgnoreCase("100-continue")) {
			S3RestServlet.writeResponse(response, "HTTP/1.1 100 Continue\r\n");
		}

		String contentType = request.getHeader("Content-Type");
		long contentLength = Converter.toLong(request
				.getHeader("Content-Length"), 0);

		String bucket = (String) request
				.getAttribute(S3Constants.BUCKET_ATTR_KEY);
		String key = (String) request.getAttribute(S3Constants.OBJECT_ATTR_KEY);
		S3PutObjectInlineRequest engineRequest = new S3PutObjectInlineRequest();
		engineRequest.setBucketName(bucket);
		engineRequest.setKey(key);
		engineRequest.setContentLength(contentLength);

		DataHandler dataHandler = new DataHandler(new ServletRequestDataSource(
				request));
		engineRequest.setData(dataHandler);

		S3PutObjectInlineResponse engineResponse = ServiceProvider
				.getInstance().getS3Engine().handleRequest(engineRequest);
		response.setHeader("ETag", engineResponse.getETag());
	}

	private void executeDeleteObject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO
	}

	private void executeHeadObject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO
	}

	public void executePostObject(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// TODO
	}
}
