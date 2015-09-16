package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 16/09/15.
 */
public class BalanceEnquiryModel {

    @SerializedName("COMMAND")
    private BalanceCommandModel balanceCommandModel;

    public BalanceCommandModel getCOMMAND() {
        return balanceCommandModel;
    }

    public void setCOMMAND(BalanceCommandModel balanceCommandModel) {
        this.balanceCommandModel = balanceCommandModel;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + balanceCommandModel + "]";
    }
}

