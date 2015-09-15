package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.ListViewAdapter;
import com.cc.grameenphone.viewmodels.BillDetailsItems;
import com.cc.grameenphone.views.RippleView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rahul on 11/09/15.
 */
public class BillPaymentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    ListView lv;
    ArrayList<BillDetailsItems> arraylist;
    ListViewAdapter listViewAdapter;
    AppCompatDialog paySelectDialog;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar_container)
    RelativeLayout toolbarContainer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.billsbar)
    TextView billsbar;
    @InjectView(R.id.recycle_view)
    ListView recycleView;
    @InjectView(R.id.pay_selected_Btn)
    Button paySelectedBtn;
    @InjectView(R.id.other_payment_btn)
    Button otherPaymentBtn;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_payment_activity);
        ButterKnife.inject(this);
        setupToolbar();
        //buttons
        paySelectedBtn = (Button) findViewById(R.id.pay_selected_Btn);
        otherPaymentBtn = (Button) findViewById(R.id.other_payment_btn);
        lv = (ListView) findViewById(R.id.recycle_view);
        displayarraylist();
        listViewAdapter = new ListViewAdapter(this, arraylist);
        lv.setAdapter(listViewAdapter);

        paySelectedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paySelectDialog = new AppCompatDialog(BillPaymentActivity.this);
                paySelectDialog.setContentView(R.layout.enterpin_dailogue);
                confirmButton = (Button) paySelectDialog.findViewById(R.id.confrimbtn);
                paySelectDialog.show();
                paySelectDialog.getWindow().setLayout(650, 500);
                confirmButton.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(BillPaymentActivity.this, PaymentActivity.class));
            }
        });
    }

    private void setupToolbar() {
        toolbarText.setText("Bill Payment");
        setSupportActionBar(toolbar);
    }

    public void displayarraylist() {
        arraylist = new ArrayList<BillDetailsItems>();
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));
        arraylist.add(new BillDetailsItems(1234567, 2834, "DESCO", "24/8/2015", false, 1200));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos = lv.getPositionForView(buttonView);
        if (pos != ListView.INVALID_POSITION) {
            BillDetailsItems l = arraylist.get(pos);
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
