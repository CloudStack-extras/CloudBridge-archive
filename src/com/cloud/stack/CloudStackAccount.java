/*
 * Copyright (C) 2011 Citrix Systems, Inc.  All rights reserved.
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

package com.cloud.stack;

import com.google.gson.annotations.SerializedName;

/**
 * @author slriv
 *
 */
public class CloudStackAccount {
	@SerializedName("id")
	private Long id;
	@SerializedName("name")
	private String name;
	@SerializedName("accounttype")
	private Long accountType;
	@SerializedName("domainid")
	private Long domainId;
	@SerializedName("domain")
	private String domain;
	@SerializedName("receivedbytes")
	private Long receivedBytes;
	@SerializedName("sendbytes")
	private Long sentBytes;
	@SerializedName("vmlimit")
	private String vmLimit;
	@SerializedName("vmtotal")
	private Long vmTotal;
	@SerializedName("vmavailable")
	private String vmAvailable;
	@SerializedName("iplimit")
	private String ipLimit;
	@SerializedName("iptotal")
	private Long ipTotal;
	@SerializedName("ipavailable")
	private String ipAvailable;
	@SerializedName("volumelimit")
	private String volumeLimit;
	@SerializedName("volumetotal")
	private Long volumeTotal;
	@SerializedName("volumeavailable")
	private String volumeAvailable;
	@SerializedName("snapshotlimit")
	private String snapShotLimit;
	@SerializedName("snapshottotal")
	private Long snapShotTotal;
	@SerializedName("snapshotavailable")
	private String snapShotAvailable;
	@SerializedName("templatelimit")
	private String templateLimit;
	@SerializedName("templatetotal")
	private String templateTotal;
	@SerializedName("templateavailable")
	private String templateAvailable;
	@SerializedName("vmstopped")
	private Long vmStopped;
	@SerializedName("vmrunning")
	private Long vmRunning;
	@SerializedName("enabled")
	private String enabled;
	@SerializedName("user")
	private CloudStackUser[] user;
	
	/**
	 * 
	 */
	public CloudStackAccount() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the accountType
	 */
	public Long getAccountType() {
		return accountType;
	}

	/**
	 * @return the domainId
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @return the receivedBytes
	 */
	public Long getReceivedBytes() {
		return receivedBytes;
	}

	/**
	 * @return the sentBytes
	 */
	public Long getSentBytes() {
		return sentBytes;
	}

	/**
	 * @return the vmLimit
	 */
	public String getVmLimit() {
		return vmLimit;
	}

	/**
	 * @return the vmTotal
	 */
	public Long getVmTotal() {
		return vmTotal;
	}

	/**
	 * @return the vmAvailable
	 */
	public String getVmAvailable() {
		return vmAvailable;
	}

	/**
	 * @return the ipLimit
	 */
	public String getIpLimit() {
		return ipLimit;
	}

	/**
	 * @return the ipTotal
	 */
	public Long getIpTotal() {
		return ipTotal;
	}

	/**
	 * @return the ipAvailable
	 */
	public String getIpAvailable() {
		return ipAvailable;
	}

	/**
	 * @return the volumeLimit
	 */
	public String getVolumeLimit() {
		return volumeLimit;
	}

	/**
	 * @return the volumeTotal
	 */
	public Long getVolumeTotal() {
		return volumeTotal;
	}

	/**
	 * @return the volumeAvailable
	 */
	public String getVolumeAvailable() {
		return volumeAvailable;
	}

	/**
	 * @return the snapShotLimit
	 */
	public String getSnapShotLimit() {
		return snapShotLimit;
	}

	/**
	 * @return the snapShotTotal
	 */
	public Long getSnapShotTotal() {
		return snapShotTotal;
	}

	/**
	 * @return the snapShotAvailable
	 */
	public String getSnapShotAvailable() {
		return snapShotAvailable;
	}

	/**
	 * @return the templateLimit
	 */
	public String getTemplateLimit() {
		return templateLimit;
	}

