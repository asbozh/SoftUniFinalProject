package com.asbozh.softuni.finalproject;

import android.app.Application;
import android.content.ContextWrapper;

import com.asbozh.softuni.finalproject.database.Category;
import com.asbozh.softuni.finalproject.database.Finances;
import com.asbozh.softuni.finalproject.database.Record;
import com.orm.SugarContext;

import java.io.File;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SugarContext.init(getApplicationContext());

        if (!isDBExists(this, "budget_database.db")) {
            Finances.findById(Finances.class, (long) 1);
            Category.findById(Category.class, (long) 1);
            Record.findById(Record.class, (long) 1);

            initDB();
        }

    }

    private boolean isDBExists(ContextWrapper contextWrapper, String dbName) {
        File dbFile = contextWrapper.getDatabasePath(dbName);

        return dbFile.exists();
    }

    private void initDB() {
        Finances income = new Finances("Income", "0");
        Finances expense = new Finances("Expense", "0");

        income.save();
        expense.save();

        Category salary = new Category(income, "Salary", "0");
        Category gift = new Category(income, "Gift", "0");
        Category investment = new Category(income, "Investment", "0");
        Category other = new Category(income, "Other", "0");

        salary.save();
        gift.save();
        investment.save();
        other.save();

        Category food = new Category(expense, "Food", "0");
        Category entertainment = new Category(expense, "Entertainment", "0");
        Category transport = new Category(expense, "Transport", "0");
        Category tax = new Category(expense, "Tax", "0");

        food.save();
        entertainment.save();
        transport.save();
        tax.save();

    }
}
