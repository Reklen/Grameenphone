package com.cc.grameenphone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.BillPaymentActivity;
import com.cc.grameenphone.activity.QuickPayActivity;
import com.cc.grameenphone.api_models.QuickPayConfirmModel;
import com.cc.grameenphone.api_models.QuickPayModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.QuickPayApi;
import com.cc.grameenphone.utils.Logger;
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
 * Created by rajkiran on 18/09/15.
 */
public class QuickBillPayFragment extends Fragment {


    MaterialDialog confirmDialog;
    @InjectView(R.id.company_text)
    TextView companyText;
    @InjectView(R.id.companyName)
    TextView companyName;
    @InjectView(R.id.container_one)
    LinearLayout containerOne;
    @InjectView(R.id.accountNmb_text)
    TextView accountNmbText;
    @InjectView(R.id.accountNumber)
    TextView accountNumber;
    @InjectView(R.id.container_two)
    LinearLayout containerTwo;
    @InjectView(R.id.billNumb_text)
    TextView billNumbText;
    @InjectView(R.id.billNumber)
    TextView billNumber;
    @InjectView(R.id.container_three)
    LinearLayout containerThree;
    @InjectView(R.id.totalAmountEditText)
    EditText totalAmountEditText;
    @InjectView(R.id.surchargeAmountEditText)
    EditText surchargeAmountEditText;
    @InjectView(R.id.dueDate_text)
    TextView dueDateText;
    @InjectView(R.id.dueDate)
    TextView dueDate;
    @InjectView(R.id.container_six)
    LinearLayout containerSix;
    @InjectView(R.id.pinNumbEdit)
    EditText pinNumbEdit;
    @InjectView(R.id.PinConform_container)
    TextInputLayout PinConformContainer;
    @InjectView(R.id.confirm_btn)
    Button confirmBtn;
    @InjectView(R.id.confirmRipple)
    RippleView confirmRipple;
    @InjectView(R.id.billPaymentLayout)
    LinearLayout billPaymentLayout;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog materialDialog, errorDialog;

    QuickPayApi quickPayApi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill_pay, container, false);
        ButterKnife.inject(this, view);

        //Caaling to get entire quick payment details
        getQuickPayDetails();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    //TODO Implement Quickpay
    public void getQuickPayDetails() {

        preferenceManager = new PreferenceManager(getActivity());
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        quickPayApi = ServiceGenerator.createService(QuickPayApi.class);
        String quickPayCode = getArguments().getString("QUICKPAYCODE");
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "QCKBILLDEL");
            innerObject.put("BILLCODE", quickPayCode);
            jsonObject.put("COMMAND", innerObject);
            Logger.d("Qickpay Bill Fragment", jsonObject.toString());
            quickPayApi.quickPay(jsonObject, new Callback<QuickPayModel>() {
                @Override
                public void success(final QuickPayModel quickPayModel, Response response) {
                    if (quickPayModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Quickpay Bill Success", quickPayModel.toString());
                        companyName.setText("" + quickPayModel.getCOMMAND().getCOMPNAME());
                        accountNumber.setText("" + quickPayModel.getCOMMAND().getACCNUM());
                        billNumber.setText("" + quickPayModel.getCOMMAND().getBILLNUM());
                        totalAmountEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                try {
                                    if (totalAmountEditText.getText().charAt(totalAmountEditText.length() - 1) == '৳') {
                                        totalAmountEditText.setText("৳ ");
                                        totalAmountEditText.setSelection(totalAmountEditText.getText().length());
                                    }
                                } catch (Exception e) {
                                    totalAmountEditText.setText("৳ ");
                                    totalAmountEditText.setSelection(totalAmountEditText.getText().length());
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        surchargeAmountEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                try {
                                    if (surchargeAmountEditText.getText().charAt(surchargeAmountEditText.length() - 1) == '৳') {
                                        surchargeAmountEditText.setText("৳ ");
                                        surchargeAmountEditText.setSelection(surchargeAmountEditText.getText().length());
                                    }
                                } catch (Exception e) {
                                    surchargeAmountEditText.setText("৳ ");
                                    surchargeAmountEditText.setSelection(surchargeAmountEditText.getText().length());
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        totalAmountEditText.setText("৳ " + quickPayModel.getCOMMAND().getAMOUNT());

                        surchargeAmountEditText.setText("৳ " + surchargeAmountEditText.getText().toString());
                        dueDate.setText("" + quickPayModel.getCOMMAND().getDUEDATE());

                        //Confirm payment
                        confirmRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                            @Override
                            public void onComplete(RippleView rippleView) {

                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    JSONObject innerObject = new JSONObject();
                                    innerObject.put("DEVICEID", android_id);
                                    innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                                    innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
                                    innerObject.put("TYPE", "CPMBCBREQ");
                                    innerObject.put("BILLCCODE", quickPayModel.getCOMMAND().getCOMPNAME().toUpperCase());
                                    innerObject.put("BILLANO", quickPayModel.getCOMMAND().getACCNUM());
                                    innerObject.put("AMOUNT", quickPayModel.getCOMMAND().getAMOUNT());
                                    innerObject.put("BILLNO", quickPayModel.getCOMMAND().getBILLNUM());
                                    innerObject.put("BPROVIDER", quickPayModel.getCOMMAND().getBPROVIDER());
                                    innerObject.put("PIN", pinNumbEdit.getText().toString());
                                    jsonObject.put("COMMAND", innerObject);
                                    Logger.d("Qickpay Bill Congirmation Strinv", jsonObject.toString());
                                    quickPayApi.quickPayConfirm(jsonObject, new Callback<QuickPayConfirmModel>() {
                                        @Override
                                        public void success(QuickPayConfirmModel quickPayConfirmModel, Response response) {
                                            if (quickPayConfirmModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                                                Logger.d("Qickpay Bill Congirmation Success", quickPayConfirmModel.toString());
                                                confirmDialog = new MaterialDialog(getActivity());
                                                confirmDialog.setMessage(quickPayConfirmModel.getCOMMAND().getMESSAGE() + "");
                                                confirmDialog.setPositiveButton("OK", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        confirmDialog.dismiss();
                                                        pinNumbEdit.setText("");
                                                        totalAmountEditText.setText("");
                                                        startActivity(new Intent(getActivity(), BillPaymentActivity.class));
                                                        getActivity().finish();
                                                    }
                                                });
                                                confirmDialog.show();

                                            } else {
                                                Logger.e("Quick pay confirmation not success ", "status " + quickPayConfirmModel.toString());
                                                errorDialog = new MaterialDialog(getActivity());
                                                errorDialog.setMessage(quickPayConfirmModel.getCOMMAND().getMESSAGE() + "");
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

                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                    } else {
                        Logger.e("Quick pay not success ", "status " + quickPayModel.toString());
                        errorDialog = new MaterialDialog(getActivity());
                        errorDialog.setMessage(quickPayModel.getCOMMAND().getMESSAGE() + "");
                        errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                                startActivity(new Intent(getActivity(), QuickPayActivity.class));
                                getActivity().finish();
                            }
                        });
                        errorDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getKind() == RetrofitError.Kind.NETWORK) {
                        Logger.e("Failuare", error.getMessage());
                    } else {
                        Logger.e("Failuare", error.getKind() + " " + error.getUrl() + " " + error.getBody() + " " + error.getResponse() + " " + error.getMessage());
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}