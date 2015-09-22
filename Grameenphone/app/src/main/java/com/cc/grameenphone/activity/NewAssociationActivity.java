package com.cc.grameenphone.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.NewAssociationAdapter;
import com.cc.grameenphone.api_models.CompanyListModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ManageAssociationApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;
import com.cc.grameenphone.views.tabs.SlidingTabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Rajkiran on 9/11/2015.
 */
public class NewAssociationActivity extends AppCompatActivity {


    NewAssociationAdapter newAssociationAdapter;

    CharSequence titles[] = {"ELECTRICITY", "GAS", "INSURANCE", "TICKETING", "INTERNET"};
    int numOfTabs = 5;
    Toolbar otherToolbar;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tabs)
    SlidingTabLayout tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    ManageAssociationApi associationApi;
    ProgressDialog loadingDialog;
    private String android_id;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_association);
        ButterKnife.inject(this);
        android_id = Settings.Secure.getString(NewAssociationActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferenceManager = new PreferenceManager(NewAssociationActivity.this);
        loadingDialog = new ProgressDialog(NewAssociationActivity.this);
        loadingDialog.setMessage("Loading ..");
        toolbarText.setText("New Association");
        associationApi = ServiceGenerator.createService(ManageAssociationApi.class);
        fetchList();
        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        newAssociationAdapter = new NewAssociationAdapter(getSupportFragmentManager(), titles, numOfTabs, 1);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(newAssociationAdapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        tabs.setViewPager(pager);


    }

    private void fetchList() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "FBILASCREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("sending json", jsonObject.toString());
            associationApi.fetchAssociaition(jsonObject, new Callback<CompanyListModel>() {
                @Override
                public void success(CompanyListModel companyListModel, Response response) {
                    Logger.d("Companyies ", companyListModel.toString());
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

