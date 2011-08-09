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

package com.cloud.bridge.service.core.ec2;

/**
 * @author slriv
 *
 */
public class EC2DescribeKeyPairs {
	private EC2KeyPairFilterSet keyFilterSet;
	private String keyName;
	
	public EC2DescribeKeyPairs() {
	}
	
	/**
	 * @return the keyName
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName the keyName to set
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * @return the keyFilterSet
	 */
	public EC2KeyPairFilterSet getKeyFilterSet() {
		return keyFilterSet;
	}

	/**
	 * @param keyFilterSet the keyFilterSet to set
	 */
	public void setKeyFilterSet(EC2KeyPairFilterSet keyFilterSet) {
		this.keyFilterSet = keyFilterSet;
	}

}
