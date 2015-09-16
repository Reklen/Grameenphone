package com.cc.grameenphone.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.RegularPayDetails;


public class ElectricityTabFragments extends Fragment {
    RadioGroup radioGroup;
    RadioButton radioDESCO,radioBPDB,radioDPDC,radioOthers;
    EditText accountNumbEdit,billNumbEdit;
    Button sumb_btn,okay_btn;
    AppCompatDialog confirmDialog;

    int type;

    RelativeLayout electricityContainer;


    public static ElectricityTabFragments newInstance(Bundle b){
        ElectricityTabFragments electricityTab = new ElectricityTabFragments();
        electricityTab.setArguments(b);
        return electricityTab;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab_electricity,container,false);

        //RelativeLayout
        electricityContainer= (RelativeLayout) v.findViewById(R.id.electricity_container);


        //EditText
        accountNumbEdit= (EditText) v.findViewById(R.id.account_numbEdit);
        billNumbEdit= (EditText) v.findViewById(R.id.bill_numbEdit);

        //buttons
        sumb_btn= (Button) v.findViewById(R.id.sbmt_btn);


        //radioGroup
        radioGroup= (RadioGroup) v.findViewById(R.id.custodial_radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                radioBPDB= (RadioButton) radioGroup.findViewById(R.id.radioBPDB);
                radioDESCO= (RadioButton) radioGroup.findViewById(R.id.radioDESCO);
                radioDPDC= (RadioButton) radioGroup.findViewById(R.id.radioDPDC);
                radioOthers= (RadioButton) radioGroup.findViewById(R.id.radioOthers);
                if (radioDESCO.isChecked()){


                }
                else if (radioBPDB.isChecked()){

                }
                else if (radioDPDC.isChecked()){

                }
               else if (radioOthers.isChecked()){

                }

            }
        });

        //submit Buttons onclick

        sumb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(),RegularPayDetails.class);
                startActivity(intent);
            }
        });

    //changes in type 1 for NewAssociationActivity
        if(type==1){
          billNumbEdit.setVisibility(View.GONE);
            sumb_btn.setText("CONFIRM");
            sumb_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog = new AppCompatDialog(getActivity());
                    confirmDialog.setContentView(R.layout.association_conformation_dialog);
                    okay_btn = (Button) confirmDialog.findViewById(R.id.resendButton);
                    confirmDialog.show();
                    confirmDialog.getWindow().setLayout(700, 300);
                    okay_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    });
                    confirmDialog.setCanceledOnTouchOutside(true);
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
            b= getArguments();
            Log.d("argu",b.toString()) ;
            type = getArguments().getInt("type");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
