/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncAsikOld;

import android.net.Uri;

import com.example.f.schMon.controller.A;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by ASUS on 10/15/2017.
 * http://bepmis.brac.net/rest/apps/education-type/list?uname=kholilur&passw=123456
 * <p>
 * http://bepmis.brac.net/rest/apps/institute?uname=kholilur&passw=123456
 * http://bepmis.brac.net/rest/apps/institute/47272800-1957-inst-8631-119f6fad8b4c?uname=kholilur&passw=123456
 * <p>
 * http://bepmis.brac.net/rest/apps/student?uname=kholilur&passw=123456
 * http://bepmis.brac.net/rest/apps/student/47272810-2988-stud-b929-b55abe6bd276?uname=kholilur&passw=123456
 * <p>
 * http://bepmis.brac.net/rest/apps/teacher/listAllTeacher?uname=kholilur&passw=123456
 * http://bepmis.brac.net/rest/apps/teacher/47272806-7016-user-89ee-ba03882a41d9?uname=kholilur&passw=123456
 */


public class NetworkHelper {
    final static String TEACHER_BASE_URL =
            "http://bepmis.brac.net/rest/apps/teacherSync/listAllTeacher?";
    final static String A_TEACHER_BASE_URL =
            "http://bepmis.brac.net/rest/apps/teacherSync/";

    final static String STUDENT_BASE_URL =
            "http://bepmis.brac.net/rest/apps/studentSync?";
    final static String A_STUDENT_BASE_URL =
            "http://bepmis.brac.net/rest/apps/studentSync/";

    final static String INSTITUTE_BASE_URL =
            "http://bepmis.brac.net/rest/apps/institute?";
    final static String A_INSTITUTE_BASE_URL =
            "http://bepmis.brac.net/rest/apps/institute/";

    final static String UNAME = "uname";
    final static String PASSW = "passw";
    private static String uname = A.getU();
    private static String passw = A.getP();

    final static String MAX = "max";
    private static String max = "10000";

    public static int contentLength = 0;

    public static URL buildTeacherUrl() {
        Uri builtUri = Uri.parse(TEACHER_BASE_URL).buildUpon()
                .appendQueryParameter(UNAME, uname)
                .appendQueryParameter(PASSW, passw)
                .appendQueryParameter(MAX, max)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTeacherUrl(String teacherID) {
        Uri builtUri = Uri.parse(A_TEACHER_BASE_URL + teacherID + "?").buildUpon()
                .appendQueryParameter(UNAME, uname)
                .appendQueryParameter(PASSW, passw)
                .appendQueryParameter(MAX, max)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildStudentUrl() {
        Uri builtUri = Uri.parse(STUDENT_BASE_URL).buildUpon()
                .appendQueryParameter(UNAME, uname)
                .appendQueryParameter(PASSW, passw)
                .appendQueryParameter(MAX, max)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildStudentUrl(String studentID) {
        Uri builtUri = Uri.parse(A_STUDENT_BASE_URL + studentID + "?").buildUpon()
                .appendQueryParameter(UNAME, uname)
                .appendQueryParameter(PASSW, passw)
                .appendQueryParameter(MAX, max)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildInstituteUrl() {
        Uri builtUri = Uri.parse(INSTITUTE_BASE_URL).buildUpon()
                .appendQueryParameter(UNAME, uname)
                .appendQueryParameter(PASSW, passw)
                .appendQueryParameter(MAX, max)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildInstituteUrl(String instituteID) {
        Uri builtUri = Uri.parse(A_INSTITUTE_BASE_URL + instituteID + "?").buildUpon()
                .appendQueryParameter(UNAME, uname)
                .appendQueryParameter(PASSW, passw)
                .appendQueryParameter(MAX, max)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /*
    This method returns the entire result from the HTTP response.
    param url, The URL to fetch the HTTP response from.
    return The contents of the HTTP response.
    throws IOException Related to network and stream reading
   */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        contentLength += urlConnection.getContentLength();
        //Log.e("ContentLength","size "+urlConnection.getContentLength());
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");// \A regExpression means The beginning of the input

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return "Error!";
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
