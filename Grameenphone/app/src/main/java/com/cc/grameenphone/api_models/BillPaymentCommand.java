package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 23/09/15.
 */
public class BillPaymentCommand {
    private String MESSAGE;

    private String BDUDATE;

    private String TXNID;

    private String TRID;

    private String BILLCCODE;

    private String BILLNO;

    private String AMOUNT;

    private String TXNSTATUS;

    private String TYPE;

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    public String getBDUDATE() {
        return BDUDATE;
    }

    public void setBDUDATE(String BDUDATE) {
        this.BDUDATE = BDUDATE;
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

    public String getBILLCCODE() {
        return BILLCCODE;
    }

    public void setBILLCCODE(String BILLCCODE) {
        this.BILLCCODE = BILLCCODE;
    }

    public String getBILLNO() {
        return BILLNO;
    }

    public void setBILLNO(String BILLNO) {
        this.BILLNO = BILLNO;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
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
        return "ClassPojo [MESSAGE = " + MESSAGE + ", BDUDATE = " + BDUDATE + ", TXNID = " + TXNID + ", TRID = " + TRID + ", BILLCCODE = " + BILLCCODE + ", BILLNO = " + BILLNO + ", AMOUNT = " + AMOUNT + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
