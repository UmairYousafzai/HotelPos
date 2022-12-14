package com.softvalley.hotelpos.views.sale;

import static com.softvalley.hotelpos.utils.CONSTANTS.DOCUMENT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.softvalley.hotelpos.databinding.FragmentSaleDocListBinding;
import com.softvalley.hotelpos.utils.DialogUtil;
import com.softvalley.hotelpos.views.sale.viewModel.SaleDocListViewModel;


public class SaleDocListFragment extends Fragment {

    private FragmentSaleDocListBinding mBinding;
    private SaleDocListViewModel viewModel;
    private NavController navController ;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding= FragmentSaleDocListBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= new ViewModelProvider(this).get(SaleDocListViewModel.class);
        navController= NavHostFragment.findNavController(this);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());

        mBinding.setSaleViewModel(viewModel);



        getLiveData();
    }

    private void getLiveData() {
        if (getArguments()!=null)
        {
            viewModel.getDocument(SaleDocListFragmentArgs.fromBundle(getArguments()).getDocType());
            ((AppCompatActivity)requireActivity()).getSupportActionBar().setTitle(
                    SaleDocListFragmentArgs.fromBundle(getArguments()).getHeader()
            );
        }

        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), showProgressDialog -> {

            if (showProgressDialog) {
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        });
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), message -> {

            if (message!=null)
            {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getDocumentMutableLiveData().observe(getViewLifecycleOwner(), document -> {
            if (document !=null)
            {
                if (navController.getPreviousBackStackEntry()!=null)
                {
                    navController.getPreviousBackStackEntry().getSavedStateHandle().set(DOCUMENT, document);
                    navController.popBackStack();

                }
            }
        });

    }
}