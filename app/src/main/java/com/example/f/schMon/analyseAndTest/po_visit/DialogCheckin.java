/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.po_visit;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.f.schMon.R;

/**
 * Created by Mir Ferdous on 04/09/2017.
 */

public class DialogCheckin {
    public static AlertDialog getCheckinDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setMessage("Please Check in if U r in School Premise")
                .setPositiveButton("Check in", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.color.bckGround2);
        return alertDialog;
    }
}
