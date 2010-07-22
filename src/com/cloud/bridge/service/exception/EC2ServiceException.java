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
package com.cloud.bridge.service.exception;

public class EC2ServiceException  extends RuntimeException {
	private static final long serialVersionUID = 8857313467757867680L;
	
	private int httpErrorCode = 0;
	
	public EC2ServiceException() {
	}
	
	public EC2ServiceException(String message) {
		super(message);
	}
	
	public EC2ServiceException(Throwable e) {
		super(e);
	}

	public EC2ServiceException(String message, Throwable e) {
		super(message, e);
	}

	public EC2ServiceException(String message, int errorCode) {
		super(message);
		this.httpErrorCode = errorCode;
	}
	
	public int getErrorCode() {
		return this.httpErrorCode;
	}
}
