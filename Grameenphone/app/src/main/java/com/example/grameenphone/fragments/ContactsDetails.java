package com.example.grameenphone.fragments;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.grameenphone.R;
import com.example.grameenphone.utils.CircularContactView;
import com.example.grameenphone.utils.ContactImageUtil;
import com.example.grameenphone.utils.ContactsQuery;
import com.example.grameenphone.utils.ImageCache;
import com.example.grameenphone.utils.async_task_thread_pool.AsyncTaskEx;
import com.example.grameenphone.utils.async_task_thread_pool.AsyncTaskThreadPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import lb.library.PinnedHeaderListView;
import lb.library.SearchablePinnedHeaderListViewAdapter;
import lb.library.StringArrayAlphabetIndexer;
/**
 * Created by rajkiran on 09/09/15.
 */
public class ContactsDetails extends Fragment {
    private LayoutInflater mInflater;
    private PinnedHeaderListView mListView;
    private ContactsAdapter mAdapter;
    ContextWrapper contextWrapper;
    public ContactsDetails() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contactspage_layout, container, false);
        setHasOptionsMenu(true);
        contextWrapper= new ContextWrapper(getActivity());
        mInflater=LayoutInflater.from(getActivity());
        final ArrayList<Contact> contacts=getContacts();
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                char lhsFirstLetter = TextUtils.isEmpty(lhs.displayName) ? ' ' : lhs.displayName.charAt(0);
                char rhsFirstLetter = TextUtils.isEmpty(rhs.displayName) ? ' ' : rhs.displayName.charAt(0);
                int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                if (firstLetterComparison == 0)
                    return lhs.displayName.compareTo(rhs.displayName);
                return firstLetterComparison;
            }
        });
        mListView=(PinnedHeaderListView)rootView.findViewById(android.R.id.list);
        mAdapter=new ContactsAdapter(contacts);

        int pinnedHeaderBackgroundColor=getResources().getColor(getResIdFromAttribute(getActivity(),android.R.attr.colorBackground));
        mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
        mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
        mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header,mListView,false));
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mAdapter);
        mListView.setEnableHeaderTransparencyChanges(false);

        // Inflate the layout for this fragment
        return rootView;
    }
    public static int getResIdFromAttribute(final Activity activity,final int attr)
    {
        if(attr==0)
            return 0;
        final TypedValue typedValue=new TypedValue();
        activity.getTheme().resolveAttribute(attr,typedValue,true);
        return typedValue.resourceId;
    }

    private ArrayList<Contact> getContacts()
    {
        if(checkContactsReadPermission())
        {
            Uri uri=ContactsQuery.CONTENT_URI;
            final Cursor cursor=getActivity().managedQuery(uri, ContactsQuery.PROJECTION,ContactsQuery.SELECTION,null,ContactsQuery.SORT_ORDER);
            if(cursor==null)
                return null;
            ArrayList<Contact> result=new ArrayList<>();
            while(cursor.moveToNext())
            {
                Contact contact=new Contact();
                contact.contactUri= ContactsContract.Contacts.getLookupUri(
                        cursor.getLong(ContactsQuery.ID),
                        cursor.getString(ContactsQuery.LOOKUP_KEY));
                contact.displayName=cursor.getString(ContactsQuery.DISPLAY_NAME);
                contact.photoId=cursor.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
                result.add(contact);
            }

            return result;
        }
        ArrayList<Contact> result=new ArrayList<>();
        Random r=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<1000;++i)
        {
            Contact contact=new Contact();
            sb.delete(0,sb.length());
            int strLength=r.nextInt(10)+1;
            for(int j=0;j<strLength;++j)
                switch(r.nextInt(3))
                {
                    case 0:
                        sb.append((char)('a'+r.nextInt('z'-'a')));
                        break;
                    case 1:
                        sb.append((char)('A'+r.nextInt('Z'-'A')));
                        break;
                    case 2:
                        sb.append((char)('0'+r.nextInt('9'-'0')));
                        break;
                }

            contact.displayName=sb.toString();
            result.add(contact);
        }
        return result;
    }

    private boolean checkContactsReadPermission()
    {
        String permission="android.permission.READ_CONTACTS";
        int res= contextWrapper.checkCallingOrSelfPermission(permission);
        return (res== PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mAdapter.mAsyncTaskThreadPool.cancelAllTasks(true);
    }

    private static class Contact
    {
        long contactId;
        Uri contactUri;
        String displayName;
        String photoId;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu,MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_home,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(final MenuItem item)
    {
        String url=null;
        switch(item.getItemId())
        {
            case R.id.menuItem_all_my_apps:
                url="https://play.google.com/store/apps/developer?id=AndroidDeveloperLB";
                break;
            case R.id.menuItem_all_my_repositories:
                url="https://github.com/AndroidDeveloperLB";
                break;
            case R.id.menuItem_current_repository_website:
                url="https://github.com/AndroidDeveloperLB/ListViewVariants";
                break;
        }
        if(url==null)
            return true;
        final Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
        return true;
    }

    // ////////////////////////////////////////////////////////////
    // ContactsAdapter //
    // //////////////////
    private class ContactsAdapter extends SearchablePinnedHeaderListViewAdapter<Contact>
    {
        private ArrayList<Contact> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        private final AsyncTaskThreadPool mAsyncTaskThreadPool=new AsyncTaskThreadPool(1,2,10);

        @Override
        public CharSequence getSectionTitle(int sectionIndex)
        {
            return ((StringArrayAlphabetIndexer.AlphaBetSection)getSections()[sectionIndex]).getName();
        }

        public ContactsAdapter(final ArrayList<Contact> contacts)
        {
            setData(contacts);
            PHOTO_TEXT_BACKGROUND_COLORS=getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE=getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<Contact> contacts)
        {
            this.mContacts=contacts;
            final String[] generatedContactNames=generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames,true));
        }

        private String[] generateContactNames(final List<Contact> contacts)
        {
            final ArrayList<String> contactNames=new ArrayList<String>();
            if(contacts!=null)
                for(final Contact contactEntity : contacts)
                    contactNames.add(contactEntity.displayName);
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position,final View convertView,final ViewGroup parent)
        {
            final ViewHolder holder;
            final View rootView;
            if(convertView==null)
            {
                holder=new ViewHolder();
                rootView=mInflater.inflate(R.layout.listview_item,parent,false);
                holder.friendProfileCircularContactView=(CircularContactView)rootView
                        .findViewById(R.id.listview_item__friendPhotoImageView);
                holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);
                holder.friendName=(TextView)rootView
                        .findViewById(R.id.listview_item__friendNameTextView);
                holder.headerView=(TextView)rootView.findViewById(R.id.header_text);
                rootView.setTag(holder);
            }
            else
            {
                rootView=convertView;
                holder=(ViewHolder)rootView.getTag();
            }
            final Contact contact=getItem(position);
            final String displayName=contact.displayName;
            holder.friendName.setText(displayName);
            boolean hasPhoto=!TextUtils.isEmpty(contact.photoId);
            if(holder.updateTask!=null&&!holder.updateTask.isCancelled())
                holder.updateTask.cancel(true);
            final Bitmap cachedBitmap=hasPhoto? ImageCache.INSTANCE.getBitmapFromMemCache(contact.photoId):null;
            if(cachedBitmap!=null)
                holder.friendProfileCircularContactView.setImageBitmap(cachedBitmap);
            else
            {
                final int backgroundColorToUse=PHOTO_TEXT_BACKGROUND_COLORS[position
                        %PHOTO_TEXT_BACKGROUND_COLORS.length];
                if(TextUtils.isEmpty(displayName))
                    holder.friendProfileCircularContactView.setImageResource(R.drawable.icon_invite,
                            backgroundColorToUse);
                else
                {
                    final String characterToShow=TextUtils.isEmpty(displayName)?"":displayName.substring(0,1).toUpperCase(Locale.getDefault());
                    holder.friendProfileCircularContactView.setTextAndBackgroundColor(characterToShow,backgroundColorToUse);
                }
                if(hasPhoto)
                {
                    holder.updateTask=new AsyncTaskEx<Void,Void,Bitmap>()
                    {

                        @Override
                        public Bitmap doInBackground(final Void... params)
                        {
                            if(isCancelled())
                                return null;
                            final Bitmap b= ContactImageUtil.loadContactPhotoThumbnail(getActivity(), contact.photoId, CONTACT_PHOTO_IMAGE_SIZE);
                            if(b!=null)
                                return ThumbnailUtils.extractThumbnail(b, CONTACT_PHOTO_IMAGE_SIZE,
                                        CONTACT_PHOTO_IMAGE_SIZE);
                            return null;
                        }

                        @Override
                        public void onPostExecute(final Bitmap result)
                        {
                            super.onPostExecute(result);
                            if(result==null)
                                return;
                            ImageCache.INSTANCE.addBitmapToCache(contact.photoId,result);
                            holder.friendProfileCircularContactView.setImageBitmap(result);
                        }
                    };
                    mAsyncTaskThreadPool.executeAsyncTask(holder.updateTask);
                }
            }
            bindSectionHeader(holder.headerView,null,position);
            return rootView;
        }

        @Override
        public boolean doFilter(final Contact item,final CharSequence constraint)
        {
            if(TextUtils.isEmpty(constraint))
                return true;
            final String displayName=item.displayName;
            return !TextUtils.isEmpty(displayName)&&displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<Contact> getOriginalList()
        {
            return mContacts;
        }


    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ViewHolder //
    // /////////////
    private static class ViewHolder
    {
        public CircularContactView friendProfileCircularContactView;
        TextView friendName, headerView;
        public AsyncTaskEx<Void,Void,Bitmap> updateTask;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
