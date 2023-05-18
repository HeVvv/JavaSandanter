package com.tkachev.javasandantertesttask.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateUtil {

    public static SimpleDateFormat getDateFormat() {
        String ISO_DATE_FORMAT_ZERO_OFFSET = "dd-MM-yyyy hh:mm:ss:SSS'";
        String UTC_TIMEZONE_NAME = "UTC";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_DATE_FORMAT_ZERO_OFFSET);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(UTC_TIMEZONE_NAME));
        return simpleDateFormat;
    }

}
