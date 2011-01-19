package com.cloud.bridge.util;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.cloud.bridge.service.core.s3.S3PolicyAction;
import com.cloud.bridge.service.core.s3.S3PolicyStatement;

/**
 * This class uses the JSON simple parser to convert the JSON of a Bucket Policy
 * into internal objects.
 */
public class PolicyParser {

	S3PolicyStatement statement = null;
	S3PolicyAction action = null;
	int entryNesting = 0;
	
	JSONParser jparser = new JSONParser();

	ContentHandler myHandler = new ContentHandler() {

		public boolean endArray() throws ParseException {
			System.out.println("endArray()");
			return true;
		}

		public void endJSON() throws ParseException {
			System.out.println("endJSON()");
		}

		public boolean endObject() throws ParseException {
			System.out.println("endObject()");
			return true;
		}

		public boolean endObjectEntry() throws ParseException {
			System.out.println("endObjectEntry()");
			
			entryNesting--;
			
			if (null != action) {
				statement.setActions( action );
				action = null;
			}
			else if (null != statement) {
				// TODO place statement into the policy object
				statement = null;
			}
			return true;
		}

		public boolean primitive(Object value) throws ParseException {
			System.out.println("primitive(): " + value);
			
			if (null != action) action.setAction( (String)value );
				
			return true;
		}

		public boolean startArray() throws ParseException {
			System.out.println("startArray()");
			return true;
		}

		public void startJSON() throws ParseException {
			System.out.println("startJSON()");
		}

		public boolean startObject() throws ParseException {
			System.out.println("startObject()");
			return true;
		}

		public boolean startObjectEntry(String key) throws ParseException {
			System.out.println("startObjectEntry(), key:" + key);
	
			     if (key.equalsIgnoreCase( "Statement" )) statement = new S3PolicyStatement();
			else if (key.equalsIgnoreCase( "Action"    )) action = new S3PolicyAction();
			     
			entryNesting++;
			return true;
		}
	};    			

	
	public void parse( String policy ) throws ParseException {
		
	    jparser.parse(policy, myHandler);
	}
}
