/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.analyseAndTest.syncIntent;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by Jisan on 10/31/2017.
 */

public class DownloadResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public DownloadResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}
