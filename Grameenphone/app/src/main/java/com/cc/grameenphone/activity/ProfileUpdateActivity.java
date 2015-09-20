package com.cc.grameenphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ProfileUpdateModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ProfileUpdateApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class ProfileUpdateActivity extends Activity {

    @InjectView(R.id.first_nameEdit)
    EditText firstNameEdit;
    @InjectView(R.id.firstName_container)
    TextInputLayout firstNameContainer;
    @InjectView(R.id.last_nameEdit)
    EditText lastNameEdit;
    @InjectView(R.id.lastName_container)
    TextInputLayout lastNameContainer;
    @InjectView(R.id.email_idEdit)
    EditText emailIdEdit;
    @InjectView(R.id.emailid_container)
    TextInputLayout emailidContainer;
    @InjectView(R.id.national_idEdit)
    EditText nationalIdEdit;
    @InjectView(R.id.nationalId_container)
    TextInputLayout nationalIdContainer;
    @InjectView(R.id.date_of_birthEdit)
    EditText dateOfBirthEdit;
    @InjectView(R.id.dateOfBirth_container)
    TextInputLayout dateOfBirthContainer;
    @InjectView(R.id.submitButton)
    Button signUpBtn;
    @InjectView(R.id.skipButton)
    Button skipButton;

    ProfileUpdateApi profileUpdateApi;
    MaterialDialog otpDialog, successSignupDialog, errorDialog;
    private String android_id;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.submitButton)
    void submitClick() {
        android_id = Settings.Secure.getString(ProfileUpdateActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(ProfileUpdateActivity.this);
        profileUpdateApi = ServiceGenerator.createService(ProfileUpdateApi.class);

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("FNAME", firstNameEdit.getText().toString());
            innerObject.put("LNAME", lastNameEdit.getText().toString());
            if (isValidEmail(emailIdEdit.getText().toString())) {
                innerObject.put("EMAIL", emailIdEdit.getText().toString());
            } else {
                Toast.makeText(this, "Invalid email id", Toast.LENGTH_LONG).show();
                emailIdEdit.requestFocus();
            }
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("DOB", dateOfBirthEdit.getText().toString());
            innerObject.put("IDNO", nationalIdEdit.getText().toString());
            innerObject.put("TYPE", "PRFLUPDATE");
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            jsonObject.put("COMMAND", innerObject);
            Logger.d("ProfileUpdates", jsonObject.toString());
            profileUpdateApi.profileUpdate(jsonObject, new Callback<ProfileUpdateModel>() {
                @Override
                public void success(ProfileUpdateModel profileUpdateModel, Response response) {
                    Logger.d("Its pin change ", "status " + profileUpdateModel.toString());
                    if (profileUpdateModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        successSignupDialog = new MaterialDialog(ProfileUpdateActivity.this);
                        successSignupDialog.setMessage(profileUpdateModel.getCommand().getMESSAGE() + "");
                        successSignupDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                successSignupDialog.dismiss();
                                startActivity(new Intent(ProfileUpdateActivity.this, HomeActivity.class));

                            }
                        });
                        successSignupDialog.show();
                    } else if (profileUpdateModel.getCommand().getTXNSTATUS().equalsIgnoreCase("MA903")) {
                        errorDialog = new MaterialDialog(ProfileUpdateActivity.this);
                        errorDialog.setMessage(profileUpdateModel.getCommand().getMESSAGE() + "");
                        errorDialog.setPositiveButton("OK", new View.OnClickListener() {
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
                    Logger.d("Profile Update response failed ", ": " + error.getMessage().toString());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.skipButton)

    void skipClick(){
        startActivity(new Intent(ProfileUpdateActivity.this, HomeActivity.class));

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
