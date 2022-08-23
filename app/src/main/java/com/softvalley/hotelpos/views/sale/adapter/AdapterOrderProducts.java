package com.softvalley.hotelpos.views.sale.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softvalley.hotelpos.databinding.ItemOrderProductBinding;
import com.softvalley.hotelpos.models.Group;
import com.softvalley.hotelpos.models.response.product.Product;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrderProducts extends RecyclerView.Adapter<AdapterOrderProducts.ViewHolder> {
    private final List<Product> productsList;
    private LayoutInflater layoutInflater ;
    private final OrderViewModel viewModel;



    public AdapterOrderProducts(OrderViewModel viewModel) {
        this.productsList = new ArrayList<>();
        this.viewModel=viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        ItemOrderProductBinding binding = ItemOrderProductBinding.inflate(layoutInflater,viewGroup,false);

        return  new ViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Product product = productsList.get(i);
        holder.mBinding.setModel(product);
        holder.mBinding.setViewModel(viewModel);
        holder.mBinding.executePendingBindings();


    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public void setProductsList( List<Product> list)
    {
        if (list!=null)
        {
            productsList.clear();
            productsList.addAll(list);
        }
        else
        {
            productsList.clear();
        }
        notifyDataSetChanged();
    }

    public List<Product> getProductsList() {
        return productsList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderProductBinding mBinding;
        ViewHolder(@NonNull ItemOrderProductBinding binding) {
            super(binding.getRoot());

            mBinding =binding;



        }
    }
}

