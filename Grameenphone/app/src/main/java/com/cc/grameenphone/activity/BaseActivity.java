package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cc.grameenphone.MainApplication;
import com.cc.grameenphone.interfaces.ApplicationComponent;
import com.cc.grameenphone.utils.PreferenceManager;

import javax.inject.Inject;

/**
 * Created by aditlal on 16/09/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Inject
    PreferenceManager preferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getApplication()).getApplicationComponent();
    }

    public void displayToast(String s) {
        Toast.makeText(BaseActivity.this, s, Toast.LENGTH_SHORT).show();
    }



}
