package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 15/09/15.
 */
public class MSISDNCommandModel {

    private String AUTHTOKEN;

    private String TXNID;

    private String TXNSTATUS;

    private String TYPE;

    private String OTP;

    public String getAUTHTOKEN() {
        return AUTHTOKEN;
    }

    public void setAUTHTOKEN(String AUTHTOKEN) {
        this.AUTHTOKEN = AUTHTOKEN;
    }

    public String getTXNID() {
        return TXNID;
    }

    public void setTXNID(String TXNID) {
        this.TXNID = TXNID;
    }

    public String getTXNSTATUS() {
        return TXNSTATUS;
    }

    public void setTXNSTATUS(String TXNSTATUS) {
        this.TXNSTATUS = TXNSTATUS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @Override
    public String toString() {
        return "ClassPojo [AUTHTOKEN = " + AUTHTOKEN + ", TXNID = " + TXNID + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + ", OTP = " + OTP + "]";
    }
}
