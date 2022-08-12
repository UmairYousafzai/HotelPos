package com.softvalley.hotelpos.views.sale.viewModel;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DOCUMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.softvalley.hotelpos.Interface.CallBackListener;
import com.softvalley.hotelpos.models.Document;
import com.softvalley.hotelpos.models.GetDocumentResponse;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.sale.adapter.SaleDocListListAdapter;
import com.softvalley.hotelpos.views.sale.repository.SaleRepository;

public class SaleDocListViewModel extends AndroidViewModel {


    private final SaleRepository repository;
    private final SaleDocListListAdapter adapter;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Document> documentMutableLiveData;
    private final MutableLiveData<Boolean> showProgressDialog;


    public SaleDocListViewModel(@NonNull Application application) {
        super(application);
        repository = new SaleRepository();
        adapter= new SaleDocListListAdapter(this);
        toastMessage= new MutableLiveData<>();
        documentMutableLiveData = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
    }

    public void onClick(Document document)
    {
//        if (document.getStatus().equals("Unauthorize"))
//        {
//            documentMutableLiveData.setValue(document);
//
//        }
//        else
//        {
//            toastMessage.setValue("Authorized Documents Cannot be Edit.");
//        }

        documentMutableLiveData.setValue(document);

    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }

    public MutableLiveData<Document> getDocumentMutableLiveData() {
        return documentMutableLiveData;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public SaleDocListListAdapter getAdapter() {
        return adapter;
    }

    public void getDocument(int type)
    {
        showProgressDialog.setValue(true);
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();

        repository.getSaleDocs(type,businessID);
        getServerResponse();
    }

    private void getServerResponse() {

        repository.setCallBackListener(new CallBackListener() {
            @Override
            public void getServerResponse(Object object, int key) {
                if (object!=null)
                {
                    if (key== GET_DOCUMENT)
                    {
                        GetDocumentResponse purchaseResponse= (GetDocumentResponse) object;

                        adapter.setPurchaseList(purchaseResponse.getPurchaseList());
                        showProgressDialog.setValue(false);
                    }
                    else if (key== SERVER_ERROR)
                    {
                        toastMessage.setValue((String) object);
                        showProgressDialog.setValue(false);
                    }
                }
            }
        });
    }
}
