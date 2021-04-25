/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab.feeSch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.FeeSalaryModel;


public class FeeSchAdapter extends ArrayAdapter<FeeSalaryModel> {
    private static final String TAG = FeeSchAdapter.class.getName();
    private final Context context;
    TextView schName, takaTV;
    View rowView;
    ImageView img;
    private FeeSalaryModel[] feeSalaryModels;
    private String paid, total_bill;

    public FeeSchAdapter(Context context, FeeSalaryModel[] feeSalaryModels) {
        super(context, -1, feeSalaryModels);
        this.context = context;
        this.feeSalaryModels = feeSalaryModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.row_fee_sch, parent, false);

        schName = rowView.findViewById(R.id.nameTV_fee);
        takaTV = rowView.findViewById(R.id.taka_fee);
        img = rowView.findViewById(R.id.img_fee_sch);


        H.setFont(getContext(), new View[]{schName, takaTV});

        //___________________set data___________________

        schName.setText(feeSalaryModels[position].schName);
        paid = feeSalaryModels[position].paid;
        total_bill = feeSalaryModels[position].total_bill;
        if (paid != null && total_bill != null
                && !paid.equals("") && !total_bill.equals("")) {
            takaTV.setText(paid + "/" + total_bill);
        } else {
            takaTV.setText("Not Available");
        }
        img.setImageResource(R.drawable.arrow_black);


        return rowView;
    }
}