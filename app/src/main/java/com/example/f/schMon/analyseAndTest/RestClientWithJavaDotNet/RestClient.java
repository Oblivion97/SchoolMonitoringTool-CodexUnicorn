/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.RestClientWithJavaDotNet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestClient {

    private static final String TAG = RestClient.class.getSimpleName();


    // ======================== Constructors ===================
    public RestClient() {

    }

    public static String POSTJson(String query, String json) {
        String result = null;
        try {
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            //insert json to post
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            result = org.apache.commons.io.IOUtils.toString(in, "UTF-8");


            in.close();
            conn.disconnect();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String GETJson(String reqUrl) {
        String response = null;

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertInputStreamToString(in);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            System.out.println("ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return response;
    }

    private static String convertInputStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}