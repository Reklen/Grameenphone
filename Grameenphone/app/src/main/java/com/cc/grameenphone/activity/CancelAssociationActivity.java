package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.CancelAssociationAdapter;
import com.cc.grameenphone.adapter.MultiCancelDialogListAdapter;
import com.cc.grameenphone.api_models.AssociationBillModel;
import com.cc.grameenphone.api_models.AssociationModel;
import com.cc.grameenphone.api_models.CancelAssociationModel;
import com.cc.grameenphone.api_models.CancelMultipleResponseModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ButtonClickInterface;
import com.cc.grameenphone.interfaces.ManageAssociationApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.viewmodels.MultiCancelAssociationListModel;
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
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationActivity extends AppCompatActivity implements ButtonClickInterface {
    Context context;
    CancelAssociationAdapter adapter;


    List<AssociationBillModel> list;

    MaterialDialog cancelDialog, removeDialog;
    ManageAssociationApi getAssociationApi;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.associationList)
    ListView associationListView;
    @InjectView(R.id.selectedCancelButton)
    Button selectedCancelButton;
    @InjectView(R.id.multiCancelBillsCheckBox)
    CheckBox multiCancelBillsCheckBox;
    @InjectView(R.id.cancelBillsbar)
    TextView cancelBillsbar;
    @InjectView(R.id.selectedCancelRippleView)
    RippleView selectedCancelRippleView;
    private String android_id;
    private PreferenceManager preferenceManager;
    private MaterialDialog errorDialog;
    private ProgressDialog loadingDialog;
    List<String> cancelBillsSelectedList;
    private ArrayList<AssociationBillModel> selectedCancelModelList;
    private ArrayList<MultiCancelAssociationListModel> multiBillListModelList;
    private MaterialDialog selectedPayConfirmationDialog;
    private MultiCancelAssociationListModel multiModel;
    private MultiCancelDialogListAdapter multiPayDialogListAdapter;
    private View pinConfirmationView;
    private EditText pinConfirmationET;
    private MaterialDialog pinConfirmDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_association);
        ButterKnife.inject(this);
        setupToolbar();
        init();
        cancelBillsSelectedList = new ArrayList<>();

    }


    private void setupToolbar() {
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
    }

    @OnClick(R.id.selectedCancelButton)
    public void selctedBillCancel() {
        selectedCancelModelList = new ArrayList<>();
        multiBillListModelList = new ArrayList<>();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setMessage("Loading , please wait");


        selectedPayConfirmationDialog = new MaterialDialog(CancelAssociationActivity.this);
        View view = LayoutInflater.from(CancelAssociationActivity.this).inflate(R.layout.dialog_multi_bill, null);
        ListView listView = (ListView) view.findViewById(R.id.multiBillsStatusListView);
        for (String modelString : cancelBillsSelectedList)
            selectedCancelModelList.add(((CancelAssociationAdapter) associationListView.getAdapter()).getItem(Integer.valueOf(modelString)));
        for (AssociationBillModel model : selectedCancelModelList) {
            multiModel = new MultiCancelAssociationListModel();
            multiModel.setpRef(model.getACCNUM());
            multiModel.setBillCancelCode(model.getCOMPCODE());
            multiModel.setStatus(-1);
            multiBillListModelList.add(multiModel);
        }

        multiPayDialogListAdapter = new MultiCancelDialogListAdapter(CancelAssociationActivity.this, multiBillListModelList);
        listView.setAdapter(multiPayDialogListAdapter);
        TextView topLabel = (TextView) view.findViewById(R.id.top_text);
        topLabel.setText("You will get payment confirmation SMS shortly from associations.");
        selectedPayConfirmationDialog.setContentView(view);


        selectedPayConfirmationDialog.setPositiveButton("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPayConfirmationDialog.dismiss();
                loadingDialog.cancel();
                fetchData();
                selectedCancelButton.setText("PAY SELECTED BILLS");
                adapter.toggleCancelButton(true);
                selectedCancelButton.setVisibility(View.GONE);
                multiCancelBillsCheckBox.setVisibility(View.GONE);
                multiBillListModelList.clear();
                selectedCancelModelList.clear();
                multiCancelBillsCheckBox.setChecked(false);
                cancelBillsbar.setText("My association");
            }
        });

        /*pinConfirmationView = LayoutInflater.from(CancelAssociationActivity.this).inflate(R.layout.dialog_pin_confirmation, null);
        pinConfirmationET = (EditText) pinConfirmationView.findViewById(R.id.pinConfirmEditText);

        pinConfirmDialog = new MaterialDialog(CancelAssociationActivity.this);
        pinConfirmDialog.setContentView(pinConfirmationView);
        pinConfirmDialog.setPositiveButton("CONFIRM", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pinConfirmationET.getText().toString().length() != 4) {
                    pinConfirmationET.requestFocus();
                    pinConfirmationET.setError("Enter your valid pin");
                    return;
                }
                loadingDialog.show();
                String pin = pinConfirmationET.getText().toString();
                pinConfirmDialog.dismiss();
                // KeyboardUtil.hideKeyboard(BillPaymentActivity.this);
                selectedPayConfirmationDialog.show();
                cancelMultiple(multiBillListModelList, pin);

            }
        });
        pinConfirmDialog.show();
*/

        loadingDialog.show();
        selectedPayConfirmationDialog.show();
        cancelMultiple(multiBillListModelList);

        Logger.d("Bills selected", "Position are " + cancelBillsSelectedList.toString());

    }

    private void init() {
        list = new ArrayList<>();
        loadingDialog = new ProgressDialog(CancelAssociationActivity.this);

        android_id = Settings.Secure.getString(CancelAssociationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(CancelAssociationActivity.this);
        getAssociationApi = ServiceGenerator.createService(ManageAssociationApi.class);

        adapter = new CancelAssociationAdapter(this, list, this);
        associationListView.setAdapter(adapter);
        ViewGroup parentGroup = (ViewGroup) associationListView.getParent();
        View emptyView = LayoutInflater.from(CancelAssociationActivity.this).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No Associated accounts found");
        parentGroup.addView(emptyView);
        associationListView.setEmptyView(emptyView);
        fetchData();

        associationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("CancelAssociation", "Position " + position);

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cancelCheckBox);
                Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

                AssociationBillModel associationBillModel = (AssociationBillModel) adapter.getItem(position);
                if (cancelBillsSelectedList.contains(position + "")) {
                    cancelBillsSelectedList.remove(position + "");
                    checkBox.setChecked(false);
                    multiCancelBillsCheckBox.setChecked(false);
                    try {
                        Field field = CompoundButton.class.getDeclaredField("mChecked");
                        field.setAccessible(true);
                        field.set(multiCancelBillsCheckBox, multiCancelBillsCheckBox.isChecked());
                        multiCancelBillsCheckBox.refreshDrawableState();
                        multiCancelBillsCheckBox.invalidate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    selectedCancelButton.setText("CANCEL SELECTED BILLS");
                    associationBillModel.setIsSelected(false);
                } else {
                    cancelBillsSelectedList.add(position + "");
                    checkBox.setChecked(true);
                    selectedCancelButton.setText("CANCEL SELECTED BILLS");
                    associationBillModel.setIsSelected(true);
                }
                if (cancelBillsSelectedList.size() == 0) {
                    adapter.toggleCancelButton(true);
                    selectedCancelRippleView.setVisibility(View.GONE);
                    multiCancelBillsCheckBox.setVisibility(View.GONE);
                    // cancelButton.setVisibility(View.VISIBLE);
                    multiCancelBillsCheckBox.setChecked(false);
                    cancelBillsbar.setText("Association List");


                } else {
                    adapter.toggleCancelButton(false);
                    selectedCancelRippleView.setVisibility(View.VISIBLE);
                    multiCancelBillsCheckBox.setVisibility(View.VISIBLE);
                    // cancelButton.setVisibility(View.GONE);
                    cancelBillsbar.setText("Cancel selected bills");
                }

            }
        });
        multiCancelBillsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int k = 0; k < adapter.getListitemslist().size(); k++) {
                    AssociationBillModel bills = adapter.getItem(k);
                    if (isChecked) {
                        bills.setIsSelected(true);
                        if (!cancelBillsSelectedList.contains(k + "")) {
                            cancelBillsSelectedList.add(k + "");
                        }
                    } else {
                        bills.setIsSelected(false);
                        cancelBillsSelectedList.clear();
                    }
                }
                adapter.notifyDataSetChanged();
                if (isChecked) {
                    Log.d("association", adapter.getListitemslist().toString());
                    selectedCancelButton.setText("CANCEL ALL ASSOCIATION");
                } else {
                    selectedCancelButton.setText("CANCEL SELECTED BILLS");
                    adapter.toggleCancelButton(true);
                    selectedCancelRippleView.setVisibility(View.GONE);
                    multiCancelBillsCheckBox.setVisibility(View.GONE);
                    multiCancelBillsCheckBox.setChecked(false);
                    cancelBillsbar.setText("My Pending bills");
                    //otherPayRippleView.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void fetchData() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "FBILASCREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("sending json", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            getAssociationApi.fetchUserAssociaition(in, new Callback<AssociationModel>() {
                @Override
                public void success(AssociationModel associationModel, Response response) {
                    Logger.d("CancelCrash", associationModel.toString());
                    if (associationModel.getCommandModel().getTXNSTATUS().equalsIgnoreCase("200")) {
                        list.clear();
                        if (associationModel.getCommandModel().getBILLDEL() != null) {
                            for (AssociationBillModel associationBillModel : associationModel.getCommandModel().getBILLDEL())
                                if (associationBillModel.getACCNUM() != null && !associationBillModel.getACCNUM().equalsIgnoreCase("null"))
                                    list.add(associationBillModel);

                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Logger.e("CancelAssociation", associationModel.getCommandModel().getTXNSTATUS().toString() + "");
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("CancelAssociation", error.getMessage() + "");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBtnClick(int position) {

        final AssociationBillModel model = adapter.getItem(position);
        adapter.clear();
        cancelDialog = new MaterialDialog(CancelAssociationActivity.this);
        cancelDialog.setMessage("Remove Acc. No: " + model.getACCNUM() + " from " + model.getCOMPCODE() + " association ?");
        Log.d("billNumb", model.getACCNUM());
        Log.d("compCode", model.getCOMPCODE());
        cancelDialog.setPositiveButton("Remove", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.setMessage("Cancelling association " + model.getACCNUM() + " from " + model.getCOMPCODE());
                loadingDialog.show();

                try {
                    JSONObject jsonObject = new JSONObject();
                    JSONObject innerObject = new JSONObject();
                    innerObject.put("DEVICEID", android_id);
                    innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                    innerObject.put("MSISDN", preferenceManager.getMSISDN());
                    innerObject.put("TYPE", "BPREGDREQ");
                    innerObject.put("BILLCCODE", model.getCOMPCODE());
                    innerObject.put("PREF1", model.getACCNUM());
                    jsonObject.put("COMMAND", innerObject);
                    Logger.d("sending json", jsonObject.toString());
                    cancelDialog.dismiss();
                    String json = jsonObject.toString();
                    TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
                    getAssociationApi.cancelAssociation(in, new Callback<CancelAssociationModel>() {
                        @Override
                        public void success(final CancelAssociationModel cancelAssociationModel, Response response) {
                            loadingDialog.cancel();
                            if (cancelAssociationModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                                removeDialog = new MaterialDialog(CancelAssociationActivity.this);
                                removeDialog.setMessage(cancelAssociationModel.getCommand().getMESSAGE());
                                removeDialog.setPositiveButton("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        removeDialog.dismiss();

                                        startActivity(new Intent(CancelAssociationActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();


                                    }

                                });

                                removeDialog.show();
                            } else

                            {
                                errorDialog = new MaterialDialog(CancelAssociationActivity.this);
                                errorDialog.setMessage(cancelAssociationModel.getCommand().getMESSAGE());
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
                            Logger.e("CancelAssociation", error.getMessage() + "");
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });
        cancelDialog.setNegativeButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelDialog.dismiss();
            }
        });
        cancelDialog.show();

    }

    JSONObject billsInnerJsonObject;

    private void cancelMultiple(final List<MultiCancelAssociationListModel> userBillsModel) {

        // KeyboardUtil.hideKeyboard(CancelAssociationActivity.this);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "BLKCBASREQ");
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
            for (MultiCancelAssociationListModel multiBillListModel : userBillsModel) {
                billsInnerJsonObject = new JSONObject();
                billsInnerJsonObject.put("PREF1", multiBillListModel.getpRef());
                billsInnerJsonObject.put("BILLCCODE", multiBillListModel.getBillCancelCode().toUpperCase());
                billsJsonArray.put(billsInnerJsonObject);

            }

            if (userBillsModel.size() == 1) {
                billsInnerJsonObject = new JSONObject();
                billsInnerJsonObject.put("PREF1", null);
                billsInnerJsonObject.put("BILLCCODE", null);
                billsJsonArray.put(billsInnerJsonObject);
            }

            innerObject.put("BILLDET", billsJsonArray);

            // innerObject.put("PIN", pin);//TODO add pin confirm dialog
            jsonObject.put("COMMAND", innerObject);
            Logger.d("Paying Bill", jsonObject.toString());
            //TODO Checking API Calls
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            getAssociationApi.cancelMultipleAssociation(in, new Callback<CancelMultipleResponseModel>() {
                        @Override
                        public void success(CancelMultipleResponseModel cancelMultipleResponseModel, Response response) {
                            Logger.d(cancelMultipleResponseModel.toString());

                            if (cancelMultipleResponseModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                                //Success
                                loadingDialog.dismiss();
                                for (int i = 0; i < cancelMultipleResponseModel.getCommand().getBillDetailsList().size(); i++) {
                                    if (cancelMultipleResponseModel.getCommand().getBillDetailsList().get(i) != null && cancelMultipleResponseModel.getCommand().getBillDetailsList().get(i).getTXNSTATUS() != null)
                                        if (cancelMultipleResponseModel.getCommand().getBillDetailsList().get(i).getTXNSTATUS().equalsIgnoreCase("200")) {
                                            userBillsModel.get(i).setStatus(1);
                                            multiPayDialogListAdapter.notifyDataSetChanged();

                                        } else {
                                            userBillsModel.get(i).setStatus(0);
                                            multiPayDialogListAdapter.notifyDataSetChanged();
                                        }
                                }
                                //     multiBillListModel
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    }
            );
                    /*
                     new Callback<CancelAssociationModel>() {
                @Override
                public void success(CancelAssociationModel cancelAssociationModel, Response response) {

                    // TODO change response api model



                   /* Logger.d(cancelAssociationModel.toString());

                    if (cancelAssociationModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        //Success
                        loadingDialog.dismiss();
                        for (int i = 0; i < cancelAssociationModel.getCommand().get().size(); i++) {
                            if (multiBillApiModel.getCOMMAND().getBILLDET().get(i) != null && multiBillApiModel.getCOMMAND().getBILLDET().get(i).getTXNSTATUS() != null)
                                if (multiBillApiModel.getCOMMAND().getBILLDET().get(i).getTXNSTATUS().equalsIgnoreCase("200")) {
                                    userBillsModel.get(i).setStatus(1);
                                    multiPayDialogListAdapter.notifyDataSetChanged();

                                } else {
                                    userBillsModel.get(i).setStatus(0);
                                    multiPayDialogListAdapter.notifyDataSetChanged();
                                }
                        }
                        //     multiBillListModel
                    }
        }

        @Override
        public void failure(RetrofitError error) {

        }
    });*/

          /*  billspaymentApi.payBill(jsonObject, new Callback<BillPaymentModel>() {
                @Override
                public void success(BillPaymentModel billPaymentModel, Response response) {
                    Logger.d("BILLS response", billPaymentModel.toString());
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


}
