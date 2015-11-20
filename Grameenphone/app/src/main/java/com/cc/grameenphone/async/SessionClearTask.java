package com.cc.grameenphone.async;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.cc.grameenphone.activity.LoginActivity;
import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;

import co.uk.rushorm.core.RushCore;

/**
 * Created by aditlal on 23/09/15.
 */
public class SessionClearTask extends AsyncTask<Void, Void, Void> {

    Context ctx;
    boolean isCleanOut;

    public SessionClearTask(Context ctx, boolean isCleanOut) {
        this.ctx = ctx;
        this.isCleanOut = isCleanOut;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (isCleanOut) {
            RushCore.getInstance().deleteAll(ContactModel.class);
            RushCore.getInstance().deleteAll(OtherPaymentCompanyModel.class);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        PreferenceManager preferenceManager = new PreferenceManager(ctx);
        Logger.d("shfjdhf", "cleared");
        preferenceManager.setCompaniesSavedFlag(false);
        preferenceManager.setAuthToken("");
        preferenceManager.setMSISDN("");

        ctx.startActivity(new Intent(ctx, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
