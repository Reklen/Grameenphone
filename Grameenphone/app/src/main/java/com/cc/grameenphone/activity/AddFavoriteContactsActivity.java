package com.cc.grameenphone.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.utils.CircularContactView;
import com.cc.grameenphone.utils.ContactImageUtil;
import com.cc.grameenphone.utils.ContactsQuery;
import com.cc.grameenphone.utils.ImageCache;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.async_task_thread_pool.AsyncTaskEx;
import com.cc.grameenphone.utils.async_task_thread_pool.AsyncTaskThreadPool;
import com.cc.grameenphone.views.MySearchView;
import com.cc.grameenphone.views.RippleView;
import com.cc.grameenphone.views.lv.PinnedHeaderListView;
import com.cc.grameenphone.views.lv.SearchablePinnedHeaderListViewAdapter;
import com.cc.grameenphone.views.lv.StringArrayAlphabetIndexer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.uk.rushorm.core.RushCallback;
import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;

public class AddFavoriteContactsActivity extends AppCompatActivity {
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar_container)
    RelativeLayout toolbarContainer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(android.R.id.list)
    PinnedHeaderListView list;
    private LayoutInflater mInflater;
    private PinnedHeaderListView mListView;
    private ContactsAdapter mAdapter;
    ContextWrapper contextWrapper;
    TextView tooltext;
    ImageView back_icon;
    private ProgressDialog loadingDialog;
    boolean isNameFoundCheck;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_favorite_contacts);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        back_icon = (ImageView) findViewById(R.id.image_back);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        contextWrapper = new ContextWrapper(this);
        mInflater = LayoutInflater.from(this);
        final ArrayList<Contact> contacts = getContacts();
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
        mListView = (PinnedHeaderListView) findViewById(android.R.id.list);
        mAdapter = new ContactsAdapter(contacts);
        loadingDialog = new ProgressDialog(AddFavoriteContactsActivity.this);
        loadingDialog.setMessage("Loading..");
        int pinnedHeaderBackgroundColor = getResources().getColor(R.color.transparent);
        mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
        mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
        mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mListView, false));
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mAdapter);
        mListView.setEnableHeaderTransparencyChanges(false);
        Logger.d("Textis", "Add fav");
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 isNameFoundCheck = false;
                                                 final Contact contact = ((ContactsAdapter) mListView.getAdapter()).getItem(position);
                                                 final ContactModel contactModel = new ContactModel();
                                                 contactModel.setName(contact.displayName);
                                                 contactModel.setNumber(contact.number);
                                                 contactModel.setPhotoId(contact.photoId);
                                                 loadingDialog.show();
                                                 new RushSearch()
                                                         .find(ContactModel.class, new RushSearchCallback<ContactModel>() {
                                                                     @Override
                                                                     public void complete(List<ContactModel> list) {
                                                                         for (ContactModel d : list) {
                                                                             if (d.getName() != null && d.getName().contains(contact.displayName)) {
                                                                                 isNameFoundCheck = true;
                                                                                 Logger.d("nme check", "found " + contact.displayName);
                                                                                 break;
                                                                             }

                                                                         }
                                                                         if (!isNameFoundCheck) {
                                                                             String num = contactModel.getNumber();
                                                                             num = num.replace(" ", "");
                                                                             contactModel.setNumber(num);
                                                                             contactModel.save(new RushCallback() {
                                                                                 @Override
                                                                                 public void complete() {
                                                                                     loadingDialog.cancel();
                                                                                     Logger.d("Contact adding to fav", contactModel.getId() + "");
                                                                                     finish();

                                                                                 }
                                                                             });
                                                                         } else

                                                                         {
                                                                             loadingDialog.cancel();
                                                                             Snackbar.make(findViewById(android.R.id.content), "This contact is already a favourite , go back ?", Snackbar.LENGTH_LONG)
                                                                                     .setAction("Ok", new View.OnClickListener() {
                                                                                         @Override
                                                                                         public void onClick(View v) {
                                                                                             finish();
                                                                                         }
                                                                                     })
                                                                                     .setActionTextColor(Color.GREEN)
                                                                                     .show();
                                                                         }
                                                                     }
                                                                 }

                                                         );

                                             }
                                         }

        );

        // Inflate the layout for this fragment
    }

    public void getFilterContacts(String searchText) {
        if (mAdapter != null) {
            Logger.d("Search Text", searchText);
            mAdapter.getFilter().filter(searchText);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_contacts, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = MySearchView.getSearchView(AddFavoriteContactsActivity.this, "");
        setupSearchView(searchItem);
       /* searchView = (SearchView) searchItem.getActionView();


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    private void setupSearchView(MenuItem searchItem) {
        searchItem.setActionView(mSearchView);
        SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //adapter.getFilter().filter(s);
                Logger.d("Text is", s);
                getFilterContacts(s);
                return true;
            }
        };
        mSearchView.setOnQueryTextListener(mOnQueryTextListener);
       /* SearchViewStyle.on(searchView);
        searchView.setIconifiedByDefault(true);
        // searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
        // Setting the textview default behaviour properties

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                if (item.isActionViewExpanded()) {
                    item.collapseActionView();
                    Logger.d(("Closing Group Search"));


                } else {
                    item.expandActionView();
                    Logger.d(("Opening Group Search"));
                }

                return true;
            default:
                return true;
        }
    }

    public static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0)
            return 0;
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }

    private ArrayList<Contact> getContacts() {
        if (checkContactsReadPermission()) {
            Uri uri = ContactsQuery.CONTENT_URI.buildUpon()
                    .appendQueryParameter(ContactsContract.REMOVE_DUPLICATE_ENTRIES, "1")
                    .build();

            final Cursor cursor = managedQuery(uri, ContactsQuery.PROJECTION, ContactsQuery.SELECTION, null, ContactsQuery.SORT_ORDER);
            if (cursor == null)
                return null;
            ArrayList<Contact> result = new ArrayList<>();
            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                contact.contactUri = ContactsContract.Contacts.getLookupUri(
                        cursor.getLong(ContactsQuery.ID),
                        cursor.getString(ContactsQuery.LOOKUP_KEY));
                contact.displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
                contact.photoId = cursor.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
                contact.number = cursor.getString(ContactsQuery.PHONE_NUMBER);
                result.add(contact);
            }

            return result;
        }
        ArrayList<Contact> result = new ArrayList<>();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; ++i) {
            Contact contact = new Contact();
            sb.delete(0, sb.length());
            int strLength = r.nextInt(10) + 1;
            for (int j = 0; j < strLength; ++j)
                switch (r.nextInt(3)) {
                    case 0:
                        sb.append((char) ('a' + r.nextInt('z' - 'a')));
                        break;
                    case 1:
                        sb.append((char) ('A' + r.nextInt('Z' - 'A')));
                        break;
                    case 2:
                        sb.append((char) ('0' + r.nextInt('9' - '0')));
                        break;
                }

            contact.displayName = sb.toString();
            result.add(contact);
        }
        return result;
    }

    private boolean checkContactsReadPermission() {
        String permission = "android.permission.READ_CONTACTS";
        int res = contextWrapper.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.mAsyncTaskThreadPool.cancelAllTasks(true);
    }

    public class Contact implements Serializable {
        long contactId;
        Uri contactUri;
        String displayName;
        String photoId;
        String number;

        public String getNumber() {
            return number;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        String url = null;
        switch (item.getItemId()) {
            case R.id.menuItem_all_my_apps:
                url = "https://play.google.com/store/apps/developer?id=AndroidDeveloperLB";
                break;
            case R.id.menuItem_all_my_repositories:
                url = "https://github.com/AndroidDeveloperLB";
                break;
            case R.id.menuItem_current_repository_website:
                url = "https://github.com/AndroidDeveloperLB/ListViewVariants";
                break;
        }
        if (url == null)
            return true;
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
        return true;
    }*/

    // ////////////////////////////////////////////////////////////
// ContactsAdapter //
// //////////////////
    private class ContactsAdapter extends SearchablePinnedHeaderListViewAdapter<Contact> {
        private ArrayList<Contact> mContacts;
        private ArrayList<Contact> dummyContactsList;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        private final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);

        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        @Override
        public Filter getFilter() {
             /*= new ArrayList<Contact>();*/
            Filter filter = new Filter() {

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    mContacts = (ArrayList<Contact>) results.values;
                    if (mContacts != null && mContacts.isEmpty()) {
                        Toast.makeText(AddFavoriteContactsActivity.this, "no contacts found", Toast.LENGTH_SHORT).show();
                    }
                    notifyDataSetChanged();
                    setHeaderViewVisible(false);

                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();

                    ArrayList<Contact> FilteredList = new ArrayList<Contact>();
                    if (dummyContactsList == null) {
                        dummyContactsList = new ArrayList<Contact>(mContacts);
                    }
                    if (constraint == null || constraint.length() == 0) {
                        results.count = dummyContactsList.size();
                        results.values = dummyContactsList;

                    } else {
                        constraint = constraint.toString().trim().toLowerCase(Locale.getDefault());
                        for (int i = 0; i < dummyContactsList.size(); i++) {
                            String name = dummyContactsList.get(i).getDisplayName();
                            String number = dummyContactsList.get(i).getNumber();
                            if (name.toLowerCase(Locale.getDefault()).contains(constraint.toString())
                                    || number.toLowerCase(Locale.getDefault()).contains(constraint.toString())) {
                                FilteredList.add(dummyContactsList.get(i));

                            }
                        }
                        results.count = FilteredList.size();
                        results.values = FilteredList;
                    }
                    return results;

                }

            };

            return filter;
        }

        public ContactsAdapter(final ArrayList<Contact> contacts) {
            setData(contacts);
            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<Contact> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<Contact> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final Contact contactEntity : contacts)
                    contactNames.add(contactEntity.displayName);
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            final View rootView;
            if (convertView == null) {
                holder = new ViewHolder();
                rootView = mInflater.inflate(R.layout.adapter_item_contact, parent, false);
                holder.friendProfileCircularContactView = (CircularContactView) rootView
                        .findViewById(R.id.listview_item__friendPhotoImageView);
                holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);
                holder.friendName = (TextView) rootView
                        .findViewById(R.id.listview_item__friendNameTextView);
                holder.friendNumber = (TextView) rootView.findViewById(R.id.listview_item__friendNumberTextView);
                holder.headerView = (TextView) rootView.findViewById(R.id.header_text);
                rootView.setTag(holder);
            } else {
                rootView = convertView;
                holder = (ViewHolder) rootView.getTag();
            }
            final Contact contact = getItem(position);
            final String displayName = contact.displayName;
            try {
                final String number = contact.number;
                holder.friendNumber.setText("" + number);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.friendName.setText(displayName);
            boolean hasPhoto = !TextUtils.isEmpty(contact.photoId);
            if (holder.updateTask != null && !holder.updateTask.isCancelled())
                holder.updateTask.cancel(true);
            final Bitmap cachedBitmap = hasPhoto ? ImageCache.INSTANCE.getBitmapFromMemCache(contact.photoId) : null;
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
                            final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(AddFavoriteContactsActivity.this, contact.photoId, CONTACT_PHOTO_IMAGE_SIZE);
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
                            ImageCache.INSTANCE.addBitmapToCache(contact.photoId, result);
                            holder.friendProfileCircularContactView.setImageBitmap(result);
                        }
                    };
                    mAsyncTaskThreadPool.executeAsyncTask(holder.updateTask);
                }
            }
            bindSectionHeader(holder.headerView, null, position);
            return rootView;
        }

        @Override
        public boolean doFilter(final Contact item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.displayName;
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<Contact> getOriginalList() {
            return mContacts;
        }

        @Override
        public Contact getItem(int position) {
            return mContacts.get(position);
        }
    }


    // /////////////////////////////////////////////////////////////////////////////////////
// ViewHolder //
// /////////////
    private static class ViewHolder {
        public CircularContactView friendProfileCircularContactView;
        TextView friendName, headerView, friendNumber;
        public AsyncTaskEx<Void, Void, Bitmap> updateTask;
    }


}
