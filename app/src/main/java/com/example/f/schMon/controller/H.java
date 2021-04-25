package com.example.f.schMon.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.DAO2;
import com.example.f.schMon.model.appInner.RepModel;
import com.example.f.schMon.model.appInner.School;
import com.example.f.schMon.view.common.InfoMdl;
import com.example.f.schMon.view.evaluation.EvalQuesModel;
import com.example.f.schMon.view.evaluation.EvlModelin;
import com.example.f.schMon.view.payments.paymdl.MonthlyPayModelList;
import com.example.f.schMon.view.payments.paymdl.PayMdl;
import com.example.f.schMon.view.payments.paymdl.PayMdlInner;
import com.example.f.schMon.view.payments.paymdl.StudentPayModelList;
import com.example.f.schMon.view.report_environment.RepMdlin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

/**
 * Created by Mir Ferdous on 02/06/2017.
 */

public class H {/*Helper class*/
    //this function will split model array from response json
    private static final String TAG = H.class.getName();

    public static String modelArrayFromJson(String json, String modelNameForSearch) {
        String res = null;
        int indx, indxArryEnd;
        indx = json.indexOf(modelNameForSearch) + modelNameForSearch.length() - 1;
        indxArryEnd = json.indexOf("]") + 1;
        res = json.substring(indx, indxArryEnd);
        return res;
    }

    public static boolean isHTMLresponse(String response) {
        /* || response.contains("</body>")
         || response.contains("<p>")*/
        return response.contains("</html>");
    }

//________________________ Internet Connection related_______________________________________________________________________________________________________________________

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static boolean isInternetConnected_withMsg(Context context) {
        boolean isConnected = isInternetConnected(context);
        if (!isConnected) {
            Toasty.error(context, "No Internet. Try Again");
        }
        return isConnected;
    }

//________________________Files Handling_______________________________________________________________________________________________________


