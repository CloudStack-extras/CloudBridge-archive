/**
 * 
 */
package com.cloud.bridge.service.core.s3;

/**
 * @author Mark
 *
 */
public class S3CopyObjectRequest extends S3Request {
	public enum MetadataDirective { COPY, REPLACE  };
	
	protected String sourceBucketName;
	protected String sourceKey;
	protected String destinationBucketName;
	protected String destinationKey;
	protected MetadataDirective directive;
	protected S3MetaDataEntry[] metaEntries;
	protected S3AccessControlList acl;

	public S3CopyObjectRequest() {
		super();
		directive = MetadataDirective.COPY;
	}
	
	public String getSourceBucketName() {
		return sourceBucketName;
	}

	public void setSourceBucketName(String bucketName) {
		sourceBucketName = bucketName;
	}
	
	public String getDestinationBucketName() {
		return destinationBucketName;
	}

	public void setDestinationBucketName(String bucketName) {
		destinationBucketName = bucketName;
	}
	
	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String key) {
		sourceKey = key;
	}

	public String getDestinationKey() {
		return destinationKey;
	}

	public void setDestinationKey(String key) {
		destinationKey = key;
	}
	
	public MetadataDirective getDirective() {
	    return directive;	
	}
	
	public void setDataDirective(String dataDirective) {
		if (null == dataDirective) return;
		
	     	 if (dataDirective.equalsIgnoreCase( "COPY"    )) directive = MetadataDirective.COPY;
		else if (dataDirective.equalsIgnoreCase( "REPLACE" )) directive = MetadataDirective.REPLACE;
		else throw new UnsupportedOperationException("Unknown Metadata Directive: " + dataDirective );
	}
	
	public S3MetaDataEntry[] getMetaEntries() {
		return metaEntries;
	}
	
	public void setMetaEntries(S3MetaDataEntry[] metaEntries) {
		this.metaEntries = metaEntries;
	}

	public S3AccessControlList getAcl() {
		return acl;
	}
	
	public void setAcl(S3AccessControlList acl) {
		this.acl = acl;
	}
}
