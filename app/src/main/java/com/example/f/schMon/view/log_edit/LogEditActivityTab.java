/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.log_edit;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.f.schMon.R;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.log_edit.logEval.LogEvalFrag;
import com.example.f.schMon.view.log_edit.logRep.LogRepFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogEditActivityTab extends AppCompatActivity implements LogRepFrag.OnFragmentInteractionListener {
    private final String TAG = LogEditActivityTab.class.getName();
    private final Context context = LogEditActivityTab.this;
    SQLiteDatabase db;
    private ViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tab)
    TabLayout tabLayout;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//___________________End option menu______________________________________
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
    protected void onResume() {
        super.onResume();
        setViewPagerAdapter(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_edit_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);


    }

    //____________adding fragment____________
    public void setViewPagerAdapter(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(LogRepFrag.newInstance("", ""), "Report Log");
        viewPagerAdapter.addFragment(LogEvalFrag.newInstance("", ""), "Evaluation Log");

        //viewPagerAdapter.addFragment(new BlankFragment1(),"Blank");
        viewPager.setAdapter(viewPagerAdapter);


    }


    //____________________Fragment Listener_______________________________________________
    @Override
    public void onFragmentInteraction(Uri uri) {
    }


    //______________________Fragment Adapter____________________________________________________
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> tabNameList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //______________Adding Fragment_________________________
        public void addFragment(Fragment fragment, String tabName) {
            fragmentList.add(fragment);
            tabNameList.add(tabName);
        }


        //___________________Override______________________________
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.d("viewsize", String.valueOf(fragmentList.size()));
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabNameList.get(position);
        }
    }
}
