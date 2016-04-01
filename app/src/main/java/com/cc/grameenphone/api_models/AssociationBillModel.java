package com.cc.grameenphone.api_models;

/**
 * Created by aditlal on 19/09/15.
 */
public class AssociationBillModel {

    private String ACCNUM;

    private String COMPCODE;

    private Boolean isSelected =false;

    public String getACCNUM() {
        return ACCNUM;
    }

    public void setACCNUM(String ACCNUM) {
        this.ACCNUM = ACCNUM;
    }

    public String getCOMPCODE() {
        return COMPCODE;
    }

    public void setCOMPCODE(String COMPCODE) {
        this.COMPCODE = COMPCODE;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "ClassPojo [ACCNUM = " + ACCNUM + ", COMPCODE = " + COMPCODE + "]";
    }
}
