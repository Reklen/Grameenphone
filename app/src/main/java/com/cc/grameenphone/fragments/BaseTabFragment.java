package com.cc.grameenphone.fragments;

import android.support.v4.app.Fragment;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by aditlal on 19/09/15.
 */
public class BaseTabFragment extends Fragment {

    int type;
    MaterialDialog confirmDialog;


    public void addRadioButtons(int number) {

        for (int row = 0; row < 1; row++) {
            LinearLayout ll = new LinearLayout(getActivity());
            ll.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 1; i <= number; i++) {
                RadioButton rdbtn = new RadioButton(getActivity());
                rdbtn.setId((row * 2) + i);
                rdbtn.setText("Radio " + rdbtn.getId());
                ll.addView(rdbtn);
            }
            // ((ViewGroup) findViewById(R.id.radiogroup)).addView(ll);
        }

    }
}
