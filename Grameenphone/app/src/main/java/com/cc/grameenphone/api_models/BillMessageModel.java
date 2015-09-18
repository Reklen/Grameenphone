package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rajkiran on 17/09/15.
 */
public class BillMessageModel {
    @SerializedName("COMPANY")
    private List<BillsCompanyListModel> comapny;

    private String BILLCOUNT;

    public List<BillsCompanyListModel> getComapny()
    {
        return comapny;
    }

    public void setComapny(List<BillsCompanyListModel> comapny)
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
