package com.softvalley.hotelpos.models.request.Order;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.response.product.Product;
import com.softvalley.hotelpos.utils.Converter;

import java.util.ArrayList;
import java.util.List;

public class Document implements Parcelable {

    @SerializedName("DocNo")
    @Expose
    private String docNo;
    @SerializedName("DocNoBusinesWise")
    @Expose
    private String docNoBusinessWise;
    @SerializedName("DocDate")
    @Expose
    private String docDate;
    @SerializedName("Amount")
    @Expose
    private double amount;
    @SerializedName("Supplier")
    @Expose
    private String supplier;
    @SerializedName("SuplierName")
    @Expose
    private String supplierName;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("SuplierCode")
    @Expose
    private String supplierCode;
    @SerializedName("LocationCode")
    @Expose
    private String locationCode;

    @SerializedName("TotalDiscountAmount")
    @Expose
    private double totalDiscountAmount;
    @SerializedName("TotalAmount")
    @Expose
    private double totalAmount;
    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("BusinessId")
    @Expose
    private String businessId;
    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("DocType")
    @Expose
    private String docType;
    @SerializedName("PartyCode")
    @Expose
    private String partyCode;
    @SerializedName("PartyName")
    @Expose
    private String PartyName;
    @SerializedName("Party")
    @Expose
    private String party;


    @SerializedName("DocumentDetail")
    @Expose
    @TypeConverters(Converter.class)
    private List<Product> items= new ArrayList<>();

    private String TableCode;
    public Document() {
    }


    protected Document(Parcel in) {
        docNo = in.readString();
        docNoBusinessWise = in.readString();
        docDate = in.readString();
        amount = in.readDouble();
        supplier = in.readString();
        supplierName = in.readString();
        location = in.readString();
        status = in.readString();
        supplierCode = in.readString();
        locationCode = in.readString();
        totalDiscountAmount = in.readDouble();
        totalAmount = in.readDouble();
        userId = in.readString();
        businessId = in.readString();
        action = in.readString();
        docType = in.readString();
        partyCode = in.readString();
        PartyName = in.readString();
        party = in.readString();
        TableCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(docNo);
        dest.writeString(docNoBusinessWise);
        dest.writeString(docDate);
        dest.writeDouble(amount);
        dest.writeString(supplier);
        dest.writeString(supplierName);
        dest.writeString(location);
        dest.writeString(status);
        dest.writeString(supplierCode);
        dest.writeString(locationCode);
        dest.writeDouble(totalDiscountAmount);
        dest.writeDouble(totalAmount);
        dest.writeString(userId);
        dest.writeString(businessId);
        dest.writeString(action);
        dest.writeString(docType);
        dest.writeString(partyCode);
        dest.writeString(PartyName);
        dest.writeString(party);
        dest.writeString(TableCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Document> CREATOR = new Creator<Document>() {
        @Override
        public Document createFromParcel(Parcel in) {
            return new Document(in);
        }

        @Override
        public Document[] newArray(int size) {
            return new Document[size];
        }
    };

    public String getTableCode() {
        return TableCode;
    }

    public void setTableCode(String tableCode) {
        TableCode = tableCode;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocNoBusinessWise() {
        return docNoBusinessWise;
    }

    public void setDocNoBusinessWise(String docNoBusinesWise) {
        this.docNoBusinessWise = docNoBusinesWise;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
