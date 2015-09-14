package com.cc.grameenphone.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.CancelAssociationAdapter;
import com.cc.grameenphone.viewmodels.CancelAssociationItem;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationActivity extends ActionBarActivity {
    ListView listView;
    Context context;
    CancelAssociationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_association_activity);
        listView= (ListView) findViewById(R.id.list_view);
        adapter= new CancelAssociationAdapter(this,CancelAssociationItem.ItemList.getItem());
        listView.setAdapter(adapter);

        //listView.setAdapter(new CancelAssociationAdapter(context,CancelAssociationItem.ItemList.getItem()));

    }
}
