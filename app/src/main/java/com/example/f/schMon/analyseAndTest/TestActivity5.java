/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.view.evaluation.EvalQuesModel;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.sync_mir.SyncSynchronous;

import org.apache.commons.collections4.CollectionUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class TestActivity5 extends AppCompatActivity {
    private static final String TAG = TestActivity5.class.getName();
    private final Context context = TestActivity5.this;
    @BindView(R.id.checkboxYes)
    CheckBox yesChk;
    @BindView(R.id.checkboxNo)
    CheckBox noChk;
    @BindView(R.id.test5Text1)
    TextView textView1;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5);
        ButterKnife.bind(this);
        pb.setVisibility(View.GONE);


    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        textView1.setText(event.toString());
    }


    Thread netThread;
    long time = 0;
    Handler handler = new Handler();

    @OnClick(R.id.btn1)
    void onClickBtn1(View v) {
        pb.setVisibility(View.VISIBLE);
        time = System.currentTimeMillis();


        netThread = new Thread(new Runnable() {
            @Override
            public void run() {
                sync();
                time = (System.currentTimeMillis() - time);
                EventBus.getDefault().post(new MessageEvent("Sync Completed. Time: " + time / 1000 + " seconds"));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                    }
                });

            }
        });

        netThread.start();

    }

    SyncSynchronous syncSynchronous;

    void sync() {
        try {
            syncSynchronous = new SyncSynchronous(context);
            syncSynchronous.syncAll();
        } catch (Exception e) {
            Log.e(TAG, "exception", e);
        }
    }


    //====================== question test =======================================
    void test1() {
        StringBuilder sb = new StringBuilder();
        List<EvlModelin> quesList = DAO2.getActiveEvaluatioQues("");
        List<EvlModelin> evlModelins = DAO2.getLastEvlModelinAllSchool().get(0);
        List<EvlModelin> unanswerd = new ArrayList<>();

        unanswerd = test(quesList, evlModelins);


        sb.append("all ques\n\n" + quesList.toString());
        sb.append("\n\nans ques\n\n" + evlModelins.toString());
        sb.append("\n\nunans ques\n\n" + unanswerd.toString());


        textView1.setText(sb);
    }

    public List<EvlModelin> test(List<EvlModelin> allQues, List<EvlModelin> ansEvlModelins) {
        EvlModelin t;
        List<EvlModelin> answered = new ArrayList<>();
        List<EvlModelin> unanswerd = new ArrayList<>();
        int n = allQues.size();


        // answered = H.fromEvlModelinToEvalQuesModel(ansEvlModelins);
        unanswerd = (List<EvlModelin>) CollectionUtils.subtract(allQues, answered);


        return unanswerd;
    }


    public List<EvalQuesModel> test3(List<EvalQuesModel> allQues, List<EvlModelin> ansEvlModelins) {
        EvalQuesModel all;
        EvlModelin ans;
        List<EvalQuesModel> answered = new ArrayList<>();
        List<EvalQuesModel> unanswerd = new ArrayList<>();
        int m = ansEvlModelins.size(),
                n = allQues.size();

        //  for (int i = 0; i < m; i++) {//ans
        all = allQues.get(0);
        for (int j = 0; j < n; j++) {//all
            ans = ansEvlModelins.get(j);
            if (ans.getServerIDQues().equals(all.getServer_id()) && ans.getQues().equals(all.getQues())) {

            }
        }


        //   }


        return unanswerd;
    }


}
