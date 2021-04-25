/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.log_edit;

/**
 * Created by Mir Ferdous on 01/10/2017.
 */

public class RepPerf {
    private String sch_id;
    private String sch_name;
    private String adminPerformRatio;
    private String synced;
    private String date_report;
    private String date_update;
    private String time_lm;
    private String totalQues;

    public RepPerf() {
    }


    public RepPerf(String sch_id, String adminPerformRatio, String date_report) {
        this.sch_id = sch_id;
        this.adminPerformRatio = adminPerformRatio;
        this.date_report = date_report;
    }

    public RepPerf(String sch_id, String sch_name, String adminPerformRatio, String synced, String date_report, String date_update, String time_lm, String totalQues) {
        this.sch_id = sch_id;
        this.sch_name = sch_name;
        this.adminPerformRatio = adminPerformRatio;
        this.synced = synced;
        this.date_report = date_report;
        this.date_update = date_update;
        this.time_lm = time_lm;
        this.totalQues = totalQues;
    }


    public String getSch_id() {
        return sch_id;
    }

    public void setSch_id(String sch_id) {
        this.sch_id = sch_id;
    }

    public String getSch_name() {
        return sch_name;
    }

    public void setSch_name(String sch_name) {
        this.sch_name = sch_name;
    }

    public String getAdminPerformRatio() {
        return adminPerformRatio;
    }

    public void setAdminPerformRatio(String adminPerformRatio) {
        this.adminPerformRatio = adminPerformRatio;
    }

    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }

    public String getDate_report() {
        return date_report;
    }

    public void setDate_report(String date_report) {
        this.date_report = date_report;
    }

    public String getDate_update() {
        return date_update;
    }

    public void setDate_update(String date_update) {
        this.date_update = date_update;
    }

    public String getTime_lm() {
        return time_lm;
    }

    public void setTime_lm(String time_lm) {
        this.time_lm = time_lm;
    }

    public String getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
    }

    @Override
    public String toString() {
        return "RepPerf{" +
                "sch_id='" + sch_id + '\'' +
                ", \nsch_name='" + sch_name + '\'' +
                ", \nadminPerformRatio='" + adminPerformRatio + '\'' +
                ", \nsynced='" + synced + '\'' +
                ", \ndate_report='" + date_report + '\'' +
                ", \ndate_update='" + date_update + '\'' +
                ", \ntime_lm='" + time_lm + '\'' +
                ", \ntotalQues='" + totalQues + '\'' +
                '}'+"\n\n";
    }
}
