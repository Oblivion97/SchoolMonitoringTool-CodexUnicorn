/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by Mir Ferdous on 9/21/2017.
 */

public class HistoryActivity extends AppCompatActivity {
    private Context context = HistoryActivity.this;
    private final String TAG = HistoryActivity.class.getName();
    private SQLiteDatabase db;
    HistoryAdapter adapter = null;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.historyList)
    ListView listView;

    int colorBck, colorTxt;
    List<RepMdlin> list = new ArrayList<>();


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
            //home menu icon(color) change
            menu.findItem(R.id.home_menu).setVisible(false);
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_home_black);
            menu.findItem(R.id.home_menu).setIcon(drawable);
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
                finish();
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
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //________________ DB __________________________

        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        // displayHistory();

        loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        //___________ app bar _____________
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
    }


    private void loadData() {

        new AsyncTask<String, Integer, List<RepMdlin>>() {
            @Override
            protected List<RepMdlin> doInBackground(String... voids) {
                list = DAO2.getAllHistoryNofifRep();
                return list;
            }

            @Override
            protected void onPostExecute(List<RepMdlin> list) {
                adapter = new HistoryAdapter(context, list);
                listView.setAdapter(adapter);
                pb.setVisibility(View.GONE);

                //  H.getAlertDialog(context, list.toString());
            }
        }.execute();


    }


    private void displayHistory() {
        //Array list of NotifData
        ArrayList<NotifData> HistoryDataList = new ArrayList<>();

        String id, title, question, date, schoolName, grade;
        try {
            // DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            db = Global.getDB_with_nativeWay_writable(Global.dbName);
            Cursor c = db.rawQuery("SELECT report_admin._id AS id, report_admin.questionTitle AS title, report_admin.question AS question, report_admin.n_date_update AS date, schools.s_name AS name, schools.edu_type AS eduType FROM report_admin, schools WHERE report_admin.sch_id = schools.s_id AND report_admin.notify_flag = '1' ", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id = c.getString(c.getColumnIndex("id"));
                        title = c.getString(c.getColumnIndex("title"));
                        question = c.getString(c.getColumnIndex("question"));
                        date = c.getString(c.getColumnIndex("date"));
                        Log.d("datab", title + " " + date);
                        // Log.e(getClass().getSimpleName(), title+" : "+question);
                        schoolName = c.getString(c.getColumnIndex("name"));
                        grade = c.getString(c.getColumnIndex("eduType"));
                        NotifData data = new NotifData(id, title, schoolName, question, grade, date);
                        HistoryDataList.add(data);
                    } while (c.moveToNext());//
                    c.close();
                }
            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {
            //db.close();
        }


        adapter = new HistoryAdapter(context, list);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                NotifData data = (NotifData) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + data.getTitle(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }


}
