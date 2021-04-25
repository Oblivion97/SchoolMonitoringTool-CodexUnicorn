/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.report;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class RepAdminAdapter extends ArrayAdapter implements RepAdminDialog.ReportAdminFragmentLister_Cancel {
    List<RepMdlin> list;
    Context context;
    AppCompatActivity appCompatActivity;
    LayoutInflater inflter;
    String openMode;
    private int selected = -1;
    private int selected2 = -1;
    // @BindView(R.id.question)
    /*TextView questionTV;
    //@BindView(R.id.yes)
    RadioButton yesRadioBtn;
    //@BindView(R.id.no)
    RadioButton noRadioBtn;
    // @BindView(R.id.repNoDetails)
    View noLayout;
    // @BindView(R.id.repDetails)
    TextView details;
    // @BindView(R.id.repPriority)
    TextView priority;
    // @BindView(R.id.repPriorityImg)
    ImageView img;*/
    Unbinder unbinder;
    String schID;

    public RepAdminAdapter(Context context, AppCompatActivity appCompatActivity,
                           List<RepMdlin> list, String openMode, String schID) {
        super(context, R.layout.row_report_admin, list);
        this.context = context;
        this.list = list;
        this.openMode = openMode;
        this.schID = schID;
    }

    /*set no button in questionlist(in Report Activity) unchecked if no_reporting_dialogFragment canceled*/
    @Override
    public void onCancel_Click_in_Dialog(DialogFragment dialog, int index) {
        if (h.noRadioBtn != null) {
            h.noRadioBtn.setChecked(false);
            h.chkNo.setChecked(false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

  /*  @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }*/

    static class ViewHolder {

        @BindView(R.id.repQuestion)
        TextView questionTV;
        @BindView(R.id.yes)
        RadioButton yesRadioBtn;
        @BindView(R.id.no)
        RadioButton noRadioBtn;
        @BindView(R.id.repNoContainer)
        View noLayout;
        @BindView(R.id.repDetails)
        TextView details;
        @BindView(R.id.repPriority)
        TextView priority;
        @BindView(R.id.repChkYes)
        CheckBox chkYes;
        @BindView(R.id.repChkNo)
        CheckBox chkNo;
        @BindView(R.id.repYesBtn)
        CircleImageView yesBtn;
        @BindView(R.id.repNoBtn)
        CircleImageView noBtn;
        @BindView(R.id.repPriorityImg)
        ImageView img;

        ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    ViewHolder h;
    RepMdlin data;
    int index;
    LayoutInflater li;

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.row_report_admin, null);
            h = new ViewHolder(convertView);
            convertView.setTag(h);


            h.chkYes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    RepMdlin planet = (RepMdlin) cb.getTag();
                    h.chkYes.setChecked(cb.isChecked());
                }
            });










        } else {
            h = (ViewHolder) convertView.getTag();
        }


        //======================== set data ====================================
        data = list.get(i);
        index = i;
        h.questionTV.setText((i + 1) + ". " + data.getQuestion());

        /*h.chkYes.setChecked(data.isYesSelect());
        h.chkNo.setChecked(data.isNoSelect());*/

        h.chkYes.setTag(data);
        h.chkNo.setTag(data);
       /* h.yesRadioBtn.setTag(i);
        h.yesRadioBtn.setChecked(i == selected);

        h.noRadioBtn.setTag(i);
        h.noRadioBtn.setChecked(i == selected2);*/

        //======================== for edit mode old answer marking ============

        //________btn check yes no for editmode________________

        if (openMode.equals(Global.EditMode)) {
            RepMdlin repModel = list.get(i);
            if (repModel.getAnswer().equals("1")) {
                h.yesRadioBtn.setChecked(true);
            } else if (repModel.getAnswer().equals("0")) {
                h.noRadioBtn.setChecked(true);

            }
        }

        //____________problem detail for editmode____________
        String priority = list.get(i).getPriority(), details = list.get(i).getDetails();
        if (details != null && priority != null) {
            if (!priority.equals("")) {
                h.priority.setText("Problem Priority: " + priority);
                if (!details.equals(""))
                    h.details.setText("Details: " + details);
            }
        }


        //======================== radio btn =========================================
        // perform setOnCheckedChangeListener event on yes btn_submit
        h.yesRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //btn check remove bug fix on scrool

                /*selected = (Integer) buttonView.getTag();
                notifyDataSetChanged();*/
                CheckBox cb = (CheckBox) buttonView;
                data = (RepMdlin) cb.getTag();
                yesRep(data, index, isChecked);
            }
        });


        h.noRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                //btn check remove bug fix on scrool

              /*  selected2 = (Integer) buttonView.getTag();
                notifyDataSetChanged();*/

                CheckBox cb = (CheckBox) buttonView;
                data = (RepMdlin) cb.getTag();
                noRep(data, index, isChecked);


            }
        });


        //================================ chk box ==================================================================
       /* h.chkYes.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // yesRep(data, i, isChecked);
                    }
                }
        );*/


        h.chkNo.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //  noRep(data, i, isChecked);
                    }
                }
        );





        //================================ btn ==================================================================
        h.yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.yesBtn.setImageResource(R.drawable.right_green);
                Toasty.info(context, String.valueOf(index)).show();

            }
        });

        h.noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.noBtn.setImageResource(R.drawable.right_green);
                Toasty.info(context, String.valueOf(index)).show();
            }
        });


        return convertView;
    }


    //================================== report func ===============================================

    private void yesRep(RepMdlin data, int i, boolean isChecked) {
        if (isChecked) {

            h.chkNo.setChecked(false);


            // set Yes values in ArrayList if RadioButton is checked
            RepMdlin r = new RepMdlin();
            r.setSchID(schID);
            r.setServerIDQues(list.get(i).getServerIDQues());
            r.setQuesTitle(list.get(i).getQuestionTitle());
            r.setQues(list.get(i).getQuestion());
            r.setAns("1");
            r.setAnsWeight("100");
            r.setDetails(null);
            r.setPriority(null);
            r.setServerQuestion(list.get(i).getServerQuestion());
            //repModelTemp.setDateReport(""); //date will be set in host activity
            r.setSynced("0");
            r.setTimeLm("");
            r.setNotify("0");

            if (context instanceof YesBtnListner) {
                //passing RepModel to host activity for saving in db
                ((YesBtnListner) context).onClickYesBtn_in_Adapter(r, i);
            }


            h.priority.setText(r.getPriority());
            h.details.setText(r.getDetails());
        }
    }


    private void noRep(RepMdlin data, int i, boolean isChecked) {
        if (isChecked) {

            h.chkYes.setChecked(false);

            DialogFragment dialog = new RepAdminDialog();
            Bundle bundle = new Bundle();
            bundle.putString(Global.openMode, openMode);
            bundle.putInt("index", i);
            bundle.putString("schID", schID);
            bundle.putString("server_id", list.get(i).getServerIDQues());
            bundle.putString("quesTitle", list.get(i).getQuestionTitle());
            bundle.putString("ques", list.get(i).getQuestion());
            bundle.putString("priority", list.get(i).getPriority());
            bundle.putString("details", list.get(i).getDetails());
            bundle.putString("fromServer", list.get(i).getServerQuestion());
            dialog.setArguments(bundle);
            dialog.setCancelable(false);
            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "RepAdminDialog");
        }
    }


    private void removeReportWhenCheckBoxUnchecked(int i) {


    }


    //========================= frag start from host activity ================================
    public interface YesBtnListner {
        void onClickYesBtn_in_Adapter(RepMdlin repModelTemp, int index);
    }

}