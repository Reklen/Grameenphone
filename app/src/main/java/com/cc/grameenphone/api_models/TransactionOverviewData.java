package com.cc.grameenphone.api_models;

import java.io.Serializable;

/**
 * Created by aditlal on 20/09/15.
 */
public class TransactionOverviewData implements Serializable {

    private String TXNSC;

    private String TXNTYPE;

    private String TXNID;

    private String SERVICE;

    private String FROMTO;

    private String TXNDATE;

    private String TXNAMT;

    private String TXNSTATUS;

    public String getTXNSC ()
    {
        return TXNSC;
    }

    public void setTXNSC (String TXNSC)
    {
        this.TXNSC = TXNSC;
    }

    public String getTXNTYPE ()
    {
        return TXNTYPE;
    }

    public void setTXNTYPE (String TXNTYPE)
    {
        this.TXNTYPE = TXNTYPE;
    }

    public String getTXNID ()
    {
        return TXNID;
    }

    public void setTXNID (String TXNID)
    {
        this.TXNID = TXNID;
    }

    public String getSERVICE ()
    {
        return SERVICE;
    }

    public void setSERVICE (String SERVICE)
    {
        this.SERVICE = SERVICE;
    }

    public String getFROMTO ()
    {
        return FROMTO;
    }

    public void setFROMTO (String FROMTO)
    {
        this.FROMTO = FROMTO;
    }

    public String getTXNDATE ()
    {
        return TXNDATE;
    }

    public void setTXNDATE (String TXNDATE)
    {
        this.TXNDATE = TXNDATE;
    }

    public String getTXNAMT ()
    {
        return TXNAMT;
    }

    public void setTXNAMT (String TXNAMT)
    {
        this.TXNAMT = TXNAMT;
    }

    public String getTXNSTATUS ()
    {
        return TXNSTATUS;
    }

    public void setTXNSTATUS (String TXNSTATUS)
    {
        this.TXNSTATUS = TXNSTATUS;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [TXNSC = "+TXNSC+", TXNTYPE = "+TXNTYPE+", TXNID = "+TXNID+", SERVICE = "+SERVICE+", FROMTO = "+FROMTO+", TXNDATE = "+TXNDATE+", TXNAMT = "+TXNAMT+", TXNSTATUS = "+TXNSTATUS+"]";
    }


}
