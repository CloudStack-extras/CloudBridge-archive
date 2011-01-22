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
package com.cloud.bridge.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cloud.bridge.service.core.s3.S3BucketPolicy;
import com.cloud.bridge.service.core.s3.S3PolicyAction;
import com.cloud.bridge.service.core.s3.S3PolicyCondition;
import com.cloud.bridge.service.core.s3.S3PolicyConditionBlock;
import com.cloud.bridge.service.core.s3.S3PolicyPrincipal;
import com.cloud.bridge.service.core.s3.S3PolicyStatement;
import com.cloud.bridge.service.core.s3.S3BucketPolicy.PolicyAccess;

/**
 * This class uses the JSON simple parser to convert the JSON of a Bucket Policy
 * into internal objects.
 * 
 * Another way to implement this by use of a stack to keep track of where the current
 * parsing is being done.   However, since we are only handling a limited JSON sequence
 * here simple counts and flags will do the same as a stack.
 */
public class PolicyParser {

	private S3BucketPolicy bucketPolicy = null;
	private S3PolicyPrincipal principals = null;
	private S3PolicyStatement statement = null;
	private S3PolicyAction actions = null;
	private S3PolicyCondition condition = null;
	private S3PolicyConditionBlock block = null;
	private String id =  null;
	private String sid = null;
	private String effect = null;
	private String resource = null;
	private String notAction = null;
	private String condKey = null;   // -> the next key in a condition
	private List<String> valueList = new ArrayList<String>();
	
	private JSONParser jparser = null;
	private int entryNesting = 0;    // -> startObjectEntry() .. nesting count
	private int condNested = 0;      // -> at what level of nesting is the condition defined
	private int keyNested = 0;       // -> at what level of nesting is the condition key defined
	
	private boolean debugOn = false;
	private boolean inId  = false;   // -> currently in an "Id" element
	private boolean inSid = false;
	private boolean inAWS = false;
	private boolean inEffect = false;
	private boolean inResource = false;	
	private boolean inNotAction = false;
	private boolean inVersion = false;
	private boolean inStatement = false;
	
	
	public PolicyParser( boolean debugOn ) {
		this.debugOn = debugOn;
		jparser = new JSONParser();
	}

