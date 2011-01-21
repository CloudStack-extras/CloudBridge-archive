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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * In the Bucket Policy language a condition block can hold one or more conditions.
 * A condition has one or more keys, where each key can have one or more values.
 */
public class S3PolicyCondition {

	private String condition = null;   
	private Map<String,String[]> keys = new HashMap<String,String[]>();
	
	public S3PolicyCondition() {
		
	}
	
	public String getConition() {
		return condition;
	}
	
	public void setCondition(String param) {
		condition = param;
	}
	
	/**
	 * Return a set holding all the condition keys kept in this object.
	 * @return Set<String>
	 */
	public Set<String> getAllKeys() {
		return keys.keySet();
	}
	
	/**
	 * After calling getAllKeys(), pass in each key from that result to get
	 * the key's associated list of values.
	 * @param key
	 * @return String[]
	 */
	public String[] getKeyValues(String key) {
		return keys.get(key);
	}
	
	public void setKey(String key, String[] values) {
		keys.put(key, values);
	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Set<String> keySet = getAllKeys();
		if (null == keySet) return "";
		Iterator<String> itr = keySet.iterator();
		
		value.append( condition + ": \n" );
		while( itr.hasNext()) {
			String keyName = (String)itr.next();
			value.append( keyName );
			value.append( ": \n" );
			String[] valueList = getKeyValues( keyName );
			for( int i=0; i < valueList.length; i++ ) {
				if (0 < i) value.append( "\n" );
				value.append( valueList[i] );
			}
			value.append( "\n\n" );
		}
		
		return value.toString();
	}
}
