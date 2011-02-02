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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.cloud.bridge.service.core.s3.S3ConditionFactory.PolicyConditions;

public class S3PolicyBoolCondition extends S3PolicyCondition {


	private Map<ConditionKeys,String[]> keys = new HashMap<ConditionKeys,String[]>();

	public S3PolicyBoolCondition() {
	}
	
	/**
	 * Return a set holding all the condition keys kept in this object.
	 * @return Set<String>
	 */
	public Set<ConditionKeys> getAllKeys() {
		return keys.keySet();
	}
	
	/**
	 * After calling getAllKeys(), pass in each key from that result to get
	 * the key's associated list of values.
	 * @param key
	 * @return String[]
	 */
	public String[] getKeyValues(ConditionKeys key) {
		return keys.get(key);
	}
	
	/** 
	 * Documentation on Bool conditions is nearly non-existent.   Only found that
	 * the 'SecureTransport' key is relvant and have not found any examples.   
	 * 
	 * @throws ParseException 
	 */
	public void setKey(ConditionKeys key, String[] values) throws ParseException {		
	    keys.put(key, values);
	}
	
	public boolean isTrue(S3PolicyContext context) 
	{
		// -> improperly defined condition evaluates to false
		Set<ConditionKeys> keySet = getAllKeys();
		if (null == keySet) return false;
		Iterator<ConditionKeys> itr = keySet.iterator();
		if (!itr.hasNext()) return false;
		
		// -> all keys in a condition are ANDed together (one false one terminates the entire condition)
		while( itr.hasNext()) 
		{
			ConditionKeys keyName = itr.next();
			// String[] valueList = getKeyValues( keyName );  // <-- not used
			boolean keyResult = false;
       	
        	if (ConditionKeys.SecureTransport == keyName && PolicyConditions.Bool == condition)
        	{
        		if (context.getIsHTTPSecure()) keyResult = true;
        	}
			
            // -> if all key values are false, false then that key is false and then the entire condition is then false
            if (!keyResult) return false;
		}
		
		return true;

	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Set<ConditionKeys> keySet = getAllKeys();
		if (null == keySet) return "";
		Iterator<ConditionKeys> itr = keySet.iterator();
		
		value.append( condition + " (a BOOL condition): \n" );
		while( itr.hasNext()) {
			ConditionKeys keyName = itr.next();
			value.append( keyName );
			value.append( ": \n" );
			String[] valueList = getKeyValues( keyName );
			for( int i=0; i < valueList.length; i++ ) {
				if (0 < i) value.append( "\n" );
				value.append( valueList[i]);
			}
			value.append( "\n\n" );
		}
		
		return value.toString();
	}
}
