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
public class EC2ModifyInstanceAttribute {
    private String instanceId;
    private Boolean disableApiTermination;
    private String instanceInitiatedShutdownBehavior;
    private String instanceType;
    private String kernel;
    private String ramdisk;
    private String userData;
    /**
     * @return the instanceId
     */
    public String getInstanceId() {
        return instanceId;
    }
    /**
     * @return the disableApiTermination
     */
    public Boolean getDisableApiTermination() {
        return disableApiTermination;
    }
    /**
     * @return the instanceInitiatedShutdownBehavior
     */
    public String getInstanceInitiatedShutdownBehavior() {
        return instanceInitiatedShutdownBehavior;
    }
    /**
     * @return the instanceType
     */
    public String getInstanceType() {
        return instanceType;
    }
    /**
     * @return the kernel
     */
    public String getKernel() {
        return kernel;
    }
    /**
     * @return the ramdisk
     */
    public String getRamdisk() {
        return ramdisk;
    }
    /**
     * @return the userData
     */
    public String getUserData() {
        return userData;
    }
    /**
     * @param instanceId the instanceId to set
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    /**
     * @param disableApiTermination the disableApiTermination to set
     */
    public void setDisableApiTermination(Boolean disableApiTermination) {
        this.disableApiTermination = disableApiTermination;
    }
    /**
     * @param instanceInitiatedShutdownBehavior the instanceInitiatedShutdownBehavior to set
     */
    public void setInstanceInitiatedShutdownBehavior(
            String instanceInitiatedShutdownBehavior) {
        this.instanceInitiatedShutdownBehavior = instanceInitiatedShutdownBehavior;
    }
    /**
     * @param instanceType the instanceType to set
     */
    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }
    /**
     * @param kernel the kernel to set
     */
    public void setKernel(String kernel) {
        this.kernel = kernel;
    }
    /**
     * @param ramdisk the ramdisk to set
     */
    public void setRamdisk(String ramdisk) {
        this.ramdisk = ramdisk;
    }
    /**
     * @param userData the userData to set
     */
    public void setUserData(String userData) {
        this.userData = userData;
    }
}
