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

import java.io.File;
import java.net.URL;

/**
 * @author Kelven Yang
 */
public class ConfigurationHelper {
	public static File findConfigurationFile(String name) {
        String newPath = "conf" + (name.startsWith(File.separator) ? "" : "/") + name;
        URL url = ClassLoader.getSystemResource(newPath);
        if (url != null) {
            return new File(url.getFile());
        }
		
        // if running under Tomcat
        newPath = System.getenv("CATALINA_HOME");
        if (newPath == null) {
        	newPath = System.getenv("CATALINA_BASE");
        }
        
        if (newPath == null) {
        	newPath = System.getProperty("catalina.home");
        }
        
        if (newPath == null) {
            return null;
        }
        
        File file = new File(newPath + File.separator + "conf" + File.separator + name);
        if (file.exists()) {
            return file;
        }
        return file;
	}
}
