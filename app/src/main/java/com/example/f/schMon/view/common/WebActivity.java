/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.f.schMon.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class WebActivity extends AppCompatActivity {

    // @BindView(R.id.web)
    WebView mWebview;

    String url;
    ProgressDialog progressDialog;
    @BindView(R.id.smoothPrg)
    SmoothProgressBar progressBar;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        url = intent.getStringExtra("url");

        mWebview = new WebView(WebActivity.this);
        mWebview.getSettings().setJavaScriptEnabled(true);

        final Activity activity = WebActivity.this;

        mWebview.setWebViewClient(new AppWebViewClients(progressBar));


        mWebview.loadUrl(url);

        setContentView(mWebview);


    }


    public class AppWebViewClients extends WebViewClient {
        private ProgressBar progressBar;

        public AppWebViewClients(ProgressBar progressBar) {
            this.progressBar = progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(WebActivity.this, description, Toast.LENGTH_SHORT).show();
        }
    }
}
