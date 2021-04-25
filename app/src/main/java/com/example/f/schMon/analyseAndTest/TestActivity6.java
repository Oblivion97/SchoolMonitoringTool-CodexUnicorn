/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.webservice.WSManager;
import com.example.f.schMon.controller.webservice.WSResponseListener;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.model.newAPI.SchMdl;
import com.example.f.schMon.model.newAPI.SchMdlList;
import com.example.f.schMon.model.newAPI.StdMdl;
import com.example.f.schMon.model.newAPI.StdMdlList;
import com.example.f.schMon.model.newAPI.TchrMdl;
import com.example.f.schMon.model.newAPI.TehMdlList;
import com.example.f.schMon.view.sync_mir.API;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class TestActivity6 extends AppCompatActivity {
    String TAG = TestActivity6.class.getName();
    Context context = TestActivity6.this;
    @BindView(R.id.testAPIBtn)
    Button testBtn;
    @BindView(R.id.testBtn)
    Button test;
    @BindView(R.id.btnClear)
    Button clenBtn;
    @BindView(R.id.testTV1)
    TextView tv1;

    String s;

    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;

    User u;
    SchMdlList schMdlList;
    TehMdlList tehMdlList;
    StdMdlList stdMdlList;
    List<SchMdl> schList = new ArrayList<>();
    List<TchrMdl> tehList = new ArrayList<>();
    List<StdMdl> stdList = new ArrayList<>();
    Gson g = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test6);
        ButterKnife.bind(this);

        test.setVisibility(View.GONE);


        u = Global.getUserCredentialFromSharePreference(context);
    }

    @OnClick(R.id.btnClear)
    public void onClickbtnClear(View v) {
        tv1.setText("");
    }


    @OnClick(R.id.testAPIBtn)
    public void onClicktestAPIBtn(View v) {
        pb.setVisibility(View.VISIBLE);

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {

                callAll();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                tv1.setText(stdList.toString());
                pb.setVisibility(View.GONE);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    void callAll() {
        callServerSchList(null);
        callServerStdList(null);
        callServerTehList(null);
    }


    //======================================================================================
    String maxSch;

    public void callServerSchList(String maxSch) {
        w1.get(API.getSchList(maxSch, u.getUsername(), u.getPassword()));
    }


    WSManager w1 = new WSManager(context, new WSResponseListener() {
        @Override
        public void success(String response, String url) {
            schMdlList = g.fromJson(response, SchMdlList.class);
            if (schMdlList.getSuccess().equals("1")) {
                maxSch = schMdlList.getTotal();
                schList = schMdlList.getModel();
                callServerSchList(maxSch);
            }

        }

        @Override
        public void failure(int statusCode, Throwable error, String content) {

        }
    });

    //======================================================================================
    String maxStd;

    public void callServerStdList(String maxStd) {
        w1.get(API.getStdList(maxStd, u.getUsername(), u.getPassword()));
    }

    WSManager w2 = new WSManager(context, new WSResponseListener() {
        @Override
        public void success(String response, String url) {
            stdMdlList = g.fromJson(response, StdMdlList.class);
            if (stdMdlList.getSuccess().equals("1")) {
                maxStd = stdMdlList.getTotal();
                stdList = stdMdlList.getModel();
                callServerStdList(maxStd);
            }
        }

        @Override
        public void failure(int statusCode, Throwable error, String content) {

        }
    });

    //=====================================================================================
    String maxTeh;

    public void callServerTehList(String maxTeh) {
        w3.get(API.getTechList(maxTeh, u.getUsername(), u.getPassword()));
    }

    WSManager w3 = new WSManager(context, new WSResponseListener() {
        @Override
        public void success(String response, String url) {
            tehMdlList = g.fromJson(response, TehMdlList.class);
            if (tehMdlList.getSuccess().equals("1")) {
                maxTeh = tehMdlList.getTotal();
                tehList = tehMdlList.getModel();
                callServerTehList(maxTeh);
            }
        }

        @Override
        public void failure(int statusCode, Throwable error, String content) {

        }
    });

}
