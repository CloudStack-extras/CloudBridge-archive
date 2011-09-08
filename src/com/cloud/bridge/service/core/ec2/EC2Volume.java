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


public class EC2Volume {

	private Long id;
	private Long size;   // <- in gigs
	private String zoneName;
	private Long   instanceId;
	private Long   snapshotId;
	private String   device;
	private Long      deviceId;
	private String   state;
	private String   type;
	private String   VMState;
	private String   hypervisor;
    private String created;
	private String attached;
    
	public EC2Volume() {
		id         = null;
		zoneName   = null;
		instanceId = null;
		snapshotId = null;
		device     = null;
		deviceId   = null;
		state      = null;
		type       = null;
		VMState    = null;
		hypervisor = null;
		created    = null;
		attached   = null;
	}
	
	public void setSize(Long size) {
		if (size != null) {
			this.size = (size / 1073741824);
		} else
			this.size = (long) 0;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the size
	 */
	public Long getSize() {
		return size;
	}

	/**
	 * @return the zoneName
	 */
	public String getZoneName() {
		return zoneName;
	}

	/**
	 * @return the instanceId
	 */
	public Long getInstanceId() {
		return instanceId;
	}

	/**
	 * @return the snapshotId
	 */
	public Long getSnapshotId() {
		return snapshotId;
	}

	/**
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * @return the deviceId
	 */
	public Long getDeviceId() {
		return deviceId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the VMState
	 */
	public String getVMState() {
		return VMState;
	}

	/**
	 * @return the hypervisor
	 */
	public String getHypervisor() {
		return hypervisor;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param zoneName the zoneName to set
	 */
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @param snapshotId the snapshotId to set
	 */
	public void setSnapshotId(Long snapshotId) {
		this.snapshotId = snapshotId;
	}

	/**
	 * @param device the device to set
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param VMState the VMState to set
	 */
	public void setVMState(String VMState) {
		this.VMState = VMState;
	}

	/**
	 * @param hypervisor the hypervisor to set
	 */
	public void setHypervisor(String hypervisor) {
		this.hypervisor = hypervisor;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @return the attached
	 */
	public String getAttached() {
		return attached;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(String attached) {
		this.attached = attached;
	}
	
}
