/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.reporting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Mir Ferdous on 12/10/2017.
 */

public class ReportingAdapter extends ArrayAdapter<RepMdlin> {
    private final String TAG = ReportingAdapter.class.getName();
    private Context context;
    List<RepMdlin> l;
    RepMdlin data;
    private String schID, openMode, date,
            ans, priority, details;
    SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    String time_lm;

    public ReportingAdapter(Context context, int textViewResourceId,
                            List<RepMdlin> l, String schID, String openMode, String date) {
        super(context, textViewResourceId, l);
        this.context = context;
        this.l = l;
        this.schID = schID;
        this.openMode = openMode;
        this.date = date;
        time_lm = df1.format(new Date(System.currentTimeMillis()));
        /*l = new ArrayList<>();
        l.addAll(l);*/
    }

    private class ViewHolder {
        View noContainer, displayContainer, inputContainer;
        TextView question, detailsTV, priorityTV;
        EditText detailEt;
        RadioButton yes, no, radioMajor, radioMinor, radioModerate;
        Button submitBtn;
    }

    @Override
    public View getView(final int i, View cv, ViewGroup parent) {
        final ViewHolder h;
        Log.v("ConvertView", String.valueOf(i));
        if (cv == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cv = vi.inflate(R.layout.reporting_row, null);

            h = new ViewHolder();
            h.noContainer = cv.findViewById(R.id.repNoContainer);
            h.displayContainer = cv.findViewById(R.id.repDetailsContainer);
            h.inputContainer = cv.findViewById(R.id.repNoContainerInput);
            h.question = cv.findViewById(R.id.repQuestion);
            h.detailsTV = cv.findViewById(R.id.repDetails);
            h.yes = cv.findViewById(R.id.yes);
            h.no = cv.findViewById(R.id.no);
            h.detailEt = cv.findViewById(R.id.repProbDetails);
            h.priorityTV = cv.findViewById(R.id.repPriority);
            h.radioMajor = cv.findViewById(R.id.radioMajor);
            h.radioModerate = cv.findViewById(R.id.radioModerate);
            h.radioMinor = cv.findViewById(R.id.radioMinor);
            h.submitBtn = cv.findViewById(R.id.repOK);

            cv.setTag(h);
        } else {
            h = (ViewHolder) cv.getTag();
        }


        data = l.get(i);
        h.question.setText((i + 1) + ". " + l.get(i).getQuestion());
        h.no.setChecked(l.get(i).isNoSelected());
        //holder.no.setTag(l.get(i));
        if (l.get(i).isNoSelected()) {
            h.noContainer.setVisibility(View.VISIBLE);
        } else {
            h.noContainer.setVisibility(View.GONE);
        }


//=============================== btn =============================================================================
        h.yes.setChecked(data.isYesSelected());
        // holder.yes.setTag(data);

        h.yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                   /* RadioButton cb = (RadioButton) v;
                    final ReportingData data = (ReportingData) cb.getTag();
                    data.setYesSelect(true);*/
                //final String id = l.get(position).getId();
                //if (holder.yes.isChecked()) {
                data.setYesSelected(true);
                data.setNoSelected(false);
                yesRepMake(data);

                l.remove(i);
                l.add(i, data);
                notifyDataSetChanged();
            }


        });
        //
        h.no.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    /*RadioButton cb = (RadioButton) v;
                    ReportingData data = (ReportingData) cb.getTag();
                    data.setNoSelect(cb.isChecked());*/
                // if (holder.no.isChecked()) {
                data.setYesSelected(false);
                data.setNoSelected(true);
                h.noContainer.setVisibility(View.VISIBLE);
                h.priorityTV.requestFocus();


                h.displayContainer.setVisibility(View.GONE);
                h.inputContainer.setVisibility(View.VISIBLE);


            }
        });

        h.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //*Button cb = (Button) view;
                // ReportingData data = (ReportingData) cb.getTag();*//*

                details = h.detailEt.getText().toString();

                noRepMake(data);


                //   if (l.get(i).getPriority() != null && (l.get(i).getDetails() != null || !l.get(i).getDetails().equals(""))) {
                h.detailsTV.setText(l.get(i).getDetails());
                h.priorityTV.setText(l.get(i).getPriority());

                view.clearFocus();

                h.displayContainer.setVisibility(View.VISIBLE);
                h.inputContainer.setVisibility(View.GONE);

                l.remove(i);
                l.add(i, data);
                notifyDataSetChanged();
            }
        });
