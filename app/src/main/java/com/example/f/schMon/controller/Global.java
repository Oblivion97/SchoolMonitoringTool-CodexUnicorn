/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;

import com.example.f.schMon.model.appInner.SQLiteDbHelper;
import com.example.f.schMon.model.appInner.SQLiteDbHelperNative;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.model.newAPI.UserModelList;
import com.example.f.schMon.model.oldAPI.UserModel;
import com.google.gson.Gson;

/**
 * Created by Mir Ferdous on 12/06/2017.
 */

public class Global {
    private static final String TAG = Global.class.getName();

    /*================================= (Global user case) =========================================================================================*/
    //various ids & variable for using across the app

    public static final String dbName = "schoolMonDB.db";
    private static int ver = 1;//app version
    public static String packageName = BuildConfig.APPLICATION_ID;

    //========================== user ===============================================================================================

    public static String user_id;
    public static String userName;
    public static String userFullName;
    public static String userPhone;
    public static String userGrade;
    public static String userAddress;
    public static String schID;


    //=========================== shared preference =====================================================================================
    public static String preference = "preference"; //this the shared preference name where all preference will be saved
    public static String userModel = "userModel";
    public static String userAndPass = "userAndPass";
    public static String versionCode = "versionCode";

    //last sync time saved string name in shared preference
    public static String timeAll = "timeAll";
    public static String timeQues = "timeReport";
    public static String timeReport = "timeReport";
    public static String timeReportOld = "timeReportOld";
    public static String timeSchTechStd = "timeSchTechStd";
    public static String timePay = "timePay";
    public static String paymentLastSyncTime;

    //for evaluation temporary data caching
    public static String evaluationPref = "evaluationPref";

    public static String apiBaseUrl = "apiBaseUrl";

    //========================= sync ================================================================================================

    public static String noNetErrorMsg = "Problem in Internet...";
    public static String synced = "1";
    public static String unsynced = "0";
    public static String syncID = "syncID";
    public static String btnFocus = "btnFocus";
    public static String syncRunningDontClose = "Sync is running.Don't close app or switch to other page.";
    public static String syncWhat = "syncWhat";
    public static String msgID = "msgID";
    public static String syncAll = "syncAll";
    public static String syncSchTechStd = "syncSchTechStd";
    public static String syncRepPost = "syncRepPost";
    public static String syncQues = "syncQues";
    public static String syncRepOld = "syncRepOld";
    public static String syncPay = "syncPay";
    public static String syncSuccessMsg = "Sync Completed";
    public static String syncFailedMsg = "Sync Failed.Try Again...";
    public static String syncInActivity = "syncInActivity";
    public static String syncInService = "syncInService";

    //============================ Activity open mode =============================================================================================

    public static String toOpenWhichActivity = "openedWhichActivity";

    //schoolSync list
    public static String SchoolProfile = "SchoolProfile";
    public static String Reporting = "Reporting";
    public static String Evaluation = "Evaluation";

    //opened from which activity (usually for keeping track of backstack)
    public static String openedFromWhichActivity = "openedFromWhichActivity";

    public static String openMode = "openMode";

    //rep admin & evaluation mode selection
    public static String NewMode = "NewMode";
    public static String EditMode = "EditMode";
    public static String EditRunMode = "EditRunMode";

    public static String DisplayMode = "DisplayMode";
    public static String RunMode = "RunMode";

    //info dialog
    public static String openWhat;

    public static String infoRep = "infoRep";
    public static String infoEvl = "infoEvl";

    public static String fromWhere;

    public static String LogOut = "LogOut";
    public static String StartOfApp = "StartOfApp";

    //submit btn
    public static String dateBtn = "dateBtn";

    //scroll position
    public static String scrollPosition = "scroll";

    //type of notifiaction to show
    public static String notifType = "notifType";
    public static String allNotif = "all";
    public static String timeRelatedNotif = "time";


/*================================= Methods =========================================================================================*/

    //Values from shared preference
    public static UserModel getUsersAllInfoFrom_pref(Context context) {
        UserModel userModel = null;
        SharedPreferences sharedpreferences = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        String json = sharedpreferences.getString(Global.userModel, null);
        Gson mGson = new Gson();
        userModel = mGson.fromJson(json, UserModel.class);
        return userModel;
    }

