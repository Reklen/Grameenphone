package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.RechargeModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 16/09/15.
 */
public class MinistatementApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void ministatement(@Body JSONObject jsonObject, Callback<RechargeModel> cb);

}
