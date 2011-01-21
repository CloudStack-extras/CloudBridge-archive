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

public class S3PolicyAction {

	private List<String> actionList = new ArrayList<String>();
	
	public S3PolicyAction() {
	}

	public String[] getActions() {
		return actionList.toArray(new String[0]);
	}
	
	public void addAction(String param) {
		actionList.add( param );
	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Iterator<String> itr = actionList.iterator();
		
		value.append( "Actions: \n" );
		while( itr.hasNext()) {
			String oneAction = itr.next();
			value.append( oneAction );
			value.append( "\n" );
		}
		
		return value.toString();
	}
}
