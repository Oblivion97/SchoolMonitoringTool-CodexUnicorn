/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.log_edit.logEval;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.evaluation.EvalQuesModel;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public class LogEvalFrag extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String TAG = LogEvalFrag.class.getName();
    List<List<EvlModelin>> listList = new ArrayList<>();//list of repModelinList of all schools;
    List<School> schoolList = new ArrayList<>();

    private String schID;
    private String schName;

    LogEvalAdapter adapter;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;


    @BindView(R.id.logListView)
    ListView listview;

    private SQLiteDatabase db;
    private int n, m;
    private String dateLastRep;
    School school;
    private List<School> schList;
    private List<RepMdlin> repMdlins = new ArrayList<>();
    private List<RepMdlin> repModelinsSches = new ArrayList<>();
    private List<List<RepMdlin>> list = new ArrayList<>();
    private List<String> dateList = new ArrayList<>();
    Unbinder unbinder;
    List<EvalQuesModel> quesList;
    List<EvlModelin> evlModelins;

    @BindView(R.id.smoothPrg)
    SmoothProgressBar pb;

    List<Integer> syncImgList = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = Global.getDB_with_nativeWay_writable(Global.dbName);

        //================= set data ==========================
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                schoolList = DAO.getAllInfoOfAllSchool();
                listList = DAO2.getLastEvlModelinAllSchool();

                setSyncImg(listList);
                adapter = new LogEvalAdapter(getActivity(), listList, schoolList,syncImgList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                pb.progressiveStop();
                pb.setVisibility(View.GONE);
                listview.setAdapter(adapter);
            }
        }.execute();

        // new AlertDialog.Builder(getContext()).setMessage(listList.toString()).create().show();
    }

    private void setSyncImg(List<List<EvlModelin>> listList) {
        for (List<EvlModelin> temp : listList) {
            if (H.isEvaluationOfThisSchoolSynced(temp)) {
                syncImgList.add(R.drawable.ic_sync_complete);
            } else syncImgList.add(R.drawable.ic_sync_incomplete);
        }
    }

    public LogEvalFrag() {
        // Required empty public constructor
    }


    public static LogEvalFrag newInstance(String param1, String param2) {
        LogEvalFrag fragment = new LogEvalFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.activity_log, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
