package com.cc.grameenphone;

import android.app.Application;

import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.api_models.NotificationMessageModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.utils.Logger;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.Rush;
import co.uk.rushorm.core.RushCore;

/**
 * Created by aditlal on 14/09/15.
 */
@ReportsCrashes(
        mailTo = "adit.lal@cognitiveclouds.com",
        customReportContent = {ReportField.APP_VERSION_CODE,
                ReportField.APP_VERSION_NAME, ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL, ReportField.CUSTOM_DATA,
                ReportField.STACK_TRACE, ReportField.LOGCAT},
        mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash
)
public class MainApplication extends Application {


    public static MainApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ACRA.init(this);
        Logger.init("Grameen");
        initializeDB();
    }

    private void initializeDB() {
       /* Luckly there is a very simple work around.
      Simple set your classes on the config object and this problem is resolved. */
        List<Class<? extends Rush>> classes = new ArrayList<>();
        // Add classes
        classes.add(ContactModel.class);
        classes.add(OtherPaymentCompanyModel.class);
        classes.add(NotificationMessageModel.class);

        AndroidInitializeConfig config = new AndroidInitializeConfig(getApplicationContext());
        config.setClasses(classes);
        RushCore.initialize(config);
    }


    public static MainApplication getInstance() {
        return application;
    }


}
