/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.view.dashboard.DashboardActivity;

public class StartingActivity extends AppCompatActivity {
    boolean rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        SharedPreferences sharedpreferences = getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        rememberMe = sharedpreferences.getBoolean("rememberMe", false);

        if (rememberMe == true) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
        if (rememberMe == false) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


        finish();
    }
}
