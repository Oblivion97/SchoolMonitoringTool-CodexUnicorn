/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import java.util.Calendar;

public class EvntDateAttn {
    public String date;
    public String attendance;
    public Calendar calendar;

    public EvntDateAttn(String date, String attendance) {
        this.date = date;
        this.attendance = attendance;
    }

    public EvntDateAttn(String date) {
        this.date = date;
    }

    public EvntDateAttn(Calendar calendar) {
        this.calendar = calendar;
    }
}
