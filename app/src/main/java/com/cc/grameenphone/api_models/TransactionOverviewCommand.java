package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 20/09/15.
 */
public class TransactionOverviewCommand {
    private String MESSAGE;

    private String TXNID;

    private String TRID;

    private String NOOFTXN;

    @SerializedName("DATA")
    private List<TransactionOverviewData> DATA;

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

    public String getTRID() {
        return TRID;
    }

    public void setTRID(String TRID) {
        this.TRID = TRID;
    }

    public String getNOOFTXN() {
        return NOOFTXN;
    }

    public void setNOOFTXN(String NOOFTXN) {
        this.NOOFTXN = NOOFTXN;
    }

    public List<TransactionOverviewData> getDATA() {
        return DATA;
    }

    public void setDATA(List<TransactionOverviewData> DATA) {
        this.DATA = DATA;
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
        return "ClassPojo [MESSAGE = " + MESSAGE + ", TXNID = " + TXNID + ", TRID = " + TRID + ", NOOFTXN = " + NOOFTXN + ", DATA = " + DATA + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }

}
