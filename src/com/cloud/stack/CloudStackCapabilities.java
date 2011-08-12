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
public class CloudStackCapabilities {
	@SerializedName("cloudstackversion")
	private String cloudStackVersion;
	@SerializedName("securitygroupsenabled")
	private Boolean securityGroupsEnabled;
	@SerializedName("userpublictemplateenabled")
	private Boolean userPublicTemplateEnabled;
	/**
	 * 
	 */
	public CloudStackCapabilities() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the cloudStackVersion
	 */
	public String getCloudStackVersion() {
		return cloudStackVersion;
	}
	/**
	 * @return the securityGroupsEnabled
	 */
	public Boolean getSecurityGroupsEnabled() {
		return securityGroupsEnabled;
	}
	/**
	 * @return the userPublicTemplateEnabled
	 */
	public Boolean getUserPublicTemplateEnabled() {
		return userPublicTemplateEnabled;
	}
	/**
	 * @param cloudStackVersion the cloudStackVersion to set
	 */
	public void setCloudStackVersion(String cloudStackVersion) {
		this.cloudStackVersion = cloudStackVersion;
	}
	/**
	 * @param securityGroupsEnabled the securityGroupsEnabled to set
	 */
	public void setSecurityGroupsEnabled(Boolean securityGroupsEnabled) {
		this.securityGroupsEnabled = securityGroupsEnabled;
	}
	/**
	 * @param userPublicTemplateEnabled the userPublicTemplateEnabled to set
	 */
	public void setUserPublicTemplateEnabled(Boolean userPublicTemplateEnabled) {
		this.userPublicTemplateEnabled = userPublicTemplateEnabled;
	}

}
