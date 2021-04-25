/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.view.evaluation.EvalQuesModel;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.payments.paymdl.PayMdl;
import com.example.f.schMon.view.payments.paymdl.PayMdlInner;
import com.example.f.schMon.view.report_environment.RepMdlin;
import com.example.f.schMon.view.school.PerfMdl;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlQ;
import com.example.f.schMon.view.sync_mir.MdlSync.RepQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Mir Ferdous on 04/10/2017.
 */

public class DAO2 {
    /**
     * based on single answer table run time query
     */
    private static final String TAG = DAO2.class.getName();

    /*================================= Rep & Eval ======================================================================================*/
    public static List<String> all_unique_values_from_a_Column(String tableName, String column) {
        /* all values(unique) of a column of any table from DB,*/
        /**all unique schoolSync id from a table to find reported schools*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> list = new ArrayList<>();
        Cursor c = null;
        String[] proj = new String[]{column};
        c = db.query(tableName, proj, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String temp = c.getString(c.getColumnIndex(column));
                    if (!list.contains(temp))
                        list.add(temp);
                } while (c.moveToNext());
            }
        }

        if (c != null)
            c.close();

        return list;
    }

    public static List<String> all_values_from_a_Column_notUnique(String tableName, String column) {
        /* all values(unique) of a column of any table from DB,*/
        /**all unique schoolSync id from a table to find reported schools*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> list = new ArrayList<>();
        Cursor c = null;
        String[] proj = new String[]{column};
        c = db.query(tableName, proj, null, null, null, null, null);
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

    public static List<String> allUniqueDateForASchool(String schID, String table, String schIDColumn, String dateColumn) {
        /** all date reported for a schoolSync*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> dateList = new ArrayList<>();
        Cursor c = null;
        String[] proj = new String[]{dateColumn};
        String sel = schIDColumn + " = ?";
        String[] selArg = new String[]{schID};
        c = db.query(table, proj, sel, selArg, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String temp = c.getString(c.getColumnIndex(dateColumn));
                    if (!dateList.contains(temp))
                        dateList.add(temp);
                } while (c.moveToNext());
            }
        }

        if (c != null) {
            c.close();
        }
        return dateList;
    }


    public static String lastDateForASchool(String schID, String table, String schIDColumn, String dateColumn) {
        /** last date of eval or report for a sch*/
        String date = null;
        List<String> allDatesForASchool = DAO2.allUniqueDateForASchool(schID, table, schIDColumn, dateColumn);


        List<String> lastDates = H.lastDates(allDatesForASchool, 1);
        if (lastDates.size() > 0)
            date = lastDates.get(0);

        return date;
    }

/*================================= Report  ======================================================================================*/

