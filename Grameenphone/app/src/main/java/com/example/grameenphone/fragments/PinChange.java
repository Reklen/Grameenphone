package com.example.grameenphone.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rajkiran on 09/09/15.
 */
public class PinChange extends Fragment {
    @InjectView(R.id.editText)
    EditText editText;
    @InjectView(R.id.editText2)
    EditText editText2;
    @InjectView(R.id.editText3)
    EditText editText3;
    AppCompatDialog confirmDialog;
    Button okbtn,loginBtn;


    public PinChange() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pin_change, container, false);
        loginBtn = (Button)rootView.findViewById(R.id.cnfm_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog = new AppCompatDialog(getActivity());
                confirmDialog.setContentView(R.layout.pin_chnage_dialog);
                okbtn = (Button) confirmDialog.findViewById(R.id.ok_btn);
                confirmDialog.show();
                confirmDialog.getWindow().setLayout(600, 400);
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.setCanceledOnTouchOutside(true);
            }
        });
        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
