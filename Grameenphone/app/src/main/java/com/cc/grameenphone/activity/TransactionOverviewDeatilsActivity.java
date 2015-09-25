package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.TransactionOverviewData;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransactionOverviewDeatilsActivity extends AppCompatActivity {


    TransactionOverviewData data;
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.transactionToolbar)
    Toolbar transactionToolbar;
    @InjectView(R.id.dueDate)
    TextView dateText;
    @InjectView(R.id.enterDate)
    TextView enterDate;
    @InjectView(R.id.ser_text)
    TextView serText;
    @InjectView(R.id.serviceTextView)
    TextView serviceTextView;
    @InjectView(R.id.second_top)
    RelativeLayout secondTop;
    @InjectView(R.id.cmpny_text)
    TextView cmpnyText;
    @InjectView(R.id.companyNameTextView)
    TextView companyNameTextView;
    @InjectView(R.id.third_top)
    RelativeLayout thirdTop;
    @InjectView(R.id.amt_txt)
    TextView amtTxt;
    @InjectView(R.id.amountTextView)
    TextView amountTextView;
    @InjectView(R.id.fourth_top)
    RelativeLayout fourthTop;
    @InjectView(R.id.txn_text)
    TextView txnText;
    @InjectView(R.id.transactionNumberTextView)
    TextView transactionNumberTextView;
    @InjectView(R.id.fifth_top)
    RelativeLayout fifthTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview_details);
        ButterKnife.inject(this);
        textTool.setText("Transaction Overview");

        handleRipples();

        handleExtras();
    }


    private void handleRipples() {
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                backClick();
            }
        });
    }

    private void handleExtras() {
        try {
            Bundle b = getIntent().getExtras();

            data = (TransactionOverviewData) b.get("transaction_obj");
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void backClick() {
        finish();
    }

    private void initData() {

        data.enterDate.setText("");
        serviceTextView.setText(data.getSERVICE());
        companyNameTextView.setText("");
        amountTextView.setText(data.getTXNAMT());
        transactionNumberTextView.setText(data.getTXNID());

    }


}
