/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.school;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.f.schMon.view.sync_mir.SyncSynchronous;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.fabric.sdk.android.Fabric;

public class TechActivity extends AppCompatActivity {
    private final String TAG = TechActivity.class.getName();
    private final Context context = TechActivity.this;
    private SQLiteDatabase db;
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

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
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
    public void onMessageEvent(MessageEvent event) {
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
                //techerImgV.setBackgroundResource(setImgAccordingToGender());
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
