package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.BillPaymentViewPagerAdapter;
import com.cc.grameenphone.views.tabs.SlidingTabLayout;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NewAssociationActivity extends ActionBarActivity {
    Toolbar toolbar;
    LinearLayout toolbarLayout;
    TextView toolbarText;
    ImageView toolbarIcon;

    BillPaymentViewPagerAdapter adapter;
    ViewPager pager;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"ELECTRICITY","GAS","INSURANCE","TICKETING","INTERNET"};
    int NumOfTabs=5;
    RelativeLayout electricityTabLayout;
    EditText enterBillNumbEdit;
    Button confrmBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        toolbar= (Toolbar) findViewById(R.id.other_tool_bar);
        toolbarLayout= (LinearLayout) findViewById(R.id.toolbar_container);
        toolbarLayout.setVisibility(View.VISIBLE);
        toolbarText= (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("New Association");
        toolbarIcon= (ImageView) findViewById(R.id.toolbar_icon);
        toolbarIcon.setVisibility(View.GONE);

            adapter=new BillPaymentViewPagerAdapter(getSupportFragmentManager(),Titles,NumOfTabs,1);

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

        /*   electricityTabLayout= (RelativeLayout) findViewById(R.id.electricity_container);
        electricityTabLayout.setVisibility(View.VISIBLE);
        enterBillNumbEdit= (EditText) findViewById(R.id.bill_numbEdit);
      // enterBillNumbEdit.setVisibility(View.GONE);
        confrmBtn= (Button) findViewById(R.id.sbmt_btn);*/
       // confrmBtn.setText("Confirm");

    }


    }

