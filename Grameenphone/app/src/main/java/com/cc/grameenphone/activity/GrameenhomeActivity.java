package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.cc.grameenphone.R;
import com.cc.grameenphone.fragments.DemoFragment;
import com.cc.grameenphone.fragments.HomeFragment;
import com.cc.grameenphone.fragments.ManageFavoriteFragment;
import com.cc.grameenphone.fragments.PinChangeFragment;
import com.cc.grameenphone.fragments.ProfileFragment;
import com.cc.grameenphone.fragments.TermsConditionFragment;
import com.cc.grameenphone.utils.PreferenceManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GrameenHomeActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container_body)
    FrameLayout containerBody;
    @InjectView(R.id.navigation_view)
    NavigationView navigationView;
    @InjectView(R.id.drawer)
    DrawerLayout drawerLayout;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;

    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.icon2)
    ImageButton icon2;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grameenhome);
        ButterKnife.inject(this);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        setSupportActionBar(toolbar);
        preferenceManager = new PreferenceManager(GrameenHomeActivity.this);
        fragment = new HomeFragment();
        getSupportActionBar().setTitle("Home");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        fragment = new HomeFragment();
                        getSupportActionBar().setTitle("Home");
                        icon1.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_2:
                        fragment = new ProfileFragment();
                        getSupportActionBar().setTitle("Profile");
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_edit));
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_3:
                        fragment = new ManageFavoriteFragment();
                        getSupportActionBar().setTitle("Manage Favorites");
                        icon1.setVisibility(View.GONE);
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_4:
                        fragment = new PinChangeFragment();
                        getSupportActionBar().setTitle("Pin Change");
                        icon1.setVisibility(View.GONE);
                        icon2.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_5:
                        fragment = new DemoFragment();
                        getSupportActionBar().setTitle("Demo");
                        icon1.setVisibility(View.GONE);
                        icon2.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_6:
                        fragment = new TermsConditionFragment();
                        getSupportActionBar().setTitle("Terms & Condition");
                        icon1.setVisibility(View.GONE);
                        icon2.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_7:
                        preferenceManager.setAuthToken("");
                        startActivity(new Intent(GrameenHomeActivity.this, LoginActivity.class));
                        finish();
                        //fragment = new LogoutFragment();
                        /*getSupportActionBar().setTitle("Logout");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();*/
                        return true;
                    default:
                        return true;

                }

            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(GrameenHomeActivity.this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }


    @OnClick(R.id.icon2)
    public void clickIcon2() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_body);

        if (f instanceof ManageFavoriteFragment) {
            startActivity(new Intent(GrameenHomeActivity.this, AddFavoriteContactsActivity.class));
        }
        if (f instanceof ProfileFragment) {
            //startActivity(new Intent(GrameenHomeActivity.this, EditProfileActivity.class));
        }
    }

    @OnClick(R.id.icon1)
    public void clickIcon1() {

    }
}
