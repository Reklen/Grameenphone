package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.ProfileModel;
import com.cc.grameenphone.api_models.ProfileUpdateModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 17/09/15.
 */
public interface ProfileUpdateApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void profileUpdate(@Body JSONObject jsonObject, Callback<ProfileUpdateModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void profile(@Body JSONObject jsonObject, Callback<ProfileModel> cb);
}
