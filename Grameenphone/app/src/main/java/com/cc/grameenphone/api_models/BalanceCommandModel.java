package com.cc.grameenphone.api_models;


/**
 * Created by aditlal on 16/09/15.
 */
public class BalanceCommandModel {

    private String BALANCE;

    private String MESSAGE;

    private String TXNID;

    private String TRID;

    private String TXNSTATUS;

    private String TYPE;

    private String FRBALANCE;

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
    }

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

    public String getTRID() {
        return TRID;
    }

    public void setTRID(String TRID) {
        this.TRID = TRID;
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

    public String getFRBALANCE() {
        return FRBALANCE;
    }

    public void setFRBALANCE(String FRBALANCE) {
        this.FRBALANCE = FRBALANCE;
    }

    @Override
    public String toString() {
        return "ClassPojo [BALANCE = " + BALANCE + ", MESSAGE = " + MESSAGE + ", TXNID = " + TXNID + ", TRID = " + TRID + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + ", FRBALANCE = " + FRBALANCE + "]";
    }


}
