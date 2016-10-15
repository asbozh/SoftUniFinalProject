package com.asbozh.softuni.finalproject.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mService = new Intent(context, NotificationService.class);
        context.startService(mService);
    }
}
