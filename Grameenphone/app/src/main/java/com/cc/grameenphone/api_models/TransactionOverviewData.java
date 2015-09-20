package com.cc.grameenphone.api_models;

import java.io.Serializable;

/**
 * Created by aditlal on 20/09/15.
 */
public class TransactionOverviewData implements Serializable {


    private String TXNTYPE;

    private String TXNID;

    private String SERVICE;

    private String FROMTO;

    private String TXNAMT;

    private String TXNSTATUS;

    public String getTXNTYPE() {
        return TXNTYPE;
    }

    public void setTXNTYPE(String TXNTYPE) {
        this.TXNTYPE = TXNTYPE;
    }

    public String getTXNID() {
        return TXNID;
    }

    public void setTXNID(String TXNID) {
        this.TXNID = TXNID;
    }

    public String getSERVICE() {
        return SERVICE;
    }

    public void setSERVICE(String SERVICE) {
        this.SERVICE = SERVICE;
    }

    public String getFROMTO() {
        return FROMTO;
    }

    public void setFROMTO(String FROMTO) {
        this.FROMTO = FROMTO;
    }

    public String getTXNAMT() {
        return TXNAMT;
    }

    public void setTXNAMT(String TXNAMT) {
        this.TXNAMT = TXNAMT;
    }

    public String getTXNSTATUS() {
        return TXNSTATUS;
    }

    public void setTXNSTATUS(String TXNSTATUS) {
        this.TXNSTATUS = TXNSTATUS;
    }

    @Override
    public String toString() {
        return "ClassPojo [TXNTYPE = " + TXNTYPE + ", TXNID = " + TXNID + ", SERVICE = " + SERVICE + ", FROMTO = " + FROMTO + ", TXNAMT = " + TXNAMT + ", TXNSTATUS = " + TXNSTATUS + "]";
    }


}
