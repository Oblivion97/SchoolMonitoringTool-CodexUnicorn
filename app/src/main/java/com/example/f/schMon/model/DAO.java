/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.FeeSalaryModel;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.SDCardManager;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.appInner.Student;
import com.example.f.schMon.model.appInner.Teacher;
import com.example.f.schMon.model.newAPI.SchMdl;
import com.example.f.schMon.model.newAPI.StdMdl;
import com.example.f.schMon.model.newAPI.TchrMdl;
import com.example.f.schMon.model.oldAPI.SchoolModel;
import com.example.f.schMon.model.oldAPI.StudentModel;
import com.example.f.schMon.model.oldAPI.StudentModel_id;
import com.example.f.schMon.model.oldAPI.TeacherModel;
import com.example.f.schMon.model.oldAPI.TeacherModel_id;
import com.example.f.schMon.view.evaluation.EvalPerfModel;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.log_edit.RepPerf;
import com.example.f.schMon.view.report_environment.RepMdlin;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlA;
import com.example.f.schMon.view.sync_mir.MdlSync.RepA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mir Ferdous on 04/06/2017
 */


public class DAO {
    private static String TAG = DAO.class.getName();
    private static SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    /*============================== DB Management =======================================================================================*/

    public static int copyDataBase(String dbPathSrc, String dbPathDes, SQLiteDatabase db) {
        String dbName = Global.dbName;

        int success = 0;
        try {
            if (dbPathSrc == null) {
               /*
                  dbPathSrc = "/data/data/com.example.f.schoollist1/databases/schoolMonDB.db";
                  dbPathSrc = File.separator + "data" +
                        File.separator + "data" +
                        File.separator + BuildConfig.APPLICATION_ID +
                        File.separator + "databases" +
                        File.separator + dbName;*/

                dbPathSrc = "/data/data/" + Global.packageName + "/databases/" + dbName;
                dbPathSrc = db.getPath();
            }
            Log.d(TAG, "db is ---------- " + dbPathSrc);
            if (dbPathDes == null)
                dbPathDes = SDCardManager.getSdCardPath();

            InputStream is = new FileInputStream(new File(dbPathSrc));

            OutputStream os = new FileOutputStream(new File(dbPathDes + dbName));

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

            success = 1;
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static int deleteDataFromTable(SQLiteDatabase db, String tableName) {
        int success;
        success = db.delete(tableName, "1", null);
        return success;//returns no of deleted rows
    }

    //table exist or not
    public static boolean isTableExists(SQLiteDatabase db, String table) {
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + table + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    // data in table(table name in parameter) exist or not
    public static boolean isTableEmpty(SQLiteDatabase db, String table) {
        boolean flag = true;
        db = Global.getDB_with_nativeWay_writable(Global.dbName);
        if (DAO.isTableExists(db, table)) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + table, null);
            if (cursor != null) {
                flag = !cursor.moveToFirst();
                cursor.close();
            }
        }
        return flag;
    }

    /*=====================================================================================================================*/
    //get IDs of a table
    public static List<String> all_values_from_a_Column(SQLiteDatabase db, String tableName, String column) {
    /* all values(not unique) of a column of any table in DB,*/
        List<String> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT " + column + " FROM " + tableName, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(c.getColumnIndex(column)));
                } while (c.moveToNext());
            }
        }
        if (c != null)
            c.close();
        return list;
    }

    public static boolean updateSingleColumnData(String table, String columnm, String data,
                                                 String where, String[] whereArg) {
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        long count;
        ContentValues cv = new ContentValues();
        cv.put(columnm, data);

        count = db.update(table, cv, where, whereArg);

        if (count >= 0) {
            flag = true;
        } else flag = false;

        return flag;
    }

    /*===================================== School Infos ================================================================================*/

    public static String getSchoolNameFromSchoolID(String schID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String sql = "SELECT schName FROM schools WHERE schID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{schID});
        String schName;
        if (cursor != null) {
            cursor.moveToFirst();
            schName = cursor.getString(cursor.getColumnIndex("schName"));
            if (cursor != null)
                cursor.close();
            return schName;
        } else
            return null;
    }

    public static List<School> getInfoOfAllSchool() {/*info of all school*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        School s;
        ArrayList<School> schList = new ArrayList<>();
        Cursor c;


        /*String[] proj = new String[]{DB.schools.schID, DB.schools.grade_id, DB.schools.schName, DB.schools.edu_type,
                DB.schools.schCode, DB.schools.grade, DB.schools.totalStd};*/

        c = db.query(DB.schools.table, null, null, null, null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    s = new School();
                    s.setSchID(c.getString(c.getColumnIndex(DB.schools.s_id)));
                    s.setSchName(c.getString(c.getColumnIndex(DB.schools.s_name)));
                    s.setEdu_type(c.getString(c.getColumnIndex(DB.schools.edu_type)));
                    s.setSchCode(c.getString(c.getColumnIndex(DB.schools.s_code)));
                    s.setGrade(c.getString(c.getColumnIndex(DB.schools.grade)));
                    s.setGradeID(c.getString(c.getColumnIndex(DB.schools.grade_id)));
                    s.setTotalStd(c.getString(c.getColumnIndex(DB.schools.totalStd)));
                    s.setLast_visit_time(c.getString(c.getColumnIndex(DB.schools.last_visit_time)));


                    schList.add(s);
                } while (c.moveToNext());


                if (c != null)
                    c.close();
            }
        }
        return schList;
    }

    @NonNull
    public static School getInfoOfASchool(String schID) {/*info of a school by school id*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        School s = new School();
        s.removeNull();
        Cursor c = null;

        String sel = DB.schools.s_id + "=?";
        String[] selA = new String[]{schID};
        c = db.query(DB.schools.table, null, sel, selA, null, null, null, null);

        if (c.moveToFirst()) {
            s = new School();
            s.set_id(c.getInt(c.getColumnIndex(DB.schools._ID)));
            s.setSchID(c.getString(c.getColumnIndex(DB.schools.s_id)));
            s.setSchName(c.getString(c.getColumnIndex(DB.schools.s_name)));
            s.setTotalStd(c.getString(c.getColumnIndex(DB.schools.totalStd)));
            s.setEdu_type(c.getString(c.getColumnIndex(DB.schools.edu_type)));
            s.setSchCode(c.getString(c.getColumnIndex(DB.schools.s_code)));
            s.setGrade(c.getString(c.getColumnIndex(DB.schools.grade)));
            s.setGradeID(c.getString(c.getColumnIndex(DB.schools.grade_id)));
            s.setS_address(c.getString(c.getColumnIndex(DB.schools.s_address)));
            s.setTime_lm(c.getString(c.getColumnIndex(DB.schools.time_lm)));
            s.setTotal_bill(c.getString(c.getColumnIndex(DB.schools.total_bill)));
            s.setPaid(c.getString(c.getColumnIndex(DB.schools.paid)));
            s.setLast_visit_time(c.getString(c.getColumnIndex(DB.schools.last_visit_time)));
        }

        if (c != null)
            c.close();

        return s;
    }

    public static List<School> getAllInfoOfAllSchool() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        School s;
        ArrayList<School> schList = new ArrayList<>();
        Cursor c;

        c = db.query(DB.schools.table, null, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    s = new School();
                    s.set_id(c.getInt(c.getColumnIndex(DB.schools._ID)));
                    s.setSchID(c.getString(c.getColumnIndex(DB.schools.s_id)));
                    s.setGradeID(c.getString(c.getColumnIndex(DB.schools.grade_id)));
                    s.setGrade(c.getString(c.getColumnIndex(DB.schools.grade)));
                    s.setSchName(c.getString(c.getColumnIndex(DB.schools.s_name)));
                    s.setPoId(c.getString(c.getColumnIndex(DB.schools.po_id)));
                    s.setPoName(c.getString(c.getColumnIndex(DB.schools.po_name)));
                    s.setS_address(c.getString(c.getColumnIndex(DB.schools.s_address)));
                    s.setTime_lm(c.getString(c.getColumnIndex(DB.schools.time_lm)));
                    s.setTotalStd(c.getString(c.getColumnIndex(DB.schools.totalStd)));
                    s.setEdu_type(c.getString(c.getColumnIndex(DB.schools.edu_type)));
                    s.setSchCode(c.getString(c.getColumnIndex(DB.schools.s_code)));
                    s.setTotal_bill(c.getString(c.getColumnIndex(DB.schools.total_bill)));
                    s.setPaid(c.getString(c.getColumnIndex(DB.schools.paid)));
                    s.setSessionStart(c.getString(c.getColumnIndex(DB.schools.session_start)));
                    s.setSessionEnd(c.getString(c.getColumnIndex(DB.schools.session_end)));
                    s.setLast_visit_time(c.getString(c.getColumnIndex(DB.schools.last_visit_time)));

                    schList.add(s);
                }

                while (c.moveToNext());

            }
            if (c != null)
                c.close();

        }
        return schList;
    }

    public static boolean insertSchoolListBasics(Context context, SQLiteDatabase db, List<SchoolModel> schoolModels) {
        boolean flag = true;
        School sch;
        String po_id = Global.getUser_id(context);
        int noOfModels = schoolModels.size();
        for (int i = 0; i < noOfModels; i++) {
            sch = new School(
                    schoolModels.get(i).getId(),
                    schoolModels.get(i).getName(),
                    po_id,
                    schoolModels.get(i).getTotalStudent(),
                    schoolModels.get(i).getEducationTypeName(),
                    schoolModels.get(i).getCode()
            );

            String sql =
                    "INSERT OR IGNORE INTO " +
                            "schools (schID,schName,poID,totalStd,edu_type,schCode)" +
                            " VALUES(?,?,?,?,?,?)";
            try {
                db.execSQL(sql, new String[]{
                        sch.schID, sch.schName, sch.poID, sch.totalStd, sch.edu_type, sch.schCode});
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    public static boolean insertLastVisitTimeSchool(String schID, Date date) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        boolean f = false;
        long count;
        String where = DB.schools.s_id + " = ? ";
        String[] whreArg = new String[]{schID};
        ContentValues cv = new ContentValues();
        String time = new SimpleDateFormat("dd-MM-yyyy").format(date);
        cv.put(DB.schools.last_visit_time, time);
        count = db.update(DB.schools.table, cv, where, whreArg);
        if (count > 0)
            f = true;
        return f;
    }

    public static Map<String, String> getLastVisitTimeAllSchool() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        HashMap<String, String> lastVisitList = new HashMap<>();
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Cursor c = null;
        c = db.query(DB.schools.table, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                String schID = c.getString(c.getColumnIndex(DB.schools.s_id));
                String time = c.getString(c.getColumnIndex(DB.schools.last_visit_time));
                if (time != null)
                    lastVisitList.put(schID, time);
            } while (c.moveToNext());
        }

        if (c != null)
            c.close();

        return lastVisitList;
    }

    public static int getTotalSchoolCount() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int totalSch = 0;
        Cursor c = null;

        try {
            c = db.query(DB.schools.table, null, null, null, null, null, null);
            totalSch = c.getCount();
        } catch (Exception e) {
            Log.d(TAG, "Exception School count: ", e);
        } finally {
            if (c != null)
                c.close();
        }

        return totalSch;
    }


    public static int[] getTotalVisitedSchoolByTotalSchoolThisMonth() {//get no of total schools visited this month
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat df7 = new SimpleDateFormat("MM");
        int totalSch = 0;
        int totalSchVisited = 0;
        int[] visitAndSch = new int[2];
        visitAndSch[0] = totalSchVisited;
        visitAndSch[1] = totalSch;

        Cursor c = null;

        Calendar currentTime = Calendar.getInstance();

        c = db.query(DB.schools.table, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                String schID = c.getString(c.getColumnIndex(DB.schools.s_id));
                if (schID != null)
                    totalSch++;
                try {

                    if (c.getString(c.getColumnIndex(DB.schools.last_visit_time)) != null) {
                        Calendar visitTime = Calendar.getInstance();
                        visitTime.setTime(df1.parse(c.getString(c.getColumnIndex(DB.schools.last_visit_time))));
                        Log.d(TAG, String.valueOf(visitTime.get(Calendar.MONTH)));

                        if (visitTime.get(Calendar.MONTH) == currentTime.get(Calendar.MONTH))
                            totalSchVisited++;
                    }

                } catch (ParseException e) {
                    Log.d(TAG, "Exc", e);
                }

            } while (c.moveToNext());

        }
        visitAndSch[0] = totalSchVisited;
        totalSch = c.getCount();
        visitAndSch[1] = totalSch;

        if (c != null)
            c.close();

        return visitAndSch;
    }


    /*================================== Student =================================================================================*/

    public static Student[] getStudentListFor_A_School(String schID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        Student std;
        int i = 0;
        Cursor c;
        /*c = db.rawQuery("SELECT std_id,name,father,birthDate,guardian_phn,roll,gender FROM students WHERE schID=?",new String[]{schID});*/

        String sel = DB.students.s_id + " = ? ";
        String[] selArg = new String[]{schID};
        c = db.query(DB.students.table, null, sel, selArg, null, null, null, null);


        Student[] stdList = new Student[c.getCount()];
        if (c.moveToFirst()) {
            do {
                std = new Student(
                        c.getString(c.getColumnIndex(DB.students.std_id)),
                        c.getString(c.getColumnIndex(DB.students.roll)),
                        c.getString(c.getColumnIndex(DB.students.name)),
                        c.getString(c.getColumnIndex(DB.students.father)),
                        c.getString(c.getColumnIndex(DB.students.guardian_phn)),
                        c.getString(c.getColumnIndex(DB.students.gender)),
                        c.getString(c.getColumnIndex(DB.students.birthDate)));

                stdList[i] = std;
                i++;
            } while (c.moveToNext());

            if (c != null)
                c.close();
        }
        return stdList;
    }

    public static String getTotalStdBySchIDFromSchTable(Context context, SQLiteDatabase db, String schID) {
        Cursor c = null;
        String noOfStd = null;
        try {
            String sql = "SELECT " + DB.schools.totalStd + " FROM schools WHERE " + DB.schools.s_id + "=?";
            c = db.rawQuery(sql, new String[]{schID});

            noOfStd = null;
            if (c.moveToFirst()) {
                noOfStd = c.getString(c.getColumnIndex(DB.schools.totalStd));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
        return noOfStd;
    }

    public static boolean insertStudentbyListBasics(Context context, SQLiteDatabase db, List<StudentModel> studentModels) {
        boolean flag = true;
        int noOfModels = studentModels.size();
        for (int i = 0; i < noOfModels; i++) {
            String sql =
                    "INSERT OR IGNORE INTO " +
                            "students (std_id,institute,gender,dropout,guardian_phn,name)" +
                            " VALUES(?,?,?,?,?,?)";
            try {
                db.execSQL(sql, new String[]{studentModels.get(i).getId(),
                        studentModels.get(i).getInstituteId(),
                        studentModels.get(i).getSex(),
                        studentModels.get(i).getDropout(),
                        studentModels.get(i).getGuardianMobile(),
                        studentModels.get(i).getFullName(),
                });
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    public static boolean insertStudentbyIdDetails(Context context, SQLiteDatabase db, StudentModel_id stdModel) {
        boolean flag = false;
        ContentValues cv = new ContentValues();
        cv.put("schID", stdModel.getInstituteId());
        cv.put("roll", stdModel.getRoll());
        cv.put("waiver", stdModel.getWaiver());
        cv.put("father", stdModel.getFatherName());
        cv.put("bkash", stdModel.getBkashNumber());
        cv.put("dropout", stdModel.getDropout());
        cv.put("transferred_institute", stdModel.getNameOfTransferredSchool());
        cv.put("birthDate", "");
        int total_rowsUpdated = db.update("students", cv, "std_id= '" + stdModel.getId() + "'", null);
        if (total_rowsUpdated > 0)
            flag = true;
        return flag;
    }


    /* ================================== Teacher ==========================================================================================*/

    public static boolean insertTeachersbyListBasics(Context context, SQLiteDatabase db, List<TeacherModel> teacherModels) {
        boolean flag = true;
        int noOfModels = teacherModels.size();
        for (int i = 0; i < noOfModels; i++) {
            String sql =
                    "INSERT OR IGNORE INTO " +
                            "teachers (t_id,grade,mobile,schName)" +
                            " VALUES(?,?,?,?)";
            try {
                db.execSQL(sql, new String[]{
                        teacherModels.get(i).getId(),
                        teacherModels.get(i).getGradeName(),
                        teacherModels.get(i).getMobileNumber(),
                        teacherModels.get(i).getInstituteName(),
                });
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    public static boolean insertTeachersbyIdDetails(Context context, SQLiteDatabase db, TeacherModel_id techrModel) {
        boolean flag = false;
        ContentValues cv = new ContentValues();
        cv.put(DB.teachers.s_id, techrModel.getInstituteId());
        cv.put(DB.teachers.address, techrModel.getPermanentAddress());
        cv.put(DB.teachers.name, techrModel.getFirstName() + " " + techrModel.getLastName());
        int total_rowsUpdated = db.update(DB.teachers.table, cv, DB.teachers.t_id + "= '" + techrModel.getId() + "'", null);
        if (total_rowsUpdated > 0)
            flag = true;
        return flag;
    }

    @Nullable
    public static Teacher getTeachersAllInfoBySchID(String schID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String tName, tID, tMobi, techrGrade,
                techrAdrs, s_id, schName, address, total_bill, paid;

        Teacher t = null;
        Cursor c;
        String sql = "SELECT * FROM " + DB.teachers.table + " WHERE " + DB.teachers.s_id + "=?";
        c = db.rawQuery(sql, new String[]{schID});

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            t = new Teacher(
                    c.getString(c.getColumnIndex(DB.teachers.name)),
                    c.getString(c.getColumnIndex(DB.teachers.t_id)),
                    c.getString(c.getColumnIndex(DB.teachers.mobile)),
                    c.getString(c.getColumnIndex(DB.teachers.grade)),
                    c.getString(c.getColumnIndex(DB.teachers.s_id)),
                    c.getString(c.getColumnIndex(DB.teachers.s_name)),
                    c.getString(c.getColumnIndex(DB.teachers.address)),
                    c.getString(c.getColumnIndex(DB.teachers.total_bill)),
                    c.getString(c.getColumnIndex(DB.teachers.paid))
            );
            t.setEducation(c.getString(c.getColumnIndex(DB.teachers.education)));
            t.setGender(c.getString(c.getColumnIndex(DB.teachers.gender)));
            t.setTime_lm(c.getString(c.getColumnIndex(DB.teachers.time_lm)));
        }
        if (c != null)
            c.close();
        return t;
    }

    public static Teacher getTeacher4InfoBySchoolID(Context context, SQLiteDatabase db, String schID) {
        String sql = "SELECT name,mobile,grade,address FROM teachers WHERE schID=?";
        Cursor cursorT = db.rawQuery(sql, new String[]{schID});
        Teacher teacher = null;
        if (cursorT != null && cursorT.getCount() > 0) {
            cursorT.moveToFirst();
            teacher = new Teacher(
                    cursorT.getString(cursorT.getColumnIndex("name")),
                    cursorT.getString(cursorT.getColumnIndex("mobile")),
                    cursorT.getString(cursorT.getColumnIndex("grade")),
                    cursorT.getString(cursorT.getColumnIndex("address"))
            );
            cursorT.close();
            return teacher;
        }
        return null;
    }


    /*================================== Payment ==========================================================================================     */

    public static FeeSalaryModel[] getSchoolWiseFeeList() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        Cursor cursor;
        FeeSalaryModel feeSalaryModel;
        int i = 0;


       /* cursor = db.rawQuery("SELECT schID,schName,paid,total_bill FROM schools WHERE poID=?",
                new String[]{poID});*/

        String[] proj = new String[]{DB.schools.s_id, DB.schools.s_name, DB.schools.paid, DB.schools.total_bill};
        cursor = db.query(DB.schools.table, proj, null, null, null, null, null);


        FeeSalaryModel[] feeSalaryModels = new FeeSalaryModel[cursor.getCount()];

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                feeSalaryModels = new FeeSalaryModel[cursor.getCount()];
                cursor.moveToFirst();
                feeSalaryModel = new FeeSalaryModel(
                        cursor.getString(cursor.getColumnIndex("schID")),
                        cursor.getString(cursor.getColumnIndex("schName")),
                        cursor.getString(cursor.getColumnIndex("paid")),
                        cursor.getString(cursor.getColumnIndex("total_bill"))
                );
                feeSalaryModels[i] = feeSalaryModel;
                i++;
                while (cursor.moveToNext()) {
                    feeSalaryModel = new FeeSalaryModel(
                            cursor.getString(cursor.getColumnIndex("schID")),
                            cursor.getString(cursor.getColumnIndex("schName")),
                            cursor.getString(cursor.getColumnIndex("paid")),
                            cursor.getString(cursor.getColumnIndex("total_bill"))
                    );
                    feeSalaryModels[i] = feeSalaryModel;
                    i++;
                }
                cursor.close();
            }
            return feeSalaryModels;
        } else
            return null;
    }

    public static FeeSalaryModel[] getStudentWiseFeeListFor_A_School(String schID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        FeeSalaryModel feeSalaryModel;
        int i = 0;
        String sql = "SELECT name,paid,total_bill FROM students WHERE schID=?";
        Cursor cursor = db.rawQuery(sql,
                new String[]{schID});

        String schName = "";
        schName = DAO.getSchoolNameFromSchoolID(schID);

        //Log.d(TAG, String.valueOf(Global.getDB_with_AssetHelper_readable()));

        FeeSalaryModel[] feeSalaryModels = new FeeSalaryModel[cursor.getCount()];
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            feeSalaryModels = new FeeSalaryModel[cursor.getCount()];
            cursor.moveToFirst();
            feeSalaryModel = new FeeSalaryModel(
                    schID,
                    schName,
                    cursor.getString(cursor.getColumnIndex("paid")),
                    cursor.getString(cursor.getColumnIndex("total_bill")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    ""
            );
            feeSalaryModels[i] = feeSalaryModel;
            i++;
            while (cursor.moveToNext()) {
                feeSalaryModel = new FeeSalaryModel(
                        schID,
                        schName,
                        cursor.getString(cursor.getColumnIndex("paid")),
                        cursor.getString(cursor.getColumnIndex("total_bill")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        ""
                );
                feeSalaryModels[i] = feeSalaryModel;
                i++;
            }
            cursor.close();
            return feeSalaryModels;
        } else
            return null;
    }

    public static FeeSalaryModel[] getTeachersSalaryList() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        FeeSalaryModel[] feeSalaryModels;
        FeeSalaryModel feeSalaryModelTemp;
        int i = 0;
        Cursor c;

   /* cursor = db.rawQuery("SELECT schName,name,paid,total_bill FROM teachers", null);
*/


        String[] proj = new String[]{DB.teachers.name, DB.teachers.s_id, DB.teachers.s_name,
                DB.teachers.paid, DB.teachers.total_bill};
        c = db.query(DB.teachers.table, proj, null, null, null, null, null);


        String schID = "";


        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            feeSalaryModels = new FeeSalaryModel[c.getCount()];
            c.moveToFirst();
            feeSalaryModelTemp = new FeeSalaryModel(
                    c.getString(c.getColumnIndex(DB.teachers.s_id)),
                    c.getString(c.getColumnIndex(DB.teachers.s_name)),
                    c.getString(c.getColumnIndex(DB.teachers.paid)),
                    c.getString(c.getColumnIndex(DB.teachers.total_bill)),
                    c.getString(c.getColumnIndex(DB.teachers.name)),
                    ""
            );
            feeSalaryModels[i] = feeSalaryModelTemp;
            i++;
            while (c.moveToNext()) {
                feeSalaryModelTemp = new FeeSalaryModel(
                        c.getString(c.getColumnIndex(DB.teachers.s_id)),
                        c.getString(c.getColumnIndex(DB.teachers.s_name)),
                        c.getString(c.getColumnIndex(DB.teachers.paid)),
                        c.getString(c.getColumnIndex(DB.teachers.total_bill)),
                        c.getString(c.getColumnIndex(DB.teachers.name)),
                        ""
                );
                feeSalaryModels[i] = feeSalaryModelTemp;
                i++;
            }
            c.close();
            return feeSalaryModels;
        } else
            return null;
    }


 /* ============================= Report Admin (CheckList) ================================================================     */

    //inserts report in report activity
    public static boolean insertReportAdmin(List<RepMdlin> list) {
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String sql, schID, date, quesID, ques, quesTitle;
        RepMdlin r;
        int count;
        ContentValues cv;

        //try{


        int size = list.size();
        for (int i = 0; i < size; i++) {
            r = list.get(i);

            cv = new ContentValues();
            cv.put(DB.report_admin._ID, r.get_id());
            cv.put(DB.report_admin.sch_id, r.getSchID());
            cv.put(DB.report_admin.grade_id, r.getGradeID());
            cv.put(DB.report_admin.server_id_ques, r.getServerIDQues());
            cv.put(DB.report_admin.local_id_ques, r.getLocalIDQues());
            cv.put(DB.report_admin.questionTitle, r.getQuestionTitle());
            cv.put(DB.report_admin.question, r.getQuestion());
            cv.put(DB.report_admin.answer, r.getAnswer());
            cv.put(DB.report_admin.priority, r.getPriority());
            cv.put(DB.report_admin.details, r.getDetails());
            cv.put(DB.report_admin.serverQuestion, r.getServerQuestion());
            cv.put(DB.report_admin.answerWeight, r.getAnswerWeight());
            cv.put(DB.report_admin.date_report, r.getDateReport());
            cv.put(DB.report_admin.date_target, r.getDateTarget());
            cv.put(DB.report_admin.date_solve, r.getDateSolve());
            cv.put(DB.report_admin.time_lm, r.getTimeLm());
            cv.put(DB.report_admin.synced, r.getSynced());
            cv.put(DB.report_admin.notify, r.getNotify());
            cv.put(DB.report_admin.notify_his, r.getNotify_his());
            cv.put(DB.report_admin.n_update_date, r.getN_update_date());
            cv.put(DB.report_admin.server_id_answer, r.getServerIDAns());
            cv.put(DB.report_admin.local_id_answer, r.getLocalIDAns());


            String sel = DB.report_admin.sch_id + " = ? AND " + DB.report_admin.date_report + " = ? AND "
                    + DB.report_admin.server_id_ques + " = ? AND " + DB.report_admin.question + " = ? ";

            schID = list.get(0).getSchID();
            date = list.get(0).getDateReport();
            ques = r.getQuestion();
            quesID = r.getServerIDQues();
            String[] selArg = new String[]{schID, date, quesID, ques};

            count = db.update(DB.report_admin.table, cv, sel, selArg);


            if (count <= 0) {
                db.insert(DB.report_admin.table, null, cv);
            }

        }

        /* } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }*/
        return flag;
    }

    //inserts old reports (environment) dowloaded from server
    public static boolean insertRepOldDownlded(List<RepA> list) {
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String sql, schID, date, quesID, ques, quesTitle;
        RepA r;
        int count;
        ContentValues cv;

        //try{
        for (int i = 0; i < list.size(); i++) {

            cv = new ContentValues();
            cv.put(DB.report_admin.server_id_answer, list.get(i).id);
            cv.put(DB.report_admin.sch_id, list.get(i).instituteId);
            cv.put(DB.report_admin.server_id_ques, list.get(i).infrastructureQuestionId);
            cv.put(DB.report_admin.date_report, list.get(i).submittedDate);
            cv.put(DB.report_admin.answer, list.get(i).answer);
            cv.put(DB.report_admin.priority, list.get(i).priority);
            cv.put(DB.report_admin.details, list.get(i).details);
            cv.put(DB.report_admin.local_id_answer, list.get(i).localId);
            cv.put(DB.report_admin.time_lm, list.get(i).modifiedDate);
            cv.put(DB.report_admin.date_target, list.get(i).targetDate);
            cv.put(DB.report_admin.date_solve, list.get(i).solveDate);
            //fields not included in Gson
            cv.put(DB.report_admin.question, list.get(i).infrastructureQuestion);
            cv.put(DB.report_admin.category, list.get(i).category);
            cv.put(DB.report_admin.ques_category_id, list.get(i).ques_category_id);
            cv.put(DB.report_admin.serverQuestion, list.get(i).server_ques);
            cv.put(DB.report_admin.synced, list.get(i).synced);
            cv.put(DB.report_admin.notify, list.get(i).notify);

            cv.put(DB.report_admin.grade, list.get(i).grade);
            cv.put(DB.report_admin.grade_id, list.get(i).gradeID);


            String sel = DB.report_admin.sch_id + " = ? AND " + DB.report_admin.date_report + " = ? AND "
                    + DB.report_admin.server_id_ques + " = ? ";

            schID = list.get(0).instituteId;
            date = list.get(0).submittedDate;
            quesID = list.get(0).infrastructureQuestionId;
            String[] selArg = new String[]{schID, date, quesID};

            count = db.update(DB.report_admin.table, cv, sel, selArg);
            if (count <= 0) {
                db.insert(DB.report_admin.table, null, cv);
            }

        }

        /* } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }*/
        return flag;
    }

    public static boolean insertRepPerf1Sch1Date(RepPerf repPerf, String schID, String dateRep) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        boolean f = true;
        int count;
        ContentValues cv;

        cv = new ContentValues();
        cv.put(DB.admin_perform.sch_id, schID);
        cv.put(DB.admin_perform.sch_name, repPerf.getSch_name());
        cv.put(DB.admin_perform.adminPerformRatio, repPerf.getAdminPerformRatio());
        cv.put(DB.admin_perform.synced, repPerf.getSynced());
        cv.put(DB.admin_perform.date_report, repPerf.getDate_report());
        cv.put(DB.admin_perform.date_update, repPerf.getDate_update());
        cv.put(DB.admin_perform.time_lm, repPerf.getTime_lm());
        cv.put(DB.admin_perform.totalQues, repPerf.getTotalQues());


        String selection = DB.admin_perform.sch_id + " = ? AND " + DB.admin_perform.date_report + " = ?";
        String[] selecArg = new String[]{schID, dateRep};
        count = db.update(DB.admin_perform.table, cv, selection, selecArg);

        if (count <= 0) {//if no last perform
            db.insert(DB.admin_perform.table, null, cv);
        }


        return f;
    }

    public static List<RepMdlin> getActiveQuesRep() {
        //done
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<RepMdlin> quesList = new ArrayList<>();
        Cursor c = null;
        RepMdlin repModel;
        // try {
       /* String[] projection = new String[]{
                DB.admin_ques.server_id,
                DB.admin_ques.quesTitle,
                DB.admin_ques.ques,
                DB.admin_ques.synced,
                DB.admin_ques.serverQuestion,
                DB.admin_ques.time_lm,
        };*/
        String sel = DB.admin_ques.active + " = ? ";
        String[] selArg = new String[]{"true"};


        c = db.query(DB.admin_ques.table,
                null, sel, selArg, null, null, null);


        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    repModel = new RepMdlin();
                    repModel.set_id(c.getString(c.getColumnIndex(DB.admin_ques._ID)));
                    repModel.setServerIDQues(c.getString(c.getColumnIndex(DB.admin_ques.server_id)));
                    repModel.setQuesTitle(c.getString(c.getColumnIndex(DB.admin_ques.quesTitle)));
                    repModel.setQues(c.getString(c.getColumnIndex(DB.admin_ques.ques)));
                    repModel.setActive(c.getString(c.getColumnIndex(DB.admin_ques.active)));
                    repModel.setSynced(c.getString(c.getColumnIndex(DB.admin_ques.synced)));
                    repModel.setServerQuestion(c.getString(c.getColumnIndex(DB.admin_ques.serverQuestion)));
                    repModel.setTimeLm(c.getString(c.getColumnIndex(DB.admin_ques.time_lm)));

                    quesList.add(repModel);
                } while (c.moveToNext());
            }
        }
       /* } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
*/
        return quesList;
    }

    public static String getLastRepAdminDate(SQLiteDatabase db, String schID) {
        String date = null;
        Cursor c = null;

        String project[] = new String[]{
                DB.perform.lastAdminPerformDate
        };

        String selec = DB.perform.sch_id + "=?";
        String[] selecArg = new String[]{schID};

        c = db.query(DB.perform.table, project, selec, selecArg, null, null, null);


        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                date = c.getString(c.getColumnIndex(DB.perform.lastAdminPerformDate));
            }
        }

        if (c != null)
            c.close();
        return date;
    }

    public static List<RepMdlin> getRepAdminBasedOnSync(SQLiteDatabase db, String syncStatus/**synced=1 & unsynced=0*/) {
        /** for editing feature of reporting. this will get old reports (checklist admin report)*/
        RepMdlin repModel;
        List<RepMdlin> list = null;
        Cursor c = null;
        // try {
        list = new ArrayList<>();
        String selection = DB.report_admin.synced + " = ? ";

        String[] selectionArg = new String[]{syncStatus};


        c = db.query(DB.report_admin.table,
                null, selection, selectionArg, null, null, null);


        if (c != null) {
            if (c.getCount() > 0) {
                c.moveToFirst();
                repModel = new RepMdlin(
                        c.getString(c.getColumnIndex(DB.report_admin.sch_id)),
                        c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)),
                        c.getString(c.getColumnIndex(DB.report_admin.questionTitle)),
                        c.getString(c.getColumnIndex(DB.report_admin.question)),
                        c.getString(c.getColumnIndex(DB.report_admin.answer)),
                        c.getString(c.getColumnIndex(DB.report_admin.answerWeight)),
                        c.getString(c.getColumnIndex(DB.report_admin.details)),
                        c.getString(c.getColumnIndex(DB.report_admin.priority)),
                        c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)),
                        c.getString(c.getColumnIndex(DB.report_admin.date_report)),
                        c.getString(c.getColumnIndex(DB.report_admin.synced)),
                        c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                list.add(repModel);

                while (c.moveToNext()) {
                    repModel = new RepMdlin(
                            c.getString(c.getColumnIndex(DB.report_admin.sch_id)),
                            c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)),
                            c.getString(c.getColumnIndex(DB.report_admin.questionTitle)),
                            c.getString(c.getColumnIndex(DB.report_admin.question)),
                            c.getString(c.getColumnIndex(DB.report_admin.answer)),
                            c.getString(c.getColumnIndex(DB.report_admin.answerWeight)),
                            c.getString(c.getColumnIndex(DB.report_admin.details)),
                            c.getString(c.getColumnIndex(DB.report_admin.priority)),
                            c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)),
                            c.getString(c.getColumnIndex(DB.report_admin.date_report)),
                            c.getString(c.getColumnIndex(DB.report_admin.synced)),
                            c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                    list.add(repModel);
                }
            }
        }
       /* } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
*/

        return list;
    }

    public static LastPerfMdl getLastPerformbySchoolID(SQLiteDatabase db, String schID) {
        /** this will return last 3 kind of performance data for a schoolSync*/
        Cursor c = null;
        LastPerfMdl lastPerfMdl = null;

        try {


            String sql = "SELECT * FROM " + DB.perform.table + " WHERE " + DB.perform.sch_id + " = ?";
            String selecArg[] = new String[]{schID};
            c = db.rawQuery(sql, selecArg);


            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    lastPerfMdl = new LastPerfMdl(
                            c.getString(c.getColumnIndex(DB.perform.sch_id)),
                            c.getString(c.getColumnIndex(DB.perform.sch_name)),
                            c.getString(c.getColumnIndex(DB.perform.lastAdminPerform)),
                            c.getString(c.getColumnIndex(DB.perform.lastAdminPerformDate)),
                            c.getString(c.getColumnIndex(DB.perform.lastEvalPerform)),
                            c.getString(c.getColumnIndex(DB.perform.lastEvalPerformDate)),
                            c.getString(c.getColumnIndex(DB.perform.lastAttenPerform)),
                            c.getString(c.getColumnIndex(DB.perform.lastAttenPerformDate))
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }
        return lastPerfMdl;
    }

    public static RepPerf getRepPerfBySchIDdateWise(SQLiteDatabase db, String schID, String date) {
        RepPerf repPerf = null;
        Cursor c = null;
        String selc = DB.admin_perform.sch_id + "= ? AND " + DB.admin_perform.date_report + "= ?";
        String[] selcArg = new String[]{schID, date};
        c = db.query(DB.admin_perform.table, null, selc, selcArg, null, null, null);
        if (c != null) {
            c.moveToFirst();
            repPerf = new RepPerf(
                    c.getString(c.getColumnIndex(DB.admin_perform.sch_id)),
                    c.getString(c.getColumnIndex(DB.admin_perform.sch_name)),
                    c.getString(c.getColumnIndex(DB.admin_perform.adminPerformRatio)),
                    c.getString(c.getColumnIndex(DB.admin_perform.synced)),
                    c.getString(c.getColumnIndex(DB.admin_perform.date_report)),
                    c.getString(c.getColumnIndex(DB.admin_perform.date_update)),
                    c.getString(c.getColumnIndex(DB.admin_perform.time_lm)),
                    c.getString(c.getColumnIndex(DB.admin_perform.totalQues)));
        }
        if (c != null)
            c.close();

        return repPerf;
    }

    public static List<RepPerf> getLast5RepPerfBySchID(String schID) {
        List<RepPerf> list = new ArrayList<>();
        RepPerf repPerf = null;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);


        List<String> dates = H.getUniqueDateFromATable(DB.report_admin.table, DB.report_admin.date_report);
        dates = H.lastDates(dates, 5);
        int n = dates.size();
        for (int i = 0; i < n; i++) {
            repPerf = DAO.getRepPerfBySchIDdateWise(db, schID, dates.get(i));
            list.add(repPerf);
        }


        return list;
    }


    //_____________ for Edit ________________

    public static List<String> getAllSchIDsFromLastPerformRep() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> list = new ArrayList<>();
        String schID, date;
        Cursor c = null;
        String[] proj = new String[]{DB.perform.sch_id, DB.perform.lastAdminPerformDate};
        c = db.query(DB.perform.table, proj, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    schID = c.getString(c.getColumnIndex(DB.perform.sch_id));
                    date = c.getString(c.getColumnIndex(DB.perform.lastAdminPerformDate));
                    if (!date.equals(""))
                        list.add(schID);
                } while (c.moveToNext());
            }
        }
        if (c != null)
            c.close();
        return list;
    }

    @Nullable
    public static String getLastRepDate(String schID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String date = null;
        LastPerfMdl lpm;
        List<LastPerfMdl> lpmList = getLastPerfAllSchoolFromPerformTable();
        int n = lpmList.size();
        for (int i = 0; i < n; i++) {
            lpm = lpmList.get(i);
            if (lpm.getSchID().equals(schID)) {
                date = lpm.getLastRepPerformDate();
            }
        }
        return date;
    }

    public static List<RepMdlin> getLastRepModelinListBySchool(String schID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        RepMdlin repMdlin = null;
        List<RepMdlin> list = new ArrayList<>();
        Cursor c = null;
        String date = getLastRepDate(schID);

        if (!schID.equals("") || schID != null || !date.equals("") || date != null) {
            String sel = DB.report_admin.sch_id + " = ? AND " + DB.report_admin.date_report + " = ? ";

            String[] selArg = new String[]{schID, date};
            c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        repMdlin = new RepMdlin(
                                c.getString(c.getColumnIndex(DB.report_admin.sch_id)),
                                c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)),
                                c.getString(c.getColumnIndex(DB.report_admin.questionTitle)),
                                c.getString(c.getColumnIndex(DB.report_admin.question)),
                                c.getString(c.getColumnIndex(DB.report_admin.answer)),
                                c.getString(c.getColumnIndex(DB.report_admin.answerWeight)),
                                c.getString(c.getColumnIndex(DB.report_admin.details)),
                                c.getString(c.getColumnIndex(DB.report_admin.priority)),
                                c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)),
                                c.getString(c.getColumnIndex(DB.report_admin.date_report)),
                                c.getString(c.getColumnIndex(DB.report_admin.synced)),
                                c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                        list.add(repMdlin);
                    } while (c.moveToNext());
                }
            }
        }
        if (c != null)
            c.close();
        return list;
    }

    public static List<List<RepMdlin>> getLastRepModelAllSchool() {
        String schid;
        RepMdlin repMdlin;
        List<String> schIds = DAO.getAllSchIDsFromLastPerformRep();
        List<RepMdlin> repMdlins = new ArrayList<>();//list of repmodelin for a schoolSync
        List<List<RepMdlin>> listList = new ArrayList<>();//list of repModelinList of all schools
        for (int i = 0; i < schIds.size(); i++) {
            schid = schIds.get(i);
            repMdlins = DAO.getLastRepModelinListBySchool(schid);
            if (repMdlins.size() > 0)
                listList.add(repMdlins);//list of repmodelin for a schoolSync
        }
        return listList;
    }

    /*============================ evaluation report academic =========================================================================     */

    public static boolean insertReportAcadEvaluationPerform(Context contex, SQLiteDatabase db,
                                                            List<EvalPerfModel> list) {
        boolean flag = true;
        contex = contex.getApplicationContext();

        int size = list.size();
        try {
            for (int i = 0; i < size; i++) {
                ContentValues cv = new ContentValues();
                cv.put(DB.acad_perform.schoolID, list.get(i).getSchoolID());
                cv.put(DB.acad_perform.schoolName, list.get(i).getSchoolName());
                cv.put(DB.acad_perform.date_report, list.get(i).getDateReport());
                cv.put(DB.acad_perform.date_update, list.get(i).getDateUpdate());
                cv.put(DB.acad_perform.attendence, list.get(i).getAttendence());
                cv.put(DB.acad_perform.evalPerformRatio, list.get(i).getEvalPerformRatio());
                cv.put(DB.acad_perform.synced, list.get(i).getSynced());
                cv.put(DB.acad_perform.time_lm, list.get(i).getTimeLm());

                db.insert(DB.acad_perform.table, null, cv);
            }

        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public static boolean insertEvlAnsToDB(List<EvlModelin> l) {
        // new only ans table based
        // not done
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        boolean f = true;
        int count;
        ContentValues cv;

        for (int i = 0; i < l.size(); i++) {
            cv = new ContentValues();
            cv.put(DB.report_acad.schID, l.get(i).getSchID());
            cv.put(DB.report_acad.schName, l.get(i).getSchName());
            cv.put(DB.report_acad.grade_id, l.get(i).getGradeID());
            cv.put(DB.report_acad.server_id_ques, l.get(i).getServerIDQues());
            cv.put(DB.report_acad.local_id_ques, l.get(i).getLocalIDQues());
            cv.put(DB.report_acad.server_id_answer, l.get(i).getServerIDAns());
            cv.put(DB.report_acad.local_id_answer, l.get(i).getLocalIDAns());
            cv.put(DB.report_acad.ques, l.get(i).getQues());
            cv.put(DB.report_acad.answer, l.get(i).getAnswer());
            cv.put(DB.report_acad.active, l.get(i).getActive());
            cv.put(DB.report_acad.synced, l.get(i).getSynced());
            cv.put(DB.report_acad.server_ques, l.get(i).getServer_ques());
            cv.put(DB.report_acad.grade, l.get(i).getGrade());
            cv.put(DB.report_acad.date_report, l.get(i).getDate_report());
            cv.put(DB.report_acad.date_update, l.get(i).getDate_update());
            cv.put(DB.report_acad.time_lm, l.get(i).getTime_lm());
            cv.put(DB.report_acad.totalStd, l.get(i).getTotalStd());
            cv.put(DB.report_acad.attendance, l.get(i).getAttendence());
            cv.put(DB.report_acad.attempt, l.get(i).getAttempt());
            cv.put(DB.report_acad.asked, l.get(i).getAsked());
            cv.put(DB.report_acad.correct, l.get(i).getCorrect());
            cv.put(DB.report_acad.perf, l.get(i).getPerf());

            cv.put(DB.report_acad.subject, l.get(i).getSubject());
            cv.put(DB.report_acad.chapter, l.get(i).getChapter());


            String sel = DB.report_acad.schID + " = ? AND " + DB.report_acad.date_report + " = ? AND " +
                    DB.report_acad.server_id_ques + " = ? AND " + DB.report_acad.ques + " = ? ";
            String[] selArg = new String[]{l.get(i).getSchID(), l.get(i).getDate_report(),
                    l.get(i).getServerIDQues(), l.get(i).getQues()};

            count = db.update(DB.report_acad.table, cv, sel, selArg);

            if (count <= 0)
                db.insert(DB.report_acad.table, null, cv);


        }
        return f;
    }

    public static EvlModelin getEvlQuesInfoFromQuesServerID(String serverIDQues, String localIDQues) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        EvlModelin evlModelin = new EvlModelin();
        String ques = null, answer = null, active = null, serverQues = null, chapter = null, subject = null, grade = null, gradeID = null;
        Cursor c = null;

        //search by server id
        if (serverIDQues != null) {
            String whre = DB.acad_ques.server_id + " = ? ";
            String[] whreArg = new String[]{serverIDQues};
            c = db.query(DB.acad_ques.table, null, whre, whreArg, null, null, null);
            if (c.moveToFirst()) {
                do {
                    ques = c.getString(c.getColumnIndex(DB.acad_ques.ques));
                    answer = c.getString(c.getColumnIndex(DB.acad_ques.answer));
                    active = c.getString(c.getColumnIndex(DB.acad_ques.active));
                    subject = c.getString(c.getColumnIndex(DB.acad_ques.subject));
                    chapter = c.getString(c.getColumnIndex(DB.acad_ques.chapter));
                    serverQues = c.getString(c.getColumnIndex(DB.acad_ques.server_question));
                    gradeID = c.getString(c.getColumnIndex(DB.acad_ques.grde_id));
                    grade = c.getString(c.getColumnIndex(DB.acad_ques.grade));
                } while (c.moveToNext());
            }
        }

        //if not found by serverid
        if (ques == null) {
            if (localIDQues != null) {
                String whre = DB.acad_ques.local_id + " = ? ";
                String[] whreArg = new String[]{localIDQues};
                c = db.query(DB.acad_ques.table, null, whre, whreArg, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        ques = c.getString(c.getColumnIndex(DB.acad_ques.ques));
                        answer = c.getString(c.getColumnIndex(DB.acad_ques.answer));
                        active = c.getString(c.getColumnIndex(DB.acad_ques.active));
                        subject = c.getString(c.getColumnIndex(DB.acad_ques.subject));
                        chapter = c.getString(c.getColumnIndex(DB.acad_ques.chapter));
                        serverQues = c.getString(c.getColumnIndex(DB.acad_ques.server_question));
                        gradeID = c.getString(c.getColumnIndex(DB.acad_ques.grde_id));
                        grade = c.getString(c.getColumnIndex(DB.acad_ques.grade));
                    } while (c.moveToNext());
                }
            }
        }


        if (ques == null)
            ques = "";
        if (answer == null)
            answer = "";
        if (active == null)
            active = "";
        evlModelin.setServerIDQues(serverIDQues);
        evlModelin.setLocalIDQues(localIDQues);
        evlModelin.setQues(ques);
        evlModelin.setAnswer(answer);
        evlModelin.setActive(active);
        evlModelin.setServer_ques(serverQues);
        evlModelin.setSubject(subject);
        evlModelin.setChapter(chapter);
        evlModelin.setGradeID(gradeID);
        evlModelin.setGrade(grade);
        if (c != null)
            c.close();

        return evlModelin;
    }


    //inserts old evaluations dowloaded from server
    public static boolean insertEvlOldDownlded(List<EvlA> l) {
        // new only ans table based
        // not done
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        boolean f = true;
        int count;
        ContentValues cv;

        for (int i = 0; i < l.size(); i++) {
            cv = new ContentValues();

            cv.put(DB.report_acad.server_id_answer, l.get(i).id);
            cv.put(DB.report_acad.local_id_answer, l.get(i).localId);
            cv.put(DB.report_acad.schID, l.get(i).instituteId);
            cv.put(DB.report_acad.server_id_ques, l.get(i).academicQuestionId);
            cv.put(DB.report_acad.local_id_ques, l.get(i).academicQuestionLocalId);
            cv.put(DB.report_acad.date_report, l.get(i).submittedDate);
            cv.put(DB.report_acad.time_lm, l.get(i).modifiedDate);
            cv.put(DB.report_acad.attendance, l.get(i).totalAttendance);
            cv.put(DB.report_acad.attempt, l.get(i).totalAttempt);
            cv.put(DB.report_acad.asked, l.get(i).totalAsked);
            cv.put(DB.report_acad.correct, l.get(i).totalAnswer);
            cv.put(DB.report_acad.perf, l.get(i).performance);
            cv.put(DB.report_acad.local_id_answer, l.get(i).localId);
            cv.put(DB.report_acad.ques, l.get(i).questionTitle);
            cv.put(DB.report_acad.answer, l.get(i).questionAnswer);
            cv.put(DB.report_acad.grade_id, l.get(i).questionGradeId);
            cv.put(DB.report_acad.server_ques, l.get(i).serverQuestion);
            cv.put(DB.report_acad.synced, l.get(i).synced);

            cv.put(DB.report_acad.schName, l.get(i).schName);
            cv.put(DB.report_acad.grade, l.get(i).grade);
            cv.put(DB.report_acad.grade_id, l.get(i).gradeID);
            cv.put(DB.report_acad.totalStd, l.get(i).totalStd);

            cv.put(DB.report_acad.subject, l.get(i).subject);
            cv.put(DB.report_acad.chapter, l.get(i).chapter);
            cv.put(DB.report_acad.active, l.get(i).active);

            String sel = DB.report_acad.schID + " = ? AND " + DB.report_acad.date_report + " = ? AND " +
                    DB.report_acad.server_id_ques + " = ? ";
            String[] selArg = new String[]{l.get(i).instituteId, l.get(i).submittedDate,
                    l.get(i).academicQuestionId};

            count = db.update(DB.report_acad.table, cv, sel, selArg);

            if (count <= 0)
                db.insert(DB.report_acad.table, null, cv);


        }
        return f;
    }


    /*========================= Dashboard =========================================================================================*/

    public static List<LastPerfMdl> getLastPerfAllSchoolFromPerformTable() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        Cursor c = null;
        LastPerfMdl l;
        List<LastPerfMdl> lpm = new ArrayList<>();

        // try {
        c = db.query(DB.perform.table, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            do {
                l = new LastPerfMdl();

                l.setSchID(c.getString(c.getColumnIndex(DB.perform.sch_id)));
                l.setSchName(c.getString(c.getColumnIndex(DB.perform.sch_name)));
                l.setGrade(c.getString(c.getColumnIndex(DB.perform.grade)));
                l.setEduType(c.getString(c.getColumnIndex(DB.perform.edu_type)));
                l.setCode(c.getString(c.getColumnIndex(DB.perform.s_code)));
                l.setLastRepPerform(c.getString(c.getColumnIndex(DB.perform.lastAdminPerform)));
                l.setLastRepPerformDate(c.getString(c.getColumnIndex(DB.perform.lastAdminPerformDate)));
                l.setLastEvalPerform(c.getString(c.getColumnIndex(DB.perform.lastEvalPerform)));
                l.setLastEvalPerformDate(c.getString(c.getColumnIndex(DB.perform.lastEvalPerformDate)));
                l.setLastAttenPerform(c.getString(c.getColumnIndex(DB.perform.lastAttenPerform)));
                l.setLastAttenPerformDate(c.getString(c.getColumnIndex(DB.perform.lastAttenPerformDate)));

                //if null
                if (l.getLastAttenPerform() == null)
                    l.setLastAttenPerform("0");
                if (l.getLastEvalPerform() == null)
                    l.setLastEvalPerform("0");
                if (l.getLastRepPerform() == null)
                    l.setLastRepPerform("0");

                lpm.add(l);
            } while (c.moveToNext());
        }

       /* } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }*/
        return lpm;
    }

    public static boolean insertLastPerformAll(String schID, LastPerfMdl lpm) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        boolean flag = true;
        int count;
        ContentValues cv;
        try {
            // lpm.removeNull();

            cv = new ContentValues();
            cv.put(DB.perform.sch_id, lpm.getSchID());
            cv.put(DB.perform.sch_name, lpm.getSchName());
            cv.put(DB.perform.edu_type, lpm.getEduType());
            cv.put(DB.perform.s_code, lpm.getCode());
            cv.put(DB.perform.grade, lpm.getGrade());
            cv.put(DB.perform.time_lm, lpm.getTime_lm());

            /*this null checking only update a single performace entity either Attendance or Report or Evaluation*/
            if (lpm.getLastEvalPerform() != null)
                cv.put(DB.perform.lastEvalPerform, lpm.getLastEvalPerform());
            if (lpm.getLastEvalPerformDate() != null)
                cv.put(DB.perform.lastEvalPerformDate, lpm.getLastEvalPerformDate());
            if (lpm.getLastRepPerform() != null)
                cv.put(DB.perform.lastAdminPerform, lpm.getLastRepPerform());
            if (lpm.getLastRepPerformDate() != null)
                cv.put(DB.perform.lastAdminPerformDate, lpm.getLastRepPerformDate());
            if (lpm.getLastAttenPerform() != null)
                cv.put(DB.perform.lastAttenPerform, lpm.getLastAttenPerform());
            if (lpm.getLastAttenPerformDate() != null)
                cv.put(DB.perform.lastAttenPerformDate, lpm.getLastAttenPerformDate());

            String selection = DB.perform.sch_id + " = ? ";
            String[] selecArg = new String[]{schID};
            count = db.update(DB.perform.table, cv, selection, selecArg);

            if (count <= 0) {//if no last perform for this school then insert new row for this school
                db.insert(DB.perform.table, null, cv);
            }

            Log.d(TAG, String.valueOf(count));
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    /*=====================================sync===========================================================*/

    public static boolean insertStudent(String studentID, String studentRollNo, String studentName, String studentGrade, String studentInstituteName, String studentGender,
                                        String studentWaiver, String studentFather, String studentGurdPhNum, String studentBkash, String studentDropout) {
        /*if(teacherMobile.equals("") || teacherMobile == null){
            teacherMobile = "Not Available";
        }*/
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;

        cv = new ContentValues();
        cv.put(DB.students.std_id, studentID);
        cv.put(DB.students.grade, studentGrade);
        cv.put(DB.students.roll, studentRollNo);
        cv.put(DB.students.name, studentName);
        cv.put(DB.students.institute, studentInstituteName);
        cv.put(DB.students.gender, studentGender);
        cv.put(DB.students.waiver, studentWaiver);
        cv.put(DB.students.father, studentFather);
        cv.put(DB.students.guardian_phn, studentGurdPhNum);
        cv.put(DB.students.bkash, studentBkash);
        cv.put(DB.students.dropout, studentDropout);

        String whereClause = DB.students.std_id + " = ? ";
        String[] whereArgs = new String[]{studentID};
        count = db.update(DB.students.table, cv, whereClause, whereArgs);
        if (count <= 0) {
            db.insert(DB.students.table, null, cv);
        }

        return flag;
    }

    public static boolean insertAStudent_idCall(String studentID, String instituteID, String roll,
                                                String transferred_institute, String birthDate, String mother, String gradeID) {
        //Log.e("add",presentAddrs);
        boolean flag = true;
        /*if(presentAddrs.equals("") || presentAddrs == null ){
            presentAddrs="Not Available";
        }*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;

        cv = new ContentValues();
        cv.put(DB.students.s_id, instituteID);
        cv.put(DB.students.roll, roll);
        cv.put(DB.students.transferred_institute, transferred_institute);
        cv.put(DB.students.birthDate, birthDate);
        cv.put(DB.students.mother, mother);
        cv.put(DB.students.grade_id, gradeID);

        String whereClause = DB.students.std_id + " = ? ";
        String[] whereArgs = new String[]{studentID};
        // Log.e("db "," row update");
        count = db.update(DB.students.table, cv, whereClause, whereArgs);
        //Log.e("db ",count+" row ");

        return flag;
    }

    //instituteID,instituteName,institutePoName,instituteCode,instituteTotalStudents,instituteEducationType
    public static boolean insertSchool(String instituteID, String instituteName, String grade, String instituteCode,
                                       String instituteTotalStudents, String instituteEducationType,
                                       String sessionStart, String sessionEnd) {
        /*if(teacherMobile.equals("") || teacherMobile == null){
            teacherMobile = "Not Available";
        }*/
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;

        cv = new ContentValues();
        cv.put(DB.schools.s_id, instituteID);
        cv.put(DB.schools.s_name, instituteName);
        //cv.put(DB.schools.poName, institutePoName);
        cv.put(DB.schools.s_code, instituteCode);
        cv.put(DB.schools.grade, grade);
        cv.put(DB.schools.totalStd, instituteTotalStudents);
        cv.put(DB.schools.edu_type, instituteEducationType);
        cv.put(DB.schools.session_start, sessionStart);
        cv.put(DB.schools.session_end, sessionEnd);


        String whereClause = DB.schools.s_id + " = ? ";
        String[] whereArgs = new String[]{instituteID};
        count = db.update(DB.schools.table, cv, whereClause, whereArgs);
        if (count <= 0) {
            db.insert(DB.schools.table, null, cv);
        }

        return flag;
    }

    public static boolean insertASchool_idCall(String instituteID, String institutePoID, String instituteAddress, String gradeID) {
        //Log.e("PO",institutePoID);
        boolean flag = true;
        if (instituteAddress.equals("") || instituteAddress == null) {
            instituteAddress = "N/A";
        }
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;

        cv = new ContentValues();
        cv.put(DB.schools.s_address, instituteAddress);
        cv.put(DB.schools.po_id, institutePoID);
        cv.put(DB.schools.grade_id, gradeID);
        cv.put(DB.schools.time_lm, df1.format(new Date()));

        String whereClause = DB.schools.s_id + " = ? ";
        String[] whereArgs = new String[]{instituteID};
        // Log.e("db "," row update");
        count = db.update(DB.schools.table, cv, whereClause, whereArgs);
        //Log.e("db ",count+" row ");

        return flag;
    }

    public static boolean insertTeacher(String teacherID, String teacherName, String teacherGrade, String teacherInstituteName, String teacherMobile) {
        if (teacherMobile.equals("") || teacherMobile == null) {
            teacherMobile = "N/A";
        }
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;

        cv = new ContentValues();
        cv.put(DB.teachers.t_id, teacherID);
        cv.put(DB.teachers.name, teacherName);
        cv.put(DB.teachers.grade, teacherGrade);
        cv.put(DB.teachers.s_name, teacherInstituteName);
        cv.put(DB.teachers.mobile, teacherMobile);

        String whereClause = DB.teachers.t_id + " = ? ";
        String[] whereArgs = new String[]{teacherID};
        count = db.update(DB.teachers.table, cv, whereClause, whereArgs);
        if (count <= 0) {
            db.insert(DB.teachers.table, null, cv);
        }

        return flag;
    }

    public static boolean insertATeacher_idCall(String teacherID, String instituteID, String presentAddrs,
                                                String gender, String education) {
        //Log.e("add",presentAddrs);
        boolean flag = true;
       /* if (presentAddrs.equals("") || presentAddrs == null) {
            presentAddrs = "Not Available";
        }*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;

        cv = new ContentValues();
        //cv.put(DB.teachers.t_id, teacherID);
        if (instituteID != null)
            cv.put(DB.teachers.s_id, instituteID);
        if (presentAddrs != null) {
            cv.put(DB.teachers.address, presentAddrs);
            cv.put(DB.teachers.education, education);
            cv.put(DB.teachers.gender, gender);
            cv.put(DB.teachers.time_lm, df1.format(new Date(System.currentTimeMillis())));
        }

        String whereClause = DB.teachers.t_id + " = ? ";
        String[] whereArgs = new String[]{teacherID};
        // Log.e("db "," row update");
        count = db.update(DB.teachers.table, cv, whereClause, whereArgs);
        //Log.e("db ",count+" row ");
        return flag;
    }


    //===============================loop sync (wrapper)======================

    public static boolean insertSchool_ListCall(List<SchMdl> list) {
        boolean flag = true;
        SchMdl temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            insertSchool(temp.getId(),
                    temp.getName(),
                    temp.getGradeName(),
                    temp.getCode(),
                    temp.getTotalStudent(),
                    temp.getInstituteTypeName(),
                    temp.getSessionStart(),
                    temp.getSessionEnd()
            );
        }
        return flag;
    }

    public static boolean insertTeacher_ListCall(List<TchrMdl> list) {
        boolean flag = true;
        TchrMdl temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            insertTeacher(temp.getId(),
                    temp.getUsername(),
                    temp.getGradeName(),
                    temp.getInstituteName(),
                    temp.getMobileNumber()
            );
        }
        return flag;
    }

    public static boolean insertStudent_ListCall(List<StdMdl> list) {
        boolean flag = true;
        StdMdl temp;
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            insertStudent(temp.getId(),
                    temp.getRoll(),
                    temp.getFullName(),
                    temp.getGradeId(),
                    temp.getInstituteId(),
                    temp.getSex(),
                    temp.getWaiver(),
                    temp.getFatherName(),
                    temp.getGuardianMobile(),
                    temp.getBkashNumber(),
                    temp.getDropout()
            );
        }
        return flag;
    }


}

