/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.model.appInner;

import android.provider.BaseColumns;

/**
 * Created by Mir Ferdous on 02/08/2017.
 */

public class DB {
    /**
     * this is database contract class
     * in it DB schema is saved
     */

    public static String schools_table_create = "CREATE TABLE `schools` (\n" +
            "\t`_id`\tTEXT,\n" +
            "\t`schID`\tTEXT,\n" +
            "\t`schName`\tTEXT,\n" +
            "\t`poID`\tTEXT,\n" +
            "\t`poName`\tTEXT,\n" +
            "\t`s_address`\tTEXT,\n" +
            "\t`schCode`\tTEXT,\n" +
            "\t`totalStd`\tTEXT,\n" +
            "\t`edu_type`\tTEXT,\n" +
            "\t`time_lm`\tTEXT,\n" +
            "\t`total_bill`\tTEXT,\n" +
            "\t`paid`\tTEXT\n" +
            ");";


    private DB() {
    }

    public static class schools implements BaseColumns {
        public static final String table = "schools";
        public static final String s_id = "s_id";
        public static final String s_name = "s_name";
        public static final String po_id = "po_id";
        public static final String po_name = "po_name";
        public static final String s_address = "s_address";
        public static final String totalStd = "no_std";
        public static final String s_code = "s_code";
        public static final String grade = "grade";
        public static final String edu_type = "edu_type";
        public static final String grade_id = "grade_id";
        public static final String time_lm = "time_lm";
        public static final String total_bill = "total_bill";
        public static final String paid = "paid";
        public static final String session_start = "session_start";
        public static final String session_end = "session_end";
        public static final String last_visit_time = "last_visit_time";

    }

    public static class students implements BaseColumns {
        public static final String table = "students";
        public static final String std_id = "std_id";
        public static final String s_id = "s_id";
        public static final String roll = "roll_no";
        public static final String name = "name";
        public static final String grade = "grade";
        public static final String grade_id = "grade_id";
        public static final String institute = "institute";
        public static final String gender = "gender";
        public static final String waiver = "waiver";
        public static final String father = "father";
        public static final String mother = "mother";
        public static final String guardian_phn = "guardian_phn";
        public static final String bkash = "bkash";
        public static final String dropout = "dropout";
        public static final String transferred_institute = "transferred_institute";
        public static final String birthDate = "birth_date";
        public static final String total_bill = "total_bill";
        public static final String paid = "paid";
        public static final String time_lm = "time_lm";
    }

    public static class teachers implements BaseColumns {
        public static final String table = "teachers";

        public static final String t_id = "t_id";
        public static final String name = "name";
        public static final String s_id = "s_id";
        public static final String grade = "grade";
        public static final String s_name = "s_name";
        public static final String mobile = "mobile";
        public static final String address = "address";
        public static final String total_bill = "total_bill";
        public static final String paid = "paid";
        public static final String gender = "gender";
        public static final String time_lm = "time_lm";
        public static final String education = "education";
    }

    /*_________________ Reporting environment ______________________________________*/

    /**
     * details administrative report (checklist)
     */

    public static class admin_ques implements BaseColumns {
        public static final String table = "admin_ques";

        public static final String sch_id = "sch_id";
        public static final String server_id = "server_id";
        public static final String local_id = "local_id";
        public static final String category_id = "ques_category_id";
        public static final String category = "category";
        public static final String quesTitle = "questionTitle";
        public static final String ques = "question";
        public static final String active = "active";
        public static final String serverQuestion = "serverQuestion";
        public static final String synced = "synced";
        public static final String time_lm = "time_lm";
    }

    public static class report_admin implements BaseColumns {
        public static final String table = "report_admin";

        public static final String sch_id = "sch_id";
        public static final String server_id_ques = "server_id_ques";
        public static final String local_id_ques = "local_id_ques";
        public static final String server_id_answer = "server_id_answer";
        public static final String local_id_answer = "local_id_answer";
        public static final String ques_category_id = "ques_category_id";
        public static final String category = "category";
        public static final String questionTitle = "questionTitle";
        public static final String question = "question";
        public static final String answer = "answer";
        public static final String priority = "priority";
        public static final String details = "details";
        public static final String serverQuestion = "serverQuestion";
        public static final String answerWeight = "answerWeight";
        public static final String date_report = "date_report";
        public static final String date_target = "date_target";
        public static final String date_solve = "date_solve";
        public static final String synced = "synced";
        public static final String time_lm = "time_lm";
        public static final String notify = "notify";
        public static final String notify_pause = "notify_pause";// when no clicked in notification activity for showing this notif later
        public static final String notify_his = "notify_his";
        public static final String n_update_date = "n_date_update";
        public static final String grade = "grade";
        public static final String grade_id = "grade_id";
    }

    public static class admin_perform implements BaseColumns {
        /*deleted from db*/
        public static final String table = "admin_perform";
        public static final String sch_id = "sch_id";
        public static final String sch_name = "sch_name";
        public static final String adminPerformRatio = "adminPerformRatio";
        public static final String synced = "synced";
        public static final String date_report = "date_report";
        public static final String date_update = "date_update";
        public static final String time_lm = "time_lm";
        public static final String totalQues = "totalQues";
    }




    /*__________________ Evaluation _________________________________*/

    /**
     * evaluation questions data will be synced(1 way) from this table
     */
    public static class acad_ques implements BaseColumns {
        public static final String table = "acad_ques";

