package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cc.grameenphone.utils.PreferenceManager;


public class SplashScreenActivity extends AppCompatActivity {


    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(SplashScreenActivity.this);
        if (preferenceManager.getAuthToken().isEmpty())
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        else
            startActivity(new Intent(SplashScreenActivity.this, GrameenHomeActivity.class));

        finish();

    }


}
