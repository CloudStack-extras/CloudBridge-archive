package com.cloud.bridge.service.core.s3;

import java.util.Calendar;

public class S3MultipartPart {
	private int partNumber = -1;
	private Calendar lastModified = null;
	private String eTag = null;
	private int size = -1;
	
	public S3MultipartPart() {
	}

	public int getPartNumber() {
		return partNumber;
	}
	
	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}
	
	public Calendar getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(Calendar lastModified) {
	    this.lastModified = lastModified;	
	}
	
	public String getETag() {
		return eTag;
	}
	
	public void setEtag(String eTag) {
		this.eTag = eTag;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
}
