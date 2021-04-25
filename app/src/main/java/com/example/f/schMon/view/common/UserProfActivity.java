/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.newAPI.UserModel;
import com.example.f.schMon.model.newAPI.UserModelList;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.login.LoginActivity;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserProfActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getName();
    private final Context context = UserProfActivity.this;
    private SQLiteDatabase db;
    @BindView(R.id.tchrNoData)
    TextView title;
    @BindView(R.id.POname)
    TextView nameTV;
    @BindView(R.id.POgender)
    TextView genderTV;
    @BindView(R.id.POid)
    TextView idTV;
    @BindView(R.id.POgrade)
    TextView gradeTV;
    @BindView(R.id.Phone)
    TextView mobileTV;
    @BindView(R.id.addressTV)
    TextView addTV;
    @BindView(R.id.logoutBtn)
    Button logoutBtn;

    UserModel userM;

    String json;
    Gson gson = new Gson();
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
            menu.findItem(R.id.settings_menu).setVisible(false);
            menu.findItem(R.id.home_menu).setVisible(false);
            menu.findItem(R.id.date_menu).setVisible(false);
            menu.findItem(R.id.sync_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                Intent intent = new Intent(context, SettingsActivity.class);
                context.startActivity(intent);
                return true;
            case android.R.id.home:
                //when back button in toolbar pressed
                startActivity(new Intent(context, DashboardActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //___________________End option menu______________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prof);
        ButterKnife.bind(this);

        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      /*  if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        /*_____ nav drawer _____*/
        NavDrawer.navDrawerBtnsClick(context, toolbar);

        // get writable global db
        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        H.setFont(context, new View[]{title, nameTV, genderTV, idTV, gradeTV, mobileTV, addTV,});


        /*_____ set data _____*/
        SharedPreferences sharedpreferences = this.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        final String oldUserName = sharedpreferences.getString("userName", "");
        boolean userInfoEmpty = true;


        json = sharedpreferences.getString(Global.userModel, "");
        UserModelList userModelList = gson.fromJson(json, com.example.f.schMon.model.newAPI.UserModelList.class);
        userM = userModelList.getModel();

        if (userM != null) {
            if (userM.getFirstName() == null && userM.getMiddleName() == null && userM.getLastName() == null)
                nameTV.setText("Name: " + H.toCamelCase(userM.getUsername()).replaceAll("_", " "));
            else if (userM.getFirstName() != null && userM.getLastName() != null)
                nameTV.setText("Name: " + H.toCamelCase(userM.getFirstName() + " " + userM.getLastName()));
            else
                nameTV.setText("Name: " + H.toCamelCase(userM.getFirstName() + " " + userM.getMiddleName() + " " + userM.getLastName()));

            if (userM.getGender() != null)
                if (userM.getGender().equals("M"))
                    genderTV.setText(H.toCamelCase("Gender: Male"));
                else genderTV.setText(H.toCamelCase("Gender: Female"));
            if (userM.getId() != null)
                idTV.setText(H.toCamelCase("ID: " + userM.getId()));
            if (userM.getGradeName() != null)
                gradeTV.setText(H.toCamelCase("PO Grade: " + userM.getGradeName()));
            if (userM.getMobileNumber() != null)
                mobileTV.setText(H.toCamelCase("Mobile: " + userM.getMobileNumber()));
            if (userM.getPresentAddress() != null)
                addTV.setText(H.toCamelCase("Address: " + userM.getPresentAddress()));
        }
    }

    @OnClick(R.id.logoutBtn)
    void onClickLogoutBtn(View v) {
        SharedPreferences pref = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("rememberMe", false);
        editor.apply();

        Intent intent3 = new Intent(context, LoginActivity.class);
        intent3.putExtra(Global.fromWhere, Global.LogOut);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent3);
    }


    private String getName(@Nullable UserModel userModel) {

        String f, l, m, u, name = "Not Available";
        if (userModel != null) {
            if (userModel.getFirstName() != null)
                f = userModel.getFirstName();
            else f = "";
            if (userModel.getLastName() != null)
                l = userModel.getLastName();
            else l = "";
            if (userModel.getMiddleName() != null)
                m = userModel.getMiddleName();
            else m = "";
            if (userModel.getUsername() != null)
                u = userModel.getUsername();
            else u = "";


            name = f + m + l;
            if (name.equals(""))
                name = u;
        }
        if (name.equals(""))
            name = Global.getUser_Name(context);

        return H.toCamelCase(name);
    }
}
