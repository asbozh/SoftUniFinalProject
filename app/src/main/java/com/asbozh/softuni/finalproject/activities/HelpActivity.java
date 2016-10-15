package com.asbozh.softuni.finalproject.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.service.AlarmReceiver;


public class HelpActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button mButtonStart, mButtonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initToolbar();
        mButtonStart = (Button) findViewById(R.id.bStartService);
        mButtonStop = (Button) findViewById(R.id.bStopService);
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager=(AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),120000,
                        pendingIntent);
            }
        });
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager=(AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                alarmManager.cancel(pendingIntent);
            }
        });
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
