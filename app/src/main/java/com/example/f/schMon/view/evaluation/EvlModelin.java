/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mir Ferdous on 05/10/2017.
 */

public class EvlModelin implements Parcelable {
    /**
     * this is model for evaluation apps internal use not for api
     */

    String schID;
    String schName;
    String gradeID;
    String grade;
    String subject;
    String chapter;
    String serverIDQues;
    String localIDQues;
    String serverIDAns;
    String localIDAns;
    String ques;
    String answer;
    String active;
    String synced;
    String server_ques;
    String time_lm;
    String date_report;
    String date_update;
    String totalStd;
    String attendence;
    String attempt;
    String asked;
    String correct;
    String perf;
    String category;
    String categoryID;

    //extra
    String askedExtra;
    String correctExtra;
    int listPosition;
    int quesNo;
    int imgID;
    int ansColor;

    //=============================== Constructor ==================================================================


//=================================================================================================

    public String getSchID() {
        return schID;
    }

    public void setSchID(String schID) {
        this.schID = schID;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getGradeID() {
        return gradeID;
    }

    public void setGradeID(String gradeID) {
        this.gradeID = gradeID;
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

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public int getAnsColor() {
        return ansColor;
    }

    public void setAnsColor(int ansColor) {
        this.ansColor = ansColor;
    }

    public String getServerIDQues() {
        return serverIDQues;
    }

    public void setServerIDQues(String serverIDQues) {
        this.serverIDQues = serverIDQues;
    }

    public String getLocalIDQues() {
        return localIDQues;
    }

    public void setLocalIDQues(String localIDQues) {
        this.localIDQues = localIDQues;
    }

    public String getQues() {
        return ques;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public String getServer_ques() {
        return server_ques;
    }

    public void setServer_ques(String server_ques) {
        this.server_ques = server_ques;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTime_lm() {
        return time_lm;
    }

    public void setTime_lm(String time_lm) {
        this.time_lm = time_lm;
    }

    public String getDate_report() {
        return date_report;
    }

    public void setDate_report(String date_report) {
        this.date_report = date_report;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
    }

    public String getTotalStd() {
        return totalStd;
    }

    public void setTotalStd(String totalStd) {
        this.totalStd = totalStd;
    }

    public String getAttendence() {
        return attendence;
    }

    public void setAttendence(String attendence) {
        this.attendence = attendence;
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public String getAsked() {
        return asked;
    }

    public void setAsked(String asked) {
        this.asked = asked;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getPerf() {
        return perf;
    }

    public void setPerf(String perf) {
        this.perf = perf;
    }

    public int getQuesNo() {
        return quesNo;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getAskedExtra() {
        return askedExtra;
    }

    public void setAskedExtra(String askedExtra) {
        this.askedExtra = askedExtra;
    }

    public String getCorrectExtra() {
        return correctExtra;
    }

    public void setCorrectExtra(String correctExtra) {
        this.correctExtra = correctExtra;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }

    public void setQuesNo(int quesNo) {
        this.quesNo = quesNo;
    }

    //=================================================================================================

    @Override
    public String toString() {
        return "EvlModelin{" +
                "\nschID='" + schID + '\'' +
                ", \nschName='" + schName + '\'' +
                ", \nserver_id='" + serverIDQues + '\'' +
                ", \nques='" + ques + '\'' +
                ", \nanswer='" + answer + '\'' +
                ", \nactive='" + active + '\'' +
                ", \nsynced='" + synced + '\'' +
                ", \nserver_ques='" + server_ques + '\'' +
                ", \ngradeID='" + gradeID + '\'' +
                ", \ngrade='" + grade + '\'' +
                ", \ntime_lm='" + time_lm + '\'' +
                ", \ndate_report='" + date_report + '\'' +
                ", \ndate_update='" + date_update + '\'' +
                ", \ntotalStd='" + totalStd + '\'' +
                ", \nattendence='" + attendence + '\'' +
                ", \nattempt='" + attempt + '\'' +
                ", \nasked='" + asked + '\'' +
                ", \naskedExtra='" + askedExtra + '\'' +
                ", \ncorrect='" + correct + '\'' +
                ", \ncorrectExtra='" + correctExtra + '\'' +
                ", \nperf='" + perf + '\'' +
                ", \nsubject='" + subject + '\'' +
                ", \nchapter='" + chapter + '\'' +
                '}' + "\n\n";
    }
//===================================== parcelable ==============================================================


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.schID);
        dest.writeString(this.schName);
        dest.writeString(this.serverIDQues);
        dest.writeString(this.ques);
        dest.writeString(this.answer);
        dest.writeString(this.active);
        dest.writeString(this.synced);
        dest.writeString(this.server_ques);
        dest.writeString(this.grade);
        dest.writeString(this.time_lm);
        dest.writeString(this.date_report);
        dest.writeString(this.date_update);
        dest.writeString(this.totalStd);
        dest.writeString(this.attendence);
        dest.writeString(this.attempt);
        dest.writeString(this.asked);
        dest.writeString(this.correct);
        dest.writeString(this.perf);
    }

    protected EvlModelin(Parcel in) {
        this.schID = in.readString();
        this.schName = in.readString();
        this.serverIDQues = in.readString();
        this.ques = in.readString();
        this.answer = in.readString();
        this.active = in.readString();
        this.synced = in.readString();
        this.server_ques = in.readString();
        this.grade = in.readString();
        this.time_lm = in.readString();
        this.date_report = in.readString();
        this.date_update = in.readString();
        this.totalStd = in.readString();
        this.attendence = in.readString();
        this.attempt = in.readString();
        this.asked = in.readString();
        this.correct = in.readString();
        this.perf = in.readString();
    }

    public static final Parcelable.Creator<EvlModelin> CREATOR = new Parcelable.Creator<EvlModelin>() {
        @Override
        public EvlModelin createFromParcel(Parcel source) {
            return new EvlModelin(source);
        }

        @Override
        public EvlModelin[] newArray(int size) {
            return new EvlModelin[size];
        }
    };

    public EvlModelin() {
    }

    public EvlModelin(String serverIDQues, String ques, String answer, String active, String synced,
                      String server_ques, String grade, String time_lm) {
        this.serverIDQues = serverIDQues;
        this.ques = ques;
        this.answer = answer;
        this.active = active;
        this.synced = synced;
        this.server_ques = server_ques;
        this.grade = grade;
        this.time_lm = time_lm;
    }

    public EvlModelin(String schID, String schName, String serverIDQues, String ques,
                      String answer, String active, String synced, String server_ques,
                      String grade, String time_lm, String date_report, String date_update,
                      String totalStd, String attendence, String attempt,
                      String asked, String correct, String perf) {
        this.schID = schID;
        this.schName = schName;
        this.serverIDQues = serverIDQues;
        this.ques = ques;
        this.answer = answer;
        this.active = active;
        this.synced = synced;
        this.server_ques = server_ques;
        this.grade = grade;
        this.time_lm = time_lm;
        this.date_report = date_report;
        this.date_update = date_update;
        this.totalStd = totalStd;
        this.attendence = attendence;
        this.attempt = attempt;
        this.asked = asked;
        this.correct = correct;
        this.perf = perf;
    }

    //================================= method ========================================================
    public boolean equalQuestion(EvlModelin testModel) {
        boolean f = false;

        if (this.getServerIDQues() != null && testModel.getServerIDQues() != null) {
            /*for server(downloaded) question*/
            if (this.getServerIDQues().equalsIgnoreCase(testModel.getServerIDQues())) {
                f = true;
            }
        } else if (this.getLocalIDQues() != null && testModel.getLocalIDQues() != null) {
            /*for user generated question*/
            if (this.getLocalIDQues().equalsIgnoreCase(testModel.getLocalIDQues()))
                f = true;
        } else if (testModel.getLocalIDQues() == null) {
            /*for user generated question*/
            f = true;
        }

        return f;
    }
}
