package com.cc.grameenphone.api_models;

import java.io.Serializable;

/**
 * Created by rajkiran on 23/09/15.
 */
public class ProfileCommandModel implements Serializable {
    private String FNAME;

    private String DOB;

    private String IDNO;

    private String EMAIL;

    private String TXNSTATUS;

    private String LNAME;

    private String TYPE;

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getIDNO() {
        return IDNO;
    }

    public void setIDNO(String IDNO) {
        this.IDNO = IDNO;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getTXNSTATUS() {
        return TXNSTATUS;
    }

    public void setTXNSTATUS(String TXNSTATUS) {
        this.TXNSTATUS = TXNSTATUS;
    }

    public String getLNAME() {
        return LNAME;
    }

    public void setLNAME(String LNAME) {
        this.LNAME = LNAME;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public String toString() {
        return "ClassPojo [FNAME = " + FNAME + ", DOB = " + DOB + ", IDNO = " + IDNO + ", EMAIL = " + EMAIL + ", TXNSTATUS = " + TXNSTATUS + ", LNAME = " + LNAME + ", TYPE = " + TYPE + "]";
    }
}
