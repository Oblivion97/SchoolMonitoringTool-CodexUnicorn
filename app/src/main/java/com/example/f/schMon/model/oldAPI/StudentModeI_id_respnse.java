package com.example.f.schMon.model.oldAPI;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StudentModeI_id_respnse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private StudentModel_id mStudentModeIid;
    @SerializedName("module")
    private String mModule;
    @SerializedName("request_type")
    private String mRequestType;
    @SerializedName("success")
    private String mSuccess;
    @SerializedName("total")
    private Object mTotal;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public StudentModel_id getModel() {
        return mStudentModeIid;
    }

    public void setModel(StudentModel_id studentModeIid) {
        mStudentModeIid = studentModeIid;
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

    public Object getTotal() {
        return mTotal;
    }

    public void setTotal(Object total) {
        mTotal = total;
    }

}
