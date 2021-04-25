/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir.MdlSync;

import com.google.gson.annotations.Expose;

/**
 * Created by Mir Ferdous on 08/11/2017.
 */
public class EvlA {
    @Expose
    public String id;
    @Expose
    public String localId;
    @Expose
    public String instituteId;
    @Expose
    public String academicQuestionId;
    @Expose
    public String submittedDate;
    @Expose
    public String modifiedDate;
    @Expose
    public int totalAttendance;
    @Expose
    public int totalAttempt;
    @Expose
    public int totalAsked;
    @Expose
    public int totalAnswer;
    @Expose
    public String performance;

    // for new question request from mobile app
    @Expose
    public String academicQuestionLocalId;
    @Expose
    public String questionTitle;
    @Expose
    public String questionAnswer;
    @Expose
    public String questionGradeId;
    @Expose
    public String serverQuestion;

    @Expose
    public String subject;
    @Expose
    public String chapter;


    //======= extra field =======
    @Expose(serialize = false, deserialize = false)
    public transient String schName;
    @Expose(serialize = false, deserialize = false)
    public transient String schCode;
    @Expose(serialize = false, deserialize = false)
    public transient String grade;
    @Expose(serialize = false, deserialize = false)
    public transient String gradeID;
    @Expose(serialize = false, deserialize = false)
    public transient String totalStd;
    @Expose(serialize = false, deserialize = false)
    public transient String active;
    @Expose(serialize = false, deserialize = false)
    public transient String synced;


    //=============== methods =========================
    public void nullToBlankString() {
        if (id == null)
            id = "";

        if (localId == null)
            localId = "";

        if (instituteId == null)
            instituteId = "";

        if (academicQuestionId == null)
            academicQuestionId = "";

        if (submittedDate == null)
            submittedDate = "";

        if (modifiedDate == null)
            modifiedDate = "";

        if (performance == null)
            performance = "";

        if (academicQuestionLocalId == null)
            academicQuestionLocalId = "";

        if (questionTitle == null)
            questionTitle = "";

        if (questionAnswer == null)
            questionAnswer = "";

        if (questionGradeId == null)
            questionGradeId = "";

        if (serverQuestion == null)
            serverQuestion = "";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getAcademicQuestionId() {
        return academicQuestionId;
    }

    public void setAcademicQuestionId(String academicQuestionId) {
        this.academicQuestionId = academicQuestionId;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public String getSchCode() {
        return schCode;
    }

    public void setSchCode(String schCode) {
        this.schCode = schCode;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(int totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public int getTotalAttempt() {
        return totalAttempt;
    }

    public void setTotalAttempt(int totalAttempt) {
        this.totalAttempt = totalAttempt;
    }

    public int getTotalAsked() {
        return totalAsked;
    }

    public void setTotalAsked(int totalAsked) {
        this.totalAsked = totalAsked;
    }

    public int getTotalAnswer() {
        return totalAnswer;
    }

    public void setTotalAnswer(int totalAnswer) {
        this.totalAnswer = totalAnswer;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public String getAcademicQuestionLocalId() {
        return academicQuestionLocalId;
    }

    public void setAcademicQuestionLocalId(String academicQuestionLocalId) {
        this.academicQuestionLocalId = academicQuestionLocalId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionGradeId() {
        return questionGradeId;
    }

    public void setQuestionGradeId(String questionGradeId) {
        this.questionGradeId = questionGradeId;
    }

    public String getServerQuestion() {
        return serverQuestion;
    }

    public void setServerQuestion(String serverQuestion) {
        this.serverQuestion = serverQuestion;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    public String getTotalStd() {
        return totalStd;
    }

    public void setTotalStd(String totalStd) {
        this.totalStd = totalStd;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "EvlA{" +
                "id='" + id + '\'' +
                ", localId='" + localId + '\'' +
                ", instituteId='" + instituteId + '\'' +
                ", academicQuestionId='" + academicQuestionId + '\'' +
                ", submittedDate='" + submittedDate + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", totalAttendance=" + totalAttendance +
                ", totalAttempt=" + totalAttempt +
                ", totalAsked=" + totalAsked +
                ", totalAnswer=" + totalAnswer +
                ", performance='" + performance + '\'' +
                ", academicQuestionLocalId='" + academicQuestionLocalId + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionAnswer='" + questionAnswer + '\'' +
                ", questionGradeId='" + questionGradeId + '\'' +
                ", serverQuestion='" + serverQuestion + '\'' +
                ", subject='" + subject + '\'' +
                ", chapter='" + chapter + '\'' +
                '}';
    }
}