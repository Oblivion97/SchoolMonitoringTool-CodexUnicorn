/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.MessageEvent;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.sync_mir.Constants;
import com.example.f.schMon.view.sync_mir.ForegroundService;
import com.example.f.schMon.view.sync_mir.SyncH;
import com.example.f.schMon.view.sync_mir.SyncSynchronous;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class SchPayActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private Context context = SchPayActivity.this;
    @BindView(R.id.schPayList)
    RecyclerView recyclerView;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.noDataTV)
    TextView noDataTV;
    @BindView(R.id.syncPayBtn)
    Button syncPayBtn;
    @BindView(R.id.noDataContainer)
    LinearLayout noDataContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean dataUpdated, dataAvailable;
    private List<School> list = new ArrayList<>();
    private SchPayAdapter schPayAdapter;
    private String lastSyncTime;
    private SimpleDateFormat df4 = new SimpleDateFormat("dd MMM yyyy, hh:mm");
    private SimpleDateFormat df7 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private Snackbar snackbar;
    private boolean serviceRunning;
    private String SyncTxt = "Payment is Not Available. Sync... ";
    private String UpdateTxt = "Payment is Not Updated. Update... ";
    private String btnLabelSync = "Sync";
    private String btnLabelUpdate = "Update";
    private String syncRunningMsg = "Payment Data is Syncing";
//___________________Start option menu___________________________________________________________________________________

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            menu.findItem(R.id.sync_menu).setVisible(true);
            menu.findItem(R.id.home_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_menu:
                if (H.isInternetConnected(context))
                    //  sync();
                    syncService();
                else
                    Toasty.error(context, Global.noNetErrorMsg, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*___________________End option menu________________________________________________________*/
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
    protected void onResume() {
        super.onResume();


        serviceRunning = SyncH.isMyServiceRunning(ForegroundService.class, this);

        dataAvailable = DAO2.isPaymentDataAvailable();
        // dataUpdated = H.isPaymentSyncedThisMonth(context);//payment updated or not
        dataUpdated = true;// to make update dialog invisible
        if (dataAvailable) {
            if (dataUpdated) {
                noDataContainer.setVisibility(View.GONE);
                new LoadData().execute();
            } else {
                noDataContainer.setVisibility(View.VISIBLE);
                noDataTV.setText(UpdateTxt);
                syncPayBtn.setText(btnLabelUpdate);
                new LoadData().execute();
            }
        } else {
            noDataContainer.setVisibility(View.VISIBLE);
            noDataTV.setText(SyncTxt);
            syncPayBtn.setText(btnLabelSync);
        }


        if (serviceRunning && A.syncMsgID == 6) {
            Log.d(TAG, String.valueOf(A.syncMsgID));
            pb.setVisibility(View.VISIBLE);
            snackbar = Snackbar.make(syncPayBtn, syncRunningMsg, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        } else {
            pb.setVisibility(View.GONE);
            if (snackbar != null)
                snackbar.dismiss();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sch_pay);
        ButterKnife.bind(this);
        //app bar
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        H.setFont(context, new View[]{noDataTV});
    }


    @OnClick(R.id.syncPayBtn)
    void onClicksyncPayBtn(View v) {
        if (H.isInternetConnected(context))
            sync();
        else
            Toasty.error(context, Global.noNetErrorMsg, Toast.LENGTH_SHORT).show();
    }

    void sync() {
        syncPayBtn.setEnabled(false);
        noDataTV.setText(syncRunningMsg);
        pb.setVisibility(View.VISIBLE);
        snackbar = Snackbar.make(syncPayBtn, syncRunningMsg, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        Thread thread;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String year = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
                SyncSynchronous syncSynchronous = new SyncSynchronous(context);
                try {
                    if (!dataAvailable)
                        syncSynchronous.schoolSync();
                    syncSynchronous.syncPaymentAllSchool(year);
                    lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
                    H.saveTimeOfLastPaymentSync(context, df7.format(new Date(System.currentTimeMillis())));
                    EventBus.getDefault().post(new MessageEvent(6, "Payment Data Updated.",
                            true, true, lastSyncTime));
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    lastSyncTime = df4.format(new Date(System.currentTimeMillis()));
                    EventBus.getDefault().post(new MessageEvent(6, "Sync Failed.Try Again...",
                            false, true, lastSyncTime));
                }
            }
        });
        thread.start();
    }

    void syncService() {
        if (!SyncH.isMyServiceRunning(ForegroundService.class, this)) {
            syncPayBtn.setEnabled(false);
            noDataTV.setText(syncRunningMsg);
            pb.setVisibility(View.VISIBLE);
            snackbar = Snackbar.make(syncPayBtn, syncRunningMsg, Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            A.syncMsgID = 6;
            startSyncService();
        }
    }

    private Intent serviceIntent;

    void startSyncService() {
        serviceIntent = new Intent(context, ForegroundService.class);
        if (!ForegroundService.IS_SERVICE_RUNNING) {
            serviceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = true;
        } else {
            serviceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
            ForegroundService.IS_SERVICE_RUNNING = false;
        }
        startService(serviceIntent);
    }

    //=============== methods ==========================================================================================================
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

       /* if (A.syncMsgID == 6) {
            Toasty.success(context, event.message).show();
        }*/


        Toasty.info(context, event.message, Toast.LENGTH_LONG).show();
        pb.setVisibility(View.GONE);
        snackbar.dismiss();

        if (event.success) {
            syncPayBtn.setEnabled(event.isBtnEnabled());
            noDataContainer.setVisibility(View.GONE);
            new LoadData().execute();
        } else {
            noDataTV.setText(event.message);
            //pb.setVisibility(View.GONE);
        }
        H.restartActivity(context);
    }


    //============== class ===========================================================================================================
    class LoadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            list = DAO.getAllInfoOfAllSchool();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            schPayAdapter = new SchPayAdapter(context, list);
            recyclerView.setAdapter(schPayAdapter);
            if (!serviceRunning)
                pb.setVisibility(View.GONE);
        }
    }
}
