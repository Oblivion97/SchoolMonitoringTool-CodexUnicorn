/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

import java.util.Random;


/**
 * This model is for inflating data in layout
 */

public class FeeSalaryModel {
    public String schID;
    public String schName;
    public String namePer;
    public String paid;
    public String total_bill;
    public String img;
    private String TAG = this.getClass().getName();

    //temp delete later
    public FeeSalaryModel(String schName, String paid) {
        this.schName = schName;
        this.paid = paid;
    }

    //for schoolSync fee
    public FeeSalaryModel(String schID, String schName, String paid, String total_bill) {
        this.schID = schID;
        this.schName = schName;
        this.paid = paid;
        this.total_bill = total_bill;
    }

    //for teacherSync's salary & student's fee
    public FeeSalaryModel(String schID, String schName, String paid, String total_bill, String namePer, String img) {
        this.schID = schID;
        this.schName = schName;
        this.namePer = namePer;
        this.paid = paid;
        this.total_bill = total_bill;
        this.img = img;
    }

    //______________temp delete later___________________
    public static int[] randomNumArr(int size, int limit) {
        int[] a = new int[size];
        int tem;
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            tem = random.nextInt() % limit + 1;
            if (tem < 0) {
                tem = tem * -1;
            }
            a[i] = tem;
        }
        return a;
    }

    @Override
    public String toString() {
        return "FeeSalaryModel{" +
                "schID='" + schID + '\'' +
                ", schName='" + schName + '\'' +
                ", namePer='" + namePer + '\'' +
                ", paid='" + paid + '\'' +
                ", total_bill='" + total_bill + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
