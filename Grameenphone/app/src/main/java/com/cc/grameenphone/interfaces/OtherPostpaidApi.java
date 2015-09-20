package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.OtherPostpaidModel;
import com.cc.grameenphone.api_models.SelfPostpaidModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 18/09/15.
 */
public interface OtherPostpaidApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void otherPostpaid(@Body JSONObject jsonObject, Callback<OtherPostpaidModel> cb);
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void selfPostpaid(@Body JSONObject jsonObject, Callback<SelfPostpaidModel> cb);

}
