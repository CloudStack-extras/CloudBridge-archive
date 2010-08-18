package com.cloud.bridge.service.core.s3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class S3ConditionalHeaders {
    protected Date   modifiedSince;
    protected Date   unmodifiedSince;
    protected String ifMatch;      
    protected String ifNoneMatch;
	
	public S3ConditionalHeaders() {
		modifiedSince = null;
		unmodifiedSince = null;
		ifMatch = null;
		ifNoneMatch = null;
	}
	
	public void setModifiedSince( String ifModifiedSince ) {
		DateFormat formatter = null;
        Calendar cal = Calendar.getInstance();

		try {
		    formatter = new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss z" );
		    cal.setTime( formatter.parse( ifModifiedSince )); 
            modifiedSince = cal.getTime();	              
		} catch( Exception e ) {}
	}
	
	public void setUnModifiedSince( String ifUnmodifiedSince ) {
		DateFormat formatter = null;
        Calendar cal = Calendar.getInstance();

		try {
		    formatter = new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss z" );
		    cal.setTime( formatter.parse( ifUnmodifiedSince )); 
            unmodifiedSince = cal.getTime();	              
		} catch( Exception e ) {}		
	}
	
	public void setMatch( String ifMatch ) {
		this.ifMatch = (null == ifMatch ? null : ifMatch.trim());
	}
	
	public void setNoneMatch( String ifNoneMatch ) {
		this.ifNoneMatch = (null == ifNoneMatch ? null : ifNoneMatch.trim());
	}
	
	/**
	 * Has the object been modified since the client has last checked?
	 * 
	 * @param lastModified
	 * @return a negative value means that the object has not been modified since
	 *         a postive  value means that this test should be ignored.
	 */
	public int ifModifiedSince( Date lastModified ) 
	{
		if (null == modifiedSince) return 1;
	
	    if ( 0 >= modifiedSince.compareTo( lastModified )) 
	    	 return  2;
	    else return -1;
	}

	/**
	 * Has the object been modified since the unmodified date?
	 * 
	 * @param lastModified
	 * @return a negative value means that the object has been modified since
	 *         a postive  value means that this test should be ignored.
	 */
	public int ifUnmodifiedSince( Date lastModified ) 
	{
		if (null == unmodifiedSince) return 1;
		
	    if ( 0 < unmodifiedSince.compareTo( lastModified )) 
	    	 return  2;
	    else return -1;
	}
	
	/**
	 * Does the object's contents (its MD5 signature) match what the client thinks
	 * it is?
	 * 
	 * @param ETag - an MD5 signature of the content of the data being stored in S3
	 * @return a negative value means that the test has failed,
	 *         a positive value means that the test succeeded or could not be done (so ignore it)
	 */
	public int ifMatchEtag( String ETag ) 
	{
		if (null == ifMatch || null == ETag) return 1;
		
		if ( ifMatch.equals( ETag ))
			 return 2;
		else return -1;
	}
	
	/**
	 * Is the content (measured by its MD5 signature) of the stored object different from 
	 * what the client thinks?
	 * 
	 * @param ETag - an MD5 signature of the content of the data being stored in S3
	 * @return a negative value means that the test has failed,
	 *         a positive value means that the test succeeded or could not be done (so ignore it)
	 */
	public int ifNoneMatchEtag( String ETag ) 
	{
		if (null == ifNoneMatch || null == ETag) return 1;
		
		if ( !ifNoneMatch.equals( ETag ))
			 return 2;
		else return -1;
	}
}
