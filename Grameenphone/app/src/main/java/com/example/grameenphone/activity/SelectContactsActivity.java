package com.example.grameenphone.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.grameenphone.R;
import com.example.grameenphone.adapter.SelectcontactAdapter;
import com.example.grameenphone.tabs.SlidingTabLayout;

public class SelectContactsActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener {
    private ViewPager pager;
    private SlidingTabLayout tabs;
    private Context context;
    private SearchView search;
    ActionBar bar;
    ImageButton back_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selct_contacts);

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
        back_icon= (ImageButton) findViewById(R.id.back_btn);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    MenuItem searchItem;

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the parent_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selct_contacts, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        search = (SearchView) MenuItemCompat.getActionView(searchItem);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setSubmitButtonEnabled(false);
        search.setIconified(false);
        search.setOnQueryTextListener(this);

        ImageView closeButton = (ImageView) search.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");
            }
        });
        ImageView goButton = (ImageView) search.findViewById(R.id.search_go_btn);
        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "go", Toast.LENGTH_LONG).show();
            }
        });
        ImageView magButton = (ImageView) search.findViewById(R.id.search_mag_icon);
        magButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "mag", Toast.LENGTH_LONG).show();
            }
        });
        final MenuItem micItem = menu.findItem(R.id.voice_add);
        micItem.setVisible(false);

        //final MenuItem menuItem = menu.findItem(R.id.menu_edit);
        //menuItem.setVisible(true);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // menuItem.setVisible(false);
                micItem.setVisible(true);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //menuItem.setVisible(true);
                micItem.setVisible(false);

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.voice_add) {
            Toast.makeText(context, "Voice Search", Toast.LENGTH_LONG).show();
            startVoiceRecognitionActivity();
            return true;
        }

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // adapter.feed.filterData(newText);
        return false;
    }

    public void searchviewHideOrShow(int value) {
        if (value == 1) {
            searchItem.setVisible(true);
        } else {
            searchItem.setVisible(false);
            searchItem.collapseActionView();
        }
    }

    private final static int REQUEST_CODE = 1;

    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now");
        startActivityForResult(intent, REQUEST_CODE);
    }
}
