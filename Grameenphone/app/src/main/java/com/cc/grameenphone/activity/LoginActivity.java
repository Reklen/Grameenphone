package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.MSISDNCheckModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.MSISDNCheckApi;
import com.cc.grameenphone.utils.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.provider.Settings.Secure;


public class LoginActivity extends AppCompatActivity {
    TextView grameenPhone, numberText, iAccept, termsCondition, walletPinText;
    EditText numberEdit, walletPInEditText;
    CheckBox checkBox;
    ImageView grameenIcon;
    Button createNewWallet, login;
    LinearLayout walletPinLayout;
    private String android_id;
    MSISDNCheckApi msisdnCheckApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grameenphone);
        //textViews
        grameenPhone = (TextView) findViewById(R.id.grameen_text);
        // numberText= (TextView) findViewById(R.id.number_text);
        iAccept = (TextView) findViewById(R.id.accept_text);
        termsCondition = (TextView) findViewById(R.id.terms_text);
        walletPinText = (TextView) findViewById(R.id.wallet_pin_text);
        msisdnCheckApi = ServiceGenerator.createService(MSISDNCheckApi.class);
        android_id = Secure.getString(LoginActivity.this.getContentResolver(),
                Secure.ANDROID_ID);
        try {
        JSONObject jsonObject = new JSONObject();
        JSONObject innerObject = new JSONObject();
        innerObject.put("DEVICEID", android_id);
        innerObject.put("MSISDN", "01719202177");
        innerObject.put("TYPE", "MSISDNCHK");

            jsonObject.put("COMMAND", innerObject);


            Logger.d("sending url", jsonObject.toString());
            msisdnCheckApi.check(jsonObject, new Callback<MSISDNCheckModel>() {
                @Override
                public void success(MSISDNCheckModel msisdnCheckModel, Response response) {
                    Logger.d("Its msisdn check ", "success " + msisdnCheckModel.toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.d("Its msisdn check ", "failure " + error.getMessage() + " its url is " + error.getUrl());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  msisdnCheckApi.check();
        //LinearLayouts or view's
        walletPinLayout = (LinearLayout) findViewById(R.id.wallet_pin_layout);


        //EditText
        walletPInEditText = (EditText) findViewById(R.id.walletPinNumber);
        numberEdit = (EditText) findViewById(R.id.phoneNumber);
        numberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 10) {
                    walletPinLayout.setVisibility(View.VISIBLE);
                    // login.setVisibility(View.VISIBLE);
                } else {
                    walletPinLayout.setVisibility(View.GONE);
                    // login.setVisibility(View.GONE);
                }

            }
        });

        //checkBox
        checkBox = (CheckBox) findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    login.setVisibility(View.VISIBLE);
//                            str=viewHolder01.numb.getText().toString();
//                            Log.d("value",str);
                } else {
                    login.setVisibility(View.GONE);
                }
            }
        });

        //igmageView
        grameenIcon = (ImageView) findViewById(R.id.grameen_icon);

        //Button
        createNewWallet = (Button) findViewById(R.id.createWalletButton);
        createNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        login = (Button) findViewById(R.id.loginButton);
        login.setVisibility(View.GONE);


    }

//    AppCompatDialog exit_dialog;
//    exit_dialog = new AppCompatDialog(LoginActivity.this);
//    exit_dialog.setContentView(R.layout.dialog_exit_app);
//    exit_app = (Button) exit_dialog.findViewById(R.id.accept_btn);
//    cancel_app_exit = (Button) exit_dialog.findViewById(R.id.cancel_btn);
//    exit_dialog.show();
//    cancel_app_exit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            exit_dialog.dismiss();
//        }
//    });
//    exit_app.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            exit_dialog.cancel();
//            finish();
//        }
//    });
}
