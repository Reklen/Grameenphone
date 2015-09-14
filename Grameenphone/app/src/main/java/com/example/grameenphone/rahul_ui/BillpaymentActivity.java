package com.example.grameenphone.rahul_ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grameenphone.R;
import com.example.grameenphone.payment.PaymentActivity;

import java.util.ArrayList;

/**
 * Created by rahul on 11/09/15.
 */
public class BillpaymentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    ListView lv;
    ArrayList<Listitems> arraylist;
    ListViewAdapter listViewAdapter;
    Button paySelectedBtn, otherPaymentBtn;
    AppCompatDialog paySelectDialog;
    Button confrimBtn;
    Toolbar toolbar;
    ImageView toolbarImageIcon01, toobarImageIcon02;
    TextView actionBarText;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_payment_activity);
        backBtn = (ImageButton) findViewById(R.id.image_back);
        actionBarText = (TextView) findViewById(R.id.toolbar_text);
        actionBarText.setText("Bill Payment");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //buttons
        paySelectedBtn = (Button) findViewById(R.id.pay_selected_Btn);
        otherPaymentBtn = (Button) findViewById(R.id.other_payment_btn);
        toolbarImageIcon01 = (ImageView) findViewById(R.id.toolbar_icon01);
        toobarImageIcon02 = (ImageView) findViewById(R.id.toolbar_icon02);
        lv = (ListView) findViewById(R.id.recycle_view);
        displayarraylist();
        listViewAdapter = new ListViewAdapter(this, arraylist);
        lv.setAdapter(listViewAdapter);

        paySelectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paySelectDialog = new AppCompatDialog(BillpaymentActivity.this);
                paySelectDialog.setContentView(R.layout.enterpin_dailogue);
                confrimBtn = (Button) paySelectDialog.findViewById(R.id.confrimbtn);
                paySelectDialog.show();
                paySelectDialog.getWindow().setLayout(650, 500);
                confrimBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
                paySelectDialog.setCanceledOnTouchOutside(true);
            }
        });
        otherPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BillpaymentActivity.this, PaymentActivity.class));
            }
        });
    }

    public void displayarraylist() {
        arraylist = new ArrayList<Listitems>();
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lv.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            Listitems l = arraylist.get(pos);
            l.setSelected(isChecked);

        }
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
