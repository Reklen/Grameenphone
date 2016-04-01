package com.cc.grameenphone.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cc.grameenphone.fragments.OtherPaymentElectricityFragment;
import com.cc.grameenphone.fragments.OtherPaymentGasFragment;
import com.cc.grameenphone.fragments.OtherPaymentInternetFragment;
import com.cc.grameenphone.fragments.OtherPaymentTicketingFragment;
import com.cc.grameenphone.fragments.OtherPaymentWaterFragment;

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
                tabFragment = OtherPaymentElectricityFragment.newInstance(bundle);
                return tabFragment;
            case 1:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = OtherPaymentGasFragment.newInstance(bundle);

                return tabFragment;
            case 2:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = OtherPaymentWaterFragment.newInstance(bundle);

                return tabFragment;
            case 3:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = OtherPaymentInternetFragment.newInstance(bundle);

                return tabFragment;
            case 4:

                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = OtherPaymentTicketingFragment.newInstance(bundle);

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