//=============================== priority btn ==============================================================================
        h.radioMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    /*RadioButton cb = (RadioButton) view;
                    ReportingData data = (ReportingData) cb.getTag();
                    data.setMajorSelected(cb.isChecked());*/
                //if (holder.radioMajor.isChecked()) {
                l.get(i).setMajorSelected(true);
                l.get(i).setMinorSelected(false);
                l.get(i).setModerateSelected(false);
                // Toast.makeText(context, "Major clicked", Toast.LENGTH_SHORT).show();
                h.priorityTV.setText("1");
                l.get(i).setPriority("1");
                //}
            }
        });
        h.radioMinor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   /* RadioButton cb = (RadioButton) view;
                    ReportingData data = (ReportingData) cb.getTag();
                    data.setMinorSelected(cb.isChecked());*/
                //if (holder.radioMinor.isChecked()) {
                l.get(i).setMinorSelected(true);
                l.get(i).setMajorSelected(false);
                l.get(i).setModerateSelected(false);
                //  Toast.makeText(context, "Minor clicked", Toast.LENGTH_SHORT).show();
                h.priorityTV.setText("3");
                l.get(i).setPriority("3");
                //}
            }
        });
        h.radioModerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   /* RadioButton cb = (RadioButton) view;
                    ReportingData data = (ReportingData) cb.getTag();
                    data.setModerateSelected(cb.isChecked());*/
                //if (holder.radioModerate.isChecked()) {
                l.get(i).setModerateSelected(true);
                l.get(i).setMinorSelected(false);
                l.get(i).setMajorSelected(false);
                l.get(i).setPriority("2");

                // Toast.makeText(context, "Moderate clicked", Toast.LENGTH_SHORT).show();
                h.priorityTV.setText("2");
                //}
            }
        });


        //Fill EditText with the value you have in data source
        h.detailEt.setText(l.get(i).getDetails());
        h.detailEt.setId(i);
        h.priorityTV.setText(l.get(i).getPriority());
        h.priorityTV.setId(i);


        h.radioMajor.setChecked(l.get(i).isMajorSelected());
        // holder.radioMajor.setTag(data);
        h.radioMinor.setChecked(l.get(i).isMinorSelected());
        // holder.radioMinor.setTag(data);
        h.radioModerate.setChecked(l.get(i).isModerateSelected());
        // holder.radioModerate.setTag(data);

        //we need to update adapter once we finish with editing
        h.detailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {

                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    h.detailsTV.setText(Caption.getText().toString());
                    // holder.detailEt.setText(Caption.getText().toString());
                    l.get(position).setDetails(Caption.getText().toString());
                    l.get(position).setDetails(Caption.getText().toString());
                    Toast.makeText(context, "Focus changed " + Caption.getText().toString(), Toast.LENGTH_SHORT).show();
                    //notifyDataSetChanged();
                }

            }

        });

        return cv;
    }


    private void yesRepMake(RepMdlin in) {
        in.setSchID(schID);
        in.setAnswer("1");
        in.setNotify("0");
        in.setNotify_his("0");
        in.setPriority(null);
        in.setDetails(null);
        in.setDateReport(date);
        in.setSynced("0");
        in.setTimeLm(time_lm);
    }

    private void noRepMake(RepMdlin in) {
        in.setSchID(schID);
        in.setAnswer("0");
        in.setDetails(details);
        in.setPriority(priority);
        in.setNotify("1");
        in.setNotify_his("0");
        in.setDateReport(date);
        in.setSynced("0");
        in.setTimeLm(time_lm);
    }


}