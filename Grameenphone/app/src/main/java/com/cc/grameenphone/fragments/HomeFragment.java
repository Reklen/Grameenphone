package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.BillPaymentActivity;
import com.cc.grameenphone.activity.ReferFriendsActivity;
import com.cc.grameenphone.activity.SelectContactsActivity;
import com.cc.grameenphone.activity.TransactionOverviewActivity;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.RechargeModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.RechargeApi;
import com.cc.grameenphone.interfaces.WalletCheckApi;
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

/**
 * Created by rajkiran on 09/09/15.
 */
public class HomeFragment extends Fragment {


    @InjectView(R.id.prepaidOption)
    RadioButton prepaidOption;
    @InjectView(R.id.postpaidOption)
    RadioButton postpaidOption;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.areaCode)
    TextView areaCode;
    @InjectView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @InjectView(R.id.phone_container)
    TextInputLayout phoneContainer;
    @InjectView(R.id.other_flex)
    TextView otherFlex;
    @InjectView(R.id.top_container1)
    RelativeLayout topContainer1;
    @InjectView(R.id.editamt)
    EditText editamt;
    @InjectView(R.id.amount_container)
    TextInputLayout amountContainer;
    @InjectView(R.id.flexi_btn)
    Button flexiBtn;
    @InjectView(R.id.billpay)
    ImageView billpay;
    @InjectView(R.id.bill_text)
    TextView billText;
    @InjectView(R.id.billPayment)
    RelativeLayout billPayment;
    @InjectView(R.id.trans_icon)
    ImageView transIcon;
    @InjectView(R.id.transc_text)
    TextView transcText;
    @InjectView(R.id.transactionOverview)
    RelativeLayout transactionOverview;
    @InjectView(R.id.emergencyicon)
    ImageView emergencyicon;
    @InjectView(R.id.emergency_text)
    TextView emergencyText;
    @InjectView(R.id.emergencyCall)
    RelativeLayout emergencyCall;
    @InjectView(R.id.friends)
    ImageView friends;
    @InjectView(R.id.friends_text)
    TextView friendsText;
    @InjectView(R.id.referFriends)
    RelativeLayout referFriends;

    int REQCODE = 100;
    ProgressDialog loadingDialog;
    RechargeApi rechargeApi;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog materialDialog;

    WalletCheckApi walletCheckApi;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_layout, container, false);
        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);
        preferenceManager = new PreferenceManager(getActivity());
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (prepaidOption.isChecked()) {

                } else if (postpaidOption.isChecked()) {

                }

            }
        });

        /*
        {"COMMAND":
{

"AUTHTOKEN" : "dcda029e9e311578cf648bfa7eca623651e77e1c4f8d276936ebd38c604dc0a0",
"MSISDN": "01718181818", "TYPE": "CBEREQ", "DEVICEID":"01234567890654321"
}}

         */

        walletCheckApi = ServiceGenerator.createService(WalletCheckApi.class);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CBEREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("wallet request ", jsonObject.toString());
            walletCheckApi.checkBalance(jsonObject, new Callback<BalanceEnquiryModel>() {
                @Override
                public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {
                    Logger.d("Balance", balanceEnquiryModel.toString());

                    Toast.makeText(getActivity(), "Your wallet balance is : " + balanceEnquiryModel.getCOMMAND().getBALANCE(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (JSONException e) {

        }

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

    @OnClick(R.id.billPayment)
    void billPaymentClick() {
        startActivity(new Intent(getActivity(), BillPaymentActivity.class));
    }

    @OnClick(R.id.transactionOverview)
    void transactionOverviewClick() {
        startActivity(new Intent(getActivity(), TransactionOverviewActivity.class));
    }

    @OnClick(R.id.emergencyCall)
    void emergencyClick() {

    }

    @OnClick(R.id.referFriends)
    void referFriendsClick() {
        startActivity(new Intent(getActivity(), ReferFriendsActivity.class));
    }

    MaterialDialog errorDialog;

    @OnClick(R.id.flexi_btn)
    void flexiButtonClick() {
        // Recharge psot
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        rechargeApi = ServiceGenerator.createService(RechargeApi.class);
        /*loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setMessage("Logging in");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();*/
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "CTMMREQ");
            innerObject.put("RCTYPE", "PREPAID");
            innerObject.put("AMOUNT", editamt.getText().toString());
            jsonObject.put("COMMAND", innerObject);

            rechargeApi.recharge(jsonObject, new Callback<RechargeModel>() {
                @Override
                public void success(RechargeModel rechargeModel, Response response) {
                    Logger.d("Its msisdn check ", "status " + rechargeModel.toString());
                    if (rechargeModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        View flexiDialog = LayoutInflater.from(getActivity()).inflate(R.layout.flexi_dialog_layout, null);

                        materialDialog = new MaterialDialog(getActivity()).setContentView(flexiDialog);
                        materialDialog.setCanceledOnTouchOutside(true);
                        ((TextView) flexiDialog.findViewById(R.id.top_text)).setText(rechargeModel.getCOMMAND().getMESSAGE() + "");
                        ((TextView) flexiDialog.findViewById(R.id.mobileNumber)).setText("017" + phoneNumberEditText.getText().toString() + "");
                        ((TextView) flexiDialog.findViewById(R.id.transactionNumber)).setText("\n" + rechargeModel.getCOMMAND().getTXNID() + "");

                        ((TextView) flexiDialog.findViewById(R.id.flxiloadAmount)).setText(editamt.getText().toString() + "");
                        materialDialog.show();
                        Button buttonOk = (Button) flexiDialog.findViewById(R.id.okButton);
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                materialDialog.dismiss();
                            }
                        });
                    } else {
                        errorDialog = new MaterialDialog(getActivity());
                        errorDialog.setMessage(rechargeModel.getCOMMAND().getMESSAGE() + "");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
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

    @OnClick(R.id.other_flex)
    void otherFlexiLoadClick() {
        otherFlex.setVisibility(View.GONE);
        phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_add_ppl, 0);
        phoneNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= phoneNumberEditText.getRight() - phoneNumberEditText.getTotalPaddingRight()) {
                        // your action for drawable click event
                        //startActivity(new Intent(getActivity(),ProfileUpdateActivity.class));
                        startActivityForResult(new Intent(getActivity(), SelectContactsActivity.class), REQCODE);
                        return true;
                    }
                }
                return true;
            }
        });
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {

        }
    }*/
}
