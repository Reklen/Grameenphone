package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.AssociationBillModel;
import com.cc.grameenphone.interfaces.ButtonClickInterface;

import java.util.List;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationAdapter extends ArrayAdapter {
    List<AssociationBillModel> list;
    Context context;
    ButtonClickInterface buttonClickInterface;


    static class ViewHolder {
        TextView accconttxt;
        TextView accNumbtext;
        TextView compText;
        TextView descText;
        Button cancelBtn;
    }


    public CancelAssociationAdapter(Context context, List<AssociationBillModel> objects, ButtonClickInterface buttonClickInterface) {
        super(context, R.layout.cancel_association_item);
        this.list = objects;
        this.context = context;
        this.buttonClickInterface = buttonClickInterface;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cancel_association_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.accNumbtext = (TextView) convertView.findViewById(R.id.accountNumber);
            viewHolder.descText = (TextView) convertView.findViewById(R.id.companyName);
            viewHolder.cancelBtn = (Button) convertView.findViewById(R.id.cancelButton);
            viewHolder.cancelBtn.setTag(position);
            viewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (buttonClickInterface != null)
                        buttonClickInterface.onBtnClick((Integer) v.getTag());
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        AssociationBillModel model = (AssociationBillModel) getItem(position);
        if (model != null) {
            viewHolder.accNumbtext.setText(model.getACCNUM());
            viewHolder.descText.setText(model.getCOMPCODE());
        }
        return convertView;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public AssociationBillModel getItem(int position) {
        return list.get(position);
    }
}
