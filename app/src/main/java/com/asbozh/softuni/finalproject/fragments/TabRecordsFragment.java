package com.asbozh.softuni.finalproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asbozh.softuni.finalproject.R;



public class TabRecordsFragment extends Fragment {


    public TabRecordsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_records, container, false);
    }

}
