/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FA on 01/02/2018.
 */

public class SharedPrefHelper {
    public static final String TAG = SharedPrefHelper.class.getName();
    /*evaluation*/
    public static final String subjectPref = "subjectPref";
    public static final String chapterPref = "chapterPref";

    public static boolean saveSubjectList(List<String> subjectList) {
        boolean flag = true;

        try {
            SharedPreferences sharedpreferences = A.getAppContext().getSharedPreferences(Global.evaluationPref,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            Gson gson = new Gson();
            String jsonRaw = gson.toJson(subjectList);
            editor.putString(subjectPref, jsonRaw);
            editor.commit();
        } catch (Exception e) {
            Log.d(TAG, "Exception", e);
            flag = false;
        }


        return flag;
    }

    public static ArrayList<String> getSubjectList() {
        boolean flag = true;
        ArrayList<String> subjectList = new ArrayList<>();
        try {
            SharedPreferences sharedpreferences = A.getAppContext().getSharedPreferences(Global.evaluationPref,
                    Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String jsonRaw = sharedpreferences.getString(subjectPref, "");
            subjectList = (ArrayList<String>) gson.fromJson(jsonRaw,
                    new TypeToken<ArrayList<String>>() {
                    }.getType());

        } catch (Exception e) {
            Log.d(TAG, "Exception", e);
        }

        return subjectList;
    }


}
