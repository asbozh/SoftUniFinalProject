package com.asbozh.softuni.finalproject.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.database.Record;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<Record> mDataSet;
    private String mCurrency;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvCategory, tvAmount, tvDate, tvCurrency;
        public ImageView ivIcon;

        public ViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvRecyclerViewEntryTitle);
            tvCategory = (TextView) view.findViewById(R.id.tvRecyclerViewEntryCategory);
            tvAmount = (TextView) view.findViewById(R.id.tvRecyclerViewEntryAmount);
            tvCurrency = (TextView) view.findViewById(R.id.tvRecyclerViewEntryCurrency);
            tvDate = (TextView) view.findViewById(R.id.tvRecyclerViewEntryDate);
            ivIcon = (ImageView) view.findViewById(R.id.ivRecyclerViewEntryIcon);
        }
    }

    public RVAdapter(List<Record> myDataSet, String currency) {
        this.mDataSet = myDataSet;
        this.mCurrency = currency;
    }

    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_entry_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvTitle.setText(mDataSet.get(position).getTitle());
        holder.tvCategory.setText(mDataSet.get(position).getCategory());
        holder.tvAmount.setText(mDataSet.get(position).getAmount());
        holder.tvDate.setText(mDataSet.get(position).getDate());
        holder.tvCurrency.setText(mCurrency);
        if (mDataSet.get(position).getFinancesTypeOfCategory().equalsIgnoreCase("income")) {
            holder.ivIcon.setImageResource(R.drawable.ic_add_circle_outline_black_48dp);
        } else {
            holder.ivIcon.setImageResource(R.drawable.ic_remove_circle_outline_black_48dp);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

