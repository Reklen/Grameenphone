package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillPaymentViewPagerAdapter;
import com.cc.grameenphone.views.RippleView;
import com.cc.grameenphone.views.tabs.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NewAssociationActivity extends AppCompatActivity {


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
    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;
    @InjectView(R.id.pager)
    ViewPager pager;
    BillPaymentViewPagerAdapter adapter;

    CharSequence Titles[] = {"ELECTRICITY", "GAS", "INSURANCE", "TICKETING", "INTERNET"};
    int NumOfTabs = 5;
    Toolbar otherToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_payment_activity);
        ButterKnife.inject(this);
        toolbarText.setText("New Association");
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        adapter = new BillPaymentViewPagerAdapter(getSupportFragmentManager(), Titles, NumOfTabs, 1);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        tabs.setViewPager(pager);

        /*   electricityTabLayout= (RelativeLayout) findViewById(R.id.electricity_container);
        electricityTabLayout.setVisibility(View.VISIBLE);
        enterBillNumbEdit= (EditText) findViewById(R.id.bill_numbEdit);
      // enterBillNumbEdit.setVisibility(View.GONE);
        confrmBtn= (Button) findViewById(R.id.sbmt_btn);*/
        // confrmBtn.setText("Confirm");

    }


}

