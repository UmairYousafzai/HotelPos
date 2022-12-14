package com.softvalley.hotelpos.viewModel;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_PARTY;
import static com.softvalley.hotelpos.utils.CONSTANTS.LOGIN_RESPONSE;
import static com.softvalley.hotelpos.utils.CONSTANTS.LOGIN_RESPONSE_ERROR;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.softvalley.hotelpos.Repository.AuthenticationRepository;
import com.softvalley.hotelpos.models.GetPartiesServerResponse;
import com.softvalley.hotelpos.models.LoginResponse;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.models.User;
import com.softvalley.hotelpos.utils.DataRepository;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;

import java.util.List;

public class AuthenticationViewModel extends BaseViewModel {
    private final AuthenticationRepository repository;
    private final DataRepository dbRepository;
    private final MutableLiveData<User> userMutableLiveData;
    private final MutableLiveData<String> loginErrorMutableLiveData;
    private final MutableLiveData<Integer> btnAction;
    private ObservableField<String> userName;
    private ObservableField<String> password;
    private final MutableLiveData<String> userNameError;
    private final MutableLiveData<String> passwordError;
    private final MutableLiveData<Boolean> moveToHomeScreenLiveData;
    private int partyCount = 0;


    public AuthenticationViewModel(@NonNull Application application) {
        super(application);

        dbRepository = new DataRepository(getApplication());
        repository = new AuthenticationRepository();
        userMutableLiveData = new MutableLiveData<>();
        loginErrorMutableLiveData = new MutableLiveData<>("");
        moveToHomeScreenLiveData = new MutableLiveData<>();
        btnAction = new MutableLiveData<>();
        userName = new ObservableField<>("");
        password = new ObservableField<>("");
        userNameError = new MutableLiveData<>("");
        passwordError = new MutableLiveData<>("");
        getServerResponse();
    }

    public void onClick(int key) {
        btnAction.setValue(key);
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public void setPassword(ObservableField<String> password) {
        this.password = password;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }


    public ObservableField<String> getUserName() {
        return userName;
    }

    public void setUserName(ObservableField<String> userName) {
        this.userName = userName;
    }

    public MutableLiveData<String> getLoginErrorMutableLiveData() {
        return loginErrorMutableLiveData;
    }

    public MutableLiveData<Integer> getBtnAction() {
        return btnAction;
    }

    public MutableLiveData<String> getUserNameError() {
        return userNameError;
    }

    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }

    public MutableLiveData<Boolean> getMoveToHomeScreenLiveData() {
        return moveToHomeScreenLiveData;
    }

    /*************************** DB Calls *************************/
    public void insertUser(User user) {

        dbRepository.insertUser(user);

    }


    public void insertParties(List<Party> partyList) {

        dbRepository.insertParties(partyList);

    }

    /*************************** network Calls *************************/


    public void login() {

        if (validateUserNameAndPassword()) {
            repository.login(userName.get().trim(), password.get());
            setShowProgressDialog(true);
        }


    }

    private boolean validateUserNameAndPassword() {
        boolean flag;
        if (userName.get().isEmpty()) {
            userNameError.setValue("Please enter username");
            flag = false;
        } else {
            userNameError.setValue("");
            flag = true;
        }
        if (password.get().isEmpty()) {
            passwordError.setValue("Please enter password");
            flag = false;
        } else {
            passwordError.setValue("");
            flag = true;
        }
        return flag;
    }


    private void getServerResponse() {

        repository.setCallBackListener((object, key) -> {
            if (object != null) {
                if (key == GET_PARTY) {
                    partyCount += 1;
                    GetPartiesServerResponse partyServerResponse = (GetPartiesServerResponse) object;
                    insertParties(partyServerResponse.getPartyList());
                    if (partyCount == 2) {
                        moveToHomeScreenLiveData.setValue(true);
                        setShowProgressDialog(false);
                    }
                } else if (key == SERVER_ERROR) {
                    toastMessage.setValue((String) object);
                    setShowProgressDialog(false);
                } else if (key == LOGIN_RESPONSE) {
                    LoginResponse loginResponse = (LoginResponse) object;

                    userMutableLiveData.setValue(loginResponse.getUser());
                    toastMessage.setValue(loginResponse.getMessage());
//                    String businessId = SharedPreferenceHelper.getInstance(getApplication()).getBUSINESS_ID();
//                    repository.getPartiesFromServer(businessId);
                } else if (key == LOGIN_RESPONSE_ERROR) {
                    toastMessage.setValue((String) object);
                    loginErrorMutableLiveData.setValue((String) object);
                    setShowProgressDialog(false);
                }
            }

        });
    }
}
