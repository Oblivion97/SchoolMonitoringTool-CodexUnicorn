/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.f.schMon.view.common.ProgressDialogCus;
import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.oldAPI.TeacherModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class TestActivity4 extends AppCompatActivity {
    private final Gson mGson = new Gson();
    public int PAGE_NO = 0;
    public int total_downloaded = 0;
    public int total_beneficiary_in_server = 0;
    public ProgressDialog pd = null;
    String TAG = getClass().getName();
    Context context = TestActivity4.this;
    TextView textView1, textView2;
    Button button1, button2;
    int x = 1;
    SQLiteDatabase db;
    Handler handler;
    ProgressDialogCus progressDialogCus;
    int progressStatus = 0;
    //___________________________________________
    boolean flag;
    private List<TeacherModel> teacherList = new ArrayList<>();

    @BindView(R.id.smoothPrg)
    SmoothProgressBar smoothProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test4);
        textView1 = findViewById(R.id.textView1);
        textView1.setMovementMethod(new ScrollingMovementMethod());
        textView2 = findViewById(R.id.textView2);
        textView2.setMovementMethod(new ScrollingMovementMethod());
        button1 = findViewById(R.id.btnCall1);
        button2 = findViewById(R.id.btnCall2);

        //gif test
        try {
            GifDrawable gifFromAssets = null;
            gifFromAssets = new GifDrawable(getAssets(), "load.gif");
            GifImageView givImageView = findViewById(R.id.gifLoad);
            givImageView.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClickTestAPICALL(View view) throws InterruptedException {
       /* List<String> temp = DAO.all_values_from_a_Column(Global.getDB_with_nativeWay_writable(Global.dbName), "students", "std_id");
        textView1.setText("Total Count : " + temp.size() + "\n\n" + temp.toString());*/

       /* textView2.setVisibility(View.GONE);
        String[] temp = Sync.callLoginAPI(context);
        if (temp[0] != null && temp[1] != null) {
            textView1.setText(temp[0]);
            textView1.setText(temp[1]);*/


        handler = new Handler();

        progressDialogCus = new ProgressDialogCus(context);

        progressDialogCus.setMessage("Schools Basic Info Loading From Server");
        progressDialogCus.setMax(100000);


        progressDialogCus.show();

        for (int i = 0; i < 100000; i++) {
            //Thread.sleep(100);
            progressDialogCus.setProgress(i);
        }
        // progressDialogCus.dismiss();


        Thread thread = new Thread(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialogCus.dismiss();
                            textView1.setText("dsfadfffffffffffff");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //thread.start();


    }

    public void onClickClear(View view) {
        textView1.setText(null);
        textView2.setText(null);
    }

    private boolean progessbarTaskOnBtnClick(final String newUserName) {
        //this function will delete all offline user data if new user login
        SharedPreferences sharedpreferences = this.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        final String oldUserName = sharedpreferences.getString("userName", "");
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                pd = new ProgressDialog(context);
                pd.setMessage("Wait");
                pd.setIndeterminate(true);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(true);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }

            @Override
            protected Boolean doInBackground(String[] params) {
                //newUserName="dfsdf";
                try {
                    if (!newUserName.equals(oldUserName) && !oldUserName.equals("") && !newUserName.equals("")) {
                        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
                        DAO.deleteDataFromTable(db, DB.schools.table);
                        DAO.deleteDataFromTable(db, DB.students.table);
                        DAO.deleteDataFromTable(db, DB.teachers.table);
                        DAO.deleteDataFromTable(db, DB.report_admin.table);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    flag = false;
                }
                return flag;
            }

            @Override
            protected void onPostExecute(Boolean flag) {
                flag = flag;
                pd.dismiss();
                //finish();// destroys activity
            }
        }.execute();

        return flag;
    }

}
