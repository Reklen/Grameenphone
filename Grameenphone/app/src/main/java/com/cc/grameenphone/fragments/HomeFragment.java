package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.activity.BillPaymentActivity;
import com.cc.grameenphone.activity.ReferFriendsActivity;
import com.cc.grameenphone.activity.SelectContactsActivity;
import com.cc.grameenphone.activity.TransactionOverviewActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rajkiran on 09/09/15.
 */
public class HomeFragment extends Fragment {


    @InjectView(R.id.prepaidOption)
    RadioButton prepaidOption;
    @InjectView(R.id.postpaidOption)
    RadioButton postpaidOption;
    @InjectView(R.id.radioGroup)
    RadioGroup radioGroup;
    @InjectView(R.id.areaCode)
    TextView areaCode;
    @InjectView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @InjectView(R.id.phone_container)
    TextInputLayout phoneContainer;
    @InjectView(R.id.other_flex)
    TextView otherFlex;
    @InjectView(R.id.top_container1)
    RelativeLayout topContainer1;
    @InjectView(R.id.editamt)
    EditText editamt;
    @InjectView(R.id.amount_container)
    TextInputLayout amountContainer;
    @InjectView(R.id.flexi_btn)
    Button flexiBtn;
    @InjectView(R.id.billpay)
    ImageView billpay;
    @InjectView(R.id.bill_text)
    TextView billText;
    @InjectView(R.id.billPayment)
    RelativeLayout billPayment;
    @InjectView(R.id.trans_icon)
    ImageView transIcon;
    @InjectView(R.id.transc_text)
    TextView transcText;
    @InjectView(R.id.transactionOverview)
    RelativeLayout transactionOverview;
    @InjectView(R.id.emergencyicon)
    ImageView emergencyicon;
    @InjectView(R.id.emergency_text)
    TextView emergencyText;
    @InjectView(R.id.emergencyCall)
    RelativeLayout emergencyCall;
    @InjectView(R.id.friends)
    ImageView friends;
    @InjectView(R.id.friends_text)
    TextView friendsText;
    @InjectView(R.id.referFriends)
    RelativeLayout referFriends;

    int REQCODE = 100;

    public HomeFragment() {
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
        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (prepaidOption.isChecked()) {

                } else if (postpaidOption.isChecked()) {
                }

            }
        });


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

    @OnClick(R.id.billPayment)
    void billPaymentClick() {
        startActivity(new Intent(getActivity(), BillPaymentActivity.class));
    }

    @OnClick(R.id.transactionOverview)
    void transactionOverviewClick() {
        startActivity(new Intent(getActivity(), TransactionOverviewActivity.class));
    }

    @OnClick(R.id.emergencyCall)
    void emergencyClick() {

    }

    @OnClick(R.id.referFriends)
    void referFriendsClick() {
        startActivity(new Intent(getActivity(), ReferFriendsActivity.class));
    }

    @OnClick(R.id.flexi_btn)
    void flexiButtonClick() {
        View flexiDialog = LayoutInflater.from(getActivity()).inflate(R.layout.flexi_dialog_layout, null);

        final MaterialDialog materialDialog = new MaterialDialog(getActivity()).setContentView(flexiDialog);
        materialDialog.setCanceledOnTouchOutside(true);
        materialDialog.show();
        Button buttonOk = (Button) flexiDialog.findViewById(R.id.okButton);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.other_flex)
    void otherFlexiLoadClick() {
        otherFlex.setVisibility(View.GONE);
        phoneNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_add_ppl, 0);
        phoneNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= phoneNumberEditText.getRight() - phoneNumberEditText.getTotalPaddingRight()) {
                        // your action for drawable click event
                        startActivityForResult(new Intent(getActivity(), SelectContactsActivity.class), REQCODE);
                        return true;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            
        }
    }
}
