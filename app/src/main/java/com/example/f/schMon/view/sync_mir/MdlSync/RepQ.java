
package com.example.f.schMon.view.sync_mir.MdlSync;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RepQ {
    @SerializedName("id")
    private String mId;

    @SerializedName("localId")
    private String mLocalId;

    @SerializedName("infrastructureQuestionCategoryId")
    private String mInfrastructureQuestionCategoryId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("infrastructureQuestionCategoryName")
    private String mInfrastructureQuestionCategoryName;

    @SerializedName("active")
    private String mActive;

    public String getActive() {
        return mActive;
    }

    public void setActive(String active) {
        mActive = active;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getInfrastructureQuestionCategoryId() {
        return mInfrastructureQuestionCategoryId;
    }

    public void setInfrastructureQuestionCategoryId(String infrastructureQuestionCategoryId) {
        mInfrastructureQuestionCategoryId = infrastructureQuestionCategoryId;
    }

    public String getInfrastructureQuestionCategoryName() {
        return mInfrastructureQuestionCategoryName;
    }

    public void setInfrastructureQuestionCategoryName(String infrastructureQuestionCategoryName) {
        mInfrastructureQuestionCategoryName = infrastructureQuestionCategoryName;
    }

    public String getLocalId() {
        return mLocalId;
    }

    public void setLocalId(String localId) {
        mLocalId = localId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return "RepQ{" +
                "mActive='" + mActive + '\'' +
                ", mId='" + mId + '\'' +
                ", mInfrastructureQuestionCategoryId='" + mInfrastructureQuestionCategoryId + '\'' +
                ", mInfrastructureQuestionCategoryName='" + mInfrastructureQuestionCategoryName + '\'' +
                ", mLocalId='" + mLocalId + '\'' +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }
}
