package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 17/09/15.
 */
public class BillMessageModel {
    private BillsCompanyListModel[] comapny;

    private String BILLCOUNT;

    public BillsCompanyListModel[] getComapny()
    {
        return comapny;
    }

    public void setComapny(BillsCompanyListModel[] comapny)
    {
        this.comapny = comapny;
    }

    public String getBILLCOUNT ()
    {
        return BILLCOUNT;
    }

    public void setBILLCOUNT (String BILLCOUNT)
    {
        this.BILLCOUNT = BILLCOUNT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [comapny = "+ comapny +", BILLCOUNT = "+BILLCOUNT+"]";
    }
}
