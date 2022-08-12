package com.softvalley.hotelpos.models.response.stockAdjustment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.request.SaveStockAdjustmentRequest;
import com.softvalley.hotelpos.models.response.ServerResponse;

import java.util.List;

public class GetStockAdjustResponse extends ServerResponse {
    @SerializedName("Data")
    @Expose
    private List<SaveStockAdjustmentRequest> data = null;

    public List<SaveStockAdjustmentRequest> getData() {
        return data;
    }

    public void setData(List<SaveStockAdjustmentRequest> data) {
        this.data = data;
    }
}
