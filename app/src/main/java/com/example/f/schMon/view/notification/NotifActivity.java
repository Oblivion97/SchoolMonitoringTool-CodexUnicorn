/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.view.common.DatePickerFragment;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by Mir Ferdous on 9/21/2017.
 */

public class NotifActivity extends AppCompatActivity implements DatePickerFragment.DateDialogListner {
    private final String TAG = NotifActivity.class.getName();
    private Context context = NotifActivity.this;
    private SQLiteDatabase db;
    int REQUEST_CODE = 1;
    List<RepMdlin> list = new ArrayList<>();
    NotifAdapter adapter = null;
    int colorBck, colorTxt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notificationList)
    ListView listView;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    private List<NotifData> dataList = new ArrayList<>();
    private String notifType;


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
            menu.findItem(R.id.history_menu).setVisible(true);
            //home menu icon(color) change
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
                return true;
            case R.id.notif_all_menu:
                Intent intentAllNotif = new Intent(context, NotifActivity.class);
                intentAllNotif.putExtra(Global.notifType, Global.allNotif);
                startActivity(intentAllNotif);
                return true;
            case R.id.history_menu:
                context.startActivity(new Intent(context, HistoryActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*___________________End option menu______________________________________*/

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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        //___________ app bar _____________

        setSupportActionBar(toolbar);
        //toolbar color
       /* colorBck = ContextCompat.getColor(context, R.color.notifBck);
        colorTxt = ContextCompat.getColor(context, R.color.notifToolbarTxt);
        toolbar.setBackgroundColor(colorBck);
        toolbar.setTitleTextColor(colorTxt);*/

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        //________________ DB __________________________

        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        //________________ set data __________________________

        // displayListView();
        loadData();


    }


    private void loadData() {

        new LoadData().execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                RepMdlin data = (RepMdlin) parent.getItemAtPosition(position);
            }
        });
    }

    public void history(View v) {
        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
    }


//=================================  date picker of Adapter =========================================================================================

    private String dateRep;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar) {
        dateRep = df1.format(calendar.getTime());
    }


    //================================ class =========================================================================================
    class LoadData extends AsyncTask<String, Integer, List<RepMdlin>> {
        @Override
        protected List<RepMdlin> doInBackground(String... voids) {
            list = DAO2.getAllExistingProblemsFromEnvReport();
            return list;
        }

        @Override
        protected void onPostExecute(List<RepMdlin> list) {
            adapter = new NotifAdapter(context, R.layout.row_notif, dataList, list);
            listView.setAdapter(adapter);
            pb.setVisibility(View.GONE);
        }
    }

}

