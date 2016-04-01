package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 23/09/15.
 */
public class BillPaymentModel {

    @SerializedName("COMMAND")
    private BillPaymentCommand COMMAND;

    public BillPaymentCommand getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(BillPaymentCommand COMMAND) {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}

