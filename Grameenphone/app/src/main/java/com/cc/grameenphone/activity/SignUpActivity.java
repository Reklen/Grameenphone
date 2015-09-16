package com.cc.grameenphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.SignupCommandModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.SignupApi;
import com.cc.grameenphone.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class SignUpActivity extends Activity {
    Toolbar signUpToolBar;
    TextView consecutiveText, acceptText, termsText;
    CheckBox checkBox01;
    EditText phoneNumber, confirmPincode, setPincode, referalCode;
    Button sign_up, resend_btn;
    AppCompatDialog signUpDialog;
    ImageView back_icon;
    SignupApi signupApi;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        signUpToolBar = (Toolbar) findViewById(R.id.transactionToolbar);
        //textView's
        consecutiveText = (TextView) findViewById(R.id.consecutivetext);
        acceptText = (TextView) findViewById(R.id.sign_accept_text01);
        termsText = (TextView) findViewById(R.id.sign_terms_text01);
        //EditText
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        confirmPincode = (EditText) findViewById(R.id.conformPinEdit);
        setPincode = (EditText) findViewById(R.id.setPinEdit);
        confirmPincode.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String strPin = setPincode.getText().toString();
                String strConfirmPin = confirmPincode.getText().toString();
                if (strPin.equals(strConfirmPin)) {
                    //confirmPincode.setError("Pin code is not matching");
                } else {
                    confirmPincode.setError("Pin code is not match!!");
                    confirmPincode.setFocusable(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        referalCode = (EditText) findViewById(R.id.referralPinEdit);
        //Button
        sign_up = (Button) findViewById(R.id.sign_up_btn);
        sign_up.setVisibility(View.GONE);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupApi = (SignupApi) ServiceGenerator.createService(SignUpActivity.class);
                deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                try {
                    JSONObject jsonObject = new JSONObject();
                    JSONObject innerJsonObj = new JSONObject();
                    innerJsonObj.put("DEVICEID", deviceId);
                    innerJsonObj.put("PHONENO", phoneNumber.getText().toString());
                    innerJsonObj.put("PINCODE", setPincode.getText().toString());
                    innerJsonObj.put("CONFIRMPIN", confirmPincode.getText().toString());
                    jsonObject.put("COMMAND", innerJsonObj);
                    signupApi.check(jsonObject, new Callback<SignupCommandModel>() {
                        @Override
                        public void success(SignupCommandModel signupCommandModel, Response response) {
                            Logger.d("Its msisdn check ", "success " + signupCommandModel.toString());
                            // Send otp after success signup
                            signUpDialog = new AppCompatDialog(SignUpActivity.this);
                            signUpDialog.setContentView(R.layout.sign_up_dialog);
                            resend_btn = (Button) signUpDialog.findViewById(R.id.okButton);
                            signUpDialog.show();
                            signUpDialog.getWindow().setLayout(600, 350);
                            resend_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            signUpDialog.setCanceledOnTouchOutside(true);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Logger.e("Error in signing up");
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        //imageView
        back_icon = (ImageView) findViewById(R.id.image_icon_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        checkBox01 = (CheckBox) findViewById(R.id.sign_check_box01);
        checkBox01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    sign_up.setVisibility(View.VISIBLE);
//
                    sign_up.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(SignUpActivity.this, GrameenhomeActivity.class));
                        }
                    });
                } else {
                    sign_up.setVisibility(View.GONE);
                }

            }
        });


    }
}
