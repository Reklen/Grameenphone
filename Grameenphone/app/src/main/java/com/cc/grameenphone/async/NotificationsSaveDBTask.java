package com.cc.grameenphone.async;


import android.content.Context;
import android.os.AsyncTask;

import com.cc.grameenphone.api_models.NotificationMessageModel;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;

/**
 * Created by aditlal on 23/09/15.
 */
public class NotificationsSaveDBTask extends AsyncTask<Void, Void, Void> {

    List<NotificationMessageModel> list;
    List<NotificationMessageModel> originalSavedList;
    List<String> modelNotificiationIdList;
    Context ctx;

    public NotificationsSaveDBTask(Context ctx, List<NotificationMessageModel> list) {
        this.list = list;
        this.ctx = ctx;
        originalSavedList = new ArrayList<>();
        modelNotificiationIdList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... params) {
        new RushSearch()
                .find(NotificationMessageModel.class, new RushSearchCallback<NotificationMessageModel>() {
                    @Override
                    public void complete(final List<NotificationMessageModel> listDB) {
                        originalSavedList.addAll(listDB);
                    }
                });
        if (originalSavedList.size() > 0) {
            for (NotificationMessageModel model1 : originalSavedList) {
                for (NotificationMessageModel model2 : list) {
                    if (!model1.getNOTID().equals(model2.getNOTID())) {
                        if (modelNotificiationIdList.contains(model2.getNOTID()))
                            modelNotificiationIdList.remove(model2.getNOTID());
                        else
                            modelNotificiationIdList.add(model2.getNOTID());
                    }
                }
            }

            for (NotificationMessageModel model : list) {
                if (modelNotificiationIdList.contains(model.getNOTID())) {
                    model.setRead(false);
                    model.save();
                }

            }
        }
        else{
            for (NotificationMessageModel model : list) {
                model.setRead(false);
                model.save();

            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
