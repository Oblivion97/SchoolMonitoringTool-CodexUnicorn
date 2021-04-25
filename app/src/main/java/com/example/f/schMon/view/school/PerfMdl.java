/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.school;

/**
 * Created by Mir Ferdous on 13/10/2017.
 */

public class PerfMdl {
    private String schID;
    private String schName;
    private String perf;
    private String date_report;

    public PerfMdl(String schID, String date_report, String perf) {
        this.schID = schID;
        this.perf = perf;
        this.date_report = date_report;
    }

    public String getSchID() {
        return schID;
    }

    public void setSchID(String schID) {
        this.schID = schID;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public String getPerf() {
        return perf;
    }

    public void setPerf(String perf) {
        this.perf = perf;
    }

    public String getDate_report() {
        return date_report;
    }

    public void setDate_report(String date_report) {
        this.date_report = date_report;
    }
}
