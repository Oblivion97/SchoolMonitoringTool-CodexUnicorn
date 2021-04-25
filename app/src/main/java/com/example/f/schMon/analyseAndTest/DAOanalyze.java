/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.SQLiteDbHelper;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.oldAPI.SchoolListModel;
import com.example.f.schMon.model.oldAPI.SchoolModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mir Ferdous on 02/08/2017.
 */

public class DAOanalyze {
    public static String TAG = DAOanalyze.class.getName();

    public static String getSchoolNameFromSchoolID(Context context, String dbName, String schID) {
        SQLiteDbHelper sqLiteDbHelper = SQLiteDbHelper.getInstance(context, dbName, null, 1);
        SQLiteDatabase db = sqLiteDbHelper.getReadableDatabase();
        String sql = "SELECT schName FROM schools WHERE schID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{schID});
        String schName;
        if (cursor != null) {
            cursor.moveToFirst();
            schName = cursor.getString(cursor.getColumnIndex("schName"));
            cursor.close();
            return schName;
        } else
            return null;
    }


    /**
     * _______________________________________________________________________________
     */
    public static List<School> getSchoolListAll(SQLiteDatabase db) {
        School schTemp;
        String po_id = Global.user_id;
        String sql = "SELECT schID,schName FROM schools";
        Cursor cursor = db.rawQuery(sql, null);


        if (cursor != null) {
            ArrayList<School> schList = new ArrayList<>(cursor.getCount());


            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                schTemp = new School(cursor.getString(cursor.getColumnIndex("schID")),
                        cursor.getString(cursor.getColumnIndex("schName")));
                schList.add(schTemp);
                while (cursor.moveToNext()) {
                    schTemp = new School(cursor.getString(cursor.getColumnIndex("schID")),
                            cursor.getString(cursor.getColumnIndex("schName")));
                    schList.add(schTemp);
                }
                cursor.close();
            }
            return schList;
        } else
            return null;
    }

    public static int insertSchoolInfo(SQLiteDatabase db) {
        int noOfModels = 10;
        for (int i = 0; i < noOfModels; i++) {
            School sch = new School(
                    "id-1546-55fe-664dsf6",
                    "name-bosila",
                    "poID-ds16616",
                    "20",
                    "primary",
                    "code-55d-sdfsddfsddsf"
            );

            String sql =
                    "INSERT INTO " +
                            "schools (schID,schName,poID,totalStd,edu_type,schCode)" +
                            " VALUES(?,?,?,?,?,?)";
            db.execSQL(sql, new String[]{
                    sch.schID, sch.schName, sch.poID, sch.totalStd, sch.edu_type, sch.schCode});

/*
                    ContentValues cv = new ContentValues();
                    cv.put("schID", sch.schID);
                    cv.put("schName", sch.schName);
                    cv.put("poID", sch.poID);
                    db.insert("schools", null, cv);*/

            Log.d(TAG, db.getPath());

        }
        return 0;
    }

    public static Object[] syncSchoolTableOfflineDataTest(Context context, String json, SQLiteDatabase db) {
        int noOfModels;
        Object[] objects = new Object[3];
        int countHTML;

        SchoolListModel schoolListModel;
        ArrayList<SchoolModel> schoolModel;
        Gson gson = new Gson();


        // Log.d(TAG,json);
        if (json == null) {
            Log.d(TAG, "null");
            // json = callApiGET();
        } else {
            if (H.isHTMLresponse(json)) {
                Log.d(TAG, "HTML response");
                countHTML = 1;
                //  json = callApiGET();
            } else {
                schoolListModel = gson.fromJson(json, SchoolListModel.class);
                // Log.d(TAG, schoolListModel.toString());   //response json in log
                countHTML = -1;

                objects[0] = countHTML;
                objects[1] = schoolListModel;
                schoolModel = (ArrayList<SchoolModel>) schoolListModel.getModel();
                //Log.d(TAG, String.valueOf(schoolModel.size()));


                String po_id = Global.getUser_id(context);
                // poID="sdf";
                /**insert in db*/

                noOfModels = schoolModel.size();
                for (int i = 0; i < noOfModels; i++) {
                    School sch = new School(
                            schoolModel.get(i).getId(),
                            schoolModel.get(i).getName(),
                            po_id,
                            schoolModel.get(i).getTotalStudent(),
                            schoolModel.get(i).getEducationTypeName(),
                            schoolModel.get(i).getCode()
                    );
                    Log.d(TAG, sch.toString());
                    Log.d(TAG, db.getPath());


                    String sql =
                            "INSERT INTO " +
                                    "schools (schID,schName,poID,totalStd,edu_type,schCode)" +
                                    " VALUES(?,?,?,?,?,?)";
                    db.execSQL(sql, new String[]{
                            sch.schID, sch.schName, sch.poID, sch.totalStd, sch.edu_type, sch.schCode});

/*
                    ContentValues cv = new ContentValues();
                    cv.put("schID", sch.schID);
                    cv.put("schName", sch.schName);
                    cv.put("poID", sch.poID);
                    db.insert("schools", null, cv);*/

                    // Log.d(TAG, db.getPath());
                }


            }
        }
        return objects;
        /**
         * ___objects[0]___

         return objects[0] = 0 means null response
         return objects[0] = -1 means successfull json response
         return objects[0] any other positive number means occurrence of Html response

         *___objects[1]___

         objects[1]=SchoolModel
         */
    }
}
