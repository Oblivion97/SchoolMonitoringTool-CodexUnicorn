/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.evaluation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.view.common.DatePickerFragment;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DialogDateAttdce extends DialogFragment {
    private final String TAG = DatePickerFragment.class.getName();
    View view;
    EditText attendanceET;
    String schID, gradeID;
    DialogDateAttdce_StartEval callbackActivity;
    TextView dateTitle, dateTV, attendanceTitle;
    String date, attendance;
    boolean dateInputted;
    ImageView dateIV;
    Calendar calendar;

    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");

    public interface DialogDateAttdce_StartEval {
        void onStartEvalClick(String schID, String gradeID,String date, String attendance);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        schID = getArguments().getString("schID");
        gradeID = getArguments().getString("gradeID");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.eval_dialog_date, null);
        dateTitle = view.findViewById(R.id.dateTitle);
        dateTV = view.findViewById(R.id.dateTV);
        dateIV = view.findViewById(R.id.dateEvalBtn);
        attendanceTitle = view.findViewById(R.id.attendanceTitle);
        attendanceET = view.findViewById(R.id.attendanceDialogDate);
        H.setFont(getContext(), new View[]{attendanceET, dateTV, dateTitle, attendanceTitle});

        dateIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* DialogFragment dialog = new DatePickerFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "CalenderDialog");*/
                datePicker();
            }
        });


        //_________________DIALOG FRAG DESIGN________________________________
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.eval_dialog_date, null));
        builder.setView(view);
        builder.setMessage("Evaluation")
                .setPositiveButton("Start Evaluation", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //  dateTV.setText("");
                       // dateTV.setVisibility(View.VISIBLE);
                        callbackActivity.onStartEvalClick(schID, gradeID, date,attendanceET.getText().toString());

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            callbackActivity = (DialogDateAttdce_StartEval) getActivity();
        } catch (ClassCastException e) {
            // when the activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    void datePicker() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(year, monthOfYear, dayOfMonth);
                date = df1.format(calendar2.getTime());
                dateTV.setVisibility(View.VISIBLE);
                dateTV.setText(H.dateFormat2(date));
            }
        }, yy, mm, dd);
        datePicker.show();
    }


}

