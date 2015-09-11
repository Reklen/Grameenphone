package com.example.grameenphone.payment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;


import com.example.grameenphone.R;
import com.example.grameenphone.tabs.SlidingTabLayout;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class PaymentActivity extends ActionBarActivity {

    ViewPagerAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"ELECTRICITY","GAS","INSURANCE","TICKETING","INTERNET"};
    int NumOfTabs=5;
    Toolbar otherToolbar;
    LinearLayout toolbarContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        otherToolbar= (Toolbar) findViewById(R.id.other_tool_bar);
        toolbarContainer= (LinearLayout) findViewById(R.id.toolbar_container);
        toolbarContainer.setVisibility(View.VISIBLE);
        adapter=new ViewPagerAdapter(getSupportFragmentManager(),Titles,NumOfTabs,0);

        pager= (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        tabs= (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        tabs.setViewPager(pager);


    }

    }

