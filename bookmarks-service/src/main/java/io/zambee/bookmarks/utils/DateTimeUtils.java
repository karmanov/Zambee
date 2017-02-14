package io.zambee.bookmarks.utils;

import org.joda.time.DateTime;

public class DateTimeUtils {

    public static DateTime unixTimeToDate(String sUnixTime) {
        long l = Long.parseLong(sUnixTime);
        return new DateTime(l * 1000);
    }
}
