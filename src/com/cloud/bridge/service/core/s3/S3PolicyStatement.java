package com.cloud.bridge.service.core.s3;

public class S3PolicyStatement {
	private String sid;
	private String effect;
	private S3PolicyPrincipal principals;
    private S3PolicyAction actions;
    private String resource;
	
	public S3PolicyStatement() {
	}

	public S3PolicyAction getActions() {
		return actions;
	}
	
	public void setActions(S3PolicyAction param) {
		actions = param;
	}

	public S3PolicyPrincipal getPrincipals() {
		return principals;
	}
	
	public void setPrincipals(S3PolicyPrincipal param) {
		principals = param;
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String param) {
		sid = param;
	}

	public String getEffect() {
		return effect;
	}
	
	public void setEffect(String param) {
		effect = param;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String param) {
		resource = param;
	}
	
	public String toString() {
		
		StringBuffer value = new StringBuffer();	
		value.append( "Statement: \n");
		if (null != sid     ) value.append( "Sid: " + sid + "\n" );
		if (null != effect  ) value.append( "Effect: "  + effect + "\n" );
		if (null != principals) value.append( principals.toString());
		if (null != actions) value.append( actions.toString());
		if (null != resource) value.append( "Resource: " + resource + "\n" );
		return value.toString();
	}
}
