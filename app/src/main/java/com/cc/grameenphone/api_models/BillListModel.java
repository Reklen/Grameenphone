package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 17/09/15.
 */
public class BillListModel {
    @SerializedName("COMMAND")
    private BillsCommandModel COMMAND;

    public BillsCommandModel getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(BillsCommandModel COMMAND) {
        this.COMMAND = COMMAND;
    }


    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}
