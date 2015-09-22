package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rajkiran on 21/09/15.
 */
public class OtherPaymentCommandModel {
    @SerializedName("NOOFCOM")
    private String NOOFCOM;

    private List<OtherPaymentCompanyModel> COMPANYDET;

    private String TXNSTATUS;

    private String TYPE;

    public String getNOOFCOM ()
    {
        return NOOFCOM;
    }

    public void setNOOFCOM (String NOOFCOM)
    {
        this.NOOFCOM = NOOFCOM;
    }

    public List<OtherPaymentCompanyModel> getCOMPANYDET ()
    {
        return COMPANYDET;
    }

    public void setCOMPANYDET (List<OtherPaymentCompanyModel> COMPANYDET)
    {
        this.COMPANYDET = COMPANYDET;
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
        return "ClassPojo [NOOFCOM = "+NOOFCOM+", COMPANYDET = "+COMPANYDET+", TXNSTATUS = "+TXNSTATUS+", TYPE = "+TYPE+"]";
    }
}
