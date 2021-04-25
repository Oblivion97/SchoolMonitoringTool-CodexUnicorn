/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mir Ferdous on 11/06/2017.
 */

public class School implements Parcelable {

    public int _id;
    public String schID,
            schName,
            poID,
            poName,
            s_address,
            time_lm, //time last modified
            totalStd,
            edu_type,
            schCode,
            grade,
            gradeID,
            total_bill,
            paid, sessionStart, sessionEnd,last_visit_time;


    //extra
    public int colorRow;
    // ============= constructor =====================

    public void removeNull() {
        schID = "";
        schName = "";
        poID = "";
        poName = "";
        s_address = "";
        time_lm = "";
        totalStd = "";
        edu_type = "";
        grade = "";
        schCode = "";
        total_bill = "";
        paid = "";
    }

    public String getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(String sessionStart) {
        this.sessionStart = sessionStart;
    }

    public String getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(String sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public String getLast_visit_time() {
        return last_visit_time;
    }

    public void setLast_visit_time(String last_visit_time) {
        this.last_visit_time = last_visit_time;
    }

    public int getColorRow() {
        return colorRow;
    }

    public void setColorRow(int colorRow) {
        this.colorRow = colorRow;
    }

    public School(String schID, String schName, String poID, String poName,
                  String s_address, String time_lm, String totalStd, String edu_type,
                  String schCode, String total_bill, String paid) {

        this.schID = schID;
        this.schName = schName;
        this.poID = poID;
        this.poName = poName;
        this.s_address = s_address;
        this.time_lm = time_lm;
        this.totalStd = totalStd;
        this.edu_type = edu_type;
        this.schCode = schCode;
        this.total_bill = total_bill;
        this.paid = paid;
    }

    public School(String schID, String schName, String poID, String poName, String s_address, String time_lm, String totalStd, String edu_type, String schCode) {
        this.schID = schID;
        this.schName = schName;
        this.poID = poID;
        this.poName = poName;
        this.s_address = s_address;
        this.time_lm = time_lm;
        this.totalStd = totalStd;
        this.edu_type = edu_type;
        this.schCode = schCode;
    }

    public School(String schID, String schName, String poID, String totalStd, String edu_type, String schCode) {
        this.schID = schID;
        this.schName = schName;
        this.poID = poID;
        this.totalStd = totalStd;
        this.edu_type = edu_type;
        this.schCode = schCode;
    }

    public School(String schID, String schName, String eduType, String totalStd) {
        this.schID = schID;
        this.schName = schName;
        this.edu_type = eduType;
        this.totalStd = totalStd;
    }

    public School(String schID, String schName) {
        this.schID = schID;
        this.schName = schName;
    }

    public School() {
    }


    //______________ getter & setters_____________________________________________

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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

    public String getPoID() {
        return poID;
    }

    public void setPoId(String po_id) {
        this.poID = po_id;
    }

    public String getPoName() {
        return poName;
    }

    public void setPoName(String po_name) {
        this.poName = po_name;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public String getTime_lm() {
        return time_lm;
    }

    public void setTime_lm(String time_lm) {
        this.time_lm = time_lm;
    }

    public String getTotalStd() {
        return totalStd;
    }

    public void setTotalStd(String totalStd) {
        this.totalStd = totalStd;
    }

    public String getEdu_type() {
        return edu_type;
    }

    public void setEdu_type(String edu_type) {
        this.edu_type = edu_type;
    }

    public String getSchCode() {
        return schCode;
    }

    public void setSchCode(String schCode) {
        this.schCode = schCode;
    }

    public String getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    //___________to string__________________________________


    @Override
    public String toString() {
        return "School{" +
                "_id=" + _id +
                ", schID='" + schID + '\'' +
                ", schName='" + schName + '\'' +
                ", poID='" + poID + '\'' +
                ", poName='" + poName + '\'' +
                ", s_address='" + s_address + '\'' +
                ", time_lm='" + time_lm + '\'' +
                ", totalStd='" + totalStd + '\'' +
                ", edu_type='" + edu_type + '\'' +
                ", schCode='" + schCode + '\'' +
                ", grade='" + grade + '\'' +
                ", total_bill='" + total_bill + '\'' +
                ", paid='" + paid + '\'' +
                ", sessionStart='" + sessionStart + '\'' +
                ", sessionEnd='" + sessionEnd + '\'' +
                ", colorRow=" + colorRow +
                '}';
    }


    // ============= Parcelable =====================
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.schID);
        dest.writeString(this.schName);
        dest.writeString(this.poID);
        dest.writeString(this.poName);
        dest.writeString(this.s_address);
        dest.writeString(this.time_lm);
        dest.writeString(this.totalStd);
        dest.writeString(this.edu_type);
        dest.writeString(this.schCode);
        dest.writeString(this.grade);
        dest.writeString(this.gradeID);
        dest.writeString(this.total_bill);
        dest.writeString(this.paid);
        dest.writeString(this.sessionStart);
        dest.writeString(this.sessionEnd);
        dest.writeInt(this.colorRow);
    }

    protected School(Parcel in) {
        this._id = in.readInt();
        this.schID = in.readString();
        this.schName = in.readString();
        this.poID = in.readString();
        this.poName = in.readString();
        this.s_address = in.readString();
        this.time_lm = in.readString();
        this.totalStd = in.readString();
        this.edu_type = in.readString();
        this.schCode = in.readString();
        this.grade = in.readString();
        this.gradeID = in.readString();
        this.total_bill = in.readString();
        this.paid = in.readString();
        this.sessionStart = in.readString();
        this.sessionEnd = in.readString();
        this.colorRow = in.readInt();
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel source) {
            return new School(source);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };
}
