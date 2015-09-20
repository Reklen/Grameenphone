package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.PinChangeModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.PinchangeApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.MyPasswordTransformationMethod;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rajkiran on 09/09/15.
 */
public class PinChangeFragment extends Fragment {

    AppCompatDialog confirmDialog;
    Button okbtn;
    @InjectView(R.id.oldPinEditText)
    EditText oldPinEditText;
    @InjectView(R.id.newPineditText)
    EditText newPineditText;
    @InjectView(R.id.confirmPinEditText)
    EditText confirmPinEditText;
    @InjectView(R.id.confirmButton)
    Button confirmButton;

    ProgressDialog loadingDialog;
    PinchangeApi pinchnageApi;
    @InjectView(R.id.oldPinEditTextLayout)
    TextInputLayout oldPinEditTextLayout;
    @InjectView(R.id.newPineditTextLayout)
    TextInputLayout newPineditTextLayout;
    @InjectView(R.id.confirmPinEditTextLayout)
    TextInputLayout confirmPinEditTextLayout;
    @InjectView(R.id.confirmRipple)
    RippleView confirmRipple;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog successSignupDialog, errorDialog;

    public PinChangeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.pin_change, container, false);
        ButterKnife.inject(this, rootView);

        // Inflate the layout for this fragment
        oldPinEditText.setTransformationMethod(new MyPasswordTransformationMethod());
        newPineditText.setTransformationMethod(new MyPasswordTransformationMethod());
        confirmPinEditText.setTransformationMethod(new MyPasswordTransformationMethod());

        confirmRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
               confirmClick();
            }
        });
        return rootView;
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

    void confirmClick() {

        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(getActivity());
        pinchnageApi = ServiceGenerator.createService(PinchangeApi.class);

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            if ((newPineditText.getText().toString().length()) == 4) {
                innerObject.put("NEWPIN", newPineditText.getText().toString());
            } else {
                newPineditText.setError("Pin code must be four digits");
                newPineditText.requestFocus();
            }
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            if ((confirmPinEditText.getText().toString().length()) == 4) {
                innerObject.put("CONFIRMPIN", confirmPinEditText.getText().toString());
            } else {
                confirmPinEditText.setError("Pin code must be four digits");
                confirmPinEditText.requestFocus();
            }
            innerObject.put("TYPE", "CCPNREQ");
            if ((oldPinEditText.getText().toString().length()) == 4) {
                innerObject.put("PIN", oldPinEditText.getText().toString());
            } else {
                oldPinEditText.setError("Pin code must be four digits");
                oldPinEditText.requestFocus();
            }
            jsonObject.put("COMMAND", innerObject);
            Logger.d("PinChange", jsonObject.toString());
            pinchnageApi.pinchange(jsonObject, new Callback<PinChangeModel>() {
                @Override
                public void success(PinChangeModel pinChangeModel, Response response) {
                    Logger.d("Its pin change ", "status " + pinChangeModel.toString());
                    if (pinChangeModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        successSignupDialog = new MaterialDialog(getActivity());
                        successSignupDialog.setMessage(pinChangeModel.getCommand().getMESSAGE() + "");
                        successSignupDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                successSignupDialog.dismiss();
                                oldPinEditText.setText("");
                                newPineditText.setText("");
                                confirmPinEditText.setText("");

                            }
                        });
                        successSignupDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    errorDialog = new MaterialDialog(getActivity());
                    errorDialog.setMessage(error.getMessage() + "");
                    errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            errorDialog.dismiss();
                        }
                    });
                    errorDialog.show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
