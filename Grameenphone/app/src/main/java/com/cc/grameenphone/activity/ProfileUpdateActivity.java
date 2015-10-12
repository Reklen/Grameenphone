package com.cc.grameenphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ProfileUpdateModel;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ProfileUpdateApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.CustomTextInputLayout;
import com.cc.grameenphone.views.RippleView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rajkiran on 9/9/2015.
 */
public class ProfileUpdateActivity extends Activity implements DatePickerDialog.OnDateSetListener {


    ProfileUpdateApi profileUpdateApi;
    MaterialDialog otpDialog, successSignupDialog, errorDialog;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.skipText)
    TextView skipText;
    @InjectView(R.id.skipRipple)
    RippleView skipRipple;
    @InjectView(R.id.toolbar_container)
    RelativeLayout toolbarContainer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.first_nameEdit)
    EditText firstNameEdit;
    @InjectView(R.id.firstName_container)
    CustomTextInputLayout firstNameContainer;
    @InjectView(R.id.last_nameEdit)
    EditText lastNameEdit;
    @InjectView(R.id.lastName_container)
    CustomTextInputLayout lastNameContainer;
    @InjectView(R.id.email_idEdit)
    EditText emailIdEdit;
    @InjectView(R.id.emailid_container)
    CustomTextInputLayout emailidContainer;
    @InjectView(R.id.national_idEdit)
    EditText nationalIdEdit;
    @InjectView(R.id.nationalId_container)
    CustomTextInputLayout nationalIdContainer;
    @InjectView(R.id.date_of_birthEdit)
    EditText dateOfBirthEdit;
    @InjectView(R.id.dateOfBirth_container)
    CustomTextInputLayout dateOfBirthContainer;
    @InjectView(R.id.submitButton)
    Button submitButton;
    @InjectView(R.id.submitRippleView)
    RippleView submitRippleView;
    private String android_id;
    PreferenceManager preferenceManager;
    private MaterialDialog sessionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.inject(this);
        submitRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                submitClick();
            }
        });
        skipRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                skipClick();
            }
        });
        dateOfBirthEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ProfileUpdateActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(false);
                dpd.setMaxDate(now);
                dpd.vibrate(true);
                dpd.dismissOnPause(true);
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

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
            String dobText = dateOfBirthEdit.getText().toString();
            dobText = dobText.replace("/", "");
            innerObject.put("DOB", dobText);
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
                    } else if (profileUpdateModel.getCommand().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        Logger.d("Balance", profileUpdateModel.toString());
                        sessionDialog = new MaterialDialog(ProfileUpdateActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SessionClearTask sessionClearTask = new SessionClearTask(ProfileUpdateActivity.this, false);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else {
                        Logger.d("Balance", profileUpdateModel.toString());
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

    void skipClick() {
        startActivity(new Intent(ProfileUpdateActivity.this, HomeActivity.class));

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateOfBirthEdit.setText(date);
    }
}
