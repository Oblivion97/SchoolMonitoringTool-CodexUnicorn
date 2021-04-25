package com.example.f.schMon.model.oldAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TeacherListModel {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private List<TeacherModel> mTeacherModel;
    @SerializedName("module")
    private String mModule;
    @SerializedName("request_type")
    private String mRequestType;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("total")
    private String mTotal;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<TeacherModel> getModel() {
        return mTeacherModel;
    }

    public void setModel(List<TeacherModel> teacherModel) {
        mTeacherModel = teacherModel;
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


    @Override
    public String toString() {
        return "TeacherListModel{" +
                "mMessage='" + mMessage + '\'' +
                ", mTeacherModel=" + mTeacherModel +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                '}';
    }
}
