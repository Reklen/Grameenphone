package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cc.grameenphone.R;

public class ReferFriendsActivity extends AppCompatActivity {

    EditText phonetext;
    ImageView back_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_friends);
        phonetext= (EditText)findViewById(R.id.phoneNumberEditText);
        phonetext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (phonetext.getRight() - phonetext.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        startActivity(new Intent(ReferFriendsActivity.this, SelectContactsActivity.class));
                        return true;
                    }
                }
                return false;
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
