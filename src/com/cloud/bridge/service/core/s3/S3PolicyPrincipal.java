package com.cloud.bridge.service.core.s3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class S3PolicyPrincipal {

	private List<String> principalList = new ArrayList<String>();
	
	public S3PolicyPrincipal() {
	}

	public String[] getPrincipals() {
		return principalList.toArray(new String[0]);
	}
	
	public void addPrincipal(String param) {
		principalList.add( param );
	}

	public String toString() {
		
		StringBuffer value = new StringBuffer();
		Iterator<String> itr = principalList.iterator();
		
		value.append( "Principals: \n" );
		while( itr.hasNext()) {
			String onePrincipal = itr.next();
			value.append( onePrincipal );
			value.append( "\n" );
		}
		
		return value.toString();
	}
}
