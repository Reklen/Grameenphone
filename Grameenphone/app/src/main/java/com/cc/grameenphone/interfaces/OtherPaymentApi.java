package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.BillConfirmationModel;
import com.cc.grameenphone.api_models.OtherPaymentModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by rajkiran on 21/09/15.
 */
public interface OtherPaymentApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void otherPayment(@Body JSONObject jsonObject, Callback<OtherPaymentModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void billConfirmation(@Body JSONObject jsonObject, Callback<BillConfirmationModel> cb);
}
