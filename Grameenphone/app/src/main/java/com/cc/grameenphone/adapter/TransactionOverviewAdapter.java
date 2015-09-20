package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.TransactionOverviewData;

import java.util.List;

/**
 * Created by aditlal on 20/09/15.
 */
public class TransactionOverviewAdapter extends BaseAdapter {

    private List<TransactionOverviewData> listitemslist;
    private Context mContext;
    private int focuseditem = 0;
    // LayoutInflater inflater = LayoutInflater inflater; (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LayoutInflater inflater;
    boolean isPayButtonVisible = true;


    public TransactionOverviewAdapter(Context context, List<TransactionOverviewData> list) {
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listitemslist = list;
    }

    @Override
    public int getCount() {
        return listitemslist.size();
    }

    @Override
    public TransactionOverviewData getItem(int i) {
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

            itemView = inflater.inflate(R.layout.adapter_item_transaction, null);

            ViewHolder holder = new ViewHolder();
            holder.service_type = (TextView) itemView.findViewById(R.id.service_type_tv);
            holder.amountTextView = (TextView) itemView.findViewById(R.id.amount_tv);
            itemView.setTag(holder);

        } else {
            itemView = convertView;

        }
        ViewHolder holder = (ViewHolder) itemView.getTag();

//        holder.checkBox.setChecked(l.isSelected());
//        holder.checkBox.setTag(l);
        holder.service_type.setText("" + getItem(position).getSERVICE());
        holder.amountTextView.setText("" + getItem(position).getTXNAMT());
//        holder.inr.setText("");
       /* // BillDetailsItems l = listitemslist.get(position);
        if (convertView != null) {

        }*/
        return itemView;

    }

    static class ViewHolder {
        protected TextView service_type, amountTextView;
    }


}

