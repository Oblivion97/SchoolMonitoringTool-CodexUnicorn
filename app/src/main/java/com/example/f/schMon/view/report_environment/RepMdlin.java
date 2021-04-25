/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.report_environment;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mir Ferdous on 29/09/2017.
 */

public class RepMdlin implements Parcelable {
    @SerializedName("_id")
    private String _id;

    @SerializedName("schID")
    private String schID;

    @SerializedName("gradeID")
    private String gradeID;

    @SerializedName("schName")
    private String schName;

    @SerializedName("eduType")
    private String eduType;
    @SerializedName("schCode")
    private String schCode;
    @SerializedName("serverIDQues")
    private String serverIDQues;
    @SerializedName("localIDQues")
    private String localIDQues;
    @SerializedName("serverIDAns")
    private String serverIDAns;
    @SerializedName("localIDAns")
    private String localIDAns;

    @SerializedName("questionTitle")
    private String questionTitle;
    @SerializedName("ques")
    private String question;
    @SerializedName("active")
    private String active;
    @SerializedName("answer")
    private String answer;
    @SerializedName("answerWeight")
    private String answerWeight;
    @SerializedName("details")
    private String details;
    @SerializedName("priority")
    private String priority;
    @SerializedName("server_ques")
    private String serverQuestion;
    @SerializedName("date_report")
    private String dateReport;

    @SerializedName("date_target")
    private String dateTarget;
    @SerializedName("date_solve")
    private String dateSolve;

    @SerializedName("synced")
    private String synced;
    @SerializedName("time_lm")
    private String timeLm;
    @SerializedName("notify")
    private String notify;
    @SerializedName("notify_his")
    private String notify_his;
    @SerializedName("n_update_date")
    private String n_update_date;


    //======== for Report ============
    private int quesNo;
    private String priorityExtra;
    private String detailsExtra;
    private int priorityColorExtra;
    private String statusExtra;
    private String dateTargetExtra;
    private String dateSolveExtra;
    private int imgID;
    private boolean yesSelected = false;
    private boolean noSelected = false;
    private boolean majorSelected = false;
    private boolean moderateSelected = false;
    private boolean minorSelected = false;
    private int listPosition;

    public String getDateTargetExtra() {
        return dateTargetExtra;
    }

    public void setDateTargetExtra(String dateTargetExtra) {
        this.dateTargetExtra = dateTargetExtra;
    }

    public String getDateSolveExtra() {
        return dateSolveExtra;
    }

    public void setDateSolveExtra(String dateSolveExtra) {
        this.dateSolveExtra = dateSolveExtra;
    }

    //===============================================================================================
    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public int getImgID() {
        return imgID;
    }

    public int getPriorityColorExtra() {
        return priorityColorExtra;
    }

    public void setPriorityColorExtra(int priorityColorExtra) {
        this.priorityColorExtra = priorityColorExtra;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public int getQuesNo() {
        return quesNo;
    }

    public void setQuesNo(int quesNo) {
        this.quesNo = quesNo;
    }

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
    }

    @Nullable
    public String get_id() {
        return _id;
    }

    public void set_id(@Nullable String _id) {
        this._id = _id;
    }

    public String getDateTarget() {
        return dateTarget;
    }

    public void setDateTarget(String dateTarget) {
        this.dateTarget = dateTarget;
    }

    public String getDateSolve() {

        return dateSolve;
    }

    public void setDateSolve(String dateSolve) {
        this.dateSolve = dateSolve;
    }

    public String getSchID() {
        return schID;
    }

    public void setSchID(String schID) {
        this.schID = schID;
    }

    public String getLocalIDQues() {
        return localIDQues;
    }

    public void setLocalIDQues(String localIDQues) {
        this.localIDQues = localIDQues;
    }

    public String getServerIDAns() {
        return serverIDAns;
    }

    public void setServerIDAns(String serverIDAns) {
        this.serverIDAns = serverIDAns;
    }

    public String getLocalIDAns() {
        return localIDAns;
    }

    public void setLocalIDAns(String localIDAns) {
        this.localIDAns = localIDAns;
    }

