package com.canyon.commons;

import java.text.MessageFormat;

public class StringUtils {
    public static String format(String pattern, Object... args) {
        return MessageFormat.format(pattern, args);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }
}
