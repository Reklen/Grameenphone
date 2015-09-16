package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ReferFriendsActivity extends AppCompatActivity {

    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.refer_text)
    TextView referText;
    @InjectView(R.id.refer_code)
    TextView referCode;
    @InjectView(R.id.refer_text_main)
    TextView referTextMain;
    @InjectView(R.id.areaCode)
    EditText areaCode;
    @InjectView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @InjectView(R.id.phone_container)
    TextInputLayout phoneContainer;
    @InjectView(R.id.top_container1)
    RelativeLayout topContainer1;
    @InjectView(R.id.confirm_btn)
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_friends);
        ButterKnife.inject(this);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        phoneNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (phoneNumberEditText.getRight() - phoneNumberEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        startActivity(new Intent(ReferFriendsActivity.this, SelectContactsActivity.class));
                        return true;
                    }
                }
                return false;
            }
        });
    }


}
