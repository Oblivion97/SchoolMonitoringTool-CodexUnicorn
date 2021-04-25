/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.model.newAPI.SchMdl;
import com.example.f.schMon.model.newAPI.SchMdlList;
import com.example.f.schMon.model.newAPI.SchMdl_Id;
import com.example.f.schMon.model.newAPI.StdMdl;
import com.example.f.schMon.model.newAPI.StdMdlList;
import com.example.f.schMon.model.newAPI.StdMdl_id;
import com.example.f.schMon.model.newAPI.TchrMdl;
import com.example.f.schMon.model.newAPI.TchrMdl_id;
import com.example.f.schMon.model.newAPI.TehMdlList;
import com.example.f.schMon.model.oldAPI.UserModel;
import com.example.f.schMon.view.payments.paymdl.PayMdl;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlA;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlAList;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlQ;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlQList;
import com.example.f.schMon.view.sync_mir.MdlSync.RepA;
import com.example.f.schMon.view.sync_mir.MdlSync.RepAList;
import com.example.f.schMon.view.sync_mir.MdlSync.RepQ;
import com.example.f.schMon.view.sync_mir.MdlSync.RepQList;
import com.example.f.schMon.view.sync_mir.MdlSync.Respnse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * Created by Mir Ferdous on 10/22/2017.
 */

