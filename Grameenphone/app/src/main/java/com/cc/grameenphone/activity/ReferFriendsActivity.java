package com.cc.grameenphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.grameenphone.R;
import com.cc.grameenphone.api_models.ReferFriendModel;
import com.cc.grameenphone.generator.ServiceGenerator;
import com.cc.grameenphone.interfaces.ReferFriendsApi;
import com.cc.grameenphone.utils.Constants;
import com.cc.grameenphone.utils.IntentUtils;
import com.cc.grameenphone.utils.Logger;
import com.cc.grameenphone.utils.PhoneUtils;
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

public class ReferFriendsActivity extends AppCompatActivity {

    @InjectView(R.id.backRipple)
    RippleView backRipple;
    @InjectView(R.id.refer_text)
    TextView referText;
    @InjectView(R.id.refer_code)
    TextView referCode;
    @InjectView(R.id.refer_text_main)
    TextView referTextMain;
    @InjectView(R.id.areaCode)
    EditText areaCode;
    @InjectView(R.id.phoneNumberEditText)
    EditText phoneNumberEditText;
    @InjectView(R.id.phone_container)
    TextInputLayout phoneContainer;
    @InjectView(R.id.top_container1)
    RelativeLayout topContainer1;
    @InjectView(R.id.confirm_btn)
    Button confirmBtn;
    @InjectView(R.id.image_back)
    ImageButton imageBack;
    @InjectView(R.id.toolbar_text)
    TextView toolbarText;
    @InjectView(R.id.toolbar_container)
    RelativeLayout toolbarContainer;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.confirmRipple)
    RippleView confirmRipple;
    private String android_id;
    PreferenceManager preferenceManager;
    MaterialDialog successDialog, errorDialog;

    ReferFriendsApi referFriendsApi;
    private String last8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_friends);
        ButterKnife.inject(this);
        // Refercode availability check
        preferenceManager = new PreferenceManager(ReferFriendsActivity.this);
        if (preferenceManager.getReferCode().equals("null"))

            referCode.setText("No Refer Code Available");
        else
            referCode.setText("" + preferenceManager.getReferCode());


        backRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
        confirmRipple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                confirmClick();
            }
        });
        phoneNumberEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (phoneNumberEditText.getRight() - phoneNumberEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        startActivityForResult(new Intent(ReferFriendsActivity.this, SelectContactsActivity.class), IntentUtils.SELECT_CONTACT_REQ);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtils.SELECT_CONTACT_REQ) {
            try {
                Logger.d("Return Contact", "contacts " + ((String) data.getExtras().get(Constants.RETURN_RESULT)));

                String num = PhoneUtils.normalizeNum(((String) data.getExtras().get(Constants.RETURN_RESULT)));
                num = num.replace("+", "");
                String upToNCharacters = num.substring(0, Math.min(num.length(), 5));
                if (upToNCharacters.equalsIgnoreCase("88017")) {
                    last8 = num.substring(5, Math.min(num.length(), num.length()));
                } else {
                    last8 = num;
                }


                phoneNumberEditText.setText("" + last8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void confirmClick() {

        android_id = Settings.Secure.getString(ReferFriendsActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        referFriendsApi = ServiceGenerator.createService(ReferFriendsApi.class);
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject innerObject = new JSONObject();
            innerObject.put("DEVICEID", android_id);
            innerObject.put("AUTHTOKEN", preferenceManager.getAuthToken());
            innerObject.put("MSISDN", "017" + preferenceManager.getMSISDN());
            innerObject.put("MSISDN2", "017" + phoneNumberEditText.getText().toString());
            innerObject.put("TYPE", "RFRFRDREQ");
            jsonObject.put("COMMAND", innerObject);
            Logger.d("pp", jsonObject.toString());
            referFriendsApi.referFriends(jsonObject, new Callback<ReferFriendModel>() {
                @Override
                public void success(ReferFriendModel referFriendModel, Response response) {
                    if (referFriendModel.getCommand().getTXNSTATUS().equalsIgnoreCase("200")) {
                        successDialog = new MaterialDialog(ReferFriendsActivity.this);
                        successDialog.setMessage(referFriendModel.getCommand().getMESSAGE() + "");
                        successDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                successDialog.dismiss();
                                phoneNumberEditText.setText("");
                            }
                        });
                        successDialog.show();
                    } else if (referFriendModel.getCommand().getTXNSTATUS().equalsIgnoreCase("MA903")) {
                        errorDialog = new MaterialDialog(ReferFriendsActivity.this);
                        errorDialog.setMessage(referFriendModel.getCommand().getMESSAGE() + "");
                        errorDialog.setPositiveButton("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                errorDialog.dismiss();
                            }
                        });
                        errorDialog.show();
                    } else {
                        Logger.d("Error", referFriendModel.toString());
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Logger.e("Error", error.getMessage().toString());
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
