/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.newAPI.SchMdl;
import com.example.f.schMon.model.newAPI.SchMdlList;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.report_environment.RepMdlin;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlA;
import com.example.f.schMon.view.sync_mir.MdlSync.RepA;
import com.example.f.schMon.view.sync_mir.MdlSync.RepQ;
import com.example.f.schMon.view.sync_mir.MdlSync.TimeMdl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mir Ferdous on 01/11/2017.
 */

public class SyncH {
    private static final String TAG = SyncH.class.getName();/*sync helper*/

    //============================= rep =========================================================================================
    public static List<RepA> getReportAnsForSync(boolean onlyUnsynced/**if true only unsynced reps if false all reps*/) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<RepA> list = new ArrayList<>();
        RepA r;
        Cursor c = null;

        if (onlyUnsynced) {
            String sel = DB.report_admin.synced + " = ? ";
            String[] selArg = new String[]{"0"};
            c = db.query(DB.report_admin.table, null, sel, selArg, null, null, null);
        } else {
            c = db.query(DB.report_admin.table, null, null, null, null, null, null);
        }

        if (c.moveToFirst()) {
            do {
                r = new RepA();
                r.id = c.getString(c.getColumnIndex(DB.report_admin._ID));
                r.instituteId = c.getString(c.getColumnIndex(DB.report_admin.sch_id));
                r.infrastructureQuestionId = c.getString(c.getColumnIndex(DB.report_admin.server_id_ques));
                r.submittedDate = c.getString(c.getColumnIndex(DB.report_admin.date_report));
                r.answer = Integer.parseInt(c.getString(c.getColumnIndex(DB.report_admin.answer)));
                if (c.getString(c.getColumnIndex(DB.report_admin.priority)) != null) {
                    if (!c.getString(c.getColumnIndex(DB.report_admin.priority)).equals(""))
                        r.priority = Integer.parseInt(c.getString(c.getColumnIndex(DB.report_admin.priority)));
                }
                r.details = c.getString(c.getColumnIndex(DB.report_admin.details));
                r.localId = c.getString(c.getColumnIndex(DB.report_admin.local_id_ques));
                r.modifiedDate = c.getString(c.getColumnIndex(DB.report_admin.time_lm));
                r.targetDate = c.getString(c.getColumnIndex(DB.report_admin.date_target));
                r.solveDate = c.getString(c.getColumnIndex(DB.report_admin.date_solve));
                list.add(r);
            } while (c.moveToNext());
        }
        if (c != null)
            c.close();
        return list;
    }

    public static int updateDB_on_SyncSuccessRep(List<RepA> list) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String sel = null;
        String[] selA;
        ContentValues cv;
        int count = 0;
        int temp = 0;
        for (RepA r : list) {
            cv = new ContentValues();
            cv.put(DB.report_admin.synced, "1");
            sel = DB.report_admin.sch_id + " = ? AND " +
                    DB.report_admin.date_report + " = ? AND " +
                    DB.report_admin.server_id_ques + " = ? ";
            selA = new String[]{r.instituteId, r.submittedDate, r.infrastructureQuestionId};
            temp = db.update(DB.report_admin.table, cv, sel, selA);
            count += temp;
        }
        return count;//how many row synced
    }

    public static List<String> setOtherDataSyncOldRep(List<RepA> repAS) {
        boolean f = false;
        List<String> listOfNotFoundQues = new ArrayList<>();
        String quesID = null;
        for (int i = 0; i < repAS.size(); i++) {
            RepA repA = repAS.get(i);
            if (repA.getInfrastructureQuestion() == null) {
                RepQ repQ = DAO2.getRepQuesyQuesID(repA.getInfrastructureQuestionId());
                School s = DAO.getInfoOfASchool(repA.instituteId);

                repA.setInfrastructureQuestion(repQ.getTitle());
                repA.setQues_category_id(repQ.getInfrastructureQuestionCategoryId());
                repA.setCategory(repQ.getInfrastructureQuestionCategoryName());
                repA.setServer_ques("1");
                repA.setSynced("1");
                repA.setNotify("0");

                repA.setSchName(s.schName);
                repA.setGrade(s.grade);
                repA.setGradeID(s.gradeID);
                repA.setSchCode(s.schCode);


                if (repQ == null)
                    listOfNotFoundQues.add(repA.getInfrastructureQuestionId());
            /*
            String where = DB.report_admin.server_id_ques + " = ? ";
            String[] whereArg = new String[]{repAS.get(i).getInfrastructureQuestionId()};
            DAO.updateSingleColumnData(DB.report_admin.table, DB.report_admin.question, repQ.getTitle()
                    , where, whereArg);
*/
            }
        }
        return listOfNotFoundQues;
    }

    public static void updatePerformTableOnSyncOldEnvironment() {  //environment
        /* last performance for dash */
        List<List<RepMdlin>> listList = DAO2.getLastRepModelinOfAllSchool();


        for (int i = 0; i < listList.size(); i++) {
            List<RepMdlin> list = listList.get(i);//reps of a school(all ques/ans of a school)

            String dateT = list.get(0).getDateReport();
            String performace = H.calcRepPerformForASch(list);//calcReportPerformForASch(list);
            School s = DAO.getInfoOfASchool(list.get(0).getSchID());
            //calculate performace of single report
            LastPerfMdl lpm = new LastPerfMdl(s.getSchID(), s.getSchName(), performace, dateT,
                    null, null, null, null);
            lpm.setCode(s.getSchCode());
            lpm.setEduType(s.getEdu_type());
            lpm.setGrade(s.getGrade());
            boolean dbInsertSuccess1 = DAO.insertLastPerformAll(s.getSchID(), lpm);

            //monthly school visit count
            try {
                SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
                DAO.insertLastVisitTimeSchool(list.get(0).getSchID(), df1.parse(list.get(0).getDateReport()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }


    }

    public static void updatePerformTableOnSyncOldEvalution() {//evaluation
          /* last performance table for dashboard */
        String date, perf;

        List<List<EvlModelin>> listList = DAO2.getLastEvlModelinAllSchool();

        for (int i = 0; i < listList.size(); i++) {
            List<EvlModelin> list = listList.get(i);
            School s = DAO.getInfoOfASchool(list.get(0).getSchID());
            perf = H.calcEvlPerfOfAEvalReport(list);
            date = list.get(0).getDate_report();
            LastPerfMdl lpm = new LastPerfMdl(
                    s.schID, s.schName, null, null, perf, date, null, null);
            lpm.setCode(s.schCode);
            lpm.setEduType(s.edu_type);
            lpm.setGrade(s.grade);
            DAO.insertLastPerformAll(s.schID, lpm);
        }

    }


    //==================================== evl ==================================================================================


    public static List<String> setOtherDataSyncOldEvl(List<EvlA> evlAs) {
        boolean f = false;
        List<String> listOfNotFoundQues = new ArrayList<>();
        EvlModelin evlModelin;
        String quesID = null;
        for (int i = 0; i < evlAs.size(); i++) {
            EvlA evlA = evlAs.get(i);

            evlModelin = DAO.getEvlQuesInfoFromQuesServerID(evlA.getAcademicQuestionId(), evlA.getAcademicQuestionLocalId());
            evlA.setQuestionTitle(evlModelin.getQues());
            evlA.setQuestionAnswer(evlModelin.getAnswer());
            evlA.setActive(evlModelin.getActive());
            evlA.setServerQuestion(evlModelin.getServer_ques());
            evlA.setSynced("1");
            evlA.setSubject(evlModelin.getSubject());
            evlA.setChapter(evlModelin.getChapter());
            /*evlA.setGradeID(evlModelin.getGradeID());
            evlA.setGrade(evlModelin.getGrade());*/

            School s = DAO.getInfoOfASchool(evlA.instituteId);
            evlA.setSchName(s.schName);
            evlA.setGrade(s.grade);
            evlA.setGradeID(s.gradeID);
            evlA.setSchCode(s.schCode);
            evlA.setTotalStd(s.totalStd);


        }
        return listOfNotFoundQues;
    }

    public static List<EvlA> getEvaluationAnsForSync(boolean onlyUnsynced/*if true only unsynced reps if false all evals*/) {
        List<EvlA> list = new ArrayList<>();
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        EvlA e;
        Cursor c = null;


        if (onlyUnsynced) {
            String sel = DB.report_acad.synced + " = ? ";
            String[] selArg = new String[]{"0"};
            c = db.query(DB.report_acad.table, null, sel, selArg, null, null, null);
        } else {
            c = db.query(DB.report_acad.table, null, null, null, null, null, null);
        }

        if (c.moveToFirst()) {
            do {
                e = new EvlA();
                e.id = c.getString(c.getColumnIndex(DB.report_acad.server_id_answer));
                e.academicQuestionId = c.getString(c.getColumnIndex(DB.report_acad.server_id_ques));
                e.academicQuestionLocalId = c.getString(c.getColumnIndex(DB.report_acad.local_id_ques));
                e.localId = c.getString(c.getColumnIndex(DB.report_acad.local_id_answer));
                e.instituteId = c.getString(c.getColumnIndex(DB.report_acad.schID));
                e.submittedDate = c.getString(c.getColumnIndex(DB.report_acad.date_report));
                e.modifiedDate = c.getString(c.getColumnIndex(DB.report_acad.time_lm));

                if (c.getString(c.getColumnIndex(DB.report_acad.attendance)) != null) {
                    if (!c.getString(c.getColumnIndex(DB.report_acad.attendance)).equals(""))
                        e.totalAttendance = Integer.parseInt(c.getString(c.getColumnIndex(DB.report_acad.attendance)));
                }
                if (c.getString(c.getColumnIndex(DB.report_acad.attempt)) != null) {
                    if (!c.getString(c.getColumnIndex(DB.report_acad.attempt)).equals(""))
                        e.totalAttempt = Integer.parseInt(c.getString(c.getColumnIndex(DB.report_acad.attempt)));
                }

                if (c.getString(c.getColumnIndex(DB.report_acad.asked)) != null) {
                    if (!c.getString(c.getColumnIndex(DB.report_acad.asked)).equals(""))
                        e.totalAsked = Integer.parseInt(c.getString(c.getColumnIndex(DB.report_acad.asked)));
                }

                if (c.getString(c.getColumnIndex(DB.report_acad.correct)) != null) {
                    if (!c.getString(c.getColumnIndex(DB.report_acad.correct)).equals(""))
                        e.totalAnswer = Integer.parseInt(c.getString(c.getColumnIndex(DB.report_acad.correct)));
                }


                e.performance = c.getString(c.getColumnIndex(DB.report_acad.perf));
                e.questionTitle = c.getString(c.getColumnIndex(DB.report_acad.ques));
                e.questionAnswer = c.getString(c.getColumnIndex(DB.report_acad.answer));
                e.questionGradeId = c.getString(c.getColumnIndex(DB.report_acad.grade_id));
                e.serverQuestion = c.getString(c.getColumnIndex(DB.report_acad.server_ques));
                list.add(e);
            } while (c.moveToNext());
        }


        if (c != null)
            c.close();
        return list;
    }

    public static int updateDB_on_SyncSuccessEvl(List<EvlA> list) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String sel = null;
        String[] selA;
        ContentValues cv;
        int count = 0;
        int temp = 0;
        for (EvlA r : list) {
            cv = new ContentValues();
            cv.put(DB.report_acad.synced, "1");
            sel = DB.report_acad.schID + " = ? AND " +
                    DB.report_acad.grade_id + " = ? AND " +
                    DB.report_acad.date_report + " = ? AND " +
                    DB.report_acad.server_id_ques + " = ? ";
            selA = new String[]{r.instituteId, r.questionGradeId, r.submittedDate, r.academicQuestionId};
            temp = db.update(DB.report_acad.table, cv, sel, selA);
            count += temp;
        }
        return count;//how many row synced
    }

    //==================================== time ==================================================================================

    public static boolean isServerTimeSystemTimeSame() throws IOException {
        boolean f = false;
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        APInterface api = Client.getAPInterface();
        TimeMdl timeMdlCall = api.getTimeFromServer(A.getU(), A.getP()).execute().body();
        Log.d(TAG, String.valueOf(timeMdlCall));
        String timeSys = df1.format(new Date(System.currentTimeMillis()));
        String timeServer = timeMdlCall.getModel();

        if (timeServer != null) {
            if (timeServer.equals(timeSys))
                f = true;
            else f = false;
        }

        return f;
    }


    //======================================================================================================================
    public static List<String> unSyncSchools(List<SchMdl> schMdlList) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> serverID = new ArrayList<>();
        List<String> unSyncID;
        for (int i = 0; i < schMdlList.size(); i++) {
            serverID.add(schMdlList.get(i).getId());
        }

        List<String> syncID = DAO.all_values_from_a_Column(db, DB.schools.table, DB.schools.s_id);

        serverID.removeAll(syncID);
        unSyncID = serverID;

        return unSyncID;
    }


    static APInterface api;

    public static List<String> unSyncSchools() {
        final List<String> localIDS = new ArrayList<>();
        final List<String> unsyncIDS = new ArrayList<>();
        final List<String> serverIDS = new ArrayList<>();
        api = Client.getAPInterface();

        api.getSchList(A.getU(), A.getP(), "0").enqueue(new Callback<SchMdlList>() {
            @Override
            public void onResponse(Call<SchMdlList> call, Response<SchMdlList> response) {
                api.getSchList(A.getU(), A.getP(), response.body().getTotal()).enqueue(new Callback<SchMdlList>() {
                    @Override
                    public void onResponse(Call<SchMdlList> call, Response<SchMdlList> response) {
                        List<SchMdl> schMdls = response.body().getModel();
                        for (SchMdl s : schMdls) {
                            serverIDS.add(s.getId());
                        }
                        localIDS.addAll(DAO2.all_unique_values_from_a_Column(DB.schools.table, DB.schools.s_id));


                        serverIDS.removeAll(localIDS);
                        unsyncIDS.addAll(serverIDS);


                    }

                    @Override
                    public void onFailure(Call<SchMdlList> call, Throwable t) {

                    }

                });

            }

            @Override
            public void onFailure(Call<SchMdlList> call, Throwable t) {

            }
        });


        return unsyncIDS;
    }

    //========================= db change on sync =================================================
    public static void syncDone1Report() {
    }

    public static void syncDone1Evaluation() {
    }


//======================== json related =============================================

    public static List<RepA> repNullToBlankString(List<RepA> repAList) {
        for (RepA r : repAList) {
            r.nullToBlankString();
        }
        return repAList;
    }

    public static List<EvlA> evlNullToBlankString(List<EvlA> evlAList) {
        for (EvlA e : evlAList) {
            e.nullToBlankString();
        }
        return evlAList;
    }


    //=================== global ======================================================================================================
    public static boolean isMyServiceRunning(Class<?> serviceClass, Activity activity) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}

