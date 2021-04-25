/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class AddQuesActivity extends AppCompatActivity {
    private final Context context = AddQuesActivity.this;
    private final String TAG = AddQuesActivity.class.getName();
    private SQLiteDatabase db;
    private String timeLm, grade, openMode, schID, gradeID, date, attendance;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    EvalQuesModel evalQuesModel;
    EvlModelin data;
    Intent intent;
    @BindView(R.id.QuestionText)
    EditText quesET;
    @BindView(R.id.AnswerText)
    EditText ansET;
    @BindView(R.id.saveAns)
    Button savaBtn;


//___________________Start option menu______________________________________

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);//Menu Resource, Menu
        return true;
    }

    /**
     * hiding irrelevant option menu for this Activity
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            menu.findItem(R.id.home_menu).setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //___________________End option menu______________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ques);
        ButterKnife.bind(this);
        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        intent = getIntent();
        openMode = intent.getStringExtra(Global.openMode);
        schID = intent.getStringExtra("schID");//schoolSync id
        gradeID = intent.getStringExtra("gradeID");
        date = intent.getStringExtra("date");
        attendance = intent.getStringExtra("attendance");
    }

    public void onClickSaveAns(View view) {
        String ques, ans;
        ques = quesET.getText().toString();
        ans = ansET.getText().toString();
        timeLm = df1.format(new Date(System.currentTimeMillis()));
        grade = DAO.getInfoOfASchool(schID).grade;

        if (!ques.equals("") && !ans.equals("")) {
            evalQuesModel = new EvalQuesModel(
                    null,
                    H.generateUniqueID("userques"),//localid
                    gradeID,
                    ques,
                    ans,
                    "true",
                    "1",
                    timeLm,
                    "1",
                    grade
            );

            DAO2.insertNewQuestion(evalQuesModel);

            Toasty.success(context, "New Question Added", Toast.LENGTH_SHORT, true).show();

            returnResult_to_StartingActivity();

            //   startEvalActivity_QuestionList();
        } else {
            new AlertDialog.Builder(context).setIcon(R.drawable.ic_warning)
                    .setTitle("Please Insert Question and Answer first...")
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).create().show();
        }
    }

    public EvlModelin evalQuesModelToEvlModelin(EvalQuesModel evalQuesModel) {
        EvlModelin eval = new EvlModelin();
        eval.setServerIDQues(evalQuesModel.getServer_id());
        eval.setLocalIDQues(evalQuesModel.getLocal_id());//user generated local id
        eval.setQues(evalQuesModel.getQues());
        eval.setAnswer(evalQuesModel.getAnswer());
        eval.setActive(evalQuesModel.getActive());
        eval.setSynced(evalQuesModel.getSynced());
        eval.setTime_lm(evalQuesModel.getTimeLm());
        eval.setServer_ques(evalQuesModel.getServerQuestion());
        eval.setGrade(evalQuesModel.getGrade());
        eval.setGradeID(evalQuesModel.getGradeID());
        return eval;
    }

    private void returnResult_to_StartingActivity() {
        this.data = evalQuesModelToEvlModelin(evalQuesModel);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Global.openMode, Global.RunMode);
        returnIntent.putExtra("schID", this.schID);
        returnIntent.putExtra("gradeID", this.gradeID);
        returnIntent.putExtra("date", this.date);
        returnIntent.putExtra("attendance", this.attendance);
        returnIntent.putExtra("data", this.data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }


    //============================== unused ===========================================================================================

    void startEvalActivity_QuestionList() {
        Intent intent = new Intent(context, EvlActivity.class);
        intent.putExtra(Global.openMode, Global.RunMode);
        intent.putExtra("schID", schID);
        intent.putExtra("date", this.date);
        intent.putExtra("attendance", attendance);
        startActivity(intent);
        overridePendingTransition(R.anim.lefttoright, 0);
        finish();
    }

}
