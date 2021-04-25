/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.report_environment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Mir Ferdous on 12/10/2017.
 */

public class RepAdapter extends ArrayAdapter<RepMdlin> {
    private final String TAG = RepAdapter.class.getName();
    private Context context;
    List<RepMdlin> l;
    RepMdlin data;
    private String schID, gradeID, openMode, date, ans, priority, details, time_lm;

    int index;
    ViewHolder h;
    SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");


    public RepAdapter(Context context, int textViewResourceId,
                      List<RepMdlin> l, String schID, String gradeID, String openMode, String date) {
        super(context, textViewResourceId, l);
        this.context = context;
        this.l = l;
        this.schID = schID;
        this.gradeID = gradeID;
        this.openMode = openMode;
        this.date = date;
        time_lm = df1.format(new Date(System.currentTimeMillis()));
    }


    public void setItems(ArrayList<RepMdlin> l) {
        this.l.clear();
        this.l.addAll(l);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        View repAnsContainer, noDisplayContainer;
        TextView questionTV, detailsTV, priorityTV, probStatusTV, dateTargetTV;
        Button submitBtn;
        CardView card;
        CircleImageView img;
    }


    @Override
    public View getView(final int i, View cv, ViewGroup parent) {
        if (cv == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cv = vi.inflate(R.layout.reporting_frag_row, parent, false);

            h = new ViewHolder();
            h.card = cv.findViewById(R.id.repCard);
            h.repAnsContainer = cv.findViewById(R.id.repAnsContainer);
            h.noDisplayContainer = cv.findViewById(R.id.repDetailsContainer);
            h.questionTV = cv.findViewById(R.id.repQuestion);
            h.probStatusTV = cv.findViewById(R.id.repProblemStatus);
            h.priorityTV = cv.findViewById(R.id.repPriority);
            h.detailsTV = cv.findViewById(R.id.repDetails);
            h.dateTargetTV = cv.findViewById(R.id.targetDateR);
            h.img = cv.findViewById(R.id.repStatusImg);

            cv.setTag(h);
        } else {
            h = (ViewHolder) cv.getTag();
        }

        //font
        // H.setFont(context,new View[]{h.questionTV, h.probStatusTV, h.priorityTV, h.detailsTV});

         /*================== set data ================================*/
        h.questionTV.setText((i + 1) + ". " + l.get(i).getQuestion());
        // === unconditional display data listview on runtime change ===
        h.img.setImageResource(l.get(i).getImgID());
        h.probStatusTV.setText(l.get(i).getStatusExtra());
        h.priorityTV.setText(l.get(i).getPriorityExtra());
        h.priorityTV.setTextColor(l.get(i).getPriorityColorExtra());
        h.detailsTV.setText(l.get(i).getDetailsExtra());
        h.dateTargetTV.setText(l.get(i).getDateTargetExtra());

        if (l.get(i).getDateTargetExtra() != null)
            Log.d(TAG, l.get(i).toString());

        /*=== display data listview on runtime change ===*/
        /*if (data.getAnswer() != null) {
            h.repAnsContainer.setVisibility(View.VISIBLE);
            if (data.getAnswer().equals("1")) {
                h.img.setImageResource(R.drawable.right_green);
                h.probStatusTV.setText("Status: No Problem.");
                h.repDetailsContainer.setVisibility(View.GONE);
            } else if (data.getAnswer().equals("0")) {
                h.repDetailsContainer.setVisibility(View.VISIBLE);
                h.img.setImageResource(R.drawable.ic_cross);
                h.probStatusTV.setText("Status: Problem Exists.");
                h.priorityTV.setText("Problem Priority: " + H.problemPriorityNumToText(data));
                h.priorityTV.setTextColor(H.problemPriorityColor(context, data));
                h.detailsTV.setText("Problem Details: " + data.getDetails());
            }
        }
*/

/*=============================== btn =============================================================================*/
        h.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.get(i).setListPosition(i);

                Bundle bundle = new Bundle();
                bundle.putString("schID", schID);
                bundle.putString("gradeID", gradeID);
                bundle.putInt("index", i);
                bundle.putString(Global.openMode, openMode);
                bundle.putParcelable("data", l.get(i));
                DialogFragment dialog = new RepDialog();
                dialog.setArguments(bundle);
                dialog.setCancelable(false);

                // dialog .setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);//this line for full screen dialog

                dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "RepDialog2");


            }
        });

        return cv;
    }
}
