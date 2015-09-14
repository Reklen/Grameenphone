package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.NotificationAdapter;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationActivity extends ActionBarActivity{
    LinearLayout toolbarContainer;
    Toolbar toolbar;
    TextView toolbarText;
    ImageView toolbarIcon;
    ListView listView;
    NotificationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        toolbar= (Toolbar) findViewById(R.id.notification_toolbar);
        toolbarContainer= (LinearLayout) findViewById(R.id.toolbar_container);
        toolbarContainer.setVisibility(View.VISIBLE);
        toolbarText= (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("Notification");
        toolbarIcon= (ImageView) findViewById(R.id.toolbar_icon);
        toolbarIcon.setVisibility(View.GONE);

        listView= (ListView) findViewById(R.id.notifi_list_view);






    }
}
