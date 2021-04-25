/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.School;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnsActivity extends AppCompatActivity {
    private final String TAG = AnsActivity.class.getName();
    private final Context context = AnsActivity.this;
    SQLiteDatabase db;
    private String quesID, ques, answer, active, openMode, asked, correct,
            serverQues, currentDate, schID, schName, date, dateUpdate,
            attendance, evalPerf, synced, time_lm, classs, gradeID;
    private EvlModelin data;
    private Intent intent;
    private School school;
    private int index;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    Snackbar snackbar;

    @BindView(R.id.Question)
    TextView Question;
    @BindView(R.id.Answer)
    TextView Answer;
    @BindView(R.id.evalutionInfo)
    TextView evalutionInfo;
    @BindView(R.id.QuestionText)
    TextView QuestionTV;
    @BindView(R.id.answerEval)
    TextView answerTV;
    @BindView(R.id.attendanceEval)
    EditText attndnceET;
    @BindView(R.id.askedEval)
    EditText askedET;
    @BindView(R.id.answeredCorrectEval)
    EditText ansCorrectET;
    @BindView(R.id.submitReportEvlAns)
    Button submitBtn;
    @BindView(R.id.attenInfo)
    TextView attenInfo;
    @BindView(R.id.askInfo)
    TextView askInfo;
    @BindView(R.id.correctInfo)
    TextView ansInfo;
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

//___________________End option menu______________________________________

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, EvlActivity.class);
        intent.putExtra(Global.openMode, Global.RunMode);
        intent.putExtra("schID", schID);
        intent.putExtra("date", date);
        intent.putExtra("onClickAttndnc", attendance);
        startActivity(intent);
        overridePendingTransition(R.anim.lefttoright, 0);
        finish();
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        //appbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//toolbar back btn
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(context, EvlActivity.class);
                intent.putExtra(Global.openMode, Global.RunMode);
                intent.putExtra("schID", schID);
                intent.putExtra("gradeID", gradeID);
                intent.putExtra("date", date);
                intent.putExtra("attendance", attendance);
                startActivity(intent);
                overridePendingTransition(R.anim.lefttoright, 0);*/
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_del);
        }

        //_____  nav drawer
        /*NavDrawer.navDrawerBtnsClick(context, toolbar);*/

        //db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);


        H.setFont(context, new View[]{Question, Answer, evalutionInfo,
                QuestionTV, answerTV, attndnceET, ansCorrectET, askedET, submitBtn,
                attenInfo, askInfo, ansInfo});

        //======================================= set data ============================================================
        intent = getIntent();
        openMode = intent.getStringExtra(Global.openMode);
        schID = intent.getStringExtra("schID");//schoolSync id
        gradeID = intent.getStringExtra("gradeID");
        date = intent.getStringExtra("date");
        attendance = intent.getStringExtra("attendance");
        index = intent.getIntExtra("index", -1);
        data = intent.getParcelableExtra("data");
        data = A.getEvlModelin();
        Log.d(TAG, "input-----" + data.toString());

        QuestionTV.setText(data.getQuesNo() + ". " + data.getQues());
        if (data.getAnswer() == null)
            answerTV.setText("No Data from Server");
        answerTV.setText(data.getAnswer());
        attndnceET.setText(attendance);
        attndnceET.setFocusable(false);

        askedET.setText(data.getAsked());
        askedET.requestFocus();
        ansCorrectET.setText(data.getCorrect());

    }


    @OnClick(R.id.submitReportEvlAns)
    public void onClickSubmit(View view) {
        if (isAllDataInserted()) {
            String check = checkDataInputConsistancy();
            if (check.equals(consistant)) {
                saveInDB();
                returnResult_to_StartingActivity();
                finish();

                //saveTemporarilyInGlobalList(data);
            } else {
                snackbar = Snackbar.make(submitBtn, check, Snackbar.LENGTH_LONG);
                snackbar.setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        } else {
            snackbar = Snackbar.make(submitBtn, "Fillup All Fields", Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }

    }

    String askGreater = "No of Asked is Greater than Attendance",
            consistant = "consistant",
            corrGreater = "No of Correctly Answered is Greater than Asked";

    private String checkDataInputConsistancy() {
        String s = null;
        int att, corr, ask;

        if (!attndnceET.getText().toString().equals("") && !ansCorrectET.getText().toString().equals("") && !askedET.getText().toString().equals("")) {
            att = Integer.parseInt(attndnceET.getText().toString());
            corr = Integer.parseInt(ansCorrectET.getText().toString());
            ask = Integer.parseInt(askedET.getText().toString());
            if (ask > att) {
                s = askGreater;
            }
            if (corr > ask) {
                s = corrGreater;
            }
            if (att >= ask && ask >= corr)
                s = consistant;
        }
        return s;
    }

    void makeAnsModelInputigData() {
        currentDate = df1.format(new Date(System.currentTimeMillis()));
        school = DAO.getInfoOfASchool(schID);

        data.setSchID(schID);
        data.setGradeID(gradeID);
        data.setSchName(school.getSchName());
        data.setSynced("0");
        data.setTime_lm(currentDate);
        data.setDate_report(currentDate);
        data.setDate_update(currentDate);
        data.setTotalStd(school.getTotalStd());
        data.setAttendence(attndnceET.getText().toString());
        data.setAttempt(askedET.getText().toString());// attempt is not necessary in calc so set same as asked
        data.setAsked(askedET.getText().toString());
        data.setCorrect(ansCorrectET.getText().toString());
        data.setPerf(H.evaluationPerfromCalc(askedET.getText().toString(), ansCorrectET.getText().toString()));
        Log.d(TAG, "Perf Single: " + data.getPerf());
        data.setLocalIDAns(H.generateUniqueID("acad-ans"));
    }

    void saveInDB() {

        makeAnsModelInputigData();


        /**     1 answer wise details save*/
        List<EvlModelin> evalModelinList = new ArrayList<>();
        evalModelinList.add(data);
        DAO.insertEvlAnsToDB(evalModelinList);


        /**     2 last performance for dashboard */
        School s = DAO.getInfoOfASchool(schID);
        String perfAvg = H.calcEvlPerfOfASchADate(schID, date);
        Log.d(TAG, perfAvg);
        LastPerfMdl lpm = new LastPerfMdl(schID, s.getSchName(), null, null, perfAvg,
                this.date, null, null
        );
        lpm.setCode(s.getSchCode());
        lpm.setEduType(s.getEdu_type());
        lpm.setGrade(s.getGrade());
        lpm.setTime_lm(currentDate);
        DAO.insertLastPerformAll(schID, lpm);

        /**     3 school visited time */
        DAO.insertLastVisitTimeSchool(schID, new Date(System.currentTimeMillis()));
    }


    private boolean isAllDataInserted() {
        if (askedET.getText().toString().equals("") || (ansCorrectET.getText().toString().equals(""))) {
            return false;
        } else return true;
    }


    private void saveTemporarilyInGlobalList(EvlModelin data) {
        List<EvlModelin> list = A.getEvlList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalQuestion(data)) {
                list.remove(i);
                list.add(i, data);
            }
        }
    }

    private void returnResult_to_StartingActivity() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Global.openMode, Global.RunMode);
        returnIntent.putExtra("schID", this.schID);
        returnIntent.putExtra("gradeID", this.gradeID);
        returnIntent.putExtra("date", this.date);
        returnIntent.putExtra("attendance", this.attendance);
        returnIntent.putExtra("index", this.index);
        returnIntent.putExtra("data", this.data);
        Log.d(TAG, "output-----" + data.toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
