package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class ReferFriendModel {
    @SerializedName("COMMAND")
    private ReferFriendCommandModel command;

    public ReferFriendCommandModel getCommand()
    {
        return command;
    }

    public void setCommand(ReferFriendCommandModel command)
    {
        this.command = command;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [command = "+ command +"]";
    }
}
