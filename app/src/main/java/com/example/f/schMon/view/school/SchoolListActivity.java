/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.MessageEvent;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.oldAPI.SchoolListModel;
import com.example.f.schMon.model.oldAPI.SchoolModel;
import com.example.f.schMon.model.oldAPI.SchoolModel_id;
import com.example.f.schMon.model.oldAPI.SchoolModel_id_respnse;
import com.example.f.schMon.view.common.DatePickerFragment;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.evaluation.DialogDateAttdce;
import com.example.f.schMon.view.evaluation.EvlActivity;
import com.example.f.schMon.view.log_edit.LogEditActivityTab;
import com.example.f.schMon.view.notification.NotifActivity;
import com.example.f.schMon.view.sync_mir.SyncSynchronous;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class SchoolListActivity extends AppCompatActivity implements
        DialogDateAttdce.DialogDateAttdce_StartEval, DatePickerFragment.DateDialogListner {
    private final String TAG = SchoolListActivity.class.getName();
    private final Context context = SchoolListActivity.this;
    private Gson mGson = new Gson();
    public int PAGE_NO = 0;
    public int totalBasics_downloaded = 0;
    public int total_beneficiary_in_server = 0;
    public ProgressDialog progressDialog = null;
    public int getTotalDetails_download = 0;
    private Intent intent;
    SQLiteDatabase db;
    SchoolModel_id_respnse resModel;
    SchoolModel_id schoolModel_id;

    private SchoolListModel responseModel;
    List<School> schList = new ArrayList<>();
    List<String> idList = new ArrayList<>();
    private List<SchoolModel> schoolModels = new ArrayList<>();
    private SchoolListAdapter adapter;
    private String toOpenWhichActivity, syncStatus;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    private SyncSynchronous syncSynchronous = new SyncSynchronous(context);
    private String gradeID;
    @BindView(R.id.noDataTV)
    TextView noDataTV;
    @BindView(R.id.schoolList_recycler_view)
    RecyclerView schoolListRV;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;


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
                if (H.isInternetConnected(context)) {
                    if (!syncThread.isAlive()) {
                        Toasty.info(context, "Sync Started").show();
                        noDataTV.setText(Global.syncRunningDontClose);
                        pb.setVisibility(View.VISIBLE);
                        syncThread.start();
                    }
                } else Toasty.error(context, "No Internet.Try Again").show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*___________________End option menu________________________________________________________*/

    /* __________ nav drawer close on back button click _____________________________________*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        } else if (!drawer.isDrawerOpen(GravityCompat.START)
                && !swipeRefreshLayout.isRefreshing()) {
            startActivity(new Intent(context, DashboardActivity.class));
            finish();
        }*/ else {
            super.onBackPressed();
            startActivity(new Intent(context, DashboardActivity.class));
            finish();
        }
    }


    //___________________bottom navigation menu_____________________________________________________________________________
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_log:
                    // temp dash test
                    startActivity(new Intent(context, LogEditActivityTab.class));
                    //new AlertDialog.Builder(context).setMessage("Message").create().show();
                    return true;
                case R.id.navigation_schList:
                    startActivity(new Intent(context, DashboardActivity.class));

                    /*Intent intent = new Intent(context, SchoolListActivity.class);
                    intent.putExtra("openedWhichActivity", "SchoolProfile");
                    startActivity(intent);*/
                    return true;
                case R.id.navigation_notific:
                    startActivity(new Intent(context, NotifActivity.class));
                    return true;
            }
            return false;
        }

    };


    /*======================= activity's methods ========================================================================================================*/

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//sets changed intent
    }

    @Override
    protected void onResume() {
        super.onResume();

         /*=========== set data =======*/

        intent = getIntent();
        toOpenWhichActivity = intent.getStringExtra(Global.toOpenWhichActivity);


        if (DAO.isTableEmpty(db, DB.schools.table)) {
            pb.setVisibility(View.GONE);
        } else
            new LoadData().execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         /*toolbar.setMonthName("List of Schools");*/

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        //bottom navigation
        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //get writable db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    //================== methods =======================================================================================================

    private Thread syncThread = new Thread(new Runnable() {
        MessageEvent e;

        @Override
        public void run() {
            try {
                e = new MessageEvent();
                syncSynchronous.schoolSync();
                e.setSuccess(true);
                e.setMessage("Sync Completed");
                EventBus.getDefault().post(e);
            } catch (Exception excep) {
                Log.e(TAG, "exception", excep);
                e = new MessageEvent();
                e.setSuccess(false);
                e.setMessage("Not Synced Properly.Try Again Later");
                EventBus.getDefault().post(e);
            }
        }
    });

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSyncComplete(MessageEvent e) {
        pb.setVisibility(View.GONE);
        Toasty.info(context, e.message, Toast.LENGTH_LONG).show();
        noDataTV.setText(e.message);
        if (e.success == true) {
            H.restartActivity(context);
        }
    }


    //=================== evaluation dialog =========================================================================================
    boolean dateInputted;
    String date;

    @Override
    public void onStartEvalClick(String schID, String gradeID, String date, String attendance) {
        this.date = date;
        dateInputted = H.isDateInputted(date);
        if (!dateInputted || attendance.equals("")) {
            new AlertDialog.Builder(context).setIcon(R.drawable.ic_warning)
                    .setTitle("Input Date and Attendance of Students")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();

        } else {
            Intent intent = new Intent(this, EvlActivity.class);
            intent.putExtra(Global.openMode, Global.NewMode);
            intent.putExtra(Global.openedFromWhichActivity, SchoolListActivity.class.getName());
            intent.putExtra("schID", schID);
            intent.putExtra("gradeID", gradeID);
            intent.putExtra("date", date);
            intent.putExtra("attendance", attendance);
            startActivity(intent);
        }
    }

    @Override
    public void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar) {
        this.dateInputted = dateInputted;//date is inputted from date picker fragment
        date = df1.format(calendar.getTime());
    }

    //=================== class ====================================================================================================
    class LoadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            schList = DAO.getInfoOfAllSchool();
            return syncStatus;
        }

        @Override
        protected void onPostExecute(String strings) {
            Log.d(TAG, toOpenWhichActivity);
            schoolListRV.setLayoutManager(new LinearLayoutManager(context));
            schoolListRV.setAdapter(new SchoolListAdapter(context, schList, toOpenWhichActivity));
            pb.setVisibility(View.GONE);
            noDataTV.setVisibility(View.GONE);
        }
    }
}
