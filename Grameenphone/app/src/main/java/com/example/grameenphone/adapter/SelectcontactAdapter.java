package com.example.grameenphone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.grameenphone.fragments.ContactsDetails;
import com.example.grameenphone.fragments.Favorites;

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
                Favorites art=new Favorites();
                return art;

            case 1:
                ContactsDetails dscn=new ContactsDetails();
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
