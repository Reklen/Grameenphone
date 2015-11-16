package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 09/11/15.
 */
public class MultiBillApiModel
{
    @SerializedName("COMMAND")
    private MultiBillApiCommand command;

    public MultiBillApiCommand getCOMMAND ()
    {
        return command;
    }

    public void setCOMMAND (MultiBillApiCommand command)
    {
        this.command = command;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COMMAND = "+command+"]";
    }
}
