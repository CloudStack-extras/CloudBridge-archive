package com.cloud.bridge.model;

import java.io.Serializable;

public class SSHKey implements Serializable {
	private static final long serialVersionUID = -6990524591652681885L;
	
	private Long id;
	private String accountName;
	private String keyName;
	private String publicKey;
	private String fingerprint;
	
	public SSHKey() {}

	public SSHKey(String accountName, String keyName, String publicKey, String fingerprint) {
		this.accountName = accountName;
		this.keyName = keyName;
		this.publicKey = publicKey;
		this.fingerprint = fingerprint;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getKeyName() {
		return keyName;
	}
	
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public String getFingerprint() {
		return fingerprint;
	}
	
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
