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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class S3BucketPolicy {
	
	/**
	 * 'NORESULT' is returned when no applicable statement can be found to evaluate
	 * for the S3 access request.  If no evaluated statement results to true then the
	 * default deny result is returned (allow ACL definitions to override it).  
	 */
	public enum PolicyAccess { ALLOW, DEFAULT_DENY, DENY }

	private List<S3PolicyStatement> statementList = new ArrayList<S3PolicyStatement>();
	private String bucketName = null;
	private String id = null;

	public S3BucketPolicy() {
		
	}
	
	public S3PolicyStatement[] getStatements() {
		return statementList.toArray(new S3PolicyStatement[0]);
	}
	
	public void addStatement(S3PolicyStatement param) {
		statementList.add( param );
	}
	
	public String getBucketName() {
		return bucketName;
	}
	
	public void setBucketName(String param) {
		bucketName = param;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String param) {
		id = param;
	}
	
	/**
	 * This function evaluates all applicable policy statements.  Following the "evaluation logic"
	 * as defined by Amazon the type of access derived from the policy is returned.
	 * 
	 * @param objectToAccess - path to the S3 bucket or object in the bucket associated with this
	 *                         policy
	 * @param userAccount - the user performing the access request
	 * @param operationRequested - the S3 operation being requested on the objectToAccess (e.g., PutObject)
	 * @return PolicyAccess type
	 */
	public PolicyAccess eval(String objectToAccess, String userAccount, String operationRequested) 
	{
		// loop though all statements in statementList
		// --> call a function to verify that the statement is relevant to the request (parameters)
		// --> call a function to evaluate the statement's condition
		// --> if hit a DENY then return it
		// --> if instead ended up with an allow return that
		return PolicyAccess.DEFAULT_DENY;
	}
	
	/**
	 * To support debugging we print out what the parsing process has resulted in.
	 */
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Iterator<S3PolicyStatement> itr = statementList.iterator();
		
		value.append( "Bucket Policy for: " + bucketName + " \n" );
		if (null != id) value.append( "Id: " + id + "\n" );
		
		while( itr.hasNext()) {
			S3PolicyStatement oneStatement = itr.next();
			value.append( oneStatement.toString());
			value.append( "\n" );
		}
		
		return value.toString();
	}
}
