/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.oldAPI;

public class LoginModel {
    private String module;
    private String request_type;
    private String success;
    private String message;
    private String total;
    private UserModel model;

    public LoginModel() {

    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getModel() {
        return model;
    }

    public void setModel(UserModel model) {
        this.model = model;
    }

    public String getRequest_type() {
        return request_type;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    //__________________To String____________________________

    @Override
    public String toString() {
        return "LoginModel{" +
                "module='" + module + '\'' +
                ", request_type='" + request_type + '\'' +
                ", success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", total='" + total + '\'' +
                ", model=" + model +
                '}';
    }
}
