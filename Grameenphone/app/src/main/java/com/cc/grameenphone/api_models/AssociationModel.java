package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 19/09/15.
 */
public class AssociationModel {

    @SerializedName("COMMAND")
    private AssociationCommandModel commandModel;


    public AssociationCommandModel getCommandModel() {
        return commandModel;
    }

    public void setCommandModel(AssociationCommandModel commandModel) {
        this.commandModel = commandModel;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + commandModel + "]";
    }
}
