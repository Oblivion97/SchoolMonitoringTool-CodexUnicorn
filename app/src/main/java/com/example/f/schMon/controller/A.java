/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.controller;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;
import com.example.f.schMon.model.appInner.SQLiteDbHelper;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.report_environment.RepMdlin;

import io.fabric.sdk.android.Fabric;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mir Ferdous on 11/06/2017.
 */


public class A extends Application {
    /**
     * this is global class
     */
    private static Context context;
    private static A instance;
    private String TAG = this.getClass().getName();
    private SQLiteDbHelper sqLiteDbHelper;
    private int ver = 1;
    public static int listPosition;
    public static List<EvlModelin> evlList = new ArrayList<>();
    public static List<RepMdlin> repList = new ArrayList<>();
    private static User user;
    private static String u;
    private static String p;
    private static int indexRepTargetDate;


    //================== activity intent alternative =======================================================================================================
    public static String toOpenWhichActivity;
    public static String openMode;


    //================ download start ===================
    private static long dwnld = 0;

    public static long getDwnld() {
        return dwnld;
    }

    public static void resetDownloadCounter() {
        dwnld = 0;
    }

    public static void calcDwnload(long bytes) {
        // dwnld = dwnld * 1024 ;
        dwnld += bytes;
        //to mb
        //  dwnld = (dwnld / 1024) ;
    }
    //================ download end ===================

    public static int getIndexRepTargetDate() {
        return indexRepTargetDate;
    }

    public static void setIndexRepTargetDate(int indexRepTargetDate) {
        A.indexRepTargetDate = indexRepTargetDate;
    }

    public static String getU() {
        if (u == null)
            u = Global.getUserCredentialFromSharePreference(getAppContext()).getUsername();
        return u;
    }

    public static String getP() {
        if (p == null)
            p = Global.getUserCredentialFromSharePreference(getAppContext()).getPassword();
        return p;
    }

    public static User getUser() {
        if (user == null) {
            user = Global.getUserCredentialFromSharePreference(getAppContext());
            if (user == null)
                user = new User("", "");
        }
        return user;
    }

    public static Context getAppContext() {
        return getInstance().context;
    }

    public static A getInstance() {
        if (instance == null) {
            instance = new A();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = getApplicationContext();

        Global.getDB_with_AssetHelper_readable(Global.dbName);
        /**db copy from asset*/

        //this.instance = this;
        // this.sqLiteDbHelper = SQLiteDbHelper.getInstance(getApplicationContext(), dbName, null, ver);
    }

    //============= global database variable ===================
    public SQLiteDatabase getDatabase() {
        return sqLiteDbHelper.getWritableDatabase();
    }

    public static int getListPosition() {
        return listPosition;
    }

    public static void setListPosition(int mlistPosition) {
        listPosition = mlistPosition;
    }

    //========================= getter setters =================================


    public static List<EvlModelin> getEvlList() {
        return evlList;
    }

    public static void setEvlList(List<EvlModelin> mEvlList) {
        evlList.clear();
        evlList.addAll(mEvlList);
    }

    public static List<RepMdlin> getRepList() {
        return repList;
    }

    public static void setRepList(List<RepMdlin> mRepList) {
        repList.clear();
        repList.addAll(mRepList);
    }

    //=========================== sync ==============================================================================================
    public static String syncWhat;
    public static int syncMsgID = -1;
    public static Thread syncThread;
    //=================================== for data downld calc ======================================================================================
    //this are in byte
    private static long uploadSize;
    private static long downloadSize;
    private static long totalDwnldAndUpld;

    public static long get_Total_Dwnld_And_Upld() {
        totalDwnldAndUpld = uploadSize + downloadSize;
        long temp = totalDwnldAndUpld;
        totalDwnldAndUpld = 0;
        return temp;
    }

    public static long getUploadSize() {
        long temp = uploadSize;
        uploadSize = 0;
        return temp;
    }

    public static void setUploadSize(long uploadSize) {
        A.uploadSize = uploadSize;
    }

    public static long getDownloadSize() {
        long temp = downloadSize;
        downloadSize = 0;
        return temp;
    }

    public static void setDownloadSize(long downloadSize) {
        A.downloadSize = downloadSize;
    }

    //=================================== for evaluation temporary data ======================================================================================

    public static EvlModelin evlModelin = null;

    public static EvlModelin getEvlModelin() {
        return evlModelin;
    }

    public static void setEvlModelin(EvlModelin evlModelin) {
        A.evlModelin = evlModelin;
    }
}


