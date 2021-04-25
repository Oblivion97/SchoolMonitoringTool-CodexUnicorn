
/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments.paymdl;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentPayModelList {

    @SerializedName("monthlyPayModelList")
    private List<MonthlyPayModelList> mMonthlyPayModelList;
    @SerializedName("roll")
    private String mRoll;
    @SerializedName("studentId")
    private String mStudentId;
    @SerializedName("studentName")
    private String mStudentName;

    public List<MonthlyPayModelList> getMonthlyPayModelList() {
        return mMonthlyPayModelList;
    }

    public void setMonthlyPayModelList(List<MonthlyPayModelList> monthlyPayModelList) {
        mMonthlyPayModelList = monthlyPayModelList;
    }

    public String getRoll() {
        return mRoll;
    }

    public void setRoll(String roll) {
        mRoll = roll;
    }

    public String getStudentId() {
        return mStudentId;
    }

    public void setStudentId(String studentId) {
        mStudentId = studentId;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String studentName) {
        mStudentName = studentName;
    }

    @Override
    public String toString() {
        return "StudentPayModelList{" +
                "mMonthlyPayModelList=" + mMonthlyPayModelList +
                ", mRoll='" + mRoll + '\'' +
                ", mStudentId='" + mStudentId + '\'' +
                ", mStudentName='" + mStudentName + '\'' +
                '}';
    }
}
