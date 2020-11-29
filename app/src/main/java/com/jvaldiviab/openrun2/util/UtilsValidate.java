package com.jvaldiviab.openrun2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsValidate {

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
}


