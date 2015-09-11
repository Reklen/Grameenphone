package com.example.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grameenphone.R;

public class AddFavoritesActivity extends AppCompatActivity {


    Button addbtn;
    TextView tooltext;
    EditText phonetext;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorites);
        back_icon = (ImageView) findViewById(R.id.image_icon_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tooltext = (TextView) findViewById(R.id.text_tool);
        tooltext.setText("Add Favorites");
        addbtn = (Button) findViewById(R.id.add_btn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddFavoritesActivity.this, "Added successfully", Toast.LENGTH_LONG).show();
            }
        });
        phonetext = (EditText) findViewById(R.id.editphone);
        phonetext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (phonetext.getRight() - phonetext.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        startActivity(new Intent(AddFavoritesActivity.this, AddFavoriteContactsActivity.class));
                        return true;
                    }
                }
                return false;
            }
        });
    }

}
