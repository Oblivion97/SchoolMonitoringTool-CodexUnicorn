/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by Mir Ferdous on 10/10/2017.
 */
public class NotifAdapter extends ArrayAdapter<RepMdlin> {
    private final String TAG = NotifAdapter.class.getName();
    private Context cntx;
    private SQLiteDatabase updateDB;
    private School s;
    private List<NotifData> dataList;
    private List<RepMdlin> list = new ArrayList<>();
    private List<School> schools = new ArrayList<>();
    private String dateUpdate, dateRep;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    private RepMdlin repMdlin;


    public NotifAdapter(Context cntx, int textViewResourceId,
                        List<NotifData> dataList, List<RepMdlin> list) {
        super(cntx, textViewResourceId, list);
        this.list = list;
        // this.schools = schools;
        this.cntx = cntx;

        updateDB = Global.getDB_with_nativeWay_writable(Global.dbName);
        this.dataList = new ArrayList<>();
        this.dataList.addAll(dataList);
    }


    static class ViewHolder {
        @BindView(R.id.notification_date)
        TextView dateTV;
        @BindView(R.id.notification_title)
        TextView title;
        @BindView(R.id.notification_school_name)
        TextView schoolName;
        @BindView(R.id.notification_description)
        TextView description;
        @BindView(R.id.notification_school_grade)
        TextView grade;
        @BindView(R.id.checkboxYes)
        CheckBox yes;
        @BindView(R.id.checkboxNo)
        CheckBox no;

        @BindView(R.id.notification_target)
        TextView targetD;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }

    DatePicker datePicker;
    RepMdlin data;
    String id;
    ViewHolder h;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row_notif, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        H.setFont(cntx, new View[]{h.dateTV, h.title, h.schoolName, h.grade, h.description, h.targetD, h.yes, h.no});
        //================ set data in row ================================
        data = list.get(position);

        s = DAO.getInfoOfASchool(list.get(position).getSchID());


        h.dateTV.setText(H.dateFormat2(list.get(position).getDateReport()));
        h.title.setText("Problem: " + H.toCamelCase(list.get(position).getQuestion()));
        h.schoolName.setText(H.toCamelCase(s.getSchName()) + "(Code: " + s.getSchCode() + ")");
        h.grade.setText("Grade: " + s.getGrade() + "\n" + "Edu. Type: " + s.getEdu_type());
        h.description.setText("Problem Details: " + list.get(position).getDetails());

        if (list.get(position).getDateTarget() == null)
            list.get(position).setDateTarget("");
        h.targetD.setText("Target date to solve: " + H.dateFormat2(list.get(position).getDateTarget()));


        h.yes.setChecked(list.get(position).isYesSelected());
        h.no.setChecked(list.get(position).isNoSelected());

        h.yes.setTag(list.get(position));
        h.no.setTag(list.get(position));

