
package com.example.f.schMon.model.newAPI;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StdMdlList {
    @Override
    public String toString() {
        return "StdMdlList{" +
                "mMessage='" + mMessage + '\'' +
                ", mStdMdl=" + mStdMdl +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                '}';
    }

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private List<StdMdl> mStdMdl;
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

    public List<StdMdl> getModel() {
        return mStdMdl;
    }

    public void setModel(List<StdMdl> StdMdl) {
        mStdMdl = StdMdl;
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

}
