package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.QuickPayConfirmModel;
import com.cc.grameenphone.api_models.QuickPayModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 18/09/15.
 */
public interface QuickPayApi {

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void quickPay(@Body JSONObject model, Callback<QuickPayModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void quickPayConfirm(@Body JSONObject model, Callback<QuickPayConfirmModel> cb);
}
