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
    @Expose
    private String userId;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("Action")
    @Expose
    private String action;
    public Table() {
    }

    public Table(String tableCode, String tableCodeBusinessWise, String tableName, String status, String userId, String businessId, String action) {
        this.tableCode = tableCode;
        this.tableCodeBusinessWise = tableCodeBusinessWise;
        this.tableName = tableName;
        this.status = status;
        this.userId = userId;
        this.businessId = businessId;
        this.action = action;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

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
