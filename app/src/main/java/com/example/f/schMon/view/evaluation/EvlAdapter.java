/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
 * Created by Mir Ferdous on 20/09/2017.
 */

public class EvlAdapter extends ArrayAdapter<EvlModelin> {
    private final String TAG = EvlAdapter.class.getName();
    private final Context context;
    List<EvlModelin> list;
    View rowView;
    private String schID, gradeID, date, attendance, active, serverQues, classs, asked, correct, attempt;
    String openMode;
    ViewHolder h;
    LayoutInflater li;
    EvlModelin data;

    public EvlAdapter(Context context, String schID, String gradeID, String date, String attendance, String openMode, List<EvlModelin> list) {
        super(context, -1, list);
        this.context = context;
        this.list = list;
        this.schID = schID;
        this.gradeID = gradeID;
        this.date = date;
        this.openMode = openMode;
        this.attendance = attendance;
    }

    @Override
    public int getCount() {
        return list.size();
    }


    static class ViewHolder {
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

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }


    @NonNull
    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
       /* LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.row_evl, parent, false);
        ButterKnife.bind(this, rowView);
         cardView = rowView.findViewById(R.id.sch_name_card_view);
        quesTV = rowView.findViewById(R.id.evlQues);
        */
        if (convertView == null) {
            li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.row_evl, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);

        } else {
            h = (ViewHolder) convertView.getTag();
        }
        //___________________Font___________________
        H.setFont(context, new View[]{h.quesTV, h.askTV, h.correctTV});


//=============================== set data ==============================================================================

        data = list.get(i);
        h.quesTV.setText((i + 1) + ". " + data.getQues());

        //=== uncondition ====
        h.askTV.setText(list.get(i).getAskedExtra());
        h.correctTV.setText(list.get(i).getCorrectExtra());
        h.evlStatusImg.setImageResource(list.get(i).getImgID());


        //_______ on click _______________________

        h.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (openMode.equalsIgnoreCase(Global.DisplayMode))
                    Toasty.error(getContext(), "This Evaluation is Synced, can't be Edited.", Toast.LENGTH_SHORT, true).show();
                else {
                    data.setListPosition(i);
                    A.setEvlList(list);
                    Intent intent = new Intent(context, AnsActivity.class);
                    intent.putExtra("schID", schID);
                    intent.putExtra("gradeID", gradeID);
                    intent.putExtra("date", date);
                    intent.putExtra("onClickAttndnc", attendance);
                    intent.putExtra("data", list.get(i));
                    intent.putExtra(Global.openMode, openMode);
                    context.startActivity(intent);
                    ((EvlActivity) context).overridePendingTransition(R.anim.righttoleft, 0);
                    ((EvlActivity) context).finish();
                }

            }
        });


        return convertView;
    }
}
