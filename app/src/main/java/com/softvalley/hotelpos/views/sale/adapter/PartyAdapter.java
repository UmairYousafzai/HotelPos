package com.softvalley.hotelpos.views.sale.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softvalley.hotelpos.databinding.ItemOrderDepartmentBinding;
import com.softvalley.hotelpos.databinding.PartyCardBinding;
import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.models.Party;
import com.softvalley.hotelpos.views.sale.viewModel.OrderViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.ViewHolder> implements Filterable {
    private List<Party> partyList;
    private LayoutInflater layoutInflater;
    private final OrderViewModel viewModel;
    private List<Party> partyListFull = new ArrayList<>();

    public PartyAdapter(OrderViewModel viewModel) {
        this.viewModel = viewModel;
        this.partyList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        PartyCardBinding binding = PartyCardBinding.inflate(layoutInflater, viewGroup, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Party party = partyList.get(i);
        holder.mBinding.setParty(party);
        String name= party.getPartyName().substring(0,1);
        holder.mBinding.tvPartNameDrawable.setText(name);
        holder.mBinding.tv.setVisibility(View.GONE);
        holder.mBinding.tvClosingBalance.setVisibility(View.GONE);
        holder.mBinding.tvDateTime.setVisibility(View.GONE);
        holder.mBinding.executePendingBindings();
        holder.mBinding.partyCard.setOnClickListener((view)-> viewModel.onClickCustomer(partyList.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return partyList.size();
    }

    public void setPartyListFull(List<Party> list)
    {
        if (list!=null)
        {
            partyListFull.clear();
            partyList.clear();
            partyListFull.addAll(list);
            partyList.addAll(list);
        }
        else
        {
            partyListFull.clear();

        }
        notifyDataSetChanged();
    }
    public List<Party> getPartyList() {
        return partyList;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Party> filterList= new ArrayList<>();
            if (constraint!=null&&constraint.length()>0)
            {
                String filterPattern= constraint.toString().toLowerCase().trim();
                for (Party party:partyListFull)
                {
                    if (party.getPartyName().toLowerCase().trim().contains(filterPattern))
                    {
                        filterList.add(party);
                    }
                }
            }
            else
            {
                filterList.addAll( partyListFull);
            }

            FilterResults filterResults= new FilterResults();
            filterResults.values= filterList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            partyList.clear();
            partyList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {

        PartyCardBinding mBinding;

        ViewHolder(@NonNull PartyCardBinding binding) {
            super(binding.getRoot());

            mBinding = binding;

        }
    }


}
