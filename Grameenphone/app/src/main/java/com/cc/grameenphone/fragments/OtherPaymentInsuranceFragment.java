package com.cc.grameenphone.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.utils.Logger;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class OtherPaymentInsuranceFragment extends BaseTabFragment {
    @InjectView(R.id.custodial_radiogroup)
    RadioGroup custodialRadiogroup;
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
    @InjectView(R.id.companyRadioGroupScroll)
    ScrollView companyRadioGroupScroll;
    @InjectView(R.id.amountEditText)
    EditText amountEditText;
    @InjectView(R.id.amountTextInputLayout)
    TextInputLayout amountTextInputLayout;
    @InjectView(R.id.surchargeEditText)
    EditText surchargeEditText;
    @InjectView(R.id.surchargeTextInputLayout)
    TextInputLayout surchargeTextInputLayout;

    MaterialDialog confirmationDialog;
    public static OtherPaymentInsuranceFragment newInstance(Bundle b) {
        OtherPaymentInsuranceFragment insuranceTabFragment = new OtherPaymentInsuranceFragment();
        insuranceTabFragment.setArguments(b);
        return insuranceTabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab_insurance, container, false);
        ButterKnife.inject(this, v);
        //TODO make amount and surcharge amount visible
        amountTextInputLayout.setVisibility(View.VISIBLE);
        surchargeTextInputLayout.setVisibility(View.VISIBLE);
        addRadioButtons(10);
        sbmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.confirmation_dialog, null);
                confirmationDialog = new MaterialDialog(getActivity());
                confirmationDialog.setView(dialogView);

                Button confirmButton = (Button) dialogView.findViewById(R.id.confirmDialogButton);
                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        confirmationDialog.dismiss();
                    }
                });
                confirmationDialog.show();
            }
        });
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
    public void addRadioButtons(int number) {

        for (int row = 0; row < 1; row++) {
            LinearLayout ll = new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.VERTICAL);

            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(getActivity());
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("Radio " + rdbtn.getId());
                rdbtn.setTextColor(Color.parseColor("#666666"));
                ll.addView(rdbtn);
            }
            custodialRadiogroup.addView(ll);
        }

    }
}
