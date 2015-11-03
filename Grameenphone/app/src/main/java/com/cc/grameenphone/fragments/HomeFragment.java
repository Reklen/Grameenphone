package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.BillPaymentActivity;
import com.cc.grameenphone.activity.ReferFriendsActivity;
import com.cc.grameenphone.activity.SelectContactsActivity;
import com.cc.grameenphone.activity.TransactionOverviewActivity;
import com.cc.grameenphone.adapter.AutoCompleteAdapter;
import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.api_models.OtherPostpaidModel;
import com.cc.grameenphone.api_models.OtherPrepaidModel;
import com.cc.grameenphone.api_models.SelfPostpaidModel;
import com.cc.grameenphone.api_models.SelfPrepaidModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.OtherPostpaidApi;
import com.cc.grameenphone.interfaces.RechargeApi;
import com.cc.grameenphone.interfaces.SelfPrepaidApi;
import com.cc.grameenphone.interfaces.WalletBalanceInterface;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.Constants;
import com.cc.grameenphone.utils.IntentUtils;
import com.cc.grameenphone.utils.KeyboardUtil;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PhoneUtils;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.AmountEditText;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;
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
    AutoCompleteTextView phoneNumberEditText;
    @InjectView(R.id.phone_container)
    TextInputLayout phoneContainer;
    @InjectView(R.id.other_flex)
    TextView otherFlex;
    @InjectView(R.id.top_container1)
    RelativeLayout topContainer1;
    @InjectView(R.id.editamt)
    AmountEditText editamt;
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

    int REQCODE = IntentUtils.SELECT_CONTACT_REQ;
    RechargeApi rechargeApi;
    @InjectView(R.id.flexi_Ripple)
    RippleView flexiRipple;
    @InjectView(R.id.divider)
    View divider;
    @InjectView(R.id.centerview)
    View centerview;
    @InjectView(R.id.billPayRipple)
    RippleView billPayRipple;
    @InjectView(R.id.transactionOverviewRipple)
    RippleView transactionOverviewRipple;
    @InjectView(R.id.emergencyCallRipple)
    RippleView emergencyCallRipple;
    @InjectView(R.id.referFriendsRipple)
    RippleView referFriendsRipple;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog materialDialog;

    WalletCheckApi walletCheckApi;
    SelfPrepaidApi selfPrepaidApi;
    OtherPostpaidApi otherPostpaidApi;
    boolean otherFlexi = false;
    private WalletBalanceInterface mCallback;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (WalletBalanceInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement WalletBalanceInterface");
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);

        preferenceManager = new PreferenceManager(getActivity());
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        phoneNumberEditText.setText(preferenceManager.getMSISDN() + "");

        phoneNumberEditText.setInputType(0x00000000);
        editamt.setText("৳ 50");
        editamt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (editamt.getText().charAt(editamt.length() - 1) == '৳') {
                        editamt.setText("৳ ");
                        editamt.setSelection(editamt.getText().length());
                    }

                    if (!editamt.getText().toString().contains("৳")) {
                        editamt.setText("৳ " + editamt.getText().toString());
                        editamt.setSelection(editamt.getText().length());
                    }
                } catch (Exception e) {
                    editamt.setText("৳ ");
                    editamt.setSelection(editamt.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (prepaidOption.isChecked()) {

                } else if (postpaidOption.isChecked()) {
                }

            }
        });

        handleRipple();


        return rootView;
    }

    private List<ContactModel> suggestionModelList;
    AutoCompleteAdapter completeAdapter;

    private void setupAutoSuggestions() {
        suggestionModelList = new ArrayList<>();

        completeAdapter = new AutoCompleteAdapter(getActivity(), suggestionModelList);

        phoneNumberEditText.setAdapter(completeAdapter);


        new RushSearch()
                .find(ContactModel.class, new RushSearchCallback<ContactModel>() {
                    @Override
                    public void complete(final List<ContactModel> list) {
                        suggestionModelList.addAll(list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                completeAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
        phoneNumberEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                List<ContactModel> contact= (List<ContactModel>) parent.getItemAtPosition(position);
                  /*String item= view.toString();
                autoCompleteTextView.setText(item);*/
//                Toast.makeText(getApplicationContext(),"clicked" ,Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void handleRipple() {
        flexiRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                flexiButtonClick();

            }
        });

        billPayRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                billPaymentClick();
            }
        });

        transactionOverviewRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                transactionOverviewClick();
            }
        });
        emergencyCallRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                emergencyClick();
            }
        });
        referFriendsRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                referFriendsClick();
            }
        });
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

    void billPaymentClick() {
        startActivity(new Intent(getActivity(), BillPaymentActivity.class));
    }

    void transactionOverviewClick() {
        startActivity(new Intent(getActivity(), TransactionOverviewActivity.class));
    }

    void emergencyClick() {
        String ussd = "*XXX*X" + Uri.encode("#");
        Uri finalUssdUri = PhoneUtils.ussdToCallableUri(ussd);
        startActivity(new Intent(Intent.ACTION_DIAL, finalUssdUri));
    }

    void referFriendsClick() {
        startActivity(new Intent(getActivity(), ReferFriendsActivity.class));
    }

    MaterialDialog errorDialog;
    ProgressDialog loadingDialog;

    View pinConfirmationView;
    EditText pinConfirmationET;
    MaterialDialog pinConfirmDialog;
    String pin;

    void flexiButtonClick() {
        // Recharge psot
        android_id = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        KeyboardUtil.hideKeyboard(getActivity());
        String str = phoneNumberEditText.getText().toString();
        str = str.replace(" ", "");
        phoneNumberEditText.setText(str);
        rechargeApi = ServiceGenerator.createService(RechargeApi.class);
        if (phoneNumberEditText.getText().toString().length() == 0) {
            phoneNumberEditText.setError("Enter a valid number");
            phoneNumberEditText.requestFocus();
            return;
        } else if (phoneNumberEditText.getText().toString().length() < 8) {
            phoneNumberEditText.setError("Enter a valid number");
            phoneNumberEditText.requestFocus();
            return;
        } else {
            phoneNumberEditText.setError(null);
            phoneNumberEditText.requestFocus();
        }

        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setMessage("Flexiload in progress , please wait");


        selfPrepaidApi = ServiceGenerator.createService(SelfPrepaidApi.class);
        otherPostpaidApi = ServiceGenerator.createService(OtherPostpaidApi.class);
        Logger.d("CheckFlexi", prepaidOption.isChecked() + " " + otherFlexi + " " + postpaidOption.isChecked());
        if ((prepaidOption.isChecked()) && otherFlexi) {
            //TODO implement other prepaid recharge
            pinConfirmationView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pin_confirmation, null);
            pinConfirmationET = (EditText) pinConfirmationView.findViewById(R.id.pinConfirmEditText);

            pinConfirmDialog = new MaterialDialog(getActivity());
            pinConfirmDialog.setContentView(pinConfirmationView);
            pinConfirmDialog.setPositiveButton("CONFIRM", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pinConfirmationET.getText().toString().length() != 4) {
                        pinConfirmationET.setError("Enter your valid pin");
                        pinConfirmationET.requestFocus();
                        return;
                    }

                    pin = pinConfirmationET.getText().toString();
                    pinConfirmDialog.dismiss();
                    KeyboardUtil.hideKeyboardDialogDismiss(getActivity());
                    loadingDialog.show();
                    flexiLoadOtherPrepaid(pin);
                }
            });
            if (!(editamt.getText().toString().length() > 2)) {
                editamt.setError("Enter Amount");
                editamt.requestFocus();
                return;

            }
            if (phoneNumberEditText.getText().toString().equalsIgnoreCase(preferenceManager.getMSISDN())) {
                phoneNumberEditText.setText("");
                phoneNumberEditText.setError("You have entered your phone number, please select a different number");
                return;
            } else {
                pinConfirmDialog.show();
                Logger.d("Pinnny", phoneNumberEditText.getText().toString() + " " + preferenceManager.getMSISDN());

            }


        } else if ((prepaidOption.isChecked()) && (otherFlexi == false)) {
            //TODO implement prepaid for self
            loadingDialog.show();
            if (!(editamt.getText().toString().length() > 2)) {
                editamt.setError("Enter Amount");
                editamt.requestFocus();
                loadingDialog.dismiss();
                return;

            }
            try {
                JSONObject jsonObject = new JSONObject();
                JSONObject innerObject = new JSONObject();
                innerObject.put("DEVICEID", android_id);
                innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                innerObject.put("MSISDN", "017" + phoneNumberEditText.getText().toString());
                innerObject.put("TYPE", "CTMMREQ");
                innerObject.put("RCTYPE", "PREPAID");
                String amt = editamt.getText().toString();
                amt = amt.replace("৳", "");
                amt = amt.replace(" ", "");
                innerObject.put("AMOUNT", amt);
                jsonObject.put("COMMAND", innerObject);
                Logger.d("Flexiload", jsonObject.toString());
                selfPrepaidApi.selfPrepaid(jsonObject, new Callback<SelfPrepaidModel>() {
                    @Override
                    public void success(SelfPrepaidModel selfPrepaidModel, Response response) {
                        if (selfPrepaidModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                            loadingDialog.cancel();
                            View flexiDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_flexi_layout, null);
                            Logger.d("Its msisdn check ", "status " + selfPrepaidModel.toString());
                            materialDialog = new MaterialDialog(getActivity()).setContentView(flexiDialog);
                            materialDialog.setCanceledOnTouchOutside(true);
                            ((TextView) flexiDialog.findViewById(R.id.top_text)).setText(selfPrepaidModel.getCOMMAND().getMESSAGE() + "");
                            ((TextView) flexiDialog.findViewById(R.id.mobileNumber)).setText("017" + phoneNumberEditText.getText().toString() + "");
                            ((TextView) flexiDialog.findViewById(R.id.transactionNumber)).setText("\n" + selfPrepaidModel.getCOMMAND().getTXNID() + "");
                            ((TextView) flexiDialog.findViewById(R.id.flxiloadAmount)).setText(editamt.getText().toString() + "");
                            materialDialog.show();
                            Button buttonOk = (Button) flexiDialog.findViewById(R.id.okButton);
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loadingDialog.cancel();
                                    materialDialog.dismiss();
                                    otherFlexi = false;
                                    phoneNumberEditText.setText("" + preferenceManager.getMSISDN());
                                    phoneNumberEditText.setInputType(0x00000000);
                                    phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    otherFlex.setVisibility(View.VISIBLE);
                                    editamt.setText("৳ 50");
                                    Logger.d("WalletCheck ", "again 1");
                                    mCallback.fetchBalanceAgain();
                                }
                            });

                        } else {
                            loadingDialog.cancel();
                            Logger.e("Its msisdn check ", "status " + selfPrepaidModel.toString());
                            errorDialog = new MaterialDialog(getActivity());
                            errorDialog.setMessage(selfPrepaidModel.getCOMMAND().getMESSAGE() + "");
                            errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    errorDialog.dismiss();
                                    editamt.setText("৳ 50");
                                    mCallback.fetchBalanceAgain();
                                }
                            });
                            errorDialog.show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        loadingDialog.cancel();
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
                loadingDialog.cancel();
            }
        } else if ((postpaidOption.isChecked()) && otherFlexi) {
            //TODO implement other postpaid recharge
            pinConfirmationView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_pin_confirmation, null);
            pinConfirmationET = (EditText) pinConfirmationView.findViewById(R.id.pinConfirmEditText);

            pinConfirmDialog = new MaterialDialog(getActivity());
            pinConfirmDialog.setContentView(pinConfirmationView);
            pinConfirmDialog.setPositiveButton("CONFIRM", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pinConfirmationET.getText().toString().length() != 4) {
                        pinConfirmationET.setError("Enter your valid pin");
                        return;
                    }

                    pin = pinConfirmationET.getText().toString();
                    pinConfirmDialog.dismiss();
                    loadingDialog.show();
                    KeyboardUtil.hideKeyboardDialogDismiss(getActivity());
                    flexiLoadOtherPostpaid(pin);
                }
            });
            if (!(editamt.getText().toString().length() > 2)) {
                editamt.setError("Enter Amount");
                editamt.requestFocus();
                return;

            }

            if (phoneNumberEditText.getText().toString().equalsIgnoreCase(preferenceManager.getMSISDN())) {
                phoneNumberEditText.setText("");
                phoneNumberEditText.setError("You have entered your phone number, please select a different number");
                return;
            } else {
                pinConfirmDialog.show();
                Logger.d("Pinnny", phoneNumberEditText.getText().toString() + " " + preferenceManager.getMSISDN());

            }


        } else if ((postpaidOption.isChecked()) && (otherFlexi == false)) {
            //TODO implement self postpaid recharge
            loadingDialog.show();
            if (!(editamt.getText().toString().length() > 2)) {
                editamt.setError("Enter Amount");
                editamt.requestFocus();
                loadingDialog.dismiss();
                return;

            }
            try {
                JSONObject jsonObject = new JSONObject();
                JSONObject innerObject = new JSONObject();
                innerObject.put("DEVICEID", android_id);
                innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                innerObject.put("MSISDN", "017" + phoneNumberEditText.getText().toString());
                innerObject.put("TYPE", "CTMMREQ");
                innerObject.put("RCTYPE", "POSTPAID");
                String amt = editamt.getText().toString();
                amt = amt.replace("৳", "");
                amt = amt.replace(" ", "");
                innerObject.put("AMOUNT", amt);
                jsonObject.put("COMMAND", innerObject);
                Logger.d("Flexiload", jsonObject.toString());
                otherPostpaidApi.selfPostpaid(jsonObject, new Callback<SelfPostpaidModel>() {
                    @Override
                    public void success(SelfPostpaidModel selfPostpaidModel, Response response) {
                        if (selfPostpaidModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                            loadingDialog.cancel();
                            View flexiDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_flexi_layout, null);
                            Logger.d("Its self postpaid check ", "status " + selfPostpaidModel.toString());
                            materialDialog = new MaterialDialog(getActivity()).setContentView(flexiDialog);
                            materialDialog.setCanceledOnTouchOutside(true);
                            ((TextView) flexiDialog.findViewById(R.id.top_text)).setText(selfPostpaidModel.getCOMMAND().getMESSAGE() + "");
                            ((TextView) flexiDialog.findViewById(R.id.mobileNumber)).setText("017" + phoneNumberEditText.getText().toString() + "");
                            ((TextView) flexiDialog.findViewById(R.id.transactionNumber)).setText("\n" + selfPostpaidModel.getCOMMAND().getTXNID() + "");
                            ((TextView) flexiDialog.findViewById(R.id.flxiloadAmount)).setText(editamt.getText().toString() + "");
                            materialDialog.show();
                            Button buttonOk = (Button) flexiDialog.findViewById(R.id.okButton);
                            buttonOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    loadingDialog.cancel();
                                    materialDialog.dismiss();
                                    otherFlexi = false;
                                    phoneNumberEditText.setText("" + preferenceManager.getMSISDN());
                                    phoneNumberEditText.setInputType(0x00000000);
                                    phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    otherFlex.setVisibility(View.VISIBLE);
                                    editamt.setText("৳ 50");
                                    Logger.d("WalletCheck ", "again 1");
                                    mCallback.fetchBalanceAgain();
                                }
                            });
                        } else {
                            loadingDialog.cancel();
                            Logger.e("Its self postpaid check ", "status " + selfPostpaidModel.toString());
                            errorDialog = new MaterialDialog(getActivity());
                            errorDialog.setMessage(selfPostpaidModel.getCOMMAND().getMESSAGE() + "");
                            errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    errorDialog.dismiss();
                                    editamt.setText("৳ 50");
                                    mCallback.fetchBalanceAgain();
                                }
                            });
                            errorDialog.show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        loadingDialog.cancel();
                    }
                });

            } catch (JSONException e) {
                loadingDialog.cancel();
                e.printStackTrace();
            }
        }
    }

    private void flexiLoadOtherPostpaid(String pin) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("MSISDN2", "017" + phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "OCTMMREQ");
            innerObject.put("RCTYPE", "POSTPAID");
            innerObject.put("PIN", pin);
            String amt = editamt.getText().toString();
            amt = amt.replace("৳", "");
            amt = amt.replace(" ", "");
            innerObject.put("AMOUNT", amt);
            jsonObject.put("COMMAND", innerObject);
            Logger.d("Flexiload", jsonObject.toString());
            otherPostpaidApi.otherPostpaid(jsonObject, new Callback<OtherPostpaidModel>() {
                @Override
                public void success(OtherPostpaidModel otherPostpaidModel, Response response) {
                    if (otherPostpaidModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        loadingDialog.cancel();
                        View flexiDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_flexi_layout, null);
                        Logger.d("Its postpaid other check ", "status " + otherPostpaidModel.toString());
                        materialDialog = new MaterialDialog(getActivity()).setContentView(flexiDialog);
                        materialDialog.setCanceledOnTouchOutside(true);
                        ((TextView) flexiDialog.findViewById(R.id.top_text)).setText(otherPostpaidModel.getCommand().getMESSAGE() + "");
                        ((TextView) flexiDialog.findViewById(R.id.mobileNumber)).setText("017" + phoneNumberEditText.getText().toString() + "");
                        ((TextView) flexiDialog.findViewById(R.id.transactionNumber)).setText("\n" + otherPostpaidModel.getCommand().getTXNID() + "");
                        ((TextView) flexiDialog.findViewById(R.id.flxiloadAmount)).setText(editamt.getText().toString() + "");
                        materialDialog.show();
                        Button buttonOk = (Button) flexiDialog.findViewById(R.id.okButton);
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadingDialog.cancel();
                                materialDialog.dismiss();
                                otherFlexi = false;
                                phoneNumberEditText.setText("" + preferenceManager.getMSISDN());
                                phoneNumberEditText.setInputType(0x00000000);
                                phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                otherFlex.setVisibility(View.VISIBLE);
                                editamt.setText("৳ 50");
                                Logger.d("WalletCheck ", "again 1");
                                mCallback.fetchBalanceAgain();
                            }
                        });
                    } else {
                        loadingDialog.cancel();
                        Logger.e("Its other postpaid check ", "status " + otherPostpaidModel.toString());
                        errorDialog = new MaterialDialog(getActivity());
                        errorDialog.setMessage(otherPostpaidModel.getCommand().getMESSAGE() + "");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                                editamt.setText("৳ 50");
                                mCallback.fetchBalanceAgain();
                            }
                        });
                        errorDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    loadingDialog.cancel();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            loadingDialog.cancel();
        }

    }

    private void flexiLoadOtherPrepaid(String pin) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("MSISDN2", "017" + phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "OCTMMREQ");
            innerObject.put("RCTYPE", "PREPAID");
            innerObject.put("PIN", pin);
            String amt = editamt.getText().toString();
            amt = amt.replace("৳", "");
            amt = amt.replace(" ", "");
            innerObject.put("AMOUNT", amt);
            jsonObject.put("COMMAND", innerObject);
            Logger.d("Flexiload", jsonObject.toString());
            selfPrepaidApi.selfPrepaidOther(jsonObject, new Callback<OtherPrepaidModel>() {
                @Override
                public void success(OtherPrepaidModel selfPrepaidModel, Response response) {

                    if (selfPrepaidModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Some taggg", "Flexi complete loading bar should disappera");
                        loadingDialog.cancel();
                        View flexiDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_flexi_layout, null);
                        Logger.d("Its prepaid other check ", "status " + selfPrepaidModel.toString());
                        materialDialog = new MaterialDialog(getActivity()).setContentView(flexiDialog);
                        materialDialog.setCanceledOnTouchOutside(true);
                        ((TextView) flexiDialog.findViewById(R.id.top_text)).setText(selfPrepaidModel.getCOMMAND().getMESSAGE() + "");
                        ((TextView) flexiDialog.findViewById(R.id.mobileNumber)).setText("017" + phoneNumberEditText.getText().toString() + "");
                        ((TextView) flexiDialog.findViewById(R.id.transactionNumber)).setText("\n" + selfPrepaidModel.getCOMMAND().getTXNID() + "");
                        ((TextView) flexiDialog.findViewById(R.id.flxiloadAmount)).setText(editamt.getText().toString() + "");
                        materialDialog.show();
                        Button buttonOk = (Button) flexiDialog.findViewById(R.id.okButton);
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadingDialog.cancel();
                                materialDialog.dismiss();
                                otherFlexi = false;
                                phoneNumberEditText.setText("" + preferenceManager.getMSISDN());
                                phoneNumberEditText.setInputType(0x00000000);
                                phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                otherFlex.setVisibility(View.VISIBLE);
                                editamt.setText("৳ 50");
                                Logger.d("WalletCheck ", "again 1");

                                mCallback.fetchBalanceAgain();
                            }
                        });
                    } else {
                        loadingDialog.cancel();
                        Logger.e("Its other prepaid check ", "status " + selfPrepaidModel.toString());
                        errorDialog = new MaterialDialog(getActivity());
                        errorDialog.setMessage(selfPrepaidModel.getCOMMAND().getMESSAGE() + "");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                                editamt.setText("৳ 50");
                                Logger.d("WalletCheck ", "again 1");
                                mCallback.fetchBalanceAgain();
                            }
                        });
                        errorDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    loadingDialog.cancel();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            loadingDialog.cancel();
        }
    }

    @OnClick(R.id.other_flex)
    void otherFlexiLoadClick() {

        otherFlex.setVisibility(View.GONE);
        phoneNumberEditText.setText("");
        otherFlexi = true;
        phoneNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editamt.setText("৳ 50");
        setupAutoSuggestions();
        phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_add_ppl, 0);
        phoneNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    int DRAWABLE_RIGHT = 2;
                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (event.getRawX() >= (phoneNumberEditText.getRight() - phoneNumberEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            // your action for drawable click event
                            //
                            startActivityForResult(new Intent(getActivity(), SelectContactsActivity.class), REQCODE);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    String last8;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQCODE) {
            try {
                Logger.d("Return Contact", "contacts " + ((String) data.getExtras().get(Constants.RETURN_RESULT)));
                if (((String) data.getExtras().get(Constants.RETURN_RESULT)) != null)
                    otherFlexi = true;
                phoneNumberEditText.setText("" + ((String) data.getExtras().get(Constants.RETURN_RESULT)));
                Logger.d("Return Contact", "contacts " + otherFlexi);

                String num = PhoneUtils.normalizeNum(((String) data.getExtras().get(Constants.RETURN_RESULT)));
                num = num.replace("+", "");
                String upToNCharacters = num.substring(0, Math.min(num.length(), 5));
                String upToNCharacters1 = num.substring(0, Math.min(num.length(), 3));
                if (upToNCharacters.equalsIgnoreCase("88017")) {
                    last8 = num.substring(5, Math.min(num.length(), num.length()));
                } else if (upToNCharacters1.equalsIgnoreCase("017")) {
                    last8 = num.substring(3, Math.min(num.length(), num.length()));

                } else {
                    last8 = num;
                }


                phoneNumberEditText.setText("" + last8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            phoneNumberEditText.setText("" + preferenceManager.getMSISDN());
        }
    }


}
