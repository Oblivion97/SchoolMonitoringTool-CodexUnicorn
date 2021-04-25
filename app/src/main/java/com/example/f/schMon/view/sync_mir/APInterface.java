/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import com.example.f.schMon.model.appInner.User;
import com.example.f.schMon.model.newAPI.SchMdlList;
import com.example.f.schMon.model.newAPI.SchMdl_Id_Res;
import com.example.f.schMon.model.newAPI.StdMdlList;
import com.example.f.schMon.model.newAPI.StdMdl_id_Res;
import com.example.f.schMon.model.newAPI.TchrMdl_id_Res;
import com.example.f.schMon.model.newAPI.TehMdlList;
import com.example.f.schMon.model.oldAPI.UserModel;
import com.example.f.schMon.view.payments.paymdl.PayMdl;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlA;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlAList;
import com.example.f.schMon.view.sync_mir.MdlSync.EvlQList;
import com.example.f.schMon.view.sync_mir.MdlSync.RepA;
import com.example.f.schMon.view.sync_mir.MdlSync.RepAList;
import com.example.f.schMon.view.sync_mir.MdlSync.RepQList;
import com.example.f.schMon.view.sync_mir.MdlSync.Respnse;
import com.example.f.schMon.view.sync_mir.MdlSync.TimeMdl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mir Ferdous on 10/22/2017.
 */

public interface APInterface {
    /*======== login ===========*/
    @POST("/rest/apps/login")
    Call<UserModel> login(@Body User user);

    @POST("/rest/apps/login")
    Call<UserModel> loginWithRawJson(@Body String json);

    /*======== schoolSync ===========*/
    @GET("/rest/apps/institute")
    Call<SchMdlList> getSchList(@Query("uname") String u, @Query("passw") String p, @Query("max") String max);


    @GET("/rest/apps/institute/{id}")
    Call<SchMdl_Id_Res> getSchByID(@Path("id") String schID, @Query("uname") String u, @Query("passw") String p);


    /*======== studentSync ===========*/
    @GET("/rest/apps/student/")
    Call<StdMdlList> getStdList(@Query("uname") String u, @Query("passw") String p, @Query("max") String max);


    @GET("/rest/apps/student/{id}")
    Call<StdMdl_id_Res> getStdByID(@Path("id") String stdID, @Query("uname") String u, @Query("passw") String p);


    /*======== teacherSync ===========*/
    @GET("/rest/apps/teacher/listAllTeacher")
    Call<TehMdlList> getTehrList(@Query("uname") String u, @Query("passw") String p, @Query("max") String max);


    @GET("/rest/apps/teacher/{id}")
    Call<TchrMdl_id_Res> getTehrByID(@Path("id") String tehrID, @Query("uname") String u, @Query("passw") String p);


    /*======== time ===========*/
    @GET("/rest/apps/server-time")
    Call<TimeMdl> getTimeFromServer(@Query("uname") String u, @Query("passw") String p);


    /*======== report ===========*/
    //ques
    @GET("/rest/apps/infrastructure-question/list")
    Call<RepQList> getRepQuesAll(@Query("uname") String u, @Query("passw") String p);

    @GET("/rest/apps/infrastructure-question/list")
    Call<RepQList> getRepQuesByGrade(@Query("uname") String u, @Query("passw") String p, @Query("gradeId") String gradeID);

    //past evl
    @GET("/rest/apps/infrastructure-report/list")
    Call<RepAList> getOldRepAll(@Query("uname") String u, @Query("passw") String p);

    @GET("/rest/apps/infrastructure-report/list")
    Call<RepAList> getOldRepAllByInstId(@Query("uname") String u, @Query("passw") String p,
                                        @Query("instituteId") String instID);


    //save
    @POST("/rest/apps/infrastructure-report/save")
    Call<Respnse> postReportList(@Query("uname") String u, @Query("passw") String p, @Body List<RepA> repAList);

    @POST("/rest/apps/infrastructure-report/save")
    Call<Respnse> saveRep(@Query("uname") String u, @Query("passw") String p, @Body RepA[] repAs);

    /*======== evaluation ===========*/
    //ques
    @GET("/rest/apps/academic-question/list")
    Call<EvlQList> getEvlQuesAll(@Query("uname") String u, @Query("passw") String p);

    @GET("/rest/apps/academic-question/list")
    Call<EvlQList> getEvlQuesByGrade(@Query("uname") String u, @Query("passw") String p, @Query("gradeId") String gradeID);

    //past evl
    @GET("/rest/apps/academic-report/list")
    Call<EvlAList> getOldEvlAll(@Query("uname") String u, @Query("passw") String p);

    @GET("/rest/apps/academic-report/list")
    Call<EvlAList> getOldEvlByInstId(@Query("uname") String u, @Query("passw") String p, @Query("instituteId") String instID);

    //save
    @POST("/rest/apps/academic-report/save")
    Call<Respnse> postEvaluationList(@Query("uname") String u, @Query("passw") String p, @Body List<EvlA> evlAList);

    @POST("/rest/apps/academic-report/save")
    Call<Respnse> saveEvl(@Query("uname") String u, @Query("passw") String p, @Body EvlA[] evlAs);


    /*==================== payment ============================*/
    @GET("/rest/apps/payment")
    Call<PayMdl> getPaymentData(@Query("uname") String u, @Query("passw") String p,
                                @Query("instituteId") String instituteId, @Query("gradeId") String gradeId,
                                @Query("year") String year);

}