    public static List<EvlModelin> getActiveEvaluatioQues(@Nullable String gradeID) {
        List<EvlModelin> quesList = new ArrayList<>();
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        EvlModelin eval;
        Cursor c = null;
        //  try {

        // when gradeID is null - getting all questions
        if (gradeID == null || gradeID.equals("")) {//get all questions without gradeID
            c = db.query(DB.acad_ques.table, null, null, null, null, null, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        eval = new EvlModelin();
                        eval.setServerIDQues(c.getString(c.getColumnIndex(DB.acad_ques._ID)));
                        eval.setServerIDQues(c.getString(c.getColumnIndex(DB.acad_ques.server_id)));
                        eval.setLocalIDQues(c.getString(c.getColumnIndex(DB.acad_ques.local_id)));
                        eval.setQues(c.getString(c.getColumnIndex(DB.acad_ques.ques)));
                        eval.setAnswer(c.getString(c.getColumnIndex(DB.acad_ques.answer)));
                        eval.setActive(c.getString(c.getColumnIndex(DB.acad_ques.active)));
                        eval.setSynced(c.getString(c.getColumnIndex(DB.acad_ques.synced)));
                        eval.setTime_lm(c.getString(c.getColumnIndex(DB.acad_ques.time_lm)));
                        eval.setServer_ques(c.getString(c.getColumnIndex(DB.acad_ques.server_question)));
                        eval.setGradeID(c.getString(c.getColumnIndex(DB.acad_ques.grde_id)));
                        eval.setGrade(c.getString(c.getColumnIndex(DB.acad_ques.grade)));

                        eval.setSubject(c.getString(c.getColumnIndex(DB.acad_ques.subject)));
                        eval.setChapter(c.getString(c.getColumnIndex(DB.acad_ques.chapter)));

                        eval.setCategory(c.getString(c.getColumnIndex(DB.acad_ques.category)));
                        eval.setCategoryID(c.getString(c.getColumnIndex(DB.acad_ques.category_id)));

                        if (eval.getActive() != null) {
                            if (eval.getActive().equals("true") || eval.getActive().equals("1"))
                                quesList.add(eval);
                        }
                    } while (c.moveToNext());
                }

            }
        } else {//get questions gradeIDwise
            String selection = DB.acad_ques.grde_id + " = ?";
            String[] selectionArg = new String[]{gradeID};
            c = db.query(DB.acad_ques.table, null, selection, selectionArg, null, null, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        eval = new EvlModelin();
                        eval.setServerIDQues(c.getString(c.getColumnIndex(DB.acad_ques._ID)));
                        eval.setServerIDQues(c.getString(c.getColumnIndex(DB.acad_ques.server_id)));
                        eval.setLocalIDQues(c.getString(c.getColumnIndex(DB.acad_ques.local_id)));
                        eval.setQues(c.getString(c.getColumnIndex(DB.acad_ques.ques)));
                        eval.setAnswer(c.getString(c.getColumnIndex(DB.acad_ques.answer)));
                        eval.setActive(c.getString(c.getColumnIndex(DB.acad_ques.active)));
                        eval.setSynced(c.getString(c.getColumnIndex(DB.acad_ques.synced)));
                        eval.setTime_lm(c.getString(c.getColumnIndex(DB.acad_ques.time_lm)));
                        eval.setServer_ques(c.getString(c.getColumnIndex(DB.acad_ques.server_question)));
                        eval.setGradeID(c.getString(c.getColumnIndex(DB.acad_ques.grde_id)));
                        eval.setGrade(c.getString(c.getColumnIndex(DB.acad_ques.grade)));

                        eval.setSubject(c.getString(c.getColumnIndex(DB.acad_ques.subject)));
                        eval.setChapter(c.getString(c.getColumnIndex(DB.acad_ques.chapter)));

                        eval.setCategory(c.getString(c.getColumnIndex(DB.acad_ques.category)));
                        eval.setCategoryID(c.getString(c.getColumnIndex(DB.acad_ques.category_id)));

                        if (eval.getActive() != null) {
                            if (eval.getActive().equals("true") || eval.getActive().equals("1"))
                                quesList.add(eval);
                        }
                    } while (c.moveToNext());
                }

            }
        }
       /* } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }*/

        return quesList;
    }

    @NonNull
    public static List<RepMdlin> getReport1School1Date(String schID, String date) {
        List<RepMdlin> list = new ArrayList<>();
        if (schID == null || date == null)
            return list;
        if (schID.equals("") || date.equals(""))
            return list;

        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        RepMdlin r = null;
        Cursor c = null;


        String sel = DB.report_admin.sch_id + " = ? AND " + DB.report_admin.date_report + " = ? ";
        String[] selArg = new String[]{schID, date};
        c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    //18 columns
                    r = new RepMdlin();
                    r.set_id(c.getString(c.getColumnIndex(DB.report_admin._ID)));
                    r.setSchID(c.getString(c.getColumnIndex(DB.report_admin.sch_id)));
                    r.setServerIDQues(c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)));
                    r.setQuesTitle(c.getString(c.getColumnIndex(DB.report_admin.questionTitle)));
                    r.setQues(c.getString(c.getColumnIndex(DB.report_admin.question)));
                    r.setAns(c.getString(c.getColumnIndex(DB.report_admin.answer)));
                    r.setAnsWeight(c.getString(c.getColumnIndex(DB.report_admin.answerWeight)));
                    r.setDetails(c.getString(c.getColumnIndex(DB.report_admin.details)));
                    r.setPriority(c.getString(c.getColumnIndex(DB.report_admin.priority)));
                    r.setServerQuestion(c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)));
                    r.setDateReport(c.getString(c.getColumnIndex(DB.report_admin.date_report)));
                    r.setDateTarget(c.getString(c.getColumnIndex(DB.report_admin.date_target)));
                    r.setDateSolve(c.getString(c.getColumnIndex(DB.report_admin.date_solve)));
                    r.setSynced(c.getString(c.getColumnIndex(DB.report_admin.synced)));
                    r.setTimeLm(c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                    r.setNotify(c.getString(c.getColumnIndex(DB.report_admin.notify)));
                    r.setNotify_his(c.getString(c.getColumnIndex(DB.report_admin.notify_his)));
                    r.setN_update_date(c.getString(c.getColumnIndex(DB.report_admin.n_update_date)));
                    r.setServerIDAns(c.getString(c.getColumnIndex(DB.report_admin.server_id_answer)));
                    r.setLocalIDAns(c.getString(c.getColumnIndex(DB.report_admin.local_id_answer)));
                    r.setLocalIDQues(c.getString(c.getColumnIndex(DB.report_admin.local_id_ques)));

                    list.add(r);
                } while (c.moveToNext());
            }
        }

        if (c != null)
            c.close();


        return list;
    }

    public static List<List<RepMdlin>> getLastRepModelinOfAllSchool() {

        // done

        List<RepMdlin> repMdlins;//list of repmodelin for a schoolSync
        List<List<RepMdlin>> listList = new ArrayList<>();//list of repModelinList of all schools
        String lastDate;
        RepMdlin repMdlin;


        List<String> schIds = DAO2.all_unique_values_from_a_Column(DB.report_admin.table, DB.report_admin.sch_id);
        for (int i = 0; i < schIds.size(); i++) {
            lastDate = DAO2.lastDateForASchool(schIds.get(i), DB.report_admin.table,
                    DB.report_admin.sch_id, DB.report_admin.date_report);

            repMdlins = DAO2.getReport1School1Date(schIds.get(i), lastDate);
            if (repMdlins.size() > 0)
                listList.add(repMdlins);//list of repmodelin for a schoolSync
        }


        return listList;
    }

    public static List<PerfMdl> getLast_n_RepPerform1School(String schID, int noOfLastEvl) {
        List<PerfMdl> perfMdls = new ArrayList<>();
        List<String> dates = H.getUniqueDateFromATableForASch(schID, DB.report_admin.table, DB.report_admin.date_report, DB.report_admin.sch_id);
        dates = H.lastDates(dates, noOfLastEvl);
        String date, temp;

        for (int i = 0; i < dates.size(); i++) {
            date = dates.get(i);
            List<RepMdlin> repMdlinList = DAO2.getReport1School1Date(schID, date);
            temp = H.calcRepPerformForASch(repMdlinList);
            // if (repMdlinList.size() > 0)
            perfMdls.add(new PerfMdl(schID, repMdlinList.get(0).getDateReport(), temp));
        }

        return perfMdls;
    }

    public static List<RepMdlin> getLastReportForASchool(String schID) {
        String date = DAO2.lastDateForASchool(schID, DB.report_admin.table,
                DB.report_admin.sch_id, DB.report_admin.date_report);
        List<RepMdlin> list = new ArrayList<>();
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        RepMdlin r = null;
        String table = DB.report_admin.table;

        Cursor c = null;

        if (!schID.equals("") || schID != null && !date.equals("") || date != null) {
            String sel = DB.report_admin.sch_id + " = ? AND " + DB.report_admin.date_report + " = ? ";

            String[] selArg = new String[]{schID, date};
            c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        //16 columns
                        r = new RepMdlin();
                        r.set_id(c.getString(c.getColumnIndex(DB.report_admin._ID)));
                        r.setSchID(c.getString(c.getColumnIndex(DB.report_admin.sch_id)));
                        r.setServerIDQues(c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)));
                        r.setQuesTitle(c.getString(c.getColumnIndex(DB.report_admin.questionTitle)));
                        r.setQues(c.getString(c.getColumnIndex(DB.report_admin.question)));
                        r.setAns(c.getString(c.getColumnIndex(DB.report_admin.answer)));
                        r.setAnsWeight(c.getString(c.getColumnIndex(DB.report_admin.answerWeight)));
                        r.setDetails(c.getString(c.getColumnIndex(DB.report_admin.details)));
                        r.setPriority(c.getString(c.getColumnIndex(DB.report_admin.priority)));
                        r.setServerQuestion(c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)));
                        r.setDateReport(c.getString(c.getColumnIndex(DB.report_admin.date_report)));
                        r.setSynced(c.getString(c.getColumnIndex(DB.report_admin.synced)));
                        r.setTimeLm(c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                        r.setNotify(c.getString(c.getColumnIndex(DB.report_admin.notify)));
                        r.setNotify_his(c.getString(c.getColumnIndex(DB.report_admin.notify_his)));
                        r.setN_update_date(c.getString(c.getColumnIndex(DB.report_admin.n_update_date)));

                        list.add(r);
                    } while (c.moveToNext());
                }
            }
        }
        if (c != null)
            c.close();


        return list;
    }

    public static boolean insertRepQues_Got_From_Server(List<RepQ> list) {
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;


        for (RepQ r : list) {
            cv = new ContentValues();
            cv.put(DB.admin_ques.server_id, r.getId());
            cv.put(DB.admin_ques.local_id, r.getLocalId());
            cv.put(DB.admin_ques.category_id, r.getInfrastructureQuestionCategoryId());
            cv.put(DB.admin_ques.category, r.getInfrastructureQuestionCategoryName());
            cv.put(DB.admin_ques.ques, r.getTitle());
            cv.put(DB.admin_ques.active, r.getActive());

            cv.put(DB.admin_ques.serverQuestion, "1");
            cv.put(DB.admin_ques.synced, "1");
            cv.put(DB.admin_ques.time_lm, H.currentDate_in_dateFormat1());


            String sel = DB.admin_ques.server_id + " = ? ";
            String[] selA = new String[]{r.getId()};
            count = db.update(DB.admin_ques.table, cv, sel, selA);
            if (count <= 0) {
                db.insert(DB.admin_ques.table, null, cv);
            }
        }
        return flag;
    }

    @Nullable
    public static RepQ getRepQuesyQuesID(@Nullable String quesID) {
        RepQ repQ = new RepQ();
        if (quesID == null)
            return repQ;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        Cursor c;
        String sel = DB.admin_ques.server_id + " = ? ";
        String[] selArg = new String[]{quesID};
        c = db.query(DB.admin_ques.table, null, sel, selArg, null, null, null);
        if (c.moveToFirst()) {
            do {
                repQ = new RepQ();
                repQ.setId(c.getString(c.getColumnIndex(DB.admin_ques.server_id)));
                repQ.setLocalId(c.getString(c.getColumnIndex(DB.admin_ques.local_id)));
                repQ.setTitle(c.getString(c.getColumnIndex(DB.admin_ques.ques)));
                repQ.setActive(c.getString(c.getColumnIndex(DB.admin_ques.active)));
                repQ.setInfrastructureQuestionCategoryId(c.getString(c.getColumnIndex(DB.admin_ques.category_id)));
                repQ.setInfrastructureQuestionCategoryName(c.getString(c.getColumnIndex(DB.admin_ques.category)));
            } while (c.moveToNext());
        }
        c.close();
        return repQ;
    }


    /*================================= Evaluation ======================================================================================*/

    public static String calcEvlPerformanceOfASch(List<EvlModelin> evlModelins) {
        String res = "0";
        int n = evlModelins.size();
        float sum = 0, avg = 0;
        for (int i = 0; i < n; i++) {
            sum += Float.parseFloat(H.evaluationPerfromCalc(evlModelins.get(i).getAsked(), evlModelins.get(i).getCorrect()));
        }

        if (sum > 0)
            avg = sum / evlModelins.size();
        res = String.valueOf(avg);
        return res;
    }


    public static List<EvlModelin> getEvaluationOfASchoolForASpeacificDate(String schID, String date) {
        /**from report_acad table*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        EvlModelin model = null;
        List<EvlModelin> list = new ArrayList<>();

        Cursor c = null;
        String sel = DB.report_acad.schID + " = ? AND " + DB.report_acad.date_report + " = ? ";

        String[] selArg = new String[]{schID, date};
        c = db.query(DB.report_acad.table, null, sel, selArg, null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    model = new EvlModelin(
                            c.getString(c.getColumnIndex(DB.report_acad.schID)),
                            c.getString(c.getColumnIndex(DB.report_acad.schName)),
                            c.getString(c.getColumnIndex(DB.report_acad.server_id_ques)),
                            c.getString(c.getColumnIndex(DB.report_acad.ques)),
                            c.getString(c.getColumnIndex(DB.report_acad.answer)),
                            c.getString(c.getColumnIndex(DB.report_acad.active)),
                            c.getString(c.getColumnIndex(DB.report_acad.synced)),
                            c.getString(c.getColumnIndex(DB.report_acad.server_ques)),
                            c.getString(c.getColumnIndex(DB.report_acad.grade)),
                            c.getString(c.getColumnIndex(DB.report_acad.time_lm)),
                            c.getString(c.getColumnIndex(DB.report_acad.date_report)),
                            c.getString(c.getColumnIndex(DB.report_acad.date_update)),
                            c.getString(c.getColumnIndex(DB.report_acad.totalStd)),
                            c.getString(c.getColumnIndex(DB.report_acad.attendance)),
                            c.getString(c.getColumnIndex(DB.report_acad.attempt)),
                            c.getString(c.getColumnIndex(DB.report_acad.asked)),
                            c.getString(c.getColumnIndex(DB.report_acad.correct)),
                            c.getString(c.getColumnIndex(DB.report_acad.perf))
                    );

                    model.setSubject(c.getString(c.getColumnIndex(DB.report_acad.subject)));
                    model.setChapter(c.getString(c.getColumnIndex(DB.report_acad.chapter)));

                    list.add(model);
                } while (c.moveToNext());
            }
        }
        if (c != null)
            c.close();


        return list;
    }


    public static List<List<EvlModelin>> getLast_n_Evl1Sch(String schID, int noOfLastEvl) {
        List<EvlModelin> temp = new ArrayList<>();
        List<List<EvlModelin>> listList = new ArrayList<>();
        List<String> dates = H.getUniqueDateFromATable(DB.report_acad.table, DB.report_acad.date_report);
        dates = H.lastDates(dates, noOfLastEvl);
        for (int i = 0; i < dates.size(); i++) {
            temp = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, dates.get(i));
            listList.add(temp);
        }
        return listList;
    }


    public static List<PerfMdl> getLast_n_EvlPerform1School(String schID, int noOfLastEvl) {
        List<PerfMdl> perfMdls = new ArrayList<>();
        List<List<EvlModelin>> listList = DAO2.getLast_n_Evl1Sch(schID, noOfLastEvl);
        List<EvlModelin> temp;
        int n = listList.size();

        for (int i = 0; i < n; i++) {
            temp = listList.get(i);
            if (temp.size() > 0)
                perfMdls.add(new PerfMdl(schID, temp.get(0).getDate_report(), DAO2.calcEvlPerformanceOfASch(temp)));
        }
        return perfMdls;
    }


    public static List<List<EvlModelin>> getLastEvlModelinAllSchool() {

        List<EvlModelin> evlModelins = new ArrayList<>();//list of repmodelin for a schoolSync
        List<List<EvlModelin>> listList = new ArrayList<>();//list of repModelinList of all schools
        String schid, lastDate;
        EvlModelin evlModelin;


        List<String> schIds = DAO2.all_unique_values_from_a_Column(DB.report_acad.table, DB.report_acad.schID);
        int n = schIds.size();
        for (int i = 0; i < n; i++) {
            schid = schIds.get(i);
            lastDate = DAO2.lastDateForASchool(schid, DB.report_acad.table,
                    DB.report_acad.schID, DB.report_acad.date_report);

            evlModelins = DAO2.getEvaluationOfASchoolForASpeacificDate(schid, lastDate);
            if (evlModelins.size() > 0)
                listList.add(evlModelins);//list of repmodelin for a schoolSync
        }


        return listList;
    }


    public static boolean insertNewQuestion(EvalQuesModel ques) {
        /*user defined question*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        boolean flag = false;
        ContentValues cv = new ContentValues();
        cv.put(DB.acad_ques.server_id, ques.getServer_id());
        cv.put(DB.acad_ques.local_id, ques.getLocal_id());
        cv.put(DB.acad_ques.grde_id, ques.getGradeID());
        cv.put(DB.acad_ques.ques, ques.getQues());
        cv.put(DB.acad_ques.answer, ques.getAnswer());
        cv.put(DB.acad_ques.server_question, ques.getServerQuestion());
        cv.put(DB.acad_ques.date_asking, ques.getDateAsking());
        cv.put(DB.acad_ques.date_range_asking, ques.getDateRangeAsking());
        cv.put(DB.acad_ques.synced, ques.getSynced());
        cv.put(DB.acad_ques.active, ques.getActive());
        cv.put(DB.acad_ques.time_lm, ques.getTimeLm());

        long row = db.insert(DB.acad_ques.table, null, cv);
        if (row >= 0) {
            flag = true;
        }

        return flag;
    }


    public static boolean insertEvlQues_Got_From_Server(List<EvlQ> list) {
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int count;
        ContentValues cv;
        String sel;
        String[] selA;


        for (EvlQ e : list) {
            cv = new ContentValues();
            cv.put(DB.acad_ques.server_id, e.getId());
            cv.put(DB.acad_ques.ques, e.getTitle());
            cv.put(DB.acad_ques.answer, e.getAnswer());
            cv.put(DB.acad_ques.active, e.getActive());
            cv.put(DB.acad_ques.grde_id, e.getGradeId());
            cv.put(DB.acad_ques.grade, e.getGradeName());

            cv.put(DB.acad_ques.subject, e.getSubject());
            cv.put(DB.acad_ques.chapter, e.getChapter());

            cv.put(DB.acad_ques.local_id, e.getLocalId());
            cv.put(DB.acad_ques.server_question, e.getServerQuestion());

            cv.put(DB.acad_ques.server_question, "1");
            cv.put(DB.acad_ques.synced, "1");
            cv.put(DB.acad_ques.time_lm, H.currentDate_in_dateFormat1());

            sel = DB.acad_ques.server_id + " = ? ";
            selA = new String[]{e.getId()};
            count = db.update(DB.acad_ques.table, cv, sel, selA);
            if (count <= 0) {
                db.insert(DB.acad_ques.table, null, cv);
            }
        }
        return flag;
    }

    @Nullable
    public static EvlQ getEvaluationQuesyQuesID(String quesID) {
        EvlQ evlQ = null;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        Cursor c;
        String sel = DB.acad_ques.server_id + " = ? ";
        String[] selArg = new String[]{quesID};
        c = db.query(DB.acad_ques.table, null, sel, selArg, null, null, null);
        if (c.moveToFirst()) {
            do {
                evlQ = new EvlQ();
                evlQ.setId(c.getString(c.getColumnIndex(DB.acad_ques.server_id)));
                evlQ.setLocalId(c.getString(c.getColumnIndex(DB.acad_ques.local_id)));
                evlQ.setTitle(c.getString(c.getColumnIndex(DB.acad_ques.ques)));
                evlQ.setActive(c.getString(c.getColumnIndex(DB.acad_ques.active)));
                evlQ.setGradeId(c.getString(c.getColumnIndex(DB.acad_ques.grde_id)));
                evlQ.setGradeName(c.getString(c.getColumnIndex(DB.acad_ques.grade)));
                evlQ.setServerQuestion(c.getString(c.getColumnIndex(DB.acad_ques.server_question)));
                evlQ.setActive(c.getString(c.getColumnIndex(DB.acad_ques.active)));
            } while (c.moveToNext());
        }
        c.close();
        return evlQ;
    }


    public static HashMap<String, List<String>> getEvaluationQuesSubjectsAndChapters(String gradeID/*school class means grade id*/) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        HashMap<String, List<String>> subsAndChapts = new HashMap<>();
        List<String> subjects = new ArrayList<>();
        List<String> chapters = new ArrayList<>();

        Cursor c = null;
        String[] proj = new String[]{DB.acad_ques.subject, DB.acad_ques.chapter};
        String selection = DB.acad_ques.grde_id + " = ?";
        String[] selectionArg = new String[]{gradeID};
        c = db.query(DB.acad_ques.table, proj, selection, selectionArg, null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    subjects.add(c.getString(c.getColumnIndex(DB.acad_ques.subject)));
                    chapters.add(c.getString(c.getColumnIndex(DB.acad_ques.chapter)));
                } while (c.moveToNext());
            }

        }

        subjects = new ArrayList<String>(new HashSet<String>(subjects));
        chapters = new ArrayList<String>(new HashSet<String>(chapters));

        subsAndChapts.put("subject", subjects);
        subsAndChapts.put("chapter", chapters);
        return subsAndChapts;
        /**
         this will return a hashmap of list of unique(without repetition) subjects & chapters

         key "subject" = arraylist
         "chapter" = arraylist

         * */
    }

    public static List<String> getSubjectList(String gradeID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> subjectList = new ArrayList<>();

        Cursor c = null;
        String[] proj = new String[]{DB.acad_ques.subject};
        String selection = DB.acad_ques.grde_id + " = ?";
        String[] selectionArg = new String[]{gradeID};
        c = db.query(DB.acad_ques.table, proj, selection, selectionArg, null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    subjectList.add(c.getString(c.getColumnIndex(DB.acad_ques.subject)));
                } while (c.moveToNext());
            }

        }

        subjectList = new ArrayList<String>(new HashSet<String>(subjectList));// for making uniques

        return subjectList;
    }

    public static List<String> getChapterList(String gradeID, String subject) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> chapterList = new ArrayList<>();

        Cursor c = null;
        String[] proj = new String[]{DB.acad_ques.chapter};
        String selection = DB.acad_ques.grde_id + " = ? AND " + DB.acad_ques.subject + " = ?";
        String[] selectionArg = new String[]{gradeID, subject};
        c = db.query(DB.acad_ques.table, proj, selection, selectionArg, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    chapterList.add(c.getString(c.getColumnIndex(DB.acad_ques.chapter)));
                } while (c.moveToNext());
            }

        }

        chapterList = new ArrayList<String>(new HashSet<String>(chapterList));// for making uniques

        return chapterList;
    }


