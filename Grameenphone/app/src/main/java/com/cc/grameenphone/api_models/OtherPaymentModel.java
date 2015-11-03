package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 21/09/15.
 */
public class OtherPaymentModel {
    @SerializedName("COMMAND")
    private OtherPaymentCommandModel COMMAND;

    public OtherPaymentCommandModel getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(OtherPaymentCommandModel COMMAND) {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}
