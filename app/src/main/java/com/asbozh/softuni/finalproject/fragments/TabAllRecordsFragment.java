package com.asbozh.softuni.finalproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.adapters.RVAdapter;
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

        return viewRoot;
    }
}
