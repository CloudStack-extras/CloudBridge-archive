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

import java.util.ArrayList;
import java.util.List;

/**
 * @author slriv
 *
 */
public class EC2ModifyImageAttribute {
    private String imageId;
    private List<String> addedUsers;
    private List<String> removedUsers;
    private Boolean isPublic;
    private String description; //unsupported
    private Boolean reset;
    
    
    /**
     * 
     */
    public EC2ModifyImageAttribute() {
        // set to null by default!
        isPublic = null;
    }


    /**
     * @return the imageId
     */
    public String getImageId() {
        return imageId;
    }


    /**
     * @return the addedUsers
     */
    public List<String> getAddedUsers() {
        return addedUsers;
    }


    /**
     * @return the removedUsers
     */
    public List<String> getRemovedUsers() {
        return removedUsers;
    }


    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * @param imageId the imageId to set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    /**
     * @param addedUsers the addedUsers to set
     */
    public void setAddedUsers(List<String> addedUsers) {
        this.addedUsers = addedUsers;
    }
    
    public void addAddedUser(String user) {
        if (this.addedUsers == null) {
            this.addedUsers = new ArrayList<String>();
        }
        this.addedUsers.add(user);
    }


    /**
     * @param removedUsers the removedUsers to set
     */
    public void setRemovedUsers(List<String> removedUsers) {
        this.removedUsers = removedUsers;
    }
    
    public void addRemovedUser(String user) {
        if (this.removedUsers == null) {
            this.removedUsers = new ArrayList<String>();
        }
        this.removedUsers.add(user);
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the isPublic
     */
    public Boolean getIsPublic() {
        return isPublic;
    }


    /**
     * @param isPublic the isPublic to set
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }


    /**
     * @return the reset
     */
    public Boolean getReset() {
        return reset;
    }


    /**
     * @param reset the reset to set
     */
    public void setReset(Boolean reset) {
        this.reset = reset;
    }

}
