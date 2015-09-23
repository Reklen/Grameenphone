package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 23/09/15.
 */
public class ProfileModel {
    @SerializedName("COMMAND")
    private ProfileCommandModel COMMAND;

    public ProfileCommandModel getCOMMAND ()
    {
        return COMMAND;
    }

    public void setCOMMAND (ProfileCommandModel COMMAND)
    {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COMMAND = "+COMMAND+"]";
    }
}
