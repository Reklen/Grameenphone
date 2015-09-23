package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ProfileModel;
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


public class EditProfileActivity extends AppCompatActivity {


    @InjectView(R.id.button)
    Button button;
    @InjectView(R.id.vertical_view)
    View verticalView;
    @InjectView(R.id.button_save)
    Button buttonSave;
    @InjectView(R.id.first_name)
    EditText firstName;
    @InjectView(R.id.firstNameTextInputLayout)
    TextInputLayout firstNameTextInputLayout;
    @InjectView(R.id.last_name)
    EditText lastName;
    @InjectView(R.id.lastNameTextInputLayout)
    TextInputLayout lastNameTextInputLayout;
    @InjectView(R.id.email_name)
    EditText emailName;
    @InjectView(R.id.emailTextInputLayout)
    TextInputLayout emailTextInputLayout;
    @InjectView(R.id.national_id)
    EditText nationalId;
    @InjectView(R.id.nationalTextInputLayout)
    TextInputLayout nationalTextInputLayout;
    @InjectView(R.id.dob)
    EditText dob;
    @InjectView(R.id.dobTextInputLayout)
    TextInputLayout dobTextInputLayout;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog successDialog, errorDialog;
    ProfileUpdateApi profileDisplay, profileUpdateApi;
    String email, fName, lName, dateOfBirth, nationId;
    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.inject(this);
        displayProfile();
        emailName.requestFocus();
    }

    private void displayProfile() {
        //TODO Implement fetching profile data
        loadingDialog = new ProgressDialog(EditProfileActivity.this);
        loadingDialog.setMessage("Loading companies..");
        loadingDialog.show();
        preferenceManager = new PreferenceManager(EditProfileActivity.this);
        android_id = Settings.Secure.getString(EditProfileActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        profileDisplay = ServiceGenerator.createService(ProfileUpdateApi.class);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "SUBDATAREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("Profile Fetch Data", jsonObject.toString());
            profileDisplay.profile(jsonObject, new Callback<ProfileModel>() {
                @Override
                public void success(ProfileModel profileModel, Response response) {
                    if (profileModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Profile Success" + profileModel.toString());
                        firstName.setText("" + profileModel.getCOMMAND().getFNAME());
                        fName = profileModel.getCOMMAND().getFNAME();
                        lastName.setText("" + profileModel.getCOMMAND().getLNAME());
                        lName = profileModel.getCOMMAND().getLNAME();
                        emailName.setText("" + profileModel.getCOMMAND().getEMAIL());
                        email = profileModel.getCOMMAND().getEMAIL().toString();
                        nationalId.setText("" + profileModel.getCOMMAND().getIDNO());
                        nationId = profileModel.getCOMMAND().getIDNO();
                        dob.setText("" + profileModel.getCOMMAND().getDOB());
                        dateOfBirth = profileModel.getCOMMAND().getDOB();

                    } else {

                        //Dont know
                        Logger.d("Profile fetch failed");
                    }
                    loadingDialog.dismiss();
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.d("Retrofit failure" + error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @OnClick(R.id.button)
    public void clickCancel() {
        finish();
    }

    @OnClick(R.id.button_save)
    public void clickSave() {

        profileUpdateApi = ServiceGenerator.createService(ProfileUpdateApi.class);
        if (emailName.getText().toString() != email) {
            try {
                JSONObject jsonObject = new JSONObject();
                JSONObject innerObject = new JSONObject();
                innerObject.put("DEVICEID", android_id);
                innerObject.put("FNAME", fName);
                innerObject.put("LNAME", lName);
                if (isValidEmail(emailName.getText().toString())) {
                    innerObject.put("EMAIL", emailName.getText().toString());
                } else {
                    Toast.makeText(this, "Invalid email id", Toast.LENGTH_LONG).show();
                    emailName.requestFocus();
                }
                innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
                innerObject.put("DOB", dateOfBirth);
                innerObject.put("IDNO", nationId);
                innerObject.put("TYPE", "PRFLUPDATE");
                innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                jsonObject.put("COMMAND", innerObject);
                Logger.d("ProfileUpdates", jsonObject.toString());
                profileUpdateApi.profileUpdate(jsonObject, new Callback<ProfileUpdateModel>() {
                    @Override
                    public void success(ProfileUpdateModel profileUpdateModel, Response response) {
                        Logger.d("Email change success change ", "status " + profileUpdateModel.toString());
                        if (profileUpdateModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                            successDialog = new MaterialDialog(EditProfileActivity.this);
                            successDialog.setMessage(profileUpdateModel.getCommand().getMESSAGE());
                            successDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    emailName.setText("" + emailName.getText().toString());
                                    successDialog.dismiss();
                                    startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
                                    finish();
                                }
                            });
                            successDialog.show();
                        } else  {
                            errorDialog = new MaterialDialog(EditProfileActivity.this);
                            errorDialog.setMessage(profileUpdateModel.getCommand().getMESSAGE());
                            errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    errorDialog.dismiss();
                                    startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
                                    finish();
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

        } else {
            errorDialog = new MaterialDialog(EditProfileActivity.this);
            errorDialog.setMessage("No change in email");
            errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    errorDialog.dismiss();
                    startActivity(new Intent(EditProfileActivity.this, HomeActivity.class));
                    finish();
                }
            });
            errorDialog.show();
        }
    }
}
