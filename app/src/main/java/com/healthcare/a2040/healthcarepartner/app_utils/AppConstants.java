package com.healthcare.a2040.healthcarepartner.app_utils;

public class AppConstants {

    public final static int SUCCESS = 200;
    public final static int FAILURE = 404;

    public final static int LIST_TYPE = 222;
    public final static int GROUP_TYPE = 111;

    public final static String S_LEAD_COMPLETED = "LEAD_COMPLETED";
    public final static String S_LEAD_CANCELLED = "LEAD_CANCELLED";
    public final static String S_LEAD_ACCEPTED = "LEAD_ACCEPTED";


    public final static String DeviceID = "DeviceID";
    public final static String S_IS_LOGGED_IN = "LoggedIn";
    public final static String S_IS_VERIFIED = "VERIFIED";
    public final static String S_USER_ID = "USER_ID";

    public final static String S_USER_EMAIL = "USER_EMAIL";
    public final static String S_USER_NAME = "USER_NAME";
    public final static String S_USER_PASSWORD = "USER_PASSWORD";
    public final static String S_USER_PHONE = "USER_PHONE";
    public final static String S_ADDRESS = "ADDRESS";

    private static final String BaseUrl = "http://www.gstbillbanao.com/webservice/";
    public static final String URL_Login = BaseUrl + "user.php?type=login";
    public static final String URL_LogOut = BaseUrl + "user.php?type=logout";
    public static final String URL_SignUp = BaseUrl + "user.php?type=logout";

}
