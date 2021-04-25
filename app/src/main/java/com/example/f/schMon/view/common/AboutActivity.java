/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.f.schMon.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {
    private Context context = AboutActivity.this;
    private static final String TAG = AboutActivity.class.getName();
    @BindView(R.id.appLogo)
    CircularImageView appLogo;
    @BindView(R.id.codexLogo)
    CircularImageView codexLogo;
    @BindView(R.id.mailLogoCodex)
    CircularImageView mailLogoCodex;
    @BindView(R.id.webLogoCodex)
    CircularImageView webLogoCodex;
    @BindView(R.id.facebookLogoCodex)
    CircularImageView fbLogoCodex;
    @BindView(R.id.projectleadContainer)
    View projectleadContainer;
    @BindView(R.id.hmContainer)
    View hmContainer;
    @BindView(R.id.mirContainer)
    View mirContainer;
    @BindView(R.id.appDescriptionTV)
    TextView appDescriptionTV;
    @BindView(R.id.bracathonDescriptionTV)
    TextView bracathonDescriptionTV;

    private String appDescription
            = "This application is made for monitoring the schools running by brac.in this app a user can collect data observe and find out the problem and progress of \n" +
            "repective school monitoring by himself.";
    private String bracathonDescription
            = "BRACathon is a platform for young coders to put their code into action by creating mobile applications for real problems.\n" +
            "based on this spectacular theme codex unicorn joined BRACATHON || with a team.and won the Hacathon as a primary winner.\n" +
            "Associates Brac and BEPMIS a Team was made for this Application.with the project Lead Role: Saiful Islam Palash\n" +
            "Project Co-ordinator: Kawser Amir;& two young Prohrammer: Mir Ferdous & HM Mahmudul Hasan";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        //appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  nav drawer
        NavDrawer.navDrawerBtnsClick(context, toolbar);
        appDescriptionTV.setText(appDescription);
        bracathonDescriptionTV.setText(bracathonDescription);

    }


    //========================== btn listner ===============================================================================================


    //========= app bracathon links================================================================================================================

    @OnClick(R.id.webLogoApp)
    void onClickwebLogoApp(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "http://bepmis.brac.net/login.html");
        startActivity(intent);
    }

    @OnClick(R.id.facebookLogoApp)
    void onClickfacebookLogoApp(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BRACathon/"));
        startActivity(browserIntent);
    }


    //========= codex links ================================================================================================================

    @OnClick(R.id.facebookLogoCodex)
    void onClickfacebookLogoCodex(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/codexunicorn/"));
        startActivity(browserIntent);
    }

    @OnClick(R.id.mailLogoCodex)
    void onClickmailLogoCodex(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"codexunicorn@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bracathon Codex App");
        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
        startActivity(Intent.createChooser(intent, ""));
    }

    @OnClick(R.id.webLogoCodex)
    void onClickwebLogoCodex(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "http://codexunicorn.pw");
        startActivity(intent);
    }


    //======= people's profiles ==================================================================================================================

    @OnClick(R.id.projectleadContainer)
    void onClickprojectleadContainer(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "https://www.linkedin.com/in/shaifulislampalash/");
        startActivity(intent);
    }

    @OnClick(R.id.coordinatorContainer)
    void onClickcoordinatorContainer(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "https://www.linkedin.com/in/kawseramir/");
        startActivity(intent);
    }

    @OnClick(R.id.hmContainer)
    void onClickhmContainer(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "https://www.linkedin.com/in/hm-mahmudul-hasan-hridoy-b08041111/");
        startActivity(intent);
    }

    @OnClick(R.id.mirContainer)
    void onClickmirContainer(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "https://www.linkedin.com/in/mirferdous/");
        startActivity(intent);
    }


    //================ bottom logos =========================================================================================================

    @OnClick(R.id.logoBrac)
    void onClicklogoBrac(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "http://www.brac.net/");
        startActivity(intent);

    }

    @OnClick(R.id.logoBracathon)
    void onClicklogoBracathon(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "http://bracathon.brac.net/");
        startActivity(intent);
    }

    @OnClick(R.id.logoBracEducation)
    void onClicklogoBracEducation(View v) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", "http://www.brac.net/education?view=page");
        startActivity(intent);
    }
}
