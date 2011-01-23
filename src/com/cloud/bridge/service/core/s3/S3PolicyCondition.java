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

import com.cloud.bridge.service.core.s3.S3PolicyAction.PolicyActions;

/**
 * In the Bucket Policy language a condition block can hold one or more conditions.
 * A condition has one or more keys, where each key can have one or more values.
 */
public class S3PolicyCondition {

	public enum PolicyConditions {
		UnknownCondition,
		StringEquals, StringNotEquals, StringEqualsIgnoreCase, StringNotEqualsIgnoreCase,
		StringLike,	StringNotLike,
		NumericEquals, NumericNotEquals, NumericLessThan, NumericLessThanEquals,
		NumericGreaterThan,	NumericGreaterThanEquals, 
		DateEquals, DateNotEquals, DateLessThan, DateLessThanEquals,
		DateGreaterThan, DateGreaterThanEquals, 
		Bool,
		IpAddress, NotIpAddres,
		ArnEquals, ArnNotEquals, ArnLike, ArnNotLike 
	}
	
	// -> map a string name into a policy condition constant
	private Map<String,PolicyConditions> conditionNames = new HashMap<String,PolicyConditions>();
	
	private PolicyConditions condition = null;   
	private Map<String,String[]> keys = new HashMap<String,String[]>();
	
	public S3PolicyCondition() {
		conditionNames.put("StringEquals",              PolicyConditions.StringEquals);
		conditionNames.put("streq",                     PolicyConditions.StringEquals);
		conditionNames.put("StringNotEquals",           PolicyConditions.StringNotEquals);
		conditionNames.put("strneq",                    PolicyConditions.StringNotEquals);
		conditionNames.put("StringEqualsIgnoreCase",    PolicyConditions.StringEqualsIgnoreCase);
		conditionNames.put("streqi",                    PolicyConditions.StringEqualsIgnoreCase);		
		conditionNames.put("StringNotEqualsIgnoreCase", PolicyConditions.StringNotEqualsIgnoreCase);
		conditionNames.put("strneqi",                   PolicyConditions.StringNotEqualsIgnoreCase);	
		conditionNames.put("StringLike",                PolicyConditions.StringLike);
		conditionNames.put("strl",                      PolicyConditions.StringLike);		
		conditionNames.put("StringNotLike",             PolicyConditions.StringNotLike);
		conditionNames.put("strnl",                     PolicyConditions.StringNotLike);

		conditionNames.put("NumericEquals",             PolicyConditions.NumericEquals);
		conditionNames.put("numeq",                     PolicyConditions.NumericEquals);
		conditionNames.put("NumericNotEquals",          PolicyConditions.NumericNotEquals);
		conditionNames.put("numneq",                    PolicyConditions.NumericNotEquals);
		conditionNames.put("NumericLessThan",           PolicyConditions.NumericLessThan);
		conditionNames.put("numlt",                     PolicyConditions.NumericLessThan);
		conditionNames.put("NumericLessThanEquals",     PolicyConditions.NumericLessThanEquals);
		conditionNames.put("numlteq",                   PolicyConditions.NumericLessThanEquals);
		conditionNames.put("NumericGreaterThan",        PolicyConditions.NumericGreaterThan);
		conditionNames.put("numgt",                     PolicyConditions.NumericGreaterThan);
		conditionNames.put("NumericGreaterThanEquals",  PolicyConditions.NumericGreaterThanEquals);
		conditionNames.put("numgteq",                   PolicyConditions.NumericGreaterThanEquals);
		
		conditionNames.put("DateEquals",                PolicyConditions.DateEquals);
		conditionNames.put("dateeq",                    PolicyConditions.DateEquals);
		conditionNames.put("DateNotEquals",             PolicyConditions.DateNotEquals);
		conditionNames.put("dateneq",                   PolicyConditions.DateNotEquals);
		conditionNames.put("DateLessThan",              PolicyConditions.DateLessThan);
		conditionNames.put("datelt",                    PolicyConditions.DateLessThan);
		conditionNames.put("DateLessThanEquals",        PolicyConditions.DateLessThanEquals);
		conditionNames.put("datelteq",                  PolicyConditions.DateLessThanEquals);
		conditionNames.put("DateGreaterThan",           PolicyConditions.DateGreaterThan);
		conditionNames.put("dategt",                    PolicyConditions.DateGreaterThan);
		conditionNames.put("DateGreaterThanEquals",     PolicyConditions.DateGreaterThanEquals);
		conditionNames.put("dategteq",                  PolicyConditions.DateGreaterThanEquals);

		conditionNames.put("Bool",                      PolicyConditions.Bool);
		conditionNames.put("IpAddress",                 PolicyConditions.IpAddress);
		conditionNames.put("NotIpAddres",               PolicyConditions.NotIpAddres);

		conditionNames.put("ArnEquals",                 PolicyConditions.ArnEquals);
		conditionNames.put("arneq",                     PolicyConditions.ArnEquals);
		conditionNames.put("ArnNotEquals",              PolicyConditions.ArnNotEquals);
		conditionNames.put("arnneq",                    PolicyConditions.ArnNotEquals);
		conditionNames.put("ArnLike",                   PolicyConditions.ArnLike);
		conditionNames.put("arnl",                      PolicyConditions.ArnLike);
		conditionNames.put("ArnNotLike",                PolicyConditions.ArnNotLike);
		conditionNames.put("arnnl",                     PolicyConditions.ArnNotLike);
	}
	
	public PolicyConditions getConition() {
		return condition;
	}
	
	public void setCondition(PolicyConditions param) {
		condition = param;
	}
	
	public PolicyConditions toPolicyConditions(String operation) {
		
		Object value = conditionNames.get( operation );

		if ( null == value ) 
			 return PolicyConditions.UnknownCondition;
		else return (PolicyConditions)value;
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
	
	public boolean isTrue() {
		// TODO - implement each type of comparison
		switch( condition ) {
		case StringEquals:               break;
		case StringNotEquals:            break;
		case StringEqualsIgnoreCase:     break;
		case StringNotEqualsIgnoreCase:  break;
		case StringLike:                 break;
		case StringNotLike:              break;
		case NumericEquals:              break;
		case NumericNotEquals:           break;
		case NumericLessThan:            break;
		case NumericLessThanEquals:      break;
		case NumericGreaterThan:	     break;
		case NumericGreaterThanEquals:   break; 
		case DateEquals:                 break;
		case DateNotEquals:              break;
		case DateLessThan:               break;
		case DateLessThanEquals:         break;
		case DateGreaterThan:            break;
		case DateGreaterThanEquals:      break;
		case Bool:                       break;
		case IpAddress:                  break;
		case NotIpAddres:                break;
		case ArnEquals:                  break;
		case ArnNotEquals:               break;
		case ArnLike:                    break;
		case ArnNotLike:                 break;
		}
		
		return false;
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
