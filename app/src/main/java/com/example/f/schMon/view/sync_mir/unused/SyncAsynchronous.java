/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir.unused;

import android.content.Context;

import com.example.f.schMon.model.DAO;
import com.example.f.schMon.model.newAPI.SchMdl;
import com.example.f.schMon.model.newAPI.SchMdlList;
import com.example.f.schMon.model.newAPI.SchMdl_Id;
import com.example.f.schMon.model.newAPI.SchMdl_Id_Res;
import com.example.f.schMon.model.newAPI.StdMdl;
import com.example.f.schMon.model.newAPI.StdMdlList;
import com.example.f.schMon.model.newAPI.StdMdl_id;
import com.example.f.schMon.model.newAPI.StdMdl_id_Res;
import com.example.f.schMon.model.newAPI.TchrMdl;
import com.example.f.schMon.model.newAPI.TchrMdl_id;
import com.example.f.schMon.model.newAPI.TchrMdl_id_Res;
import com.example.f.schMon.model.newAPI.TehMdlList;
import com.example.f.schMon.controller.A;
import com.example.f.schMon.view.sync_mir.APInterface;
import com.example.f.schMon.view.sync_mir.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mir Ferdous on 10/22/2017.
 */

public class SyncAsynchronous {
    private static final String TAG = SyncAsynchronous.class.getName();
    private Context context;
    APInterface api = Client.getAPInterface();
    long bytes = 0;
    /*sync helper*/
    public String maxStd, maxSch, maxTchr;//total no of models is max parameter of get list query
    String s;

    List<SchMdl> schMdls;
    List<TchrMdl> tchrMdls;
    List<StdMdl> stdMdls;

    public synchronized long dataCost(long count) {
        bytes = bytes + count;
        return bytes;
    }


    public void syncAllAsync() {
        schoolSyncAsync();
        teacherSyncAsync();
        studentSyncAsync();
    }


    //======================== schoolSync ================================================================================


