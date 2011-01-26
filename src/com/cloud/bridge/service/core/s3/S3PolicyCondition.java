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

import java.text.ParseException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.cloud.bridge.service.core.s3.S3ConditionFactory.PolicyConditions;

/**
 * In the Bucket Policy language a condition block can hold one or more conditions.
 * A condition has one or more keys, where each key can have one or more values.
 */
public abstract class S3PolicyCondition {

	public enum ConditionKeys {
		UnknownKey,
	    CurrentTime, SecureTransport, SourceIp, SourceArn, UserAgent, EpochTime, Referer
	}
	
	protected PolicyConditions condition = null;   
	
	public S3PolicyCondition() {
	}
	
	public PolicyConditions getConition() {
		return condition;
	}
	
	public void setCondition(PolicyConditions param) {
		condition = param;
	}
	
	public static ConditionKeys toConditionKeys(String keyName) 
	{
	         if (keyName.equalsIgnoreCase( "aws:CurrentTime"     )) return ConditionKeys.CurrentTime;
	    else if (keyName.equalsIgnoreCase( "aws:SecureTransport" )) return ConditionKeys.SecureTransport;
	    else if (keyName.equalsIgnoreCase( "aws:SourceIp"        )) return ConditionKeys.SourceIp;
	    else if (keyName.equalsIgnoreCase( "aws:SourceArn"       )) return ConditionKeys.SourceArn;
	    else if (keyName.equalsIgnoreCase( "aws:UserAgent"       )) return ConditionKeys.UserAgent;
	    else if (keyName.equalsIgnoreCase( "aws:EpochTime"       )) return ConditionKeys.EpochTime;
	    else if (keyName.equalsIgnoreCase( "aws:Referer"         )) return ConditionKeys.Referer;
	    else return ConditionKeys.UnknownKey;
	}

	public Set<ConditionKeys> getAllKeys() {
		return null;
	}
	
	/**
	 * After calling getAllKeys(), pass in each key from that result to get
	 * the key's associated list of values.
	 * @param key
	 * @return object[]
	 */
	public Object[] getKeyValues(ConditionKeys key) {
		return null;
	}
	
	public void setKey(ConditionKeys key, String[] values) throws ParseException {
	}

	public boolean isTrue(HttpServletRequest request) {
		return false;
	}
	
	public String toString() {
		return "";
	}
}
