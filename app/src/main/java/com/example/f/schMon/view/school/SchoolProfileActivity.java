/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.school;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.appInner.Teacher;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.common.SettingsActivity;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.evaluation.DialogDateAttdce;
import com.example.f.schMon.view.evaluation.EvlActivity;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.report_environment.RepActivity;
import com.example.f.schMon.view.report_environment.RepMdlin;
import com.example.f.schMon.view.student.StdListActivity;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class SchoolProfileActivity extends AppCompatActivity implements
        DialogDateAttdce.DialogDateAttdce_StartEval {
    private static final String TAG = SchoolProfileActivity.class.getName();
    private final Context context = SchoolProfileActivity.this;
    private String schID, gradeID;
    private String poNme;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    public String date;
    private LastPerfMdl lpm;
    private School s;
    private boolean dateInputted = false;
    private SQLiteDatabase db;
    private Teacher tech;
    private Intent intent;
    Snackbar snackbar1;
    Snackbar snackbar2;
    @BindView(R.id.schName)
    TextView schNmeTxt;
    @BindView(R.id.educationType)
    TextView eduTypeTxt;
    @BindView(R.id.teacherTV)
    TextView teacherTxt;
    @BindView(R.id.schoolcode)
    TextView schCodeTxt;
    @BindView(R.id.schProfGrade)
    TextView gradeTV;
    @BindView(R.id.totalstudent)
    TextView totalStdTxt;
    @BindView(R.id.poTV)
    TextView poNmeTxt;
    @BindView(R.id.perfRepAdminValue)
    TextView perfAdminTV;
    @BindView(R.id.perfEvalValue)
    TextView perfEvalTV;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.htryDate1)
    TextView htryDate1;
    @BindView(R.id.htryAcadPerf1)
    TextView htryAcadPerf1;
    @BindView(R.id.htryAdminPerf1)
    TextView htryAdminPerf1;
    @BindView(R.id.htryDate2)
    TextView htryDate2;
    @BindView(R.id.htryAcadPerf2)
    TextView htryAcadPerf2;
    @BindView(R.id.htryAdminPerf2)
    TextView htryAdminPerf2;
    @BindView(R.id.htryDate3)
    TextView htryDate3;
    @BindView(R.id.htryAcadPerf3)
    TextView htryAcadPerf3;
    @BindView(R.id.htryAdminPerf3)
    TextView htryAdminPerf3;
    @BindView(R.id.htryDate4)
    TextView htryDate4;
    @BindView(R.id.htryAcadPerf4)
    TextView htryAcadPerf4;
    @BindView(R.id.htryAdminPerf4)
    TextView htryAdminPerf4;
    @BindView(R.id.htryDate5)
    TextView htryDate5;
    @BindView(R.id.htryAcadPerf5)
    TextView htryAcadPerf5;
    @BindView(R.id.htryAdminPerf5)
    TextView htryAdminPerf5;
    @BindView(R.id.reportingBtn)
    Button reportingBtn;
    @BindView(R.id.evaluationBtn)
    Button evalBtn;
    @BindView(R.id.studentsBtn)
    Button stdBtn;
    @BindView(R.id.repNewRepSingleRadio)
    Button perFormGrphBtn;
    @BindView(R.id.repNewReport)
    Button attdnceBtn;
    @BindView(R.id.perfEval)
    CircularProgressBar perfEvalPB;
    @BindView(R.id.perfRepAdmin)
    CircularProgressBar perfAdminPB;
    @BindView(R.id.perfRepGraph)
    View perfAdminView;
    @BindView(R.id.perfEvalGraph)
    View perfEvalView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


