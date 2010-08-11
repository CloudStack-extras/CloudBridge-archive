package com.cloud.bridge.service.core.ec2;

public class EC2SecurityGroup {

	private String name;
	private String description;
	private String account;

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
}
