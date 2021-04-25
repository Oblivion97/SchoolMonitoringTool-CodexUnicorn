/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.controller.webservice.WSManager;
import com.example.f.schMon.controller.webservice.WSResponseListener;
import com.example.f.schMon.model.oldAPI.UserModel;
import com.example.f.schMon.view.payments.paymdl.PayMdl;
import com.example.f.schMon.view.sync_mir.APInterface;
import com.example.f.schMon.view.sync_mir.Client;
import com.example.f.schMon.view.sync_mir.MdlSync.RepA;
import com.example.f.schMon.view.sync_mir.MdlSync.RepAList;
import com.example.f.schMon.view.sync_mir.MdlSync.Respnse;
import com.example.f.schMon.view.sync_mir.SyncH;
import com.example.f.schMon.view.sync_mir.SyncSynchronous;
import com.example.f.schMon.view.sync_mir.unused.StringConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.entity.StringEntity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TestActivity7 extends Activity {
    private static final String TAG = TestActivity7.class.getName();
    Context context = TestActivity7.this;
    boolean timeSame;
    Object object;
    UserModel userModel = null;
    APInterface api = Client.getAPInterface();
    String u = A.getU(), p = A.getP();
    SyncSynchronous syncSynchronous = new SyncSynchronous(context);
    Respnse respnse1, respnse2;
    String temp1, url;


    GsonBuilder gb = new GsonBuilder().registerTypeAdapter
            (String.class, new StringConverter());
    Gson gson = gb.create();


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @BindView(R.id.text1)
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test7);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
//=========================== start btn ====================================================================================================

    @OnClick(R.id.btn2)
    void onClickBTN2(View v) {
        //H.getAlertDialog(context, H.booleanFieldVaueTypeForAColumn(DB.report_admin.table, DB.report_admin.synced));
       /* temp1 = gson.toJson(SyncH.getReportAnsForSync(false));
        //  temp1 = gson.toJson(SyncH.getEvaluationAnsForSync(false));
        H.getAlertDialog(context, temp1);*/

       /*
       http://bepmis.brac.net/rest/apps/payment?uname=shopon_kumer&passw=brac123&
       instituteId=47272801-3021-inst-a623-00808530be7b
       &gradeId=47272800-2475-grad-89ce-06658dccda05
       &year=2017
       * */
        final String schID = "47272801-3021-inst-a623-00808530be7b",
                gradeID = "47272800-2475-grad-89ce-06658dccda05",
                year = "2017";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PayMdl payMdl = api.getPaymentData(A.getU(), A.getP(), schID, gradeID, year).execute().body();
                    Log.d(TAG, payMdl.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @OnClick(R.id.btn1)
    void onClickBTN1(View v) {
       /* showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //post();
                    //login();
                    postWithAsyncHttpClient();
                } catch (Exception e) {
                    Log.d(TAG, "exception", e);
                }
            }
        }).start();*/


       gsonTest();
    }

//=========================== end btn ==============================================================================================

    //=======================gson test==================================================================================================
    void gsonTest() {
        String s = "{\"module\":\"INFRASTRUCTURE_QUESTION_ANSWER\",\"request_type\":\"REQUEST_TYPE_GET_LATEST_REPORT\",\"success\":\"1\",\"message\":\"Successful\",\"total\":\"103\",\"additionalOne\":null,\"model\":[{\"id\":\"51169286-9824-Infr-b8b4-894f32fd6c0c\",\"instituteId\":\"48775026-5593-inst-993f-27ba037b41b8\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"26-11-2017\",\"answer\":1,\"priority\":3,\"details\":\"tt\",\"localId\":\"\",\"modifiedDate\":\"\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0337-Infr-91ea-145a6e0e9f58\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0337-Infr-9240-33855dc5080c\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072102\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0337-Infr-af3f-0e73aaf70997\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072101\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-8253-4c8431207b89\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072104\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-8f2d-8407a7c12cdc\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072103\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-9167-f3acf242b8ff\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072108\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-a09a-f13267c254b5\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072106\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-b204-52747826c3de\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072105\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-b8a5-f82f180982d4\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072107\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0338-Infr-bd5e-8a8b707abaa7\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072109\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-849f-a5606330a429\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072114\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-8683-2f2e660f1632\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072112\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\n" +
                "12-12 13:36:09.315 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: \":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-8981-2bc8622ebb62\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072116\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-949b-d4d599b67fa1\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072111\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-b5cd-8187f1fb8196\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072110\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-b89b-44089116ffac\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072115\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51178485-0339-Infr-b93f-4478c6949b0b\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072113\",\"submittedDate\":\"27-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"27-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6201-Infr-8803-46e385018986\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072102\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":1,\"details\":\"snzhHa\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6201-Infr-8f80-d504dbf4779b\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072101\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"zbzjsj\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"16-11-2017\",\"solveDate\":\"\"},{\"id\":\"51189310-6201-Infr-9136-ea024156f695\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"hdhdjzka\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"29-11-2017\",\"solveDate\":\"\"},{\"id\":\"51189310-6201-Infr-a3f0-65a22e911f74\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072103\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-846d-8a4ce56aac96\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072104\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-8f22-628846c309a8\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072110\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-a067-5a9e0c78349d\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072108\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-a816-70f53e992733\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072106\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"mod\n" +
                "12-12 13:36:09.315 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: ifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-adf6-b29bd500b1ed\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072111\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":3,\"details\":\"sbshsj\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"30-11-2017\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-b525-21477a800a04\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072112\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"sjsjsks\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-b733-85d714f8d890\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072107\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-ba5b-7cda07d2bc10\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072105\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6202-Infr-bdc0-5d6345f318d0\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072109\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6203-Infr-80c3-6c6323bfeb6a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072116\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6203-Infr-8f31-e42737e209e2\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072113\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":3,\"details\":\"zbzjzks\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6203-Infr-96f8-7f2df128048c\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072114\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189310-6203-Infr-bd6b-c68f66390740\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072115\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2088-Infr-a052-17687d1b3efc\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072101\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"zbzjsj\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"16-11-2017\",\"solveDate\":\"\"},{\"id\":\"51189392-2088-Infr-bb9c-a924516772b8\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"hdhdjzka\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"29-11-2017\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-85cd-e7527d463437\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072102\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":1,\"details\":\"snzhHa\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-8ecc-5a434c2f099e\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072105\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifie\n" +
                "12-12 13:36:09.316 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: dDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-958c-a0313e889b1d\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072110\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-a6e5-53dc8f853c91\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072108\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-b074-5622e1b35875\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072104\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-b16a-e1730043a029\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072103\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-b25d-68cc647dbf96\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072107\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-b592-fd185d806d6f\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072109\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2089-Infr-bdc2-c35c25b1671b\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072106\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-87a0-db77616a8ecc\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072111\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":3,\"details\":\"sbshsj\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"30-11-2017\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-8ba9-d99c7f787869\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072103\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-8c24-145d6fe2307d\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072116\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-8edf-e6ada07a4050\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072101\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-90b3-d93fe3bdd47e\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072113\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":3,\"details\":\"zbzjzks\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-96af-9fff58e48d41\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072112\",\"submittedDate\":\"29-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"sjsjsks\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDa\n" +
                "12-12 13:36:09.316 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: te\":\"\"},{\"id\":\"51189392-2090-Infr-9f81-9456317bdfeb\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072115\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-b046-0db83b6b2e7a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072114\",\"submittedDate\":\"29-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"29-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-b394-556bd541c3f0\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072102\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2090-Infr-baae-62bc4b4ea312\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-860a-1077f6dbd5e9\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072109\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-9116-be5121a4782e\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072105\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-93dd-76bf5b639dd2\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072108\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-9429-3f6931a5af34\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072104\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-9671-9878078de88f\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072111\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-9895-8e8835658cfd\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072106\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-a506-b0fba1caf820\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072107\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-a948-bb5cb5822791\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072112\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2091-Infr-bd90-37b94b5b3b2e\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072110\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2092-Infr-958c-9dfddc632542\",\"instituteId\":\"472728\n" +
                "12-12 13:36:09.316 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: 00-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072115\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2092-Infr-b065-04ad4cc33790\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072113\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2092-Infr-b344-5492ac9a6753\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072114\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51189392-2092-Infr-b63d-a9fb1aea0aff\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072116\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-80f4-983388e02a43\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"30-11-2017\",\"answer\":0,\"priority\":3,\"details\":\"xbxjzk\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"28-11-2017\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-8795-636642dcf809\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072102\",\"submittedDate\":\"30-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"djsjsk\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-8c53-fe1670763540\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072103\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-8d07-4a4e3461ef2a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072105\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-a48a-8d381af659e2\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072106\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-a6ad-af92f1d631f5\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072101\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5364-Infr-bdbd-c878d88797b5\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072104\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-83e3-eecc1306051f\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072112\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-8a89-8c26bc3cd9df\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072107\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-8fc3-45cc48b33354\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuest\n" +
                "12-12 13:36:09.316 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: ionId\":\"50995403-2618-udv-954a-7c52b7072115\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-93d4-a4b432d51ba2\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072113\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-942a-6f4cc3cde9c2\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072111\",\"submittedDate\":\"30-11-2017\",\"answer\":0,\"priority\":3,\"details\":\"dhsjaj sjaja\\n\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"29-11-2017\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-984e-e899680d9a6b\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072116\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-a715-665f75ff9767\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072114\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-a8f0-d59e2acb1f1b\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072110\",\"submittedDate\":\"30-11-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-abfd-737be0ca8d3f\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072109\",\"submittedDate\":\"30-11-2017\",\"answer\":0,\"priority\":2,\"details\":\"dndjaak ahajaja \",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51190176-5365-Infr-b32d-ec0b2d70d8c4\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072108\",\"submittedDate\":\"30-11-2017\",\"answer\":0,\"priority\":1,\"details\":\"dbsnajak sjsjakak\",\"localId\":\"\",\"modifiedDate\":\"30-11-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8669-Infr-830f-a5cf9a451af5\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072103\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8669-Infr-a230-747697b85f3a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072102\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8669-Infr-a295-a02a87e21eef\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072101\",\"submittedDate\":\"02-12-2017\",\"answer\":0,\"priority\":1,\"details\":\" g gb\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8669-Infr-b632-87cf997eff15\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072100\",\"submittedDate\":\"02-12-2017\",\"answer\":0,\"priority\":2,\"details\":\"ththhh\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-94d0-f9598861ef5a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072107\",\"submittedDate\":\"02-12-2017\",\"answer\":0,\"priority\":3,\"details\":\"fbgngnyn\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-9e54-3c5291b6485a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQue\n" +
                "12-12 13:36:09.316 343-756/pw.codexunicorn.schoolmonitoringtool D/OkHttp: stionId\":\"50995403-2618-udv-954a-7c52b7072110\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-a435-ae2ff5b2307c\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072104\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-a5c5-bc8bfa005f7a\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072108\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-aa44-2fe8202c5faa\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072109\",\"submittedDate\":\"02-12-2017\",\"answer\":0,\"priority\":3,\"details\":\"v h hn\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-aac8-24cdec7242f3\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072106\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-ae63-d361e5b45701\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072112\",\"submittedDate\":\"02-12-2017\",\"answer\":0,\"priority\":3,\"details\":\"dn t rj.\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-b087-fb56c715c1e9\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072111\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8670-Infr-b46b-730915ea4b23\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072105\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8671-Infr-81f6-40522d37bc3b\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072116\",\"submittedDate\":\"02-12-2017\",\"answer\":0,\"priority\":3,\"details\":\"dndhndhrhn\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8671-Infr-96f3-595963ddd907\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072114\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8671-Infr-a154-2919db1587e8\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072113\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"},{\"id\":\"51220090-8671-Infr-bab4-a60bc477c500\",\"instituteId\":\"47272800-1957-inst-8631-119f6fad8b4c\",\"infrastructureQuestionId\":\"50995403-2618-udv-954a-7c52b7072115\",\"submittedDate\":\"02-12-2017\",\"answer\":1,\"priority\":0,\"details\":\"\",\"localId\":\"\",\"modifiedDate\":\"02-12-2017\",\"targetDate\":\"\",\"solveDate\":\"\"}]}";


        Gson gson = new Gson();
        RepAList repAList=gson.fromJson(s, RepAList.class);
        repAList.getModel();
        Log.d(TAG, repAList.getModel().toString());
    }
    //=========================================================================================================================


//============================== start async http clicnt =================================================================================================

    void postWithAsyncHttpClient() throws UnsupportedEncodingException {
        List<RepA> repAns = SyncH.getReportAnsForSync(false);
        repAns = SyncH.repNullToBlankString(repAns);
        List<RepA> newRep = new ArrayList<>();
        newRep.add(repAns.get(0));
        // newRep.add(repAns.get(1));

        String json = gson.toJson(newRep);
        Log.d(TAG, json);
        url = "http://bepmis.brac.net/rest/apps/infrastructure-report/save?uname=kholilur&passw=123456";
        StringEntity entity = new StringEntity(json);
        wsManager.post(url, entity);
    }


    WSManager wsManager = new WSManager(context, new WSResponseListener() {
        @Override
        public void success(String response, String url) {
            Log.d(TAG, response);
        }

        @Override
        public void failure(int statusCode, Throwable error, String content) {

        }
    });
//=========================== end async http clicnt ====================================================================================================

    void post() throws Exception {
        /*syncSynchronous.dwnldRepQues();
        syncSynchronous.dwnldEvlQues();*/
        respnse1 = syncSynchronous.postEvlAns(true);
        respnse2 = syncSynchronous.postRepAns(true);
        EventBus.getDefault().post(new MessageEvent(respnse1, respnse2));
    }


    void login() throws Exception {
        userModel = new SyncSynchronous(context).login("kholilur", "123456");
        Log.d(TAG, userModel.toString());
    }

    void pastReps() throws IOException {

    }


    //===========================   ====================================================================================================

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent e) {
        H.getAlertDialog(context, e.object1.toString() +
                "\n" + e.object2.toString());
    }

    ProgressDialog mProgressDialog;

    void showProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Syncing........");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.show();
    }
}
