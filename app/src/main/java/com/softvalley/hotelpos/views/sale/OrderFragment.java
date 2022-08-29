package com.softvalley.hotelpos.views.sale;

import static com.softvalley.hotelpos.utils.CONSTANTS.ADD_CUSTOMER_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.BACK_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.ORDERING_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.ORDER_BTN;
import static com.softvalley.hotelpos.utils.CONSTANTS.PAYMENT_BTN;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.softvalley.hotelpos.databinding.CustomSelectCustomerDialogBinding;
import com.softvalley.hotelpos.databinding.FragmentOrderBinding;
import com.softvalley.hotelpos.utils.CONSTANTS;
import com.softvalley.hotelpos.utils.DialogUtil;
import com.softvalley.hotelpos.views.sale.adapter.PartyAdapter;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding mBinding;
    private OrderViewModel viewModel;
    private NavController navController;
    private AlertDialog progressDialog;
    private boolean isOrderedBtnClick;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentOrderBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        navController = NavHostFragment.findNavController(this);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
        mBinding.setViewModel(viewModel);

        getLiveData();

        if (getArguments()!=null)
        {
            viewModel.setTable(OrderFragmentArgs.fromBundle(getArguments()).getTable());
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        viewModel.getToastMessage().setValue(null);
        viewModel.getBtnAction().setValue(-1);
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

        viewModel.getProduct().observe(getViewLifecycleOwner(), product -> {
            if (product != null) {
                mBinding.btnOrdering.setTextSize(18f);
                mBinding.btnOrdering.setTypeface(mBinding.btnOrdering.getTypeface(), Typeface.BOLD);
                isOrderedBtnClick = false;
                mBinding.btnOrdered.setTextSize(12f);
                mBinding.btnOrdered.setTypeface(mBinding.btnOrdered.getTypeface(), Typeface.NORMAL);
                mBinding.btnSend.setVisibility(View.VISIBLE);
                mBinding.btnPayment.setVisibility(View.GONE);
                mBinding.rvOrderedProduct.setVisibility(View.GONE);
                mBinding.rvOrderingProduct.setVisibility(View.VISIBLE);
                viewModel.setOrderingListProduct(product);
            }
        });

        viewModel.getBtnAction().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (ADD_CUSTOMER_BTN == integer) {
                    showCustomerDialog();
                } else if (ORDER_BTN == integer) {
                    mBinding.btnOrdered.setTextSize(18f);
                    mBinding.btnOrdered.setTypeface(mBinding.btnOrdered.getTypeface(), Typeface.BOLD);
                    mBinding.btnOrdering.setTextSize(12f);
                    mBinding.btnOrdering.setTypeface(mBinding.btnOrdering.getTypeface(), Typeface.NORMAL);
                    mBinding.btnSend.setVisibility(View.GONE);
                    mBinding.btnPayment.setVisibility(View.VISIBLE);
                    mBinding.rvOrderedProduct.setVisibility(View.VISIBLE);
                    mBinding.rvOrderingProduct.setVisibility(View.GONE);
                } else if (ORDERING_BTN == integer) {
                    mBinding.btnOrdering.setTextSize(18f);
                    mBinding.btnOrdering.setTypeface(mBinding.btnOrdering.getTypeface(), Typeface.BOLD);
                    isOrderedBtnClick = false;
                    mBinding.btnOrdered.setTextSize(12f);
                    mBinding.btnOrdered.setTypeface(mBinding.btnOrdered.getTypeface(), Typeface.NORMAL);
                    mBinding.btnSend.setVisibility(View.VISIBLE);
                    mBinding.btnPayment.setVisibility(View.GONE);
                    mBinding.rvOrderedProduct.setVisibility(View.GONE);
                    mBinding.rvOrderingProduct.setVisibility(View.VISIBLE);
                }else if (BACK_BTN==integer)
                {
                    requireActivity().onBackPressed();
                }
                else if(PAYMENT_BTN== integer)
                {
                    if (integer!=-1) {
                        navController.navigate(OrderFragmentDirections.actionOrderFragmentToPaymentFragment(viewModel.getDocumentMutableLiveData().getValue()));
                    }
                }
            }
        });

    }

    private void showCustomerDialog() {
        PartyAdapter adapter = new PartyAdapter(viewModel);
        CustomSelectCustomerDialogBinding dialogBinding = CustomSelectCustomerDialogBinding.inflate(getLayoutInflater());

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();

        dialogBinding.setViewModel(viewModel);

        alertDialog.show();

        dialogBinding.rvCustomer.setLayoutManager(new LinearLayoutManager(requireContext()));
        dialogBinding.rvCustomer.setAdapter(adapter);
        viewModel.getCustomer();
        viewModel.getPartyList().observe(getViewLifecycleOwner(), parties -> {
            if (parties != null) {
                adapter.setPartyListFull(parties);
            }
        });
        dialogBinding.svSelectedCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(editable);
            }
        });
        dialogBinding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());

        dialogBinding.btnSave.setOnClickListener(v -> {
            alertDialog.dismiss();
            viewModel.setCustomerID();
        });


    }

}