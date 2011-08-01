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

public class CloudStackUserVm {
	
	@SerializedName(ApiConstants.ID)
    private Long id;

    @SerializedName(ApiConstants.NAME)
    private String name;

    @SerializedName("displayname")
    private String displayName;

    @SerializedName(ApiConstants.IP_ADDRESS)
    private String ipAddress;

    @SerializedName(ApiConstants.ACCOUNT)
    private String accountName;

    @SerializedName(ApiConstants.DOMAIN_ID)
    private Long domainId;

    @SerializedName(ApiConstants.DOMAIN)
    private String domainName;

    @SerializedName(ApiConstants.CREATED)
    private String created;

    @SerializedName(ApiConstants.STATE)
    private String state;

    @SerializedName(ApiConstants.HA_ENABLE)
    private Boolean haEnable;

    @SerializedName(ApiConstants.GROUP_ID)
    private Long groupId;

    @SerializedName(ApiConstants.GROUP)
    private String group;

    @SerializedName(ApiConstants.ZONE_ID)
    private Long zoneId;

    @SerializedName("zonename")
    private String zoneName;

    @SerializedName(ApiConstants.HOST_ID)
    private Long hostId;

    @SerializedName("hostname") 
    private String hostName;

    @SerializedName(ApiConstants.TEMPLATE_ID)
    private Long templateId;

    @SerializedName("templatename")
    private String templateName;

    @SerializedName("templatedisplaytext")
    private String templateDisplayText;

    @SerializedName(ApiConstants.PASSWORD_ENABLED)
    private Boolean passwordEnabled;

    @SerializedName("isoid")
    private Long isoId;

    @SerializedName("isoname")
    private String isoName;

    @SerializedName("isodisplaytext")
    private String isoDisplayText;

    @SerializedName("serviceofferingid")
    private Long serviceOfferingId;

    @SerializedName("serviceofferingname")
    private String serviceOfferingName;
    
    @SerializedName("forvirtualnetwork")
    private Boolean forVirtualNetwork;

    @SerializedName(ApiConstants.CPU_NUMBER)
    private Integer cpuNumber;

    @SerializedName(ApiConstants.CPU_SPEED)
    private Integer cpuSpeed;

    @SerializedName(ApiConstants.MEMORY)
    private Integer memory;

    @SerializedName("cpuused")
    private String cpuUsed;
    
    @SerializedName("networkkbsread")
    private Long networkKbsRead;

    @SerializedName("networkkbswrite")
    private Long networkKbsWrite;

    @SerializedName("guestosid")
    private Long guestOsId;

    @SerializedName("rootdeviceid")
    private Long rootDeviceId;

    @SerializedName("rootdevicetype")
    private String rootDeviceType;

    @SerializedName("securitygroup")
    private List<CloudStackSecurityGroup> securityGroupList;

    @SerializedName(ApiConstants.PASSWORD)
    private String password;

    @SerializedName(ApiConstants.JOB_ID)
    private Long jobId;

    @SerializedName("jobstatus")
    private Integer jobStatus;
    
    @SerializedName("nic") 
    private List<CloudStackNic> nics;
    
    @SerializedName("hypervisor")
    private String hypervisor;
    
	public CloudStackUserVm() {
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getIpAddress() {
		return ipAddress;
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

	public String getCreated() {
		return created;
	}

	public String getState() {
		return state;
	}

	public Boolean getHaEnable() {
		return haEnable;
	}

	public Long getGroupId() {
		return groupId;
	}

	public String getGroup() {
		return group;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public Long getHostId() {
		return hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getTemplateDisplayText() {
		return templateDisplayText;
	}

	public Boolean getPasswordEnabled() {
		return passwordEnabled;
	}

	public Long getIsoId() {
		return isoId;
	}

	public String getIsoName() {
		return isoName;
	}

	public String getIsoDisplayText() {
		return isoDisplayText;
	}

	public Long getServiceOfferingId() {
		return serviceOfferingId;
	}

	public String getServiceOfferingName() {
		return serviceOfferingName;
	}

	public Boolean getForVirtualNetwork() {
		return forVirtualNetwork;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public Integer getCpuSpeed() {
		return cpuSpeed;
	}

	public Integer getMemory() {
		return memory;
	}

	public String getCpuUsed() {
		return cpuUsed;
	}

	public Long getNetworkKbsRead() {
		return networkKbsRead;
	}

	public Long getNetworkKbsWrite() {
		return networkKbsWrite;
	}

	public Long getGuestOsId() {
		return guestOsId;
	}

	public Long getRootDeviceId() {
		return rootDeviceId;
	}

	public String getRootDeviceType() {
		return rootDeviceType;
	}

	public List<CloudStackSecurityGroup> getSecurityGroupList() {
		return securityGroupList;
	}

	public String getPassword() {
		return password;
	}

	public Long getJobId() {
		return jobId;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public List<CloudStackNic> getNics() {
		return nics;
	}

	public String getHypervisor() {
		return hypervisor;
	}
}
