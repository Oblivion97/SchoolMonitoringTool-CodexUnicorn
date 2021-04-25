/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.report.RepAdminActivity;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.newAPI.SchMdlList;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.paymentsWithTab.PaymentActivityTabSch;
import com.example.f.schMon.view.login.LoginActivity;
import com.example.f.schMon.view.school.SchoolListActivity;
import com.example.f.schMon.view.sync_mir.APInterface;
import com.example.f.schMon.view.sync_mir.Client;
import com.example.f.schMon.view.sync_mir.unused.SyncAsynchronous;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity0 extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int MY_PERMISSIONS_EXTERNAL_STORAGE = 1;
    private final String TAG = TestActivity0.class.getName();
    SQLiteDatabase db;
    private Context context = TestActivity0.this;
    @BindView(R.id.btnTestActivity6)
    Button testBtn6;
    private TextView mTextMessage;
    //___________________navigation menu______________________________________
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_log:
                    mTextMessage.setText("Message");
                    return true;
                case R.id.navigation_schList:
                    Intent intent = new Intent(TestActivity0.this, SchoolListActivity.class);
                    intent.putExtra("openedWhichActivity", "SchoolProfile");
                    startActivity(intent);
                    return true;
                case R.id.navigation_notific:
                    mTextMessage.setText("Notification");
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_menu:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.home_menu:
                startActivity(new Intent(context, DashboardActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//___________________End option menu______________________________________


    /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @BindView(R.id.btnTestActivity5)
    Button test5Btn;

    @OnClick(R.id.btnTestActivity5)
    public void onClickTestBtn5(View view) {
        startActivity(new Intent(context, TestActivity5.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test0);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //  nav drawer
//        NavDrawer.navDrawerBtnsClick(context, toolbar);
        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        mService = Client.getAPInterface();

    }

    APInterface mService;
    String s, max = "10";
    Handler handler;

    public void loadAPI() {
        mService.getSchList(A.getU(), A.getP(), max).enqueue(new Callback<SchMdlList>() {
            @Override
            public void onResponse(Call<SchMdlList> call, Response<SchMdlList> response) {
                s = response.body().getModel().toString();
                H.getAlertDialog(context, s);
            }

            @Override
            public void onFailure(Call<SchMdlList> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.test0API)
    public void onClicktest0API(View v) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                syncRetrofit();
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                H.getAlertDialog(context, String.valueOf(
                        new DecimalFormat("#.00").format(A.getDwnld())));
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }

    void syncRetrofit() {
        SyncAsynchronous syncAsynchronous = new SyncAsynchronous(context);
        syncAsynchronous.syncAllAsync();
        // unSyncSchools();
    }


    @OnClick(R.id.btnTestActivity6)
    public void onClickTestBtn6(View v) {
        startActivity(new Intent(context, TestActivity6.class));
    }


    @OnClick(R.id.newReporting)
    public void onClickNewReport(View v) {
        Intent intent = new Intent(context, SchoolListActivity.class);
        intent.putExtra("openedWhichActivity", "Reporting");                    /** same schoolSync list is used for both opening School Profile
         Activity & Reporting Activity so based on this parameter School List Activity will desice which one to open
         if Reporting menu from Nav Drawer is clicked then schoolSync list item click will open reportin Activity
         if School List menu from Nav Drawer is clicked then schoolSync list item click will open School Profile Activity*/
        startActivity(intent);
    }


    public void onCopyDBDash(View view) {

       /* int succ = DAO.copyDataBase(null, null,
                Global.getDB_with_nativeWay_writable(Global.dbName));
        String toastMessg;
        if (succ == 1)
            toastMessg = "db copied";
        else toastMessg = "failed to copy";
        Toast.makeText(context, toastMessg, Toast.LENGTH_SHORT).show();*/

        copyDB();


        //============================ permission event bus =================

    /*    int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_EXTERNAL_STORAGE);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                ActivityCompat.OnRequestPermissionsResultCallback a =
                        new ActivityCompat.OnRequestPermissionsResultCallback() {
                            @Override
                            public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                                int succ = DAO.copyDataBase(null, null,
                                        Global.getDB_with_nativeWay_writable(Global.dbName));
                                String toastMessg;
                                if (succ == 1)
                                    toastMessg = "db copied";
                                else toastMessg = "failed to copy";
                                Toast.makeText(context, toastMessg, Toast.LENGTH_SHORT).show();
                            }
                        };
            *//*    a.onRequestPermissionsResult(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new int[]  {
                    MY_PERMISSIONS_EXTERNAL_STORAGE
                });*//*

            }
        }*/


    }


    private static final int STORAGE_PERMISSION = 120;

    @AfterPermissionGranted(STORAGE_PERMISSION)
    void copyDB() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            int succ = DAO.copyDataBase(null, null,
                    Global.getDB_with_nativeWay_writable(Global.dbName));
            String toastMessg;
            if (succ == 1)
                toastMessg = "db copied";
            else toastMessg = "failed to copy";
            Toast.makeText(context, toastMessg, Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "This App needs Storage Permission to Run Properly",
                    STORAGE_PERMISSION, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }


    @OnClick(R.id.dbListBtn)
    void onClickDBList(View v) {
        startActivity(new Intent(context, TestActivityDBList.class));
    }

    public void onClickDeleteDB(View view) {
        final EditText tableInput = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        tableInput.setLayoutParams(lp);

        AlertDialog alertDialog = new AlertDialog.Builder(context).
                setMessage("insert table name to delete:").setPositiveButton("delete this table", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String messg;
                String table = tableInput.getText().toString();
                try {
                    DAO.deleteDataFromTable(db, table);
                    messg = table + " table deleted";
                } catch (Exception ex) {
                    messg = "wrong table name";
                }

                Toast toast = Toast.makeText(context, messg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 600);
                toast.show();
            }
        }).setNegativeButton("Delete All table", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    DAO.deleteDataFromTable(db, DB.schools.table);
                    DAO.deleteDataFromTable(db, DB.students.table);
                    DAO.deleteDataFromTable(db, DB.teachers.table);
                    DAO.deleteDataFromTable(db, DB.admin_ques.table);
                    DAO.deleteDataFromTable(db, DB.acad_ques.table);
                } catch (Exception ex) {
                }
                Toast.makeText(context, DB.schools.table + "\n" +
                        DB.students.table + "\n" +
                        DB.teachers.table + "\n" +
                        DB.admin_ques.table + "\n" +
                        DB.acad_ques.table + "\n" +
                        "tables deleted", Toast.LENGTH_SHORT).show();
            }
        }).setView(tableInput).create();


        alertDialog.show();


    }

    public void onClickLogout(View view) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        boolean rememberMe = pref.getBoolean("rememberMe", false);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("rememberMe", false);
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Global.fromWhere, Global.LogOut);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onClickPayment(View view) {


        Intent intent = new Intent(this, PaymentActivityTabSch.class);
        startActivity(intent);
    }

    public void onClickTestAPI(View view) {
        Intent intent = new Intent(this, TestActivity1.class);
        startActivity(intent);
    }

    public void onClickTestAPI2(View view) {
        Intent intent = new Intent(this, TestActivity2.class);
        startActivity(intent);
    }

    public void onClickTestActiity3(View view) {
        Intent intent = new Intent(this, TestActivity3.class);
        startActivity(intent);
    }

    @OnClick(R.id.testActivity7)
    public void onClickTestActivity7(View v) {
        startActivity(new Intent(context, TestActivity7.class));
    }

    public void onClickReportMain(View view) {
        Intent intent = new Intent(this, RepAdminActivity.class);
        startActivity(intent);
    }

    public void onClickTestActiity4(View view) {
        Intent intent = new Intent(this, TestActivity4.class);
        startActivity(intent);
    }


}