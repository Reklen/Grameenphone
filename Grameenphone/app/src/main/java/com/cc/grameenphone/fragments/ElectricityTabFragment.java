package com.cc.grameenphone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.RegularPayDetailsActivity;
import com.cc.grameenphone.utils.Logger;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;


public class ElectricityTabFragment extends BaseTabFragment {


    @InjectView(R.id.radioBPDB)
    RadioButton radioBPDB;
    @InjectView(R.id.radioDESCO)
    RadioButton radioDESCO;
    @InjectView(R.id.radioDPDC)
    RadioButton radioDPDC;
    @InjectView(R.id.radioOthers)
    RadioButton radioOthers;
    @InjectView(R.id.custodial_radiogroup)
    RadioGroup radioGroup;
    @InjectView(R.id.account_numbEdit)
    EditText accountNumbEdit;
    @InjectView(R.id.account_numb_container)
    TextInputLayout accountNumbContainer;
    @InjectView(R.id.bill_numbEdit)
    EditText billNumbEdit;
    @InjectView(R.id.bill_numb_container)
    TextInputLayout billNumbContainer;
    @InjectView(R.id.sbmt_btn)
    Button sbmtBtn;
    @InjectView(R.id.electricity_container)
    RelativeLayout electricityContainer;


    public static ElectricityTabFragment newInstance(Bundle b) {
        ElectricityTabFragment electricityTab = new ElectricityTabFragment();
        electricityTab.setArguments(b);
        return electricityTab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_electricity, container, false);
        ButterKnife.inject(this, v);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioBPDB = (RadioButton) radioGroup.findViewById(R.id.radioBPDB);
                radioDESCO = (RadioButton) radioGroup.findViewById(R.id.radioDESCO);
                radioDPDC = (RadioButton) radioGroup.findViewById(R.id.radioDPDC);
                radioOthers = (RadioButton) radioGroup.findViewById(R.id.radioOthers);
                if (radioDESCO.isChecked()) {


                } else if (radioBPDB.isChecked()) {

                } else if (radioDPDC.isChecked()) {

                } else if (radioOthers.isChecked()) {

                }

            }
        });

        //submit Buttons onclick

        sbmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegularPayDetailsActivity.class);
                startActivity(intent);
            }
        });

        //changes in type 1 for NewAssociationActivity
        if (type == 1) {
            billNumbEdit.setVisibility(View.GONE);
            sbmtBtn.setText("CONFIRM");
            sbmtBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog = new MaterialDialog(getActivity());
                    /*confirmDialog.setContentView(R.layout.association_conformation_dialog);
                    okay = (Button) confirmDialog.findViewById(R.id.resendButton);
                    confirmDialog.show();
                    confirmDialog.getWindow().setLayout(700, 300);
                    okay_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    });
                    confirmDialog.setCanceledOnTouchOutside(true);*/
                }
            });

        }

        return v;


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleArguments();
    }

    private void handleArguments() {

        Bundle b;
        try {
            b = getArguments();
            Logger.d("argu", b.toString());
            type = getArguments().getInt("type");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
