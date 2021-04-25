/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.report;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.LinearLayout;
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
import com.example.f.schMon.model.appInner.RepModel;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class RepAdminActivity extends AppCompatActivity
        implements DatePickerFragment.DateDialogListner,
        RepAdminDialog.ReportAdminDialogFragmentListner_Done,
        RepAdminDialog.ReportAdminFragmentLister_Cancel, RepAdminAdapter.YesBtnListner {

    private static final String TAG = RepAdminActivity.class.getName();
    Context context = RepAdminActivity.this;
    RepModel repModel;
    RepMdlin repMdlin;
    List<RepMdlin> repMdlins = new ArrayList<>();
    List<RepModel> quesList = new ArrayList<>();


    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    boolean dbInsertSuccess, dbInsertSuccess2, dbInsertSuccess3;
    List<Integer> indexOfUnanswerQues = new ArrayList<>();
    private boolean dateInputted = false;
    RepAdminAdapter adapter;


    private String date;
    private String schID;
    private SQLiteDatabase db;
    @BindView(R.id.simpleListView)
    ListView listView;
    @BindView(R.id.dateRepBtn)
    Button dateBtn;
    @BindView(R.id.repSubmitBtn)
    Button submitBtn;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;

    Intent intent;
    String openMode;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_admin);
        ButterKnife.bind(this);
        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        db = Global.getDB_with_nativeWay_writable(Global.dbName);


        intent = getIntent();
        schID = intent.getStringExtra("schID");
        openMode = intent.getStringExtra(Global.openMode);


//_____________get data ____________________
        // repModels = DataPump.getReportModelList();

        // new SetData().execute(intent);
        loadAndsetData();
        pb.setVisibility(View.GONE);

        //for edit hide date picker
        if (dateInputted) {
            dateBtn.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dateBtn.getLayoutParams();
            params = new LinearLayout.LayoutParams(0, 100);
            params.weight = 1;
            dateBtn.setLayoutParams(params);
        }

    }


    public void loadAndsetData() {
        //edit mode
        if (openMode.equals(Global.EditMode)) {
            //repMdlins = DAO.getLastRepModelinListBySchool(schID);
            String lastDate = DAO2.lastDateForASchool(schID, DB.report_admin.table,
                    DB.report_admin.sch_id, DB.report_admin.date_report);
            repMdlins = DAO2.getReport1School1Date(schID, lastDate);
            int n = repMdlins.size();


            date = intent.getStringExtra("date");
            if (date != null || !date.equals(""))
                dateInputted = true;

            //  list = DAO.getActiveQuesRep();
            adapter = new RepAdminAdapter(context, RepAdminActivity.this, repMdlins, openMode, schID);

            listView.setAdapter(adapter);

            new AlertDialog.Builder(context).setMessage(repMdlins.toString()).create().show();

        }
        // new report moce
        else if (openMode.equals(Global.NewMode)) {
            //get question
            repMdlins = DAO.getActiveQuesRep();

            adapter = new RepAdminAdapter(context, RepAdminActivity.this, repMdlins, openMode, schID);
            listView.setAdapter(adapter);
        }


    }




    //======================================================================================================================


    public void onClickSubmit(View view) {
        String msg;
        if (dateInputted) {//checking date inputted or not
            /*setting date to all RepModel after filling up all answers*/
            int n = repMdlins.size();
            for (int i = 0; i < n; i++) {
                if (repMdlins.get(i).getAnswer() != null) {
                    repMdlins.get(i).setDateReport(date);
                } else {
                    if (!indexOfUnanswerQues.contains(i))//multiple entry block
                        indexOfUnanswerQues.add(i);
                }
            }

            if (indexOfUnanswerQues.size() > 0) {//checking all ques answered or not
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


                SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);

                /**     1. db insert
                 * *insert checklist answers to db */

                dbInsertSuccess = DAO.insertReportAdmin(repMdlins);


                /**     2  db insert
                 * last performance for dash */
                String dateT = repMdlins.get(0).getDateReport();
                String adminPerf = H.calcRepPerformForASch(repMdlins);//calcAdminPerformForASch(repMdlins);
                String schName = DAO.getSchoolNameFromSchoolID( schID);
                LastPerfMdl lpm = new LastPerfMdl(schID, schName, adminPerf, dateT, null, null, null, null);
                dbInsertSuccess2 = DAO.insertLastPerformAll( schID, lpm);


                /**     3  db insert
                 * admin_perf Table one schoolSync perf for a date *//*
                RepPerf repPerf = new RepPerf();
                dbInsertSuccess3 = DAO.insertRepPerf1Sch1Date(repPerf, schID, this.date);*/


                if (dbInsertSuccess && dbInsertSuccess2)

                    //============= clear rep data ==================
                    // this.repModels=null;


                    msg = repMdlins.toString();
                else msg = "Inserting Report In DB Failed";
                new AlertDialog.Builder(context).setMessage(msg)
                        .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RepAdminActivity.this.finish();
                            }
                        }).create().show();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Please Input Date...Then Click Submit")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }


    }

    /**
     * _____________________ CheckList Admin Perform Calculation (Avg of all ques)  _________________________________
     */
    public static String calcAdminPerformForASch(List<RepMdlin> repModelList) {
         /*calculation of all checklist avg perform*/

        int sum = 0, avg = 0;
        try {
            int size = repModelList.size();
            for (int i = 0; i < size; i++) {
                String ansWeight = repModelList.get(i).getAnswerWeight();
                if (ansWeight != null)
                    sum += Integer.parseInt(ansWeight);
            }
            avg = sum / size;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(avg);
    }


    //________________callback from date picker fragment______________________________________

    public void onClickDate(View view) {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "CalenderDialog");
    }

    @Override
    public void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar) {
        this.dateInputted = dateInputted;//date is inputted from date picker fragment now submit btn can work
        date = dateFormat.format(calendar.getTime());
    }


    //================= yes no ===================
    @Override
    public void onDone_Click_in_Dialog(DialogFragment dialog, RepMdlin repModelTemp, int index) {
        repMdlins.remove(index);
        repMdlins.add(index, repModelTemp);


        for (int i = 0; i < repMdlins.size(); i++) {
            if (repMdlins.get(i).getQuestion().equals(repModelTemp.getQuestion())) {
                repMdlins.set(i, repModelTemp);
            }
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClickYesBtn_in_Adapter(RepMdlin repModelTemp, int index) {
        repMdlins.remove(index);
        repMdlins.add(index, repModelTemp);

        for (int i = 0; i < repMdlins.size(); i++) {
            if (repMdlins.get(i).getQuestion().equals(repModelTemp.getQuestion())) {
                repMdlins.set(i, repModelTemp);
            }
        }

        adapter.notifyDataSetChanged();
        // new AlertDialog.Builder(context).setMessage(repModelTemp.toString()).create().show();
    }

    //=========== not used ================
    @Override
    public void onCancel_Click_in_Dialog(DialogFragment dialog, int index) {

    }

}
