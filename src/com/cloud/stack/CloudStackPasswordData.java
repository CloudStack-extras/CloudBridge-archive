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
public class CloudStackPasswordData {
	
	@SerializedName("id")
	private String encryptedpassword;

	/**
	 * 
	 */
	public CloudStackPasswordData() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the encryptedpassword
	 */
	public String getEncryptedpassword() {
		return encryptedpassword;
	}

	/**
	 * @param encryptedpassword the encryptedpassword to set
	 */
	public void setEncryptedpassword(String encryptedpassword) {
		this.encryptedpassword = encryptedpassword;
	}

}