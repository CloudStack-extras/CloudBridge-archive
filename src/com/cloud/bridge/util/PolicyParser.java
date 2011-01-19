package com.cloud.bridge.util;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class uses the JSON simple parser to convert the JSON of a Bucket Policy
 * into internal objects.
 */
public class PolicyParser {

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
			return true;
		}

		public boolean primitive(Object value) throws ParseException {
			System.out.println("primitive(): " + value);
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
			return true;
		}
	};    			

	
	public void parse( String policy ) throws ParseException {
		
	    jparser.parse(policy, myHandler);
	}
}
