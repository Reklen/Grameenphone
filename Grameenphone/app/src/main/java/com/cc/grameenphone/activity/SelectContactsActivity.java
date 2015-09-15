package com.cc.grameenphone.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.SelectcontactAdapter;
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
    private ViewPager pager;
    private SlidingTabLayout tabs;
    private Context context;
    SearchView searchView;
    ActionBar bar;
    ImageButton back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        bar = getSupportActionBar();
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
        back_icon = (ImageButton) findViewById(R.id.back_btn);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        searchView.setIconifiedByDefault(true);
        // searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        // | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
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
