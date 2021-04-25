/*
 * Copyright (c) MiR FeRdOuS
 */

package com.example.f.schMon.view.sync_mir;

import android.support.annotation.Nullable;

public class API {

    //____________________urls of api__________________________________________________
    public static String urlLgin = "http://bepmis.brac.net/rest/auth/login";

    public static String urlSchoolListPowise =
            "http://bepmis.brac.net/rest/institute?max=10&start=0";

    public static String urlSchoolby_id =
            "http://bepmis.brac.net/rest/institute/";


    public static String urlStudentListPowise =
            "http://bepmis.brac.net/rest/studentSync?bkashNumber=&dropout=&fullName=&gradeId=&guardianMobile=&instituteId=&locationHierarchy=&locationId=&locationTypeUdvId=&max=10&nameOfTransferredSchool=&sex=&start=0&studentId=&typeOfCsnUdvId=&typeOfEthnicityCommunityUdvId=&waiver=";

    public static String urlStudentby_id =
            "http://bepmis.brac.net/rest/studentSync/";


    public static String urlTeacherListPowise =
            "http://bepmis.brac.net/rest/teacherSync/listAllTeacher?bkashNumber=&dropOut=&email=&instituteId=&locationHierarchy=&locationId=&max=10&mobileNumber=&start=0&userCategoryUdvId=Teacher&username=";

    public static String urlTeacherby_id =
            "http://bepmis.brac.net/rest/teacherSync/";


    public static String baseUrl = "http://bepmis.brac.net";
    private static String schList;
    private static String schID;
    private static String stdList;
    private static String stdID;
    private static String techList;
    private static String techID;
    private static String login = "http://bepmis.brac.net/rest/apps/login";


    public static String getLogin() {
        return login;
    }

    public static String getSchList(@Nullable String max, String u, String p) {
        if (max == null)
            schList = "http://bepmis.brac.net/rest/apps/institute?uname=" + u + "&passw=" + p;
        else
            schList = "http://bepmis.brac.net/rest/apps/institute?uname=" + u + "&passw=" + p + "&max=" + max;
        return schList;
    }

    public static String getSchID(String id, String u, String p) {
        schID = "http://bepmis.brac.net/rest/apps/institute/" + id + "?uname=kholilur&passw=123456";
        return schID;
    }

    public static String getStdList(@Nullable String max, String u, String p) {
        if (max == null)
            stdList = "http://bepmis.brac.net/rest/apps/studentSync?uname=" + u + "&passw=" + p;
        else
            stdList = "http://bepmis.brac.net/rest/apps/studentSync?uname=" + u + "&passw=" + p + "&max=" + max;
        return stdList;
    }

    public static String getStdID(String id, String u, String p) {
        stdID = "http://bepmis.brac.net/rest/apps/studentSync/" + id + "?uname=kholilur&passw=123456";
        return stdID;
    }

    public static String getTechList(@Nullable String max, String u, String p) {
        if (max == null)
            techList = "http://bepmis.brac.net/rest/apps/teacherSync/listAllTeacher?uname=" + u + "&passw=" + p;
        else
            techList = "http://bepmis.brac.net/rest/apps/teacherSync/listAllTeacher?uname=" + u + "&passw=" + p + "&max=" + max;
        return techList;
    }

    public static String getTechID(String id, String u, String p) {
        techID = "http://bepmis.brac.net/rest/apps/teacherSync/" + id + "?uname=kholilur&passw=123456";
        return techID;
    }






    /*
    //______________________users________________________________________________
    public static String userAdmin = "{\n" + "  \"username\": \"admin\",\n" + "  \"password\": \"@dmin\"\n" + "}";
    public static String userKholilur = "{\n" + "  \"username\": \"kholilur\",\n" + "  \"password\": \"123456\"\n" + "}";


    //________________________Temp______________________________________________
    public static String resLogin = "{\"module\":null,\"request_type\":null,\"success\":\"1\",\"message\":\"Successful\",\"total\":null,\"model\":{\"id\":null,\"username\":\"admin\",\"email\":null,\"password\":null,\"firstName\":null,\"middleName\":null,\"lastName\":null,\"fatherName\":null,\"motherName\":null,\"husbandName\":null,\"mobileNumber\":null,\"dateOfBirth\":null,\"gender\":null,\"status\":null,\"designationUdvId\":null,\"roleId\":null,\"upLevelId\":null,\"imagePath\":null,\"locationId\":null,\"userCategoryUdvId\":null,\"userHierarchyId\":null,\"locationTypeUdvId\":null,\"latitude\":null,\"longitude\":null,\"organizationId\":null,\"instituteId\":null,\"locationHierarchy\":null,\"staffGrageUdvId\":null,\"dropOut\":null,\"dropOutReason\":null,\"bracGraduate\":null,\"teachingExpBrac\":null,\"replacement\":null,\"maritalStatus\":null,\"religion\":null,\"education\":null,\"nid\":null,\"presentAddress\":null,\"permanentAddress\":null,\"joiningDate\":null,\"onlyTeacher\":false,\"roleModel\":null,\"categoryModel\":null,\"totalLeave\":null,\"salaryEditPermission\":false,\"bkashNumber\":null,\"gradeName\":null,\"instituteName\":null,\"monthlySalary\":null,\"confirm\":false,\"instituteTypeUdvId\":null,\"slab\":null,\"joiningDateBrac\":null,\"joiningDatePace\":null,\"branchName\":null,\"bepArea\":null,\"district\":null,\"branchJoiningDate\":null,\"lastSlabChangeDate\":null,\"homeDistrict\":null,\"qualification\":null,\"lastSubject\":null,\"lastEduInstitute\":null,\"roleName\":null,\"workingPlace\":null,\"additionalLocationIds\":null}}";
    public static String garbagResponse = "{\"module\":null,\"request_type\":null," +
            "\"success\":\"0\",\"message\":\"Invalid username or password\"," +
            "\"total\":null,\"model\":null}";*/

//==================================new ========================================================

    /*http://bepmis.brac.net/rest/apps/login
http://bepmis.brac.net/rest/apps/education-type/list?uname=kholilur&passw=123456
http://bepmis.brac.net/rest/apps/institute?uname=kholilur&passw=123456
http://bepmis.brac.net/rest/apps/student?uname=kholilur&passw=123456
http://bepmis.brac.net/rest/apps/teacher/listAllTeacher?uname=kholilur&passw=123456
http://bepmis.brac.net/rest/apps/teacher/47272806-7016-user-89ee-ba03882a41d9?uname=kholilur&passw=123456
http://bepmis.brac.net/rest/apps/student/47272810-2988-stud-b929-b55abe6bd276?uname=kholilur&passw=123456
http://bepmis.brac.net/rest/apps/institute/47272800-1957-inst-8631-119f6fad8b4c?uname=kholilur&passw=123456*/
}
