/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.report_environment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Unbinder;

/**
 * Created by Mir Ferdous on 15/10/2017.
 */

public class RepDialog extends DialogFragment {
    private final String TAG = RepDialog.class.getName();
    public static final String TAG2 = "FullScreenDialog";

    private Unbinder unbinder;
    View view;
    TextView quesTV, targetTV;
    EditText detailsET;
    RadioGroup radioGroupPriority, radioGroupAns;
    RadioButton yes, no, major, moderate, minor;
    View repNoContainer;
    Button submitBtn;
    ImageView targetIV;
    Bundle bundle;
    String schID, gradeID, openMode, priority1, ans, details1, dateRep, time_lm;
    int index;
    RepMdlin data;
    RepMdlin defaultQuesData;
    private String problem;
    private SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
    RepSubmitInterface callbackActivity;
    private Snackbar snackbar;
    private AlertDialog alertDialog;

    public interface RepSubmitInterface {
        void onClickSubmitSingleRepFromDialogFrag(String schID, int index, RepMdlin data);
    }

    @Override
    public void onResume() {/*
        *//*full screen dialog*//*
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
*/

        super.onResume();
        /*dismiss stopping on submit click (if condition for priority snackbar showing)*/
        AlertDialog alertDialog = (AlertDialog) getDialog();
        Button submitThis = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        submitThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSubmitButtonAction();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //full screen dialog
       /* Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }*/
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //full screen dialog
        // setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);

        //get data
        bundle = getArguments();
        schID = bundle.getString("schID");
        gradeID = bundle.getString("gradeID");
        index = bundle.getInt("index");
        openMode = bundle.getString(Global.openMode);
        data = bundle.getParcelable("data");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.reporting_frag_dialog, null);

        quesTV = view.findViewById(R.id.repQuestion);
        detailsET = view.findViewById(R.id.repProbDetails);
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);
        radioGroupAns = view.findViewById(R.id.radioGroupAns);
        radioGroupPriority = view.findViewById(R.id.radioGroupAdminReport);
        major = view.findViewById(R.id.radioMajor);
        moderate = view.findViewById(R.id.radioModerate);
        minor = view.findViewById(R.id.radioMinor);
        submitBtn = view.findViewById(R.id.repOK);
        repNoContainer = view.findViewById(R.id.repNoContainer);
        targetIV = view.findViewById(R.id.solveDatePicker);
        targetTV = view.findViewById(R.id.solveDateDialog);

        //================= DIALOG FRAG DESIGN ======================================

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflater.inflate(R.layout.reporting_frag_dialog, null));
        builder.setView(view);
        // builder.setMonthName("Report Details");
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                detailsET.setText(null);
                radioGroupAns.clearCheck();
                radioGroupPriority.clearCheck();
            }
        });

        //================= set data ================================================

        setData();

        //================= radios ==================================================
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repNoContainer.setVisibility(View.GONE);
                detailsET.setText(null);
                radioGroupPriority.clearCheck();
                problem = "yes";
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repNoContainer.setVisibility(View.VISIBLE);
                problem = "no";
            }
        });
        //=====================priority btn==============================================
        major.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // major.setChecked(true);
                // priority = "1";
                data.setPriority("1");
            }
        });

        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // moderate.setChecked(true);
                //  priority = "2";
                data.setPriority("2");
            }
        });

        minor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // minor.setChecked(true);
                //  priority = "3";
                data.setPriority("3");
            }
        });


        //target date
        targetIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* DialogFragment dialog = new DatePickerFragment2();
                dialog.show(getActivity().getSupportFragmentManager(), "CalenderDialog2");*/
                datePicker();
            }
        });


        alertDialog = builder.create();
        Dialog.OnClickListener d = null;
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Submit This", d);
        return alertDialog;
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
                targetTV.setText(df1.format(calendar2.getTime()));
            }
        }, yy, mm, dd);
        datePicker.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            callbackActivity = (RepSubmitInterface) getActivity();
        } catch (ClassCastException e) {
            // when the activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    private void performSubmitButtonAction() {

        if (problem == null) {//when not answered
            snackbar = Snackbar.make(view, "Select Yes or No", Snackbar.LENGTH_SHORT);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else if (problem.equals("yes")) {
            yesRepMake(data);
            callbackActivity.onClickSubmitSingleRepFromDialogFrag(schID, index, data);
            alertDialog.dismiss();
        } else if (problem.equals("no")) {
            if (radioGroupPriority.getCheckedRadioButtonId() == -1) {
                Snackbar.make(view, "Select Problem Priority", Snackbar.LENGTH_SHORT)
                        .setAction("Dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                snackbar.dismiss();
                            }
                        }).show();
            } else {
                noRepMake(data);
                callbackActivity.onClickSubmitSingleRepFromDialogFrag(schID, index, data);
                alertDialog.dismiss();
                Log.d(TAG, data.toString());
            }
        }
    }

    //========================= Methods ============================================================
    private void setData() {
        quesTV.setText((data.getQuesNo()) + ". " + data.getQuestion());
        /**not answered*/
        if (data.getAnswer() == null)
            repNoContainer.setVisibility(View.GONE);
        /**answered*/
        else {
            if (data.getAnswer().equals("1")) {
                /**when answered is yes*/
                problem = "yes";
                repNoContainer.setVisibility(View.GONE);
                yes.setChecked(true);
            }
            /**when answered is no*/
            else if (data.getAnswer().equals("0")) {
                problem = "no";
                detailsET.setText(data.getDetails());
                detailsET.setSelection(detailsET.getText().length());//sets cursor to left of text
                no.setChecked(true);
                if (data.getPriority() != null) {
                    if (data.getPriority().equals("1"))
                        major.setChecked(true);
                    else if (data.getPriority().equals("2"))
                        moderate.setChecked(true);
                    else if (data.getPriority().equals("3"))
                        minor.setChecked(true);
                }

            }
        }
    }

    private void yesRepMake(RepMdlin in) {
        in.setSchID(schID);
        in.setGradeID(gradeID);
        in.setAnswer("1");
        in.setNotify("0");
        in.setNotify_his("0");
        in.setPriority(null);
        in.setDetails(null);
        in.setDateReport(dateRep);
        in.setSynced("0");
        in.setTimeLm(time_lm);
        if (in.getLocalIDAns() == null)
            in.setLocalIDAns(H.generateUniqueID("admin-ans"));
    }

    private void noRepMake(RepMdlin in) {
      /*  if (major.isChecked())
            priority = "1";
        else if (moderate.isChecked())
            priority = "2";
        else if (minor.isChecked())
            priority = "3";*/
        // details = detailsET.getText().toString();
        Log.d(TAG, "Priority: " + data.getPriority() + "\ndetails: " + data.getDetails());

        in.setSchID(schID);
        in.setGradeID(gradeID);
        in.setAnswer("0");
        in.setDetails(detailsET.getText().toString());
        //in.setPriority(priority);
        in.setNotify("1");
        in.setNotify_his("0");
        in.setDateReport(dateRep);
        in.setDateTarget(targetTV.getText().toString());
        in.setSynced("0");
        in.setTimeLm(time_lm);
        if (in.getLocalIDAns() == null)
            in.setLocalIDAns(H.generateUniqueID("admin-ans"));
    }


}
