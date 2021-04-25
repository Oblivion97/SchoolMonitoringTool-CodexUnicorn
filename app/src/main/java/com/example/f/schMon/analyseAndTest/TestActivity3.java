/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.FileHelper;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.oldAPI.StudentListModel;
import com.example.f.schMon.model.oldAPI.StudentModeI_id_respnse;
import com.example.f.schMon.model.oldAPI.StudentModel;
import com.example.f.schMon.model.oldAPI.StudentModel_id;
import com.example.f.schMon.model.oldAPI.TeacherListModel;
import com.example.f.schMon.model.oldAPI.TeacherModel;
import com.example.f.schMon.view.evaluation.AnsActivity;
import com.example.f.schMon.view.login.LoginActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TestActivity3 extends AppCompatActivity implements View.OnClickListener {
    SQLiteDatabase db;
    Gson gson = new Gson();
    private String TAG = TestActivity3.class.getName();
    private Context context = TestActivity3.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        db = Global.getDB_with_nativeWay_writable(Global.dbName);


//--------------------------------------
        Button button1 = findViewById(R.id.Button1);
        button1.setText("insertSTDid");
        button1.setOnClickListener(this);

        Button button2 = findViewById(R.id.Button2);
        button2.setText("insertSTDlist");
        button2.setOnClickListener(this);

        Button button3 = findViewById(R.id.Button3);
        button3.setText("interTeacherList");
        button3.setOnClickListener(this);

        Button button4 = findViewById(R.id.Button4);
        button4.setText("delete sch, tech, std table");
        button4.setOnClickListener(this);

        Button button5 = findViewById(R.id.Button5);
        button5.setText("insert RepActivityFrag");
        button5.setOnClickListener(this);

        Button button6 = findViewById(R.id.Button6);
        button6.setOnClickListener(this);
        button6.setText("delete data for new user");

        Button button7 = findViewById(R.id.Button7);
        button7.setOnClickListener(this);
        button7.setText("String Space Test");

        Button button8 = findViewById(R.id.Button8);
        button8.setOnClickListener(this);
        button8.setText("AnsActivity");

        Button button9 = findViewById(R.id.Button9);
        button9.setOnClickListener(this);

        Button button10 = findViewById(R.id.Button10);
        button10.setOnClickListener(this);

        Button button11 = findViewById(R.id.Button11);
        button11.setOnClickListener(this);

        Button button12 = findViewById(R.id.Button12);
        button12.setOnClickListener(this);

        Button button13 = findViewById(R.id.Button13);
        button13.setOnClickListener(this);

        Button button14 = findViewById(R.id.Button14);
        button14.setOnClickListener(this);

        Button button15 = findViewById(R.id.Button15);
        button15.setOnClickListener(this);

        Button button16 = findViewById(R.id.Button16);
        button16.setOnClickListener(this);

        Button button17 = findViewById(R.id.Button17);
        button17.setOnClickListener(this);

        Button button18 = findViewById(R.id.Button18);
        button18.setOnClickListener(this);
    }

    //--------------------------------------
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Button1:
                insertStdbyID();
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button2:
                insertStdList();
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button3:
                interTeacherList();
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button4:
                try {
                    DAO.deleteDataFromTable(db, DB.schools.table);
                    DAO.deleteDataFromTable(db, DB.students.table);
                    DAO.deleteDataFromTable(db, DB.teachers.table);
                    DAO.deleteDataFromTable(db, DB.report_admin.table);
                } catch (Exception ex) {
                }
                Toast.makeText(context, DB.schools.table + "\n" +
                        DB.students.table + "\n" +
                        DB.teachers.table + "\n" +
                        DB.report_admin.table + "\n" +
                        "table deleted", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button5:
                insertReportAdmin();
                break;


            case R.id.Button6:
             //   LoginActivity.deleteOldUserData(context, "admin");

                break;


            case R.id.Button7:
                String inp = H.multipleSpacetoSingleSpace("go  there");
                new AlertDialog.Builder(context).setMessage(H.multipleSpacetoSingleSpace(inp))
                        .create().show();
                break;


            case R.id.Button8:
                context.startActivity(new Intent(context, AnsActivity.class));
                break;


            case R.id.Button9:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button10:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button11:
                Toast.makeText(getApplicationContext(), "Button 11 Clicked", Toast.LENGTH_LONG).show();

                break;


            case R.id.Button12:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button13:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button14:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button15:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button16:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button17:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


            case R.id.Button18:
                Toast.makeText(context, v.getId() + "Clicked", Toast.LENGTH_LONG).show();
                break;


        }


        //--------------------------------------

    }


    //__________________________________________________
    void insertStdbyID() {
        ArrayList<String> idList = (ArrayList<String>) DAO.all_values_from_a_Column(db, "students", "std_id");
        Log.d(TAG, idList.toString());
        StudentModeI_id_respnse respnse = gson.fromJson(
                FileHelper.getTextFileFromAssets(context, "studentIDwise.txt"), StudentModeI_id_respnse.class);
        StudentModel_id studentModel_id = respnse.getModel();
        DAO.insertStudentbyIdDetails(context, db, studentModel_id);
    }

    void insertStdList() {
        StudentListModel res = gson.fromJson(
                FileHelper.getTextFileFromAssets(context, "studentsPoWise.txt"), StudentListModel.class);
        List<StudentModel> studentModels = res.getModel();
        DAO.insertStudentbyListBasics(context, db, studentModels);
    }


    void interTeacherList() {
        TeacherListModel res = gson.fromJson(
                FileHelper.getTextFileFromAssets(context, "teachersPoWise.txt"), TeacherListModel.class);
        List<TeacherModel> teacherModels = res.getModel();
        DAO.insertTeachersbyListBasics(context, db, teacherModels);

    }


    void insertReportAdmin() {
      //  boolean succ = DAO.insertReportAdmin(context, db, DataPump.getReportModelList(), "schID");
       // Toast.makeText(context, String.valueOf(succ), Toast.LENGTH_SHORT).show();
       // Log.d(TAG, DataPump.getReportModelList().toString());
    }

}
