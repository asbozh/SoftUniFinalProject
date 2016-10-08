package com.asbozh.softuni.finalproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asbozh.softuni.finalproject.R;
import com.github.clans.fab.FloatingActionButton;


public class HomeFragment extends Fragment {

    FloatingActionButton fabIncome, fabExpense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fabIncome = (FloatingActionButton) getActivity().findViewById(R.id.fabIncome);
        fabExpense = (FloatingActionButton) getActivity().findViewById(R.id.fabExpense);

        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asbozh", "Income fab clicked!");
            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("asbozh", "Expense fab clicked!");
            }
        });

    }
}
