/*
 * Copyright 2011 Cloud.com, Inc.
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
package com.cloud.bridge.service.core.s3;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cloud.bridge.service.core.s3.S3PolicyAction.PolicyActions;
import com.cloud.bridge.service.core.s3.S3PolicyCondition.ConditionKeys;

/**
 * The purpose of this class is to pass into the heart of the Bucket Policy evaluation logic
 * all the context (strings from the request) needed for each defined condition.
 */
public class S3PolicyContext {

	private HttpServletRequest request = null;;
	private String bucketName = null;
	private long bucketId = 0;
	private PolicyActions requestedAction;
	private Map<ConditionKeys,String> evalParams = new HashMap<ConditionKeys,String>();

	public S3PolicyContext(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getHttp() {
		return request;
	}
	
	public String getRemoveAddr() {
        if ( null == request ) 
         	 return null;
        else return request.getRemoteAddr();
	}
	
	public boolean getIsHTTPSecure() {
	    if ( null == request )
	    	 return false;
	    else return request.isSecure();
	}
	
	public String getBucketName() {
		return bucketName;
	}
	
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	
	public long getBucketId() {
		return bucketId;
	}
	
	public void setBucketId(long bucketId) {
		this.bucketId = bucketId;
	}
	
	public PolicyActions getRequestedAction() {
		return requestedAction;
	}
	
	public void setRequestedAction(PolicyActions action) {
		this.requestedAction = action;
	}
	
	public String getEvalParam(ConditionKeys key) 
	{	
		String param = null;
		
	         if (ConditionKeys.UserAgent == key) {
	        	 if (null != request) param = request.getHeader( "User-Agent" );
	         }
     	else if (ConditionKeys.Referer == key) {
     		     if (null != request) param = request.getHeader( "Referer" );
     	}
     	else param = evalParams.get(key);
	         
	    return param;
	}
	
	public void setEvalParam(ConditionKeys key, String value) {
		evalParams.put(key, value);
	}
}
