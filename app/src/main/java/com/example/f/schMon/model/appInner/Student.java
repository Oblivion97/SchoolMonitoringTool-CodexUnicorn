/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Mir Ferdous on 13/06/2017.
 */

public class Student implements Parcelable, Comparable<Student> {
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    //________________________________constructors__________________________________
    public String id, name, father, mother, guardianPhn, gender, address, age;
    @Nullable
  public   String roll;

    public Student(String id, String roll, String name, String father, String mother,
                   String guardianPhn, String gender, String address, String age) {
        this.id = id;
        this.roll = roll;
        this.name = name;
        this.father = father;
        this.mother = mother;
        this.gender = gender;
        this.guardianPhn = guardianPhn;
        this.age = age;
        this.address = address;

    }

    public Student(String id, String roll, String name, String father,
                   String guardianPhn, String gender, String age) {
        this.id = id;
        this.roll = roll;
        this.name = name;
        this.father = father;
        this.guardianPhn = guardianPhn;
        this.gender = gender;
        this.age = age;
    }


    public Student() {
    }


    protected Student(Parcel in) {
        id = in.readString();
        roll = in.readString();
        name = in.readString();
        father = in.readString();
        mother = in.readString();
        guardianPhn = in.readString();
        gender = in.readString();
        address = in.readString();
        age = in.readString();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", roll='" + roll + '\'' +
                ", name='" + name + '\'' +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                ", guardianPhn='" + guardianPhn + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", birthDate='" + age + '\'' +
                '}';
    }

    //___________________Parcelable Implementation____________________________
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(roll);
        dest.writeString(name);
        dest.writeString(father);
        dest.writeString(mother);
        dest.writeString(guardianPhn);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(age);
    }

    //_____________________Comparable Implementation________________________________________________
    @Override
    public int compareTo(@NonNull Student o) {
        if (roll != null && o.roll != null) {
            int roll1 = Integer.parseInt(roll);
            int roll2 = Integer.parseInt(o.roll);

            if (roll1 > roll2) {
                return 1;
            } else if (roll1 < roll2) {
                return -1;
            } else {
                return 0;
            }
        } else return 0;
    }
}
