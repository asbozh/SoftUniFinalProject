package com.asbozh.softuni.finalproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asbozh.softuni.finalproject.R;


public class AddEntryActivity extends AppCompatActivity {

    Toolbar mToolbar;
    String entryType = "unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        initToolbar();
        entryType = getIntent().getStringExtra("TYPE_KEY");
        if (entryType.equalsIgnoreCase("INCOME")) {
            mToolbar.setTitle(R.string.add_income_entry);
        } else if (entryType.equalsIgnoreCase("EXPENSE")) {
            mToolbar.setTitle(R.string.add_expense_entry);
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