/*================================= notification ======================================================================================*/

    public static int noOfProblemExistInEnvRepForNotif() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        int noOfProblemExist = 0;
        Cursor c = null;
        String sel = DB.report_admin.notify + " = ? ";
        String[] selArg = new String[]{"1"};
        c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);
        if (c != null) {
            noOfProblemExist = c.getCount();
            c.close();
        }
        return noOfProblemExist;
    }


    public static List<RepMdlin> getAllExistingProblemsFromEnvReport() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        RepMdlin r;
        List<RepMdlin> l = new ArrayList<>();
        Cursor c = null;


        String sel = /*DB.report_admin.answer + " = ? AND " + */DB.report_admin.notify + " = ? ";
        String[] selArg = new String[]{"1"};
        c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    //18 columns
                    r = new RepMdlin();
                    r.set_id(c.getString(c.getColumnIndex(DB.report_admin._ID)));
                    r.setSchID(c.getString(c.getColumnIndex(DB.report_admin.sch_id)));
                    r.setServerIDQues(c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)));
                    r.setQuesTitle(c.getString(c.getColumnIndex(DB.report_admin.questionTitle)));
                    r.setQues(c.getString(c.getColumnIndex(DB.report_admin.question)));
                    r.setAns(c.getString(c.getColumnIndex(DB.report_admin.answer)));
                    r.setAnsWeight(c.getString(c.getColumnIndex(DB.report_admin.answerWeight)));
                    r.setDetails(c.getString(c.getColumnIndex(DB.report_admin.details)));
                    r.setPriority(c.getString(c.getColumnIndex(DB.report_admin.priority)));
                    r.setServerQuestion(c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)));
                    r.setDateReport(c.getString(c.getColumnIndex(DB.report_admin.date_report)));
                    r.setDateTarget(c.getString(c.getColumnIndex(DB.report_admin.date_target)));
                    r.setDateSolve(c.getString(c.getColumnIndex(DB.report_admin.date_solve)));
                    r.setSynced(c.getString(c.getColumnIndex(DB.report_admin.synced)));
                    r.setTimeLm(c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                    r.setNotify(c.getString(c.getColumnIndex(DB.report_admin.notify)));
                    r.setNotify_his(c.getString(c.getColumnIndex(DB.report_admin.notify_his)));
                    r.setN_update_date(c.getString(c.getColumnIndex(DB.report_admin.n_update_date)));

                    l.add(r);
                } while (c.moveToNext());
            }
        }

        if (c != null) {
            c.close();
        }
        return l;
    }


    public static List<RepMdlin> getAllHistoryNofifRep() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        RepMdlin r;
        List<RepMdlin> l = new ArrayList<>();
        Cursor c = null;


        String sel = DB.report_admin.notify + " = ? AND " + DB.report_admin.notify_his + " = ? ";
        String[] selArg = new String[]{"0", "1"};
        c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    //16 columns
                    r = new RepMdlin();
                    r.set_id(c.getString(c.getColumnIndex(DB.report_admin._ID)));
                    r.setSchID(c.getString(c.getColumnIndex(DB.report_admin.sch_id)));
                    r.setServerIDQues(c.getString(c.getColumnIndex(DB.report_admin.server_id_ques)));
                    r.setQuesTitle(c.getString(c.getColumnIndex(DB.report_admin.questionTitle)));
                    r.setQues(c.getString(c.getColumnIndex(DB.report_admin.question)));
                    r.setAns(c.getString(c.getColumnIndex(DB.report_admin.answer)));
                    r.setAnsWeight(c.getString(c.getColumnIndex(DB.report_admin.answerWeight)));
                    r.setDetails(c.getString(c.getColumnIndex(DB.report_admin.details)));
                    r.setPriority(c.getString(c.getColumnIndex(DB.report_admin.priority)));
                    r.setServerQuestion(c.getString(c.getColumnIndex(DB.report_admin.serverQuestion)));
                    r.setDateReport(c.getString(c.getColumnIndex(DB.report_admin.date_report)));
                    r.setDateTarget(c.getString(c.getColumnIndex(DB.report_admin.date_target)));
                    r.setDateSolve(c.getString(c.getColumnIndex(DB.report_admin.date_solve)));
                    r.setSynced(c.getString(c.getColumnIndex(DB.report_admin.synced)));
                    r.setTimeLm(c.getString(c.getColumnIndex(DB.report_admin.time_lm)));
                    r.setNotify(c.getString(c.getColumnIndex(DB.report_admin.notify)));
                    r.setNotify_his(c.getString(c.getColumnIndex(DB.report_admin.notify_his)));
                    r.setN_update_date(c.getString(c.getColumnIndex(DB.report_admin.n_update_date)));

                    l.add(r);
                } while (c.moveToNext());
            }
        }

        if (c != null) {
            c.close();
        }
        return l;
    }


