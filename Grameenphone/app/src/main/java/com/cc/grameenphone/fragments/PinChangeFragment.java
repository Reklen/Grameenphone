package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.HomeActivity;
import com.cc.grameenphone.api_models.PinChangeModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.PinchangeApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.MyPasswordTransformationMethod;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by rajkiran on 09/09/15.
 */
public class PinChangeFragment extends Fragment implements ValidationListener {

    AppCompatDialog confirmDialog;
    Button okbtn;

    @NotEmpty
    @Bind(R.id.oldPinEditText)
    EditText oldPinEditText;
    @NotEmpty
    @Bind(R.id.newPineditText)
    EditText newPineditText;
    @NotEmpty
    @Bind(R.id.confirmPinEditText)
    EditText confirmPinEditText;
    @Bind(R.id.confirmButton)
    Button confirmButton;

    ProgressDialog loadingDialog;
    PinchangeApi pinchnageApi;
    @Bind(R.id.oldPinEditTextLayout)
    TextInputLayout oldPinEditTextLayout;
    @Bind(R.id.newPineditTextLayout)
    TextInputLayout newPineditTextLayout;
    @Bind(R.id.confirmPinEditTextLayout)
    TextInputLayout confirmPinEditTextLayout;
    @Bind(R.id.confirmRipple)
    RippleView confirmRipple;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog successSignupDialog, errorDialog;
    private boolean isPinCons = false, isPinRep = false;

    Validator validator;

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
        ButterKnife.bind(this, rootView);
        validator = new Validator(this);
        validator.setValidationListener(this);
        // Inflate the layout for this fragment
        oldPinEditText.setTransformationMethod(new MyPasswordTransformationMethod());
        newPineditText.setTransformationMethod(new MyPasswordTransformationMethod());
        confirmPinEditText.setTransformationMethod(new MyPasswordTransformationMethod());

        confirmRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkPinValidationFirst();
            }
        });
        return rootView;
    }

    private void checkPinValidationFirst() {
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
        Matcher regexMatcher = consPattern.matcher(newPineditText.getText().toString());
        if (regexMatcher.matches()) {
            //Logger.d("Pattern", "matches");
            isPinCons = true;
        } else {
            Logger.e("Pattern", "does not match");
            isPinCons = false;
        }


        Pattern pattern = Pattern.compile("([0-9])\\1{3}");
        Matcher matcher = pattern.matcher(newPineditText.getText().toString());
        if (matcher.find()) {
            //Logger.d("PatternRep", "matches");
            isPinRep = true;
        } else {
            Logger.e("PatternRep", "does not match");
            isPinRep = false;

        }

        if (isPinCons) {
            Toast.makeText(getActivity(), "Password cant contain consecutive digits", Toast.LENGTH_SHORT).show();
            newPineditText.setError("Password error");
            confirmPinEditText.setText("");
            newPineditText.requestFocus();
            newPineditText.setText("");

            return;
        }
        if (isPinRep) {
            Toast.makeText(getActivity(), "Password cant contain repetitive digits", Toast.LENGTH_SHORT).show();
            newPineditText.setError("Password error");
            confirmPinEditText.setText("");
            newPineditText.setText("");
            newPineditText.requestFocus();

            return;
        }

        validator.validate();

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
        ButterKnife.unbind(this);
    }

    void confirmClick() {

        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(getActivity());
        pinchnageApi = ServiceGenerator.createService(getActivity(),PinchangeApi.class);

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            if ((newPineditText.getText().toString().length()) >= 4) {
                innerObject.put("NEWPIN", newPineditText.getText().toString());
            } else {
                newPineditText.setError("Pin code must be four digits");
                newPineditText.requestFocus();
                return;
            }
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            if ((confirmPinEditText.getText().toString().length()) >= 4) {
                innerObject.put("CONFIRMPIN", confirmPinEditText.getText().toString());
            } else {
                confirmPinEditText.setError("Pin code must be four digits");
                confirmPinEditText.requestFocus();
                return;
            }
            if (!newPineditText.getText().toString().equalsIgnoreCase(confirmPinEditText.getText().toString())) {
                Toast.makeText(getActivity(), "Pin code dont match", Toast.LENGTH_SHORT).show();
                confirmPinEditText.setText("");
                confirmPinEditText.setError("Pin code must be four digits");
                confirmPinEditText.requestFocus();
                return;
            }
            innerObject.put("TYPE", "CCPNREQ");
            if ((oldPinEditText.getText().toString().length()) >= 4) {
                innerObject.put("PIN", oldPinEditText.getText().toString());
            } else {
                oldPinEditText.setError("Pin code must be four digits");
                oldPinEditText.requestFocus();
                return;
            }
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("PinChange", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            pinchnageApi.pinChange(in, new Callback<PinChangeModel>() {
                @Override
                public void success(PinChangeModel pinChangeModel, Response response) {
                    //Logger.d("Its pin change ", "status " + pinChangeModel.toString());
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
                                startActivity(new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                getActivity().finish();


                            }
                        });
                        successSignupDialog.show();
                    } else {
                        errorDialog = new MaterialDialog(getActivity());
                        errorDialog.setMessage(pinChangeModel.getCommand().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                                oldPinEditText.setText("");
                                newPineditText.setText("");
                                confirmPinEditText.setText("");

                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onValidationSucceeded() {
        confirmClick();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
                ((EditText) view).setText("");
                ((EditText) view).requestFocus();
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
