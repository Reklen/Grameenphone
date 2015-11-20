package com.cc.grameenphone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.HomeActivity;
import com.cc.grameenphone.api_models.QuickPayConfirmModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.QuickPayApi;
import com.cc.grameenphone.utils.IntentUtils;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

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
    TextView accountNumberTV;
    @InjectView(R.id.container_two)
    LinearLayout containerTwo;
    @InjectView(R.id.billNumb_text)
    TextView billNumbText;
    @InjectView(R.id.billNumber)
    TextView billNumberTV;
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
    String billCCode, accountNumber, billAmount, billNumber, bProvider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill_pay, container, false);
        ButterKnife.inject(this, view);
        quickPayApi = ServiceGenerator.createService(QuickPayApi.class);
        handleArguments();
        //Confirm payment
        confirmRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                preferenceManager = new PreferenceManager(getActivity());

                try {
                    JSONObject jsonObject = new JSONObject();
                    JSONObject innerObject = new JSONObject();
                    innerObject.put("DEVICEID", android_id);
                    innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                    innerObject.put("MSISDN",  preferenceManager.getMSISDN());
                    innerObject.put("TYPE", "CPMBCBREQ");
                    innerObject.put("BILLCCODE", billCCode.toUpperCase());
                    innerObject.put("BILLANO", accountNumber);
                    innerObject.put("AMOUNT", billAmount);
                    innerObject.put("BILLNO", billNumber);
                    innerObject.put("BPROVIDER", bProvider);
                    innerObject.put("PIN", pinNumbEdit.getText().toString());
                    jsonObject.put("COMMAND", innerObject);
                    Logger.d("Qickpay Bill Congirmation Strinv", jsonObject.toString());
                    String json = jsonObject.toString();
                    TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
                    quickPayApi.quickPayConfirm(in, new Callback<QuickPayConfirmModel>() {
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
                                        startActivity(new Intent(getActivity(), HomeActivity.class));
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });

        return view;
    }

    private void handleArguments() {
        Bundle bundle = getArguments();

        billCCode = bundle.getString(IntentUtils.QUICK_PAY_COMPANY_NAME);
        accountNumber = bundle.getString(IntentUtils.QUICK_PAY_ACCOUNT_NUM);
        billNumber = bundle.getString("" + IntentUtils.QUICK_PAY_BILL_NUM);
        billAmount = bundle.getString("" + IntentUtils.QUICK_PAY_TOTAL_AMOUNT);
        bProvider = bundle.getString("" + IntentUtils.QUICK_PAY_BILL_PROVIDER);

        companyName.setText("" + billCCode);
        accountNumberTV.setText("" + accountNumber);
        billNumberTV.setText("" + billNumber);
        totalAmountEditText.setText("৳ " + billAmount);
        surchargeAmountEditText.setText("৳ " + bundle.getString(IntentUtils.QUICK_PAY_SURCHARGE));
        dueDate.setText("" + bundle.getString(IntentUtils.QUICK_PAY_DUE_DATE));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}