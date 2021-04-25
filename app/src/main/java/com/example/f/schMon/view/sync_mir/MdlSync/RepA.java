/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir.MdlSync;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mir Ferdous on 08/11/2017.
 */
public class RepA {
    @Expose
    @SerializedName("id")
    public String id;
    @Expose
    @SerializedName("instituteId")
    public String instituteId;
    @Expose
    @SerializedName("infrastructureQuestionId")
    public String infrastructureQuestionId;
    @Expose
    @SerializedName("submittedDate")
    public String submittedDate;
    @Expose
    @SerializedName("answer")
    public int answer;
    @Expose
    @SerializedName("priority")
    public int priority;
    @Expose
    @SerializedName("details")
    public String details;
    @Expose
    @SerializedName("localId")
    public String localId;
    @Expose
    @SerializedName("modifiedDate")
    public String modifiedDate;
    @Expose
    @SerializedName("targetDate")
    public String targetDate;
    @Expose
    @SerializedName("solveDate")
    public String solveDate;

    //======= extra field =======
    @Expose(serialize = false, deserialize = false)
    public transient String infrastructureQuestion;
    @Expose(serialize = false, deserialize = false)
    public transient String nfrastructureQuestionTitle;
    @Expose(serialize = false, deserialize = false)
    public transient String ques_category_id;
    @Expose(serialize = false, deserialize = false)
    public transient String category;
    @Expose(serialize = false, deserialize = false)
    public transient String synced;
    @Expose(serialize = false, deserialize = false)
    public transient String notify;
    @Expose(serialize = false, deserialize = false)
    public transient String server_ques;
    @Expose(serialize = false, deserialize = false)
    public transient String schName;
    @Expose(serialize = false, deserialize = false)
    public transient String grade;
    @Expose(serialize = false, deserialize = false)
    public transient String gradeID;
    @Expose(serialize = false, deserialize = false)
    public transient String schCode;


    //=========================== methods  ====================================================================================================


    public void nullToBlankString() {
        if (id == null)
            id = "";
        if (instituteId == null)
            instituteId = "";
        if (infrastructureQuestionId == null)
            infrastructureQuestionId = "";
        if (submittedDate == null)
            submittedDate = "";
        if (details == null)
            details = "";
        if (localId == null)
            localId = "";
        if (modifiedDate == null)
            modifiedDate = "";
        if (targetDate == null)
            targetDate = "";
        if (solveDate == null)
            solveDate = "";
    }

    //=========================================================================================================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getInfrastructureQuestionId() {
        return infrastructureQuestionId;
    }

    public void setInfrastructureQuestionId(String infrastructureQuestionId) {
        this.infrastructureQuestionId = infrastructureQuestionId;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(String solveDate) {
        this.solveDate = solveDate;
    }

    public String getInfrastructureQuestion() {
        return infrastructureQuestion;
    }

    public void setInfrastructureQuestion(String infrastructureQuestion) {
        this.infrastructureQuestion = infrastructureQuestion;
    }

    public String getNfrastructureQuestionTitle() {
        return nfrastructureQuestionTitle;
    }

    public void setNfrastructureQuestionTitle(String nfrastructureQuestionTitle) {
        this.nfrastructureQuestionTitle = nfrastructureQuestionTitle;
    }

    public String getSchCode() {
        return schCode;
    }

    public void setSchCode(String schCode) {
        this.schCode = schCode;
    }

    public String getQues_category_id() {
        return ques_category_id;
    }

    public void setQues_category_id(String ques_category_id) {
        this.ques_category_id = ques_category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getServer_ques() {
        return server_ques;
    }

    public void setServer_ques(String server_ques) {
        this.server_ques = server_ques;
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

    @Override
    public String toString() {
        return "RepA{" +
                "id='" + id + '\'' +
                ", instituteId='" + instituteId + '\'' +
                ", infrastructureQuestionId='" + infrastructureQuestionId + '\'' +
                ", submittedDate='" + submittedDate + '\'' +
                ", answer=" + answer +
                ", priority=" + priority +
                ", details='" + details + '\'' +
                ", localId='" + localId + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                ", targetDate='" + targetDate + '\'' +
                ", solveDate='" + solveDate + '\'' +
                '}';
    }
}