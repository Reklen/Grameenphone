package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cc.grameenphone.utils.PreferenceManager;


public class SplashActivity extends AppCompatActivity {


    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager = new PreferenceManager(SplashActivity.this);
        if (preferenceManager.getAuthToken().isEmpty())
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        else
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));

        finish();

    }


}
