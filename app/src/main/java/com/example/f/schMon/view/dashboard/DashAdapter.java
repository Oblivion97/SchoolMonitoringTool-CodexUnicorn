/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.LastPerfMdl;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.school.SchoolProfileActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mir Ferdous on 01/11/2017.
 */

public class DashAdapter extends ArrayAdapter<LastPerfMdl> {
    private static final String TAG = DashAdapter.class.getName();
    private Context context;
    private String typeAttndnc = "attendance";
    private String typeEnv = "environment";
    private String typeEvl = "evaluation";
    private String grade, perfType;
    private List<LastPerfMdl> lpm;// for getting schoolSync name and performace percentage of onClickAttndnc,adminReport,evaluation
    private List<School> schInfos;//for getting education type
    private String code;
    private String schID;

    public DashAdapter(Context context, String perfType, List<LastPerfMdl> lastPerfMdls, List<School> schoolList) {
        super(context, R.layout.row_dash_sch_percent, R.id.tv_schoolName, lastPerfMdls);
        this.context = context;
        this.perfType = perfType;
        this.lpm = lastPerfMdls;
        this.schInfos = schoolList;
    }


    static class ViewHolder {
        @BindView(R.id.tv_schoolName)
        TextView schNameTV;
        @BindView(R.id.tv_schoolType)
        TextView eduTypeTV;
        @BindView(R.id.tv_section_name)
        TextView performPercentTV;
        @BindView(R.id.dashRowParent)
        View cardVw;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    ViewHolder h;

    @NonNull
    @Override
    public View getView(final int j, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.row_dash_sch_percent, parent, false);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        H.setFont(context, new View[]{h.schNameTV, h.eduTypeTV, h.performPercentTV});

        //======== set data ===========
        setData(j);


        //======== on click ===========
        h.cardVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  onClickRow(j);
            }
        });


        return convertView;
    }


    void onClickRow(int j) {
        schID = schInfos.get(j).getSchID();
        Intent intent = new Intent(context, SchoolProfileActivity.class);
        intent.putExtra("schID", schID);
        context.startActivity(intent);
    }


    void setData(int j) {

        h.schNameTV.setText(lpm.get(j).getSchName().toUpperCase() + "\n" +
                "Code: " + lpm.get(j).getCode() + "\n" +
                "Grade: " + lpm.get(j).getGrade());
        //h.eduTypeTV.setText(grade);
        if (perfType.equals(typeAttndnc)) {
            h.performPercentTV.setText(lpm.get(j).getLastAttenPerform() + "%");
        } else if (perfType.equals(typeEnv)) {
            h.performPercentTV.setText(lpm.get(j).getLastRepPerform() + "%");
        } else if (perfType.equals(typeEvl)) {
            h.performPercentTV.setText(lpm.get(j).getLastEvalPerform() + "%");
        }
    }

}