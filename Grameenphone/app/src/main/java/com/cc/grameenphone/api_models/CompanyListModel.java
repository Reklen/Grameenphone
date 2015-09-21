package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 19/09/15.
 */
public class CompanyListModel {

    @SerializedName("COMMAND")
    private CompanyListCommand model;

    public CompanyListCommand getCOMMAND() {
        return model;
    }

    public void setCOMMAND(CompanyListCommand model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + model + "]";
    }
}
