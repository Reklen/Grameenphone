package com.cc.grameenphone.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by sudendra on 24/1/15.
 */
public final class PreferenceManager {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public PreferenceManager(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("grameenphonePrefs", Context.MODE_PRIVATE);
    }

    private SharedPreferences getPref() {
        return mSharedPreferences;
    }

}