package com.example.grameenphone.rahul_ui;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.grameenphone.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 09/09/15.
 */
public class ListViewAdapter extends ArrayAdapter {

    private List<Listitems> listitemslist;
    private Context mContext;
    private int focuseditem = 0;
   // LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    public ListViewAdapter(Context context,List<Listitems> Objects) {
        super(context, R.layout.listrow,Objects);
        this.mContext = context;
        this.listitemslist = Objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ListViewRowHolder holder = new ListViewRowHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.listrow, null);
            holder.accountNumber_String = (TextView) itemView.findViewById(R.id.accountNumber_String);
            holder.billNumber_String = (TextView) itemView.findViewById(R.id.billNumber_String);
            holder.company_String = (TextView) itemView.findViewById(R.id.company_String);
            holder.dueDate_String = (TextView) itemView.findViewById(R.id.dueDate_String);
            holder.checkBox = (CheckBox) itemView.findViewById(R.id.chckBox);
            holder.accountNumber = (TextView) itemView.findViewById(R.id.accountNumber);
            holder.billNumber = (TextView) itemView.findViewById(R.id.billNumber);
            holder.company = (TextView) itemView.findViewById(R.id.company);
            holder.dueDate = (TextView) itemView.findViewById(R.id.dueDate);
            holder.paybutton = (Button) itemView.findViewById(R.id.paybutton);
           // holder.inr = (TextView) itemView.findViewById(R.id.inr);
            holder.value = (TextView) itemView.findViewById(R.id.ruppesTExt);


        } else {
            holder = (ListViewRowHolder) itemView.getTag();
        }
        // Listitems l = listitemslist.get(position);
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
