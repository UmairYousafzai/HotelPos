package com.softvalley.hotelpos.models.response.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.response.ServerResponse;

import java.util.List;

public class ProductResponse extends ServerResponse {
    @SerializedName("Data")
    @Expose
    private List<Product> productList = null;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
