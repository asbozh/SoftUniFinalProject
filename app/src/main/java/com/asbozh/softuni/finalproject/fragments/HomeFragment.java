package com.asbozh.softuni.finalproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.activities.AddEntryActivity;
import com.asbozh.softuni.finalproject.database.Category;
import com.asbozh.softuni.finalproject.database.Finances;
import com.asbozh.softuni.finalproject.database.Record;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;


public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;

    FloatingActionMenu fabMenu;
    FloatingActionButton fabIncome, fabExpense;
    Button btnMoreStatistics;
    TextView tvTotalIncome, tvTotalExpense, tvBalance, tvEntries, tvLastEntry;

    private List<Finances> mFinancesList;
    private List<Category> mCategoryList;
    private List<Record> mRecordList;

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
        tvTotalIncome = (TextView) getActivity().findViewById(R.id.tvIncomeTotalCard);
        tvTotalExpense = (TextView) getActivity().findViewById(R.id.tvExpenseTotalCard);
        tvBalance = (TextView) getActivity().findViewById(R.id.tvBalanceTotalCard);
        tvEntries = (TextView) getActivity().findViewById(R.id.tvEntriesTotalCard);
        tvLastEntry = (TextView) getActivity().findViewById(R.id.tvDateTotalCard);

        updateCardView();

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
                addEntryIntent.putExtra("TYPE_KEY", "Income");
                startActivity(addEntryIntent);

            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(false);
                addEntryIntent.putExtra("TYPE_KEY", "Expense");
                startActivity(addEntryIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCardView();
    }

    private void updateCardView() {
        double incomeAmount = 0;
        mFinancesList = Finances.listAll(Finances.class);
        mCategoryList = mFinancesList.get(0).getAllCategories();
        for (int i = 0; i < mCategoryList.size(); i++) {
            incomeAmount += Double.valueOf(mCategoryList.get(i).getCategoryTotalPrice());
        }
        mFinancesList.get(0).setTypeTotalPrice(String.valueOf(incomeAmount));
        mFinancesList.get(0).save();
        tvTotalIncome.setText("Total Income: " + mFinancesList.get(0).getTypeTotalPrice() + " BGN");

        double expenseAmount = 0;
        mFinancesList = Finances.listAll(Finances.class);
        mCategoryList = mFinancesList.get(1).getAllCategories();
        for (int i = 0; i < mCategoryList.size(); i++) {
            expenseAmount += Double.valueOf(mCategoryList.get(i).getCategoryTotalPrice());
        }
        mFinancesList.get(1).setTypeTotalPrice(String.valueOf(expenseAmount));
        mFinancesList.get(1).save();
        tvTotalExpense.setText("Total Expenses: " + mFinancesList.get(1).getTypeTotalPrice() + " BGN");

        double balance = incomeAmount - expenseAmount;
        tvBalance.setText("Balance: " + String.valueOf(balance) + " BGN");

        mCategoryList = Category.listAll(Category.class);
        mRecordList = mCategoryList.get(0).getAllRecords();
        for (int i = 1; i < mCategoryList.size(); i++) {
            mRecordList.addAll(mCategoryList.get(i).getAllRecords());
        }
        tvEntries.setText("Number Of Entries: " + mRecordList.size());

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs",
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains("last_entry")) {
            tvLastEntry.setText("Last Entry Date: " + sharedPreferences.getString("last_entry", "N/A"));
        }

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
