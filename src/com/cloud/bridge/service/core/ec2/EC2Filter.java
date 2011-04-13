package com.cloud.bridge.service.core.ec2;

import java.util.ArrayList;
import java.util.List;

import com.cloud.bridge.util.StringHelper;

public class EC2Filter {

	// -> a single filter can have several possible values to compare to
	protected String filterName;
	protected List<String> valueSet = new ArrayList<String>();    
	
	public EC2Filter() {
		filterName = null;
	}

	public String getName() {
		return filterName;
	}
	
	public void setName( String param ) {
	    filterName = param;	
	}
	
	/**
	 * From Amazon: 
	 * "You can use wildcards with the filter values: * matches zero or more characters, and ? matches 
	 * exactly one character. You can escape special characters using a backslash before the character. For 
	 * example, a value of \*amazon\?\\ searches for the literal string *amazon?\. "
     */
	public void addValueEncoded( String param ) {	
		valueSet.add( StringHelper.toRegex( param ));
	}

	public void addValue( String param ) {	
		valueSet.add( param );
	}

	public String[] getValueSet() {
		return valueSet.toArray(new String[0]);
	}
}
