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
public class CloudStackTemplate {
	@SerializedName("id")
	private Long id;
	@SerializedName("name")
	private String name;
	@SerializedName("displaytext")
	private String displayText;
	@SerializedName("ispublic")
	private boolean isPublic;
	@SerializedName("created")
	private String created;
	@SerializedName("isready")
	private boolean isReady;
	@SerializedName("passwordenabled")
	private boolean passwordEnabled;
	@SerializedName("format")
	private String format;
	@SerializedName("isfeatured")
	private boolean isFeatured;
	@SerializedName("crossZones")
	private boolean crossZones;
	@SerializedName("ostypeid")
	private Long osTypeId;
	@SerializedName("ostypename")
	private String osTypeName;
	@SerializedName("account")
	private String account;
	@SerializedName("zoneid")
	private Long zoneId;
	@SerializedName("zonename")
	private String zoneName;
	@SerializedName("status")
	private String status;
	@SerializedName("size")
	private Long size;
	@SerializedName("templatetype")
	private String templateType;
	@SerializedName("hypervisor")
	private String hyperVisor;
	@SerializedName("domain")
	private String domain;
	@SerializedName("domainid")
	private Long domainId;
	@SerializedName("isextractable")
	private boolean isExtractable;
	@SerializedName("checksum")
	private String checksum;

	/**
	 * 
	 */
	public CloudStackTemplate() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * @return the isPublic
	 */
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * @param isPublic the isPublic to set
	 */
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the isReady
	 */
	public boolean isReady() {
		return isReady;
	}

	/**
	 * @param isReady the isReady to set
	 */
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	/**
	 * @return the passwordEnabled
	 */
	public boolean isPasswordEnabled() {
		return passwordEnabled;
	}

	/**
	 * @param passwordEnabled the passwordEnabled to set
	 */
	public void setPasswordEnabled(boolean passwordEnabled) {
		this.passwordEnabled = passwordEnabled;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the isFeatured
	 */
	public boolean isFeatured() {
		return isFeatured;
	}

	/**
	 * @param isFeatured the isFeatured to set
	 */
	public void setFeatured(boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	/**
	 * @return the crossZones
	 */
	public boolean isCrossZones() {
		return crossZones;
	}

	/**
	 * @param crossZones the crossZones to set
	 */
	public void setCrossZones(boolean crossZones) {
		this.crossZones = crossZones;
	}

	/**
	 * @return the ostypeid
	 */
	public Long getOsTypeId() {
		return osTypeId;
	}

	/**
	 * @param ostypeid the ostypeid to set
	 */
	public void setOsTypeId(Long ostypeid) {
		this.osTypeId = ostypeid;
	}

	/**
	 * @return the osTypeName
	 */
	public String getOsTypeName() {
		return osTypeName;
	}

	/**
	 * @param osTypeName the osTypeName to set
	 */
	public void setOsTypeName(String osTypeName) {
		this.osTypeName = osTypeName;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the zoneId
	 */
	public Long getZoneId() {
		return zoneId;
	}

	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}

	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	/**
	 * @return the hyperVisor
	 */
	public String getHyperVisor() {
		return hyperVisor;
	}

	/**
	 * @param hyperVisor the hyperVisor to set
	 */
	public void setHyperVisor(String hyperVisor) {
		this.hyperVisor = hyperVisor;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the domainId
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * @param domainId the domainId to set
	 */
	public void setDomainId(Long domainId) {
		this.domainId = domainId;
	}

	/**
	 * @return the isExtractable
	 */
	public boolean isExtractable() {
		return isExtractable;
	}

	/**
	 * @param isExtractable the isExtractable to set
	 */
	public void setExtractable(boolean isExtractable) {
		this.isExtractable = isExtractable;
	}

	/**
	 * @return the checksum
	 */
	public String getChecksum() {
		return checksum;
	}

	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
