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
import java.util.Iterator;
import java.util.Set;

/**
 * @author Kelven Yang
 */
public class SObject implements Serializable {
	private static final long serialVersionUID = 8566744941395660486L;

	private Long id;
	
	private String nameKey;
	private String ownerCanonicalId;
	
	private int nextSequence;
	private int deletionMark;
	
	private Date createTime;
	
	private SBucket bucket;
	
	private Set<SObjectItem> items = new HashSet<SObjectItem>();

	public SObject() {
	}
	
	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}
	
	public String getNameKey() {
		return nameKey;
	}

	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}

	public String getOwnerCanonicalId() {
		return ownerCanonicalId;
	}

	public void setOwnerCanonicalId(String ownerCanonicalId) {
		this.ownerCanonicalId = ownerCanonicalId;
	}

	public int getNextSequence() {
		return nextSequence;
	}

	public void setNextSequence(int nextSequence) {
		this.nextSequence = nextSequence;
	}

	public int getDeletionMark() {
		return deletionMark;
	}

	public void setDeletionMark(int deletionMark) {
		this.deletionMark = deletionMark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public SBucket getBucket() {
		return bucket;
	}

	public void setBucket(SBucket bucket) {
		this.bucket = bucket;
	}
	
	public Set<SObjectItem> getItems() {
		return items;
	}

	public void setItems(Set<SObjectItem> items) {
		this.items = items;
	}
	
	public SObjectItem getLatestVersion() {
		Iterator<SObjectItem> it = getItems().iterator();
		int maxVersion = 0;
		int curVersion;
		SObjectItem latestItem = null;
		while(it.hasNext()) {
			SObjectItem item = it.next();
			
			try {
				curVersion = Integer.parseInt(item.getVersion());
			} catch (NumberFormatException e) {
				curVersion = 0;
			}
			
			if(curVersion > maxVersion) {
				maxVersion = curVersion;
				latestItem = item;
			}
		}
		return latestItem;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other)
			return true;
		
		if(!(other instanceof SObject))
			return false;
		
		if(!getNameKey().equals(((SObject)other).getNameKey()))
			return false;
		
		if(getBucket() != null) {
			if(!getBucket().equals(((SObject)other).getBucket()))
				return false;
		} else {
			if(((SObject)other).getBucket() != null)
				return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode = hashCode*17 + getNameKey().hashCode();
		
		if(getBucket() != null)
			hashCode = hashCode*17 + getBucket().hashCode(); 
		return hashCode;
	}
}
