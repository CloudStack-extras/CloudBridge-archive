package com.cloud.bridge.service.core.s3;

import java.util.ArrayList;
import java.util.List;

public class S3PolicyAction {

	private List<String> actionList = new ArrayList<String>();
	
	public S3PolicyAction() {
	}

	public String[] getActions() {
		return actionList.toArray(new String[0]);
	}
	
	public void setAction(String param) {
		actionList.add( param );
	}
}
