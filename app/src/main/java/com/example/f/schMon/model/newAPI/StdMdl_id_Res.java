
package com.example.f.schMon.model.newAPI;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StdMdl_id_Res {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private StdMdl_id mStdMdlid;
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

    public StdMdl_id getModel() {
        return mStdMdlid;
    }

    public void setModel(StdMdl_id stdMdlid) {
        mStdMdlid = stdMdlid;
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
