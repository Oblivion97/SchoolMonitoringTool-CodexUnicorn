/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO2;

/**
 * Created by Mir Ferdous on 9/21/2017.
 */



/*
The following code uses Alarmmanager with BroadcastReceiver which will help you out achieving your need.

        In your activity:


        System.currentTimeInMillis() - denotes that the alarm will trigger at current time, you can pass a constant time of a day in milliseconds.

        Then create a NotifAlarmReceiver class something like this,
*/

public class NotifAlarmReceiver extends BroadcastReceiver {
    int reqCode = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        int noOfProblem = DAO2.noOfProblemExistInEnvRepForNotif();
        if (noOfProblem > 0)
            showNotification(context);
    }

    @SuppressWarnings("deprecation")
    public void showNotification(Context context) {
        Intent intent = new Intent(context, NotifActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, reqCode, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("There are some Problems in some of the Schools")
                .setContentText("Check them !");
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(reqCode, mBuilder.build());
    }
}