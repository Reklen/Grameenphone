package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ProfileModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ProfileUpdateApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by rajkiran on 09/09/15.
 */
public class ProfileFragment extends Fragment {
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
    MaterialDialog materialDialog;
    ProfileUpdateApi profileDisplay;
    ProgressDialog loadingDialog;

    ProfileModel profileModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment_layout, container, false);
        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);
        displayProfile();
        return rootView;
    }

    private void displayProfile() {
        //TODO Implement fetching profile data
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMessage("Loading profile..");
        loadingDialog.show();
        preferenceManager = new PreferenceManager(getActivity());
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        profileDisplay = ServiceGenerator.createService(ProfileUpdateApi.class);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN",  preferenceManager.getMSISDN());
            innerObject.put("TYPE", "SUBDATAREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("Profile Fetch Data", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            profileDisplay.profile(in, new Callback<ProfileModel>() {
                @Override
                public void success(ProfileModel profileModel, Response response) {
                    if (profileModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Profile Success" + profileModel.toString());
                        try {
                            setProfileModel(profileModel);
                            firstName.setText("" + profileModel.getCOMMAND().getFNAME());
                            lastName.setText("" + profileModel.getCOMMAND().getLNAME());
                            emailName.setText("" + profileModel.getCOMMAND().getEMAIL());
                            nationalId.setText("" + profileModel.getCOMMAND().getIDNO());
                            dob.setText("" + profileModel.getCOMMAND().getDOB());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public ProfileModel getProfileModel() {
        return profileModel;
    }

    public void setProfileModel(ProfileModel profileModel) {
        this.profileModel = profileModel;
    }
}
