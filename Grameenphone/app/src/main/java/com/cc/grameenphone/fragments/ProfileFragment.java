package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rajkiran on 09/09/15.
 */
public class ProfileFragment extends Fragment {
    @InjectView(R.id.first_name)
    EditText firstName;
    @InjectView(R.id.firstNameTextInputLayout)
    TextInputLayout firstNameTextInputLayout;
    @InjectView(R.id.last_name)
    EditText lastName;
    @InjectView(R.id.lastNameTextInputLayout)
    TextInputLayout lastNameTextInputLayout;
    @InjectView(R.id.email_name)
    EditText emailName;
    @InjectView(R.id.emailTextInputLayout)
    TextInputLayout emailTextInputLayout;
    @InjectView(R.id.national_id)
    EditText nationalId;
    @InjectView(R.id.nationalTextInputLayout)
    TextInputLayout nationalTextInputLayout;
    @InjectView(R.id.dob)
    EditText dob;
    @InjectView(R.id.dobTextInputLayout)
    TextInputLayout dobTextInputLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment_layout, container, false);


        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
