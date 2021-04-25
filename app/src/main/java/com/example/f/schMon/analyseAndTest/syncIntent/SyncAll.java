/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncIntent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.view.common.NavDrawer;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class SyncAll extends AppCompatActivity implements DownloadResultReceiver.Receiver {
    private static final String TAG = SyncAll.class.getName();
    private final Context context = SyncAll.this;
    ProgressBar progressBarSchool, progressBarStudent, progressBarTeacher;
    TextView tvDownload;
    private ListView listView = null;
    ArrayList<String> urls;
    private ArrayAdapter arrayAdapter = null;
    private DownloadResultReceiver mReceiver;

    final String instituteUrl = "http://bepmis.brac.net/rest/apps/institute?uname=kholilur&passw=123456&max=100000";
    final String studentUrl = " http://bepmis.brac.net/rest/apps/student?uname=kholilur&passw=123456&max=100000";
    final String teacherUrl = " http://bepmis.brac.net/rest/apps/teacher/listAllTeacher?uname=kholilur&passw=123456&max=100000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_intent);
        ButterKnife.bind(this);
        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);


        /* Allow activity to show indeterminate progressbar */
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        /* Set activity layout */

        //listView = findViewById(R.id.listView);
        progressBarSchool = findViewById(R.id.progressBarSchool);
        progressBarTeacher = findViewById(R.id.progressBarTeacher);
        progressBarStudent = findViewById(R.id.progressBarStudent);
        tvDownload = findViewById(R.id.sync_total_download_value);
        //String [] urls = new String[3];
        urls = new ArrayList<>();
        urls.add(instituteUrl);
        urls.add(teacherUrl);
        urls.add(studentUrl);
    }

    public void syncAll(View v) {

        //progressBar.setVisibility(View.VISIBLE);

        /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);
        /* Send optional extras to Download IntentService */
        intent.putExtra("urls", urls);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);
        startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:
                progressBarSchool.setVisibility(View.VISIBLE);
                progressBarTeacher.setVisibility(View.VISIBLE);
                progressBarStudent.setVisibility(View.VISIBLE);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                String result = resultData.getString("result");
                int task = Integer.parseInt(result);
                if (task == 2) {
                    progressBarSchool.setIndeterminate(false);
                    progressBarTeacher.setIndeterminate(false);
                    progressBarStudent.setIndeterminate(false);
                }
                Toast.makeText(this, "Task " + task + " completed!", Toast.LENGTH_LONG).show();
                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                progressBarSchool.setVisibility(View.INVISIBLE);
                progressBarTeacher.setVisibility(View.INVISIBLE);
                progressBarStudent.setVisibility(View.INVISIBLE);
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
