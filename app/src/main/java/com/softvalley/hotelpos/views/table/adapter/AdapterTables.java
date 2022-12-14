package com.softvalley.hotelpos.views.table.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softvalley.hotelpos.R;
import com.softvalley.hotelpos.databinding.ItemTablesBinding;
import com.softvalley.hotelpos.models.response.table.Table;
import com.softvalley.hotelpos.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.List;

public class AdapterTables extends RecyclerView.Adapter<AdapterTables.ViewHolder> {
    private final List<Table> tableModelList;
    private final Context context;
    private LayoutInflater layoutInflater;
    private TableClickListener listener;

    public AdapterTables(Context context, TableClickListener listener) {
        this.context = context;
        this.tableModelList = new ArrayList<>();
        this.listener = listener;
    }

    public interface TableClickListener {
        void onClick(Table table, int noOfGuest);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }

        ItemTablesBinding binding = ItemTablesBinding.inflate(layoutInflater, viewGroup, false);

        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Table table = tableModelList.get(i);
        holder.mBinding.txtTable.setText(table.getTableName());
        if (table.getStatus().equals("Reserved")) {
            holder.mBinding.tableCard.setCardBackgroundColor(Color.parseColor("#EEAF48"));
        }else
        {
            holder.mBinding.tableCard.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        int numberOfGuest = SharedPreferenceHelper.getInstance(context).getNumberOfGuestByTable(String.valueOf(table.getTableCode()));
        holder.mBinding.tvNumberGuest.setText(String.valueOf(numberOfGuest));
        Log.e("tabelName", "" + table.getTableName());
    }

    @Override
    public int getItemCount() {
        return tableModelList.size();
    }

    public void setTableModelList(List<Table> list) {
        tableModelList.clear();
        if (list != null) {
            tableModelList.addAll(list);
        }


        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemTablesBinding mBinding;

        ViewHolder(@NonNull ItemTablesBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.txtTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Table table = tableModelList.get(getAdapterPosition());
                    if (!table.getStatus().equals("Reserved")) {
                        showTableDialog(table);
                    } else {
                        if (listener != null) {
                            listener.onClick(table, -1);
                        }
                    }
                }
            });

        }
    }

    void showTableDialog(Table table) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialogue_guest);
        TextView txtOne, txtTwo, txtThree, txtFour, txtFive, txtSix, txtSeven, txtEight, txtNine, txtTen, txtEleven, txtTwelve;
        txtOne = dialog.findViewById(R.id.txtOne);
        txtTwo = dialog.findViewById(R.id.txtTwo);
        txtThree = dialog.findViewById(R.id.txtThree);
        txtFour = dialog.findViewById(R.id.txtFour);
        txtFive = dialog.findViewById(R.id.txtFive);
        txtSix = dialog.findViewById(R.id.txtSix);
        txtSeven = dialog.findViewById(R.id.txtSeven);
        txtEight = dialog.findViewById(R.id.txtEight);
        txtNine = dialog.findViewById(R.id.txtNine);
        txtTen = dialog.findViewById(R.id.txtTen);
        txtEleven = dialog.findViewById(R.id.txtEleven);
        txtTwelve = dialog.findViewById(R.id.txtTweleve);
        txtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavController navController= NavHostFragment.findNavController(fragment);
//
//                DashBoardFragmentDirections.ActionDashBoardFragmentToOrderFragment action= DashBoardFragmentDirections.actionDashBoardFragmentToOrderFragment();
//                action.setNumOfGuest(1);
//                action.setTable(table);
//
//                navController.navigate(action);
                listener.onClick(table, 1);
                Log.e("tableNAMe", table.getTableName());
                dialog.dismiss();
            }
        });
        txtTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 2);
                dialog.dismiss();
            }
        });
        txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 3);

                dialog.dismiss();
            }
        });
        txtFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 4);
                dialog.dismiss();
            }
        });
        txtFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 5);
                dialog.dismiss();
            }
        });
        txtSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 6);
                dialog.dismiss();
            }
        });
        txtSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 7);
                dialog.dismiss();
            }
        });
        txtEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 8);
                dialog.dismiss();
            }
        });
        txtNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 9);
                dialog.dismiss();
            }
        });
        txtTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 10);
                dialog.dismiss();
            }
        });
        txtEleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 11);
                dialog.dismiss();
            }
        });
        txtTwelve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(table, 12);
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
