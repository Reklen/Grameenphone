package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 20/09/15.
 */
public class TransactionOverviewModel {

    @SerializedName("COMMAND")
    private TransactionOverviewCommand command;

    public TransactionOverviewCommand getCOMMAND() {
        return command;
    }

    public void setCOMMAND(TransactionOverviewCommand COMMAND) {
        this.command = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + command.toString() + "]";
    }
}
