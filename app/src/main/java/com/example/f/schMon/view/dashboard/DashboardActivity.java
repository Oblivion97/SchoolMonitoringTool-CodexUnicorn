/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.oldAPI.LoginModel;
import com.example.f.schMon.model.oldAPI.UserModel;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.common.UserProfActivity;
import com.example.f.schMon.view.log_edit.LogEditActivityTab;
import com.example.f.schMon.view.notification.NotifActivity;
import com.example.f.schMon.view.notification.NotifService;
import com.example.f.schMon.view.school.SchoolListActivity;
import com.example.f.schMon.view.sync_mir.SyncActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class DashboardActivity extends AppCompatActivity {
    private final String TAG = DashboardActivity.class.getName();
    private Context context = DashboardActivity.this;
    private String typeAttndnc = "attendance";
    private String typeEnv = "environment";
    private String typeEvl = "evaluation";
    private SQLiteDatabase db;
    private ArrayList<String> results = new ArrayList<>();
    private List<School> schInfos = new ArrayList<>();
    private List<LastPerfMdl> lastPerfMdls = new ArrayList<>();
    private List<Integer> avgPerformanceList;
    private DashAdapter adapter;
    private int progrssPercent = 0;
    private int curStatus = 0;
    private Handler handler = new Handler();
    private int[] visitedSch = new int[2];
    private int dashBtnColor, dashBtnDefault;
    @BindView(R.id.dash_progTitle)
    TextView progTitle;
    @BindView(R.id.dash_progress)
    TextView dashProgress;
    @BindView(R.id.circularProgressBar)
    ProgressBar progressBar;
    @BindView(R.id.dash_schoolList)
    ListView listView;
    @BindView(R.id.dash_username)
    TextView userNameTV;
    @BindView(R.id.dash_mobile)
    TextView userMobileTV;
    @BindView(R.id.dash_userPic)
    CircleImageView userIV;
    @BindView(R.id.bottomNavigation)
    BottomNavigationView navigation;
    @BindView(R.id.dash_rank)
    TextView dashRank;
    @BindView(R.id.dash_tv_attendance)
    TextView dash_tv_attendance;
    @BindView(R.id.dash_tv_activities)
    TextView dash_tv_activities;
    @BindView(R.id.dash_tv_infrastructure)
    TextView dash_tv_infrastructure;
    @BindView(R.id.dash_listValue)
    TextView dash_listValue;
    @BindView(R.id.dash_listName)
    TextView dash_listName;
    @BindView(R.id.noDataContainer)
    View noDataContainer;
    @BindView(R.id.noDataTV)
    TextView noDataTV;
    @BindView(R.id.noDataIV)
    ImageView noDataIV;
    @BindView(R.id.noDataBtn)
    Button noDataBtn;
    @BindView(R.id.visitedSchContainer)
    View visitedSchoolContainer;
    @BindView(R.id.visitedSchValue)
    TextView visitedSchValue;
    @BindView(R.id.visitedSchTitle)
    TextView visitedSchTitle;
    @BindView(R.id.dash_attendance)
    CircleImageView dash_attendance;
    @BindView(R.id.dash_evl)
    CircleImageView dash_evl;
    @BindView(R.id.dash_environment)
    CircleImageView dash_environment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //___________________bottom navigation menu______________________________________
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_log:
                    startActivity(new Intent(context, LogEditActivityTab.class));
                    return true;
                case R.id.navigation_schList:
                    Intent intent = new Intent(context, SchoolListActivity.class);
                    intent.putExtra(Global.toOpenWhichActivity, Global.SchoolProfile);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notific:
                    startActivity(new Intent(context, NotifActivity.class));
                    return true;
            }
            return false;
        }

    };


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
            menu.findItem(R.id.home_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        } else {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    //=====================  =========================================================================================


    @Override
    protected void onResume() {
        super.onResume();
        new LoadData().execute();
        if (DAO.isTableEmpty(db, DB.perform.table)) {
            noDataContainer.setVisibility(View.VISIBLE);
            noDataContainer.setFocusable(true);
            //listView.setVisibility(View.GONE);
        } else noDataContainer.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        /**used for app exit (start)*/
        if (getIntent().getBooleanExtra("Exit", false)) {
            finish();
        }
        /**used for app exit (end)*/

        //appbar
        setSupportActionBar(toolbar);

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        //bottom navigation
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        H.setFont(context, new View[]{userNameTV, userMobileTV, dashRank, progTitle, dash_tv_attendance,
                dash_tv_activities, dash_tv_infrastructure, dash_listValue, dash_listName, noDataTV, noDataBtn, visitedSchValue
                , visitedSchTitle});

        dashBtnColor = ContextCompat.getColor(context, R.color.dashBtn);
        dashBtnDefault = ContextCompat.getColor(context, R.color.white);

        //_________ set Data _____________
        setPOInfos();

        //notification schedule again from dashboard, another schedule instance done form service
        Intent i = new Intent(context, NotifService.class);
        context.startService(i);
    }

    //==================================== click event ==================================================================
    @OnClick(R.id.dash_userPic)
    void onClickUserImg(View v) {
        startActivity(new Intent(context, UserProfActivity.class));
    }


    @OnClick(R.id.dash_attendance)
    void onClickAttndnc(View v) {
        dash_attendance.setImageResource(R.drawable.ic_attendance_black);
        dash_environment.setImageResource(R.drawable.ic_env_dash);
        dash_evl.setImageResource(R.drawable.ic_evl_dash);

        curStatus = 0;
        progrssPercent = avgPerformanceList.get(0);
        animateProgressBar();
        progTitle.setText("Overall Attendance");
        if (schInfos.size() > 0) {
            noDataContainer.setVisibility(View.GONE);
            adapter = new DashAdapter(context, typeAttndnc, lastPerfMdls, schInfos);
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.GONE);
            noDataContainer.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.dash_evl)
    void onClickEvl(View v) {
        dash_evl.setImageResource(R.drawable.ic_evl_dash_black);
        dash_attendance.setImageResource(R.drawable.ic_attendance_dash);
        dash_environment.setImageResource(R.drawable.ic_env_dash);

        curStatus = 0;
        progrssPercent = avgPerformanceList.get(1);
        animateProgressBar();
        progTitle.setText("Overall Evaluation");
        if (!DAO.isTableEmpty(db, DB.perform.table)) {
            noDataContainer.setVisibility(View.GONE);
            adapter = new DashAdapter(context, typeEvl, lastPerfMdls, schInfos);
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.GONE);
            noDataContainer.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.dash_environment)
    void onClickEnv(View v) {
        dash_evl.setImageResource(R.drawable.ic_evl_dash);
        dash_attendance.setImageResource(R.drawable.ic_attendance_dash);
        dash_environment.setImageResource(R.drawable.ic_env_black);


        curStatus = 0;
        progrssPercent = avgPerformanceList.get(2);
        animateProgressBar();
        progTitle.setText("Overall Environment");
        if (!DAO.isTableEmpty(db, DB.perform.table)) {
            noDataContainer.setVisibility(View.GONE);
            adapter = new DashAdapter(context, typeEnv, lastPerfMdls, schInfos);
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.GONE);
            noDataContainer.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.noDataTV)
    void onClickNoDataTV(View v) {
        Intent intent1 = new Intent(context, SyncActivity.class);
        intent1.putExtra(Global.btnFocus, "oldReport");
        intent1.putExtra(Global.syncID, "5");
        startActivity(intent1);
    }

    //==================================== perform ==================================================================

    public void animateProgressBar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (curStatus <= progrssPercent) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(curStatus);
                            dashProgress.setText(curStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (progrssPercent > 0)// added later
                        curStatus++;
                }
            }
        }).start();
    }


    //==================================== Load Date Thread ==================================================================
    void setPOInfos() {
        //__________ get po info _______________
        UserModel userM;
        SharedPreferences sp = getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        String responseSave = sp.getString(Global.userModel, "");
        Gson gson = new Gson();
        if (responseSave.equals("")) {
            userM = new UserModel();
        } else {
            LoginModel mResponseModel = gson.fromJson(responseSave, LoginModel.class);
            userM = mResponseModel.getModel();
        }

        if (userM.getFirstName() == null && userM.getMiddleName() == null && userM.getLastName() == null)
            userNameTV.setText(H.toCamelCase(userM.getUsername()).replaceAll("_", " "));
        else if (userM.getFirstName() != null && userM.getLastName() != null)
            userNameTV.setText(H.toCamelCase(userM.getFirstName() + " " + userM.getLastName()));
        else
            userNameTV.setText(H.toCamelCase(userM.getFirstName() + " " + userM.getMiddleName() + " " + userM.getLastName()));


        userMobileTV.setText(userM.getMobileNumber());
    }


    class LoadData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            visitedSch = DAO.getTotalVisitedSchoolByTotalSchoolThisMonth();
            lastPerfMdls = DAO.getLastPerfAllSchoolFromPerformTable();
            // Log.d(TAG, lastPerfMdls.toString());
            schInfos = DAO.getInfoOfAllSchool();
            avgPerformanceList = Calc.avgPerformCalc(lastPerfMdls);
            adapter = new DashAdapter(context, typeEnv, lastPerfMdls, schInfos);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            visitedSchValue.setText(visitedSch[0] + "/" + visitedSch[1]);
            listView.setAdapter(adapter);
            //default performance environment set
            progrssPercent = avgPerformanceList.get(2);
            progTitle.setText("Overall Environment");
            animateProgressBar();
        }
    }
}




