package com.cc.grameenphone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.MSISDNCheckModel;
import com.cc.grameenphone.api_models.SignupModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.MSISDNCheckApi;
import com.cc.grameenphone.interfaces.SignupApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.MyPasswordTransformationMethod;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.mobsandgeeks.saripaar.Validator.ValidationListener;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class SignUpActivity extends BaseActivity implements ValidationListener {
    /* Toolbar signUpToolBar;
     TextView consecutiveText,acceptText,termsText;
     CheckBox checkBox01;
     EditText phnNumberEdit,conformEdit,setPinEdit,enterReferralEdit;
     Button sign_up,resend_btn;*/
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.transactionToolbar)
    Toolbar transactionToolbar;

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
    @InjectView(R.id.setPinEdit)
    EditText setPinEdit;
    @InjectView(R.id.setPin_container)
    TextInputLayout setPinContainer;
    @InjectView(R.id.consecutivetext)
    TextView consecutivetext;

    @ConfirmPassword
    @InjectView(R.id.conformPinEdit)
    EditText conformPinEdit;
    @InjectView(R.id.confromPin_container)
    TextInputLayout confromPinContainer;
    @InjectView(R.id.referralCodeEdit)
    EditText referralCodeEdit;
    @InjectView(R.id.referral_container)
    TextInputLayout referralContainer;
    @Checked
    @InjectView(R.id.checkbox_signup)
    CheckBox checkbox_signup;
    @InjectView(R.id.sign_accept_text01)
    TextView signAcceptText01;
    @InjectView(R.id.sign_terms_text01)
    TextView signTermsText01;
    @InjectView(R.id.checkbox_layout)
    RelativeLayout checkboxLayout;
    @InjectView(R.id.sign_up_btn)
    Button signUpBtn;
    @InjectView(R.id.sign_up_layout)
    LinearLayout signUpLayout;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;

    Validator validator;
    MSISDNCheckApi msisdnCheckApi;

    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.grameen_icon)
    ImageView grameenIcon;
    View otpView;
    SignupApi signupApi;
    MaterialDialog otpDialog, successSignupDialog, errorDialog;
    private String android_id;
    private String otpString, authTokenString;

    // ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        setUpToolBar();
        validator = new Validator(this);
        validator.setValidationListener(this);
        msisdnCheckApi = ServiceGenerator.createService(MSISDNCheckApi.class);

        handleExtras();
        android_id = Settings.Secure.getString(SignUpActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(SignUpActivity.this);

        signupApi = ServiceGenerator.createService(SignupApi.class);

        setPinEdit.setTransformationMethod(new MyPasswordTransformationMethod());
        conformPinEdit.setTransformationMethod(new MyPasswordTransformationMethod());
        checkbox_signup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    signUpBtn.setVisibility(View.VISIBLE);

                } else {
                    signUpBtn.setVisibility(View.GONE);
                }

            }
        });

        referralCodeEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });


    }

    private void handleExtras() {
        Bundle b = getIntent().getExtras();
        try {
            String number = b.getString("mobile_number");
            phoneNumberEditText.setText(number);
            setPinEdit.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUpToolBar() {
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
    }


    @OnClick(R.id.sign_up_btn)
    void signUpClick() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        Logger.d("Signup validation", "success");
        doMSISDNCheck();
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(SignUpActivity.this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void doMSISDNCheck() {
        Logger.d("Signup ", "doing msisdncheck");
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("MSISDN", "017" + phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "MSISDNCHK");
            jsonObject.put("COMMAND", innerObject);
            msisdnCheckApi.check(jsonObject, new Callback<MSISDNCheckModel>() {
                @Override
                public void success(MSISDNCheckModel msisdnCheckModel, Response response) {
                    Logger.d("MSISDN  ", msisdnCheckModel.toString());
                    if (msisdnCheckModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        otpString = msisdnCheckModel.getCOMMAND().getOTP();
                        authTokenString = msisdnCheckModel.getCOMMAND().getAUTHTOKEN();
                        signUpUser();
                    } else {

                        errorDialog = new MaterialDialog(SignUpActivity.this);
                        errorDialog.setMessage(msisdnCheckModel.getCOMMAND().getAUTHTOKEN() + "");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class).putExtra("mobile_number", phoneNumberEditText.getText().toString()));
                                finish();

                            }
                        });

                        errorDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void signUpUser() {
        //READ :
        // {"COMMAND": {"RFRCODE": "ud85szn","PIN": "2468",
        // "MSISDN": "01719202177", "TYPE": "CUSTREG", "DEVICEID":"01234567890654321",
        // "AUTHTOKEN": "5e48dad31259c988275c34641d1ba78761d54391a389225309bb65bd8aed946d", "OTP": "3427359088" }}
        otpView = LayoutInflater.from(SignUpActivity.this).inflate(R.layout.sign_up_dialog, null);
        Logger.d("Signup ", "doing signUpUser");
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerJsonObj = new JSONObject();

            innerJsonObj.put("DEVICEID", android_id);
            innerJsonObj.put("MSISDN", "017" + phoneNumberEditText.getText().toString());
            innerJsonObj.put("TYPE", "CUSTREG");
            if (!referralCodeEdit.getText().toString().isEmpty())
                innerJsonObj.put("RFRCODE", referralCodeEdit.getText().toString());
            else
                innerJsonObj.put("RFRCODE", "");
            innerJsonObj.put("PIN", setPinEdit.getText().toString());
            innerJsonObj.put("AUTHTOKEN", "" + authTokenString);
            jsonObject.put("COMMAND", innerJsonObj);

            Logger.d("Signup ", "doing signUpUser " + jsonObject.toString());
            signupApi.signup(jsonObject, new Callback<SignupModel>() {
                @Override
                public void success(SignupModel signupModel, Response response) {
                    Logger.d("Signup Model", signupModel.toString());
                    if (signupModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        //dialog
                        /*otpDialog = new MaterialDialog(SignUpActivity.this).setContentView(otpView);
                        otpDialog.setPositiveButton("Resend", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        ((TextView) otpView.findViewById(R.id.resendButton)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO what to do
                            }
                        });


                        ((TextView) otpView.findViewById(R.id.code_text)).setText("6 digit code has been sent to\n\n" +
                                "+880 " + phoneNumberEditText.getText().toString());


                        otpDialog.setCanceledOnTouchOutside(false);

                        otpDialog.show();*/

                              /*  SmsRadar.initializeSmsRadarService(SignUpActivity.this, new SmsListener() {
                                    @Override
                                    public void onSmsSent(Sms sms) {
                                        displayToast(sms.getMsg());
                                    }

                                    @Override
                                    public void onSmsReceived(Sms sms) {
                                        displayToast(sms.getMsg());

                                        if (sms.getMsg().contains("otp")) {
                                            //then call signup api
                                            SmsRadar.stopSmsRadarService(SignUpActivity.this);
                                        }
                                    }
                                });
                    */
                        successSignupDialog = new MaterialDialog(SignUpActivity.this);
                        successSignupDialog.setMessage(signupModel.getCommand().getMESSAGE() + "");
                        successSignupDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                preferenceManager.setAuthToken(authTokenString);

                                preferenceManager.setMSISDN(phoneNumberEditText.getText().toString());
                                successSignupDialog.dismiss();
                                preferenceManager.setMSISDN(phoneNumberEditText.getText().toString());
                                startActivity(new Intent(SignUpActivity.this, ProfileUpdateActivity.class));
                                finish();

                            }
                        });
                        successSignupDialog.show();
                    } else if (signupModel.getCommand().getTXNSTATUS().equalsIgnoreCase("GP230")) {
                        //dialog
                        // otpDialog.dismiss();
                        errorDialog = new MaterialDialog(SignUpActivity.this);
                        errorDialog.setMessage(signupModel.getCommand().getMESSAGE() + "");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.show();
                    } else {
//                        otpDialog.dismiss();
                        errorDialog = new MaterialDialog(SignUpActivity.this);
                        errorDialog.setMessage(signupModel.getCommand().getMESSAGE() + "");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });

                        errorDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.d("Its msisdn check ", "failure " + error.getMessage() + " its url is " + error.getUrl());
                    displayToast("Some error occurred");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            displayToast("Some Error occured");
        }


    }
}
