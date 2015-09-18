package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 17/09/15.
 */
public class BillsCommandModel {

    @SerializedName("MESSAGE")
    private BillMessageModel message;

    private String TXNSTATUS;

    private String TYPE;

    public BillMessageModel getMessage()
    {
        return message;
    }

    public void setMessage(BillMessageModel message)
    {
        this.message = message;
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
        return "ClassPojo [message = "+ message +", TXNSTATUS = "+TXNSTATUS+", TYPE = "+TYPE+"]";
    }
}
