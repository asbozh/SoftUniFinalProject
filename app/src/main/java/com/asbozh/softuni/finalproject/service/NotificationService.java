package com.asbozh.softuni.finalproject.service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.asbozh.softuni.finalproject.R;
import com.asbozh.softuni.finalproject.activities.MainActivity;


public class NotificationService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_body))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .build();
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification);

        stopSelf();

        return Service.START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
