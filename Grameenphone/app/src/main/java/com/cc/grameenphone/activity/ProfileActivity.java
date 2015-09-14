package com.cc.grameenphone.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.cc.grameenphone.R;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class ProfileActivity extends Activity {
    EditText firstNameEdit,lastNameEdit,emailIdEdit,nationalIdEdit,dateOfBirthEdit;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        //EditText
        firstNameEdit= (EditText) findViewById(R.id.first_nameEdit);
        lastNameEdit= (EditText) findViewById(R.id.last_nameEdit);
        emailIdEdit= (EditText) findViewById(R.id.email_idEdit);
        nationalIdEdit= (EditText) findViewById(R.id.national_idEdit);
        dateOfBirthEdit= (EditText) findViewById(R.id.date_of_birthEdit);

        //Buttons
        submit_btn= (Button) findViewById(R.id.sign_up_btn);

    }
}
