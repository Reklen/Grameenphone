package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 18/09/15.
 */
public class QuickPayMessageModel {
    private String DUEDATE;

    private String COMPNAME;

    private String ACCNUM;

    private String BILLNUM;

    private String AMOUNT;

    private String BPROVIDER;

    public String getDUEDATE ()
    {
        return DUEDATE;
    }

    public void setDUEDATE (String DUEDATE)
    {
        this.DUEDATE = DUEDATE;
    }

    public String getCOMPNAME ()
    {
        return COMPNAME;
    }

    public void setCOMPNAME (String COMPNAME)
    {
        this.COMPNAME = COMPNAME;
    }

    public String getACCNUM ()
    {
        return ACCNUM;
    }

    public void setACCNUM (String ACCNUM)
    {
        this.ACCNUM = ACCNUM;
    }

    public String getBILLNUM ()
    {
        return BILLNUM;
    }

    public void setBILLNUM (String BILLNUM)
    {
        this.BILLNUM = BILLNUM;
    }

    public String getAMOUNT ()
    {
        return AMOUNT;
    }

    public void setAMOUNT (String AMOUNT)
    {
        this.AMOUNT = AMOUNT;
    }

    public String getBPROVIDER ()
    {
        return BPROVIDER;
    }

    public void setBPROVIDER (String BPROVIDER)
    {
        this.BPROVIDER = BPROVIDER;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DUEDATE = "+DUEDATE+", COMPNAME = "+COMPNAME+", ACCNUM = "+ACCNUM+", BILLNUM = "+BILLNUM+", AMOUNT = "+AMOUNT+", BPROVIDER = "+BPROVIDER+"]";
    }
}
