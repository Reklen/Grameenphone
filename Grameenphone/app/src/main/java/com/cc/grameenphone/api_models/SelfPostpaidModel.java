package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 18/09/15.
 */
public class SelfPostpaidModel {
    private SelfPostpaidCommandModel COMMAND;

    public SelfPostpaidCommandModel getCOMMAND ()
    {
        return COMMAND;
    }

    public void setCOMMAND (SelfPostpaidCommandModel COMMAND)
    {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [COMMAND = "+COMMAND+"]";
    }
}
