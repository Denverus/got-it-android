package com.jzson.gotit.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Denis on 10/18/2015.
 */
public class Utils {

    private static final  String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static String doubleToString(double value) {
        return value + "";
    }

    public static String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            return sdf.format(date);
        } else {
            return "";
        }
    }
}
