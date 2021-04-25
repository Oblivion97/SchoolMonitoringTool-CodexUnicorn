/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

/**
 * Created by Mir Ferdous on 9/21/2017.
 */

public class NotifData {
    String id = null;
    String title = null;
    String schoolName = null;
    String description = null;
    String grade = null;
    String date = null;
    boolean yesSelected = false;
    boolean noSelected = false;

    public NotifData(String id, String title, String schoolName, String description, String grade, boolean yesSelected, boolean noSelected) {
        super();
        this.id = id;
        this.title = title;
        this.schoolName = schoolName;
        this.description = description;
        this.grade = grade;
        this.yesSelected = yesSelected;
        this.noSelected = noSelected;
    }
    public NotifData(String id, String title, String schoolName, String description, String grade, String date) {
        super();
        this.id = id;
        this.title = title;
        this.schoolName = schoolName;
        this.description = description;
        this.grade = grade;
        this.date = date;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){return date;}
    public String getSchoolName() {
        return schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isYesSelected() {
        return yesSelected;
    }
    public void setYesSelected(boolean selected) {
        this.yesSelected = selected;
    }
    public boolean isNoSelected() {
        return noSelected;
    }
    public void setNoSelected(boolean selected) {
        this.noSelected = selected;
    }
}
