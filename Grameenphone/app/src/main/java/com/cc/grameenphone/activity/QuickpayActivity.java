package com.cc.grameenphone.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.fragments.BillPaymentFragment;
import com.cc.grameenphone.fragments.QuickPayFragment;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class QuickPayActivity extends AppCompatActivity implements QuickPayFragment.TopFragmentListener {

    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container)
    FrameLayout container;
    private WalletCheckApi walletCheckApi;
    private String android_id;
    private PreferenceManager preferenceManager;
    private MaterialDialog walletBalanceDialog;
    QuickPayFragment qf;
    BillPaymentFragment bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickpay);

        ButterKnife.inject(this);
        setupToolbar();
        preferenceManager = new PreferenceManager(QuickPayActivity.this);


        qf = new QuickPayFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, qf);
        transaction.commit();
        getWalletBalance();
    }

    private void setupToolbar() {
        icon1Ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                walletBalanceDialog = new MaterialDialog(QuickPayActivity.this);

                BalanceEnquiryModel md = (BalanceEnquiryModel) walletLabel.getTag();
                if (md != null) {
                    walletBalanceDialog.setMessage(md.getCOMMAND().getMESSAGE());
                    walletBalanceDialog.setPositiveButton("Ok", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            walletBalanceDialog.dismiss();
                        }
                    });
                    walletBalanceDialog.show();
                }


            }
        });

        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                final FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() != 0) {
                    fragmentManager.popBackStackImmediate();
                } else {
                    finish();
                }
            }
        });
    }

    //Need to edit! The paycode is passed over here!
    public void onclickQuickPay_QuickPayFragment(String paycode) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack("qf");
        bf = new BillPaymentFragment();
        transaction.replace(R.id.container, bf);
        transaction.commit();
    }

    private void getWalletBalance() {

        walletCheckApi = ServiceGenerator.createService(WalletCheckApi.class);
        android_id = Settings.Secure.getString(QuickPayActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CBEREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("wallet request ", jsonObject.toString());
            walletCheckApi.checkBalance(jsonObject, new Callback<BalanceEnquiryModel>() {
                @Override
                public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {
                    if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        Logger.d("Balance", balanceEnquiryModel.toString());
                        walletLabel.setText("  ৳ " + balanceEnquiryModel.getCOMMAND().getBALANCE());
                        walletLabel.setTag(balanceEnquiryModel);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("Balance", error.getMessage());
                }
            });
        } catch (JSONException e) {

        }
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            finish();
        }
    }
}
