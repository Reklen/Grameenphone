package com.cc.grameenphone.api_models;

import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;

/**
 * Created by aditlal on 19/11/15.
 */
public class NotificationMessageModel implements Rush {
    private String NOTDATA;

    private String NOTCODE;

    private String NOTHEAD;

    private String NOTENDDAT;

    private String NOTSTDAT;

    private String NOTID;

    private boolean isRead;

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

    public String getNOTID() {
        return NOTID;
    }

    public void setNOTID(String NOTID) {
        this.NOTID = NOTID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "ClassPojo [NOTDATA = " + NOTDATA + ", NOTCODE = " + NOTCODE + ", NOTID = " + NOTID + ", NOTHEAD = " + NOTHEAD + ", NOTENDDAT = " + NOTENDDAT + ", NOTSTDAT = " + NOTSTDAT + "]";
    }

    @Override
    public void save() {
        RushCore.getInstance().save(this);
    }

    @Override
    public void save(RushCallback callback) {
        RushCore.getInstance().save(this, callback);
    }

    @Override
    public void delete() {
        RushCore.getInstance().delete(this);
    }

    @Override
    public void delete(RushCallback callback) {
        RushCore.getInstance().delete(this, callback);
    }

    @Override
    public String getId() {
        return RushCore.getInstance().getId(this);
    }
}