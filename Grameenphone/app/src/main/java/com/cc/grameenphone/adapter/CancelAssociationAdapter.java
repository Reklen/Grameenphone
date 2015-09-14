package com.cc.grameenphone.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.viewmodels.CancelAssociationItem;

import java.util.List;

/**
 * Created by Rajkiran on 9/10/2015.
 */
public class CancelAssociationAdapter extends ArrayAdapter {
    List<CancelAssociationItem> list;
    Context context;
    AppCompatDialog cancelDialog, removeDialog;
    Button cancelBtn,removeBtn,okay_btn;

    static class ViewHolder {
        TextView accconttxt;
        TextView accNumbtext;
        TextView compText;
        TextView descText;
        Button cancelBtn;
    }


    public CancelAssociationAdapter(Context context, List<CancelAssociationItem> Objects) {
        super(context, R.layout.cancel_association_item,Objects);
        this.list=Objects;
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.cancel_association_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.accconttxt = (TextView) convertView.findViewById(R.id.account_numb_text);
            viewHolder.accNumbtext = (TextView) convertView.findViewById(R.id.accNumb_text);
            viewHolder.compText = (TextView) convertView.findViewById(R.id.comp_text);
            viewHolder.descText = (TextView) convertView.findViewById(R.id.desc_text);
            viewHolder.cancelBtn= (Button) convertView.findViewById(R.id.cancel_btn);
            viewHolder.cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancelDialog= new AppCompatDialog(getContext());
                    cancelDialog.setContentView(R.layout.list_item_cancel_dialog);
                    removeBtn = (Button) cancelDialog.findViewById(R.id.remove_btn);
                    cancelBtn = (Button) cancelDialog.findViewById(R.id.canceldialog_btn);
                    cancelDialog.show();
                    cancelDialog.getWindow().setLayout(600, 300);
                    removeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                         removeDialog= new AppCompatDialog(context);
                            removeDialog.setContentView(R.layout.payment_successfull_dialog);
                            okay_btn= (Button) removeDialog.findViewById(R.id.okay_btn);
                            removeDialog.show();
                            removeDialog.getWindow().setLayout(600, 300);
                            okay_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                            removeDialog.setCanceledOnTouchOutside(true);
                            cancelDialog.dismiss();
                        }
                    });
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cancelDialog.dismiss();
                        }
                    });
                    //confirmDialog.setCanceledOnTouchOutside(true);

                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CancelAssociationItem cancelList = (CancelAssociationItem) getItem(position);
        if (cancelList != null) {
            viewHolder.accconttxt.setText(cancelList.getAccNumbText());
            viewHolder.accNumbtext.setText(cancelList.getAccountText());
            viewHolder.compText.setText(cancelList.getCompText());
            viewHolder.descText.setText(cancelList.getDescText());
            viewHolder.cancelBtn.getResources().getInteger(cancelList.getCancelBtn());
        }
        return convertView;
    }
}
