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

public class CloudStackSnapshot {
	
	@SerializedName("id")
    private Long id;

    @SerializedName("account")
    private String accountName;

    @SerializedName("domainid")
    private Long domainId;

    @SerializedName("domain")
    private String domainName;

    @SerializedName("snapshottype")
    private String snapshotType;

    @SerializedName("volumeid")
    private Long volumeId;

    @SerializedName("volumename")
    private String volumeName;

    @SerializedName("volumetype")
    private String volumeType;

    @SerializedName("created")
    private String created;

    @SerializedName("name")
    private String name;

    @SerializedName("jobid")
    private Long jobId;

    @SerializedName("jobstatus")
    private Integer jobStatus;

    @SerializedName("intervaltype")
    private String intervalType;

    @SerializedName("state")
    private String state;

    public CloudStackSnapshot() {
    }
    
    public Long getId() {
		return id;
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

	public String getSnapshotType() {
		return snapshotType;
	}

	public Long getVolumeId() {
		return volumeId;
	}

	public String getVolumeName() {
		return volumeName;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public String getCreated() {
		return created;
	}

	public String getName() {
		return name;
	}

	public Long getJobId() {
		return jobId;
	}

	public Integer getJobStatus() {
		return jobStatus;
	}

	public String getIntervalType() {
		return intervalType;
	}

	public String getState() {
		return state;
	}
}
