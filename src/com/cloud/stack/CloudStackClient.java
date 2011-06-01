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
package com.cloud.stack;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SignatureException;

import org.apache.log4j.Logger;

import com.cloud.bridge.util.JsonAccessor;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * CloudStackClient implements a simple CloudStack client object, it can be used to execute CloudStack commands 
 * with JSON response
 * 
 * @author Kelven Yang
 */
public class CloudStackClient {
    protected final static Logger logger = Logger.getLogger(CloudStackClient.class);
    
	private String _serviceUrl;

	public CloudStackClient(String serviceRootUrl) {
		assert(serviceRootUrl != null);
		
		if(!serviceRootUrl.endsWith("/"))
			_serviceUrl = serviceRootUrl + "/api?";
		else
			_serviceUrl = serviceRootUrl + "api?";
	}
	
	public CloudStackClient(String cloudStackServiceHost, int port, boolean bSslEnabled) {
		StringBuffer sb = new StringBuffer();
		if(!bSslEnabled) {
			sb.append("http://" + cloudStackServiceHost);
			if(port != 80)
				sb.append(":").append(port);
		} else {
			sb.append("https://" + cloudStackServiceHost);
			if(port != 443)
				sb.append(":").append(port);
		}
		
		//
		// If the CloudStack root context path has been from /client to some other name
		// use the first constructor instead
		//
		sb.append("/client/api");
		sb.append("?");
		_serviceUrl = sb.toString();
	}
	
	public JsonAccessor execute(CloudStackCommand cmd, String apiKey, String secretKey) 
		throws MalformedURLException, SignatureException, IOException {
		
		JsonParser parser = new JsonParser();
		URL url = new URL(_serviceUrl + cmd.signCommand(apiKey, secretKey));
		
		if(logger.isDebugEnabled())
			logger.debug("Cloud API call + [" + url.toString() + "]");
		
        URLConnection connect = url.openConnection();
        
        int statusCode;
        statusCode = ((HttpURLConnection)connect).getResponseCode();
        if(statusCode >= 400) {
        	logger.error("Cloud API call + [" + url.toString() + "] failed with status code: " + statusCode);
        	throw new IOException("CloudStack API call error : " + statusCode);
        }
        
        InputStream inputStream = connect.getInputStream(); 
		JsonElement jsonElement = parser.parse(new InputStreamReader(inputStream));
		if(jsonElement == null) {
        	logger.error("Cloud API call + [" + url.toString() + "] failed: unable to parse expected JSON response");
        	
        	throw new IOException("CloudStack API call error : invalid JSON response");
		}
		
		if(logger.isDebugEnabled())
			logger.debug("Cloud API call + [" + url.toString() + "] returned: " + jsonElement.toString());
		return new JsonAccessor(jsonElement);
	}
}
