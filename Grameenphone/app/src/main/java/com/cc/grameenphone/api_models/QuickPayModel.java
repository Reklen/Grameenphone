package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class QuickPayModel {
    @SerializedName("COMMAND")
    private QuickPayCommandModel COMMAND;

    public QuickPayCommandModel getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(QuickPayCommandModel COMMAND) {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}
