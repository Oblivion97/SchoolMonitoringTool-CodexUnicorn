/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncMirOld;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Mir Ferdous on 13/08/2017.
 */

public class SyncHelper {
    public static List<String> getAllIDFromTable(Context cntx, SQLiteDatabase db, String table, String column) {
        /**this function for getting all ID from a table, user can use it to syn details of a teacherSync,schoolSync,student by
         * id Listner call.in that time user need all ID s in DB*/
        int rows;
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + column + " FROM " + table, null);
        rows = cursor.getCount();
        cursor.moveToFirst();
        if (rows > 0) {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndex(column)));
            }
        }
        return list;
    }


    public static boolean isUnique(List<String> list) {
        boolean unique = false;
        Set<String> set = new HashSet<>(list);
        if (set.size() == list.size())
            unique = true;
        return unique;
    }

    public static boolean isExistsListImplementaito(Context cntx, SQLiteDatabase db, String table, String column, String value) {
        /**value = cell value to search in db*/
        boolean found = false;
        List<String> list = SyncHelper.getAllIDFromTable(cntx, db, table, column);
        for (String x : list) {
            if (x.equals(value))
                found = true;
        }
        return found;
    }

}
