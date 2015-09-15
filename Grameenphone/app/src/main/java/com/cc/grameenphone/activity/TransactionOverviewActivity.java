package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.TransactionAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TransactionOverviewActivity extends AppCompatActivity {


    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.transactionToolbar)
    Toolbar signUpToolbar;
    @InjectView(R.id.transactionList)
    ListView listView;
    TransactionAdapter transactionAdapter;
    TextView tooltext;
    String array[] = {"Tadjdj", "HHHKhkbkdj", "Hjbdkhbkb", "dhjbfbf", "bfkhbsdbd", "cjssdhjjhd", "Tadjdj", "HHHKhkbkdj", "Hjbdkhbkb", "dhjbfbf", "bfkhbsdbd", "cjssdhjjhd"};

    ImageView back_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_overview);
        ButterKnife.inject(this);
        tooltext = (TextView)findViewById(R.id.text_tool);
        tooltext.setText("Transaction Overview");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);
        //listView.setAdapter(transactionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(TransactionOverviewActivity.this, TransactionOverviewDeatilsActivity.class));
            }
        });
        back_icon= (ImageView) findViewById(R.id.image_icon_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
