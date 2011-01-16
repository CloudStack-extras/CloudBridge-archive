package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

public class EC2DescribeAddressesResponse {

	private List<EC2Address> addressSet = new ArrayList<EC2Address>();    

	public EC2DescribeAddressesResponse() {
	}
	
	public void addAddress( EC2Address param ) {
		addressSet.add( param );
	}
	
	public EC2Address[] getAddressSet() {
		return addressSet.toArray(new EC2Address[0]);
	}
}
