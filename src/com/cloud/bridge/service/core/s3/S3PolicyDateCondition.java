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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import com.cloud.bridge.util.DateHelper;

public class S3PolicyDateCondition extends S3PolicyCondition {

	private Map<ConditionKeys,Calendar[]> keys = new HashMap<ConditionKeys,Calendar[]>();

	public S3PolicyDateCondition() {
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
	 * @return Calendar[]
	 */
	public Calendar[] getKeyValues(ConditionKeys key) {
		return keys.get(key);
	}
	
	/** 
	 * Convert the key's values into the type depending on the what
	 * the condition expects.
	 * @throws ParseException 
	 */
	public void setKey(ConditionKeys key, String[] values) throws ParseException {
		Calendar[] dates = new Calendar[ values.length ];
		
	    for( int i=0; i < values.length; i++ )
		     dates[i] = DateHelper.toCalendar( DateHelper.parseISO8601DateString( values[i] ));
		
	    keys.put(key, dates);
	}
	
	public boolean isTrue() {
		// TODO - implement each type of comparison
		switch( condition ) {
		case DateEquals:                 
			 break;
		case DateNotEquals:              
			 break;
		case DateLessThan:               
			 break;
		case DateLessThanEquals:        
			 break;
		case DateGreaterThan:            
			 break;
		case DateGreaterThanEquals:      
			 break;
		default: 
			return false;
		}
		
		return false;
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
			Calendar[] valueList = getKeyValues( keyName );
			for( int i=0; i < valueList.length; i++ ) {
				if (0 < i) value.append( "\n" );
				value.append( DatatypeConverter.printDateTime( valueList[i] ));
			}
			value.append( "\n\n" );
		}
		
		return value.toString();
	}
}
