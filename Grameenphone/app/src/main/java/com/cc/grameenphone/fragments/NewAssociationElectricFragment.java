package com.cc.grameenphone.fragments;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.CompanyListModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.api_models.OtherPaymentModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ManageAssociationApi;
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
 * Created by rajkiran on 21/09/15.
 */
public class NewAssociationElectricFragment extends BaseTabFragment {


    ManageAssociationApi associationApi;
    ProgressDialog loadingDialog;
    @InjectView(R.id.custodial_radiogroup)
    RadioGroup custodialRadiogroup;
    @InjectView(R.id.companyRadioGroupScroll)
    ScrollView companyRadioGroupScroll;
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
    private String android_id;
    private PreferenceManager preferenceManager;
    private OtherPaymentApi otherPaymentApi;
    MaterialDialog confirmationDialog;
    LinearLayout ll;
    RadioButton comapanyOptions;
    private List<OtherPaymentCompanyModel> companyList;
    private int numberOfCompany;
    RadioGroup rg;
    String selectedCompany;

    public static NewAssociationElectricFragment newInstance(Bundle b) {
        NewAssociationElectricFragment electricityTab = new NewAssociationElectricFragment();
        electricityTab.setArguments(b);
        return electricityTab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_association_frament, container, false);
        ButterKnife.inject(this, v);
        getCompaniesDetails();

        return v;


    }

    private void getCompaniesDetails() {
        //TODO implement other bills details
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMessage("Loading companies..");
        loadingDialog.show();

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
                                        Toast.makeText(getActivity(), "Selected company" + btn.getText(), Toast.LENGTH_LONG).show();
                                        selectedCompany = btn.getText().toString();
                                        return;
                                    }
                                }
                            }
                        });

                        loadingDialog.dismiss();
                        //TODO Submitting amount, surcharge amount
                        /*sbmtBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

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
                            }
                        });*/
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


        } catch (JSONException e) {

        }
    }

    @Override
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void fetchList() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "FBILASCREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("sending json", jsonObject.toString());
            associationApi.fetchAssociaition(jsonObject, new Callback<CompanyListModel>() {
                @Override
                public void success(CompanyListModel companyListModel, Response response) {
                    Logger.d("Companyies ", companyListModel.toString());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
