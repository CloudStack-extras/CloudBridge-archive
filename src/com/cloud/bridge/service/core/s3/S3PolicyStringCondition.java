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

import com.cloud.bridge.service.core.s3.S3PolicyCondition.ConditionKeys;

public class S3PolicyStringCondition extends S3PolicyCondition {

	private Map<ConditionKeys,String[]> keys = new HashMap<ConditionKeys,String[]>();

	public S3PolicyStringCondition() {
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
	 * Convert the key's values into the type depending on the what
	 * the condition expects.
	 * @throws ParseException 
	 */
	public void setKey(ConditionKeys key, String[] values) throws ParseException {		
	    keys.put(key, values);
	}
	
	public boolean isTrue(HttpServletRequest request) 
	{	
		// -> improperly defined condition evaluates to false
		Set<ConditionKeys> keySet = getAllKeys();
		if (null == keySet) return false;
		Iterator<ConditionKeys> itr = keySet.iterator();
		if (!itr.hasNext()) return false;
		
		while( itr.hasNext()) 
		{
			ConditionKeys keyName = itr.next();
			String[] valueList = getKeyValues( keyName );
			String toCompareWith = null;
			boolean keyResult = false;
			
			// -> stop when we hit the first true key value (i.e., key values are 'OR'ed together)
            for( int i=0; i < valueList.length && !keyResult; i++ )
            {
            	     if (ConditionKeys.UserAgent == keyName) toCompareWith = request.getHeader( "User-Agent" );
            	else if (ConditionKeys.Referer   == keyName) toCompareWith = request.getHeader( "Referer" );
            	else continue;
            	if (null == toCompareWith) continue;
            		
            	switch( condition ) {
        		case StringEquals:
        			 if (valueList[i].equals( toCompareWith )) keyResult = true;
       			     break;
       		    case StringNotEquals:
       			     if (!valueList[i].equals( toCompareWith )) keyResult = true;
       			     break;
       		    case StringEqualsIgnoreCase:
       			     if (valueList[i].equalsIgnoreCase( toCompareWith )) keyResult = true;
       			     break;
       		    case StringNotEqualsIgnoreCase:
      			     if (!valueList[i].equalsIgnoreCase( toCompareWith )) keyResult = true;
       			     break;
       		    case StringLike:
       		    	 // TODO need to use regex here as done in resource matching
       			     break;		 
       		    case StringNotLike:
      		    	 // TODO need to use regex here as done in resource matching
       			     break;		 
		        default: 
			         return false;
            	}
            }
            
            // -> if all key values are, false then that key is false and then the entire condition is then false
            if (!keyResult) return false;
		}
		
		return true;
	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Set<ConditionKeys> keySet = getAllKeys();
		if (null == keySet) return "";
		Iterator<ConditionKeys> itr = keySet.iterator();
		
		value.append( condition + ": \n" );
		while( itr.hasNext()) {
			ConditionKeys keyName = itr.next();
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
