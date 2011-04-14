package com.cloud.bridge.service.core.ec2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.bridge.service.exception.EC2ServiceException;
import com.cloud.bridge.util.DateHelper;


public class EC2VolumeFilterSet {

	protected List<EC2Filter> filterSet = new ArrayList<EC2Filter>();    
	
	private Map<String,String> filterTypes = new HashMap<String,String>();


	public EC2VolumeFilterSet() 
	{
		// -> use these values to check that the proper filter is passed to this type of filter set
		filterTypes.put( "attachment.attach-time",           "xsd:dateTime" );
		filterTypes.put( "attachment.delete-on-termination", "null"         );
		filterTypes.put( "attachment.device",                "string"       );
		filterTypes.put( "attachment.instance-id",           "string"       );
		filterTypes.put( "attachment.status",                "null"         );
		filterTypes.put( "availability-zone",                "string"       );
		filterTypes.put( "create-time",                      "xsd:dateTime" );
		filterTypes.put( "size",                             "integer"      );
		filterTypes.put( "snapshot-id",                      "string"       );
		filterTypes.put( "status",                           "set:creating|available|in-use|deleting|deleted|error" );
		filterTypes.put( "tag-key",                          "null"         );
		filterTypes.put( "tag-value",                        "null"         );
		filterTypes.put( "volume-id",                        "string"       );	
//		filterTypes.put( "tag:*",                            "null" );
	}
	
	
	public void addFilter( EC2Filter param ) 
	{	
		String filterName = param.getName();
		String value = (String) filterTypes.get( filterName );
		
		if (null == value)
			throw new EC2ServiceException( "Unsupported filter [" + filterName + "] - 1", 501 );
		
		if (null != value && value.equalsIgnoreCase( "null" ))
			throw new EC2ServiceException( "Unsupported filter [" + filterName + "] - 2", 501 );

		// ToDo we could add checks to make sure the type of a filters value is correct (e.g., an integer)
		filterSet.add( param );
	}
	
	
	public EC2Filter[] getFilterSet() {
		return filterSet.toArray(new EC2Filter[0]);
	}
	
	
	public boolean allowedByFilter() {
		
		return true;
	}

	
	/**
	 * For a filter to match a volume just one of its values has to match the volume.
	 * For a volume to be included in the volume response it must pass all the defined filters.
	 * 
	 * @param sampleList - list of volumes to test against the defined filters
	 * @return EC2DescribeVolumeResponse
	 * @throws ParseException 
	 */
	public EC2DescribeVolumesResponse evaluate( EC2DescribeVolumesResponse sampleList ) throws ParseException 
	{
    	EC2DescribeVolumesResponse resultList = new EC2DescribeVolumesResponse();
    	boolean matched;
    	
    	EC2Volume[] volumeSet = sampleList.getVolumeSet();
    	EC2Filter[] filterSet = getFilterSet();
    	for( int i=0; i < volumeSet.length; i++ )
    	{
    		matched = true;
    		for( int j=0; j < filterSet.length; j++ )
    		{
    			if (!filterMatched( volumeSet[i], filterSet[j] )) {
    				matched = false;
    				break;
    			}
    		}
    		
    		if (matched) resultList.addVolume( volumeSet[i] );
    	}

		return resultList;
	}
	
	
	private boolean filterMatched( EC2Volume vol, EC2Filter filter ) throws ParseException
	{
		String filterName = filter.getName();
		String[] valueSet = filter.getValueSet();
		
	    if ( filterName.equalsIgnoreCase( "availability-zone" )) 
	    {
	    	 return containsString( vol.getZoneName(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "create-time" ))
	    {
	         return containsTime( vol.getCreated(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "size" )) 
	    {
	         return containsInteger( vol.getSize(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "snapshot-id" )) 
	    {	
	    	 return containsString( vol.getSnapShotId(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "status" )) 
	    {	
	    	 return containsString( vol.getState(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "volume-id" )) 
	    {	
	    	 return containsString( vol.getId(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "attachment.attach-time" ))
	    {
	         return containsTime( vol.getAttached(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "attachment.device" )) 
	    {
	         return containsDevice( vol.getDeviceId(), valueSet );	
	    }
	    else if (filterName.equalsIgnoreCase( "attachment.instance-id" )) 
	    {
	    	 return containsString( vol.getInstanceId(), valueSet );		
	    }
	    else return false;
	}
	
	
	private boolean containsString( String lookingFor, String[] set )
	{
		if (null == lookingFor) return false;
		
	    for( int i=0; i < set.length; i++ )
	    {
	    	//System.out.println( "contsinsString: " + lookingFor + " " + set[i] );
	    	if (lookingFor.matches( set[i] )) return true;
	    }
	    return false;
	}
	
	
	private boolean containsInteger( int lookingFor, String[] set )
	{
        for( int i=0; i < set.length; i++ )
        {
	    	//System.out.println( "contsinsInteger: " + lookingFor + " " + set[i] );
        	int temp = Integer.parseInt( set[i] );
        	if (lookingFor == temp) return true;
        }
		return false;
	}

	
	private boolean containsTime( Calendar lookingFor, String[] set ) throws ParseException
	{
        for( int i=0; i < set.length; i++ )
        {
	    	//System.out.println( "contsinsCalendar: " + lookingFor + " " + set[i] );
        	Calendar toMatch = Calendar.getInstance();
        	toMatch.setTime( DateHelper.parseISO8601DateString( set[i] ));
        	if (0 == lookingFor.compareTo( toMatch )) return true;
        }
		return false;
	}

	
	private boolean containsDevice( int deviceId, String[] set )
	{
        for( int i=0; i < set.length; i++ )
        {
	    	//System.out.println( "contsinsDevice: " + deviceId + " " + set[i] );
        	switch( deviceId ) {
        	case 1:
       		     if (( "/dev/sdb" ).matches( set[i] )) return true;
    		     if (( "/dev/xvdb").matches( set[i] )) return true;
        		 break;
        		 
        	case 2:
       		     if (( "/dev/sdc"  ).matches( set[i] )) return true;
    		     if (( "/dev/xvdc" ).matches( set[i] )) return true;
        		 break;
        		 
        	case 4:
       		     if (( "/dev/sde"  ).matches( set[i] )) return true;
    		     if (( "/dev/xvde" ).matches( set[i] )) return true;
        		 break;
        		 
        	case 5:
      		     if (( "/dev/sdf"  ).matches( set[i] )) return true;
   		         if (( "/dev/xvdf" ).matches( set[i] )) return true;
       		     break;

        	case 6:
     		     if (( "/dev/sdg"  ).matches( set[i] )) return true;
  		         if (( "/dev/xvdg" ).matches( set[i] )) return true;
      		     break;

        	case 7:
    		     if (( "/dev/sdh"  ).matches( set[i] )) return true;
 		         if (( "/dev/xvdh" ).matches( set[i] )) return true;
     		     break;

        	case 8:
    		     if (( "/dev/sdi"  ).matches( set[i] )) return true;
 		         if (( "/dev/xvdi" ).matches( set[i] )) return true;
     		     break;

        	case 9:
    		     if (( "/dev/sdj"  ).matches( set[i] )) return true;
 		         if (( "/dev/xvdj" ).matches( set[i] )) return true;
     		     break;
        	}
        }
		return false;
	}
}
