
/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments.paymdl;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PayMdl {

    @SerializedName("message")
    private Object mMessage;
    @SerializedName("model")
    private Object mModel;
    @SerializedName("module")
    private String mModule;
    @SerializedName("request_type")
    private String mRequestType;
    @SerializedName("schoolId")
    private String mSchoolId;
    @SerializedName("schoolName")
    private String mSchoolName;
    @SerializedName("studentPayModelList")
    private List<StudentPayModelList> mStudentPayModelList;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("year")
    private String mYear;

    public Object getMessage() {
        return mMessage;
    }

    public void setMessage(Object message) {
        mMessage = message;
    }

    public Object getModel() {
        return mModel;
    }

    public void setModel(Object model) {
        mModel = model;
    }

    public String getModule() {
        return mModule;
    }

    public void setModule(String module) {
        mModule = module;
    }

    public String getRequestType() {
        return mRequestType;
    }

    public void setRequestType(String requestType) {
        mRequestType = requestType;
    }

    public String getSchoolId() {
        return mSchoolId;
    }

    public void setSchoolId(String schoolId) {
        mSchoolId = schoolId;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }

    public List<StudentPayModelList> getStudentPayModelList() {
        return mStudentPayModelList;
    }

    public void setStudentPayModelList(List<StudentPayModelList> studentPayModelList) {
        mStudentPayModelList = studentPayModelList;
    }

    public String getSuccess() {
        return mSuccess;
    }

    public void setSuccess(String success) {
        mSuccess = success;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    @Override
    public String toString() {
        return "PayMdl{" +
                "mMessage=" + mMessage +
                ", mModel=" + mModel +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSchoolId='" + mSchoolId + '\'' +
                ", mSchoolName='" + mSchoolName + '\'' +
                ", mStudentPayModelList=" + mStudentPayModelList +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                ", mYear='" + mYear + '\'' +
                '}';
    }
}
