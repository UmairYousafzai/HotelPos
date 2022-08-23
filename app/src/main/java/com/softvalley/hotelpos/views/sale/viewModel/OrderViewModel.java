package com.softvalley.hotelpos.views.sale.viewModel;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_GROUP;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PRODUCT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.GetGroupResponse;
import com.softvalley.hotelpos.models.Group;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.response.product.Product;
import com.softvalley.hotelpos.models.response.product.ProductResponse;
import com.softvalley.hotelpos.utils.CONSTANTS;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.department.DepartmentRepository;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderDepartment;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderProducts;
import com.softvalley.hotelpos.views.sale.adapter.AdapterOrderingProduct;
import com.softvalley.hotelpos.views.sale.repository.SaleRepository;

public class OrderViewModel extends AndroidViewModel {


    private final SaleRepository repository;
    private final AdapterOrderingProduct orderingProductAdapter;
    private final AdapterOrderProducts productAdapter;
    private final AdapterOrderDepartment departmentAdapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Boolean> showProgressDialog;
    private final MutableLiveData<Product> product;
    private final ObservableField<String> subTotalAmount;
    private final ObservableField<String> totalQty;


    public OrderViewModel(@NonNull Application application) {
        super(application);
        repository = new SaleRepository();
        orderingProductAdapter = new AdapterOrderingProduct(this);
        productAdapter = new AdapterOrderProducts(this);
        departmentAdapter = new AdapterOrderDepartment(this);
        toastMessage = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        product = new MutableLiveData<>();
        subTotalAmount = new ObservableField<>("0.0");
        totalQty = new ObservableField<>("0");
        getDepartments(true);
        getServerResponse();
    }

    public void productAdapterOnClick(Product model) {

        product.setValue(model);
    }

    public void orderingProductAdapterOnClick(Product model, int btnKey,int position) {
        if (btnKey == CONSTANTS.PRODUCT_REDUCE_BTN) {
            if (model.getQty() <= 1) {
                orderingProductAdapter.getItemList().remove(model);
                orderingProductAdapter.notifyItemChanged(position);
            } else {
                model.setQty(model.getQty() - 1);
                orderingProductAdapter.getItemList().remove(position);
                orderingProductAdapter.notifyItemChanged(position);
                orderingProductAdapter.addProduct(model,position);

            }
            subTotalAmount.set(String.valueOf(Double.parseDouble(subTotalAmount.get())-model.getUnitRetail()));
            totalQty.set(String.valueOf(Double.valueOf(totalQty.get())-1));
        }else
        {
            subTotalAmount.set(String.valueOf(Double.parseDouble(subTotalAmount.get())+model.getUnitRetail()));
            totalQty.set(String.valueOf(Double.valueOf(totalQty.get())+1));
            model.setQty(model.getQty()+1);
            orderingProductAdapter.getItemList().remove(position);
            orderingProductAdapter.notifyItemChanged(position);
            orderingProductAdapter.addProduct(model,position);

        }
    }

    public void departmentAdapterOnClick(Department model) {

        getProductsByDep(true, model);
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

    public void setOrderingListProduct(Product product) {
        product.setQty(1);
        orderingProductAdapter.getItemList().add(product);
        subTotalAmount.set(String.valueOf(Double.parseDouble(subTotalAmount.get())+product.getAmount()));
        totalQty.set(String.valueOf(Double.valueOf(totalQty.get())+product.getQty()));
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

    private void getServerResponse() {

        repository.setCallBackListener((object, key) -> {
            if (object != null) {
                if (key == GET_DEPARTMENT) {

                    GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                    departmentAdapter.setDepartmentList(getDepartmentResponse.getDepartmentList());
                    toastMessage.setValue(getDepartmentResponse.getMessage());
                    showProgressDialog.setValue(false);

                } else if (key == GET_PRODUCT_RESPONSE) {

                    ProductResponse productResponse = (ProductResponse) object;
                    productAdapter.setProductsList(productResponse.getProductList());
                    toastMessage.setValue(productResponse.getMessage());
                    showProgressDialog.setValue(false);

                } else if (key == SERVER_ERROR) {
                    String error = (String) object;

                    toastMessage.setValue(error);
                    showProgressDialog.setValue(false);

                }
            }
        });
    }
}
