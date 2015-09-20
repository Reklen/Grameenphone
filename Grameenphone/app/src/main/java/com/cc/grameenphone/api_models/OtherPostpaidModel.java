package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class OtherPostpaidModel {
    @SerializedName("COMMAND")
    private OtherPostpaidCommandModel command;

    public OtherPostpaidCommandModel getCommand()
    {
        return command;
    }

    public void setCommand(OtherPostpaidCommandModel command)
    {
        this.command = command;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [command = "+ command +"]";
    }

}
