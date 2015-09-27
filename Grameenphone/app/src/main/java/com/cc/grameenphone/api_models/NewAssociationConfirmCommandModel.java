package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 25/09/15.
 */
public class NewAssociationConfirmCommandModel {
    private String MESSAGE;

    private String TXNID;

    private String INTERVAL;

    private String MSISDN;

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

    public String getTXNID ()
    {
        return TXNID;
    }

    public void setTXNID (String TXNID)
    {
        this.TXNID = TXNID;
    }

    public String getINTERVAL ()
    {
        return INTERVAL;
    }

    public void setINTERVAL (String INTERVAL)
    {
        this.INTERVAL = INTERVAL;
    }

    public String getMSISDN ()
    {
        return MSISDN;
    }

    public void setMSISDN (String MSISDN)
    {
        this.MSISDN = MSISDN;
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
        return "ClassPojo [MESSAGE = "+MESSAGE+", TXNID = "+TXNID+", INTERVAL = "+INTERVAL+", MSISDN = "+MSISDN+", TXNSTATUS = "+TXNSTATUS+", TYPE = "+TYPE+"]";
    }
}
