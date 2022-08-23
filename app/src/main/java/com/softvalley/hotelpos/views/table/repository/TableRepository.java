package com.softvalley.hotelpos.views.table.repository;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_TABLE_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import com.softvalley.hotelpos.Interface.CallBackListener;
import com.softvalley.hotelpos.models.response.table.TableResponse;
import com.softvalley.hotelpos.network.ApiClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableRepository {

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void getTables(String businessId) {


        Call<TableResponse> call = ApiClient.getInstance().getApi().getTables(businessId);

        call.enqueue(new Callback<TableResponse>() {
            @Override
            public void onResponse(@NotNull Call<TableResponse> call, @NotNull Response<TableResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            if (callBackListener != null) {
                                callBackListener.getServerResponse(response.body(), GET_TABLE_RESPONSE);

                            }
                        } else {
                            callBackListener.getServerResponse(response.body().getMessage(), SERVER_ERROR);

                        }
                    } else {
                        callBackListener.getServerResponse(response.message(), SERVER_ERROR);
                    }
                } else {
                    if (response.errorBody() != null) {
                        if (callBackListener != null) {
                            callBackListener.getServerResponse(response.errorBody().toString(), SERVER_ERROR);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<TableResponse> call, @NotNull Throwable t) {
                callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);
            }
        });

    }

}
