package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.viewmodels.TransactionItem;

import java.util.ArrayList;

/**
 * Created by rajkiran on 10/09/15.
 */
public class TransactionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TransactionItem> transactionItemArrayList;
    private static LayoutInflater inflater=null;
    public TransactionAdapter(Context context1) {
        this.context = context1;
        transactionItemArrayList = this.transactionItemArrayList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return transactionItemArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.transaction_row_layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.servicetext = (TextView) vi.findViewById(R.id.service_type);
            holder.servieamt=(TextView)vi.findViewById(R.id.tranc_amt);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();
        return null;
    }
    public static class ViewHolder{

        public TextView servicetext;
        public TextView servieamt;

    }
}
