package com.example.f.schMon.controller.webservice;

public interface WSResponseListener {
    void success(String response, String url);

    void failure(int statusCode, Throwable error, String content);
}

