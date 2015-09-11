package com.example.grameenphone.sign_up;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.grameenphone.R;


public class MainActivity extends ActionBarActivity {
    TextView grameenPhone,numberText,iAccept,termsCondition,walletPinText;
    EditText numberEdit,walletPInEditText;
    CheckBox checkBox;
    ImageView grameenIcon;
    Button createNewWallet,login;
    LinearLayout walletPinLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grameenphone);
        //textViews
        grameenPhone= (TextView) findViewById(R.id.grameen_text);
        numberText= (TextView) findViewById(R.id.number_text);
        iAccept= (TextView) findViewById(R.id.accept_text);
        termsCondition= (TextView) findViewById(R.id.terms_text);
        walletPinText= (TextView) findViewById(R.id.wallet_pin_text);

        //LinearLayouts or view's
        walletPinLayout= (LinearLayout) findViewById(R.id.wallet_pin_layout);


        //EditText
        walletPInEditText= (EditText) findViewById(R.id.wallet_pin_edittext);
        numberEdit= (EditText) findViewById(R.id.number_edittext);
        numberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==10){
                    walletPinLayout.setVisibility(View.VISIBLE);
                  // login.setVisibility(View.VISIBLE);
                }else {
                    walletPinLayout.setVisibility(View.GONE);
                   // login.setVisibility(View.GONE);
                }

            }
        });

        //checkBox
        checkBox= (CheckBox) findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    login.setVisibility(View.VISIBLE);
//                            str=viewHolder01.numb.getText().toString();
//                            Log.d("value",str);
                } else {
                    login.setVisibility(View.GONE);
                }
            }
        });

        //igmageView
        grameenIcon= (ImageView) findViewById(R.id.grameen_icon);

        //Button
        createNewWallet= (Button) findViewById(R.id.create_btn);
        createNewWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        login= (Button) findViewById(R.id.login_btn);
        login.setVisibility(View.GONE);



    }

//    AppCompatDialog exit_dialog;
//    exit_dialog = new AppCompatDialog(MainActivity.this);
//    exit_dialog.setContentView(R.layout.dialog_exit_app);
//    exit_app = (Button) exit_dialog.findViewById(R.id.accept_btn);
//    cancel_app_exit = (Button) exit_dialog.findViewById(R.id.cancel_btn);
//    exit_dialog.show();
//    cancel_app_exit.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            exit_dialog.dismiss();
//        }
//    });
//    exit_app.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            exit_dialog.cancel();
//            finish();
//        }
//    });
}
