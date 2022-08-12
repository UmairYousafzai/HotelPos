package com.softvalley.hotelpos.network;

import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.models.Document;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.GetDocumentByCode;
import com.softvalley.hotelpos.models.GetDocumentResponse;
import com.softvalley.hotelpos.models.GetGroupResponse;
import com.softvalley.hotelpos.models.GetItemByCodeResponse;
import com.softvalley.hotelpos.models.GetItemResponse;
import com.softvalley.hotelpos.models.GetLocationResponse;
import com.softvalley.hotelpos.models.GetPartiesServerResponse;
import com.softvalley.hotelpos.models.GetSubGroup;
import com.softvalley.hotelpos.models.Group;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.LoginResponse;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.PostLocation;
import com.softvalley.hotelpos.models.SaveDepartmentResponse;
import com.softvalley.hotelpos.models.SaveDocumentResponse;
import com.softvalley.hotelpos.models.SaveGroupResponse;
import com.softvalley.hotelpos.models.SaveSubGroupResponse;
import com.softvalley.hotelpos.models.SignUpUser;
import com.softvalley.hotelpos.models.SubGroup;
import com.softvalley.hotelpos.models.request.SaveStockAdjustmentRequest;
import com.softvalley.hotelpos.models.request.Voucher;
import com.softvalley.hotelpos.models.response.ServerResponse;
import com.softvalley.hotelpos.models.response.party.SavePartyResponse;
import com.softvalley.hotelpos.models.response.stockAdjustment.GetStockAdjustByCode;
import com.softvalley.hotelpos.models.response.stockAdjustment.SaveStockAdjustmentResponse;
import com.softvalley.hotelpos.models.response.table.TableResponse;
import com.softvalley.hotelpos.models.response.voucher.VoucherResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    /****************************   Authentication **********************/
    @GET("api/Account/Login")
    Call<LoginResponse> login(@Query("username") String username, @Query("password") String password);

    @GET("api/Location/LocationData")
    Call<GetLocationResponse> getLocations();

    @POST("api/Location/SaveLocation")
    Call<ServerResponse> saveLocation(@Body PostLocation postLocation);

    @POST("api/Account/SignUP")
    Call<LoginResponse> saveUser(@Body SignUpUser signUpUser);


    /****************************   Party **********************/


    @POST("api/Party/SaveParty")
    Call<SavePartyResponse> saveParty(@Body Party party);

    @GET("api/Party/PartyData")
    Call<GetPartiesServerResponse> getParties(@Query("BusinessId")String businessID, @Query("partytype") String partyType);

    /****************************   Department , Group And Sub Group **********************/


    @GET("api/Department/DepartmentData")
    Call<GetDepartmentResponse> getDepartment(@Query("BusinessId")String businessID);

    @POST("api/Department/SaveDepartment")
    Call<SaveDepartmentResponse> saveDepartment(@Body Department department);


    @GET("api/Group/GroupData")
    Call<GetGroupResponse> getGroupList(@Query("BusinessId")String businessID, @Query("DepartmentCode")String departmentCode);

    @POST("api/Group/SaveGroup")
    Call<SaveGroupResponse> saveGroup(@Body Group group);

    @POST("api/SubGroup/SaveSubGroup")
    Call<SaveSubGroupResponse> saveSubGroup(@Body SubGroup subGroup);

    @GET("api/SubGroup/SubGroupData")
    Call<GetSubGroup> getSubGroupList(@Query("BusinessId")String businessID, @Query("DepartmentCode")String departmentCode, @Query("GroupCode")String groupCode);


    /****************************   Items **********************/

    @GET("api/Product/ProductData")
    Call<GetItemResponse> getProducts(@Query("BusinessId")String businessID);

    @GET("api/Product/ProductByCode")
    Call<GetItemByCodeResponse> getProductByCode(@Query("Barcode")String barCode);

    @POST("api/Product/SaveProduct")
    Call<ServerResponse> saveItem(@Body Item item);

    /****************************   Purchases **********************/


    @GET("api/Purchase/PurchaseData")
    Call<GetDocumentResponse> getPurchasesList(@Query("BusinessId")String businessID);

    @GET("api/PurchaseReturn/PurchaseReturnData")
    Call<GetDocumentResponse> getPurchasesReturnList(@Query("BusinessId")String businessID);

    @GET("api/Purchase/PurchaseByCode")
    Call<GetDocumentByCode> getPurchaseByCode(@Query("DocNo")String docNO);

    @GET("api/PurchaseReturn/PurchaseReturnByCode")
    Call<GetDocumentByCode> getPurchaseReturnByCode(@Query("DocNo")String docNO);

    @POST("api/Purchase/SavePurchase")
    Call<SaveDocumentResponse> savePurchase(@Body Document document);

    @POST("api/PurchaseReturn/SavePurchaseReturn")
    Call<SaveDocumentResponse> savePurchaseReturn(@Body Document document);



    /****************************   Sale **********************/


    @GET("api/Sale/SaleData")
    Call<GetDocumentResponse> getSaleDocList(@Query("DocType")int docType, @Query("BusinessId")String businessID);

    @GET("api/Sale/SaleByCode")
    Call<GetDocumentByCode> getSaleDocByCode(@Query("DocNo")String docNO);

    @POST("api/Sale/SaveSale")
    Call<SaveDocumentResponse> saveSaleDoc(@Body Document document);

    @POST("api/Party/SavePartyVoucher")
    Call<ServerResponse> saveVoucher(@Body Voucher voucher);

    @GET("api/Party/PartyVoucherHistory")
    Call<VoucherResponse> getVoucherDetail(@Query("partyCode")String partyCode,@Query("businessId")String businessId);


    /****************************   Stock Adjustment  **********************/

    @POST("api/Wastage/SaveWastage")
    Call<SaveStockAdjustmentResponse> saveStockAdjustDoc(@Body SaveStockAdjustmentRequest stockAdjustmentRequest);

    @GET("api/Wastage/WastageData")
    Call<GetDocumentResponse> getStockAdjustmentList(@Query("BusinessId")String businessID);

    @GET("api/Wastage/WastageByCode")
    Call<GetStockAdjustByCode> getStockAdjustmentByCode(@Query("DocNo")String docNO);



    /****************************   Table  **********************/

    @GET("api/Table/TablesData")
    Call<TableResponse> getTables();


}
