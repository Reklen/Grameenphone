package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 15/09/15.
 */
public class LoginCommand {
    private String MESSAGE;


    private String AUTHTOKEN;

    private String TXNSTATUS;

    private String TYPE;

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getAUTHTOKEN() {
        return AUTHTOKEN;
    }

    public void setAUTHTOKEN(String AUTHTOKEN) {
        this.AUTHTOKEN = AUTHTOKEN;
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

    @Override
    public String toString() {
        return "ClassPojo [MESSAGE = " + MESSAGE + ", AUTHTOKEN = " + AUTHTOKEN + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
