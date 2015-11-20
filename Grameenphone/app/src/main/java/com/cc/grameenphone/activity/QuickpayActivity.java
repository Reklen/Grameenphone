package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.QuickPayModel;
import com.cc.grameenphone.fragments.QuickBillPayFragment;
import com.cc.grameenphone.fragments.QuickPayFragment;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.QuickPayApi;
import com.cc.grameenphone.interfaces.QuickPayInterface;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.IntentUtils;
import com.cc.grameenphone.utils.KeyboardUtil;
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

public class QuickPayActivity extends AppCompatActivity implements QuickPayInterface {

    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container)
    FrameLayout container;
    private WalletCheckApi walletCheckApi;
    private String android_id;
    private PreferenceManager preferenceManager;
    private MaterialDialog walletBalanceDialog, successDialog;
    QuickPayFragment quickCodeFragment;
    QuickBillPayFragment quickBillFragment;
    private QuickPayApi quickPayApi;
    private MaterialDialog errorDialog;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay);

        ButterKnife.inject(this);
        setupToolbar();
        preferenceManager = new PreferenceManager(QuickPayActivity.this);


        quickCodeFragment = new QuickPayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, quickCodeFragment);
        transaction.commit();
        getWalletBalance();
    }

    private void setupToolbar() {
        icon1Ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                walletBalanceDialog = new MaterialDialog(QuickPayActivity.this);

                BalanceEnquiryModel md = (BalanceEnquiryModel) walletLabel.getTag();
                if (md != null) {
                    walletBalanceDialog.setMessage(md.getCOMMAND().getMESSAGE());
                    walletBalanceDialog.setPositiveButton("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            walletBalanceDialog.dismiss();
                        }
                    });
                    walletBalanceDialog.show();
                }


            }
        });

        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                final FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() != 0) {
                    fragmentManager.popBackStackImmediate();
                } else {
                    finish();
                }
            }
        });
    }

    //Need to edit! The paycode is passed over here!
    public void onclickQuickPay_QuickPayFragment(String paycode) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // transaction.addToBackStack("quickCodeFragment");
        quickBillFragment = new QuickBillPayFragment();
        quickCodeFragment = new QuickPayFragment();
        Bundle args = new Bundle();
        args.putString("QUICKPAYCODE", paycode);
        quickBillFragment.setArguments(args);
        Logger.d("PAYCODE" + paycode);
        if (paycode.equals("")) {
            transaction.replace(R.id.container, quickCodeFragment);
            successDialog = new MaterialDialog(QuickPayActivity.this);
            successDialog.setMessage("You must enter a valid quick pay code");
            successDialog.setPositiveButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    successDialog.dismiss();
                }
            });
            successDialog.show();
        } else {
            transaction.replace(R.id.container, quickBillFragment);
        }
        transaction.commit();
    }

    private void getWalletBalance() {

        walletCheckApi = ServiceGenerator.createService(WalletCheckApi.class);
        android_id = Settings.Secure.getString(QuickPayActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CBEREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("wallet request ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            walletCheckApi.checkBalance(in, new Callback<BalanceEnquiryModel>() {
                @Override
                public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {
                    if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Balance", balanceEnquiryModel.toString());
                        walletLabel.setText("  ৳ " + balanceEnquiryModel.getCOMMAND().getBALANCE());
                        walletLabel.setTag(balanceEnquiryModel);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("Balance", error.getMessage());
                }
            });
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            finish();
        }
    }

    @Override
    public void onQuickCodeSubmit(String code) {
        //Caaling to get entire quick payment details
        getQuickPayDetails(code);
    }


    //TODO Implement Quickpay
    public void getQuickPayDetails(String code) {
        loadingDialog = new ProgressDialog(QuickPayActivity.this);
        loadingDialog.setMessage("Fetching details");
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
        quickPayApi = ServiceGenerator.createService(QuickPayApi.class);
        String quickPayCode = code;
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
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            quickPayApi.quickPay(in, new Callback<QuickPayModel>() {
                @Override
                public void success(final QuickPayModel quickPayModel, Response response) {
                    if (quickPayModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Quickpay Bill Success", quickPayModel.toString());
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        loadingDialog.cancel();
                        quickBillFragment = new QuickBillPayFragment();
                        Bundle args = new Bundle();
                        args.putString(IntentUtils.QUICK_PAY_COMPANY_NAME, quickPayModel.getCOMMAND().getCOMPNAME() + "");
                        args.putString(IntentUtils.QUICK_PAY_ACCOUNT_NUM, quickPayModel.getCOMMAND().getACCNUM() + "");
                        args.putString(IntentUtils.QUICK_PAY_BILL_NUM, quickPayModel.getCOMMAND().getBILLNUM() + "");
                        args.putString(IntentUtils.QUICK_PAY_DUE_DATE, quickPayModel.getCOMMAND().getDUEDATE() + "");
                        args.putString(IntentUtils.QUICK_PAY_TOTAL_AMOUNT, quickPayModel.getCOMMAND().getAMOUNT() + "");
                        args.putString(IntentUtils.QUICK_PAY_BILL_PROVIDER, quickPayModel.getCOMMAND().getBPROVIDER() + "");
                        args.putString(IntentUtils.QUICK_PAY_SURCHARGE, "0");
                        quickBillFragment.setArguments(args);

                        transaction.replace(R.id.container, quickBillFragment);
                        transaction.addToBackStack("quickCodeFragment");
                        KeyboardUtil.hideKeyboard(QuickPayActivity.this);
                        transaction.commit();

                    } else {
                        loadingDialog.cancel();
                        Logger.e("Quick pay not success ", "status " + quickPayModel.toString());
                        errorDialog = new MaterialDialog(QuickPayActivity.this);
                        errorDialog.setMessage(quickPayModel.getCOMMAND().getMESSAGE() + "");
                        errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                                quickCodeFragment.clearCodeFromET();
                                ///chechiehfiuegiluhlg
                                //TODO WTF
                            }
                        });
                        errorDialog.show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    loadingDialog.cancel();
                    if (error.getKind() == RetrofitError.Kind.NETWORK) {
                        Logger.e("Failuare", error.getMessage());
                    } else {
                        Logger.e("Failuare", error.getKind() + " " + error.getUrl() + " " + error.getBody() + " " + error.getResponse() + " " + error.getMessage());
                    }
                }
            });

        } catch (JSONException e) {
            loadingDialog.cancel();
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
/*
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
 */