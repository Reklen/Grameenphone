package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class AddAssociationCommandModel {
    @SerializedName("COMPANYDET")
    private String NOOFCOM;

    private AddAssociationComapanyModel[] COMPANYDET;

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

    public AddAssociationComapanyModel[] getCOMPANYDET ()
    {
        return COMPANYDET;
    }

    public void setCOMPANYDET (AddAssociationComapanyModel[] COMPANYDET)
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
