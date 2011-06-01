package com.cloud.gate.util;

import org.apache.log4j.Logger;

import com.cloud.gate.testcase.BaseTestCase;
import com.cloud.stack.CloudStackClient;
import com.cloud.stack.CloudStackCommand;

public class CloudStackClientTestCase extends BaseTestCase {
    protected final static Logger logger = Logger.getLogger(CloudStackClientTestCase.class);

    // remember to replace with a valid key-pair in test
    private final static String API_KEY = "kVMfr1iE0KlKKOUPD-H4GburZHo4KLxIczbl5CM_ilcKFXkmsIfZjWIkCY5QpuKpDvu-DyFud44VfVvXmPKMkw";
    private final static String SECRET_KEY = "a5Y0ysvVHZ0cuffaV26wRm_vvsV5VQldRq9udC21AE8Kwsk0JG8-pz6YSp3bbc3rC0kK5q3_B9QBBzjHafVicw";

    public void testCloudStackClient() {
    	CloudStackClient client = new CloudStackClient("192.168.130.22", 8080, false);

		CloudStackCommand command = new CloudStackCommand("stopVirtualMachine");
		command.setParam("id", "246443");
		try {
			client.execute(command, API_KEY, SECRET_KEY);
		} catch(Exception e) {
			logger.error("Unexpected exception ", e);
		}
    }
}
