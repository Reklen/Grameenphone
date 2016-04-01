package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 17/09/15.
 */
public class ProfileUpdateModel {
    @SerializedName("COMMAND")
    private ProfileUpdateCommandModel command;

    public ProfileUpdateCommandModel getCommand() {
        return command;
    }

    public void setCommand(ProfileUpdateCommandModel command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ClassPojo [command = " + command + "]";
    }
}
