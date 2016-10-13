package com.asbozh.softuni.finalproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.activities.AddEntryActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;


public class HomeFragment extends Fragment {

    FloatingActionMenu fabMenu;
    FloatingActionButton fabIncome, fabExpense;
    Button btnMoreStatistics;

    private OnHomeFragmentClickListener mListener;

    public HomeFragment() {

    }

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
        btnMoreStatistics = (Button) getActivity().findViewById(R.id.btnMoreStatistics);

        btnMoreStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onHomeFragmentInteraction();
                }
            }
        });

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentClickListener) {
            mListener = (OnHomeFragmentClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHomeFragmentClickListener {
        void onHomeFragmentInteraction();
    }
}
