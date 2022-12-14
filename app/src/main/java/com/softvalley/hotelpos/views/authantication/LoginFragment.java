package com.softvalley.hotelpos.views.authantication;

import static com.softvalley.hotelpos.utils.CONSTANTS.LOGIN_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.NAVIGATE_SIGNUP_BTN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.softvalley.hotelpos.R;
import com.softvalley.hotelpos.databinding.FragmentLoginBinding;
import com.softvalley.hotelpos.utils.DataViewModel;
import com.softvalley.hotelpos.utils.DialogUtil;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.viewModel.AuthenticationViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding mBinding;
    private NavController navController;
    private DataViewModel dataViewModel;
    private AuthenticationViewModel viewModel;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentLoginBinding.inflate(inflater, container, false);


        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());

        viewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        mBinding.setViewModel(viewModel);

        liveDataObserver();


    }

    private void liveDataObserver() {
        viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), user -> {

            if (user != null) {
                SharedPreferenceHelper.getInstance(requireContext()).setUserID(user.getUserId());
                SharedPreferenceHelper.getInstance(requireContext()).setBUSINESS_ID(user.getBusinessId());
                SharedPreferenceHelper.getInstance(requireContext()).setBusinessName(user.getBusinessName());
                SharedPreferenceHelper.getInstance(requireContext()).setLocationID(user.getLocationId());
                SharedPreferenceHelper.getInstance(requireContext()).setDefaultPartyID(user.getDefaultPartyCode());
                SharedPreferenceHelper.getInstance(requireContext()).setIsLogin(true);
                viewModel.insertUser(user);
                navController.navigate(R.id.action_loginFragment_to_tableListFragment);

            }
        });

        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                if (flag) {
                    progressDialog.show();
                } else {
                    progressDialog.dismiss();
                }
            }
        });

        viewModel.getLoginErrorMutableLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    mBinding.etUserNameLayout.setError("Invalid");
                    mBinding.etPasswordLayout.setError("Invalid");
                    mBinding.etUserName.requestFocus();
                }
            }
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getBtnAction().observe(getViewLifecycleOwner(), key -> {

            if (LOGIN_BTN == key) {
                viewModel.login();
            } else if (NAVIGATE_SIGNUP_BTN==key){
                NavDirections navDirections = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
                navController.navigate(navDirections);
            }
        });

        viewModel.getPasswordError().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                mBinding.etPasswordLayout.setError(message);
            }

        });
        viewModel.getUserNameError().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                mBinding.etUserNameLayout.setError(message);
            }

        });
        viewModel.getMoveToHomeScreenLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean flag) {
                if (flag)
                {
                    navController.navigate(R.id.action_loginFragment_to_tableListFragment);

                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.getMoveToHomeScreenLiveData().setValue(false);
        viewModel.getUserNameError().setValue(null);
        viewModel.getPasswordError().setValue(null);
        viewModel.getBtnAction().setValue(0);
    }
}