    /**
     * get string from text file in asset
     */
    public static String getStringFrom_File_in_Assets(Context context, String filename) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        String mLine = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));


            // do reading, usually loop until end of file reading

            mLine = reader.readLine();

            while (mLine != null) {
                sb.append(mLine); // process line
                try {
                    mLine = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


//___________________________activity related____________________________________________________________________________________________________________________

    public static void restartActivity(Context context) {
        Activity activity = (Activity) context;
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            Intent intent = activity.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            activity.finish();
            activity.overridePendingTransition(0, 0);
            activity.startActivity(intent);
            activity.overridePendingTransition(0, 0);
        }

    }


    //__________________ String Related ________________________________
    public static String toCamelCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    /**
     * server returned students name is double spaced this function is for correcting that
     * to single space. for double space search function in studentlist activity doesn't works
     * properly.
     */
    public static String multipleSpacetoSingleSpace(String input) {
        StringBuilder res = new StringBuilder();
        String delims = "[ ]+";
        String[] tokens = input.split(delims);
        int n = tokens.length;
        for (int i = 0; i < n; i++) {
            res.append(tokens[i]);
            if (i < n - 1)
                res.append(" ");
        }

        return res.toString();
    }

    public static String underscoreToSpacedFormat(String in) {
        in = in.replaceAll("_", " ");
        in = H.toCamelCase(in);
        return in;
    }


    /*============================== Color ======================================================================================*/


    public static int setColorBasedOnPerformace(Context cntx, @NonNull String perform) {
        int color;
        int p = Integer.parseInt(perform);
        if (p >= 0 && p <= 20)
            color = ContextCompat.getColor(cntx, R.color.perf0to20);
        if (p >= 21 && p <= 40)
            color = ContextCompat.getColor(cntx, R.color.perf21to40);
        else if (p >= 41 && p <= 60)
            color = ContextCompat.getColor(cntx, R.color.perf41to60);
        else if (p >= 61 && p <= 80)
            color = ContextCompat.getColor(cntx, R.color.perf61to80);
        else
            color = ContextCompat.getColor(cntx, R.color.perf81to100);
        //color = Color.parseColor("#bdbdbd");
        return color;
    }

    public static int randomIntinRange(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }


    /*============================== Font ======================================================================================*/
    public static boolean setFont(Context cntx, View[] views) {
        boolean f = true;
        try {
            Typeface codexUnicornFont = Typeface.createFromAsset(cntx.getAssets(), "fonts/codexunicornfont.ttf");
            for (int i = 0; i < views.length; i++)
                if (views[i] instanceof TextView)
                    ((TextView) views[i]).setTypeface(codexUnicornFont);
                else if (views[i] instanceof Button)
                    ((Button) views[i]).setTypeface(codexUnicornFont);
                else if (views[i] instanceof EditText)
                    ((EditText) views[i]).setTypeface(codexUnicornFont);

        } catch (Exception e) {
            e.printStackTrace();
            f = false;
        } finally {
        }
        return f;
    }
    
    
    /*============================== Utility ======================================================================================*/

    public static void getAlertDialog(Context c, String msg) {
        new AlertDialog.Builder(c).setMessage(msg).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();

    }

    public static School getSchoolByIDFromList(List<School> list, String schID) {
        School schoolTemp = null;
        //  schoolTemp.removeNull();

        /** search a schoolSync by id from a list of schools*/
        for (School school : list) {
            if (school.getSchID().equals(schID)) {
                schoolTemp = school;
            }
        }
        return schoolTemp;
    }

    public static InfoMdl makeInfoMdlForInfoDialogFromEvlModelOrRepMdl(Object in, boolean sync, String schID) {
        InfoMdl info = null;
        School schInfo;
        if (in != null) {
            //admin report
            if (in instanceof RepMdlin) {
                schInfo = DAO.getInfoOfASchool(schID);
                info = new InfoMdl();
                info.setIcon(R.drawable.ic_report_info);
                info.setTitle("About Administrative Report");

                if (schInfo.getSchCode() != null)
                    info.setSchName("School: " + schInfo.getSchName() + " \nSchool Code: " + schInfo.getSchCode());
                else
                    info.setSchName("School: " + schInfo.getSchName());

                if (schInfo.getEdu_type() != null)
                    info.setEduType("Edu. Type: " + schInfo.getEdu_type());

                if (((RepMdlin) in).getDateReport() != null)
                    info.setDateRep("Date Reported: " + (H.dateFormat2(((RepMdlin) in).getDateReport())));

                if (sync) {
                    info.setSyncStatus("Sync Status: Synced");
                    info.setSyncIMG(R.drawable.ic_sync_complete);
                } else {
                    info.setSyncStatus("Sync Status: Unsynced");
                    info.setSyncIMG(R.drawable.ic_sync_incomplete);
                }

            }
            /*evaluation*/
            else if (in instanceof EvlModelin) {
                schInfo = DAO.getInfoOfASchool(schID);
                info = new InfoMdl();
                info.setIcon(R.drawable.ic_evl_info);
                info.setTitle("About Evaluation");

                if (schInfo.getSchCode() != null)
                    info.setSchName("School: " + schInfo.getSchName() + " \nSchool Code: " + schInfo.getSchCode());
                else
                    info.setSchName("School: " + schInfo.getSchName());

                if (schInfo.getEdu_type() != null)
                    info.setEduType("Edu. Type: " + schInfo.getEdu_type());

                if (((EvlModelin) in).getDate_report() != null)
                    info.setDateRep("Date Evaluated: " + (H.dateFormat2(((EvlModelin) in).getDate_report())));

                if (sync) {
                    info.setSyncStatus("Sync Status: Synced");
                    info.setSyncIMG(R.drawable.ic_sync_complete);
                } else {
                    info.setSyncStatus("Sync Status: Unsynced");
                    info.setSyncIMG(R.drawable.ic_sync_incomplete);
                }
            }
        }


        return info;
    }

    public static String booleanFieldVaueTypeForAColumn(String table, String column) {
        /** columns which saves boolean type of data . some fields in db are saved in 1/0 format some are in db in true/false
         * format such as report questions and evluation question active value from server is in true/false format
         * so in local db it is also in true/false format*/
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        String out = null;
        String temp;
        Cursor c = null;

        String[] proj = new String[]{column};
        c = db.query(table, proj, null, null, null, null, null);
        c.moveToFirst();
        temp = c.getString(c.getColumnIndex(column));

        if (temp.equalsIgnoreCase("true") || temp.equalsIgnoreCase("false"))
            out = "true/false";
        else if (temp.equalsIgnoreCase("1") || temp.equalsIgnoreCase("0"))
            out = "1/0";
        return out;
    }

    public static String generateUniqueID(String id_for_What_clue) {
        String ts = String.valueOf(System.currentTimeMillis());
        String rand = UUID.randomUUID().toString();
        String id = id_for_What_clue + rand + ts;
     /*if (id_for_What_clue != null)
            id = DigestUtils.sha1Hex(ts + id_for_What_clue + rand);
        else id = DigestUtils.sha1Hex(ts + rand);*/
        return id;
    }

    //__________________________________date__________________________________________


    public static List<String> getUniqueDateFromRepModel(List<RepMdlin> repMdlins) {
        List<String> dateList = new ArrayList<>();
        int n = repMdlins.size();
        for (int i = 0; i < n; i++) {
            String date = repMdlins.get(i).getDateReport();
            if (!dateList.contains(date)) {
                dateList.add(date);
            }
        }
        return dateList;
    }

    public static List<String> getUniqueDateFromATable(String table, String dateColumnName) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> dateList = new ArrayList<>();
        String x;
        Cursor c = null;

        try {
            c = db.query(table, new String[]{dateColumnName}, null, null, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        x = c.getString(c.getColumnIndex(dateColumnName));
                        if (!dateList.contains(x))
                            dateList.add(x);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }

        return dateList;
    }

    public static List<String> getUniqueDateFromATableForASch(String schID, String table, String dateColumnName, String schIDColumn) {
        SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
        List<String> dateList = new ArrayList<>();
        String date;
        Cursor c = null;

        try {
            String sel = schIDColumn + " = ? ";
            String[] selArg = new String[]{schID};
            c = db.query(table, new String[]{schIDColumn, dateColumnName}, sel, selArg, null, null, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        date = c.getString(c.getColumnIndex(dateColumnName));
                        if (!dateList.contains(date))
                            dateList.add(date);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null)
                c.close();
        }

        return dateList;
    }

    public static List<RepMdlin> getOneRepAdminForAschool(List<RepMdlin> listAll, String schID, String date) {
        /** this will return one reportModel(all checklist for a day for corresponding schoolSync) for a schoolSync*/
        List<RepMdlin> list = new ArrayList<>();
        for (RepMdlin rep : listAll) {
            if (rep != null) {
                if (rep.getSchID().equals(schID) && rep.getDateReport().equals(date)) {
                    list.add(rep);
                }
            }
        }
        return list;
    }


    public static RepModel from_RepModelin_to_RepModel(RepMdlin repMdlin) {
        return new RepModel(
                repMdlin.getServerIDQues(),
                repMdlin.getQuestionTitle(),
                repMdlin.getQuestion(),
                repMdlin.getAnswer(),
                repMdlin.getAnswerWeight(),
                repMdlin.getDetails(),
                repMdlin.getPriority(),
                repMdlin.getServerQuestion(),
                repMdlin.getDateReport(),
                repMdlin.getSynced(),
                repMdlin.getTimeLm()
        );
    }

    public static RepMdlin fromRepModeltoRepModelin(RepModel repModel) {
        RepMdlin repMdlin = new RepMdlin();

        repMdlin.setServerIDQues(repModel.getQuestionID());
        repMdlin.setQuesTitle(repModel.getQuestionTitle());
        repMdlin.setQues(repModel.getQuestion());
        repMdlin.setAns(repModel.getAnswer());
        repMdlin.setAnsWeight(repModel.getAnswerWeight());
        repMdlin.setDetails(repModel.getDetails());
        repMdlin.setPriority(repModel.getPriority());
        repMdlin.setServerQuestion(repModel.getServerQuestion());
        repMdlin.setDateReport(repModel.getDateReport());
        repMdlin.setSynced(repModel.getSynced());
        repMdlin.setTimeLm(repModel.getTimeLm());

        return repMdlin;
    }


    public static List<String> lastDates(List<String> allDates, int noOfLastDates) {
        /** last n no of dates from a list of date.  */
        List<String> lastDates = new ArrayList<>();

        Comparator<String> dateStringComparator = new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("dd-MM-yyyy");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        };


        Collections.sort(allDates, Collections.reverseOrder(dateStringComparator));//descending order


        if (allDates.size() >= noOfLastDates) {
            for (int i = 0; i < noOfLastDates; i++) {
                lastDates.add(allDates.get(i));
            }
        } else {
            for (int i = 0; i < allDates.size(); i++) {
                lastDates.add(allDates.get(i));
            }
        }


        return lastDates;
    }


    public static List<Boolean> allRepAdminAnsweredOrNot() {
        List<Boolean> booleanList = new ArrayList<>();

        return booleanList;
    }


    public static boolean isReportOfThisSchoolSynced(List<RepMdlin> list/*report list of a perticular schoolSync*/) {
        /** if a repModel(1 question) is synced then full list of repModels for that schoolSync will be counted as synced*/
        boolean f = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSynced() == null) {
                f = false;
            } else {
                if (list.get(i).getSynced().equals("0"))
                    f = false;
            }
        }
        return f;
    }

    public static int[] getPreviousMonth() {
        int previousMonth = 1, year;
        int[] monthAndYear = new int[2];
        /*
        Date date = new Date(System.currentTimeMillis());
        long time = date.getTime();
        time = time - 2592000000L; // 2592000000 millisecond =30 day
        date = new Date(time);
        previousMonth = date.getMonth();
        */
        Calendar calendar = Calendar.getInstance();
        // Log.d(TAG, calendar.toString());
        int currentMonth = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        previousMonth = currentMonth - 1;
        if (previousMonth == 0) {
            previousMonth = 12;
            year = year - 1;
        }
        monthAndYear[0] = previousMonth;
        monthAndYear[1] = year;
        return monthAndYear;
    }
    //=========================== evaluation ======================================================================================

    public static String evaluationPerfromCalc(String askedStdNo, String answeredCorrectStdNo) {
        /**evaluation performance calculation
         Attendance-25=100%
         Attempt-15=60%
         Asked-5=33.3%
         Answered correctly-3=60%
         * */
        float res = 0;
        String result;
        try {
            float asked = Float.parseFloat(askedStdNo);
            float answeredCorrect = Float.parseFloat(answeredCorrectStdNo);
            res = (answeredCorrect / asked) * 100;
        } catch (Exception e) {
            Log.d(TAG, "Calc Exception", e);
        }
        return String.valueOf(res);
    }

    public static List<EvlModelin> fromEvalQuesModelToEvlModelin(List<EvalQuesModel> quesList) {
        List<EvlModelin> evlModelins = new ArrayList<>();
        EvlModelin evlModelin;
        EvalQuesModel evalQuesModel;
        int n = quesList.size();
        for (int i = 0; i < n; i++) {
            evalQuesModel = quesList.get(i);
            evlModelin = new EvlModelin(
                    evalQuesModel.getServer_id(),
                    evalQuesModel.getQues(),
                    evalQuesModel.getAnswer(),
                    evalQuesModel.getActive(),
                    evalQuesModel.getSynced(),
                    evalQuesModel.getServerQuestion(),
                    evalQuesModel.getGrade(),
                    evalQuesModel.getTimeLm()
            );
            evlModelins.add(evlModelin);
        }
        return evlModelins;
    }


    public static List<EvalQuesModel> fromEvlModelinToEvalQuesModel(List<EvlModelin> evlModelinList) {
        List<EvalQuesModel> evalQuesList = new ArrayList<>();
        EvlModelin evlModelin;
        EvalQuesModel evalQuesModel;

        int n = evlModelinList.size();
        for (int i = 0; i < n; i++) {
            evlModelin = evlModelinList.get(i);
            evalQuesModel = new EvalQuesModel(
                    evlModelin.getServerIDQues(),
                    evlModelin.getQues(),
                    evlModelin.getAnswer(),
                    evlModelin.getActive(),
                    evlModelin.getSynced(),
                    evlModelin.getServer_ques(),
                    evlModelin.getGrade(),
                    evlModelin.getTime_lm()
            );
            evlModelinList.add(evlModelin);
        }
        return evalQuesList;
    }

    public static List<EvlModelin> getAnsweredQuesFromEvlList(List<EvlModelin> all) {
        List<EvlModelin> ansList = new ArrayList<>();
        int n = all.size();
        for (int i = 0; i < n; i++) {
            if (all.get(i).getCorrect() != null || !all.get(i).getCorrect().equals("")) {
                ansList.add(all.get(i));
            }
        }
        return ansList;
    }


    //for edit which questions has not been answered

    public static List<EvlModelin> quesNotAnsweredPreviousEvaluation(List<EvlModelin> allQues, List<EvlModelin> answereds) {
        // done
        List<EvlModelin> notAnswered = new ArrayList<>();
        EvlModelin ans;

        for (int i = 0; i < answereds.size(); i++) {
            ans = answereds.get(i);
            for (int j = 0; j < allQues.size(); j++) {
                if (allQues.get(j).equalQuestion(ans)) {
                    allQues.remove(j);
                }
            }


            notAnswered = allQues;
        }

        return notAnswered;
    }


    public static List<EvlModelin> allQuesWithPrevAnswers(List<EvlModelin> allQues, List<EvlModelin> answered) {
        //done
        List<EvlModelin> notAnswered;
        notAnswered = H.quesNotAnsweredPreviousEvaluation(allQues, answered);
        answered.addAll(notAnswered);
        List<EvlModelin> allQuesWithPrevAns = answered;
        return allQuesWithPrevAns;
    }

    public static boolean isEvaluationOfThisSchoolSynced(List<EvlModelin> list) {
        /** if a repModel(1 question) is synced then full list of repModels for that schoolSync will be counted as synced*/
        boolean f = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSynced() == null) {
                f = false;
            } else {
                if (list.get(i).getSynced().equals("0"))
                    f = false;
            }
        }
        return f;
    }

    public static void setQuesNoForAnsActivity(List<EvlModelin> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setQuesNo(i + 1);
        }
    }

    public static EvlModelin formatEvlDataToShowAns(EvlModelin e) {
        if (e.getAsked() != null && e.getCorrect() != null) {
            e.setImgID(R.drawable.right_green);
            e.setAskedExtra("Asked: " + e.getAsked());
            e.setCorrectExtra("Correctly Answered: " + e.getCorrect());
        }
        return e;
    }

    public static List<EvlModelin> formatEvlDataToShowAnsInList(List<EvlModelin> list) {
        List<EvlModelin> formattedList = new ArrayList<>();
        formattedList.addAll(list);

        for (EvlModelin e : formattedList) {
            if (e.getAsked() != null && e.getCorrect() != null) {
                e.setImgID(R.drawable.right_green);
                e.setAskedExtra("Asked: " + e.getAsked());
                e.setCorrectExtra("Correctly Answered: " + e.getCorrect());
            }
        }

        return formattedList;
    }

    public static String calcEvlPerfOfASchADate(String schID, String date) {
        String perfAvg = "0";
        List<EvlModelin> evlModelinList = DAO2.getEvaluationOfASchoolForASpeacificDate(schID, date);
        perfAvg = H.calcEvlPerfOfAEvalReport(evlModelinList);
        return perfAvg;
    }

    public static String calcEvlPerfOfAEvalReport(List<EvlModelin> evlModelinList) {
        String perfAvg = "0";
        float sum = 0, avg = 0, temp;
        for (EvlModelin e : evlModelinList) {
            if (e.getPerf() != null)
                sum += Float.parseFloat(e.getPerf());
        }
        if (evlModelinList.size() > 0) {
            avg = sum / evlModelinList.size();
            perfAvg = String.valueOf((int) avg);
        }
        return perfAvg;
    }

    //====================================== report ==================================================================================
    public static String calcRepPerformForASch(List<RepMdlin> l) {
        /**calculation of Report performance of a schoolSync based on all problems of this schoolSync*/
        String res = "";
        RepMdlin r;
        float perf = 0;
        float sum = 0, avg = 0;
        try {
            for (int i = 0; i < l.size(); i++) {
                r = l.get(i);
                if (r.getAnswer().equals("1"))
                    perf = 100;
                else {
                    if (r.getPriority().equals("1"))
                        perf = 0;
                    else if (r.getPriority().equals("2"))
                        perf = 50;
                    else if (r.getPriority().equals("3"))
                        perf = 75;
                }
                sum += perf;
            }
            avg = sum / l.size();
            res = String.valueOf((int) avg);
        } catch (Exception e) {
            Log.d(TAG, "Number Exception", e);
        }
        return res;
    }

    public static String problemPriorityNumToText(RepMdlin repMdlin) {
        String x = "Not Available";
        if (repMdlin != null) {
            if (repMdlin.getPriority() != null) {
                if (repMdlin.getPriority().equals("1"))
                    x = "High";
                else if (repMdlin.getPriority().equals("2"))
                    x = "Medium";
                else if (repMdlin.getPriority().equals("3"))
                    x = "Low";
            }
        }

        return x;
    }

    public static int problemPriorityColor(Context c, RepMdlin repMdlin) {
        int x = ContextCompat.getColor(c, R.color.notifTxtDark);
        ;


        if (repMdlin != null) {
            if (repMdlin.getPriority() != null) {
                if (repMdlin.getPriority().equals("1"))
                    x = ContextCompat.getColor(c, R.color.red);
                else if (repMdlin.getPriority().equals("2"))
                    x = ContextCompat.getColor(c, R.color.yellow);
                else if (repMdlin.getPriority().equals("3"))
                    x = ContextCompat.getColor(c, R.color.green);
            }
        }

        return x;
    }

    public static void setQuesNoForDialogFrag(List<RepMdlin> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setQuesNo(i + 1);
        }
    }


    //for edit (run time list change)which questions has not been answered

    public static List<RepMdlin> questionNotAnsweredPreviouslyRep(List<RepMdlin> allQues, List<RepMdlin> answereds) {
        // done
        List<RepMdlin> notAnswered = new ArrayList<>();
        RepMdlin ans, all;

        for (int i = 0; i < answereds.size(); i++) {
            ans = answereds.get(i);


            for (int j = 0; j < allQues.size(); j++) {
                if (allQues.get(j).equalQuestion(ans)) {
                    allQues.remove(j);
                }
            }


            notAnswered = allQues;
        }

        return notAnswered;
    }

    public static RepMdlin formatReportDataToShowInAns(Context context, RepMdlin data) {
        if (data.getAnswer() != null) {
            if (data.getAnswer().equals("1")) {
                data.setImgID(R.drawable.right_green);
                data.setStatusExtra("Status: No Problem.");
                data.setPriorityExtra("");
                data.setDetailsExtra("");
            } else if (data.getAnswer().equals("0")) {
                data.setImgID(R.drawable.ic_cross);
                data.setStatusExtra("Status: Problem Exists.");
                data.setPriorityExtra("Problem Priority: " + H.problemPriorityNumToText(data));
                data.setPriorityColorExtra(H.problemPriorityColor(context, data));
                data.setDetailsExtra("Problem Details: " + data.getDetails());
                if (data.getDateTarget() == null)
                    data.setDateTargetExtra("");
                else if (data.getDateTarget().equals(""))
                    data.setDateTargetExtra("");
                else
                    data.setDateTargetExtra("Target Date to Solve: " + H.dateFormat2(data.getDateTarget()));
            }
        }
        return data;
    }

    public static List<RepMdlin> formatReportDataToShowInAnsList(Context context, List<RepMdlin> list) {
        List<RepMdlin> formattedList = new ArrayList<>();
        formattedList.addAll(list);

        for (RepMdlin data : formattedList) {
            if (data.getAnswer() != null) {
                if (data.getAnswer().equals("1")) {
                    data.setImgID(R.drawable.right_green);
                    data.setStatusExtra("Status: No Problem.");
                    data.setPriorityExtra("");
                    data.setDetailsExtra("");
                } else if (data.getAnswer().equals("0")) {
                    data.setImgID(R.drawable.ic_cross);
                    data.setStatusExtra("Status: Problem Exists.");
                    data.setPriorityExtra("Problem Priority: " + H.problemPriorityNumToText(data));
                    data.setPriorityColorExtra(H.problemPriorityColor(context, data));
                    data.setDetailsExtra("Problem Details: " + data.getDetails());
                    if (data.getDateTarget() == null)
                        data.setDateTargetExtra("");
                    else if (data.getDateTarget().equals(""))
                        data.setDateTargetExtra("");
                    else
                        data.setDateTargetExtra("Target Date to Solve: " + H.dateFormat2(data.getDateTarget()));

                }
            }
        }

        return formattedList;
    }

    //================================== date format ==================================================================================

    public static String dateFormat1(@Nullable String in) {
        /**"dd MMM yyyy" to dd-MM-yyyy*/
        if (in == null) return null;
        String out;
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date = df2.parse(in);
            out = df1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            out = in;
        }

        return out;
    }

    public static String dateFormat2(@Nullable String in) {
        /** dd-MM-yyyy to "dd MMM yyyy"*/
        if (in == null) return null;
        String out;

        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy");
        try {
            Date date = df1.parse(in);
            out = df2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            out = in;
        }

        return out;
    }

    public static String dateFormat3(@Nullable String in) {
        /** yyyy-MM-dd to "dd-MM-yyyy"*/
        if (in == null) return null;
        String out;

        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = df1.parse(in);
            out = df2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            out = in;
        }

        return out;
    }


    public static String df1DateFromYearMonthDay(int year, int month, int date) {
        String out = null;
        Calendar c = Calendar.getInstance();
        c.set(year, month, date);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date(c.getTimeInMillis());
        out = df1.format(d);
        return out;
    }


    public static String currentDate_in_dateFormat1() {
        String date = null;
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        date = df1.format(new Date(System.currentTimeMillis()));
        return date;
    }

    public static String currentDate_in_dateFormat2() {
        String date = null;
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        date = df2.format(new Date(System.currentTimeMillis()));
        return date;
    }

    public static boolean isDateInputted(String date) {
        if (date != null) {
            if (!date.equals(""))
                return true;
            else return false;
        } else
            return false;
    }


    //================================= download & upload size ========================================================================================
    public static long byteToKB(long bytes) {
        return bytes / 1000;
    }

    public static long byteToMB(long bytes) {
        return bytes / 1000000;
    }

    public static long byteToGB(long bytes) {
        return bytes / 1000000000;
    }

    public static long KBToMB(long KB) {
        return KB / 1000;
    }


    public static String inProperFormatBytesToKB_or_MB(long bytes) {
        String dataCost = null;
        if (bytes < 1000)
            dataCost = String.valueOf(bytes) + " byte";
        else if (bytes >= 1000 && bytes < 1000000)
            dataCost = String.valueOf(byteToKB(bytes)) + " KB";
        else if (bytes >= 1000000 && bytes < 1000000000)
            dataCost = String.valueOf(byteToMB(bytes)) + " MB";
        else if (bytes >= 1000000000)
            dataCost = String.valueOf(byteToGB(bytes)) + " GB";
        return dataCost;
    }


    //========================= payment ================================================================================================
    public static String getTimeOfLastPaymentSync(Context context) {
        String time;
        SharedPreferences sp = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        time = sp.getString(Global.paymentLastSyncTime, "");
        return time;
    }

    public static void saveTimeOfLastPaymentSync(Context context, String time/*format
    "dd-MM-yyyy hh:mm:ss" */) {
        SharedPreferences sp = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Global.paymentLastSyncTime, time);
        editor.commit();
    }

    public static boolean isPaymentSyncedThisMonth(Context context) {
        boolean flag = false;
        try {
            SimpleDateFormat df5 = new SimpleDateFormat("MM");
            SimpleDateFormat df4 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            String lastSyncTime = H.getTimeOfLastPaymentSync(context);
            if (lastSyncTime == null)
                return flag;
            else if (lastSyncTime.equals(""))
                return flag;


            Date lastSyncDate = df4.parse(lastSyncTime);

            String lastSyncMonth = df5.format(lastSyncDate);

            String currentMonth = df5.format(new Date(System.currentTimeMillis()));

            if (lastSyncMonth.equals(currentMonth))
                flag = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static List<PayMdlInner> PayMdl_to_PayMdlInner(PayMdl payMdl, String schID, String gradeID, String year) {  // payMdl to PayMdlInner
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        List<PayMdlInner> payMdlInnerList = new ArrayList<>();
        PayMdlInner payMdlInner;
        String schName, stdID, stdName, roll, type, category, month, payable, paid, due, time_lm;
        time_lm = df1.format(new Date(System.currentTimeMillis()));
        schName = payMdl.getSchoolName();
        List<StudentPayModelList> studentPayModelList = payMdl.getStudentPayModelList();

        for (StudentPayModelList list : studentPayModelList) {
            stdID = list.getStudentId();
            stdName = list.getStudentName();
            roll = list.getRoll();
            List<MonthlyPayModelList> monthlyPayModelLists = list.getMonthlyPayModelList();
            for (MonthlyPayModelList l : monthlyPayModelLists) {
                type = l.getType();
                category = l.getCategory();
                month = l.getMonth();
                payable = l.getPayable();
                paid = l.getPaid();
                due = l.getDue();

                payMdlInner = new PayMdlInner(schID, gradeID, schName, stdID, stdName,
                        roll, payable, paid, month, year, category, type, time_lm);
                payMdlInnerList.add(payMdlInner);
            }

        }
        return payMdlInnerList;
    }


    @Nullable
    public static String floatToZeroDecimalPlace(String inp) {
        /*if any exception occurs original input will be returned*/
        String out = inp;
        try {
            float temp = Float.parseFloat(inp);
            out = String.format("%.0f", temp);
        } catch (NumberFormatException e) {
            Log.d(TAG, "Exception", e);
        }
        return out;
    }
    //================================== ==================================================================================

    public static String print(List<RepMdlin> objects) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < objects.size(); i++) {
            sb.append(objects.get(i).print());
        }


        return sb.toString();

    }


}

