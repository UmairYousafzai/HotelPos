package com.softvalley.hotelpos.models.response.voucher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.response.ServerResponse;

public class VoucherResponse extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private VoucherData voucherData;

    public VoucherData getVoucherData() {
        return voucherData;
    }

    public void setVoucherData(VoucherData voucherData) {
        this.voucherData = voucherData;
    }
}
