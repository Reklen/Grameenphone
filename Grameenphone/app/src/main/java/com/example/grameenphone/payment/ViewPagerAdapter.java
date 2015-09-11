package com.example.grameenphone.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Rajkiran on 7/3/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOTabs;
    int type;

    Bundle bundle;

    public ViewPagerAdapter(FragmentManager fm, CharSequence Titles[], int NumOfTabs , int type) {
        super(fm);
        this.Titles=Titles;
        this.NumbOTabs=NumOfTabs;
        this.type=type;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                bundle = new Bundle();
                bundle.putInt("type",type);
                ElectricityTab tab = ElectricityTab.newInstance(bundle);

                return tab;
            case 1:
                return new GasTab();
            case 2:
                return new InternetTab();
            case 3:
                return new InsuranceTab();
            case 4:
                return new TicketingTab();
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
