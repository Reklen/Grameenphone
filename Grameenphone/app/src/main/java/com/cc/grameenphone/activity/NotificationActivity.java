package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.NotificationAdapter;
import com.cc.grameenphone.adapter.NotificationSectionAdapter;
import com.cc.grameenphone.adapter.NotificationsAdapter;
import com.cc.grameenphone.api_models.NotificationMessageModel;
import com.cc.grameenphone.api_models.NotificationModel;
import com.cc.grameenphone.async.NotificationsSaveDBTask;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.NotificationClickInterface;
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
import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationActivity extends AppCompatActivity implements NotificationClickInterface {

    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.notificationList)
    RecyclerView mRecyclerView;
    private String android_id;
    private PreferenceManager preferenceManager;
    NotificationAdapter adapter;
    List<NotificationMessageModel> list;
    private ProgressDialog progressDialog;
    MaterialDialog mMaterialDialog;
    View dialogView;
    NotificationsAdapter notificationsAdapter;

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
        LinearLayoutManager llm = new LinearLayoutManager(NotificationActivity.this);
        mRecyclerView.setLayoutManager(llm);
        notificationsAdapter = new NotificationsAdapter(NotificationActivity.this, list, NotificationActivity.this);
        // adapter = new NotificationAdapter(list, NotificationActivity.this);
        //mRecyclerView.setAdapter(adapter);

        fetchNotifications();

       /* mRecyclerView.addOnItemTouchListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NotificationMessageModel model = adapter.getItem(position);
              *//*  dialogView = LayoutInflater.from(NotificationActivity.this).inflate(R.layout.notification_row_item, null);
                ((TextView) dialogView.findViewById(R.id.notificationTypeTextView)).setText("" + model.getNOTCODE());
                ((TextView) dialogView.findViewById(R.id.notificationTitleTextView)).setText("" + model.getNOTHEAD());
                ((TextView) dialogView.findViewById(R.id.notificationContentTextView)).setText("" + model.getNOTDATA());
                ((TextView) dialogView.findViewById(R.id.notificationTimeTextView)).setText("" + model.getNOTSTDAT());*//*
                model.setRead(true);
                adapter.notifyDataSetChanged();
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
        });*/

       /* mRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(NotificationActivity.this, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NotificationMessageModel model = mSectionedAdapter.getItem(position);
        *//*//**//*  dialogView = LayoutInflater.from(NotificationActivity.this).inflate(R.layout.notification_row_item, null);
        ((TextView) dialogView.findViewById(R.id.notificationTypeTextView)).setText("" + model.getNOTCODE());
        ((TextView) dialogView.findViewById(R.id.notificationTitleTextView)).setText("" + model.getNOTHEAD());
        ((TextView) dialogView.findViewById(R.id.notificationContentTextView)).setText("" + model.getNOTDATA());
        ((TextView) dialogView.findViewById(R.id.notificationTimeTextView)).setText("" + model.getNOTSTDAT());*//*
                model.setRead(true);
                mSectionedAdapter.notifyDataSetChanged();
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
        }));*/

        ViewGroup parentGroup = (ViewGroup) mRecyclerView.getParent();
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No notifications");
        parentGroup.addView(emptyView);
        // mRecyclerView.setEmptyView(emptyView);


    }

    private void fetchNotifications() {
        progressDialog.show();
        new RushSearch()
                .find(NotificationMessageModel.class, new RushSearchCallback<NotificationMessageModel>() {
                    @Override
                    public void complete(final List<NotificationMessageModel> listDB) {
                        progressDialog.cancel();
                        list.addAll(listDB);
                        if (list.size() > 0)
                            calculateSections(notificationsAdapter, list);
                    }
                });
        final NotificationFetchApi notificationFetchApi = ServiceGenerator.createService(NotificationActivity.this,NotificationFetchApi.class);
        android_id = Settings.Secure.getString(NotificationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (list.size() == 0) {
            try {
                JSONObject jsonObject = new JSONObject();
                JSONObject innerObject = new JSONObject();
                innerObject.put("DEVICEID", android_id);
                innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
                innerObject.put("MSISDN", preferenceManager.getMSISDN());
                innerObject.put("TYPE", "SUBNOTFREQ");
                jsonObject.put("COMMAND", innerObject);
                //Logger.d("wallet request ", jsonObject.toString());
                String json = jsonObject.toString();
                TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));

                notificationFetchApi.fetchNotifications(in, new Callback<NotificationModel>() {
                    @Override
                    public void success(NotificationModel notificationModel, Response response) {
                        progressDialog.cancel();
                        //Logger.d("notification", notificationModel.toString());
                        if (notificationModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                            if (list.size() == 0) {
                                list.addAll(notificationModel.getCommand().getMessageModelList());
                                NotificationsSaveDBTask saveDBTask = new NotificationsSaveDBTask(NotificationActivity.this, notificationModel.getCommand().getMessageModelList());
                                saveDBTask.execute();
                                if (list.size() > 0)
                                    calculateSections(notificationsAdapter, list);
                            } else if (list.size() < notificationModel.getCommand().getMessageModelList().size()) {
                                list.addAll(notificationModel.getCommand().getMessageModelList());
                                NotificationsSaveDBTask saveDBTask = new NotificationsSaveDBTask(NotificationActivity.this, notificationModel.getCommand().getMessageModelList());
                                saveDBTask.execute();
                                if (list.size() > 0)
                                    calculateSections(notificationsAdapter, list);
                            }
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

    NotificationSectionAdapter mSectionedAdapter;

    private void calculateSections(NotificationsAdapter mAdapter, List<NotificationMessageModel> notificationsList) {
        //This is the code to provide a sectioned list
        List<NotificationSectionAdapter.Section> sections =
                new ArrayList<NotificationSectionAdapter.Section>();
        List<String> sectionHeaders = new ArrayList<>();
        List<Integer> sectionPositionList = new ArrayList<>();

        sections.add(new NotificationSectionAdapter.Section(0, "Unread Notifications"));
        sectionHeaders.add("Unread Notifications");
        sectionPositionList.add(0);

        for (int i = 0; i < notificationsList.size(); i++) {
            NotificationMessageModel mo = notificationsList.get(i);
            if (mo.isRead() && !sectionHeaders.contains("Read Notifications")) {
                sections.add(new NotificationSectionAdapter.Section(i, "Read Notifications"));
                sectionHeaders.add("Read Notifications");
                sectionPositionList.add(i);
            }

        }


        mAdapter.setSectionPositionList(sectionPositionList);
        //Add your adapter to the sectionAdapter
        NotificationSectionAdapter.Section[] dummy = new NotificationSectionAdapter.Section[sections.size()];
        mSectionedAdapter = new
                NotificationSectionAdapter(NotificationActivity.this, R.layout.adapter_item_section, R.id.section_text, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));
        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
        //Logger.d("Sectipons", mSectionedAdapter.getItemCount() + "");
    }


    @Override
    public void clickNotification(final int pos) {
        final NotificationMessageModel model = list.get(pos);

        //Logger.d("POSS", pos + "" + model.getNOTDATA());
        /*//*  dialogView = LayoutInflater.from(NotificationActivity.this).inflate(R.layout.notification_row_item, null);
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
                        if (!model.isRead()) {
                            model.setRead(true);
                            model.save(new RushCallback() {
                                @Override
                                public void complete() {

                                    //  fetchNotifications();
                                    new RushSearch()
                                            .find(NotificationMessageModel.class, new RushSearchCallback<NotificationMessageModel>() {
                                                @Override
                                                public void complete(final List<NotificationMessageModel> listDB) {
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            list.clear();
                                                            list.addAll(listDB);
                                                            notificationsAdapter = new NotificationsAdapter(NotificationActivity.this, list, NotificationActivity.this);

                                                            if (list.size() > 0)
                                                                calculateSections(notificationsAdapter, list);
                                                        }
                                                    });
                                                }
                                            });
                                }
                            });
                        }
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
}
