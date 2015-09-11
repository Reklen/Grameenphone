package com.example.grameenphone.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grameenphone.R;

public class TransactionOverviewDeatilsActivity extends ActionBarActivity {
    TextView tooltext;

    ImageView back_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview_deatils);
        tooltext = (TextView) findViewById(R.id.text_tool);
        tooltext.setText("Transaction Overview");
        back_icon= (ImageView) findViewById(R.id.image_icon_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
