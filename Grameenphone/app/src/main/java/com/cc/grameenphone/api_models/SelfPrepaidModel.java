package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 18/09/15.
 */
public class SelfPrepaidModel {
    @SerializedName("COMMAND")
    private SelfPrepaidCommandModel COMMAND;

    public SelfPrepaidCommandModel getCOMMAND ()
    {
        return COMMAND;
    }

    public void setCOMMAND (SelfPrepaidCommandModel COMMAND)
    {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COMMAND = "+COMMAND+"]";
    }
}
