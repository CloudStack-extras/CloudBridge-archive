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

import java.util.ArrayList;
import java.util.List;

/**
 * @author slriv
 *
 */
public class EC2DescribeImageAttributesResponse {
        private String id;
        private Boolean isPublic;
        private String domainId;
        private List<String> accounts;
        
        /**
         * @return the id
         */
        public String getId() {
            return id;
        }
        /**
         * @return the isPublic
         */
        public Boolean getIsPublic() {
            return isPublic;
        }
        /**
         * @return the domainId
         */
        public String getDomainId() {
            return domainId;
        }
        /**
         * @return the accounts
         */
        public List<String> getAccounts() {
            return accounts;
        }
        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }
        /**
         * @param isPublic the isPublic to set
         */
        public void setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
        }
        /**
         * @param domainId the domainId to set
         */
        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }
        /**
         * @param accounts the accounts to set
         */
        public void setAccounts(List<String> accounts) {
            this.accounts = accounts;
        }
        
        public void addAccount(String accountName) {
            if (this.accounts == null) {
                this.accounts = new ArrayList<String>();
            }
            this.accounts.add(accountName);
        }
}
