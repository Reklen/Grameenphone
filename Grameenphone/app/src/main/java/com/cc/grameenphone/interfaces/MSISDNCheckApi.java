package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.MSISDNCheckModel;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by aditlal on 15/09/15.
 */
public interface MSISDNCheckApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    @FormUrlEncoded
    void check(@Body JsonObject jsonObject, Callback<MSISDNCheckModel> cb);
}
