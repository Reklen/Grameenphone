package com.cc.grameenphone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cc.grameenphone.fragments.ContactsDetailsFragment;
import com.cc.grameenphone.fragments.FavoritesFragment;

/**
 * Created by rajkiran on 09/09/15.
 */
public class SelectcontactAdapter extends FragmentPagerAdapter {

    String tabs[] = {"Favorites","Contacts"};
    public SelectcontactAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FavoritesFragment art=new FavoritesFragment();
                return art;

            case 1:
                ContactsDetailsFragment dscn=new ContactsDetailsFragment();
                return dscn;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }


    @Override
    public int getCount() {
        return 2;
    }
}
