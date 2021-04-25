/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.report_environment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mir Ferdous on 29/08/2017.
 */
public class DatePickerFragment2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final String TAG = DatePickerFragment2.class.getName();
    public boolean dateInputted = true;
    Calendar calendar;

    DateDialogListner2 mCallback;

    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    String date;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.dateInputted = true;
        this.calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        date = df1.format(calendar.getTime());
        //((TextView) getActivity().findViewById(R.id.solveDateDialog)).setText(date);
        mCallback.onDateClick2(DatePickerFragment2.this, dateInputted, calendar);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallback = (DateDialogListner2) getActivity();
        } catch (ClassCastException e) {
            // when the activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface DateDialogListner2 {
        void onDateClick2(DialogFragment dialog, boolean dateInputted, Calendar calendar);
    }

}
