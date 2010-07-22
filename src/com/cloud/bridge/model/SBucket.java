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
package com.cloud.bridge.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Kelven Yang
 */
public class SBucket implements Serializable {
	private static final long serialVersionUID = 7430267766019671273L;

	private Long id;
	
	private String name;
	private String ownerCanonicalId;
	
	private SHost shost;
	private Date createTime;
	
	private Set<SObject> objectsInBucket = new HashSet<SObject>();
	
	public SBucket() {
	}
	
	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwnerCanonicalId() {
		return ownerCanonicalId;
	}
	
	public void setOwnerCanonicalId(String ownerCanonicalId) {
		this.ownerCanonicalId = ownerCanonicalId;
	}
	
	public SHost getShost() {
		return shost;
	}
	
	public void setShost(SHost shost) {
		this.shost = shost;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Set<SObject> getObjectsInBucket() {
		return objectsInBucket;
	}

	public void setObjectsInBucket(Set<SObject> objectsInBucket) {
		this.objectsInBucket = objectsInBucket;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other)
			return true;
		
		if(!(other instanceof SBucket))
			return false;
		
		return getName().equals(((SBucket)other).getName());
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
}
