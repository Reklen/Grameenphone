package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.LoginModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.LoginApi;
import com.cc.grameenphone.utils.KeyboardUtil;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.MyPasswordTransformationMethod;
import com.cc.grameenphone.utils.PreferenceManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.provider.Settings.Secure;


public class LoginActivity extends BaseActivity implements ValidationListener {

    static String TAG = LoginActivity.class.getSimpleName();
    @InjectView(R.id.grameen_icon)
    ImageView grameenIcon;
    @InjectView(R.id.grameen_text)
    TextView grameenText;
    @InjectView(R.id.areaCode)
    TextView areaCode;
    @NotEmpty
    @InjectView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @InjectView(R.id.phone_container)
    TextInputLayout phoneContainer;
    @InjectView(R.id.top_container1)
    RelativeLayout topContainer1;
    @NotEmpty
    @Password(min = 4, scheme = Password.Scheme.NUMERIC)
    @InjectView(R.id.walletPinNumber)
    EditText walletPinNumber;
    @InjectView(R.id.wallet_pin_layout)
    LinearLayout walletPinLayout;
    @Checked
    @InjectView(R.id.check_box)
    CheckBox checkBox;
    @InjectView(R.id.accept_text)
    TextView acceptText;
    @InjectView(R.id.terms_text)
    TextView termsText;
    @InjectView(R.id.checkbox_layout)
    RelativeLayout checkboxLayout;
    @InjectView(R.id.loginButton)
    Button loginButton;
    @InjectView(R.id.or_text)
    TextView orText;
    @InjectView(R.id.view_layout)
    LinearLayout viewLayout;
    @InjectView(R.id.createWalletButton)
    Button createWalletButton;
    @InjectView(R.id.wallet_pin_input_layout)
    TextInputLayout walletPinInputLayout;
    LoginApi loginApi;

    Validator validator;
    AlertDialog.Builder errorDialogBuilder;
    AlertDialog errorDialog;
    private String android_id;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        handleExtras();
        android_id = Secure.getString(LoginActivity.this.getContentResolver(),
                Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(LoginActivity.this);
        errorDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        loginApi = ServiceGenerator.createService(LoginApi.class);
        loadingDialog = new ProgressDialog(LoginActivity.this);
        walletPinNumber.setTransformationMethod(new MyPasswordTransformationMethod());
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 8) {
                    walletPinInputLayout.setVisibility(View.VISIBLE);
                }


            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    loginButton.setVisibility(View.VISIBLE);
                    KeyboardUtil.hideKeyboard(LoginActivity.this);
                } else {
                    loginButton.setVisibility(View.GONE);
                }


            }
        });


        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setVisibility(View.GONE);


    }

    private void handleExtras() {
        Bundle b = getIntent().getExtras();
        try {
            String number = b.getString("mobile_number");
            phoneNumberEditText.setText(number);
            walletPinInputLayout.setVisibility(View.VISIBLE);
            walletPinNumber.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick(R.id.loginButton)
    void loginUser() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        loadingDialog.setMessage("Logging in");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("MSISDN", "017" + phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "CLOGINVAL");
            innerObject.put("PIN", walletPinNumber.getText().toString());
            jsonObject.put("COMMAND", innerObject);


            Logger.d("sending url", jsonObject.toString());
            loginApi.login(jsonObject, new Callback<LoginModel>() {
                @Override
                public void success(LoginModel model, Response response) {
                    Logger.d("Its msisdn check ", "status " + model.toString());
                    if (model.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Its msisdn check ", "success " + model.getCommand().getAUTHTOKEN().toString());
                        preferenceManager.setAuthToken(model.getCommand().getAUTHTOKEN());
                        preferenceManager.setPINCode(walletPinNumber.getText().toString());
                        if (model.getCommand().getRFRCODE() != null)
                            preferenceManager.setReferCode(model.getCommand().getRFRCODE());
                        else
                            preferenceManager.setReferCode("No Refercode Available");
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        preferenceManager.setMSISDN(phoneNumberEditText.getText().toString());
                        finish();
                        loadingDialog.dismiss();
                    } else if (model.getCommand().getTXNSTATUS().equalsIgnoreCase("00066")) {
                        loadingDialog.dismiss();
                        errorDialogBuilder.setMessage(model.getCommand().getMESSAGE() + "");
                        errorDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                errorDialog.cancel();

                                startActivity(new Intent(LoginActivity.this, SignUpActivity.class).putExtra("mobile_number", phoneNumberEditText.getText().toString()));
                                //TODO goto signup
                            }
                        });
                        errorDialog = errorDialogBuilder.create();
                        errorDialog.show();
                        //TODO chance to message and or dialog

                    } else if (model.getCommand().getTXNSTATUS().equalsIgnoreCase("00068")) {
                        walletPinNumber.setError("Pin invalid");
                        loadingDialog.dismiss();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.d("Its msisdn check ", "failure " + error.getMessage() + " its url is " + error.getUrl());
                    displayToast("Some error occurred");
                    loadingDialog.dismiss();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(LoginActivity.this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }



    /*

     */

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
