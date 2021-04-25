/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncAsikOld;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import static java.lang.Math.ceil;

/**
 * Created by ARK on 10/15/2017.
 */

public class DataSyncing extends AppCompatActivity {
    //ArrayList<DataModel> teacherList,studentList,instituteList;
    ArrayList<String> teacherIDs, studentIDs, instituteIDs;
    //ArrayList<ADataModel> aTeacherList,aStudentList,aInstituteList;
    ProgressBar teacherProgress, studentProgress, instituteProgress;
    TextView totalDownload, totalKB;
    Button syncButton;
    int total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_data);
        teacherProgress = (ProgressBar) findViewById(R.id.progressBarTeacher);
        studentProgress = (ProgressBar) findViewById(R.id.progressBarStudent);
        instituteProgress = (ProgressBar) findViewById(R.id.progressBarSchool);
        totalDownload = (TextView) findViewById(R.id.sync_total_download_value);
        totalKB = findViewById(R.id.sync_total_downloadValue);
        syncButton = findViewById(R.id.syncBtnAll);
        //teacherList = new ArrayList<>();
        teacherIDs = new ArrayList<>();
        studentIDs = new ArrayList<>();
        instituteIDs = new ArrayList<>();
        // aTeacherList = new ArrayList<>();

        // studentList = new ArrayList<>();
        // aStudentList = new ArrayList<>();

        // instituteList = new ArrayList<>();
        // aInstituteList = new ArrayList<>();

        total = 0;
    }

    public void syncNow(View v) {
        total = 0;
        URL instituteUrl = NetworkHelper.buildInstituteUrl();
        URL teacherUrl = NetworkHelper.buildTeacherUrl();
        URL studentUrl = NetworkHelper.buildStudentUrl();

        Log.e("Teacher URL Link", "" + teacherUrl);
        Log.e("Student URL Link", "" + studentUrl);
        Log.e("Institute URL Link", "" + instituteUrl);
        new instituteQueryTask().execute(instituteUrl);
        new teacherQueryTask().execute(teacherUrl);
        new studentQueryTask().execute(studentUrl);
/*
        ArrayList<AsyncTask<URL, Integer, String>> asyncTasks = new ArrayList<>();
        //AsyncTask<URL, Integer, String> asyncTask1 = new instituteQueryTask().execute(instituteUrl);
        AsyncTask<URL, Integer, String> asyncTask2 = new teacherQueryTask().execute(teacherUrl);
        //AsyncTask<URL, Integer, String> asyncTask3 = new studentQueryTask().execute(studentUrl);

        //asyncTasks.add(asyncTask1);
        asyncTasks.add(asyncTask2);
        //asyncTasks.add(asyncTask3);
        for(int i=0;i<asyncTasks.size();i++){
            AsyncTask<URL, Integer, String> asyncTaskItem = asyncTasks.get(i);
            // getStatus() would return PENDING,RUNNING,FINISHED statuses
            String status = asyncTaskItem.getStatus().toString();
            //if status is FINISHED for all the 3 async tasks, hide the progressbar
            Log.e(i+". Task Status",status);
        }*/

    }

    public void showTeacherData(String teacherResults) {
        total += teacherResults.length();
        //Log.e("Teacher total ", " " + total);
        //hiding the error message
        //errorMessageTextView.setVisibility(View.INVISIBLE);
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(teacherResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("model");


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String teacherID = jsonObject.optString("id");
                teacherIDs.add(teacherID);
                String teacherName = jsonObject.optString("username");
                String teacherGrade = jsonObject.optString("gradeName");
                String teacherInstituteName = jsonObject.optString("instituteName");
                String teacherMobile = jsonObject.optString("mobileNumber");
                DAO.insertTeacher(teacherID, teacherName, teacherGrade, teacherInstituteName, teacherMobile);
                //teacherList.add(new DataModel(teacherID,teacherName,teacherGrade,teacherInstituteName,teacherMobile));
                // Log.d((i+1)+". teacherID",teacherList.get(i).getTeacherID());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.e("show teacherSync List size",""+teacherList.size());

    }

    public void showATeacherData(String aTeacherResults) {
        total += aTeacherResults.length();
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

    public void showStudentData(String studentResults) {
        total += studentResults.length();
        //Log.e("Student size ", " " + total);
        //hiding the error message
        //errorMessageTextView.setVisibility(View.INVISIBLE);
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(studentResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("model");
            /*if (studentIDs != null) {
                studentIDs.clear();
            }*/
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String studentID = jsonObject.optString("id");
                studentIDs.add(studentID);
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
                DAO.insertStudent(studentID, studentRollNo, studentName, studentGrade, studentInstituteName, studentGender, studentWaiver, studentFather, studentGurdPhNum, studentBkash, studentDropout);

                //studentList.add(new DataModel(studentID, studentRollNo, studentName, studentGrade,  studentInstituteName, studentGender, studentWaiver, studentFather, studentGurdPhNum,studentBkash,studentDropout));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showAStudentData(String aStudentResults) {
        total += aStudentResults.length();
        /*if (aStudentList != null) {
            aStudentList.clear();
        }*/
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(aStudentResults);
            //Get the instance of JSONArray that contains JSONObjects
            JSONObject model = jsonRootObject.getJSONObject("model");
            String id = model.optString("id");
            String roll = model.optString("roll");
            String transfer_inst = model.optString("transferredSchoolId");
            String studentInstituteID = model.optString("instituteId");
            String birth = model.optString("dateOfBirth");
            String mother = model.optString("motherName");
            String gradeID= model.optString("gradeId");
            DAO.insertAStudent_idCall(id, studentInstituteID, roll, transfer_inst, birth, mother,gradeID);

            // aStudentList.add(new ADataModel(studentInstituteID));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showInstituteData(String instituteResults) {
        total += instituteResults.length();
        //Log.e("Institute size ", " " + total);
        //hiding the error message
        //errorMessageTextView.setVisibility(View.INVISIBLE);
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
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String instituteID = jsonObject.optString("id");
                instituteIDs.add(instituteID);
                //Log.e("schoolSync id ",instituteID);
                String instituteName = jsonObject.optString("name");
                String gradeName = jsonObject.optString("gradeName");
                String instituteCode = jsonObject.optString("code");
                String instituteTotalStudents = jsonObject.optString("totalStudent");
                String instituteEducationType = jsonObject.optString("instituteTypeName");
                DAO.insertSchool(instituteID, instituteName, gradeName, instituteCode,
                        instituteTotalStudents, instituteEducationType, null, null);

                // instituteList.add(new DataModel(instituteID,instituteName,institutePoName,instituteCode,instituteTotalStudents,instituteEducationType));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.e("show instituteList data",instituteIDs.size()+"");
    }

    public void showAnInstituteData(String anInstituteResults) {

        total += anInstituteResults.length();
        /*if (aInstituteList != null) {
            aInstituteList.clear();
        }*/
        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(anInstituteResults);

            JSONObject model = jsonRootObject.getJSONObject("model");
            String id = model.optString("id");
            String institutePoID = model.optString("poId");
            //Log.e("pO id",institutePoID);
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

    public void showErrorMessage() {
        // errorMessageTextView.setVisibility(View.VISIBLE);
        final Snackbar snackbar = Snackbar.make(syncButton, "Something went wrong", Snackbar.LENGTH_LONG);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
        Log.e("Error", "showErrorMessage called");
    }

    public class teacherQueryTask extends AsyncTask<URL, Integer, String> {
        //Overriding onPreExecute to set the loading indicator to visible
        int count = 0;

        @Override
        protected void onPreExecute() {
            teacherProgress.setVisibility(View.VISIBLE);
            teacherProgress.setProgress(0);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String teacherList = null;
            try {
                teacherList = NetworkHelper.getResponseFromHttpUrl(url);
                // Log.e("count",teacherList.length()+" OK ");
                count = teacherList.length();
                for (int i = 0; i < count; i++) {
                    publishProgress((int) ((i / (float) count) * 100));
                    //Thread.sleep(100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return teacherList; // returning the JSONString data
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            teacherProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String teacherResults) {
            // progressBar.setVisibility(View.INVISIBLE); // As soon as the loading is complete, hiding the loading indicator
            //teacherProgress.setVisibility(View.INVISIBLE);
            //Log.e("Teacher ",teacherResults.length()+"");
            if (teacherResults != null && !teacherResults.equals("")) {
                // showJsonDataView if we have valid, non-null results
                showTeacherData(teacherResults);
                for (int i = 0; i < teacherIDs.size(); i++) {
                    URL aTeacherUrl = NetworkHelper.buildTeacherUrl(teacherIDs.get(i));
                    new aTeacherQueryTask().execute(aTeacherUrl);
                }
            } else {
                showErrorMessage();//showErrorMessage if the result is null in onPostExecute
            }

            totalDownload.setText(total + " Bytes");
            totalKB.setText(ceil(total / 1024.0) + " Kilobytes");
        }
    }

    public class aTeacherQueryTask extends AsyncTask<URL, Integer, String> {
        //Overriding onPreExecute to set the loading indicator to visible
        int count = 0;

        @Override
        protected void onPreExecute() {
            teacherProgress.setVisibility(View.VISIBLE);
            teacherProgress.setProgress(0);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String aTeacherList = null;
            try {
                aTeacherList = NetworkHelper.getResponseFromHttpUrl(url);
                count = aTeacherList.length();
                for (int i = 0; i < count; i++) {
                    publishProgress((int) ((i / (float) count) * 100));
                    //Thread.sleep(100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return aTeacherList; // returning the JSONString data
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            teacherProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String aTeacherResults) {
            if (aTeacherResults != null && !aTeacherResults.equals("")) {
                // showJsonDataView if we have valid, non-null results
                showATeacherData(aTeacherResults);
                //Log.e("data",aTeacherResults.length()+"");
            } else {
                showErrorMessage();//showErrorMessage if the result is null in onPostExecute
            }
            //Log.e("outside","teacherSync size "+teacherList.size()+" a teacherSync size "+aTeacherList.size());
            totalDownload.setText(total + " Bytes");
            totalKB.setText(ceil(total / 1024.0) + " Kilobytes");
            // teacherProgress.setVisibility(View.INVISIBLE);
        }
    }

    public class studentQueryTask extends AsyncTask<URL, Integer, String> {
        //Overriding onPreExecute to set the loading indicator to visible
        int count = 0;

        @Override
        protected void onPreExecute() {
            studentProgress.setVisibility(View.VISIBLE);
            studentProgress.setProgress(0);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String studentList = null;
            try {
                studentList = NetworkHelper.getResponseFromHttpUrl(url);
                Log.e("count studentList", studentList.length() + " OK ");
                count = studentList.length();
                for (int i = 0; i < count; i++) {
                    publishProgress((int) ((i / (float) count) * 100));
                    //Thread.sleep(100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return studentList; // returning the JSONString data
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            studentProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String studentResults) {

            // progressBar.setVisibility(View.INVISIBLE); // As soon as the loading is complete, hiding the loading indicator
            if (studentResults != null && !studentResults.equals("")) {
                // showJsonDataView if we have valid, non-null results
                showStudentData(studentResults);
                //Log.e("data",studentResults.length()+"");
                for (int i = 0; i < studentIDs.size(); i++) {
                    URL aStudentUrl = NetworkHelper.buildStudentUrl(studentIDs.get(i));
                    new aStudentQueryTask().execute(aStudentUrl);
                }
            } else {
                showErrorMessage();//showErrorMessage if the result is null in onPostExecute
            }

            //totalDownload.setText(Integer.parseInt(totalDownload.getText().toString())+total);
            totalDownload.setText(total + " Bytes");
            totalKB.setText(ceil(total / 1024.0) + " Kilobytes");

        }
    }

    public class aStudentQueryTask extends AsyncTask<URL, Integer, String> {
        //Overriding onPreExecute to set the loading indicator to visible
        int count = 0;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String aStudentList = null;
            try {
                aStudentList = NetworkHelper.getResponseFromHttpUrl(url);
                count = aStudentList.length();
                for (int i = 0; i < count; i++) {
                    publishProgress((int) ((i / (float) count) * 100));
                    //Thread.sleep(100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return aStudentList; // returning the JSONString data
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            studentProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String aStudentResults) {
            if (aStudentResults != null && !aStudentResults.equals("")) {
                // showJsonDataView if we have valid, non-null results
                showAStudentData(aStudentResults);
                //Log.e("data",aTeacherResults.length()+"");
            } else {
                showErrorMessage();//showErrorMessage if the result is null in onPostExecute
            }
            totalDownload.setText(total + " Bytes");
            totalKB.setText(ceil(total / 1024.0) + " Kilobytes");
            //studentProgress.setVisibility(View.INVISIBLE);
            // Log.e("outside","studentSync size "+studentList.size()+" a studentSync size "+aStudentList.size());
        }
    }

    public class instituteQueryTask extends AsyncTask<URL, Integer, String> {
        //Overriding onPreExecute to set the loading indicator to visible
        int count = 0;

        @Override
        protected void onPreExecute() {
            instituteProgress.setVisibility(View.VISIBLE);
            instituteProgress.setProgress(0);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String instituteList = null;
            try {
                instituteList = NetworkHelper.getResponseFromHttpUrl(url);
                //Log.e("count",instituteList.length()+" OK ");
                count = instituteList.length();
                for (int i = 0; i < count; i++) {
                    publishProgress((int) ((i / (float) count) * 100));
                    //Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return instituteList; // returning the JSONString data
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            instituteProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String instituteResults) {
            // progressBar.setVisibility(View.INVISIBLE); // As soon as the loading is complete, hiding the loading indicator
            if (instituteResults != null && !instituteResults.equals("")) {
                // showJsonDataView if we have valid, non-null results
                showInstituteData(instituteResults);
                Log.e("schoolSync ", instituteIDs.size() + "");
                for (int i = 0; i < instituteIDs.size(); i++) {
                    URL anInstituteUrl = NetworkHelper.buildInstituteUrl(instituteIDs.get(i));
                    Log.e("anInstituteUrl", anInstituteUrl.toString());
                    new anInstituteQueryTask().execute(anInstituteUrl);
                }
            } else {
                showErrorMessage();//showErrorMessage if the result is null in onPostExecute
            }

            totalDownload.setText(total + " Bytes");
            totalKB.setText((total / 1024.0) + " Kilobytes");
        }
    }

    public class anInstituteQueryTask extends AsyncTask<URL, Integer, String> {
        //Overriding onPreExecute to set the loading indicator to visible
        int count = 0;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String anInstituteList = null;
            try {
                anInstituteList = NetworkHelper.getResponseFromHttpUrl(url);
                count = anInstituteList.length();
                for (int i = 0; i < count; i++) {
                    publishProgress((int) ((i / (float) count) * 100));
                    //Thread.sleep(100);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return anInstituteList; // returning the JSONString data
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            instituteProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String anInstituteResults) {
            if (anInstituteResults != null && !anInstituteResults.equals("")) {
                // showJsonDataView if we have valid, non-null results
                showAnInstituteData(anInstituteResults);
                //Log.e("data",aTeacherResults.length()+"");
            } else {
                showErrorMessage();//showErrorMessage if the result is null in onPostExecute
            }
            totalDownload.setText(total + " Bytes");
            totalKB.setText(ceil(total / 1024.0) + " Kilobytes");
            //instituteProgress.setVisibility(View.INVISIBLE);
        }
    }
}
