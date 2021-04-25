/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.reporting;

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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.view.common.DatePickerFragment;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by ASUS on 10/10/2017.
 */

public class ReportingActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListner {
    private final Context context = ReportingActivity.this;
    private final String TAG = ReportingActivity.class.getName();
    private SQLiteDatabase db;

    List<Integer> indexOfUnanswerQues = new ArrayList<>();
    ReportingAdapter dataAdapter = null;
    private SQLiteDatabase newDB;
    public List<ReportingData> dataList = new ArrayList<>();
    ;
    @BindView(R.id.repSubmitBtn)
    Button submitBtn;
    @BindView(R.id.repDateBtn)
    Button dateBtn;
    Toolbar toolbar;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    Intent intent;
    String openMode;
    List<RepMdlin> list = new ArrayList<>();
    ;
    private String schID, msg;
    private boolean dateInputted, dbInsertSuccess, dbInsertSuccess2, dbInsertSuccess3;
    ReportingAdapter adapter;
    SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

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
            menu.findItem(R.id.home_menu).setVisible(false);
            menu.findItem(R.id.date_menu).setVisible(false);
            menu.findItem(R.id.settings_menu).setVisible(false);
            menu.findItem(R.id.sync_menu_details).setVisible(false);
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
            case R.id.sync_menu:
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


    /* __________ nav drawer close on back button click ____________________*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @BindView(R.id.simpleListView)
    ListView listView;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporting_admin);
        ButterKnife.bind(this);

        //appbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Administrative Report");

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        db = Global.getDB_with_nativeWay_writable(Global.dbName);


        intent = getIntent();
        schID = intent.getStringExtra("schID");
        openMode = intent.getStringExtra(Global.openMode);


        loadData();

       /* getQuestions();
        displayListView();*/
    }


    private void loadData() {
        new AsyncTask<String, Void, List<RepMdlin>>() {
            @Override
            protected List<RepMdlin> doInBackground(String... strings) {
                openMode = strings[0];
                //edit mode
                if (openMode.equals(Global.EditMode)) {
                    //repModelins = DAO.getLastRepModelinListBySchool(schID);
                    String lastDate = DAO2.lastDateForASchool(schID, DB.report_admin.table,
                            DB.report_admin.sch_id, DB.report_admin.date_report);
                    list = DAO2.getReport1School1Date(schID, lastDate);
                    int n = list.size();


                    date = intent.getStringExtra("date");
                    if (date != null || !date.equals(""))
                        dateInputted = true;

                    //  l = DAO.getActiveQuesRep();


                }
                // new report moce
                else if (openMode.equals(Global.NewMode)) {
                    //get question
                    list = DAO.getActiveQuesRep();


                }


                return list;
            }

            @Override
            protected void onPostExecute(List<RepMdlin> list) {
                adapter = new ReportingAdapter(context, R.layout.reporting_row, list, schID, openMode, date);
                listView.setAdapter(adapter);
                pb.setVisibility(View.GONE);

                // H.getAlertDialog(context, list.toString());

            }
        }.execute(new String[]{openMode});
    }


    @OnClick(R.id.repSubmitBtn)
    public void onClickSubmit(View v) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String msg;
                if (dateInputted) {
                    dbUpdateOnSubmit();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please Input Date...Then Click Submit")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create().show();
                }
                return null;
            }
        }.execute();


//----------------------------------------------------------------------------------------------------------------------
       /* Toast.makeText(getApplicationContext(), "submitBtn pressed " + dataList.toString(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < dataList.size(); i++) {
            Log.d("ListVal", "details " + dataList.get(i).getRepDetails() + " val " + dataList.get(i).getRepDetailsVal());
        }*/
    }

    private void dbUpdateOnSubmit() {

        /**=========== setting date to all RepModel after filling up all answers =========== */
        int n = list.size();
        for (int i = 0; i < n; i++) {
            if (list.get(i).getAnswer() != null) {
                list.get(i).setDateReport(date);
            } else {
                if (!indexOfUnanswerQues.contains(i))//multiple entry block
                    indexOfUnanswerQues.add(i);
            }
        }


        //=========== checking all ques answered or not ===========
        if (indexOfUnanswerQues.size() > 0) {
            //unanswered ques list
            StringBuffer t = new StringBuffer();
            for (int i = 0; i < indexOfUnanswerQues.size(); i++) {
                t.append(String.valueOf(indexOfUnanswerQues.get(i)));
                if (i < indexOfUnanswerQues.size()) {
                    t.append(", ");
                }
            }
            new AlertDialog.Builder(context)
                    .setMessage("Please Answer Question No - \n" + t.toString() + "\nThen Submit Again")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    }).create().show();
        } else {


            /**     1. db insert
             * *insert checklist answers to db */

            dbInsertSuccess = DAO.insertReportAdmin(list);


            /**     2  db insert
             * last performance for dash */
            String dateT = list.get(0).getDateReport();
            String performace = H.calcRepPerformForASch(list);//calcReportPerformForASch(list);
            String schName = DAO.getSchoolNameFromSchoolID(schID);
            LastPerfMdl lpm = new LastPerfMdl(schID, schName, performace, dateT, null, null, null, null);
            dbInsertSuccess2 = DAO.insertLastPerformAll(schID, lpm);


            if (dbInsertSuccess && dbInsertSuccess2) {
                String msg = list.toString();
            } else msg = "Inserting Report In DB Failed";
            new AlertDialog.Builder(context).setMessage(msg)
                    .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((ReportingActivity) context).finish();
                        }
                    }).create().show();
        }


    }


    @OnClick(R.id.repDateBtn)
    public void onClickDate(View view) {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "CalenderDialog");
    }


    @Override
    public void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar) {
        this.dateInputted = dateInputted;//date is inputted from date picker fragment now submit btn can work
        date = df1.format(calendar.getTime());
    }
/*

    private void displayListView() {
        //Array l of NotificationData
        ArrayList<ReportingData> reportingDataList = new ArrayList<>();
        for (String q : questionList) {
            ReportingData data = new ReportingData(q, "", "", "", false, false, false, false, false);
            reportingDataList.add(data);
        }


        //create an ArrayAdaptar from the String Array
        dataAdapter = new RepAdapter2(this,
                R.layout.reporting_row, reportingDataList);


        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);
        pb.setVisibility(View.GONE);

      */
/*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                NotificationData data = (NotificationData) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + data.getMonthName(),
                        Toast.LENGTH_LONG).show();
            }
        });*//*



    }














    private void getQuestions() {
        questionList = new ArrayList<>();
        String question;
        try {
            // DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            newDB = Global.getDB_with_nativeWay_writable(Global.dbName);
            Cursor c = newDB.rawQuery("SELECT question from admin_ques", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        question = c.getString(c.getColumnIndex("question"));
                        // Log.e(getClass().getSimpleName(), title+" : "+question)
                        questionList.add(question);
                    } while (c.moveToNext());//
                    c.close();
                }
            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            //newDB.close();
        }
    }

*/

}
