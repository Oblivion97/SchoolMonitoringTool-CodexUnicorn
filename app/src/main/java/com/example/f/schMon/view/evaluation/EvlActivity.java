/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.controller.SharedPrefHelper;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.view.common.DatePickerFragment;
import com.example.f.schMon.view.common.InfoDialog;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.school.SchoolProfileActivity;
import com.example.f.schMon.view.sync_mir.SyncActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class EvlActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListner {
    private final String TAG = EvlActivity.class.getName();
    private Context context = EvlActivity.this;
    private List<EvlModelin> list = new ArrayList<>();
    private List<EvlModelin> listFiltered = new ArrayList<>();
    private List<EvlModelin> allQues = new ArrayList<>();//all questions from question list(no answer)
    private List<EvlModelin> ansQuesList = new ArrayList<>();//answered questions in previous(this) evaluation
    private List<EvlModelin> unAnsQuesList = new ArrayList<>();//unanswered questions in previous(this) evaluation
    private EvlModelin data;
    private String subjectDefault, chapterDefault;
    private EvlAdapterRecycler adapterRecycler;
    private SQLiteDatabase db;
    //private EvlAdapter adapter;
    private String schID, gradeID, date, attendance;
    private String openMode, openedFromWhichActivity;
    private int index;
    private boolean dateInputted = false;
    private boolean sync, resultFromActivity;
    private Intent intent;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat df2 = new SimpleDateFormat("d MMM yyyy");
    List<String> chapterList = new ArrayList<String>();
    List<String> subjectList = new ArrayList<String>();
    ArrayAdapter<String> dataAdapterChapter;
    ArrayAdapter<String> dataAdapterSubject;
    @BindView(R.id.evlRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.evlListV)
    ListView listview;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.fabEval)
    FloatingActionButton fab;
    @BindView(R.id.evlSubmitBtn)
    Button evlSubmitBtn;
    @BindView(R.id.noDataContainer)
    View noDataContainer;
    @BindView(R.id.noDataTV)
    TextView noDataTV;
    @BindView(R.id.spinnerSubject)
    Spinner spinnerSubject;
    @BindView(R.id.spinnerChapter)
    Spinner spinnerChapter;
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
            case R.id.submit_menu:
                submitAll();
                return true;
            case R.id.date_menu:
                DialogFragment dialog = new DatePickerFragment();
                dialog.show(EvlActivity.this.getSupportFragmentManager(), "CalenderDialog");
                return true;
            case R.id.info_menu:
                Bundle bundle = new Bundle();
                bundle.putString(Global.openWhat, Global.infoEvl);
                if (list.size() > 0) {
                    if (list.get(0) != null) {
                        sync = H.isEvaluationOfThisSchoolSynced(list);
                        bundle.putParcelable("data", H.makeInfoMdlForInfoDialogFromEvlModelOrRepMdl(list.get(0), sync, schID));
                    }
                }
                DialogFragment dialog2 = new InfoDialog();
                dialog2.setArguments(bundle);
                dialog2.setCancelable(false);
                dialog2.show(((AppCompatActivity) context).getSupportFragmentManager(), "InfoDialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//___________________End option menu______________________________________
   /*__________ nav drawer close on back button click ____________________*/

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

    /*=============================================================================================================================*/

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        outState.putString("openMode", openMode);
        outState.putString("schID", schID);
        outState.putString("gradeID", gradeID);
        outState.putString("date", date);
        outState.putString("attendance", attendance);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list = savedInstanceState.getParcelableArrayList("list");
        openMode = savedInstanceState.getString("openMode");
        schID = savedInstanceState.getString("schID");
        gradeID = savedInstanceState.getString("gradeID");
        date = savedInstanceState.getString("date");
        attendance = savedInstanceState.getString("attendance");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//result from answer activity
            if (resultCode == Activity.RESULT_OK) {
                resultFromActivity = true;
                openMode = returnIntent.getStringExtra(Global.openMode);
                index = returnIntent.getIntExtra("index", -1);
                data = returnIntent.getParcelableExtra("data");
                changeItemDataInList(index, data);
            }
        } else if (requestCode == 2) {// result from add new question activity
            if (resultCode == Activity.RESULT_OK) {
                resultFromActivity = true;
                openMode = returnIntent.getStringExtra(Global.openMode);
                EvlModelin newQuestion = returnIntent.getParcelableExtra("data");
                list.add(newQuestion);
                adapterRecycler.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // openMode = intent.getStringExtra(Global.openMode);
        setIntent(intent);
        openMode = intent.getStringExtra(Global.openMode);

        //  Log.d(TAG, "on new intent");
    }

    @Override
    protected void onResume() {
        super.onResume();


        /*==================== set data ===============================*/
        // Log.d(TAG, "onResume");

        try {
            getSupportActionBar().setTitle("Evaluation Reporting" /*+ df2.format(df1.parse(date))*/);
        } catch (Exception e) {
            Log.d(TAG, "Exception", e);
        }


        if (openMode.equals(Global.DisplayMode)) {
            fab.setVisibility(View.GONE);
            evlSubmitBtn.setVisibility(View.GONE);
        }

        //  loadData();
        new LoadData().execute();

        // no question msg
        if (DAO.isTableEmpty(db, DB.acad_ques.table)) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval);
        ButterKnife.bind(this);
        //app bar
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        //db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        H.setFont(context, new View[]{evlSubmitBtn});

        // intent
        intent = getIntent();
        openMode = intent.getStringExtra(Global.openMode);
        if (resultFromActivity)
            openMode = Global.RunMode;
        openedFromWhichActivity = intent.getStringExtra(Global.openedFromWhichActivity);
        schID = intent.getStringExtra("schID");//schoolSync id
        gradeID = intent.getStringExtra("gradeID");
        date = intent.getStringExtra("date");
        attendance = intent.getStringExtra("attendance");


/*===================================== spinner =======================================================================*/


        subjectList = DAO2.getSubjectList(gradeID); // subAndChapt.get("subject");
        subjectDefault = subjectList.get(0);
        chapterList = DAO2.getChapterList(gradeID, subjectDefault);




 /*subject*/

       /* SharedPrefHelper.saveSubjectList(subjectList);
        Log.d(TAG, "total : " + SharedPrefHelper.getSubjectList().size()
                + SharedPrefHelper.getSubjectList().toString());*/

        dataAdapterSubject = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, subjectList);
        dataAdapterSubject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubject.setAdapter(dataAdapterSubject);

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectDefault = subjectList.get(position);
                chapterList = DAO2.getChapterList(gradeID, subjectDefault);
                //  Log.d(TAG, chapterList.toString());


               /* chapterList.clear();
                chapterList.addAll(DAO2.getChapterList(gradeID, subjectDefault));*/

                dataAdapterChapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, chapterList);
                dataAdapterChapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerChapter.setAdapter(dataAdapterChapter);
                dataAdapterChapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        
