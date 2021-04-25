/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments.paymdl;

/**
 * Created by Mir Ferdous on 06/12/2017.
 */

public class SchPayMdl {
    String schName;
    String schID;
    String schCode;
    String schGrade;
    String schGradeID;
    String payable;
    String paid;
    String due;
    int payStatusIcon;

    //=========================================================================================================================

    public SchPayMdl() {
    }

    public String getSchCode() {
        return schCode;
    }

    public void setSchCode(String schCode) {
        this.schCode = schCode;
    }

    public String getSchGrade() {
        return schGrade;
    }

    public void setSchGrade(String schGrade) {
        this.schGrade = schGrade;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getSchID() {
        return schID;
    }

    public void setSchID(String schID) {
        this.schID = schID;
    }

    public String getSchGradeID() {
        return schGradeID;
    }

    public void setSchGradeID(String schGradeID) {
        this.schGradeID = schGradeID;
    }

    public String getPayable() {
        return payable;
    }

    public void setPayable(String payable) {
        this.payable = payable;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public int getPayStatusIcon() {
        return payStatusIcon;
    }

    public void setPayStatusIcon(int payStatusIcon) {
        this.payStatusIcon = payStatusIcon;
    }

    @Override
    public String toString() {
        return "SchPayMdl{" +
                "schName='" + schName + '\'' +
                ", schID='" + schID + '\'' +
                ", gradeID='" + schGradeID + '\'' +
                ", payable='" + payable + '\'' +
                ", paid='" + paid + '\'' +
                ", due='" + due + '\'' +
                ", payStatusIcon=" + payStatusIcon +
                '}';
    }
}
