package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 19/11/15.
 */
public class NotificationMessageModel {
    private String NOTDATA;

    private String NOTCODE;

    private String NOTHEAD;

    private String NOTENDDAT;

    private String NOTSTDAT;

    public String getNOTDATA() {
        return NOTDATA;
    }

    public void setNOTDATA(String NOTDATA) {
        this.NOTDATA = NOTDATA;
    }

    public String getNOTCODE() {
        return NOTCODE;
    }

    public void setNOTCODE(String NOTCODE) {
        this.NOTCODE = NOTCODE;
    }

    public String getNOTHEAD() {
        return NOTHEAD;
    }

    public void setNOTHEAD(String NOTHEAD) {
        this.NOTHEAD = NOTHEAD;
    }

    public String getNOTENDDAT() {
        return NOTENDDAT;
    }

    public void setNOTENDDAT(String NOTENDDAT) {
        this.NOTENDDAT = NOTENDDAT;
    }

    public String getNOTSTDAT() {
        return NOTSTDAT;
    }

    public void setNOTSTDAT(String NOTSTDAT) {
        this.NOTSTDAT = NOTSTDAT;
    }

    @Override
    public String toString() {
        return "ClassPojo [NOTDATA = " + NOTDATA + ", NOTCODE = " + NOTCODE + ", NOTHEAD = " + NOTHEAD + ", NOTENDDAT = " + NOTENDDAT + ", NOTSTDAT = " + NOTSTDAT + "]";
    }
}