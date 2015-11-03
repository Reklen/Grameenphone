package com.cc.grameenphone.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.AssociationBillModel;
import com.cc.grameenphone.api_models.UserBillsModel;
import com.cc.grameenphone.interfaces.ButtonClickInterface;
import com.cc.grameenphone.viewmodels.BillDetailsViewHolder;

import java.util.List;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationAdapter extends ArrayAdapter {
    List<AssociationBillModel> list;
    Context context;
    ButtonClickInterface buttonClickInterface;
    private Boolean isCancelButtonVisible= true;


    static class ViewHolder {
        TextView accconttxt;
        TextView accNumbtext;
        TextView compText;
        TextView descText;
        Button cancelBtn;
        CheckBox checkBox;
    }


    public CancelAssociationAdapter(Context context, List<AssociationBillModel> objects, ButtonClickInterface buttonClickInterface) {
        super(context, R.layout.cancel_association_item);
        this.list = objects;
        this.context = context;
        this.buttonClickInterface = buttonClickInterface;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cancel_association_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.accNumbtext = (TextView) convertView.findViewById(R.id.accountNumber);
            viewHolder.descText = (TextView) convertView.findViewById(R.id.companyName);
            viewHolder.cancelBtn = (Button) convertView.findViewById(R.id.cancelButton);
            viewHolder.checkBox= (CheckBox) convertView.findViewById(R.id.cancelCheckBox);
            viewHolder.checkBox.setTag(getItem(position));
            viewHolder.cancelBtn.setTag(position);

            if (!isCancelButtonVisible)
                viewHolder.cancelBtn.setVisibility(View.INVISIBLE);
            else
                viewHolder.cancelBtn.setVisibility(View.VISIBLE);
            convertView.setTag(viewHolder);
        } else {
            ((ViewHolder) convertView.getTag()).checkBox.setTag(getItem(position));
            ((ViewHolder) convertView.getTag()).cancelBtn.setTag(position);
            if (!isCancelButtonVisible)
                ((ViewHolder) convertView.getTag()).cancelBtn.setVisibility(View.INVISIBLE);
            else
                ((ViewHolder) convertView.getTag()).cancelBtn.setVisibility(View.VISIBLE);

        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pos",""+getItem(position).getACCNUM());
                if (buttonClickInterface != null)
                    buttonClickInterface.onBtnClick((Integer) v.getTag());
            }
        });

        AssociationBillModel model = (AssociationBillModel) getItem(position);
        if (model != null) {
            viewHolder.accNumbtext.setText(model.getACCNUM());
            viewHolder.descText.setText(model.getCOMPCODE());
            viewHolder.checkBox.setChecked(getItem(position).isSelected());
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
    public void toggleCancelButton(boolean b) {
        isCancelButtonVisible = b;
        notifyDataSetInvalidated();
    }
    public List<AssociationBillModel> getListitemslist() {
        return list;
    }
}
