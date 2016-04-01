package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.BillListModel;
import com.cc.grameenphone.api_models.BillPaymentModel;
import com.cc.grameenphone.api_models.MultiBillApiModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by rajkiran on 17/09/15.
 */
public interface BillspaymentApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void fetchBills(@Body TypedInput jsonObject, Callback<BillListModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void payBill(@Body TypedInput jsonObject, Callback<BillPaymentModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void payMultipleBill(@Body TypedInput jsonObject, Callback<MultiBillApiModel> cb);


}
