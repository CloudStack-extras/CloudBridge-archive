package com.cloud.bridge.util;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cloud.bridge.service.core.s3.S3BucketPolicy;
import com.cloud.bridge.service.core.s3.S3PolicyAction;
import com.cloud.bridge.service.core.s3.S3PolicyPrincipal;
import com.cloud.bridge.service.core.s3.S3PolicyStatement;

/**
 * This class uses the JSON simple parser to convert the JSON of a Bucket Policy
 * into internal objects.
 */
public class PolicyParser {

	S3BucketPolicy bucketPolicy = null;
	S3PolicyPrincipal principals = null;
	S3PolicyStatement statement = null;
	S3PolicyAction actions = null;
	String sid = null;
	String effect = null;
	String resource = null;
	
	JSONParser jparser = null;
	int entryNesting = 0;
	boolean debugOn = false;

	boolean inSid = false;
	boolean inAWS = false;
	boolean inEffect = false;
	boolean inResource = false;	
	boolean inStatement = false;
	
	
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
			    if (null != bucketPolicy) bucketPolicy.addStatement( statement );
			    statement = null;
			}
		}

		public boolean endObject() throws ParseException {
			if (debugOn) System.out.println("endObject(), nesting: " + entryNesting);
			
			if (null != statement && 1 >= entryNesting) {
				//System.out.println( "endObject() - statement");
				if (null != bucketPolicy) bucketPolicy.addStatement( statement );
				statement = null;
			}
			
			if (0 == entryNesting) inStatement = false;
			return true;
		}

		public boolean endObjectEntry() throws ParseException {
			entryNesting--;

			if (debugOn) System.out.println("endObjectEntry(), nesting: " + entryNesting);
			
			     if (inSid) {
				 if (null != statement) statement.setSid( sid );
				 inSid = false;
			}
		    else if (inEffect) {
			     if (null != statement) statement.setEffect( effect );
			     inEffect = false;
			}
			else if (inResource) {
				 if (null != statement) statement.setResource( resource );
				 inResource = false;
			}
			else if (null != actions) {
				 if (null != statement) statement.setActions( actions );
				 actions = null;
			}
			else if (null != principals) {
				 if (inAWS && null != statement) statement.setPrincipals( principals );
			     principals = null;
			}
			else if (null != statement && 0 == entryNesting) {
				 if (null != bucketPolicy) bucketPolicy.addStatement( statement );
				 statement = null;
			}
			return true;
		}

		public boolean primitive(Object value) throws ParseException {
			if (debugOn) System.out.println("primitive(): " + value);
			
			     if (inSid) sid = (String)value;
			else if (inEffect) effect = (String)value;
			else if (inResource) resource = (String)value;
			else if (null != actions   ) actions.addAction( (String)value );
			else if (null != principals) principals.addPrincipal( (String)value );
	
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
			if (debugOn) System.out.println("startObjectEntry(), key: [" + key + "]");
	
			inSid = false; inAWS = false; inEffect = false; inResource = false;
			
			     if (key.equalsIgnoreCase( "Statement" )) inStatement = true;
			else if (key.equalsIgnoreCase( "Action"    )) actions = new S3PolicyAction();
			else if (key.equalsIgnoreCase( "Principal" )) principals = new S3PolicyPrincipal();
			else if (key.equalsIgnoreCase( "AWS" ) && null != principals) inAWS = true;
			else if (key.equalsIgnoreCase( "Sid"      )) inSid = true;
			else if (key.equalsIgnoreCase( "Effect"   )) inEffect = true;
			else if (key.equalsIgnoreCase( "Resource" )) inResource = true;
			else if (key.equalsIgnoreCase( "Version"  )) ;
			else if (key.equalsIgnoreCase( "Id" )) ;
			else if (debugOn) System.out.println("startObjectEntry() no match");
			     
			entryNesting++;
			return true;
		}
	};    			

	
	public S3BucketPolicy parse( String policy ) throws ParseException {
		
		bucketPolicy = new S3BucketPolicy();
	    jparser.parse(policy, myHandler);
	    return bucketPolicy;
	}
}
