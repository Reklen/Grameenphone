package com.example.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.example.grameenphone.R;

public class TransactionOverviewDeatils extends ActionBarActivity {
    TextView tooltext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview_deatils);
        tooltext = (TextView) findViewById(R.id.text_tool);
        tooltext.setText("Transaction Overview");
    }


}
