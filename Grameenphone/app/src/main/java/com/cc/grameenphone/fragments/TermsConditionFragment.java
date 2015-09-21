package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.binaryfork.spanny.Spanny;
import com.cc.grameenphone.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by rajkiran on 09/09/15.
 */
public class TermsConditionFragment extends Fragment {
    @InjectView(R.id.textView)
    TextView textView;

    public TermsConditionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);
        Spanny spanny = new Spanny("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vitae sodales metus. Proin ac felis tincidunt, aliquet sem sit amet, faucibus tellus. Nam aliquam pharetra lectus semper tristique. Vivamus fringilla felis velit, rhoncus tincidunt dui eleifend vel. Aenean eleifend gravida erat. Donec lacinia consequat eros et maximus. Suspendisse tincidunt lacus ac molestie dignissim. Pellentesque porttitor justo sit amet massa finibus, nec scelerisque eros congue. Suspendisse condimentum, diam nec placerat feugiat, nulla massa ultrices nunc, eu tincidunt tortor odio vestibulum lacus. Morbi pharetra nisl sit amet tellus posuere mollis. Nunc tempor porttitor fermentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque at leo id tortor eleifend maximus. Vivamus sollicitudin efficitur euismod. Quisque interdum consectetur sagittis.\n" +
                "\n" +
                "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent convallis risus enim, sit amet consectetur nunc vulputate a. Donec ac lectus vel elit ullamcorper vulputate at eu sapien. Praesent convallis luctus sem, sed lobortis ipsum maximus quis. Quisque est odio, facilisis quis finibus ut, convallis non nulla. Curabitur viverra tempus lorem id hendrerit. Suspendisse sed lectus posuere, vestibulum est et, tempor tortor.\n" +
                "\n" +
                "Cras finibus ornare auctor. Interdum et malesuada fames ac ante ipsum primis in faucibus. Curabitur eget faucibus quam, at maximus ipsum. Donec aliquet scelerisque vestibulum. Maecenas mi ex, scelerisque a sodales non, vehicula sed purus. Quisque pulvinar molestie leo rhoncus fermentum. Phasellus eget aliquam erat. Nulla maximus ex in dignissim malesuada. Sed tincidunt pretium dui id suscipit. Donec fermentum hendrerit ipsum, at convallis mi. Vivamus interdum consequat porttitor. Duis venenatis dictum nibh, at semper tortor pretium in. Vestibulum imperdiet, mi in egestas gravida, ante erat ornare nibh, eu placerat purus est sit amet nulla. Quisque dignissim vel massa vitae cursus.\n" +
                "\n" +
                "Quisque id luctus justo. Aliquam egestas feugiat aliquet. Integer eu consectetur nisi. Morbi eu metus purus. Nullam egestas ipsum non neque euismod fermentum. Fusce a malesuada justo. Aenean cursus varius feugiat. Aenean pellentesque erat ut dictum imperdiet. Pellentesque vitae eros libero.\n" +
                "\n" +
                "Aenean sed justo lacus. Nullam vel quam ex. Nam ultrices posuere nulla, id maximus nisi suscipit nec. Interdum et malesuada fames ac ante ipsum primis in faucibus. Praesent sagittis aliquam lectus, vitae lacinia urna porttitor nec. Etiam euismod est eu odio elementum, non facilisis lectus consectetur. Praesent sodales posuere dui, id fringilla sem. Aliquam tristique sapien eu libero venenatis, et ornare mi lacinia. Etiam libero massa, vestibulum at venenatis eget, gravida quis urna. Ut lobortis ultrices tincidunt. Nam suscipit posuere elit.");

        // Inflate the layout for this fragment
        ButterKnife.inject(this, rootView);
        textView.setText(spanny);
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