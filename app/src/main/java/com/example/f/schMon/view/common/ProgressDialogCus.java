/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.f.schMon.R;
import com.example.f.schMon.view.common.DatePickerFragment;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class ProgressDialogCus extends ProgressDialog {
    private final String TAG = DatePickerFragment.class.getName();
    View view;
    private Context context;

    public ProgressDialogCus(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.progress_dialog_custom);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.progress_dialog_custom, null);


        getWindow().setBackgroundDrawableResource(R.color.progressBarBckgrnd);

        ProgressBar prog = findViewById(R.id.myprogress);


        GifImageView givImageView = findViewById(R.id.gifLoad);
        try {
            GifDrawable gifFromAssets = null;
            gifFromAssets = new GifDrawable(getContext().getAssets(), "load.gif");
            givImageView.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // final LayoutInflater inflater = getActivity().getLayoutInflater();
        // view = inflater.inflate(R.layout.progress_dialog_custom, null);


        //_________________DIALOG FRAG DESIGN________________________________
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.progress_dialog_custom);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.progressBarBckgrnd);
        // dialog.setMonthName("Title...");

// set the custom dialog components - title, ProgressBar and button


        ProgressBar prog = dialog.findViewById(R.id.myprogress);


        GifImageView givImageView = dialog.findViewById(R.id.gifLoad);
        try {
            GifDrawable gifFromAssets = null;
            gifFromAssets = new GifDrawable(getContext().getAssets(), "load.gif");
            givImageView.setImageDrawable(gifFromAssets);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dialog;
    }


}

