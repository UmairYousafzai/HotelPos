package com.softvalley.hotelpos.views.table.viewModel;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_TABLE_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SAVE_TABLE_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.softvalley.hotelpos.models.response.table.SaveTableResponse;
import com.softvalley.hotelpos.models.response.table.Table;
import com.softvalley.hotelpos.models.response.table.TableResponse;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.table.adapter.AdapterTables;
import com.softvalley.hotelpos.views.table.repository.TableRepository;

import java.util.List;

public class TableViewModel extends AndroidViewModel {
    private final TableRepository repository;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<List<Table>> tableLiveData;
    private final MutableLiveData<Boolean> showProgressDialog;

    public TableViewModel(@NonNull Application application) {
        super(application);
        repository = new TableRepository();
        toastMessage = new MutableLiveData<>();
        tableLiveData = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        getServerResponse();

    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public MutableLiveData<List<Table>> getTableLiveData() {
        return tableLiveData;
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }
    public void getTables()
    {
//        String businessID = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        showProgressDialog.setValue(true);
        repository.getTables("0000000001");
    }

    public void saveTable(Table table)
    {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        showProgressDialog.setValue(true);
        repository.saveTable(businessID,table);
    }
    private void getServerResponse() {
        repository.setCallBackListener((object, key) -> {
            if (object != null) {
                if (key == GET_TABLE_RESPONSE) {
                    TableResponse tableResponse = (TableResponse) object;
                    tableLiveData.setValue(tableResponse.getTableList());
                    toastMessage.setValue(tableResponse.getMessage());
                    showProgressDialog.setValue(false);
                } else if (key == SAVE_TABLE_RESPONSE) {
                    SaveTableResponse tableResponse= (SaveTableResponse) object;
                    toastMessage.setValue(tableResponse.getMessage());
                    showProgressDialog.setValue(false);
                }else if (key == SERVER_ERROR) {
                    toastMessage.setValue((String) object);
                    showProgressDialog.setValue(false);
                }
            }
        });
    }


}
