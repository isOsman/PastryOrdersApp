package com.example.osman.orders;

public abstract class Request {
    public static final int ORDER_ADD = 1;
    public static final int ORDER_EDIT = 2;

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
