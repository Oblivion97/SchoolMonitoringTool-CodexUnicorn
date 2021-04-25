/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mir Ferdous on 02/08/2017.
 */

public class SQLiteDbHelperNative extends SQLiteOpenHelper {
    /**
     * #####################    ##########################
     * done with SQLiteOpenHelper Native Way
     */
    public static final int DATABASE_VERSION = 1;
    // public static final String dbName = Global.dbName;

    private static SQLiteDbHelperNative sqLiteDbHelperNative;

    private SQLiteDbHelperNative(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, null, DATABASE_VERSION);
    }

    /**
     * _______________ get singleton of SQLiteDbHelperNative _______________
     */
    public static SQLiteDbHelperNative getInstance(Context context, String dbName) {
        if (sqLiteDbHelperNative == null) {
            sqLiteDbHelperNative = new SQLiteDbHelperNative(context, dbName, null, DATABASE_VERSION);
        }
        return sqLiteDbHelperNative;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // db.execSQL(DB.schools_table_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
