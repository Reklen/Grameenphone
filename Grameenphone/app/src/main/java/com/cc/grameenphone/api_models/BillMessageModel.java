package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rajkiran on 17/09/15.
 */
public class BillMessageModel {
    @SerializedName("COMPANY")
    private List<UserBillsModel> comapny;

    private String BILLCOUNT;

    public List<UserBillsModel> getComapny()
    {
        return comapny;
    }

    public void setComapny(List<UserBillsModel> comapny)
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
