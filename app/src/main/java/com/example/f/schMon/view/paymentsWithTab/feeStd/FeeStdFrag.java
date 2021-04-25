/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab.feeStd;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.FeeSalaryModel;


public class FeeStdFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    FeeSalaryModel[] feeSalaryModels;
    private String TAG = this.getClass().getName();
    private String schID;
    private String schName;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    public FeeStdFrag() {
        // Required empty public constructor
    }


    public static FeeStdFrag newInstance(String param1, String param2) {
        FeeStdFrag fragment = new FeeStdFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fee_std_frag, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listview = getActivity().findViewById(R.id.listViewFee);
        Intent intent = getActivity().getIntent();
        schID = intent.getStringExtra("schID");
        schName = intent.getStringExtra("schName");

        // schID = TempDataLog.schID;
        feeSalaryModels = DAO.getStudentWiseFeeListFor_A_School(schID);
        /* FeeSalaryModel feeSalaryModel;
        int size = 20;
        int[] rand = FeeSalaryModel.randomNumArr(size, 1000);
        feeSalaryModelsSch = new FeeSalaryModel[size];

        for (int i = 0; i < size; i++) {
            feeSalaryModel = new FeeSalaryModel("", "School Name", "Student", String.valueOf(rand[i]), "1000", "");
            feeSalaryModelsSch[i] = feeSalaryModel;
        }*/
        FeeStdAdapter adapter = new FeeStdAdapter(getActivity(), feeSalaryModels);
        listview.setAdapter(adapter);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
