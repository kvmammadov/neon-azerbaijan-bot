package com.kv.ms.bot.neonazerbaijan.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtil {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private final static long MILLIS_PER_N_HOURS = 6 * 10 * 1000L;
    private final static long MILLIS_PER_N_HOURS = 60 * 60 * 1000L;
    public static Date lastPublishingDate = getCurrentDate();

    @SneakyThrows
    public static boolean isMoreThanNHours(Long n) {
        var HOURS = MILLIS_PER_N_HOURS * n;
        return Math.abs(getCurrentDate().getTime() - lastPublishingDate.getTime()) >= HOURS;
    }

    @SneakyThrows
    public static Date getCurrentDate() {
        String currentDate = sdf.format(new Date());
        return sdf.parse(currentDate);
    }
}
