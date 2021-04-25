
package com.example.f.schMon.view.sync_mir.MdlSync;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")

/*id	"50988098-7723-Acad-ac0b-e0ee1e08d1a5"
title	"First question"
answer	"one"
active	"true"
gradeId	"47272800-4094-grad-b8a4-924ff12a4bfd"
gradeName	"One"
localId	null
serverQuestion	"true"
subject	"English"
chapter	"Second Chapter"*/
public class EvlQ {

    @SerializedName("gradeName")
    private String mGradeName;

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("answer")
    private String mAnswer;

    @SerializedName("active")
    private String mActive;

    @SerializedName("gradeId")
    private String mGradeId;

    @SerializedName("localId")
    private String mLocalId;

    @SerializedName("serverQuestion")
    private String mServerQuestion;

    @SerializedName("chapter")
    private String mchapter;

    @SerializedName("subject")
    private String mSubject;

    public String getActive() {
        return mActive;
    }

    public void setActive(String active) {
        mActive = active;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getGradeId() {
        return mGradeId;
    }

    public void setGradeId(String gradeId) {
        mGradeId = gradeId;
    }

    public String getGradeName() {
        return mGradeName;
    }

    public void setGradeName(String gradeName) {
        mGradeName = gradeName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLocalId() {
        return mLocalId;
    }

    public void setLocalId(String localId) {
        mLocalId = localId;
    }

    public String getServerQuestion() {
        return mServerQuestion;
    }

    public void setServerQuestion(String serverQuestion) {
        mServerQuestion = serverQuestion;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getChapter() {
        return mchapter;
    }

    public void setChapter(String mchapter) {
        this.mchapter = mchapter;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    @Override
    public String toString() {
        return "\nEvlQ{" +
                "\nmGradeName='" + mGradeName + '\'' +
                ", \nmId='" + mId + '\'' +
                ", \nmTitle='" + mTitle + '\'' +
                ", \nmAnswer='" + mAnswer + '\'' +
                ", \nmActive='" + mActive + '\'' +
                ", \nmGradeId='" + mGradeId + '\'' +
                ", \nmLocalId='" + mLocalId + '\'' +
                ", \nmServerQuestion='" + mServerQuestion + '\'' +
                ", \nmchapter='" + mchapter + '\'' +
                ", \nmSubject='" + mSubject + '\'' +
                '}';
    }
}
