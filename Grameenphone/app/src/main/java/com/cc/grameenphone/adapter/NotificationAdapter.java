package com.cc.grameenphone.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.cc.grameenphone.R;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationAdapter extends ArrayAdapter {


    public NotificationAdapter(Context context, int resource) {
        super(context, R.layout.notification_row_item);
    }
}
