/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab.salary;

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

public class SalaryFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String TAG = SalaryFrag.class.getName();
    FeeSalaryModel[] feeSalaryModels;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SalaryFrag() {
        // Required empty public constructor
    }


    public static SalaryFrag newInstance(String param1, String param2) {
        SalaryFrag fragment = new SalaryFrag();
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
        return inflater.inflate(R.layout.salary_frag, container, false);
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listview = getActivity().findViewById(R.id.listViewSalary);

        feeSalaryModels = DAO.getTeachersSalaryList();
        if (feeSalaryModels == null) {

        } else {

            SalaryAdapter adapter = new SalaryAdapter(getActivity(), feeSalaryModels);
            listview.setAdapter(adapter);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and namePer
        void onFragmentInteraction(Uri uri);
    }
}
