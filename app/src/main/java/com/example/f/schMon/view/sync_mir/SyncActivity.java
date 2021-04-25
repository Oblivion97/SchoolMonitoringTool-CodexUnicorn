/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.MessageEvent;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.sync_mir.MdlSync.Respnse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class SyncActivity extends AppCompatActivity {
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
    private static final String TAG = SyncActivity.class.getName();
    private Context context = SyncActivity.this;
    private Intent serviceIntent;
    private Intent intent;
    private Respnse respnse1, respnse2;
    private String syncStatus;
    private boolean syncRunning;
    private String lastsyncTimeTitile = "Last synced on ";
    private String lastSyncTime, btnFocus;
    private SyncSynchronous syncSynchronous = new SyncSynchronous(context);
    private Thread syncAllThread, syncQuesThread, syncAnsThread, syncReportOldThread, syncSchTechStdThread;
    private SimpleDateFormat df4 = new SimpleDateFormat("dd MMM yyyy, hh:mm");
    private Animation animShake;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Snackbar snackbar;
    private boolean serviceRunning;
    private Drawable btnColorEnabled;
    private int btnColorDisabled;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.sync_progressBar)
    ProgressBar progressBarCommon;
    @BindView(R.id.sync_progressBarSyncBtnAll)
    ProgressBar progressBarSyncAll;
    @BindView(R.id.sync_progressBarSyncBtnReport)
    ProgressBar progressBarSyncReport;
    @BindView(R.id.sync_progressBarSyncBtnReportOld)
    ProgressBar progressBarSyncReportOld;
    @BindView(R.id.sync_progressBarSyncBtnQues)
    ProgressBar progressBarSyncQues;
    @BindView(R.id.progressBarSyncSchTechStd)
    ProgressBar progressBarSyncSchTechStd;
    @BindView(R.id.sync_Msg)
    TextView syncMsgTV;
    @BindView(R.id.syncReportTV)
    TextView syncReportTV;
    @BindView(R.id.syncReportOldTV)
    TextView syncReportOldTV;
    @BindView(R.id.syncQuesTV)
    TextView syncQuesTV;
    @BindView(R.id.syncAllTV)
    TextView syncAllTV;
    @BindView(R.id.syncSchTechStdTV)
    TextView syncSchTechStdTV;
    @BindView(R.id.syncBtnAll)
    Button syncBtnAll;
    @BindView(R.id.syncBtnReport)
    Button syncBtnReport;
    @BindView(R.id.syncBtnReportOld)
    Button syncBtnReportOld;
    @BindView(R.id.syncBtnQues)
    Button syncBtnQues;
    @BindView(R.id.syncBtnSchTechStd)
    Button syncBtnSchTechStd;
    @BindView(R.id.syncBtnAllContainer)
    View syncBtnAllContainer;
    @BindView(R.id.syncBtnReportContainer)
    View syncBtnReportContainer;
    @BindView(R.id.syncBtnReportOldContainer)
    View syncBtnReportOldContainer;
    @BindView(R.id.syncBtnQuesContainer)
    View syncBtnQuesContainer;
    @BindView(R.id.syncBtnSchTechStdContainer)
    View syncBtnSchTechStdContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.syncActivityContentParent)
    View syncActivityContentParent;
    @BindView(R.id.syncBtnPayContainer)
    View syncBtnPayContainer;
    @BindView(R.id.syncBtnPay)
    Button syncBtnPay;
    @BindView(R.id.sync_progressBarSyncBtnPay)
    ProgressBar progressBarSyncPay;
    @BindView(R.id.syncPayTV)
    TextView syncPayTV;
    //=========================================================================================================================

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = getIntent();
        syncStatus = intent.getStringExtra("syncStatus");
        btnFocus = intent.getStringExtra(Global.btnFocus);


        initializeViews();


        //focus
        setBtnFocusWhenStartedFromNoDataClick();


        // service running or not check
        serviceRunning = SyncH.isMyServiceRunning(ForegroundService.class, this);
        Log.d(TAG, "Service Running: " + serviceRunning);
        if (serviceRunning) {
            switch (A.syncMsgID) {
                case 1: //sync all
                    uiChangeSyncStart(syncBtnAll, progressBarSyncAll, Global.syncInService);
                    break;
                case 4: // std , tech ,sch sync
                    uiChangeSyncStart(syncBtnSchTechStd, progressBarSyncSchTechStd, Global.syncInService);
                    break;
                case 6:
                    break;
            }
        }
        //btn label
       /* if (syncStatus != null) {
            if (syncStatus.equals("running")) {
                syncBtnAll.setText("Stop Sync All");
                progressBarSyncAll.setVisibility(View.VISIBLE);
            } else if (syncStatus.equals("notRunning")) {
                syncBtnAll.setText("Start Sync All");
            }
        } else syncBtnAll.setText("Start Sync All");*/

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        ButterKnife.bind(this);
        //appbar
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        H.setFont(context, new View[]{syncMsgTV, syncBtnAll, syncBtnQues, syncBtnReport, syncBtnSchTechStd
                , syncReportTV, syncSchTechStdTV, syncQuesTV, syncAllTV, syncReportOldTV, syncBtnReportOld, syncPayTV, syncBtnPay});

        sp = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        editor = sp.edit();

        //btn color
        // btnColorEnabled = ContextCompat.getDrawable(context, R.drawable.btn_round_edge_inside_app);
        btnColorDisabled = ContextCompat.getColor(context, R.color.disabledBtn);

    }


    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        syncBtnAll.setText("Start Sync All");
        super.onStop();
    }


    //=========================================================================================================================

    void startSyncService() {
        serviceIntent = new Intent(context, ForegroundService.class);

        if (!SyncH.isMyServiceRunning(ForegroundService.class, this)) {
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = true;


            startService(serviceIntent);
            syncStatus = "running";
        } else {
           /* serviceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = false;

            MessageEvent e = new MessageEvent();
            stopSyncService(e);
            syncStatus = "notRunning";*/

            Toasty.warning(context, "Sync Already Running.Wait...").show();
        }


       /* if (!ForegroundService.IS_SERVICE_RUNNING) {
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = true;
        } else {
            serviceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = false;
        }
        startService(serviceIntent);*/
    }

    void stopSyncService(MessageEvent event) {

      /*  if (A.SyncThread != null) {
            if (A.SyncThread.isAlive()) {
                A.SyncThread.interrupt();
                A.SyncThread.stop();
            }
        }*/

        //serviceIntent = new Intent(context, ForegroundService.class);

        stopService(serviceIntent);
        event.messageID = A.syncMsgID;
        event.setSuccess(false);
        event.setMessage("Sync Stopped.Try Again...");
        EventBus.getDefault().post(event);
    }

    void uiChangeSyncStart(Button btnRunningSync, ProgressBar progressBar, String syncInWhat) {
        syncBtnReport.setEnabled(false);
        syncBtnQues.setEnabled(false);
        syncBtnReportOld.setEnabled(false);
        syncBtnSchTechStd.setEnabled(false);
        syncBtnAll.setEnabled(false);
        syncBtnPay.setEnabled(false);

        syncBtnReport.setBackgroundColor(btnColorDisabled);
        syncBtnQues.setBackgroundColor(btnColorDisabled);
        syncBtnReportOld.setBackgroundColor(btnColorDisabled);
        syncBtnSchTechStd.setBackgroundColor(btnColorDisabled);
        syncBtnAll.setBackgroundColor(btnColorDisabled);
        syncBtnPay.setBackgroundColor(btnColorDisabled);

        progressBar.setVisibility(View.VISIBLE);
        snackbar = Snackbar.make(btnRunningSync, Global.syncRunningDontClose, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        if (syncInWhat.equals(Global.syncInService)) {
            btnRunningSync.setEnabled(true);
            btnRunningSync.setBackgroundResource(R.drawable.btn_round_edge_inside_app);
            snackbar = Snackbar.make(btnRunningSync, "Sync Running", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else {
            syncMsgTV.setText(Global.syncRunningDontClose);
            snackbar = Snackbar.make(btnRunningSync, Global.syncRunningDontClose, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }

    }

    void uiChangeSyncStop(Button btnRunningSync, ProgressBar progressBar, String syncInWhat) {
        syncBtnReport.setEnabled(true);
        syncBtnQues.setEnabled(true);
        syncBtnReportOld.setEnabled(true);
        syncBtnSchTechStd.setEnabled(true);
        syncBtnAll.setEnabled(true);
        syncBtnPay.setEnabled(true);

        syncBtnReport.setBackgroundResource(R.drawable.btn_round_edge_inside_app);
        syncBtnQues.setBackgroundResource(R.drawable.btn_round_edge_inside_app);
        syncBtnReportOld.setBackgroundResource(R.drawable.btn_round_edge_inside_app);
        syncBtnSchTechStd.setBackgroundResource(R.drawable.btn_round_edge_inside_app);
        syncBtnAll.setBackgroundResource(R.drawable.btn_round_edge_inside_app);
        syncBtnPay.setBackgroundResource(R.drawable.btn_round_edge_inside_app);


        progressBar.setVisibility(View.GONE);
        snackbar.dismiss();
    }

    //=========================== btn clicks ==============================================================================================

    @OnClick(R.id.syncBtnSchTechStd)
    void onClickSyncBtnSchTechStd(View v) {

        if (H.isInternetConnected(context)) {
         /*   //stop sync
            if (syncStatus.equals("running")) {
                syncStatus = "notRunning";
                syncBtnSchTechStd.setText("Start Download School,Teacher,Student");
                progressBarSyncSchTechStd.setVisibility(View.GONE);
                Toasty.info(getBaseContext(), "Sync Stopped.Try Again...", Toast.LENGTH_LONG).show();
                snackbar.dismiss();

                serviceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                ForegroundService.IS_SERVICE_RUNNING = false;

                MessageEvent e = new MessageEvent();
                e.setMessageID(4);
                e.setSuccess(false);
                stopSyncService(e);
            }
            //start sync
            else if (syncStatus.equals("notRunning")) {*/
            syncStatus = "running";
            syncBtnSchTechStd.setText("Start Download School,Teacher,Student");
            progressBarSyncSchTechStd.setVisibility(View.VISIBLE);
            uiChangeSyncStart(syncBtnSchTechStd, progressBarSyncSchTechStd, Global.syncInService);

            A.syncWhat = Global.syncSchTechStd;
            A.syncMsgID = 4;
            startSyncService();

            //}
        } else Toasty.error(context, Global.noNetErrorMsg).show();

    }

    @OnClick(R.id.syncBtnAll)
    void onClickSyncBtnAll(View v) {

        if (H.isInternetConnected(context)) {
            /*//stop sync
            if (syncStatus.equals("running")) {
                syncStatus = "notRunning";
                syncBtnAll.setText("Start Sync All");
                progressBarSyncAll.setVisibility(View.GONE);
                Toasty.info(getBaseContext(), "Sync Stopped.Try Again...", Toast.LENGTH_LONG).show();
                snackbar.dismiss();

                serviceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                ForegroundService.IS_SERVICE_RUNNING = false;

                MessageEvent e = new MessageEvent();
                e.setMessageID(1);
                e.setSuccess(false);
                stopSyncService(e);
            }
            //start sync
            else if (syncStatus.equals("notRunning")) {*/
            syncStatus = "running";
            syncBtnAll.setText("Start Sync All");
            progressBarSyncAll.setVisibility(View.VISIBLE);
            uiChangeSyncStart(syncBtnAll, progressBarSyncAll, Global.syncInService);

            A.syncWhat = Global.syncAll;
            A.syncMsgID = 1;
            startSyncService();

            //}
        } else Toasty.error(context, Global.noNetErrorMsg).show();

    }

    private Thread syncPayThread;

    @OnClick(R.id.syncBtnPay)
    void onClickSyncBtnPay(View v) {
        if (H.isInternetConnected(context)) {
            uiChangeSyncStart(syncBtnPay, progressBarSyncPay, Global.syncInActivity);
            syncPayThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String year = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
                    SimpleDateFormat df7 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    SyncSynchronous syncSynchronous = new SyncSynchronous(context);
                    try {
                        if (!DAO2.isPaymentDataAvailable())
                            syncSynchronous.schoolSync();
                        syncSynchronous.syncPaymentAllSchool(year);
                        lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
                        H.saveTimeOfLastPaymentSync(context, df7.format(new Date(System.currentTimeMillis())));
                        EventBus.getDefault().post(new MessageEvent(6, "Payment Data Updated.",
                                true, true, lastSyncTime));
                    } catch (Exception e) {
                        Log.d(TAG, e.toString(), e);
                        EventBus.getDefault().post(exceptionEventBus(e, 6, "Sync Payments"));
                    }
                }
            });
            syncPayThread.start();
        } else {
            Toasty.error(context, Global.noNetErrorMsg).show();
        }
    }

    /*post reports*/
    @OnClick(R.id.syncBtnReport)
    void onClickSyncBtnReport(View v) {

        if (H.isInternetConnected(context)) {
            uiChangeSyncStart(syncBtnReport, progressBarSyncReport, Global.syncInActivity);
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        respnse2 = syncSynchronous.postRepAns(true);
                        respnse1 = syncSynchronous.postEvlAns(true);
                        lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
                        EventBus.getDefault().post(new MessageEvent(3,
                                "All Report of Evaluation & Environment Uploaded to Server", true, true, lastSyncTime));
                    } catch (Exception e) {
                        Log.d(TAG, e.toString(), e);
                        EventBus.getDefault().post(exceptionEventBus(e, 3, "Upload Reports"));
                    }
                }
            };

            syncAnsThread = new Thread(runnable);
            syncAnsThread.start();
        } else Toasty.error(context, Global.noNetErrorMsg).show();

    }

    @OnClick(R.id.syncBtnReportOld)
    void onClickSyncBtnReportOld(View v) {

        if (H.isInternetConnected(context)) {
            uiChangeSyncStart(syncBtnReportOld, progressBarSyncReportOld, Global.syncInActivity);
            syncReportOldThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        syncSynchronous.dwnldEnvironmentAndEvaluationOld();
                        lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
                        EventBus.getDefault().post(new MessageEvent(5,
                                "All Old Reports of Evaluation & Environment Downloaded", true, true, lastSyncTime));
                    } catch (Exception e) {
                        Log.d(TAG, "Exception", e);
                        EventBus.getDefault().post(exceptionEventBus(e, 5, "Download Old Reports"));
                    }
                }
            });
            syncReportOldThread.start();
        } else Toasty.error(context, Global.noNetErrorMsg).show();

    }

    @OnClick(R.id.syncBtnQues)
    void onClickSyncBtnQues(View v) {

        if (H.isInternetConnected(context)) {
            uiChangeSyncStart(syncBtnQues, progressBarSyncQues, Global.syncInActivity);
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        syncSynchronous.dwnldEnvironmentQues();
                        syncSynchronous.dwnldEvlQues();
                        lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
                        EventBus.getDefault().post(new MessageEvent(2,
                                "All Questions of Evaluation & CheckList Downloaded.", true, true, lastSyncTime));
                    } catch (Exception e) {
                        Log.d(TAG, e.toString(), e);
                        EventBus.getDefault().post(exceptionEventBus(e, 2, "Start Question Sync"));
                    }
                }
            };

            syncQuesThread = new Thread(runnable);
            syncQuesThread.start();
        } else Toasty.error(context, Global.noNetErrorMsg).show();

    }


    //================================ methods =========================================================================================

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent e) {
        syncMsgTV.setText(e.message);
        Toasty.success(context, e.message).show();
        switch (e.messageID) {
            case 1:

              /*  syncBtnAll.setEnabled(e.isBtnEnabled());
                progressBarSyncAll.setVisibility(View.GONE);*/
                syncBtnAll.setText("Start Sync All");

                uiChangeSyncStop(syncBtnAll, progressBarSyncAll, Global.syncInService);
                if (e.success) {
                    Toasty.success(context, Global.syncSuccessMsg).show();
                    syncAllTV.setText(lastsyncTimeTitile + e.lastSyncTime);
                    editor.putString(Global.timeAll, e.lastSyncTime);
                    editor.commit();
                } else Toasty.info(context, Global.syncFailedMsg).show();
                break;
            case 2:
                uiChangeSyncStop(syncBtnQues, progressBarSyncQues, Global.syncInActivity);
                if (e.success) {
                    Toasty.success(context, Global.syncSuccessMsg).show();
                    syncQuesTV.setText(lastsyncTimeTitile + e.lastSyncTime);
                    editor.putString(Global.timeQues, e.lastSyncTime);
                    editor.commit();
                } else Toasty.info(context, Global.syncFailedMsg).show();
                break;
            case 3:
                uiChangeSyncStop(syncBtnReport, progressBarSyncReport, Global.syncInActivity);
                if (e.success) {
                    Toasty.success(context, Global.syncSuccessMsg).show();
                    syncReportTV.setText(lastsyncTimeTitile + e.lastSyncTime);
                    editor.putString(Global.timeReport, e.lastSyncTime);
                    editor.commit();
                } else Toasty.info(context, Global.syncFailedMsg).show();
                break;
            case 4:
               /* syncBtnSchTechStd.setEnabled(e.isBtnEnabled());
                progressBarSyncSchTechStd.setVisibility(View.GONE);
*/

                uiChangeSyncStop(syncBtnSchTechStd, progressBarSyncSchTechStd, Global.syncInService);
                if (e.success) {
                    Toasty.success(context, Global.syncSuccessMsg).show();
                    syncSchTechStdTV.setText(lastsyncTimeTitile + e.lastSyncTime);
                    editor.putString(Global.timeSchTechStd, e.lastSyncTime);
                    editor.commit();
                } else Toasty.info(context, Global.syncFailedMsg).show();
            case 5:
                uiChangeSyncStop(syncBtnReportOld, progressBarSyncReportOld, Global.syncInActivity);
                if (e.success) {
                    Toasty.success(context, Global.syncSuccessMsg).show();
                    syncReportOldTV.setText(lastsyncTimeTitile + e.lastSyncTime);
                    editor.putString(Global.timeReportOld, e.lastSyncTime);
                    editor.commit();
                } else Toasty.info(context, Global.syncFailedMsg).show();
                break;

            case 6:
                uiChangeSyncStop(syncBtnPay, progressBarSyncPay, Global.syncInActivity);
                if (e.success) {
                    Toasty.success(context, Global.syncSuccessMsg).show();
                    syncPayTV.setText(lastsyncTimeTitile + e.lastSyncTime);
                    editor.putString(Global.timePay, e.lastSyncTime);
                    editor.commit();
                } else Toasty.info(context, Global.syncFailedMsg).show();
                break;
        }
    }


    void initializeViews() {
        //hide progressbars
        progressBarSyncReport.setVisibility(View.GONE);
        progressBarSyncQues.setVisibility(View.GONE);
        progressBarSyncSchTechStd.setVisibility(View.GONE);
        progressBarSyncAll.setVisibility(View.GONE);
        progressBarSyncReportOld.setVisibility(View.GONE);
        progressBarSyncPay.setVisibility(View.GONE);

        //set last synced time

        syncReportTV.setText(lastsyncTimeTitile + sp.getString(Global.timeReport, ""));
        syncReportOldTV.setText(lastsyncTimeTitile + sp.getString(Global.timeReportOld, ""));
        syncQuesTV.setText(lastsyncTimeTitile + sp.getString(Global.timeQues, ""));
        syncSchTechStdTV.setText(lastsyncTimeTitile + sp.getString(Global.timeSchTechStd, ""));
        syncAllTV.setText(lastsyncTimeTitile + sp.getString(Global.timeAll, ""));
        syncPayTV.setText(lastsyncTimeTitile + sp.getString(Global.timePay, ""));
    }

    void setBtnFocusWhenStartedFromNoDataClick() {
        animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
        if (btnFocus != null) {
            if (!btnFocus.equals("")) {
                if (btnFocus.equals("oldReport")) {
                    syncReportOldTV.setFocusable(true);
                    syncBtnReportOld.startAnimation(animShake);
                } else if (btnFocus.equals("report")) {
                    syncBtnReportContainer.setFocusable(true);
                    syncBtnReport.startAnimation(animShake);
                } else if (btnFocus.equals("schTchrStd")) {
                    syncBtnSchTechStdContainer.setFocusable(true);
                    syncBtnSchTechStd.startAnimation(animShake);
                } else if (btnFocus.equals("ques")) {
                    syncBtnQuesContainer.setFocusable(true);
                    syncBtnQues.startAnimation(animShake);
                } else if (btnFocus.equals("all")) {
                    syncBtnAllContainer.setFocusable(true);
                    syncBtnAll.startAnimation(animShake);
                } else if (btnFocus.equals("pay")) {
                    syncBtnPayContainer.setFocusable(true);
                    syncBtnPay.startAnimation(animShake);
                }
            }
        }
    }


    //======================== exception post =================================================================================================

    public MessageEvent exceptionEventBus(Exception excp, int msgID, String btnLabel) {
        MessageEvent e = new MessageEvent();
        e.setBtnEnabled(true);
        e.setMessageID(msgID);
        if (excp instanceof UnknownHostException) {
            e.setMessage(Global.noNetErrorMsg);
            e.setBtnLabel(btnLabel);
            e.setBtnEnabled(true);
            e.setSuccess(false);
        }
        if (excp instanceof SocketTimeoutException) {
            e.setMessage(Global.noNetErrorMsg);
            e.setBtnLabel(btnLabel);
            e.setBtnEnabled(true);
            e.setSuccess(false);
        } else {
            e.setMessage("Sync Failed.Try Again...");
            e.setBtnLabel(btnLabel);
            e.setBtnEnabled(true);
            e.setSuccess(false);
        }
        return e;
    }
}

