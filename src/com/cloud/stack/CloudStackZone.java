/*
 * Copyright 2010 Cloud.com, Inc.
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

public class CloudStackZone {
    @SerializedName(ApiConstants.ID)
    private Long id;

    @SerializedName(ApiConstants.NAME)
    private String name;

    @SerializedName(ApiConstants.DESCRIPTION)
    private String description;

    @SerializedName(ApiConstants.DNS1)
    private String dns1;

    @SerializedName(ApiConstants.DNS2)
    private String dns2;

    @SerializedName(ApiConstants.INTERNAL_DNS1)
    private String internalDns1;

    @SerializedName(ApiConstants.INTERNAL_DNS2)
    private String internalDns2;
    
    @SerializedName(ApiConstants.VLAN)
    private String vlan;

    @SerializedName(ApiConstants.GUEST_CIDR_ADDRESS)
    private String guestCidrAddress;
    
    //TODO - generate description
    @SerializedName("status")
    private String status;

    @SerializedName(ApiConstants.DISPLAY_TEXT)
    private String displayText;
    
    @SerializedName(ApiConstants.DOMAIN)
    private String domain;

    @SerializedName(ApiConstants.DOMAIN_ID)
    private Long domainId;
    
    @SerializedName(ApiConstants.NETWORK_TYPE)
    private String networkType;
    
    @SerializedName("securitygroupsenabled")
    private boolean securityGroupsEnabled;
    
    @SerializedName("allocationstate")
    private String allocationState; 
    
    public CloudStackZone() {
    }

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDns1() {
		return dns1;
	}

	public String getDns2() {
		return dns2;
	}

	public String getInternalDns1() {
		return internalDns1;
	}

	public String getInternalDns2() {
		return internalDns2;
	}

	public String getVlan() {
		return vlan;
	}

	public String getGuestCidrAddress() {
		return guestCidrAddress;
	}

	public String getStatus() {
		return status;
	}

	public String getDisplayText() {
		return displayText;
	}

	public String getDomain() {
		return domain;
	}

	public Long getDomainId() {
		return domainId;
	}

	public String getNetworkType() {
		return networkType;
	}

	public boolean isSecurityGroupsEnabled() {
		return securityGroupsEnabled;
	}

	public String getAllocationState() {
		return allocationState;
	}
}
