/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.controller.webservice.WSManager;
import com.example.f.schMon.controller.webservice.WSResponseListener;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.model.oldAPI.TeacherListModel;
import com.example.f.schMon.model.oldAPI.TeacherModel;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.report_environment.RepMdlin;
import com.example.f.schMon.view.sync_mir.API;
import com.google.gson.Gson;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class TestActivity2 extends Activity implements WSResponseListener {
    private final Gson mGson = new Gson();
    public int PAGE_NO = 0;
    public int total_downloaded = 0;
    public int total_beneficiary_in_server = 0;
    public ProgressDialog progressDialog = null;
    String TAG = getClass().getName();
    Context context = TestActivity2.this;
    Button syncSchBtn, syncStdBtn, syncTechBtn, cleanBtn;
    String jsonRes = "";
    String[] asyncTaskParamUrl;
    int x = 1;
    SQLiteDatabase db;
    WSManager wsManager = new WSManager(context, this);
    private List<TeacherModel> teacherList = new ArrayList<>();

    @BindView(R.id.testTV1)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);


        //_________temp btn label______________________

        db = Global.getDB_with_nativeWay_writable(Global.dbName);


        pb.setVisibility(View.GONE);

    }

    List<String> dates;
    List<String> schs;
    String s;

    @OnClick(R.id.testBtn)
    public void onClickTestBtn(View v) {
        int i = 0;
        //  s = DAO.getRepAdminBasedOnSync(db, Global.unsynced).toString();

      /*  dates = H.getUniqueDateFromATable( DB.acad_perform.table, DB.acad_perform.date_report);
        schs = DAO.all_values_from_a_Column(db, DB.schools.table, DB.schools.schID);


        s = DAO.getLast5RepPerfBySchID( schs.get(i)).toString();

        s = DAO.getRepPerfBySchIDdateWise(db, schs.get(i), dates.get(i)).toString();


        pb.setVisibility(View.VISIBLE);
        //pb.progressiveStart();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dates = DataPump.getListOfDates();

                s = H.lastDates(dates, 5).toString();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // textView.setText(dates + "\n\n\n" + s);
                textView.setText(DAO.getLast5RepPerfBySchID( schs.get(0)).toString());
                pb.progressiveStop();
                pb.setVisibility(View.GONE);
            }
        };
*/
        //  s();
        // lastRep();

        //textView.setText(DAO.getAllInfoOfAllSchool(context, db).toString());
        //  textView.setText(DAO.getQuestionsRepAdminCheckList().toString());
        //  textView.setText(DAO.getLastRepModelAllSchool().toString());


        /*String id;
        List<String> t = DAO2.all_unique_values_from_a_Column(DB.report_admin.table, DB.report_admin.sch_id);
        id = t.get(0);

        List<String> temp = DAO2.allUniqueDateForASchool(id, DB.report_admin.table,
                DB.report_admin.sch_id, DB.report_admin.date_report);


        String last = DAO2.lastDateForASchool(id, DB.report_admin.table,
                DB.report_admin.sch_id, DB.report_admin.date_report);*/
        //   textView.setText(last);
        //textView.setText(DAO2.allUniqueDateForASchool().toString());

        /*textView.setText(DAO2.getLastRepModelinOfAllSchool().size()+"\n\n"+
                DAO2.getLastRepModelinOfAllSchool().toString());
*/


      /*  textView.setText(DAO.getActiveEvaluatioQues("").size() + "\n\n" +
                DAO.getActiveEvaluatioQues("").toString());
*/

//-----------------------------------

     /*   String schid, lastDate = null;
        List<String> schIds = DAO2.all_unique_values_from_a_Column(DB.report_acad.table, DB.report_acad.schID);
        int n = schIds.size();
        for (int j = 0; j < n; j++) {
            schid = schIds.get(j);
            lastDate = DAO2.lastDateForASchool(schid, DB.report_acad.table,
                    DB.report_acad.schID, DB.report_acad.date_report);
        }


        textView.setText(lastDate);


        List<List<EvlModelin>> listListevl = DAO2.getLastEvlModelinAllSchool();
        textView.setText(listListevl.size() + "\n\n" + listListevl.toString());
*/

        //----------------all problem notify----------------

      /*  textView.setText(DAO2.getAllExistingProblemsFromEnvReport().size()+"\n\n"+
                DAO2.getAllExistingProblemsFromEnvReport().toString());*/

/*
        List<String> datesAll = H.getUniqueDateFromATable(DB.admin_perform.table, DB.admin_perform.date_report);


        dates = H.lastDates(datesAll, 3);

        textView.setText(datesAll.toString() + "\n\n\n" + dates.toString());*/

        User userCreden = new User("kholilur", "123456");
        Gson gson = new Gson();
        postJson = gson.toJson(userCreden);
        StringEntity entity = null;
        try {
            entity = new StringEntity(gson.toJson(userCreden));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        url = "http://bepmis.brac.net/rest/apps/login";
        url = "http://bepmis.brac.net/rest/auth/login";
        wsManager2.post(API.getLogin(), entity);

        // textView.setText(Global.getUserCredentialFromSharePreference(context).toString());

       /* evl();
        textView.setText( list.toString());*/

    }

    String postJson;
    WSManager wsManager2 = new WSManager(context, new WSResponseListener() {
        @Override
        public void success(String response, String url) {
            t = response;
            textView.setText(t);
            Log.d(TAG, t);
        }

        @Override
        public void failure(int statusCode, Throwable error, String content) {
            textView.setText(statusCode + content + "\nfailed\n" + postJson);
            Log.d(TAG, postJson);
        }
    });


    List<EvlModelin> list = new ArrayList<>();
    List<EvlModelin> allQues = new ArrayList<>();//all questions from question list(no answer)
    List<EvlModelin> ansQuesList = new ArrayList<>();//answered questions in previous(this) evaluation
    List<EvlModelin> unAnsQuesList = new ArrayList<>();//unanswered questions in previous(this) evaluation

    private void evl() {
        String schID = "47272763-1662-user-9e16-022329f50fb7", date = "05-10-2017";


        allQues = DAO2.getActiveEvaluatioQues(null);
        ansQuesList = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
        unAnsQuesList = H.quesNotAnsweredPreviousEvaluation(allQues, ansQuesList);

        list.clear();
        list.addAll(ansQuesList);
        list.addAll(unAnsQuesList);
    }


    WSManager wsManager1;

    //==========================================================================================

    //==========================================================================================

    String t, url;


    void lastRep() {

        StringBuffer sb = new StringBuffer();
        List<List<RepMdlin>> listList = new ArrayList<>();//list of repModelinList of all schools

        listList = DAO.getLastRepModelAllSchool();

        sb.append(listList.toString());
        textView.setText("total schoolSync reports: " + listList.size() + "\n\n\n" + sb);
    }

    void s() {
        String x;
        List<String> tempList = DAO.getAllSchIDsFromLastPerformRep();
        for (int i = 0; i < tempList.size(); i++) {
            s = DAO.getLastRepDate(tempList.get(i));
        }
        textView.setText(s);
    }


    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;

    @OnClick(R.id.btnClear)
    public void onClickClearBtn(View v) {
        textView.setText(null);
    }


    //_________________Start of teacherSync api_______________________________


    public void onClickTestAPICALL(View view) {
        callServer(PAGE_NO, 10);
    }

    /**
     * response
     */
    @Override
    public void success(String response, String url) {
        try {
            TeacherListModel responseModel = mGson.fromJson(response, TeacherListModel.class);
            //System.out.println(responseModel.getSuccess());

            if (responseModel != null && responseModel.getSuccess().equalsIgnoreCase("1") && responseModel.getModel().size() > 0) {
                teacherList.addAll(responseModel.getModel());
                PAGE_NO += 1;
                total_downloaded += responseModel.getModel().size();
                System.out.println("page no: " + PAGE_NO);
                callServer(PAGE_NO, 10);
            } else {
                closeProgressDialog();
                String messages = "";
                for (TeacherModel teacherModel : teacherList) {
                    //messages += (x + ". " + teacherModel.getUsername() + ": " + teacherModel.getId() + "\n");
                    messages += (x + ". " + teacherModel.toString() + "\n");
                    x++;
                }
                textView.setText(messages);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void callServer(int pageNo, int pageSize) {
        if (PAGE_NO == 0) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Teacher loading from server");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(total_beneficiary_in_server);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.setMax(total_beneficiary_in_server);
            progressDialog.setProgress(total_downloaded);
        }
        wsManager.get("http://bepmis.brac.net/rest/teacherSync/listAllTeacher?max=" + pageSize + "&start=" + pageNo);
    }


    @Override
    public void failure(int statusCode, Throwable error, String content) {
        Toast.makeText(context, "Internet or Server Connection Error", Toast.LENGTH_SHORT).show();
    }


    //__________________End of teacherSync api__________________________________________


}


