/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.controller.A;
import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.controller.webservice.WSManager;
import com.example.f.schMon.controller.webservice.WSResponseListener;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.view.sync_mir.API;
import com.example.f.schMon.analyseAndTest.syncMirOld.Sync;
import com.example.f.schMon.analyseAndTest.syncMirOld.SyncHelper;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.analyseAndTest.report.RepAdminActivity;
import com.example.f.schMon.view.school.SchoolListActivity;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class TestActivity1 extends AppCompatActivity implements WSResponseListener {
    String TAG = getClass().getName();
    TextView textView;
    Button syncSchBtn, syncStdBtn, syncTechBtn, cleanBtn, stringFromFileBtn,
            syncStdIDBtn, syncSchIDBtn, syncTechIDBtn, copyBtn,
            testAPICallBtn, testQueryBtn;
    WSManager wsManager;
    String response1,
            response2, y;
    Context context = TestActivity1.this;
    String[] loginArr;
    String jsonRes = "";
    String[] asyncTaskParamUrl;
    int x = 0;
    SQLiteDatabase db;
    EditText tableNameET;
    private String url;
    Button btnCopyDB;

    // __________ nav drawer close on back button click ____________________

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        textView = findViewById(R.id.testTV1);
        syncSchBtn = findViewById(R.id.testBtn1);
        syncStdBtn = findViewById(R.id.testBtn2);
        syncTechBtn = findViewById(R.id.testBtn3);
        cleanBtn = findViewById(R.id.btnClear);
        stringFromFileBtn = findViewById(R.id.testBtn5);
        syncStdIDBtn = findViewById(R.id.testBtn12);
        syncSchBtn = findViewById(R.id.testBtn1);

        tableNameET = findViewById(R.id.deleteTableET);


        btnCopyDB = (Button) findViewById(R.id.btnCopyDB);
        btnCopyDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             TestActivity1PermissionsDispatcher.copyDBWithPermissionCheck(TestActivity1.this);
                //copyDB();
            }
        });


        //_________temp btn label______________________

        db = Global.getDB_with_nativeWay_writable(Global.dbName);
        if (DAO.isTableEmpty(db, "schools")) {
            textView.setText("table is empty");
        } else {
            textView.setText("table full");
        }


        //nav drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.Home) {
                    Intent searchIntent = new Intent(context, DashboardActivity.class);
                    startActivity(searchIntent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else if (id == R.id.Reporting) {
                    Intent searchIntent = new Intent(context, RepAdminActivity.class);
                    startActivity(searchIntent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                } else if (id == R.id.SchoolList) {
                    Intent searchIntent = new Intent(context, SchoolListActivity.class);
                    startActivity(searchIntent);
                    overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        TestActivity1PermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    public void StringFromFile(View view) {
        String t = H.getStringFrom_File_in_Assets(context, "schoolPoWise.txt");
        textView.setText(t);
    }

    public void onClickClear(View v) {
        cleanBtn.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        textView.setText(null);
    }


    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void copyDB() {
        int succ = DAO.copyDataBase(null, null,
                Global.getDB_with_nativeWay_writable(Global.dbName));
        String toastMessg;
        if (succ == 1)
            toastMessg = "db copied";
        else toastMessg = "failed to copy";

        Toast.makeText(TestActivity1.this, toastMessg, Toast.LENGTH_SHORT).show();
    }


    public void onClickTestAPICALL(View view) {

        StringBuilder t = new StringBuilder();

        Sync.callLoginAPI(context);


        //1
        t.append("\n_________Call 1_________\n\n\n");
        String jsonRes1 = Sync.callApiGET(context,
                "http://bepmis.brac.net/rest/teacherSync/listAllTeacher?max=10&start=0");
        t.append(jsonRes1);


        //2
        t.append("\n\n\n\n_________Call 2_________\n\n\n");
        String jsonRes2 = Sync.callApiGET(context,
                "http://bepmis.brac.net/rest/teacherSync/listAllTeacher?max=10&start=1");
        t.append(jsonRes2);


        //3
        t.append("\n\n\n\n_________Call 3_________\n\n\n");
        String jsonRes3 = Sync.callApiGET(context,
                "http://bepmis.brac.net/rest/teacherSync/listAllTeacher?max=10&start=2");
        t.append(jsonRes3);


        //same of not check
        if (jsonRes1 != null && jsonRes2 != null && jsonRes3 != null) {

            if (jsonRes1.equals(jsonRes2) || jsonRes1.equals(jsonRes3) || jsonRes2.equals(jsonRes3))
                y = "same json response";
            else
                y = "diffrent json response";
            t.append("\n\n\n\n_________" + y + "_________\n\n\n");
        }


        if (jsonRes != null) {
            textView.setText(t.toString());
        }
    }

    public void onTestQuery(View view) {
        List<String> list = SyncHelper.getAllIDFromTable(context, db, "teachers", "t_id");
        int size = list.size();
        boolean x = SyncHelper.isUnique(list);

        Log.d(TAG, "size is : " + size + "\n\n" +
                "is unique : " + x + "\n\n" +
                list.toString());

    }

    public void onClickSyncSchools(View v) {
        asyncTaskParamUrl = new String[]{API.urlSchoolListPowise};
        new RestAPICallThread().execute(asyncTaskParamUrl);
        cleanBtn.setTextColor(ContextCompat.getColor(TestActivity1.this, R.color.navigationBarColor));
    }

    public void onClickSyncStd(View view) {
        Toast.makeText(A.getAppContext(), "not available", Toast.LENGTH_SHORT).show();
    }

    public void onClickSyncTeacher(View view) {
        // SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        Object[] object = Sync.syncTeacherTableAll(context.getApplicationContext(), db);
        if (object != null && object[1] != null) {
            textView.setText(object[1].toString());
            Toast.makeText(context, "inserted", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSyncTeacherID(View view) {
        Sync.updateATeachersAllInfos(context.getApplicationContext(), db);
        Toast.makeText(context, "All info of teacherSync updated", Toast.LENGTH_SHORT).show();

    }

    public void onClickDeleteTable(View view) {
        String messg;
        String table = tableNameET.getText().toString();
        try {
            DAO.deleteDataFromTable(db, table);
            messg = table + " table deleted";
        } catch (Exception ex) {
            messg = "wrong table name";
        }
        Toast.makeText(context, messg, Toast.LENGTH_SHORT).show();
    }


    /**
     * response
     */
    @Override
    public void success(String response, String url) {
        response1 = response;
        //response2 = response;
        // textView.setText(response1);
    }

    @Override
    public void failure(int statusCode, Throwable error, String content) {
    }


    class RestAPICallThread extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
            Object[] object = new Object[5];
            //  object1 = Sync.syncSchoolTableTen(A.getAppContext(), db, params[0]);
            object = Sync.syncSchoolTableAll(A.getAppContext(), db, params[0]);
            if (object[0] != null)
                jsonRes = object[1].toString();
            return jsonRes;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(jsonRes);
        }
    }


}


