package com.example.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.grameenphone.R;
import com.example.grameenphone.fragments.DemoFragment;
import com.example.grameenphone.fragments.HomePage;
import com.example.grameenphone.fragments.Logout;
import com.example.grameenphone.fragments.ManageFavorite;
import com.example.grameenphone.fragments.PinChange;
import com.example.grameenphone.fragments.ProfileFragment;
import com.example.grameenphone.fragments.TermsCondition;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GrameenhomeActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grameenhome);
        ButterKnife.inject(this);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        setSupportActionBar(toolbar);

        fragment = new HomePage();
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
                        fragment = new HomePage();
                        getSupportActionBar().setTitle("Home");
                        icon1.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_2:
                        fragment = new ProfileFragment();
                        getSupportActionBar().setTitle("Profile");
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_border_color_white));
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_3:
                        fragment = new ManageFavorite();
                        getSupportActionBar().setTitle("Manage Favorites");
                        icon1.setVisibility(View.GONE);
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_18dp));
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_4:
                        fragment = new PinChange();
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
                        fragment = new TermsCondition();
                        getSupportActionBar().setTitle("Terms & Condition");
                        icon1.setVisibility(View.GONE);
                        icon2.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_7:
                        fragment = new Logout();
                        getSupportActionBar().setTitle("Logout");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    default:
                        return true;

                }

            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(GrameenhomeActivity.this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

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

        if (f instanceof ManageFavorite) {
            startActivity(new Intent(GrameenhomeActivity.this, AddFavoriteContactsActivity.class));
        }
        if( f instanceof ProfileFragment){
            startActivity(new Intent(GrameenhomeActivity.this, EditProfileActivity.class));
        }
    }

    @OnClick(R.id.icon1)
    public void clickIcon1() {

    }
}
