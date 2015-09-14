package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillPaymentViewPagerAdapter;
import com.cc.grameenphone.views.tabs.SlidingTabLayout;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    BillPaymentViewPagerAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"ELECTRICITY", "GAS", "INSURANCE", "TICKETING", "INTERNET"};
    int NumOfTabs = 5;
    Toolbar otherToolbar;
    RelativeLayout toolbarContainer;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        otherToolbar = (Toolbar) findViewById(R.id.other_tool_bar);
        toolbarContainer = (RelativeLayout) findViewById(R.id.toolbar_container);
        backBtn = (ImageButton) findViewById(R.id.image_back);

        adapter = new BillPaymentViewPagerAdapter(getSupportFragmentManager(), Titles, NumOfTabs, 0);

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


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
        }
    }
}

