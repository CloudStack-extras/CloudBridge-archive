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
public class CloudStackSecurityGroupIngress {
	@SerializedName("account")
	private String account;
	@SerializedName("cidr")
	private String cidr;
	@SerializedName("endport")
	private Integer endPort;
	@SerializedName("icmpcode")
	private Integer icmpcode;
	@SerializedName("icmptype")
	private Integer icmptype;
	@SerializedName("protocol")
	private String protocol;
	@SerializedName("ruleid")
	private Long ruleId;
	@SerializedName("securitygroupname")
	private String securityGroupName;
	@SerializedName("startport")
	private Integer startPort;
	

	/**
	 * 
	 */
	public CloudStackSecurityGroupIngress() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}


	/**
	 * @return the cidr
	 */
	public String getCidr() {
		return cidr;
	}


	/**
	 * @return the endPort
	 */
	public Integer getEndPort() {
		return endPort;
	}


	/**
	 * @return the icmpcode
	 */
	public Integer getIcmpcode() {
		return icmpcode;
	}


	/**
	 * @return the icmptype
	 */
	public Integer getIcmptype() {
		return icmptype;
	}


	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}


	/**
	 * @return the ruleId
	 */
	public Long getRuleId() {
		return ruleId;
	}


	/**
	 * @return the securityGroupName
	 */
	public String getSecurityGroupName() {
		return securityGroupName;
	}


	/**
	 * @return the startPort
	 */
	public Integer getStartPort() {
		return startPort;
	}


	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}


	/**
	 * @param cidr the cidr to set
	 */
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}


	/**
	 * @param endPort the endPort to set
	 */
	public void setEndPort(Integer endPort) {
		this.endPort = endPort;
	}


	/**
	 * @param icmpcode the icmpcode to set
	 */
	public void setIcmpcode(Integer icmpcode) {
		this.icmpcode = icmpcode;
	}


	/**
	 * @param icmptype the icmptype to set
	 */
	public void setIcmptype(Integer icmptype) {
		this.icmptype = icmptype;
	}


	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}


	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}


	/**
	 * @param securityGroupName the securityGroupName to set
	 */
	public void setSecurityGroupName(String securityGroupName) {
		this.securityGroupName = securityGroupName;
	}


	/**
	 * @param startPort the startPort to set
	 */
	public void setStartPort(Integer startPort) {
		this.startPort = startPort;
	}

}
