/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.view.common.NavDrawer;
import com.example.f.schMon.view.common.SettingsActivity;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.paymentsWithTab.feeSch.FeeSchFrag;
import com.example.f.schMon.view.paymentsWithTab.feeStd.FeeStdFrag;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivityTabStd extends AppCompatActivity implements FeeSchFrag.OnFragmentInteractionListener {
    private final String TAG = PaymentActivityTabStd.class.getName();
    private final Context context = PaymentActivityTabStd.this;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private String schName;
    private SQLiteDatabase db;

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
            menu.findItem(R.id.date_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sync_menu:

                return true;
            case R.id.home_menu:
                context.startActivity(new Intent(context, DashboardActivity.class));
                return true;
            case R.id.settings_menu:
                Intent intent = new Intent(context, SettingsActivity.class);
                context.startActivity(intent);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_tab_std);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        //app bar title change
        schName = TempDataPayment.schNameForTitle;
        toolbar.setTitle(schName);

        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        if (DAO.isTableEmpty(db, DB.students.table)) {
            new AlertDialog.Builder(context).setMessage("No Data Available. Please Sync School Data.").create().show();
        } else {


            viewPager = findViewById(R.id.container);
            setViewPagerAdapter(viewPager);
            TabLayout tabLayout = findViewById(R.id.tab);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    //____________adding fragment____________
    public void setViewPagerAdapter(ViewPager viewPager) {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(FeeStdFrag.newInstance("", ""), "Fee Collection [Student]");
        // viewPagerAdapter.addFragment(LogEvaFrag.newInstance("", ""), "Teacher's Salary");

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
            //return FeeStdFrag.newInstance("", "", position + 1);
            //return PlaceholderFragment.newInstance(position + 1);
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            Log.d("viewsize", String.valueOf(fragmentList.size()));
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*switch (position) {
                case 0:
                    return "Fee Collection";
                case 1:
                    return "Teacher's Salary";

            }
            return null;*/
            return tabNameList.get(position);
        }
    }
}
