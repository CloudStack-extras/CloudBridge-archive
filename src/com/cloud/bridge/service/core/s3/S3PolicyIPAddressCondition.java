package com.cloud.bridge.service.core.s3;

import java.net.InetAddress;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class S3PolicyIPAddressCondition extends S3PolicyCondition {

	private Map<ConditionKeys,String[]> keys = new HashMap<ConditionKeys,String[]>();

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
	public String[] getKeyValues(ConditionKeys key) {
		return keys.get(key);
	}
	
	/** 
	 * Convert the key's values into the type depending on the what
	 * the condition expects.
	 * @throws ParseException 
	 */
	public void setKey(ConditionKeys key, String[] values) throws ParseException {		
		
		//try {
		//InetAddress addr = InetAddress.getByAddress( values[0], null );
		//}
		//catch( Exception e ) {
		//   System.out.println( "test ip address conversion: " + e.toString());	
		//}
		
	    keys.put(key, values);
	}
	
	public boolean isTrue() {
		// TODO - implement each type of comparison
		switch( condition ) {
		case IpAddress: 
			 break;
		case NotIpAddres:
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
