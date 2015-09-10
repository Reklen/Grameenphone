package com.example.grameenphone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.grameenphone.R;
import com.example.grameenphone.activity.SelctContacts;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rajkiran on 09/09/15.
 */
public class HomePage extends Fragment {
    private Button flexiBtn;

    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_layout, container, false);
        final ImageView cntcts = (ImageView) rootView.findViewById(R.id.contact_icons);
        TextView flexText = (TextView) rootView.findViewById(R.id.other_flex);
        flexText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelctContacts.class));
                /*cntcts.setVisibility(View.VISIBLE);
                cntcts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(),SelctContacts.class));
                    }
                });*/
            }
        });
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radio_pre = (RadioButton) radioGroup.findViewById(R.id.radioprepaid);
                RadioButton radio_post = (RadioButton) radioGroup.findViewById(R.id.radiopostpaid);
                if (radio_pre.isChecked()) {

                } else if (radio_post.isChecked()) {
                }

            }
        });
        flexiBtn = (Button) rootView.findViewById(R.id.flexi_btn);
        flexiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupview = inflater.inflate(R.layout.flexi_dialog_layout, null);

                final MaterialDialog materialDialog = new MaterialDialog(getActivity()).setContentView(popupview);
                materialDialog.setCanceledOnTouchOutside(true);
                materialDialog.show();
            }
        });
        // Inflate the layout for this fragment
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


}
