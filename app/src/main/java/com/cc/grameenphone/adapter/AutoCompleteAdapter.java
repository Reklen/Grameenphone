package com.cc.grameenphone.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.utils.CircularContactView;
import com.cc.grameenphone.utils.ContactImageUtil;
import com.cc.grameenphone.utils.ImageCache;
import com.cc.grameenphone.utils.async_task_thread_pool.AsyncTaskEx;
import com.cc.grameenphone.utils.async_task_thread_pool.AsyncTaskThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Rajkiran on 9/28/2015.
 */
public class AutoCompleteAdapter extends ArrayAdapter<ContactModel> implements Filterable {
    private static int CONTACT_PHOTO_IMAGE_SIZE;
    private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
    private List<ContactModel> fullList;
    List<ContactModel> mOriginalValues;
    ArrayFilter mFilter;
    Context context;
    private final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);
    LayoutInflater layoutInflater;

    static class ViewHolder {
        TextView nametext;
        TextView numberText;
        CircularContactView friendProfileCircularContactView;
        AsyncTaskEx<Void, Void, Bitmap> updateTask;
    }

    public AutoCompleteAdapter(Context context, List<ContactModel> objects) {
        super(context, R.layout.adapter_item_fav_contact, objects);
        this.fullList = objects;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        PHOTO_TEXT_BACKGROUND_COLORS = context.getResources().getIntArray(R.array.contacts_text_background_colors);
        CONTACT_PHOTO_IMAGE_SIZE = context.getResources().getDimensionPixelSize(
                R.dimen.list_item__contact_imageview_size);
    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public ContactModel getItem(int position) {
        return fullList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.adapter_item_fav_contact, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.nametext = (TextView) convertView.findViewById(R.id.listview_item__friendNameTextView);
            viewHolder.numberText = (TextView) convertView.findViewById(R.id.listview_item__friendNumberTextView);
            viewHolder.friendProfileCircularContactView = (CircularContactView) convertView.findViewById(R.id.listview_item__friendPhotoImageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ContactModel model = getItem(position);
        if (model != null) {
            String displayName = model.getName();
            viewHolder.nametext.setText(model.getName());
            viewHolder.numberText.setText(model.getNumber());
            boolean hasPhoto = !TextUtils.isEmpty(model.getPhotoId());
            if (viewHolder.updateTask != null && !viewHolder.updateTask.isCancelled())
                viewHolder.updateTask.cancel(true);
            final Bitmap cachedBitmap = hasPhoto ? ImageCache.INSTANCE.getBitmapFromMemCache(model.getPhotoId()) : null;
            if (cachedBitmap != null)
                viewHolder.friendProfileCircularContactView.setImageBitmap(cachedBitmap);
            else {
                final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position
                        % PHOTO_TEXT_BACKGROUND_COLORS.length];
                if (TextUtils.isEmpty(model.getName()))
                    viewHolder.friendProfileCircularContactView.setImageResource(R.drawable.icon_add_ppl,
                            backgroundColorToUse);
                else {
                    final String characterToShow = TextUtils.isEmpty(displayName) ? "" : displayName.substring(0, 1).toUpperCase(Locale.getDefault());
                    viewHolder.friendProfileCircularContactView.setTextAndBackgroundColor(characterToShow, backgroundColorToUse);
                }
                if (hasPhoto) {
                    viewHolder.updateTask = new AsyncTaskEx<Void, Void, Bitmap>() {

                        @Override
                        public Bitmap doInBackground(final Void... params) {
                            if (isCancelled())
                                return null;
                            final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(context, model.getPhotoId(), CONTACT_PHOTO_IMAGE_SIZE);
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
                            ImageCache.INSTANCE.addBitmapToCache(model.getPhotoId(), result);
                            viewHolder.friendProfileCircularContactView.setImageBitmap(result);
                        }
                    };
                    mAsyncTaskThreadPool.executeAsyncTask(viewHolder.updateTask);
                }
            }
        }
        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }


    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();


            if (mOriginalValues == null) {
                mOriginalValues = new ArrayList<ContactModel>(fullList);
            }

            List<ContactModel> result;
            if (prefix == null || prefix.length() <= 0)
                result = new ArrayList<ContactModel>(mOriginalValues);
            else {
                result = new ArrayList<ContactModel>();
                for (int i = 0; i < mOriginalValues.size(); i++) {
                    String number = mOriginalValues.get(i).getNumber();
                    number = number.replace(" ", "");
                    if (prefix.length() > 0 && number.contains(prefix.toString()))
                        result.add(mOriginalValues.get(i));
                }
            }
            results.values = result;
            results.count = result.size();
            return results;
        }


        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            if (results.values != null) {
                fullList = (ArrayList<ContactModel>) results.values;
            } else {
                fullList = new ArrayList<ContactModel>();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
