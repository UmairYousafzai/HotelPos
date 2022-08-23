package com.softvalley.hotelpos.models.response.table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Table implements Serializable {
    @SerializedName("TableCode")
    @Expose
    private String tableCode;
    @SerializedName("TableCodeBusinessWise")
    @Expose
    private String tableCodeBusinessWise;
    @SerializedName("TableName")
    @Expose
    private String tableName;
    @SerializedName("Status")
    @Expose
    private String status;

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableCodeBusinessWise() {
        return tableCodeBusinessWise;
    }

    public void setTableCodeBusinessWise(String tableCodeBusinessWise) {
        this.tableCodeBusinessWise = tableCodeBusinessWise;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
