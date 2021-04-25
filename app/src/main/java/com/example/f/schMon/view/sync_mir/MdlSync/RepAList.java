
package com.example.f.schMon.view.sync_mir.MdlSync;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RepAList {

    @SerializedName("message")
    String mMessage;
    @SerializedName("module")
    String mModule;
    @SerializedName("request_type")
    String mRequestType;
    @SerializedName("success")
    String mSuccess;
    @SerializedName("total")
    String mTotal;
    @SerializedName("model")
    List<RepA> mModel;

    public RepAList() {
    }

    public RepAList(List<RepA> mModel, String mTotal, String mModule, String mRequestType, String mSuccess, String mMessage) {
        this.mMessage = mMessage;
        this.mModule = mModule;
        this.mRequestType = mRequestType;
        this.mSuccess = mSuccess;
        this.mTotal = mTotal;
        this.mModel = mModel;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<RepA> getModel() {
        return mModel;
    }

    public void setModel(List<RepA> model) {
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
        return "RepAList{" +
                "mMessage='" + mMessage + '\'' +
                ", mModel=" + mModel +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                '}';
    }
}
