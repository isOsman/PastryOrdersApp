package com.example.osman.orders;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    //return  date in a localized format
    public static String getDate() {
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }
}