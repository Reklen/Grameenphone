package com.cc.grameenphone.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class ProfileActivity extends Activity {

    @InjectView(R.id.transactionToolbar)
    Toolbar transactionToolbar;
    @InjectView(R.id.first_nameEdit)
    EditText firstNameEdit;
    @InjectView(R.id.firstName_container)
    TextInputLayout firstNameContainer;
    @InjectView(R.id.last_nameEdit)
    EditText lastNameEdit;
    @InjectView(R.id.lastName_container)
    TextInputLayout lastNameContainer;
    @InjectView(R.id.email_idEdit)
    EditText emailIdEdit;
    @InjectView(R.id.emailid_container)
    TextInputLayout emailidContainer;
    @InjectView(R.id.national_idEdit)
    EditText nationalIdEdit;
    @InjectView(R.id.nationalId_container)
    TextInputLayout nationalIdContainer;
    @InjectView(R.id.date_of_birthEdit)
    EditText dateOfBirthEdit;
    @InjectView(R.id.dateOfBirth_container)
    TextInputLayout dateOfBirthContainer;
    @InjectView(R.id.sign_up_btn)
    Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.inject(this);

    }
}
