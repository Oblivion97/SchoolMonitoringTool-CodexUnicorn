/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.oldAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolListModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("module")
    private String mModule;
    @SerializedName("request_type")
    private String mRequestType;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("total")
    private String mTotal;
    @SerializedName("model")
    private List<SchoolModel> mSchoolModelSub;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<SchoolModel> getModel() {
        return mSchoolModelSub;
    }

    public void setModel(List<SchoolModel> schoolModelSub) {
        mSchoolModelSub = schoolModelSub;
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


    //_______________To String______________________________

    @Override
    public String toString() {
        return "SchoolListModel{" +
                "mMessage='" + mMessage + '\'' +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                ", mSchoolModelSub=" + mSchoolModelSub +
                '}';
    }
}
