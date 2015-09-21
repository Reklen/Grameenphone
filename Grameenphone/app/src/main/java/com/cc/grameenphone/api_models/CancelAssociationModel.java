package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 19/09/15.
 */
public class CancelAssociationModel {
    @SerializedName("COMMAND")
    private CancelAssociationCommandModel model;

    public CancelAssociationCommandModel getCommand() {
        return model;
    }

    public void setCommand(CancelAssociationCommandModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + model + "]";
    }
}

