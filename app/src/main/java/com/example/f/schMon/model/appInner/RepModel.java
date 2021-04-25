/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RepModel {
    @SerializedName("server_id")
    private String mQuestionID;
    @SerializedName("questionTitle")
    private String questionTitle;
    @SerializedName("ques")
    private String mQuestion;
    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("answerWeight")
    private String mAnswerWeight;
    @SerializedName("details")
    private String mDetails;
    @SerializedName("priority")
    private String mPriority;
    @SerializedName("server_ques")
    private String serverQuestion;
    @SerializedName("date_report")
    private String mDateReport;
    @SerializedName("synced")
    private String mSynced;
    @SerializedName("time_lm")
    private String mTimeLm;

    public RepModel() {
    }

    public RepModel(String mQuestionID, String questionTitle, String mQuestion,
                    String mAnswer, String mAnswerWeight, String mDetails, String mPriority,
                    String serverQuestion, String mDateReport, String mSynced, String mTimeLm) {
        this.mQuestionID = mQuestionID;
        this.questionTitle = questionTitle;
        this.mQuestion = mQuestion;
        this.mAnswer = mAnswer;
        this.mAnswerWeight = mAnswerWeight;
        this.mDetails = mDetails;
        this.mPriority = mPriority;
        this.serverQuestion = serverQuestion;
        this.mDateReport = mDateReport;
        this.mSynced = mSynced;
        this.mTimeLm = mTimeLm;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getAnswerWeight() {
        return mAnswerWeight;
    }

    public void setAnswerWeight(String answerWeight) {
        mAnswerWeight = answerWeight;
    }

    public String getDateReport() {
        return mDateReport;
    }

    public void setDateReport(String dateReport) {
        mDateReport = dateReport;
    }

    public String getDetails() {
        return mDetails;
    }

    public void setDetails(String details) {
        mDetails = details;
    }

    public String getPriority() {
        return mPriority;
    }

    public void setPriority(String priority) {
        mPriority = priority;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getQuestionID() {
        return mQuestionID;
    }

    public void setQuestionID(String questionID) {
        mQuestionID = questionID;
    }

    public String getServerQuestion() {
        return serverQuestion;
    }

    public void setServerQuestion(String serverQuestion) {
        this.serverQuestion = serverQuestion;
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

    @Override
    public String toString() {
        return "mQuestionID='" + mQuestionID + '\'' +
                ", \nquestionTitle='" + questionTitle + '\'' +
                ", \nmQuestion='" + mQuestion + '\'' +
                ", \nmAnswer='" + mAnswer + '\'' +
                ", \nmAnswerWeight='" + mAnswerWeight + '\'' +
                ", \nmDetails='" + mDetails + '\'' +
                ", \nmPriority='" + mPriority + '\'' +
                ", \nserverQuestion='" + serverQuestion + '\'' +
                ", \nmDateReport='" + mDateReport + '\'' +
                ", \nmSynced='" + mSynced + '\'' +
                ", \nmTimeLm='" + mTimeLm + '\'' + "\n\n";
    }
}
