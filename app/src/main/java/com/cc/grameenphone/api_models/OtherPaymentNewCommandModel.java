package com.cc.grameenphone.api_models;

import java.io.Serializable;

/**
 * Created by aditlal on 21/12/15.
 */
public class OtherPaymentNewCommandModel implements Serializable {
    private String ISASSOCREQ;

    private String SURCHARGE;

    private String AMOUNT;

    private String TYPE;

    public String getISASSOCREQ() {
        return ISASSOCREQ;
    }

    public void setISASSOCREQ(String ISASSOCREQ) {
        this.ISASSOCREQ = ISASSOCREQ;
    }

    public String getSURCHARGE() {
        return SURCHARGE;
    }

    public void setSURCHARGE(String SURCHARGE) {
        this.SURCHARGE = SURCHARGE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public String toString() {
        return "ClassPojo [ISASSOCREQ = " + ISASSOCREQ + ", SURCHARGE = " + SURCHARGE + ", AMOUNT = " + AMOUNT + ", TYPE = " + TYPE + "]";
    }
}
