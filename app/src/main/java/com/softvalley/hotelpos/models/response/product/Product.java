package com.softvalley.hotelpos.models.response.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("DepartmentCode")
    @Expose
    private String departmentCode;
    @SerializedName("DepartmentName")
    @Expose
    private String departmentName;
    @SerializedName("GroupCode")
    @Expose
    private String groupCode;
    @SerializedName("GroupName")
    @Expose
    private String groupName;
    @SerializedName("SubGroupCode")
    @Expose
    private String subGroupCode;
    @SerializedName("SubGroupName")
    @Expose
    private String subGroupName;
    @SerializedName("Barcode")
    @Expose
    private String barcode;
    @SerializedName("BusinessBarcode")
    @Expose
    private String businessBarcode;
    @SerializedName("ReferenceCode")
    @Expose
    private String referenceCode;
    @SerializedName("Uan")
    @Expose
    private String uan;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("NativeDescription")
    @Expose
    private String nativeDescription;
    @SerializedName("UnitCost")
    @Expose
    private double unitCost;
    @SerializedName("UnitRetail")
    @Expose
    private double unitRetail;
    @SerializedName("CtnPcs")
    @Expose
    private double ctnPcs;
    @SerializedName("PromoPercentage")
    @Expose
    private Object promoPercentage;
    @SerializedName("GSTType")
    @Expose
    private String gSTType;
    @SerializedName("GST_Percentage")
    @Expose
    private double gSTPercentage;
    @SerializedName("HsCode")
    @Expose
    private String hsCode;
    @SerializedName("HsPercentage")
    @Expose
    private double hsPercentage;
    @SerializedName("GSTAmount")
    @Expose
    private double gSTAmount;
    @SerializedName("NetCost")
    @Expose
    private double netCost;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("ProductImage")
    @Expose
    private String productImage;

    private int Qty;
    private double amount;

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
        setAmount(Qty* unitRetail);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubGroupCode() {
        return subGroupCode;
    }

    public void setSubGroupCode(String subGroupCode) {
        this.subGroupCode = subGroupCode;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBusinessBarcode() {
        return businessBarcode;
    }

    public void setBusinessBarcode(String businessBarcode) {
        this.businessBarcode = businessBarcode;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getUan() {
        return uan;
    }

    public void setUan(String uan) {
        this.uan = uan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNativeDescription() {
        return nativeDescription;
    }

    public void setNativeDescription(String nativeDescription) {
        this.nativeDescription = nativeDescription;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getUnitRetail() {
        return unitRetail;
    }

    public void setUnitRetail(double unitRetail) {
        this.unitRetail = unitRetail;
    }

    public double getCtnPcs() {
        return ctnPcs;
    }

    public void setCtnPcs(double ctnPcs) {
        this.ctnPcs = ctnPcs;
    }

    public Object getPromoPercentage() {
        return promoPercentage;
    }

    public void setPromoPercentage(Object promoPercentage) {
        this.promoPercentage = promoPercentage;
    }

    public String getgSTType() {
        return gSTType;
    }

    public void setgSTType(String gSTType) {
        this.gSTType = gSTType;
    }

    public double getgSTPercentage() {
        return gSTPercentage;
    }

    public void setgSTPercentage(double gSTPercentage) {
        this.gSTPercentage = gSTPercentage;
    }

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public double getHsPercentage() {
        return hsPercentage;
    }

    public void setHsPercentage(double hsPercentage) {
        this.hsPercentage = hsPercentage;
    }

    public double getgSTAmount() {
        return gSTAmount;
    }

    public void setgSTAmount(double gSTAmount) {
        this.gSTAmount = gSTAmount;
    }

    public double getNetCost() {
        return netCost;
    }

    public void setNetCost(double netCost) {
        this.netCost = netCost;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
