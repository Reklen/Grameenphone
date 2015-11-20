package com.cc.grameenphone.async;


import android.content.Context;
import android.os.AsyncTask;

import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;

import java.util.List;

/**
 * Created by aditlal on 23/09/15.
 */
public class CompaniesAssociationSaveDBTask extends AsyncTask<Void, Void, Void> {

    List<OtherPaymentCompanyModel> list;
    Context ctx;

    public CompaniesAssociationSaveDBTask(Context ctx, List<OtherPaymentCompanyModel> list) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (OtherPaymentCompanyModel model : list) {
            model.save();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        PreferenceManager preferenceManager = new PreferenceManager(ctx);
        Logger.d("shfjdhf", "fetching list saved");
        preferenceManager.setCompaniesSavedFlag(true);
    }
}
