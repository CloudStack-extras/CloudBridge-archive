
/**
 * AmazonEC2CallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package com.amazon.ec2.client;

    /**
     *  AmazonEC2CallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AmazonEC2CallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AmazonEC2CallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AmazonEC2CallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for describeInstances method
            * override this method for handling normal response from describeInstances operation
            */
           public void receiveResultdescribeInstances(
                    com.amazon.ec2.DescribeInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeInstances operation
           */
            public void receiveErrordescribeInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeRegions method
            * override this method for handling normal response from describeRegions operation
            */
           public void receiveResultdescribeRegions(
                    com.amazon.ec2.DescribeRegionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeRegions operation
           */
            public void receiveErrordescribeRegions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stopInstances method
            * override this method for handling normal response from stopInstances operation
            */
           public void receiveResultstopInstances(
                    com.amazon.ec2.StopInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stopInstances operation
           */
            public void receiveErrorstopInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for rebootInstances method
            * override this method for handling normal response from rebootInstances operation
            */
           public void receiveResultrebootInstances(
                    com.amazon.ec2.RebootInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from rebootInstances operation
           */
            public void receiveErrorrebootInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeVpcs method
            * override this method for handling normal response from describeVpcs operation
            */
           public void receiveResultdescribeVpcs(
                    com.amazon.ec2.DescribeVpcsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeVpcs operation
           */
            public void receiveErrordescribeVpcs(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for bundleInstance method
            * override this method for handling normal response from bundleInstance operation
            */
           public void receiveResultbundleInstance(
                    com.amazon.ec2.BundleInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from bundleInstance operation
           */
            public void receiveErrorbundleInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for runInstances method
            * override this method for handling normal response from runInstances operation
            */
           public void receiveResultrunInstances(
                    com.amazon.ec2.RunInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from runInstances operation
           */
            public void receiveErrorrunInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteSubnet method
            * override this method for handling normal response from deleteSubnet operation
            */
           public void receiveResultdeleteSubnet(
                    com.amazon.ec2.DeleteSubnetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteSubnet operation
           */
            public void receiveErrordeleteSubnet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for terminateInstances method
            * override this method for handling normal response from terminateInstances operation
            */
           public void receiveResultterminateInstances(
                    com.amazon.ec2.TerminateInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from terminateInstances operation
           */
            public void receiveErrorterminateInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeKeyPairs method
            * override this method for handling normal response from describeKeyPairs operation
            */
           public void receiveResultdescribeKeyPairs(
                    com.amazon.ec2.DescribeKeyPairsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeKeyPairs operation
           */
            public void receiveErrordescribeKeyPairs(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPasswordData method
            * override this method for handling normal response from getPasswordData operation
            */
           public void receiveResultgetPasswordData(
                    com.amazon.ec2.GetPasswordDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPasswordData operation
           */
            public void receiveErrorgetPasswordData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetImageAttribute method
            * override this method for handling normal response from resetImageAttribute operation
            */
           public void receiveResultresetImageAttribute(
                    com.amazon.ec2.ResetImageAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetImageAttribute operation
           */
            public void receiveErrorresetImageAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createVolume method
            * override this method for handling normal response from createVolume operation
            */
           public void receiveResultcreateVolume(
                    com.amazon.ec2.CreateVolumeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createVolume operation
           */
            public void receiveErrorcreateVolume(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for monitorInstances method
            * override this method for handling normal response from monitorInstances operation
            */
           public void receiveResultmonitorInstances(
                    com.amazon.ec2.MonitorInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from monitorInstances operation
           */
            public void receiveErrormonitorInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteSpotDatafeedSubscription method
            * override this method for handling normal response from deleteSpotDatafeedSubscription operation
            */
           public void receiveResultdeleteSpotDatafeedSubscription(
                    com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteSpotDatafeedSubscription operation
           */
            public void receiveErrordeleteSpotDatafeedSubscription(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for unmonitorInstances method
            * override this method for handling normal response from unmonitorInstances operation
            */
           public void receiveResultunmonitorInstances(
                    com.amazon.ec2.UnmonitorInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from unmonitorInstances operation
           */
            public void receiveErrorunmonitorInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for associateDhcpOptions method
            * override this method for handling normal response from associateDhcpOptions operation
            */
           public void receiveResultassociateDhcpOptions(
                    com.amazon.ec2.AssociateDhcpOptionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from associateDhcpOptions operation
           */
            public void receiveErrorassociateDhcpOptions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteCustomerGateway method
            * override this method for handling normal response from deleteCustomerGateway operation
            */
           public void receiveResultdeleteCustomerGateway(
                    com.amazon.ec2.DeleteCustomerGatewayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteCustomerGateway operation
           */
            public void receiveErrordeleteCustomerGateway(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authorizeSecurityGroupIngress method
            * override this method for handling normal response from authorizeSecurityGroupIngress operation
            */
           public void receiveResultauthorizeSecurityGroupIngress(
                    com.amazon.ec2.AuthorizeSecurityGroupIngressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authorizeSecurityGroupIngress operation
           */
            public void receiveErrorauthorizeSecurityGroupIngress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createSnapshot method
            * override this method for handling normal response from createSnapshot operation
            */
           public void receiveResultcreateSnapshot(
                    com.amazon.ec2.CreateSnapshotResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createSnapshot operation
           */
            public void receiveErrorcreateSnapshot(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getConsoleOutput method
            * override this method for handling normal response from getConsoleOutput operation
            */
           public void receiveResultgetConsoleOutput(
                    com.amazon.ec2.GetConsoleOutputResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getConsoleOutput operation
           */
            public void receiveErrorgetConsoleOutput(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createImage method
            * override this method for handling normal response from createImage operation
            */
           public void receiveResultcreateImage(
                    com.amazon.ec2.CreateImageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createImage operation
           */
            public void receiveErrorcreateImage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSpotPriceHistory method
            * override this method for handling normal response from describeSpotPriceHistory operation
            */
           public void receiveResultdescribeSpotPriceHistory(
                    com.amazon.ec2.DescribeSpotPriceHistoryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSpotPriceHistory operation
           */
            public void receiveErrordescribeSpotPriceHistory(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeAddresses method
            * override this method for handling normal response from describeAddresses operation
            */
           public void receiveResultdescribeAddresses(
                    com.amazon.ec2.DescribeAddressesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeAddresses operation
           */
            public void receiveErrordescribeAddresses(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteSnapshot method
            * override this method for handling normal response from deleteSnapshot operation
            */
           public void receiveResultdeleteSnapshot(
                    com.amazon.ec2.DeleteSnapshotResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteSnapshot operation
           */
            public void receiveErrordeleteSnapshot(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deletePlacementGroup method
            * override this method for handling normal response from deletePlacementGroup operation
            */
           public void receiveResultdeletePlacementGroup(
                    com.amazon.ec2.DeletePlacementGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deletePlacementGroup operation
           */
            public void receiveErrordeletePlacementGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for detachVpnGateway method
            * override this method for handling normal response from detachVpnGateway operation
            */
           public void receiveResultdetachVpnGateway(
                    com.amazon.ec2.DetachVpnGatewayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from detachVpnGateway operation
           */
            public void receiveErrordetachVpnGateway(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeBundleTasks method
            * override this method for handling normal response from describeBundleTasks operation
            */
           public void receiveResultdescribeBundleTasks(
                    com.amazon.ec2.DescribeBundleTasksResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeBundleTasks operation
           */
            public void receiveErrordescribeBundleTasks(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeLicenses method
            * override this method for handling normal response from describeLicenses operation
            */
           public void receiveResultdescribeLicenses(
                    com.amazon.ec2.DescribeLicensesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeLicenses operation
           */
            public void receiveErrordescribeLicenses(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteVpnGateway method
            * override this method for handling normal response from deleteVpnGateway operation
            */
           public void receiveResultdeleteVpnGateway(
                    com.amazon.ec2.DeleteVpnGatewayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteVpnGateway operation
           */
            public void receiveErrordeleteVpnGateway(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for purchaseReservedInstancesOffering method
            * override this method for handling normal response from purchaseReservedInstancesOffering operation
            */
           public void receiveResultpurchaseReservedInstancesOffering(
                    com.amazon.ec2.PurchaseReservedInstancesOfferingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from purchaseReservedInstancesOffering operation
           */
            public void receiveErrorpurchaseReservedInstancesOffering(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteDhcpOptions method
            * override this method for handling normal response from deleteDhcpOptions operation
            */
           public void receiveResultdeleteDhcpOptions(
                    com.amazon.ec2.DeleteDhcpOptionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteDhcpOptions operation
           */
            public void receiveErrordeleteDhcpOptions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createCustomerGateway method
            * override this method for handling normal response from createCustomerGateway operation
            */
           public void receiveResultcreateCustomerGateway(
                    com.amazon.ec2.CreateCustomerGatewayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createCustomerGateway operation
           */
            public void receiveErrorcreateCustomerGateway(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteKeyPair method
            * override this method for handling normal response from deleteKeyPair operation
            */
           public void receiveResultdeleteKeyPair(
                    com.amazon.ec2.DeleteKeyPairResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteKeyPair operation
           */
            public void receiveErrordeleteKeyPair(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createDhcpOptions method
            * override this method for handling normal response from createDhcpOptions operation
            */
           public void receiveResultcreateDhcpOptions(
                    com.amazon.ec2.CreateDhcpOptionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createDhcpOptions operation
           */
            public void receiveErrorcreateDhcpOptions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSubnets method
            * override this method for handling normal response from describeSubnets operation
            */
           public void receiveResultdescribeSubnets(
                    com.amazon.ec2.DescribeSubnetsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSubnets operation
           */
            public void receiveErrordescribeSubnets(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createSecurityGroup method
            * override this method for handling normal response from createSecurityGroup operation
            */
           public void receiveResultcreateSecurityGroup(
                    com.amazon.ec2.CreateSecurityGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createSecurityGroup operation
           */
            public void receiveErrorcreateSecurityGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSpotDatafeedSubscription method
            * override this method for handling normal response from describeSpotDatafeedSubscription operation
            */
           public void receiveResultdescribeSpotDatafeedSubscription(
                    com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSpotDatafeedSubscription operation
           */
            public void receiveErrordescribeSpotDatafeedSubscription(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSnapshotAttribute method
            * override this method for handling normal response from describeSnapshotAttribute operation
            */
           public void receiveResultdescribeSnapshotAttribute(
                    com.amazon.ec2.DescribeSnapshotAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSnapshotAttribute operation
           */
            public void receiveErrordescribeSnapshotAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteVpnConnection method
            * override this method for handling normal response from deleteVpnConnection operation
            */
           public void receiveResultdeleteVpnConnection(
                    com.amazon.ec2.DeleteVpnConnectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteVpnConnection operation
           */
            public void receiveErrordeleteVpnConnection(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describePlacementGroups method
            * override this method for handling normal response from describePlacementGroups operation
            */
           public void receiveResultdescribePlacementGroups(
                    com.amazon.ec2.DescribePlacementGroupsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describePlacementGroups operation
           */
            public void receiveErrordescribePlacementGroups(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createSubnet method
            * override this method for handling normal response from createSubnet operation
            */
           public void receiveResultcreateSubnet(
                    com.amazon.ec2.CreateSubnetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createSubnet operation
           */
            public void receiveErrorcreateSubnet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSpotInstanceRequests method
            * override this method for handling normal response from describeSpotInstanceRequests operation
            */
           public void receiveResultdescribeSpotInstanceRequests(
                    com.amazon.ec2.DescribeSpotInstanceRequestsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSpotInstanceRequests operation
           */
            public void receiveErrordescribeSpotInstanceRequests(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancelBundleTask method
            * override this method for handling normal response from cancelBundleTask operation
            */
           public void receiveResultcancelBundleTask(
                    com.amazon.ec2.CancelBundleTaskResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancelBundleTask operation
           */
            public void receiveErrorcancelBundleTask(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeVolumes method
            * override this method for handling normal response from describeVolumes operation
            */
           public void receiveResultdescribeVolumes(
                    com.amazon.ec2.DescribeVolumesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeVolumes operation
           */
            public void receiveErrordescribeVolumes(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for requestSpotInstances method
            * override this method for handling normal response from requestSpotInstances operation
            */
           public void receiveResultrequestSpotInstances(
                    com.amazon.ec2.RequestSpotInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from requestSpotInstances operation
           */
            public void receiveErrorrequestSpotInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeImages method
            * override this method for handling normal response from describeImages operation
            */
           public void receiveResultdescribeImages(
                    com.amazon.ec2.DescribeImagesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeImages operation
           */
            public void receiveErrordescribeImages(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for associateAddress method
            * override this method for handling normal response from associateAddress operation
            */
           public void receiveResultassociateAddress(
                    com.amazon.ec2.AssociateAddressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from associateAddress operation
           */
            public void receiveErrorassociateAddress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for releaseAddress method
            * override this method for handling normal response from releaseAddress operation
            */
           public void receiveResultreleaseAddress(
                    com.amazon.ec2.ReleaseAddressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from releaseAddress operation
           */
            public void receiveErrorreleaseAddress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for revokeSecurityGroupIngress method
            * override this method for handling normal response from revokeSecurityGroupIngress operation
            */
           public void receiveResultrevokeSecurityGroupIngress(
                    com.amazon.ec2.RevokeSecurityGroupIngressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from revokeSecurityGroupIngress operation
           */
            public void receiveErrorrevokeSecurityGroupIngress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifySnapshotAttribute method
            * override this method for handling normal response from modifySnapshotAttribute operation
            */
           public void receiveResultmodifySnapshotAttribute(
                    com.amazon.ec2.ModifySnapshotAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifySnapshotAttribute operation
           */
            public void receiveErrormodifySnapshotAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createPlacementGroup method
            * override this method for handling normal response from createPlacementGroup operation
            */
           public void receiveResultcreatePlacementGroup(
                    com.amazon.ec2.CreatePlacementGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createPlacementGroup operation
           */
            public void receiveErrorcreatePlacementGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for detachVolume method
            * override this method for handling normal response from detachVolume operation
            */
           public void receiveResultdetachVolume(
                    com.amazon.ec2.DetachVolumeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from detachVolume operation
           */
            public void receiveErrordetachVolume(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeDhcpOptions method
            * override this method for handling normal response from describeDhcpOptions operation
            */
           public void receiveResultdescribeDhcpOptions(
                    com.amazon.ec2.DescribeDhcpOptionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeDhcpOptions operation
           */
            public void receiveErrordescribeDhcpOptions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createVpnConnection method
            * override this method for handling normal response from createVpnConnection operation
            */
           public void receiveResultcreateVpnConnection(
                    com.amazon.ec2.CreateVpnConnectionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createVpnConnection operation
           */
            public void receiveErrorcreateVpnConnection(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeImageAttribute method
            * override this method for handling normal response from describeImageAttribute operation
            */
           public void receiveResultdescribeImageAttribute(
                    com.amazon.ec2.DescribeImageAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeImageAttribute operation
           */
            public void receiveErrordescribeImageAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for registerImage method
            * override this method for handling normal response from registerImage operation
            */
           public void receiveResultregisterImage(
                    com.amazon.ec2.RegisterImageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from registerImage operation
           */
            public void receiveErrorregisterImage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeInstanceAttribute method
            * override this method for handling normal response from describeInstanceAttribute operation
            */
           public void receiveResultdescribeInstanceAttribute(
                    com.amazon.ec2.DescribeInstanceAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeInstanceAttribute operation
           */
            public void receiveErrordescribeInstanceAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteSecurityGroup method
            * override this method for handling normal response from deleteSecurityGroup operation
            */
           public void receiveResultdeleteSecurityGroup(
                    com.amazon.ec2.DeleteSecurityGroupResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteSecurityGroup operation
           */
            public void receiveErrordeleteSecurityGroup(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createVpnGateway method
            * override this method for handling normal response from createVpnGateway operation
            */
           public void receiveResultcreateVpnGateway(
                    com.amazon.ec2.CreateVpnGatewayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createVpnGateway operation
           */
            public void receiveErrorcreateVpnGateway(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for attachVpnGateway method
            * override this method for handling normal response from attachVpnGateway operation
            */
           public void receiveResultattachVpnGateway(
                    com.amazon.ec2.AttachVpnGatewayResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from attachVpnGateway operation
           */
            public void receiveErrorattachVpnGateway(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetInstanceAttribute method
            * override this method for handling normal response from resetInstanceAttribute operation
            */
           public void receiveResultresetInstanceAttribute(
                    com.amazon.ec2.ResetInstanceAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetInstanceAttribute operation
           */
            public void receiveErrorresetInstanceAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeVpnGateways method
            * override this method for handling normal response from describeVpnGateways operation
            */
           public void receiveResultdescribeVpnGateways(
                    com.amazon.ec2.DescribeVpnGatewaysResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeVpnGateways operation
           */
            public void receiveErrordescribeVpnGateways(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeReservedInstances method
            * override this method for handling normal response from describeReservedInstances operation
            */
           public void receiveResultdescribeReservedInstances(
                    com.amazon.ec2.DescribeReservedInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeReservedInstances operation
           */
            public void receiveErrordescribeReservedInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyImageAttribute method
            * override this method for handling normal response from modifyImageAttribute operation
            */
           public void receiveResultmodifyImageAttribute(
                    com.amazon.ec2.ModifyImageAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyImageAttribute operation
           */
            public void receiveErrormodifyImageAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modifyInstanceAttribute method
            * override this method for handling normal response from modifyInstanceAttribute operation
            */
           public void receiveResultmodifyInstanceAttribute(
                    com.amazon.ec2.ModifyInstanceAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modifyInstanceAttribute operation
           */
            public void receiveErrormodifyInstanceAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteVolume method
            * override this method for handling normal response from deleteVolume operation
            */
           public void receiveResultdeleteVolume(
                    com.amazon.ec2.DeleteVolumeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteVolume operation
           */
            public void receiveErrordeleteVolume(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for allocateAddress method
            * override this method for handling normal response from allocateAddress operation
            */
           public void receiveResultallocateAddress(
                    com.amazon.ec2.AllocateAddressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from allocateAddress operation
           */
            public void receiveErrorallocateAddress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createKeyPair method
            * override this method for handling normal response from createKeyPair operation
            */
           public void receiveResultcreateKeyPair(
                    com.amazon.ec2.CreateKeyPairResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createKeyPair operation
           */
            public void receiveErrorcreateKeyPair(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeVpnConnections method
            * override this method for handling normal response from describeVpnConnections operation
            */
           public void receiveResultdescribeVpnConnections(
                    com.amazon.ec2.DescribeVpnConnectionsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeVpnConnections operation
           */
            public void receiveErrordescribeVpnConnections(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSecurityGroups method
            * override this method for handling normal response from describeSecurityGroups operation
            */
           public void receiveResultdescribeSecurityGroups(
                    com.amazon.ec2.DescribeSecurityGroupsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSecurityGroups operation
           */
            public void receiveErrordescribeSecurityGroups(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeCustomerGateways method
            * override this method for handling normal response from describeCustomerGateways operation
            */
           public void receiveResultdescribeCustomerGateways(
                    com.amazon.ec2.DescribeCustomerGatewaysResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeCustomerGateways operation
           */
            public void receiveErrordescribeCustomerGateways(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeSnapshots method
            * override this method for handling normal response from describeSnapshots operation
            */
           public void receiveResultdescribeSnapshots(
                    com.amazon.ec2.DescribeSnapshotsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeSnapshots operation
           */
            public void receiveErrordescribeSnapshots(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deactivateLicense method
            * override this method for handling normal response from deactivateLicense operation
            */
           public void receiveResultdeactivateLicense(
                    com.amazon.ec2.DeactivateLicenseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deactivateLicense operation
           */
            public void receiveErrordeactivateLicense(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for attachVolume method
            * override this method for handling normal response from attachVolume operation
            */
           public void receiveResultattachVolume(
                    com.amazon.ec2.AttachVolumeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from attachVolume operation
           */
            public void receiveErrorattachVolume(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteVpc method
            * override this method for handling normal response from deleteVpc operation
            */
           public void receiveResultdeleteVpc(
                    com.amazon.ec2.DeleteVpcResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteVpc operation
           */
            public void receiveErrordeleteVpc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createSpotDatafeedSubscription method
            * override this method for handling normal response from createSpotDatafeedSubscription operation
            */
           public void receiveResultcreateSpotDatafeedSubscription(
                    com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createSpotDatafeedSubscription operation
           */
            public void receiveErrorcreateSpotDatafeedSubscription(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for cancelSpotInstanceRequests method
            * override this method for handling normal response from cancelSpotInstanceRequests operation
            */
           public void receiveResultcancelSpotInstanceRequests(
                    com.amazon.ec2.CancelSpotInstanceRequestsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from cancelSpotInstanceRequests operation
           */
            public void receiveErrorcancelSpotInstanceRequests(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for startInstances method
            * override this method for handling normal response from startInstances operation
            */
           public void receiveResultstartInstances(
                    com.amazon.ec2.StartInstancesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from startInstances operation
           */
            public void receiveErrorstartInstances(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deregisterImage method
            * override this method for handling normal response from deregisterImage operation
            */
           public void receiveResultderegisterImage(
                    com.amazon.ec2.DeregisterImageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deregisterImage operation
           */
            public void receiveErrorderegisterImage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for disassociateAddress method
            * override this method for handling normal response from disassociateAddress operation
            */
           public void receiveResultdisassociateAddress(
                    com.amazon.ec2.DisassociateAddressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from disassociateAddress operation
           */
            public void receiveErrordisassociateAddress(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for activateLicense method
            * override this method for handling normal response from activateLicense operation
            */
           public void receiveResultactivateLicense(
                    com.amazon.ec2.ActivateLicenseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from activateLicense operation
           */
            public void receiveErroractivateLicense(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for resetSnapshotAttribute method
            * override this method for handling normal response from resetSnapshotAttribute operation
            */
           public void receiveResultresetSnapshotAttribute(
                    com.amazon.ec2.ResetSnapshotAttributeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from resetSnapshotAttribute operation
           */
            public void receiveErrorresetSnapshotAttribute(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createVpc method
            * override this method for handling normal response from createVpc operation
            */
           public void receiveResultcreateVpc(
                    com.amazon.ec2.CreateVpcResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createVpc operation
           */
            public void receiveErrorcreateVpc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for confirmProductInstance method
            * override this method for handling normal response from confirmProductInstance operation
            */
           public void receiveResultconfirmProductInstance(
                    com.amazon.ec2.ConfirmProductInstanceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from confirmProductInstance operation
           */
            public void receiveErrorconfirmProductInstance(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeAvailabilityZones method
            * override this method for handling normal response from describeAvailabilityZones operation
            */
           public void receiveResultdescribeAvailabilityZones(
                    com.amazon.ec2.DescribeAvailabilityZonesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeAvailabilityZones operation
           */
            public void receiveErrordescribeAvailabilityZones(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for describeReservedInstancesOfferings method
            * override this method for handling normal response from describeReservedInstancesOfferings operation
            */
           public void receiveResultdescribeReservedInstancesOfferings(
                    com.amazon.ec2.DescribeReservedInstancesOfferingsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from describeReservedInstancesOfferings operation
           */
            public void receiveErrordescribeReservedInstancesOfferings(java.lang.Exception e) {
            }
                


    }
    