	/**
	 * @return the templateTotal
	 */
	public String getTemplateTotal() {
		return templateTotal;
	}

	/**
	 * @return the templateAvailable
	 */
	public String getTemplateAvailable() {
		return templateAvailable;
	}

	/**
	 * @return the vmStopped
	 */
	public Long getVmStopped() {
		return vmStopped;
	}

	/**
	 * @return the vmRunning
	 */
	public Long getVmRunning() {
		return vmRunning;
	}

	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @return the user
	 */
	public CloudStackUser[] getUser() {
		return user;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(Long accountType) {
		this.accountType = accountType;
	}

	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @param receivedBytes the receivedBytes to set
	 */
	public void setReceivedBytes(Long receivedBytes) {
		this.receivedBytes = receivedBytes;
	}

	/**
	 * @param sentBytes the sentBytes to set
	 */
	public void setSentBytes(Long sentBytes) {
		this.sentBytes = sentBytes;
	}

	/**
	 * @param vmLimit the vmLimit to set
	 */
	public void setVmLimit(String vmLimit) {
		this.vmLimit = vmLimit;
	}

	/**
	 * @param vmTotal the vmTotal to set
	 */
	public void setVmTotal(Long vmTotal) {
		this.vmTotal = vmTotal;
	}

	/**
	 * @param vmAvailable the vmAvailable to set
	 */
	public void setVmAvailable(String vmAvailable) {
		this.vmAvailable = vmAvailable;
	}

	/**
	 * @param ipLimit the ipLimit to set
	 */
	public void setIpLimit(String ipLimit) {
		this.ipLimit = ipLimit;
	}

	/**
	 * @param ipTotal the ipTotal to set
	 */
	public void setIpTotal(Long ipTotal) {
		this.ipTotal = ipTotal;
	}

	/**
	 * @param ipAvailable the ipAvailable to set
	 */
	public void setIpAvailable(String ipAvailable) {
		this.ipAvailable = ipAvailable;
	}

	/**
	 * @param volumeLimit the volumeLimit to set
	 */
	public void setVolumeLimit(String volumeLimit) {
		this.volumeLimit = volumeLimit;
	}

	/**
	 * @param volumeTotal the volumeTotal to set
	 */
	public void setVolumeTotal(Long volumeTotal) {
		this.volumeTotal = volumeTotal;
	}

	/**
	 * @param volumeAvailable the volumeAvailable to set
	 */
	public void setVolumeAvailable(String volumeAvailable) {
		this.volumeAvailable = volumeAvailable;
	}

	/**
	 * @param snapShotLimit the snapShotLimit to set
	 */
	public void setSnapShotLimit(String snapShotLimit) {
		this.snapShotLimit = snapShotLimit;
	}

	/**
	 * @param snapShotTotal the snapShotTotal to set
	 */
	public void setSnapShotTotal(Long snapShotTotal) {
		this.snapShotTotal = snapShotTotal;
	}

	/**
	 * @param snapShotAvailable the snapShotAvailable to set
	 */
	public void setSnapShotAvailable(String snapShotAvailable) {
		this.snapShotAvailable = snapShotAvailable;
	}

	/**
	 * @param templateLimit the templateLimit to set
	 */
	public void setTemplateLimit(String templateLimit) {
		this.templateLimit = templateLimit;
	}

	/**
	 * @param templateTotal the templateTotal to set
	 */
	public void setTemplateTotal(String templateTotal) {
		this.templateTotal = templateTotal;
	}

	/**
	 * @param templateAvailable the templateAvailable to set
	 */
	public void setTemplateAvailable(String templateAvailable) {
		this.templateAvailable = templateAvailable;
	}

	/**
	 * @param vmStopped the vmStopped to set
	 */
	public void setVmStopped(Long vmStopped) {
		this.vmStopped = vmStopped;
	}

	/**
	 * @param vmRunning the vmRunning to set
	 */
	public void setVmRunning(Long vmRunning) {
		this.vmRunning = vmRunning;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(CloudStackUser[] user) {
		this.user = user;
	}

}
