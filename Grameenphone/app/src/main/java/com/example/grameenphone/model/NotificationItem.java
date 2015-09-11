package com.example.grameenphone.model;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationItem {
    private String ExclusiveText;
    private String todayText;
    private String fullTalkTimeText;
    private String fullStatementText;

    public NotificationItem(String exclusiveText, String todayText, String fullStatementText, String fullTalkTimeText) {
        ExclusiveText = exclusiveText;
        this.todayText = todayText;
        this.fullStatementText = fullStatementText;
        this.fullTalkTimeText = fullTalkTimeText;
    }

    public String getExclusiveText() {
        return ExclusiveText;
    }

    public void setExclusiveText(String exclusiveText) {
        ExclusiveText = exclusiveText;
    }

    public String getTodayText() {
        return todayText;
    }

    public void setTodayText(String todayText) {
        this.todayText = todayText;
    }

    public String getFullTalkTimeText() {
        return fullTalkTimeText;
    }

    public void setFullTalkTimeText(String fullTalkTimeText) {
        this.fullTalkTimeText = fullTalkTimeText;
    }

    public String getFullStatementText() {
        return fullStatementText;
    }

    public void setFullStatementText(String fullStatementText) {
        this.fullStatementText = fullStatementText;
    }
}
