/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.google.gson.Gson;

/**
 * Created by Mir Ferdous on 05/12/2017.
 */

public class LastSynced {
    /*this class to persist last synced time*/
    public static String preferenceLastSync;
    private String all;
    private String report;
    private String ques;
    private String schTchStd;


    //===========methods=============================================================================================================

    public static void syncTimeSave(@Nullable String rep, @Nullable String ques, @Nullable String schTechStd,
                                    @Nullable String all, @Nullable String repOld, Activity activity) {


        /************************************************** ************         * */


        //  not done in this fuction there is problem fix before use



       /*input one parameter at a time set other parameter null*/

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPref.edit();


        LastSynced lastSynced = new LastSynced();
        lastSynced = LastSynced.getLastSyncedTimeFromPref();
        if (rep != null)
            e.putString(Global.timeAll, rep);
        if (ques != null)
            e.putString(Global.timeAll, ques);
        if (schTechStd != null)
            e.putString(Global.timeAll, all);
        if (all != null)
            e.putString(Global.timeAll, all);


        e.commit();
        //LastSynced.saveLastSyncedTimeToPref(lastSynced);
    }

    public static LastSynced getLastSyncedTimeFromPref() {
        Gson g = new Gson();
        SharedPreferences sp = A.getAppContext().getSharedPreferences(Global.preference, Context.MODE_PRIVATE);

        //when no share pre..........................
        LastSynced ls = new LastSynced();
        ls = LastSynced.nullRemove(ls);
        String jsonWhenNothingSaved = g.toJson(ls, LastSynced.class);
        //...........................................

        String json = sp.getString(LastSynced.preferenceLastSync, jsonWhenNothingSaved);
        LastSynced lastSynced = g.fromJson(json, LastSynced.class);
        return lastSynced;
    }

    public static void saveLastSyncedTimeToPref(LastSynced lastSynced) {
        lastSynced = LastSynced.nullRemove(lastSynced);
        Gson g = new Gson();
        String json = g.toJson(lastSynced, LastSynced.class);
        SharedPreferences sp = A.getAppContext().getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sp.edit();
        prefsEditor.putString(LastSynced.preferenceLastSync, json);
        prefsEditor.commit();
    }

    public static LastSynced nullRemove(LastSynced lastSynced) {
        if (lastSynced.getAll() == null)
            lastSynced.setAll("");
        if (lastSynced.getReport() == null)
            lastSynced.setReport("");
        if (lastSynced.getQues() == null)
            lastSynced.setQues("");
        if (lastSynced.getSchTchStd() == null)
            lastSynced.setSchTchStd("");
        return lastSynced;
    }

    //===============getter setter & constructor to string ==========================================================================================================

    @Override
    public String toString() {
        return "LastSynced{" +
                "all='" + all + '\'' +
                ", report='" + report + '\'' +
                ", ques='" + ques + '\'' +
                ", schTchStd='" + schTchStd + '\'' +
                '}';
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getSchTchStd() {
        return schTchStd;
    }

    public void setSchTchStd(String schTchStd) {
        this.schTchStd = schTchStd;
    }
}
