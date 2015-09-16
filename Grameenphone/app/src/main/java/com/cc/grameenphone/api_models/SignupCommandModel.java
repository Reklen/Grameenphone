package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 15/09/15.
 */
public class SignupCommandModel {
    private String MESSAGE;

    private String TXNID;

    private String TXNSTATUS;

    private String TYPE;

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
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

    @Override
    public String toString() {
        return "ClassPojo [MESSAGE = " + MESSAGE + ", TXNID = " + TXNID + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }

    /*
    {"COMMAND": {
    "MESSAGE": "Your GP MobiCash Account is being created. You will receive confirmation message shortly. If not, please call 1200 for help.",
    "TXNID": "CUSTREG150916.1121.C00001",
    "TXNSTATUS": "200",
    "TYPE": "CUSTREG"
}}

     */
}
