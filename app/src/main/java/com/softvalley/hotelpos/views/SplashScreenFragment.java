package com.softvalley.hotelpos.views;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.softvalley.hotelpos.BuildConfig;
import com.softvalley.hotelpos.databinding.FragmentSplashScreenBinding;
import com.softvalley.hotelpos.utils.DataViewModel;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;


public class SplashScreenFragment extends Fragment {

    private FragmentSplashScreenBinding mBinding;
    private NavController navController;
    private DataViewModel dataViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentSplashScreenBinding.inflate(inflater,container,false);

//        MeowBottomNavigation navBar = requireActivity().findViewById(R.id.bottom_view);
//
//        if (navBar!=null)
//        {
//            navBar.setVisibility(View.GONE);
//        }


        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        dataViewModel= new ViewModelProvider(this).get(DataViewModel.class);
        dataViewModel.deleteParties();

        mBinding.versionName.setText(String.format("Version %s", BuildConfig.VERSION_NAME));



        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                requireActivity().finishAffinity();
                        }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
//        if (((AppCompatActivity) requireActivity()).getSupportActionBar()!=null)
//        {
//            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
//
//        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkLogin();
                dataViewModel.getAndSaveParties();
            }
        },500);

    }

    public void checkLogin()
    {

        boolean isLogin= SharedPreferenceHelper.getInstance(requireContext()).getIS_Login();
        NavDirections navDirections;
        if (isLogin)
        {
            navDirections = SplashScreenFragmentDirections.actionSplashScreenFragmentToTableListFragment();

        }
        else
        {
            navDirections = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment();

        }
        navController.navigate(navDirections);
    }
}