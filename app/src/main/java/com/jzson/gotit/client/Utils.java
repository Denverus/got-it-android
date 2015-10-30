package com.jzson.gotit.client;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Denis on 10/18/2015.
 */
public class Utils {

    private static final  String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private static final  String DATE_BIRTH_FORMAT = "yyyy-MM-dd";

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

    public static String dateBirthToString(Date date) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_BIRTH_FORMAT, Locale.US);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    public static String ageToString(Date date) {
        if (date != null) {
            Date now = new Date();
            long years = (now.getTime() - date.getTime())/ DateUtils.YEAR_IN_MILLIS;
            return years+" years old";
        } else {
            return "";
        }
    }

    public static Date getRandomBirthDate() {
        Random rnd = new Random();
        int year = rnd.nextInt(25)+1980;
        int month = rnd.nextInt(12);
        int day = rnd.nextInt(28);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }
}
