package com.softvalley.hotelpos.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.softvalley.hotelpos.MainActivity;
import com.softvalley.hotelpos.R;
import com.softvalley.hotelpos.databinding.FragmentMoreBinding;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;

public class MoreFragment extends Fragment {


    private FragmentMoreBinding mBinding;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentMoreBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);

        btListener();
        setBusinessName();
    }

    private void setBusinessName() {
        String businessName= SharedPreferenceHelper.getInstance(requireContext()).getBusinessName();

        mBinding.etBusinessName.setText(businessName);

    }
    private void btListener() {

        mBinding.stockCard.setOnClickListener(view -> navController.navigate(R.id.action_moreFragment_to_itemListFragment));
        mBinding.purchaseCard.setOnClickListener(view -> navController.navigate(R.id.action_moreFragment_to_purchaseFragment));
        mBinding.saleCard.setOnClickListener(view -> navController.navigate(R.id.action_moreFragment_to_saleReturnDocFragment));
        mBinding.btnLogout.setOnClickListener(view -> {


            ((MainActivity) requireActivity()).signOut();
        });
    }
}