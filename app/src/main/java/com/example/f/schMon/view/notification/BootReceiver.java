/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Mir Ferdous on 25/11/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = BootReceiver.class.getName();


    public void onReceive(Context context, Intent intent) {

        // Your code to execute when Boot Completd
       /* Toast.makeText(context, "Booting Completed", Toast.LENGTH_LONG).show();
        intent = new Intent(context, NotifService.class);
        context.startService(intent);*/

        Intent i = new Intent(context, NotifService.class);
        context.startService(i);

    }

    public void scheduleNotification(Context context) {
        //current hour in 24H format
        int GMT6 = 6 * 60 * 60 * 1000; // bangladeshi time GMT+6
        long millis = System.currentTimeMillis() + GMT6;

        millis = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Date date = new Date(millis);
        long time = Long.parseLong(sdf.format(date));
        Log.e(TAG, "current time is in 24h : " + time);
        // if (time >= 1100 && time < 1400) { // if time is greater or equal  11 am
        //notification in every 1 week
        Intent intent = new Intent(context, NotifAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        long delay = 2;
        long futureTime = /*System.currentTimeMillis() +*/ delay;
        am.setInexactRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), futureTime, pendingIntent);
        //  am.setRepeating(am.RTC_WAKEUP, time, am.INTERVAL_DAY * 1, pendingIntent);
        //  }
    }
}