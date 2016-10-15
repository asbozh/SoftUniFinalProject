package com.asbozh.softuni.finalproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.database.Category;
import com.asbozh.softuni.finalproject.database.Finances;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class StatisticsFragment extends Fragment implements View.OnClickListener {

    PieChart chart;
    Button bStatsIncome, bStatsExpense;

    private List<Finances> mFinancesList;
    private List<Category> mCategoryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_statistics, container, false);
        chart = (PieChart) viewRoot.findViewById(R.id.chart);
        bStatsIncome = (Button) viewRoot.findViewById(R.id.bStatisticsIncome);
        bStatsExpense = (Button) viewRoot.findViewById(R.id.bStatisticsExpense);
        bStatsIncome.setOnClickListener(this);
        bStatsExpense.setOnClickListener(this);
        bStatsIncome.performClick();

        return viewRoot;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bStatisticsIncome) {
            mFinancesList = Finances.listAll(Finances.class);
            mCategoryList = mFinancesList.get(0).getAllCategories();
            float totalPrice = Float.valueOf(mFinancesList.get(0).getTypeTotalPrice());
            float cat1 = Float.valueOf(mCategoryList.get(0).getCategoryTotalPrice());
            float cat2 = Float.valueOf(mCategoryList.get(1).getCategoryTotalPrice());
            float cat3 = Float.valueOf(mCategoryList.get(2).getCategoryTotalPrice());
            float cat4 = Float.valueOf(mCategoryList.get(3).getCategoryTotalPrice());

            List<PieEntry> entries = new ArrayList<>();

            cat1 /= totalPrice;
            entries.add(new PieEntry(cat1 * 100, mCategoryList.get(0).getCategoryName()));
            cat2 /= totalPrice;
            entries.add(new PieEntry(cat2 * 100, mCategoryList.get(1).getCategoryName()));
            cat3 /= totalPrice;
            entries.add(new PieEntry(cat3 * 100, mCategoryList.get(2).getCategoryName()));
            cat4 /= totalPrice;
            entries.add(new PieEntry(cat4 * 100, mCategoryList.get(3).getCategoryName()));

            PieDataSet set = new PieDataSet(entries, "Income Results");
            set.setColors(new int[]{ContextCompat.getColor(getActivity(), R.color.first),
                    ContextCompat.getColor(getActivity(), R.color.second),
                    ContextCompat.getColor(getActivity(), R.color.third),
                    ContextCompat.getColor(getActivity(), R.color.forth)});
            PieData data = new PieData(set);
            chart.setDescription("");
            chart.setCenterText("Income");
            chart.setCenterTextSize(18f);
            chart.setData(data);
            chart.invalidate();
        } else {
            mFinancesList = Finances.listAll(Finances.class);
            mCategoryList = mFinancesList.get(1).getAllCategories();
            float totalAmount = Float.valueOf(mFinancesList.get(1).getTypeTotalPrice());
            float category1 = Float.valueOf(mCategoryList.get(0).getCategoryTotalPrice());
            float category2 = Float.valueOf(mCategoryList.get(1).getCategoryTotalPrice());
            float category3 = Float.valueOf(mCategoryList.get(2).getCategoryTotalPrice());
            float category4 = Float.valueOf(mCategoryList.get(3).getCategoryTotalPrice());

            List<PieEntry> entries = new ArrayList<>();

            category1 /= totalAmount;
            entries.add(new PieEntry(category1 * 100, mCategoryList.get(0).getCategoryName()));
            category2 /= totalAmount;
            entries.add(new PieEntry(category2 * 100, mCategoryList.get(1).getCategoryName()));
            category3 /= totalAmount;
            entries.add(new PieEntry(category3 * 100, mCategoryList.get(2).getCategoryName()));
            category4 /= totalAmount;
            entries.add(new PieEntry(category4 * 100, mCategoryList.get(3).getCategoryName()));

            PieDataSet  set = new PieDataSet(entries, "Expense Results");
            set.setColors(new int[]{ContextCompat.getColor(getActivity(), R.color.first),
                    ContextCompat.getColor(getActivity(), R.color.second),
                    ContextCompat.getColor(getActivity(), R.color.third),
                    ContextCompat.getColor(getActivity(), R.color.forth)});
            PieData data = new PieData(set);
            chart.setDescription("");
            chart.setCenterText("Expenses");
            chart.setCenterTextSize(18f);
            chart.setData(data);
            chart.invalidate();
        }
    }
}