//=============================== payments ==========================================================================================

    public static boolean isPaymentDataAvailable() {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        if (DAO.isTableEmpty(db, DB.payments.table))
            return false;
        else return true;
    }

    public static boolean insertPaymentData(PayMdl payMdl, String schID, String gradeID, String year) {
        boolean flag = true;
        long update;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        ContentValues cv = null;
        List<PayMdlInner> payMdlInnerList = H.PayMdl_to_PayMdlInner(payMdl, schID, gradeID, year);


        // db.delete(DB.payments.table, null, null);
        for (PayMdlInner p : payMdlInnerList) {
            cv = new ContentValues();
            cv.put(DB.payments.sch_id, p.getSch_id());
            cv.put(DB.payments.grade_id, p.getGrade_id());
            cv.put(DB.payments.sch_name, p.getSch_name());
            cv.put(DB.payments.std_id, p.getStd_id());
            cv.put(DB.payments.std_name, p.getStd_name());
            cv.put(DB.payments.roll, p.getRoll());
            cv.put(DB.payments.payable, p.getPayable());
            cv.put(DB.payments.paid, p.getPaid());
            cv.put(DB.payments.month, p.getMonth());
            cv.put(DB.payments.year, p.getYear());
            cv.put(DB.payments.category, p.getCategory());
            cv.put(DB.payments.type, p.getType());
            cv.put(DB.payments.time_lm, p.getTime_lm());


            String where = DB.payments.sch_id + " = ? AND " + DB.payments.grade_id + " = ? AND "
                    + DB.payments.std_id + " = ? AND " + DB.payments.month + " = ? AND "
                    + DB.payments.year + " = ? ";
            String[] whereArg = new String[]{schID, gradeID, p.getStd_id(), p.getMonth(), p.getYear()};
            update = db.update(DB.payments.table, cv, where, whereArg);
            if (update <= 0) {
                db.insert(DB.payments.table, null, cv);
            }
        }

        return flag;
    }


    public static boolean insertPaymentDataInSchTable_a_Sch(PayMdl payMdl, String schID, String gradeID, String year) {
        boolean flag = true;
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        ContentValues cv = null;
        List<PayMdlInner> payMdlInnerList = H.PayMdl_to_PayMdlInner(payMdl, schID, gradeID, year);
        double payableTotal = 0, paidTotal = 0;

        for (PayMdlInner p : payMdlInnerList) {
            payableTotal += Double.parseDouble(p.getPayable());
            paidTotal += Double.parseDouble(p.getPaid());
        }

        cv = new ContentValues();
        cv.put(DB.schools.total_bill, String.format("%.2f", payableTotal));
        cv.put(DB.schools.paid, String.format("%.2f", paidTotal));

        String where = DB.schools.s_id + " = ? AND " + DB.schools.grade_id + " = ?";
        String[] whereArg = new String[]{schID, gradeID};

        db.update(DB.schools.table, cv, where, whereArg);


        return flag;
    }


    public static List<PayMdlInner> getPaymentOf_a_School(String schID, String gradeID) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<PayMdlInner> list = new ArrayList<>();
        PayMdlInner p;
        Cursor c = null;

        String where = DB.payments.sch_id + " = ? AND " + DB.payments.grade_id + " = ? ";

        where = DB.payments.sch_id + " = ? ";
        String[] whereArg = new String[]{schID};

       /* where=null;
        whereArg=null;*/
        c = db.query(DB.payments.table, null, where, whereArg, null, null, null);

        if (c.moveToFirst()) {
            do {
                p = new PayMdlInner();
                p.setStd_id(c.getString(c.getColumnIndex(DB.payments.std_id)));
                p.setStd_name(c.getString(c.getColumnIndex(DB.payments.std_name)));
                p.setRoll(c.getString(c.getColumnIndex(DB.payments.roll)));
                p.setPaid(c.getString(c.getColumnIndex(DB.payments.paid)));
                p.setPayable(c.getString(c.getColumnIndex(DB.payments.payable)));

                list.add(p);
            } while (c.moveToNext());
        }
        if (c != null)
            c.close();

        return list;
    }


    public static List<PayMdlInner> getPaymentOf_a_School_Monthwise(String schID, String gradeID, String month, String year) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<PayMdlInner> list = new ArrayList<>();
        PayMdlInner p;
        Cursor c = null;

        String where = DB.payments.sch_id + " = ? AND " + DB.payments.grade_id + " = ? ";

        where = DB.payments.sch_id + " = ? ";

        where = DB.payments.sch_id + " = ? AND " + DB.payments.grade_id + " = ? AND " +
                DB.payments.month + " = ? AND " + DB.payments.year + " = ? ";

        String[] whereArg = new String[]{schID, gradeID, month, year};

       /* where=null;
        whereArg=null;*/
        c = db.query(DB.payments.table, null, where, whereArg, null, null, null);

        if (c.moveToFirst()) {
            do {
                p = new PayMdlInner();
                p.setStd_id(c.getString(c.getColumnIndex(DB.payments.std_id)));
                p.setStd_name(c.getString(c.getColumnIndex(DB.payments.std_name)));
                p.setRoll(c.getString(c.getColumnIndex(DB.payments.roll)));
                p.setPaid(c.getString(c.getColumnIndex(DB.payments.paid)));
                p.setPayable(c.getString(c.getColumnIndex(DB.payments.payable)));

                list.add(p);
            } while (c.moveToNext());
        }
        if (c != null)
            c.close();

        return list;
    }


}


