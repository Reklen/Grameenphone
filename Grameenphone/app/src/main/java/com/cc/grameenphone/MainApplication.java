package com.cc.grameenphone;

import android.app.Application;

/**
 * Created by aditlal on 14/09/15.
 */
public class MainApplication extends Application {


    public static MainApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MainApplication getInstance() {
        return application;
    }
}
