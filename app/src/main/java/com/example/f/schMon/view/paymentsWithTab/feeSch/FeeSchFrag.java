/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.paymentsWithTab.feeSch;

import com.example.f.schMon.view.paymentsWithTab.*;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.FeeSalaryModel;


public class FeeSchFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String TAG = FeeSchFrag.class.getName();
    FeeSalaryModel[] feeSalaryModels;
    private String schID;
    private String schName;

    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    public FeeSchFrag() {
        // Required empty public constructor
    }


    public static FeeSchFrag newInstance(String param1, String param2) {
        FeeSchFrag fragment = new FeeSchFrag();
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
        return inflater.inflate(R.layout.fee_sch_frag, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listview = getActivity().findViewById(R.id.listViewFee);


        feeSalaryModels = DAO.getSchoolWiseFeeList(               );

        if (feeSalaryModels == null) {
            /*new AlertDialog.Builder(getActivity())
                    .setMessage("Payment Related Information is not Available").create().show();*/
        } else {
            FeeSchAdapter adapter = new FeeSchAdapter(getActivity(), feeSalaryModels);
            listview.setAdapter(adapter);
            //____________________start studentSync fee activity_________________________________
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast toast = Toast.makeText(getActivity(), "School" + (position + 1) + "selected!", Toast.LENGTH_SHORT);
                toast.show();*/
                    //Log.d(TAG, "row_fee_sch" + position + "selected!");

                    Intent intent = new Intent(getActivity(), PaymentActivityTabStd.class);
                    schID = feeSalaryModels[position].schID;
                    schName = feeSalaryModels[position].schName;

                    TempDataPayment.schID = schID;
                    TempDataPayment.schNameForTitle = schName;
                    intent.putExtra("schID", schID);
                    intent.putExtra("schName", schName);
                    startActivity(intent);
                }
            });
        }
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
