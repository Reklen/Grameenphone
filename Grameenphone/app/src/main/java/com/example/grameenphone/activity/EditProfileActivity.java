package com.example.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grameenphone.R;

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
    @InjectView(R.id.tool_bar)
    Toolbar toolbarWidget;
    @InjectView(R.id.first_text)
    TextView firstText;
    @InjectView(R.id.frst_name)
    EditText frstName;
    @InjectView(R.id.last_text)
    TextView lastText;
    @InjectView(R.id.last_name)
    EditText lastName;
    @InjectView(R.id.email_text)
    TextView emailText;
    @InjectView(R.id.emil_name)
    EditText emilName;
    @InjectView(R.id.national_name)
    TextView nationalName;
    @InjectView(R.id.national_id)
    EditText nationalId;
    @InjectView(R.id.dob_name)
    TextView dobName;
    @InjectView(R.id.dob)
    EditText dob;

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
        Toast.makeText(EditProfileActivity.this,"Display save",Toast.LENGTH_LONG).show();
    }
}
