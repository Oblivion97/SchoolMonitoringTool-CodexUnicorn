/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.dashboard;

import android.util.Log;

import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.LastPerfMdl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mir Ferdous on 06/11/2017.
 */

public class Calc {
    private static final String TAG = Calc.class.getName();

    //========================== dash ==================================================
    public static List<Integer> avgPerformCalc(List<LastPerfMdl> lpm) {
        List<Integer> avgPerformanceList = new ArrayList<>();
        float attendanc = 0, evlalution = 0, report = 0, sumAttndce = 0, sumReport = 0, sumEvaluation = 0,
                attendancCount = 0, evaluationCount = 0, reportCount = 0;
        int lpmPerf;
        int totalSch = DAO.getTotalSchoolCount();
        Log.d(TAG, "Total Std: " + String.valueOf(totalSch));

        try {
            for (int i = 0; i < lpm.size(); i++) {

                if (lpm.get(i).getLastAttenPerform() != null) {
                    lpmPerf = (int) Float.parseFloat(lpm.get(i).getLastAttenPerform());
                    sumAttndce += Float.parseFloat(lpm.get(i).getLastAttenPerform());
                    attendancCount++;
                }

                if (lpm.get(i).getLastRepPerform() != null) {
                    lpmPerf = (int) Float.parseFloat(lpm.get(i).getLastRepPerform());
                    sumReport += Float.parseFloat(lpm.get(i).getLastRepPerform());
                    reportCount++;
                }
                if (lpm.get(i).getLastEvalPerform() != null) {
                    lpmPerf = (int) Float.parseFloat(lpm.get(i).getLastEvalPerform());
                    sumEvaluation += Float.parseFloat(lpm.get(i).getLastEvalPerform());
                    evaluationCount++;
                }
            }

            if (attendancCount > 0) {
                attendanc = sumAttndce / totalSch;
                Log.d(TAG, "Sum Attndece" + sumAttndce);
                // attendanc = sumAttndce / attendancCount;
            }
            if (evaluationCount > 0) {
                evlalution = sumEvaluation / totalSch;
                Log.d(TAG, "Sum Evalution" + sumEvaluation);
                // evlalution = sumEvaluation / evaluationCount;
            }
            if (reportCount > 0) {
                report = sumReport / totalSch;
                Log.d(TAG, "Sum Report: " + sumReport);
                // report = sumReport / reportCount;
            }
            avgPerformanceList.add(Math.round(attendanc));
            avgPerformanceList.add(Math.round(evlalution));
            avgPerformanceList.add(Math.round(report));
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
            //if exception ie divide by zero occurs returns 0 value for all 3 items
           /* if (e instanceof ArithmeticException) {*/

            /*avgPerformanceList.add(0);
            avgPerformanceList.add(0);
            avgPerformanceList.add(0);*/

            //}
        }

        return avgPerformanceList;
        /**index 0 = onClickAttndnc
         * index 1 = evaluation
         * index 2 = report administrative(environment)
         * */
    }
}