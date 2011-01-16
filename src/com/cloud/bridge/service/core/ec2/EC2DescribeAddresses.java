package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

public class EC2DescribeAddresses {

	private List<String> publicIpsSet = new ArrayList<String>();

	public EC2DescribeAddresses() {
	}

	public void addPublicIp( String param ) {
		publicIpsSet.add( param );
	}
	
	public String[] getPublicIpsSet() {
		return publicIpsSet.toArray(new String[0]);
	}
}
