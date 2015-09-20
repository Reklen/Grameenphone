package com.cc.grameenphone;

import android.app.Application;

import com.cc.grameenphone.utils.Logger;

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
     //  AndroidInitializeConfig config = new AndroidInitializeConfig(this);
     //  RushCore.initialize(config);
    }


    public static MainApplication getInstance() {
        return application;
    }


}
