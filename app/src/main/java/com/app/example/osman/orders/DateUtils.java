package com.app.example.osman.orders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static final int WAKE_HOUR = 7;
    //return  date in a localized format
    public static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }
    public static String getParseFormat(){return "EEE MMM dd HH:mm:ss zzz yyyy";}

    public static int getHoursBetween(Date date1,Date date2){
        int MILLS_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLS_TO_HOUR;
    }

    public static long getDateDiff(Date date1,Date date2,TimeUnit timeUnit){
        long diffInMills = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMills,TimeUnit.MILLISECONDS);
    }



}