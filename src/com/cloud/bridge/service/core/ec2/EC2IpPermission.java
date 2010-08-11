package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

public class EC2IpPermission {
	
	private String protocol;
	private int    fromPort;
	private int    toPort;
	private List<EC2SecurityGroup> userSet  = new ArrayList<EC2SecurityGroup>();    // a list of groups identifying users 
	private List<String>           rangeSet = new ArrayList<String>();              // a list of strings identifying CIDR

	public EC2IpPermission() {
		protocol = null;
		fromPort = 0;
		toPort   = 0;
	}
	
	public void setProtocol( String protocol ) {
		this.protocol = protocol;
	}
	
	public String getProtocol() {
		return this.protocol;
	}
	
	public void setFromPort( int fromPort ) {
		this.fromPort = fromPort;
	}
	
	public int getFromPort() {
		return this.fromPort;
	}
	
	public void setToPort( int toPort ) {
		this.toPort = toPort;
	}

	public int getToPort() {
		return this.toPort;
	}
	
	public void addUser( EC2SecurityGroup param ) {
		userSet.add( param );
	}
	
	public EC2SecurityGroup[] getUserSet() {
		return userSet.toArray(new EC2SecurityGroup[0]);
	}

	public void addIpRange( String param ) {
		rangeSet.add( param );
	}
	
	public String[] getIpRangeSet() {
		return rangeSet.toArray(new String[0]);
	}
}
