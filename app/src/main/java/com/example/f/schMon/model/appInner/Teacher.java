/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

/**
 * Created by Mir Ferdous on 14/07/2017.
 */

public class Teacher {
    public String _id, tName, tID, tMobi, techrGrade, s_id, schName, address, total_bill, paid, education, gender, time_lm;


    public Teacher(String tName, String tID, String tMobi, String techrGrade, String s_id, String schName, String address, String total_bill, String paid) {

        this.tName = tName;
        this.tID = tID;
        this.tMobi = tMobi;
        this.techrGrade = techrGrade;
        this.s_id = s_id;
        this.schName = schName;
        this.address = address;
        this.total_bill = total_bill;
        this.paid = paid;
    }

    /**
     * _____________________ constructors ____________________________________
     */

    public Teacher(String tID, String tMobil, String tGrade, String schName) {
        this.tID = tID;
        this.tMobi = tMobil;
        this.techrGrade = tGrade;
        this.schName = schName;
    }

    public Teacher() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
    }

    public String gettMobi() {
        return tMobi;
    }

    public void settMobi(String tMobi) {
        this.tMobi = tMobi;
    }

    public String getTechrGrade() {
        return techrGrade;
    }

    public void setTechrGrade(String techrGrade) {
        this.techrGrade = techrGrade;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(String total_bill) {
        this.total_bill = total_bill;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTime_lm() {
        return time_lm;
    }

    public void setTime_lm(String time_lm) {
        this.time_lm = time_lm;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "_id='" + _id + '\'' +
                ", tName='" + tName + '\'' +
                ", tID='" + tID + '\'' +
                ", tMobi='" + tMobi + '\'' +
                ", techrGrade='" + techrGrade + '\'' +
                ", schID='" + s_id + '\'' +
                ", schName='" + schName + '\'' +
                ", address='" + address + '\'' +
                ", total_bill='" + total_bill + '\'' +
                ", paid='" + paid + '\'' +
                '}';
    }
}
