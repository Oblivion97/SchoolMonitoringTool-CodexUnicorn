
package com.example.f.schMon.view.sync_mir.MdlSync;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EvlAList {

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
    private List<EvlA> mModel;

    public EvlAList() {
    }

    public EvlAList(List<EvlA> mModel, String mTotal, String mModule, String mRequestType, String mSuccess, String mMessage) {
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

    public List<EvlA> getModel() {
        return mModel;
    }

    public void setModel(List<EvlA> model) {
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
        return "EvlAList{" +
                "mMessage='" + mMessage + '\'' +
                ", mModel=" + mModel +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                '}';
    }
}
