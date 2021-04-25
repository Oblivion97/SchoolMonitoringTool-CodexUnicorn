/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncMirOld;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.f.schMon.R;

/**
 * Created by Mir Ferdous on 04/09/2017.
 */

public class DialogSync {
    public static AlertDialog getSyncDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setMessage("Do You Want to Sync All Data with Server?")
                .setPositiveButton("Sync", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

        alertDialog.getWindow().setBackgroundDrawableResource(R.color.bckGround2);


        //alertDialog.getWindow().setBackgroundDrawableResource(R.color.colorCream);
        return alertDialog;
    }
}
