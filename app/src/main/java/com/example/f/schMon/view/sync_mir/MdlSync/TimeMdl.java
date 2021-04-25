
/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir.MdlSync;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TimeMdl {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private String mModel;
    @SerializedName("module")
    private Object mModule;
    @SerializedName("request_type")
    private Object mRequestType;
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

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public Object getModule() {
        return mModule;
    }

    public void setModule(Object module) {
        mModule = module;
    }

    public Object getRequestType() {
        return mRequestType;
    }

    public void setRequestType(Object requestType) {
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


    @Override
    public String toString() {
        return "TimeMdl{" +
                "mMessage='" + mMessage + '\'' +
                ", mModel='" + mModel + '\'' +
                ", mModule=" + mModule +
                ", mRequestType=" + mRequestType +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal=" + mTotal +
                '}';
    }
}
