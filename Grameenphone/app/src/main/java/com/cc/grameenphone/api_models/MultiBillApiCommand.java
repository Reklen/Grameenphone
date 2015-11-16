package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 09/11/15.
 */
public class MultiBillApiCommand {

    @SerializedName("BILLDET")
    private List<MultiBillApiBillDetail> billDetailList;

    private String TXNSTATUS;

    private String TYPE;

    public List<MultiBillApiBillDetail> getBILLDET() {
        return billDetailList;
    }

    public void setBILLDET(List<MultiBillApiBillDetail> billDetailList) {
        this.billDetailList = billDetailList;
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
        return "ClassPojo [BILLDET = " + billDetailList + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