	ContentHandler myHandler = new ContentHandler() {

		public boolean endArray() throws ParseException {
			if (debugOn) System.out.println("endArray()");
			return true;
		}

		public void endJSON() throws ParseException {
			if (debugOn) System.out.println("endJSON()");
			
			if (null != statement) {
				//System.out.println( "endJSON() - statement");
				if (null != block) statement.setConditionBlock( block );
			    if (null != bucketPolicy) bucketPolicy.addStatement( statement );
			    statement = null;
			    block = null;
			}
		}

		public boolean endObject() throws ParseException {
			if (debugOn) System.out.println("endObject(), nesting: " + entryNesting);
			
			if (null != statement && 1 >= entryNesting) {
				//System.out.println( "endObject() - statement");
				if (null != block) statement.setConditionBlock( block );
				if (null != bucketPolicy) bucketPolicy.addStatement( statement );
				statement = null;
				block = null;
			}
			
			if (0 == entryNesting) inStatement = false;
			return true;
		}

		public boolean endObjectEntry() throws ParseException {
			if (debugOn) System.out.println("endObjectEntry(), nesting: " + entryNesting);
			
			     if (inSid) {
				 if (null != statement) statement.setSid( sid );
				 inSid = false;
			}
		    else if (inEffect) 
		    {
			     if (null != statement) 
			     {
			    	      if (effect.equalsIgnoreCase("Allow")) statement.setEffect( PolicyAccess.ALLOW );
			    	 else if (effect.equalsIgnoreCase("Deny" )) statement.setEffect( PolicyAccess.DENY );
			     }
			     inEffect = false;
			}
			else if (inResource) 
			{
				 if (null != statement && resource.startsWith("arn:aws:s3:::")) {
					 statement.setResource( resource.substring(13) );
				 }
				 inResource = false;
			}
			else if (inNotAction) 
			{
				 if (null != statement) statement.setNotAction( notAction );
				 inNotAction = false;
			}
			else if (inVersion) 
			{
				 inVersion = false;
			}
			else if (inId) 
			{
				 if (null != bucketPolicy) bucketPolicy.setId( id );
				 inId = false;
			}
			else if (null != actions) 
			{
				 if (null != statement) statement.setActions( actions );
				 actions = null;
			}
			else if (null != principals) 
			{
				 if (inAWS && null != statement) statement.setPrincipals( principals );
			     principals = null;
			}
			else if (null != condition) 
			{
				 //System.out.println( "in condition: " + condNested + " " + entryNesting + " " + keyNested );
				 // -> is it just the current key that is done?
				 if (keyNested == entryNesting) {
					 String[] values = valueList.toArray(new String[0]);
					 condition.setKey(condKey, values);
					 valueList.clear();
					 condKey = null;
				 }
				 
				 // -> is the condition completely done?
				 if (condNested == entryNesting) {
					 block.addCondition( condition );
					 condition = null;
				 }
			}
			else if (null != statement && 1 == entryNesting) 
			{
				 if (null != block) statement.setConditionBlock( block );
				 if (null != bucketPolicy) bucketPolicy.addStatement( statement );
				 statement = null;
				 block = null;
			}
			     
		    entryNesting--;
			return true;
		}

		public boolean primitive(Object value) throws ParseException, IOException {
			if (debugOn) System.out.println("primitive(): " + value);
			
			     if (inSid) sid = (String)value;
			else if (inEffect) effect = (String)value;
			else if (inResource) resource = (String)value;
			else if (inNotAction) notAction = (String)value;
			else if (inId) id = (String)value;
			else if (null != actions   ) actions.addAction( actions.toPolicyActions((String)value));
			else if (null != principals) principals.addPrincipal( (String)value );
			else if (null != condition) valueList.add( (String)value );
			else if (inVersion) {
		    	 String version = (String)value;
		    	 if (!version.equals( "2008-10-17" )) 
		    		 throw new IOException( "S3 Bucket Policy has unsupported version: " + version );
		     }

			return true;
		}

		public boolean startArray() throws ParseException {
			if (debugOn) System.out.println("startArray()");
			return true;
		}

		public void startJSON() throws ParseException {
			if (debugOn) System.out.println("startJSON()");
		}

		public boolean startObject() throws ParseException {
			if (debugOn) System.out.println("startObject(), nesting: " + entryNesting);
			
			if (1 == entryNesting && inStatement) statement = new S3PolicyStatement();
			
			return true;
		}

		public boolean startObjectEntry(String key) throws ParseException {
			entryNesting++;
			if (debugOn) System.out.println("startObjectEntry(), key: [" + key + "]");
	
			inSid = false; inAWS = false; inEffect = false; inResource = false; 
			inNotAction = false; inVersion = false; inId = false;
			
			     if (key.equalsIgnoreCase( "Statement" )) inStatement = true;
			else if (key.equalsIgnoreCase( "Action"    )) actions = new S3PolicyAction();
			else if (key.equalsIgnoreCase( "Principal" )) principals = new S3PolicyPrincipal();
			else if (key.equalsIgnoreCase( "Condition" )) block = new S3PolicyConditionBlock();
			else if (key.equalsIgnoreCase( "AWS" ) && null != principals) inAWS = true;
			else if (key.equalsIgnoreCase( "Sid"       )) inSid = true;
			else if (key.equalsIgnoreCase( "Effect"    )) inEffect = true;
			else if (key.equalsIgnoreCase( "Resource"  )) inResource = true;
			else if (key.equalsIgnoreCase( "NotAction" )) inNotAction = true;
			else if (key.equalsIgnoreCase( "Version"   )) inVersion = true;
			else if (key.equalsIgnoreCase( "Id" )) inId = true;
			else if (null != condition) {
				 condKey = key;
				 keyNested = entryNesting;
			}
			else if (null != block) {
				 condition = new S3PolicyCondition();
				 condition.setCondition( key );
				 condNested = entryNesting;
			}
			else if (debugOn) {
				 System.out.println("startObjectEntry() no match");
			}
			     
			return true;
		}
	};    			

	
	public S3BucketPolicy parse( String policy, String bucketName ) throws ParseException {
		
		bucketPolicy = new S3BucketPolicy();
		bucketPolicy.setBucketName( bucketName );
	    jparser.parse(policy, myHandler);
	    return bucketPolicy;
	}
}
