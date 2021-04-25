/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncMirOld;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.controller.webservice.WSManager;
import com.example.f.schMon.controller.webservice.WSResponseListener;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.appInner.Teacher;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.view.sync_mir.API;
import com.example.f.schMon.model.oldAPI.SchoolListModel;
import com.example.f.schMon.model.oldAPI.SchoolModel;
import com.example.f.schMon.model.oldAPI.TeacherListModel;
import com.example.f.schMon.model.oldAPI.TeacherModel;
import com.example.f.schMon.model.oldAPI.TeacherModel_id;
import com.google.gson.Gson;

import org.apache.http.entity.StringEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mir Ferdous on 21/07/2017.
 */

public class SyncOld {
    private final static String TAG = SyncOld.class.getName();
    private static String response;

    //____________________________Common_______________________________________
    public static String callApiGET(Context context, String url) {
        WSManager wsManager;
        WSResponseListener wsResponseListener = new WSResponseListener() {
            @Override
            public void success(String response, String url) {
                SyncOld.response = response;
            }

            @Override
            public void failure(int statusCode, Throwable error, String content) {
                response = null;
            }
        };
        wsManager = new WSManager(context, wsResponseListener);
        wsManager.get(url);
        return response;
    }

    /**
     * _____________Login Api Call for using before calling other api when remember me is active
     */
    public static String[] callLoginAPI() {
        Context context = A.getAppContext();
        WSManager wsManager;
        final String[] responseUser = new String[2];
        User user;
        StringEntity entity;
        WSResponseListener wsResponseListener;
        Gson mGson = new Gson();

        SharedPreferences sharedpreferences = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        user = new User(sharedpreferences.getString("userName", ""),
                sharedpreferences.getString("password", ""));

        // user = new User("kholilur", "123456");

        wsResponseListener = new WSResponseListener() {
            @Override
            public void success(String response, String url) {
                responseUser[0] = response;
            }

            @Override
            public void failure(int statusCode, Throwable error, String content) {
                responseUser[1] = content;
            }
        };

        wsManager = new WSManager(context, wsResponseListener);
        entity = new StringEntity(mGson.toJson(user), "UTF-8");
        wsManager.post(API.urlLgin, entity);
        return responseUser;
        /**
         * responseUser[0]=success
         * responseUser[1]=fail
         * */
    }


