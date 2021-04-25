/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.f.schMon.R;
import com.example.f.schMon.model.appInner.Student;
import com.example.f.schMon.view.common.NavDrawer;

public class StdListSearchActivity extends AppCompatActivity {
    private final String TAG = StdListSearchActivity.class.getName();
    private final Context context = StdListSearchActivity.this;
    Student[] stdListSearch;
    ListView listview;
    StdListAdapter adapter;
    private String schID;


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
        setContentView(R.layout.activity_std_list_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        Intent intent = getIntent();
        schID = intent.getStringExtra("schID");
        listview = findViewById(R.id.listViewStdSearch);


        //___________________set data___________________

       /* bundle = getIntent().getExtras();
        parcelables = bundle.getParcelableArray("stdListSearch");
        int parLenth = parcelables.length;
        stdListSearch = new Student[parLenth];
        for (int i = 0; i < parLenth; i++)
            stdListSearch[i] = (Student) parcelables[i];*/
        stdListSearch = Temp.stdListSearch;
        adapter = new StdListAdapter(this, stdListSearch);
        listview.setAdapter(adapter);

    }


}



