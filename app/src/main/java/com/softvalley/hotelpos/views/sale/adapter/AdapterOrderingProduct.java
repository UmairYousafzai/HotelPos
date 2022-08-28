package com.softvalley.hotelpos.views.sale.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softvalley.hotelpos.databinding.ItemOrderingProductsBinding;
import com.softvalley.hotelpos.models.Item;
import com.softvalley.hotelpos.models.response.product.Product;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrderingProduct extends RecyclerView.Adapter<AdapterOrderingProduct.ViewHolder> {
    private List<Product> itemList;
    private LayoutInflater layoutInflater;
    private OrderViewModel viewModel;

    public AdapterOrderingProduct(OrderViewModel orderViewModel) {
        this.viewModel= orderViewModel;
        this.itemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater==null)
        {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        ItemOrderingProductsBinding binding = ItemOrderingProductsBinding.inflate(layoutInflater,viewGroup,false);

        return  new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Product item = itemList.get(i);
        holder.mBinding.setModel(item);
        holder.mBinding.setPosition(i);
        holder.mBinding.setViewModel(viewModel);
//        holder.mBinding.txtProductName.setText(item.getDescription());
//        holder.mBinding.tvPrice.setText( String.valueOf(item.getUnitRetail()));
//        holder.mBinding.tvTotalAmount.setText( String.valueOf(item.getAmount()));
//        holder.mBinding.txtQuantityProduct.setText(String.valueOf(item.getQty()));
//        holder.mBinding.lyAddItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onAddClick(i, itemList.get(i));
//
//            }
//        });
//        holder.mBinding.lyReduceItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reducelistener.onReduceClick(i, itemList.get(i));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<Product> list)
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

    public void addProduct(Product product,int position)
    {
        itemList.add(position,product);
        notifyItemChanged(position);
    }

    public List<Product> getItemList() {
        return itemList;
    }

    public void clearList() {
        itemList.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ItemOrderingProductsBinding mBinding;

        ViewHolder(@NonNull ItemOrderingProductsBinding binding) {
            super(binding.getRoot());

            mBinding= binding;

        }
    }
}

