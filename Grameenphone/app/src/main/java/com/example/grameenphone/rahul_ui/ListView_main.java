package com.example.grameenphone.rahul_ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.example.grameenphone.R;

import java.util.ArrayList;

/**
 * Created by rahul on 11/09/15.
 */
public class ListView_main extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ListView lv;
    ArrayList<Listitems> arraylist;
    ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);
        lv=(ListView) findViewById(R.id.recycle_view);
        displayarraylist();
        listViewAdapter = new ListViewAdapter(this,arraylist);
        lv.setAdapter(listViewAdapter);

    }

    public void displayarraylist()
    {
        arraylist = new ArrayList<Listitems>();
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));
        arraylist.add(new Listitems(1234567, 2834, "DESCO","24/8/2015",false, 1200));

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos= lv.getPositionForView(buttonView);
        if(pos!=ListView.INVALID_POSITION)
        {
            Listitems l = arraylist.get(pos);
            l.setSelected(isChecked);

        }
    }
}
