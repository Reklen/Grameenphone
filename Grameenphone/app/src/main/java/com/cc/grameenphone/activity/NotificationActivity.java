package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.NotificationAdapter;
import com.cc.grameenphone.api_models.NotificationMessageModel;
import com.cc.grameenphone.api_models.NotificationModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.NotificationFetchApi;
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

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationActivity extends AppCompatActivity {

    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.notificationList)
    ListView listView;
    private String android_id;
    private PreferenceManager preferenceManager;
    NotificationAdapter adapter;
    List<NotificationMessageModel> list;
    private ProgressDialog progressDialog;
    MaterialDialog mMaterialDialog;
    View dialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.inject(this);
        progressDialog = new ProgressDialog(NotificationActivity.this);
        progressDialog.setMessage("Loading");
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        list = new ArrayList<>();
        preferenceManager = new PreferenceManager(NotificationActivity.this);
        adapter = new NotificationAdapter(list, NotificationActivity.this);
        listView.setAdapter(adapter);
        fetchNotifications();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotificationMessageModel model = adapter.getItem(position);
              /*  dialogView = LayoutInflater.from(NotificationActivity.this).inflate(R.layout.notification_row_item, null);
                ((TextView) dialogView.findViewById(R.id.notificationTypeTextView)).setText("" + model.getNOTCODE());
                ((TextView) dialogView.findViewById(R.id.notificationTitleTextView)).setText("" + model.getNOTHEAD());
                ((TextView) dialogView.findViewById(R.id.notificationContentTextView)).setText("" + model.getNOTDATA());
                ((TextView) dialogView.findViewById(R.id.notificationTimeTextView)).setText("" + model.getNOTSTDAT());*/

                mMaterialDialog = new MaterialDialog(NotificationActivity.this)
                        .setTitle(model.getNOTHEAD())
                        .setMessage(model.getNOTDATA() + " \n\n" + model.getNOTSTDAT())
                        .setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        })
                        .setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        });

                mMaterialDialog.show();

            }
        });

        ViewGroup parentGroup = (ViewGroup) listView.getParent();
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No notifications");
        parentGroup.addView(emptyView);
        listView.setEmptyView(emptyView);


    }

    private void fetchNotifications() {
        NotificationFetchApi notificationFetchApi = ServiceGenerator.createService(NotificationFetchApi.class);
        android_id = Settings.Secure.getString(NotificationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        progressDialog.show();
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "SUBNOTFREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("wallet request ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));

            notificationFetchApi.fetchNotifications(in, new Callback<NotificationModel>() {
                @Override
                public void success(NotificationModel notificationModel, Response response) {
                    progressDialog.cancel();
                    Logger.d("notification", notificationModel.toString());
                    if (notificationModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        list.addAll(notificationModel.getCommand().getMessageModelList());
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.cancel();
                    Logger.e("Notifications", error.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.cancel();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            progressDialog.cancel();
        }
    }
}
