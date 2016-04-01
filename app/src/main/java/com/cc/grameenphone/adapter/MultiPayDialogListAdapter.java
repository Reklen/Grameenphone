package com.cc.grameenphone.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.viewmodels.MultiBillListModel;
import com.cc.grameenphone.viewmodels.MultiBillsListViewHolder;

import java.util.List;

/**
 * Created by rahul on 09/09/15.
 */
public class MultiPayDialogListAdapter extends BaseAdapter {

    private List<MultiBillListModel> listitemslist;
    private Context mContext;
    private int focuseditem = 0;
    // LayoutInflater inflater = LayoutInflater inflater; (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    LayoutInflater inflater;


    public MultiPayDialogListAdapter(Context context, List<MultiBillListModel> list) {
        this.mContext = context;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listitemslist = list;
    }

    @Override
    public int getCount() {
        return listitemslist.size();
    }

    @Override
    public MultiBillListModel getItem(int i) {
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

            itemView = inflater.inflate(R.layout.adapter_multi_bill, null);

            MultiBillsListViewHolder holder = new MultiBillsListViewHolder();
            holder.account_textView = (TextView) itemView.findViewById(R.id.accountNmb_text);
            holder.amount_textView = (TextView) itemView.findViewById(R.id.amount_text);
            holder.statusImageView = (ImageView) itemView.findViewById(R.id.statusImage);


            itemView.setTag(holder);
            holder.statusImageView.setTag(getItem(position));
        } else {
            itemView = convertView;
            ((MultiBillsListViewHolder) itemView.getTag()).statusImageView.setTag(getItem(position));
        }
        MultiBillsListViewHolder holder = (MultiBillsListViewHolder) itemView.getTag();
        MultiBillListModel model = listitemslist.get(position);
        holder.account_textView.setText(model.getAccountNumber());
        holder.amount_textView.setText("৳ " + model.getAmount());
        if (model.getStatus() == -1)
            holder.statusImageView.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
        else if (model.getStatus() == 0)
            holder.statusImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_cross));
        else
            holder.statusImageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_check));
        return itemView;

    }


}