    public static boolean getUser_Info_from_pref_for_nav_drawer(Context context) {
        boolean flag = false;
        UserModel userModel = getUsersAllInfoFrom_pref(context);
        Global.userFullName = userModel.getFirstName() + " " + userModel.getMiddleName() + " " + userModel.getLastName();
        Global.userPhone = userModel.getMobileNumber();
        Global.userAddress = userModel.getAddress();
        Global.userGrade = userModel.getGradeName();
        if (userModel.getFirstName() == null && userModel.getMiddleName() == null && userModel.getLastName() == null)
            Global.userFullName = H.toCamelCase(Global.getUser_Name(context));
        if (Global.userPhone == null)
            Global.userPhone = "Not Available";
        if (Global.userAddress == null)
            Global.userAddress = "Not Available";
        if (Global.userGrade == null)
            Global.userGrade = "Not Available";

        flag = true;
        return flag;

    }


    @Nullable
    public static User getUserCredentialFromSharePreference(Context context) {
        User user = null;
        SharedPreferences sharedpreferences = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        String json = sharedpreferences.getString(Global.userAndPass, "");
        if (!json.equals("")) {
            Gson gson = new Gson();
            user = gson.fromJson(json, User.class);
        }
        return user;
    }

    public static String getUser_id(Context context) {
        UserModelList userModelList = null;
        String userID = null;
        SharedPreferences sharedpreferences = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);

        String json = sharedpreferences.getString(Global.userModel, "");
        if (!json.equals("")) {
            userModelList = new Gson().fromJson(json, UserModelList.class);
        }
        if (userModelList != null)
            userID = userModelList.getModel().getId();
        return userID;
    }

    @NonNull
    public static String getUser_Name(Context context) {
        String username = "";
        SharedPreferences sharedpreferences = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        User user = Global.getUserCredentialFromSharePreference(context);
        if (user != null)
            username = user.getUsername();
        return username;
    }


    //=========================== Database ==============================================================================================

    // get db of the app with SQLiteOpenHelper (native way)
    public static SQLiteDatabase getDB_with_nativeWay_readable(String dbName) {
        if (sqLiteDB == null)
            sqLiteDB = SQLiteDbHelperNative.getInstance(A.getAppContext(), dbName)
                    .getReadableDatabase();
        return sqLiteDB;
    }

    public static SQLiteDatabase getDB_with_nativeWay_writable(String dbName) {
        if (sqLiteDB == null)
            sqLiteDB = SQLiteDbHelperNative.getInstance(A.getAppContext(), dbName)
                    .getWritableDatabase();
        return sqLiteDB;
    }


    //________________ get db of the app with SQLiteAssetHelper Library_____________________
    private static SQLiteDatabase sqLiteDB;

    public static SQLiteDatabase getDB_with_AssetHelper_readable(String dbName) {
        if (sqLiteDB == null)
            sqLiteDB = SQLiteDbHelper.getInstance(A.getAppContext(), dbName, null, ver)
                    .getReadableDatabase();
        return sqLiteDB;
    }

    public static SQLiteDatabase getDB_with_AssetHelper__writable(String dbName) {
        if (sqLiteDB == null)
            sqLiteDB = SQLiteDbHelper.getInstance(A.getAppContext(), dbName, null, ver)
                    .getWritableDatabase();
        return sqLiteDB;
    }

    //=============================== First Run Check ==========================================================================================

    public static int checkFirstRun(Context context) {
        int flag = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        //  Toast.makeText(context,String.valueOf(currentVersionCode),Toast.LENGTH_LONG).show();
        // Get saved version code
        SharedPreferences prefs = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        int savedVersionCode = prefs.getInt("versionCode", -1);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            flag = 0;
            //return flag;

        } else if (savedVersionCode == -1) {

            // TODO This is a new install (or the user cleared the shared preferences)
            flag = 1;
        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
            flag = 2;
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt("versionCode", currentVersionCode).commit();
        return flag;

        /**
         * new install returns 1
         * upgrade app(update version) returns 2
         * normal usual run returns 0
         * default initialized value of flag returns -1
         * */
    }


}
