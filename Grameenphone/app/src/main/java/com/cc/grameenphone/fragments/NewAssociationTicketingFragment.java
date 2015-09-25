package com.cc.grameenphone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.TypedValue;
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
import com.cc.grameenphone.activity.HomeActivity;
import com.cc.grameenphone.api_models.BillConfirmationModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.AddAssociationApi;
import com.cc.grameenphone.interfaces.OtherPaymentApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rajkiran on 21/09/15.
 */
public class NewAssociationTicketingFragment extends BaseTabFragment implements Validator.ValidationListener {
    @InjectView(R.id.customRadioGroupLayout)
    LinearLayout custodialRadiogroup;
    @InjectView(R.id.companyRadioGroupScroll)
    ScrollView companyRadioGroupScroll;
    @NotEmpty
    @InjectView(R.id.account_numbEdit)
    EditText accountNumbEdit;
    @InjectView(R.id.account_numb_container)
    TextInputLayout accountNumbContainer;
    @NotEmpty
    @InjectView(R.id.bill_numbEdit)
    EditText billNumbEdit;
    @InjectView(R.id.bill_numb_container)
    TextInputLayout billNumbContainer;
    @InjectView(R.id.submitButton)
    Button sbmtBtn;
    @InjectView(R.id.submitRippleView)
    RippleView submitRippleView;
    @InjectView(R.id.electricity_container)
    RelativeLayout electricityContainer;
    private String android_id;
    private PreferenceManager preferenceManager;
    MaterialDialog confirmationDialog, errorDialog;
    LinearLayout ll;
    RadioButton comapanyOptions;
    private List<OtherPaymentCompanyModel> companyList;
    private int numberOfCompany;
    RadioGroup rg;
    AddAssociationApi addAssociationApi;
    int selectedSurchargePos;
    String selectedCompany;
    View pinConfirmationView;
    EditText pinConfirmationET;
    MaterialDialog pinConfirmDialog;
    Validator validator;

    public static NewAssociationTicketingFragment newInstance(Bundle b) {
        NewAssociationTicketingFragment gasTabFragment = new NewAssociationTicketingFragment();
        gasTabFragment.setArguments(b);
        return gasTabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_association_frament, container, false);
        ButterKnife.inject(this, v);
        validator = new Validator(this);
        validator.setValidationListener(this);

        //Calling method to get companies details for other bill details
        getCompaniesDetails();

        submitRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                validator.validate();

            }
        });

        return v;
    }

    private void getCompaniesDetails() {
        //TODO implement other bills details

        new RushSearch().whereEqual("CATCODE", "TCKT")
                .find(OtherPaymentCompanyModel.class, new RushSearchCallback<OtherPaymentCompanyModel>() {
                    @Override
                    public void complete(final List<OtherPaymentCompanyModel> list) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setupViews(list);
                            }
                        });
                    }
                });


    }


    /*@Override
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

    private void setupViews(List<OtherPaymentCompanyModel> list) {
        numberOfCompany = list.size();
        companyList = list;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 16, getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 10, getResources().getDisplayMetrics());

        int compoundDrawablePadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 50, getResources().getDisplayMetrics());
        layoutParams.setMargins(margins, 0, margins, 0);
        for (int k = 0; k < 1; k++) {
            final RadioButton[] rb = new RadioButton[numberOfCompany];
            rg = new RadioGroup(getActivity());
            rg.setOrientation(RadioGroup.VERTICAL);
            for (int i = 0; i < numberOfCompany; i++) {
                rb[i] = new RadioButton(getActivity());
                rg.addView(rb[i], layoutParams);
                rb[i].setPadding(padding, padding, 0, padding);
                rb[i].setCompoundDrawablePadding(compoundDrawablePadding);
                rb[i].setTextSize(15);
                rb[i].setAllCaps(true);
                rb[i].setTextColor(getActivity().getResources().getColor(R.color.black_semi_transparent));
                rb[i].setText(companyList.get(i).getCOMPNAME());
                rb[i].setTag(companyList.get(i));

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
                        selectedSurchargePos = pos;
                        selectedCompany = btn.getText().toString().toUpperCase();
                       /* Logger.d("Selcted Compnay" + selectedCompany);
                        try {
                            OtherPaymentCompanyModel companyModel = (OtherPaymentCompanyModel) btn.getTag();
                            String isSurcharge = companyModel.getSURCREQ();
                            Logger.d("Surcharge Available ", isSurcharge);
                            if (isSurcharge.equalsIgnoreCase("Y")) {
                                surchargeEditText.setVisibility(View.VISIBLE);
                            } else {
                                surchargeEditText.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                        }*/
                        return;
                    }
                }
            }
        });
        //companyRadioGroupScroll.fullScroll(ScrollView.FOCUS_UP);

    }

    void pinConfirmation() {
        pinConfirmationView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pin_confirmation, null);
        pinConfirmationET = (EditText) pinConfirmationView.findViewById(R.id.pinConfirmEditText);

        pinConfirmDialog = new MaterialDialog(getActivity());
        pinConfirmDialog.setContentView(pinConfirmationView);
        pinConfirmDialog.setPositiveButton("CONFIRM", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
                pinConfirmDialog.dismiss();

            }
        });
        pinConfirmDialog.show();
    }


    void onSubmitClick() {
        //TODO Submitting amount, surcharge amount

        preferenceManager = new PreferenceManager(getActivity());
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        addAssociationApi = ServiceGenerator.createService(AddAssociationApi.class);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "BPREGREQ");
            innerObject.put("CATEGORY", "TCKT");
            innerObject.put("PREF1", accountNumbEdit.getText().toString());
            innerObject.put("BILLCCODE", selectedCompany);
            jsonObject.put("COMMAND", innerObject);
            Logger.d("confirmaing bill payment ", jsonObject.toString());
            addAssociationApi.associationSubmit(jsonObject, new Callback<BillConfirmationModel>() {
                @Override
                public void success(BillConfirmationModel billConfirmationModel, Response response) {

                    if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {

                        confirmationDialog = new MaterialDialog(getActivity());
                        confirmationDialog.setMessage("" + billConfirmationModel.getCOMMAND().getMESSAGE());
                        confirmationDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                getActivity().finish();
                            }
                        });
                        confirmationDialog.show();

                    } else {
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


    @Override
    public void onValidationSucceeded() {

        pinConfirmation();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
