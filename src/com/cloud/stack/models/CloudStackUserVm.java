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
package com.cloud.stack.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class CloudStackUserVm {
	@SerializedName(ApiConstants.ID)
    private Long id;
    @SerializedName(ApiConstants.ACCOUNT)
    private String accountName;
    @SerializedName(ApiConstants.CPU_NUMBER)
    private Integer cpuNumber;
    @SerializedName(ApiConstants.CPU_SPEED)
    private Integer cpuSpeed;
    @SerializedName(ApiConstants.CPU_USED)
    private String cpuUsed;
    @SerializedName(ApiConstants.CREATED)
    private String created;
    @SerializedName(ApiConstants.DISPLAY_NAME)
    private String displayName;
    @SerializedName(ApiConstants.DOMAIN)
    private String domainName;
    @SerializedName(ApiConstants.DOMAIN_ID)
    private Long domainId;
    @SerializedName(ApiConstants.FOR_VIRTUAL_NETWORK)
    private Boolean forVirtualNetwork;
    @SerializedName(ApiConstants.GROUP)
    private String group;
    @SerializedName(ApiConstants.GROUP_ID)
    private Long groupId;
    @SerializedName(ApiConstants.GUEST_OS_ID)
    private Long guestOsId;
    @SerializedName(ApiConstants.HA_ENABLE)
    private Boolean haEnable;
    @SerializedName(ApiConstants.HOST_ID)
    private Long hostId;
    @SerializedName(ApiConstants.HOST_NAME) 
    private String hostName;
    @SerializedName(ApiConstants.HYPERVISOR)
    private String hypervisor;    
    @SerializedName(ApiConstants.IP_ADDRESS)
    private String ipAddress;
    @SerializedName(ApiConstants.ISO_DISPLAY_TEXT)
    private String isoDisplayText;
    @SerializedName(ApiConstants.ISO_ID)
    private Long isoId;
    @SerializedName(ApiConstants.ISO_NAME)
    private String isoName;
    @SerializedName(ApiConstants.JOB_ID)
    private Long jobId;
    @SerializedName(ApiConstants.JOB_STATUS)
    private Integer jobStatus;
    @SerializedName(ApiConstants.MEMORY)
    private Integer memory;
    @SerializedName(ApiConstants.NAME)
    private String name;
    @SerializedName(ApiConstants.NETWORK_KBS_READ)
    private Long networkKbsRead;
    @SerializedName(ApiConstants.NETWORK_KBS_WRITE)
    private Long networkKbsWrite;
    @SerializedName(ApiConstants.PASSWORD)
    private String password;
    @SerializedName(ApiConstants.PASSWORD_ENABLED)
    private Boolean passwordEnabled;
    @SerializedName(ApiConstants.ROOT_DEVICE_ID)
    private Long rootDeviceId;
    @SerializedName(ApiConstants.ROOT_DEVICE_TYPE)
    private String rootDeviceType;
    @SerializedName(ApiConstants.SERVICE_OFFERING_ID)
    private Long serviceOfferingId;
    @SerializedName(ApiConstants.SERVICE_OFFERING_NAME)
    private String serviceOfferingName;
    @SerializedName(ApiConstants.STATE)
    private String state;
    @SerializedName(ApiConstants.TEMPLATE_DISPLAY_TEXT)
    private String templateDisplayText;
    @SerializedName(ApiConstants.TEMPLATE_ID)
    private Long templateId;
    @SerializedName(ApiConstants.TEMPLATE_NAME)
    private String templateName;
    @SerializedName(ApiConstants.ZONE_ID)
    private Long zoneId;
    @SerializedName(ApiConstants.ZONE_NAME)
    private String zoneName;
    @SerializedName(ApiConstants.NIC) 
    private List<CloudStackNic> nics;
    @SerializedName(ApiConstants.SECURITY_GROUP)
    private List<CloudStackSecurityGroup> securityGroupList;
    
	public CloudStackUserVm() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @return the cpuNumber
	 */
	public Integer getCpuNumber() {
		return cpuNumber;
	}

	/**
	 * @return the cpuSpeed
	 */
	public Integer getCpuSpeed() {
		return cpuSpeed;
	}

	/**
	 * @return the cpuUsed
	 */
	public String getCpuUsed() {
		return cpuUsed;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}

	/**
	 * @return the domainId
	 */
	public Long getDomainId() {
		return domainId;
	}

	/**
	 * @return the forVirtualNetwork
	 */
	public Boolean getForVirtualNetwork() {
		return forVirtualNetwork;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @return the guestOsId
	 */
	public Long getGuestOsId() {
		return guestOsId;
	}

	/**
	 * @return the haEnable
	 */
	public Boolean getHaEnable() {
		return haEnable;
	}

	/**
	 * @return the hostId
	 */
	public Long getHostId() {
		return hostId;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @return the hypervisor
	 */
	public String getHypervisor() {
		return hypervisor;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @return the isoDisplayText
	 */
	public String getIsoDisplayText() {
		return isoDisplayText;
	}

	/**
	 * @return the isoId
	 */
	public Long getIsoId() {
		return isoId;
	}

	/**
	 * @return the isoName
	 */
	public String getIsoName() {
		return isoName;
	}

	/**
	 * @return the jobId
	 */
	public Long getJobId() {
		return jobId;
	}

	/**
	 * @return the jobStatus
	 */
	public Integer getJobStatus() {
		return jobStatus;
	}

	/**
	 * @return the memory
	 */
	public Integer getMemory() {
		return memory;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the networkKbsRead
	 */
	public Long getNetworkKbsRead() {
		return networkKbsRead;
	}

	/**
	 * @return the networkKbsWrite
	 */
	public Long getNetworkKbsWrite() {
		return networkKbsWrite;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the passwordEnabled
	 */
	public Boolean getPasswordEnabled() {
		return passwordEnabled;
	}

	/**
	 * @return the rootDeviceId
	 */
	public Long getRootDeviceId() {
		return rootDeviceId;
	}

	/**
	 * @return the rootDeviceType
	 */
	public String getRootDeviceType() {
		return rootDeviceType;
	}

	/**
	 * @return the serviceOfferingId
	 */
	public Long getServiceOfferingId() {
		return serviceOfferingId;
	}

	/**
	 * @return the serviceOfferingName
	 */
	public String getServiceOfferingName() {
		return serviceOfferingName;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the templateDisplayText
	 */
	public String getTemplateDisplayText() {
		return templateDisplayText;
	}

	/**
	 * @return the templateId
	 */
	public Long getTemplateId() {
		return templateId;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @return the zoneId
	 */
	public Long getZoneId() {
		return zoneId;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @return the nics
	 */
	public List<CloudStackNic> getNics() {
		return nics;
	}

	/**
	 * @return the securityGroupList
	 */
	public List<CloudStackSecurityGroup> getSecurityGroupList() {
		return securityGroupList;
	}

}

