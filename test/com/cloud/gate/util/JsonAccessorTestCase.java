package com.cloud.gate.util;

import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.cloud.bridge.util.JsonAccessor;
import com.cloud.gate.testcase.BaseTestCase;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonAccessorTestCase extends BaseTestCase {
    protected final static Logger logger = Logger.getLogger(UtilTestCase.class);
	
    public void testJsonAccessor() {
    	JsonParser parser = new JsonParser(); 
    	
    	JsonElement json = parser.parse("{firstName: 'Kelven', lastName: 'Yang', arrayObj: [{name: 'elem1'}, {name: 'elem2'}]}");
    	
    	JsonAccessor jsonAccessor = new JsonAccessor(json);
    	
    	Assert.assertTrue("Kelven".equals(jsonAccessor.getAsString("firstName")));
    	Assert.assertTrue("Kelven".equals(jsonAccessor.getAsString("this.firstName")));
    	Assert.assertTrue("Yang".equals(jsonAccessor.getAsString("lastName")));
    	Assert.assertTrue("Yang".equals(jsonAccessor.getAsString("this.lastName")));
    	
    	Assert.assertTrue("elem1".equals(jsonAccessor.getAsString("arrayObj[0].name")));
    	Assert.assertTrue("elem2".equals(jsonAccessor.getAsString("arrayObj[1].name")));
    	
    	Assert.assertTrue("elem1".equals(jsonAccessor.getAsString("this.arrayObj.this[0].name")));
    	Assert.assertTrue("elem2".equals(jsonAccessor.getAsString("this.arrayObj.this[1].name")));
    	
    	Assert.assertTrue(jsonAccessor.getMatchCount("firstName") == 1);
    	Assert.assertTrue(jsonAccessor.getMatchCount("middleName") == -1);
    	Assert.assertTrue(jsonAccessor.getMatchCount("arrayObj") == 2);
    	Assert.assertTrue(jsonAccessor.getMatchCount("arrayObj[0]") == 1);
    }
}
