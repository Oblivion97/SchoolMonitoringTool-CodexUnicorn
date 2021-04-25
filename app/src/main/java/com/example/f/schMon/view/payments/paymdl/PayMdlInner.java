/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments.paymdl;

/**
 * Created by Mir Ferdous on 06/12/2017.
 */

public class PayMdlInner {
    public String sch_id;
    public String grade_id;
    public String sch_name;
    public String std_id;
    public String std_name;
    public String roll;
    public String payable;
    public String paid;
    public String month;
    public String year;
    public String category;
    public String type;
    public String time_lm;

    public PayMdlInner() {
    }

    public PayMdlInner(String sch_id, String grade_id, String sch_name,
                       String std_id, String std_name, String roll, String payable,
                       String paid, String month, String year, String category, String type, String time_lm) {
        this.sch_id = sch_id;
        this.grade_id = grade_id;
        this.sch_name = sch_name;
        this.std_id = std_id;
        this.std_name = std_name;
        this.roll = roll;
        this.payable = payable;
        this.paid = paid;
        this.month = month;
        this.year = year;
        this.category = category;
        this.type = type;
        this.time_lm = time_lm;
    }

    public String getSch_id() {
        return sch_id;
    }

    public void setSch_id(String sch_id) {
        this.sch_id = sch_id;
    }

    public String getGrade_id() {
        return grade_id;
    }

    public void setGrade_id(String grade_id) {
        this.grade_id = grade_id;
    }

    public String getSch_name() {
        return sch_name;
    }

    public void setSch_name(String sch_name) {
        this.sch_name = sch_name;
    }

    public String getStd_id() {
        return std_id;
    }

    public void setStd_id(String std_id) {
        this.std_id = std_id;
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime_lm() {
        return time_lm;
    }

    public void setTime_lm(String time_lm) {
        this.time_lm = time_lm;
    }

    @Override
    public String toString() {
        return "PayMdlInner{" +
                "sch_id='" + sch_id + '\'' +
                ", grade_id='" + grade_id + '\'' +
                ", sch_name='" + sch_name + '\'' +
                ", std_id='" + std_id + '\'' +
                ", std_name='" + std_name + '\'' +
                ", roll='" + roll + '\'' +
                ", payable='" + payable + '\'' +
                ", paid='" + paid + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", time_lm='" + time_lm + '\'' +
                '}';
    }
}
