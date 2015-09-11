package com.example.grameenphone.payment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grameenphone.R;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class TicketingTab extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_ticketing,container,false);
        return v;
    }
}
