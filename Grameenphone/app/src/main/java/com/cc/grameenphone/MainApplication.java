package com.cc.grameenphone;

import android.app.Application;

import com.cc.grameenphone.interfaces.ApplicationComponent;
import com.cc.grameenphone.interfaces.DaggerApplicationComponent;
import com.cc.grameenphone.utils.Logger;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

/**
 * Created by aditlal on 14/09/15.
 */
public class MainApplication extends Application {


    public static MainApplication application;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.init("gp");
        initializeInjector();
        initializeDB();
    }

    private void initializeDB() {
        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        RushCore.initialize(config);
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static MainApplication getInstance() {
        return application;
    }
}
