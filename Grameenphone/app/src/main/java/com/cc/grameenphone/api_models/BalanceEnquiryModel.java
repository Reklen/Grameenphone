package com.cc.grameenphone.api_models;

import com.google.gson.annotations.SerializedName;

import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushCore;

/**
 * Created by aditlal on 16/09/15.
 */
public class BalanceEnquiryModel implements Rush {

    @SerializedName("COMMAND")
    private BalanceCommandModel balanceCommandModel;

    public BalanceCommandModel getCOMMAND() {
        return balanceCommandModel;
    }

    public void setCOMMAND(BalanceCommandModel balanceCommandModel) {
        this.balanceCommandModel = balanceCommandModel;
    }

    @Override
    public String toString() {
        return "ClassPojo [COMMAND = " + balanceCommandModel + "]";
    }

    @Override
    public void save() { RushCore.getInstance().save(this); }
    @Override
    public void save(RushCallback callback) { RushCore.getInstance().save(this, callback); }
    @Override
    public void delete() { RushCore.getInstance().delete(this); }
    @Override
    public void delete(RushCallback callback) { RushCore.getInstance().delete(this, callback); }
    @Override
    public String getId() { return RushCore.getInstance().getId(this); }
}

