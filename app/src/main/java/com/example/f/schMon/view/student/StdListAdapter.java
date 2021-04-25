/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.student;

/**
 * Created by Mir Ferdous on 13/06/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.Student;

public class StdListAdapter extends ArrayAdapter<Student> {
    private final Context context;
    TextView nameTV, fatherTV, rollTV, ageTV, phnTV, genderTV;
    View rowView;
    ImageView stdIV;
    private Student[] stdList;

    public StdListAdapter(Context context, Student[] stdList) {
        super(context, -1, stdList);
        this.context = context;
        this.stdList = stdList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        rowView = inflater.inflate(R.layout.row_std_list, parent, false);

        stdIV = rowView.findViewById(R.id.stdIV);
        nameTV = rowView.findViewById(R.id.stdNameTV);
        fatherTV = rowView.findViewById(R.id.stdFatherTV);
        phnTV = rowView.findViewById(R.id.stdGuardianPhnTV);
        // ageTV = (TextView) rowView.findViewById(R.id.stdAgeTV);
        rollTV = rowView.findViewById(R.id.stdRollTV);

        //___________________Font___________________
        Typeface codexUnicornFont = Typeface.createFromAsset(A.getAppContext().getAssets(), "fonts/codexunicornfont.ttf");
        nameTV.setTypeface(codexUnicornFont);
        fatherTV.setTypeface(codexUnicornFont);
        phnTV.setTypeface(codexUnicornFont);
        // ageTV.setTypeface(codexUnicornFont);
        rollTV.setTypeface(codexUnicornFont);

        //___________________set data___________________

        nameTV.setText(H.toCamelCase(stdList[position].name));
        fatherTV.setText("Father: " + H.toCamelCase(stdList[position].father));
        phnTV.setText("Contact: " + stdList[position].guardianPhn);
        //ageTV.setText("Age: " + stdList[position].birthDate);
        rollTV.setText("Roll: " + stdList[position].roll);


        String gender = stdList[position].gender;
        if (gender.equals("M")) {
            stdIV.setImageResource(R.drawable.ic_std_male);
        } else {
            stdIV.setImageResource(R.drawable.ic_std_female);
        }

        return rowView;
    }
}