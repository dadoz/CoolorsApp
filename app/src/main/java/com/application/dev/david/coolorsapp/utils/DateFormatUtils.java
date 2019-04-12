package com.application.dev.david.coolorsapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    private static String pattern = "hh:mm dd MMM yy";

    public static String formatDate(Date createdAt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(createdAt);
    }
}
