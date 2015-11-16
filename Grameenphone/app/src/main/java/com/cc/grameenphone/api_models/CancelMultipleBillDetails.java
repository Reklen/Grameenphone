package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 16/11/15.
 */
public class CancelMultipleBillDetails {

    private String MESSAGE;

    private String TXNID;

    private String BILLANO;

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

    public String getBILLANO() {
        return BILLANO;
    }

    public void setBILLANO(String BILLANO) {
        this.BILLANO = BILLANO;
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
        return "ClassPojo [MESSAGE = " + MESSAGE + ", TXNID = " + TXNID + ", BILLANO = " + BILLANO + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
