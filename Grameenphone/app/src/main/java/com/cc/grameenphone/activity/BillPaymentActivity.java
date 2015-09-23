package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillsListAdapter;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.BillListModel;
import com.cc.grameenphone.api_models.UserBillsModel;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.BillspaymentApi;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.utils.ToolBarUtils;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by rahul on 11/09/15.
 */
public class BillPaymentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    @InjectView(R.id.quickpayButton)
    Button qquickpayButton;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar_container)
    RelativeLayout toolbarContainer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.billsbar)
    TextView billsbar;
    @InjectView(R.id.billContainer)
    RelativeLayout billContainer;
    @InjectView(R.id.billsList)
    ListView billsListView;
    @InjectView(R.id.selectedPaymentButton)
    Button selectedPaymentButton;
    @InjectView(R.id.otherPaymentButton)
    Button otherPaymentButton;
    BillsListAdapter listViewAdapter;
    BillspaymentApi billspaymentApi;
    MaterialDialog otpDialog, successSignupDialog, errorDialog;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.selectedPayRippleView)
    RippleView selectedPayRippleView;
    @InjectView(R.id.quickPayRippleView)
    RippleView quickPayRippleView;
    @InjectView(R.id.otherPayRippleView)
    RippleView otherPayRippleView;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    private String android_id;
    PreferenceManager preferenceManager;
    List<UserBillsModel> userBillsModels;
    private WalletCheckApi walletCheckApi;
    List<String> billsSelectedList;
    ProgressDialog loadingDialog;
    MaterialDialog walletBalanceDialog;
    private MaterialDialog sessionDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payment);
        ButterKnife.inject(this);
        setupToolbar();
        //TODO Listing total number of bills
        loadingDialog = new ProgressDialog(BillPaymentActivity.this);
        loadingDialog.setMessage("Loading due bills..");
        userBillsModels = new ArrayList<>();
        billsSelectedList = new ArrayList<>();
        View emptyView = LayoutInflater.from(BillPaymentActivity.this).inflate(R.layout.empty_bills_list, null);
        listViewAdapter = new BillsListAdapter(BillPaymentActivity.this, userBillsModels);
        billsListView.setAdapter(listViewAdapter);
        billsListView.setEmptyView(emptyView);
        android_id = Settings.Secure.getString(BillPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(BillPaymentActivity.this);
        billspaymentApi = ServiceGenerator.createService(BillspaymentApi.class);
        loadingDialog.show();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "SAPLBPREQ");
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            jsonObject.put("COMMAND", innerObject);
            Logger.d("ProfileUpdates", jsonObject.toString());
            //TODO Checking API Calls
            billspaymentApi.billsPay(jsonObject, new Callback<BillListModel>() {
                @Override
                public void success(BillListModel billListModel, Response response) {
                    Logger.d("BILLS response", billListModel.toString());
                    if (billListModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        loadingDialog.dismiss();
                        if (billListModel.getCOMMAND().getMessage().getComapny() != null) {
                            Logger.d("BILLS response", billListModel.getCOMMAND().getMessage().getComapny().toString());
                            List<UserBillsModel> bills = billListModel.getCOMMAND().getMessage().getComapny();
                            userBillsModels.addAll(bills);
                            listViewAdapter.notifyDataSetChanged();
                        } else {
                            //dontknwo
                            loadingDialog.dismiss();
                        }

                    } else if (billListModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        Logger.e("Balance", billListModel.getCOMMAND().toString() + " ");
                        loadingDialog.cancel();
                        sessionDialog = new MaterialDialog(BillPaymentActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sessionDialog.dismiss();
                                SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else {
                        loadingDialog.cancel();
                        Logger.e("Balance", billListModel.toString());
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("BILLS", error.getMessage());
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
        setupListViewItemClick();
        setupRipples();
        getWalletBalance();

    }

    @OnClick(R.id.selectedPaymentButton)
    public  void selctedBillPay(){
        //TODO Paying selected bills




    }
    private void setupListViewItemClick() {
        billsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.billCheckBox);
                UserBillsModel userBillsModel = (UserBillsModel) listViewAdapter.getItem(position);
                if (billsSelectedList.contains(position + "")) {
                    billsSelectedList.remove(position + "");
                    checkBox.setChecked(false);
                    userBillsModel.setIsSelected(false);
                } else {
                    billsSelectedList.add(position + "");
                    checkBox.setChecked(true);
                    userBillsModel.setIsSelected(true);
                }

                if (billsSelectedList.size() == 0) {
                    listViewAdapter.togglePayButton(true);
                    selectedPayRippleView.setVisibility(View.GONE);
                    quickPayRippleView.setVisibility(View.VISIBLE);
                    otherPayRippleView.setVisibility(View.VISIBLE);
                } else {
                    listViewAdapter.togglePayButton(false);
                    selectedPayRippleView.setVisibility(View.VISIBLE);
                    quickPayRippleView.setVisibility(View.GONE);
                    otherPayRippleView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getWalletBalance() {

        walletCheckApi = ServiceGenerator.createService(WalletCheckApi.class);
        android_id = Settings.Secure.getString(BillPaymentActivity.this.getContentResolver(),
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
            walletCheckApi.checkBalance(jsonObject, new Callback<BalanceEnquiryModel>() {
                @Override
                public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {
                    if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Balance", balanceEnquiryModel.toString());
                        walletLabel.setText("  ৳ " + balanceEnquiryModel.getCOMMAND().getBALANCE());
                        walletLabel.setTag(balanceEnquiryModel);
                    } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        Logger.e("Balance", balanceEnquiryModel.getCOMMAND().getMESSAGE().toString() + " ");
                        loadingDialog.cancel();
                        sessionDialog = new MaterialDialog(BillPaymentActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sessionDialog.dismiss();
                                SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else {
                        Logger.e("Balance", balanceEnquiryModel.toString());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("Balance", error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupToolbar() {
        toolbarText.setText("Bill Payment");
        setSupportActionBar(toolbar);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });

        walletBalanceDialog = new MaterialDialog(BillPaymentActivity.this);
        icon1Ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
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
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        selectedPaymentButton.setVisibility(View.VISIBLE);
        int pos = billsListView.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
           /* BillDetailsItems l = arraylist.get(pos);
            l.setSelected(isChecked);*/

        }
    }

    void setupRipples() {

        selectedPayRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

            }
        });

        quickPayRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                startActivity(new Intent(BillPaymentActivity.this, QuickPayActivity.class));
            }
        });
        otherPayRippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                startActivity(new Intent(BillPaymentActivity.this, OtherPaymentActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bill_payment_menu, menu);
        int srcColor = 0xFFFFFFFF;
        ToolBarUtils.colorizeToolbar(toolbar, srcColor, BillPaymentActivity.this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.newAssociation) {
            startActivity(new Intent(BillPaymentActivity.this, NewAssociationActivity.class));
            return true;
        }
        if (id == R.id.cancelAssociation) {
            startActivity(new Intent(BillPaymentActivity.this, CancelAssociationActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
