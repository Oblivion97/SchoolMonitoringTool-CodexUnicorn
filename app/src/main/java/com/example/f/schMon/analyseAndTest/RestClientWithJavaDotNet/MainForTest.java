package com.example.f.schMon.analyseAndTest.RestClientWithJavaDotNet;

/*
 * Copyright (c) Mir Ferdous
 */
public class MainForTest {
    public static void main(String[] args) {
        String url1, url2, url3;

        url1 = "http://localhost:3000/say/hello";   //rails
        url2 = "http://localhost/user.php"; //php
        url3 = "http://api.androidhive.info/contacts/";
        String url4 = "http://www.studytrails.com/java/json/java-google-json-introduction/";
        String s = new RestClient().GETJson(url3);


        System.out.println(s);

    }
}
