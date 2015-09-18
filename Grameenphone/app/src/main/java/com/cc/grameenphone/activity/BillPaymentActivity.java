package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillsListAdapter;
import com.cc.grameenphone.api_models.BillListModel;
import com.cc.grameenphone.api_models.BillsCompanyListModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.BillspaymentApi;
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
 * Created by rahul on 11/09/15.
 */
public class BillPaymentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


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
    private String android_id;
    PreferenceManager preferenceManager;
    List<BillsCompanyListModel> billsCompanyListModels;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_payment_activity);
        ButterKnife.inject(this);
        setupToolbar();
        //TODO Listing total number of bills
        billsCompanyListModels = new ArrayList<>();
        View emptyView  = LayoutInflater.from(BillPaymentActivity.this).inflate(R.layout.empty_bills_list,null);
        listViewAdapter = new BillsListAdapter(BillPaymentActivity.this,billsCompanyListModels);
        billsListView.setAdapter(listViewAdapter);
        billsListView.setEmptyView(emptyView);
        android_id = Settings.Secure.getString(BillPaymentActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(BillPaymentActivity.this);
        billspaymentApi = ServiceGenerator.createService(BillspaymentApi.class);
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
                    Logger.d("BILLS response",billListModel.toString());
                    if(billListModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {

                        if (billListModel.getCOMMAND().getMessage().getComapny()!=null) {
                            Logger.d("BILLS response", billListModel.getCOMMAND().getMessage().getComapny().toString());
                            List<BillsCompanyListModel> bills = billListModel.getCOMMAND().getMessage().getComapny();
                            billsCompanyListModels.addAll(bills);
                            listViewAdapter.notifyDataSetChanged();
                        } else {
                            //dontknwo
                        }

                    }
                    else{

                    }

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*selectedPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paySelectDialog = new AppCompatDialog(BillPaymentActivity.this);
                paySelectDialog.setContentView(R.layout.enterpin_dailogue);
                confirmButton = (Button) paySelectDialog.findViewById(R.id.confrimButton);
                paySelectDialog.show();
                paySelectDialog.getWindow().setLayout(650, 500);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
                paySelectDialog.setCanceledOnTouchOutside(true);
            }
        });
        otherPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BillPaymentActivity.this, PaymentActivity.class));
            }
        });*/
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


}
