package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.fragments.BillPaymentFragment;
import com.cc.grameenphone.fragments.QuickPayFragment;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuickPayActivity extends AppCompatActivity implements QuickPayFragment.TopFragmentListener {

    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay);

        ButterKnife.inject(this);

        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        QuickPayFragment qf = new QuickPayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, qf);
        transaction.commit();
    }

    //Need to edit! The paycode is passed over here!
    public void onclickQuickPay_QuickPayFragment(String paycode) {
        BillPaymentFragment qf = new BillPaymentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, qf);
        transaction.commit();
    }
}
