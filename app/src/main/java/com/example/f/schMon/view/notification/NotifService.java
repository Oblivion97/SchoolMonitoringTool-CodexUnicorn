/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by Mir Ferdous on 9/21/2017.
 */

public class NotifService extends Service {
    private static final String TAG = NotifService.class.getName();
    private Context context = NotifService.this;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        scheduleNotification(context);
        return super.onStartCommand(intent, flags, startId);
    }

    public  void scheduleNotification(Context context) {
        AlarmManager alarmMangr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotifAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        long repeatDelay = 24 * 60 * 60 * 1000;//alarm will repeat after 24 hour
        alarmMangr.setRepeating(AlarmManager.RTC_WAKEUP, setUpAlarmTime(9, 0, 0).getTimeInMillis(),
                repeatDelay, pendingIntent);

    }

    public  Calendar setUpAlarmTime(int hour, int min, int sec) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        return calendar;
    }
}
