package com.cc.grameenphone.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rajkiran on 18/09/15.
 */
public class BillPaymentFragment extends Fragment {


    MaterialDialog confirmDialog;
    @InjectView(R.id.company_text)
    TextView companyText;
    @InjectView(R.id.DESCO_text)
    TextView DESCOText;
    @InjectView(R.id.container_one)
    LinearLayout containerOne;
    @InjectView(R.id.accountNmb_text)
    TextView accountNmbText;
    @InjectView(R.id.Anumb_text)
    TextView AnumbText;
    @InjectView(R.id.container_two)
    LinearLayout containerTwo;
    @InjectView(R.id.billNumb_text)
    TextView billNumbText;
    @InjectView(R.id.Bnumb_text)
    TextView BnumbText;
    @InjectView(R.id.container_three)
    LinearLayout containerThree;
    @InjectView(R.id.amount_text)
    TextView amountText;
    @InjectView(R.id.ruppes_text)
    TextView ruppesText;
    @InjectView(R.id.amtNumb_text)
    TextView amtNumbText;
    @InjectView(R.id.container_four)
    RelativeLayout containerFour;
    @InjectView(R.id.subcharge_text)
    TextView subchargeText;
    @InjectView(R.id.ruppes02_text)
    TextView ruppes02Text;
    @InjectView(R.id.subAmt_text)
    TextView subAmtText;
    @InjectView(R.id.container_five)
    RelativeLayout containerFive;
    @InjectView(R.id.dueDate_text)
    TextView dueDateText;
    @InjectView(R.id.date_text)
    TextView dateText;
    @InjectView(R.id.container_six)
    LinearLayout containerSix;
    @InjectView(R.id.pinNumbEdit)
    EditText pinNumbEdit;
    @InjectView(R.id.PinConform_container)
    TextInputLayout PinConformContainer;
    @InjectView(R.id.confirm_btn)
    Button confirmBtn;
    @InjectView(R.id.confirmRipple)
    RippleView confirmRipple;
    @InjectView(R.id.billPaymentLayout)
    LinearLayout billPaymentLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill_pay, container, false);
        ButterKnife.inject(this, view);
        pinNumbEdit.requestFocus();
        confirmRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                confirmClick();
            }
        });
        return view;
    }

    void confirmClick() {

        confirmDialog = new MaterialDialog(getActivity());
        confirmDialog.setMessage("Are you sure, you want to continue ?");

        confirmDialog.setPositiveButton("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });
        confirmDialog.setNegativeButton("No", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog.dismiss();
            }
        });
        confirmDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}