package com.kv.ms.bot.neonazerbaijan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static long MILLIS_PER_N_HOURS = 60 * 60 * 1000L;

    public static boolean isMoreThanNHours(Long n, Date currentDate, Date lastPublishingDate) {
        var HOURS = MILLIS_PER_N_HOURS * n;
        return Math.abs(currentDate.getTime() - lastPublishingDate.getTime()) > HOURS;
    }
}
