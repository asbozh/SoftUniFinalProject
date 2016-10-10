package com.asbozh.softuni.finalproject.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asbozh.softuni.finalproject.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddEntryActivity extends AppCompatActivity implements View.OnClickListener {

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;

    Toolbar mToolbar;
    String entryType = "unknown";
    TextView tvDateEntry;
    ImageButton ibCalendarPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        initToolbar();
        entryType = getIntent().getStringExtra("TYPE_KEY");
        if (entryType.equalsIgnoreCase("INCOME")) {
            getSupportActionBar().setTitle(R.string.add_income_entry);
        } else if (entryType.equalsIgnoreCase("EXPENSE")) {
            getSupportActionBar().setTitle(R.string.add_expense_entry);
        }

        setViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEntry();
            }

        };
        updateDateEntry();
    }

    private void updateDateEntry() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        tvDateEntry.setText(sdf.format(myCalendar.getTime()));
    }

    private void setViews() {
        tvDateEntry = (TextView) findViewById(R.id.tvDateEntry);
        ibCalendarPicker = (ImageButton) findViewById(R.id.ibCalendarPicker);
        ibCalendarPicker.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibCalendarPicker:
                new DatePickerDialog(AddEntryActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }
}
