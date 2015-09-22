package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 22/09/15.
 */
public class QuickPayConfirmModel {
    @SerializedName("COMMAND")
    private QuickPayConfirmCommandModel COMMAND;

    public QuickPayConfirmCommandModel getCOMMAND ()
    {
        return COMMAND;
    }

    public void setCOMMAND (QuickPayConfirmCommandModel COMMAND)
    {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COMMAND = "+COMMAND+"]";
    }
}
