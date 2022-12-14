package com.softvalley.hotelpos.views.sale.repository;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DOCUMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_ITEMS;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PARTY;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PRODUCT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.util.Log;

import androidx.annotation.NonNull;

import com.softvalley.hotelpos.Interface.CallBackListener;
import com.softvalley.hotelpos.models.Document;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.GetDocumentByCode;
import com.softvalley.hotelpos.models.GetDocumentResponse;
import com.softvalley.hotelpos.models.GetItemResponse;
import com.softvalley.hotelpos.models.GetPartiesServerResponse;
import com.softvalley.hotelpos.models.SaveDocumentResponse;
import com.softvalley.hotelpos.models.response.order.SaveOrderResponse;
import com.softvalley.hotelpos.models.response.product.ProductResponse;
import com.softvalley.hotelpos.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleRepository {

    private CallBackListener callBackListener;

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void getSaleDocs(int docType ,String businessID) {
        Call<GetDocumentResponse> call = ApiClient.getInstance().getApi().getSaleDocList(docType,businessID);
        call.enqueue(new Callback<GetDocumentResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDocumentResponse> call, @NonNull Response<GetDocumentResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            if (callBackListener != null) {
                                callBackListener.getServerResponse(response.body(), GET_DOCUMENT);

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
            public void onFailure(@NonNull Call<GetDocumentResponse> call, @NonNull Throwable t) {
                callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

            }
        });
    }


    public void getPartiesFromServer(String type, String businessID) {


        Call<GetPartiesServerResponse> call = ApiClient.getInstance().getApi().getParties(businessID, type);
        call.enqueue(new Callback<GetPartiesServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetPartiesServerResponse> call, @NonNull Response<GetPartiesServerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200) {
                            if (callBackListener != null) {
                                callBackListener.getServerResponse(response.body(), GET_PARTY);

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
            public void onFailure(@NonNull Call<GetPartiesServerResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:", t.getMessage());
                callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

            }
        });

    }

    public void getItems(String businessID) {
        Call<GetItemResponse> call = ApiClient.getInstance().getApi().getProducts(businessID);
        call.enqueue(new Callback<GetItemResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetItemResponse> call, @NonNull Response<GetItemResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        GetItemResponse getItemResponse = response.body();

                        if (getItemResponse.getCode() == 200) {

                            if (getItemResponse.getItem() != null && getItemResponse.getItem().size() > 0) {
                                if (callBackListener != null) {
                                    callBackListener.getServerResponse(getItemResponse, GET_ITEMS);

                                }

                            }
                        } else {
                            callBackListener.getServerResponse(getItemResponse.getMessage(), SERVER_ERROR);
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
            public void onFailure(@NonNull Call<GetItemResponse> call, @NonNull Throwable t) {
                Log.e("Parties Saving Error:", t.getMessage());
                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });

    }


    public void saveSaleDocument(Document document) {
        Call<SaveDocumentResponse> call = ApiClient.getInstance().getApi().saveSaleDoc(document);

        call.enqueue(new Callback<SaveDocumentResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveDocumentResponse> call, @NonNull Response<SaveDocumentResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), SAVE_DOCUMENT_RESPONSE);


                        }
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
            public void onFailure(@NonNull Call<SaveDocumentResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
    public void saveSaleOrder(com.softvalley.hotelpos.models.request.Order.Document document) {
        Call<SaveOrderResponse> call = ApiClient.getInstance().getApi().saveOrder(document);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveOrderResponse> call, @NonNull Response<SaveOrderResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), SAVE_DOCUMENT_RESPONSE);


                        }
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
            public void onFailure(@NonNull Call<SaveOrderResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }


    public void getSaleDocByCode(String docCode) {
        Call<GetDocumentByCode> call = ApiClient.getInstance().getApi().getSaleDocByCode(docCode);

        call.enqueue(new Callback<GetDocumentByCode>() {
            @Override
            public void onResponse(@NonNull Call<GetDocumentByCode> call, @NonNull Response<GetDocumentByCode> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), GET_DOCUMENT_BY_CODE);

                        }
                    } else {
                        callBackListener.getServerResponse("Nothing Found", SERVER_ERROR);

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
            public void onFailure(@NonNull Call<GetDocumentByCode> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
    public void getOrderCode(String docCode) {
        Call<SaveOrderResponse> call = ApiClient.getInstance().getApi().getOrderByCode(docCode);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveOrderResponse> call, @NonNull Response<SaveOrderResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), GET_DOCUMENT_BY_CODE);

                        }
                    } else {
                        callBackListener.getServerResponse("Nothing Found", SERVER_ERROR);

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
            public void onFailure(@NonNull Call<SaveOrderResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
    public void getOrderByTAble(String tableCode,String businessID) {
        Call<SaveOrderResponse> call = ApiClient.getInstance().getApi().getOrderByTable(tableCode,businessID);

        call.enqueue(new Callback<SaveOrderResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveOrderResponse> call, @NonNull Response<SaveOrderResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (callBackListener != null) {

                            callBackListener.getServerResponse(response.body(), GET_DOCUMENT_BY_CODE);

                        }
                    } else {
                        callBackListener.getServerResponse("Nothing Found", SERVER_ERROR);

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
            public void onFailure(@NonNull Call<SaveOrderResponse> call, @NonNull Throwable t) {

                if (callBackListener != null) {
                    callBackListener.getServerResponse(t.getMessage(), SERVER_ERROR);

                }
            }
        });
    }
    public void getDepartmentFromServer(String businessId)
    {

        Call<GetDepartmentResponse> call = ApiClient.getInstance().getApi().getDepartment(businessId);

        call.enqueue(new Callback<GetDepartmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDepartmentResponse> call, @NonNull Response<GetDepartmentResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_DEPARTMENT);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDepartmentResponse> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }
    public void getProductsByDepartment(String businessId,String departmentCode)
    {

        Call<ProductResponse> call = ApiClient.getInstance().getApi().getProductByDepCode(
                departmentCode,
                "",
                "",
                "1",
                "10",
                businessId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_PRODUCT_RESPONSE);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }
}
