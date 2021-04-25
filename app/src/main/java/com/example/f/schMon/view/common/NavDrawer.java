/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.analyseAndTest.TestActivity0;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.oldAPI.LoginModel;
import com.example.f.schMon.model.oldAPI.UserModel;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.log_edit.LogEditActivityTab;
import com.example.f.schMon.view.login.LoginActivity;
import com.example.f.schMon.view.payments.SchPayActivity;
import com.example.f.schMon.view.school.SchoolListActivity;
import com.example.f.schMon.view.sync_mir.SyncActivity;
import com.google.gson.Gson;

/**
 * Created by Mir Ferdous on 04/09/2017.
 */

public class NavDrawer {
    public static void navDrawerBtnsClick(final Context context, Toolbar toolbar) {

        final AppCompatActivity appCompatActivity = (AppCompatActivity) context;

        // ________________ StartOfApp Nav Drawer ________________

        DrawerLayout drawer = appCompatActivity.findViewById(R.id.drawer_layout);

        NavigationView navigationView = appCompatActivity.findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userID = headerView.findViewById(R.id.POid);
        TextView userName = headerView.findViewById(R.id.POname);
        TextView userGrade = headerView.findViewById(R.id.POgrade);
        TextView userPhone = headerView.findViewById(R.id.POPhone);
        TextView userAddress = headerView.findViewById(R.id.POaddress);

        //__________ get po info _______________
        UserModel userM;
        SharedPreferences sp = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        String responseSave = sp.getString(Global.userModel, "");
        Gson gson = new Gson();
        if (responseSave.equals("")) {
            userM = new UserModel();
        } else {
            LoginModel mResponseModel = gson.fromJson(responseSave, LoginModel.class);
            userM = mResponseModel.getModel();
        }
        //null data check in userModel
        String s = "Not Available";
        if (userM.getId() != null)
            userID.setText("ID: " + userM.getId());
        else
            userID.setText("ID: " + s);

        if (userM.getFirstName() == null && userM.getMiddleName() == null && userM.getLastName() == null)
            userName.setText("Name: " + H.toCamelCase(userM.getUsername()).replaceAll("_", " "));
        else if (userM.getFirstName() != null && userM.getLastName() != null)
            userName.setText("Name: " + H.toCamelCase(userM.getFirstName() + " " + userM.getLastName()));
        else
            userName.setText("Name: " + H.toCamelCase(userM.getFirstName() + " " + userM.getMiddleName() + " " + userM.getLastName()));

        if (userM.getGradeName() != null)
            userGrade.setText("Grade: " + userM.getGradeName());
        else
            userGrade.setText("Grade: " + s);
        if (userM.getMobileNumber() != null)
            userPhone.setText("Phone: " + userM.getMobileNumber());
        else
            userPhone.setText("Phone: " + s);
        if (userM.getAddress() != null)
            userAddress.setText("Address: " + userM.getAddress());
        else userAddress.setText("Address: " + s);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                appCompatActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                if (id == R.id.Home) {
                    intent = new Intent(context, DashboardActivity.class);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.SyncNewMir) {
                    intent = new Intent(context, SyncActivity.class);
                    intent.putExtra("syncStatus", "notRunning");
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.Reporting) {
                    intent = new Intent(context, SchoolListActivity.class);
                    intent.putExtra(Global.toOpenWhichActivity, Global.Reporting);
                    /** same schoolSync list is used for both opening School Profile
                     Activity & Reporting Activity so based on this parameter School List Activity will desice which one to open
                     if Reporting menu from Nav Drawer is clicked then schoolSync list item click will open reportin Activity
                     if School List menu from Nav Drawer is clicked then schoolSync list item click will open School Profile Activity*/
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.Evaluation) {
                    intent = new Intent(context, SchoolListActivity.class);
                    intent.putExtra(Global.toOpenWhichActivity, Global.Evaluation);
                    appCompatActivity.startActivity(intent);
                    /*
                    DialogFragment dialog = new DialogDateAttdce();
                    dialog.setCancelable(false);
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "DialogDateAttdce");*/
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.Payment) {
                    intent = new Intent(context, SchPayActivity.class);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.SchoolList) {
                    intent = new Intent(context, SchoolListActivity.class);
                    intent.putExtra(Global.toOpenWhichActivity, Global.SchoolProfile);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.Log) {
                    appCompatActivity.startActivity(new Intent(context, LogEditActivityTab.class));


                } else if (id == R.id.About) {
                    intent = new Intent(context, AboutActivity.class);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.Exit) {
                    intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.LogOut) {
                    SharedPreferences pref = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("rememberMe", false);
                    editor.apply();

                    Intent intent3 = new Intent(context, LoginActivity.class);
                    intent3.putExtra(Global.fromWhere, Global.LogOut);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    appCompatActivity.startActivity(intent3);
                    appCompatActivity.finish();

                } else if (id == R.id.Testing) {
                    appCompatActivity.startActivity(new Intent(context, TestActivity0.class));
                    // appCompatActivity.startActivity(new Intent(context, TestActivity6.class));
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                } else if (id == R.id.Settings) {
                    intent = new Intent(context, SettingsActivity.class);
                    appCompatActivity.startActivity(intent);
                    appCompatActivity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

                }

                DrawerLayout drawer = appCompatActivity.findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // ________________ End Nav Drawer Menu ________________


        //____________________________ header click user profile opens _______________________________________________________________

        headerView.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProfActivity.class);
                context.startActivity(intent);
            }
        });


    }
}
