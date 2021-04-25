/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.reporting;

/**
 * Created by ASUS on 10/10/2017.
 */

public class ReportingData {
    String question = null;
    String repDetails = null;
    String repDetailsVal = null;
    String repPriorityVal = null;
    boolean yesSelected = false;
    boolean noSelected = false;
    boolean majorSelected = false;
    boolean moderateSelected = false;
    boolean minorSelected = false;

    public ReportingData(String question,String repDetailsVal, String repDetails, String repPriorityVal, boolean yesSelected, boolean noSelected, boolean majorSelected, boolean moderateSelected, boolean minorSelected) {
        this.question = question;
        this.repDetailsVal = repDetailsVal;
        this.repDetails = repDetails;
        this.repPriorityVal = repPriorityVal;
        this.yesSelected = yesSelected;
        this.noSelected = noSelected;
        this.majorSelected = majorSelected;
        this.moderateSelected = moderateSelected;
        this.minorSelected = minorSelected;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getRepDetailsVal() {
        return repDetailsVal;
    }

    public void setRepDetailsVal(String repDetailsVal) {
        this.repDetailsVal = repDetailsVal;
    }
    public String getRepDetails() {
        return repDetails;
    }

    public void setRepDetails(String repDetails) {
        this.repDetails = repDetails;
    }

    public String getRepPriorityVal() {
        return repPriorityVal;
    }

    public void setRepPriorityVal(String repPriorityVal) {
        this.repPriorityVal = repPriorityVal;
    }

    public boolean isYesSelected() {
        return yesSelected;
    }

    public void setYesSelected(boolean yesSelected) {
        this.yesSelected = yesSelected;
    }

    public boolean isNoSelected() {
        return noSelected;
    }

    public void setNoSelected(boolean noSelected) {
        this.noSelected = noSelected;
    }

    public boolean isMajorSelected() {
        return majorSelected;
    }

    public void setMajorSelected(boolean majorSelected) {
        this.majorSelected = majorSelected;
    }

    public boolean isModerateSelected() {
        return moderateSelected;
    }

    public void setModerateSelected(boolean moderateSelected) {
        this.moderateSelected = moderateSelected;
    }

    public boolean isMinorSelected() {
        return minorSelected;
    }

    public void setMinorSelected(boolean minorSelected) {
        this.minorSelected = minorSelected;
    }

    public void okPressed(){

    }

}
