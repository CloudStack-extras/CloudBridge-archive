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

public class CloudStackNic {
	
    @SerializedName("id")
    private Long id;

    @SerializedName("networkid")
    private Long networkid;
    
    @SerializedName("netmask")
    private String netmask;
    
    @SerializedName("gateway")
    private String gateway;
    
    @SerializedName("ipaddress")
    private String ipaddress;
    
    @SerializedName("isolationuri")
    private String isolationUri;
    
    @SerializedName("broadcasturi")
    private String broadcastUri;
    
    @SerializedName("traffictype")
    private String trafficType;
    
    @SerializedName("type") 
    private String type;
    
    @SerializedName("isdefault")
    private Boolean isDefault;
    
    public CloudStackNic() {
    }

	public Long getId() {
		return id;
	}

	public Long getNetworkid() {
		return networkid;
	}

	public String getNetmask() {
		return netmask;
	}

	public String getGateway() {
		return gateway;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public String getIsolationUri() {
		return isolationUri;
	}

	public String getBroadcastUri() {
		return broadcastUri;
	}

	public String getTrafficType() {
		return trafficType;
	}

	public String getType() {
		return type;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}
}