public class SyncSynchronous {
    private static final String TAG = SyncSynchronous.class.getName();
    private Context context;
    private APInterface api = Client.getAPInterface();
    private long bytes = 0;
    /*sync helper*/
    private String maxStd, maxSch, maxTchr;//total no of models is max parameter of get list query
    private Gson gson = new Gson();
    SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);

    public synchronized long dataCost(long count) {
        bytes = bytes + count;
        return bytes;
    }


    public void syncAll() throws Exception {
        schoolSync();
        teacherSync();
        studentSync();
        dwnldEnvironmentQues();
        dwnldEvlQues();
       /* SimpleDateFormat df8 = new SimpleDateFormat("yyyy");
        String currentYear = df8.format(new Date(System.currentTimeMillis()));
        syncPaymentAllSchool(currentYear);*/
    }


    //========================= Old Reports Sync (Environment & Evaluation)================================================================================================
    //old evaluation and environment
    public void dwnldEnvironmentAndEvaluationOld() throws Exception {
        //school dwnld
        if (DAO.isTableEmpty(db, DB.schools.table)) {
            schoolSync();
        }

        //environment dwnld
        dwnldEnvironmentQues();/*before downloading old reports questions are downloaded.because some in old report api
        //only question id is returned*/
        RepAList repAList = api.getOldRepAll(A.getU(), A.getP()).execute().body();
        List<RepA> repAS = repAList.getModel();
        SyncH.setOtherDataSyncOldRep(repAS);
        DAO.insertRepOldDownlded(repAS);
        SyncH.updatePerformTableOnSyncOldEnvironment(); /* last performance table for dashboard */

        //evaluation dwnld
        dwnldEvlQues();
        EvlAList evlAList = api.getOldEvlAll(A.getU(), A.getP()).execute().body();
        List<EvlA> evlAS = evlAList.getModel();
        SyncH.setOtherDataSyncOldEvl(evlAS);
        DAO.insertEvlOldDownlded(evlAS);
        SyncH.updatePerformTableOnSyncOldEvalution(); /* last performance table for dashboard */
    }


    //======================== Report evironment ================================================================================

    public void dwnldEnvironmentQues() throws Exception {
        if (DAO.isTableEmpty(db, DB.schools.table)) {
            schoolSync();
        }
        /** download Report Environment questions from server*/
        RepQList repQList = api.getRepQuesAll(A.getU(), A.getP()).execute().body();
        List<RepQ> repQs = null;
        if (repQList != null) {
            repQs = repQList.getModel();
            if (repQs != null) {
                DAO2.insertRepQues_Got_From_Server(repQs);
            }
        }
    }


    public Respnse postRepAns(boolean onlyUnsynced/**if true only unsynced reps if false all reps*/) throws Exception {
        //post rep unsynced
        List<RepA> repAns = SyncH.getReportAnsForSync(onlyUnsynced);
        repAns = SyncH.repNullToBlankString(repAns);
        Log.d(TAG, gson.toJson(repAns));


        // Respnse respnse = api.saveRep(A.getU(), A.getP(), repAns.toArray(new RepA[repAns.size()])).execute().body();
        Respnse respnse = api.postReportList(A.getU(), A.getP(), repAns).execute().body();
        if (respnse != null) {
            if (respnse.getSuccess().equalsIgnoreCase("1")) {
                SyncH.updateDB_on_SyncSuccessRep(repAns);
            }
        }
        return respnse;
    }
    //======================== Evaluation ================================================================================

    public void dwnldEvlQues() throws Exception {
        if (DAO.isTableEmpty(db, DB.schools.table)) {
            schoolSync();
        }

        /** download evaluation questions from server*/
        EvlQList evlQList = api.getEvlQuesAll(A.getU(), A.getP()).execute().body();
       // Log.d(TAG, evlQList.toString());

        List<EvlQ> evlQs = null;
        if (evlQList != null) {
            evlQs = evlQList.getModel();
            if (evlQs != null) {
                DAO2.insertEvlQues_Got_From_Server(evlQs);
            }
        }
    }

    public Respnse postEvlAns(boolean onlyUnsynced/*if true only unsynced reps if false all evals*/) throws Exception {
        //post evl unsynced
        List<EvlA> evlAns = SyncH.getEvaluationAnsForSync(onlyUnsynced);
        evlAns = SyncH.evlNullToBlankString(evlAns);

        //  Log.d(TAG, gson.toJson(evlAns));

        // Respnse respnse = api.saveEvl(A.getU(), A.getP(), (EvlA[]) evlAns.toArray()).execute().body();
        Respnse respnse = api.postEvaluationList(A.getU(), A.getP(), evlAns).execute().body();
        if (respnse != null) {
            if (respnse.getSuccess().equalsIgnoreCase("1")) {
                SyncH.updateDB_on_SyncSuccessEvl(evlAns);
            }
        }
        return respnse;
    }

    //======================== schoolSync ================================================================================
    private List<SchMdl> schMdls;
    private SchMdlList schMdlList;
    private SchMdl_Id schMdl_id;

    public void schoolSync() throws Exception {
        schMdlList = api.getSchList(A.getU(), A.getP(), "0").execute().body();
        maxSch = schMdlList.getTotal();
        Log.d(TAG, "Total school: " + maxSch);
         /*=========sch basics==================*/
        schMdlList = api.getSchList(A.getU(), A.getP(), maxSch).execute().body();
        schMdls = schMdlList.getModel();
        // Log.d(TAG, schMdls.toString());
        if (schMdlList.getSuccess().equalsIgnoreCase("1")) {
            DAO.insertSchool_ListCall(schMdlList.getModel());
        }
         /*=========sch details==================*/
        for (int i = 0; i < schMdls.size(); i++) {
            schMdl_id = api.getSchByID(schMdls.get(i).getId(), A.getU(), A.getP()).execute().body().getModel();
            //  Log.d(TAG, schMdl_id.toString());
            DAO.insertASchool_idCall(schMdl_id.getId(), schMdl_id.getPoId(), schMdl_id.getLocationId(), schMdl_id.getCurrentGradeId());
        }
    }


    //======================== teacherSync ===============================================================================
    List<TchrMdl> tchrMdls;
    TehMdlList tehMdlList;
    TchrMdl_id tchrMdl_id;

    public void teacherSync() throws Exception {
        tehMdlList = api.getTehrList(A.getU(), A.getP(), "0").execute().body();
        maxTchr = tehMdlList.getTotal();
        Log.d(TAG, "Total teacher: " + maxTchr);
         /*========= teacherSync basics==================*/
        tehMdlList = api.getTehrList(A.getU(), A.getP(), maxTchr).execute().body();
        tchrMdls = tehMdlList.getModel();
        //   Log.d(TAG, tehMdlList.toString());
        if (tehMdlList.getSuccess().equalsIgnoreCase("1")) {
            DAO.insertTeacher_ListCall(tehMdlList.getModel());
        }
        /*=========teacherSync details==================*/
        for (int i = 0; i < tchrMdls.size(); i++) {
            tchrMdl_id = api.getTehrByID(tchrMdls.get(i).getId(), A.getU(), A.getP()).execute().body().getModel();
            //  Log.d(TAG, tchrMdl_id.toString());
            DAO.insertATeacher_idCall(tchrMdl_id.getId(), tchrMdl_id.getInstituteId(), tchrMdl_id.getPresentAddress(),
                    tchrMdl_id.getGender(), tchrMdl_id.getEducation());
        }
    }


    //======================== stduents ==============================================================================
    List<StdMdl> stdMdls;
    StdMdlList stdMdlList;
    StdMdl_id stdMdl_id;

    public void studentSync() throws Exception {
        stdMdlList = api.getStdList(A.getU(), A.getP(), "0").execute().body();
        maxStd = stdMdlList.getTotal();
        Log.d(TAG, "Total student: " + maxStd);
        /*========= stduentSync basics==================*/
        stdMdlList = api.getStdList(A.getU(), A.getP(), maxStd).execute().body();
        stdMdls = stdMdlList.getModel();
        //  Log.d(TAG, stdMdlList.toString());
        if (stdMdlList.getSuccess().equalsIgnoreCase("1")) {
            DAO.insertStudent_ListCall(stdMdlList.getModel());
        }
        /*=========  studentDetails ==================*/
        for (int i = 0; i < stdMdls.size(); i++) {
            stdMdl_id = api.getStdByID(stdMdls.get(i).getId(), A.getU(), A.getP()).execute().body().getModel();
            //   Log.d(TAG, stdMdl_id.toString());
            DAO.insertAStudent_idCall(stdMdl_id.getId(), stdMdl_id.getInstituteId(),
                    stdMdl_id.getRoll(), stdMdl_id.getTransferredSchoolId(), stdMdl_id.getDateOfBirth(),
                    stdMdl_id.getMotherName(), stdMdl_id.getGradeId());
        }
    }

    //======================== login ================================================================================
    public UserModel login(String userName, String password) throws Exception {
        UserModel userModel = null;
        User userCreden = new User(userName, password);
        String json = gson.toJson(userCreden);

        //  json="{\"password":\"123456\",\"username\":\"kholilur\"}";

        retrofit2.Response respnse = api.loginWithRawJson(json).execute();

        // Log.d(TAG, respnse.body().toString());
        //  UserModel respnse = api.login(userCreden).execute().body();


        return userModel;
    }


    //================== constructor =================================================================================


    public SyncSynchronous(Context context) {
        this.context = context;
    }

    //================================================================================================================
    public String printTotal() {
        StringBuffer sb = new StringBuffer();
        sb.append("Total Schools: " + maxSch);
        sb.append("Total Teachers: " + maxTchr);
        sb.append("Total Students: " + maxStd);
        return sb.toString();
    }


    //============================ payment =============================================================================================
    public void syncPaymentAllSchool(String year) throws Exception {
        if (DAO.isTableEmpty(db, DB.schools.table)) {
            schoolSync();
        }
        List<School> schoolList = DAO.getAllInfoOfAllSchool();
        for (School sch : schoolList) {
            syncPayment_a_School_a_Year(sch.schID, sch.gradeID, year);
        }
    }


    public void syncPayment_a_School_a_Year(String schID, String gradeID, String year) throws IOException {

        PayMdl payMdl = api.getPaymentData(A.getU(), A.getP(), schID, gradeID, year).execute().body();

        DAO2.insertPaymentData(payMdl, schID, gradeID, year);

        DAO2.insertPaymentDataInSchTable_a_Sch(payMdl, schID, gradeID, year);
    }


}