        public static final String server_id = "server_id";
        public static final String local_id = "local_id";
        public static final String category_id = "category_id";
        public static final String category = "category";
        public static final String ques = "ques";
        public static final String answer = "answer";
        public static final String active = "active";
        public static final String classs = "class";
        public static final String synced = "synced";
        public static final String time_lm = "time_lm";
        public static final String server_question = "server_ques";
        public static final String date_asking = "date_asking";
        public static final String date_range_asking = "date_range_asking";
        public static final String grade = "grade";
        public static final String grde_id = "grade_id";
        public static final String subject = "subject";
        public static final String chapter = "chapter";
    }

    public static class report_acad implements BaseColumns {
        public static final String table = "report_acad";

        public static final String schID = "schID";
        public static final String schName = "schName";
        public static final String server_id_ques = "server_id_ques";
        public static final String local_id_ques = "local_id_ques";
        public static final String server_id_answer = "server_id_answer";
        public static final String local_id_answer = "local_id_answer";
        public static final String ques = "ques";
        public static final String answer = "answer";
        public static final String active = "active";
        public static final String synced = "synced";
        public static final String server_ques = "server_ques";
        public static final String grade = "grade";
        public static final String grade_id = "grade_id";
        public static final String subject = "subject";
        public static final String chapter = "chapter";
        public static final String date_report = "date_report";
        public static final String date_update = "date_update";
        public static final String time_lm = "time_lm";
        public static final String totalStd = "totalStd";
        public static final String attendance = "attendance";
        public static final String attempt = "attempt";
        public static final String asked = "asked";
        public static final String correct = "correct";
        public static final String perf = "perf";

        //will not be used later
        public static final String date_asking = "date_asking";
        public static final String date_range_asking = "date_range_asking";
        public static final String total_ques = "total_ques";
    }

    public static class acad_perform implements BaseColumns {
        /*deleted from db*/
        public static final String table = "acad_perform";
        public static final String schoolID = "schID";
        public static final String schoolName = "schName";
        public static final String evalPerformRatio = "evalPerformRatio";
        public static final String synced = "synced";
        public static final String date_report = "date_report";
        public static final String date_update = "date_update";
        public static final String time_lm = "time_lm";
        public static final String attendence = "attendence";
        public static final String totalQues = "totalQues";     //total ques asked for a date in a schoolSync, it will be used to calculate performace avg
    }


//========================================================================================================================


    public static class perform implements BaseColumns {
        public static final String table = "perform";

        public static final String sch_id = "sch_id";
        public static final String sch_name = "sch_name";
        public static final String grade = "grade";
        public static final String edu_type = "edu_type";
        public static final String s_code = "s_code";
        public static final String lastAdminPerform = "lastAdminPerform";
        public static final String lastAdminPerformDate = "lastAdminPerformDate";
        public static final String lastEvalPerform = "lastEvalPerform";
        public static final String lastEvalPerformDate = "lastEvalPerformDate";
        public static final String lastAttenPerform = "lastAttenPerform";
        public static final String lastAttenPerformDate = "lastAttenPerformDate";
        public static final String time_lm = "time_lm";

    }

    //============================== notification ===============================================
    public static class notif_bckup implements BaseColumns {
        public static final String table = "notif_bckup";

        public static final String sch_id = "sch_id";
        public static final String server_id_ques = "server_id_ques";
        public static final String local_id_ques = "local_id_ques";
        public static final String server_id_answer = "server_id_answer";
        public static final String local_id_answer = "local_id_answer";
        public static final String ques_category_id = "ques_category_id";
        public static final String category = "category";
        public static final String questionTitle = "questionTitle";
        public static final String question = "question";
        public static final String answer = "answer";
        public static final String priority = "priority";
        public static final String details = "details";
        public static final String serverQuestion = "serverQuestion";
        public static final String answerWeight = "answerWeight";
        public static final String date_report = "date_report";
        public static final String date_target = "date_target";
        public static final String date_solve = "date_solve";
        public static final String synced = "synced";
        public static final String time_lm = "time_lm";
        public static final String notify = "notify";
        public static final String notify_pause = "notify_pause";// when no clicked in notification activity for showing this notif later
        public static final String notify_his = "notify_his";
        public static final String n_update_date = "n_date_update";
        public static final String grade = "grade";
        public static final String grade_id = "grade_id";
    }


    //========================== payment ===============================================================================================

    public static class payments implements BaseColumns {
        public static final String table = "payments";

        public static final String sch_id = "sch_id";
        public static final String grade_id = "grade_id";
        public static final String sch_name = "sch_name";
        public static final String std_id = "std_id";
        public static final String std_name = "std_name";
        public static final String roll = "roll";
        public static final String payable = "payable";
        public static final String paid = "paid";
        public static final String month = "month";
        public static final String year = "year";
        public static final String category = "category";
        public static final String type = "type";
        public static final String time_lm = "time_lm";


        /*CREATE TABLE `payments` (
    `sch_id`	TEXT,
	``	TEXT,
	`sch_name`	TEXT,
	`std_id`	TEXT,
	`std_name`	TEXT,
	`roll`	TEXT,
	`payable`	TEXT,
	`paid`	TEXT,
	`month`	TEXT,
	`year`	TEXT,
	`category`	TEXT,
	`type`	TEXT,
	`time_lm`	TEXT
);*/
    }
}
