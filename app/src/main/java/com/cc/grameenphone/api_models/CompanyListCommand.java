package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 19/09/15.
 */
public class CompanyListCommand {

    private String NOOFCOM;

    @SerializedName("COMPANYDET")
    private List<CompanyDetailsModel> companyDetailsModels;

    private String TXNSTATUS;

    private String TYPE;

    public String getNOOFCOM() {
        return NOOFCOM;
    }

    public void setNOOFCOM(String NOOFCOM) {
        this.NOOFCOM = NOOFCOM;
    }

    public List<CompanyDetailsModel> getCompanyDetailsModels() {
        return companyDetailsModels;
    }

    public void setCompanyDetailsModels(List<CompanyDetailsModel> companyDetailsModels) {
        this.companyDetailsModels = companyDetailsModels;
    }

    public String getTXNSTATUS() {
        return TXNSTATUS;
    }

    public void setTXNSTATUS(String TXNSTATUS) {
        this.TXNSTATUS = TXNSTATUS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public String toString() {
        return "ClassPojo [NOOFCOM = " + NOOFCOM + ", COMPANYDET = " + companyDetailsModels + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
