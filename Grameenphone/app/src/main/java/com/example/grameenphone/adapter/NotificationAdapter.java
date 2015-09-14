package com.example.grameenphone.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.grameenphone.R;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationAdapter extends ArrayAdapter {


    public NotificationAdapter(Context context, int resource) {
        super(context, R.layout.notification_row_item);
    }
}
