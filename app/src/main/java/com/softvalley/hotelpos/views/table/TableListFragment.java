package com.softvalley.hotelpos.views.table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.softvalley.hotelpos.databinding.CustomAddTableDialogBinding;
import com.softvalley.hotelpos.databinding.CustomProductDialogSaleBinding;
import com.softvalley.hotelpos.databinding.FragmentTablesListBinding;
import com.softvalley.hotelpos.models.response.table.Table;
import com.softvalley.hotelpos.utils.DialogUtil;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;
import com.softvalley.hotelpos.views.table.adapter.AdapterTables;
import com.softvalley.hotelpos.views.table.viewModel.TableViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TableListFragment extends Fragment implements AdapterTables.TableClickListener {

    private FragmentTablesListBinding binding;
    private NavController navController;
    private AdapterTables adapterTables;
    private List<Table> tableList = new ArrayList<>();
    private TableViewModel viewModel;
    private AlertDialog progressDialog;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTablesListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(this).get(TableViewModel.class);
        progressDialog = DialogUtil.getInstance().getProgressDialog(requireContext());

        viewModel.getTables();
        setUpRecyclerView();
        liveDataListener();
        btnListener();

    }

    private void btnListener() {

        binding.btnAdd.setOnClickListener((view -> {
            showAddTableDialog();
        }));
        binding.swipeLayout.setOnRefreshListener(() -> {
            binding.swipeLayout.setRefreshing(false);
            viewModel.getTables();
        });
    }

    private void showAddTableDialog() {


            CustomAddTableDialogBinding dialogBinding = CustomAddTableDialogBinding.inflate(getLayoutInflater());

            AlertDialog alertDialog= new AlertDialog.Builder(requireContext()).setView(dialogBinding.getRoot())
                    .setCancelable(false)
                    .create();
            alertDialog.show();
            setupProductStatusDialog(dialogBinding);

            dialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            dialogBinding.btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status;
                    if (dialogBinding.statusSpinner.getText()!=null &&
                            dialogBinding.statusSpinner.getText().toString().equals("Active"))
                    {
                       status= "a";
                    }else
                    {
                        status="i";
                    }
                    if (dialogBinding.etTableName.getText()!=null &&
                    dialogBinding.etTableName.getText().toString().isEmpty())
                    {
                        dialogBinding.etNameLayout.setError("Please enter the name");

                    }else
                    {
                        String userId= SharedPreferenceHelper.getInstance(requireContext()).getUserID();
                        String businessID= SharedPreferenceHelper.getInstance(requireContext()).getBUSINESS_ID();
                        viewModel.saveTable(
                                new Table("","",
                                        dialogBinding.etTableName.getText().toString()
                                ,status,userId,businessID,"INSERT")
                        );
                        alertDialog.dismiss();

                    }

                }
            });



    }

    private void setupProductStatusDialog(CustomAddTableDialogBinding dialogBinding) {

        ArrayList<String> list= new ArrayList<>();
        list.add("Active");
        list.add("in-Active");
        ArrayAdapter adapter= new ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line,list);
        dialogBinding.statusSpinner.setAdapter(adapter);
    }

    private void liveDataListener() {
        viewModel.getTableLiveData().observe(getViewLifecycleOwner(), tables -> {
            if (tables != null) {
                adapterTables.setTableModelList(tables);
            }
        });

        viewModel.getToastMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s!=null)
                {
                    Snackbar.make(requireView(),s,Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getShowProgressDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

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

    private void setUpRecyclerView() {

        adapterTables = new AdapterTables(requireContext(), this);
        binding.rvTables.setAdapter(adapterTables);
        binding.rvTables.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

    @Override
    public void onClick(Table table, int noOfGuest) {
        navController.navigate( TableListFragmentDirections.actionTableListFragmentToOrderFragment(table,noOfGuest));
    }
}