package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.viewmodels.BillDetailsItems;
import com.cc.grameenphone.viewmodels.BillDetailsViewHolder;

import java.util.List;

/**
 * Created by rahul on 09/09/15.
 */
public class ListViewAdapter extends ArrayAdapter {

    private List<BillDetailsItems> listitemslist;
    private Context mContext;
    private int focuseditem = 0;
    // LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    public ListViewAdapter(Context context, List<BillDetailsItems> Objects) {
        super(context, R.layout.listrow, Objects);
        this.mContext = context;
        this.listitemslist = Objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        BillDetailsViewHolder holder = new BillDetailsViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.listrow, null);
            holder.accountNumber_String = (TextView) itemView.findViewById(R.id.accountNumberText);
            holder.billNumber_String = (TextView) itemView.findViewById(R.id.billNumber_String);
            holder.company_String = (TextView) itemView.findViewById(R.id.company_String);
            holder.dueDate_String = (TextView) itemView.findViewById(R.id.dueDate_String);
            holder.checkBox = (CheckBox) itemView.findViewById(R.id.billCheckBox);
            holder.accountNumber = (TextView) itemView.findViewById(R.id.accountNumber);
            holder.billNumber = (TextView) itemView.findViewById(R.id.billNumber);
            holder.company = (TextView) itemView.findViewById(R.id.companyName);
            holder.dueDate = (TextView) itemView.findViewById(R.id.dueDate);
            holder.paybutton = (Button) itemView.findViewById(R.id.payButton);
            // holder.inr = (TextView) itemView.findViewById(R.id.inr);
            holder.value = (TextView) itemView.findViewById(R.id.totalBillAmount);


        } else {
            holder = (BillDetailsViewHolder) itemView.getTag();
        }
        // BillDetailsItems l = listitemslist.get(position);
        if (convertView != null) {
            holder.accountNumber_String.setText("");
            holder.billNumber_String.setText("");
            holder.company_String.setText("");
            holder.dueDate_String.setText("");
//        holder.checkBox.setChecked(l.isSelected());
//        holder.checkBox.setTag(l);
            holder.accountNumber.setText("");
            holder.billNumber.setText("");
            holder.company.setText("");
            holder.dueDate.setText("");
            holder.value.setText("");
            holder.inr.setText("");
        }
        return itemView;

    }

}
