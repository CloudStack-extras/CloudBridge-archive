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
public class CloudStackDiskOfferings {
	@SerializedName("id")
	private Long id;
	@SerializedName("name")
	private String name;
	@SerializedName("displaytext")
	private String displayText;
	@SerializedName("disksize")
	private Long diskSize;
	@SerializedName("created")
	private String created;
	@SerializedName("iscustomized")
	private boolean isCustomized;

	/**
	 * 
	 */
	public CloudStackDiskOfferings() {
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
	 * @return the displayText
	 */
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * @return the diskSize
	 */
	public Long getDiskSize() {
		return diskSize;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @return the isCustomized
	 */
	public boolean isCustomized() {
		return isCustomized;
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
	 * @param displayText the displayText to set
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * @param diskSize the diskSize to set
	 */
	public void setDiskSize(Long diskSize) {
		this.diskSize = diskSize;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @param isCustomized the isCustomized to set
	 */
	public void setCustomized(boolean isCustomized) {
		this.isCustomized = isCustomized;
	}

}
