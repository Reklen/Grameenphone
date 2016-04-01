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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillsListAdapter;
import com.cc.grameenphone.adapter.MultiPayDialogListAdapter;
import com.cc.grameenphone.api_models.BalanceCommandModel;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.BillListModel;
import com.cc.grameenphone.api_models.BillPaymentModel;
import com.cc.grameenphone.api_models.MultiBillApiModel;
import com.cc.grameenphone.api_models.UserBillsModel;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.BIllsPayButtonInterface;
import com.cc.grameenphone.interfaces.BillspaymentApi;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.ConnectivityUtils;
import com.cc.grameenphone.utils.KeyboardUtil;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.utils.ToolBarUtils;
import com.cc.grameenphone.viewmodels.MultiBillListModel;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;


/**
 * Created by rahul on 11/09/15.
 */
public class BillPaymentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, BIllsPayButtonInterface {


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
    @InjectView(R.id.multiBillsCheckBox)
    CheckBox multiBillsCheckBox;
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

        listViewAdapter = new BillsListAdapter(BillPaymentActivity.this, userBillsModels, BillPaymentActivity.this);
        billsListView.setAdapter(listViewAdapter);
        ViewGroup parentGroup = (ViewGroup) billsListView.getParent();
        View emptyView = LayoutInflater.from(BillPaymentActivity.this).inflate(R.layout.empty_list, parentGroup, false);
        parentGroup.addView(emptyView);
        billsListView.setEmptyView(emptyView);
        android_id = Settings.Secure.getString(BillPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(BillPaymentActivity.this);
        billspaymentApi = ServiceGenerator.createService(BillPaymentActivity.this, BillspaymentApi.class);

        fetchBills();
        setupListViewItemClick();
        setupRipples();

    }

