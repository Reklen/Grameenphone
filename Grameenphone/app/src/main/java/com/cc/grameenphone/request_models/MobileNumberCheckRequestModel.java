package com.cc.grameenphone.request_models;

/**
 * Created by aditlal on 15/09/15.
 */
public class MobileNumberCheckRequestModel {

    String requestObject;

    public MobileNumberCheckRequestModel(String requestObject) {
        this.requestObject = requestObject;
    }

    @Override
    public String toString() {
        return requestObject;
    }
}
