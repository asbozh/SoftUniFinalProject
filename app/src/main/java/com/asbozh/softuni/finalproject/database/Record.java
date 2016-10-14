package com.asbozh.softuni.finalproject.database;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class Record extends SugarRecord implements Parcelable {

    private Category category;
    private String title;
    private String moreDetails;
    private String amount;
    private String date;
    private String location;
    private String image;

    public Record(){}

    public Record(Category category, String title, String moreDetails, String amount, String date, String location, String image) {
        this.category = category;
        this.setTitle(title);
        this.setMoreDetails(moreDetails);
        this.setAmount(amount);
        this.setDate(date);
        this.setLocation(location);
        this.setImage(image);
    }

    public String getFinancesTypeOfCategory() {
        return category.getFinances();
    }

    public String getCategory() {
       return category.getCategoryName();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoreDetails() {
        return moreDetails;
    }

    public void setMoreDetails(String moreDetails) {
        this.moreDetails = moreDetails;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    protected Record(Parcel in) {
        category = (Category) in.readValue(Category.class.getClassLoader());
        title = in.readString();
        moreDetails = in.readString();
        amount = in.readString();
        date = in.readString();
        location = in.readString();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(category);
        dest.writeString(title);
        dest.writeString(moreDetails);
        dest.writeString(amount);
        dest.writeString(date);
        dest.writeString(location);
        dest.writeString(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
        @Override
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        @Override
        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
}