//___________________Start option menu______________________________________

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);//Menu Resource, Menu
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
            case R.id.home_menu:
                startActivity(new Intent(context, DashboardActivity.class));
                return true;
            case R.id.settings_menu:
                item.setIntent(new Intent(context, SettingsActivity.class));
                startActivity(item.getIntent());
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
        } else {
            /* if schoolList is opened for other task ie evaluation or report after opening schProfile activity(this activity)
            *  in that case schoolList activity can be finished before school profile acitvity, following code will ensure
            *  that schoolList activity will always be opened after schoolProfile back btn press*/
           /* Intent intentSchListOpen = new Intent(context, SchoolListActivity.class);
            intentSchListOpen.putExtra(Global.toOpenWhichActivity, Global.SchoolProfile);
            startActivity(intentSchListOpen);*/
            super.onBackPressed();
        }
    }

    //===============================================================================================================
    @Override
    public void onStart() {
        super.onStart();
        //   EventBus.getDefault().register(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ============  set data  ==============
        intent = getIntent();
        schID = intent.getStringExtra("schID");
        gradeID = intent.getStringExtra("gradeID");
        db = Global.getDB_with_nativeWay_writable(Global.dbName);
        loadData();
        bindViewData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_profile);
        ButterKnife.bind(this);
        //appbar
        setSupportActionBar(toolbar);

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);


        H.setFont(context, new View[]{
                schNmeTxt, gradeTV, eduTypeTxt, schCodeTxt, totalStdTxt, poNmeTxt, reportingBtn, evalBtn, stdBtn,
                perFormGrphBtn, attdnceBtn, teacherTxt});


    }

    @Override
    public void onStop() {
        super.onStop();
        // EventBus.getDefault().unregister(this);
    }

    private void bindViewData() {
        schNmeTxt.setText("School Name: " + H.toCamelCase(s.getSchName()));
        gradeTV.setText("Grade: " + s.getGrade());
        schCodeTxt.setText("School Code: " + s.getSchCode());
        totalStdTxt.setText("Total Students: " + s.getTotalStd());
        eduTypeTxt.setText("Education Type: " + s.getEdu_type());
        poNmeTxt.setText("PO: " + H.toCamelCase(poNme));

        if (tech != null) {
            if (!tech.tName.equals(""))
                teacherTxt.setText("Teacher: " + H.underscoreToSpacedFormat(tech.tName));
            else teacherTxt.setText("Teacher: Not Available");
        }

        /*======================= Perf Graph =====================================*/
        float p1 = 0, p2 = 0;
        String ps1 = "0", ps2 = "0";
        int animationDuration = 7000;
        if (lpm != null) {
            if (lpm.getLastRepPerform() == null)
                lpm.setLastRepPerform("0");
            if (lpm.getLastEvalPerform() == null)
                lpm.setLastEvalPerform("0");

            try {
                p1 = Math.round(Float.parseFloat(H.floatToZeroDecimalPlace(lpm.getLastEvalPerform())));
                p2 = Math.round(Float.parseFloat(H.floatToZeroDecimalPlace(lpm.getLastRepPerform())));
                ps1 = H.floatToZeroDecimalPlace(lpm.getLastEvalPerform());
                ps2 = H.floatToZeroDecimalPlace(lpm.getLastRepPerform());
            } catch (NumberFormatException e) {
                Log.d(TAG, "Exception", e);
            }
        }
        perfEvalPB.setProgressWithAnimation(p1, animationDuration);
        perfAdminPB.setProgressWithAnimation(p2, animationDuration);
        perfEvalTV.setText(ps1 + "%");
        perfAdminTV.setText(ps2 + "%");
    }

    private void loadData() {
        poNme = Global.getUserCredentialFromSharePreference(context).getUsername();

        s = DAO.getInfoOfASchool(schID);
        tech = DAO.getTeachersAllInfoBySchID(schID);
        lpm = DAO.getLastPerformbySchoolID(db, schID);

         /*======================= HistoryActivity =====================================*/
        List<PerfMdl> evl = new ArrayList<>(), rep = new ArrayList<>();


        try {
            evl = DAO2.getLast_n_EvlPerform1School(schID, 5);
            rep = DAO2.getLast_n_RepPerform1School(schID, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }


        pb.setVisibility(View.GONE);

        if (evl.size() >= 1 && rep.size() >= 1) {
            htryDate1.setText(evl.get(0).getDate_report());
            htryAdminPerf1.setText(rep.get(0).getPerf() + "%");
            htryAcadPerf1.setText(evl.get(0).getPerf() + "%");
        }
        if (evl.size() >= 2 && rep.size() >= 2) {
            htryDate2.setText(evl.get(1).getDate_report());
            htryAdminPerf2.setText(rep.get(1).getPerf() + "%");
            htryAcadPerf2.setText(evl.get(1).getPerf() + "%");
        }
        if (evl.size() >= 3 && rep.size() >= 3) {
            htryDate3.setText(evl.get(2).getDate_report());
            htryAdminPerf3.setText(rep.get(2).getPerf() + "%");
            htryAcadPerf3.setText(evl.get(2).getPerf() + "%");
        }
        if (evl.size() >= 4 && rep.size() >= 4) {
            htryDate4.setText(evl.get(3).getDate_report());
            htryAdminPerf4.setText(rep.get(3).getPerf() + "%");
            htryAcadPerf4.setText(evl.get(3).getPerf() + "%");
        }
        if (evl.size() >= 5 && rep.size() >= 5) {
            htryDate5.setText(evl.get(4).getDate_report());
            htryAdminPerf5.setText(rep.get(4).getPerf() + "%");
            htryAcadPerf5.setText(evl.get(4).getPerf() + "%");
        }
    }


    //========================== Button listeners ======================================

    @OnClick(R.id.teacherTV)
    public void onClickTeacherTV(View view) {
        Intent intent = new Intent(this, TechrProfActivity.class);
        intent.putExtra("schID", schID);
        startActivity(intent);
    }

    @OnClick(R.id.studentsBtn)
    public void onClickStudentBtn(View view) {
        Intent intent = new Intent(this, StdListActivity.class);
        intent.putExtra("schID", schID);
        startActivity(intent);
    }

    //=========== rep click ===============


    @OnClick(R.id.repNewReport)/*final report*/
    public void onClickrepNewReportBtn(View view) {
        Intent intent = new Intent(context, RepActivity.class);
        intent.putExtra("schID", schID);
        intent.putExtra("gradeID", gradeID);
        intent.putExtra(Global.openMode, Global.NewMode);
        intent.putExtra(Global.openedFromWhichActivity, SchoolProfileActivity.class.getName());
        context.startActivity(intent);
    }

    //=========== evl click ===============
    @OnClick(R.id.evaluationBtn)
    public void onClickEvaluationBtn(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("schID", schID);
        bundle.putString("gradeID", gradeID);
        bundle.putString(Global.openMode, Global.NewMode);
        bundle.putString(Global.openedFromWhichActivity, SchoolProfileActivity.class.getName());

        DialogFragment dialog = new DialogDateAttdce();
        dialog.setArguments(bundle);
        dialog.setCancelable(false);
        dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "DialogDateAttdce");
    }


    //=========== graph click ===============
    @OnClick(R.id.perfRepGraph)
    public void onClickperfReportCircle(View view) {

        String date = DAO2.lastDateForASchool(schID, DB.report_admin.table,
                DB.report_admin.sch_id, DB.report_admin.date_report);

        if (date != null) {
            Intent intent = new Intent(context, RepActivity.class);
            intent.putExtra("schID", schID);
            intent.putExtra("gradeID", gradeID);
            intent.putExtra("date", date);

            List<RepMdlin> listTemp = DAO2.getReport1School1Date(schID, date);
            if (H.isReportOfThisSchoolSynced(listTemp))
                intent.putExtra(Global.openMode, Global.DisplayMode);
            else intent.putExtra(Global.openMode, Global.EditMode);

            intent.putExtra(Global.openedFromWhichActivity, SchoolProfileActivity.class.getName());
            intent.putExtra(Global.dateBtn, false);
            context.startActivity(intent);
        } else {
            snackbar1 = Snackbar.make(perfAdminView, "Not Available", Snackbar.LENGTH_SHORT).setAction("dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar1.dismiss();
                }
            });
            snackbar1.show();
        }
    }


    @OnClick(R.id.perfEvalGraph)
    public void onClickperfEvaluationCircle(View view) {
        String date = DAO2.lastDateForASchool(schID, DB.report_acad.table,
                DB.report_acad.schID, DB.report_acad.date_report);
        if (date != null) {
            List<EvlModelin> lastEvl = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);

            String attendance = null;
            if (lastEvl.size() > 0)
                attendance = lastEvl.get(0).getAttendence();

            Intent intent1 = new Intent(context, EvlActivity.class);
            intent1.putExtra("schID", schID);
            intent1.putExtra("gradeID", gradeID);
            intent1.putExtra("date", date);
            intent1.putExtra("attendance", attendance);

            List<EvlModelin> listTemp = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
            if (H.isEvaluationOfThisSchoolSynced(listTemp))
                intent1.putExtra(Global.openMode, Global.DisplayMode);
            else intent1.putExtra(Global.openMode, Global.EditMode);

            intent1.putExtra(Global.openedFromWhichActivity, SchoolProfileActivity.class.getName());
            startActivity(intent1);

        } else {
            snackbar2 = Snackbar.make(perfAdminView, "Not Available", Snackbar.LENGTH_SHORT).setAction("dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar2.dismiss();
                }
            });
            snackbar2.show();
        }
    }

    /*=============================== Evaluation ======================================================================================*/
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
            intent.putExtra(Global.openedFromWhichActivity, SchoolProfileActivity.class.getName());
            intent.putExtra("schID", schID);
            intent.putExtra("gradeID", gradeID);
            intent.putExtra("date", date);
            intent.putExtra("attendance", attendance);
            startActivity(intent);
        }
    }
}