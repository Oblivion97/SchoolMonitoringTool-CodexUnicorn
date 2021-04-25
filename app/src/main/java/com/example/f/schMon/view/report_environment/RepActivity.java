/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.report_environment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.common.DatePickerFragment;
import com.example.f.schMon.view.common.InfoDialog;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.school.SchoolProfileActivity;
import com.example.f.schMon.view.sync_mir.SyncActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by ASUS on 10/10/2017.
 */

public class RepActivity extends AppCompatActivity implements RepDialog.RepSubmitInterface, DatePickerFragment.DateDialogListner, DatePickerFragment2.DateDialogListner2 {
    private final Context context = RepActivity.this;
    private final String TAG = RepActivity.class.getName();
    private SQLiteDatabase db;
    private int currentPostion;
    private Intent intent;
    private String openMode, schID, msg, date;
    private boolean dateInputted, dbInsertSuccess1, dbInsertSuccess2, dbInsertSuccess3, dateBtnShow;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    private String gradeID;
    boolean sync;
    private List<RepMdlin> list = new ArrayList<>();
    private List<Integer> unanswerQues = new ArrayList<>();
    private RepAdapterRecycler adapterRecycler = null;
    @BindView(R.id.repListRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.repSubmitBtn)
    Button submitBtn;
    @BindView(R.id.repDateBtn)
    Button dateBtn;
    Toolbar toolbar;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.simpleListView)
    ListView listView;
    @BindView(R.id.noDataContainer)
    View noDataContainer;
    @BindView(R.id.noDataTV)
    TextView noDataTV;

    /*___________________Start option menu______________________________________*/
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
            menu.findItem(R.id.info_menu).setVisible(true);
            menu.findItem(R.id.home_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.date_menu:
                DialogFragment dialog = new DatePickerFragment();
                dialog.show(getSupportFragmentManager(), "CalenderDialog");
                return true;
            case R.id.info_menu:
                Bundle bundle = new Bundle();
                bundle.putString(Global.openWhat, Global.infoRep);
                if (list.size() > 0) {
                    if (list.get(0) != null) {
                        sync = H.isReportOfThisSchoolSynced(list);
                        bundle.putParcelable("data", H.makeInfoMdlForInfoDialogFromEvlModelOrRepMdl(list.get(0), sync, schID));
                    }
                }
                DialogFragment dialog2 = new InfoDialog();
                dialog2.setArguments(bundle);
                dialog2.setCancelable(false);
                dialog2.show(((AppCompatActivity) context).getSupportFragmentManager(), "InfoDialog");
                return true;
            case R.id.sync_menu_details:

                return true;
            case R.id.settings_menu:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*___________________End option menu______________________________________*/


    /*  nav drawer close on back button click */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intentSchProf = new Intent(context, SchoolProfileActivity.class);
            intentSchProf.putExtra("schID", schID);
            intentSchProf.putExtra("gradeID", gradeID);
            intentSchProf.putExtra(Global.openMode, Global.NewMode);
            startActivity(intentSchProf);
        }
    }


    /*==========================================================================================================================*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Global.openMode, openMode);
        //  outState.putInt(Global.scrollPosition, listView.getSelectedItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (openMode.equals(Global.RunMode)) {
            //   currentPostion = savedInstanceState.getInt(Global.scrollPosition);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //========= set data =============================================================

        intent = getIntent();
        schID = intent.getStringExtra("schID");
        gradeID = intent.getStringExtra("gradeID");
        openMode = intent.getStringExtra(Global.openMode);
        dateBtnShow = intent.getBooleanExtra(Global.dateBtn, true);
        if (openMode.equals(Global.RunMode))
            currentPostion = intent.getIntExtra(Global.scrollPosition, 0);

        if (!dateBtnShow) {
            dateBtn.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            submitBtn.setLayoutParams(param);
        }

        if (openMode.equalsIgnoreCase(Global.DisplayMode))
            submitBtn.setVisibility(View.GONE);

        loadData();

        // no question msg
        if (DAO.isTableEmpty(db, DB.admin_ques.table)) {
            noDataContainer.setVisibility(View.VISIBLE);
            noDataTV.setText("No Questions Available.Download Questions...");
            noDataTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(context, SyncActivity.class);
                    intent1.putExtra(Global.btnFocus, "ques");
                    intent1.putExtra(Global.syncID, "2");
                    startActivity(intent1);
                }
            });
        } else {
            noDataContainer.setVisibility(View.GONE);
        }
    }

    //=====================================================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_with_frag);
        ButterKnife.bind(this);

        //appbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Administrative Report");

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        //db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);
    }

    private void loadData() {
        new LoadData().execute(openMode);
    }


    //====================== submit ==================================================

    @OnClick(R.id.repSubmitBtn)
    public void onClickSubmitAll(View v) {
        if (dateInputted) {
            dbUpdateOnSubmit();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Pick a Date...then Submit")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }
    }

    private String dbUpdateOnSubmit() {

        //=========== checking all ques answered or not ===========
        if (unansweredQues() != null) {           //unanswered ques list
            new AlertDialog.Builder(context)
                    .setMessage("Answer Question No - \n" + unansweredQues() + "\nthen Submit")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create().show();
        } else {

            //============== date set to all model ===================
            if (!openMode.equals(Global.EditMode))
                setDateToallRep();

            //============== DB insert ===================
            /**  1. db insert insert checklist answers to db */
            dbInsertSuccess1 = DAO.insertReportAdmin(list);


            /** 2  db insert last performance for dash */
            String dateT = list.get(0).getDateReport();
            String performace = H.calcRepPerformForASch(list);//calcReportPerformForASch(list);
            School s = DAO.getInfoOfASchool(schID);
            LastPerfMdl lpm = new LastPerfMdl(schID, s.getSchName(), performace, dateT, null, null, null, null);
            lpm.setCode(s.getSchCode());
            lpm.setEduType(s.getEdu_type());
            lpm.setGrade(s.getGrade());

            dbInsertSuccess2 = DAO.insertLastPerformAll(schID, lpm);
            Log.d(TAG, schID);

            if (dbInsertSuccess1 && dbInsertSuccess2) {
                msg = H.print(list);
            } else msg = "Inserting Report In DB Failed";


            /** 3 school visited time */
            DAO.insertLastVisitTimeSchool(schID, new Date(System.currentTimeMillis()));

           /* new AlertDialog.Builder(context).setMessage(msg)
                    .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((RepActivity) context).finish();
                        }
                    }).create().show();*/


            Intent intentSchProf = new Intent(context, SchoolProfileActivity.class);
            intentSchProf.putExtra("schID", schID);
            intentSchProf.putExtra("gradeID", gradeID);
            intentSchProf.putExtra(Global.openMode, Global.NewMode);
            startActivity(intentSchProf);

            ((RepActivity) context).finish();
        }
        return msg;
    }

    private void setDateToallRep() {
        /**=========== setting date to all RepModel after filling up all answers =========== */
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAnswer() != null) {
                if (list.get(i).getAnswer().equals("1") || list.get(i).getAnswer().equals("0")) {
                    list.get(i).setDateReport(date);
                    list.get(i).setTimeLm(date);
                }
            }
        }
    }

    private String unansweredQues() {
        String x = null;
        RepMdlin r;
        int n = list.size();
        for (int i = 0; i < n; i++) {
            r = list.get(i);
            if (r.getAnswer() == null || (!r.getAnswer().equals("0") && !r.getAnswer().equals("1"))) {
                if (!unanswerQues.contains(i + 1))//multiple entry block
                    unanswerQues.add(i + 1);
            }
        }

        StringBuffer t = new StringBuffer();
        for (int i = 0; i < unanswerQues.size(); i++) {
            t.append(String.valueOf(unanswerQues.get(i)));
            if (i < unanswerQues.size() - 1)
                t.append(", ");
        }

        if (unanswerQues.size() > 0)
            x = t.toString();
        else x = null;
        unanswerQues.clear();
        return x;
    }


    //=================== dialog frag interface implementation ==============================
    void changeItemOnAnswer(int index, RepMdlin chagedData) {
        list.set(index, chagedData);
        adapterRecycler.notifyItemChanged(index);
    }

    @Override
    public void onClickSubmitSingleRepFromDialogFrag(String schID, int index, RepMdlin data) {
        data = H.formatReportDataToShowInAns(context, data);
        changeItemOnAnswer(index, data);
        // answerShowOnRunTimeUsingGlobalVariable(data);
    }


    ListView getListView() {
        return this.listView;
    }


    //====================== date ==================================================

    @OnClick(R.id.repDateBtn)
    public void onClickDate(View view) {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "CalenderDialog");
    }

    //date of report(of all checklists)
    @Override
    public void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar) {
        this.dateInputted = dateInputted;//date is inputted from date picker fragment now submit btn can work
        date = df1.format(calendar.getTime());
    }

    //target date of individual problem in report dialog fragment
    @Override
    public void onDateClick2(DialogFragment dialog, boolean dateInputted, Calendar calendar) {

    }


    //====================== class ==================================================

    class LoadData extends AsyncTask<String, Void, List<RepMdlin>> {
        @Override
        protected List<RepMdlin> doInBackground(String... strings) {
            openMode = strings[0];

                 /*_____________ edit mode _______________________*/
            if (openMode.equals(Global.EditMode)) {

                date = intent.getStringExtra("date");
                if (date != null || !date.equals(""))
                    dateInputted = true;

                String lastDate;
                  /*  lastDate = DAO2.lastDateForASchool(schID, DB.report_admin.table,
                            DB.report_admin.sch_id, DB.report_admin.date_report);*/

                list = DAO2.getReport1School1Date(schID, date);
                list = H.formatReportDataToShowInAnsList(context, list);
                //  listMakeForEdit(schID, date);
            }
                /*_____________ new report mode _______________________*/
            else if (openMode.equals(Global.NewMode)) {
                //get questionTV
                list = DAO.getActiveQuesRep();
            }

                /*_____________ display mode _______________________*/
            else if (openMode.equals(Global.DisplayMode)) {
                date = intent.getStringExtra("date");
                if (date != null || !date.equals(""))
                    dateInputted = true;
                list = DAO2.getReport1School1Date(schID, date);
                list = H.formatReportDataToShowInAnsList(context, list);
            }
                 /*_____________ run mode _______________________*/
           /* else if (openMode.equals(Global.RunMode)) {
                list.addAll(A.getRepList());
                list = H.formatReportDataToShowInAnsList(context, list);
            }*/

            return list;
        }

        @Override
        protected void onPostExecute(List<RepMdlin> list) {
            H.setQuesNoForDialogFrag(list);

            //listview
          /*  adapter = new RepAdapter(context, R.layout.reporting_row, list, schID, gradeID, openMode, date);
            listView.setAdapter(adapter);*/

            // RecyclerView
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            adapterRecycler = new RepAdapterRecycler(context, list, schID, gradeID, openMode, date);
            recyclerView.setAdapter(adapterRecycler);

            //divider
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getOrientation());
            recyclerView.addItemDecoration(mDividerItemDecoration);

            pb.setVisibility(View.GONE);

            //H.getAlertDialog(context, String.valueOf(currentPostion));
            // H.getAlertDialog(context, openWhat + "\n" + list.toString());
            //  Log.d(TAG, openWhat + "\n" + list.toString());

        }
    }


    //============================== Unused ===========================================================================================


    List<RepMdlin> allQues = new ArrayList<>();//all questions from question list(no answer)
    List<RepMdlin> ansQuesList = new ArrayList<>();//answered questions in previous(this) evaluation
    List<RepMdlin> unAnsQuesList = new ArrayList<>();//unanswered questions in previous(this) evaluation

    void listMakeForEdit(String schID, String date) {
        allQues = DAO.getActiveQuesRep();
        ansQuesList = DAO2.getReport1School1Date(schID, date);
        unAnsQuesList = H.questionNotAnsweredPreviouslyRep(allQues, ansQuesList);

        list.clear();
        list.addAll(ansQuesList);
        list.addAll(unAnsQuesList);
    }


    void answerShowOnRunTimeUsingGlobalVariable(RepMdlin data) {
        saveTemporarilyInGlobalList(list, data);

        A.setListPosition(data.getListPosition());

        Intent intent = new Intent(this, RepActivity.class);
        intent.putExtra("schID", schID);
        intent.putExtra("gradeID", gradeID);
        if (openMode.equals(Global.EditMode))
            intent.putExtra(Global.dateBtn, false);
        else intent.putExtra(Global.dateBtn, true);
        intent.putExtra(Global.openMode, Global.RunMode);
        intent.putExtra(Global.scrollPosition, listView.getFirstVisiblePosition());
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    void saveTemporarilyInGlobalList(List<RepMdlin> list, RepMdlin data) {
        A.getRepList().clear();
        List<RepMdlin> temp = new ArrayList<>();
        temp.addAll(list);

        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getServerIDQues().equals(data.getServerIDQues()) &&
                    temp.get(i).getQuestion().equals(data.getQuestion())) {
                temp.remove(i);
                temp.add(i, data);
            }
        }

        A.setRepList(temp);
    }

    void answerShowOnRunTimeUsingDBreQuery(RepMdlin data) {

        if (dateInputted) {

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getServerIDQues().equals(data.getServerIDQues()) &&
                        list.get(i).getQuestion().equals(data.getQuestion())) {
                    list.remove(i);
                    list.add(i, data);
                }
            }


            setDateToallRep();

            /**  1. db insert insert checklist answers to db */
            dbInsertSuccess1 = DAO.insertReportAdmin(list);


            /** 2  db insert last performance for dash */
            String dateT = list.get(0).getDateReport();
            String performace = H.calcRepPerformForASch(list);//calcReportPerformForASch(list);
            String schName = DAO.getSchoolNameFromSchoolID(schID);
            LastPerfMdl lpm = new LastPerfMdl(schID, schName, performace, dateT, null, null, null, null);
            dbInsertSuccess2 = DAO.insertLastPerformAll(schID, lpm);


            if (dbInsertSuccess1 && dbInsertSuccess2) {
                msg = H.print(list);
            } else msg = "Inserting Report In DB Failed";


            Intent intentRep = new Intent(this, com.example.f.schMon.view.report_environment.RepActivity.class);
            intentRep.putExtra("schID", schID);
            intentRep.putExtra("date", date);
            intentRep.putExtra(Global.openMode, Global.EditMode);
            //intentRep.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intentRep);
            finish();


        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Pick a Date...then Submit")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();


        }

    }

}
