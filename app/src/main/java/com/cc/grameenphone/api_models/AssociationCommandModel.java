package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aditlal on 19/09/15.
 */
public class AssociationCommandModel {

    private String NOOFCOM;

    @SerializedName("BILLDEL")
    private List<AssociationBillModel> BILLDEL;

    private String TXNSTATUS;

    private String TYPE;

    public String getNOOFCOM() {
        return NOOFCOM;
    }

    public void setNOOFCOM(String NOOFCOM) {
        this.NOOFCOM = NOOFCOM;
    }


    public List<AssociationBillModel> getBILLDEL() {
        return BILLDEL;
    }

    public void setBILLDEL(List<AssociationBillModel> BILLDEL) {
        this.BILLDEL = BILLDEL;
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
        return "ClassPojo [NOOFCOM = " + NOOFCOM + ", BILLDEL = " + BILLDEL + ", TXNSTATUS = " + TXNSTATUS + ", TYPE = " + TYPE + "]";
    }
}
