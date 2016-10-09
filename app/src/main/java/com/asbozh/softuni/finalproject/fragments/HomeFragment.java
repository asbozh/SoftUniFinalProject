package com.asbozh.softuni.finalproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.activities.AddEntryActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


public class HomeFragment extends Fragment {

    FloatingActionMenu fabMenu;

    FloatingActionButton fabIncome, fabExpense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fabMenu = (FloatingActionMenu) getActivity().findViewById(R.id.fabMenu);
        fabIncome = (FloatingActionButton) getActivity().findViewById(R.id.fabIncome);
        fabExpense = (FloatingActionButton) getActivity().findViewById(R.id.fabExpense);

        final Intent addEntryIntent = new Intent(getActivity(), AddEntryActivity.class);

        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(false);
                addEntryIntent.putExtra("TYPE_KEY", "INCOME");
                startActivity(addEntryIntent);

            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(false);
                addEntryIntent.putExtra("TYPE_KEY", "EXPENSE");
                startActivity(addEntryIntent);
            }
        });

    }
}
