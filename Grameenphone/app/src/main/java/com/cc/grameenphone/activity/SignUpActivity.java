package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.MSISDNCheckApi;
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
    AppCompatDialog signUpDialog;
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.transactionToolbar)
    Toolbar transactionToolbar;
    @InjectView(R.id.grameen_icon01)
    ImageView grameenIcon01;
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

    private String android_id;
    // ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        ButterKnife.inject(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        msisdnCheckApi = ServiceGenerator.createService(MSISDNCheckApi.class);
        getApplicationComponent().inject(this);
        android_id = Settings.Secure.getString(SignUpActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
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


    }


    @OnClick(R.id.sign_up_btn)
    void signUpClick() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {

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
                    if (msisdnCheckModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        signUpUser();
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
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerJsonObj = new JSONObject();

            innerJsonObj.put("DEVICEID", android_id);
            innerJsonObj.put("MSISDN", phoneNumberEditText.getText().toString());
            innerJsonObj.put("TYPE", "CUSTREG");
            if (!referralCodeEdit.getText().toString().isEmpty())
                innerJsonObj.put("RFRCODE", referralCodeEdit.getText().toString());
            innerJsonObj.put("PIN", setPinEdit.getText().toString());
            innerJsonObj.put("AUTHTOKEN", "" + preferenceManager.getAuthToken());
            jsonObject.put("COMMAND", innerJsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        signUpDialog = new AppCompatDialog(SignUpActivity.this);
        signUpDialog.setContentView(R.layout.sign_up_dialog);
        ((Button) signUpDialog.findViewById(R.id.resendButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO what to do
            }
        });


        ((TextView) signUpDialog.findViewById(R.id.code_text)).setText("6 digit code has been sent to\\n\n" +
                "        +880 " + phoneNumberEditText.getText().toString());


        signUpDialog.setCanceledOnTouchOutside(false);

        signUpDialog.show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((Button) signUpDialog.findViewById(R.id.resendButton)).setEnabled(true);
            }
        }, 2000);

    }
}
