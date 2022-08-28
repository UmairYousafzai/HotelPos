package com.softvalley.hotelpos.models.response.table;

import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.response.ServerResponse;

public class SaveTableResponse extends ServerResponse {

    @SerializedName("Data")
    private Table table;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
