/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EvalPerfModel {

    /**
     * for saving data in acad_perform table and sync evaluation report
     */


    @SerializedName("attendence")
    private String mAttendence;
    @SerializedName("date_report")
    private String mDateReport;
    @SerializedName("date_update")
    private String DateUpdate;  // if ans updates
    @SerializedName("evalPerformRatio")
    private String mEvalPerformRatio;
    @SerializedName("schID")
    private String mSchoolID;
    @SerializedName("schName")
    private String mSchoolName;
    @SerializedName("synced")
    private String mSynced;
    @SerializedName("time_lm")
    private String mTimeLm;
    @SerializedName("totalQues")
    private String totalQues;

    //-----------------constructors---------------------------------------------
    public EvalPerfModel() {
    }

    public EvalPerfModel(String mSchoolID, String mSchoolName, String mDateReport, String DateUpdate,
                         String mAttendence, String mEvalPerformRatio,
                         String mSynced, String mTimeLm) {
        this.mAttendence = mAttendence;
        this.mDateReport = mDateReport;
        this.DateUpdate = DateUpdate;
        this.mEvalPerformRatio = mEvalPerformRatio;
        this.mSchoolID = mSchoolID;
        this.mSchoolName = mSchoolName;
        this.mSynced = mSynced;
        this.mTimeLm = mTimeLm;
    }

    public String getDateUpdate() {
        return DateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        DateUpdate = dateUpdate;
    }

    public String getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
    }

    public String getAttendence() {
        return mAttendence;
    }

    public void setAttendence(String attendence) {
        mAttendence = attendence;
    }

    public String getDateReport() {
        return mDateReport;
    }

    public void setDateReport(String dateReport) {
        mDateReport = dateReport;
    }

    public String getEvalPerformRatio() {
        return mEvalPerformRatio;
    }

    public void setEvalPerformRatio(String evalPerformRatio) {
        mEvalPerformRatio = evalPerformRatio;
    }

    public String getSchoolID() {
        return mSchoolID;
    }

    public void setSchoolID(String schoolID) {
        mSchoolID = schoolID;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }

    public String getSynced() {
        return mSynced;
    }

    public void setSynced(String synced) {
        mSynced = synced;
    }

    public String getTimeLm() {
        return mTimeLm;
    }

    public void setTimeLm(String timeLm) {
        mTimeLm = timeLm;
    }

}
