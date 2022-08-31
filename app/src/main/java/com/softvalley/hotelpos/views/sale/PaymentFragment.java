package com.softvalley.hotelpos.views.sale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.softvalley.hotelpos.databinding.FragmentPaymentBinding;
import com.softvalley.hotelpos.utils.DialogUtil;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;
import com.softvalley.hotelpos.views.sale.viewModel.PaymentViewModel;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;
    private PaymentViewModel viewModel;
    private AlertDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
        binding.setViewModel(viewModel);

        if(getArguments()!=null)
        {
            viewModel.setDocument(PaymentFragmentArgs.fromBundle(getArguments()).getDocumentOrder());
        }

        getLiveData();


    }


    @Override
    public void onStop() {
        super.onStop();

//        viewModel.getToastMessage().setValue(null);
    }

    private void getLiveData() {


        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != null) {
                    Snackbar.make(requireView(),s,Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showProgressDialog) {

                if (showProgressDialog) {
                    progressDialog.show();
                } else {
                    progressDialog.dismiss();
                }
            }
        });


    }

//    private void showCustomerDialog() {
//        PartyAdapter adapter = new PartyAdapter(viewModel);
//        CustomSelectCustomerDialogBinding dialogBinding = CustomSelectCustomerDialogBinding.inflate(getLayoutInflater());
//
//        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot())
//                .setCancelable(false)
//                .create();
//
//        dialogBinding.setViewModel(viewModel);
//
//        alertDialog.show();
//
//        dialogBinding.rvCustomer.setLayoutManager(new LinearLayoutManager(requireContext()));
//        dialogBinding.rvCustomer.setAdapter(adapter);
//        viewModel.getCustomer();
//        viewModel.getPartyList().observe(getViewLifecycleOwner(), parties -> {
//            if (parties != null) {
//                adapter.setPartyListFull(parties);
//            }
//        });
//        dialogBinding.svSelectedCustomer.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                adapter.getFilter().filter(editable);
//            }
//        });
//        dialogBinding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());
//
//        dialogBinding.btnSave.setOnClickListener(v -> {
//            alertDialog.dismiss();
//            viewModel.setCustomerID();
//        });
//
//
//    }

}