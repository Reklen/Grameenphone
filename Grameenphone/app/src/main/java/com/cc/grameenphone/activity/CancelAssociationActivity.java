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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.CancelAssociationAdapter;
import com.cc.grameenphone.api_models.AssociationBillModel;
import com.cc.grameenphone.api_models.AssociationModel;
import com.cc.grameenphone.api_models.CancelAssociationModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ButtonClickInterface;
import com.cc.grameenphone.interfaces.ManageAssociationApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

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
    ListView associationList;
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

    }

    private void init() {
        list = new ArrayList<>();
        loadingDialog = new ProgressDialog(CancelAssociationActivity.this);

        android_id = Settings.Secure.getString(CancelAssociationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(CancelAssociationActivity.this);
        getAssociationApi = ServiceGenerator.createService(ManageAssociationApi.class);

        adapter = new CancelAssociationAdapter(this, list, this);
        associationList.setAdapter(adapter);
        ViewGroup parentGroup = (ViewGroup) associationList.getParent();
        View emptyView = LayoutInflater.from(CancelAssociationActivity.this).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No Associated accounts found");
        parentGroup.addView(emptyView);
        associationList.setEmptyView(emptyView);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "FBILASCREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("sending json", jsonObject.toString());
            getAssociationApi.fetchUserAssociaition(jsonObject, new Callback<AssociationModel>() {
                @Override
                public void success(AssociationModel associationModel, Response response) {
                    if (associationModel.getCommandModel().getTXNSTATUS().equalsIgnoreCase("200")) {
                        list.clear();
                        if (associationModel.getCommandModel().getBILLDEL() != null) {
                            list.addAll(associationModel.getCommandModel().getBILLDEL());
                            adapter.notifyDataSetChanged();
                        }
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
        }

        associationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("CancelAssociation", "Position " + position);

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.cancelCheckBox);
                Button cancelButton= (Button) view.findViewById(R.id.cancelButton);

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


                }else {
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
                }
                else {
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

    @Override
    public void onBtnClick(int position) {

        final AssociationBillModel model = adapter.getItem(position);
        adapter.clear();
        cancelDialog = new MaterialDialog(CancelAssociationActivity.this);
        cancelDialog.setMessage("Remove Acc. No: " + model.getACCNUM() + " from " + model.getCOMPCODE() + " association ?");
        Log.d("billNumb", model.getACCNUM());
        Log.d("compCode",model.getCOMPCODE());
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
                    innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
                    innerObject.put("TYPE", "BPREGDREQ");
                    innerObject.put("BILLCCODE", model.getCOMPCODE());
                    innerObject.put("PREF1", model.getACCNUM());
                    jsonObject.put("COMMAND", innerObject);
                    Logger.d("sending json", jsonObject.toString());
                    cancelDialog.dismiss();
                    getAssociationApi.cancelAssociation(jsonObject, new Callback<CancelAssociationModel>() {
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


}
