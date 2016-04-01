package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 18/09/15.
 */
public class AddAssociationModel {
    private AddAssociationCommandModel COMMAND;

    public AddAssociationCommandModel getCOMMAND() {
        return COMMAND;
    }

    public void setCOMMAND(AddAssociationCommandModel COMMAND) {
        this.COMMAND = COMMAND;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + COMMAND + "]";
    }
}
