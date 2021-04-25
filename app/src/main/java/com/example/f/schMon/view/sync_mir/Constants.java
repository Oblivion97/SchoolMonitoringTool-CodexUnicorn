/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

/**
 * Created by Mir Ferdous on 03/11/2017.
 */

public class Constants {


    public interface ACTION {
        public static String MAIN_ACTION = "com.example.f.schMon.view.syncMir.foregroundservice.action.main";
        public static String STARTFOREGROUND_ACTION = "com.example.f.schMon.view.syncMir.foregroundservice.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.example.f.schMon.view.syncMir.foregroundservice.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}