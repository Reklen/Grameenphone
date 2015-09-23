package com.cc.grameenphone.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.SelectcontactAdapter;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.ToolBarUtils;
import com.cc.grameenphone.views.MySearchView;
import com.cc.grameenphone.views.RippleView;
import com.cc.grameenphone.views.tabs.SlidingTabLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SelectContactsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    @InjectView(R.id.back_btn)
    ImageButton backBtn;
    @InjectView(R.id.parenting_feed_heading_txt)
    TextView parentingFeedHeadingTxt;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.contacts_tabs)
    SlidingTabLayout contactsTabs;
    @InjectView(R.id.contacts_viewpager)
    ViewPager contactsViewpager;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    private Context context;
    SearchView searchView;
    private SearchView mSearchView;

    SelectcontactAdapter selectcontactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        ButterKnife.inject(this);
        setupToolBar();

    }

    private void setupToolBar() {

        int srcColor = 0xFFFFFFFF;
        ToolBarUtils.colorizeToolbar(toolbar, srcColor, SelectContactsActivity.this);
        setSupportActionBar(toolbar);
        selectcontactAdapter = new SelectcontactAdapter(getSupportFragmentManager());
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        contactsTabs.setDistributeEvenly(true);
        contactsTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {

                return getResources().getColor(R.color.textColorPrimary);
            }
        });
        contactsViewpager.setAdapter(selectcontactAdapter);
        contactsTabs.setViewPager(contactsViewpager);
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_contacts, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = MySearchView.getSearchView(SelectContactsActivity.this, "");
        setupSearchView(searchItem);
       /* searchView = (SearchView) searchItem.getActionView();


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
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

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);


        }
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
                if (contactsViewpager.getCurrentItem() == 1)
                    selectcontactAdapter.contactsDetailsFragment.getFilterContacts(s);
                else {
                    selectcontactAdapter.manageFavoriteFragment.getFilterContacts(s);
                }
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
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        return false;
    }
}
