package com.cc.grameenphone;

import android.app.Application;

import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.utils.Logger;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

/**
 * Created by aditlal on 14/09/15.
 */
public class MainApplication extends Application {


    public static MainApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.init("gp");
        initializeDB();
    }

    private void initializeDB() {
       /* Luckly there is a very simple work around.
      Simple set your classes on the config object and this problem is resolved. */
        List<Class<? extends Rush>> classes = new ArrayList<>();
        // Add classes
        classes.add(ContactModel.class);

        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        config.setClasses(classes);
        RushCore.initialize(config);
    }


    public static MainApplication getInstance() {
        return application;
    }


}
