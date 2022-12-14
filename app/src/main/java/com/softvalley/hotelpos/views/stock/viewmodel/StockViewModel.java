package com.softvalley.hotelpos.views.stock.viewmodel;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_ITEMS;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_ITEM_BY_CODE;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PARTY;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_RESPONSE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.softvalley.hotelpos.Interface.CallBackListener;
import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.response.ServerResponse;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.stock.adapter.ItemListAdapter;
import com.softvalley.hotelpos.views.stock.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;

public class StockViewModel extends AndroidViewModel {

    private final StockRepository stockRepository;
    private final MutableLiveData<List<Department>> departmentMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Party>> partyMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<ServerResponse> serverLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> serverErrorLiveData = new MutableLiveData<>();
    private final ItemListAdapter adapter;
    private final MutableLiveData<Item> itemMutableLiveData;
    private final MutableLiveData<List<Item>> itemListMutableLiveData;
    private final List<Item> selectedItems;
    private final MutableLiveData<String> selectedBtnText;
    private final MutableLiveData<Boolean> showProgressDialog;


    public StockViewModel(@NonNull Application application) {
        super(application);

        stockRepository = StockRepository.getInstance(application);
        adapter = new ItemListAdapter(this);
        itemMutableLiveData = new MutableLiveData<>();
        itemListMutableLiveData = new MutableLiveData<>();
        selectedItems = new ArrayList<>();
        selectedBtnText = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }

    public MutableLiveData<String> getSelectedBtnText() {
        return selectedBtnText;
    }

    public MutableLiveData<String> getServerErrorLiveData() {
        return serverErrorLiveData;
    }

    public ItemListAdapter getAdapter() {
        return adapter;
    }

    public void onAdapterCardClick(Item item, int action) {
        if (item != null) {
            if (action == 1) {
                if (!selectedItems.contains(item)) {
                    selectedItems.add(item);
                    String size = String.valueOf(selectedItems.size());
                    selectedBtnText.setValue(size);
                } else {
                    serverErrorLiveData.setValue("Already selected");
                }

            } else {
                item.setBtn_action(action);
                itemMutableLiveData.setValue(item);
            }

        }
    }

    public MutableLiveData<List<Item>> getItemListMutableLiveData() {
        return itemListMutableLiveData;
    }

    public void onClick() {
        if (selectedItems.size() > 0) {
            itemListMutableLiveData.setValue(selectedItems);
        } else {
            serverErrorLiveData.setValue("please select item");
        }
    }

    public void setAdapterKey(int key) {
        adapter.setKey(key);
    }

    public void searchItem(String queryText) {
        adapter.getFilter().filter(queryText);
    }

    public MutableLiveData<Item> getItemMutableLiveData() {
        return itemMutableLiveData;
    }

    public MutableLiveData<ServerResponse> saveItem(Item item) {
        stockRepository.saveItemToServer(item);
        getServerResponse();
        return serverLiveData;
    }

    public MutableLiveData<List<Department>> getDepartment() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        stockRepository.getDepartmentFromServer(businessID);
        getServerResponse();
        return departmentMutableLiveData;
    }

    public MutableLiveData<List<Party>> getParty() {
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        stockRepository.getSupplier(businessID);
        getServerResponse();
        return partyMutableLiveData;
    }

    public void getItemsList() {
        showProgressDialog.setValue(true);
        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        getServerResponse();
        stockRepository.getItems(businessID);
    }

    public void getItemByCode(String itemCode) {
        showProgressDialog.setValue(true);

        getServerResponse();
        stockRepository.getItemByCode(itemCode);
    }


    public void getServerResponse() {
        stockRepository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {

                if (object != null) {
                    if (key == SERVER_RESPONSE) {
                        ServerResponse serverResponse = (ServerResponse) object;
                        serverLiveData.setValue(serverResponse);
                        showProgressDialog.setValue(false);
                    } else if (key == GET_DEPARTMENT) {

                        GetDepartmentResponse getDepartmentResponse = (GetDepartmentResponse) object;
                        List<Department> list = getDepartmentResponse.getDepartmentList();
                        departmentMutableLiveData.setValue(list);

                    } else if (key == SERVER_ERROR) {
                        String error = (String) object;
                        showProgressDialog.setValue(false);

                        serverErrorLiveData.setValue(error);
                    } else if (key == GET_PARTY) {

                        List<Party> partyList = (List<Party>) object;
                        partyMutableLiveData.setValue(partyList);
                    } else if (key == GET_ITEMS) {
                        showProgressDialog.setValue(false);

                        List<Item> list = (List<Item>) object;
                        adapter.setItemList(list);

                    } else if (key == GET_ITEM_BY_CODE) {
                        showProgressDialog.setValue(false);
                        itemMutableLiveData.setValue((Item) object);

                    }
                }

            }
        });

    }


}
