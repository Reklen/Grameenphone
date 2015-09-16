package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 16/09/15.
 */
public class PinChangeCommandModel {
    private String MESSAGE;

    private String TRID;

    private String TXNSTATUS;

    private String TYPE;

    public String getMESSAGE ()
    {
        return MESSAGE;
    }

    public void setMESSAGE (String MESSAGE)
    {
        this.MESSAGE = MESSAGE;
    }

    public String getTRID ()
    {
        return TRID;
    }

    public void setTRID (String TRID)
    {
        this.TRID = TRID;
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
        return "ClassPojo [MESSAGE = "+MESSAGE+", TRID = "+TRID+", TXNSTATUS = "+TXNSTATUS+", TYPE = "+TYPE+"]";
    }
}
