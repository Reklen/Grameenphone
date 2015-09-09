package com.example.grameenphone.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.example.grameenphone.R;
import com.example.grameenphone.adapter.SelectcontactAdapter;
import com.example.grameenphone.tabs.SlidingTabLayout;

public class SelctContacts extends ActionBarActivity {
    private ViewPager pager;
    private SlidingTabLayout tabs;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selct_contacts);
        tabs = (SlidingTabLayout) findViewById(R.id.contacts_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {

                return getResources().getColor(R.color.textColorPrimary);
            }
        });
        pager = (ViewPager) findViewById(R.id.contacts_viewpager);
        pager.setAdapter(new SelectcontactAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
    }

   /* public void onToolBarSearch(View view) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(searchableInfo);
    }
    protected void onNewIntent(Intent intent) {
        if (ContactsContract.Intents.SEARCH_SUGGESTION_CLICKED.equals(intent.getAction())) {
            //handles suggestion clicked query
            String displayName = getDisplayNameForContact(intent);
            //resultText.setText(displayName);
        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
            //resultText.setText("should search for query: '" + query + "'...");
        }
    }
    private String getDisplayNameForContact(Intent intent) {
        Cursor phoneCursor = getContentResolver().query(intent.getData(), null, null, null, null);
        phoneCursor.moveToFirst();
        int idDisplayName = phoneCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        String name = phoneCursor.getString(idDisplayName);
        phoneCursor.close();
        return name;
    }*/
}
