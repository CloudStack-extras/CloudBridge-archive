
package com.cloud.bridge.service.core.ec2;

public class EC2Address {

    private String ipAddress = null;
    private String associatedInstanceId = null;

    public EC2Address() { }

    public void setIpAddress( String ipAddress ) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setAssociatedInstanceId( String associatedInstanceId ) {
        this.associatedInstanceId = associatedInstanceId;
    }

    public String getAssociatedInstanceId() {
        return this.associatedInstanceId;
    }
}
