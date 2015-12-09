package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.TransactionOverviewAdapter;
import com.cc.grameenphone.api_models.TransactionOverviewData;
import com.cc.grameenphone.api_models.TransactionOverviewModel;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.TransactionOverviewApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class TransactionOverviewActivity extends AppCompatActivity {
    ImageView back_icon;
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.toolbar)
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
    private MaterialDialog sessionDialog;
    private MaterialDialog errorDialog;

    ArrayList<String> posMessageMap;

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
        posMessageMap = new ArrayList<>();
        transactionOverviewApi = ServiceGenerator.createService(TransactionOverviewApi.class);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        adapter = new TransactionOverviewAdapter(TransactionOverviewActivity.this, listItemsList);
        transactionList.setAdapter(adapter);
        ViewGroup parentGroup = (ViewGroup) transactionList.getParent();
        View emptyView = LayoutInflater.from(TransactionOverviewActivity.this).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No Transactions history found");
        parentGroup.addView(emptyView);
        transactionList.setEmptyView(emptyView);
        transactionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(TransactionOverviewActivity.this,
                        TransactionOverviewDeatilsActivity.class).putExtra("transaction_obj", adapter.getItem(i)).putExtra("transactionMap", posMessageMap.get(i)));
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
            innerObject.put("MSISDN",  preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CLTREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("sending json", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            transactionOverviewApi.fetchStatements(in, new Callback<TransactionOverviewModel>() {
                        @Override
                        public void success(TransactionOverviewModel transactionOverviewModel, Response response) {
                            Logger.d("TransactionOverview", transactionOverviewModel.toString());
                            if (transactionOverviewModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                                listItemsList.clear();
                                try {
                                    for (TransactionOverviewData modelData : transactionOverviewModel.getCOMMAND().getDATA()) {
                                        if (!modelData.getTXNAMT().equalsIgnoreCase("null")) {
                                            listItemsList.add(modelData);
                                        }
                                    }

                                    mapValues(transactionOverviewModel);
                                    adapter.notifyDataSetChanged();
                                    loadingDialog.cancel();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    loadingDialog.cancel();
                                }
                            } else if (transactionOverviewModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA903")) {
                                loadingDialog.cancel();
                                errorDialog = new MaterialDialog(TransactionOverviewActivity.this);
                                errorDialog.setMessage(transactionOverviewModel.getCOMMAND().getMESSAGE() + "");
                                errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        errorDialog.dismiss();
                                    }
                                });
                                errorDialog.show();
                            } else if (transactionOverviewModel.getCOMMAND().

                                    getTXNSTATUS()

                                    .

                                            equalsIgnoreCase("MA907")

                                    )

                            {
                                Logger.d("Balance", transactionOverviewModel.toString());
                                loadingDialog.cancel();
                                sessionDialog = new MaterialDialog(TransactionOverviewActivity.this);
                                sessionDialog.setMessage("Session expired , please login again");
                                sessionDialog.setPositiveButton("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SessionClearTask sessionClearTask = new SessionClearTask(TransactionOverviewActivity.this, true);
                                        sessionClearTask.execute();

                                    }
                                });
                                sessionDialog.setCanceledOnTouchOutside(false);
                                sessionDialog.show();
                            } else

                            {
                                Logger.e("Error", transactionOverviewModel.toString());
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Logger.e("TO", error.getMessage() + "");
                            Toast.makeText(TransactionOverviewActivity.this, "Some Error Occured", Toast.LENGTH_SHORT).show();

                            loadingDialog.cancel();
                        }
                    }

            );
        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void mapValues(TransactionOverviewModel transactionOverviewModel) {
        String message = transactionOverviewModel.getCOMMAND().getMESSAGE();
        String[] parts = message.split("\\n");
        for (int k = 0; k < parts.length; k++) {
            String s = parts[k];
            String[] wordParts = s.split(" ");
            posMessageMap.add(wordParts[2]);
        }
    }


}
