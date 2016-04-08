package com.cc.grameenphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.NotificationMessageModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NotificationAdapter extends BaseAdapter {

    List<NotificationMessageModel> list;
    Context context;
    private LayoutInflater inflater;

    public NotificationAdapter(List<NotificationMessageModel> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NotificationMessageModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;

        if (convertView == null) {

            itemView = inflater.inflate(R.layout.notification_row_item, null);

            ViewHolder holder = new ViewHolder(itemView);
            holder.notificationTypeTextView = (TextView) itemView.findViewById(R.id.notificationTypeTextView);
            holder.notificationTitleTextView = (TextView) itemView.findViewById(R.id.notificationTitleTextView);
            holder.notificationContentTextView = (TextView) itemView.findViewById(R.id.notificationContentTextView);
            holder.notificationTimeTextView = (TextView) itemView.findViewById(R.id.notificationTimeTextView);


            itemView.setTag(holder);
        } else {
            itemView = convertView;
        }
        ViewHolder holder = (ViewHolder) itemView.getTag();
        NotificationMessageModel model = list.get(position);
        holder.notificationTypeTextView.setText("" + model.getNOTCODE());
        holder.notificationTitleTextView.setText("" + model.getNOTHEAD());
        holder.notificationContentTextView.setText("" + model.getNOTDATA());

        holder.notificationTimeTextView.setText("" + model.getNOTSTDAT());


        return itemView;

    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'adapter_item_notification.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.notificationTypeTextView)
        TextView notificationTypeTextView;
        @Bind(R.id.notificationTitleTextView)
        TextView notificationTitleTextView;
        @Bind(R.id.notificationContentTextView)
        TextView notificationContentTextView;
        @Bind(R.id.notificationTimeTextView)
        TextView notificationTimeTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
