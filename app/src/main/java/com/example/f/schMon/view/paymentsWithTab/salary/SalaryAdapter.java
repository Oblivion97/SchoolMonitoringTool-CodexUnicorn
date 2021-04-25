/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab.salary;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.FeeSalaryModel;

public class SalaryAdapter extends ArrayAdapter<FeeSalaryModel> {
    private final String TAG = SalaryAdapter.class.getName();
    private final Context context;
    TextView techrNameTV, schNameTV, takaTV;
    RelativeLayout salaryRow;
    View rowView;
    ImageView img;
    private FeeSalaryModel[] feeSalaryModels;
    private String paid, total_bill;

    public SalaryAdapter(Context context, FeeSalaryModel[] feeSalaryModels) {
        super(context, -1, feeSalaryModels);
        this.context = context;
        this.feeSalaryModels = feeSalaryModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.row_salary, parent, false);
        techrNameTV = rowView.findViewById(R.id.teachNameTV_salary);
        schNameTV = rowView.findViewById(R.id.schNameTV_salary);
        takaTV = rowView.findViewById(R.id.taka_salary);
        salaryRow = rowView.findViewById(R.id.root_salary);

        H.setFont(getContext(), new View[]{schNameTV, techrNameTV, takaTV});
        //___________________set data___________________


        techrNameTV.setText(feeSalaryModels[position].namePer);
        schNameTV.setText(feeSalaryModels[position].schName);


        paid = feeSalaryModels[position].paid;
        total_bill = feeSalaryModels[position].total_bill;
        if (paid != null && total_bill != null && !paid.equals("") && !total_bill.equals("")) {
            takaTV.setText(paid + "/" + total_bill);
       /*100 takay 33.33 er niche taka dile lal 66.66-33.33 er vitor holud 66.66 up sobuj*/
            double paidPercent = Double.parseDouble(paid) / Double.parseDouble(total_bill) * 100;
            if (paidPercent > 66.66) {
                //img.setImageResource(R.drawable.right_green);
                salaryRow.setBackgroundColor(Color.parseColor("#7ebf86"));
            } else if (paidPercent >= 33.33 && paidPercent <= 66.66) {
               // img.setImageResource(R.drawable.right_yellow);
                salaryRow.setBackgroundColor(Color.parseColor("#fcb74d"));
            } else if (paidPercent < 33.33) {
                //img.setImageResource(R.drawable.right_red);
                salaryRow.setBackgroundColor(Color.parseColor("#d41e15"));
            }
        } else {
            takaTV.setText("Not Available");
            //img.setImageResource(R.drawable.right_red);
            salaryRow.setBackgroundColor(Color.parseColor("#d41e15"));
        }
        return rowView;
    }
}