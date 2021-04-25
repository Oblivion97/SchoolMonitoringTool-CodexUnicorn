/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab;

import java.util.List;

/********************  query url example **********************************
 http://bepmis.brac.net/rest/apps/payment?uname=kholilur&passw=123456&school_id=1316sdfsd6161ssa&year=2017

    this will return  json of class --- PaySchoolWise
 */

public class PaySchoolWise {
    String module;
    String request_type;
    String success;
    String message;
    String total;
    String school_id;
    String school_name;
    String year;

    List<StudentPayModel> student_pay_model;
}

class StudentPayModel {
    String std_id;
    String std_name;
    List<montlyPayModel> montly_pay_model;
}

class montlyPayModel {
    String month;
    String payable;//koto dite hobe
    String paid;// koto dise
    String due;
}