package com.softvalley.hotelpos.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.response.ServerResponse;


public class SaveDocumentResponse extends ServerResponse {

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
