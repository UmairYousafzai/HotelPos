package com.softvalley.hotelpos.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.softvalley.hotelpos.databinding.FragmentEditBusinessNameBottomSheetBinding;
import com.softvalley.hotelpos.utils.CONSTANTS;

public class EditBusinessNameBottomSheetFragment extends BottomSheetDialogFragment
{

    private FragmentEditBusinessNameBottomSheetBinding mBinding;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentEditBusinessNameBottomSheetBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= NavHostFragment.findNavController(this);


        btnListener();
    }



    private void btnListener() {


        mBinding.btnSave.setOnClickListener(v -> {
            String businessName=" ";
            if (mBinding.etBusinessName.getText()!=null)
            {
                 businessName= mBinding.etBusinessName.getText().toString();

            }

            if (navController.getPreviousBackStackEntry()!=null)
            {
                navController.getPreviousBackStackEntry().getSavedStateHandle().set(CONSTANTS.BUSINESS_NAME,businessName);
                navController.popBackStack();
            }
            else
            {
                Toast.makeText(requireContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
                Log.e("Previous BAck Stack: ","Null");
            }



        });
    }
}