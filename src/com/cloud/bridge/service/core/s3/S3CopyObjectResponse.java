package com.cloud.bridge.service.core.s3;

import java.util.Calendar;

public class S3CopyObjectResponse  extends S3Response  {
	protected String ETag;
	protected Calendar lastModified;
	
	public S3CopyObjectResponse() {
		super();
	}

	public String getETag() {
		return ETag;
	}

	public void setETag(String eTag) {
		this.ETag = eTag;
	}

	public Calendar getLastModified() {
		return lastModified;
	}

	public void setLastModified(Calendar lastModified) {
		this.lastModified = lastModified;
	}
}