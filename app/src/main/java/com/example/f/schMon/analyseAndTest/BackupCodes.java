/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest;

/**
 * Created by Mir Ferdous on 01/09/2017.
 */

public class BackupCodes {
    /*class RestAPICallThread extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            response = RestClient.POSTJson(API.urlLgin, postJson);
//            Log.d("mir", response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if (response == null || response.equals(API.garbagResponse)) {
                Toast toast = Toast.makeText(LoginActivity.this, "Wrong Username or Password", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 440);
                toast.show();
            } else {
                if (chk_remember.isChecked()) {
                    rememberMe(true);
                }
                intent = new Intent(LoginActivity.this, TestActivity0.class);
                startActivity(intent);
                finish();// destroys activity
            }
        }
    }*/


    //===================student sync old===============================
    /*


    //_____________________details_______________________________

    public void callServerBasics(int pageNo, int pageSize) {
        if (PAGE_NO == 0) {
            progressDialog = new ProgressDialog(context);
            progressDialog.getWindow().setBackgroundDrawableResource(R.color.colorCream);
            progressDialog.setMessage("Students' Basic Info Loading From Server");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(total_beneficiary_in_server);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();
        } else {
            progressDialog.setMax(total_beneficiary_in_server);
            progressDialog.setProgress(totalBasics_downloaded);
        }
        wsManagerBasics.get("http://bepmis.brac.net/rest/studentSync?max=" + pageSize + "&start=" + pageNo);
    }

    public void callServerDetails(String id) {
        if (totalDetails_download == 0) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Students' Details Info Loading From Server");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(totalStd);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.getWindow().setBackgroundDrawableResource(R.color.colorCream);
            progressDialog.show();
        } else {
            progressDialog.setMax(totalStd);
            progressDialog.setProgress(totalDetails_download);
        }
        wsManagerDetails.get("http://bepmis.brac.net/rest/studentSync/" + id);
    }






     */










    /*   WSManager wsManagerDetails = new WSManager(context, new WSResponseListener() {
        @Override
        public void success(String response, String url) {
            Log.d(TAG, "json : " + response);
            resModel = mGson.fromJson(response, StudentModeI_id_respnse.class);


            if (resModel != null && resModel.getSuccess().equalsIgnoreCase("1")
                    && totalDetails_download < totalStd
                    //&&  resModel.getModel().size() > 0 && resModel.getRequestType().equalsIgnoreCase("REQUEST_TYPE_DELETE")
                    ) {

        studentModel_id = resModel.getModel();
        DAO.insertStudentbyIdDetails(context, db, studentModel_id);
        totalDetails_download++;
        try {
            callServerDetails(idList.get(totalDetails_download));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            closeProgressDialog();
            H.restartActivity(context);
        }
    } else {
        closeProgressDialog();
        H.restartActivity(context);
    }
}

    @Override
    public void failure(int statusCode, Throwable error, String content) {

    }
});
        //basic sync
        WSManager wsManagerBasics = new WSManager(context, new WSResponseListener() {

@Override
public void success(String response, String url) {
        try {
        responseModel = mGson.fromJson(response, StudentListModel.class);
        total_beneficiary_in_server = (Integer.parseInt(responseModel.getTotal()));
        if (responseModel != null && responseModel.getSuccess().equalsIgnoreCase("1") && responseModel.getModel().size() > 0) {
        studentModels.addAll(responseModel.getModel());
        DAO.insertStudentbyListBasics(context.getApplicationContext(), db, studentModels);
        PAGE_NO += 1;
        totalBasics_downloaded += responseModel.getModel().size();
        //Log.d(TAG, studentModels.toString());
        DAO.insertStudentbyListBasics(context, db, studentModels);
        callServerBasics(PAGE_NO, 10);
        } else {
        closeProgressDialog();
        //details call from id api
        idList = (ArrayList<String>) DAO.all_values_from_a_Column(db, "students", "std_id");
        totalStd = idList.size();
        callServerDetails(idList.get(0));
        }
        } catch (Exception e) {
        e.printStackTrace();
        } finally {

        }
        }

@Override
public void failure(int statusCode, Throwable error, String content) {
        // Toast.makeText(context, "Internet or Server Connection Error", Toast.LENGTH_SHORT).show();
        }
        });*/
}
