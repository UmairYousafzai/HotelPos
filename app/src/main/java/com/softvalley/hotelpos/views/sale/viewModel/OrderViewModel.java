package com.softvalley.hotelpos.views.sale.viewModel;

import static com.softvalley.hotelpos.utils.CONSTANTS.ADD_CUSTOMER_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PARTY;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PRODUCT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.ORDER_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SAVE_HOTEL_SALE_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.GetDocumentByCode;
import com.softvalley.hotelpos.models.GetPartiesServerResponse;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.SaveDocumentResponse;
import com.softvalley.hotelpos.models.request.Order.Document;
import com.softvalley.hotelpos.models.response.order.SaveOrderResponse;
import com.softvalley.hotelpos.models.response.product.Product;
import com.softvalley.hotelpos.models.response.product.ProductResponse;
import com.softvalley.hotelpos.models.response.table.Table;
import com.softvalley.hotelpos.utils.CONSTANTS;
import com.softvalley.hotelpos.utils.Converter;
import com.softvalley.hotelpos.utils.DateUtil;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderDepartment;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderProducts;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderedProducts;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderingProduct;
import com.softvalley.hotelpos.views.sale.repository.SaleRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderViewModel extends AndroidViewModel {


    private final SaleRepository repository;
    private Table table;
    private final AdapterOrderingProduct orderingProductAdapter;
    private final AdapterOrderedProducts orderedProductAdapter;
    private final AdapterOrderProducts productAdapter;
    private final AdapterOrderDepartment departmentAdapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Boolean> showProgressDialog;
    private final MutableLiveData<Integer> btnAction;
    private final MutableLiveData<Product> product;
    private final MutableLiveData<List<Party>> partyList;
    private final MutableLiveData<Document> documentMutableLiveData;
    private final ObservableField<String> subTotalAmount;
    private final ObservableField<String> totalQty;
    private final ObservableField<String> selectedCustomerName;
    private final HashMap<String, String> customerIdHashMap;
    private String customerCode = "";
    private String docNumber = "";
    private String businessDocNumber = "";
    private String action = "INSERT";
    private boolean isAuthorizeRequest = false;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        repository = new SaleRepository();
        orderingProductAdapter = new AdapterOrderingProduct(this);
        productAdapter = new AdapterOrderProducts(this);
        departmentAdapter = new AdapterOrderDepartment(this);
        orderedProductAdapter = new AdapterOrderedProducts(this);
        toastMessage = new MutableLiveData<>();
        documentMutableLiveData = new MutableLiveData<>();
        btnAction = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        product = new MutableLiveData<>();
        partyList = new MutableLiveData<>();
        subTotalAmount = new ObservableField<>("0.0");
        totalQty = new ObservableField<>("0");
        selectedCustomerName = new ObservableField<>("");
        customerIdHashMap = new HashMap<>();
        getDepartments(true);
        getServerResponse();
        table = new Table();
    }

    public void onClick(int key) {
        if (key == SAVE_HOTEL_SALE_BTN) {
            saveSaleDocument(false);
        } else if (key == ORDER_BTN) {
            getOrderByDocCode(docNumber);
        }
        btnAction.setValue(key);

    }


    public void onClickCustomer(Party party) {
        selectedCustomerName.set(party.getPartyName());
    }

    public void productAdapterOnClick(Product model) {

        product.setValue(model);
    }

    public void orderingProductAdapterOnClick(Product model, int btnKey, int position) {
        if (btnKey == CONSTANTS.PRODUCT_REDUCE_BTN) {
            if (model.getQty() <= 1) {
                orderingProductAdapter.getItemList().remove(model);
                orderingProductAdapter.notifyItemChanged(position);
            } else {
                model.setQty(model.getQty() - 1);
                orderingProductAdapter.getItemList().remove(position);
                orderingProductAdapter.notifyItemChanged(position);
                orderingProductAdapter.addProduct(model, position);

            }
            subTotalAmount.set(String.valueOf(Double.parseDouble(subTotalAmount.get()) - model.getUnitRetail()));
            totalQty.set(String.valueOf(Double.valueOf(totalQty.get()) - 1));
        } else {
            subTotalAmount.set(String.valueOf(Double.parseDouble(subTotalAmount.get()) + model.getUnitRetail()));
            totalQty.set(String.valueOf(Double.valueOf(totalQty.get()) + 1));
            model.setQty(model.getQty() + 1);
            orderingProductAdapter.getItemList().remove(position);
            orderingProductAdapter.notifyItemChanged(position);
            orderingProductAdapter.addProduct(model, position);

        }
    }

    public void departmentAdapterOnClick(Department model) {

        getProductsByDep(true, model);
    }


    public void setCustomerID() {
        if (selectedCustomerName.get() != null && !selectedCustomerName.get().isEmpty()) {
            customerCode = customerIdHashMap.get(selectedCustomerName.get());
        }
    }

    public MutableLiveData<Integer> getBtnAction() {
        return btnAction;
    }

    public ObservableField<String> getSelectedCustomerName() {
        return selectedCustomerName;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public MutableLiveData<Product> getProduct() {
        return product;
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }


    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public AdapterOrderProducts getProductAdapter() {
        return productAdapter;
    }

    public AdapterOrderDepartment getDepartmentAdapter() {
        return departmentAdapter;
    }

    public AdapterOrderingProduct getOrderingProductAdapter() {
        return orderingProductAdapter;
    }

    public ObservableField<String> getSubTotalAmount() {
        return subTotalAmount;
    }

    public ObservableField<String> getTotalQty() {
        return totalQty;
    }

    public MutableLiveData<List<Party>> getPartyList() {
        return partyList;
    }

    public AdapterOrderedProducts getOrderedProductAdapter() {
        return orderedProductAdapter;
    }

    public void setOrderingListProduct(Product product) {
        product.setQty(1);
        orderingProductAdapter.getItemList().add(product);
        subTotalAmount.set(String.valueOf(Double.parseDouble(subTotalAmount.get()) + product.getAmount()));
        totalQty.set(String.valueOf(Double.valueOf(totalQty.get()) + product.getQty()));
    }

    private void getDepartments(boolean showProgress) {
        showProgressDialog.setValue(showProgress);
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        repository.getDepartmentFromServer(businessID);

    }

    private void getProductsByDep(boolean showProgress, Department department) {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        showProgressDialog.setValue(showProgress);
        repository.getProductsByDepartment(businessID, department.getDepartmentCode());

    }

    public MutableLiveData<Document> getDocumentMutableLiveData() {
        return documentMutableLiveData;
    }

    public void getCustomer() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        showProgressDialog.setValue(true);

        repository.getPartiesFromServer("c", businessID);

    }

    public void getOrderByDocCode(String docCode) {
        if (docCode != null && !docCode.isEmpty()) {
            showProgressDialog.setValue(true);
            repository.getOrderCode(docCode);
        }
    }
    public void getOrderByTableCode(String tableCode) {
        if (tableCode != null && !tableCode.isEmpty()) {
            String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
            showProgressDialog.setValue(true);
            repository.getOrderByTAble(tableCode,businessID);
        }
    }

    private void setFields(com.softvalley.hotelpos.models.request.Order.Document document) {
        docNumber = document.getDocNo();
        selectedCustomerName.set(document.getPartyName());
        subTotalAmount.set(String.valueOf(document.getTotalAmount()));
        List<Product> list = new ArrayList<>(document.getItems());
        orderedProductAdapter.setItemList(list);
        action = "UPDATE";
        customerCode = document.getPartyCode();
        businessDocNumber= document.getDocNoBusinessWise();

        double quantity = 0;
        for (Product item : document.getItems()) {
            quantity += item.getQty();
        }
        totalQty.set(String.valueOf(quantity));
        documentMutableLiveData.setValue(document);
    }

    private void saveSaleDocument(boolean isAuthorizeRequest) {
        if (!customerCode.isEmpty()) {
            if (!subTotalAmount.get().equals("0")) {
                String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
                String userID = SharedPreferenceHelper.getInstance(getApplication()).getUserID();
                Document document = new Document();
                if (!isAuthorizeRequest) {
                    document.setItems(orderingProductAdapter.getItemList());
                } else {
                    document.setItems(orderedProductAdapter.getItemList());
                }
                document.setAction(action);
                document.setBusinessId(businessID);
                document.setLocationCode("000001");
                document.setDocType("2");
                document.setPartyCode(customerCode);
                document.setTotalAmount(Double.parseDouble(subTotalAmount.get()));
                document.setUserId(userID);
                document.setStatus("0");
                document.setDocDate(DateUtil.getInstance().getDate());
                document.setDocNo(docNumber);
                document.setDocNoBusinessWise(businessDocNumber);
                document.setTableCode(table.getTableCode());
//                    if (actionMutableLiveData.getValue().equals("UPDATE")) {
//                        document.setDocNoBusinessWise(docNumberBusiness.get());
//                        document.setDocNo(docNumber);
//                    } else {
//                        document.setDocNo("");
//
//                    }
                documentMutableLiveData.setValue(document);

                showProgressDialog.setValue(true);

                repository.saveSaleOrder(document);

            } else {
                showProgressDialog.setValue(false);

                toastMessage.setValue("Please Enter Products");
            }
        } else {
            showProgressDialog.setValue(false);

            toastMessage.setValue("Please select Customer");
        }


    }


    private void clearData() {
        docNumber = "";
        selectedCustomerName.set("");
        orderingProductAdapter.clearList();
        subTotalAmount.set("0");
        totalQty.set("0");
    }

    private void getServerResponse() {

        repository.setCallBackListener((object, key) -> {
            if (object != null) {
                if (key == GET_DEPARTMENT) {

                    GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                    departmentAdapter.setDepartmentList(getDepartmentResponse.getDepartmentList());
                    if (getDepartmentResponse.getCode()!=200) {
                        toastMessage.setValue(getDepartmentResponse.getMessage());
                    }
                    showProgressDialog.setValue(false);

                } else if (key == GET_PARTY) {
                    GetPartiesServerResponse partyServerResponse = (GetPartiesServerResponse) object;
                    populateCustomerIDHashMap(partyServerResponse.getPartyList());
                    partyList.setValue(partyServerResponse.getPartyList());
                    showProgressDialog.setValue(false);
                    if (partyServerResponse.getCode()!=200) {
                        toastMessage.setValue(partyServerResponse.getMessage());
                    }
                } else if (key == GET_PRODUCT_RESPONSE) {

                    ProductResponse productResponse = (ProductResponse) object;
                    productAdapter.setProductsList(productResponse.getProductList());
                    if (productResponse.getCode()!=200) {
                        toastMessage.setValue(productResponse.getMessage());
                    }
                    showProgressDialog.setValue(false);

                } else if (key == SERVER_ERROR) {
                    String error = (String) object;

                    toastMessage.setValue(error);
                    showProgressDialog.setValue(false);

                } else if (key == GET_DOCUMENT_BY_CODE) {
                    SaveOrderResponse purchaseByCode = (SaveOrderResponse) object;
                    if (purchaseByCode.getCode() == 200) {

                        if (purchaseByCode.getDocument() != null) {
                            setFields(purchaseByCode.getDocument());
                        }
                    }
                    toastMessage.setValue(purchaseByCode.getMessage());
                    showProgressDialog.setValue(false);
                } else if (key == SAVE_DOCUMENT_RESPONSE) {
                    SaveOrderResponse saveDocumentResponse = (SaveOrderResponse) object;
                    if (saveDocumentResponse.getCode() == 200) {
                        if (isAuthorizeRequest) {
                            isAuthorizeRequest = false;
                            clearData();
                        } else {
                            docNumber = saveDocumentResponse.getDocument().getDocNo();
                        }
                    }

                    toastMessage.setValue(saveDocumentResponse.getMessage());
                    showProgressDialog.setValue(false);

                }
            }
        });
    }

    private void populateCustomerIDHashMap(List<Party> partyList) {

        for (Party party : partyList) {
            customerIdHashMap.put(party.getPartyName(), party.getPartyCode());
        }

    }


}
