package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 16/09/15.
 */
public class RechargeModel {
    @SerializedName("COMMAND")
    private RechargeCommandModel rechargeCommandModel;

    public RechargeCommandModel getCOMMAND() {
        return rechargeCommandModel;
    }

    public void setCOMMAND(RechargeCommandModel rechargeCommandModel) {
        this.rechargeCommandModel = rechargeCommandModel;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + rechargeCommandModel + "]";
    }
}
