package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.cc.grameenphone.R;
import com.cc.grameenphone.fragments.BillPaymentFragment;
import com.cc.grameenphone.fragments.QuickPayFragment;

public class QuickpayActivity extends AppCompatActivity implements QuickPayFragment.TopFragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay);
        QuickPayFragment qf = new QuickPayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, qf);
        transaction.commit();
    }

    //Need to edit! The paycode is passed over here!
    public void onclickQuickPay_QuickPayFragment(String paycode)
    {
        Intent i = new Intent (this,QuickpayActivity.class);
        startActivity(i);
        BillPaymentFragment qf = new BillPaymentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, qf);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quickpay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
