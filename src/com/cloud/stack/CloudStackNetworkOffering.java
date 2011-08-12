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
public class CloudStackNetworkOffering {
	@SerializedName("id")	
	private Long id;
	@SerializedName("availability")	
	private String availability;
	@SerializedName("created")	
	private String created;
	@SerializedName("displaytext")	
	private String displayText;
	@SerializedName("guestiptype")	
	private String guestIpType;
	@SerializedName("isdefault")	
	private Boolean isDefault;
	@SerializedName("maxconnections")	
	private Long maxconnections;
	@SerializedName("name")	
	private String name;
	@SerializedName("networkrate")	
	private Long networkRate;
	@SerializedName("specifyvlan")	
	private Boolean specifyVlan;
	@SerializedName("tags")	
	private String tags;
	@SerializedName("traffictype")	
	private String traffictype;
	
	/**
	 * 
	 */
	public CloudStackNetworkOffering() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the availability
	 */
	public String getAvailability() {
		return availability;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * @return the guestIpType
	 */
	public String getGuestIpType() {
		return guestIpType;
	}

	/**
	 * @return the isDefault
	 */
	public Boolean getIsDefault() {
		return isDefault;
	}

	/**
	 * @return the maxconnections
	 */
	public Long getMaxconnections() {
		return maxconnections;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the networkRate
	 */
	public Long getNetworkRate() {
		return networkRate;
	}

	/**
	 * @return the specifyVlan
	 */
	public Boolean getSpecifyVlan() {
		return specifyVlan;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @return the traffictype
	 */
	public String getTraffictype() {
		return traffictype;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * @param guestIpType the guestIpType to set
	 */
	public void setGuestIpType(String guestIpType) {
		this.guestIpType = guestIpType;
	}

	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @param maxconnections the maxconnections to set
	 */
	public void setMaxconnections(Long maxconnections) {
		this.maxconnections = maxconnections;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param networkRate the networkRate to set
	 */
	public void setNetworkRate(Long networkRate) {
		this.networkRate = networkRate;
	}

	/**
	 * @param specifyVlan the specifyVlan to set
	 */
	public void setSpecifyVlan(Boolean specifyVlan) {
		this.specifyVlan = specifyVlan;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @param traffictype the traffictype to set
	 */
	public void setTraffictype(String traffictype) {
		this.traffictype = traffictype;
	}

}
