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

public class CloudStackVolume {
    @SerializedName(ApiConstants.ID)
    private Long id;

    @SerializedName(ApiConstants.JOB_ID)
    private Long jobId;

    @SerializedName("jobstatus")
    private Integer jobStatus;

    @SerializedName(ApiConstants.NAME)
    private String name;

    @SerializedName(ApiConstants.ZONE_ID)
    private Long zoneId;

    @SerializedName("zonename")
    private String zoneName;

    @SerializedName(ApiConstants.TYPE)
    private String volumeType;

    @SerializedName(ApiConstants.DEVICE_ID)
    private Long deviceId;

    @SerializedName(ApiConstants.VIRTUAL_MACHINE_ID)
    private Long virtualMachineId;

    @SerializedName("vmname")
    private String virtualMachineName;

    @SerializedName("vmdisplayname")
    private String virtualMachineDisplayName;

    @SerializedName("vmstate")
    private String virtualMachineState;

    @SerializedName(ApiConstants.SIZE)
    private Long size;

    @SerializedName(ApiConstants.CREATED)
    private String created;

    @SerializedName(ApiConstants.STATE)
    private String state;

    @SerializedName(ApiConstants.ACCOUNT)
    private String accountName;

    @SerializedName(ApiConstants.DOMAIN_ID)
    private Long domainId;

    @SerializedName(ApiConstants.DOMAIN)
    private String domainName;

    @SerializedName("storagetype")
    private String storageType;

    @SerializedName(ApiConstants.HYPERVISOR)
    private String hypervisor;

    @SerializedName(ApiConstants.DISK_OFFERING_ID)
    private Long diskOfferingId;

    @SerializedName("diskofferingname")
    private String diskOfferingName;

    @SerializedName("diskofferingdisplaytext")
    private String diskOfferingDisplayText;

    @SerializedName("storage")
    private String storagePoolName;

    @SerializedName(ApiConstants.SNAPSHOT_ID)
    private Long snapshotId;

    @SerializedName("attached")
    private String attached;

    @SerializedName("destroyed")
    private Boolean destroyed;

    @SerializedName(ApiConstants.SERVICE_OFFERING_ID)
    private Long serviceOfferingId;

    @SerializedName("serviceofferingname")
    private String serviceOfferingName;

    @SerializedName("serviceofferingdisplaytext")
    private String serviceOfferingDisplayText;

    @SerializedName("isextractable")
    private Boolean extractable;
    
    public CloudStackVolume() {
    }

	public Long getId() {
		return id;
	}

	public Long getJobId() {
		return jobId;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public String getName() {
		return name;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public Long getDeviceId() {
		return deviceId;
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

	public String getVirtualMachineState() {
		return virtualMachineState;
	}

	public Long getSize() {
		return size;
	}

	public String getCreated() {
		return created;
	}

	public String getState() {
		return state;
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

	public String getStorageType() {
		return storageType;
	}

	public String getHypervisor() {
		return hypervisor;
	}

	public Long getDiskOfferingId() {
		return diskOfferingId;
	}

	public String getDiskOfferingName() {
		return diskOfferingName;
	}

	public String getDiskOfferingDisplayText() {
		return diskOfferingDisplayText;
	}

	public String getStoragePoolName() {
		return storagePoolName;
	}

	public Long getSnapshotId() {
		return snapshotId;
	}

	public String getAttached() {
		return attached;
	}

	public Boolean getDestroyed() {
		return destroyed;
	}

	public Long getServiceOfferingId() {
		return serviceOfferingId;
	}

	public String getServiceOfferingName() {
		return serviceOfferingName;
	}

	public String getServiceOfferingDisplayText() {
		return serviceOfferingDisplayText;
	}

	public Boolean getExtractable() {
		return extractable;
	}
}
