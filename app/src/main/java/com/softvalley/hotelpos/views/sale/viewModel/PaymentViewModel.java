package com.softvalley.hotelpos.views.sale.viewModel;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DOCUMENT_BY_CODE;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PARTY;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PRODUCT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SAVE_DOCUMENT_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;
import android.content.res.Resources;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.softvalley.hotelpos.R;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.GetPartiesServerResponse;
import com.softvalley.hotelpos.models.request.Order.Document;
import com.softvalley.hotelpos.models.response.order.SaveOrderResponse;
import com.softvalley.hotelpos.models.response.product.ProductResponse;
import com.softvalley.hotelpos.utils.Converter;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.sale.repository.SaleRepository;

import java.net.HttpCookie;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PaymentViewModel extends AndroidViewModel {

    private final SaleRepository saleRepository;
    private final ObservableField<String> totalAmount;
    private final ObservableField<String> payAmount;
    private final ObservableField<String> changeAmount;
    private final ObservableField<Boolean> isChangeNegative;
    private Document document;
    private MutableLiveData<Boolean> showProgressDialog;
    private MutableLiveData<String> toastMessage;

    public PaymentViewModel(@NonNull Application application) {
        super(application);
        saleRepository = new SaleRepository();
        totalAmount = new ObservableField<>("0.0");
        payAmount = new ObservableField<>("0.0");
        changeAmount = new ObservableField<>("0.0");
        isChangeNegative = new ObservableField<>(false);
        toastMessage = new MutableLiveData<>();
        showProgressDialog = new MutableLiveData<>();
        getServerResponse();
    }

    public void onClick(String key) {
        switch (key) {
            case "clear":
                clearAllFields();
                break;
            case "remove":
                StringBuilder amount = new StringBuilder(payAmount.get());
                if (amount.length() != 0 && !amount.toString().equals("0.0")) {
                    amount.deleteCharAt(amount.length() - 1);
                    payAmount.set(amount.toString());
                }

                break;
            case "pay":
                authorizeDocument();
                break;
            case "recipt":
                printPDF();
                break;
            default:
                if (key.equals(".") && !payAmount.get().contains(".")) {
                    if (payAmount.get().isEmpty()) {
                        payAmount.set("0.");
                    } else {
                        payAmount.set(payAmount.get() + key);
                    }
                } else if (!key.equals(".")) {
                    if (!payAmount.get().equals("0.0")) {
                        payAmount.set(payAmount.get() + key);
                    } else {
                        payAmount.set(key);
                    }
                    double payAmount = Double.parseDouble(this.payAmount.get());
                    double total = Double.parseDouble(totalAmount.get());
                    double change = total - payAmount;
                    if (change < 0) {
                        isChangeNegative.set(true);
                    }else {
                        isChangeNegative.set(false);
                    }
                    changeAmount.set(formatDoubleNumbers(change));
                }
//                double payAmount = toDouble(Double.parseDouble(this.payAmount.get().isEmpty()
//                        ? "0.0" : this.payAmount.get()), key);
//                this.payAmount.set(String.valueOf(payAmount));
//                double totalAmount = toDouble(0.00, this.totalAmount.get());
//                double change = totalAmount - payAmount;
//                changeAmount.set(formatDoubleNumbers(change));
//                if ( !payAmount.get().equals("0.0")) {
//
//                    payAmount.set(payAmount.get() + key);
//                }else
//                {
//                }
                break;
        }
    }

    private void printPDF() {

    }

    private void authorizeDocument() {
        document.setAction("UPDATE");
        showProgressDialog.setValue(true);
        saleRepository.saveSaleOrder(document);
    }

    private void clearAllFields() {


    }


    public ObservableField<String> getTotalAmount() {
        return totalAmount;
    }

    public ObservableField<String> getPayAmount() {
        return payAmount;
    }

    public ObservableField<String> getChangeAmount() {
        return changeAmount;
    }

    public ObservableField<Boolean> getIsChangeNegative() {
        return isChangeNegative;
    }

    public MutableLiveData<Boolean> getShowProgressDialog() {
        return showProgressDialog;
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setDocument(Document document) {
        String businessID= SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
        String userID= SharedPreferenceHelper.getInstance(getApplication()).getUserID();
        document.setBusinessId(businessID);
        document.setUserId(userID);
        document.setStatus("3");
        totalAmount.set(String.valueOf(document.getTotalAmount()));
        this.document= document;
    }


    public String formatDoubleNumbers(double num) {
        return new DecimalFormat("#0.00").format(num);
    }

    public double toDouble(double oldValue,
                           String value) {
        NumberFormat numberFormat = getNumberFormat();
        try {
            return numberFormat.parse(value).doubleValue();
        } catch (ParseException e) {
            return oldValue;
        }
    }

    private NumberFormat getNumberFormat() {
        Resources resources = getApplication().getResources();
        Locale locale = resources.getConfiguration().locale;
        NumberFormat format =
                NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            DecimalFormat decimalFormat = (DecimalFormat) format;
            decimalFormat.setGroupingUsed(false);
        }
        return format;
    }

    private void getServerResponse() {

        saleRepository.setCallBackListener((object, key) -> {
            if (object != null) {
                if (key == SERVER_ERROR) {
                    String error = (String) object;

                    toastMessage.setValue(error);
                    showProgressDialog.setValue(false);

                } else if (key == SAVE_DOCUMENT_RESPONSE) {
                    SaveOrderResponse saveDocumentResponse = (SaveOrderResponse) object;


                    toastMessage.setValue(saveDocumentResponse.getMessage());
                    showProgressDialog.setValue(false);

                }
            }
        });
    }
}
