package com.example.grameenphone.rahul_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.grameenphone.R;

import java.util.List;

/**
 * Created by rahul on 11/09/15.
 */
public class PaidbillsDialogueAdapter extends ArrayAdapter {
    private List<Paidbillsitems> paidbillsitemsList;
    private Context context;

    public PaidbillsDialogueAdapter(Context context, int resource, List<Paidbillsitems> paidbillsitemsList) {
        super(context, R.layout.paidbills_listrow);
        this.context = context;
        this.paidbillsitemsList = paidbillsitemsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        return itemView;

    }
}
