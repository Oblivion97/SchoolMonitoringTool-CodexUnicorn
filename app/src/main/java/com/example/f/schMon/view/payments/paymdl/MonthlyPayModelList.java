
/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments.paymdl;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MonthlyPayModelList {

    @SerializedName("category")
    private String mCategory;
    @SerializedName("due")
    private String mDue;
    @SerializedName("month")
    private String mMonth;
    @SerializedName("paid")
    private String mPaid;
    @SerializedName("payable")
    private String mPayable;
    @SerializedName("type")
    private String mType;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDue() {
        return mDue;
    }

    public void setDue(String due) {
        mDue = due;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getPaid() {
        return mPaid;
    }

    public void setPaid(String paid) {
        mPaid = paid;
    }

    public String getPayable() {
        return mPayable;
    }

    public void setPayable(String payable) {
        mPayable = payable;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public String toString() {
        return "MonthlyPayModelList{" +
                "mCategory='" + mCategory + '\'' +
                ", mDue='" + mDue + '\'' +
                ", mMonth='" + mMonth + '\'' +
                ", mPaid='" + mPaid + '\'' +
                ", mPayable='" + mPayable + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
