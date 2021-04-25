/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

/**
 * Created by Mir Ferdous on 15/09/2017.
 */

public class LastPerfMdl {
    private String _id;
    private String schID;
    private String schName;
    private String code;
    private String grade;
    private String eduType;
    private String lastRepPerform;
    private String lastRepPerformDate;
    private String lastEvalPerform;
    private String lastEvalPerformDate;
    private String lastAttenPerform;
    private String lastAttenPerformDate;
    private String time_lm;

    //========================= methods ===========================================================
    public void removeNull() {
        if (_id == null)
            _id = "";
        if (schID == null)
            schID = "";
        if (schName == null)
            schName = "";
        if (code == null)
            code = "";
        if (grade == null)
            grade = "";
        if (eduType == null)
            eduType = "";
        if (lastRepPerform == null)
            lastRepPerform = "";
        if (lastRepPerformDate == null)
            lastRepPerformDate = "";
        if (lastEvalPerform == null)
            lastEvalPerform = "";
        if (lastEvalPerformDate == null)
            lastEvalPerformDate = "";
        if (lastAttenPerform == null)
            lastAttenPerform = "";
        if (lastAttenPerformDate == null)
            lastAttenPerformDate = "";
    }

    //______________ constructors __________________


    public LastPerfMdl() {
    }

    public LastPerfMdl(String schID, String schName, String lastRepPerform, String lastRepPerformDate,
                       String lastEvalPerform, String lastEvalPerformDate, String lastAttenPerform,
                       String lastAttenPerformDate) {
        this.schID = schID;
        this.schName = schName;
        this.lastRepPerform = lastRepPerform;
        this.lastRepPerformDate = lastRepPerformDate;
        this.lastEvalPerform = lastEvalPerform;
        this.lastEvalPerformDate = lastEvalPerformDate;
        this.lastAttenPerform = lastAttenPerform;
        this.lastAttenPerformDate = lastAttenPerformDate;
    }

    public LastPerfMdl(String _id, String schID, String schName, String code, String grade, String eduType, String lastRepPerform, String lastRepPerformDate, String lastEvalPerform, String lastEvalPerformDate, String lastAttenPerform, String lastAttenPerformDate) {
        this._id = _id;
        this.schID = schID;
        this.schName = schName;
        this.code = code;
        this.grade = grade;
        this.eduType = eduType;
        this.lastRepPerform = lastRepPerform;
        this.lastRepPerformDate = lastRepPerformDate;
        this.lastEvalPerform = lastEvalPerform;
        this.lastEvalPerformDate = lastEvalPerformDate;
        this.lastAttenPerform = lastAttenPerform;
        this.lastAttenPerformDate = lastAttenPerformDate;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEduType() {
        return eduType;
    }

    public void setEduType(String type) {
        this.eduType = type;
    }

    public String getSchID() {
        return schID;
    }

    public void setSchID(String schID) {
        this.schID = schID;
    }

    public String getLastRepPerform() {
        return lastRepPerform;
    }

    public void setLastRepPerform(String lastRepPerform) {
        this.lastRepPerform = lastRepPerform;
    }

    public String getLastRepPerformDate() {
        return lastRepPerformDate;
    }

    public void setLastRepPerformDate(String lastRepPerformDate) {
        this.lastRepPerformDate = lastRepPerformDate;
    }

    public String getLastEvalPerform() {
        return lastEvalPerform;
    }

    public void setLastEvalPerform(String lastEvalPerform) {
        this.lastEvalPerform = lastEvalPerform;
    }

    public String getTime_lm() {
        return time_lm;
    }

    public void setTime_lm(String time_lm) {
        this.time_lm = time_lm;
    }

    public String getLastEvalPerformDate() {
        return lastEvalPerformDate;
    }

    public void setLastEvalPerformDate(String lastEvalPerformDate) {
        this.lastEvalPerformDate = lastEvalPerformDate;
    }

    public String getLastAttenPerform() {
        return lastAttenPerform;
    }

    public void setLastAttenPerform(String lastAttenPerform) {
        this.lastAttenPerform = lastAttenPerform;
    }

    public String getLastAttenPerformDate() {
        return lastAttenPerformDate;
    }

    public void setLastAttenPerformDate(String lastAttenPerformDate) {
        this.lastAttenPerformDate = lastAttenPerformDate;
    }

    @Override
    public String toString() {
        return
                "{_id='" + _id + '\'' +
                        "\n, schID='" + schID + '\'' +
                        "\n, schName='" + schName + '\'' +
                        "\n, code='" + code + '\'' +
                        "\n, grade='" + grade + '\'' +
                        "\n, eduType='" + eduType + '\'' +
                        "\n, lastRepPerform='" + lastRepPerform + '\'' +
                        "\n, lastRepPerformDate='" + lastRepPerformDate + '\'' +
                        "\n, lastEvalPerform='" + lastEvalPerform + '\'' +
                        "\n, lastEvalPerformDate='" + lastEvalPerformDate + '\'' +
                        "\n, lastAttenPerform='" + lastAttenPerform + '\'' +
                        "\n, lastAttenPerformDate='" + lastAttenPerformDate + '\'' +
                        '}' + "\n";
    }
}
