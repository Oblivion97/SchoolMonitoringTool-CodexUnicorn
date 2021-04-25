/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.log_edit.logRep;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.report_environment.RepActivity;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LogRepAdapter extends ArrayAdapter<List<RepMdlin>> {
    private final String TAG = LogRepAdapter.class.getName();
    private final Context context;
    private List<List<RepMdlin>> listList = new ArrayList<>();
    List<School> schoolList = new ArrayList<>();
    List<RepMdlin> tRepMdlins = new ArrayList<>();
    String schID;
    School school;
    String date;
    int m, n;
    List<RepMdlin> oneSchRep = new ArrayList<>();
    boolean synced = true;

    List<Integer> syncImgList;

    public LogRepAdapter(Context context, List<List<RepMdlin>> listList, List<School> schoolList,
                         List<Integer> syncImgList) {
        super(context, -1, listList);
        this.context = context;
        this.listList = listList;
        this.schoolList = schoolList;
        this.syncImgList = syncImgList;
    }

    static class ViewHolder {
        @BindView(R.id.logName)
        TextView schTV;
        @BindView(R.id.logEduType)
        TextView eduTV;
        @BindView(R.id.logDate)
        TextView dateTV;
        @BindView(R.id.logEditImg)
        ImageView editIV;
        @BindView(R.id.logSyncImg)
        ImageView syncIV;
        @BindView(R.id.logRepCard)
        CardView cardVw;

        ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder h;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_log_rep, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }
        //========== set data ==============

        schID = listList.get(i).get(0).getSchID();
        school = H.getSchoolByIDFromList(schoolList, schID);

        if (school != null) {
            String schName = school.getSchName();
            String eduType = school.getEdu_type();
            date = listList.get(i).get(0).getDateReport();

            h.schTV.setText(schName + " (Code: " + school.getSchCode() + ")");
            h.dateTV.setText("Reported Date: " + date);
            h.eduTV.setText("E.Type: " + eduType);
            h.syncIV.setImageResource(syncImgList.get(i));
        }


        //=========== btn click ===========
        h.cardVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickView(i);
            }
        });


        return convertView;
    }


    void onClickView(int i) {
        synced = H.isReportOfThisSchoolSynced(listList.get(i));

        Intent intent = new Intent(context, RepActivity.class);
        intent.putExtra(Global.openMode, Global.EditMode);
        intent.putExtra(Global.dateBtn, false);
        intent.putExtra("schID", listList.get(i).get(0).getSchID());
        intent.putExtra("school", school);
        intent.putExtra("gradeID", school.getGradeID());
        intent.putExtra("date", listList.get(i).get(0).getDateReport());
        intent.putExtra(Global.openMode, Global.DisplayMode);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        if (synced) {
            intent.putExtra(Global.openMode, Global.DisplayMode);
        } else {
            intent.putExtra(Global.openMode, Global.EditMode);
        }

        context.startActivity(intent);
    }

}