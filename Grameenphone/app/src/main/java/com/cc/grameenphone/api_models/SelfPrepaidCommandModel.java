package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 18/09/15.
 */
public class SelfPrepaidCommandModel {
    private String MESSAGE;

    private String TXNID;

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

    public String getTXNID ()
    {
        return TXNID;
    }

    public void setTXNID (String TXNID)
    {
        this.TXNID = TXNID;
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
        return "ClassPojo [MESSAGE = "+MESSAGE+", TXNID = "+TXNID+", TRID = "+TRID+", TXNSTATUS = "+TXNSTATUS+", TYPE = "+TYPE+"]";
    }

}
