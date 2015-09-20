package com.cc.grameenphone.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cc.grameenphone.fragments.ElectricityTabFragment;
import com.cc.grameenphone.fragments.GasTabFragment;
import com.cc.grameenphone.fragments.InsuranceTabFragment;
import com.cc.grameenphone.fragments.InternetTabFragment;
import com.cc.grameenphone.fragments.TicketingTabFragment;

/**
 * Created by Rajkiran on 7/3/2015.
 */
public class BillPaymentViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOTabs;
    int type;
    Fragment tabFragment;
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
                tabFragment = ElectricityTabFragment.newInstance(bundle);

                return tabFragment;
            case 1:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = GasTabFragment.newInstance(bundle);

                return tabFragment;
            case 2:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = InsuranceTabFragment.newInstance(bundle);

                return tabFragment;
            case 3:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = TicketingTabFragment.newInstance(bundle);

                return tabFragment;
            case 4:

                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = InternetTabFragment.newInstance(bundle);

                return tabFragment;
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
