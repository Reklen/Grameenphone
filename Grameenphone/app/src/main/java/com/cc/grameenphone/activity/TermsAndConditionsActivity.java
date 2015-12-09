package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.fragments.TermsConditionFragment;
import com.cc.grameenphone.views.RippleView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by aditlal on 07/12/15.
 */
public class TermsAndConditionsActivity extends AppCompatActivity {
    FragmentManager fm;
    @InjectView(R.id.image_icon_back)
    ImageView imageIconBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.text_tool)
    TextView textTool;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tocFrameLayout)
    FrameLayout tocFrameLayout;
    private FragmentTransaction ft;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toc);
        ButterKnife.inject(this);

        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        fm = getSupportFragmentManager();
        fragment = new TermsConditionFragment();
        ft = fm.beginTransaction();
        ft.replace(R.id.tocFrameLayout, fragment);
        ft.commit();
    }
}