    public String getSchName() {
        return schName;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerWeight(String answerWeight) {
        this.answerWeight = answerWeight;
    }

    public String getActive() {

        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String eduType) {
        this.eduType = eduType;
    }

    public String getSchCode() {
        return schCode;
    }

    public void setSchCode(String schCode) {
        this.schCode = schCode;
    }

    public String getServerIDQues() {
        return serverIDQues;
    }

    public void setServerIDQues(String serverIDQues) {
        this.serverIDQues = serverIDQues;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuesTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestion() {
        return question;
    }

    public void setQues(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAns(String answer) {
        this.answer = answer;
    }

    public String getAnswerWeight() {
        return answerWeight;
    }

    public void setAnsWeight(String answerWeight) {
        this.answerWeight = answerWeight;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getServerQuestion() {
        return serverQuestion;
    }

    public void setServerQuestion(String serverQuestion) {
        this.serverQuestion = serverQuestion;
    }

    public String getDateReport() {
        return dateReport;
    }

    public void setDateReport(String dateReport) {
        this.dateReport = dateReport;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getTimeLm() {
        return timeLm;
    }

    public void setTimeLm(String timeLm) {
        this.timeLm = timeLm;
    }

    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getNotify_his() {
        return notify_his;
    }

    public void setNotify_his(String notify_his) {
        this.notify_his = notify_his;
    }

    public String getN_update_date() {
        return n_update_date;
    }

    public void setN_update_date(String n_update_date) {
        this.n_update_date = n_update_date;
    }

    public boolean isYesSelected() {
        return yesSelected;
    }

    public void setYesSelected(boolean yesSelected) {
        this.yesSelected = yesSelected;
    }

    public boolean isNoSelected() {
        return noSelected;
    }

    public void setNoSelected(boolean noSelected) {
        this.noSelected = noSelected;
    }

    public boolean isMajorSelected() {
        return majorSelected;
    }

    public void setMajorSelected(boolean majorSelected) {
        this.majorSelected = majorSelected;
    }

    public boolean isModerateSelected() {
        return moderateSelected;
    }

    public void setModerateSelected(boolean moderateSelected) {
        this.moderateSelected = moderateSelected;
    }

    public boolean isMinorSelected() {
        return minorSelected;
    }

    public void setMinorSelected(boolean minorSelected) {
        this.minorSelected = minorSelected;
    }

    public String getPriorityExtra() {
        return priorityExtra;
    }

    public void setPriorityExtra(String priorityExtra) {
        this.priorityExtra = priorityExtra;
    }

    public String getDetailsExtra() {
        return detailsExtra;
    }

    public void setDetailsExtra(String detailsExtra) {
        this.detailsExtra = detailsExtra;
    }

    public String getStatusExtra() {
        return statusExtra;
    }

    public void setStatusExtra(String statusExtra) {
        this.statusExtra = statusExtra;
    }
//============================ to string ===================================================================


    @Override
    public String toString() {
        return "RepMdlin{" +
                "_id='" + _id + '\'' +
                ", schID='" + schID + '\'' +
                ", gradeID='" + gradeID + '\'' +
                ", schName='" + schName + '\'' +
                ", eduType='" + eduType + '\'' +
                ", schCode='" + schCode + '\'' +
                ", serverIDQues='" + serverIDQues + '\'' +
                ", localIDQues='" + localIDQues + '\'' +
                ", serverIDAns='" + serverIDAns + '\'' +
                ", localIDAns='" + localIDAns + '\'' +
                ", questionTitle='" + questionTitle + '\'' +
                ", question='" + question + '\'' +
                ", active='" + active + '\'' +
                ", answer='" + answer + '\'' +
                ", answerWeight='" + answerWeight + '\'' +
                ", details='" + details + '\'' +
                ", priority='" + priority + '\'' +
                ", serverQuestion='" + serverQuestion + '\'' +
                ", dateReport='" + dateReport + '\'' +
                ", dateTarget='" + dateTarget + '\'' +
                ", dateSolve='" + dateSolve + '\'' +
                ", synced='" + synced + '\'' +
                ", timeLm='" + timeLm + '\'' +
                ", notify='" + notify + '\'' +
                ", notify_his='" + notify_his + '\'' +
                ", n_update_date='" + n_update_date + '\'' +
                ", quesNo=" + quesNo +
                ", priorityExtra='" + priorityExtra + '\'' +
                ", detailsExtra='" + detailsExtra + '\'' +
                ", priorityColorExtra=" + priorityColorExtra +
                ", statusExtra='" + statusExtra + '\'' +
                ", dateTargetExtra='" + dateTargetExtra + '\'' +
                ", dateSolveExtra='" + dateSolveExtra + '\'' +
                ", imgID=" + imgID +
                ", yesSelected=" + yesSelected +
                ", noSelected=" + noSelected +
                ", majorSelected=" + majorSelected +
                ", moderateSelected=" + moderateSelected +
                ", minorSelected=" + minorSelected +
                ", listPosition=" + listPosition +
                '}';
    }

    public String print() {
        return "Report " +

                ", \nschID='" + schID + '\'' +
                ", \nquestion='" + question + '\'' +
                ", \nanswer='" + answer + '\'' +
                ", \npriority='" + priority + '\'' +
                ", \ndetails='" + details + '\'' +
                ", \ndateReport='" + dateReport + '\'' +
                ", \ndateTarget='" + dateTarget + '\'' +
                ", \ndateSolve='" + dateSolve + '\'' +
                ", \nsynced='" + synced + '\'' +
                ", \ntimeLm='" + timeLm + '\'' +
                ", \nnotify='" + notify + '\'' +
                ", \nnotify_his='" + notify_his + '\'' +
                ", \nn_update_date='" + n_update_date + '\'' +
                "\n\n";
    }

    //============================== Constructor =================================================================
    public RepMdlin() {
    }

    public RepMdlin(String schID, String schName, String eduType, String schCode, String serverIDQues,
                    String questionTitle, String question, String answer, String answerWeight, String details,
                    String priority, String serverQuestion, String dateReport, String synced, String timeLm) {
        this.schID = schID;
        this.schName = schName;
        this.eduType = eduType;
        this.schCode = schCode;
        this.serverIDQues = serverIDQues;
        this.questionTitle = questionTitle;
        this.question = question;
        this.answer = answer;
        this.answerWeight = answerWeight;
        this.details = details;
        this.priority = priority;
        this.serverQuestion = serverQuestion;
        this.dateReport = dateReport;
        this.synced = synced;
        this.timeLm = timeLm;
    }

    public RepMdlin(String schID, String serverIDQues, String questionTitle, String question,
                    String answer, String answerWeight, String details, String priority,
                    String serverQuestion, String dateReport, String synced, String timeLm) {
        this.schID = schID;
        this.serverIDQues = serverIDQues;
        this.questionTitle = questionTitle;
        this.question = question;
        this.answer = answer;
        this.answerWeight = answerWeight;
        this.details = details;
        this.priority = priority;
        this.serverQuestion = serverQuestion;
        this.dateReport = dateReport;
        this.synced = synced;
        this.timeLm = timeLm;
    }

    //================================ parcelable =================================================
    protected RepMdlin(Parcel in) {
        schID = in.readString();
        schName = in.readString();
        eduType = in.readString();
        schCode = in.readString();
        serverIDQues = in.readString();
        questionTitle = in.readString();
        question = in.readString();
        answer = in.readString();
        answerWeight = in.readString();
        details = in.readString();
        priority = in.readString();
        serverQuestion = in.readString();
        dateReport = in.readString();
        synced = in.readString();
        timeLm = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(schID);
        dest.writeString(schName);
        dest.writeString(eduType);
        dest.writeString(schCode);
        dest.writeString(serverIDQues);
        dest.writeString(questionTitle);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(answerWeight);
        dest.writeString(details);
        dest.writeString(priority);
        dest.writeString(serverQuestion);
        dest.writeString(dateReport);
        dest.writeString(synced);
        dest.writeString(timeLm);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RepMdlin> CREATOR = new Parcelable.Creator<RepMdlin>() {
        @Override
        public RepMdlin createFromParcel(Parcel in) {
            return new RepMdlin(in);
        }

        @Override
        public RepMdlin[] newArray(int size) {
            return new RepMdlin[size];
        }
    };

    //============================ methods ===================================================================

    public boolean equalQuestion(RepMdlin testModel) {
        boolean f = false;

        if (this.getServerIDQues() != null && testModel.getServerIDQues() != null) {
            if (this.getServerIDQues().equals(testModel.getServerIDQues()
            ) && this.getQuestion().equals(testModel.getQuestion())) {
                f = true;
            } else {
                if (this.getServerIDQues().equals(testModel.getServerIDQues()
                ) && this.getQuestion().equals(testModel.getQuestion())) {
                    f = true;
                } else {
                    f = false;
                }
            }
        }

        return f;
    }
}