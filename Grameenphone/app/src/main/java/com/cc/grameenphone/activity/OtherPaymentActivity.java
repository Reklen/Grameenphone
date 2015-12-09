package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillPaymentViewPagerAdapter;
import com.cc.grameenphone.api_models.BalanceCommandModel;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.api_models.OtherPaymentModel;
import com.cc.grameenphone.async.CompaniesSaveDBTask;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.OtherPaymentApi;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;
import com.cc.grameenphone.views.tabs.SlidingTabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class OtherPaymentActivity extends AppCompatActivity {

    BillPaymentViewPagerAdapter adapter;

    CharSequence Titles[] = {"ELECTRICITY", "GAS", "INSURANCE", "TICKETING", "INTERNET"};
    int NumOfTabs = 5;

    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;
    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    private WalletCheckApi walletCheckApi;
    ProgressDialog loadingDialog;
    private String android_id;
    private PreferenceManager preferenceManager;
    private OtherPaymentApi otherPaymentApi;
    private MaterialDialog errorDialog, sessionDialog;
    private MaterialDialog walletBalanceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_payment_activity);
        ButterKnife.inject(this);
        preferenceManager = new PreferenceManager(OtherPaymentActivity.this);
        setupToolbar();
        fetchCompanies();
    }


    private void setupToolbar() {
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        adapter = new BillPaymentViewPagerAdapter(getSupportFragmentManager(), Titles, NumOfTabs, 0);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        walletLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                walletBalanceDialog = new MaterialDialog(OtherPaymentActivity.this);

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

        walletCheckApi = ServiceGenerator.createService(WalletCheckApi.class);
        android_id = Settings.Secure.getString(OtherPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CBEREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("wallet request ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            if (preferenceManager.getWalletBalance().isEmpty())
                walletCheckApi.checkBalance(in, new Callback<BalanceEnquiryModel>() {
                    @Override
                    public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {
                        if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                            Logger.d("Balance", balanceEnquiryModel.toString());
                            walletLabel.setText("  ৳ " + balanceEnquiryModel.getCOMMAND().getBALANCE());
                            walletLabel.setTag(balanceEnquiryModel);
                            preferenceManager.setWalletBalance(balanceEnquiryModel.getCOMMAND().getBALANCE());
                            preferenceManager.setWalletMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE());
                        } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                            Logger.d("Balance", balanceEnquiryModel.toString());
                            sessionDialog = new MaterialDialog(OtherPaymentActivity.this);
                            sessionDialog.setMessage("Session expired , please login again");
                            sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SessionClearTask sessionClearTask = new SessionClearTask(OtherPaymentActivity.this, true);
                                    sessionClearTask.execute();

                                }
                            });
                            sessionDialog.show();
                        } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA903")) {
                            Logger.d("Balance", balanceEnquiryModel.toString());
                            sessionDialog = new MaterialDialog(OtherPaymentActivity.this);
                            sessionDialog.setMessage("Session expired , please login again");
                            sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SessionClearTask sessionClearTask = new SessionClearTask(OtherPaymentActivity.this, true);
                                    sessionClearTask.execute();

                                }
                            });
                            sessionDialog.setCanceledOnTouchOutside(false);
                            sessionDialog.show();
                        } else {
                            errorDialog = new MaterialDialog(OtherPaymentActivity.this);
                            errorDialog.setMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE() + "");
                            errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    errorDialog.dismiss();
                                }
                            });
                            errorDialog.show();
                            Logger.d("Balance", balanceEnquiryModel.toString());
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

    private void fetchCompanies() {
        loadingDialog = new ProgressDialog(OtherPaymentActivity.this);
        loadingDialog.setMessage("Fetching list..");
        loadingDialog.show();
        Logger.d("Pref Check", preferenceManager.getCompaniesSavedFlag() + "");
        if (preferenceManager.getCompaniesSavedFlag()) {

            loadingDialog.dismiss();
            tabs.setViewPager(pager);

        } else {
            // getOtherPaymentCompanies();
        }
    }


    private void getOtherPaymentCompanies() {
        otherPaymentApi = ServiceGenerator.createService(OtherPaymentApi.class);
        android_id = Settings.Secure.getString(OtherPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CTCMPLREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("getOtherPaymentCompanies ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            otherPaymentApi.fetchCompanies(in, new Callback<OtherPaymentModel>() {
                @Override
                public void success(OtherPaymentModel otherPaymentModel, Response response) {

                    if (otherPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        OtherPaymentModel model = otherPaymentModel;
                        final List<OtherPaymentCompanyModel> companiesList = model.getCOMMAND().getCOMPANYDET();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                loadingDialog.dismiss();
                                tabs.setViewPager(pager);
                            }
                        });
                        CompaniesSaveDBTask companiesSaveDBTask = new CompaniesSaveDBTask(getApplicationContext(), companiesList);
                        companiesSaveDBTask.execute();
                    } else {
                        Logger.e("getOtherPaymentCompanies", otherPaymentModel.getCOMMAND().getTXNSTATUS() + " " + otherPaymentModel.getCOMMAND().toString());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("getOtherPaymentCompanies", error.getMessage() + "");
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

