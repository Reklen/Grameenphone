package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.cc.grameenphone.interfaces.OnSMSInterface;
import com.cc.grameenphone.interfaces.SignupApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.MyPasswordTransformationMethod;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.utils.SMS;
import com.cc.grameenphone.views.RippleView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

import static com.mobsandgeeks.saripaar.Validator.ValidationListener;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class SignUpActivity extends BaseActivity implements ValidationListener, OnSMSInterface {
    /* Toolbar signUpToolBar;
     TextView consecutiveText,acceptText,termsText;
     CheckBox checkBox01;
     EditText phnNumberEdit,conformEdit,setPinEdit,enterReferralEdit;
     Button sign_up,resend_btn;*/
    @Bind(R.id.image_icon_back)
    ImageView imageIconBack;
    @Bind(R.id.text_tool)
    TextView textTool;
    @Bind(R.id.toolbar)
    Toolbar transactionToolbar;

    @Bind(R.id.grameen_text)
    TextView grameenText;
    @Bind(R.id.areaCode)
    TextView areaCode;

    @NotEmpty
    @Length(min = 11)
    @Bind(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;

    @Bind(R.id.phone_container)
    TextInputLayout phoneContainer;
    @Bind(R.id.top_container1)
    RelativeLayout topContainer1;
    @NotEmpty
    @Password(min = 4, scheme = Password.Scheme.NUMERIC)
    @Bind(R.id.setPinEdit)
    EditText setPinEdit;
    @Bind(R.id.setPin_container)
    TextInputLayout setPinContainer;
    @Bind(R.id.consecutivetext)
    TextView consecutivetext;

    @ConfirmPassword
    @Bind(R.id.conformPinEdit)
    EditText conformPinEdit;
    @Bind(R.id.confromPin_container)
    TextInputLayout confromPinContainer;
    @Bind(R.id.referralCodeEdit)
    EditText referralCodeEdit;
    @Bind(R.id.referral_container)
    TextInputLayout referralContainer;
    @Checked
    @Bind(R.id.checkbox_signup)
    CheckBox checkbox_signup;
    @Bind(R.id.sign_accept_text01)
    TextView signAcceptText01;
    @Bind(R.id.sign_terms_text01)
    TextView signTermsText01;
    @Bind(R.id.checkbox_layout)
    RelativeLayout checkboxLayout;
    @Bind(R.id.sign_up_btn)
    Button signUpBtn;
    @Bind(R.id.sign_up_layout)
    LinearLayout signUpLayout;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    ProgressDialog loadingDialog;
    Validator validator;
    MSISDNCheckApi msisdnCheckApi;

    @Bind(R.id.backRipple)
    RippleView backRipple;
    @Bind(R.id.grameen_icon)
    ImageView grameenIcon;
    View otpView;
    SignupApi signupApi;
    MaterialDialog otpDialog, successSignupDialog, errorDialog;
    private String android_id;
    boolean isOTPDialogShown = false;
    private String otpString, receivedOtpString, authTokenString;


    SMS sms;
    boolean isPinCons = false, isPinRep = false;

    // ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        setUpToolBar();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        //Extends BroadcastReceiver
        sms = new SMS();
        sms.setSMSListener(SignUpActivity.this);
        registerReceiver(sms, filter);
        validator = new Validator(this);
        validator.setValidationListener(this);
        msisdnCheckApi = ServiceGenerator.createService(SignUpActivity.this,MSISDNCheckApi.class);
        loadingDialog = new ProgressDialog(SignUpActivity.this);
        loadingDialog.setMessage("Registering , please wait..");
        handleExtras();
        android_id = Settings.Secure.getString(SignUpActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(SignUpActivity.this);

        signupApi = ServiceGenerator.createService(SignUpActivity.this,SignupApi.class);

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

    @OnClick(R.id.sign_terms_text01)
    void tocClick() {
        startActivity(new Intent(SignUpActivity.this, TermsAndConditionsActivity.class));
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

        Pattern consPattern = Pattern.compile(
                "\\b                                                       \n" +
                        "(?:                                                       \n" +
                        " (?:                                                      \n" +
                        "  0(?=1|\\b)|                                             \n" +
                        "  1(?=2|\\b)|                                             \n" +
                        "  2(?=3|\\b)|                                             \n" +
                        "  3(?=4|\\b)|                                             \n" +
                        "  4(?=5|\\b)|                                             \n" +
                        "  5(?=6|\\b)|                                             \n" +
                        "  6(?=7|\\b)|                                             \n" +
                        "  7(?=8|\\b)|                                             \n" +
                        "  8(?=9|\\b)|                                             \n" +
                        "  9\\b         # or 9(?=0|\\b) if you want to allow 890123\n" +
                        " )+                                                       \n" +
                        " |                                                        \n" +
                        " (?:                                                      \n" +
                        "  9(?=8|\\b)|                                             \n" +
                        "  8(?=7|\\b)|                                             \n" +
                        "  7(?=6|\\b)|                                             \n" +
                        "  6(?=5|\\b)|                                             \n" +
                        "  5(?=4|\\b)|                                             \n" +
                        "  4(?=3|\\b)|                                             \n" +
                        "  3(?=2|\\b)|                                             \n" +
                        "  2(?=1|\\b)|                                             \n" +
                        "  1(?=0|\\b)|                                             \n" +
                        "  0\\b         # or 0(?=9|\\b) if you want to allow 321098\n" +
                        " )+                                                       \n" +
                        ")                                                         \n" +
                        "\\b",
                Pattern.COMMENTS);
        Matcher regexMatcher = consPattern.matcher(setPinEdit.getText().toString());
        if (regexMatcher.matches()) {
            //Logger.d("Pattern", "matches");
            isPinCons = true;
        } else {
            Logger.e("Pattern", "does not match");
            isPinCons = false;
        }


        Pattern pattern = Pattern.compile("([0-9])\\1{3}");
        Matcher matcher = pattern.matcher(setPinEdit.getText().toString());
        if (matcher.find()) {
            //Logger.d("PatternRep", "matches");
            isPinRep = true;
        } else {
            Logger.e("PatternRep", "does not match");
            isPinRep = false;

        }

        if (isPinCons) {
            Toast.makeText(SignUpActivity.this, "Password cant contain consecutive digits", Toast.LENGTH_SHORT).show();
            setPinEdit.setError("Password error");
            conformPinEdit.setText("");
            setPinEdit.requestFocus();
            setPinEdit.setText("");

            return;
        }
        if (isPinRep) {
            Toast.makeText(SignUpActivity.this, "Password cant contain repetitive digits", Toast.LENGTH_SHORT).show();
            setPinEdit.setError("Password error");
            conformPinEdit.setText("");
            setPinEdit.setText("");
            setPinEdit.requestFocus();

            return;
        }
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        //Logger.d("Signup validation", "success");
        doMSISDNCheck();
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        loadingDialog.cancel();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(SignUpActivity.this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                ((EditText) view).setText("");
                ((EditText) view).requestFocus();
            } else {
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getKey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.cc.grameenphone",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "";

        } catch (NoSuchAlgorithmException e) {
            return "";

        }
        return "";

    }

    private void doMSISDNCheck() {
        //Logger.d("Signup ", "doing msisdncheck");
        String key =getKey();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("MSISDN", phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "MSISDNCHK");
            innerObject.put("INITKEY", key);

            jsonObject.put("COMMAND", innerObject);
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            msisdnCheckApi.check(in, new Callback<MSISDNCheckModel>() {
                @Override
                public void success(MSISDNCheckModel msisdnCheckModel, Response response) {
                    Logger.d("MSISDN  ", msisdnCheckModel.toString());
                    if (msisdnCheckModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        otpString = msisdnCheckModel.getCOMMAND().getOTP();
                        // Toast.makeText(SignUpActivity.this, "OTP for test is "+ otpString, Toast.LENGTH_LONG).show();
                        authTokenString = msisdnCheckModel.getCOMMAND().getAUTHTOKEN();
                        signUpUser();
                    } else {
                        loadingDialog.cancel();
                        errorDialog = new MaterialDialog(SignUpActivity.this);
                        errorDialog.setMessage(msisdnCheckModel.getCOMMAND().getAUTHTOKEN() + "");
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
                    loadingDialog.cancel();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void signUpUser() {
        //READ :
        // {"COMMAND": {"RFRCODE": "ud85szn","PIN": "2468",
        // "MSISDN": "01719202177", "TYPE": "CUSTREG", "DEVICEID":"01234567890654321",
        // "AUTHTOKEN": "5e48dad31259c988275c34641d1ba78761d54391a389225309bb65bd8aed946d", "OTP": "3427359088" }}

        otpView = LayoutInflater.from(SignUpActivity.this).inflate(R.layout.sign_up_dialog, null);
        //Logger.d("Signup ", "doing signUpUser");
       /* SmsRadar.initializeSmsRadarService(SignUpActivity.this, new SmsListener() {
            @Override
            public void onSmsSent(Sms sms) {
                displayToast(sms.getMsg());
            }

            @Override
            public void onSmsReceived(Sms sms) {
                displayToast(sms.getMsg());
                //Logger.d("OTP", sms.getMsg());

                if (sms.getMsg().contains("otp")) {
                    //then call signup api
                    //Logger.d("OTP", sms.getMsg());
                    SmsRadar.stopSmsRadarService(SignUpActivity.this);
                }
            }
        });*/

        otpDialog = new MaterialDialog(SignUpActivity.this).setContentView(otpView);
        otpDialog.setNegativeButton("Resend", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        otpDialog.setPositiveButton("Confirm", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((EditText) otpView.findViewById(R.id.otpEdit)).getText().length() == 0)
                    ((EditText) otpView.findViewById(R.id.otpEdit)).setError("Enter OTP to proceed");
                if (((EditText) otpView.findViewById(R.id.otpEdit)).getText().toString().equalsIgnoreCase(otpString)) {
                    otpDialog.dismiss();
                    signupRequest();
                    //signup
                } else
                    ((EditText) otpView.findViewById(R.id.otpEdit)).setError("Invalid OTP entered");

            }
        });
       /* ((TextView) otpView.findViewById(R.id.resendButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO what to do
                startActivity(new Intent(SignUpActivity.this, ProfileUpdateActivity.class));
                finish();

            }
        });*/


        ((TextView) otpView.findViewById(R.id.code_text)).setText("6 digit code has been sent to\n\n" +
                "+88" + phoneNumberEditText.getText().toString());


        otpDialog.setCanceledOnTouchOutside(false);

        otpDialog.show();
        isOTPDialogShown = true;
        otpDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isOTPDialogShown = false;
            }
        });


    }

    void signupRequest() {
        loadingDialog.show();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerJsonObj = new JSONObject();

            innerJsonObj.put("DEVICEID", android_id);
            innerJsonObj.put("MSISDN", phoneNumberEditText.getText().toString());
            innerJsonObj.put("TYPE", "CUSTREG");
            if (!referralCodeEdit.getText().toString().isEmpty())
                innerJsonObj.put("RFRCODE", referralCodeEdit.getText().toString());
            else
                innerJsonObj.put("RFRCODE", "");
            innerJsonObj.put("PIN", setPinEdit.getText().toString());
            innerJsonObj.put("AUTHTOKEN", "" + authTokenString);
            jsonObject.put("COMMAND", innerJsonObj);

            //Logger.d("Signup ", "doing signUpUser check " + jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            signupApi.signup(in, new Callback<SignupModel>() {
                @Override
                public void success(final SignupModel signupModel, Response response) {
                    //Logger.d("Signup Model", signupModel.toString());
                    if (signupModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        //dialog


                        loadingDialog.cancel();
                        successSignupDialog = new MaterialDialog(SignUpActivity.this);
                        successSignupDialog.setMessage(signupModel.getCommand().getMESSAGE() + "");
                        successSignupDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                preferenceManager.setAuthToken(authTokenString);
                                if (signupModel.getCommand().getRFRCODE() != null)
                                    preferenceManager.setReferCode(signupModel.getCommand().getRFRCODE());
                                else
                                    preferenceManager.setReferCode("No Refercode Available");
                                preferenceManager.setMSISDN(phoneNumberEditText.getText().toString());
                                successSignupDialog.dismiss();
                                startActivity(new Intent(SignUpActivity.this, ProfileUpdateActivity.class));
                                finish();


                            }
                        });
                        successSignupDialog.show();
                    } else if (signupModel.getCommand().getTXNSTATUS().equalsIgnoreCase("GP230")) {
                        //dialog
                        loadingDialog.cancel();

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
                        loadingDialog.cancel();

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
                    //Logger.d("Its msisdn check ", "failure " + error.getMessage() + " its url is " + error.getUrl());
                    displayToast("Some error occurred");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            displayToast("Some Error occured");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(sms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSMS(String smsMessage) {
        receivedOtpString = smsMessage;
        if (otpDialog != null)
            if (isOTPDialogShown)
                if (smsMessage.contains(otpString)) {
                    ((EditText) otpView.findViewById(R.id.otpEdit)).setText(otpString);
                    (otpView.findViewById(R.id.otpEdit)).setEnabled(false);
                    otpDialog.dismiss();
                    unregisterReceiver(sms);
                    signupRequest();
                }

    }
}
