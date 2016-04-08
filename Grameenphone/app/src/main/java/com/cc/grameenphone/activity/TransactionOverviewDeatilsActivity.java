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

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionOverviewDeatilsActivity extends AppCompatActivity {


    TransactionOverviewData data;
    @Bind(R.id.image_icon_back)
    ImageView imageIconBack;
    @Bind(R.id.backRipple)
    RippleView backRipple;
    @Bind(R.id.text_tool)
    TextView textTool;
    @Bind(R.id.toolbar)
    Toolbar transactionToolbar;
    @Bind(R.id.dueDate)
    TextView dateText;
    @Bind(R.id.enterDate)
    TextView enterDate;
    @Bind(R.id.accountText)
    TextView serText;
    @Bind(R.id.accountTextView)
    TextView serviceTextView;
    @Bind(R.id.second_top)
    RelativeLayout secondTop;
    @Bind(R.id.billText)
    TextView cmpnyText;
    @Bind(R.id.companyTextView)
    TextView companyNameTextView;
    @Bind(R.id.third_top)
    RelativeLayout thirdTop;
    @Bind(R.id.amt_txt)
    TextView amtTxt;
    @Bind(R.id.amountTextView)
    TextView amountTextView;
    @Bind(R.id.fourth_top)
    RelativeLayout fourthTop;
    @Bind(R.id.surchargeText)
    TextView txnText;
    @Bind(R.id.surchargeTextView)
    TextView transactionNumberTextView;
    @Bind(R.id.fifth_top)
    RelativeLayout fifthTop;
    String date;
    @Bind(R.id.amt_sc_txt)
    TextView amtScTxt;
    @Bind(R.id.amountSCTextView)
    TextView amountSCTextView;
    @Bind(R.id.fourth_half_top)
    RelativeLayout fourthHalfTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview_details);
        ButterKnife.bind(this);
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

            //   date = b.getString("transactionMap");
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void backClick() {
        finish();
    }

    private void initData() {

        enterDate.setText("" + data.getTXNDATE());
        serviceTextView.setText(data.getSERVICE());
        companyNameTextView.setText("" + data.getFROMTO());
        amountTextView.setText("৳ " + data.getTXNAMT());
        transactionNumberTextView.setText(data.getTXNID());
        amountSCTextView.setText("৳ " +data.getTXNSC());

    }


}