    private void fetchBills() {
        loadingDialog.show();
        getWalletBalance();
        userBillsModels.clear();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "SAPLBPREQ");
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("ProfileUpdates", jsonObject.toString());
            //TODO Checking API Calls
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            billspaymentApi.fetchBills(in, new Callback<BillListModel>() {
                @Override
                public void success(BillListModel billListModel, Response response) {
                    //Logger.d("BILLS response", billListModel.toString());
                    if (billListModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {

                        if (billListModel.getCOMMAND().getMessage().getComapny() != null) {
                            //Logger.d("BILLS response", billListModel.getCOMMAND().getMessage().getComapny().toString());
                            List<UserBillsModel> bills = billListModel.getCOMMAND().getMessage().getComapny();
                            for (UserBillsModel b : bills) {
                                if (!b.getBILLNUM().equalsIgnoreCase("null")) {
                                    userBillsModels.add(b);
                                    //Logger.d("UserBills not null");
                                } else {
                                    //Logger.d("UserBills null");
                                }
                            }

                            listViewAdapter.notifyDataSetChanged();
                            loadingDialog.dismiss();
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
                                SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this, true);
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
                    loadingDialog.cancel();
                    if (!ConnectivityUtils.isConnected(BillPaymentActivity.this)) {
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage("Unable to connect to server , please check your connectivity");
                        errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.show();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    MultiBillListModel multiBillListModel;
    List<UserBillsModel> selectedBillsList;
    List<MultiBillListModel> multiBillListModelList;
    MultiPayDialogListAdapter multiPayDialogListAdapter;
    MaterialDialog selectedPayConfirmationDialog;

    @OnClick(R.id.selectedPaymentButton)
    public void selctedBillPay() {

        selectedBillsList = new ArrayList<>();
        multiBillListModelList = new ArrayList<>();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setMessage("Loading , please wait");


        selectedPayConfirmationDialog = new MaterialDialog(BillPaymentActivity.this);
        View view = LayoutInflater.from(BillPaymentActivity.this).inflate(R.layout.dialog_multi_bill, null);
        ListView listView = (ListView) view.findViewById(R.id.multiBillsStatusListView);
        for (String modelString : billsSelectedList)
            selectedBillsList.add(((BillsListAdapter) billsListView.getAdapter()).getItem(Integer.valueOf(modelString)));
        for (UserBillsModel model : selectedBillsList) {
            multiBillListModel = new MultiBillListModel();
            multiBillListModel.setAccountNumber(model.getACCOUNTNUM());
            multiBillListModel.setAmount(model.getAMOUNT());
            multiBillListModel.setCompanyName(model.getCOMPANYNAME());
            multiBillListModel.setBillNum(model.getBILLNUM());
            multiBillListModel.setbProvider(model.getBPROVIDER());
            multiBillListModel.setStatus(-1);
            multiBillListModelList.add(multiBillListModel);
        }

        multiPayDialogListAdapter = new MultiPayDialogListAdapter(BillPaymentActivity.this, multiBillListModelList);
        listView.setAdapter(multiPayDialogListAdapter);
        TextView topLabel = (TextView) view.findViewById(R.id.top_text);
        topLabel.setText("You will get payment confirmation SMS shortly from associations.");
        selectedPayConfirmationDialog.setContentView(view);


        selectedPayConfirmationDialog.setPositiveButton("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPayConfirmationDialog.dismiss();
                loadingDialog.cancel();
                preferenceManager.setWalletBalance("");
                fetchBills();
                selectedPaymentButton.setText("PAY SELECTED BILLS");
                listViewAdapter.togglePayButton(true);
                selectedPayRippleView.setVisibility(View.GONE);
                quickPayRippleView.setVisibility(View.VISIBLE);
                multiBillsCheckBox.setVisibility(View.GONE);
                multiBillListModelList.clear();
                billsSelectedList.clear();
                multiBillsCheckBox.setChecked(false);
                billsbar.setText("My Pending bills");
                otherPayRippleView.setVisibility(View.VISIBLE);
            }
        });

        pinConfirmationView = LayoutInflater.from(BillPaymentActivity.this).inflate(R.layout.dialog_pin_confirmation, null);
        pinConfirmationET = (EditText) pinConfirmationView.findViewById(R.id.pinConfirmEditText);

        pinConfirmDialog = new MaterialDialog(BillPaymentActivity.this);
        pinConfirmDialog.setContentView(pinConfirmationView);
        pinConfirmDialog.setPositiveButton("CONFIRM", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pinConfirmationET.getText().toString().length() < 4) {
                    pinConfirmationET.requestFocus();
                    pinConfirmationET.setError("Enter your valid pin");
                    return;
                }
                loadingDialog.show();
                String pin = pinConfirmationET.getText().toString();
                pinConfirmDialog.dismiss();
                // KeyboardUtil.hideKeyboard(BillPaymentActivity.this);

                payBillForMultiPosition(multiBillListModelList, pin);

            }
        });
        pinConfirmDialog.show();


        //Logger.d("Bills selected", "Position are " + billsSelectedList.toString());


    }

    /*private void payBillForMultiPosition(final MultiBillListModel userBillsModel, String pin) {

        KeyboardUtil.hideKeyboard(BillPaymentActivity.this);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CPMPBREQ");
            innerObject.put("BILLCCODE", userBillsModel.getCompanyName().toUpperCase());
            innerObject.put("BILLANO", userBillsModel.getAccountNumber());
            innerObject.put("AMOUNT", userBillsModel.getAmount());
            innerObject.put("BILLNO", userBillsModel.getBillNum());
            innerObject.put("PIN", pin);//TODO add pin confirm dialog
            innerObject.put("BPROVIDER", userBillsModel.getbProvider());
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("Paying Bill", jsonObject.toString());
            //TODO Checking API Calls
            billspaymentApi.payBill(jsonObject, new Callback<BillPaymentModel>() {
                @Override
                public void success(BillPaymentModel billPaymentModel, Response response) {
                    //Logger.d("BILLS response", billPaymentModel.toString());
                    if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        loadingDialog.dismiss();
                        userBillsModel.setStatus(1);
                        multiPayDialogListAdapter.notifyDataSetChanged();

                    } else if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        Logger.e("Balance", billPaymentModel.getCOMMAND().toString() + " ");
                        loadingDialog.cancel();
                        userBillsModel.setStatus(0);
                        multiPayDialogListAdapter.notifyDataSetChanged();
                        selectedPayConfirmationDialog.dismiss();
                        sessionDialog = new MaterialDialog(BillPaymentActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sessionDialog.dismiss();
                                SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this, false);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("00068")) {
                        loadingDialog.cancel();

                        selectedPayConfirmationDialog.dismiss();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage(billPaymentModel.getCOMMAND().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                    } else {
                        loadingDialog.cancel();
                        selectedPayConfirmationDialog.dismiss();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage(billPaymentModel.getCOMMAND().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                        Logger.e("Balance", billPaymentModel.toString());
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("BILLS", error.getMessage());
                    loadingDialog.cancel();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
            loadingDialog.cancel();
        }

    }*/
    JSONObject billsInnerJsonObject;

    private void payBillForMultiPosition(final List<MultiBillListModel> userBillsModel, String pin) {

        KeyboardUtil.hideKeyboard(BillPaymentActivity.this);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "BLKBPAYREQ");
            innerObject.put("NOOFBILLS", userBillsModel.size());
            JSONArray billsJsonArray = new JSONArray();

            /*
              innerObject.put("BILLCCODE", userBillsModel.getCompanyName().toUpperCase());
            innerObject.put("BILLANO", userBillsModel.getAccountNumber());
            innerObject.put("AMOUNT", userBillsModel.getAmount());
            innerObject.put("BILLNO", userBillsModel.getBillNum());
            innerObject.put("PIN", pin);//TODO add pin confirm dialog
            innerObject.put("BPROVIDER", userBillsModel.getbProvider());
             */
            for (MultiBillListModel multiBillListModel : userBillsModel) {
                billsInnerJsonObject = new JSONObject();
                billsInnerJsonObject.put("BILLCCODE", multiBillListModel.getCompanyName().toUpperCase());
                billsInnerJsonObject.put("BILLANO", multiBillListModel.getAccountNumber());
                billsInnerJsonObject.put("AMOUNT", multiBillListModel.getAmount());
                billsInnerJsonObject.put("BILLNO", multiBillListModel.getBillNum());
                billsInnerJsonObject.put("BPROVIDER", multiBillListModel.getbProvider());
                billsJsonArray.put(billsInnerJsonObject);
            }

            innerObject.put("BILLDET", billsJsonArray);

            innerObject.put("PIN", pin);//TODO add pin confirm dialog
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("Paying Bill", jsonObject.toString());
            //TODO Checking API Calls
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));

            billspaymentApi.payMultipleBill(in, new Callback<MultiBillApiModel>() {
                @Override
                public void success(MultiBillApiModel multiBillApiModel, Response response) {
                    //Logger.d(multiBillApiModel.toString());
                    if (multiBillApiModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        //Success
                        loadingDialog.dismiss();
                        selectedPayConfirmationDialog.show();
                        for (int i = 0; i < multiBillApiModel.getCOMMAND().getBILLDET().size(); i++) {
                            if (multiBillApiModel.getCOMMAND().getBILLDET().get(i) != null && multiBillApiModel.getCOMMAND().getBILLDET().get(i).getTXNSTATUS() != null)
                                try {
                                    if (multiBillApiModel.getCOMMAND().getBILLDET().get(i).getTXNSTATUS().equalsIgnoreCase("200")) {
                                        userBillsModel.get(i).setStatus(1);
                                        multiPayDialogListAdapter.notifyDataSetChanged();

                                    } else {
                                        userBillsModel.get(i).setStatus(0);
                                        multiPayDialogListAdapter.notifyDataSetChanged();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                        //     multiBillListModel
                    } else if (multiBillApiModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("01035")) {
                        loadingDialog.dismiss();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage("You have entered an invalid pin");
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                    } else if (multiBillApiModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("00068")) {
                        loadingDialog.dismiss();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage(multiBillApiModel.getCOMMAND().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e(error);
                    loadingDialog.cancel();
                }
            });
          /*  billspaymentApi.payBill(jsonObject, new Callback<BillPaymentModel>() {
                @Override
                public void success(BillPaymentModel billPaymentModel, Response response) {
                    //Logger.d("BILLS response", billPaymentModel.toString());
                    if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        loadingDialog.dismiss();
                        userBillsModel.setStatus(1);
                        multiPayDialogListAdapter.notifyDataSetChanged();

                    } else if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        Logger.e("Balance", billPaymentModel.getCOMMAND().toString() + " ");
                        loadingDialog.cancel();
                        userBillsModel.setStatus(0);
                        multiPayDialogListAdapter.notifyDataSetChanged();
                        selectedPayConfirmationDialog.dismiss();
                        sessionDialog = new MaterialDialog(BillPaymentActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sessionDialog.dismiss();
                                SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this, false);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("00068")) {
                        loadingDialog.cancel();

                        selectedPayConfirmationDialog.dismiss();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage(billPaymentModel.getCOMMAND().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                    } else {
                        loadingDialog.cancel();
                        selectedPayConfirmationDialog.dismiss();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage(billPaymentModel.getCOMMAND().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                        Logger.e("Balance", billPaymentModel.toString());
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("BILLS", error.getMessage());
                    loadingDialog.cancel();
                }
            });*/


        } catch (JSONException e) {
            e.printStackTrace();
            loadingDialog.cancel();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void setupListViewItemClick() {
        billsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Logger.d("bills test ", "clicked on " + view.getId() + "");
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.billCheckBox);
                UserBillsModel userBillsModel = (UserBillsModel) listViewAdapter.getItem(position);
                //Logger.d("bills test ", "clicked on " + position + " " + billsSelectedList.toString());
                if (billsSelectedList.contains(position + "")) {
                    billsSelectedList.remove(position + "");
                    checkBox.setChecked(false);
                    multiBillsCheckBox.setChecked(false);
                    try {
                        Field field = CompoundButton.class.getDeclaredField("mChecked");
                        field.setAccessible(true);
                        field.set(multiBillsCheckBox, multiBillsCheckBox.isChecked());
                        multiBillsCheckBox.refreshDrawableState();
                        multiBillsCheckBox.invalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selectedPaymentButton.setText("PAY SELECTED BILLS");
                    userBillsModel.setIsSelected(false);
                } else {
                    billsSelectedList.add(position + "");
                    checkBox.setChecked(true);
                    selectedPaymentButton.setText("PAY SELECTED BILLS");
                    userBillsModel.setIsSelected(true);
                }

                if (billsSelectedList.size() == 0) {
                    listViewAdapter.togglePayButton(true);
                    selectedPayRippleView.setVisibility(View.GONE);
                    quickPayRippleView.setVisibility(View.VISIBLE);
                    multiBillsCheckBox.setVisibility(View.GONE);
                    multiBillsCheckBox.setChecked(false);
                    billsbar.setText("My Pending bills");
                    otherPayRippleView.setVisibility(View.VISIBLE);
                } else {
                    listViewAdapter.togglePayButton(false);
                    selectedPayRippleView.setVisibility(View.VISIBLE);
                    quickPayRippleView.setVisibility(View.GONE);
                    multiBillsCheckBox.setVisibility(View.VISIBLE);
                    billsbar.setText("Select all bills");
                    otherPayRippleView.setVisibility(View.GONE);
                }

               /* if (billsSelectedList.size() == listViewAdapter.getCount()) {
                    selectedPaymentButton.setText("PAY ALL BILLS");
                } else {
                    selectedPaymentButton.setText("PAY SELECTED BILLS");
                }*/
            }
        });

        multiBillsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int k = 0; k < listViewAdapter.getListitemslist().size(); k++) {
                    UserBillsModel bills = listViewAdapter.getItem(k);
                    if (isChecked) {
                        bills.setIsSelected(true);
                        if (!billsSelectedList.contains(k + "")) {
                            billsSelectedList.add(k + "");
                        }
                    } else {
                        bills.setIsSelected(false);
                        billsSelectedList.clear();
                    }
                }
                listViewAdapter.notifyDataSetChanged();
                if (isChecked)
                    selectedPaymentButton.setText("PAY ALL BILLS");
                else {
                    selectedPaymentButton.setText("PAY SELECTED BILLS");
                    listViewAdapter.togglePayButton(true);
                    selectedPayRippleView.setVisibility(View.GONE);
                    quickPayRippleView.setVisibility(View.VISIBLE);
                    multiBillsCheckBox.setVisibility(View.GONE);
                    multiBillsCheckBox.setChecked(false);
                    billsbar.setText("My Pending bills");
                    otherPayRippleView.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void getWalletBalance() {

        walletCheckApi = ServiceGenerator.createService(BillPaymentActivity.this, WalletCheckApi.class);
        android_id = Settings.Secure.getString(BillPaymentActivity.this.getContentResolver(),
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
                            preferenceManager.setWalletBalance(balanceEnquiryModel.getCOMMAND().getBALANCE());
                            preferenceManager.setWalletMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE());
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
                                    SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this, true);
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
            else {
                walletLabel.setText("  ৳ " + preferenceManager.getWalletBalance());
                BalanceEnquiryModel balanceEnquiryModel = new BalanceEnquiryModel();
                BalanceCommandModel balanceCommandModel = new BalanceCommandModel();
                balanceCommandModel.setMESSAGE(preferenceManager.getWalletMessage());
                balanceEnquiryModel.setCOMMAND(balanceCommandModel);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
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

    View pinConfirmationView;
    EditText pinConfirmationET;
    MaterialDialog pinConfirmDialog;

    @Override
    public void payClickedAt(int position) {
        //Logger.d("bills test ", "clicked on " + position + "");
        final UserBillsModel userBillsModel = listViewAdapter.getItem(position);
        pinConfirmationView = LayoutInflater.from(BillPaymentActivity.this).inflate(R.layout.dialog_pin_confirmation, null);
        pinConfirmationET = (EditText) pinConfirmationView.findViewById(R.id.pinConfirmEditText);

        pinConfirmDialog = new MaterialDialog(BillPaymentActivity.this);
        pinConfirmDialog.setContentView(pinConfirmationView);
        pinConfirmDialog.setPositiveButton("CONFIRM", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinConfirmationET.getText().toString().length() < 4) {
                    pinConfirmationET.setError("Enter your valid pin");
                    pinConfirmationET.requestFocus();
                    return;
                }
                String pin = pinConfirmationET.getText().toString();
                payBillForClickedPosition(userBillsModel, pin);
                pinConfirmDialog.dismiss();
            }
        });
        pinConfirmDialog.show();
    }

    MaterialDialog dialog;


    private void payBillForClickedPosition(UserBillsModel userBillsModel, String pin) {
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setMessage("Loading , please wait");
        loadingDialog.show();
        KeyboardUtil.hideKeyboard(BillPaymentActivity.this);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CPMPBREQ");
            innerObject.put("BILLCCODE", userBillsModel.getCOMPANYNAME().toUpperCase());
            innerObject.put("BILLANO", userBillsModel.getACCOUNTNUM());
            innerObject.put("AMOUNT", userBillsModel.getAMOUNT());
            innerObject.put("BILLNO", userBillsModel.getBILLNUM());
            innerObject.put("PIN", pin);//TODO add pin confirm dialog
            innerObject.put("BPROVIDER", userBillsModel.getBPROVIDER());
            jsonObject.put("COMMAND", innerObject);
            //Logger.d("Paying Bill", jsonObject.toString());
            //TODO Checking API Calls
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            billspaymentApi.payBill(in, new Callback<BillPaymentModel>() {
                @Override
                public void success(BillPaymentModel billPaymentModel, Response response) {
                    //Logger.d("BILLS response", billPaymentModel.toString());
                    if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        loadingDialog.dismiss();
                        dialog = new MaterialDialog(BillPaymentActivity.this);
                        dialog.setMessage("" + billPaymentModel.getCOMMAND().getMESSAGE());
                        dialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                preferenceManager.setWalletBalance("");

                                fetchBills();
                                //  getWalletBalance();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    } else if (billPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                        Logger.e("Balance", billPaymentModel.getCOMMAND().toString() + " ");
                        loadingDialog.cancel();
                        sessionDialog = new MaterialDialog(BillPaymentActivity.this);
                        sessionDialog.setMessage("Session expired , please login again");
                        sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sessionDialog.dismiss();
                                SessionClearTask sessionClearTask = new SessionClearTask(BillPaymentActivity.this, true);
                                sessionClearTask.execute();

                            }
                        });
                        sessionDialog.setCanceledOnTouchOutside(false);
                        sessionDialog.show();
                    } else {
                        loadingDialog.cancel();
                        errorDialog = new MaterialDialog(BillPaymentActivity.this);
                        errorDialog.setMessage(billPaymentModel.getCOMMAND().getMESSAGE());
                        errorDialog.setPositiveButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.setCanceledOnTouchOutside(true);
                        errorDialog.show();
                        Logger.e("Balance", billPaymentModel.toString());
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("BILLS", error.getMessage());
                    loadingDialog.cancel();
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
            loadingDialog.cancel();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (loadingDialog != null)
            if (!loadingDialog.isShowing())
                super.onBackPressed();

    }
}
