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

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author slriv
 *
 */
public class CloudStackNetwork {
	@SerializedName("id")
	private Long id;
	@SerializedName("name")
	private String name;
	@SerializedName("displaytext")
	private String displaytext;
	@SerializedName("broadcastdomaintype")
	private String broadcastDomainType;
	@SerializedName("traffictype")
	private String trafficType;
	@SerializedName("zoneid")
	private Long zoneId;
	@SerializedName("networkofferingid")
	private Long networkOfferingId;
	@SerializedName("networkofferingname")
	private String networkOfferingName;
	@SerializedName("networkofferingdisplaytext")
	private String networkOfferingDisplayText;
    @SerializedName("networkofferingavailability")
    private String networkOfferingAvailability;
    @SerializedName("isshared")
    private Boolean isShared;
    @SerializedName("issystem")
    private Boolean isSystem;
    @SerializedName("state")
    private String state;
    @SerializedName("related")
    private Long related;
    @SerializedName("dns1")
    private String dns1;
    @SerializedName("type")
    private String type;
    @SerializedName("account")
    private String account;
    @SerializedName("domainid")
    private Long domainId;
    @SerializedName("domain")
    private String domain;
    @SerializedName("isdefault")
    private Boolean isDefault;
    @SerializedName("networkdomain")
    private String networkDomain;
    @SerializedName("securitygroupenabled")
    private Boolean securityGroupEnabled;
    @SerializedName("service")
    private List<CloudStackNetworkService> services;
    
	

	/**
	 * 
	 */
	public CloudStackNetwork() {
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
	 * @return the displaytext
	 */
	public String getDisplaytext() {
		return displaytext;
	}



	/**
	 * @return the broadcastDomainType
	 */
	public String getBroadcastDomainType() {
		return broadcastDomainType;
	}



	/**
	 * @return the trafficType
	 */
	public String getTrafficType() {
		return trafficType;
	}



	/**
	 * @return the zoneId
	 */
	public Long getZoneId() {
		return zoneId;
	}



	/**
	 * @return the networkOfferingId
	 */
	public Long getNetworkOfferingId() {
		return networkOfferingId;
	}



	/**
	 * @return the networkOfferingName
	 */
	public String getNetworkOfferingName() {
		return networkOfferingName;
	}



	/**
	 * @return the networkOfferingDisplayText
	 */
	public String getNetworkOfferingDisplayText() {
		return networkOfferingDisplayText;
	}



	/**
	 * @return the networkOfferingAvailability
	 */
	public String getNetworkOfferingAvailability() {
		return networkOfferingAvailability;
	}



	/**
	 * @return the isShared
	 */
	public Boolean getIsShared() {
		return isShared;
	}



	/**
	 * @return the isSystem
	 */
	public Boolean getIsSystem() {
		return isSystem;
	}



	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}



	/**
	 * @return the related
	 */
	public Long getRelated() {
		return related;
	}



	/**
	 * @return the dns1
	 */
	public String getDns1() {
		return dns1;
	}



	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}



	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
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
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}



	/**
	 * @return the networkDomain
	 */
	public String getNetworkDomain() {
		return networkDomain;
	}



	/**
	 * @return the securityGroupEnabled
	 */
	public Boolean getSecurityGroupEnabled() {
		return securityGroupEnabled;
	}



	/**
	 * @return the services
	 */
	public List<CloudStackNetworkService> getServices() {
		return services;
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
	 * @param displaytext the displaytext to set
	 */
	public void setDisplaytext(String displaytext) {
		this.displaytext = displaytext;
	}



	/**
	 * @param broadcastDomainType the broadcastDomainType to set
	 */
	public void setBroadcastDomainType(String broadcastDomainType) {
		this.broadcastDomainType = broadcastDomainType;
	}



	/**
	 * @param trafficType the trafficType to set
	 */
	public void setTrafficType(String trafficType) {
		this.trafficType = trafficType;
	}



	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}



	/**
	 * @param networkOfferingId the networkOfferingId to set
	 */
	public void setNetworkOfferingId(Long networkOfferingId) {
		this.networkOfferingId = networkOfferingId;
	}



	/**
	 * @param networkOfferingName the networkOfferingName to set
	 */
	public void setNetworkOfferingName(String networkOfferingName) {
		this.networkOfferingName = networkOfferingName;
	}



	/**
	 * @param networkOfferingDisplayText the networkOfferingDisplayText to set
	 */
	public void setNetworkOfferingDisplayText(String networkOfferingDisplayText) {
		this.networkOfferingDisplayText = networkOfferingDisplayText;
	}



	/**
	 * @param networkOfferingAvailability the networkOfferingAvailability to set
	 */
	public void setNetworkOfferingAvailability(String networkOfferingAvailability) {
		this.networkOfferingAvailability = networkOfferingAvailability;
	}



	/**
	 * @param isShared the isShared to set
	 */
	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}



	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}



	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}



	/**
	 * @param related the related to set
	 */
	public void setRelated(Long related) {
		this.related = related;
	}



	/**
	 * @param dns1 the dns1 to set
	 */
	public void setDns1(String dns1) {
		this.dns1 = dns1;
	}



	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}



	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
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
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}



	/**
	 * @param networkDomain the networkDomain to set
	 */
	public void setNetworkDomain(String networkDomain) {
		this.networkDomain = networkDomain;
	}



	/**
	 * @param securityGroupEnabled the securityGroupEnabled to set
	 */
	public void setSecurityGroupEnabled(Boolean securityGroupEnabled) {
		this.securityGroupEnabled = securityGroupEnabled;
	}



	/**
	 * @param services the services to set
	 */
	public void setServices(List<CloudStackNetworkService> services) {
		this.services = services;
	}

}
