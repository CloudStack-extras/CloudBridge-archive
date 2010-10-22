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
package com.cloud.bridge.service.core.ec2;

import java.util.Calendar;

import com.cloud.bridge.util.EC2RestAuth;

public class EC2Instance {

	private String		id;
	private String 		name;
	private String 		zoneName;
	private String		templateId;
    private String		group;
    private String		state;
    private String		previousState;
    private String		ipAddress;
    private String		instanceType;
    private Calendar	created;
    private String		accountName;
    private String		domainId;
    
	public EC2Instance() {
		id            = null;
		name          = null;
		zoneName      = null;
		templateId    = null;
		group         = null;
		state         = null;
		previousState = null;
		ipAddress     = null;
		created       = null;
		instanceType  = null;
		accountName   = null;
		domainId	  = null;
	}
	
	public void setId( String id ) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setZoneName( String zoneName ) {
		this.zoneName = zoneName;
	}
	
	public String getZoneName() {
		return this.zoneName;
	}

	public void setTemplateId( String templateId ) {
		this.templateId = templateId;
	}
	
	public String getTemplateId() {
		return this.templateId;
	}
	
	public void setGroup( String group ) {
		this.group = group;
	}
	
	public String getGroup() {
		return this.group;
	}

	public void setState( String state ) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}

	public void setPreviousState( String state ) {
		this.previousState = state;
	}
	
	public String getPreviousState() {
		return this.previousState;
	}

	public void setCreated( String created ) {
		this.created = EC2RestAuth.parseDateString( created );
	}
	
	public Calendar getCreated() {
		return this.created;
	}

	public void setIpAddress( String ipAddress ) {
		this.ipAddress = ipAddress;
	}
	
	public String getIpAddress() {
		return this.ipAddress;
	}
	
	public void setServiceOffering( String instanceType ) {
		this.instanceType = instanceType;
	}
	
	public String getServiceOffering() {
		return this.instanceType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	
	
}
