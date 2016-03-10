package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.BalanceCommandModel;
import com.cc.grameenphone.api_models.BalanceEnquiryModel;
import com.cc.grameenphone.api_models.NotificationMessageModel;
import com.cc.grameenphone.api_models.OtherPaymentCompanyModel;
import com.cc.grameenphone.api_models.OtherPaymentModel;
import com.cc.grameenphone.api_models.ProfileModel;
import com.cc.grameenphone.async.CompaniesSaveDBTask;
import com.cc.grameenphone.async.SessionClearTask;
import com.cc.grameenphone.fragments.DemoFragment;
import com.cc.grameenphone.fragments.HomeFragment;
import com.cc.grameenphone.fragments.ManageFavoriteFragment;
import com.cc.grameenphone.fragments.PinChangeFragment;
import com.cc.grameenphone.fragments.ProfileFragment;
import com.cc.grameenphone.fragments.TermsConditionFragment;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.OtherPaymentApi;
import com.cc.grameenphone.interfaces.WalletBalanceInterface;
import com.cc.grameenphone.interfaces.WalletCheckApi;
import com.cc.grameenphone.utils.ConnectivityUtils;
import com.cc.grameenphone.utils.KeyboardUtil;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PreferenceManager;
import com.cc.grameenphone.views.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class HomeActivity extends BaseActivity implements WalletBalanceInterface {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.container_body)
    FrameLayout containerBody;
    @InjectView(R.id.navigation_view)
    NavigationView navigationView;
    @InjectView(R.id.drawer)
    DrawerLayout drawerLayout;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    @InjectView(R.id.toolbar_text)
    TextView toolbarTextView;
    @InjectView(R.id.icon1)
    ImageButton icon1;
    @InjectView(R.id.icon2)
    ImageButton icon2;
    PreferenceManager preferenceManager;
    @InjectView(R.id.walletLabel)
    TextView walletLabel;
    @InjectView(R.id.icon1Ripple)
    RippleView icon1Ripple;
    @InjectView(R.id.icon2Ripple)
    RippleView icon2Ripple;
    private String android_id;

    private WalletCheckApi walletCheckApi;
    MaterialDialog logoutDialog;
    private MaterialDialog walletBalanceDialog, sessionDialog, internetDialog;
    private OtherPaymentApi otherPaymentApi;
    private MaterialDialog errorDialog;
    private MaterialDialog exit_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grameenhome);
        ButterKnife.inject(this);
        init();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.commit();


        if (!preferenceManager.getCompaniesSavedFlag()) {
            Logger.d("shfjdhf", "fetching list");
            getOtherPaymentCompanies();

        }

        handleNavigationView();
        handleRipple();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletBalance();
    }


    private void handleNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        fragment = new HomeFragment();
                        //getSupportActionBar().setTitle("Home");
                        toolbarTextView.setText("Home");
                        icon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_wallet_balance));
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_notification));
                        walletLabel.setVisibility(View.VISIBLE);
                        icon1Ripple.setVisibility(View.VISIBLE);
                        icon2Ripple.setVisibility(View.VISIBLE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_2:
                        fragment = new ProfileFragment();
                        //   getSupportActionBar().setTitle("Profile");
                        toolbarTextView.setText("Profile");
                        icon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_edit));
                        walletLabel.setVisibility(View.GONE);
                        icon1Ripple.setVisibility(View.VISIBLE);
                        icon2Ripple.setVisibility(View.VISIBLE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_3:
                        Bundle b = new Bundle();
                        b.putBoolean("isHome", true);
                        fragment = ManageFavoriteFragment.newInstance(b);
                        //  getSupportActionBar().setTitle("Manage Favorites");
                        toolbarTextView.setText("Manage Favorites");
                        // icon1.setVisibility(View.GONE);
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_add));
                        icon1Ripple.setVisibility(View.GONE);
                        icon2Ripple.setVisibility(View.VISIBLE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_4:
                        fragment = new PinChangeFragment();
                        // getSupportActionBar().setTitle("Pin Change");
                        toolbarTextView.setText("Pin Change");
                        icon1Ripple.setVisibility(View.GONE);
                        icon2Ripple.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_5:
                        fragment = new DemoFragment();
                        // getSupportActionBar().setTitle("Demo");
                        toolbarTextView.setText("Demo");
                       /* icon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_wallet_balance));
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_notification));*/
                        icon1Ripple.setVisibility(View.GONE);
                        icon2Ripple.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_6:
                        fragment = new TermsConditionFragment();
                        // getSupportActionBar().setTitle("Terms & Condition");
                        toolbarTextView.setText("Terms & Condition");
                        /*icon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_wallet_balance));
                        icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_notification));*/
                        icon1Ripple.setVisibility(View.GONE);
                        icon2Ripple.setVisibility(View.GONE);
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container_body, fragment);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.navigation_item_7:

                        logoutDialog = new MaterialDialog(HomeActivity.this);
                        logoutDialog.setTitle("Logout");
                        logoutDialog.setMessage("Are you sure ?");
                        logoutDialog.setPositiveButton("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SessionClearTask sessionClearTask = new SessionClearTask(HomeActivity.this, true);
                                sessionClearTask.execute();
                                finish();

                            }
                        });
                        logoutDialog.setNegativeButton("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                logoutDialog.dismiss();
                            }
                        });

                        logoutDialog.show();


                        return true;
                    default:
                        return true;

                }

            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                KeyboardUtil.hideKeyboard(HomeActivity.this);
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                KeyboardUtil.hideKeyboard(HomeActivity.this);
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }

    int unreadNotifications;

    private void init() {
        unreadNotifications = 0;
        internetDialog = new MaterialDialog(HomeActivity.this);
        internetDialog.setMessage("No Internet connection , please connect and retry");
        internetDialog.setCanceledOnTouchOutside(false);
        internetDialog.setPositiveButton("Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetDialog.dismiss();
                finish();
            }
        });
        internetDialog.setNegativeButton("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetDialog.dismiss();
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        if (!ConnectivityUtils.isConnected(HomeActivity.this)) {
            internetDialog.show();
        }
        preferenceManager = new PreferenceManager(HomeActivity.this);
        fragment = new HomeFragment();
        toolbarTextView.setText("Home");
        new RushSearch()
                .find(NotificationMessageModel.class, new RushSearchCallback<NotificationMessageModel>() {
                    @Override
                    public void complete(final List<NotificationMessageModel> listDB) {
                        for (NotificationMessageModel model : listDB)
                            if (model.isRead())
                                unreadNotifications++;
                    }
                });
    }

    private void getOtherPaymentCompanies() {
        otherPaymentApi = ServiceGenerator.createService(OtherPaymentApi.class);
        android_id = Settings.Secure.getString(HomeActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CTCMPLREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("getOtherPaymentCompanies ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            otherPaymentApi.fetchCompanies(in, new Callback<OtherPaymentModel>() {
                @Override
                public void success(OtherPaymentModel otherPaymentModel, Response response) {

                    if (otherPaymentModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                        OtherPaymentModel model = otherPaymentModel;
                        List<OtherPaymentCompanyModel> companiesList = model.getCOMMAND().getCOMPANYDET();
                        CompaniesSaveDBTask companiesSaveDBTask = new CompaniesSaveDBTask(getApplicationContext(), companiesList);
                        companiesSaveDBTask.execute();
                    } else {
                        Logger.e("getOtherPaymentCompanies", otherPaymentModel.getCOMMAND().getTXNSTATUS() + " " + otherPaymentModel.getCOMMAND().toString());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("getOtherPaymentCompanies", error.getMessage() + "");
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void handleRipple() {
        icon2Ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_body);

                if (f instanceof ManageFavoriteFragment) {

                    startActivity(new Intent(HomeActivity.this, AddFavoriteContactsActivity.class));

                }
                if (f instanceof ProfileFragment) {
                    ProfileModel profileModel = ((ProfileFragment) f).getProfileModel();

                    startActivity(new Intent(HomeActivity.this, EditProfileActivity.class).putExtra("profileObj", profileModel));

                }
                if (f instanceof HomeFragment) {

                    startActivity(new Intent(HomeActivity.this, NotificationActivity.class));

                }
            }
        });
        icon1Ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                walletBalanceDialog = new MaterialDialog(HomeActivity.this);
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_body);
                if (f instanceof HomeFragment) {
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
                if (f instanceof ProfileFragment) {

                    fragment = new ProfileFragment();
                    //   getSupportActionBar().setTitle("Profile");
                    toolbarTextView.setText("Profile");
                    icon1.setImageDrawable(getResources().getDrawable(R.drawable.icon_refresh));
                    icon2.setImageDrawable(getResources().getDrawable(R.drawable.icon_edit));
                    walletLabel.setVisibility(View.GONE);
                    icon1Ripple.setVisibility(View.VISIBLE);
                    icon2Ripple.setVisibility(View.VISIBLE);
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.commit();

                }

            }
        });
    }


    private void getWalletBalance() {
        walletCheckApi = ServiceGenerator.createService(WalletCheckApi.class);
        android_id = Settings.Secure.getString(HomeActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", preferenceManager.getMSISDN());
            innerObject.put("TYPE", "CBEREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("wallet request ", jsonObject.toString());
            String json = jsonObject.toString();
            TypedInput in = new TypedByteArray("application/json", json.getBytes("UTF-8"));
            if (preferenceManager.getWalletBalance().isEmpty())
                walletCheckApi.checkBalance(in, new Callback<BalanceEnquiryModel>() {
                    @Override
                    public void success(BalanceEnquiryModel balanceEnquiryModel, Response response) {
                        if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("200")) {
                            Logger.d("Balance", balanceEnquiryModel.toString());
                            walletLabel.setText("  ৳ " + balanceEnquiryModel.getCOMMAND().getBALANCE());
                            preferenceManager.setWalletBalance(balanceEnquiryModel.getCOMMAND().getBALANCE());
                            preferenceManager.setWalletMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE());
                            walletLabel.setTag(balanceEnquiryModel);
                        } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA907")) {
                            Logger.d("Balance", balanceEnquiryModel.toString());
                            sessionDialog = new MaterialDialog(HomeActivity.this);
                            sessionDialog.setMessage("Session expired , please login again");
                            sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SessionClearTask sessionClearTask = new SessionClearTask(HomeActivity.this, true);
                                    sessionClearTask.execute();

                                }
                            });
                            sessionDialog.setCanceledOnTouchOutside(false);
                            sessionDialog.show();
                        } else if (balanceEnquiryModel.getCOMMAND().getTXNSTATUS().equalsIgnoreCase("MA903")) {
                            Logger.d("Balance", balanceEnquiryModel.toString());
                            sessionDialog = new MaterialDialog(HomeActivity.this);
                            sessionDialog.setMessage("Session expired , please login again");
                            sessionDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SessionClearTask sessionClearTask = new SessionClearTask(HomeActivity.this, true);
                                    sessionClearTask.execute();

                                }
                            });
                            sessionDialog.setCanceledOnTouchOutside(false);
                            sessionDialog.show();
                        } else {
                            errorDialog = new MaterialDialog(HomeActivity.this);
                            errorDialog.setMessage(balanceEnquiryModel.getCOMMAND().getMESSAGE() + "");
                            errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    errorDialog.dismiss();
                                }
                            });
                            errorDialog.show();
                            Logger.d("Balance", balanceEnquiryModel.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Logger.e("Balance", error.getMessage());
                    }
                });
            else {
                walletLabel.setText("  ৳ " + preferenceManager.getWalletBalance());
                BalanceEnquiryModel balanceEnquiryModel = new BalanceEnquiryModel();
                BalanceCommandModel balanceCommandModel = new BalanceCommandModel();
                balanceCommandModel.setMESSAGE(preferenceManager.getWalletMessage());
                balanceEnquiryModel.setCOMMAND(balanceCommandModel);
                walletLabel.setTag(balanceEnquiryModel);
            }
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Logger.d("Return Contact", "contacts " + data.getExtras().getString(Constants.RETURN_RESULT));
    }

    @Override
    public void fetchBalanceAgain() {
        Logger.d("WalletCheck ", "again 2");

        walletLabel.setText("  ৳ ");
        preferenceManager.setWalletBalance("");
        getWalletBalance();
    }

    @Override
    public void onBackPressed() {
        if (getVisibleFragment() instanceof HomeFragment) {
            exit_dialog = new MaterialDialog(HomeActivity.this);
            exit_dialog.setTitle("Exit App");
            exit_dialog.setMessage("Are you sure, want to exit the app ?");
            exit_dialog.setPositiveButton("Exit", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    exit_dialog.dismiss();
                    finish();
                }
            });
            exit_dialog.setNegativeButton("Cancel", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exit_dialog.dismiss();

                }
            });
            exit_dialog.show();


        } else {
            fragment = new HomeFragment();
            toolbarTextView.setText("Home");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }


    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}
