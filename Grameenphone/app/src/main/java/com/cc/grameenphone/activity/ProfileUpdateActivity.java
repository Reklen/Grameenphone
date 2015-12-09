package com.cc.grameenphone.activity;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
public class ProfileUpdateActivity extends Activity implements DatePickerDialog.OnDateSetListener, ValidationListener {


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

    @NotEmpty
    @InjectView(R.id.first_nameEdit)
    EditText firstNameEdit;
    @InjectView(R.id.firstName_container)
    CustomTextInputLayout firstNameContainer;

    @NotEmpty
    @InjectView(R.id.last_nameEdit)
    EditText lastNameEdit;
    @InjectView(R.id.lastName_container)
    CustomTextInputLayout lastNameContainer;
    @NotEmpty
    @InjectView(R.id.email_idEdit)
    EditText emailIdEdit;

    @InjectView(R.id.emailid_container)
    CustomTextInputLayout emailidContainer;

    @NotEmpty
    @InjectView(R.id.national_idEdit)
    EditText nationalIdEdit;
    @InjectView(R.id.nationalId_container)
    CustomTextInputLayout nationalIdContainer;

    @NotEmpty
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
    private Validator validator;

    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.inject(this);
        loadingDialog = new ProgressDialog(ProfileUpdateActivity.this);
        loadingDialog.setMessage("Loading..");
        submitRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                validator.validate();
                //  submitClick();
            }
        });
        validator = new Validator(ProfileUpdateActivity.this);
        validator.setValidationListener(ProfileUpdateActivity.this);
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
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            String dobText = dateOfBirthEdit.getText().toString();
            dobText = dobText.replace("/", "");
            dobText = dobText.replace("\\", "");
            innerObject.put("DOB", dobText);
            innerObject.put("IDNO", nationalIdEdit.getText().toString());
            innerObject.put("TYPE", "PRFLUPDATE");
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            jsonObject.put("COMMAND", innerObject);
            Logger.d("ProfileUpdates", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            profileUpdateApi.profileUpdate(in, new Callback<ProfileUpdateModel>() {
                @Override
                public void success(ProfileUpdateModel profileUpdateModel, Response response) {
                    Logger.d("Its pin change ", "status " + profileUpdateModel.toString());
                    loadingDialog.dismiss();
                    if (profileUpdateModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        successSignupDialog = new MaterialDialog(ProfileUpdateActivity.this);
                        successSignupDialog.setMessage(profileUpdateModel.getCommand().getMESSAGE() + "");
                        successSignupDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                successSignupDialog.dismiss();
                                startActivity(new Intent(ProfileUpdateActivity.this, HomeActivity.class));
                                finish();
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
                                SessionClearTask sessionClearTask = new SessionClearTask(ProfileUpdateActivity.this, true);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else {
                        Toast.makeText(ProfileUpdateActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                        Logger.d("ProfileUpdate", profileUpdateModel.toString());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    loadingDialog.dismiss();
                    Logger.d("Profile Update response failed ", ": " + error.getMessage().toString());
                }
            });

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void skipClick() {
        startActivity(new Intent(ProfileUpdateActivity.this, HomeActivity.class));
        finish();

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int yearInt, int monthOfYear, int dayOfMonth) {
        String date, day, month, year;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        else
            day = "" + dayOfMonth;
        if (monthOfYear < 10)
            month = "0" + (++monthOfYear);
        else
            month = "" + (++monthOfYear);

        year = "" + yearInt;

        date = day + "/" + month + "/" + year;

        dateOfBirthEdit.setText(date);
        dateOfBirthEdit.setError(null);
    }

    @Override
    public void onValidationSucceeded() {
        loadingDialog.show();
        submitClick();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        loadingDialog.cancel();
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(ProfileUpdateActivity.this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                ((EditText) view).setText("");
            } else {
                Toast.makeText(ProfileUpdateActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
