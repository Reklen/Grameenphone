package com.cc.grameenphone.interfaces;

import com.cc.grameenphone.api_models.AddAssociationModel;
import com.cc.grameenphone.api_models.AssociationModel;
import com.cc.grameenphone.api_models.CancelAssociationModel;
import com.cc.grameenphone.api_models.CancelMultipleResponseModel;
import com.cc.grameenphone.api_models.CompanyListModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by aditlal on 19/09/15.
 */
public interface ManageAssociationApi {
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")

    void fetchUserAssociaition(@Body TypedInput model, Callback<AssociationModel> cb);
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")

    void addAssociation(@Body TypedInput jsonObject, Callback<AddAssociationModel> cb);
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")

    void cancelAssociation(@Body TypedInput jsonObject, Callback<CancelAssociationModel> cb);
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")

    void cancelMultipleAssociation(@Body TypedInput jsonObject, Callback<CancelMultipleResponseModel> cb);
    @POST("/GPTxn/CelliciumSelector?LOGIN=Ussd_Bearer1&PASSWORD=U$$d_Int11&REQUEST_GATEWAY_CODE=J2ME&REQUEST_GATEWAY_TYPE=J2ME&requestText=")

    void fetchAssociaition(@Body TypedInput jsonObject, Callback<CompanyListModel> cb);
}
