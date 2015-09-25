package com.cc.grameenphone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
    private String android_id;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_association);
        ButterKnife.inject(this);
        setupToolbar();
        init();

    }


    private void setupToolbar() {
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
    }

    private void init() {
        list = new ArrayList<>();
        android_id = Settings.Secure.getString(CancelAssociationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(CancelAssociationActivity.this);
        getAssociationApi = ServiceGenerator.createService(ManageAssociationApi.class);

        adapter = new CancelAssociationAdapter(this, list, this);
        associationList.setAdapter(adapter);
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
                        list.addAll(associationModel.getCommandModel().getBILLDEL());
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
        }


        associationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("CancelAssociation", "Position " + position);

            }
        });
    }

    @Override
    public void onBtnClick(int position) {
        final AssociationBillModel model = adapter.getItem(position);
        cancelDialog = new MaterialDialog(CancelAssociationActivity.this);
        cancelDialog.setMessage("Remove Acc. No: " + model.getACCNUM() + " from " + model.getCOMPCODE() + " association ?");
        cancelDialog.setPositiveButton("Remove", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                            removeDialog = new MaterialDialog(CancelAssociationActivity.this);
                            removeDialog.setMessage(cancelAssociationModel.getCommand().getMESSAGE());
                            removeDialog.setPositiveButton("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    removeDialog.dismiss();
                                    if (cancelAssociationModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                                        startActivity(new Intent(CancelAssociationActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        finish();
                                    }

                                }
                            });
                            removeDialog.show();
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
