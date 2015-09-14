package com.cc.grameenphone.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cc.grameenphone.fragments.ElectricityTabFragments;
import com.cc.grameenphone.fragments.GasTabFragment;
import com.cc.grameenphone.fragments.InsuranceTabFragment;
import com.cc.grameenphone.fragments.InternetTabFragments;
import com.cc.grameenphone.fragments.TicketingTabFragment;

/**
 * Created by Rajkiran on 7/3/2015.
 */
public class BillPaymentViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOTabs;
    int type;

    Bundle bundle;

    public BillPaymentViewPagerAdapter(FragmentManager fm, CharSequence Titles[], int NumOfTabs, int type) {
        super(fm);
        this.Titles = Titles;
        this.NumbOTabs = NumOfTabs;
        this.type = type;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                bundle = new Bundle();
                bundle.putInt("type", type);
                ElectricityTabFragments tab = ElectricityTabFragments.newInstance(bundle);

                return tab;
            case 1:
                return new GasTabFragment();
            case 2:
                return new InternetTabFragments();
            case 3:
                return new InsuranceTabFragment();
            case 4:
                return new TicketingTabFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NumbOTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
}
