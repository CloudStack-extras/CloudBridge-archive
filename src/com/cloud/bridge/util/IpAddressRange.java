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

/**
 * Represents a network IP address or a range of addresses.
 * A range is useful when representing IP addresses defined in 
 * CIDR format.   The range is a 32 bit IP inclusive.
 */
public class IpAddressRange {

	private int minAddress;
	private int maxAddress;
	
	public IpAddressRange() {
		
	}
	
	public int getMinAddress() {
		return minAddress;
	}
	
	public void setMinAddress(int param) {
		this.minAddress = param;
	}
	
	public int getMaxAddress() {
		return maxAddress;
	}
	
	public void setMaxAddress(int param) {
		this.maxAddress = param;
	}
	
	public String toString() 
	{
		StringBuffer value = new StringBuffer();
		value.append( "ip range min: " + minAddress );
		value.append( "\n" );
		value.append( "ip range max: " + maxAddress );
		value.append( "\n" );
		return value.toString();
	}
	
	/**
	 * Is the parameter (i.e., left) inside the range represented by this object?
	 * @param left
	 * @return boolean
	 */
	public boolean contains(IpAddressRange left) {	
		int leftMin = left.getMinAddress();
		
		if ( leftMin < minAddress || leftMin > maxAddress ) 
			 return false;
		else return true;
	}
	
	public static IpAddressRange parseRange(String ipAddress) 
	{
		IpAddressRange range = null;
		int maskBits = 0;
		int address = 0;
		
		if (null == ipAddress) return null;
		
	    // -> is it a CIDR format?
		String[] halfs = ipAddress.split( "/" );
		if ( 2 == halfs.length ) 
		{
			 range = new IpAddressRange();
			 address = IpAddressRange.ipToInt( halfs[0] );
			 
			 maskBits = Integer.parseInt( halfs[1] ) & 0xFF;
			 if (maskBits >= 1 && maskBits <= 32)
			 {
				 range.setMinAddress( address & (~((1 << (32-maskBits))-1) & 0xFFFFFFFF));
				 range.setMaxAddress( range.getMinAddress() | (((1 << (32-maskBits))-1) & 0xFFFFFFFF));
			 }	
		}	
		else if (1 == halfs.length)
		{
			 // -> should be just a simple IP address
			 range = new IpAddressRange();
			 address = IpAddressRange.ipToInt( ipAddress );
			 range.setMaxAddress( address );
			 range.setMinAddress( address );
		}

		return range;
	}
	
	private static int ipToInt(String ipAddress) 
	{
		String[] parts = ipAddress.split( "." );
		if (4 != parts.length) return 0;
		
		int[] address = new int[4];
		address[0] = Integer.parseInt( parts[0] ) & 0xFF;
		address[1] = Integer.parseInt( parts[1] ) & 0xFF;
		address[2] = Integer.parseInt( parts[2] ) & 0xFF;
		address[3] = Integer.parseInt( parts[3] ) & 0xFF;
		
		if (address[0] > 255 || address[1] > 255 || address[2] > 255 || address[3] > 255) return 0;
		
		return (address[0]<<24) | (address[1]<<16) | (address[2]<<8) | address[3];
	}	
}
