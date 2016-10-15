package com.asbozh.softuni.finalproject.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.activities.AddEntryActivity;
import com.asbozh.softuni.finalproject.activities.ViewEntryActivity;
import com.asbozh.softuni.finalproject.adapters.RVAdapter;
import com.asbozh.softuni.finalproject.adapters.RVItemClickListener;
import com.asbozh.softuni.finalproject.adapters.RVItemDecorationDivider;
import com.asbozh.softuni.finalproject.database.Category;
import com.asbozh.softuni.finalproject.database.Record;

import java.util.List;


public class TabAllRecordsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Category> mCategoryList;
    private List<Record> myDataSet;

    public TabAllRecordsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_tab_records, container, false);
        mRecyclerView = (RecyclerView) viewRoot.findViewById(R.id.rvItems);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RVItemDecorationDivider(getActivity()));

        mCategoryList = Category.listAll(Category.class);
        myDataSet = mCategoryList.get(0).getAllRecords();
        for (int i = 1; i < mCategoryList.size(); i++) {
            myDataSet.addAll(mCategoryList.get(i).getAllRecords());
        }

        mAdapter = new RVAdapter(myDataSet, "BGN");
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RVItemClickListener(getContext(), mRecyclerView ,new RVItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent viewEntryIntent = new Intent(getActivity(), ViewEntryActivity.class);
                        Record selectedRecord = myDataSet.get(position);
                        viewEntryIntent.putExtra("ENTRY", selectedRecord);
                        startActivity(viewEntryIntent);
                    }

                    @Override public void onLongItemClick(View view, final int position) {
                        AlertDialog.Builder adBuilder = new AlertDialog.Builder(getContext());
                        adBuilder.setTitle(getString(R.string.delete_title));
                        adBuilder.setMessage(getString(R.string.delete_body));
                        adBuilder.setPositiveButton(getString(R.string.alert_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Record selectedRecord = myDataSet.get(position);
                                Category category = null;
                                for (int i = 0; i < mCategoryList.size(); i++) {
                                    if (mCategoryList.get(i).getCategoryName().equalsIgnoreCase(selectedRecord.getCategory())) {
                                        category = mCategoryList.get(i);
                                    }
                                }

                                // Remove Amount from the Category
                                double categoryPrice = Double.valueOf(category.getCategoryTotalPrice());
                                categoryPrice -= Double.valueOf(selectedRecord.getAmount());
                                category.setCategoryTotalPrice(String.valueOf(categoryPrice));
                                category.save();

                                selectedRecord.delete();
                                myDataSet.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                        adBuilder.setNegativeButton(getString(R.string.alert_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = adBuilder.create();
                        alertDialog.show();
                    }
                })
        );

        return viewRoot;
    }
}