/* chapter */


        dataAdapterChapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, chapterList);
        dataAdapterChapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChapter.setAdapter(dataAdapterChapter);

        spinnerChapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chapterDefault = chapterList.get(position);
                openMode = Global.RunMode;
                new LoadData().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //============================== filter list according to subject and chapter  ===========================================================================================

    List<EvlModelin> filterSubjectAndChapter(List<EvlModelin> inputList) {
        List<EvlModelin> outputList = new ArrayList<>();
        for (EvlModelin e : inputList) {
            if (e.getSubject() != null && e.getChapter() != null) {
                if (e.getSubject().equalsIgnoreCase(subjectDefault)
                        && e.getChapter().equalsIgnoreCase(chapterDefault)) {
                    outputList.add(e);
                }
            }
        }
        return outputList;
    }

    @OnClick(R.id.evlSubmitBtn)
    public void onClickevlSubmitBtn(View v) {
        Intent intentSchProf = new Intent(context, SchoolProfileActivity.class);
        intentSchProf.putExtra("schID", schID);
        intentSchProf.putExtra("gradeID", gradeID);
        intentSchProf.putExtra(Global.openMode, Global.NewMode);
        startActivity(intentSchProf);
        finish();
    }

    @OnClick(R.id.fabEval)
    public void onFABClick(View v) {
        Intent intent1 = new Intent(context, AddQuesActivity.class);
        intent1.putExtra("schID", schID);
        intent1.putExtra("gradeID", gradeID);
        intent1.putExtra("date", date);
        intent1.putExtra("attendance", attendance);
        intent1.putExtra(Global.openMode, openMode);
        startActivityForResult(intent1, 2);
        overridePendingTransition(R.anim.righttoleft, 0);
    }


    //============================== Result from AnsActivity===========================================================================================


    private void changeItemDataInList(int index, EvlModelin changedData) {
        H.formatEvlDataToShowAns(changedData);
        list.set(index, changedData);
        listFiltered=filterSubjectAndChapter(list);

        adapterRecycler.notifyItemChanged(index);
        adapterRecycler.notifyDataSetChanged();

       // Log.d(TAG,list.toString());
    }

    private void submitAll() {
        H.getAlertDialog(context, H.getAnsweredQuesFromEvlList(list).toString());
    }


    @Override
    public void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar) {
        /** not done*/
        this.dateInputted = dateInputted;//date is inputted from date picker fragment
        date = df1.format(calendar.getTime());

        // load questions based on new inputted date
       /* list = DAO2.getActiveEvaluatioQues("");
        adapter = new EvlAdapter(this, schID, gradeID, date, attendance, openMode, list);
        listview.setAdapter(adapter);*/

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapterRecycler = new EvlAdapterRecycler(context, schID, gradeID, date, attendance, openMode, list);
        recyclerView.setAdapter(adapterRecycler);
    }


    //=============================== class ========================================================================================
    class LoadData extends AsyncTask<String, Void, List<EvlModelin>> {
        @Override
        protected List<EvlModelin> doInBackground(String... strings) {
            Log.d(TAG, openMode);



                /*_____________ new mode _______________________*/
            if (openMode.equals(Global.NewMode)) {
                list = DAO2.getActiveEvaluatioQues(gradeID);
                H.setQuesNoForAnsActivity(list);

                subjectDefault = subjectList.get(0);
                chapterDefault = chapterList.get(0);

                listFiltered = filterSubjectAndChapter(list);
            }
            //_____________ edit mode _______________________
            else if (openMode.equals(Global.EditMode)) {
                allQues = DAO2.getActiveEvaluatioQues(gradeID);
                ansQuesList = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
                unAnsQuesList = H.quesNotAnsweredPreviousEvaluation(allQues, ansQuesList);

                list.clear();
                list.addAll(ansQuesList);
                list.addAll(unAnsQuesList);
                H.setQuesNoForAnsActivity(list);
                list = H.formatEvlDataToShowAnsInList(list);


                subjectDefault = subjectList.get(0);
                chapterDefault = chapterList.get(0);

                listFiltered = filterSubjectAndChapter(list);

            }
            //_____________ Run mode _______________________
            else if (openMode.equals(Global.RunMode)) {
                //  list.addAll(A.getEvlList());
                H.setQuesNoForAnsActivity(list);
                list = H.formatEvlDataToShowAnsInList(list);

                listFiltered = filterSubjectAndChapter(list);

            }
            //_____________ display mode _______________________
            else if (openMode.equals(Global.DisplayMode)) {
                list = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
                list = H.formatEvlDataToShowAnsInList(list);

                subjectDefault = subjectList.get(0);
                chapterDefault = chapterList.get(0);

                listFiltered = filterSubjectAndChapter(list);
            }


            //Log.d(TAG, list.toString());
            return list;
        }

        @Override
        protected void onPostExecute(List<EvlModelin> evlModelins) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapterRecycler = new EvlAdapterRecycler(context, schID, gradeID, date, attendance, openMode, listFiltered);
            recyclerView.setAdapter(adapterRecycler);
            pb.setVisibility(View.GONE);
        }
    }


    //================================ unused =========================================================================================
    private void loadData() {
        new AsyncTask<String, Void, List<EvlModelin>>() {
            @Override
            protected List<EvlModelin> doInBackground(String... strings) {
                openMode = strings[0];
                /*_____________ new mode _______________________*/
                if (openMode.equals(Global.NewMode)) {
                    list = DAO2.getActiveEvaluatioQues(null);
                    H.setQuesNoForAnsActivity(list);
                }
                //_____________ edit mode _______________________
                else if (openMode.equals(Global.EditMode)) {
                    allQues = DAO2.getActiveEvaluatioQues(null);
                    ansQuesList = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
                    unAnsQuesList = H.quesNotAnsweredPreviousEvaluation(allQues, ansQuesList);

                    list.clear();
                    list.addAll(ansQuesList);
                    list.addAll(unAnsQuesList);
                    H.setQuesNoForAnsActivity(list);
                    list = H.formatEvlDataToShowAnsInList(list);
                }
                //_____________ Run mode _______________________
                else if (openMode.equals(Global.RunMode)) {
                    //  list.addAll(A.getEvlList());
                    H.setQuesNoForAnsActivity(list);
                    list = H.formatEvlDataToShowAnsInList(list);
                }
                //_____________ display mode _______________________
                else if (openMode.equals(Global.DisplayMode)) {
                    list = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
                    list = H.formatEvlDataToShowAnsInList(list);
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<EvlModelin> evlModelins) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                adapterRecycler = new EvlAdapterRecycler(context, schID, gradeID, date, attendance, openMode, list);
                recyclerView.setAdapter(adapterRecycler);


                /*adapter = new EvlAdapter(context, schID, gradeID, date, attendance, openMode, list);
                listview.setAdapter(adapter);*/

                pb.setVisibility(View.GONE);


                //H.getAlertDialog(context, allQues.toString());
                //H.getAlertDialog(context, ansQuesList.toString());
                //H.getAlertDialog(context, H.quesNotAnsweredPreviousEvaluation(allQues, ansQuesList).toString());
                //H.getAlertDialog(context, list.toString());
            }
        }.execute(openMode);
    }

}
