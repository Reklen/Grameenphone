package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 19/11/15.
 */
public class NotificationModel {
    @SerializedName("COMMAND")
    private NotificationCommand command;

    public NotificationCommand getCommand() {
        return command;
    }

    public void setCommand(NotificationCommand command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + command + "]";
    }
}
