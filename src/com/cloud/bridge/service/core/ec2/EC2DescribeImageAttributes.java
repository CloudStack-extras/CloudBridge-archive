/*
 * Copyright (C) 2012 Citrix Systems, Inc.  All rights reserved.
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
public class EC2DescribeImageAttributes {
    private String imageId;
    private Boolean launchPermission;
    private Boolean Description;

    /**
     * @return the launchPermission
     */
    public Boolean getLaunchPermission() {
        return launchPermission;
    }
    /**
     * @return the description
     */
    public Boolean getDescription() {
        return Description;
    }
    /**
     * @param launchPermission the launchPermission to set
     */
    public void setLaunchPermission(Boolean launchPermission) {
        this.launchPermission = launchPermission;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(Boolean description) {
        Description = description;
    }
    /**
     * @return the imageId
     */
    public String getImageId() {
        return imageId;
    }
    /**
     * @param imageId the imageId to set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

}
