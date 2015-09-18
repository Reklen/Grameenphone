package com.cc.grameenphone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.grameenphone.R;

/**
 * Created by rajkiran on 18/09/15.
 */
public class BillPaymentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.quickpay_billpayment,container,false);
        return view;
    }
}