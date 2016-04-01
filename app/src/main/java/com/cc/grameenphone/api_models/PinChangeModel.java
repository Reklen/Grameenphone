package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 16/09/15.
 */
public class PinChangeModel {
    @SerializedName("COMMAND")
    private PinChangeCommandModel command;

    public PinChangeCommandModel getCommand() {
        return command;
    }

    public void setCommand(PinChangeCommandModel command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ClassPojo [command = " + command + "]";
    }
}
