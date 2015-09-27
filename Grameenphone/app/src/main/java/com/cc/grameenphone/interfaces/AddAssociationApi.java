package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.BillConfirmationModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 25/09/15.
 */
public interface AddAssociationApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void associationSubmit(@Body JSONObject jsonObject, Callback<BillConfirmationModel> cb);
}
