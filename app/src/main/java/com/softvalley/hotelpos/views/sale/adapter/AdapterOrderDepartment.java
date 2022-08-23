package com.softvalley.hotelpos.views.sale.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softvalley.hotelpos.databinding.ItemOrderDepartmentBinding;
import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterOrderDepartment extends RecyclerView.Adapter<AdapterOrderDepartment.ViewHolder> {
    private List<Department> departmentList;
    private LayoutInflater layoutInflater;
    private OrderViewModel viewModel;

    public AdapterOrderDepartment(OrderViewModel viewModel) {
        this.viewModel = viewModel;
        this.departmentList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        ItemOrderDepartmentBinding binding = ItemOrderDepartmentBinding.inflate(layoutInflater, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Department department = departmentList.get(i);
        holder.mBinding.setModel(department);
        holder.mBinding.setViewModel(viewModel);
        holder.mBinding.executePendingBindings();
        holder.mBinding.parentLayout.setBackgroundColor(getRandomDarkColor());
    }

    @Override
    public int getItemCount() {
        return departmentList.size();
    }

    public void setDepartmentList(List<Department> list) {
        System.out.println();
        if (list != null) {
            departmentList.clear();
            departmentList.addAll(list);
        } else {
            departmentList.clear();
        }
        notifyDataSetChanged();
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemOrderDepartmentBinding mBinding;

        ViewHolder(@NonNull ItemOrderDepartmentBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

        }
    }

    public int getRandomDarkColor() {
        int randColor = getRandomColor();
        return darker(randColor, 0.75f);
    }

    public int getRandomColor() {
        Random randomGenerator = new Random();
        return Color.rgb(randomGenerator.nextInt(255),
                randomGenerator.nextInt(255), randomGenerator.nextInt(255));
    }

    public int darker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        return Color.argb(a, Math.max((int) (r * factor), 0),
                Math.max((int) (g * factor), 0),
                Math.max((int) (b * factor), 0));
    }
}
