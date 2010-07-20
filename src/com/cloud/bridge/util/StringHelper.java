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
package com.cloud.bridge.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Kelven
 */
public class StringHelper {
	public static final String EMPTY_STRING = "";

	private static final char[] hexChars = { '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F' }; 
	
	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(hexChars[ (int)(((int)b[i] >> 4) & 0x0f)]);
			sb.append(hexChars[ (int)(((int)b[i]) & 0x0f)]);
		}
	    return sb.toString(); 
	}
	
	public static String substringInBetween(String name, 
		String prefix, String delimiter) {
		
		int startPos = 0;
		if(prefix != null)
			startPos = prefix.length() + 1;
		
		int endPos = name.indexOf(delimiter, startPos);
		if(endPos > 0)
			return name.substring(startPos, endPos);
		
		return null;
	}
	
	public static String stringFromStream(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
	    byte[] b = new byte[4096];
	    int n;
	    while((n = is.read(b)) != -1) {
	        sb.append(new String(b, 0, n));
	    }
	    return sb.toString();
	}
}
