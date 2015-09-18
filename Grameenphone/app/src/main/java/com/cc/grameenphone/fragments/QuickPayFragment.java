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

import com.cc.grameenphone.R;

/**
 * Created by rajkiran on 18/09/15.
 */
public class QuickPayFragment extends Fragment {
    TopFragmentListener activityCommander;

    private static EditText payCode;
    public interface TopFragmentListener{
        public void onclickQuickPay_QuickPayFragment(String PayCode);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityCommander = (TopFragmentListener) activity;
        }catch(ClassCastException e){
            throw new ClassCastException(activity.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quickpay_entercode,container, false);
        payCode = (EditText) view.findViewById(R.id.editTextQuickPayCode);
        Button submitbutton = (Button) view.findViewById(R.id.submitbutton);
        submitbutton.setOnClickListener
                (
                        new View.OnClickListener()
                        {
                            public void onClick(View v)
                            {
                                buttonClicked(v);
                            };
                        }
                );
        return view;
    }
    public void buttonClicked(View v)
    {
        activityCommander.onclickQuickPay_QuickPayFragment(payCode.getText().toString());
    }
}