package com.kv.ms.bot.neonazerbaijan.util;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static long MILLIS_PER_N_HOURS = 60 * 60 * 1000L;
    public static Date lastPublishingDate;

    {
        try {
            String lastPublishingDate_OLD = "2022-06-06 00:00:59";
            lastPublishingDate = sdf.parse(lastPublishingDate_OLD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static boolean isMoreThanNHours(Long n) {
        String currentDate = sdf.format(new Date());
        Date currentDateTime = sdf.parse(currentDate);

        var HOURS = MILLIS_PER_N_HOURS * n;

        lastPublishingDate = currentDateTime;
        return Math.abs(currentDateTime.getTime() - lastPublishingDate.getTime()) > HOURS;
    }
}
