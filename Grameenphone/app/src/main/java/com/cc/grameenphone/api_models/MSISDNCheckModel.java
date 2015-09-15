package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 15/09/15.
 */
public class MSISDNCheckModel {

    @SerializedName("command")
    private MSISDNCommandModel command;

    public MSISDNCommandModel getCOMMAND() {
        return command;
    }

    public void setCOMMAND(MSISDNCommandModel COMMAND) {
        this.command = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + command + "]";
    }
}
