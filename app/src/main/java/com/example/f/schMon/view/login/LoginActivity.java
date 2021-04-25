/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f.schMon.R;
import com.example.f.schMon.controller.Global;
import com.example.f.schMon.controller.H;
import com.example.f.schMon.controller.webservice.WSManager;
import com.example.f.schMon.controller.webservice.WSResponseListener;
import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.appInner.DB;
import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.model.newAPI.UserModelList;
import com.example.f.schMon.view.dashboard.DashboardActivity;
import com.example.f.schMon.view.sync_mir.API;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity implements WSResponseListener {
    private final String TAG = LoginActivity.class.getName();
    private final Context context = LoginActivity.this;
    UserModelList mResponseModel;
    Snackbar snackbar;
    WSManager wsManager = new WSManager(context, this);
    private TextView title;
    private EditText edt_u;
    private EditText edt_p;
    private Button btn_login;
    private CheckBox chk_remember;
    private Intent intent;
    private String postJson, response = null;
    private boolean rememberMe = false;
    private Gson gson = new Gson();
    private ProgressDialog progressDialog;
    User userCreden;
    String fromWhere = "";


    public boolean deleteOldUserData(Context context, final String newUserName) {
        boolean flag = true;
        //this function will delete all offline user data when new user login
        String oldUserName = Global.getUser_Name(context);

        try {
            if (!newUserName.equals(oldUserName)) {
                //db delete
                SQLiteDatabase db = Global.getDB_with_nativeWay_writable(Global.dbName);
                DAO.deleteDataFromTable(db, DB.schools.table);
                DAO.deleteDataFromTable(db, DB.students.table);
                DAO.deleteDataFromTable(db, DB.teachers.table);
                DAO.deleteDataFromTable(db, DB.report_admin.table);
                DAO.deleteDataFromTable(db, DB.report_acad.table);
                DAO.deleteDataFromTable(db, DB.admin_ques.table);
                DAO.deleteDataFromTable(db, DB.acad_ques.table);
                DAO.deleteDataFromTable(db, DB.perform.table);
                DAO.deleteDataFromTable(db, DB.payments.table);
                DAO.deleteDataFromTable(db, DB.notif_bckup.table);

                //shared preference delete
                SharedPreferences sp = context.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                String blank = "";
                editor.putString(Global.timeAll, blank);
                editor.putString(Global.timeQues, blank);
                editor.putString(Global.timeSchTechStd, blank);
                editor.putString(Global.timeReportOld, blank);
                editor.putString(Global.timeReport, blank);

                Toasty.info(context, "New User Logged in").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       /* if (fromWhere.equals(Global.LogOut)) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra(Global.fromWhere, Global.LogOut);
            startActivity(intent);
            overridePendingTransition(0, R.anim.fadeout);
        }*/
    }

    //========================================================================================================
    protected void onCreate(@Nullable Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        title = findViewById(R.id.title);
        edt_u = findViewById(R.id.editTextUser);
        edt_p = findViewById(R.id.editTextPass);
        btn_login = findViewById(R.id.loginBtn);
        chk_remember = findViewById(R.id.chkBoxRemember);
        // ============ Design ==============
        H.setFont(context, new View[]{title, edt_u, edt_p, chk_remember, btn_login});


        fromWhere = getIntent().getStringExtra(Global.fromWhere);// logout


        //______________user name & pass inputted by default___________________
        SharedPreferences sharedpreferences = this.getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        User userInfo = new User(sharedpreferences.getString("userName", ""),
                sharedpreferences.getString("password", ""));
        edt_u.setText(userInfo.getUsername());
        edt_u.setSelection(edt_u.getText().length());//sets cursor to left of text

        edt_p.setText(userInfo.getPassword());
        edt_p.setSelection(edt_p.getText().length());//sets cursor to left of text

        chk_remember.setTag("mir");
        chk_remember.setTag("hridoy");
        if (chk_remember.getTag().equals("mir") || chk_remember.getTag().equals("hridoy")) {
            chk_remember.setChecked(true);
        }


        //test 1st install
       /* if (Global.checkFirstRun(context) == 1) {
            new AlertDialog.Builder(context).setMessage("this is first run")
                    .create().show();
        } else if (Global.checkFirstRun(context) == 0) {
            new AlertDialog.Builder(context).setMessage("this is not first run")
                    .create().show();

        }*/

    }


    @OnClick(R.id.loginBtn)
    public void onClickLogin(View view) {
        try {
            if (snackbar != null)
                snackbar.dismiss();
            userCreden = new User(edt_u.getText().toString(), edt_p.getText().toString());

            if (userCreden.getUsername().equals("") || userCreden.getPassword().equals("")) {
                Snackbar.make(btn_login, "Enter username and password", Snackbar.LENGTH_SHORT).show();
            } else {
                onlineLogin();
                if (!offlineLogin()) {
                    onlineLogin();
                }
            }
        } catch (Exception e) {
        }


    }

    private void startActivityDash() {
        intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();// destroys activity
    }

    private boolean offlineLogin() {
        boolean f = false;
        User user1 = Global.getUserCredentialFromSharePreference(context);

        if (user1 != null) {
            if (user1.getUsername().replaceAll("\\s+", "").equals(edt_u.getText().toString().replaceAll("\\s+", ""))
                    && user1.getPassword().replaceAll("\\s+", "").equals(edt_p.getText().toString().replaceAll("\\s+", ""))) {
                f = true;
                startActivityDash();
                //.replaceAll("\\s+","") removes any whitespace
            }
        }
        return f;
    }

    private void onlineLogin() throws UnsupportedEncodingException {
        if (H.isInternetConnected(context)) {
            postJson = gson.toJson(userCreden);
            StringEntity entity = new StringEntity(gson.toJson(userCreden));
            wsManager.post(API.getLogin(), entity);
        } else
            Toasty.error(context, Global.noNetErrorMsg, Toast.LENGTH_SHORT, true).show();
    }


    @Override
    public void success(String responseSave, String url) {
        try {
            if (url.equalsIgnoreCase(API.getLogin())) {
                mResponseModel = gson.fromJson(responseSave, UserModelList.class);
                Log.d(TAG, responseSave);
                if (mResponseModel != null) {
                    com.example.f.schMon.model.newAPI.UserModel userModel = mResponseModel.getModel();
                    //wrong user name & pass msg from server
                    if (mResponseModel.getRequestType() != null) {
                        if (mResponseModel.getRequestType().toString().equalsIgnoreCase("Invalid username or password")) {
                            snackbar = Snackbar.make(btn_login, "Wrong Username or Password", Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                }
                            });
                            snackbar.show();
                        }
                    }
                    //successful login
                    else if (mResponseModel.getSuccess().equalsIgnoreCase("1")) {

                        deleteOldUserData(context, edt_u.getText().toString());

                        /* save data */
                        saveUserNamePass(userCreden);
                        saveFullUserModelData(responseSave);

                        if (chk_remember.isChecked()) {
                            rememberMe(true, responseSave);
                        }
                        Global.getUser_Info_from_pref_for_nav_drawer(context);//user info for nav drawer
                        startActivityDash();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void failure(int statusCode, Throwable error, String content) {


        //------------------------- with snackbar -----------------------------
        Log.d(TAG, "failur statusCode: " + statusCode + "\ncontent: " + content);
        snackbar = Snackbar.make(btn_login, "Error in Server Connection", Snackbar.LENGTH_SHORT);
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();

        //------------------------- with toast -----------------------------
      /*  Toast toast = Toast.makeText(LoginActivity.this, "Error in Server Connection", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 440);
        toast.show();*/
    }

    public void rememberMe(boolean rememberMe, String responseSave) {
        SharedPreferences sharedpreferences = getSharedPreferences(Global.preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userName", userCreden.getUsername());
        editor.putString("password", userCreden.getPassword());
        editor.putBoolean("rememberMe", rememberMe);
        editor.putString("user_id", Global.user_id);
        editor.commit();
    }

    public boolean saveFullUserModelData(String responseSave) {
        try {
            /**full response save*/
            SharedPreferences.Editor editor = getSharedPreferences(Global.preference, Context.MODE_PRIVATE).edit();
            editor.putString(Global.userModel, responseSave);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private boolean saveUserNamePass(User user) {
        try {
            Gson gson = new Gson();
            /**full response save*/
            SharedPreferences.Editor editor = getSharedPreferences(Global.preference, Context.MODE_PRIVATE).edit();
            editor.putString(Global.userAndPass, gson.toJson(user));
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
