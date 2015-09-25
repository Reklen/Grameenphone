package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkiran on 25/09/15.
 */
public class NewAssociationConfirmModel {
    @SerializedName("COMMAND")
    private NewAssociationConfirmCommandModel COMMAND;

    public NewAssociationConfirmCommandModel getCOMMAND ()
    {
        return COMMAND;
    }

    public void setCOMMAND (NewAssociationConfirmCommandModel COMMAND)
    {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COMMAND = "+COMMAND+"]";
    }
}
