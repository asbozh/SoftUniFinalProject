package com.asbozh.softuni.finalproject.database;


import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

import java.util.List;

public class Finances extends SugarRecord implements Parcelable {

    private String financesType;
    private String typeTotalPrice;

    public Finances(){}

    public Finances(String financesType, String typeTotalPrice) {
        this.setFinancesType(financesType);
        this.setTypeTotalPrice(typeTotalPrice);
    }

    public List<Category> getAllCategories() {
        return Category.find(Category.class, "finances = ?", String.valueOf(getId()));
    }

    public String getFinancesType() {
        return financesType;
    }

    public void setFinancesType(String financesType) {
        this.financesType = financesType;
    }

    public String getTypeTotalPrice() {
        return typeTotalPrice;
    }

    public void setTypeTotalPrice(String typeTotalPrice) {
        this.typeTotalPrice = typeTotalPrice;
    }

    protected Finances(Parcel in) {
        financesType = in.readString();
        typeTotalPrice = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(financesType);
        dest.writeString(typeTotalPrice);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Finances> CREATOR = new Parcelable.Creator<Finances>() {
        @Override
        public Finances createFromParcel(Parcel in) {
            return new Finances(in);
        }

        @Override
        public Finances[] newArray(int size) {
            return new Finances[size];
        }
    };
}
