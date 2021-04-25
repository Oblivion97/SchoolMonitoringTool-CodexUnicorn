/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

import com.example.f.schMon.view.evaluation.EvalQuesModel;
import com.example.f.schMon.model.appInner.RepModel;

import org.apache.commons.collections4.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Mir Ferdous on 27/08/2017.
 */

public class DataPump {
    public static List<RepModel> getReportModelList() {
        List<RepModel> repModelList = new ArrayList<>();
        RepModel repModel = new RepModel();

        repModel.setQuestionID("1");
        repModel.setQuestion("Is School Door Ok?");
        repModel.setServerQuestion("1");
        repModelList.add(repModel);

        repModel = new RepModel();
        repModel.setQuestionID("2");
        repModel.setQuestion("Is schoolSync Tubewell Working well?");
        repModel.setServerQuestion("1");
        repModelList.add(repModel);

        repModel = new RepModel();
        repModel.setQuestionID("3");
        repModel.setQuestion("is School Sanitation Clean and good enough to use?");
        repModel.setServerQuestion("1");
        repModelList.add(repModel);

        repModel = new RepModel();
        repModel.setQuestionID("4");
        repModel.setQuestion("Schools Meterials Available?");
        repModel.setServerQuestion("1");
        repModelList.add(repModel);

        repModel = new RepModel();
        repModel.setQuestionID("5");
        repModel.setQuestion("Teacher Taking class properly?");
        repModel.setServerQuestion("1");
        repModelList.add(repModel);


        repModel = new RepModel();
        repModel.setQuestionID("6");
        repModel.setQuestion("Is Window Ok?");
        repModel.setServerQuestion("1");
        repModelList.add(repModel);


        return repModelList;
    }

    public static List<EvalQuesModel> getReportAcadModelList() {
        List<EvalQuesModel> repAcadModelList = new ArrayList<>();
        EvalQuesModel evalQuesModel;

        evalQuesModel = new EvalQuesModel();
        evalQuesModel.setServer_id("1");
        evalQuesModel.setQues("What is name of ur Country?");
        evalQuesModel.setAnswer("Bangladesh forms the largest and easternmost part of the Bengal region.[12] Bangladeshis include people from a range of ethnic groups and religions. Bengalis, who speak the official Bengali language, make up 98% of the population.[2][3] The politically dominant Bengali Muslims make the nation the world's third largest Muslim-majority country. Most of Bangladesh is covered by the Bengal delta, the largest delta on Earth. The country has 700 rivers and 8,046 km (5,000 miles) of inland waterways. Highlands with evergreen forests are found in the northeastern and southeastern regions of the country. Bangladesh has many islands and a coral reef. The longest unbroken sea beach, Cox's Bazar Beach is located here. It is home to the Sundarbans, the largest mangrove forest in the world. The country's biodiversity includes a " +
                "vast array of plant and wildlife, including critically endangered Bengal tigers, the national animal.");
        evalQuesModel.setSynced("0");
        evalQuesModel.setDateAsking("01-01-2017");

        for (int i = 0; i < 10; i++) {

            repAcadModelList.add(evalQuesModel);
        }

        return repAcadModelList;
    }


    public static List<String> getListOfDates() {
        int y = 700;
        List<String> list = new ArrayList<>(y);


        for (int i = 0; i < y; i++) {


            GregorianCalendar gc = new GregorianCalendar();

            int year = randBetween(1900, 2010);

            gc.set(gc.YEAR, year);

            int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

            gc.set(gc.DAY_OF_YEAR, dayOfYear);

            String x = gc.get(gc.DAY_OF_MONTH) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.YEAR);
            SimpleDateFormat f = new SimpleDateFormat("d-M-yyyy");
            Date d = new Date();
            try {
                d = f.parse(x);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            x = new SimpleDateFormat("dd-MM-yyyy").format(d);


            list.add(x);

        }


        return list;
    }


    public static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }


    public static List<String> test() {
        List<String> all = new ArrayList<>();
        List<String> answed = new ArrayList<>();
        List<String> unanswed = new ArrayList<>();

        //===============set data=========================================
        for (int i = 0; i < 50; i++) {
            String x = "ques" + String.valueOf(i + 1);
            all.add(x);
        }

        for (int i = 0; i < 30; i++) {
            String x = "ques" + String.valueOf(i + 1);
            answed.add(x);
        }

//================ logic =======================
        /*for (int i = 0; i < answed.size(); i++) {
            String t = answed.get(i);
            if (all.contains(t))
                all.remove(t);
        }*/


        all.removeAll(answed);



        unanswed = (List<String>) CollectionUtils.subtract(all, answed);

        return all;
    }
}
