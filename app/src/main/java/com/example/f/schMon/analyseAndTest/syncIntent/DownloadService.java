/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncIntent;

/**
 * Created by Jisan on 10/31/2017.
 */

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.view.dashboard.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadService extends IntentService {
    private NotificationManager mNotificationManager;
    public static final int NOTIFICATION_ID = 1;

    final String BASE_SCHOOL_URL = "http://bepmis.brac.net/rest/apps/institute/";
    final String POSTFIX_URL = "?uname=kholilur&passw=123456&max=100000";

    final String BASE_TEACHER_URL = "http://bepmis.brac.net/rest/apps/teacher/";

    final String BASE_STUDENT_URL = "http://bepmis.brac.net/rest/apps/student/";

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
    }


    public DownloadService() {
        super(DownloadService.class.getName());
        Log.e("service name", DownloadService.class.getName());
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("service", "started");
        showNotification("Download Service Running");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("service", "destroyed");
        showNotification("Download completed");
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    public void showNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, DashboardActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Service Notification")
                        .setContentText(msg)
                        .setSmallIcon(R.drawable.braclogo_about);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        ArrayList<String> urls = (ArrayList<String>) intent.getSerializableExtra("urls");
        Log.e(TAG, urls.toString());
        Bundle bundle = new Bundle();
        for (int i = 0; i < urls.size(); i++) {
            if (!TextUtils.isEmpty(urls.get(i))) {
            /* Update UI: Download Service is Running */
                receiver.send(STATUS_RUNNING, Bundle.EMPTY);

                try {
                    String[] results = downloadData(urls.get(i), i);
                /* Sending result back to activity */
                    if (null != results && results.length > 0) {

                        bundle.putString("result", i + "");
                        receiver.send(STATUS_FINISHED, bundle);
                    }

                } catch (Exception e) {

                /* Sending error message back to activity */
                    bundle.putString(Intent.EXTRA_TEXT, e.toString());
                    receiver.send(STATUS_ERROR, bundle);
                }
            }
        }
        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    public boolean showTeacherData(String teacherResults) {
        boolean status = false;
        //total+=teacherResults.length();
        //Log.e("Teacher total ", " " + total);
        //hiding the error message
        //errorMessageTextView.setVisibility(View.INVISIBLE);
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(teacherResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("model");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                String teacherID = jsonObject.optString("id");
                try {
                    downloadAData(BASE_TEACHER_URL + teacherID + POSTFIX_URL, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DownloadException e) {
                    e.printStackTrace();
                }
                //Log.e("teacherID id ",teacherID);
                //teacherIDs.add(teacherID);
                String teacherName = jsonObject.optString("username");
                String teacherGrade = jsonObject.optString("gradeName");
                String teacherInstituteName = jsonObject.optString("instituteName");
                String teacherMobile = jsonObject.optString("mobileNumber");
                status = DAO.insertTeacher(teacherID, teacherName, teacherGrade, teacherInstituteName, teacherMobile);
                //teacherList.add(new DataModel(teacherID,teacherName,teacherGrade,teacherInstituteName,teacherMobile));
                // Log.d((i+1)+". teacherID",teacherList.get(i).getTeacherID());
            }
        } catch (JSONException e) {
            status = false;
            e.printStackTrace();
        }
        return status;

    }

    public void showATeacherData(String aTeacherResults) {
        // total+=aTeacherResults.length();
        //Log.e("ATeacherData", " " + total);
       /* if (aTeacherList != null) {
            aTeacherList.clear();
        }*/
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(aTeacherResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONObject model = jsonRootObject.getJSONObject("model");
            String aTeacherID = model.optString("id");
            String instituteID = model.optString("instituteId");
            String presentAddrs = model.optString("presentAddress");
            // Log.e("teacherList",". "+instituteID+" addrs "+presentAddrs);
            DAO.insertATeacher_idCall(aTeacherID, instituteID, presentAddrs,null,null);

            //aTeacherList.add(new ADataModel(instituteID,presentAddrs));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean showStudentData(String studentResults) {
        boolean status = false;
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(studentResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("model");
            /*if (studentIDs != null) {
                studentIDs.clear();
            }*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                String studentID = jsonObject.optString("id");
                try {
                    downloadAData(BASE_STUDENT_URL + studentID + POSTFIX_URL, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DownloadException e) {
                    e.printStackTrace();
                }
                //studentIDs.add(studentID);
                //String studentInstituteID= jsonObject.optString("instituteId");
                String studentRollNo = jsonObject.optString("studentId");
                String studentName = jsonObject.optString("fullName");
                String studentGrade = jsonObject.optString("gradeId");
                String studentInstituteName = jsonObject.optString("instituteId");
                String studentGender = jsonObject.optString("sex");
                String studentWaiver = jsonObject.optString("waiver");
                String studentFather = jsonObject.optString("fatherName");
                String studentGurdPhNum = jsonObject.optString("guardianMobile");
                String studentBkash = jsonObject.optString("bkashNumber");
                String studentDropout = jsonObject.optString("dropout");
                status = DAO.insertStudent(studentID, studentRollNo, studentName, studentGrade, studentInstituteName, studentGender, studentWaiver, studentFather, studentGurdPhNum, studentBkash, studentDropout);

                //studentList.add(new DataModel(studentID, studentRollNo, studentName, studentGrade,  studentInstituteName, studentGender, studentWaiver, studentFather, studentGurdPhNum,studentBkash,studentDropout));
            }
        } catch (JSONException e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public void showAStudentData(String aStudentResults) {
        //total+=aStudentResults.length();
        /*if (aStudentList != null) {
            aStudentList.clear();
        }*/
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(aStudentResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONObject model = jsonRootObject.getJSONObject("model");
            String id = model.optString("id");
            String studentInstituteID = model.optString("instituteId");
            String gradeID=model.optString("gradeId");
            DAO.insertAStudent_idCall(id, studentInstituteID, null, null, null,null,gradeID);

            // aStudentList.add(new ADataModel(studentInstituteID));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean showInstituteData(String instituteResults) {

        boolean status = false;
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(instituteResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("model");
           /* if (instituteList != null) {
                instituteList.clear();
            }
            instituteList = new ArrayList<>();*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                String instituteID = jsonObject.optString("id");
                try {
                    downloadAData(BASE_SCHOOL_URL + instituteID + POSTFIX_URL, 0);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DownloadException e) {
                    e.printStackTrace();
                }
                ;

                String instituteName = jsonObject.optString("name");
                String institutePoName = jsonObject.optString("poUserName");
                String instituteCode = jsonObject.optString("code");
                String instituteTotalStudents = jsonObject.optString("totalStudent");
                String instituteEducationType = jsonObject.optString("instituteTypeName");
                status = DAO.insertSchool(instituteID, instituteName, institutePoName, instituteCode,
                        instituteTotalStudents, instituteEducationType,null,null);

                // instituteList.add(new DataModel(instituteID,instituteName,institutePoName,instituteCode,instituteTotalStudents,instituteEducationType));
            }
        } catch (JSONException e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    public void showAnInstituteData(String anInstituteResults) {

        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(anInstituteResults);

            JSONObject model = jsonRootObject.getJSONObject("model");
            String id = model.optString("id");
            String institutePoID = model.optString("poId");
            Log.e("pO id", institutePoID);
            //String instituteAddress = model.optString("presentAddress");
            String instituteAddress = "";
            String gradeID="";
            DAO.insertASchool_idCall(id, institutePoID, instituteAddress,gradeID);
            // Log.e("teacherList",". "+instituteID+" addrs "+presentAddrs);
            //aInstituteList.add(new ADataModel(institutePoID,instituteAddress));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String[] downloadData(String requestUrl, int i) throws IOException, DownloadException {
        InputStream inputStream = null;

        HttpURLConnection urlConnection = null;

        /* forming th java.net.URL object1 */
        URL url = new URL(requestUrl);

        urlConnection = (HttpURLConnection) url.openConnection();

        /* for Get request */
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            String response = convertInputStreamToString(inputStream);

            String[] results = parseResult(response, i);

            return results;
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }

    private void downloadAData(String requestUrl, int i) throws IOException, DownloadException {
        InputStream inputStream = null;

        HttpURLConnection urlConnection = null;

        /* forming th java.net.URL object1 */
        URL url = new URL(requestUrl);

        urlConnection = (HttpURLConnection) url.openConnection();

        /* for Get request */
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

        /* 200 represents HTTP OK */
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            String response = convertInputStreamToString(inputStream);
            parseAResult(response, i);

        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    private String[] parseResult(String result, int which) {

        String[] updateStatus = new String[1];
        if (which == 0) {
            updateStatus[0] = "Is School Updated ? " + showInstituteData(result);
            //Log.e("which "+which, updateStatus[0]+" !");
        } else if (which == 1) {
            updateStatus[0] = "Is Teacher Updated ? " + showTeacherData(result);
            //Log.e("which "+which, updateStatus[0]+" !");
        } else if (which == 2) {
            updateStatus[0] = "Is Student Updated ? " + showStudentData(result);
            //Log.e("which "+which, updateStatus[0]+" !");
        }

        return updateStatus;
    }

    private void parseAResult(String result, int which) {
        if (which == 0) {
            showAnInstituteData(result);
        } else if (which == 1) {
            showATeacherData(result);
        } else if (which == 2) {
            showAStudentData(result);
        }

    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}

