/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Mir Ferdous on 29/08/2017.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final String TAG = DatePickerFragment.class.getName();
    public boolean dateInputted = true;
    Calendar calendar;

    DateDialogListner mCallback;

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
        mCallback.onDateClick(DatePickerFragment.this, dateInputted, calendar);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallback = (DateDialogListner) getActivity();
        } catch (ClassCastException e) {
            // when the activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface DateDialogListner {
        void onDateClick(DialogFragment dialog, boolean dateInputted, Calendar calendar);
    }

}
