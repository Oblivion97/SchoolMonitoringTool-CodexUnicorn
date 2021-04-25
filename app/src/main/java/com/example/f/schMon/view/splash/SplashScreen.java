/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.login.LoginActivity;

/**
 * Created by ARK on 10/1/2017.
 */

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = SplashScreen.class.getName();
    private Intent intent;
    private RelativeLayout layoutTop, layoutLeft, layoutRight;
    private ImageView bckGrndIV;
    private Animation uptobottom, lefttoright, righttoleft;
    private long time;
    private boolean rememberMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        // singleImageForAllDevice();


        // animationSplash();

        time = 2*1000;    //time


        SharedPreferences sharedpreferences = getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        rememberMe = sharedpreferences.getBoolean("rememberMe", false);

        if (rememberMe == true) {
            intent = new Intent(this, DashboardActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
            intent.putExtra(Global.fromWhere, Global.StartOfApp);
        }


        // intent = new Intent(this, LoginActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();

    }


    void singleImageForAllDevice() {
          /* create a full screen window */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);

    /* adapt the image to the size of the display */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(), R.drawable.splash_bckgrnd), size.x, size.y, true);

    /* fill the background ImageView with the resized image */
        bckGrndIV = findViewById(R.id.bckgrndSplash);
        bckGrndIV.setImageBitmap(bmp);
    }

    private void animationSplash() {
        layoutTop = findViewById(R.id.layoutTop);
        layoutLeft = findViewById(R.id.layoutLeft);
        layoutRight = findViewById(R.id.layoutRight);

        uptobottom = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        layoutTop.setAnimation(uptobottom);

        lefttoright = AnimationUtils.loadAnimation(this, R.anim.lefttoright);
        layoutLeft.setAnimation(lefttoright);

        righttoleft = AnimationUtils.loadAnimation(this, R.anim.righttoleft);
        layoutRight.setAnimation(righttoleft);
    }

}
