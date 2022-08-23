package com.softvalley.hotelpos.views.sale.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softvalley.hotelpos.databinding.ItemOrderedProductBinding;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrderedProducts extends RecyclerView.Adapter<AdapterOrderedProducts.ViewHolder> {
    private List<Item> itemList;
    private OrderViewModel viewModel;
    private LayoutInflater layoutInflater;

    //public interface setOnLongClick

    public AdapterOrderedProducts(OrderViewModel viewModel) {
        this.viewModel= viewModel;
        this.itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater==null)
        {
            layoutInflater =LayoutInflater.from(viewGroup.getContext());
        }

        ItemOrderedProductBinding binding = ItemOrderedProductBinding.inflate(layoutInflater,viewGroup,false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Item item = itemList.get(i);
        holder.mBinding.txtProductName.setText(item.getDescription());
        holder.mBinding.tvProductPrice.setText(String.valueOf(item.getUnitRetail()));
        holder.mBinding.txtQuantityProduct.setText(String.valueOf(item.getQty()));
        double totalAmount= item.getQty()*item.getUnitRetail();
        holder.mBinding.tvTotalAmount.setText(String.valueOf(totalAmount));


    }
    public void setItemList(List<Item> list)
    {
        if (list!=null)
        {
            itemList.clear();
            itemList.addAll(list);
        }
        if (list != null && list.size() == 0) {
            itemList.clear();
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemOrderedProductBinding mBinding;
        ViewHolder(@NonNull ItemOrderedProductBinding binding) {
            super(binding.getRoot());

            mBinding= binding;

        }
    }

    public List<Item> getItemList() {
        return itemList;
    }
}

