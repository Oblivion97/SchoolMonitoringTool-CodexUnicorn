/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.controller.webservice;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by palash on 10/24/2016.
 */
public class HttpClientManager {
    private static HttpClientManager wsm = null;
    private final AsyncHttpClient client;
    private String TAG = this.getClass().getName();

    private HttpClientManager() {
        client = new AsyncHttpClient();
    }

    public static HttpClientManager getInstance() {
        if (wsm == null) {
            wsm = new HttpClientManager();
        }
        return wsm;
    }

    public AsyncHttpClient getClient() {
        return client;
    }

}
