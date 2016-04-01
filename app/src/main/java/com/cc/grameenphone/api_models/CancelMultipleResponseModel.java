package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 16/11/15.
 */
public class CancelMultipleResponseModel {
    @SerializedName("COMMAND")
    private CancelMultipleCommand command;

    public CancelMultipleCommand getCommand() {
        return command;
    }

    public void setCommand(CancelMultipleCommand command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + command + "]";
    }
}
