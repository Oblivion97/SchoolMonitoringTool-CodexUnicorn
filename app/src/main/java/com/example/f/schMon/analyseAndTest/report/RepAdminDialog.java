/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.report;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.view.common.DatePickerFragment;
import com.example.f.schMon.view.report_environment.RepMdlin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RepAdminDialog extends DialogFragment {
    private final String TAG = DatePickerFragment.class.getName();
    View view;
    @BindView(R.id.detailsReportAdminET)
    EditText detailsET;
    @BindView(R.id.radioGroupRep)
    RadioGroup radioGroup;
    @BindView(R.id.radioMajor)
    RadioButton majorRadio;
    @BindView(R.id.radioMinor)
    RadioButton minorRadio;
    @BindView(R.id.radioModerate)
    RadioButton moderateRadio;
    Snackbar snackbar;
    int index;//index of RepModel in ReportModelList in HostActivity
    private String questionID, quesTitle, question, fromServer, answerWeight, priority, details;
    ReportAdminDialogFragmentListner_Done callbackActivity;
    ReportAdminFragmentLister_Cancel callbackAdapter;
    Unbinder unbinder;
    AlertDialog alertDialog;
    String openMode, schID;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(getActivity());*/
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.report_admin_dialog, null);
        unbinder = ButterKnife.bind(this, view);


        //______________________ set priority btn __________________________

        /** priority=1 means most severe problem
         * answerWeight=0 means schools performance in administrative checklist is lowest
         * */
        majorRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    priority = "1";
                    answerWeight = "0";
                }
            }
        });
        moderateRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priority = "2";
                    answerWeight = "25";
                }
            }
        });
        minorRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    priority = "3";
                    answerWeight = "50";
                }
            }
        });

        //______________________getting data__________________________
        Bundle bundle = this.getArguments();
        if (bundle != null) {//this will get value of i from adapter
            this.openMode = bundle.getString(Global.openMode, "");
            this.index = bundle.getInt("index", 0);
            this.schID = bundle.getString("schID", "");
            this.questionID = bundle.getString("server_id", "");
            this.quesTitle = bundle.getString("quesTitle", "");
            this.question = bundle.getString("ques", "");
            this.priority = bundle.getString("priority", "");
            this.details = bundle.getString("details", "");
            this.fromServer = bundle.getString("fromServer", "");

        }
        //_________________DIALOG FRAG DESIGN________________________________

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setMessage("Report in Details");

        //.setPositiveButton("Save", new DialogInterface.OnClickListener());

        /*if (openMode.equals(Global.RepEditMode)) {
            detailsET.setText(details);
            if (priority.equals("1"))
                majorRadio.setChecked(true);
            else if (priority.equals("2"))
                moderateRadio.setChecked(true);
            else if (priority.equals("3"))
                minorRadio.setChecked(true);
        }*/

        Dialog.OnClickListener d = null;
        alertDialog = builder.create();
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", d);
        return alertDialog;
    }


    @Override
    public void onResume() {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSaveButtonAction();
            }
        });
    }

    private void performSaveButtonAction() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            snackbar = Snackbar.make(view, "select Priority ... then save", Snackbar.LENGTH_SHORT);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else {                           // one of the radio buttons is checked


            String details = detailsET.getText().toString();

            RepMdlin r = new RepMdlin();
            r.setSchID(schID);
            r.setServerIDQues(questionID);
            r.setQues(question);
            r.setAns("0");
            r.setAnsWeight(answerWeight);
            r.setDetails(details);
            r.setPriority(priority);
            r.setServerQuestion(fromServer);
            //repModelTemp.setDateReport(""); //date will be set in host activity
            r.setSynced("0");
            r.setTimeLm("");
            r.setNotify("1");
            r.setNotify_his("0");
            r.setN_update_date(null);
            //passing model to Host Activity for saving in db
            callbackActivity.onDone_Click_in_Dialog(RepAdminDialog.this, r, index);
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            callbackActivity = (ReportAdminDialogFragmentListner_Done) getActivity();
            callbackAdapter = (ReportAdminFragmentLister_Cancel) getActivity();
        } catch (ClassCastException e) {
            // when the activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface ReportAdminDialogFragmentListner_Done {
        void onDone_Click_in_Dialog(DialogFragment dialog, RepMdlin repModelTemp, int index);
        //public void onCancel_Click_in_Dialog(DialogFragment dialog,  int index);
    }


    public interface ReportAdminFragmentLister_Cancel {
        void onCancel_Click_in_Dialog(DialogFragment dialog, int index);
    }
}

