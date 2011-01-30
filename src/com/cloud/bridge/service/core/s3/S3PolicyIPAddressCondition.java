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

import com.cloud.bridge.util.IpAddressRange;

public class S3PolicyIPAddressCondition extends S3PolicyCondition {

	private Map<ConditionKeys,IpAddressRange[]> keys = new HashMap<ConditionKeys,IpAddressRange[]>();

	public S3PolicyIPAddressCondition() {
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
	public IpAddressRange[] getKeyValues(ConditionKeys key) {
		return keys.get(key);
	}
	
	/** 
	 * Convert the key's values into the type depending on the what the condition expects.
	 * @throws ParseException 
	 */
	public void setKey(ConditionKeys key, String[] values) throws ParseException {		
		IpAddressRange[] addresses = new IpAddressRange[ values.length ];
	
		for( int i=0; i < values.length; i++ )
	        addresses[i] = IpAddressRange.parseRange( values[i] );

	    keys.put(key, addresses);
	}
	
	public boolean isTrue(S3PolicyContext context) 
	{
		// -> improperly defined condition evaluates to false
		Set<ConditionKeys> keySet = getAllKeys();
		if (null == keySet) return false;
		Iterator<ConditionKeys> itr = keySet.iterator();
		if (!itr.hasNext()) return false;
		
		// -> returns the Internet Protocol (IP) address of the client or last proxy that sent the request. 
		//    For HTTP servlets, same as the value of the CGI variable REMOTE_ADDR. 
		IpAddressRange toCompareWith = IpAddressRange.parseRange( context.getHttp().getRemoteAddr());
		if (null == toCompareWith) return false;
			
		
		// -> all keys in a condition are ANDed together (one false one terminates the entire condition)
		while( itr.hasNext()) 
		{
			ConditionKeys keyName = itr.next();
			IpAddressRange[] valueList = getKeyValues( keyName );
			boolean keyResult = false;

			// -> stop when we hit the first true key value (i.e., key values are 'OR'ed together)
            for( int i=0; i < valueList.length && !keyResult; i++ )
            {          	
            	switch( condition ) {
        		case IpAddress: 
        			 if (valueList[i].contains( toCompareWith )) keyResult = true;
       			     break;
        		case NotIpAddres:
       			     if (!valueList[i].contains( toCompareWith )) keyResult = true;
       			     break;
		        default: 
			         return false;
            	}
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
		
		value.append( condition + " (an IP address condition): \n" );
		while( itr.hasNext()) {
			ConditionKeys keyName = itr.next();
			value.append( keyName );
			value.append( ": \n" );
			IpAddressRange[] valueList = getKeyValues( keyName );
			for( int i=0; i < valueList.length; i++ ) {
				if (0 < i) value.append( "\n" );
				value.append( valueList[i].toString());
			}
			value.append( "\n\n" );
		}
		
		return value.toString();
	}
}
