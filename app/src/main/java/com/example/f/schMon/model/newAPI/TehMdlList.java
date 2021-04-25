
package com.example.f.schMon.model.newAPI;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TehMdlList {
    @Override
    public String toString() {
        return "TehMdlList{" +
                "mMessage='" + mMessage + '\'' +
                ", mTchrMdl=" + mTchrMdl +
                ", mModule='" + mModule + '\'' +
                ", mRequestType='" + mRequestType + '\'' +
                ", mSuccess='" + mSuccess + '\'' +
                ", mTotal='" + mTotal + '\'' +
                '}';
    }

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private List<TchrMdl> mTchrMdl;
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

    public List<TchrMdl> getModel() {
        return mTchrMdl;
    }

    public void setModel(List<TchrMdl> TchrMdl) {
        mTchrMdl = TchrMdl;
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
