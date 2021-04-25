/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.MessageEvent;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.Teacher;
import com.example.f.schMon.model.oldAPI.TeacherModel;
import com.example.f.schMon.model.oldAPI.TeacherModel_id;
import com.example.f.schMon.model.oldAPI.TeacherModel_id_respnse;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.common.SettingsActivity;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.sync_mir.SyncSynchronous;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.fabric.sdk.android.Fabric;

public class TechrProfActivity extends AppCompatActivity {
    private final String TAG = TechrProfActivity.class.getName();
    private final Context context = TechrProfActivity.this;
    private SQLiteDatabase db;

    public int PAGE_NO = 0;
    public int total_downloaded = 0;
    public int total_beneficiary_in_server = 0;
    public ProgressDialog progressDialog = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int totalDetails_download = 0, totalTeacher, teacherImg;
    private TeacherModel_id_respnse resModel;
    private TeacherModel_id teacherModel_id;
    private ArrayList<String> idList;
    private List<TeacherModel> teacherList = new ArrayList<>();
    private String techrNme, techrPhn, techrGrade, techrAdrs;
    private Cursor cursorT;

    private String schID;
    private Snackbar snackbar;
    private Teacher techr;
    private SyncSynchronous syncSynchronous;
    Intent intent;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.teachername)
    TextView techrNmeTxtVw;
    @BindView(R.id.teacherphone)
    TextView techrPhnTxtVw;
    @BindView(R.id.teachergrade)
    TextView techrGradeTxtVw;
    @BindView(R.id.address)
    TextView techrAdrsTxtVw;
    @BindView(R.id.tchrNoData)
    TextView noDataTV;
    @BindView(R.id.poImgView)
    ImageView techerImgV;
    @BindView(R.id.teacherEducation)
    TextView teacherEducation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //___________________start option menu______________________________________

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);//Menu Resource, Menu
        return true;
    }

    /**
     * hiding irrelevant option menu for this Activity
     */
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu:
                startActivity(new Intent(context, DashboardActivity.class));
                return true;

            case R.id.sync_menu:
                if (H.isInternetConnected(context)) {
                    syncSynchronous = new SyncSynchronous(context);
                    if (!syncTechThread.isAlive()) {
                        Toasty.info(context, "Sync Started").show();
                        noDataTV.setText(Global.syncRunningDontClose);
                        snackbar = Snackbar.make(getCurrentFocus(), Global.syncRunningDontClose, Snackbar.LENGTH_INDEFINITE);
                        snackbar.show();
                        pb.setVisibility(View.VISIBLE);
                        syncTechThread.start();
                    }
                } else Toasty.error(context, Global.noNetErrorMsg).show();
                return true;

            case R.id.settings_menu:
                Intent intent = new Intent(context, SettingsActivity.class);
                context.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //___________________End option menu______________________________________

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);  } */ else {
            super.onBackPressed();
            Intent intentSchProf = new Intent(context, SchoolProfileActivity.class);
            intentSchProf.putExtra("schID", schID);
            startActivity(intentSchProf);
        }
    }


    //_________________Start of Sync_______________________________

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //___________________set data___________________

        intent = getIntent();
        schID = intent.getStringExtra("schID");

        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        if (DAO.isTableEmpty(db, DB.teachers.table)) {
            pb.setVisibility(View.GONE);
        } else
            new LoadData().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_techr_prof);
        ButterKnife.bind(this);
        //appbar
        setSupportActionBar(toolbar);

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        // get writable global db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);


        //___________________Font___________________
          H.setFont(context, new View[]{techrNmeTxtVw, techrPhnTxtVw, techrGradeTxtVw, techrAdrsTxtVw, noDataTV, teacherEducation});

        //  syncSynchronous = new SyncSynchronous(context);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursorT != null)
            cursorT.close();
    }

    private int setImgAccordingToGender() {
        int techImg = R.drawable.ic_teacher_male;
        if (techr.gender == null)
            return techImg;
        else {
            if (techr.gender.equals("") || techr.gender.equalsIgnoreCase("M") || techr.gender.equalsIgnoreCase("male"))
                return techImg;
            else /*if (techr.gender.equalsIgnoreCase("F") || techr.gender.equalsIgnoreCase("female"))*/
                techImg = R.drawable.ic_teacher_female;
        }
        return techImg;
    }

    //================method=========================================================================================================

    private Thread syncTechThread = new Thread(new Runnable() {
        MessageEvent event = new MessageEvent();

        @Override
        public void run() {
            try {
                syncSynchronous = new SyncSynchronous(context);
                syncSynchronous.teacherSync();
                event.setSuccess(true);
                EventBus.getDefault().post(event);
            } catch (Exception e) {
                Log.e(TAG, "exception", e);
                event.setSuccess(false);
                EventBus.getDefault().post(event);
            }
        }
    });

    @Subscribe(threadMode = ThreadMode.MAIN)
    void onMessageEvent(MessageEvent event) {
        pb.setVisibility(View.GONE);
        if (event.success) {
            Toasty.success(context, "Sync Completed.").show();
            snackbar.dismiss();
            H.restartActivity(context);
        } else {
            Toasty.error(context, "Not Synced Properly.Try Again Later", Toast.LENGTH_LONG).show();
            noDataTV.setText("Not Synced Properly.Try Again Later");
            snackbar.dismiss();
        }
    }

    //==================== class =====================================================================================================

    class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            techr = DAO.getTeachersAllInfoBySchID(schID);
            //Log.d(TAG, techr.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pb.setVisibility(View.GONE);
            noDataTV.setVisibility(View.GONE);
            if (techr != null) {
                techerImgV.setBackgroundResource(setImgAccordingToGender());
                if (techr.tName == null)
                    techrNmeTxtVw.setText(H.underscoreToSpacedFormat("Name : Not Available"));
                else
                    techrNmeTxtVw.setText(H.underscoreToSpacedFormat(techr.tName));

                if (techr.education == null)
                    teacherEducation.setText("Educational Qualification: Not Available");
                else
                    teacherEducation.setText("Educational Qualification: " + techr.education);

                if (techr.tMobi == null)
                    techrPhnTxtVw.setText("Contact: Not Available");
                else
                    techrPhnTxtVw.setText("Contact: " + techr.tMobi);

                if (techr.techrGrade == null)
                    techrGradeTxtVw.setText("Teacher's Grade: Not Available");
                else
                    techrGradeTxtVw.setText("Teacher's Grade: " + techr.techrGrade);

                if (techr.address == null)
                    techrAdrsTxtVw.setText("Address: Not Available");
                else
                    techrAdrsTxtVw.setText("Address: " + H.toCamelCase(techr.address));
            }
        }
    }
}
