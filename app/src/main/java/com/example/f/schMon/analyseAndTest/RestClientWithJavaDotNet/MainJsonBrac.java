package com.example.f.schMon.analyseAndTest.RestClientWithJavaDotNet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * Copyright (c) Mir Ferdous
 */
public class MainJsonBrac {

    static String query = "http://bepmis.brac.net/rest/auth/login";
    static String json = "{\n" +
            "  \"username\": \"admin\",\n" +
            "  \"password\": \"@dmin\"\n" +
            "}";


    public static void main(String[] args) {
        Gson gson = new Gson();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();


        String response = RestClient.POSTJson(query, json);
        System.out.println(response);

    }
}
