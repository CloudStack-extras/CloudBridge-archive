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

public class EC2Volume {

	private String   id;
	private int      size;   // <- in gigs
	private String   zoneName;
	private String   instanceId;
	private String   device;
	private int      deviceId;
	private String   status;
	private String   type;
	private String   vmstate;
    private Calendar created;
    
	public EC2Volume() {
		id         = null;
		zoneName   = null;
		instanceId = null;
		device     = null;
		deviceId   = 0;
		status     = null;
		type       = null;
		vmstate    = null;
		created    = null;
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
		     if (0 != bytes) this.size = (int)(bytes / 1073741824);
		}
		else this.size = 0;
	}
	
	public int getSize() {
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
	
	public void setDevice( String device ) {
		this.device = device;
	}
	
	public String getDevice() {
		return this.device;
	}

	public void setDeviceId( int deviceId ) {
		this.deviceId = deviceId;
	}
	
	public int getDeviceId() {
		return this.deviceId;
	}

	public void setStatus( String status ) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
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

	public void setCreated( String created ) {
		this.created = EC2RestAuth.parseDateString( created );
	}
	
	public Calendar getCreated() {
		return this.created;
	}
}
