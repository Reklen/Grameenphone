package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TransactionOverviewDeatilsActivity extends ActionBarActivity {

    ImageView back_icon;
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.transactionToolbar)
    Toolbar transactionToolbar;
    @InjectView(R.id.date_text)
    TextView dateText;
    @InjectView(R.id.enterDate)
    TextView enterDate;
    @InjectView(R.id.ser_text)
    TextView serText;
    @InjectView(R.id.mobileNumber)
    TextView mobileNumber;
    @InjectView(R.id.second_top)
    RelativeLayout secondTop;
    @InjectView(R.id.cmpny_text)
    TextView cmpnyText;
    @InjectView(R.id.companyName)
    TextView companyName;
    @InjectView(R.id.third_top)
    RelativeLayout thirdTop;
    @InjectView(R.id.amt_txt)
    TextView amtTxt;
    @InjectView(R.id.amountPaid)
    TextView amountPaid;
    @InjectView(R.id.fourth_top)
    RelativeLayout fourthTop;
    @InjectView(R.id.txn_text)
    TextView txnText;
    @InjectView(R.id.transactionNumber)
    TextView transactionNumber;
    @InjectView(R.id.fifth_top)
    RelativeLayout fifthTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview_details);
        ButterKnife.inject(this);
        textTool.setText("TransactionItem Overview");

    }

    @OnClick(R.id.image_icon_back)
    void backClick(){
        finish();
    }

}
