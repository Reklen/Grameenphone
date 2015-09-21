package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.UserBillsModel;
import com.cc.grameenphone.viewmodels.BillDetailsViewHolder;

import java.util.List;

/**
 * Created by rahul on 09/09/15.
 */
public class BillsListAdapter extends BaseAdapter {

    private List<UserBillsModel> listitemslist;
    private Context mContext;
    private int focuseditem = 0;
    // LayoutInflater inflater = LayoutInflater inflater; (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LayoutInflater inflater;
    boolean isPayButtonVisible = true;


    public BillsListAdapter(Context context, List<UserBillsModel> list) {
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listitemslist = list;
    }

    @Override
    public int getCount() {
        return listitemslist.size();
    }

    @Override
    public UserBillsModel getItem(int i) {
        return listitemslist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;

        if (convertView == null) {

            itemView = inflater.inflate(R.layout.adapter_item_bill, null);

            BillDetailsViewHolder holder = new BillDetailsViewHolder();
            holder.accountNumber_String = (TextView) itemView.findViewById(R.id.accountNumberText);
            holder.billNumber_String = (TextView) itemView.findViewById(R.id.billNumber_String);
            holder.company_String = (TextView) itemView.findViewById(R.id.company_String);
            holder.dueDate_String = (TextView) itemView.findViewById(R.id.dueDate_String);
            holder.checkBox = (CheckBox) itemView.findViewById(R.id.billCheckBox);
            holder.accountNumber = (TextView) itemView.findViewById(R.id.accountNumber);
            holder.billNumber = (TextView) itemView.findViewById(R.id.billNumber);
            holder.company = (TextView) itemView.findViewById(R.id.companyName);
            holder.categoryType = (TextView) itemView.findViewById(R.id.categoryCompany);
            holder.dueDate = (TextView) itemView.findViewById(R.id.dueDate);
            holder.paybutton = (Button) itemView.findViewById(R.id.payButton);
            // holder.inr = (TextView) itemView.findViewById(R.id.inr);
            holder.value = (TextView) itemView.findViewById(R.id.totalBillAmount);
            if (!isPayButtonVisible)
                holder.paybutton.setVisibility(View.INVISIBLE);
            else
                holder.paybutton.setVisibility(View.VISIBLE);

            itemView.setTag(holder);
            holder.checkBox.setTag(getItem(position));

        } else {
            itemView = convertView;
            ((BillDetailsViewHolder) itemView.getTag()).checkBox.setTag(getItem(position));
            if (!isPayButtonVisible)
                ((BillDetailsViewHolder) itemView.getTag()).paybutton.setVisibility(View.INVISIBLE);
            else
                ((BillDetailsViewHolder) itemView.getTag()).paybutton.setVisibility(View.VISIBLE);
        }
        BillDetailsViewHolder holder = (BillDetailsViewHolder) itemView.getTag();

//        holder.checkBox.setChecked(l.isSelected());
//        holder.checkBox.setTag(l);
        holder.accountNumber.setText("" + listitemslist.get(position).getACCOUNTNUM());
        holder.billNumber.setText("" + "" + listitemslist.get(position).getBILLNUM());
        holder.company.setText("" + listitemslist.get(position).getCOMPANYNAME());
        holder.dueDate.setText("" + listitemslist.get(position).getDUEDATE());
        holder.value.setText("৳ " + listitemslist.get(position).getAMOUNT());
        holder.categoryType.setText("" + listitemslist.get(position).getCATEGORYNAME());
        holder.checkBox.setChecked(getItem(position).isSelected());

//        holder.inr.setText("");
       /* // BillDetailsItems l = listitemslist.get(position);
        if (convertView != null) {

        }*/
        return itemView;

    }

    public void togglePayButton(boolean b) {
        isPayButtonVisible = b;
        notifyDataSetInvalidated();
    }
}