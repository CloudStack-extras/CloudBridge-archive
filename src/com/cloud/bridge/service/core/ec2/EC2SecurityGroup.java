package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

public class EC2SecurityGroup {

	private String name;
	private String description;
	private String account;
	private List<EC2IpPermission> permissionSet = new ArrayList<EC2IpPermission>();    

	public EC2SecurityGroup() {
		name        = null;
		description = null;
		account     = null;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setDescription( String description ) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setAccount( String account ) {
		this.account = account;
	}
	
	public String getAccount() {
		return this.account;
	}
	
	public void addIpPermission( EC2IpPermission param ) {
		permissionSet.add( param );
	}
	
	public EC2IpPermission[] getIpPermissionSet() {
		return permissionSet.toArray(new EC2IpPermission[0]);
	}
}
