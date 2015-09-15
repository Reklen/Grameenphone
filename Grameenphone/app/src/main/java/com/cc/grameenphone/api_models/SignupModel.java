package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 15/09/15.
 */
public class SignupModel {
    @SerializedName("COMMAND")
    private SignupCommandModel command;

    public SignupCommandModel getCommand()
    {
        return command;
    }

    public void setCommand(SignupCommandModel command)
    {
        this.command = command;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [command = "+ command +"]";
    }
}
