package com.cloud.bridge.service.core.s3;

public class S3PolicyStatement {
    private S3PolicyAction actions;
	
	public S3PolicyStatement() {
	}

	public S3PolicyAction getActions() {
		return actions;
	}
	
	public void setActions(S3PolicyAction param) {
		actions = param;
	}

}
