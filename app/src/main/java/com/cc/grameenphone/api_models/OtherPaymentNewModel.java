package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aditlal on 21/12/15.
 */
public class OtherPaymentNewModel implements Serializable {

    @SerializedName("COMMAND")
    private OtherPaymentNewCommandModel command;


    public OtherPaymentNewCommandModel getCommand() {
        return command;
    }

    public void setCommand(OtherPaymentNewCommandModel command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + command + "]";
    }
}