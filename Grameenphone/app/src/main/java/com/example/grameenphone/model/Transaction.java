package com.example.grameenphone.model;

/**
 * Created by rajkiran on 10/09/15.
 */
public class Transaction {
    private String serviceType;
    private String  serviceAmt;

    public void setServiceAmt(String serviceAmt) {
        this.serviceAmt = serviceAmt;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceAmt() {
        return serviceAmt;
    }

    public String getServiceType() {
        return serviceType;
    }
}
