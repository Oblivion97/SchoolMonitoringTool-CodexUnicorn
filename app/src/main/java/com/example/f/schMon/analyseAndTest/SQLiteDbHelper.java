/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.compat.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Mir Ferdous on 05/06/2017.
 */
public class SQLiteDbHelper extends SQLiteOpenHelper {
    /**
     * #####################    ##########################
     * my implementation of copy db from asset folder
     */

    /* file extension of a database is .db and that
     * my databases are in assets/databases/
     * it will be copied from this location to main DB location of app if
     * no in this name is already existed
    * */
    final static String packageName = BuildConfig.APPLICATION_ID;
    private static SQLiteDbHelper sqLiteDbHelperInstance;
    //final static String DB_PATH = "/data/data/" + packageName + "/databases/";
    private String DB_PATH;
    private String dbName;
    private Context context;
    private File dbFile;

    //===================== Constructor & Singleton =============================
    private SQLiteDbHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
        this.context = context;
        this.dbName = dbName;
        this.DB_PATH = context.getDatabasePath(dbName).getPath();
        dbFile = new File(DB_PATH + dbName);
        // use SQLiteDbHelper.getInstance(....) to get SQLiteDbHelper object1
    }


    //===================== Singleton =============================
    public static synchronized SQLiteDbHelper getInstance(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {

        if (sqLiteDbHelperInstance == null) {
            sqLiteDbHelperInstance = new SQLiteDbHelper(context, dbName, factory, version);
        }
        return sqLiteDbHelperInstance;
        /*
          Use the application context, which will ensure that you
          don't accidentally leak an Activity's context.
          See this article for more information: http://bit.ly/6LRzfx
         * 3rd param CursorFactory factory can be null
         */
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {

        if (!dbFile.exists()) {
            SQLiteDatabase db = super.getWritableDatabase();
            copyDataBase(db.getPath());
        }
        return super.getWritableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (!dbFile.exists()) {
            SQLiteDatabase db = super.getReadableDatabase();
            copyDataBase(db.getPath());
        }
        return super.getReadableDatabase();
    }


    //========================= Methods ===========================================

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void copyDataBase(String dbPath) {
        try {
            InputStream assestDB = context.getAssets().open("databases/" + dbName);

            OutputStream appDB = new FileOutputStream(dbPath, false);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = assestDB.read(buffer)) > 0) {
                appDB.write(buffer, 0, length);
            }

            appDB.flush();
            appDB.close();
            assestDB.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}