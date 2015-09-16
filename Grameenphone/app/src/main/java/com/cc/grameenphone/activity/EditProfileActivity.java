package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.button)
    public void clickCancel() {
        finish();
    }

    @OnClick(R.id.button_save)
    public void clickSave() {
        Toast.makeText(EditProfileActivity.this, "Display save", Toast.LENGTH_LONG).show();
    }
}
