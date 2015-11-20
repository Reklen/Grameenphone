package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.PinChangeModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by rajkiran on 16/09/15.
 */
public interface PinchangeApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void pinChange(@Body TypedInput jsonObject, Callback<PinChangeModel> cb);

}
