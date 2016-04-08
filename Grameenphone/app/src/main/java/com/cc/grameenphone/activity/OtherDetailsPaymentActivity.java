package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.BalanceCommandModel;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.BillConfirmationModel;
import com.cc.grameenphone.api_models.OtherPaymentNewModel;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.AddAssociationApi;
import com.cc.grameenphone.interfaces.OtherPaymentApi;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.KeyboardUtil;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.CustomTextInputLayout;
import com.cc.grameenphone.views.RippleView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by aditlal on 21/12/15.
 */
public class OtherDetailsPaymentActivity extends AppCompatActivity implements ValidationListener {

    String company, accNum, billNum, billCCode, amount, surcharge, category;
    boolean isAssociationRequired;
    OtherPaymentNewModel model;
    @Bind(R.id.image_back)
    ImageButton imageBack;
    @Bind(R.id.backRipple)
    RippleView backRipple;
    @Bind(R.id.toolbar_text)
    TextView toolbarText;
    @Bind(R.id.icon1)
    ImageButton icon1;
    @Bind(R.id.walletLabel)
    TextView walletLabel;
    @Bind(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @Bind(R.id.toolbar_container)
    RelativeLayout toolbarContainer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.companyText)
    TextView companyText;
    @Bind(R.id.companyTextView)
    TextView companyTextView;
    @Bind(R.id.accountText)
    TextView accountText;
    @Bind(R.id.accountTextView)
    TextView accountTextView;
    @Bind(R.id.second_top)
    RelativeLayout secondTop;
    @Bind(R.id.billText)
    TextView billText;
    @Bind(R.id.third_top)
    RelativeLayout thirdTop;
    @Bind(R.id.amt_txt)
    TextView amtTxt;
    @Bind(R.id.amountTextView)
    TextView amountTextView;
    @Bind(R.id.fourth_top)
    RelativeLayout fourthTop;
    @Bind(R.id.surchargeText)
    TextView surchargeText;
    @Bind(R.id.surchargeTextView)
    TextView surchargeTextView;
    @Bind(R.id.fifth_top)
    RelativeLayout fifthTop;
    @Bind(R.id.due_date_text)
    TextView dueDateText;
    @Bind(R.id.dueDateTextView)
    TextView dueDateTextView;
    @Bind(R.id.sixth_top)
    RelativeLayout sixthTop;
    @NotEmpty
    @Bind(R.id.pinConfirmEditText)
    EditText pinConfirmEditText;
    @Bind(R.id.pinTIL)
    CustomTextInputLayout pinTIL;
    @Bind(R.id.pinLayout)
    RelativeLayout pinLayout;
    @Bind(R.id.confirmButton)
    Button confirmButton;
    @Bind(R.id.confirmRippleView)
    RippleView confirmRippleView;
    @Bind(R.id.billTextView)
    TextView billTextView;
    @Bind(R.id.pinHeading)
    TextView pinHeading;
    private MaterialDialog walletBalanceDialog, sessionDialog, errorDialog, confirmationDialog;
    private WalletCheckApi walletCheckApi;
    private String android_id;
    private PreferenceManager preferenceManager;
    Validator validator;
    OtherPaymentApi otherPaymentApi;
    ProgressDialog progressDialog;
    private AddAssociationApi addAssociationApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_payment_details);
        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(OtherDetailsPaymentActivity.this);
        otherPaymentApi = ServiceGenerator.createService(OtherDetailsPaymentActivity.this,OtherPaymentApi.class);
        addAssociationApi = ServiceGenerator.createService(OtherDetailsPaymentActivity.this,AddAssociationApi.class);

        validator = new Validator(OtherDetailsPaymentActivity.this);
        validator.setValidationListener(OtherDetailsPaymentActivity.this);
        progressDialog = new ProgressDialog(OtherDetailsPaymentActivity.this);
        progressDialog.setMessage("Loading..");
        handleExtras();
        setupViews();

    }

    private void handleExtras() {
        Bundle extras = getIntent().getExtras();
        model = (OtherPaymentNewModel) extras.get("otherDetailsModel");
        company = extras.getString("otherCompany");
        accNum = extras.getString("otherAccNum");
        billNum = extras.getString("otherBillNum");
        billCCode = extras.getString("otherBillCCODE");
        surcharge = model.getCommand().getSURCHARGE();
        amount = model.getCommand().getAMOUNT();
        isAssociationRequired = ((model.getCommand().getISASSOCREQ().equalsIgnoreCase("Y")));
        category = extras.getString("otherCategory");


    }


    private void setupViews() {
        setupToolbar();
        accountTextView.setText(accNum);
        billTextView.setText(billNum);
        companyTextView.setText(company);
        amountTextView.setText("৳ " + model.getCommand().getAMOUNT());
        if (model.getCommand().getSURCHARGE() != null)
            surchargeTextView.setText("৳ " + model.getCommand().getSURCHARGE());

        confirmRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                validator.validate();
            }
        });

    }

    private void confirmBillPayment() {
        progressDialog.show();
        android_id = Settings.Secure.getString(OtherDetailsPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        otherPaymentApi = ServiceGenerator.createService(OtherDetailsPaymentActivity.this,OtherPaymentApi.class);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CPMBREQ");
            innerObject.put("BILLCCODE", billCCode);
            innerObject.put("BILLANO", accNum);
            innerObject.put("AMOUNT", amount);
            innerObject.put("BILLNO", billNum);
            innerObject.put("BPROVIDER", "101");
            innerObject.put("SURCHARGE", surcharge);
            innerObject.put("PIN", pinConfirmEditText.getText().toString());
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("confirmaing bill payment ", jsonObject.toString());
            //Logger.d("confirmaing bill payment ",billCCode );

            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            otherPaymentApi.billConfirmation(in, new Callback<BillConfirmationModel>() {
                @Override
                public void success(final BillConfirmationModel billConfirmationModel, Response response) {
                    if (!isAssociationRequired) {
                        progressDialog.dismiss();
                        if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                            confirmationDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                            confirmationDialog.setMessage("" + billConfirmationModel.getCOMMAND().getMESSAGE());
                            confirmationDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(OtherDetailsPaymentActivity.this, HomeActivity.class));
                                    finish();
                                }
                            });
                            confirmationDialog.show();

                        } else {
                            errorDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                            errorDialog.setMessage(billConfirmationModel.getCOMMAND().getMESSAGE());
                            errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    errorDialog.dismiss();
                                    pinConfirmEditText.setText("");
                                    pinConfirmEditText.clearFocus();
                                    startActivity(new Intent(OtherDetailsPaymentActivity.this, HomeActivity.class));
                                    finish();
                                }
                            });
                            errorDialog.show();
                        }
                    } else
                        addAssociation(billConfirmationModel);

                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                }
            });


        } catch (JSONException e) {
            progressDialog.dismiss();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }

    }

    public void addAssociation(BillConfirmationModel billConfirmationModel) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "BPREGREQ");
            innerObject.put("CATEGORY", category);
            innerObject.put("PREF1", accNum);
            innerObject.put("BILLCCODE", billCCode);
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("confirmaing bill payment ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            addAssociationApi.associationSubmit(in, new Callback<BillConfirmationModel>() {
                @Override
                public void success(BillConfirmationModel billConfirmationModel, Response response) {
                    progressDialog.cancel();
                    if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {

                        confirmationDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                        confirmationDialog.setMessage("" + billConfirmationModel.getCOMMAND().getMESSAGE());
                        confirmationDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(OtherDetailsPaymentActivity.this, HomeActivity.class));
                                OtherDetailsPaymentActivity.this.finish();
                            }
                        });
                        confirmationDialog.show();

                    } else if (billConfirmationModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        //Logger.d("Balance", billConfirmationModel.toString());
                        sessionDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SessionClearTask sessionClearTask = new SessionClearTask(OtherDetailsPaymentActivity.this, false);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else {

                        errorDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
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
            e.printStackTrace();
            progressDialog.cancel();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void setupToolbar() {
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });

        toolbarText.setText(company);

        walletLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletBalanceDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);

                BalanceEnquiryModel md = (BalanceEnquiryModel) v.getTag();
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

        getWalletBalance();
    }

    private void getWalletBalance() {

        walletCheckApi = ServiceGenerator.createService(OtherDetailsPaymentActivity.this,WalletCheckApi.class);
        android_id = Settings.Secure.getString(OtherDetailsPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CBEREQ");
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("wallet request ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            if (preferenceManager.getWalletBalance().isEmpty())
                walletCheckApi.checkBalance(in, new Callback<BalanceEnquiryModel>() {
                    @Override
                    public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {

                        if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                            //Logger.d("Balance", balanceEnquiryModel.toString());
                            walletLabel.setText("  ৳ " + balanceEnquiryModel.getCOMMAND().getBALANCE());
                            walletLabel.setTag(balanceEnquiryModel);
                            preferenceManager.setWalletBalance(balanceEnquiryModel.getCOMMAND().getBALANCE());
                            preferenceManager.setWalletMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE());
                        } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                            //Logger.d("Balance", balanceEnquiryModel.toString());
                            sessionDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                            sessionDialog.setMessage("Session expired , please login again");
                            sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SessionClearTask sessionClearTask = new SessionClearTask(OtherDetailsPaymentActivity.this, true);
                                    sessionClearTask.execute();

                                }
                            });
                            sessionDialog.show();
                        } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA903")) {
                            //Logger.d("Balance", balanceEnquiryModel.toString());
                            sessionDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                            sessionDialog.setMessage("Session expired , please login again");
                            sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SessionClearTask sessionClearTask = new SessionClearTask(OtherDetailsPaymentActivity.this, true);
                                    sessionClearTask.execute();

                                }
                            });
                            sessionDialog.setCanceledOnTouchOutside(false);
                            sessionDialog.show();
                        } else {
                            errorDialog = new MaterialDialog(OtherDetailsPaymentActivity.this);
                            errorDialog.setMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE() + "");
                            errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    errorDialog.dismiss();
                                }
                            });
                            errorDialog.show();
                            //Logger.d("Balance", balanceEnquiryModel.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Logger.e("Balance", error.getMessage());
                    }
                });
            else {
                walletLabel.setText("  ৳ " + preferenceManager.getWalletBalance());
                BalanceEnquiryModel balanceEnquiryModel = new BalanceEnquiryModel();
                BalanceCommandModel balanceCommandModel = new BalanceCommandModel();
                balanceCommandModel.setMESSAGE(preferenceManager.getWalletMessage());
                balanceEnquiryModel.setCOMMAND(balanceCommandModel);
                walletLabel.setTag(balanceEnquiryModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onValidationSucceeded() {
        confirmBillPayment();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        KeyboardUtil.hideKeyboard(OtherDetailsPaymentActivity.this);
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(OtherDetailsPaymentActivity.this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(OtherDetailsPaymentActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
