package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;

/**
 * Created by rajkiran on 21/09/15.
 */
public class OtherPaymentCompanyModel implements Rush, Serializable {
    @SerializedName("SURCREQ")
    private String SURCREQ;

    private String COMPNAME;

    private String CATCODE;

    private String COMPCODE;

    public String getSURCREQ() {
        return SURCREQ;
    }

    public void setSURCREQ(String SURCREQ) {
        this.SURCREQ = SURCREQ;
    }

    public String getCOMPNAME() {
        return COMPNAME;
    }

    public void setCOMPNAME(String COMPNAME) {
        this.COMPNAME = COMPNAME;
    }

    public String getCOMPCODE() {
        return COMPCODE;
    }

    public void setCOMPCODE(String COMPCODE) {
        this.COMPCODE = COMPCODE;
    }
    public String getCATCODE ()
    {
        return CATCODE;
    }

    public void setCATCODE (String CATCODE)
    {
        this.CATCODE = CATCODE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SURCREQ = "+SURCREQ+", COMPNAME = "+COMPNAME+", CATCODE = "+CATCODE+", COMPCODE = "+COMPCODE+"]";
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
