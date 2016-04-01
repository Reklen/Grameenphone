package com.cc.grameenphone.viewmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aditlal on 16/11/15.
 */
public class MultiCancelAssociationListModel {
    @SerializedName("PREF1")
    String pRef;

    @SerializedName("BILLCCODE")
    String billCancelCode;

    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getpRef() {
        return pRef;
    }

    public void setpRef(String pRef) {
        this.pRef = pRef;
    }

    public String getBillCancelCode() {
        return billCancelCode;
    }

    public void setBillCancelCode(String billCancelCode) {
        this.billCancelCode = billCancelCode;
    }
}
