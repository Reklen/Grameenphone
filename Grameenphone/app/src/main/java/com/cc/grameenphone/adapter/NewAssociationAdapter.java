package com.cc.grameenphone.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cc.grameenphone.fragments.NewAssociationElectricFragment;
import com.cc.grameenphone.fragments.NewAssociationGasFragment;
import com.cc.grameenphone.fragments.NewAssociationInsuranceFragment;
import com.cc.grameenphone.fragments.NewAssociationInternetFragment;
import com.cc.grameenphone.fragments.NewAssociationTicketingFragment;

/**
 * Created by rajkiran on 21/09/15.
 */
public class NewAssociationAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[];
    int NumbOTabs;
    int type;
    Fragment tabFragment;
    Bundle bundle;

    public NewAssociationAdapter(FragmentManager fm, CharSequence Titles[], int NumOfTabs, int type) {
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
                tabFragment = NewAssociationElectricFragment.newInstance(bundle);

                return tabFragment;
            case 1:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = NewAssociationGasFragment.newInstance(bundle);

                return tabFragment;
            case 2:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = NewAssociationInsuranceFragment.newInstance(bundle);

                return tabFragment;
            case 3:
                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = NewAssociationTicketingFragment.newInstance(bundle);

                return tabFragment;
            case 4:

                bundle = new Bundle();
                bundle.putInt("type", type);
                tabFragment = NewAssociationInternetFragment.newInstance(bundle);

                return tabFragment;
            default:
                return null;
        }
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
