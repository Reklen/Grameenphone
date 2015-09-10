package com.example.grameenphone.raj_ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grameenphone.R;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class SignUpActivity extends Activity {
    Toolbar signUpToolBar;
    TextView consecutiveText,acceptText,termsText;
    CheckBox checkBox01;
    EditText phnNumberEdit,conformEdit,setPinEdit,enterReferralEdit;
    Button sign_up,resend_btn;
    AppCompatDialog signUpDialog;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        signUpToolBar= (Toolbar) findViewById(R.id.sign_up_toolbar);
        //textView's
        consecutiveText= (TextView) findViewById(R.id.consecutivetext);
        acceptText= (TextView) findViewById(R.id.sign_accept_text01);
        termsText= (TextView) findViewById(R.id.sign_terms_text01);
        //EditText
        phnNumberEdit= (EditText) findViewById(R.id.sign_number_edittext01);
        conformEdit= (EditText) findViewById(R.id.conformPinEdit);
        setPinEdit= (EditText) findViewById(R.id.setPinEdit);
        enterReferralEdit= (EditText) findViewById(R.id.referralPinEdit);
        //Button
        sign_up= (Button) findViewById(R.id.sign_up_btn);
        sign_up.setVisibility(View.GONE);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             signUpDialog = new AppCompatDialog(SignUpActivity.this);
                signUpDialog.setContentView(R.layout.sign_up_dialog);
               resend_btn= (Button) signUpDialog.findViewById(R.id.resend_btn);
                signUpDialog.show();
                signUpDialog.getWindow().setLayout(600,350);
                resend_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                signUpDialog.setCanceledOnTouchOutside(true);
            }
        });
        //imageView
        back_icon= (ImageView) findViewById(R.id.image_icon_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        checkBox01= (CheckBox) findViewById(R.id.sign_check_box01);
        checkBox01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    sign_up.setVisibility(View.VISIBLE);
//                            str=viewHolder01.numb.getText().toString();
//                            Log.d("value",str);
                } else {
                    sign_up.setVisibility(View.GONE);
                }

            }
        });


    }
}
