/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.payments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.view.payments.paymdl.PayMdlInner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mir Ferdous on 06/12/2017.
 */

public class StdPayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = StdPayAdapter.class.getName();
    private Context context;
    private List<PayMdlInner> list;
    VH myViewHolder;

    public StdPayAdapter(Context context, List<PayMdlInner> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater LInflater = LayoutInflater.from(context);
        View row = LInflater.inflate(R.layout.row_fee_std, parent, false);
        myViewHolder = new StdPayAdapter.VH(row);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder h, final int i) {
        ((VH) h).nameTV_fee.setText(H.toCamelCase(list.get(i).getStd_name()));
        ((VH) h).rollTV_fee.setText("Roll: " + list.get(i).getRoll());
        String taka = list.get(i).getPaid() + " Tk of " + list.get(i).getPayable() + " Tk";
        ((VH) h).taka_fee.setText(taka);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.stdContainer)
        View schContainer;
        @BindView(R.id.nameTV_fee)
        TextView nameTV_fee;
        @BindView(R.id.rollTV_fee)
        TextView rollTV_fee;
        @BindView(R.id.taka_fee)
        TextView taka_fee;

        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            H.setFont(context, new View[]{schContainer, nameTV_fee, rollTV_fee, taka_fee});
        }
    }
}
