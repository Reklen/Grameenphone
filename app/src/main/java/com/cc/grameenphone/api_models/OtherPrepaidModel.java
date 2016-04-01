package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class OtherPrepaidModel {
    @SerializedName("COMMAND")
    private OtherPrepaidCommandModel COMMAND;

    public OtherPrepaidCommandModel getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(OtherPrepaidCommandModel COMMAND) {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}
