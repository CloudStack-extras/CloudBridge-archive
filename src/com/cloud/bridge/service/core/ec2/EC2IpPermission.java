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
package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

public class EC2IpPermission {
	
	private String protocol;
	private String cidr;
	private int    fromPort;
	private int    toPort;
	private List<EC2SecurityGroup> userSet  = new ArrayList<EC2SecurityGroup>();    // a list of groups identifying users 
	private List<String>           rangeSet = new ArrayList<String>();              // a list of strings identifying CIDR

	public EC2IpPermission() {
		protocol = null;
		cidr     = null;
		fromPort = 0;
		toPort   = 0;
	}
	
	public void setProtocol( String protocol ) {
		this.protocol = protocol;
	}
	
	public String getProtocol() {
		return this.protocol;
	}
	
	public void setCIDR( String cidr ) {
		this.cidr = cidr;
	}
	
	public String getCIDR() {
		return this.cidr;
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
