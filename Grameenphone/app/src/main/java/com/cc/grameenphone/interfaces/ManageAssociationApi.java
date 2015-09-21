package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.AddAssociationModel;
import com.cc.grameenphone.api_models.AssociationModel;
import com.cc.grameenphone.api_models.CancelAssociationModel;
import com.cc.grameenphone.api_models.CompanyListModel;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by aditlal on 19/09/15.
 */
public interface ManageAssociationApi {

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void fetchUserAssociaition(@Body JSONObject model, Callback<AssociationModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void addAssociation(@Body JSONObject jsonObject, Callback<AddAssociationModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void cancelAssociation(@Body JSONObject jsonObject, Callback<CancelAssociationModel> cb);

    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")
    void fetchAssociaition(@Body JSONObject jsonObject, Callback<CompanyListModel> cb);
}
