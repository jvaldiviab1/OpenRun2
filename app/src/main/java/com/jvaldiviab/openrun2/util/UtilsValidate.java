package com.jvaldiviab.openrun2.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsValidate {

    // Factor de conversión de metros a millas
    private static final double METER_TO_MILE_CONVERSION = 0.000621371;

    public static boolean emailAndPass(String email, String password) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true && password.length() >= 6)
            return true;
        else
            return false;
    }

    public static float convertMetersToMiles(float meters){
        return meters * (float)METER_TO_MILE_CONVERSION;
    }

    public static float getAveragePace(float meters, long milliseconds){
        int secs = (int)(milliseconds / 1000);
        float mins = (float)secs / 60;
        float miles = convertMetersToMiles(meters);
        return mins / miles;
    }

    public static String convertDecimalToMins(float decimal){
        int mins = (int) Math.floor(decimal);
        double fractional = decimal - mins;
        int secs = (int) Math.round(fractional * 60);
        return String.format(Locale.getDefault(), "%d:%02d", mins, secs);
    }

    public static String getStopwatchTime(long startMillis, long endMillis){
        int secs = (int)((endMillis - startMillis) / 1000);
        int mins = secs / 60;
        int hours = mins / 60;
        secs %= 60;
        mins %= 60;
        return String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, mins, secs);
    }

    public static float getAverageP(float meters, long milliseconds){
        int secs = (int)(milliseconds / 1000);
        float mins = (float)secs / 60;
        float miles = convertMetersToMiles(meters);
        return mins / miles;
    }

    public static String getDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH) + 1;
        /* Months are indexed starting at 0
         *  Jan = 0, Feb = 1, etc. */
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        return String.format(Locale.getDefault(), "%d/%d/%d", month, dayOfMonth, year);
    }


}


