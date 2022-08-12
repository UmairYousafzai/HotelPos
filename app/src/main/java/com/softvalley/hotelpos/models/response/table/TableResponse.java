package com.softvalley.hotelpos.models.response.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.response.ServerResponse;

import java.util.List;

public class TableResponse extends ServerResponse {

    @SerializedName("Data")
    @Expose
    private List<Table> tableList = null;

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }
}
