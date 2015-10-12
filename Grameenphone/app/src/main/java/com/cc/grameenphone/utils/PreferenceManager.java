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

    public void setAuthToken(String authToken) {
        Logger.d("Preference manager ", "Auth token   " + authToken);
        mSharedPreferences.edit().putString("authToken", authToken).commit();
    }

    public String getAuthToken() {
        return mSharedPreferences.getString("authToken", "");
    }


    public void setMSISDN(String msisdn) {
        mSharedPreferences.edit().putString("MSISDN", msisdn).commit();
    }

    public String getMSISDN() {
        return mSharedPreferences.getString("MSISDN", "");
    }

    public void setReferCode(String referCode) {
        mSharedPreferences.edit().putString("REFER", referCode).commit();
    }

    public String getReferCode() {
        return mSharedPreferences.getString("REFER", "");
    }

    public void setPINCode(String pinCode) {
        mSharedPreferences.edit().putString("PIN", pinCode).commit();
    }

    public String getPINCode() {
        return mSharedPreferences.getString("PIN", "");
    }

    public void setCompaniesSavedFlag(boolean companiesSavedFlag) {

        mSharedPreferences.edit().putBoolean("companiesSavedFlag", companiesSavedFlag).commit();
        Logger.d("Pref Check", getCompaniesSavedFlag() + "");
    }

    public Boolean getCompaniesSavedFlag() {
        return mSharedPreferences.getBoolean("companiesSavedFlag", false);
    }
}