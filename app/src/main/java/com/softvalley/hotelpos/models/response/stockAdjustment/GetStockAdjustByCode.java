package com.softvalley.hotelpos.models.response.stockAdjustment;

import com.softvalley.hotelpos.models.request.SaveStockAdjustmentRequest;
import com.softvalley.hotelpos.models.response.ServerResponse;

public class GetStockAdjustByCode extends ServerResponse {

    private SaveStockAdjustmentRequest Data=null;

    public SaveStockAdjustmentRequest getData() {
        return Data;
    }

    public void setData(SaveStockAdjustmentRequest data) {
        Data = data;
    }
}
