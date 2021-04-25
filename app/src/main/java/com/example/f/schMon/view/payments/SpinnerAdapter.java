/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;

import java.util.List;

/**
 * Created by Mir Ferdous on 08/12/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<MonthMdl> {

    LayoutInflater flater;

    public SpinnerAdapter(Activity context, int resouceId, int textviewId, List<MonthMdl> list) {

        super(context, resouceId, textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        MonthMdl monthMdl = getItem(position);

        viewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.row_spinner_month, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.title);
            holder.imageView = (ImageView) rowview.findViewById(R.id.icon);
            rowview.setTag(holder);
        } else {
            holder = (viewHolder) rowview.getTag();
        }
        holder.imageView.setImageResource(monthMdl.getId());
        holder.txtTitle.setText(monthMdl.getMonthName());

        return rowview;
    }

    private class viewHolder {
        TextView txtTitle;
        ImageView imageView;
    }
}