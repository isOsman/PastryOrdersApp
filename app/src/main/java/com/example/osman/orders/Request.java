package com.example.osman.orders;

import android.content.Context;

import androidx.appcompat.widget.Toolbar;

public abstract class Request {
    public static final int ORDER_ADD = 1;
    public static final int ORDER_EDIT = 2;

    //adds
    public static final String ADD_ON = "ON";
    public static final String ADD_OFF = "OFF";
    public static boolean ADD_IS_ON = true;

    //notifications
    public static int NOTIFICATION_HOUR = 16;
    public static int NOTIFICATION_MINUTE = 20;

    //dialogs
    public static final String DIALOG_ON = "ON";
    public static final String DIALOG_OFF = "OFF";


    public static String getRequestName(int requestCode) {
        if (requestCode == 1) {
            return "ORDER_ADD";
        } else if (requestCode == 2) {
            return "ORDER_EDIT";
        }else {
            return "NO CODE";
        }
    }





}
