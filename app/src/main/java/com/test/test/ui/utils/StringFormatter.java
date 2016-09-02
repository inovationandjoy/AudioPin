package com.test.test.ui.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by sparvez on 2016-09-02.
 */
public class StringFormatter {
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
    public static String format(Date date, boolean millis) {
        Calendar calendar = new GregorianCalendar(UTC_TIME_ZONE, Locale.US);
        calendar.setTime(date);
        int capacity = "yyyy-MM-dd hh:mm:ss".length();
        capacity += millis ? ".sss".length() : 0;
        StringBuilder formatted = new StringBuilder(capacity);
        padInt(formatted, calendar.get(Calendar.YEAR), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(Calendar.MONTH) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(Calendar.DAY_OF_MONTH), "dd".length());
        formatted.append(' ');
        padInt(formatted, calendar.get(Calendar.HOUR_OF_DAY), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(Calendar.MINUTE), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(Calendar.SECOND), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(Calendar.MILLISECOND), "sss".length());
        }
        return formatted.toString();
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }
}
