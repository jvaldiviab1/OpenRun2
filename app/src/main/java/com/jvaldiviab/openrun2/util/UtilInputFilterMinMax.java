package com.jvaldiviab.openrun2.util;

import android.text.InputFilter;
import android.text.Spanned;

public class UtilInputFilterMinMax implements InputFilter {
    private int min, max;
    public UtilInputFilterMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }
    public UtilInputFilterMinMax(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if((dest.toString()+source.toString()).length()!=1) {
                if (isInRange(min, max, input))
                    return null;
            }else{
            return null;}
        } catch (NumberFormatException nfe) {

        }
        return "";
    }
    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
