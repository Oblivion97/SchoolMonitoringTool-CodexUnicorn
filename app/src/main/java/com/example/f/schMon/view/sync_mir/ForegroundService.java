/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.MessageEvent;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.view.payments.SchPayActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class ForegroundService extends Service {
    /**
     * ======================== Important =======================================================
     * Message ID for EventBus
     * sync all = 1
     * sync questions = 2
     * sync reports = 3
     * sync school,teacher,student = 4
     * sync reports old = 5
     * sync payments = 6
     * <p>
     * sync status types
     * 1. running
     * 2. notRunning
     */
    private static final String TAG = ForegroundService.class.getName();
    public static boolean IS_SERVICE_RUNNING = false;
    private String contentTextNotif;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private NotificationManager mNotifyManager;
    private String syncStatus, lastSyncTime, syncWhat;
    private SimpleDateFormat df4 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
    private SyncSynchronous syncSynchronous;
    private int msgID = -1;//to determine sync what
    private SyncThread workingThread;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            msgID = A.syncMsgID;
            syncWhat = A.syncWhat;
            Log.d(TAG, "syncWhat : " + syncWhat +
                    "Sync ID : " + msgID);


            workingThread = new SyncThread();
            workingThread.start();
            A.syncThread = workingThread;

          /*  if (msgID == 1) {
                syncAllFunc.start();
                A.syncThread = syncAllFunc;
            } else if (msgID == 4) {
                syncSchTechStdFunc.start();
                A.syncThread = syncSchTechStdFunc;
            } else if (msgID == 6) {
                syncPayFunc.start();
                A.syncThread = syncPayFunc;
            }*/


            showNotification(msgID);
            Toasty.info(this, "Sync Started!", Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        workingThread.requestStop();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    //============ sync threads =============================================================================================================


    class SyncThread extends Thread {
        private volatile boolean stopRequested = false;

        public void run() {
            while (!stopRequested) {

                if (msgID == 1) {
                    syncAllFunc();
                } else if (msgID == 4) {
                    syncSchTechStdFunc();
                } else if (msgID == 6) {
                    syncPayFunc();
                }
            }
        }

        public void requestStop() {
            stopRequested = true;
        }
    }

    public void syncPayFunc() {
        try {
            A.syncMsgID = 6;
            SimpleDateFormat df7 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String year = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
            syncSynchronous = new SyncSynchronous(getBaseContext());
            if (!DAO2.isPaymentDataAvailable())
                syncSynchronous.schoolSync();
            syncSynchronous.syncPaymentAllSchool(year);
            lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
            H.saveTimeOfLastPaymentSync(getBaseContext(), df7.format(new Date(System.currentTimeMillis())));
            EventBus.getDefault().post(new MessageEvent(6, "Payment Synced.",
                    true, true, lastSyncTime));

        } catch (Exception exep) {
            Log.d(TAG, "Exception Pay Service", exep);
            MessageEvent e = new MessageEvent();
            e.setMessageID(6);
            e.setMessage("Sync Failed.Try Again...");
            A.syncMsgID = 6;
            e.success = false;
            EventBus.getDefault().post(e);
        }
    }


    public void syncAllFunc() {
        try {
            A.syncMsgID = 1;
            contentTextNotif = "Sync Running";
            syncSynchronous = new SyncSynchronous(A.getAppContext());

            syncSynchronous.syncAll();


            lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
            EventBus.getDefault().post(new MessageEvent(1, "Sync Complete", true, true, lastSyncTime));

        } catch (Exception e) {
            Log.e(TAG, "exception", e);
            lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
            EventBus.getDefault().post(new MessageEvent(1, "Sync Error.Try Again...", false, true, lastSyncTime));
            A.syncMsgID = 1;
        }
    }


    public void syncSchTechStdFunc() {
        try {
            A.syncMsgID = 4;
            contentTextNotif = "Sync Running";
            syncSynchronous = new SyncSynchronous(getBaseContext());


            syncSynchronous.schoolSync();
            syncSynchronous.teacherSync();
            syncSynchronous.studentSync();



            lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
            EventBus.getDefault().post(new MessageEvent(4, "Sync Complete", true, true, lastSyncTime));

        } catch (Exception e) {
            Log.e(TAG, "exception", e);
            lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
            EventBus.getDefault().post(new MessageEvent(4, "Sync Error.Try Again...", false, true, lastSyncTime));
            A.syncMsgID = 4;
        }
    }


    //=================================== methods =========================================================================================================


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        contentTextNotif = event.message;
        if (event.success)
            Toasty.info(getBaseContext(), "Sync Completed.", Toast.LENGTH_LONG).show();
        else Toasty.info(getBaseContext(), "Sync Error.Try Again...", Toast.LENGTH_LONG).show();

        A.syncThread = null;
        stopForeground(true);
        stopSelf();
    }


    private void showNotification(int syncMsgID) {
        Intent notificationIntent;
        if (syncMsgID == 6)
            notificationIntent = new Intent(this, SchPayActivity.class);
        else
            notificationIntent = new Intent(this, SyncActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        if (IS_SERVICE_RUNNING)
            syncStatus = "running";
        else syncStatus = "notRunning";
        notificationIntent.putExtra("syncStatus", syncStatus);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);

        builder = new NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText(contentTextNotif)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true);
        builder.setProgress(0, 0, true);
        notification = builder.build();
        startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                notification);
    }
}