    /**
     * ________________School______________________
     */
    public static Object[] syncSchoolTableAll(Context context, SQLiteDatabase db, String url) {
        /**this will sync all the schoolSync by running syncSchoolTableTen() function in loop*/
        String oldStart, newStart;
        Object[] objects = new Object[3];
        int countHTML = 0;
        int noOfSchools;
        SchoolListModel schoolListModel = null;
        boolean s = false;
        String json;
        callLoginAPI();
        json = callApiGET(context, url);

        if (json == null) {
            Log.d(TAG, "null");
        } else {
            if (H.isHTMLresponse(json)) {
                Log.d(TAG, "HTML response");
                countHTML = 1;
            } else {
                Gson gson = new Gson();
                schoolListModel = gson.fromJson(json, SchoolListModel.class);
                objects[0] = countHTML;
                objects[1] = schoolListModel;

                noOfSchools = Integer.parseInt(schoolListModel.getTotal());
                int loop = noOfSchools / 10;//since we get 10 schoolSync in single api call
                for (int i = 0; i <= loop; i++) {
                    oldStart = "start=0";
                    newStart = "start=" + i;
                    url = API.urlSchoolListPowise.replaceAll(oldStart, newStart);
                    syncSchoolTableTen(context, db, url);
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

    public static Object[] syncSchoolTableTen(Context context, SQLiteDatabase db, String url) {
        int noOfModels;
        Object[] objects = new Object[3];
        int countHTML;
        String json;
        SchoolListModel schoolListModel;
        ArrayList<SchoolModel> schoolModel;
        Gson gson = new Gson();
        //callLoginAPI();
        json = callApiGET(context, url);

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
                /*SQLiteDbHelper sqLiteDbHelper = SQLiteDbHelper.getInstance(A.getAppContext(), Global.dbName, null, 1);
                SQLiteDatabase db = sqLiteDbHelper.getWritableDatabase();*/
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


    /**
     * ________________Teacher______________________
     */

    public static Object[] syncTeacherTableAll(Context context, SQLiteDatabase db) {
        /**this will sync all the teachers by running syncTeacherTableTen() function in loop*/
        String oldStart, newStart;
        Object[] objects = new Object[3];
        int countHTML = 0;
        int noOfTeachers;
        //SchoolListModel schoolListModel = null;
        TeacherListModel teacherListModel = null;
        boolean s = false;
        String json;
        callLoginAPI();
        String url = API.urlTeacherListPowise;
        json = callApiGET(context, url);

        if (json == null) {
            Log.d(TAG, "null");
        } else {
            if (H.isHTMLresponse(json)) {
                Log.d(TAG, "HTML response");
                countHTML = 1;
            } else {
                // Log.d(TAG, json);
                Gson gson = new Gson();

                teacherListModel = gson.fromJson(json, TeacherListModel.class);
                objects[0] = countHTML;
                objects[1] = teacherListModel;

                noOfTeachers = Integer.parseInt(teacherListModel.getTotal());
                int loop = noOfTeachers / 10;//since we get 10 teachers in single api call
                for (int i = 0; i <= loop; i++) {
                   /* oldStart = "start=0";
                    newStart = "start=" + i;
                    url = API.urlTeacherListPowise.replaceAll(oldStart, newStart);*/

                    url = "http://bepmis.brac.net/rest/teacherSync/listAllTeacher?bkashNumber=&dropOut=&email=&instituteId=&locationHierarchy=&locationId=&max=10&mobileNumber=&start=" +
                            i + "&userCategoryUdvId=Teacher&username=";
                    syncTeacherTableTen(context, db, url);
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

         objects[1]=RepModel
         */
    }

    public static Object[] syncTeacherTableTen(Context context, SQLiteDatabase db, String url) {
        int noOfModels;
        Object[] objects = new Object[3];
        int countHTML;
        String json, jsonDetail;
        TeacherListModel teacherListModel;
        TeacherModel_id teacherModel_id;
        ArrayList<TeacherModel> teacherModels;
        Gson gson = new Gson();
        // callLoginAPI();
        json = callApiGET(context, url);

        if (json == null) {
            Log.d(TAG, "null");
            // json = callApiGET();
        } else {
            if (H.isHTMLresponse(json)) {
                Log.d(TAG, "HTML response");
                countHTML = 1;
                //  json = callApiGET();
            } else {
                teacherListModel = gson.fromJson(json, TeacherListModel.class);
                // Log.d(TAG, schoolListModel.toString());   //response json in log
                countHTML = -1;

                objects[0] = countHTML;
                objects[1] = teacherListModel;
                teacherModels = (ArrayList<TeacherModel>) teacherListModel.getModel();


                Gson gson1 = new Gson();

                //  String poID = Global.getUser_id(context);

                /**insert in db*/

                String id;
                /**for checking dulicate entry*/
                List<String> ids = SyncHelper.getAllIDFromTable(context, db, "teachers", "t_id");


                noOfModels = teacherModels.size();
                for (int i = 0; i < noOfModels; i++) {
                    id = teacherModels.get(i).getId();
                    if (!ids.contains(id)) {


                        /**details call*/
                        //Sync.updateATeachersAllInfos(context, db, id);

                        jsonDetail = SyncOld.callApiGET(context, API.urlTeacherby_id + id);
                        teacherModel_id = gson1.fromJson(jsonDetail, TeacherModel_id.class);
                        Log.d(TAG, "teacherSync details : \n" + jsonDetail);


                        /***/
                        Teacher techr = new Teacher(
                                teacherModels.get(i).getId(),
                                teacherModels.get(i).getMobileNumber(),
                                teacherModels.get(i).getGradeName(),
                                teacherModels.get(i).getInstituteName());


                        String sql =
                                "INSERT INTO " +
                                        "teachers (t_id,mobile,grade,schName)" +
                                        " VALUES(?,?,?,?)";
                        db.execSQL(sql, new String[]{
                                techr.tID, techr.tMobi, techr.techrGrade, techr.schName});


                    }
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

    public static Object[] updateATeachersAllInfos(Context context, SQLiteDatabase db) {
        Object[] objects = new Object[3];
        Gson gson = new Gson();
        TeacherModel_id teacherModel_id = null;
        String json = null, id;
        int countHTML;
        List<String> ids = SyncHelper.getAllIDFromTable(context, db, "teachers", "t_id");
        int no_of_techr = ids.size();
        callLoginAPI();
        id = ids.get(0);
        json = callApiGET(context, API.urlTeacherby_id + id);


        if (json == null) {
            Log.d(TAG, "null");
            // json = callApiGET();
        } else {
            if (H.isHTMLresponse(json)) {
                Log.d(TAG, "HTML response");
                countHTML = 1;
            } else {


                /**all id of teachers from db*/


                for (int i = 0; i < no_of_techr; i++) {
                    id = ids.get(i);
                    json = callApiGET(context, API.urlTeacherby_id + id);
                    teacherModel_id = gson.fromJson(json, TeacherModel_id.class);
                    ContentValues cv = new ContentValues();
                    cv.put("name", teacherModel_id.getFirstName() + " " + teacherModel_id.getMiddleName() + " " + teacherModel_id.getLastName());
                    cv.put("schID", teacherModel_id.getInstituteId());
                    cv.put("address", teacherModel_id.getPresentAddress());


                    String selection = "t_id=?";
                    id = ids.get(i);
                    String[] selectionArg = new String[]{id};
                    db.update("teachers", cv, selection, selectionArg);
                    Log.d(TAG, "teacherSync by id : " + id + "id\n" + teacherModel_id.toString());
                    // String sql = "UPDATE teachers SET name = ?, schID = ?, address = ? WHERE t_id = ?";
                }
            }
        }

        objects[1] = no_of_techr;
        return objects;
    }

    /**
     * ____________________duplicate insert check(already exists or not)_________________________
     */

    public static boolean idExists(SQLiteDatabase db, String table, String columnName, String id) {
        //String[] selectionArg = new String[]{columnName, table, columnName, id};
        String sql = "SELECT " + columnName + " FROM " + table + " WHERE " + columnName + " = " + id;
        sql = "select t_id from teachers where t_id = " + id;
        //  Cursor cursor = db.rawQuery(sql, null);

        String[] projection = new String[]{"t_id"};
        String selection = "t_id = ?";
        String[] selectionArg = new String[]{id};
        Cursor cursor = db.query("teachers", projection, selection, selectionArg, null, null, null);


        int c = cursor.getCount();
        cursor.close();
        // Log.d(TAG, String.valueOf("cursor count for id:+" + id + c));
        return c > 0;
    }
}

