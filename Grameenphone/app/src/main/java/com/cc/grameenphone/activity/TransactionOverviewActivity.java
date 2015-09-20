package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.TransactionOverviewAdapter;
import com.cc.grameenphone.api_models.TransactionOverviewData;
import com.cc.grameenphone.api_models.TransactionOverviewModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.TransactionOverviewApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TransactionOverviewActivity extends AppCompatActivity {


    TextView tooltext;

    ImageView back_icon;
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.transactionToolbar)
    Toolbar transactionToolbar;
    @InjectView(R.id.transactionList)
    ListView transactionList;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    TransactionOverviewAdapter adapter;
    ProgressDialog loadingDialog;
    List<TransactionOverviewData> listItemsList;
    private String android_id;
    private PreferenceManager preferenceManager;
    TransactionOverviewApi transactionOverviewApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview);
        ButterKnife.inject(this);
        listItemsList = new ArrayList<>();
        loadingDialog = new ProgressDialog(TransactionOverviewActivity.this);
        loadingDialog.setMessage("Fetching Transactions");
        textTool.setText("Transaction Overview");
        android_id = Settings.Secure.getString(TransactionOverviewActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(TransactionOverviewActivity.this);

        transactionOverviewApi = ServiceGenerator.createService(TransactionOverviewApi.class);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        adapter = new TransactionOverviewAdapter(TransactionOverviewActivity.this, listItemsList);
        transactionList.setAdapter(adapter);
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(TransactionOverviewActivity.this,
                        TransactionOverviewDeatilsActivity.class).putExtra("transaction_obj", adapter.getItem(i)));
            }
        });
        fetchList();
    }

    private void fetchList() {
        loadingDialog.show();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CLTREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("sending json", jsonObject.toString());
            transactionOverviewApi.fetchStatements(jsonObject, new Callback<TransactionOverviewModel>() {
                @Override
                public void success(TransactionOverviewModel transactionOverviewModel, Response response) {
                    if (transactionOverviewModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        listItemsList.clear();
                        listItemsList.addAll(transactionOverviewModel.getCOMMAND().getDATA());
                        adapter.notifyDataSetChanged();
                        loadingDialog.cancel();
                    } else {
                        Logger.e("TO", transactionOverviewModel.getCOMMAND().getMESSAGE().toString() + " ");
                        loadingDialog.cancel();
                        Toast.makeText(TransactionOverviewActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("TO", error.getMessage() + "");
                    Toast.makeText(TransactionOverviewActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();

                    loadingDialog.cancel();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
