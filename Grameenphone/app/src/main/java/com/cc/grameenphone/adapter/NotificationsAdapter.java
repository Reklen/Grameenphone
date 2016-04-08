package com.cc.grameenphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.NotificationMessageModel;
import com.cc.grameenphone.interfaces.NotificationClickInterface;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by aditlal on 10/11/15.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder> {
    List<NotificationMessageModel> notifications;
    List<Integer> sectionPositionList;
    int size8 = 8, size24 = 24;
    int margin8 = 8, margin24 = 24;
    NotificationClickInterface clickInterface;
    Context context;


    public NotificationsAdapter(Context c, List<NotificationMessageModel> notifications, NotificationClickInterface clickInterface) {
        this.notifications = notifications;
        this.context = c;
        this.clickInterface = clickInterface;
        try {
            margin8 = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, size8, c.getResources()
                            .getDisplayMetrics());
            margin24 = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, size24, c.getResources()
                            .getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public NotificationsAdapter.NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_row_item, parent, false);
        return new NotificationsViewHolder(clickInterface, v);
    }

    @Override
    public void onBindViewHolder(NotificationsViewHolder holder, int position) {
        //Logger.d("BindHodler", "pos = " + position);
        NotificationMessageModel model = notifications.get(position);
        holder.notificationTypeTextView.setText("" + model.getNOTCODE());
        holder.notificationLayout.setTag(position);
        holder.notificationTitleTextView.setText("" + model.getNOTHEAD());
        holder.notificationContentTextView.setText("" + model.getNOTDATA());

        holder.notificationTimeTextView.setText("" + model.getNOTSTDAT());


    }


    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public NotificationMessageModel getItemForPosition(int pos) {
        return notifications.get(pos);
    }

    public void setSectionPositionList(List<Integer> sectionPositionList) {
        this.sectionPositionList = sectionPositionList;
    }

    static class NotificationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.notificationTypeTextView)
        TextView notificationTypeTextView;
        @Bind(R.id.notificationTitleTextView)
        TextView notificationTitleTextView;
        @Bind(R.id.notificationContentTextView)
        TextView notificationContentTextView;
        @Bind(R.id.notificationTimeTextView)
        TextView notificationTimeTextView;
        @Bind(R.id.notificationLayout)
        RelativeLayout notificationLayout;


        NotificationClickInterface clickInterface;

        NotificationsViewHolder(NotificationClickInterface clickInterface, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.clickInterface = clickInterface;
            notificationLayout.setOnClickListener(this);
          /*  notificationTypeTextView.setOnClickListener(this);
            notificationTitleTextView.setOnClickListener(this);
            notificationContentTextView.setOnClickListener(this);
            notificationTimeTextView.setOnClickListener(this);

*/
        }

        @Override
        public void onClick(View v) {

            if (clickInterface != null)
                if (v.getTag() != null)
                    clickInterface.clickNotification((Integer) v.getTag());
        }
    }


}
