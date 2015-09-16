package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 15/09/15.
 */
public class LoginModel {

    @SerializedName("COMMAND")
    private LoginCommand command;

    public LoginCommand getCommand() {
        return command;
    }

    public void setCommand(LoginCommand command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ClassPojo [command = " + command + "]";
    }
}
