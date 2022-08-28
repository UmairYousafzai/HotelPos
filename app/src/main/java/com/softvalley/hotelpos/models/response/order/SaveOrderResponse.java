package com.softvalley.hotelpos.models.response.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.request.Order.Document;
import com.softvalley.hotelpos.models.response.ServerResponse;

public class SaveOrderResponse extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
