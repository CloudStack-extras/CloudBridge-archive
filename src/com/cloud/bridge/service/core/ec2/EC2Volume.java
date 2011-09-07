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

import java.util.Calendar;

import com.cloud.bridge.util.EC2RestAuth;

public class EC2Volume {

	private String   id;
	private Long      size;   // <- in gigs
	private String   zoneName;
	private String   instanceId;
	private String   snapshotId;
	private String   device;
	private Integer      deviceId;
	private String   state;
	private String   type;
	private String   vmstate;
	private String   hypervisor;
    private Calendar created;
	private Calendar attached;
    
	public EC2Volume() {
		id         = null;
		zoneName   = null;
		instanceId = null;
		snapshotId = null;
		device     = null;
		deviceId   =  0;
		state      = null;
		type       = null;
		vmstate    = null;
		hypervisor = null;
		created    = null;
		attached   = null;
	}
	
	public void setId( String id ) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}

	public void setSize( String size ) {
		if ( null != size ) {
		     // -> convert from number of bytes to number of gigabytes
		     long bytes = Long.parseLong( size );
		     if (0 != bytes) this.size = (long) (bytes / 1073741824);
		} else 
			this.size = (long) 0;
	}
	
	public Long getSize() {
		return this.size;
	}
	
	public void setZoneName( String zoneName ) {
		this.zoneName = zoneName;
	}
	
	public String getZoneName() {
		return this.zoneName;
	}

	public void setInstanceId( String instanceId ) {
		this.instanceId = instanceId;
	}
	
	public String getInstanceId() {
		return this.instanceId;
	}

	public void setSnapShotId( String id ) {
		snapshotId = id;
	}
	
	public String getSnapShotId() {
		return snapshotId;
	}
	
	public void setDevice( String device ) {
		this.device = device;
	}
	
	public String getDevice() {
		return this.device;
	}

	public void setDeviceId( Integer deviceId ) {
		this.deviceId = deviceId;
	}
	
	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setState( String state ) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}

	public void setType( String type ) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}

	public void setVMState( String vmstate ) {
		this.vmstate = vmstate;
	}
	
	public String getVMState() {
		return this.vmstate;
	}

	public void setHypervisor( String hypervisor ) {
		this.hypervisor = hypervisor;
	}
	
	public String getHypervisor() {
		return this.hypervisor;
	}

	public void setCreated( String created ) {
		this.created = EC2RestAuth.parseDateString( created );
	}
	
	public Calendar getCreated() {
		return this.created;
	}

	public void setAttached( String attached ) {
		this.attached = EC2RestAuth.parseDateString( attached );
	}
	
	public Calendar getAttached() {
		return this.attached;
	}
}
