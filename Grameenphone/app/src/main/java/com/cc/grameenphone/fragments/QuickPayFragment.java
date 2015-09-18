package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by rajkiran on 18/09/15.
 */
public class QuickPayFragment extends Fragment {
    TopFragmentListener activityCommander;

    private static EditText payCode;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.editTextQuickPayCode)
    EditText editTextQuickPayCode;
    @InjectView(R.id.submitbutton)
    Button submitbutton;
    @InjectView(R.id.quickPayFragment)
    LinearLayout quickPayFragment;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public interface TopFragmentListener {
        public void onclickQuickPay_QuickPayFragment(String PayCode);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCommander = (TopFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quickpay_entercode, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @OnClick(R.id.submitbutton)
    void submitbuttonClicked() {
        activityCommander.onclickQuickPay_QuickPayFragment(editTextQuickPayCode.getText().toString());
    }
}