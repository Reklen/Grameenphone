package com.cc.grameenphone;

import com.cc.grameenphone.utils.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by aditlal on 15/09/15.
 */
@Module
public class ApplicationModule {
    private MainApplication mApp;

    public ApplicationModule(MainApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    PreferenceManager provideSharedPrefs() {
        return new PreferenceManager(mApp);
    }
}