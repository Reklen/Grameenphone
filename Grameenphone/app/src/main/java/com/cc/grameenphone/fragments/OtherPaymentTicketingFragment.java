package com.cc.grameenphone.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.BillConfirmationModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.api_models.OtherPaymentModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.OtherPaymentApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class OtherPaymentTicketingFragment extends BaseTabFragment {
    @InjectView(R.id.custodial_radiogroup)
    LinearLayout custodialRadiogroup;
    @InjectView(R.id.account_numbEdit)
    EditText accountNumbEdit;
    @InjectView(R.id.account_numb_container)
    TextInputLayout accountNumbContainer;
    @InjectView(R.id.bill_numbEdit)
    EditText billNumbEdit;
    @InjectView(R.id.bill_numb_container)
    TextInputLayout billNumbContainer;
    @InjectView(R.id.sbmt_btn)
    Button sbmtBtn;
    @InjectView(R.id.electricity_container)
    RelativeLayout electricityContainer;
    @InjectView(R.id.companyRadioGroupScroll)
    ScrollView companyRadioGroupScroll;
    @InjectView(R.id.amountEditText)
    EditText amountEditText;
    @InjectView(R.id.amountTextInputLayout)
    TextInputLayout amountTextInputLayout;
    @InjectView(R.id.surchargeEditText)
    EditText surchargeEditText;
    @InjectView(R.id.surchargeTextInputLayout)
    TextInputLayout surchargeTextInputLayout;

    private String android_id;
    private PreferenceManager preferenceManager;
    private OtherPaymentApi otherPaymentApi;
    MaterialDialog confirmationDialog, errorDialog;
    LinearLayout ll;
    RadioButton comapanyOptions;
    private List<OtherPaymentCompanyModel> companyList;
    private int numberOfCompany;
    RadioGroup rg;
    String selectedCompany;

    public static OtherPaymentTicketingFragment newInstance(Bundle b) {
        OtherPaymentTicketingFragment ticketingTabFragment = new OtherPaymentTicketingFragment();
        ticketingTabFragment.setArguments(b);
        return ticketingTabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.other_payments_fragment, container, false);
        ButterKnife.inject(this, v);
        addRadioButtons(10);
        getCompaniesDetails();
        return v;
    }


  /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleArguments();
    }

    private void handleArguments() {

        Bundle b;
        try {
            b = getArguments();
            Logger.d("argu", b.toString());
            type = getArguments().getInt("type");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void getCompaniesDetails() {
        //TODO implement other bills details


        otherPaymentApi = ServiceGenerator.createService(OtherPaymentApi.class);
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(getActivity());
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CTCMPLREQ");
            innerObject.put("BILLCCODE", "GAS");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("electric fragment request ", jsonObject.toString());

            otherPaymentApi.otherPayment(jsonObject, new Callback<OtherPaymentModel>() {
                @Override
                public void success(OtherPaymentModel otherPaymentModel, Response response) {

                    if (otherPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {

                        Logger.d("Response string ", otherPaymentModel.getCOMMAND().getCOMPANYDET().toString());
                        // TODO display companies details and after selection next actions
                        //addRadioButtons(Integer.valueOf(otherPaymentModel.getCOMMAND().getNOOFCOM().toString()));
                        // Number of company will be passed as per response
                        numberOfCompany = Integer.valueOf(otherPaymentModel.getCOMMAND().getNOOFCOM().toString());
                        companyList = otherPaymentModel.getCOMMAND().getCOMPANYDET();
                        for (int k = 0; k < 1; k++) {
                            final RadioButton[] rb = new RadioButton[numberOfCompany];
                            rg = new RadioGroup(getActivity());
                            rg.setOrientation(RadioGroup.VERTICAL);
                            for (int i = 0; i < numberOfCompany; i++) {
                                rb[i] = new RadioButton(getActivity());
                                rg.addView(rb[i]);
                                rb[i].setPadding(10, 0, 0, 0);
                                rb[i].setCompoundDrawablePadding(50);
                                rb[i].setTextSize(15);
                                rb[i].setAllCaps(true);
                                rb[i].setTextColor(Color.parseColor("#666666"));
                                rb[i].setText(companyList.get(i).getCOMPCODE());

                            }
                            custodialRadiogroup.addView(rg);
                        }


                        // TODO check the company option selected to perform next operations


                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup radioGroup, int pos) {
                                for (int i = 0; i < rg.getChildCount(); i++) {
                                    RadioButton btn = (RadioButton) rg.getChildAt(i);
                                    if (btn.getId() == pos) {
                                        // Toast.makeText(getActivity(), "Selected company" + btn.getText(), Toast.LENGTH_LONG).show();
                                        selectedCompany = btn.getText().toString();
                                        return;
                                    }
                                }
                            }
                        });


                        //TODO Submitting amount, surcharge amount
                        //TODO Submitting amount, surcharge amount
                        sbmtBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    JSONObject innerObject = new JSONObject();
                                    innerObject.put("DEVICEID", android_id);
                                    innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                                    innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
                                    innerObject.put("TYPE", "CPMBREQ");
                                    innerObject.put("BILLCCODE", selectedCompany);
                                    if (accountNumbEdit.getText().toString() != null)
                                        innerObject.put("BILLANO", accountNumbEdit.getText().toString());//coomented for testing
                                    else
                                        accountNumbEdit.setError("Account number is empty");
                                    if (amountEditText.getText().toString() != null)
                                        innerObject.put("AMOUNT", amountEditText.getText().toString());
                                    else
                                        amountEditText.setError("Amount must be fill");
                                    if (billNumbEdit.getText().toString() != null)
                                        innerObject.put("BILLNO", billNumbEdit.getText().toString());
                                    else
                                        billNumbEdit.setError("Bill number is empty");
                                    innerObject.put("BPROVIDER", "101");
                                    //if (otherPaymentModel.getCOMMAND().getCOMPANYDET().get(selectedSurchargePos).getSURCREQ().equalsIgnoreCase("Y"))
                                    innerObject.put("SURCHARGE", "1"/*surchargeEditText.getText().toString()*/);
                                    // else
                                    // innerObject.put("SURCHARGE", "0");
                                    innerObject.put("PIN", preferenceManager.getPINCode());
                                    jsonObject.put("COMMAND", innerObject);
                                    Logger.d("confirmaing bill payment ", jsonObject.toString());
                                    otherPaymentApi.billConfirmation(jsonObject, new Callback<BillConfirmationModel>() {
                                        @Override
                                        public void success(BillConfirmationModel billConfirmationModel, Response response) {
                                            if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                                                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.confirmation_dialog, null);
                                                confirmationDialog = new MaterialDialog(getActivity());
                                                confirmationDialog.setView(dialogView);

                                                Button confirmButton = (Button) dialogView.findViewById(R.id.confirmDialogButton);
                                                confirmButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        confirmationDialog.dismiss();
                                                    }
                                                });
                                                confirmationDialog.show();
                                            } else  if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("00292")) {
                                                errorDialog = new MaterialDialog(getActivity());
                                                errorDialog.setMessage(billConfirmationModel.getCOMMAND().getMESSAGE());
                                                errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        errorDialog.dismiss();
                                                    }
                                                });
                                                errorDialog.show();
                                            }
                                            else  if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("00351")) {
                                                errorDialog = new MaterialDialog(getActivity());
                                                errorDialog.setMessage(billConfirmationModel.getCOMMAND().getMESSAGE());
                                                errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        errorDialog.dismiss();
                                                    }
                                                });
                                                errorDialog.show();
                                            }
                                            else {
                                                errorDialog = new MaterialDialog(getActivity());
                                                errorDialog.setMessage(billConfirmationModel.getCOMMAND().getMESSAGE());
                                                errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
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

                                }


                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


        } catch (JSONException e) {

        }
    }
}
