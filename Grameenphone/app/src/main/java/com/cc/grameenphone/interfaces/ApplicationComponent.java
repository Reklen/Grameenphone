package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.ApplicationModule;
import com.cc.grameenphone.activity.LoginActivity;
import com.cc.grameenphone.activity.SignUpActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by aditlal on 15/09/15.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(SignUpActivity activity);

    void inject(LoginActivity activity);
}
