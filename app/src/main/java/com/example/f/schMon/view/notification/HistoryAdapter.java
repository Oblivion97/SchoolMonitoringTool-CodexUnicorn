/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

/**
 * Created by Mir Ferdous on 10/10/2017.
 */

public class HistoryAdapter extends ArrayAdapter<RepMdlin> {
    private static final String TAG = HistoryAdapter.class.getName();
    private Context context;
    private List<RepMdlin> list;

    public HistoryAdapter(Context context, List<RepMdlin> list) {
        super(context, -1, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        @BindView(R.id.historyDate)
        TextView dateTV;
        @BindView(R.id.notification_title)
        TextView title;
        @BindView(R.id.notification_school_name)
        TextView schoolName;
        @BindView(R.id.notification_description)
        TextView description;
        @BindView(R.id.notification_school_grade)
        TextView eduType;
        @BindView(R.id.historyDelete)
        ImageButton delete;
        @BindView(R.id.notification_target)
        TextView targetDate;
        @BindView(R.id.notification_solve)
        TextView solveDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    ViewHolder h;
    RepMdlin data;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //================= view to view h assignment =====================
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row_notif_history, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
            //h.delete.setTag(position);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        H.setFont(context, new View[]{h.dateTV, h.title, h.schoolName, h.eduType, h.description, h.targetDate, h.solveDate});
        //================= set data =====================
        data = list.get(position);
        s = DAO.getInfoOfASchool(data.getSchID());

        h.dateTV.setText("Report Date: " + H.dateFormat2(data.getDateReport()));
        h.title.setText("Problem: " + H.toCamelCase(data.getQuestion()));
        h.schoolName.setText(H.toCamelCase(s.getSchName()) + "(Code: " + s.getSchCode() + ")");
        h.eduType.setText("Grade: " + s.getGrade() + "\n" + "Edu. Type: " + s.getEdu_type());
        h.description.setText("Problem Details: " + data.getDetails());

        String targetDate = data.getDateTarget();
        String solveDate = data.getDateSolve();


        if (targetDate == null)
            targetDate = "";
        if (solveDate == null)
            solveDate = "";

        h.targetDate.setText("Target Date: " + H.dateFormat2(targetDate));
        h.solveDate.setText("Solved Date: " + H.dateFormat2(solveDate));

        //================ on click =========================
        h.delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                data = list.get(position);
                // H.getAlertDialog(context, String.valueOf(position) + " is index\n" + list.get(position).toString());


                deleteNotifHistory(list.get(position).getSchID(),
                        list.get(position).getDateReport(),
                        list.get(position).getServerIDQues(),
                        list.get(position).getQuestion());

                /* updateDB(id);*/

                Toasty.success(context, "This Problem Still Exists", Toast.LENGTH_SHORT).show();
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    School s;

    public void updateDB(String id) {
        SQLiteDatabase updateDB = Global.getDB_with_nativeWay_writable(Global.dbName);
        updateDB = Global.getDB_with_nativeWay_writable(Global.dbName);
        ContentValues cv = new ContentValues();
        cv.put("answer", "0"); //These Fields should be your String values of actual column names
        cv.put("notify_flag", "0");
        cv.put("n_date_update", "0");
        int i = updateDB.update("report_admin", cv, "_id =" + id, null);
        Log.d("updateDB_on_NotifyYes", "status " + i);
    }


    public boolean deleteNotifHistory(String schID, String date,
                                      String quesID, String ques) {
        boolean f = true;
        //get not solved reps from backup table
        RepMdlin r = getRepFromNotifyHistoryBckupTable(schID, date, quesID, ques);

        if (r == null) {
            Log.d(TAG, "null");
            // H.getAlertDialog(context, "null");
        } else {
            List<RepMdlin> repMdlins = new ArrayList<>();
            repMdlins.add(r);

            //  insert not solved item in main table
            f = DAO.insertReportAdmin(repMdlins);
        }
        return f;
    }

    @Nullable
    public static RepMdlin getRepFromNotifyHistoryBckupTable(String schID, String date,
                                                             String quesID, String ques) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String table = DB.notif_bckup.table;
        RepMdlin r = null;
        Cursor c = null;

        String sel = DB.notif_bckup.sch_id + " = ? AND " + DB.notif_bckup.date_report + " = ? AND " +
                DB.notif_bckup.server_id_ques + " = ? AND " + DB.notif_bckup.question + " = ? ";

        String[] selArg = new String[]{schID, date, quesID, ques};
        c = db.query(table, null, sel, selArg, null, null, null);

        if (c != null) {
            if (c.moveToFirst()) {
                r = new RepMdlin();
                r.set_id(c.getString(c.getColumnIndex(DB.notif_bckup._ID)));
                r.setSchID(c.getString(c.getColumnIndex(DB.notif_bckup.sch_id)));
                r.setServerIDQues(c.getString(c.getColumnIndex(DB.notif_bckup.server_id_ques)));
                r.setQuesTitle(c.getString(c.getColumnIndex(DB.notif_bckup.questionTitle)));
                r.setQues(c.getString(c.getColumnIndex(DB.notif_bckup.question)));
                r.setAns(c.getString(c.getColumnIndex(DB.notif_bckup.answer)));
                r.setAnsWeight(c.getString(c.getColumnIndex(DB.notif_bckup.answerWeight)));
                r.setDetails(c.getString(c.getColumnIndex(DB.notif_bckup.details)));
                r.setPriority(c.getString(c.getColumnIndex(DB.notif_bckup.priority)));
                r.setServerQuestion(c.getString(c.getColumnIndex(DB.notif_bckup.serverQuestion)));
                r.setDateReport(c.getString(c.getColumnIndex(DB.notif_bckup.date_report)));
                r.setDateTarget(c.getString(c.getColumnIndex(DB.notif_bckup.date_target)));
                r.setDateSolve(c.getString(c.getColumnIndex(DB.notif_bckup.date_solve)));
                r.setSynced(c.getString(c.getColumnIndex(DB.notif_bckup.synced)));
                r.setTimeLm(c.getString(c.getColumnIndex(DB.notif_bckup.time_lm)));
                r.setNotify_his(c.getString(c.getColumnIndex(DB.notif_bckup.notify)));
                r.setNotify_his(c.getString(c.getColumnIndex(DB.notif_bckup.notify_his)));
                r.setN_update_date(c.getString(c.getColumnIndex(DB.notif_bckup.n_update_date)));
                r.setServerIDAns(c.getString(c.getColumnIndex(DB.notif_bckup.server_id_answer)));
                r.setLocalIDAns(c.getString(c.getColumnIndex(DB.notif_bckup.local_id_answer)));
                r.setLocalIDQues(c.getString(c.getColumnIndex(DB.notif_bckup.local_id_ques)));
            }
        }
        if (c != null)
            c.close();

        return r;
    }
}