package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.BillListModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 17/09/15.
 */
public interface BillspaymentApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void billsPay(@Body JSONObject jsonObject, Callback<BillListModel> cb);

}
