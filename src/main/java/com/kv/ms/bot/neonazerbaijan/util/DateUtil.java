package com.kv.ms.bot.neonazerbaijan.util;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static long MILLIS_PER_N_HOURS = 60 * 60 * 1000L;
    public static Date lastPublishingDate;

    static {
        try {
            String lastPublishingDate_OLD = "2022-06-07 10:00:59";
            lastPublishingDate = sdf.parse(lastPublishingDate_OLD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static boolean isMoreThanNHours(Long n) {
        var HOURS = MILLIS_PER_N_HOURS * n;
        return Math.abs(getCurrentDate().getTime() - lastPublishingDate.getTime()) > HOURS;
    }

    @SneakyThrows
    public static Date getCurrentDate() {
        String currentDate = sdf.format(new Date());
        return sdf.parse(currentDate);
    }

}
