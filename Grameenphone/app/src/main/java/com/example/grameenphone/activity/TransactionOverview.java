package com.example.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grameenphone.R;
import com.example.grameenphone.adapter.TransactionAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransactionOverview extends AppCompatActivity {


    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.sign_up_toolbar)
    Toolbar signUpToolbar;
    @InjectView(R.id.listView)
    ListView listView;
    TransactionAdapter transactionAdapter;
    TextView tooltext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview);
        ButterKnife.inject(this);
        tooltext = (TextView)findViewById(R.id.text_tool);
        tooltext.setText("Transaction Overview");
        transactionAdapter = new TransactionAdapter(this);
        listView.setAdapter(transactionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(TransactionOverview.this,TransactionOverviewDeatils.class));
            }
        });
    }


}
