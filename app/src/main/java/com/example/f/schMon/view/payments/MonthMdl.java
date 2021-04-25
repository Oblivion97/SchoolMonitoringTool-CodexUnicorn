/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments;

/**
 * Created by Mir Ferdous on 08/12/2017.
 */
public class MonthMdl {

    private int id;
    private String monthName;

    public MonthMdl(String monthName, int id) {

        this.monthName = monthName;
        this.id = id;
    }

    public String getMonthName() {

        return monthName;
    }

    public void setMonthName(String Title) {

        this.monthName = Title;
    }

    public int getId() {

        return id;
    }

    public void setId(int ImageId) {

        this.id = ImageId;
    }

    @Override
    public String toString() {
        return monthName;
    }
}