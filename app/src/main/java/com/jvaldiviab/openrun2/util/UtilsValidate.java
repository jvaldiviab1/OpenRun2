package com.jvaldiviab.openrun2.util;

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


}


