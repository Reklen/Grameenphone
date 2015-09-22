package com.cc.grameenphone;

import com.cc.grameenphone.utils.Logger;
import com.orm.SugarApp;

/**
 * Created by aditlal on 14/09/15.
 */
public class MainApplication extends SugarApp {


    public static MainApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.init("gp");
        initializeDB();
    }

    private void initializeDB() {
        //   AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        // RushAndroid.initialize(config);
    }


    public static MainApplication getInstance() {
        return application;
    }


}
