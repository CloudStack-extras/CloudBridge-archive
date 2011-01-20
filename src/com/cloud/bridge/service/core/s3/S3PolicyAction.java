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