        //================ yes btn ================================
        h.yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateDBYesChkClick(position, v, list.get(position));
            }
        });
        //============================== no btn =======================================================
        h.no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                data = (RepMdlin) cb.getTag();
                data.setNoSelected(cb.isChecked());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void updateDBYesChkClick(final int position, View v, final RepMdlin data) {

        CheckBox cb = (CheckBox) v;
        //data = (RepMdlin) cb.getTag();
        data.setYesSelected(cb.isChecked());
        //id = list.get(position).getSchID();

        //====== backup solved problem for notification history delete =========

        // backupProblemAfterNotifYesClick(data);

        //====== change data(problem solved) =========
       /* data.setNotify("0");
        data.setNotify_his("1");
        data.setN_update_date(df1.format(new Date(System.currentTimeMillis())));
        updateDB_on_NotifyYes(data);*/


        //=============== change data in notif_bckup table after yes click notification problem solve =========

        final Calendar cl = Calendar.getInstance();
        int yy = cl.get(Calendar.YEAR);
        final int mm = cl.get(Calendar.MONTH);
        int dd = cl.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(cntx, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                //====== backup solved problem for notification history delete =========

                backupProblemAfterNotifYesClick(data);

                //====== change data(problem solved) =========
                data.setNotify("0");
                data.setNotify_his("1");
                data.setN_update_date(df1.format(new Date(System.currentTimeMillis())));
                data.setDateSolve(H.df1DateFromYearMonthDay(y, m, d));
                updateDB_on_NotifyYes(data);

                Toasty.success(cntx, "This Problem Solved on " + data.getDateSolve(), Toast.LENGTH_SHORT).show();
                //remove row
                list.remove(position);
                notifyDataSetChanged();


            }
        }, yy, mm, dd);
        datePicker.show();
    }


    public static boolean updateDB_on_NotifyYes(RepMdlin r) {
        boolean f;
        List<RepMdlin> repMdlins = new ArrayList<>();
        repMdlins.add(r);
        f = DAO.insertReportAdmin(repMdlins);
        return f;
    }


    public static boolean backupProblemAfterNotifYesClick(RepMdlin repMdlin) {
        /** backing up problem no report for later revert**/
        boolean flag = true;
        List<RepMdlin> list = new ArrayList<>();
        list.add(repMdlin);
        String table = DB.notif_bckup.table;

        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String sql, schID, date, quesID, ques, quesTitle;
        RepMdlin r;
        int count;
        ContentValues cv;

        //try{


        int size = list.size();
        for (int i = 0; i < size; i++) {
            r = list.get(i);

            cv = new ContentValues();
            cv.put(DB.notif_bckup._ID, r.get_id());
            cv.put(DB.notif_bckup.sch_id, r.getSchID());
            cv.put(DB.notif_bckup.grade_id, r.getGradeID());
            cv.put(DB.notif_bckup.server_id_ques, r.getServerIDQues());
            cv.put(DB.notif_bckup.local_id_ques, r.getLocalIDQues());
            cv.put(DB.notif_bckup.questionTitle, r.getQuestionTitle());
            cv.put(DB.notif_bckup.question, r.getQuestion());
            cv.put(DB.notif_bckup.answer, r.getAnswer());
            cv.put(DB.notif_bckup.priority, r.getPriority());
            cv.put(DB.notif_bckup.details, r.getDetails());
            cv.put(DB.notif_bckup.serverQuestion, r.getServerQuestion());
            cv.put(DB.notif_bckup.answerWeight, r.getAnswerWeight());
            cv.put(DB.notif_bckup.date_report, r.getDateReport());
            cv.put(DB.notif_bckup.date_target, r.getDateTarget());
            cv.put(DB.notif_bckup.date_solve, r.getDateSolve());
            cv.put(DB.notif_bckup.time_lm, r.getTimeLm());
            cv.put(DB.notif_bckup.synced, r.getSynced());
            cv.put(DB.notif_bckup.notify, r.getNotify());
            cv.put(DB.notif_bckup.notify_his, r.getNotify_his());
            cv.put(DB.notif_bckup.n_update_date, r.getN_update_date());
            cv.put(DB.notif_bckup.server_id_answer, r.getServerIDAns());
            cv.put(DB.notif_bckup.local_id_answer, r.getLocalIDAns());

            String sel = DB.notif_bckup.sch_id + " = ? AND " + DB.notif_bckup.date_report + " = ? AND "
                    + DB.notif_bckup.server_id_ques + " = ? AND " + DB.notif_bckup.question + " = ? ";

            schID = list.get(0).getSchID();
            date = list.get(0).getDateReport();
            ques = r.getQuestion();
            quesID = r.getServerIDQues();
            String[] selArg = new String[]{schID, date, quesID, ques};

            count = db.update(table, cv, sel, selArg);


            if (count <= 0) {
                db.insert(table, null, cv);
            }



           /* } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }*/
        }
        return flag;
    }


}