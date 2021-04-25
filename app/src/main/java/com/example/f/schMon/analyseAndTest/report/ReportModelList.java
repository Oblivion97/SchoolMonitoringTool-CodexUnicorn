/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.report;

import com.example.f.schMon.model.appInner.RepModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReportModelList {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private List<RepModel> mRepModel;
    @SerializedName("module")
    private String mModule;
    @SerializedName("request_type")
    private String mRequestType;
    @SerializedName("schID")
    private String mSchoolID;
    @SerializedName("schName")
    private String mSchoolName;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("userID")
    private String mUserID;
    @SerializedName("userName")
    private String mUserName;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<RepModel> getReportModel() {
        return mRepModel;
    }

    public void setReportModel(List<RepModel> repModel) {
        mRepModel = repModel;
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

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String userID) {
        mUserID = userID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    @Override
    public String toString() {
        return "ReportModelList{" +
                "mMessage='" + mMessage + '\'' +
                ", mRepModel=" + mRepModel +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSchoolID='" + mSchoolID + '\'' +
                ", mSchoolName='" + mSchoolName + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                ", mUserID='" + mUserID + '\'' +
                ", mUserName='" + mUserName + '\'' +
                '}';
    }
}
