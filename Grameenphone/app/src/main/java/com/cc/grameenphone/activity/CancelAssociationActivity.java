package com.cc.grameenphone.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.CancelAssociationAdapter;
import com.cc.grameenphone.viewmodels.CancelAssociationItem;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationActivity extends AppCompatActivity {
    Context context;
    CancelAssociationAdapter adapter;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.associationList)
    ListView associationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_association_activity);
        ButterKnife.inject(this);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        associationList = (ListView) findViewById(R.id.associationList);
        adapter = new CancelAssociationAdapter(this, CancelAssociationItem.ItemList.getItem());
        associationList.setAdapter(adapter);

        //listView.setAdapter(new CancelAssociationAdapter(context,CancelAssociationItem.ItemList.getItem()));

    }
}
