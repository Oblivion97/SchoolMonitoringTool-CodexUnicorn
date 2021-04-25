
package com.example.f.schMon.model.newAPI;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class UserModelList {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("model")
    private UserModel mUserModel;
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

    public UserModel getModel() {
        return mUserModel;
    }

    public void setModel(UserModel userModel) {
        mUserModel = userModel;
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

}
