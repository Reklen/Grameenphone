package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 19/11/15.
 */
public class NotificationCommand {
    @SerializedName("MESSAGE")
    private List<NotificationMessageModel> messageModelList;

    private String TXNID;

    private String TXNSTATUS;

    private String TYPE;

    public List<NotificationMessageModel> getMessageModelList() {
        return messageModelList;
    }

    public void setMessageModelList(List<NotificationMessageModel> messageModelList) {
        this.messageModelList = messageModelList;
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
        return "ClassPojo [MESSAGE = " + messageModelList + ", TXNID = " + TXNID + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
