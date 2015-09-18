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
import com.cc.grameenphone.utils.SearchViewStyle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        ButterKnife.inject(this);
        setupToolBar();

    }

    private void setupToolBar() {
        setSupportActionBar(toolbar);
        contactsTabs.setDistributeEvenly(true);
        contactsTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {

                return getResources().getColor(R.color.textColorPrimary);
            }
        });
        contactsViewpager.setAdapter(new SelectcontactAdapter(getSupportFragmentManager()));
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
        inflater.inflate(R.menu.dashboard, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
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
        SearchViewStyle.on(searchView);
        searchView.setIconifiedByDefault(true);
        // searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
