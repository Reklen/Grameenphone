package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.cc.grameenphone.R;
import com.cc.grameenphone.viewmodels.PaidBillsItems;

import java.util.List;

/**
 * Created by rahul on 11/09/15.
 */
public class PaidBillsDialogueAdapter extends ArrayAdapter {
    private List<PaidBillsItems> paidBillsItemsList;
    private Context context;

    public PaidBillsDialogueAdapter(Context context, int resource, List<PaidBillsItems> paidBillsItemsList) {
        super(context, R.layout.paidbills_listrow);
        this.context = context;
        this.paidBillsItemsList = paidBillsItemsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        return itemView;

    }
}
