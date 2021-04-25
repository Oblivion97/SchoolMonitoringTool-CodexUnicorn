/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

/**
 * Created by Mir Ferdous on 29/11/2017.
 */

public class EvlAdapterRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = EvlAdapterRecycler.class.getName();
    private Context context;
    private List<EvlModelin> list;
    private VH vh;
    private SQLiteDatabase db;
    private String tabelStatus, schID, gradeID, date, attendance, openMode;

    public EvlAdapterRecycler(Context context, String schID, String gradeID, String date, String attendance, String openMode, List<EvlModelin> list) {
        this.context = context;
        this.list = list;
        this.schID = schID;
        this.gradeID = gradeID;
        this.date = date;
        this.openMode = openMode;
        this.attendance = attendance;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater LInflater = LayoutInflater.from(context);
        View row = LInflater.inflate(R.layout.row_evl, parent, false);
        vh = new VH(row);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int i) {

        int serial = i + 1;
        ((VH) h).quesTV.setText(serial + ". " + list.get(i).getQues());
        ((VH) h).askTV.setText(list.get(i).getAskedExtra());
        ((VH) h).correctTV.setText(list.get(i).getCorrectExtra());
        ((VH) h).evlStatusImg.setImageResource(list.get(i).getImgID());


        //___________________Font___________________
        H.setFont(context, new View[]{((VH) h).quesTV, ((VH) h).askTV, ((VH) h).correctTV});

        ((VH) h).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (openMode.equalsIgnoreCase(Global.DisplayMode))
                    Toasty.error(context, "This Evaluation is Synced, can't be Edited.", Toast.LENGTH_SHORT, true).show();

                else {
                    A.setEvlList(list);
                    Intent intent = new Intent(context, AnsActivity.class);
                    intent.putExtra("schID", schID);
                    intent.putExtra("gradeID", gradeID);
                    intent.putExtra("date", date);
                    intent.putExtra("attendance", attendance);
                    intent.putExtra("index", i);
                    intent.putExtra("data", list.get(i));
                    A.setEvlModelin(list.get(i));
                    Log.d(TAG, list.get(i).toString());
                    intent.putExtra(Global.openMode, openMode);
                    ((EvlActivity) context).startActivityForResult(intent, 1);
                    ((EvlActivity) context).overridePendingTransition(R.anim.righttoleft, 0);
                    // ((EvlActivity) context).finish();
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //============================== viewholder ==========================================================================================
    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.evlQues)
        TextView quesTV;
        @BindView(R.id.evalAsk)
        TextView askTV;
        @BindView(R.id.evalCorrect)
        TextView correctTV;
        @BindView(R.id.sch_name_card_view)
        CardView cardView;
        @BindView(R.id.evlContainer)
        View containerOfInfo;
        @BindView(R.id.evlStatusImg)
        CircleImageView evlStatusImg;

        public VH(View row) {
            super(row);
            ButterKnife.bind(this, row);
        }
    }


    //=============================Methods============================================================================================

}