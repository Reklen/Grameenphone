package com.cc.grameenphone.api_models;

/**
 * Created by rajkiran on 17/09/15.
 */
public class UserBillsModel {
    private String DUEDATE;

    private String ACCOUNTNUM;

    private String BILLNUM;

    private String COMPANYNAME;

    private String CATEGORYNAME;

    private String BPROVIDER;

    private String AMOUNT;

    private boolean isSelected;

    public String getDUEDATE() {
        return DUEDATE;
    }

    public void setDUEDATE(String DUEDATE) {
        this.DUEDATE = DUEDATE;
    }

    public String getACCOUNTNUM() {
        return ACCOUNTNUM;
    }

    public void setACCOUNTNUM(String ACCOUNTNUM) {
        this.ACCOUNTNUM = ACCOUNTNUM;
    }

    public String getBILLNUM() {
        return BILLNUM;
    }

    public void setBILLNUM(String BILLNUM) {
        this.BILLNUM = BILLNUM;
    }

    public String getCOMPANYNAME() {
        return COMPANYNAME;
    }

    public void setCOMPANYNAME(String COMPANYNAME) {
        this.COMPANYNAME = COMPANYNAME;
    }

    public String getCATEGORYNAME() {
        return CATEGORYNAME;
    }

    public void setCATEGORYNAME(String CATEGORYNAME) {
        this.CATEGORYNAME = CATEGORYNAME;
    }

    public String getBPROVIDER() {
        return BPROVIDER;
    }

    public void setBPROVIDER(String BPROVIDER) {
        this.BPROVIDER = BPROVIDER;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "ClassPojo [DUEDATE = " + DUEDATE + ", ACCOUNTNUM = " + ACCOUNTNUM + ", BILLNUM = " + BILLNUM + ", COMPANYNAME = " + COMPANYNAME + ", CATEGORYNAME = " + CATEGORYNAME + ", BPROVIDER = " + BPROVIDER + ", AMOUNT = " + AMOUNT + "]";
    }
}
