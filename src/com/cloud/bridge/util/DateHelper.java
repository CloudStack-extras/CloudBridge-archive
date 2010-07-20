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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Kelven Yang
 */
public class DateHelper {
    public static final TimeZone GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
    public static final String YYYYMMDD_FORMAT = "yyyyMMddHHmmss";
    
	public static Date currentGMTTime() {
		return new Date();
	}
	
	public static SimpleDateFormat getGMTDateFormat(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df;
	}
	
	public static Date parseDateString(TimeZone tz, String dateString) {
		return parseDateString(tz, dateString, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date parseDateString(TimeZone tz, String dateString, String formatString) {
		DateFormat df = new SimpleDateFormat(formatString);
		df.setTimeZone(tz);
		
    	try {
    		return df.parse(dateString);
    	} catch (ParseException e) {
    		throw new IllegalArgumentException(e);
    	}
	}
	
	public static String getDateDisplayString(TimeZone tz, Date time) {
		return getDateDisplayString(tz, time, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getDateDisplayString(TimeZone tz, Date time, String formatString) {
		DateFormat df = new SimpleDateFormat(formatString);
		df.setTimeZone(tz);
		
		return df.format(time);
	}
	
	public static Calendar toCalendar(Date dt) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		return calendar;
	}
}
