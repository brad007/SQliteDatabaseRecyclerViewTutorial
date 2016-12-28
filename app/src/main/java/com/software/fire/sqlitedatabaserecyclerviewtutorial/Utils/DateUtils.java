package com.software.fire.sqlitedatabaserecyclerviewtutorial.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Brad on 12/27/2016.
 */

public class DateUtils {
    public static String getReadableTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
        Date resultDate = new Date(Long.parseLong(time));
        return sdf.format(resultDate);
    }

    public static String getReadableDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        Date resultDate = new Date(Long.parseLong(s));
        return sdf.format(resultDate);
    }
}
