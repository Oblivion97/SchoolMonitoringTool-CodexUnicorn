/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab.feeStd;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.FeeSalaryModel;


public class FeeStdAdapter extends ArrayAdapter<FeeSalaryModel> {
    private final Context context;
    TextView stdName, takaTV;
    View rowView;
    LinearLayout rootStudent;
    private String TAG = this.getClass().getName();
    private FeeSalaryModel[] feeSalaryModels;
    private String paid, total_bill;

    public FeeStdAdapter(Context context, FeeSalaryModel[] feeSalaryModels) {
        super(context, -1, feeSalaryModels);
        this.context = context;
        this.feeSalaryModels = feeSalaryModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.row_fee_std, parent, false);

        stdName = rowView.findViewById(R.id.nameTV_fee);
        takaTV = rowView.findViewById(R.id.taka_fee);
        rootStudent = rowView.findViewById(R.id.root_student);

        H.setFont(getContext(), new View[]{stdName, takaTV});
        //___________________set data___________________

        stdName.setText(feeSalaryModels[position].namePer);
        paid = feeSalaryModels[position].paid;
        total_bill = feeSalaryModels[position].total_bill;

        if (paid != null && total_bill != null && !paid.equals("") && !total_bill.equals("")) {
            takaTV.setText(paid + "/" + total_bill);

            double paidPercent = Double.parseDouble(paid) / Double.parseDouble(total_bill) * 100;
            if (paidPercent > 66.66) {
                //img.setImageResource(R.drawable.right_green);
                rootStudent.setBackgroundColor(Color.parseColor("#7ebf86"));
            } else if (paidPercent >= 33.33 && paidPercent <= 66.66) {
                //img.setImageResource(R.drawable.right_yellow);
                rootStudent.setBackgroundColor(Color.parseColor("#fcb74d"));
            } else if (paidPercent < 33.33) {
                //img.setImageResource(R.drawable.right_red);
                rootStudent.setBackgroundColor(Color.parseColor("#d41e15"));
            }
        } else {
            takaTV.setText("Not Available");
            //img.setImageResource(R.drawable.right_red);
            rootStudent.setBackgroundColor(Color.parseColor("#d41e15"));
        }


        return rowView;
    }
}