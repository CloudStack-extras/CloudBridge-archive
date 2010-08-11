package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

public class EC2DescribeSecurityGroups {
	
	private List<String> groupSet = new ArrayList<String>();    // a list of strings identifying each group

	public EC2DescribeSecurityGroups() {
	}

	public void addGroupName( String param ) {
		groupSet.add( param );
	}
	
	public String[] getGroupSet() {
		return groupSet.toArray(new String[0]);
	}
}
