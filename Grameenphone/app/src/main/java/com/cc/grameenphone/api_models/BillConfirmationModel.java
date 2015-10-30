package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 21/09/15.
 */
public class BillConfirmationModel {
    @SerializedName("COMMAND")
    private BillConfirmationCommandModel COMMAND;

    public BillConfirmationCommandModel getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(BillConfirmationCommandModel COMMAND) {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}
