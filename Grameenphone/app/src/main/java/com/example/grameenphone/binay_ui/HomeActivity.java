package com.example.grameenphone.binay_ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.grameenphone.R;

public class HomeActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(HomeActivity.this);
        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomePage();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new ProFragment();
                title = "Pro";
                break;
            case 2:
                fragment = new ManageFavorite();
                title ="Manage Favorite";
                break;
            case 3:
                fragment = new PinChange();
                title = "Pin Change";
                break;
            case 4:
                fragment = new DemoFragment();
                title = "Demo";
                break;
            case 5:
                fragment = new TermsCondition();
                title = "terms condition";
                break;
            case 6:
                fragment = new Logout();
                title = "Logout";
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

        }
    }
}