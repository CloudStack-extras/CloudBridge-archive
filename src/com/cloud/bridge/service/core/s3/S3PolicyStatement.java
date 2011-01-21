/*
 * Copyright 2011 Cloud.com, Inc.
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
package com.cloud.bridge.service.core.s3;

public class S3PolicyStatement {
	private String sid;
	private String effect;
	private S3PolicyPrincipal principals;
    private S3PolicyAction actions;
    private String notAction;
    private String resource;
    private S3PolicyConditionBlock block;
	
	public S3PolicyStatement() {
	}

	public S3PolicyAction getActions() {
		return actions;
	}
	
	public void setActions(S3PolicyAction param) {
		actions = param;
	}

	public S3PolicyPrincipal getPrincipals() {
		return principals;
	}
	
	public void setPrincipals(S3PolicyPrincipal param) {
		principals = param;
	}
	
	public String getSid() {
		return sid;
	}
	
	public void setSid(String param) {
		sid = param;
	}

	public String getEffect() {
		return effect;
	}
	
	public void setEffect(String param) {
		effect = param;
	}
	
	public String getResource() {
		return resource;
	}
	
	public void setResource(String param) {
		resource = param;
	}
	
	public String getNotAction() {
		return notAction;
	}
	
	public void setNotAction(String param) {
		notAction = param;
	}

	public S3PolicyConditionBlock getConditionBlock() {
		return block;
	}
	
	public void setConditionBlock(S3PolicyConditionBlock param) {
		block = param;
	}

	public String toString() {
		
		StringBuffer value = new StringBuffer();	
		value.append( "Statement: \n");
		if (null != sid       ) value.append( "Sid: " + sid + "\n" );
		if (null != effect    ) value.append( "Effect: "  + effect + "\n" );
		if (null != principals) value.append( principals.toString());
		if (null != actions   ) value.append( actions.toString());
		if (null != notAction ) value.append( "NotAction: "  + notAction + "\n" );
		if (null != resource  ) value.append( "Resource: " + resource + "\n" );
		if (null != block     ) value.append( block.toString());
		return value.toString();
	}

}
