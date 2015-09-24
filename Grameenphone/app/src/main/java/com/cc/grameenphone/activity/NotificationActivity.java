package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.NotificationAdapter;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationActivity extends ActionBarActivity {

    NotificationAdapter adapter;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.notificationList)
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        ButterKnife.inject(this);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });

        ViewGroup parentGroup = (ViewGroup) listView.getParent();
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No notifications");
        parentGroup.addView(emptyView);
        listView.setEmptyView(emptyView);

    }
}
