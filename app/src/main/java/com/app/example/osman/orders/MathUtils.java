package com.app.example.osman.orders;

public class MathUtils {

    public static int DEFAULT_CONTENT_PERCENT = 70;

    public static int  getPercent(int from,int percent){
        return  (int) Math.floor(from * percent / 100);
    }


}
