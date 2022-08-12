package com.softvalley.hotelpos.views.table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.softvalley.hotelpos.databinding.FragmentTablesListBinding;
import com.softvalley.hotelpos.models.response.table.Table;
import com.softvalley.hotelpos.views.table.adapter.AdapterTables;
import com.softvalley.hotelpos.views.table.viewModel.TableViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TableListFragment extends Fragment {

    private FragmentTablesListBinding binding;
    private NavController navController;
    private AdapterTables adapterTables;
    private List<Table> tableList = new ArrayList<>();
    private TableViewModel viewModel;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentTablesListBinding.inflate(inflater,container,false);

       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = NavHostFragment.findNavController(this);
        viewModel= new ViewModelProvider(this).get(TableViewModel.class);
        viewModel.getTables();
        setUpRecyclerView();
        liveDataListener();

    }

    private void liveDataListener() {
        viewModel.getTableLiveData().observe(getViewLifecycleOwner(), tables -> {
            if (tables!=null)
            {
                adapterTables.setTableModelList(tables);
            }
        });
    }

    private void setUpRecyclerView() {

         adapterTables = new AdapterTables(requireContext());
        binding.rvTables.setAdapter(adapterTables);
        binding.rvTables.setLayoutManager(new GridLayoutManager(requireContext(), 3));
    }

}