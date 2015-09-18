package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class EditProfileActivity extends AppCompatActivity {


    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.vertical_view)
    View verticalView;
    @InjectView(R.id.button_save)
    Button buttonSave;
    @InjectView(R.id.first_name)
    EditText firstName;
    @InjectView(R.id.firstNameTextInputLayout)
    TextInputLayout firstNameTextInputLayout;
    @InjectView(R.id.last_name)
    EditText lastName;
    @InjectView(R.id.lastNameTextInputLayout)
    TextInputLayout lastNameTextInputLayout;
    @InjectView(R.id.email_name)
    EditText emailName;
    @InjectView(R.id.emailTextInputLayout)
    TextInputLayout emailTextInputLayout;
    @InjectView(R.id.national_id)
    EditText nationalId;
    @InjectView(R.id.nationalTextInputLayout)
    TextInputLayout nationalTextInputLayout;
    @InjectView(R.id.dob)
    EditText dob;
    @InjectView(R.id.dobTextInputLayout)
    TextInputLayout dobTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.inject(this);
        emailName.requestFocus();
    }

    @OnClick(R.id.button)
    public void clickCancel() {
        finish();
    }

    @OnClick(R.id.button_save)
    public void clickSave() {
        Toast.makeText(EditProfileActivity.this, "Display save", Toast.LENGTH_LONG).show();
        finish();
    }
}
