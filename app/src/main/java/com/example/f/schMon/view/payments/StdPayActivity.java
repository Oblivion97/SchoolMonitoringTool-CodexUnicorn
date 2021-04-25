/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.payments.paymdl.PayMdlInner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class StdPayActivity extends AppCompatActivity {
    private static final String TAG = StdPayActivity.class.getName();
    private Context context = StdPayActivity.this;
    private String schID, gradeID, schName, code, month, year;
    private StdPayAdapter adapter;
    Toolbar toolbar;
    private List<PayMdlInner> list = new ArrayList<>();
    HashMap<String, String> monthMap;
    SimpleDateFormat df6 = new SimpleDateFormat("yyyy");
    @BindView(R.id.stdPayList)
    RecyclerView recyclerView;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;
    @BindView(R.id.spinnerMonth)
    Spinner spinnerMonth;
    @BindView(R.id.spinnerYear)
    Spinner spinnerYear;
    @BindView(R.id.infoTV)
    TextView infoTV;
    @BindView(R.id.noDataTV)
    TextView noDataTV;

//___________________start option menu______________________________________

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            menu.findItem(R.id.home_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        Intent intent = getIntent();
        schID = intent.getStringExtra("schID");
        gradeID = intent.getStringExtra("gradeID");
        schName = intent.getStringExtra("schName");
        code = intent.getStringExtra("code");

        month = "January";
        Log.d(TAG, String.valueOf(H.getPreviousMonth()));
        year = df6.format(new Date(System.currentTimeMillis()));
        year = "2017";
        monthMap = getMonthMap();


        year = String.valueOf(H.getPreviousMonth()[1]);


        toolbar.setTitle(schName + " (Code: " + code + ")");
        new LoadData().execute();
        if (list.isEmpty()) {
            noDataTV.setVisibility(View.VISIBLE);
        } else noDataTV.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_pay);
        ButterKnife.bind(this);

        //app bar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        H.setFont(context, new View[]{infoTV, noDataTV});

        //==================== spinner simple =====================================================================================================


        //================ month spinner =========================================================================================================

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_textview, getMonthList());
        spinnerMonth.setAdapter(adapter);
        spinnerMonth.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int i = spinnerMonth.getSelectedItemPosition();
                        month = getMonthList().get(i);
                        month = monthMap.get(month);//digit
                        //month = String.valueOf(H.getPreviousMonth()[0]);
                        pb.setVisibility(View.VISIBLE);
                        new LoadData().execute();
                        if (list.isEmpty()) {
                            noDataTV.setVisibility(View.VISIBLE);
                        } else noDataTV.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );
        spinnerMonth.setSelection(H.getPreviousMonth()[0]);




        //============= year ============================================================================================================
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(this, R.layout.spinner_textview, getYearList());
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int i = spinnerYear.getSelectedItemPosition();
                        year = getYearList().get(i);
                        pb.setVisibility(View.VISIBLE);
                        new LoadData().execute();
                        if (list.isEmpty()) {
                            noDataTV.setVisibility(View.VISIBLE);
                        } else noDataTV.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );


    }


    //=========================================================================================================================

    public List<String> getYearList() {
        List<String> m;
        m = DAO2.all_unique_values_from_a_Column(DB.payments.table, DB.payments.year);
        return m;
    }

    public List<String> getMonthList() {
        List<String> m = new ArrayList<>();
        m.add("January");
        m.add("February");
        m.add("March");
        m.add("April");
        m.add("May");
        m.add("June");
        m.add("July");
        m.add("August");
        m.add("September");
        m.add("October");
        m.add("November");
        m.add("December");
        return m;
    }

    public HashMap<String, String> getMonthMap() {
        HashMap<String, String> maps = new HashMap<>();
        List<String> listMonth = getMonthList();
        for (int i = 0; i < listMonth.size(); i++) {
            maps.put(listMonth.get(i), String.valueOf(i));
        }
        return maps;
    }


    //=============class============================================================================================================

    class LoadData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            //  list = DAO2.getPaymentOf_a_School(schID, gradeID);
            list = DAO2.getPaymentOf_a_School_Monthwise(schID, gradeID, month, year);
            //  Log.d(TAG, list.toString());
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new StdPayAdapter(context, list);
            recyclerView.setAdapter(adapter);
            pb.setVisibility(View.GONE);
        }
    }
}
