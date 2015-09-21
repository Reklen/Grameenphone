package com.cc.grameenphone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.utils.CircularContactView;
import com.cc.grameenphone.utils.ContactImageUtil;
import com.cc.grameenphone.utils.ImageCache;
import com.cc.grameenphone.utils.async_task_thread_pool.AsyncTaskEx;
import com.cc.grameenphone.utils.async_task_thread_pool.AsyncTaskThreadPool;

import java.util.List;
import java.util.Locale;

/**
 * Created by aditlal on 20/09/15.
 */
public class ManageFavAdapter extends BaseAdapter {
    List<ContactModel> list;
    Context context;
    private LayoutInflater mInflater;
    private final int CONTACT_PHOTO_IMAGE_SIZE;
    private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
    private final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);

    public ManageFavAdapter(List<ContactModel> list, Context ctx) {
        this.list = list;
        this.context = ctx;
        PHOTO_TEXT_BACKGROUND_COLORS = ctx.getResources().getIntArray(R.array.contacts_text_background_colors);
        CONTACT_PHOTO_IMAGE_SIZE = ctx.getResources().getDimensionPixelSize(
                R.dimen.list_item__contact_imageview_size);
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ContactModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final View rootView;
        if (convertView == null) {
            holder = new ViewHolder();
            rootView = mInflater.inflate(R.layout.adapter_item_fav_contact, parent, false);
            holder.friendProfileCircularContactView = (CircularContactView) rootView
                    .findViewById(R.id.listview_item__friendPhotoImageView);
            holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);
            holder.friendName = (TextView) rootView
                    .findViewById(R.id.listview_item__friendNameTextView);
            holder.friendNumber = (TextView) rootView.findViewById(R.id.listview_item__friendNumberTextView);
            //holder.headerView = (TextView) rootView.findViewById(R.id.header_text);
            // holder.headerView.setVisibility(View.GONE);
            rootView.setTag(holder);
        } else {
            rootView = convertView;
            holder = (ViewHolder) rootView.getTag();
            // holder.headerView.setVisibility(View.GONE);
        }
        final ContactModel contact = getItem(position);
        final String displayName = contact.getName();
        try {
            final String number = contact.getNumber();
            holder.friendNumber.setText("" + number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.friendName.setText(displayName);
        boolean hasPhoto = !TextUtils.isEmpty(contact.getPhotoId());
        if (holder.updateTask != null && !holder.updateTask.isCancelled())
            holder.updateTask.cancel(true);
        final Bitmap cachedBitmap = hasPhoto ? ImageCache.INSTANCE.getBitmapFromMemCache(contact.getPhotoId()) : null;
        if (cachedBitmap != null)
            holder.friendProfileCircularContactView.setImageBitmap(cachedBitmap);
        else {
            final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position
                    % PHOTO_TEXT_BACKGROUND_COLORS.length];
            if (TextUtils.isEmpty(displayName))
                holder.friendProfileCircularContactView.setImageResource(R.drawable.icon_invite,
                        backgroundColorToUse);
            else {
                final String characterToShow = TextUtils.isEmpty(displayName) ? "" : displayName.substring(0, 1).toUpperCase(Locale.getDefault());
                holder.friendProfileCircularContactView.setTextAndBackgroundColor(characterToShow, backgroundColorToUse);
            }
            if (hasPhoto) {
                holder.updateTask = new AsyncTaskEx<Void, Void, Bitmap>() {

                    @Override
                    public Bitmap doInBackground(final Void... params) {
                        if (isCancelled())
                            return null;
                        final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(context, contact.getPhotoId(), CONTACT_PHOTO_IMAGE_SIZE);
                        if (b != null)
                            return ThumbnailUtils.extractThumbnail(b, CONTACT_PHOTO_IMAGE_SIZE,
                                    CONTACT_PHOTO_IMAGE_SIZE);
                        return null;
                    }

                    @Override
                    public void onPostExecute(final Bitmap result) {
                        super.onPostExecute(result);
                        if (result == null)
                            return;
                        ImageCache.INSTANCE.addBitmapToCache(contact.getPhotoId(), result);
                        holder.friendProfileCircularContactView.setImageBitmap(result);
                    }
                };
                mAsyncTaskThreadPool.executeAsyncTask(holder.updateTask);
            }
        }
        return rootView;
    }

    public void remove(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public CircularContactView friendProfileCircularContactView;
        TextView friendName, friendNumber;
        public AsyncTaskEx<Void, Void, Bitmap> updateTask;
    }

}
