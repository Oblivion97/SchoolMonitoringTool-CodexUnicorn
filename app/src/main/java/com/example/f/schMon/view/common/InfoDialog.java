/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;

import java.text.SimpleDateFormat;

/**
 * Created by Mir Ferdous on 15/10/2017.
 */

public class InfoDialog extends DialogFragment {
    private final String TAG = InfoDialog.class.getName();
    private View view;
    private Bundle bundle;
    private String openWhat;
    private int icon;
    private String title = "";
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    private Snackbar snackbar;
    private AlertDialog alertDialog;
    private InfoMdl data;
    private TextView titleTV, schNameTV, eduTypeTV, dateTV, syncTV;
    private ImageView synImg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        openWhat = bundle.getString(Global.openWhat);
        data = bundle.getParcelable("data");
        if (data != null) {
            title = data.getTitle();
            icon = data.getIcon();
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_info, null);
        titleTV = view.findViewById(R.id.infoTitle);
        schNameTV = view.findViewById(R.id.infoSchName);
        eduTypeTV = view.findViewById(R.id.infoType);
        dateTV = view.findViewById(R.id.infoDate);
        syncTV = view.findViewById(R.id.infoSynced);
        synImg = view.findViewById(R.id.infoSyncImg);

        //================= DIALOG FRAG DESIGN ======================================

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(title);
        builder.setIcon(icon);
        builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        H.setFont(getContext(), new View[]{titleTV, schNameTV, eduTypeTV, dateTV, syncTV});
        //================= set data =================================

        setData();


        alertDialog = builder.create();
        return alertDialog;
    }


    //========================= Methods ============================================================
    private void setData() {
        if (data != null) {
            titleTV.setVisibility(View.GONE);
            schNameTV.setText(data.getSchName());
            eduTypeTV.setText(data.getEduType());
            dateTV.setText(data.getDateRep());
            syncTV.setText(data.getSyncStatus());
            synImg.setImageResource(data.getSyncIMG());
        }
    }

}

