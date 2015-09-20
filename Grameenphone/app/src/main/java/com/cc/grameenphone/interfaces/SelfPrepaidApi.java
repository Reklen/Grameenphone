package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.SelfPrepaidModel;
import com.cc.grameenphone.api_models.OtherPrepaidModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 18/09/15.
 */
public interface SelfPrepaidApi {

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void selfPrepaid(@Body JSONObject jsonObject, Callback<SelfPrepaidModel> cb);
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void selfPrepaidOther(@Body JSONObject jsonObject, Callback<OtherPrepaidModel> cb);

}
