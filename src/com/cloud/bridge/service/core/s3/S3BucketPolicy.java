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

	private List<S3PolicyStatement> statementList = new ArrayList<S3PolicyStatement>();
	private String id = null;

	public S3BucketPolicy() {
		
	}
	
	public S3PolicyStatement[] getStatements() {
		return statementList.toArray(new S3PolicyStatement[0]);
	}
	
	public void addStatement(S3PolicyStatement param) {
		statementList.add( param );
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String param) {
		id = param;
	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Iterator<S3PolicyStatement> itr = statementList.iterator();
		
		value.append( "Bucket Policy: \n" );
		if (null != id) value.append( "Id: " + id + "\n" );
		
		while( itr.hasNext()) {
			S3PolicyStatement oneStatement = itr.next();
			value.append( oneStatement.toString());
			value.append( "\n" );
		}
		
		return value.toString();
	}
}
