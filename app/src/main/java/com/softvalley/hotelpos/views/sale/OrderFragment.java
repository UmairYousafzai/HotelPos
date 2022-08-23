package com.softvalley.hotelpos.views.sale;

import android.graphics.Typeface;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.softvalley.hotelpos.databinding.FragmentOrderBinding;
import com.softvalley.hotelpos.utils.DialogUtil;
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

        mBinding = FragmentOrderBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= new ViewModelProvider(this).get(OrderViewModel.class);
        navController = NavHostFragment.findNavController(this);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());
        ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
        mBinding.setViewModel(viewModel);

        getLiveData();
    }


    @Override
    public void onStop() {
        super.onStop();

        viewModel.getToastMessage().setValue(null);
    }

    private void getLiveData() {





        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s!=null)
                {
                    Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showProgressDialog) {

                if (showProgressDialog)
                {
                    progressDialog.show();
                }
                else
                {
                    progressDialog.dismiss();
                }
            }
        });

        viewModel.getProduct().observe(getViewLifecycleOwner(), product -> {
            if (product!=null)
            {
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




    }
//    private void btnListeners() {
//
//        mBinding.btnOrdered.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mBinding.btnOrdered.setTextSize(18f);
//                mBinding.btnOrdered.setTypeface(mBinding.btnOrdered.getTypeface(), Typeface.BOLD);
//                mBinding.btnOrdering.setTextSize(12f);
//                mBinding.btnOrdering.setTypeface(mBinding.btnOrdering.getTypeface(), Typeface.NORMAL);
//                adapterOrderedProducts.setOrderList(orderedProductsList);
//                mBinding.btnSend.setVisibility(View.GONE);
//                mBinding.btnPayment.setVisibility(View.VISIBLE);
//                mBinding.rvOrderedProduct.setVisibility(View.VISIBLE);
//                mBinding.rvOrderingProduct.setVisibility(View.GONE);
//                quantity=0;
//                payment=0;
//                for (MDOrder model : orderedProductsList) {
//                    quantity = quantity + 1;
//                    payment = payment + (Double.parseDouble(model.getUNIT_RETAIL()) * model.getUnitSize());
//
//
//                }
//                mBinding.txtSubTotal.setText(String.valueOf(payment));
//                mBinding.txtQuantityProduct.setText(String.valueOf(quantity));
//
//            }
//        });
//        mBinding.btnOrdering.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mBinding.btnOrdering.setTextSize(18f);
//                mBinding.btnOrdering.setTypeface(mBinding.btnOrdering.getTypeface(), Typeface.BOLD);
//                isOrderedBtnClick = false;
//                mBinding.btnOrdered.setTextSize(12f);
//                mBinding.btnOrdered.setTypeface(mBinding.btnOrdered.getTypeface(), Typeface.NORMAL);
//                mBinding.btnSend.setVisibility(View.VISIBLE);
//                mBinding.btnPayment.setVisibility(View.GONE);
//                mBinding.rvOrderedProduct.setVisibility(View.GONE);
//                mBinding.rvOrderingProduct.setVisibility(View.VISIBLE);
//                adapterOrderingProduct.setOrderList(orderingProductsList);
//                quantity=0;
//                payment=0;
//                for (MDOrder model : orderingProductsList) {
//                    quantity = quantity + 1;
//                    payment = payment + (Double.parseDouble(model.getUNIT_RETAIL()) * model.getUnitSize());
//
//
//                }
//                mBinding.txtSubTotal.setText(String.valueOf(payment));
//                mBinding.txtQuantityProduct.setText(String.valueOf(quantity));
//
//            }
//        });
//
//        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isSave) {
//
//                    showConfirmationDialog();
//                } else {
//                    navController.popBackStack();
//
//                }
//
//
//            }
//        });
//
//
//        mBinding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                dataViewModel.insertAllOrder(orderingProductsList);
//                orderedProductsList.addAll(orderingProductsList);
//                orderingProductsList.clear();
//                adapterOrderingProduct.setOrderList(orderingProductsList);
//
//                mBinding.txtQuantityProduct.setText("0.0");
//                mBinding.txtSubTotal.setText("0.0");
//
//                isSave = false;
//                SharedPreferenceHelper.getInstance(requireContext()).setIsReserved(mdTable.getTablesNAME(), true);
//
//                mdTable.setReserved(true);
//
//
//            }
//        });
//    }


}