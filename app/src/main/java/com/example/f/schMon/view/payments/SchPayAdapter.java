/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.appInner.School;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mir Ferdous on 06/12/2017.
 */

public class SchPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = SchPayAdapter.class.getName();
    private Context context;
    private List<School> list;
    VH myViewHolder;

    public SchPayAdapter(Context context, List<School> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater LInflater = LayoutInflater.from(context);
        View row = LInflater.inflate(R.layout.row_fee_sch, parent, false);
        myViewHolder = new VH(row);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int i) {

        if (list.get(i).getPaid() != null) {
            ((VH) h).nameTV_fee.setText(list.get(i).getSchName());
            ((VH) h).codeTV_fee.setText("Code " + list.get(i).getSchCode());
            ((VH) h).gradeTV_fee.setText("Grade " + list.get(i).getGrade());
            String taka = list.get(i).getPaid() + " Tk of " + list.get(i).getTotal_bill() + " Tk";
            ((VH) h).taka_fee.setText(taka);
        }

        H.setFont(context, new View[]{((VH) h).nameTV_fee, ((VH) h).codeTV_fee
                , ((VH) h).gradeTV_fee, ((VH) h).taka_fee});

        ((VH) h).schContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StdPayActivity.class);
                intent.putExtra("schID", list.get(i).getSchID());
                intent.putExtra("gradeID", list.get(i).getGradeID());
                intent.putExtra("schName", list.get(i).getSchName());
                intent.putExtra("code", list.get(i).getSchCode());
                ((SchPayActivity) context).startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.schContainer)
        View schContainer;
        @BindView(R.id.nameTV_fee)
        TextView nameTV_fee;
        @BindView(R.id.taka_fee)
        TextView taka_fee;
        @BindView(R.id.img_fee_sch)
        ImageView img_fee_sch;
        @BindView(R.id.gradeTV_fee)
        TextView gradeTV_fee;
        @BindView(R.id.codeTV_fee)
        TextView codeTV_fee;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
