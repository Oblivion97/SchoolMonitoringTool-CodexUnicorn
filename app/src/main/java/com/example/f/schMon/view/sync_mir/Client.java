/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import com.example.f.schMon.controller.A;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mir Ferdous on 10/22/2017.
 */

public class Client {
    //==================================================
    public static APInterface getAPInterface() {
        return Client.getClient().create(APInterface.class);
    }


    //=================Client making=================================
    private static Retrofit retrofit = null;
    private static String BASE_URL = API.baseUrl;

    public static Retrofit getClient() {
        //interceptor for debugging
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //interceptor for data cost
        //InterceptorForDataCost InterceptorForDataCost = new InterceptorForDataCost();
        OkHttpClient client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                //.addInterceptor(httpLoggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);

        client = builder.build();  //with interceptor


        // client = new OkHttpClient.Builder().build();        //without interceptor

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    /*.addConverterFactory(ScalarsConverterFactory.create())*//*for raw json post as string*/
                    .addConverterFactory(GsonConverterFactory.create())
                    //.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())) //it ignores null
                   /* .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))*/ //adds also nulls
                    .client(client)
                    .build();
        }
        return retrofit;
    }


    static class InterceptorForDataCost implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();///
            Response response = chain.proceed(request);
            // for request size
            long requestLength = request.body().contentLength();
            A.setUploadSize(requestLength);
            // for response size
            long responseLength = response.body().contentLength();
            A.setDownloadSize(responseLength);
            return response;
        }
    }
}