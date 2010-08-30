
/**
 * AmazonEC2MessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */
        package com.amazon.ec2;

        /**
        *  AmazonEC2MessageReceiverInOut message receiver
        */

        public class AmazonEC2MessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        AmazonEC2SkeletonInterface skel = (AmazonEC2SkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(op.getName().getLocalPart())) != null)){

        

            if("describeInstances".equals(methodName)){
                
                com.amazon.ec2.DescribeInstancesResponse describeInstancesResponse175 = null;
	                        com.amazon.ec2.DescribeInstances wrappedParam =
                                                             (com.amazon.ec2.DescribeInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeInstancesResponse175 =
                                                   
                                                   
                                                         skel.describeInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeInstancesResponse175, false);
                                    } else 

            if("describeRegions".equals(methodName)){
                
                com.amazon.ec2.DescribeRegionsResponse describeRegionsResponse177 = null;
	                        com.amazon.ec2.DescribeRegions wrappedParam =
                                                             (com.amazon.ec2.DescribeRegions)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeRegions.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeRegionsResponse177 =
                                                   
                                                   
                                                         skel.describeRegions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeRegionsResponse177, false);
                                    } else 

            if("stopInstances".equals(methodName)){
                
                com.amazon.ec2.StopInstancesResponse stopInstancesResponse179 = null;
	                        com.amazon.ec2.StopInstances wrappedParam =
                                                             (com.amazon.ec2.StopInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.StopInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               stopInstancesResponse179 =
                                                   
                                                   
                                                         skel.stopInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), stopInstancesResponse179, false);
                                    } else 

            if("rebootInstances".equals(methodName)){
                
                com.amazon.ec2.RebootInstancesResponse rebootInstancesResponse181 = null;
	                        com.amazon.ec2.RebootInstances wrappedParam =
                                                             (com.amazon.ec2.RebootInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.RebootInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               rebootInstancesResponse181 =
                                                   
                                                   
                                                         skel.rebootInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), rebootInstancesResponse181, false);
                                    } else 

            if("describeVpcs".equals(methodName)){
                
                com.amazon.ec2.DescribeVpcsResponse describeVpcsResponse183 = null;
	                        com.amazon.ec2.DescribeVpcs wrappedParam =
                                                             (com.amazon.ec2.DescribeVpcs)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeVpcs.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeVpcsResponse183 =
                                                   
                                                   
                                                         skel.describeVpcs(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeVpcsResponse183, false);
                                    } else 

            if("bundleInstance".equals(methodName)){
                
                com.amazon.ec2.BundleInstanceResponse bundleInstanceResponse185 = null;
	                        com.amazon.ec2.BundleInstance wrappedParam =
                                                             (com.amazon.ec2.BundleInstance)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.BundleInstance.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               bundleInstanceResponse185 =
                                                   
                                                   
                                                         skel.bundleInstance(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), bundleInstanceResponse185, false);
                                    } else 

            if("runInstances".equals(methodName)){
                
                com.amazon.ec2.RunInstancesResponse runInstancesResponse187 = null;
	                        com.amazon.ec2.RunInstances wrappedParam =
                                                             (com.amazon.ec2.RunInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.RunInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               runInstancesResponse187 =
                                                   
                                                   
                                                         skel.runInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), runInstancesResponse187, false);
                                    } else 

            if("deleteSubnet".equals(methodName)){
                
                com.amazon.ec2.DeleteSubnetResponse deleteSubnetResponse189 = null;
	                        com.amazon.ec2.DeleteSubnet wrappedParam =
                                                             (com.amazon.ec2.DeleteSubnet)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteSubnet.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteSubnetResponse189 =
                                                   
                                                   
                                                         skel.deleteSubnet(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteSubnetResponse189, false);
                                    } else 

            if("terminateInstances".equals(methodName)){
                
                com.amazon.ec2.TerminateInstancesResponse terminateInstancesResponse191 = null;
	                        com.amazon.ec2.TerminateInstances wrappedParam =
                                                             (com.amazon.ec2.TerminateInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.TerminateInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               terminateInstancesResponse191 =
                                                   
                                                   
                                                         skel.terminateInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), terminateInstancesResponse191, false);
                                    } else 

            if("describeKeyPairs".equals(methodName)){
                
                com.amazon.ec2.DescribeKeyPairsResponse describeKeyPairsResponse193 = null;
	                        com.amazon.ec2.DescribeKeyPairs wrappedParam =
                                                             (com.amazon.ec2.DescribeKeyPairs)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeKeyPairs.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeKeyPairsResponse193 =
                                                   
                                                   
                                                         skel.describeKeyPairs(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeKeyPairsResponse193, false);
                                    } else 

            if("getPasswordData".equals(methodName)){
                
                com.amazon.ec2.GetPasswordDataResponse getPasswordDataResponse195 = null;
	                        com.amazon.ec2.GetPasswordData wrappedParam =
                                                             (com.amazon.ec2.GetPasswordData)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.GetPasswordData.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getPasswordDataResponse195 =
                                                   
                                                   
                                                         skel.getPasswordData(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getPasswordDataResponse195, false);
                                    } else 

            if("resetImageAttribute".equals(methodName)){
                
                com.amazon.ec2.ResetImageAttributeResponse resetImageAttributeResponse197 = null;
	                        com.amazon.ec2.ResetImageAttribute wrappedParam =
                                                             (com.amazon.ec2.ResetImageAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ResetImageAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resetImageAttributeResponse197 =
                                                   
                                                   
                                                         skel.resetImageAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resetImageAttributeResponse197, false);
                                    } else 

            if("createVolume".equals(methodName)){
                
                com.amazon.ec2.CreateVolumeResponse createVolumeResponse199 = null;
	                        com.amazon.ec2.CreateVolume wrappedParam =
                                                             (com.amazon.ec2.CreateVolume)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateVolume.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createVolumeResponse199 =
                                                   
                                                   
                                                         skel.createVolume(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createVolumeResponse199, false);
                                    } else 

            if("monitorInstances".equals(methodName)){
                
                com.amazon.ec2.MonitorInstancesResponse monitorInstancesResponse201 = null;
	                        com.amazon.ec2.MonitorInstances wrappedParam =
                                                             (com.amazon.ec2.MonitorInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.MonitorInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               monitorInstancesResponse201 =
                                                   
                                                   
                                                         skel.monitorInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), monitorInstancesResponse201, false);
                                    } else 

            if("deleteSpotDatafeedSubscription".equals(methodName)){
                
                com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse deleteSpotDatafeedSubscriptionResponse203 = null;
	                        com.amazon.ec2.DeleteSpotDatafeedSubscription wrappedParam =
                                                             (com.amazon.ec2.DeleteSpotDatafeedSubscription)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteSpotDatafeedSubscription.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteSpotDatafeedSubscriptionResponse203 =
                                                   
                                                   
                                                         skel.deleteSpotDatafeedSubscription(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteSpotDatafeedSubscriptionResponse203, false);
                                    } else 

            if("unmonitorInstances".equals(methodName)){
                
                com.amazon.ec2.UnmonitorInstancesResponse unmonitorInstancesResponse205 = null;
	                        com.amazon.ec2.UnmonitorInstances wrappedParam =
                                                             (com.amazon.ec2.UnmonitorInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.UnmonitorInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               unmonitorInstancesResponse205 =
                                                   
                                                   
                                                         skel.unmonitorInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), unmonitorInstancesResponse205, false);
                                    } else 

            if("associateDhcpOptions".equals(methodName)){
                
                com.amazon.ec2.AssociateDhcpOptionsResponse associateDhcpOptionsResponse207 = null;
	                        com.amazon.ec2.AssociateDhcpOptions wrappedParam =
                                                             (com.amazon.ec2.AssociateDhcpOptions)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.AssociateDhcpOptions.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               associateDhcpOptionsResponse207 =
                                                   
                                                   
                                                         skel.associateDhcpOptions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), associateDhcpOptionsResponse207, false);
                                    } else 

            if("deleteCustomerGateway".equals(methodName)){
                
                com.amazon.ec2.DeleteCustomerGatewayResponse deleteCustomerGatewayResponse209 = null;
	                        com.amazon.ec2.DeleteCustomerGateway wrappedParam =
                                                             (com.amazon.ec2.DeleteCustomerGateway)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteCustomerGateway.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteCustomerGatewayResponse209 =
                                                   
                                                   
                                                         skel.deleteCustomerGateway(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteCustomerGatewayResponse209, false);
                                    } else 

            if("authorizeSecurityGroupIngress".equals(methodName)){
                
                com.amazon.ec2.AuthorizeSecurityGroupIngressResponse authorizeSecurityGroupIngressResponse211 = null;
	                        com.amazon.ec2.AuthorizeSecurityGroupIngress wrappedParam =
                                                             (com.amazon.ec2.AuthorizeSecurityGroupIngress)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.AuthorizeSecurityGroupIngress.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               authorizeSecurityGroupIngressResponse211 =
                                                   
                                                   
                                                         skel.authorizeSecurityGroupIngress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), authorizeSecurityGroupIngressResponse211, false);
                                    } else 

            if("createSnapshot".equals(methodName)){
                
                com.amazon.ec2.CreateSnapshotResponse createSnapshotResponse213 = null;
	                        com.amazon.ec2.CreateSnapshot wrappedParam =
                                                             (com.amazon.ec2.CreateSnapshot)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateSnapshot.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createSnapshotResponse213 =
                                                   
                                                   
                                                         skel.createSnapshot(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createSnapshotResponse213, false);
                                    } else 

            if("getConsoleOutput".equals(methodName)){
                
                com.amazon.ec2.GetConsoleOutputResponse getConsoleOutputResponse215 = null;
	                        com.amazon.ec2.GetConsoleOutput wrappedParam =
                                                             (com.amazon.ec2.GetConsoleOutput)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.GetConsoleOutput.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getConsoleOutputResponse215 =
                                                   
                                                   
                                                         skel.getConsoleOutput(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getConsoleOutputResponse215, false);
                                    } else 

            if("createImage".equals(methodName)){
                
                com.amazon.ec2.CreateImageResponse createImageResponse217 = null;
	                        com.amazon.ec2.CreateImage wrappedParam =
                                                             (com.amazon.ec2.CreateImage)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateImage.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createImageResponse217 =
                                                   
                                                   
                                                         skel.createImage(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createImageResponse217, false);
                                    } else 

            if("describeSpotPriceHistory".equals(methodName)){
                
                com.amazon.ec2.DescribeSpotPriceHistoryResponse describeSpotPriceHistoryResponse219 = null;
	                        com.amazon.ec2.DescribeSpotPriceHistory wrappedParam =
                                                             (com.amazon.ec2.DescribeSpotPriceHistory)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSpotPriceHistory.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSpotPriceHistoryResponse219 =
                                                   
                                                   
                                                         skel.describeSpotPriceHistory(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSpotPriceHistoryResponse219, false);
                                    } else 

            if("describeAddresses".equals(methodName)){
                
                com.amazon.ec2.DescribeAddressesResponse describeAddressesResponse221 = null;
	                        com.amazon.ec2.DescribeAddresses wrappedParam =
                                                             (com.amazon.ec2.DescribeAddresses)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeAddresses.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeAddressesResponse221 =
                                                   
                                                   
                                                         skel.describeAddresses(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeAddressesResponse221, false);
                                    } else 

            if("deleteSnapshot".equals(methodName)){
                
                com.amazon.ec2.DeleteSnapshotResponse deleteSnapshotResponse223 = null;
	                        com.amazon.ec2.DeleteSnapshot wrappedParam =
                                                             (com.amazon.ec2.DeleteSnapshot)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteSnapshot.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteSnapshotResponse223 =
                                                   
                                                   
                                                         skel.deleteSnapshot(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteSnapshotResponse223, false);
                                    } else 

            if("deletePlacementGroup".equals(methodName)){
                
                com.amazon.ec2.DeletePlacementGroupResponse deletePlacementGroupResponse225 = null;
	                        com.amazon.ec2.DeletePlacementGroup wrappedParam =
                                                             (com.amazon.ec2.DeletePlacementGroup)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeletePlacementGroup.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deletePlacementGroupResponse225 =
                                                   
                                                   
                                                         skel.deletePlacementGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deletePlacementGroupResponse225, false);
                                    } else 

            if("detachVpnGateway".equals(methodName)){
                
                com.amazon.ec2.DetachVpnGatewayResponse detachVpnGatewayResponse227 = null;
	                        com.amazon.ec2.DetachVpnGateway wrappedParam =
                                                             (com.amazon.ec2.DetachVpnGateway)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DetachVpnGateway.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               detachVpnGatewayResponse227 =
                                                   
                                                   
                                                         skel.detachVpnGateway(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), detachVpnGatewayResponse227, false);
                                    } else 

            if("describeBundleTasks".equals(methodName)){
                
                com.amazon.ec2.DescribeBundleTasksResponse describeBundleTasksResponse229 = null;
	                        com.amazon.ec2.DescribeBundleTasks wrappedParam =
                                                             (com.amazon.ec2.DescribeBundleTasks)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeBundleTasks.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeBundleTasksResponse229 =
                                                   
                                                   
                                                         skel.describeBundleTasks(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeBundleTasksResponse229, false);
                                    } else 

            if("describeLicenses".equals(methodName)){
                
                com.amazon.ec2.DescribeLicensesResponse describeLicensesResponse231 = null;
	                        com.amazon.ec2.DescribeLicenses wrappedParam =
                                                             (com.amazon.ec2.DescribeLicenses)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeLicenses.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeLicensesResponse231 =
                                                   
                                                   
                                                         skel.describeLicenses(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeLicensesResponse231, false);
                                    } else 

            if("deleteVpnGateway".equals(methodName)){
                
                com.amazon.ec2.DeleteVpnGatewayResponse deleteVpnGatewayResponse233 = null;
	                        com.amazon.ec2.DeleteVpnGateway wrappedParam =
                                                             (com.amazon.ec2.DeleteVpnGateway)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteVpnGateway.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteVpnGatewayResponse233 =
                                                   
                                                   
                                                         skel.deleteVpnGateway(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteVpnGatewayResponse233, false);
                                    } else 

            if("purchaseReservedInstancesOffering".equals(methodName)){
                
                com.amazon.ec2.PurchaseReservedInstancesOfferingResponse purchaseReservedInstancesOfferingResponse235 = null;
	                        com.amazon.ec2.PurchaseReservedInstancesOffering wrappedParam =
                                                             (com.amazon.ec2.PurchaseReservedInstancesOffering)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.PurchaseReservedInstancesOffering.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               purchaseReservedInstancesOfferingResponse235 =
                                                   
                                                   
                                                         skel.purchaseReservedInstancesOffering(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), purchaseReservedInstancesOfferingResponse235, false);
                                    } else 

            if("deleteDhcpOptions".equals(methodName)){
                
                com.amazon.ec2.DeleteDhcpOptionsResponse deleteDhcpOptionsResponse237 = null;
	                        com.amazon.ec2.DeleteDhcpOptions wrappedParam =
                                                             (com.amazon.ec2.DeleteDhcpOptions)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteDhcpOptions.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteDhcpOptionsResponse237 =
                                                   
                                                   
                                                         skel.deleteDhcpOptions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteDhcpOptionsResponse237, false);
                                    } else 

            if("createCustomerGateway".equals(methodName)){
                
                com.amazon.ec2.CreateCustomerGatewayResponse createCustomerGatewayResponse239 = null;
	                        com.amazon.ec2.CreateCustomerGateway wrappedParam =
                                                             (com.amazon.ec2.CreateCustomerGateway)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateCustomerGateway.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createCustomerGatewayResponse239 =
                                                   
                                                   
                                                         skel.createCustomerGateway(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createCustomerGatewayResponse239, false);
                                    } else 

            if("deleteKeyPair".equals(methodName)){
                
                com.amazon.ec2.DeleteKeyPairResponse deleteKeyPairResponse241 = null;
	                        com.amazon.ec2.DeleteKeyPair wrappedParam =
                                                             (com.amazon.ec2.DeleteKeyPair)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteKeyPair.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteKeyPairResponse241 =
                                                   
                                                   
                                                         skel.deleteKeyPair(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteKeyPairResponse241, false);
                                    } else 

            if("createDhcpOptions".equals(methodName)){
                
                com.amazon.ec2.CreateDhcpOptionsResponse createDhcpOptionsResponse243 = null;
	                        com.amazon.ec2.CreateDhcpOptions wrappedParam =
                                                             (com.amazon.ec2.CreateDhcpOptions)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateDhcpOptions.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createDhcpOptionsResponse243 =
                                                   
                                                   
                                                         skel.createDhcpOptions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createDhcpOptionsResponse243, false);
                                    } else 

            if("describeSubnets".equals(methodName)){
                
                com.amazon.ec2.DescribeSubnetsResponse describeSubnetsResponse245 = null;
	                        com.amazon.ec2.DescribeSubnets wrappedParam =
                                                             (com.amazon.ec2.DescribeSubnets)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSubnets.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSubnetsResponse245 =
                                                   
                                                   
                                                         skel.describeSubnets(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSubnetsResponse245, false);
                                    } else 

            if("createSecurityGroup".equals(methodName)){
                
                com.amazon.ec2.CreateSecurityGroupResponse createSecurityGroupResponse247 = null;
	                        com.amazon.ec2.CreateSecurityGroup wrappedParam =
                                                             (com.amazon.ec2.CreateSecurityGroup)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateSecurityGroup.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createSecurityGroupResponse247 =
                                                   
                                                   
                                                         skel.createSecurityGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createSecurityGroupResponse247, false);
                                    } else 

            if("describeSpotDatafeedSubscription".equals(methodName)){
                
                com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse describeSpotDatafeedSubscriptionResponse249 = null;
	                        com.amazon.ec2.DescribeSpotDatafeedSubscription wrappedParam =
                                                             (com.amazon.ec2.DescribeSpotDatafeedSubscription)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSpotDatafeedSubscription.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSpotDatafeedSubscriptionResponse249 =
                                                   
                                                   
                                                         skel.describeSpotDatafeedSubscription(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSpotDatafeedSubscriptionResponse249, false);
                                    } else 

            if("describeSnapshotAttribute".equals(methodName)){
                
                com.amazon.ec2.DescribeSnapshotAttributeResponse describeSnapshotAttributeResponse251 = null;
	                        com.amazon.ec2.DescribeSnapshotAttribute wrappedParam =
                                                             (com.amazon.ec2.DescribeSnapshotAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSnapshotAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSnapshotAttributeResponse251 =
                                                   
                                                   
                                                         skel.describeSnapshotAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSnapshotAttributeResponse251, false);
                                    } else 

            if("deleteVpnConnection".equals(methodName)){
                
                com.amazon.ec2.DeleteVpnConnectionResponse deleteVpnConnectionResponse253 = null;
	                        com.amazon.ec2.DeleteVpnConnection wrappedParam =
                                                             (com.amazon.ec2.DeleteVpnConnection)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteVpnConnection.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteVpnConnectionResponse253 =
                                                   
                                                   
                                                         skel.deleteVpnConnection(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteVpnConnectionResponse253, false);
                                    } else 

            if("describePlacementGroups".equals(methodName)){
                
                com.amazon.ec2.DescribePlacementGroupsResponse describePlacementGroupsResponse255 = null;
	                        com.amazon.ec2.DescribePlacementGroups wrappedParam =
                                                             (com.amazon.ec2.DescribePlacementGroups)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribePlacementGroups.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describePlacementGroupsResponse255 =
                                                   
                                                   
                                                         skel.describePlacementGroups(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describePlacementGroupsResponse255, false);
                                    } else 

            if("createSubnet".equals(methodName)){
                
                com.amazon.ec2.CreateSubnetResponse createSubnetResponse257 = null;
	                        com.amazon.ec2.CreateSubnet wrappedParam =
                                                             (com.amazon.ec2.CreateSubnet)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateSubnet.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createSubnetResponse257 =
                                                   
                                                   
                                                         skel.createSubnet(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createSubnetResponse257, false);
                                    } else 

            if("describeSpotInstanceRequests".equals(methodName)){
                
                com.amazon.ec2.DescribeSpotInstanceRequestsResponse describeSpotInstanceRequestsResponse259 = null;
	                        com.amazon.ec2.DescribeSpotInstanceRequests wrappedParam =
                                                             (com.amazon.ec2.DescribeSpotInstanceRequests)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSpotInstanceRequests.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSpotInstanceRequestsResponse259 =
                                                   
                                                   
                                                         skel.describeSpotInstanceRequests(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSpotInstanceRequestsResponse259, false);
                                    } else 

            if("cancelBundleTask".equals(methodName)){
                
                com.amazon.ec2.CancelBundleTaskResponse cancelBundleTaskResponse261 = null;
	                        com.amazon.ec2.CancelBundleTask wrappedParam =
                                                             (com.amazon.ec2.CancelBundleTask)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CancelBundleTask.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               cancelBundleTaskResponse261 =
                                                   
                                                   
                                                         skel.cancelBundleTask(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), cancelBundleTaskResponse261, false);
                                    } else 

            if("describeVolumes".equals(methodName)){
                
                com.amazon.ec2.DescribeVolumesResponse describeVolumesResponse263 = null;
	                        com.amazon.ec2.DescribeVolumes wrappedParam =
                                                             (com.amazon.ec2.DescribeVolumes)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeVolumes.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeVolumesResponse263 =
                                                   
                                                   
                                                         skel.describeVolumes(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeVolumesResponse263, false);
                                    } else 

            if("requestSpotInstances".equals(methodName)){
                
                com.amazon.ec2.RequestSpotInstancesResponse requestSpotInstancesResponse265 = null;
	                        com.amazon.ec2.RequestSpotInstances wrappedParam =
                                                             (com.amazon.ec2.RequestSpotInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.RequestSpotInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               requestSpotInstancesResponse265 =
                                                   
                                                   
                                                         skel.requestSpotInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), requestSpotInstancesResponse265, false);
                                    } else 

            if("describeImages".equals(methodName)){
                
                com.amazon.ec2.DescribeImagesResponse describeImagesResponse267 = null;
	                        com.amazon.ec2.DescribeImages wrappedParam =
                                                             (com.amazon.ec2.DescribeImages)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeImages.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeImagesResponse267 =
                                                   
                                                   
                                                         skel.describeImages(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeImagesResponse267, false);
                                    } else 

            if("associateAddress".equals(methodName)){
                
                com.amazon.ec2.AssociateAddressResponse associateAddressResponse269 = null;
	                        com.amazon.ec2.AssociateAddress wrappedParam =
                                                             (com.amazon.ec2.AssociateAddress)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.AssociateAddress.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               associateAddressResponse269 =
                                                   
                                                   
                                                         skel.associateAddress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), associateAddressResponse269, false);
                                    } else 

            if("releaseAddress".equals(methodName)){
                
                com.amazon.ec2.ReleaseAddressResponse releaseAddressResponse271 = null;
	                        com.amazon.ec2.ReleaseAddress wrappedParam =
                                                             (com.amazon.ec2.ReleaseAddress)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ReleaseAddress.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               releaseAddressResponse271 =
                                                   
                                                   
                                                         skel.releaseAddress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), releaseAddressResponse271, false);
                                    } else 

            if("revokeSecurityGroupIngress".equals(methodName)){
                
                com.amazon.ec2.RevokeSecurityGroupIngressResponse revokeSecurityGroupIngressResponse273 = null;
	                        com.amazon.ec2.RevokeSecurityGroupIngress wrappedParam =
                                                             (com.amazon.ec2.RevokeSecurityGroupIngress)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.RevokeSecurityGroupIngress.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               revokeSecurityGroupIngressResponse273 =
                                                   
                                                   
                                                         skel.revokeSecurityGroupIngress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), revokeSecurityGroupIngressResponse273, false);
                                    } else 

            if("modifySnapshotAttribute".equals(methodName)){
                
                com.amazon.ec2.ModifySnapshotAttributeResponse modifySnapshotAttributeResponse275 = null;
	                        com.amazon.ec2.ModifySnapshotAttribute wrappedParam =
                                                             (com.amazon.ec2.ModifySnapshotAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ModifySnapshotAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               modifySnapshotAttributeResponse275 =
                                                   
                                                   
                                                         skel.modifySnapshotAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), modifySnapshotAttributeResponse275, false);
                                    } else 

            if("createPlacementGroup".equals(methodName)){
                
                com.amazon.ec2.CreatePlacementGroupResponse createPlacementGroupResponse277 = null;
	                        com.amazon.ec2.CreatePlacementGroup wrappedParam =
                                                             (com.amazon.ec2.CreatePlacementGroup)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreatePlacementGroup.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createPlacementGroupResponse277 =
                                                   
                                                   
                                                         skel.createPlacementGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createPlacementGroupResponse277, false);
                                    } else 

            if("detachVolume".equals(methodName)){
                
                com.amazon.ec2.DetachVolumeResponse detachVolumeResponse279 = null;
	                        com.amazon.ec2.DetachVolume wrappedParam =
                                                             (com.amazon.ec2.DetachVolume)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DetachVolume.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               detachVolumeResponse279 =
                                                   
                                                   
                                                         skel.detachVolume(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), detachVolumeResponse279, false);
                                    } else 

            if("describeDhcpOptions".equals(methodName)){
                
                com.amazon.ec2.DescribeDhcpOptionsResponse describeDhcpOptionsResponse281 = null;
	                        com.amazon.ec2.DescribeDhcpOptions wrappedParam =
                                                             (com.amazon.ec2.DescribeDhcpOptions)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeDhcpOptions.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeDhcpOptionsResponse281 =
                                                   
                                                   
                                                         skel.describeDhcpOptions(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeDhcpOptionsResponse281, false);
                                    } else 

            if("createVpnConnection".equals(methodName)){
                
                com.amazon.ec2.CreateVpnConnectionResponse createVpnConnectionResponse283 = null;
	                        com.amazon.ec2.CreateVpnConnection wrappedParam =
                                                             (com.amazon.ec2.CreateVpnConnection)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateVpnConnection.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createVpnConnectionResponse283 =
                                                   
                                                   
                                                         skel.createVpnConnection(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createVpnConnectionResponse283, false);
                                    } else 

            if("describeImageAttribute".equals(methodName)){
                
                com.amazon.ec2.DescribeImageAttributeResponse describeImageAttributeResponse285 = null;
	                        com.amazon.ec2.DescribeImageAttribute wrappedParam =
                                                             (com.amazon.ec2.DescribeImageAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeImageAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeImageAttributeResponse285 =
                                                   
                                                   
                                                         skel.describeImageAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeImageAttributeResponse285, false);
                                    } else 

            if("registerImage".equals(methodName)){
                
                com.amazon.ec2.RegisterImageResponse registerImageResponse287 = null;
	                        com.amazon.ec2.RegisterImage wrappedParam =
                                                             (com.amazon.ec2.RegisterImage)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.RegisterImage.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               registerImageResponse287 =
                                                   
                                                   
                                                         skel.registerImage(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), registerImageResponse287, false);
                                    } else 

            if("describeInstanceAttribute".equals(methodName)){
                
                com.amazon.ec2.DescribeInstanceAttributeResponse describeInstanceAttributeResponse289 = null;
	                        com.amazon.ec2.DescribeInstanceAttribute wrappedParam =
                                                             (com.amazon.ec2.DescribeInstanceAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeInstanceAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeInstanceAttributeResponse289 =
                                                   
                                                   
                                                         skel.describeInstanceAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeInstanceAttributeResponse289, false);
                                    } else 

            if("deleteSecurityGroup".equals(methodName)){
                
                com.amazon.ec2.DeleteSecurityGroupResponse deleteSecurityGroupResponse291 = null;
	                        com.amazon.ec2.DeleteSecurityGroup wrappedParam =
                                                             (com.amazon.ec2.DeleteSecurityGroup)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteSecurityGroup.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteSecurityGroupResponse291 =
                                                   
                                                   
                                                         skel.deleteSecurityGroup(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteSecurityGroupResponse291, false);
                                    } else 

            if("createVpnGateway".equals(methodName)){
                
                com.amazon.ec2.CreateVpnGatewayResponse createVpnGatewayResponse293 = null;
	                        com.amazon.ec2.CreateVpnGateway wrappedParam =
                                                             (com.amazon.ec2.CreateVpnGateway)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateVpnGateway.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createVpnGatewayResponse293 =
                                                   
                                                   
                                                         skel.createVpnGateway(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createVpnGatewayResponse293, false);
                                    } else 

            if("attachVpnGateway".equals(methodName)){
                
                com.amazon.ec2.AttachVpnGatewayResponse attachVpnGatewayResponse295 = null;
	                        com.amazon.ec2.AttachVpnGateway wrappedParam =
                                                             (com.amazon.ec2.AttachVpnGateway)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.AttachVpnGateway.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               attachVpnGatewayResponse295 =
                                                   
                                                   
                                                         skel.attachVpnGateway(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), attachVpnGatewayResponse295, false);
                                    } else 

            if("resetInstanceAttribute".equals(methodName)){
                
                com.amazon.ec2.ResetInstanceAttributeResponse resetInstanceAttributeResponse297 = null;
	                        com.amazon.ec2.ResetInstanceAttribute wrappedParam =
                                                             (com.amazon.ec2.ResetInstanceAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ResetInstanceAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resetInstanceAttributeResponse297 =
                                                   
                                                   
                                                         skel.resetInstanceAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resetInstanceAttributeResponse297, false);
                                    } else 

            if("describeVpnGateways".equals(methodName)){
                
                com.amazon.ec2.DescribeVpnGatewaysResponse describeVpnGatewaysResponse299 = null;
	                        com.amazon.ec2.DescribeVpnGateways wrappedParam =
                                                             (com.amazon.ec2.DescribeVpnGateways)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeVpnGateways.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeVpnGatewaysResponse299 =
                                                   
                                                   
                                                         skel.describeVpnGateways(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeVpnGatewaysResponse299, false);
                                    } else 

            if("describeReservedInstances".equals(methodName)){
                
                com.amazon.ec2.DescribeReservedInstancesResponse describeReservedInstancesResponse301 = null;
	                        com.amazon.ec2.DescribeReservedInstances wrappedParam =
                                                             (com.amazon.ec2.DescribeReservedInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeReservedInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeReservedInstancesResponse301 =
                                                   
                                                   
                                                         skel.describeReservedInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeReservedInstancesResponse301, false);
                                    } else 

            if("modifyImageAttribute".equals(methodName)){
                
                com.amazon.ec2.ModifyImageAttributeResponse modifyImageAttributeResponse303 = null;
	                        com.amazon.ec2.ModifyImageAttribute wrappedParam =
                                                             (com.amazon.ec2.ModifyImageAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ModifyImageAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               modifyImageAttributeResponse303 =
                                                   
                                                   
                                                         skel.modifyImageAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), modifyImageAttributeResponse303, false);
                                    } else 

            if("modifyInstanceAttribute".equals(methodName)){
                
                com.amazon.ec2.ModifyInstanceAttributeResponse modifyInstanceAttributeResponse305 = null;
	                        com.amazon.ec2.ModifyInstanceAttribute wrappedParam =
                                                             (com.amazon.ec2.ModifyInstanceAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ModifyInstanceAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               modifyInstanceAttributeResponse305 =
                                                   
                                                   
                                                         skel.modifyInstanceAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), modifyInstanceAttributeResponse305, false);
                                    } else 

            if("deleteVolume".equals(methodName)){
                
                com.amazon.ec2.DeleteVolumeResponse deleteVolumeResponse307 = null;
	                        com.amazon.ec2.DeleteVolume wrappedParam =
                                                             (com.amazon.ec2.DeleteVolume)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteVolume.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteVolumeResponse307 =
                                                   
                                                   
                                                         skel.deleteVolume(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteVolumeResponse307, false);
                                    } else 

            if("allocateAddress".equals(methodName)){
                
                com.amazon.ec2.AllocateAddressResponse allocateAddressResponse309 = null;
	                        com.amazon.ec2.AllocateAddress wrappedParam =
                                                             (com.amazon.ec2.AllocateAddress)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.AllocateAddress.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               allocateAddressResponse309 =
                                                   
                                                   
                                                         skel.allocateAddress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), allocateAddressResponse309, false);
                                    } else 

            if("createKeyPair".equals(methodName)){
                
                com.amazon.ec2.CreateKeyPairResponse createKeyPairResponse311 = null;
	                        com.amazon.ec2.CreateKeyPair wrappedParam =
                                                             (com.amazon.ec2.CreateKeyPair)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateKeyPair.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createKeyPairResponse311 =
                                                   
                                                   
                                                         skel.createKeyPair(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createKeyPairResponse311, false);
                                    } else 

            if("describeVpnConnections".equals(methodName)){
                
                com.amazon.ec2.DescribeVpnConnectionsResponse describeVpnConnectionsResponse313 = null;
	                        com.amazon.ec2.DescribeVpnConnections wrappedParam =
                                                             (com.amazon.ec2.DescribeVpnConnections)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeVpnConnections.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeVpnConnectionsResponse313 =
                                                   
                                                   
                                                         skel.describeVpnConnections(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeVpnConnectionsResponse313, false);
                                    } else 

            if("describeSecurityGroups".equals(methodName)){
                
                com.amazon.ec2.DescribeSecurityGroupsResponse describeSecurityGroupsResponse315 = null;
	                        com.amazon.ec2.DescribeSecurityGroups wrappedParam =
                                                             (com.amazon.ec2.DescribeSecurityGroups)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSecurityGroups.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSecurityGroupsResponse315 =
                                                   
                                                   
                                                         skel.describeSecurityGroups(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSecurityGroupsResponse315, false);
                                    } else 

            if("describeCustomerGateways".equals(methodName)){
                
                com.amazon.ec2.DescribeCustomerGatewaysResponse describeCustomerGatewaysResponse317 = null;
	                        com.amazon.ec2.DescribeCustomerGateways wrappedParam =
                                                             (com.amazon.ec2.DescribeCustomerGateways)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeCustomerGateways.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeCustomerGatewaysResponse317 =
                                                   
                                                   
                                                         skel.describeCustomerGateways(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeCustomerGatewaysResponse317, false);
                                    } else 

            if("describeSnapshots".equals(methodName)){
                
                com.amazon.ec2.DescribeSnapshotsResponse describeSnapshotsResponse319 = null;
	                        com.amazon.ec2.DescribeSnapshots wrappedParam =
                                                             (com.amazon.ec2.DescribeSnapshots)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeSnapshots.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeSnapshotsResponse319 =
                                                   
                                                   
                                                         skel.describeSnapshots(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeSnapshotsResponse319, false);
                                    } else 

            if("deactivateLicense".equals(methodName)){
                
                com.amazon.ec2.DeactivateLicenseResponse deactivateLicenseResponse321 = null;
	                        com.amazon.ec2.DeactivateLicense wrappedParam =
                                                             (com.amazon.ec2.DeactivateLicense)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeactivateLicense.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deactivateLicenseResponse321 =
                                                   
                                                   
                                                         skel.deactivateLicense(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deactivateLicenseResponse321, false);
                                    } else 

            if("attachVolume".equals(methodName)){
                
                com.amazon.ec2.AttachVolumeResponse attachVolumeResponse323 = null;
	                        com.amazon.ec2.AttachVolume wrappedParam =
                                                             (com.amazon.ec2.AttachVolume)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.AttachVolume.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               attachVolumeResponse323 =
                                                   
                                                   
                                                         skel.attachVolume(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), attachVolumeResponse323, false);
                                    } else 

            if("deleteVpc".equals(methodName)){
                
                com.amazon.ec2.DeleteVpcResponse deleteVpcResponse325 = null;
	                        com.amazon.ec2.DeleteVpc wrappedParam =
                                                             (com.amazon.ec2.DeleteVpc)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeleteVpc.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deleteVpcResponse325 =
                                                   
                                                   
                                                         skel.deleteVpc(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deleteVpcResponse325, false);
                                    } else 

            if("createSpotDatafeedSubscription".equals(methodName)){
                
                com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse createSpotDatafeedSubscriptionResponse327 = null;
	                        com.amazon.ec2.CreateSpotDatafeedSubscription wrappedParam =
                                                             (com.amazon.ec2.CreateSpotDatafeedSubscription)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateSpotDatafeedSubscription.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createSpotDatafeedSubscriptionResponse327 =
                                                   
                                                   
                                                         skel.createSpotDatafeedSubscription(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createSpotDatafeedSubscriptionResponse327, false);
                                    } else 

            if("cancelSpotInstanceRequests".equals(methodName)){
                
                com.amazon.ec2.CancelSpotInstanceRequestsResponse cancelSpotInstanceRequestsResponse329 = null;
	                        com.amazon.ec2.CancelSpotInstanceRequests wrappedParam =
                                                             (com.amazon.ec2.CancelSpotInstanceRequests)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CancelSpotInstanceRequests.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               cancelSpotInstanceRequestsResponse329 =
                                                   
                                                   
                                                         skel.cancelSpotInstanceRequests(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), cancelSpotInstanceRequestsResponse329, false);
                                    } else 

            if("startInstances".equals(methodName)){
                
                com.amazon.ec2.StartInstancesResponse startInstancesResponse331 = null;
	                        com.amazon.ec2.StartInstances wrappedParam =
                                                             (com.amazon.ec2.StartInstances)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.StartInstances.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               startInstancesResponse331 =
                                                   
                                                   
                                                         skel.startInstances(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), startInstancesResponse331, false);
                                    } else 

            if("deregisterImage".equals(methodName)){
                
                com.amazon.ec2.DeregisterImageResponse deregisterImageResponse333 = null;
	                        com.amazon.ec2.DeregisterImage wrappedParam =
                                                             (com.amazon.ec2.DeregisterImage)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DeregisterImage.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               deregisterImageResponse333 =
                                                   
                                                   
                                                         skel.deregisterImage(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), deregisterImageResponse333, false);
                                    } else 

            if("disassociateAddress".equals(methodName)){
                
                com.amazon.ec2.DisassociateAddressResponse disassociateAddressResponse335 = null;
	                        com.amazon.ec2.DisassociateAddress wrappedParam =
                                                             (com.amazon.ec2.DisassociateAddress)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DisassociateAddress.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               disassociateAddressResponse335 =
                                                   
                                                   
                                                         skel.disassociateAddress(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), disassociateAddressResponse335, false);
                                    } else 

            if("activateLicense".equals(methodName)){
                
                com.amazon.ec2.ActivateLicenseResponse activateLicenseResponse337 = null;
	                        com.amazon.ec2.ActivateLicense wrappedParam =
                                                             (com.amazon.ec2.ActivateLicense)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ActivateLicense.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               activateLicenseResponse337 =
                                                   
                                                   
                                                         skel.activateLicense(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), activateLicenseResponse337, false);
                                    } else 

            if("resetSnapshotAttribute".equals(methodName)){
                
                com.amazon.ec2.ResetSnapshotAttributeResponse resetSnapshotAttributeResponse339 = null;
	                        com.amazon.ec2.ResetSnapshotAttribute wrappedParam =
                                                             (com.amazon.ec2.ResetSnapshotAttribute)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ResetSnapshotAttribute.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               resetSnapshotAttributeResponse339 =
                                                   
                                                   
                                                         skel.resetSnapshotAttribute(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), resetSnapshotAttributeResponse339, false);
                                    } else 

            if("createVpc".equals(methodName)){
                
                com.amazon.ec2.CreateVpcResponse createVpcResponse341 = null;
	                        com.amazon.ec2.CreateVpc wrappedParam =
                                                             (com.amazon.ec2.CreateVpc)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.CreateVpc.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               createVpcResponse341 =
                                                   
                                                   
                                                         skel.createVpc(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), createVpcResponse341, false);
                                    } else 

            if("confirmProductInstance".equals(methodName)){
                
                com.amazon.ec2.ConfirmProductInstanceResponse confirmProductInstanceResponse343 = null;
	                        com.amazon.ec2.ConfirmProductInstance wrappedParam =
                                                             (com.amazon.ec2.ConfirmProductInstance)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.ConfirmProductInstance.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               confirmProductInstanceResponse343 =
                                                   
                                                   
                                                         skel.confirmProductInstance(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), confirmProductInstanceResponse343, false);
                                    } else 

            if("describeAvailabilityZones".equals(methodName)){
                
                com.amazon.ec2.DescribeAvailabilityZonesResponse describeAvailabilityZonesResponse345 = null;
	                        com.amazon.ec2.DescribeAvailabilityZones wrappedParam =
                                                             (com.amazon.ec2.DescribeAvailabilityZones)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeAvailabilityZones.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeAvailabilityZonesResponse345 =
                                                   
                                                   
                                                         skel.describeAvailabilityZones(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeAvailabilityZonesResponse345, false);
                                    } else 

            if("describeReservedInstancesOfferings".equals(methodName)){
                
                com.amazon.ec2.DescribeReservedInstancesOfferingsResponse describeReservedInstancesOfferingsResponse347 = null;
	                        com.amazon.ec2.DescribeReservedInstancesOfferings wrappedParam =
                                                             (com.amazon.ec2.DescribeReservedInstancesOfferings)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    com.amazon.ec2.DescribeReservedInstancesOfferings.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               describeReservedInstancesOfferingsResponse347 =
                                                   
                                                   
                                                         skel.describeReservedInstancesOfferings(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), describeReservedInstancesOfferingsResponse347, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        }
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeRegions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeRegions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeRegionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeRegionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.StopInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.StopInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.StopInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.StopInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RebootInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RebootInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RebootInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RebootInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVpcs param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVpcs.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVpcsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVpcsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.BundleInstance param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.BundleInstance.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.BundleInstanceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.BundleInstanceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RunInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RunInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RunInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RunInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSubnet param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSubnet.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSubnetResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSubnetResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.TerminateInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.TerminateInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.TerminateInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.TerminateInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeKeyPairs param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeKeyPairs.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeKeyPairsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeKeyPairsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.GetPasswordData param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.GetPasswordData.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.GetPasswordDataResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.GetPasswordDataResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ResetImageAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ResetImageAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ResetImageAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ResetImageAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVolume param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVolume.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVolumeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVolumeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.MonitorInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.MonitorInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.MonitorInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.MonitorInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSpotDatafeedSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSpotDatafeedSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.UnmonitorInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.UnmonitorInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.UnmonitorInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.UnmonitorInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AssociateDhcpOptions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AssociateDhcpOptions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AssociateDhcpOptionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AssociateDhcpOptionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteCustomerGateway param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteCustomerGateway.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteCustomerGatewayResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteCustomerGatewayResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AuthorizeSecurityGroupIngress param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AuthorizeSecurityGroupIngress.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AuthorizeSecurityGroupIngressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AuthorizeSecurityGroupIngressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSnapshot param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSnapshot.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSnapshotResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSnapshotResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.GetConsoleOutput param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.GetConsoleOutput.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.GetConsoleOutputResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.GetConsoleOutputResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateImage param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateImage.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateImageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateImageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSpotPriceHistory param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSpotPriceHistory.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSpotPriceHistoryResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSpotPriceHistoryResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeAddresses param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeAddresses.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeAddressesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeAddressesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSnapshot param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSnapshot.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSnapshotResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSnapshotResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeletePlacementGroup param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeletePlacementGroup.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeletePlacementGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeletePlacementGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DetachVpnGateway param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DetachVpnGateway.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DetachVpnGatewayResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DetachVpnGatewayResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeBundleTasks param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeBundleTasks.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeBundleTasksResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeBundleTasksResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeLicenses param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeLicenses.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeLicensesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeLicensesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVpnGateway param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVpnGateway.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVpnGatewayResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVpnGatewayResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.PurchaseReservedInstancesOffering param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.PurchaseReservedInstancesOffering.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.PurchaseReservedInstancesOfferingResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.PurchaseReservedInstancesOfferingResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteDhcpOptions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteDhcpOptions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteDhcpOptionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteDhcpOptionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateCustomerGateway param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateCustomerGateway.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateCustomerGatewayResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateCustomerGatewayResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteKeyPair param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteKeyPair.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteKeyPairResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteKeyPairResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateDhcpOptions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateDhcpOptions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateDhcpOptionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateDhcpOptionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSubnets param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSubnets.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSubnetsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSubnetsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSecurityGroup param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSecurityGroup.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSecurityGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSecurityGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSpotDatafeedSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSpotDatafeedSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSnapshotAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSnapshotAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSnapshotAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSnapshotAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVpnConnection param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVpnConnection.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVpnConnectionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVpnConnectionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribePlacementGroups param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribePlacementGroups.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribePlacementGroupsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribePlacementGroupsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSubnet param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSubnet.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSubnetResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSubnetResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSpotInstanceRequests param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSpotInstanceRequests.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSpotInstanceRequestsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSpotInstanceRequestsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CancelBundleTask param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CancelBundleTask.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CancelBundleTaskResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CancelBundleTaskResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVolumes param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVolumes.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVolumesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVolumesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RequestSpotInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RequestSpotInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RequestSpotInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RequestSpotInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeImages param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeImages.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeImagesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeImagesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AssociateAddress param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AssociateAddress.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AssociateAddressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AssociateAddressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ReleaseAddress param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ReleaseAddress.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ReleaseAddressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ReleaseAddressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RevokeSecurityGroupIngress param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RevokeSecurityGroupIngress.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RevokeSecurityGroupIngressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RevokeSecurityGroupIngressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ModifySnapshotAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ModifySnapshotAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ModifySnapshotAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ModifySnapshotAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreatePlacementGroup param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreatePlacementGroup.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreatePlacementGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreatePlacementGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DetachVolume param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DetachVolume.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DetachVolumeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DetachVolumeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeDhcpOptions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeDhcpOptions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeDhcpOptionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeDhcpOptionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVpnConnection param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVpnConnection.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVpnConnectionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVpnConnectionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeImageAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeImageAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeImageAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeImageAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RegisterImage param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RegisterImage.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.RegisterImageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.RegisterImageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeInstanceAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeInstanceAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeInstanceAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeInstanceAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSecurityGroup param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSecurityGroup.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteSecurityGroupResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteSecurityGroupResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVpnGateway param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVpnGateway.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVpnGatewayResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVpnGatewayResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AttachVpnGateway param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AttachVpnGateway.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AttachVpnGatewayResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AttachVpnGatewayResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ResetInstanceAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ResetInstanceAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ResetInstanceAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ResetInstanceAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVpnGateways param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVpnGateways.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVpnGatewaysResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVpnGatewaysResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeReservedInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeReservedInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeReservedInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeReservedInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ModifyImageAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ModifyImageAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ModifyImageAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ModifyImageAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ModifyInstanceAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ModifyInstanceAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ModifyInstanceAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ModifyInstanceAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVolume param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVolume.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVolumeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVolumeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AllocateAddress param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AllocateAddress.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AllocateAddressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AllocateAddressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateKeyPair param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateKeyPair.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateKeyPairResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateKeyPairResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVpnConnections param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVpnConnections.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeVpnConnectionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeVpnConnectionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSecurityGroups param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSecurityGroups.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSecurityGroupsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSecurityGroupsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeCustomerGateways param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeCustomerGateways.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeCustomerGatewaysResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeCustomerGatewaysResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSnapshots param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSnapshots.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeSnapshotsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeSnapshotsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeactivateLicense param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeactivateLicense.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeactivateLicenseResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeactivateLicenseResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AttachVolume param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AttachVolume.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.AttachVolumeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.AttachVolumeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVpc param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVpc.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeleteVpcResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeleteVpcResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSpotDatafeedSubscription param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSpotDatafeedSubscription.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CancelSpotInstanceRequests param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CancelSpotInstanceRequests.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CancelSpotInstanceRequestsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CancelSpotInstanceRequestsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.StartInstances param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.StartInstances.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.StartInstancesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.StartInstancesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeregisterImage param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeregisterImage.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DeregisterImageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DeregisterImageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DisassociateAddress param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DisassociateAddress.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DisassociateAddressResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DisassociateAddressResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ActivateLicense param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ActivateLicense.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ActivateLicenseResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ActivateLicenseResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ResetSnapshotAttribute param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ResetSnapshotAttribute.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ResetSnapshotAttributeResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ResetSnapshotAttributeResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVpc param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVpc.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.CreateVpcResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.CreateVpcResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ConfirmProductInstance param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ConfirmProductInstance.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.ConfirmProductInstanceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.ConfirmProductInstanceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeAvailabilityZones param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeAvailabilityZones.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeAvailabilityZonesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeAvailabilityZonesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeReservedInstancesOfferings param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeReservedInstancesOfferings.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.amazon.ec2.DescribeReservedInstancesOfferingsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.amazon.ec2.DescribeReservedInstancesOfferingsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeInstancesResponse wrapDescribeInstances(){
                                com.amazon.ec2.DescribeInstancesResponse wrappedElement = new com.amazon.ec2.DescribeInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeRegionsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeRegionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeRegionsResponse wrapDescribeRegions(){
                                com.amazon.ec2.DescribeRegionsResponse wrappedElement = new com.amazon.ec2.DescribeRegionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.StopInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.StopInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.StopInstancesResponse wrapStopInstances(){
                                com.amazon.ec2.StopInstancesResponse wrappedElement = new com.amazon.ec2.StopInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.RebootInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.RebootInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.RebootInstancesResponse wrapRebootInstances(){
                                com.amazon.ec2.RebootInstancesResponse wrappedElement = new com.amazon.ec2.RebootInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeVpcsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeVpcsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeVpcsResponse wrapDescribeVpcs(){
                                com.amazon.ec2.DescribeVpcsResponse wrappedElement = new com.amazon.ec2.DescribeVpcsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.BundleInstanceResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.BundleInstanceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.BundleInstanceResponse wrapBundleInstance(){
                                com.amazon.ec2.BundleInstanceResponse wrappedElement = new com.amazon.ec2.BundleInstanceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.RunInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.RunInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.RunInstancesResponse wrapRunInstances(){
                                com.amazon.ec2.RunInstancesResponse wrappedElement = new com.amazon.ec2.RunInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteSubnetResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteSubnetResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteSubnetResponse wrapDeleteSubnet(){
                                com.amazon.ec2.DeleteSubnetResponse wrappedElement = new com.amazon.ec2.DeleteSubnetResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.TerminateInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.TerminateInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.TerminateInstancesResponse wrapTerminateInstances(){
                                com.amazon.ec2.TerminateInstancesResponse wrappedElement = new com.amazon.ec2.TerminateInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeKeyPairsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeKeyPairsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeKeyPairsResponse wrapDescribeKeyPairs(){
                                com.amazon.ec2.DescribeKeyPairsResponse wrappedElement = new com.amazon.ec2.DescribeKeyPairsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.GetPasswordDataResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.GetPasswordDataResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.GetPasswordDataResponse wrapGetPasswordData(){
                                com.amazon.ec2.GetPasswordDataResponse wrappedElement = new com.amazon.ec2.GetPasswordDataResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ResetImageAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ResetImageAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ResetImageAttributeResponse wrapResetImageAttribute(){
                                com.amazon.ec2.ResetImageAttributeResponse wrappedElement = new com.amazon.ec2.ResetImageAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateVolumeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateVolumeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateVolumeResponse wrapCreateVolume(){
                                com.amazon.ec2.CreateVolumeResponse wrappedElement = new com.amazon.ec2.CreateVolumeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.MonitorInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.MonitorInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.MonitorInstancesResponse wrapMonitorInstances(){
                                com.amazon.ec2.MonitorInstancesResponse wrappedElement = new com.amazon.ec2.MonitorInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse wrapDeleteSpotDatafeedSubscription(){
                                com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse wrappedElement = new com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.UnmonitorInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.UnmonitorInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.UnmonitorInstancesResponse wrapUnmonitorInstances(){
                                com.amazon.ec2.UnmonitorInstancesResponse wrappedElement = new com.amazon.ec2.UnmonitorInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.AssociateDhcpOptionsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.AssociateDhcpOptionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.AssociateDhcpOptionsResponse wrapAssociateDhcpOptions(){
                                com.amazon.ec2.AssociateDhcpOptionsResponse wrappedElement = new com.amazon.ec2.AssociateDhcpOptionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteCustomerGatewayResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteCustomerGatewayResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteCustomerGatewayResponse wrapDeleteCustomerGateway(){
                                com.amazon.ec2.DeleteCustomerGatewayResponse wrappedElement = new com.amazon.ec2.DeleteCustomerGatewayResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.AuthorizeSecurityGroupIngressResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.AuthorizeSecurityGroupIngressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.AuthorizeSecurityGroupIngressResponse wrapAuthorizeSecurityGroupIngress(){
                                com.amazon.ec2.AuthorizeSecurityGroupIngressResponse wrappedElement = new com.amazon.ec2.AuthorizeSecurityGroupIngressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateSnapshotResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateSnapshotResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateSnapshotResponse wrapCreateSnapshot(){
                                com.amazon.ec2.CreateSnapshotResponse wrappedElement = new com.amazon.ec2.CreateSnapshotResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.GetConsoleOutputResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.GetConsoleOutputResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.GetConsoleOutputResponse wrapGetConsoleOutput(){
                                com.amazon.ec2.GetConsoleOutputResponse wrappedElement = new com.amazon.ec2.GetConsoleOutputResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateImageResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateImageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateImageResponse wrapCreateImage(){
                                com.amazon.ec2.CreateImageResponse wrappedElement = new com.amazon.ec2.CreateImageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSpotPriceHistoryResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSpotPriceHistoryResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSpotPriceHistoryResponse wrapDescribeSpotPriceHistory(){
                                com.amazon.ec2.DescribeSpotPriceHistoryResponse wrappedElement = new com.amazon.ec2.DescribeSpotPriceHistoryResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeAddressesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeAddressesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeAddressesResponse wrapDescribeAddresses(){
                                com.amazon.ec2.DescribeAddressesResponse wrappedElement = new com.amazon.ec2.DescribeAddressesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteSnapshotResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteSnapshotResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteSnapshotResponse wrapDeleteSnapshot(){
                                com.amazon.ec2.DeleteSnapshotResponse wrappedElement = new com.amazon.ec2.DeleteSnapshotResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeletePlacementGroupResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeletePlacementGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeletePlacementGroupResponse wrapDeletePlacementGroup(){
                                com.amazon.ec2.DeletePlacementGroupResponse wrappedElement = new com.amazon.ec2.DeletePlacementGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DetachVpnGatewayResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DetachVpnGatewayResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DetachVpnGatewayResponse wrapDetachVpnGateway(){
                                com.amazon.ec2.DetachVpnGatewayResponse wrappedElement = new com.amazon.ec2.DetachVpnGatewayResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeBundleTasksResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeBundleTasksResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeBundleTasksResponse wrapDescribeBundleTasks(){
                                com.amazon.ec2.DescribeBundleTasksResponse wrappedElement = new com.amazon.ec2.DescribeBundleTasksResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeLicensesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeLicensesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeLicensesResponse wrapDescribeLicenses(){
                                com.amazon.ec2.DescribeLicensesResponse wrappedElement = new com.amazon.ec2.DescribeLicensesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteVpnGatewayResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteVpnGatewayResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteVpnGatewayResponse wrapDeleteVpnGateway(){
                                com.amazon.ec2.DeleteVpnGatewayResponse wrappedElement = new com.amazon.ec2.DeleteVpnGatewayResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.PurchaseReservedInstancesOfferingResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.PurchaseReservedInstancesOfferingResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.PurchaseReservedInstancesOfferingResponse wrapPurchaseReservedInstancesOffering(){
                                com.amazon.ec2.PurchaseReservedInstancesOfferingResponse wrappedElement = new com.amazon.ec2.PurchaseReservedInstancesOfferingResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteDhcpOptionsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteDhcpOptionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteDhcpOptionsResponse wrapDeleteDhcpOptions(){
                                com.amazon.ec2.DeleteDhcpOptionsResponse wrappedElement = new com.amazon.ec2.DeleteDhcpOptionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateCustomerGatewayResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateCustomerGatewayResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateCustomerGatewayResponse wrapCreateCustomerGateway(){
                                com.amazon.ec2.CreateCustomerGatewayResponse wrappedElement = new com.amazon.ec2.CreateCustomerGatewayResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteKeyPairResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteKeyPairResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteKeyPairResponse wrapDeleteKeyPair(){
                                com.amazon.ec2.DeleteKeyPairResponse wrappedElement = new com.amazon.ec2.DeleteKeyPairResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateDhcpOptionsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateDhcpOptionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateDhcpOptionsResponse wrapCreateDhcpOptions(){
                                com.amazon.ec2.CreateDhcpOptionsResponse wrappedElement = new com.amazon.ec2.CreateDhcpOptionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSubnetsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSubnetsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSubnetsResponse wrapDescribeSubnets(){
                                com.amazon.ec2.DescribeSubnetsResponse wrappedElement = new com.amazon.ec2.DescribeSubnetsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateSecurityGroupResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateSecurityGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateSecurityGroupResponse wrapCreateSecurityGroup(){
                                com.amazon.ec2.CreateSecurityGroupResponse wrappedElement = new com.amazon.ec2.CreateSecurityGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse wrapDescribeSpotDatafeedSubscription(){
                                com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse wrappedElement = new com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSnapshotAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSnapshotAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSnapshotAttributeResponse wrapDescribeSnapshotAttribute(){
                                com.amazon.ec2.DescribeSnapshotAttributeResponse wrappedElement = new com.amazon.ec2.DescribeSnapshotAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteVpnConnectionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteVpnConnectionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteVpnConnectionResponse wrapDeleteVpnConnection(){
                                com.amazon.ec2.DeleteVpnConnectionResponse wrappedElement = new com.amazon.ec2.DeleteVpnConnectionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribePlacementGroupsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribePlacementGroupsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribePlacementGroupsResponse wrapDescribePlacementGroups(){
                                com.amazon.ec2.DescribePlacementGroupsResponse wrappedElement = new com.amazon.ec2.DescribePlacementGroupsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateSubnetResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateSubnetResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateSubnetResponse wrapCreateSubnet(){
                                com.amazon.ec2.CreateSubnetResponse wrappedElement = new com.amazon.ec2.CreateSubnetResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSpotInstanceRequestsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSpotInstanceRequestsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSpotInstanceRequestsResponse wrapDescribeSpotInstanceRequests(){
                                com.amazon.ec2.DescribeSpotInstanceRequestsResponse wrappedElement = new com.amazon.ec2.DescribeSpotInstanceRequestsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CancelBundleTaskResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CancelBundleTaskResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CancelBundleTaskResponse wrapCancelBundleTask(){
                                com.amazon.ec2.CancelBundleTaskResponse wrappedElement = new com.amazon.ec2.CancelBundleTaskResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeVolumesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeVolumesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeVolumesResponse wrapDescribeVolumes(){
                                com.amazon.ec2.DescribeVolumesResponse wrappedElement = new com.amazon.ec2.DescribeVolumesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.RequestSpotInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.RequestSpotInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.RequestSpotInstancesResponse wrapRequestSpotInstances(){
                                com.amazon.ec2.RequestSpotInstancesResponse wrappedElement = new com.amazon.ec2.RequestSpotInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeImagesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeImagesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeImagesResponse wrapDescribeImages(){
                                com.amazon.ec2.DescribeImagesResponse wrappedElement = new com.amazon.ec2.DescribeImagesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.AssociateAddressResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.AssociateAddressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.AssociateAddressResponse wrapAssociateAddress(){
                                com.amazon.ec2.AssociateAddressResponse wrappedElement = new com.amazon.ec2.AssociateAddressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ReleaseAddressResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ReleaseAddressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ReleaseAddressResponse wrapReleaseAddress(){
                                com.amazon.ec2.ReleaseAddressResponse wrappedElement = new com.amazon.ec2.ReleaseAddressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.RevokeSecurityGroupIngressResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.RevokeSecurityGroupIngressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.RevokeSecurityGroupIngressResponse wrapRevokeSecurityGroupIngress(){
                                com.amazon.ec2.RevokeSecurityGroupIngressResponse wrappedElement = new com.amazon.ec2.RevokeSecurityGroupIngressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ModifySnapshotAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ModifySnapshotAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ModifySnapshotAttributeResponse wrapModifySnapshotAttribute(){
                                com.amazon.ec2.ModifySnapshotAttributeResponse wrappedElement = new com.amazon.ec2.ModifySnapshotAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreatePlacementGroupResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreatePlacementGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreatePlacementGroupResponse wrapCreatePlacementGroup(){
                                com.amazon.ec2.CreatePlacementGroupResponse wrappedElement = new com.amazon.ec2.CreatePlacementGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DetachVolumeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DetachVolumeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DetachVolumeResponse wrapDetachVolume(){
                                com.amazon.ec2.DetachVolumeResponse wrappedElement = new com.amazon.ec2.DetachVolumeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeDhcpOptionsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeDhcpOptionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeDhcpOptionsResponse wrapDescribeDhcpOptions(){
                                com.amazon.ec2.DescribeDhcpOptionsResponse wrappedElement = new com.amazon.ec2.DescribeDhcpOptionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateVpnConnectionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateVpnConnectionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateVpnConnectionResponse wrapCreateVpnConnection(){
                                com.amazon.ec2.CreateVpnConnectionResponse wrappedElement = new com.amazon.ec2.CreateVpnConnectionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeImageAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeImageAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeImageAttributeResponse wrapDescribeImageAttribute(){
                                com.amazon.ec2.DescribeImageAttributeResponse wrappedElement = new com.amazon.ec2.DescribeImageAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.RegisterImageResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.RegisterImageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.RegisterImageResponse wrapRegisterImage(){
                                com.amazon.ec2.RegisterImageResponse wrappedElement = new com.amazon.ec2.RegisterImageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeInstanceAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeInstanceAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeInstanceAttributeResponse wrapDescribeInstanceAttribute(){
                                com.amazon.ec2.DescribeInstanceAttributeResponse wrappedElement = new com.amazon.ec2.DescribeInstanceAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteSecurityGroupResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteSecurityGroupResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteSecurityGroupResponse wrapDeleteSecurityGroup(){
                                com.amazon.ec2.DeleteSecurityGroupResponse wrappedElement = new com.amazon.ec2.DeleteSecurityGroupResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateVpnGatewayResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateVpnGatewayResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateVpnGatewayResponse wrapCreateVpnGateway(){
                                com.amazon.ec2.CreateVpnGatewayResponse wrappedElement = new com.amazon.ec2.CreateVpnGatewayResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.AttachVpnGatewayResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.AttachVpnGatewayResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.AttachVpnGatewayResponse wrapAttachVpnGateway(){
                                com.amazon.ec2.AttachVpnGatewayResponse wrappedElement = new com.amazon.ec2.AttachVpnGatewayResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ResetInstanceAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ResetInstanceAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ResetInstanceAttributeResponse wrapResetInstanceAttribute(){
                                com.amazon.ec2.ResetInstanceAttributeResponse wrappedElement = new com.amazon.ec2.ResetInstanceAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeVpnGatewaysResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeVpnGatewaysResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeVpnGatewaysResponse wrapDescribeVpnGateways(){
                                com.amazon.ec2.DescribeVpnGatewaysResponse wrappedElement = new com.amazon.ec2.DescribeVpnGatewaysResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeReservedInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeReservedInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeReservedInstancesResponse wrapDescribeReservedInstances(){
                                com.amazon.ec2.DescribeReservedInstancesResponse wrappedElement = new com.amazon.ec2.DescribeReservedInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ModifyImageAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ModifyImageAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ModifyImageAttributeResponse wrapModifyImageAttribute(){
                                com.amazon.ec2.ModifyImageAttributeResponse wrappedElement = new com.amazon.ec2.ModifyImageAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ModifyInstanceAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ModifyInstanceAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ModifyInstanceAttributeResponse wrapModifyInstanceAttribute(){
                                com.amazon.ec2.ModifyInstanceAttributeResponse wrappedElement = new com.amazon.ec2.ModifyInstanceAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteVolumeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteVolumeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteVolumeResponse wrapDeleteVolume(){
                                com.amazon.ec2.DeleteVolumeResponse wrappedElement = new com.amazon.ec2.DeleteVolumeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.AllocateAddressResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.AllocateAddressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.AllocateAddressResponse wrapAllocateAddress(){
                                com.amazon.ec2.AllocateAddressResponse wrappedElement = new com.amazon.ec2.AllocateAddressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateKeyPairResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateKeyPairResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateKeyPairResponse wrapCreateKeyPair(){
                                com.amazon.ec2.CreateKeyPairResponse wrappedElement = new com.amazon.ec2.CreateKeyPairResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeVpnConnectionsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeVpnConnectionsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeVpnConnectionsResponse wrapDescribeVpnConnections(){
                                com.amazon.ec2.DescribeVpnConnectionsResponse wrappedElement = new com.amazon.ec2.DescribeVpnConnectionsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSecurityGroupsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSecurityGroupsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSecurityGroupsResponse wrapDescribeSecurityGroups(){
                                com.amazon.ec2.DescribeSecurityGroupsResponse wrappedElement = new com.amazon.ec2.DescribeSecurityGroupsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeCustomerGatewaysResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeCustomerGatewaysResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeCustomerGatewaysResponse wrapDescribeCustomerGateways(){
                                com.amazon.ec2.DescribeCustomerGatewaysResponse wrappedElement = new com.amazon.ec2.DescribeCustomerGatewaysResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeSnapshotsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeSnapshotsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeSnapshotsResponse wrapDescribeSnapshots(){
                                com.amazon.ec2.DescribeSnapshotsResponse wrappedElement = new com.amazon.ec2.DescribeSnapshotsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeactivateLicenseResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeactivateLicenseResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeactivateLicenseResponse wrapDeactivateLicense(){
                                com.amazon.ec2.DeactivateLicenseResponse wrappedElement = new com.amazon.ec2.DeactivateLicenseResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.AttachVolumeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.AttachVolumeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.AttachVolumeResponse wrapAttachVolume(){
                                com.amazon.ec2.AttachVolumeResponse wrappedElement = new com.amazon.ec2.AttachVolumeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeleteVpcResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeleteVpcResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeleteVpcResponse wrapDeleteVpc(){
                                com.amazon.ec2.DeleteVpcResponse wrappedElement = new com.amazon.ec2.DeleteVpcResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse wrapCreateSpotDatafeedSubscription(){
                                com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse wrappedElement = new com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CancelSpotInstanceRequestsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CancelSpotInstanceRequestsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CancelSpotInstanceRequestsResponse wrapCancelSpotInstanceRequests(){
                                com.amazon.ec2.CancelSpotInstanceRequestsResponse wrappedElement = new com.amazon.ec2.CancelSpotInstanceRequestsResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.StartInstancesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.StartInstancesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.StartInstancesResponse wrapStartInstances(){
                                com.amazon.ec2.StartInstancesResponse wrappedElement = new com.amazon.ec2.StartInstancesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DeregisterImageResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DeregisterImageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DeregisterImageResponse wrapDeregisterImage(){
                                com.amazon.ec2.DeregisterImageResponse wrappedElement = new com.amazon.ec2.DeregisterImageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DisassociateAddressResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DisassociateAddressResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DisassociateAddressResponse wrapDisassociateAddress(){
                                com.amazon.ec2.DisassociateAddressResponse wrappedElement = new com.amazon.ec2.DisassociateAddressResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ActivateLicenseResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ActivateLicenseResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ActivateLicenseResponse wrapActivateLicense(){
                                com.amazon.ec2.ActivateLicenseResponse wrappedElement = new com.amazon.ec2.ActivateLicenseResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ResetSnapshotAttributeResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ResetSnapshotAttributeResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ResetSnapshotAttributeResponse wrapResetSnapshotAttribute(){
                                com.amazon.ec2.ResetSnapshotAttributeResponse wrappedElement = new com.amazon.ec2.ResetSnapshotAttributeResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.CreateVpcResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.CreateVpcResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.CreateVpcResponse wrapCreateVpc(){
                                com.amazon.ec2.CreateVpcResponse wrappedElement = new com.amazon.ec2.CreateVpcResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.ConfirmProductInstanceResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.ConfirmProductInstanceResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.ConfirmProductInstanceResponse wrapConfirmProductInstance(){
                                com.amazon.ec2.ConfirmProductInstanceResponse wrappedElement = new com.amazon.ec2.ConfirmProductInstanceResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeAvailabilityZonesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeAvailabilityZonesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeAvailabilityZonesResponse wrapDescribeAvailabilityZones(){
                                com.amazon.ec2.DescribeAvailabilityZonesResponse wrappedElement = new com.amazon.ec2.DescribeAvailabilityZonesResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.amazon.ec2.DescribeReservedInstancesOfferingsResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(com.amazon.ec2.DescribeReservedInstancesOfferingsResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private com.amazon.ec2.DescribeReservedInstancesOfferingsResponse wrapDescribeReservedInstancesOfferings(){
                                com.amazon.ec2.DescribeReservedInstancesOfferingsResponse wrappedElement = new com.amazon.ec2.DescribeReservedInstancesOfferingsResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.amazon.ec2.DescribeInstances.class.equals(type)){
                
                           return com.amazon.ec2.DescribeInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeRegions.class.equals(type)){
                
                           return com.amazon.ec2.DescribeRegions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeRegionsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeRegionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.StopInstances.class.equals(type)){
                
                           return com.amazon.ec2.StopInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.StopInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.StopInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RebootInstances.class.equals(type)){
                
                           return com.amazon.ec2.RebootInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RebootInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.RebootInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVpcs.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVpcs.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVpcsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVpcsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.BundleInstance.class.equals(type)){
                
                           return com.amazon.ec2.BundleInstance.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.BundleInstanceResponse.class.equals(type)){
                
                           return com.amazon.ec2.BundleInstanceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RunInstances.class.equals(type)){
                
                           return com.amazon.ec2.RunInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RunInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.RunInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSubnet.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSubnet.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSubnetResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSubnetResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.TerminateInstances.class.equals(type)){
                
                           return com.amazon.ec2.TerminateInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.TerminateInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.TerminateInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeKeyPairs.class.equals(type)){
                
                           return com.amazon.ec2.DescribeKeyPairs.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeKeyPairsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeKeyPairsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.GetPasswordData.class.equals(type)){
                
                           return com.amazon.ec2.GetPasswordData.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.GetPasswordDataResponse.class.equals(type)){
                
                           return com.amazon.ec2.GetPasswordDataResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ResetImageAttribute.class.equals(type)){
                
                           return com.amazon.ec2.ResetImageAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ResetImageAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.ResetImageAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVolume.class.equals(type)){
                
                           return com.amazon.ec2.CreateVolume.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVolumeResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateVolumeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.MonitorInstances.class.equals(type)){
                
                           return com.amazon.ec2.MonitorInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.MonitorInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.MonitorInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSpotDatafeedSubscription.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSpotDatafeedSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSpotDatafeedSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.UnmonitorInstances.class.equals(type)){
                
                           return com.amazon.ec2.UnmonitorInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.UnmonitorInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.UnmonitorInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AssociateDhcpOptions.class.equals(type)){
                
                           return com.amazon.ec2.AssociateDhcpOptions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AssociateDhcpOptionsResponse.class.equals(type)){
                
                           return com.amazon.ec2.AssociateDhcpOptionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteCustomerGateway.class.equals(type)){
                
                           return com.amazon.ec2.DeleteCustomerGateway.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteCustomerGatewayResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteCustomerGatewayResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AuthorizeSecurityGroupIngress.class.equals(type)){
                
                           return com.amazon.ec2.AuthorizeSecurityGroupIngress.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AuthorizeSecurityGroupIngressResponse.class.equals(type)){
                
                           return com.amazon.ec2.AuthorizeSecurityGroupIngressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSnapshot.class.equals(type)){
                
                           return com.amazon.ec2.CreateSnapshot.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSnapshotResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateSnapshotResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.GetConsoleOutput.class.equals(type)){
                
                           return com.amazon.ec2.GetConsoleOutput.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.GetConsoleOutputResponse.class.equals(type)){
                
                           return com.amazon.ec2.GetConsoleOutputResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateImage.class.equals(type)){
                
                           return com.amazon.ec2.CreateImage.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateImageResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateImageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSpotPriceHistory.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSpotPriceHistory.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSpotPriceHistoryResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSpotPriceHistoryResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeAddresses.class.equals(type)){
                
                           return com.amazon.ec2.DescribeAddresses.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeAddressesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeAddressesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSnapshot.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSnapshot.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSnapshotResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSnapshotResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeletePlacementGroup.class.equals(type)){
                
                           return com.amazon.ec2.DeletePlacementGroup.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeletePlacementGroupResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeletePlacementGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DetachVpnGateway.class.equals(type)){
                
                           return com.amazon.ec2.DetachVpnGateway.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DetachVpnGatewayResponse.class.equals(type)){
                
                           return com.amazon.ec2.DetachVpnGatewayResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeBundleTasks.class.equals(type)){
                
                           return com.amazon.ec2.DescribeBundleTasks.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeBundleTasksResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeBundleTasksResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeLicenses.class.equals(type)){
                
                           return com.amazon.ec2.DescribeLicenses.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeLicensesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeLicensesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVpnGateway.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVpnGateway.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVpnGatewayResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVpnGatewayResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.PurchaseReservedInstancesOffering.class.equals(type)){
                
                           return com.amazon.ec2.PurchaseReservedInstancesOffering.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.PurchaseReservedInstancesOfferingResponse.class.equals(type)){
                
                           return com.amazon.ec2.PurchaseReservedInstancesOfferingResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteDhcpOptions.class.equals(type)){
                
                           return com.amazon.ec2.DeleteDhcpOptions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteDhcpOptionsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteDhcpOptionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateCustomerGateway.class.equals(type)){
                
                           return com.amazon.ec2.CreateCustomerGateway.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateCustomerGatewayResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateCustomerGatewayResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteKeyPair.class.equals(type)){
                
                           return com.amazon.ec2.DeleteKeyPair.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteKeyPairResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteKeyPairResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateDhcpOptions.class.equals(type)){
                
                           return com.amazon.ec2.CreateDhcpOptions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateDhcpOptionsResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateDhcpOptionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSubnets.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSubnets.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSubnetsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSubnetsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSecurityGroup.class.equals(type)){
                
                           return com.amazon.ec2.CreateSecurityGroup.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSecurityGroupResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateSecurityGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSpotDatafeedSubscription.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSpotDatafeedSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSpotDatafeedSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSnapshotAttribute.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSnapshotAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSnapshotAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSnapshotAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVpnConnection.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVpnConnection.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVpnConnectionResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVpnConnectionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribePlacementGroups.class.equals(type)){
                
                           return com.amazon.ec2.DescribePlacementGroups.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribePlacementGroupsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribePlacementGroupsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSubnet.class.equals(type)){
                
                           return com.amazon.ec2.CreateSubnet.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSubnetResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateSubnetResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSpotInstanceRequests.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSpotInstanceRequests.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSpotInstanceRequestsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSpotInstanceRequestsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CancelBundleTask.class.equals(type)){
                
                           return com.amazon.ec2.CancelBundleTask.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CancelBundleTaskResponse.class.equals(type)){
                
                           return com.amazon.ec2.CancelBundleTaskResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVolumes.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVolumes.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVolumesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVolumesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RequestSpotInstances.class.equals(type)){
                
                           return com.amazon.ec2.RequestSpotInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RequestSpotInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.RequestSpotInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeImages.class.equals(type)){
                
                           return com.amazon.ec2.DescribeImages.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeImagesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeImagesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AssociateAddress.class.equals(type)){
                
                           return com.amazon.ec2.AssociateAddress.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AssociateAddressResponse.class.equals(type)){
                
                           return com.amazon.ec2.AssociateAddressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ReleaseAddress.class.equals(type)){
                
                           return com.amazon.ec2.ReleaseAddress.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ReleaseAddressResponse.class.equals(type)){
                
                           return com.amazon.ec2.ReleaseAddressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RevokeSecurityGroupIngress.class.equals(type)){
                
                           return com.amazon.ec2.RevokeSecurityGroupIngress.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RevokeSecurityGroupIngressResponse.class.equals(type)){
                
                           return com.amazon.ec2.RevokeSecurityGroupIngressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ModifySnapshotAttribute.class.equals(type)){
                
                           return com.amazon.ec2.ModifySnapshotAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ModifySnapshotAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.ModifySnapshotAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreatePlacementGroup.class.equals(type)){
                
                           return com.amazon.ec2.CreatePlacementGroup.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreatePlacementGroupResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreatePlacementGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DetachVolume.class.equals(type)){
                
                           return com.amazon.ec2.DetachVolume.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DetachVolumeResponse.class.equals(type)){
                
                           return com.amazon.ec2.DetachVolumeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeDhcpOptions.class.equals(type)){
                
                           return com.amazon.ec2.DescribeDhcpOptions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeDhcpOptionsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeDhcpOptionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVpnConnection.class.equals(type)){
                
                           return com.amazon.ec2.CreateVpnConnection.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVpnConnectionResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateVpnConnectionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeImageAttribute.class.equals(type)){
                
                           return com.amazon.ec2.DescribeImageAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeImageAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeImageAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RegisterImage.class.equals(type)){
                
                           return com.amazon.ec2.RegisterImage.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.RegisterImageResponse.class.equals(type)){
                
                           return com.amazon.ec2.RegisterImageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeInstanceAttribute.class.equals(type)){
                
                           return com.amazon.ec2.DescribeInstanceAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeInstanceAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeInstanceAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSecurityGroup.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSecurityGroup.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteSecurityGroupResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteSecurityGroupResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVpnGateway.class.equals(type)){
                
                           return com.amazon.ec2.CreateVpnGateway.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVpnGatewayResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateVpnGatewayResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AttachVpnGateway.class.equals(type)){
                
                           return com.amazon.ec2.AttachVpnGateway.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AttachVpnGatewayResponse.class.equals(type)){
                
                           return com.amazon.ec2.AttachVpnGatewayResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ResetInstanceAttribute.class.equals(type)){
                
                           return com.amazon.ec2.ResetInstanceAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ResetInstanceAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.ResetInstanceAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVpnGateways.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVpnGateways.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVpnGatewaysResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVpnGatewaysResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeReservedInstances.class.equals(type)){
                
                           return com.amazon.ec2.DescribeReservedInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeReservedInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeReservedInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ModifyImageAttribute.class.equals(type)){
                
                           return com.amazon.ec2.ModifyImageAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ModifyImageAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.ModifyImageAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ModifyInstanceAttribute.class.equals(type)){
                
                           return com.amazon.ec2.ModifyInstanceAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ModifyInstanceAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.ModifyInstanceAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVolume.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVolume.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVolumeResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVolumeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AllocateAddress.class.equals(type)){
                
                           return com.amazon.ec2.AllocateAddress.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AllocateAddressResponse.class.equals(type)){
                
                           return com.amazon.ec2.AllocateAddressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateKeyPair.class.equals(type)){
                
                           return com.amazon.ec2.CreateKeyPair.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateKeyPairResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateKeyPairResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVpnConnections.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVpnConnections.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeVpnConnectionsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeVpnConnectionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSecurityGroups.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSecurityGroups.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSecurityGroupsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSecurityGroupsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeCustomerGateways.class.equals(type)){
                
                           return com.amazon.ec2.DescribeCustomerGateways.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeCustomerGatewaysResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeCustomerGatewaysResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSnapshots.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSnapshots.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeSnapshotsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeSnapshotsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeactivateLicense.class.equals(type)){
                
                           return com.amazon.ec2.DeactivateLicense.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeactivateLicenseResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeactivateLicenseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AttachVolume.class.equals(type)){
                
                           return com.amazon.ec2.AttachVolume.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.AttachVolumeResponse.class.equals(type)){
                
                           return com.amazon.ec2.AttachVolumeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVpc.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVpc.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeleteVpcResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeleteVpcResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSpotDatafeedSubscription.class.equals(type)){
                
                           return com.amazon.ec2.CreateSpotDatafeedSubscription.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateSpotDatafeedSubscriptionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CancelSpotInstanceRequests.class.equals(type)){
                
                           return com.amazon.ec2.CancelSpotInstanceRequests.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CancelSpotInstanceRequestsResponse.class.equals(type)){
                
                           return com.amazon.ec2.CancelSpotInstanceRequestsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.StartInstances.class.equals(type)){
                
                           return com.amazon.ec2.StartInstances.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.StartInstancesResponse.class.equals(type)){
                
                           return com.amazon.ec2.StartInstancesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeregisterImage.class.equals(type)){
                
                           return com.amazon.ec2.DeregisterImage.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DeregisterImageResponse.class.equals(type)){
                
                           return com.amazon.ec2.DeregisterImageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DisassociateAddress.class.equals(type)){
                
                           return com.amazon.ec2.DisassociateAddress.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DisassociateAddressResponse.class.equals(type)){
                
                           return com.amazon.ec2.DisassociateAddressResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ActivateLicense.class.equals(type)){
                
                           return com.amazon.ec2.ActivateLicense.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ActivateLicenseResponse.class.equals(type)){
                
                           return com.amazon.ec2.ActivateLicenseResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ResetSnapshotAttribute.class.equals(type)){
                
                           return com.amazon.ec2.ResetSnapshotAttribute.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ResetSnapshotAttributeResponse.class.equals(type)){
                
                           return com.amazon.ec2.ResetSnapshotAttributeResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVpc.class.equals(type)){
                
                           return com.amazon.ec2.CreateVpc.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.CreateVpcResponse.class.equals(type)){
                
                           return com.amazon.ec2.CreateVpcResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ConfirmProductInstance.class.equals(type)){
                
                           return com.amazon.ec2.ConfirmProductInstance.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.ConfirmProductInstanceResponse.class.equals(type)){
                
                           return com.amazon.ec2.ConfirmProductInstanceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeAvailabilityZones.class.equals(type)){
                
                           return com.amazon.ec2.DescribeAvailabilityZones.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeAvailabilityZonesResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeAvailabilityZonesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeReservedInstancesOfferings.class.equals(type)){
                
                           return com.amazon.ec2.DescribeReservedInstancesOfferings.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.amazon.ec2.DescribeReservedInstancesOfferingsResponse.class.equals(type)){
                
                           return com.amazon.ec2.DescribeReservedInstancesOfferingsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    