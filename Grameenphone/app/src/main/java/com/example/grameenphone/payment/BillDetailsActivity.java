package com.example.grameenphone.payment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.grameenphone.R;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class BillDetailsActivity extends ActionBarActivity {
    TextView CompanyText,DESCOTExt,ACNOText,AccNumbText,billNumbText,bNumbText,AmountText,AnumbText,SubchargeText,subNumbText,
             dueDateText,pinConfromText;
    EditText pinNumbEdit;
    Button confrim_btn,okay_btn;
    AppCompatDialog confirmDialog;
    Toolbar toolbar;
    LinearLayout toolbarContainer;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_details);
        toolbar= (Toolbar) findViewById(R.id.desco_toolbar);
        toolbarContainer= (LinearLayout) findViewById(R.id.toolbar_container);
        toolbarContainer.setVisibility(View.VISIBLE);
        toolbarText= (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("DESCO");

        // setSupportActionBar(toolbar);
//        toolbarLayout= (LinearLayout) findViewById(R.id.toolbar_container);
//        toolbarText= (TextView) findViewById(R.id.toolbar_text);
//        getSupportActionBar().setTitle("DESCO");

        //EditText
        pinNumbEdit= (EditText) findViewById(R.id.pinNumbEdit);

        //Button
        confrim_btn= (Button) findViewById(R.id.conform_btn);
        confrim_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pinNumber = pinNumbEdit.getText().toString();
                Log.d("pinNumb", pinNumber);

                confirmDialog = new AppCompatDialog(BillDetailsActivity.this);
                confirmDialog.setContentView(R.layout.payment_successfull_dialog);
                okay_btn = (Button) confirmDialog.findViewById(R.id.okay_btn);
                confirmDialog.show();
                confirmDialog.getWindow().setLayout(600, 300);
                okay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
                confirmDialog.setCanceledOnTouchOutside(true);

            }
        });

    }
}