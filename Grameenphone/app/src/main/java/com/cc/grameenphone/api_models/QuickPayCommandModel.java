package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class QuickPayCommandModel {
    @SerializedName("MESSAGE")
    private QuickPayMessageModel MESSAGE;

    private String TXNSTATUS;

    private String TYPE;

    public QuickPayMessageModel getMESSAGE ()
    {
        return MESSAGE;
    }

    public void setMESSAGE (QuickPayMessageModel MESSAGE)
    {
        this.MESSAGE = MESSAGE;
    }

    public String getTXNSTATUS ()
    {
        return TXNSTATUS;
    }

    public void setTXNSTATUS (String TXNSTATUS)
    {
        this.TXNSTATUS = TXNSTATUS;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [MESSAGE = "+MESSAGE+", TXNSTATUS = "+TXNSTATUS+", TYPE = "+TYPE+"]";
    }
}
