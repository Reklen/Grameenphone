package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 16/11/15.
 */
public class CancelMultipleCommand {
    private String content;

    @SerializedName("BILLDET")
    private List<CancelMultipleBillDetails> billDetailsList;

    private String TXNSTATUS;

    private String TYPE;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CancelMultipleBillDetails> getBillDetailsList() {
        return billDetailsList;
    }

    public void setBillDetailsList(List<CancelMultipleBillDetails> billDetailsList) {
        this.billDetailsList = billDetailsList;
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
        return "ClassPojo [content = " + content + ", BILLDET = " + billDetailsList + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
