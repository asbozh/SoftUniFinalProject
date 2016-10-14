package com.asbozh.softuni.finalproject.database;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

public class Category extends SugarRecord implements Parcelable {

    private Finances finances;
    private String categoryName;
    private String categoryTotalPrice;

    public Category(){}

    public Category(Finances finances, String categoryName, String categoryTotalPrice) {
        this.finances = finances;
        this.setCategoryName(categoryName);
        this.setCategoryTotalPrice(categoryTotalPrice);
    }

    public List<Record> getAllRecords() {
        return Record.find(Record.class, "category = ?", String.valueOf(getId()));
    }

    public String getFinances() {
        return finances.getFinancesType();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryTotalPrice() {
        return categoryTotalPrice;
    }

    public void setCategoryTotalPrice(String categoryTotalPrice) {
        this.categoryTotalPrice = categoryTotalPrice;
    }

    protected Category(Parcel in) {
        finances = (Finances) in.readValue(Finances.class.getClassLoader());
        categoryName = in.readString();
        categoryTotalPrice = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(finances);
        dest.writeString(categoryName);
        dest.writeString(categoryTotalPrice);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
