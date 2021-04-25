package com.example.f.schMon.controller.webservice;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.entity.StringEntity;

public class WSManager {
    public static final String WS_POST_JSON_CONTENT_TYPE = "application/json";
    private String TAG = this.getClass().getName();
    private WSResponseListener wsResponseListener;
    private Context context;
    private HttpClientManager wsm;

    public WSManager(Context _context, WSResponseListener _wsResponseListener) {
        context = _context;
        wsResponseListener = _wsResponseListener;
        wsm = HttpClientManager.getInstance();
    }

    public void post(final String url, StringEntity entity) {
        //client.addHeader(IConstant.WS_USERNAME, user);
        wsm.getClient().setTimeout(600000);
        wsm.getClient().post(context, url, entity, WS_POST_JSON_CONTENT_TYPE, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.v(TAG, "Post Called Successfully");
                wsResponseListener.success(response, url);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                wsResponseListener.failure(statusCode, error, content);
            }
        });
    }

    public void get(final String url) {
        wsm.getClient().setTimeout(600000);
        wsm.getClient().get(context, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                wsResponseListener.success(response, url);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                wsResponseListener.failure(statusCode, error, content);
            }
        });
    }
}
