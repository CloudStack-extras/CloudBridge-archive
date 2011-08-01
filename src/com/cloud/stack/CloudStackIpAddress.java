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

public class CloudStackIpAddress {
	
    @SerializedName("id")
    private Long id;
    
    @SerializedName("ipaddress")
    private String ipAddress;

    @SerializedName("allocated")
    private String allocated;

    @SerializedName("zoneid")
    private Long zoneId;

    @SerializedName("zonename")
    private String zoneName;

    @SerializedName("issourcenat")
    private Boolean sourceNat;

    @SerializedName("account")
    private String accountName;

    @SerializedName("domainid")
    private Long domainId;

    @SerializedName("domain")
    private String domainName;

    @SerializedName("forvirtualnetwork")
    private Boolean forVirtualNetwork;

    @SerializedName("vlanid")
    private Long vlanId;

    @SerializedName("vlanname")
    private String vlanName;

    @SerializedName("isstaticnat")
    private Boolean staticNat;
    
    @SerializedName("virtualmachineid")
    private Long virtualMachineId;
    
    @SerializedName("virtualmachinename")
    private String virtualMachineName;
    
    @SerializedName("virtualmachinedisplayname")
    private String virtualMachineDisplayName;
    
    @SerializedName("associatednetworkid")
    private Long associatedNetworkId;
    
    @SerializedName("networkid")
    private Long networkId;
    
    @SerializedName(ApiConstants.STATE)
    private String state;
    
    @SerializedName(ApiConstants.JOB_ID)
    private Long jobId;

    @SerializedName("jobstatus")
    private Integer jobStatus;
	
    public CloudStackIpAddress() {
    }

	public Long getId() {
		return id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getAllocated() {
		return allocated;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public Boolean getSourceNat() {
		return sourceNat;
	}

	public String getAccountName() {
		return accountName;
	}

	public Long getDomainId() {
		return domainId;
	}

	public String getDomainName() {
		return domainName;
	}

	public Boolean getForVirtualNetwork() {
		return forVirtualNetwork;
	}

	public Long getVlanId() {
		return vlanId;
	}

	public String getVlanName() {
		return vlanName;
	}

	public Boolean getStaticNat() {
		return staticNat;
	}

	public Long getVirtualMachineId() {
		return virtualMachineId;
	}

	public String getVirtualMachineName() {
		return virtualMachineName;
	}

	public String getVirtualMachineDisplayName() {
		return virtualMachineDisplayName;
	}

	public Long getAssociatedNetworkId() {
		return associatedNetworkId;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public String getState() {
		return state;
	}

	public Long getJobId() {
		return jobId;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}
}