    /*=========sch basics==================*/
    public void schoolSyncAsync() {
        api.getSchList(A.getU(), A.getP(), "0").enqueue(new Callback<SchMdlList>() {
            @Override
            public void onResponse(Call<SchMdlList> call, Response<SchMdlList> response) {
                dataCost(response.body().toString().length());
                A.calcDwnload(response.body().toString().length());
                if (response.body().getSuccess().equalsIgnoreCase("1")) {
                    maxSch = response.body().getTotal();
                    api.getSchList(A.getU(), A.getP(), maxSch).enqueue(new Callback<SchMdlList>() {
                        @Override
                        public void onResponse(Call<SchMdlList> call, Response<SchMdlList> response) {
                            dataCost(response.body().toString().length());
                            A.calcDwnload(response.body().toString().length());
                            SchMdlList t = response.body();
                            if (t.getSuccess().equalsIgnoreCase("1")) {
                                DAO.insertSchool_ListCall(t.getModel());
                                schoolDetails(t.getModel());
                            }
                        }

                        @Override
                        public void onFailure(Call<SchMdlList> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SchMdlList> call, Throwable t) {

            }
        });
    }

    /*=========sch details==================*/
    public void schoolDetails(List<SchMdl> schMdls) {
        for (int i = 0; i < schMdls.size(); i++) {
            api.getSchByID(schMdls.get(i).getId(), A.getU(), A.getP()).enqueue(new Callback<SchMdl_Id_Res>() {
                @Override
                public void onResponse(Call<SchMdl_Id_Res> call, Response<SchMdl_Id_Res> response) {
                    dataCost(response.body().toString().length());
                    A.calcDwnload(response.body().toString().length());
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        SchMdl_Id t = response.body().getModel();
                        DAO.insertASchool_idCall(t.getId(), t.getPoId(), t.getLocationId(),t.getCurrentGradeId());
                    }
                }

                @Override
                public void onFailure(Call<SchMdl_Id_Res> call, Throwable t) {

                }
            });
        }


    }

    //======================== teacherSync ===============================================================================

    /*========= teacherSync basics==================*/
    public void teacherSyncAsync() {
        api.getTehrList(A.getU(), A.getP(), "0").enqueue(new Callback<TehMdlList>() {
            @Override
            public void onResponse(Call<TehMdlList> call, Response<TehMdlList> response) {
                dataCost(response.body().toString().length());
                A.calcDwnload(response.body().toString().length());
                if (response.body().getSuccess().equalsIgnoreCase("1"))
                    maxTchr = response.body().getTotal();
                api.getTehrList(A.getU(), A.getP(), maxTchr).enqueue(new Callback<TehMdlList>() {
                    @Override
                    public void onResponse(Call<TehMdlList> call, Response<TehMdlList> response) {
                        dataCost(response.body().toString().length());
                        A.calcDwnload(response.body().toString().length());
                        // H.getAlertDialog(context, String.valueOf(response.body().toString()));
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            DAO.insertTeacher_ListCall(response.body().getModel());
                            teacherDetails(response.body().getModel());
                        }

                    }

                    @Override
                    public void onFailure(Call<TehMdlList> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<TehMdlList> call, Throwable t) {

            }
        });
    }

    /*=========teacherSync details==================*/
    public void teacherDetails(List<TchrMdl> tchrMdls) {
        for (int i = 0; i < tchrMdls.size(); i++) {
            api.getTehrByID(tchrMdls.get(i).getId(), A.getU(), A.getP()).enqueue(new Callback<TchrMdl_id_Res>() {
                @Override
                public void onResponse(Call<TchrMdl_id_Res> call, Response<TchrMdl_id_Res> response) {
                    dataCost(response.body().toString().length());
                    A.calcDwnload(response.body().toString().length());
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        if (response.body().getModel() != null) {
                            TchrMdl_id t = response.body().getModel();
                            DAO.insertATeacher_idCall(t.getId(), t.getInstituteId(), t.getPresentAddress(),
                            t.getGender(),t.getEducation());
                        }
                    }
                }

                @Override
                public void onFailure(Call<TchrMdl_id_Res> call, Throwable t) {

                }
            });
        }
    }

    //======================== stduents ==============================================================================

    public void studentSyncAsync() {
        api.getStdList(A.getU(), A.getP(), "0").enqueue(new Callback<StdMdlList>() {
            @Override
            public void onResponse(Call<StdMdlList> call, Response<StdMdlList> response) {
                dataCost(response.body().toString().length());
                A.calcDwnload(response.body().toString().length());
                maxStd = response.body().getTotal();
                api.getStdList(A.getU(), A.getP(), maxStd).enqueue(new Callback<StdMdlList>() {
                    @Override
                    public void onResponse(Call<StdMdlList> call, Response<StdMdlList> response) {
                        dataCost(response.body().toString().length());
                        A.calcDwnload(response.body().toString().length());
                        List<StdMdl> t = response.body().getModel();
                        DAO.insertStudent_ListCall(t);
                        studentDetails(t);
                    }

                    @Override
                    public void onFailure(Call<StdMdlList> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<StdMdlList> call, Throwable t) {

            }
        });
    }

    public void studentDetails(List<StdMdl> stdMdls) {
        for (int i = 0; i < stdMdls.size(); i++) {
            api.getStdByID(stdMdls.get(i).getId(), A.getU(), A.getP()).enqueue(new Callback<StdMdl_id_Res>() {
                @Override
                public void onResponse(Call<StdMdl_id_Res> call, Response<StdMdl_id_Res> response) {
                    dataCost(response.body().toString().length());
                    A.calcDwnload(response.body().toString().length());
                    if (response.body().getModel() != null) {
                        StdMdl_id t = response.body().getModel();
                        DAO.insertAStudent_idCall(t.getId(), t.getInstituteId(), t.getRoll(), t.getTransferredSchoolId(),
                                t.getDateOfBirth(), t.getMotherName(),t.getGradeId());
                    }
                }

                @Override
                public void onFailure(Call<StdMdl_id_Res> call, Throwable t) {

                }
            });
        }

    }

    //================== constructor =================================================================================


    public SyncAsynchronous(Context context) {
        this.context = context;
    }

    //================================================================================================================
    public String printTotal() {
        StringBuffer sb = new StringBuffer();
        sb.append("Total Schools: " + maxSch);
        sb.append("Total Teachers: " + maxTchr);
        sb.append("Total Students: " + maxStd);
        return sb.toString();
    }

}
